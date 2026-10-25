/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.llamalad7.mixinextras.sugar.Local
 *  com.mojang.blaze3d.opengl.DirectStateAccess
 *  com.mojang.blaze3d.textures.GpuTexture
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.export;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.opengl.DirectStateAccess;
import com.mojang.blaze3d.textures.GpuTexture;
import com.moulberry.flashback.exporting.SaveableFramebuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets={"com.mojang.blaze3d.opengl.GlCommandEncoder"})
public class MixinGlCommandEncoder {
    @WrapOperation(method={"copyTextureToBuffer(Lcom/mojang/blaze3d/textures/GpuTexture;Lcom/mojang/blaze3d/buffers/GpuBuffer;JLjava/lang/Runnable;IIIII)V"}, at={@At(value="INVOKE", target="Lcom/mojang/blaze3d/opengl/DirectStateAccess;bindFrameBufferTextures(IIIII)V")})
    public void copyTextureToBuffer_bindFrameBufferTextures(DirectStateAccess instance, int fbo, int color, int depth, int mipLevel, int bindSlot, Operation<Void> original, @Local(argsOnly=true) GpuTexture source) {
        if (SaveableFramebuffer.fixDepthDownload && source.getFormat().hasDepthAspect() && color != 0 && depth == 0) {
            int temp = color;
            color = depth;
            depth = temp;
        }
        original.call(new Object[]{instance, fbo, color, depth, mipLevel, bindSlot});
    }
}

