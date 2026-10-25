/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.renderer.Projection
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.playback;

import com.mojang.blaze3d.systems.RenderSystem;
import com.moulberry.flashback.ext.ProjectionExt;
import net.minecraft.client.renderer.Projection;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Projection.class})
public class MixinProjection
implements ProjectionExt {
    @Shadow
    private boolean isMatrixDirty;
    @Shadow
    private long matrixVersion;
    @Shadow
    @Final
    private Matrix4f matrix;
    @Shadow
    private float width;
    @Shadow
    private float height;
    @Shadow
    private boolean orthoInvertY;
    @Shadow
    private float zNear;
    @Shadow
    private float zFar;
    @Unique
    private boolean centeredOrtho = false;

    @Override
    public void flashback$setCenteredOrtho(boolean centered) {
        if (this.centeredOrtho != centered) {
            this.centeredOrtho = centered;
            this.isMatrixDirty = true;
        }
    }

    @Inject(method={"setupPerspective"}, at={@At(value="HEAD")})
    public void setupPerspective(CallbackInfo ci) {
        this.flashback$setCenteredOrtho(false);
    }

    @Inject(method={"setupOrtho"}, at={@At(value="HEAD")})
    public void setupOrtho(CallbackInfo ci) {
        this.flashback$setCenteredOrtho(false);
    }

    @Inject(method={"getMatrix"}, at={@At(value="HEAD")}, cancellable=true)
    public void getMatrix(Matrix4f dest, CallbackInfoReturnable<Matrix4f> cir) {
        if (this.isMatrixDirty && this.centeredOrtho) {
            this.isMatrixDirty = false;
            ++this.matrixVersion;
            cir.setReturnValue((Object)dest.set((Matrix4fc)this.matrix.setOrtho(-this.width / 2.0f, this.width / 2.0f, this.orthoInvertY ? this.height / 2.0f : -this.height / 2.0f, this.orthoInvertY ? -this.height / 2.0f : this.height / 2.0f, this.zNear, this.zFar, RenderSystem.getDevice().isZZeroToOne())));
        }
    }
}

