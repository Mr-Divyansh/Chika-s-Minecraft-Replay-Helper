/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.CvMat
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import org.bytedeco.javacv.GeometricCalibrator;
import org.bytedeco.javacv.MarkedPlane;
import org.bytedeco.javacv.Marker;
import org.bytedeco.javacv.MarkerDetector;
import org.bytedeco.javacv.Parallel;
import org.bytedeco.javacv.ProjectiveDevice;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.CvMat;
import org.bytedeco.opencv.opencv_core.IplImage;

public class ProCamGeometricCalibrator {
    private final int MSB_IMAGE_SHIFT = 8;
    private final int LSB_IMAGE_SHIFT = 7;
    private Settings settings;
    private MarkerDetector.Settings detectorSettings;
    private GeometricCalibrator[] cameraCalibrators;
    private MarkerDetector[] markerDetectors;
    LinkedList<Marker[]>[] allImagedBoardMarkers;
    private IplImage[] grayscaleImage;
    private IplImage[] tempImage1;
    private IplImage[] tempImage2;
    private Marker[][] lastDetectedMarkers1;
    private Marker[][] lastDetectedMarkers2;
    private double[] rmseBoardWarp;
    private double[] rmseProjWarp;
    private CvMat[] boardWarp;
    private CvMat[] projWarp;
    private CvMat[] prevBoardWarp;
    private CvMat[] lastBoardWarp;
    private CvMat[] tempPts1;
    private CvMat[] tempPts2;
    private boolean updatePrewarp = false;
    private final MarkedPlane boardPlane;
    private final MarkedPlane projectorPlane;
    private final GeometricCalibrator projectorCalibrator;
    private final CvMat boardWarpSrcPts;
    private static ThreadLocal<CvMat> tempWarp3x3 = CvMat.createThreadLocal((int)3, (int)3);

    public ProCamGeometricCalibrator(Settings settings, MarkerDetector.Settings detectorSettings, MarkedPlane boardPlane, MarkedPlane projectorPlane, ProjectiveDevice camera, ProjectiveDevice projector) {
        this(settings, detectorSettings, boardPlane, projectorPlane, new GeometricCalibrator[]{new GeometricCalibrator(settings, detectorSettings, boardPlane, camera)}, new GeometricCalibrator(settings, detectorSettings, projectorPlane, projector));
    }

    public ProCamGeometricCalibrator(Settings settings, MarkerDetector.Settings detectorSettings, MarkedPlane boardPlane, MarkedPlane projectorPlane, GeometricCalibrator[] cameraCalibrators, GeometricCalibrator projectorCalibrator) {
        int h;
        int w;
        this.settings = settings;
        this.detectorSettings = detectorSettings;
        this.boardPlane = boardPlane;
        this.projectorPlane = projectorPlane;
        this.cameraCalibrators = cameraCalibrators;
        int n = cameraCalibrators.length;
        this.markerDetectors = new MarkerDetector[n];
        this.allImagedBoardMarkers = new LinkedList[n];
        this.grayscaleImage = new IplImage[n];
        this.tempImage1 = new IplImage[n];
        this.tempImage2 = new IplImage[n];
        this.lastDetectedMarkers1 = new Marker[n][];
        this.lastDetectedMarkers2 = new Marker[n][];
        this.rmseBoardWarp = new double[n];
        this.rmseProjWarp = new double[n];
        this.boardWarp = new CvMat[n];
        this.projWarp = new CvMat[n];
        this.prevBoardWarp = new CvMat[n];
        this.lastBoardWarp = new CvMat[n];
        this.tempPts1 = new CvMat[n];
        this.tempPts2 = new CvMat[n];
        for (int i = 0; i < n; ++i) {
            this.markerDetectors[i] = new MarkerDetector(detectorSettings);
            this.allImagedBoardMarkers[i] = new LinkedList();
            this.grayscaleImage[i] = null;
            this.tempImage1[i] = null;
            this.tempImage2[i] = null;
            this.lastDetectedMarkers1[i] = null;
            this.lastDetectedMarkers2[i] = null;
            this.rmseBoardWarp[i] = Double.POSITIVE_INFINITY;
            this.rmseProjWarp[i] = Double.POSITIVE_INFINITY;
            this.boardWarp[i] = CvMat.create((int)3, (int)3);
            this.projWarp[i] = CvMat.create((int)3, (int)3);
            this.prevBoardWarp[i] = CvMat.create((int)3, (int)3);
            this.lastBoardWarp[i] = CvMat.create((int)3, (int)3);
            opencv_core.cvSetIdentity((CvArr)this.prevBoardWarp[i]);
            opencv_core.cvSetIdentity((CvArr)this.lastBoardWarp[i]);
            this.tempPts1[i] = CvMat.create((int)1, (int)4, (int)6, (int)2);
            this.tempPts2[i] = CvMat.create((int)1, (int)4, (int)6, (int)2);
        }
        this.projectorCalibrator = projectorCalibrator;
        this.boardWarpSrcPts = CvMat.create((int)1, (int)4, (int)6, (int)2);
        if (boardPlane != null) {
            w = boardPlane.getImage().width();
            h = boardPlane.getImage().height();
            this.boardWarpSrcPts.put(new double[]{0.0, 0.0, w, 0.0, w, h, 0.0, h});
        }
        if (projectorPlane != null) {
            w = projectorPlane.getImage().width();
            h = projectorPlane.getImage().height();
            projectorCalibrator.getProjectiveDevice().imageWidth = w;
            projectorCalibrator.getProjectiveDevice().imageHeight = h;
        }
    }

