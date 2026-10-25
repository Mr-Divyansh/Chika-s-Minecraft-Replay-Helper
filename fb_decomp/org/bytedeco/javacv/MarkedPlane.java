/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.global.opencv_calib3d
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.CvMat
 *  org.bytedeco.opencv.opencv_core.CvScalar
 *  org.bytedeco.opencv.opencv_core.IplImage
 *  org.bytedeco.opencv.opencv_core.Mat
 */
package org.bytedeco.javacv;

import org.bytedeco.javacv.JavaCV;
import org.bytedeco.javacv.Marker;
import org.bytedeco.opencv.global.opencv_calib3d;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.CvMat;
import org.bytedeco.opencv.opencv_core.CvScalar;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;

public class MarkedPlane {
    private Marker[] markers = null;
    private CvMat prewarp;
    private IplImage planeImage = null;
    private IplImage superPlaneImage = null;
    private CvScalar foregroundColor;
    private CvScalar backgroundColor;
    private ThreadLocal<CvMat> localSrcPts;
    private ThreadLocal<CvMat> localDstPts;
    private static ThreadLocal<CvMat> tempWarp3x3 = CvMat.createThreadLocal((int)3, (int)3);

    public MarkedPlane(int width, int height, Marker[] planeMarkers, double superScale) {
        this(width, height, planeMarkers, false, CvScalar.BLACK, CvScalar.WHITE, superScale);
    }

    public MarkedPlane(int width, int height, Marker[] markers, boolean initPrewarp, CvScalar foregroundColor, CvScalar backgroundColor, double superScale) {
        this.markers = markers;
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
        this.prewarp = null;
        if (initPrewarp) {
            this.prewarp = CvMat.create((int)3, (int)3);
            double minx = Double.MAX_VALUE;
            double miny = Double.MAX_VALUE;
            double maxx = Double.MIN_VALUE;
            double maxy = Double.MIN_VALUE;
            for (Marker m : markers) {
                double[] c = m.corners;
                minx = Math.min(Math.min(Math.min(Math.min(minx, c[0]), c[2]), c[4]), c[6]);
                miny = Math.min(Math.min(Math.min(Math.min(miny, c[1]), c[3]), c[5]), c[7]);
                maxx = Math.max(Math.max(Math.max(Math.max(maxx, c[0]), c[2]), c[4]), c[6]);
                maxy = Math.max(Math.max(Math.max(Math.max(maxy, c[1]), c[3]), c[5]), c[7]);
            }
            double aspect = (maxx - minx) / (maxy - miny);
            if (aspect > (double)width / (double)height) {
                double h = (double)width / aspect;
                JavaCV.getPerspectiveTransform(new double[]{minx, miny, maxx, miny, maxx, maxy, minx, maxy}, new double[]{0.0, (double)height - h, width, (double)height - h, width, height, 0.0, height}, this.prewarp);
            } else {
                double w = (double)height * aspect;
                JavaCV.getPerspectiveTransform(new double[]{minx, miny, maxx, miny, maxx, maxy, minx, maxy}, new double[]{0.0, 0.0, w, 0.0, w, height, 0.0, height}, this.prewarp);
            }
        }
        if (width > 0 && height > 0) {
            this.planeImage = IplImage.create((int)width, (int)height, (int)8, (int)1);
            this.superPlaneImage = superScale == 1.0 ? null : IplImage.create((int)((int)Math.ceil((double)width * superScale)), (int)((int)Math.ceil((double)height * superScale)), (int)8, (int)1);
            this.setPrewarp(this.prewarp);
        }
        this.localSrcPts = CvMat.createThreadLocal((int)(markers.length * 4), (int)2);
        this.localDstPts = CvMat.createThreadLocal((int)(markers.length * 4), (int)2);
    }

    public CvScalar getForegroundColor() {
        return this.foregroundColor;
    }

    public void setForegroundColor(CvScalar foregroundColor) {
        this.foregroundColor = foregroundColor;
        this.setPrewarp(this.prewarp);
    }

    public CvScalar getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(CvScalar backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.setPrewarp(this.prewarp);
    }

    public Marker[] getMarkers() {
        return this.markers;
    }

