/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.CvContour
 *  org.bytedeco.opencv.opencv_core.CvMemStorage
 *  org.bytedeco.opencv.opencv_core.CvPoint
 *  org.bytedeco.opencv.opencv_core.CvRect
 *  org.bytedeco.opencv.opencv_core.CvScalar
 *  org.bytedeco.opencv.opencv_core.CvSeq
 *  org.bytedeco.opencv.opencv_core.CvSlice
 *  org.bytedeco.opencv.opencv_core.IplImage
 *  org.bytedeco.opencv.opencv_imgproc.CvMoments
 */
package org.bytedeco.javacv;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacv.BaseChildSettings;
import org.bytedeco.javacv.JavaCV;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.CvContour;
import org.bytedeco.opencv.opencv_core.CvMemStorage;
import org.bytedeco.opencv.opencv_core.CvPoint;
import org.bytedeco.opencv.opencv_core.CvRect;
import org.bytedeco.opencv.opencv_core.CvScalar;
import org.bytedeco.opencv.opencv_core.CvSeq;
import org.bytedeco.opencv.opencv_core.CvSlice;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_imgproc.CvMoments;

public class HandMouse {
    private Settings settings;
    private IplImage relativeResidual = null;
    private IplImage binaryImage = null;
    private CvRect roi = null;
    private CvMemStorage storage = CvMemStorage.create();
    private int contourPointsSize = 0;
    private IntPointer intPointer = new IntPointer(1L);
    private CvPoint contourPoints = null;
    private IntBuffer contourPointsBuffer = null;
    private CvMoments moments = new CvMoments();
    private double edgeX = 0.0;
    private double edgeY = 0.0;
    private double centerX = 0.0;
    private double centerY = 0.0;
    private double imageTipX = -1.0;
    private double tipX = -1.0;
    private double prevTipX = -1.0;
    private double imageTipY = -1.0;
    private double tipY = -1.0;
    private double prevTipY = -1.0;
    private long tipTime = 0L;
    private long prevTipTime = 0L;
    private CvPoint pt1 = new CvPoint();
    private CvPoint pt2 = new CvPoint();
    private boolean imageUpdateNeeded = false;

    public HandMouse() {
        this(new Settings());
    }

    public HandMouse(Settings settings) {
        this.setSettings(settings);
    }

