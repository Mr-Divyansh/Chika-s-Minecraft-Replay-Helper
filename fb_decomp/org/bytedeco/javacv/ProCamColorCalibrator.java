/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.CvMat
 *  org.bytedeco.opencv.opencv_core.CvPoint
 *  org.bytedeco.opencv.opencv_core.CvScalar
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import java.awt.Color;
import org.bytedeco.javacv.BaseChildSettings;
import org.bytedeco.javacv.CameraDevice;
import org.bytedeco.javacv.ColorCalibrator;
import org.bytedeco.javacv.JavaCV;
import org.bytedeco.javacv.MarkedPlane;
import org.bytedeco.javacv.Marker;
import org.bytedeco.javacv.MarkerDetector;
import org.bytedeco.javacv.ProjectorDevice;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.CvMat;
import org.bytedeco.opencv.opencv_core.CvPoint;
import org.bytedeco.opencv.opencv_core.CvScalar;
import org.bytedeco.opencv.opencv_core.IplImage;

public class ProCamColorCalibrator {
    private Settings settings;
    private MarkerDetector markerDetector = null;
    private MarkedPlane boardPlane = null;
    private CameraDevice camera = null;
    private ProjectorDevice projector = null;
    private Color[] projectorColors = null;
    private Color[] cameraColors = null;
    private int counter = 0;
    private CvMat boardSrcPts;
    private CvMat boardDstPts;
    private CvMat projSrcPts;
    private CvMat projDstPts;
    private CvMat camKinv;
    private IplImage mask;
    private IplImage mask2;
    private IplImage undistImage;
    private static ThreadLocal<CvMat> H3x3 = CvMat.createThreadLocal((int)3, (int)3);
    private static ThreadLocal<CvMat> R3x3 = CvMat.createThreadLocal((int)3, (int)3);
    private static ThreadLocal<CvMat> t3x1 = CvMat.createThreadLocal((int)3, (int)1);
    private static ThreadLocal<CvMat> n3x1 = CvMat.createThreadLocal((int)3, (int)1);
    private static ThreadLocal<CvMat> z3x1 = CvMat.createThreadLocal((int)3, (int)1);

    public ProCamColorCalibrator(Settings settings, MarkerDetector.Settings detectorSettings, MarkedPlane boardPlane, CameraDevice camera, ProjectorDevice projector) {
        this.settings = settings;
        this.markerDetector = new MarkerDetector(detectorSettings);
        this.boardPlane = boardPlane;
        this.camera = camera;
        this.projector = projector;
        Marker[] boardMarkers = boardPlane.getMarkers();
        this.boardSrcPts = CvMat.create((int)(4 + boardMarkers.length * 4), (int)1, (int)6, (int)2);
        this.boardDstPts = CvMat.create((int)(4 + boardMarkers.length * 4), (int)1, (int)6, (int)2);
        this.boardSrcPts.put(new double[]{0.0, 0.0, boardPlane.getWidth(), 0.0, boardPlane.getWidth(), boardPlane.getHeight(), 0.0, boardPlane.getHeight()});
        for (int i = 0; i < boardMarkers.length; ++i) {
            this.boardSrcPts.put(8 + i * 8, boardMarkers[i].corners);
        }
        this.projSrcPts = CvMat.create((int)4, (int)1, (int)6, (int)2);
        this.projDstPts = CvMat.create((int)4, (int)1, (int)6, (int)2);
        this.projSrcPts.put(new double[]{0.0, 0.0, projector.imageWidth - 1, 0.0, projector.imageWidth - 1, projector.imageHeight - 1, 0.0, projector.imageHeight - 1});
        this.camKinv = CvMat.create((int)3, (int)3);
        opencv_core.cvInvert((CvArr)camera.cameraMatrix, (CvArr)this.camKinv);
    }

    public int getColorCount() {
        return this.counter;
    }

