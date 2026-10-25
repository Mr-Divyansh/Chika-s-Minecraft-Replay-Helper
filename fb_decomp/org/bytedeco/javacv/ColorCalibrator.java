/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.CvMat
 *  org.bytedeco.opencv.opencv_core.CvScalar
 */
package org.bytedeco.javacv;

import java.awt.Color;
import org.bytedeco.javacv.ProjectiveDevice;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.CvMat;
import org.bytedeco.opencv.opencv_core.CvScalar;

public class ColorCalibrator {
    private ProjectiveDevice device;

    public ColorCalibrator(ProjectiveDevice device) {
        this.device = device;
    }

    public double calibrate(Color[] referenceColors, Color[] deviceColors) {
        assert (referenceColors.length == deviceColors.length);
        int[] order = this.device.getRGBColorOrder();
        CvMat A = CvMat.create((int)(referenceColors.length * 3), (int)12);
        CvMat b = CvMat.create((int)(referenceColors.length * 3), (int)1);
        CvMat x = CvMat.create((int)12, (int)1);
        double gamma = this.device.getSettings().getResponseGamma();
        for (int i = 0; i < referenceColors.length; ++i) {
            float[] dc = deviceColors[i].getRGBColorComponents(null);
            float[] rc = referenceColors[i].getRGBColorComponents(null);
            double dc1 = Math.pow(dc[order[0]], gamma);
            double dc2 = Math.pow(dc[order[1]], gamma);
            double dc3 = Math.pow(dc[order[2]], gamma);
            for (int j = 0; j < 3; ++j) {
                int k = i * 36 + j * 16;
                A.put(k, dc1);
                A.put(k + 1, dc2);
                A.put(k + 2, dc3);
                A.put(k + 3, 1.0);
                if (j >= 2) continue;
                for (int m = 0; m < 12; ++m) {
                    A.put(k + 4 + m, 0.0);
                }
            }
            b.put(i * 3, (double)rc[order[0]]);
            b.put(i * 3 + 1, (double)rc[order[1]]);
            b.put(i * 3 + 2, (double)rc[order[2]]);
        }
        if ((double)opencv_core.cvSolve((CvArr)A, (CvArr)b, (CvArr)x, (int)1) != 1.0) {
            System.out.println("Error solving.");
        }
        CvMat b2 = CvMat.create((int)b.rows(), (int)1);
        opencv_core.cvMatMul((CvArr)A, (CvArr)x, (CvArr)b2);
        double MSE = opencv_core.cvNorm((CvArr)b, (CvArr)b2) * opencv_core.cvNorm((CvArr)b, (CvArr)b2) / (double)b.rows();
        double RMSE = Math.sqrt(MSE);
        CvScalar mean = new CvScalar();
        CvScalar stddev = new CvScalar();
        opencv_core.cvAvgSdv((CvArr)b, (CvScalar)mean, (CvScalar)stddev, null);
        double R2 = 1.0 - MSE / (stddev.val(0) * stddev.val(0));
        this.device.colorMixingMatrix = CvMat.create((int)3, (int)3);
        this.device.additiveLight = CvMat.create((int)3, (int)1);
        for (int i = 0; i < 3; ++i) {
            double x0 = x.get(i * 4);
            double x1 = x.get(i * 4 + 1);
            double x2 = x.get(i * 4 + 2);
            double x3 = x.get(i * 4 + 3);
            this.device.colorMixingMatrix.put(i * 3, x0);
            this.device.colorMixingMatrix.put(i * 3 + 1, x1);
            this.device.colorMixingMatrix.put(i * 3 + 2, x2);
            this.device.additiveLight.put(i, x3);
        }
        this.device.colorR2 = R2;
        this.device.avgColorErr = RMSE;
        return this.device.avgColorErr;
    }
}

