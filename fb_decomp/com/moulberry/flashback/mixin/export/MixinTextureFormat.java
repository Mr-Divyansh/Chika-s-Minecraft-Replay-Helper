/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.textures.TextureFormat
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package com.moulberry.flashback.mixin.export;

import com.mojang.blaze3d.textures.TextureFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={TextureFormat.class})
public enum MixinTextureFormat {
    FLASHBACK_R32_FLOAT(4);


    @Shadow
    private MixinTextureFormat(int pixelSize) {
    }
}

