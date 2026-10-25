/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.artoolkitplus.global.ARToolKitPlus
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.CvMat
 *  org.bytedeco.opencv.opencv_core.CvPoint
 *  org.bytedeco.opencv.opencv_core.CvScalar
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.bytedeco.artoolkitplus.global.ARToolKitPlus;
import org.bytedeco.javacv.BaseChildSettings;
import org.bytedeco.javacv.JavaCV;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.CvMat;
import org.bytedeco.opencv.opencv_core.CvPoint;
import org.bytedeco.opencv.opencv_core.CvScalar;
import org.bytedeco.opencv.opencv_core.IplImage;

public class Marker
implements Cloneable {
    public int id;
    public double[] corners;
    public double confidence;
    private static IplImage[] imageCache = new IplImage[4096];
    private static final double[] src = new double[]{0.0, 0.0, 8.0, 0.0, 8.0, 8.0, 0.0, 8.0};
    private static ThreadLocal<CvMat> H3x3 = CvMat.createThreadLocal((int)3, (int)3);
    private static ThreadLocal<CvMat> srcPts4x1 = CvMat.createThreadLocal((int)4, (int)1, (int)6, (int)2);
    private static ThreadLocal<CvMat> dstPts4x1 = CvMat.createThreadLocal((int)4, (int)1, (int)6, (int)2);

    public Marker(int id, double[] corners, double confidence) {
        this.id = id;
        this.corners = corners;
        this.confidence = confidence;
    }

    public Marker(int id, double ... corners) {
        this(id, corners, 1.0);
    }

    public Marker clone() {
        return new Marker(this.id, (double[])this.corners.clone(), this.confidence);
    }

    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + (this.corners != null ? this.corners.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object o) {
        if (o instanceof Marker) {
            Marker m = (Marker)o;
            return m.id == this.id && Arrays.equals(m.corners, this.corners);
        }
        return false;
    }

    public double[] getCenter() {
        double x = 0.0;
        double y = 0.0;
        for (int i = 0; i < 4; ++i) {
            x += this.corners[2 * i];
            y += this.corners[2 * i + 1];
        }
        return new double[]{x /= 4.0, y /= 4.0};
    }

    public IplImage getImage() {
        return Marker.getImage(this.id);
    }

    public static IplImage getImage(int id) {
        if (imageCache[id] == null) {
            Marker.imageCache[id] = IplImage.create((int)8, (int)8, (int)8, (int)1);
            ARToolKitPlus.createImagePatternBCH((int)id, (ByteBuffer)imageCache[id].getByteBuffer());
        }
        return imageCache[id];
    }

    public void draw(IplImage image) {
        this.draw(image, CvScalar.BLACK, 1.0, null);
    }

    public void draw(IplImage image, CvScalar color, double scale, CvMat prewarp) {
        this.draw(image, color, scale, scale, prewarp);
    }

    public void draw(IplImage image, CvScalar color, double scaleX, double scaleY, CvMat prewarp) {
        CvMat H = H3x3.get();
        JavaCV.getPerspectiveTransform(src, this.corners, H);
        if (prewarp != null) {
            opencv_core.cvGEMM((CvArr)prewarp, (CvArr)H, (double)1.0, null, (double)0.0, (CvArr)H, (int)0);
        }
        IplImage marker = this.getImage();
        ByteBuffer mbuf = marker.getByteBuffer();
        CvMat srcPts = srcPts4x1.get();
        CvMat dstPts = dstPts4x1.get();
        CvPoint tempPts = new CvPoint(4L);
        int h = marker.height();
        int w = marker.width();
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                int i;
                if (mbuf.get(y * w + x) != 0) continue;
                srcPts.put(new double[]{x, y, x + 1, y, x + 1, y + 1, x, y + 1});
                opencv_core.cvPerspectiveTransform((CvArr)srcPts, (CvArr)dstPts, (CvMat)H);
                double centerx = 0.0;
                double centery = 0.0;
                for (i = 0; i < 4; ++i) {
                    centerx += dstPts.get(i * 2);
                    centery += dstPts.get(i * 2 + 1);
                }
                centerx /= 4.0;
                centery /= 4.0;
                for (i = 0; i < 4; ++i) {
                    double a = dstPts.get(i * 2);
                    double b = dstPts.get(i * 2 + 1);
                    double dx = centerx - a;
                    double dy = centery - b;
                    dx = dx < 0.0 ? -1.0 : 0.0;
                    dy = dy < 0.0 ? -1.0 : 0.0;
                    tempPts.position((long)i).x((int)Math.round((a * scaleX + dx) * 65536.0));
                    tempPts.position((long)i).y((int)Math.round((b * scaleY + dy) * 65536.0));
                }
                opencv_imgproc.cvFillConvexPoly((CvArr)image, (CvPoint)tempPts.position(0L), (int)4, (CvScalar)color, (int)8, (int)16);
            }
        }
    }

    public static Marker[][] createArray(ArraySettings settings) {
        return Marker.createArray(settings, 0.0, 0.0);
    }

    public static Marker[][] createArray(ArraySettings settings, double marginx, double marginy) {
        Marker[] markers = new Marker[settings.rows * settings.columns];
        int id = 0;
        for (int y = 0; y < settings.rows; ++y) {
            for (int x = 0; x < settings.columns; ++x) {
                double sx = settings.sizeX / 2.0;
                double sy = settings.sizeY / 2.0;
                double cx = (double)x * settings.spacingX + sx + marginx;
                double cy = (double)y * settings.spacingY + sy + marginy;
                markers[id] = new Marker(id, new double[]{cx - sx, cy - sy, cx + sx, cy - sy, cx + sx, cy + sy, cx - sx, cy + sy}, 1.0);
                ++id;
            }
        }
        if (!settings.checkered) {
            return new Marker[][]{markers};
        }
        Marker[] markers1 = new Marker[markers.length / 2];
        Marker[] markers2 = new Marker[markers.length / 2];
        for (int i = 0; i < markers.length; ++i) {
            int y;
            int x = i % settings.columns;
            if (x % 2 == 0 ^ (y = i / settings.columns) % 2 == 0) {
                markers2[i / 2] = markers[i];
                continue;
            }
            markers1[i / 2] = markers[i];
        }
        return new Marker[][]{markers2, markers1};
    }

    public static Marker[][] createArray(int rows, int columns, double sizeX, double sizeY, double spacingX, double spacingY, boolean checkered, double marginx, double marginy) {
        ArraySettings s = new ArraySettings();
        s.rows = rows;
        s.columns = columns;
        s.sizeX = sizeX;
        s.sizeY = sizeY;
        s.spacingX = spacingX;
        s.spacingY = spacingY;
        s.checkered = checkered;
        return Marker.createArray(s, marginx, marginy);
    }

    public static void applyWarp(Marker[] markers, CvMat warp) {
        CvMat pts = srcPts4x1.get();
        for (Marker m : markers) {
            opencv_core.cvPerspectiveTransform((CvArr)pts.put(m.corners), (CvArr)pts, (CvMat)warp);
            pts.get(m.corners);
        }
    }

    public String toString() {
        String s = "[" + this.id + ": (" + this.corners[0] + ", " + this.corners[1] + ") (" + this.corners[2] + ", " + this.corners[3] + ") (" + this.corners[4] + ", " + this.corners[5] + ") (" + this.corners[6] + ", " + this.corners[7] + ")]";
        return s;
    }

    public static class ArraySettings
    extends BaseChildSettings {
        int rows = 8;
        int columns = 12;
        double sizeX = 200.0;
        double sizeY = 200.0;
        double spacingX = 300.0;
        double spacingY = 300.0;
        boolean checkered = true;

        public int getRows() {
            return this.rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
            this.firePropertyChange("rows", this.rows, this.rows);
        }

        public int getColumns() {
            return this.columns;
        }

        public void setColumns(int columns) {
            this.columns = columns;
            this.firePropertyChange("columns", this.columns, this.columns);
        }

        public double getSizeX() {
            return this.sizeX;
        }

        public void setSizeX(double sizeX) {
            this.sizeX = sizeX;
            this.firePropertyChange("sizeX", this.sizeX, this.sizeX);
        }

        public double getSizeY() {
            return this.sizeY;
        }

        public void setSizeY(double sizeY) {
            this.sizeY = sizeY;
            this.firePropertyChange("sizeY", this.sizeY, this.sizeY);
        }

        public double getSpacingX() {
            return this.spacingX;
        }

        public void setSpacingX(double spacingX) {
            this.spacingX = spacingX;
            this.firePropertyChange("spacingX", this.spacingX, this.spacingX);
        }

        public double getSpacingY() {
            return this.spacingY;
        }

        public void setSpacingY(double spacingY) {
            this.spacingY = spacingY;
            this.firePropertyChange("spacingY", this.spacingY, this.spacingY);
        }

        public boolean isCheckered() {
            return this.checkered;
        }

        public void setCheckered(boolean checkered) {
            this.checkered = checkered;
            this.firePropertyChange("checkered", this.checkered, this.checkered);
        }
    }
}

