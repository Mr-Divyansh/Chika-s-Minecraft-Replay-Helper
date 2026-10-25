/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.artoolkitplus.ARMarkerInfo
 *  org.bytedeco.artoolkitplus.MultiTracker
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.CvBox2D
 *  org.bytedeco.opencv.opencv_core.CvMat
 *  org.bytedeco.opencv.opencv_core.CvMemStorage
 *  org.bytedeco.opencv.opencv_core.CvPoint2D32f
 *  org.bytedeco.opencv.opencv_core.CvScalar
 *  org.bytedeco.opencv.opencv_core.CvSize
 *  org.bytedeco.opencv.opencv_core.CvTermCriteria
 *  org.bytedeco.opencv.opencv_core.IplImage
 *  org.bytedeco.opencv.opencv_imgproc.CvFont
 */
package org.bytedeco.javacv;

import java.util.Arrays;
import org.bytedeco.artoolkitplus.ARMarkerInfo;
import org.bytedeco.artoolkitplus.MultiTracker;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacv.BaseChildSettings;
import org.bytedeco.javacv.JavaCV;
import org.bytedeco.javacv.Marker;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.CvBox2D;
import org.bytedeco.opencv.opencv_core.CvMat;
import org.bytedeco.opencv.opencv_core.CvMemStorage;
import org.bytedeco.opencv.opencv_core.CvPoint2D32f;
import org.bytedeco.opencv.opencv_core.CvScalar;
import org.bytedeco.opencv.opencv_core.CvSize;
import org.bytedeco.opencv.opencv_core.CvTermCriteria;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_imgproc.CvFont;

public class MarkerDetector {
    private Settings settings;
    private MultiTracker tracker = null;
    private IntPointer markerNum = new IntPointer(1L);
    private int width = 0;
    private int height = 0;
    private int depth = 0;
    private int channels = 0;
    private IplImage tempImage;
    private IplImage tempImage2;
    private IplImage sumImage;
    private IplImage sqSumImage;
    private IplImage thresholdedImage;
    private CvMat points = CvMat.create((int)1, (int)4, (int)5, (int)2);
    private CvPoint2D32f corners = new CvPoint2D32f(4L);
    private CvMemStorage memory = CvMemStorage.create();
    private CvSize subPixelSize = null;
    private CvSize subPixelZeroZone = null;
    private CvTermCriteria subPixelTermCriteria = null;
    private CvFont font = opencv_imgproc.cvFont((double)1.0, (int)1);
    private CvSize textSize = new CvSize();

    public MarkerDetector(Settings settings) {
        this.setSettings(settings);
    }

    public MarkerDetector() {
        this(new Settings());
    }

    public Settings getSettings() {
        return this.settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
        this.subPixelSize = opencv_core.cvSize((int)(settings.subPixelWindow / 2), (int)(settings.subPixelWindow / 2));
        this.subPixelZeroZone = opencv_core.cvSize((int)-1, (int)-1);
        this.subPixelTermCriteria = opencv_core.cvTermCriteria((int)2, (int)100, (double)0.001);
    }

    public IplImage getThresholdedImage() {
        return this.thresholdedImage;
    }

    private void init(IplImage image) {
        if (this.tracker != null && image.width() == this.width && image.height() == this.height && image.depth() == this.depth && image.nChannels() == this.channels) {
            return;
        }
        this.width = image.width();
        this.height = image.height();
        this.depth = image.depth();
        this.channels = image.nChannels();
        if (this.depth != 8 || this.channels > 1) {
            this.tempImage = IplImage.create((int)this.width, (int)this.height, (int)8, (int)1);
        }
        if (this.depth != 8 && this.channels > 1) {
            this.tempImage2 = IplImage.create((int)this.width, (int)this.height, (int)8, (int)3);
        }
        this.sumImage = IplImage.create((int)(this.width + 1), (int)(this.height + 1), (int)64, (int)1);
        this.sqSumImage = IplImage.create((int)(this.width + 1), (int)(this.height + 1), (int)64, (int)1);
        this.thresholdedImage = IplImage.create((int)this.width, (int)this.height, (int)8, (int)1);
        this.tracker = new MultiTracker(this.thresholdedImage.widthStep(), this.thresholdedImage.height());
        int pixfmt = 7;
        this.tracker.setPixelFormat(pixfmt);
        this.tracker.setBorderWidth(0.125f);
        this.tracker.setUndistortionMode(0);
        this.tracker.setMarkerMode(2);
        this.tracker.setImageProcessingMode(1);
    }

