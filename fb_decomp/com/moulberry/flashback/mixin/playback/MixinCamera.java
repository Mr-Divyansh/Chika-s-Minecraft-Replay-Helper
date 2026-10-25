/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Camera
 *  net.minecraft.client.DeltaTracker
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.Projection
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Matrix4f
 *  org.joml.Quaternionf
 *  org.joml.Vector2f
 *  org.joml.Vector3d
 *  org.joml.Vector3f
 *  org.jspecify.annotations.Nullable
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.playback;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.combo_options.ExportProjection;
import com.moulberry.flashback.exporting.ExportJob;
import com.moulberry.flashback.ext.MinecraftExt;
import com.moulberry.flashback.ext.ProjectionExt;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import com.moulberry.flashback.visuals.AccurateEntityPositionHandler;
import com.moulberry.flashback.visuals.CameraRotation;
import java.util.Objects;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Projection;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Camera.class})
public abstract class MixinCamera {
    @Shadow
    private float eyeHeightOld;
    @Shadow
    public float eyeHeight;
    @Shadow
    private @Nullable Entity entity;
    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    private int matrixPropertiesDirty;
    @Shadow
    private float depthFar;
    @Shadow
    @Final
    private Projection projection;
    @Shadow
    private float fov;
    @Shadow
    private Vec3 position;
    @Shadow
    @Final
    private Vector3f forwards;
    @Shadow
    private boolean isPanoramicMode;

    @Shadow
    protected abstract void setRotation(float var1, float var2);

    @Shadow
    protected abstract void setPosition(double var1, double var3, double var5);

    @Shadow
    protected abstract void setupOrtho(float var1, float var2, float var3, float var4, boolean var5);

    @Inject(method={"update"}, at={@At(value="RETURN")})
    public void afterSetPosition(DeltaTracker deltaTracker, CallbackInfo ci) {
        Vector3d position;
        if (this.entity == null) {
            return;
        }
        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(false);
        Vector2f rotation = AccurateEntityPositionHandler.getAccurateRotation(this.entity, partialTick);
        if (rotation != null) {
            this.setRotation(rotation.y, rotation.x);
        }
        if ((position = AccurateEntityPositionHandler.getAccuratePosition(this.entity, partialTick)) != null) {
            this.setPosition(position.x, position.y + (double)Mth.lerp((float)partialTick, (float)this.eyeHeightOld, (float)this.eyeHeight), position.z);
        }
    }

    @WrapMethod(method={"getCameraEntityPartialTicks"})
    public float getCameraEntityPartialTicks(DeltaTracker deltaTracker, Operation<Float> original) {
        float originalPartialTick = ((Float)original.call(new Object[]{deltaTracker})).floatValue();
        if (Flashback.isInReplay() && this.entity == this.minecraft.player) {
            return ((MinecraftExt)this.minecraft).flashback$getLocalPlayerPartialTick(originalPartialTick);
        }
        return originalPartialTick;
    }

    @WrapMethod(method={"rotation"})
    public Quaternionf rotation(Operation<Quaternionf> original) {
        Quaternionf modifiedQuaternion;
        Quaternionf originalQuaternion = (Quaternionf)original.call(new Object[0]);
        if (!Objects.equals(originalQuaternion, modifiedQuaternion = CameraRotation.modifyViewQuaternion(originalQuaternion))) {
            this.matrixPropertiesDirty |= 3;
        }
        return modifiedQuaternion;
    }

    @WrapMethod(method={"calculateFov"})
    public float calculateFov(float partialTicks, Operation<Float> original) {
        if (Flashback.isInReplay()) {
            if (this.isPanoramicMode) {
                return 90.0f;
            }
            EditorState editorState = EditorStateManager.getCurrent();
            if (editorState != null && editorState.replayVisuals.overrideFov) {
                return editorState.replayVisuals.overrideFovAmount;
            }
            return ((Integer)this.minecraft.options.fov().get()).intValue();
        }
        return ((Float)original.call(new Object[]{Float.valueOf(partialTicks)})).floatValue();
    }

    @WrapOperation(method={"update"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/Camera;setupPerspective(FFFFF)V")})
    public void update_setupPerspective(Camera instance, float zNear, float zFar, float fov, float width, float height, Operation<Void> original) {
        ExportJob exportJob = Flashback.EXPORT_JOB;
        if (exportJob != null) {
            if (exportJob.getSettings().projection() == ExportProjection.ORTHOGRAPHIC) {
                float orthoHeight = (float)(Math.tan(Math.toRadians(fov / 2.0f)) * (double)zFar / 4.0) / exportJob.getSettings().orthographicZoom();
                float orthoWidth = width / height * orthoHeight;
                this.setupOrtho(-zFar, zFar, orthoWidth, orthoHeight, false);
                ((ProjectionExt)this.projection).flashback$setCenteredOrtho(true);
                return;
            }
            if (exportJob.getSettings().projection() == ExportProjection.CUBE_MAP || exportJob.getSettings().projection() == ExportProjection.EQUIRECTANGULAR) {
                width = Math.max(width, height);
                height = Math.max(width, height);
            }
        }
        original.call(new Object[]{instance, Float.valueOf(zNear), Float.valueOf(zFar), Float.valueOf(fov), Float.valueOf(width), Float.valueOf(height)});
    }

    @Inject(method={"createProjectionMatrixForCulling"}, at={@At(value="HEAD")}, cancellable=true)
    public void createProjectionMatrixForCulling(CallbackInfoReturnable<Matrix4f> cir) {
        ExportJob exportJob = Flashback.EXPORT_JOB;
        if (exportJob != null && exportJob.getSettings().projection() == ExportProjection.ORTHOGRAPHIC) {
            Matrix4f projection = new Matrix4f();
            float fovForCulling = Math.max(this.fov, (float)((Integer)this.minecraft.options.fov().get()).intValue());
            float width = this.minecraft.getWindow().getWidth();
            float height = this.minecraft.getWindow().getHeight();
            float orthoHeight = (float)(Math.tan(Math.toRadians(fovForCulling / 2.0f)) * (double)this.depthFar / 4.0) / exportJob.getSettings().orthographicZoom();
            float orthoWidth = width / height * orthoHeight;
            projection.setOrtho(-orthoWidth / 2.0f, orthoWidth / 2.0f, -orthoHeight / 2.0f, orthoHeight / 2.0f, -this.depthFar, this.depthFar, RenderSystem.getDevice().isZZeroToOne());
            cir.setReturnValue((Object)projection);
        }
    }
}