    public MarkedPlane getBoardPlane() {
        return this.boardPlane;
    }

    public MarkedPlane getProjectorPlane() {
        return this.projectorPlane;
    }

    public GeometricCalibrator[] getCameraCalibrators() {
        return this.cameraCalibrators;
    }

    public GeometricCalibrator getProjectorCalibrator() {
        return this.projectorCalibrator;
    }

    public int getImageCount() {
        int n = this.projectorCalibrator.getImageCount() / this.cameraCalibrators.length;
        for (GeometricCalibrator c : this.cameraCalibrators) {
            assert (c.getImageCount() == n);
        }
        return n;
    }

    public Marker[][] processCameraImage(IplImage cameraImage) {
        return this.processCameraImage(cameraImage, 0);
    }

    public Marker[][] processCameraImage(IplImage cameraImage, final int cameraNumber) {
        Marker[][] markerArray;
        boolean projWhiteMarkers;
        this.cameraCalibrators[cameraNumber].getProjectiveDevice().imageWidth = cameraImage.width();
        this.cameraCalibrators[cameraNumber].getProjectiveDevice().imageHeight = cameraImage.height();
        if (cameraImage.nChannels() > 1) {
            if (this.grayscaleImage[cameraNumber] == null || this.grayscaleImage[cameraNumber].width() != cameraImage.width() || this.grayscaleImage[cameraNumber].height() != cameraImage.height() || this.grayscaleImage[cameraNumber].depth() != cameraImage.depth()) {
                this.grayscaleImage[cameraNumber] = IplImage.create((int)cameraImage.width(), (int)cameraImage.height(), (int)cameraImage.depth(), (int)1, (int)cameraImage.origin());
            }
            opencv_imgproc.cvCvtColor((CvArr)cameraImage, (CvArr)this.grayscaleImage[cameraNumber], (int)6);
        } else {
            this.grayscaleImage[cameraNumber] = cameraImage;
        }
        final boolean boardWhiteMarkers = this.boardPlane.getForegroundColor().magnitude() > this.boardPlane.getBackgroundColor().magnitude();
        boolean bl = projWhiteMarkers = this.projectorPlane.getForegroundColor().magnitude() > this.projectorPlane.getBackgroundColor().magnitude();
        if (this.grayscaleImage[cameraNumber].depth() > 8) {
            if (this.tempImage1[cameraNumber] == null || this.tempImage1[cameraNumber].width() != this.grayscaleImage[cameraNumber].width() || this.tempImage1[cameraNumber].height() != this.grayscaleImage[cameraNumber].height()) {
                this.tempImage1[cameraNumber] = IplImage.create((int)this.grayscaleImage[cameraNumber].width(), (int)this.grayscaleImage[cameraNumber].height(), (int)8, (int)1, (int)this.grayscaleImage[cameraNumber].origin());
                this.tempImage2[cameraNumber] = IplImage.create((int)this.grayscaleImage[cameraNumber].width(), (int)this.grayscaleImage[cameraNumber].height(), (int)8, (int)1, (int)this.grayscaleImage[cameraNumber].origin());
            }
            Parallel.run(new Runnable(){

                @Override
                public void run() {
                    opencv_core.cvConvertScale((CvArr)ProCamGeometricCalibrator.this.grayscaleImage[cameraNumber], (CvArr)ProCamGeometricCalibrator.this.tempImage1[cameraNumber], (double)0.0078125, (double)0.0);
                    ((ProCamGeometricCalibrator)ProCamGeometricCalibrator.this).lastDetectedMarkers1[cameraNumber] = ((ProCamGeometricCalibrator)ProCamGeometricCalibrator.this).cameraCalibrators[cameraNumber].markerDetector.detect(ProCamGeometricCalibrator.this.tempImage1[cameraNumber], boardWhiteMarkers);
                }
            }, new Runnable(){

                @Override
                public void run() {
                    opencv_core.cvConvertScale((CvArr)ProCamGeometricCalibrator.this.grayscaleImage[cameraNumber], (CvArr)ProCamGeometricCalibrator.this.tempImage2[cameraNumber], (double)0.00390625, (double)0.0);
                    ((ProCamGeometricCalibrator)ProCamGeometricCalibrator.this).lastDetectedMarkers2[cameraNumber] = ProCamGeometricCalibrator.this.markerDetectors[cameraNumber].detect(ProCamGeometricCalibrator.this.tempImage2[cameraNumber], projWhiteMarkers);
                }
            });
        } else {
            Parallel.run(new Runnable(){

                @Override
                public void run() {
                    ((ProCamGeometricCalibrator)ProCamGeometricCalibrator.this).lastDetectedMarkers1[cameraNumber] = ((ProCamGeometricCalibrator)ProCamGeometricCalibrator.this).cameraCalibrators[cameraNumber].markerDetector.detect(ProCamGeometricCalibrator.this.grayscaleImage[cameraNumber], boardWhiteMarkers);
                }
            }, new Runnable(){

                @Override
                public void run() {
                    ((ProCamGeometricCalibrator)ProCamGeometricCalibrator.this).lastDetectedMarkers2[cameraNumber] = ProCamGeometricCalibrator.this.markerDetectors[cameraNumber].detect(ProCamGeometricCalibrator.this.grayscaleImage[cameraNumber], projWhiteMarkers);
                }
            });
        }
        if (this.processMarkers(cameraNumber)) {
            Marker[][] markerArrayArray = new Marker[2][];
            markerArrayArray[0] = this.lastDetectedMarkers1[cameraNumber];
            markerArray = markerArrayArray;
            markerArrayArray[1] = this.lastDetectedMarkers2[cameraNumber];
        } else {
            markerArray = null;
        }
        return markerArray;
    }

