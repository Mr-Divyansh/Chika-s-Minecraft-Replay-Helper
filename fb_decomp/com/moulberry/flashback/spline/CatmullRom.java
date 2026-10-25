/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  org.joml.Vector3d
 *  org.joml.Vector3dc
 */
package com.moulberry.flashback.spline;

import com.moulberry.flashback.Interpolation;
import net.minecraft.util.Mth;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class CatmullRom {
    private static double positionCentripetalTj(Vector3d p1, Vector3d p2) {
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        double dz = p2.z - p1.z;
        return Math.pow(dx * dx + dy * dy + dz * dz, 0.25);
    }

    public static Vector3d position(Vector3d p0, Vector3d p1, Vector3d p2, Vector3d p3, float time1, float time2, float time3, float amount) {
        double tj3;
        double tj2;
        double tj1 = CatmullRom.positionCentripetalTj(p0, p1);
        double averageTj = (tj1 + (tj2 = CatmullRom.positionCentripetalTj(p1, p2)) + (tj3 = CatmullRom.positionCentripetalTj(p2, p3))) / 3.0;
        if (averageTj == 0.0) {
            return new Vector3d((Vector3dc)p1);
        }
        double averageTime = time3 / 3.0f;
        double relation = averageTime / averageTj;
        double deltaTime1 = 0.0;
        if (time1 > 0.0f) {
            double factor1 = tj1 * relation / (double)time1;
            factor1 = Math.max((double)0.4f, Math.min(2.5, factor1));
            deltaTime1 = tj1 * relation / factor1;
        }
        double deltaTime2 = 0.0;
        if (time2 - time1 > 0.0f) {
            double factor2 = tj2 * relation / (double)(time2 - time1);
            factor2 = Math.max((double)0.4f, Math.min(2.5, factor2));
            deltaTime2 = tj2 * relation / factor2;
        }
        double deltaTime3 = 0.0;
        if (time3 - time2 > 0.0f) {
            double factor3 = tj3 * relation / (double)(time3 - time2);
            factor3 = Math.max((double)0.4f, Math.min(2.5, factor3));
            deltaTime3 = tj3 * relation / factor3;
        }
        double t0 = 0.0;
        double t1 = t0 + deltaTime1;
        double t2 = t1 + deltaTime2;
        double t3 = t2 + deltaTime3;
        double t = t1 + (t2 - t1) * (double)amount;
        Vector3d a1 = t0 == t1 ? p0.lerp((Vector3dc)p1, 0.5, new Vector3d()) : p0.lerp((Vector3dc)p1, (t - t0) / (t1 - t0), new Vector3d());
        Vector3d a2 = t1 == t2 ? p1.lerp((Vector3dc)p2, 0.5, new Vector3d()) : p1.lerp((Vector3dc)p2, (t - t1) / (t2 - t1), new Vector3d());
        Vector3d a3 = t2 == t3 ? p2.lerp((Vector3dc)p3, 0.5, new Vector3d()) : p2.lerp((Vector3dc)p3, (t - t2) / (t3 - t2), new Vector3d());
        Vector3d b1 = t0 == t2 ? a1.lerp((Vector3dc)a2, 0.5, new Vector3d()) : a1.lerp((Vector3dc)a2, (t - t0) / (t2 - t0), new Vector3d());
        Vector3d b2 = t1 == t3 ? a2.lerp((Vector3dc)a3, 0.5, new Vector3d()) : a2.lerp((Vector3dc)a3, (t - t1) / (t3 - t1), new Vector3d());
        if (t1 == t2) {
            return b1.lerp((Vector3dc)b2, 0.5, new Vector3d());
        }
        return b1.lerp((Vector3dc)b2, (t - t1) / (t2 - t1), new Vector3d());
    }

    private static float valueCentripetalTj(float p1, float p2) {
        return (float)Math.sqrt(Math.abs(p1 - p2));
    }

    public static float value(float p0, float p1, float p2, float p3, float time1, float time2, float time3, float amount) {
        float tj3;
        float tj2;
        float tj1 = CatmullRom.valueCentripetalTj(p0, p1);
        float averageTj = (tj1 + (tj2 = CatmullRom.valueCentripetalTj(p1, p2)) + (tj3 = CatmullRom.valueCentripetalTj(p2, p3))) / 3.0f;
        if ((double)averageTj == 0.0) {
            return p1;
        }
        float averageTime = time3 / 3.0f;
        float relation = averageTime / averageTj;
        float deltaTime1 = 0.0f;
        if (time1 > 0.0f) {
            float factor1 = tj1 * relation / time1;
            factor1 = Math.max(0.4f, Math.min(2.5f, factor1));
            deltaTime1 = tj1 * relation / factor1;
        }
        float deltaTime2 = 0.0f;
        if (time2 - time1 > 0.0f) {
            float factor2 = tj2 * relation / (time2 - time1);
            factor2 = Math.max(0.4f, Math.min(2.5f, factor2));
            deltaTime2 = tj2 * relation / factor2;
        }
        float deltaTime3 = 0.0f;
        if (time3 - time2 > 0.0f) {
            float factor3 = tj3 * relation / (time3 - time2);
            factor3 = Math.max(0.4f, Math.min(2.5f, factor3));
            deltaTime3 = tj3 * relation / factor3;
        }
        float t0 = 0.0f;
        float t1 = t0 + deltaTime1;
        float t2 = t1 + deltaTime2;
        float t3 = t2 + deltaTime3;
        float t = t1 + (t2 - t1) * amount;
        float a1 = t0 == t1 ? Interpolation.linear(p0, p1, 0.5f) : Interpolation.linear(p0, p1, (t - t0) / (t1 - t0));
        float a2 = t1 == t2 ? Interpolation.linear(p1, p2, 0.5f) : Interpolation.linear(p1, p2, (t - t1) / (t2 - t1));
        float a3 = t2 == t3 ? Interpolation.linear(p2, p3, 0.5f) : Interpolation.linear(p2, p3, (t - t2) / (t3 - t2));
        float b1 = t0 == t2 ? Interpolation.linear(a1, a2, 0.5f) : Interpolation.linear(a1, a2, (t - t0) / (t2 - t0));
        float b2 = t1 == t3 ? Interpolation.linear(a2, a3, 0.5f) : Interpolation.linear(a2, a3, (t - t1) / (t3 - t1));
        if (t1 == t2) {
            return Interpolation.linear(b1, b2, 0.5f);
        }
        return Interpolation.linear(b1, b2, (t - t1) / (t2 - t1));
    }

    private static float degreesCentripetalTj(float p1, float p2) {
        return (float)Math.sqrt(Math.abs(Mth.wrapDegrees((float)(p1 - p2))));
    }

    public static float degrees(float p0, float p1, float p2, float p3, float time1, float time2, float time3, float amount) {
        float tj3;
        float tj2;
        float delta1 = Mth.wrapDegrees((float)(p1 - p0));
        float delta2 = Mth.wrapDegrees((float)(p2 - p1));
        float delta3 = Mth.wrapDegrees((float)(p3 - p2));
        p0 = Mth.wrapDegrees((float)p0);
        p1 = p0 + delta1;
        p2 = p1 + delta2;
        p3 = p2 + delta3;
        float tj1 = CatmullRom.degreesCentripetalTj(p0, p1);
        float averageTj = (tj1 + (tj2 = CatmullRom.degreesCentripetalTj(p1, p2)) + (tj3 = CatmullRom.degreesCentripetalTj(p2, p3))) / 3.0f;
        if ((double)averageTj == 0.0) {
            return p1;
        }
        float averageTime = time3 / 3.0f;
        float relation = averageTime / averageTj;
        float minFactor = 0.4f;
        float maxFactor = 2.5f;
        float deltaTime1 = 0.0f;
        if (time1 > 0.0f) {
            float factor1 = tj1 * relation / time1;
            factor1 = Math.max(0.4f, Math.min(2.5f, factor1));
            deltaTime1 = tj1 * relation / factor1;
        }
        float deltaTime2 = 0.0f;
        if (time2 - time1 > 0.0f) {
            float factor2 = tj2 * relation / (time2 - time1);
            factor2 = Math.max(0.4f, Math.min(2.5f, factor2));
            deltaTime2 = tj2 * relation / factor2;
        }
        float deltaTime3 = 0.0f;
        if (time3 - time2 > 0.0f) {
            float factor3 = tj3 * relation / (time3 - time2);
            factor3 = Math.max(0.4f, Math.min(2.5f, factor3));
            deltaTime3 = tj3 * relation / factor3;
        }
        float t0 = 0.0f;
        float t1 = t0 + deltaTime1;
        float t2 = t1 + deltaTime2;
        float t3 = t2 + deltaTime3;
        float t = t1 + (t2 - t1) * amount;
        float a1 = t0 == t1 ? Interpolation.linear(p0, p1, 0.5f) : Interpolation.linear(p0, p1, (t - t0) / (t1 - t0));
        float a2 = t1 == t2 ? Interpolation.linear(p1, p2, 0.5f) : Interpolation.linear(p1, p2, (t - t1) / (t2 - t1));
        float a3 = t2 == t3 ? Interpolation.linear(p2, p3, 0.5f) : Interpolation.linear(p2, p3, (t - t2) / (t3 - t2));
        float b1 = t0 == t2 ? Interpolation.linear(a1, a2, 0.5f) : Interpolation.linear(a1, a2, (t - t0) / (t2 - t0));
        float b2 = t1 == t3 ? Interpolation.linear(a2, a3, 0.5f) : Interpolation.linear(a2, a3, (t - t1) / (t3 - t1));
        return Interpolation.linear(b1, b2, amount);
    }
}

