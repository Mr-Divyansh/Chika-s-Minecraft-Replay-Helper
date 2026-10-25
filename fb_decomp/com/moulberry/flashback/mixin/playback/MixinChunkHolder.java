/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ChunkHolder
 *  net.minecraft.world.level.chunk.LevelChunk
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.playback;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.ext.LevelChunkExt;
import com.moulberry.flashback.playback.ReplayServer;
import java.util.BitSet;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ChunkHolder.class}, priority=1100)
public class MixinChunkHolder {
    @Shadow
    private boolean hasChangedSections;
    @Shadow
    @Final
    private BitSet skyChangedLightSectionFilter;
    @Shadow
    @Final
    private BitSet blockChangedLightSectionFilter;

    @Inject(method={"broadcastChanges"}, at={@At(value="HEAD")})
    public void broadcastChanges(LevelChunk levelChunk, CallbackInfo ci) {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer != null && levelChunk instanceof LevelChunkExt) {
            LevelChunkExt levelChunkExt = (LevelChunkExt)levelChunk;
            if (this.hasChangedSections || !this.skyChangedLightSectionFilter.isEmpty() || !this.blockChangedLightSectionFilter.isEmpty()) {
                levelChunkExt.flashback$setCachedChunkId(-1);
            }
        }
    }
}