    public void drawMarkers(IplImage image) {
        this.drawMarkers(image, 0);
    }

    public void drawMarkers(IplImage image, int cameraNumber) {
        this.cameraCalibrators[cameraNumber].markerDetector.draw(image, this.lastDetectedMarkers1[cameraNumber]);
        this.projectorCalibrator.markerDetector.draw(image, this.lastDetectedMarkers2[cameraNumber]);
    }

    public boolean processMarkers() {
        return this.processMarkers(0);
    }

    public boolean processMarkers(int cameraNumber) {
        return this.processMarkers(this.lastDetectedMarkers1[cameraNumber], this.lastDetectedMarkers2[cameraNumber], cameraNumber);
    }

    public boolean processMarkers(Marker[] imagedBoardMarkers, Marker[] imagedProjectorMarkers) {
        return this.processMarkers(imagedBoardMarkers, imagedProjectorMarkers, 0);
    }

    public boolean processMarkers(Marker[] imagedBoardMarkers, Marker[] imagedProjectorMarkers, int cameraNumber) {
        this.rmseBoardWarp[cameraNumber] = this.boardPlane.getTotalWarp(imagedBoardMarkers, this.boardWarp[cameraNumber]);
        this.rmseProjWarp[cameraNumber] = this.projectorPlane.getTotalWarp(imagedProjectorMarkers, this.projWarp[cameraNumber]);
        int imageSize = (this.cameraCalibrators[cameraNumber].getProjectiveDevice().imageWidth + this.cameraCalibrators[cameraNumber].getProjectiveDevice().imageHeight) / 2;
        if (!(this.rmseBoardWarp[cameraNumber] <= this.settings.prewarpUpdateErrorMax * (double)imageSize) || !(this.rmseProjWarp[cameraNumber] <= this.settings.prewarpUpdateErrorMax * (double)imageSize)) {
            return false;
        }
        this.updatePrewarp = true;
        if ((double)imagedBoardMarkers.length < (double)this.boardPlane.getMarkers().length * this.settings.detectedBoardMin || (double)imagedProjectorMarkers.length < (double)this.projectorPlane.getMarkers().length * this.settings.detectedProjectorMin) {
            return false;
        }
        opencv_core.cvPerspectiveTransform((CvArr)this.boardWarpSrcPts, (CvArr)this.tempPts1[cameraNumber], (CvMat)this.boardWarp[cameraNumber]);
        opencv_core.cvPerspectiveTransform((CvArr)this.boardWarpSrcPts, (CvArr)this.tempPts2[cameraNumber], (CvMat)this.prevBoardWarp[cameraNumber]);
        double rmsePrev = opencv_core.cvNorm((CvArr)this.tempPts1[cameraNumber], (CvArr)this.tempPts2[cameraNumber]);
        opencv_core.cvPerspectiveTransform((CvArr)this.boardWarpSrcPts, (CvArr)this.tempPts2[cameraNumber], (CvMat)this.lastBoardWarp[cameraNumber]);
        double rmseLast = opencv_core.cvNorm((CvArr)this.tempPts1[cameraNumber], (CvArr)this.tempPts2[cameraNumber]);
        opencv_core.cvCopy((CvArr)this.boardWarp[cameraNumber], (CvArr)this.prevBoardWarp[cameraNumber]);
        return rmsePrev < this.settings.patternSteadySize * (double)imageSize && rmseLast > this.settings.patternMovedSize * (double)imageSize;
    }