    public Color[] getProjectorColors() {
        double invgamma = 1.0 / this.projector.getSettings().getResponseGamma();
        int s = this.settings.samplesPerChannel;
        if (this.projectorColors == null) {
            this.projectorColors = new Color[s * s * s];
            this.cameraColors = new Color[s * s * s];
            for (int i = 0; i < this.projectorColors.length; ++i) {
                int j = i / s;
                int k = j / s;
                double r = Math.pow((double)(i % s) / (double)(s - 1), invgamma);
                double g = Math.pow((double)(j % s) / (double)(s - 1), invgamma);
                double b = Math.pow((double)(k % s) / (double)(s - 1), invgamma);
                this.projectorColors[i] = new Color((float)r, (float)g, (float)b);
            }
        }
        return this.projectorColors;
    }

    public Color getProjectorColor() {
        return this.getProjectorColors()[this.counter];
    }

    public Color[] getCameraColors() {
        return this.cameraColors;
    }

    public Color getCameraColor() {
        return this.getCameraColors()[this.counter];
    }

    public void addCameraColor() {
        ++this.counter;
    }

    public void addCameraColor(Color color) {
        this.cameraColors[this.counter++] = color;
    }

    public IplImage getMaskImage() {
        return this.mask;
    }

    public IplImage getUndistortedCameraImage() {
        return this.undistImage;
    }

    public boolean processCameraImage(IplImage cameraImage) {
        if (this.undistImage == null || this.undistImage.width() != cameraImage.width() || this.undistImage.height() != cameraImage.height() || this.undistImage.depth() != cameraImage.depth()) {
            this.undistImage = cameraImage.clone();
        }
        if (this.mask == null || this.mask2 == null || this.mask.width() != cameraImage.width() || this.mask2.width() != cameraImage.width() || this.mask.height() != cameraImage.height() || this.mask2.height() != cameraImage.width()) {
            this.mask = IplImage.create((int)cameraImage.width(), (int)cameraImage.height(), (int)8, (int)1, (int)cameraImage.origin());
            this.mask2 = IplImage.create((int)cameraImage.width(), (int)cameraImage.height(), (int)8, (int)1, (int)cameraImage.origin());
        }
        CvMat H = H3x3.get();
        CvMat R = R3x3.get();
        CvMat t = t3x1.get();
        CvMat n = n3x1.get();
        CvMat z = z3x1.get();
        z.put(new double[]{0.0, 0.0, 1.0});
        this.camera.undistort(cameraImage, this.undistImage);
        Marker[] detectedBoardMarkers = this.markerDetector.detect(this.undistImage, false);
        if ((double)detectedBoardMarkers.length >= (double)this.boardPlane.getMarkers().length * this.settings.detectedBoardMin) {
            int j;
            this.boardPlane.getTotalWarp(detectedBoardMarkers, H);
            opencv_core.cvPerspectiveTransform((CvArr)this.boardSrcPts, (CvArr)this.boardDstPts, (CvMat)H);
            double[] boardPts = this.boardDstPts.get();
            opencv_core.cvMatMul((CvArr)this.camKinv, (CvArr)H, (CvArr)R);
            double error = JavaCV.HnToRt(R, z, R, t);
            opencv_core.cvMatMul((CvArr)R, (CvArr)z, (CvArr)n);
            double d = opencv_core.cvDotProduct((CvArr)t, (CvArr)z);
            opencv_core.cvGEMM((CvArr)this.projector.T, (CvArr)n, (double)(-1.0 / d), (CvArr)this.projector.R, (double)1.0, (CvArr)H, (int)2);
            opencv_core.cvMatMul((CvArr)this.projector.cameraMatrix, (CvArr)H, (CvArr)H);
            opencv_core.cvMatMul((CvArr)H, (CvArr)this.camKinv, (CvArr)H);
            opencv_core.cvConvertScale((CvArr)H, (CvArr)H, (double)(1.0 / H.get(8)), (double)0.0);
            opencv_core.cvInvert((CvArr)H, (CvArr)H);
            opencv_core.cvConvertScale((CvArr)H, (CvArr)H, (double)(1.0 / H.get(8)), (double)0.0);
            opencv_core.cvPerspectiveTransform((CvArr)this.projSrcPts, (CvArr)this.projDstPts, (CvMat)H);
            double[] projPts = this.projDstPts.get();
            opencv_core.cvSetZero((CvArr)this.mask);
            double cx = 0.0;
            double cy = 0.0;
            for (j = 0; j < 4; ++j) {
                cx += boardPts[j * 2];
                cy += boardPts[j * 2 + 1];
            }
            cx /= 4.0;
            cy /= 4.0;
            for (j = 0; j < 4; ++j) {
                int n2 = j * 2;
                boardPts[n2] = boardPts[n2] - (boardPts[j * 2] - cx) * this.settings.trimmingFraction;
                int n3 = j * 2 + 1;
                boardPts[n3] = boardPts[n3] - (boardPts[j * 2 + 1] - cy) * this.settings.trimmingFraction;
            }
            opencv_imgproc.cvFillConvexPoly((CvArr)this.mask, (CvPoint)new CvPoint(4L).put((byte)16, boardPts, 0, 8), (int)4, (CvScalar)CvScalar.WHITE, (int)8, (int)16);
            for (j = 0; j < (boardPts.length - 8) / 8; ++j) {
                opencv_imgproc.cvFillConvexPoly((CvArr)this.mask, (CvPoint)new CvPoint(4L).put((byte)16, boardPts, 8 + j * 8, 8), (int)4, (CvScalar)CvScalar.BLACK, (int)8, (int)16);
            }
            opencv_core.cvSetZero((CvArr)this.mask2);
            cx = 0.0;
            cy = 0.0;
            for (j = 0; j < 4; ++j) {
                cx += projPts[j * 2];
                cy += projPts[j * 2 + 1];
            }
            cx /= 4.0;
            cy /= 4.0;
            for (j = 0; j < 4; ++j) {
                int n4 = j * 2;
                projPts[n4] = projPts[n4] - (projPts[j * 2] - cx) * this.settings.trimmingFraction;
                int n5 = j * 2 + 1;
                projPts[n5] = projPts[n5] - (projPts[j * 2 + 1] - cy) * this.settings.trimmingFraction;
            }
            opencv_imgproc.cvFillConvexPoly((CvArr)this.mask2, (CvPoint)new CvPoint(4L).put((byte)16, projPts, 0, 8), (int)4, (CvScalar)CvScalar.WHITE, (int)8, (int)16);
            opencv_core.cvAnd((CvArr)this.mask, (CvArr)this.mask2, (CvArr)this.mask, null);
            opencv_imgproc.cvErode((CvArr)this.mask, (CvArr)this.mask, null, (int)1);
            CvScalar c = opencv_core.cvAvg((CvArr)this.undistImage, (CvArr)this.mask);
            int[] o = this.camera.getRGBColorOrder();
            double s = cameraImage.highValue();
            this.cameraColors[this.counter] = new Color((float)(c.val(o[0]) / s), (float)(c.val(o[1]) / s), (float)(c.val(o[2]) / s));
            return true;
        }
        return false;
    }

