/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerChunkCache
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.chunk.ChunkGeneratorStructureState
 *  org.jetbrains.annotations.NotNull
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.replay_server;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.ext.ServerLevelExt;
import com.moulberry.flashback.playback.ReplayServer;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ServerLevel.class}, priority=900)
public abstract class MixinServerLevel
implements ServerLevelExt {
    @Unique
    private long seedHash = 0L;
    @Unique
    private final LongSet validChunks = new LongOpenHashSet();
    @Unique
    private boolean canSpawnEntities = true;

    @Shadow
    @NotNull
    public abstract MinecraftServer getServer();

    @Override
    public void flashback$setSeedHash(long seedHash) {
        this.seedHash = seedHash;
    }

    @Override
    public long flashback$getSeedHash() {
        return this.seedHash;
    }

    @Override
    public boolean flashback$shouldSendChunk(long pos) {
        return this.validChunks.contains(pos);
    }

    @Override
    public void flashback$markChunkAsSendable(long pos) {
        this.validChunks.add(pos);
    }

    @Override
    public void flashback$setCanSpawnEntities(boolean canSpawnEntities) {
        this.canSpawnEntities = canSpawnEntities;
    }

    @Inject(method={"addFreshEntity"}, at={@At(value="HEAD")}, cancellable=true)
    public void addFreshEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!(entity instanceof Player) && !this.canSpawnEntities) {
            cir.setReturnValue((Object)false);
        }
    }

    @WrapOperation(method={"<init>"}, at={@At(value="INVOKE", target="Lnet/minecraft/server/level/ServerChunkCache;getGeneratorState()Lnet/minecraft/world/level/chunk/ChunkGeneratorStructureState;")})
    public ChunkGeneratorStructureState getGeneratorState(ServerChunkCache instance, Operation<ChunkGeneratorStructureState> original) {
        if (this.getServer() instanceof ReplayServer) {
            return null;
        }
        return (ChunkGeneratorStructureState)original.call(new Object[]{instance});
    }

    @WrapOperation(method={"<init>"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/level/chunk/ChunkGeneratorStructureState;ensureStructuresGenerated()V")})
    public void ensureStructuresGenerated(ChunkGeneratorStructureState instance, Operation<Void> original) {
        if (instance != null) {
            original.call(new Object[]{instance});
        }
    }

    @Inject(method={"waitForEntities"}, at={@At(value="HEAD")}, cancellable=true)
    public void waitForChunkAndEntities(ChunkPos chunkPos, int i, CallbackInfo ci) {
        if (this.getServer() instanceof ReplayServer) {
            ci.cancel();
        }
    }
}

