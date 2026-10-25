/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.SoundEngine
 *  net.minecraft.client.sounds.SoundEngine$PlayResult
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.audio;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.sound.FlashbackAudioManager;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={SoundEngine.class})
public class MixinSoundEngine {
    @Unique
    private boolean wasExportingAudio = false;

    @Inject(method={"destroy"}, at={@At(value="INVOKE", target="Lcom/mojang/blaze3d/audio/Library;cleanup()V")})
    public void destroy(CallbackInfo ci) {
        FlashbackAudioManager.invalidateLoadedBuffers();
    }

    @Inject(method={"shouldChangeDevice"}, at={@At(value="HEAD")}, cancellable=true)
    public void shouldChangeDevice(CallbackInfoReturnable<Boolean> cir) {
        boolean isExportingAudio;
        boolean bl = isExportingAudio = Flashback.isExporting() && Flashback.EXPORT_JOB.getSettings().recordAudio();
        if (this.wasExportingAudio != isExportingAudio) {
            this.wasExportingAudio = isExportingAudio;
            cir.setReturnValue((Object)true);
        } else if (isExportingAudio) {
            cir.setReturnValue((Object)false);
        }
    }

    @Inject(method={"play"}, at={@At(value="HEAD")}, cancellable=true)
    public void play(SoundInstance soundInstance, CallbackInfoReturnable<SoundEngine.PlayResult> cir) {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer != null && Flashback.EXPORT_JOB == null && replayServer.replayPaused) {
            cir.setReturnValue((Object)SoundEngine.PlayResult.NOT_STARTED);
        }
    }
}

