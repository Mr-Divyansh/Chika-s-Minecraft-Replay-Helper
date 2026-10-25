/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  org.apache.commons.math3.analysis.interpolation.HermiteInterpolator
 *  org.joml.Vector3d
 */
package com.moulberry.flashback.spline;

import java.util.Map;
import net.minecraft.util.Mth;
import org.apache.commons.math3.analysis.interpolation.HermiteInterpolator;
import org.joml.Vector3d;

public class Hermite {
    public static Vector3d position(Map<Float, Vector3d> map, float amount) {
        HermiteInterpolator hermiteInterpolator = new HermiteInterpolator();
        double[] array = new double[3];
        for (Map.Entry<Float, Vector3d> entry : map.entrySet()) {
            array[0] = entry.getValue().x;
            array[1] = entry.getValue().y;
            array[2] = entry.getValue().z;
            hermiteInterpolator.addSamplePoint((double)entry.getKey().floatValue(), (double[][])new double[][]{array});
        }
        double[] values = hermiteInterpolator.value((double)amount);
        return new Vector3d(values[0], values[1], values[2]);
    }

    public static double value(Map<Float, Double> map, float amount) {
        HermiteInterpolator hermiteInterpolator = new HermiteInterpolator();
        double[] array = new double[1];
        for (Map.Entry<Float, Double> entry : map.entrySet()) {
            array[0] = entry.getValue();
            hermiteInterpolator.addSamplePoint((double)entry.getKey().floatValue(), (double[][])new double[][]{array});
        }
        return hermiteInterpolator.value((double)amount)[0];
    }

    public static double degrees(Map<Float, Double> map, float amount) {
        HermiteInterpolator hermiteInterpolator = new HermiteInterpolator();
        double lastAngle = 0.0;
        double[] array = new double[1];
        for (Map.Entry<Float, Double> entry : map.entrySet()) {
            double angle;
            array[0] = angle = lastAngle + Mth.wrapDegrees((double)(entry.getValue() - lastAngle));
            hermiteInterpolator.addSamplePoint((double)entry.getKey().floatValue(), (double[][])new double[][]{array});
            lastAngle = angle;
        }
        return Mth.wrapDegrees((double)hermiteInterpolator.value((double)amount)[0]);
    }
}