    public void addMarkers() throws InterruptedException {
        this.addMarkers(0);
    }

    public void addMarkers(int cameraNumber) throws InterruptedException {
        this.addMarkers(this.lastDetectedMarkers1[cameraNumber], this.lastDetectedMarkers2[cameraNumber], cameraNumber);
    }

    public void addMarkers(Marker[] imagedBoardMarkers, Marker[] imagedProjectorMarkers) throws InterruptedException {
        this.addMarkers(imagedBoardMarkers, imagedProjectorMarkers, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addMarkers(Marker[] imagedBoardMarkers, Marker[] imagedProjectorMarkers, int cameraNumber) throws InterruptedException {
        int i;
        CvMat tempWarp = tempWarp3x3.get();
        if (!this.settings.useOnlyIntersection) {
            this.cameraCalibrators[cameraNumber].addMarkers(this.boardPlane.getMarkers(), imagedBoardMarkers);
            this.allImagedBoardMarkers[cameraNumber].add(imagedBoardMarkers);
        } else {
            Marker[] inProjectorBoardMarkers = new Marker[imagedBoardMarkers.length];
            for (i = 0; i < inProjectorBoardMarkers.length; ++i) {
                inProjectorBoardMarkers[i] = imagedBoardMarkers[i].clone();
            }
            opencv_core.cvInvert((CvArr)this.projWarp[cameraNumber], (CvArr)tempWarp);
            Marker.applyWarp(inProjectorBoardMarkers, tempWarp);
            int w = this.projectorPlane.getImage().width();
            int h = this.projectorPlane.getImage().height();
            Marker[] boardMarkersToAdd = new Marker[imagedBoardMarkers.length];
            int totalToAdd = 0;
            for (int i2 = 0; i2 < inProjectorBoardMarkers.length; ++i2) {
                double[] c = inProjectorBoardMarkers[i2].corners;
                boolean outside = false;
                for (int j = 0; j < 4; ++j) {
                    int margin = this.detectorSettings.subPixelWindow / 2;
                    if (!(c[2 * j] < (double)margin || c[2 * j] >= (double)(w - margin) || c[2 * j + 1] < (double)margin) && !(c[2 * j + 1] >= (double)(h - margin))) continue;
                    outside = true;
                    break;
                }
                if (outside) continue;
                boardMarkersToAdd[totalToAdd++] = imagedBoardMarkers[i2];
            }
            Marker[] a = Arrays.copyOf(boardMarkersToAdd, totalToAdd);
            this.cameraCalibrators[cameraNumber].addMarkers(this.boardPlane.getMarkers(), a);
            this.allImagedBoardMarkers[cameraNumber].add(a);
        }
        Marker[] prewrappedProjMarkers = new Marker[this.projectorPlane.getMarkers().length];
        for (i = 0; i < prewrappedProjMarkers.length; ++i) {
            prewrappedProjMarkers[i] = this.projectorPlane.getMarkers()[i].clone();
        }
        Marker.applyWarp(prewrappedProjMarkers, this.projectorPlane.getPrewarp());
        GeometricCalibrator geometricCalibrator = this.projectorCalibrator;
        synchronized (geometricCalibrator) {
            while (this.projectorCalibrator.getImageCount() % this.cameraCalibrators.length < cameraNumber) {
                this.projectorCalibrator.wait();
            }
            this.projectorCalibrator.addMarkers(imagedProjectorMarkers, prewrappedProjMarkers);
            this.projectorCalibrator.notify();
        }
        opencv_core.cvCopy((CvArr)this.boardWarp[cameraNumber], (CvArr)this.lastBoardWarp[cameraNumber]);
    }

    public IplImage getProjectorImage() {
        if (this.updatePrewarp) {
            double minRmse = Double.MAX_VALUE;
            int minCameraNumber = 0;
            for (int i = 0; i < this.cameraCalibrators.length; ++i) {
                double rmse = this.rmseBoardWarp[i] + this.rmseProjWarp[i];
                if (!(rmse < minRmse)) continue;
                minRmse = rmse;
                minCameraNumber = i;
            }
            CvMat prewarp = this.projectorPlane.getPrewarp();
            opencv_core.cvInvert((CvArr)this.projWarp[minCameraNumber], (CvArr)prewarp);
            opencv_core.cvMatMul((CvArr)prewarp, (CvArr)this.boardWarp[minCameraNumber], (CvArr)prewarp);
            this.projectorPlane.setPrewarp(prewarp);
        }
        return this.projectorPlane.getImage();
    }

    public double[] calibrate(boolean useCenters, boolean calibrateCameras) {
        return this.calibrate(useCenters, calibrateCameras);
    }

    public double[] calibrate(boolean useCenters, boolean calibrateCameras, int cameraAtOrigin) {
        int cameraNumber;
        GeometricCalibrator calibratorAtOrigin = this.cameraCalibrators[cameraAtOrigin];
        if (calibrateCameras) {
            for (int cameraNumber2 = 0; cameraNumber2 < this.cameraCalibrators.length; ++cameraNumber2) {
                this.cameraCalibrators[cameraNumber2].calibrate(useCenters);
                if (this.cameraCalibrators[cameraNumber2] == calibratorAtOrigin) continue;
                calibratorAtOrigin.calibrateStereo(useCenters, this.cameraCalibrators[cameraNumber2]);
            }
        }
        LinkedList<Marker[]> allDistortedProjectorMarkers = this.projectorCalibrator.getAllObjectMarkers();
        LinkedList<Marker[]> distortedProjectorMarkersAtOrigin = new LinkedList<Marker[]>();
        LinkedList<Marker[]> allUndistortedProjectorMarkers = new LinkedList<Marker[]>();
        LinkedList<Marker[]> undistortedProjectorMarkersAtOrigin = new LinkedList<Marker[]>();
        Iterator ip = allDistortedProjectorMarkers.iterator();
        Iterator[] ib = new Iterator[this.cameraCalibrators.length];
        for (cameraNumber = 0; cameraNumber < this.cameraCalibrators.length; ++cameraNumber) {
            ib[cameraNumber] = this.allImagedBoardMarkers[cameraNumber].iterator();
        }
        while (ip.hasNext()) {
            for (cameraNumber = 0; cameraNumber < this.cameraCalibrators.length; ++cameraNumber) {
                Marker m;
                int i;
                double maxError = this.settings.prewarpUpdateErrorMax * (double)(this.cameraCalibrators[cameraNumber].getProjectiveDevice().imageWidth + this.cameraCalibrators[cameraNumber].getProjectiveDevice().imageHeight) / 2.0;
                Marker[] distortedBoardMarkers = (Marker[])ib[cameraNumber].next();
                Marker[] distortedProjectorMarkers = (Marker[])ip.next();
                Marker[] undistortedBoardMarkers = new Marker[distortedBoardMarkers.length];
                Marker[] undistortedProjectorMarkers = new Marker[distortedProjectorMarkers.length];
                for (i = 0; i < distortedBoardMarkers.length; ++i) {
                    m = undistortedBoardMarkers[i] = distortedBoardMarkers[i].clone();
                    m.corners = this.cameraCalibrators[cameraNumber].getProjectiveDevice().undistort(m.corners);
                }
                for (i = 0; i < distortedProjectorMarkers.length; ++i) {
                    m = undistortedProjectorMarkers[i] = distortedProjectorMarkers[i].clone();
                    m.corners = this.cameraCalibrators[cameraNumber].getProjectiveDevice().undistort(m.corners);
                }
                if (this.boardPlane.getTotalWarp(undistortedBoardMarkers, this.boardWarp[cameraNumber]) > maxError) assert (false);
                opencv_core.cvInvert((CvArr)this.boardWarp[cameraNumber], (CvArr)this.boardWarp[cameraNumber]);
                Marker.applyWarp(undistortedProjectorMarkers, this.boardWarp[cameraNumber]);
                allUndistortedProjectorMarkers.add(undistortedProjectorMarkers);
                if (this.cameraCalibrators[cameraNumber] == calibratorAtOrigin) {
                    undistortedProjectorMarkersAtOrigin.add(undistortedProjectorMarkers);
                    distortedProjectorMarkersAtOrigin.add(distortedProjectorMarkers);
                    continue;
                }
                undistortedProjectorMarkersAtOrigin.add(new Marker[0]);
                distortedProjectorMarkersAtOrigin.add(new Marker[0]);
            }
        }
        this.projectorCalibrator.setAllObjectMarkers(allUndistortedProjectorMarkers);
        double[] reprojErr = this.projectorCalibrator.calibrate(useCenters);
        LinkedList<Marker[]> om = calibratorAtOrigin.getAllObjectMarkers();
        LinkedList<Marker[]> im = calibratorAtOrigin.getAllImageMarkers();
        calibratorAtOrigin.setAllObjectMarkers(undistortedProjectorMarkersAtOrigin);
        calibratorAtOrigin.setAllImageMarkers(distortedProjectorMarkersAtOrigin);
        double[] epipolarErr = calibratorAtOrigin.calibrateStereo(useCenters, this.projectorCalibrator);
        this.projectorCalibrator.setAllObjectMarkers(allDistortedProjectorMarkers);
        calibratorAtOrigin.setAllObjectMarkers(om);
        calibratorAtOrigin.setAllImageMarkers(im);
        return new double[]{reprojErr[0], reprojErr[1], epipolarErr[0], epipolarErr[1]};
    }

    public static class Settings
    extends GeometricCalibrator.Settings {
        double detectedProjectorMin = 0.5;
        boolean useOnlyIntersection = true;
        double prewarpUpdateErrorMax = 0.01;

        public double getDetectedProjectorMin() {
            return this.detectedProjectorMin;
        }

        public void setDetectedProjectorMin(double detectedProjectorMin) {
            this.detectedProjectorMin = detectedProjectorMin;
        }

        public boolean isUseOnlyIntersection() {
            return this.useOnlyIntersection;
        }

        public void setUseOnlyIntersection(boolean useOnlyIntersection) {
            this.useOnlyIntersection = useOnlyIntersection;
        }

        public double getPrewarpUpdateErrorMax() {
            return this.prewarpUpdateErrorMax;
        }

        public void setPrewarpUpdateErrorMax(double prewarpUpdateErrorMax) {
            this.prewarpUpdateErrorMax = prewarpUpdateErrorMax;
        }
    }
}