    public void setColors(CvScalar foregroundColor, CvScalar backgroundColor) {
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
        this.setPrewarp(this.prewarp);
    }

    public CvMat getPrewarp() {
        return this.prewarp;
    }

    public void setPrewarp(CvMat prewarp) {
        this.prewarp = prewarp;
        if (this.superPlaneImage == null) {
            opencv_core.cvSet((CvArr)this.planeImage, (CvScalar)this.backgroundColor);
        } else {
            opencv_core.cvSet((CvArr)this.superPlaneImage, (CvScalar)this.backgroundColor);
        }
        for (int i = 0; i < this.markers.length; ++i) {
            if (this.superPlaneImage == null) {
                this.markers[i].draw(this.planeImage, this.foregroundColor, 1.0, prewarp);
                continue;
            }
            this.markers[i].draw(this.superPlaneImage, this.foregroundColor, (double)this.superPlaneImage.width() / (double)this.planeImage.width(), prewarp);
        }
        if (this.superPlaneImage != null) {
            opencv_imgproc.cvResize((CvArr)this.superPlaneImage, (CvArr)this.planeImage, (int)3);
        }
    }

    public IplImage getImage() {
        return this.planeImage;
    }

    public int getWidth() {
        return this.planeImage.width();
    }

    public int getHeight() {
        return this.planeImage.height();
    }

    public double getTotalWarp(Marker[] imagedMarkers, CvMat totalWarp) {
        return this.getTotalWarp(imagedMarkers, totalWarp, false);
    }

    public double getTotalWarp(Marker[] imagedMarkers, CvMat totalWarp, boolean useCenters) {
        double rmse = Double.POSITIVE_INFINITY;
        int pointsPerMarker = useCenters ? 1 : 4;
        CvMat srcPts = this.localSrcPts.get();
        srcPts.rows(this.markers.length * pointsPerMarker);
        CvMat dstPts = this.localDstPts.get();
        dstPts.rows(this.markers.length * pointsPerMarker);
        int numPoints = 0;
        block0: for (Marker m1 : this.markers) {
            for (Marker m2 : imagedMarkers) {
                if (m1.id != m2.id) continue;
                if (useCenters) {
                    srcPts.put(numPoints * 2, m1.getCenter());
                    dstPts.put(numPoints * 2, m2.getCenter());
                } else {
                    srcPts.put(numPoints * 2, m1.corners);
                    dstPts.put(numPoints * 2, m2.corners);
                }
                numPoints += pointsPerMarker;
                continue block0;
            }
        }
        if (numPoints > 4 || srcPts.rows() == 4 && numPoints == 4) {
            srcPts.rows(numPoints);
            dstPts.rows(numPoints);
            if (numPoints == 4) {
                JavaCV.getPerspectiveTransform(srcPts.get(), dstPts.get(), totalWarp);
            } else {
                opencv_core.cvCopy((CvArr)opencv_core.cvMat((Mat)opencv_calib3d.findHomography((Mat)opencv_core.cvarrToMat((CvArr)srcPts), (Mat)opencv_core.cvarrToMat((CvArr)dstPts))), (CvArr)totalWarp);
            }
            srcPts.cols(1);
            srcPts.type(6, 2);
            dstPts.cols(1);
            dstPts.type(6, 2);
            opencv_core.cvPerspectiveTransform((CvArr)srcPts, (CvArr)srcPts, (CvMat)totalWarp);
            srcPts.cols(2);
            srcPts.type(6, 1);
            dstPts.cols(2);
            dstPts.type(6, 1);
            rmse = 0.0;
            for (int i = 0; i < numPoints; ++i) {
                double dx = dstPts.get(i * 2) - srcPts.get(i * 2);
                double dy = dstPts.get(i * 2 + 1) - srcPts.get(i * 2 + 1);
                rmse += dx * dx + dy * dy;
            }
            rmse = Math.sqrt(rmse / (double)numPoints);
            if (this.prewarp != null) {
                CvMat tempWarp = tempWarp3x3.get();
                opencv_core.cvInvert((CvArr)this.prewarp, (CvArr)tempWarp);
                opencv_core.cvMatMul((CvArr)totalWarp, (CvArr)tempWarp, (CvArr)totalWarp);
            }
        }
        return rmse;
    }
}

