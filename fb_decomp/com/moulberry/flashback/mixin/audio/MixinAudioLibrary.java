/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.mojang.blaze3d.audio.Library
 *  org.jspecify.annotations.Nullable
 *  org.lwjgl.openal.ALC10
 *  org.lwjgl.openal.SOFTLoopback
 *  org.lwjgl.system.MemoryStack
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.audio;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.audio.Library;
import com.moulberry.flashback.Flashback;
import java.nio.IntBuffer;
import org.jspecify.annotations.Nullable;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.SOFTLoopback;
import org.lwjgl.system.MemoryStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Library.class})
public class MixinAudioLibrary {
    @Shadow
    public long currentDevice;
    @Unique
    private boolean usingLoopbackDevice = false;

    @Inject(method={"init"}, at={@At(value="HEAD")})
    public void init(CallbackInfo ci) {
        boolean bl = this.usingLoopbackDevice = Flashback.isExporting() && Flashback.EXPORT_JOB.getSettings().recordAudio();
        if (this.usingLoopbackDevice) {
            Flashback.LOGGER.info("Enabling loopback device for recording audio");
        }
    }

    @WrapOperation(method={"init"}, at={@At(value="INVOKE", target="Lcom/mojang/blaze3d/audio/Library;openDeviceOrFallback(Ljava/lang/String;Ljava/lang/String;)J")})
    public long init_openDevice(@Nullable String preferredDevice, @Nullable String systemDefaultDevice, Operation<Long> original) {
        if (this.usingLoopbackDevice) {
            return SOFTLoopback.alcLoopbackOpenDeviceSOFT((CharSequence)null);
        }
        return (Long)original.call(new Object[]{preferredDevice, systemDefaultDevice});
    }

    @WrapOperation(method={"init"}, at={@At(value="INVOKE", target="Lorg/lwjgl/openal/ALC10;alcCreateContext(JLjava/nio/IntBuffer;)J", remap=false)})
    public long init_createContext(long deviceHandle, IntBuffer attrList, Operation<Long> original) {
        if (this.usingLoopbackDevice) {
            try (MemoryStack memoryStack = MemoryStack.stackPush();){
                int channels = Flashback.EXPORT_JOB.getSettings().stereoAudio() ? 5377 : 5376;
                IntBuffer intBuffer = memoryStack.callocInt(7).put(6545).put(5126).put(6544).put(channels).put(4103).put(48000).put(0).flip();
                long l = ALC10.alcCreateContext((long)this.currentDevice, (IntBuffer)intBuffer);
                return l;
            }
        }
        return (Long)original.call(new Object[]{deviceHandle, attrList});
    }
}