    public Settings getSettings() {
        return this.settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void reset() {
        this.prevTipY = -1.0;
        this.prevTipX = -1.0;
        this.tipY = -1.0;
        this.tipX = -1.0;
    }

    public void update(IplImage[] images, int pyramidLevel, CvRect roi, double[] roiPts) {
        CvContour contour;
        this.roi = roi;
        IplImage target = images[1];
        IplImage transformed = images[2];
        IplImage residual = images[3];
        IplImage mask = images[4];
        int width = roi.width();
        int height = roi.height();
        int channels = residual.nChannels();
        this.relativeResidual = IplImage.createIfNotCompatible((IplImage)this.relativeResidual, (IplImage)mask);
        this.binaryImage = IplImage.createIfNotCompatible((IplImage)this.binaryImage, (IplImage)mask);
        opencv_core.cvResetImageROI((IplImage)this.relativeResidual);
        opencv_core.cvResetImageROI((IplImage)this.binaryImage);
        double brightnessMin = (double)(channels > 3 ? 3 : channels) * this.settings.brightnessMin;
        double contourEdgeAreaMax = (double)((width + height) / 2 * width * height) * this.settings.edgeAreaMax;
        double contourEdgeAreaMin = (double)((width + height) / 2 * width * height) * this.settings.edgeAreaMin;
        ByteBuffer maskBuf = mask.getByteBuffer();
        FloatBuffer residualBuf = residual.getFloatBuffer();
        FloatBuffer targetBuf = target.getFloatBuffer();
        FloatBuffer transformedBuf = transformed.getFloatBuffer();
        ByteBuffer relResBuf = this.relativeResidual.getByteBuffer();
        while (maskBuf.hasRemaining() && residualBuf.hasRemaining() && targetBuf.hasRemaining() && transformedBuf.hasRemaining() && relResBuf.hasRemaining()) {
            byte m = maskBuf.get();
            if (m == 0) {
                residualBuf.position(residualBuf.position() + channels);
                targetBuf.position(targetBuf.position() + channels);
                transformedBuf.position(transformedBuf.position() + channels);
                relResBuf.put((byte)0);
                continue;
            }
            double relativeNorm = 0.0;
            double brightness = 0.0;
            for (int z = 0; z < channels; ++z) {
                float r = Math.abs(residualBuf.get());
                float c = targetBuf.get();
                float t = transformedBuf.get();
                if (z >= 3) continue;
                float maxct = Math.max(c, t);
                brightness += (double)maxct;
                relativeNorm = Math.max((double)(r / maxct), relativeNorm);
            }
            if (brightness < brightnessMin) {
                relResBuf.put((byte)0);
                continue;
            }
            relResBuf.put((byte)Math.round(255.0 / this.settings.thresholdHigh * Math.min(relativeNorm, this.settings.thresholdHigh)));
        }
        JavaCV.hysteresisThreshold(this.relativeResidual, this.binaryImage, 255.0, 255.0 * this.settings.thresholdLow / this.settings.thresholdHigh, 255.0);
        int roiX = roi.x();
        int roiY = roi.y();
        opencv_core.cvSetImageROI((IplImage)this.binaryImage, (CvRect)roi);
        if (this.settings.mopIterations > 0) {
            opencv_imgproc.cvMorphologyEx((CvArr)this.binaryImage, (CvArr)this.binaryImage, null, null, (int)2, (int)this.settings.mopIterations);
            opencv_imgproc.cvMorphologyEx((CvArr)this.binaryImage, (CvArr)this.binaryImage, null, null, (int)3, (int)this.settings.mopIterations);
        }
        opencv_imgproc.cvFindContours((CvArr)this.binaryImage, (CvMemStorage)this.storage, (CvSeq)contour, (int)Loader.sizeof(CvContour.class), (int)0, (int)1);
        double largestContourEdgeArea = 0.0;
        CvContour largestContour = null;
        for (contour = new CvContour(null); contour != null && !contour.isNull(); contour = contour.h_next()) {
            this.contourPointsSize = contour.total();
            if (this.contourPoints == null || this.contourPoints.capacity() < (long)this.contourPointsSize) {
                this.contourPoints = new CvPoint((long)this.contourPointsSize);
                this.contourPointsBuffer = this.contourPoints.asByteBuffer().asIntBuffer();
            }
            opencv_core.cvCvtSeqToArray((CvSeq)contour, (Pointer)this.contourPoints.position(0L));
            double[] edgePts = new double[roiPts.length];
            for (int i = 0; i < roiPts.length / 2; ++i) {
                edgePts[2 * i] = roiPts[2 * i] / (double)(1 << pyramidLevel) - (double)roiX;
                edgePts[2 * i + 1] = roiPts[2 * i + 1] / (double)(1 << pyramidLevel) - (double)roiY;
            }
            double m00 = 0.0;
            double m10 = 0.0;
            double m01 = 0.0;
            block4: for (int i = 0; i < this.contourPointsSize; ++i) {
                int x = this.contourPointsBuffer.get(2 * i);
                int y = this.contourPointsBuffer.get(2 * i + 1);
                for (int j = 0; j < roiPts.length / 2; ++j) {
                    double x1 = edgePts[2 * j];
                    double y1 = edgePts[2 * j + 1];
                    double x2 = edgePts[(2 * j + 2) % edgePts.length];
                    double y2 = edgePts[(2 * j + 3) % edgePts.length];
                    double dx = x2 - x1;
                    double dy = y2 - y1;
                    double d2 = dx * dx + dy * dy;
                    double u = (((double)x - x1) * dx + ((double)y - y1) * dy) / d2;
                    double px = x1 + u * dx;
                    double py = y1 + u * dy;
                    if (!((d2 = (dx = px - (double)x) * dx + (dy = py - (double)y) * dy) < 2.0)) continue;
                    m00 += 1.0;
                    m10 += (double)x;
                    m01 += (double)y;
                    continue block4;
                }
            }
            double contourEdgeArea = m00 * Math.abs(opencv_imgproc.cvContourArea((CvArr)contour, (CvSlice)opencv_core.CV_WHOLE_SEQ, (int)0));
            if (!(contourEdgeArea > contourEdgeAreaMin) || !(contourEdgeArea < contourEdgeAreaMax) || !(contourEdgeArea > largestContourEdgeArea)) continue;
            largestContourEdgeArea = contourEdgeArea;
            largestContour = contour;
            double inv_m00 = 1.0 / m00;
            this.edgeX = m10 * inv_m00;
            this.edgeY = m01 * inv_m00;
        }
        if (this.isClick()) {
            this.prevTipX = -1.0;
            this.prevTipY = -1.0;
            this.prevTipTime = 0L;
        } else if (!this.isSteady()) {
            this.prevTipX = this.tipX;
            this.prevTipY = this.tipY;
            this.prevTipTime = System.currentTimeMillis();
        }
        if (largestContour == null) {
            this.tipX = -1.0;
            this.tipY = -1.0;
            this.tipTime = 0L;
            this.imageUpdateNeeded = false;
        } else {
            opencv_imgproc.cvMoments(largestContour, (CvMoments)this.moments, (int)0);
            double inv_m00 = 1.0 / this.moments.m00();
            this.centerX = this.moments.m10() * inv_m00;
            this.centerY = this.moments.m01() * inv_m00;
            this.contourPointsSize = largestContour.total();
            opencv_core.cvCvtSeqToArray(largestContour, (Pointer)this.contourPoints.position(0L));
            double tipDist2 = 0.0;
            int tipIndex = 0;
            for (int i = 0; i < this.contourPointsSize; ++i) {
                int x = this.contourPointsBuffer.get(2 * i);
                int y = this.contourPointsBuffer.get(2 * i + 1);
                double dx = this.centerX - this.edgeX;
                double dy = this.centerY - this.edgeY;
                double d2 = dx * dx + dy * dy;
                double u = (((double)x - this.edgeX) * dx + ((double)y - this.edgeY) * dy) / d2;
                double px = this.edgeX + u * dx;
                double py = this.edgeY + u * dy;
                if (!((d2 = (dx = px - this.edgeX) * dx + (dy = py - this.edgeY) * dy) > tipDist2)) continue;
                tipIndex = i;
                tipDist2 = d2;
            }
            double a = this.imageTipX < 0.0 || this.imageTipY < 0.0 ? 1.0 : this.settings.updateAlpha;
            this.imageTipX = a * (double)this.contourPointsBuffer.get(2 * tipIndex) + (1.0 - a) * this.imageTipX;
            this.imageTipY = a * (double)this.contourPointsBuffer.get(2 * tipIndex + 1) + (1.0 - a) * this.imageTipY;
            this.tipX = (this.imageTipX + (double)roiX) * (double)(1 << pyramidLevel);
            this.tipY = (this.imageTipY + (double)roiY) * (double)(1 << pyramidLevel);
            this.tipTime = System.currentTimeMillis();
            this.imageUpdateNeeded = true;
        }
        opencv_core.cvClearMemStorage((CvMemStorage)this.storage);
    }

    public IplImage getRelativeResidual() {
        return this.relativeResidual;
    }

    public IplImage getResultImage() {
        if (this.imageUpdateNeeded) {
            opencv_core.cvSetZero((CvArr)this.binaryImage);
            opencv_imgproc.cvFillPoly((CvArr)this.binaryImage, (CvPoint)this.contourPoints, (IntPointer)this.intPointer.put(this.contourPointsSize), (int)1, (CvScalar)CvScalar.WHITE, (int)8, (int)0);
            this.pt1.put((byte)16, new double[]{this.edgeX, this.edgeY});
            opencv_imgproc.cvCircle((CvArr)this.binaryImage, (CvPoint)this.pt1, (int)327680, (CvScalar)CvScalar.GRAY, (int)2, (int)8, (int)16);
            this.pt1.put((byte)16, new double[]{this.centerX - 5.0, this.centerY - 5.0});
            this.pt2.put((byte)16, new double[]{this.centerX + 5.0, this.centerY + 5.0});
            opencv_imgproc.cvRectangle((CvArr)this.binaryImage, (CvPoint)this.pt1, (CvPoint)this.pt2, (CvScalar)CvScalar.GRAY, (int)2, (int)8, (int)16);
            this.pt1.put((byte)16, new double[]{this.imageTipX - 5.0, this.imageTipY - 5.0});
            this.pt2.put((byte)16, new double[]{this.imageTipX + 5.0, this.imageTipY + 5.0});
            opencv_imgproc.cvLine((CvArr)this.binaryImage, (CvPoint)this.pt1, (CvPoint)this.pt2, (CvScalar)CvScalar.GRAY, (int)2, (int)8, (int)16);
            this.pt1.put((byte)16, new double[]{this.imageTipX - 5.0, this.imageTipY + 5.0});
            this.pt2.put((byte)16, new double[]{this.imageTipX + 5.0, this.imageTipY - 5.0});
            opencv_imgproc.cvLine((CvArr)this.binaryImage, (CvPoint)this.pt1, (CvPoint)this.pt2, (CvScalar)CvScalar.GRAY, (int)2, (int)8, (int)16);
            opencv_core.cvResetImageROI((IplImage)this.binaryImage);
            this.imageUpdateNeeded = false;
        }
        return this.binaryImage;
    }

    public double getX() {
        return this.tipX;
    }

    public double getY() {
        return this.tipY;
    }

    public boolean isSteady() {
        if (this.tipX >= 0.0 && this.tipY >= 0.0 && this.prevTipX >= 0.0 && this.prevTipY >= 0.0) {
            double dx = this.tipX - this.prevTipX;
            double dy = this.tipY - this.prevTipY;
            int imageSize = (this.roi.width() + this.roi.height()) / 2;
            double steadySize = this.settings.clickSteadySize * (double)imageSize;
            return dx * dx + dy * dy < steadySize * steadySize;
        }
        return false;
    }

    public boolean isClick() {
        return this.isSteady() && this.tipTime - this.prevTipTime > this.settings.clickSteadyTime;
    }

    public static class Settings
    extends BaseChildSettings {
        int mopIterations = 1;
        double clickSteadySize = 0.05;
        long clickSteadyTime = 250L;
        double edgeAreaMin = 0.001;
        double edgeAreaMax = 0.1;
        double thresholdHigh = 0.5;
        double thresholdLow = 0.25;
        double brightnessMin = 0.1;
        double updateAlpha = 0.5;

        public Settings() {
        }

        public Settings(Settings s) {
            s.mopIterations = this.mopIterations;
            s.clickSteadySize = this.clickSteadySize;
            s.clickSteadyTime = this.clickSteadyTime;
            s.edgeAreaMin = this.edgeAreaMin;
            s.edgeAreaMax = this.edgeAreaMax;
            s.thresholdHigh = this.thresholdHigh;
            s.thresholdLow = this.thresholdLow;
            s.brightnessMin = this.brightnessMin;
            s.updateAlpha = this.updateAlpha;
        }

        public int getMopIterations() {
            return this.mopIterations;
        }

        public void setMopIterations(int mopIterations) {
            this.mopIterations = mopIterations;
        }

        public double getClickSteadySize() {
            return this.clickSteadySize;
        }

        public void setClickSteadySize(double clickSteadySize) {
            this.clickSteadySize = clickSteadySize;
        }

        public long getClickSteadyTime() {
            return this.clickSteadyTime;
        }

        public void setClickSteadyTime(long clickSteadyTime) {
            this.clickSteadyTime = clickSteadyTime;
        }

        public double getEdgeAreaMin() {
            return this.edgeAreaMin;
        }

        public void setEdgeAreaMin(double edgeAreaMin) {
            this.edgeAreaMin = edgeAreaMin;
        }

        public double getEdgeAreaMax() {
            return this.edgeAreaMax;
        }

        public void setEdgeAreaMax(double edgeAreaMax) {
            this.edgeAreaMax = edgeAreaMax;
        }

        public double getThresholdHigh() {
            return this.thresholdHigh;
        }

        public void setThresholdHigh(double thresholdHigh) {
            this.thresholdHigh = thresholdHigh;
        }

        public double getThresholdLow() {
            return this.thresholdLow;
        }

        public void setThresholdLow(double thresholdLow) {
            this.thresholdLow = thresholdLow;
        }

        public double getBrightnessMin() {
            return this.brightnessMin;
        }

        public void setBrightnessMin(double brightnessMin) {
            this.brightnessMin = brightnessMin;
        }

        public double getUpdateAlpha() {
            return this.updateAlpha;
        }

        public void setUpdateAlpha(double updateAlpha) {
            this.updateAlpha = updateAlpha;
        }
    }
}

