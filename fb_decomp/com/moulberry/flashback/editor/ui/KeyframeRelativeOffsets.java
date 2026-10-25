/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  org.joml.Vector3d
 *  org.joml.Vector3dc
 */
package com.moulberry.flashback.editor.ui;

import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.impl.CameraKeyframe;
import com.moulberry.flashback.keyframe.impl.CameraOrbitKeyframe;
import net.minecraft.util.Mth;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class KeyframeRelativeOffsets {
    public Vector3d oldOrigin = new Vector3d();
    public Vector3d newOrigin = new Vector3d();
    public float oldYaw = 0.0f;
    public float newYaw = 0.0f;
    public float oldPitch = 0.0f;
    public float newPitch = 0.0f;

    public void apply(Keyframe keyframe) {
        if (keyframe instanceof CameraKeyframe) {
            CameraKeyframe cameraKeyframe = (CameraKeyframe)keyframe;
            Vector3d position = cameraKeyframe.position;
            position.sub((Vector3dc)this.oldOrigin);
            position.rotateY(Math.toRadians(this.oldYaw));
            position.rotateX(Math.toRadians(this.newPitch - this.oldPitch));
            position.rotateY(Math.toRadians(-this.newYaw));
            position.add((Vector3dc)this.newOrigin);
            cameraKeyframe.yaw += Mth.wrapDegrees((float)(this.newYaw - this.oldYaw));
            cameraKeyframe.pitch += Mth.wrapDegrees((float)(this.newPitch - this.oldPitch));
        } else if (keyframe instanceof CameraOrbitKeyframe) {
            CameraOrbitKeyframe cameraOrbitKeyframe = (CameraOrbitKeyframe)keyframe;
            Vector3d center = cameraOrbitKeyframe.center;
            center.sub((Vector3dc)this.oldOrigin);
            center.rotateY(Math.toRadians(this.oldYaw));
            center.rotateX(Math.toRadians(this.newPitch - this.oldPitch));
            center.rotateY(Math.toRadians(-this.newYaw));
            center.add((Vector3dc)this.newOrigin);
            if (this.newPitch != this.oldPitch) {
                float oldPitchRadians = (float)Math.toRadians(cameraOrbitKeyframe.pitch);
                float oldYawRadians = (float)Math.toRadians(-cameraOrbitKeyframe.yaw);
                float oldCosYaw = Mth.cos((double)oldYawRadians);
                float oldSinYaw = Mth.sin((double)oldYawRadians);
                float oldCosPitch = Mth.cos((double)oldPitchRadians);
                float oldSinPitch = Mth.sin((double)oldPitchRadians);
                Vector3d oldLook = new Vector3d((double)(oldSinYaw * oldCosPitch), (double)(-oldSinPitch), (double)(oldCosYaw * oldCosPitch));
                Vector3d camera = new Vector3d((Vector3dc)cameraOrbitKeyframe.center).sub((Vector3dc)oldLook.mul((double)cameraOrbitKeyframe.distance));
                camera.sub((Vector3dc)this.oldOrigin);
                camera.rotateY(Math.toRadians(this.oldYaw));
                camera.rotateX(Math.toRadians(this.newPitch - this.oldPitch));
                camera.rotateY(Math.toRadians(-this.newYaw));
                camera.add((Vector3dc)this.newOrigin);
                double dx = center.x - camera.x;
                double dy = center.y - camera.y;
                double dz = center.z - camera.z;
                double dhorz = Math.sqrt(dx * dx + dz * dz);
                cameraOrbitKeyframe.pitch = Mth.wrapDegrees((float)((float)(-(Mth.atan2((double)dy, (double)dhorz) * 180.0 / 3.1415927410125732))));
                cameraOrbitKeyframe.yaw = Mth.wrapDegrees((float)((float)(Mth.atan2((double)dz, (double)dx) * 180.0 / 3.1415927410125732) - 90.0f));
            } else if (this.newYaw != this.oldYaw) {
                cameraOrbitKeyframe.yaw += Mth.wrapDegrees((float)(this.newYaw - this.oldYaw));
            }
        }
    }
}