    public Marker[] detect(IplImage image, boolean whiteMarkers) {
        this.init(image);
        if (this.depth != 8 && this.channels > 1) {
            opencv_core.cvConvertScale((CvArr)image, (CvArr)this.tempImage2, (double)(255.0 / image.highValue()), (double)0.0);
            opencv_imgproc.cvCvtColor((CvArr)this.tempImage2, (CvArr)this.tempImage, (int)(this.channels > 3 ? 11 : 6));
            image = this.tempImage;
        } else if (this.depth != 8) {
            opencv_core.cvConvertScale((CvArr)image, (CvArr)this.tempImage, (double)(255.0 / image.highValue()), (double)0.0);
            image = this.tempImage;
        } else if (this.channels > 1) {
            opencv_imgproc.cvCvtColor((CvArr)image, (CvArr)this.tempImage, (int)(this.channels > 3 ? 11 : 6));
            image = this.tempImage;
        }
        JavaCV.adaptiveThreshold(image, this.sumImage, this.sqSumImage, this.thresholdedImage, whiteMarkers, this.settings.thresholdWindowMax, this.settings.thresholdWindowMin, this.settings.thresholdVarMultiplier, whiteMarkers ? this.settings.thresholdKWhiteMarkers : this.settings.thresholdKBlackMarkers);
        int n = 0;
        ARMarkerInfo markers = new ARMarkerInfo(null);
        this.tracker.arDetectMarkerLite(this.thresholdedImage.imageData(), 128, markers, this.markerNum);
        Marker[] markers2 = new Marker[this.markerNum.get(0L)];
        for (int i = 0; i < markers2.length && !markers.isNull(); ++i) {
            markers.position((long)i);
            int id = markers.id();
            if (id < 0) continue;
            int dir = markers.dir();
            float confidence = markers.cf();
            float[] vertex = new float[8];
            markers.vertex().get(vertex);
            int w = this.settings.subPixelWindow / 2 + 1;
            if (vertex[0] - (float)w < 0.0f || vertex[0] + (float)w >= (float)this.width || vertex[1] - (float)w < 0.0f || vertex[1] + (float)w >= (float)this.height || vertex[2] - (float)w < 0.0f || vertex[2] + (float)w >= (float)this.width || vertex[3] - (float)w < 0.0f || vertex[3] + (float)w >= (float)this.height || vertex[4] - (float)w < 0.0f || vertex[4] + (float)w >= (float)this.width || vertex[5] - (float)w < 0.0f || vertex[5] + (float)w >= (float)this.height || vertex[6] - (float)w < 0.0f || vertex[6] + (float)w >= (float)this.width || vertex[7] - (float)w < 0.0f || vertex[7] + (float)w >= (float)this.height) continue;
            this.points.getFloatBuffer().put(vertex);
            CvBox2D box = opencv_imgproc.cvMinAreaRect2((CvArr)this.points, (CvMemStorage)this.memory);
            float bw = box.size().width();
            float bh = box.size().height();
            opencv_core.cvClearMemStorage((CvMemStorage)this.memory);
            if (bw <= 0.0f || bh <= 0.0f || (double)(bw / bh) < 0.1 || bw / bh > 10.0f) continue;
            for (int j = 0; j < 4; ++j) {
                this.corners.position((long)j).put((double)vertex[2 * j], (double)vertex[2 * j + 1]);
            }
            opencv_imgproc.cvFindCornerSubPix((CvArr)image, (CvPoint2D32f)this.corners.position(0L), (int)4, (CvSize)this.subPixelSize, (CvSize)this.subPixelZeroZone, (CvTermCriteria)this.subPixelTermCriteria);
            double[] d = new double[]{this.corners.position((long)((4 - dir) % 4)).x(), this.corners.position((long)((4 - dir) % 4)).y(), this.corners.position((long)((5 - dir) % 4)).x(), this.corners.position((long)((5 - dir) % 4)).y(), this.corners.position((long)((6 - dir) % 4)).x(), this.corners.position((long)((6 - dir) % 4)).y(), this.corners.position((long)((7 - dir) % 4)).x(), this.corners.position((long)((7 - dir) % 4)).y()};
            markers2[n++] = new Marker(id, d, (double)confidence);
        }
        return Arrays.copyOf(markers2, n);
    }

