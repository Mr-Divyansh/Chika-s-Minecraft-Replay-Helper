/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 */
package com.moulberry.flashback.action;

import net.minecraft.util.Mth;

public record PositionAndAngle(double x, double y, double z, float yaw, float pitch) {
    public PositionAndAngle lerp(PositionAndAngle other, double amount) {
        return new PositionAndAngle(this.x + (other.x - this.x) * amount, this.y + (other.y - this.y) * amount, this.z + (other.z - this.z) * amount, (float)Mth.wrapDegrees((double)((double)this.yaw + (double)Mth.wrapDegrees((float)(other.yaw - this.yaw)) * amount)), (float)Mth.wrapDegrees((double)((double)this.pitch + (double)Mth.wrapDegrees((float)(other.pitch - this.pitch)) * amount)));
    }
}

