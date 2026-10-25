/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.SectionPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelHeightAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.ChunkAccess
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.chunk.LevelChunk$EntityCreationType
 *  net.minecraft.world.level.chunk.LevelChunkSection
 *  net.minecraft.world.level.chunk.PalettedContainerFactory
 *  net.minecraft.world.level.chunk.UpgradeData
 *  net.minecraft.world.level.levelgen.Heightmap
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.level.levelgen.blending.BlendingData
 *  net.minecraft.world.level.lighting.LightEngine
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.playback;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.ext.LevelChunkExt;
import com.moulberry.flashback.playback.ReplayServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainerFactory;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.lighting.LightEngine;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={LevelChunk.class})
public abstract class MixinLevelChunk
extends ChunkAccess
implements LevelChunkExt {
    @Shadow
    @Final
    private Level level;
    @Unique
    private int cachedChunkId = -1;

    public MixinLevelChunk(ChunkPos chunkPos, UpgradeData upgradeData, LevelHeightAccessor levelHeightAccessor, PalettedContainerFactory palettedContainerFactory, long l, @Nullable LevelChunkSection[] levelChunkSections, @Nullable BlendingData blendingData) {
        super(chunkPos, upgradeData, levelHeightAccessor, palettedContainerFactory, l, levelChunkSections, blendingData);
    }

    @Shadow
    public abstract void addAndRegisterBlockEntity(BlockEntity var1);

    @Shadow
    protected abstract <T extends BlockEntity> void updateBlockEntityTicker(T var1);

    @Shadow
    @Nullable
    public abstract BlockEntity getBlockEntity(BlockPos var1, LevelChunk.EntityCreationType var2);

    @Shadow
    public abstract void markUnsaved();

    @Override
    public int flashback$getCachedChunkId() {
        return this.cachedChunkId;
    }

    @Override
    public void flashback$setCachedChunkId(int id) {
        this.cachedChunkId = id;
    }

    @Inject(method={"setBlockState"}, at={@At(value="RETURN")})
    public void setBlockState(BlockPos blockPos, BlockState blockState, int i, CallbackInfoReturnable<BlockState> cir) {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer == null) {
            return;
        }
        BlockState old = (BlockState)cir.getReturnValue();
        if (old != null && old != blockState) {
            replayServer.blockChangeOccurred(blockPos, blockState);
            this.cachedChunkId = -1;
        }
    }

    @Override
    public BlockState flashback$setBlockStateWithoutUpdates(BlockPos blockPos, BlockState blockState) {
        Block block;
        int localZ;
        int localY;
        int y = blockPos.getY();
        int sectionY = this.getSectionIndex(y);
        if (sectionY < 0 || sectionY >= this.getSectionsCount()) {
            return null;
        }
        LevelChunkSection levelChunkSection = this.getSection(sectionY);
        boolean oldHasOnlyAir = levelChunkSection.hasOnlyAir();
        if (oldHasOnlyAir && blockState.isAir()) {
            return null;
        }
        int localX = blockPos.getX() & 0xF;
        BlockState oldBlockState = levelChunkSection.setBlockState(localX, localY = y & 0xF, localZ = blockPos.getZ() & 0xF, blockState);
        if (oldBlockState == blockState) {
            return null;
        }
        this.cachedChunkId = -1;
        ((Heightmap)this.heightmaps.get(Heightmap.Types.MOTION_BLOCKING)).update(localX, y, localZ, blockState);
        ((Heightmap)this.heightmaps.get(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES)).update(localX, y, localZ, blockState);
        ((Heightmap)this.heightmaps.get(Heightmap.Types.OCEAN_FLOOR)).update(localX, y, localZ, blockState);
        ((Heightmap)this.heightmaps.get(Heightmap.Types.WORLD_SURFACE)).update(localX, y, localZ, blockState);
        boolean newHasOnlyAir = levelChunkSection.hasOnlyAir();
        if (oldHasOnlyAir != newHasOnlyAir) {
            this.level.getChunkSource().getLightEngine().updateSectionStatus(blockPos, newHasOnlyAir);
            this.level.getChunkSource().onSectionEmptinessChanged(this.chunkPos.x(), SectionPos.blockToSectionCoord((int)y), this.chunkPos.z(), newHasOnlyAir);
        }
        if (LightEngine.hasDifferentLightProperties((BlockState)oldBlockState, (BlockState)blockState)) {
            if (this.skyLightSources != null) {
                this.skyLightSources.update((BlockGetter)this, localX, y, localZ);
            }
            this.level.getChunkSource().getLightEngine().checkBlock(blockPos);
        }
        if (!oldBlockState.is((Object)(block = blockState.getBlock())) && oldBlockState.hasBlockEntity()) {
            this.removeBlockEntity(blockPos);
        }
        if (!levelChunkSection.getBlockState(localX, localY, localZ).is((Object)block)) {
            return oldBlockState;
        }
        if (blockState.hasBlockEntity()) {
            BlockEntity blockEntity = this.getBlockEntity(blockPos, LevelChunk.EntityCreationType.CHECK);
            if (blockEntity == null) {
                blockEntity = ((EntityBlock)block).newBlockEntity(blockPos, blockState);
                if (blockEntity != null) {
                    this.addAndRegisterBlockEntity(blockEntity);
                }
            } else {
                blockEntity.setBlockState(blockState);
                this.updateBlockEntityTicker(blockEntity);
            }
        }
        this.markUnsaved();
        return oldBlockState;
    }
}

