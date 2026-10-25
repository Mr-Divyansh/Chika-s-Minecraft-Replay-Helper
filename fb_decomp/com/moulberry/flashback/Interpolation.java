/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  org.joml.Math
 */
package com.moulberry.flashback;

import net.minecraft.util.Mth;
import org.joml.Math;

public class Interpolation {
    public static double linear(double from, double to, double amount) {
        return from + (to - from) * amount;
    }

    public static float linear(float from, float to, float amount) {
        return from + (to - from) * amount;
    }

    public static float linearAngle(float from, float to, float amount) {
        float delta = Mth.wrapDegrees((float)(to - from));
        return from + Math.max((float)-180.0f, (float)Math.min((float)180.0f, (float)(delta * amount)));
    }

    public static double linearAngle(double from, double to, double amount) {
        double delta = Mth.wrapDegrees((double)(to - from));
        return from + Math.max((double)-180.0, (double)Math.min((double)180.0, (double)(delta * amount)));
    }
}
