/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.util.Mth
 *  org.joml.Vector3d
 *  org.joml.Vector3dc
 */
package com.moulberry.flashback.keyframe.change;

import com.moulberry.flashback.Interpolation;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public record KeyframeChangeCameraPositionOrbit(Vector3d center, double distance, double yaw, double pitch) implements KeyframeChange
{
    @Override
    public void apply(KeyframeHandler keyframeHandler) {
        float pitchRadians = (float)Math.toRadians(this.pitch);
        float yawRadians = (float)Math.toRadians(-this.yaw);
        float cosYaw = Mth.cos((double)yawRadians);
        float sinYaw = Mth.sin((double)yawRadians);
        float cosPitch = Mth.cos((double)pitchRadians);
        float sinPitch = Mth.sin((double)pitchRadians);
        Vector3d look = new Vector3d((double)(sinYaw * cosPitch), (double)(-sinPitch), (double)(cosYaw * cosPitch));
        Vector3d cameraPosition = new Vector3d((Vector3dc)this.center).sub((Vector3dc)look.mul(this.distance));
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            cameraPosition.y -= (double)player.getEyeHeight();
        }
        keyframeHandler.applyCameraPosition(cameraPosition, this.yaw, this.pitch, 0.0);
    }

    @Override
    public KeyframeChange interpolate(KeyframeChange to, double amount) {
        KeyframeChangeCameraPositionOrbit other = (KeyframeChangeCameraPositionOrbit)to;
        return new KeyframeChangeCameraPositionOrbit(this.center.lerp((Vector3dc)other.center, amount, new Vector3d()), Interpolation.linear(this.distance, other.distance, amount), Interpolation.linear(this.yaw, other.yaw, amount), Interpolation.linear(this.pitch, other.pitch, amount));
    }
}

