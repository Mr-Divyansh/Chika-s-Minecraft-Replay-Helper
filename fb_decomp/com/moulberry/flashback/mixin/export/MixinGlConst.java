/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.opengl.GlConst
 *  com.mojang.blaze3d.textures.TextureFormat
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.export;

import com.mojang.blaze3d.opengl.GlConst;
import com.mojang.blaze3d.textures.TextureFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={GlConst.class})
public class MixinGlConst {
    @Inject(method={"Lcom/mojang/blaze3d/opengl/GlConst;toGlInternalId(Lcom/mojang/blaze3d/textures/TextureFormat;)I"}, at={@At(value="HEAD")}, cancellable=true)
    private static void toGlInternalId(TextureFormat textureFormat, CallbackInfoReturnable<Integer> cir) {
        if (textureFormat == TextureFormat.FLASHBACK_R32_FLOAT) {
            cir.setReturnValue((Object)33326);
        }
    }

    @Inject(method={"Lcom/mojang/blaze3d/opengl/GlConst;toGlExternalId(Lcom/mojang/blaze3d/textures/TextureFormat;)I"}, at={@At(value="HEAD")}, cancellable=true)
    private static void toGlExternalId(TextureFormat textureFormat, CallbackInfoReturnable<Integer> cir) {
        if (textureFormat == TextureFormat.FLASHBACK_R32_FLOAT) {
            cir.setReturnValue((Object)6403);
        }
    }

    @Inject(method={"Lcom/mojang/blaze3d/opengl/GlConst;toGlType(Lcom/mojang/blaze3d/textures/TextureFormat;)I"}, at={@At(value="HEAD")}, cancellable=true)
    private static void toGlType(TextureFormat textureFormat, CallbackInfoReturnable<Integer> cir) {
        if (textureFormat == TextureFormat.FLASHBACK_R32_FLOAT) {
            cir.setReturnValue((Object)5126);
        }
    }
}

