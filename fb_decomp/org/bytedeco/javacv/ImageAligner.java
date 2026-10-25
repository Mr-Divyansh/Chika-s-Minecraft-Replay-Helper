/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.opencv_core.CvRect
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import org.bytedeco.javacv.BaseChildSettings;
import org.bytedeco.javacv.ImageTransformer;
import org.bytedeco.opencv.opencv_core.CvRect;
import org.bytedeco.opencv.opencv_core.IplImage;

public interface ImageAligner {
    public Settings getSettings();

    public void setSettings(Settings var1);

    public IplImage getTemplateImage();

    public void setTemplateImage(IplImage var1, double[] var2);

    public IplImage getTargetImage();

    public void setTargetImage(IplImage var1);

    public int getPyramidLevel();

    public void setPyramidLevel(int var1);

    public ImageTransformer.Parameters getParameters();

    public void setParameters(ImageTransformer.Parameters var1);

    public double[] getTransformedRoiPts();

    public IplImage getTransformedImage();

    public IplImage getResidualImage();

    public IplImage getMaskImage();

    public double getRMSE();

    public CvRect getRoi();

    public IplImage[] getImages();

    public boolean iterate(double[] var1);

    public static class Settings
    extends BaseChildSettings
    implements Cloneable {
        int pyramidLevelMin = 0;
        int pyramidLevelMax = 4;
        double[] thresholdsZero = new double[]{0.04, 0.03, 0.02, 0.01, 0.0};
        double[] thresholdsOutlier = new double[]{0.2};
        boolean thresholdsMulRMSE = false;

        public Settings() {
        }

        public Settings(Settings s) {
            this.pyramidLevelMin = s.pyramidLevelMin;
            this.pyramidLevelMax = s.pyramidLevelMax;
            this.thresholdsZero = s.thresholdsZero;
            this.thresholdsOutlier = s.thresholdsOutlier;
            this.thresholdsMulRMSE = s.thresholdsMulRMSE;
        }

        public int getPyramidLevelMin() {
            return this.pyramidLevelMin;
        }

        public void setPyramidLevelMin(int pyramidLevelMin) {
            this.pyramidLevelMin = pyramidLevelMin;
        }

        public int getPyramidLevelMax() {
            return this.pyramidLevelMax;
        }

        public void setPyramidLevelMax(int pyramidLevelMax) {
            this.pyramidLevelMax = pyramidLevelMax;
        }

        public double[] getThresholdsZero() {
            return this.thresholdsZero;
        }

        public void setThresholdsZero(double[] thresholdsZero) {
            this.thresholdsZero = thresholdsZero;
        }

        public double[] getThresholdsOutlier() {
            return this.thresholdsOutlier;
        }

        public void setThresholdsOutlier(double[] thresholdsOutlier) {
            this.thresholdsOutlier = thresholdsOutlier;
        }

        public boolean isThresholdsMulRMSE() {
            return this.thresholdsMulRMSE;
        }

        public void setThresholdsMulRMSE(boolean thresholdsMulRMSE) {
            this.thresholdsMulRMSE = thresholdsMulRMSE;
        }

        public Settings clone() {
            return new Settings(this);
        }
    }
}