    public void draw(IplImage image, Marker[] markers) {
        for (Marker m : markers) {
            int cx = 0;
            int cy = 0;
            int[] pts = new int[8];
            for (int i = 0; i < 4; ++i) {
                int x = (int)Math.round(m.corners[i * 2] * 65536.0);
                int y = (int)Math.round(m.corners[i * 2 + 1] * 65536.0);
                pts[2 * i] = x;
                pts[2 * i + 1] = y;
                cx += x;
                cy += y;
            }
            cx /= 4;
            opencv_imgproc.cvPolyLine((CvArr)image, (int[])pts, (int[])new int[]{pts.length / 2}, (int)1, (int)1, (CvScalar)opencv_core.CV_RGB((double)0.0, (double)0.0, (double)image.highValue()), (int)1, (int)16, (int)16);
            String text = Integer.toString(m.id);
            int[] baseline = new int[1];
            opencv_imgproc.cvGetTextSize((String)text, (CvFont)this.font, (CvSize)this.textSize, (int[])baseline);
            int[] pt1 = new int[]{cx - (this.textSize.width() * 3 / 2 << 16) / 2, (cy /= 4) + (this.textSize.height() * 3 / 2 << 16) / 2};
            int[] pt2 = new int[]{cx + (this.textSize.width() * 3 / 2 << 16) / 2, cy - (this.textSize.height() * 3 / 2 << 16) / 2};
            opencv_imgproc.cvRectangle((CvArr)image, (int[])pt1, (int[])pt2, (CvScalar)opencv_core.CV_RGB((double)0.0, (double)image.highValue(), (double)0.0), (int)-1, (int)16, (int)16);
            int[] pt = new int[]{(int)Math.round((double)cx / 65536.0 - (double)(this.textSize.width() / 2)), (int)Math.round((double)cy / 65536.0 + (double)(this.textSize.height() / 2)) + 1};
            opencv_imgproc.cvPutText((CvArr)image, (String)text, (int[])pt, (CvFont)this.font, (CvScalar)CvScalar.BLACK);
        }
    }

    public static class Settings
    extends BaseChildSettings {
        int thresholdWindowMin = 5;
        int thresholdWindowMax = 63;
        double thresholdVarMultiplier = 1.0;
        double thresholdKBlackMarkers = 0.6;
        double thresholdKWhiteMarkers = 1.0;
        int subPixelWindow = 11;

        public int getThresholdWindowMin() {
            return this.thresholdWindowMin;
        }

        public void setThresholdWindowMin(int thresholdWindowMin) {
            this.thresholdWindowMin = thresholdWindowMin;
        }

        public int getThresholdWindowMax() {
            return this.thresholdWindowMax;
        }

        public void setThresholdWindowMax(int thresholdWindowMax) {
            this.thresholdWindowMax = thresholdWindowMax;
        }

        public double getThresholdVarMultiplier() {
            return this.thresholdVarMultiplier;
        }

        public void setThresholdVarMultiplier(double thresholdVarMultiplier) {
            this.thresholdVarMultiplier = thresholdVarMultiplier;
        }

        public double getThresholdKBlackMarkers() {
            return this.thresholdKBlackMarkers;
        }

        public void setThresholdKBlackMarkers(double thresholdKBlackMarkers) {
            this.thresholdKBlackMarkers = thresholdKBlackMarkers;
        }

        public double getThresholdKWhiteMarkers() {
            return this.thresholdKWhiteMarkers;
        }

        public void setThresholdKWhiteMarkers(double thresholdKWhiteMarkers) {
            this.thresholdKWhiteMarkers = thresholdKWhiteMarkers;
        }

        public int getSubPixelWindow() {
            return this.subPixelWindow;
        }

        public void setSubPixelWindow(int subPixelWindow) {
            this.subPixelWindow = subPixelWindow;
        }
    }
}

