/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  org.joml.Vector3d
 */
package com.moulberry.flashback.keyframe.handler;

import com.moulberry.flashback.keyframe.change.KeyframeChange;
import net.minecraft.client.Minecraft;
import org.joml.Vector3d;

public interface KeyframeHandler {
    public boolean supportsKeyframeChange(Class<? extends KeyframeChange> var1);

    default public Minecraft getMinecraft() {
        return null;
    }

    default public boolean alwaysApplyLastKeyframe() {
        return false;
    }

    default public void applyCameraPosition(Vector3d position, double yaw, double pitch, double roll) {
    }

    default public void applyFov(float fov) {
    }

    default public void applyTickrate(float tickrate) {
    }

    default public void applyFreeze(boolean frozen, int frozenDelay) {
    }

    default public void applyTimeOfDay(int timeOfDay) {
    }

    default public void applyCameraShake(float frequencyX, float amplitudeX, float frequencyY, float amplitudeY) {
    }
}

