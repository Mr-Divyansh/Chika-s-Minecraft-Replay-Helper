/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.network.PlayerChunkSender
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
import com.moulberry.flashback.ext.ServerLevelExt;
import com.moulberry.flashback.playback.FlashbackFakePlayer;
import com.moulberry.flashback.playback.ReplayServer;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.PlayerChunkSender;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={PlayerChunkSender.class})
public class MixinPlayerChunkSender {
    @Shadow
    private float desiredChunksPerTick;
    @Shadow
    private int unacknowledgedBatches;
    @Shadow
    private float batchQuota;
    @Shadow
    @Final
    public LongSet pendingChunks;

    @Inject(method={"sendNextChunks"}, at={@At(value="HEAD")}, cancellable=true)
    public void sendNextChunks(ServerPlayer serverPlayer, CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            this.unacknowledgedBatches = 0;
            this.batchQuota = 0.0f;
            this.desiredChunksPerTick = Math.max(this.desiredChunksPerTick, 256.0f);
            if (serverPlayer instanceof FlashbackFakePlayer) {
                this.pendingChunks.clear();
                ci.cancel();
            }
        }
    }

    @Inject(method={"markChunkPendingToSend"}, at={@At(value="HEAD")}, cancellable=true)
    public void markChunkPendingToSend(LevelChunk levelChunk, CallbackInfo ci) {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer != null && !((ServerLevelExt)levelChunk.getLevel()).flashback$shouldSendChunk(levelChunk.getPos().pack())) {
            ci.cancel();
        }
    }
}