    public double calibrate() {
        Color[] cc = this.getCameraColors();
        Color[] pc = this.getProjectorColors();
        assert (this.counter == pc.length);
        ColorCalibrator calibrator = new ColorCalibrator(this.projector);
        this.projector.avgColorErr = calibrator.calibrate(cc, pc);
        this.camera.colorMixingMatrix = CvMat.create((int)3, (int)3);
        this.camera.additiveLight = CvMat.create((int)3, (int)1);
        opencv_core.cvSetIdentity((CvArr)this.camera.colorMixingMatrix);
        opencv_core.cvSetZero((CvArr)this.camera.additiveLight);
        this.counter = 0;
        return this.projector.avgColorErr;
    }

    public static class Settings
    extends BaseChildSettings {
        int samplesPerChannel = 4;
        double trimmingFraction = 0.01;
        double detectedBoardMin = 0.5;

        public int getSamplesPerChannel() {
            return this.samplesPerChannel;
        }

        public void setSamplesPerChannel(int samplesPerChannel) {
            this.samplesPerChannel = samplesPerChannel;
        }

        public double getDetectedBoardMin() {
            return this.detectedBoardMin;
        }

        public void setDetectedBoardMin(double detectedBoardMin) {
            this.detectedBoardMin = detectedBoardMin;
        }
    }
}

