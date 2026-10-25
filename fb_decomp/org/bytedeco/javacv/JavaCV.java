/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.CvBox2D
 *  org.bytedeco.opencv.opencv_core.CvMat
 *  org.bytedeco.opencv.opencv_core.CvPoint2D32f
 *  org.bytedeco.opencv.opencv_core.CvRect
 *  org.bytedeco.opencv.opencv_core.CvSize2D32f
 *  org.bytedeco.opencv.opencv_core.IplImage
 *  org.bytedeco.opencv.opencv_imgproc.CvMoments
 */
package org.bytedeco.javacv;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import org.bytedeco.javacv.Parallel;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.CvBox2D;
import org.bytedeco.opencv.opencv_core.CvMat;
import org.bytedeco.opencv.opencv_core.CvPoint2D32f;
import org.bytedeco.opencv.opencv_core.CvRect;
import org.bytedeco.opencv.opencv_core.CvSize2D32f;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_imgproc.CvMoments;

public class JavaCV {
    public static final double SQRT2 = 1.4142135623730951;
    public static final double FLT_EPSILON = 1.1920928955078125E-7;
    public static final double DBL_EPSILON = 2.220446049250313E-16;
    private static ThreadLocal<CvMoments> moments = CvMoments.createThreadLocal();
    private static ThreadLocal<CvMat> A8x8 = CvMat.createThreadLocal((int)8, (int)8);
    private static ThreadLocal<CvMat> b8x1 = CvMat.createThreadLocal((int)8, (int)1);
    private static ThreadLocal<CvMat> x8x1 = CvMat.createThreadLocal((int)8, (int)1);
    private static ThreadLocal<CvMat> A3x3 = CvMat.createThreadLocal((int)3, (int)3);
    private static ThreadLocal<CvMat> b3x1 = CvMat.createThreadLocal((int)3, (int)1);
    private static ThreadLocal<CvMat> n3x1 = CvMat.createThreadLocal((int)3, (int)1);
    private static ThreadLocal<CvMat> H3x3 = CvMat.createThreadLocal((int)3, (int)3);
    private static ThreadLocal<CvMat> M3x2 = CvMat.createThreadLocal((int)3, (int)2);
    private static ThreadLocal<CvMat> S2x2 = CvMat.createThreadLocal((int)2, (int)2);
    private static ThreadLocal<CvMat> U3x2 = CvMat.createThreadLocal((int)3, (int)2);
    private static ThreadLocal<CvMat> V2x2 = CvMat.createThreadLocal((int)2, (int)2);
    private static ThreadLocal<CvMat> R13x3 = CvMat.createThreadLocal((int)3, (int)3);
    private static ThreadLocal<CvMat> R23x3 = CvMat.createThreadLocal((int)3, (int)3);
    private static ThreadLocal<CvMat> t13x1 = CvMat.createThreadLocal((int)3, (int)1);
    private static ThreadLocal<CvMat> t23x1 = CvMat.createThreadLocal((int)3, (int)1);
    private static ThreadLocal<CvMat> n13x1 = CvMat.createThreadLocal((int)3, (int)1);
    private static ThreadLocal<CvMat> n23x1 = CvMat.createThreadLocal((int)3, (int)1);
    private static ThreadLocal<CvMat> H13x3 = CvMat.createThreadLocal((int)3, (int)3);
    private static ThreadLocal<CvMat> H23x3 = CvMat.createThreadLocal((int)3, (int)3);
    private static ThreadLocal<CvMat> S3x3 = CvMat.createThreadLocal((int)3, (int)3);
    private static ThreadLocal<CvMat> U3x3 = CvMat.createThreadLocal((int)3, (int)3);
    private static ThreadLocal<CvMat> V3x3 = CvMat.createThreadLocal((int)3, (int)3);

    public static double distanceToLine(double x1, double y1, double x2, double y2, double x3, double y3) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double d2 = dx * dx + dy * dy;
        double u = ((x3 - x1) * dx + (y3 - y1) * dy) / d2;
        double x = x1 + u * dx;
        double y = y1 + u * dy;
        dx = x - x3;
        dy = y - y3;
        return dx * dx + dy * dy;
    }

    public static CvBox2D boundedRect(CvMat contour, CvBox2D box) {
        int contourLength = contour.length();
        CvMoments m = moments.get();
        opencv_imgproc.cvMoments((CvArr)contour, (CvMoments)m, (int)0);
        double inv_m00 = 1.0 / m.m00();
        double centerX = m.m10() * inv_m00;
        double centerY = m.m01() * inv_m00;
        float[] pts = new float[8];
        CvPoint2D32f center = box.center();
        CvSize2D32f size = box.size();
        center.put(centerX, centerY);
        opencv_imgproc.cvBoxPoints((CvBox2D)box, (float[])pts);
        float scale = Float.POSITIVE_INFINITY;
        for (int i = 0; i < 4; ++i) {
            double x1 = centerX;
            double y1 = centerY;
            double x2 = pts[2 * i];
            double y2 = pts[2 * i + 1];
            for (int j = 0; j < contourLength; ++j) {
                int k = (j + 1) % contourLength;
                double x3 = contour.get(2 * j);
                double y3 = contour.get(2 * j + 1);
                double x4 = contour.get(2 * k);
                double y4 = contour.get(2 * k + 1);
                double d = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
                double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / d;
                double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / d;
                if (!(ub >= 0.0) || !(ub <= 1.0) || !(ua >= 0.0) || !(ua < (double)scale)) continue;
                scale = (float)ua;
            }
        }
        size.width(scale * size.width()).height(scale * size.height());
        return box;
    }

    public static CvRect boundingRect(double[] contour, CvRect rect, int padX, int padY, int alignX, int alignY) {
        double minX = contour[0];
        double minY = contour[1];
        double maxX = contour[0];
        double maxY = contour[1];
        for (int i = 1; i < contour.length / 2; ++i) {
            double x = contour[2 * i];
            double y = contour[2 * i + 1];
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }
        int x = (int)Math.floor(Math.max((double)rect.x(), minX - (double)padX) / (double)alignX) * alignX;
        int y = (int)Math.floor(Math.max((double)rect.y(), minY - (double)padY) / (double)alignY) * alignY;
        int width = (int)Math.ceil(Math.min((double)rect.width(), maxX + (double)padX) / (double)alignX) * alignX - x;
        int height = (int)Math.ceil(Math.min((double)rect.height(), maxY + (double)padY) / (double)alignY) * alignY - y;
        return rect.x(x).y(y).width(Math.max(0, width)).height(Math.max(0, height));
    }

    public static CvMat getPerspectiveTransform(double[] src, double[] dst, CvMat map_matrix) {
        CvMat A = A8x8.get();
        CvMat b = b8x1.get();
        CvMat x = x8x1.get();
        for (int i = 0; i < 4; ++i) {
            A.put(i * 8 + 0, src[i * 2]);
            A.put((i + 4) * 8 + 3, src[i * 2]);
            A.put(i * 8 + 1, src[i * 2 + 1]);
            A.put((i + 4) * 8 + 4, src[i * 2 + 1]);
            A.put(i * 8 + 2, 1.0);
            A.put((i + 4) * 8 + 5, 1.0);
            A.put(i * 8 + 3, 0.0);
            A.put(i * 8 + 4, 0.0);
            A.put(i * 8 + 5, 0.0);
            A.put((i + 4) * 8 + 0, 0.0);
            A.put((i + 4) * 8 + 1, 0.0);
            A.put((i + 4) * 8 + 2, 0.0);
            A.put(i * 8 + 6, -src[i * 2] * dst[i * 2]);
            A.put(i * 8 + 7, -src[i * 2 + 1] * dst[i * 2]);
            A.put((i + 4) * 8 + 6, -src[i * 2] * dst[i * 2 + 1]);
            A.put((i + 4) * 8 + 7, -src[i * 2 + 1] * dst[i * 2 + 1]);
            b.put(i, dst[i * 2]);
            b.put(i + 4, dst[i * 2 + 1]);
        }
        opencv_core.cvSolve((CvArr)A, (CvArr)b, (CvArr)x, (int)0);
        map_matrix.put(x.get());
        map_matrix.put(8, 1.0);
        return map_matrix;
    }

    public static void perspectiveTransform(double[] src, double[] dst, CvMat map_matrix) {
        double[] mat = map_matrix.get();
        for (int j = 0; j < src.length; j += 2) {
            double x = src[j];
            double y = src[j + 1];
            double w = x * mat[6] + y * mat[7] + mat[8];
            if (Math.abs(w) > 1.1920928955078125E-7) {
                w = 1.0 / w;
                dst[j] = (x * mat[0] + y * mat[1] + mat[2]) * w;
                dst[j + 1] = (x * mat[3] + y * mat[4] + mat[5]) * w;
                continue;
            }
            dst[j + 1] = 0.0;
            dst[j] = 0.0;
        }
    }

    public static CvMat getPlaneParameters(double[] src, double[] dst, CvMat invSrcK, CvMat dstK, CvMat R, CvMat t, CvMat n) {
        CvMat A = A3x3.get();
        CvMat b = b3x1.get();
        double[] x = new double[6];
        double[] y = new double[6];
        JavaCV.perspectiveTransform(src, x, invSrcK);
        opencv_core.cvInvert((CvArr)dstK, (CvArr)A);
        JavaCV.perspectiveTransform(dst, y, A);
        for (int i = 0; i < 3; ++i) {
            A.put(i, 0, (t.get(2) * y[i * 2] - t.get(0)) * x[i * 2]);
            A.put(i, 1, (t.get(2) * y[i * 2] - t.get(0)) * x[i * 2 + 1]);
            A.put(i, 2, t.get(2) * y[i * 2] - t.get(0));
            b.put(i, (R.get(2, 0) * x[i * 2] + R.get(2, 1) * x[i * 2 + 1] + R.get(2, 2)) * y[i * 2] - (R.get(0, 0) * x[i * 2] + R.get(0, 1) * x[i * 2 + 1] + R.get(0, 2)));
        }
        opencv_core.cvSolve((CvArr)A, (CvArr)b, (CvArr)n, (int)0);
        return n;
    }

    public static CvMat getPerspectiveTransform(double[] src, double[] dst, CvMat invSrcK, CvMat dstK, CvMat R, CvMat t, CvMat H) {
        CvMat n = n3x1.get();
        JavaCV.getPlaneParameters(src, dst, invSrcK, dstK, R, t, n);
        opencv_core.cvGEMM((CvArr)t, (CvArr)n, (double)-1.0, (CvArr)R, (double)1.0, (CvArr)H, (int)2);
        opencv_core.cvMatMul((CvArr)dstK, (CvArr)H, (CvArr)H);
        opencv_core.cvMatMul((CvArr)H, (CvArr)invSrcK, (CvArr)H);
        return H;
    }

    public static void perspectiveTransform(double[] src, double[] dst, CvMat invSrcK, CvMat dstK, CvMat R, CvMat t, CvMat n, boolean invert) {
        CvMat H = H3x3.get();
        opencv_core.cvGEMM((CvArr)t, (CvArr)n, (double)-1.0, (CvArr)R, (double)1.0, (CvArr)H, (int)2);
        opencv_core.cvMatMul((CvArr)dstK, (CvArr)H, (CvArr)H);
        opencv_core.cvMatMul((CvArr)H, (CvArr)invSrcK, (CvArr)H);
        if (invert) {
            opencv_core.cvInvert((CvArr)H, (CvArr)H);
        }
        JavaCV.perspectiveTransform(src, dst, H);
    }

    public static void HtoRt(CvMat H, CvMat R, CvMat t) {
        CvMat M = M3x2.get();
        CvMat S = S2x2.get();
        CvMat U = U3x2.get();
        CvMat V = V2x2.get();
        M.put(new double[]{H.get(0), H.get(1), H.get(3), H.get(4), H.get(6), H.get(7)});
        opencv_core.cvSVD((CvArr)M, (CvArr)S, (CvArr)U, (CvArr)V, (int)4);
        double lambda = S.get(3);
        t.put(new double[]{H.get(2) / lambda, H.get(5) / lambda, H.get(8) / lambda});
        opencv_core.cvMatMul((CvArr)U, (CvArr)V, (CvArr)M);
        R.put(new double[]{M.get(0), M.get(1), M.get(2) * M.get(5) - M.get(3) * M.get(4), M.get(2), M.get(3), M.get(1) * M.get(4) - M.get(0) * M.get(5), M.get(4), M.get(5), M.get(0) * M.get(3) - M.get(1) * M.get(2)});
    }

    public static double HnToRt(CvMat H, CvMat n, CvMat R, CvMat t) {
        double err;
        CvMat S = S3x3.get();
        CvMat U = U3x3.get();
        CvMat V = V3x3.get();
        opencv_core.cvSVD((CvArr)H, (CvArr)S, (CvArr)U, (CvArr)V, (int)0);
        CvMat R1 = R13x3.get();
        CvMat R2 = R23x3.get();
        CvMat t1 = t13x1.get();
        CvMat t2 = t23x1.get();
        CvMat n1 = n13x1.get();
        CvMat n2 = n23x1.get();
        CvMat H1 = H13x3.get();
        CvMat H2 = H23x3.get();
        double zeta = JavaCV.homogToRt(S, U, V, R1, t1, n1, R2, t2, n2);
        opencv_core.cvGEMM((CvArr)R1, (CvArr)H, (double)(1.0 / S.get(4)), null, (double)0.0, (CvArr)H1, (int)1);
        opencv_core.cvGEMM((CvArr)R2, (CvArr)H, (double)(1.0 / S.get(4)), null, (double)0.0, (CvArr)H2, (int)1);
        H1.put(0, H1.get(0) - 1.0);
        H1.put(4, H1.get(4) - 1.0);
        H1.put(8, H1.get(8) - 1.0);
        H2.put(0, H2.get(0) - 1.0);
        H2.put(4, H2.get(4) - 1.0);
        H2.put(8, H2.get(8) - 1.0);
        double d = Math.abs(n.get(0)) + Math.abs(n.get(1)) + Math.abs(n.get(2));
        double[] s = new double[]{-Math.signum(n.get(0)), -Math.signum(n.get(1)), -Math.signum(n.get(2))};
        t1.put(new double[]{0.0, 0.0, 0.0});
        t2.put(new double[]{0.0, 0.0, 0.0});
        for (int i = 0; i < 3; ++i) {
            t1.put(0, t1.get(0) + s[i] * H1.get(i) / d);
            t1.put(1, t1.get(1) + s[i] * H1.get(i + 3) / d);
            t1.put(2, t1.get(2) + s[i] * H1.get(i + 6) / d);
            t2.put(0, t2.get(0) + s[i] * H2.get(i) / d);
            t2.put(1, t2.get(1) + s[i] * H2.get(i + 3) / d);
            t2.put(2, t2.get(2) + s[i] * H2.get(i + 6) / d);
        }
        opencv_core.cvGEMM((CvArr)t1, (CvArr)n, (double)1.0, (CvArr)H1, (double)1.0, (CvArr)H1, (int)2);
        opencv_core.cvGEMM((CvArr)t2, (CvArr)n, (double)1.0, (CvArr)H2, (double)1.0, (CvArr)H2, (int)2);
        double err1 = opencv_core.cvNorm((CvArr)H1);
        double err2 = opencv_core.cvNorm((CvArr)H2);
        if (err1 < err2) {
            if (R != null) {
                R.put(R1);
            }
            if (t != null) {
                t.put(t1);
            }
            err = err1;
        } else {
            if (R != null) {
                R.put(R2);
            }
            if (t != null) {
                t.put(t2);
            }
            err = err2;
        }
        return err;
    }

    public static double homogToRt(CvMat H, CvMat R1, CvMat t1, CvMat n1, CvMat R2, CvMat t2, CvMat n2) {
        CvMat S = S3x3.get();
        CvMat U = U3x3.get();
        CvMat V = V3x3.get();
        opencv_core.cvSVD((CvArr)H, (CvArr)S, (CvArr)U, (CvArr)V, (int)0);
        double zeta = JavaCV.homogToRt(S, U, V, R1, t1, n1, R2, t2, n2);
        return zeta;
    }

    public static double homogToRt(CvMat S, CvMat U, CvMat V, CvMat R1, CvMat t1, CvMat n1, CvMat R2, CvMat t2, CvMat n2) {
        double s1 = S.get(0) / S.get(4);
        double s3 = S.get(8) / S.get(4);
        double zeta = s1 - s3;
        double a1 = Math.sqrt(1.0 - s3 * s3);
        double b1 = Math.sqrt(s1 * s1 - 1.0);
        double[] ab = JavaCV.unitize(a1, b1);
        double[] cd = JavaCV.unitize(1.0 + s1 * s3, a1 * b1);
        double[] ef = JavaCV.unitize(-ab[1] / s1, -ab[0] / s3);
        R1.put(new double[]{cd[0], 0.0, cd[1], 0.0, 1.0, 0.0, -cd[1], 0.0, cd[0]});
        opencv_core.cvGEMM((CvArr)U, (CvArr)R1, (double)1.0, null, (double)0.0, (CvArr)R1, (int)0);
        opencv_core.cvGEMM((CvArr)R1, (CvArr)V, (double)1.0, null, (double)0.0, (CvArr)R1, (int)2);
        R2.put(new double[]{cd[0], 0.0, -cd[1], 0.0, 1.0, 0.0, cd[1], 0.0, cd[0]});
        opencv_core.cvGEMM((CvArr)U, (CvArr)R2, (double)1.0, null, (double)0.0, (CvArr)R2, (int)0);
        opencv_core.cvGEMM((CvArr)R2, (CvArr)V, (double)1.0, null, (double)0.0, (CvArr)R2, (int)2);
        double[] v1 = new double[]{V.get(0), V.get(3), V.get(6)};
        double[] v3 = new double[]{V.get(2), V.get(5), V.get(8)};
        double sign1 = 1.0;
        double sign2 = 1.0;
        for (int i = 2; i >= 0; --i) {
            n1.put(i, sign1 * (ab[1] * v1[i] - ab[0] * v3[i]));
            n2.put(i, sign2 * (ab[1] * v1[i] + ab[0] * v3[i]));
            t1.put(i, sign1 * (ef[0] * v1[i] + ef[1] * v3[i]));
            t2.put(i, sign2 * (ef[0] * v1[i] - ef[1] * v3[i]));
            if (i != 2) continue;
            if (n1.get(2) < 0.0) {
                n1.put(2, -n1.get(2));
                t1.put(2, -t1.get(2));
                sign1 = -1.0;
            }
            if (!(n2.get(2) < 0.0)) continue;
            n2.put(2, -n2.get(2));
            t2.put(2, -t2.get(2));
            sign2 = -1.0;
        }
        return zeta;
    }

    public static double[] unitize(double a, double b) {
        double norm = Math.sqrt(a * a + b * b);
        if (norm > 1.1920928955078125E-7) {
            a /= norm;
            b /= norm;
        }
        return new double[]{a, b};
    }

    public static void adaptiveThreshold(IplImage srcImage, IplImage sumImage, IplImage sqSumImage, IplImage dstImage, final boolean invert, final int windowMax, final int windowMin, double varMultiplier, final double k) {
        final int w = srcImage.width();
        final int h = srcImage.height();
        int srcChannels = srcImage.nChannels();
        final int srcDepth = srcImage.depth();
        int dstDepth = dstImage.depth();
        if (srcChannels > 1 && dstDepth == 8) {
            opencv_imgproc.cvCvtColor((CvArr)srcImage, (CvArr)dstImage, (int)(srcChannels == 4 ? 11 : 6));
            srcImage = dstImage;
        }
        final ByteBuffer srcBuf = srcImage.getByteBuffer();
        final ByteBuffer dstBuf = dstImage.getByteBuffer();
        final DoubleBuffer sumBuf = sumImage.getDoubleBuffer();
        final DoubleBuffer sqSumBuf = sqSumImage.getDoubleBuffer();
        final int srcStep = srcImage.widthStep();
        final int dstStep = dstImage.widthStep();
        final int sumStep = sumImage.widthStep();
        final int sqSumStep = sqSumImage.widthStep();
        opencv_imgproc.cvIntegral((CvArr)srcImage, (CvArr)sumImage, (CvArr)sqSumImage, null);
        double totalMean = sumBuf.get((h - 1) * sumStep / 8 + (w - 1)) - sumBuf.get((h - 1) * sumStep / 8) - sumBuf.get(w - 1) + sumBuf.get(0);
        double totalSqMean = sqSumBuf.get((h - 1) * sqSumStep / 8 + (w - 1)) - sqSumBuf.get((h - 1) * sqSumStep / 8) - sqSumBuf.get(w - 1) + sqSumBuf.get(0);
        double totalVar = (totalSqMean /= (double)(w * h)) - (totalMean /= (double)(w * h)) * totalMean;
        final double targetVar = totalVar * varMultiplier;
        Parallel.loop(0, h, new Parallel.Looper(){

            @Override
            public void loop(int from, int to, int looperID) {
                for (int y = from; y < to; ++y) {
                    for (int x = 0; x < w; ++x) {
                        double threshold;
                        double var = 0.0;
                        double mean = 0.0;
                        double sqMean = 0.0;
                        int upperLimit = windowMax;
                        int lowerLimit = windowMin;
                        int window = upperLimit;
                        while (upperLimit - lowerLimit > 2) {
                            int x1 = Math.max(x - window / 2, 0);
                            int x2 = Math.min(x + window / 2 + 1, w);
                            int y1 = Math.max(y - window / 2, 0);
                            int y2 = Math.min(y + window / 2 + 1, h);
                            mean = sumBuf.get(y2 * sumStep / 8 + x2) - sumBuf.get(y2 * sumStep / 8 + x1) - sumBuf.get(y1 * sumStep / 8 + x2) + sumBuf.get(y1 * sumStep / 8 + x1);
                            sqMean = sqSumBuf.get(y2 * sqSumStep / 8 + x2) - sqSumBuf.get(y2 * sqSumStep / 8 + x1) - sqSumBuf.get(y1 * sqSumStep / 8 + x2) + sqSumBuf.get(y1 * sqSumStep / 8 + x1);
                            var = (sqMean /= (double)(window * window)) - (mean /= (double)(window * window)) * mean;
                            if (window == upperLimit && var < targetVar) break;
                            if (var > targetVar) {
                                upperLimit = window;
                            } else {
                                lowerLimit = window;
                            }
                            window = lowerLimit + (upperLimit - lowerLimit) / 2;
                            window = window / 2 * 2 + 1;
                        }
                        double value = 0.0;
                        if (srcDepth == 8) {
                            value = srcBuf.get(y * srcStep + x) & 0xFF;
                        } else if (srcDepth == 32) {
                            value = srcBuf.getFloat(y * srcStep + 4 * x);
                        } else if (srcDepth == 64) {
                            value = srcBuf.getDouble(y * srcStep + 8 * x);
                        } else assert (false);
                        if (invert) {
                            threshold = 255.0 - (255.0 - mean) * k;
                            dstBuf.put(y * dstStep + x, value < threshold ? (byte)-1 : 0);
                            continue;
                        }
                        threshold = mean * k;
                        dstBuf.put(y * dstStep + x, value > threshold ? (byte)-1 : 0);
                    }
                }
            }
        });
    }

    public static void hysteresisThreshold(IplImage srcImage, IplImage dstImage, double highThresh, double lowThresh, double maxValue) {
        byte prev;
        int highThreshold = (int)Math.round(highThresh);
        int lowThreshold = (int)Math.round(lowThresh);
        byte lowValue = 0;
        byte medValue = (byte)Math.round(maxValue / 2.0);
        byte highValue = (byte)Math.round(maxValue);
        int height = srcImage.height();
        int width = srcImage.width();
        ByteBuffer srcData = srcImage.getByteBuffer();
        ByteBuffer dstData = dstImage.getByteBuffer();
        int srcStep = srcImage.widthStep();
        int dstStep = dstImage.widthStep();
        int srcIndex = 0;
        int dstIndex = 0;
        int i = 0;
        int in = srcData.get(srcIndex + i) & 0xFF;
        if (in >= highThreshold) {
            dstData.put(dstIndex + i, highValue);
        } else if (in < lowThreshold) {
            dstData.put(dstIndex + i, lowValue);
        } else {
            dstData.put(dstIndex + i, medValue);
        }
        for (i = 1; i < width - 1; ++i) {
            in = srcData.get(srcIndex + i) & 0xFF;
            if (in >= highThreshold) {
                dstData.put(dstIndex + i, highValue);
                continue;
            }
            if (in < lowThreshold) {
                dstData.put(dstIndex + i, lowValue);
                continue;
            }
            prev = dstData.get(dstIndex + i - 1);
            if (prev == highValue) {
                dstData.put(dstIndex + i, highValue);
                continue;
            }
            dstData.put(dstIndex + i, medValue);
        }
        i = width - 1;
        in = srcData.get(srcIndex + i) & 0xFF;
        if (in >= highThreshold) {
            dstData.put(dstIndex + i, highValue);
        } else if (in < lowThreshold) {
            dstData.put(dstIndex + i, lowValue);
        } else {
            prev = dstData.get(dstIndex + i - 1);
            if (prev == highValue) {
                dstData.put(dstIndex + i, highValue);
            } else {
                dstData.put(dstIndex + i, medValue);
            }
        }
        while (true) {
            byte prev3;
            byte prev2;
            byte prev1;
            int n = --height;
            --height;
            if (n <= 0) break;
            dstIndex += dstStep;
            i = 0;
            in = srcData.get((srcIndex += srcStep) + i) & 0xFF;
            if (in >= highThreshold) {
                dstData.put(dstIndex + i, highValue);
            } else if (in < lowThreshold) {
                dstData.put(dstIndex + i, lowValue);
            } else {
                prev1 = dstData.get(dstIndex + i - dstStep);
                prev2 = dstData.get(dstIndex + i - dstStep + 1);
                if (prev1 == highValue || prev2 == highValue) {
                    dstData.put(dstIndex + i, highValue);
                } else {
                    dstData.put(dstIndex + i, medValue);
                }
            }
            for (i = 1; i < width - 1; ++i) {
                in = srcData.get(srcIndex + i) & 0xFF;
                if (in >= highThreshold) {
                    dstData.put(dstIndex + i, highValue);
                    continue;
                }
                if (in < lowThreshold) {
                    dstData.put(dstIndex + i, lowValue);
                    continue;
                }
                prev1 = dstData.get(dstIndex + i - 1);
                prev2 = dstData.get(dstIndex + i - dstStep - 1);
                prev3 = dstData.get(dstIndex + i - dstStep);
                byte prev4 = dstData.get(dstIndex + i - dstStep + 1);
                if (prev1 == highValue || prev2 == highValue || prev3 == highValue || prev4 == highValue) {
                    dstData.put(dstIndex + i, highValue);
                    continue;
                }
                dstData.put(dstIndex + i, medValue);
            }
            i = width - 1;
            in = srcData.get(srcIndex + i) & 0xFF;
            if (in >= highThreshold) {
                dstData.put(dstIndex + i, highValue);
                continue;
            }
            if (in < lowThreshold) {
                dstData.put(dstIndex + i, lowValue);
                continue;
            }
            prev1 = dstData.get(dstIndex + i - 1);
            prev2 = dstData.get(dstIndex + i - dstStep - 1);
            prev3 = dstData.get(dstIndex + i - dstStep);
            if (prev1 == highValue || prev2 == highValue || prev3 == highValue) {
                dstData.put(dstIndex + i, highValue);
                continue;
            }
            dstData.put(dstIndex + i, medValue);
        }
        height = srcImage.height();
        dstIndex = (height - 1) * dstStep;
        if (dstData.get(dstIndex + (i = (width = srcImage.width()) - 1)) == medValue) {
            dstData.put(dstIndex + i, lowValue);
        }
        for (i = width - 2; i > 0; --i) {
            if (dstData.get(dstIndex + i) != medValue) continue;
            if (dstData.get(dstIndex + i + 1) == highValue) {
                dstData.put(dstIndex + i, highValue);
                continue;
            }
            dstData.put(dstIndex + i, lowValue);
        }
        i = 0;
        if (dstData.get(dstIndex + i) == medValue) {
            if (dstData.get(dstIndex + i + 1) == highValue) {
                dstData.put(dstIndex + i, highValue);
            } else {
                dstData.put(dstIndex + i, lowValue);
            }
        }
        while (true) {
            int n = --height;
            --height;
            if (n <= 0) break;
            i = width - 1;
            if (dstData.get((dstIndex -= dstStep) + i) == medValue) {
                if (dstData.get(dstIndex + i + dstStep) == highValue || dstData.get(dstIndex + i + dstStep - 1) == highValue) {
                    dstData.put(dstIndex + i, highValue);
                } else {
                    dstData.put(dstIndex + i, lowValue);
                }
            }
            for (i = width - 2; i > 0; --i) {
                if (dstData.get(dstIndex + i) != medValue) continue;
                if (dstData.get(dstIndex + i + 1) == highValue || dstData.get(dstIndex + i + dstStep + 1) == highValue || dstData.get(dstIndex + i + dstStep) == highValue || dstData.get(dstIndex + i + dstStep - 1) == highValue) {
                    dstData.put(dstIndex + i, highValue);
                    continue;
                }
                dstData.put(dstIndex + i, lowValue);
            }
            i = 0;
            if (dstData.get(dstIndex + i) != medValue) continue;
            if (dstData.get(dstIndex + i + 1) == highValue || dstData.get(dstIndex + i + dstStep + 1) == highValue || dstData.get(dstIndex + i + dstStep) == highValue) {
                dstData.put(dstIndex + i, highValue);
                continue;
            }
            dstData.put(dstIndex + i, lowValue);
        }
    }

    public static void clamp(IplImage src, IplImage dst, double min, double max) {
        switch (src.depth()) {
            case 8: {
                ByteBuffer sb = src.getByteBuffer();
                ByteBuffer db = dst.getByteBuffer();
                for (int i = 0; i < sb.capacity(); ++i) {
                    db.put(i, (byte)Math.max(Math.min((double)(sb.get(i) & 0xFF), max), min));
                }
                break;
            }
            case 16: {
                ShortBuffer sb = src.getShortBuffer();
                ShortBuffer db = dst.getShortBuffer();
                for (int i = 0; i < sb.capacity(); ++i) {
                    db.put(i, (short)Math.max(Math.min((double)(sb.get(i) & 0xFFFF), max), min));
                }
                break;
            }
            case 32: {
                FloatBuffer sb = src.getFloatBuffer();
                FloatBuffer db = dst.getFloatBuffer();
                for (int i = 0; i < sb.capacity(); ++i) {
                    db.put(i, (float)Math.max(Math.min((double)sb.get(i), max), min));
                }
                break;
            }
            case -2147483640: {
                ByteBuffer sb = src.getByteBuffer();
                ByteBuffer db = dst.getByteBuffer();
                for (int i = 0; i < sb.capacity(); ++i) {
                    db.put(i, (byte)Math.max(Math.min((double)sb.get(i), max), min));
                }
                break;
            }
            case -2147483632: {
                ShortBuffer sb = src.getShortBuffer();
                ShortBuffer db = dst.getShortBuffer();
                for (int i = 0; i < sb.capacity(); ++i) {
                    db.put(i, (short)Math.max(Math.min((double)sb.get(i), max), min));
                }
                break;
            }
            case -2147483616: {
                IntBuffer sb = src.getIntBuffer();
                IntBuffer db = dst.getIntBuffer();
                for (int i = 0; i < sb.capacity(); ++i) {
                    db.put(i, (int)Math.max(Math.min((double)sb.get(i), max), min));
                }
                break;
            }
            case 64: {
                DoubleBuffer sb = src.getDoubleBuffer();
                DoubleBuffer db = dst.getDoubleBuffer();
                for (int i = 0; i < sb.capacity(); ++i) {
                    db.put(i, Math.max(Math.min(sb.get(i), max), min));
                }
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
    }

    public static double norm(double[] v) {
        return JavaCV.norm(v, 2.0);
    }

    public static double norm(double[] v, double p) {
        double norm = 0.0;
        if (p == 1.0) {
            for (double e : v) {
                norm += Math.abs(e);
            }
        } else if (p == 2.0) {
            for (double e : v) {
                norm += e * e;
            }
            norm = Math.sqrt(norm);
        } else if (p == Double.POSITIVE_INFINITY) {
            for (double e : v) {
                if (!((e = Math.abs(e)) > norm)) continue;
                norm = e;
            }
        } else if (p == Double.NEGATIVE_INFINITY) {
            norm = Double.MAX_VALUE;
            for (double e : v) {
                if (!((e = Math.abs(e)) < norm)) continue;
                norm = e;
            }
        } else {
            for (double e : v) {
                norm += Math.pow(Math.abs(e), p);
            }
            norm = Math.pow(norm, 1.0 / p);
        }
        return norm;
    }

    public static double norm(CvMat A) {
        return JavaCV.norm(A, 2.0);
    }

    public static double norm(CvMat A, double p) {
        return JavaCV.norm(A, p, null);
    }

    public static double norm(CvMat A, double p, CvMat W) {
        double norm = -1.0;
        if (p == 1.0) {
            int cols = A.cols();
            int rows = A.rows();
            for (int j = 0; j < cols; ++j) {
                double n = 0.0;
                for (int i = 0; i < rows; ++i) {
                    n += Math.abs(A.get(i, j));
                }
                norm = Math.max(n, norm);
            }
        } else if (p == 2.0) {
            int size = Math.min(A.rows(), A.cols());
            if (W == null || W.rows() != size || W.cols() != 1) {
                W = CvMat.create((int)size, (int)1);
            }
            opencv_core.cvSVD((CvArr)A, (CvArr)W, null, null, (int)0);
            norm = W.get(0);
        } else if (p == Double.POSITIVE_INFINITY) {
            int rows = A.rows();
            int cols = A.cols();
            for (int i = 0; i < rows; ++i) {
                double n = 0.0;
                for (int j = 0; j < cols; ++j) {
                    n += Math.abs(A.get(i, j));
                }
                norm = Math.max(n, norm);
            }
        } else assert (false);
        return norm;
    }

    public static double cond(CvMat A) {
        return JavaCV.cond(A, 2.0);
    }

    public static double cond(CvMat A, double p) {
        return JavaCV.cond(A, p, null);
    }

    public static double cond(CvMat A, double p, CvMat W) {
        double cond = -1.0;
        if (p == 2.0) {
            int size = Math.min(A.rows(), A.cols());
            if (W == null || W.rows() != size || W.cols() != 1) {
                W = CvMat.create((int)size, (int)1);
            }
            opencv_core.cvSVD((CvArr)A, (CvArr)W, null, null, (int)0);
            cond = W.get(0) / W.get(W.length() - 1);
        } else {
            int rows = A.rows();
            int cols = A.cols();
            if (W == null || W.rows() != rows || W.cols() != cols) {
                W = CvMat.create((int)rows, (int)cols);
            }
            CvMat Ainv = W;
            opencv_core.cvInvert((CvArr)A, (CvArr)Ainv);
            cond = JavaCV.norm(A, p) * JavaCV.norm(Ainv, p);
        }
        return cond;
    }

    public static double median(double[] doubles) {
        double[] sorted = (double[])doubles.clone();
        Arrays.sort(sorted);
        if (doubles.length % 2 == 0) {
            return (sorted[doubles.length / 2 - 1] + sorted[doubles.length / 2]) / 2.0;
        }
        return sorted[doubles.length / 2];
    }

    public static <T> T median(T[] objects) {
        Object[] sorted = (Object[])objects.clone();
        Arrays.sort(sorted);
        return (T)sorted[sorted.length / 2];
    }

    public static void fractalTriangleWave(double[] line, int i, int j, double a) {
        JavaCV.fractalTriangleWave(line, i, j, a, -1);
    }

    public static void fractalTriangleWave(double[] line, int i, int j, double a, int roughness) {
        int m = (j - i) / 2 + i;
        if (i == j || i == m) {
            return;
        }
        line[m] = (line[i] + line[j]) / 2.0 + a;
        if (roughness > 0 && line.length > roughness * (j - i)) {
            JavaCV.fractalTriangleWave(line, i, m, 0.0, roughness);
            JavaCV.fractalTriangleWave(line, m, j, 0.0, roughness);
        } else {
            JavaCV.fractalTriangleWave(line, i, m, a / 1.4142135623730951, roughness);
            JavaCV.fractalTriangleWave(line, m, j, -a / 1.4142135623730951, roughness);
        }
    }

    public static void fractalTriangleWave(IplImage image, CvMat H) {
        JavaCV.fractalTriangleWave(image, H, -1);
    }

    public static void fractalTriangleWave(IplImage image, CvMat H, int roughness) {
        assert (image.depth() == 32);
        double[] line = new double[image.width()];
        JavaCV.fractalTriangleWave(line, 0, line.length / 2, 1.0, roughness);
        JavaCV.fractalTriangleWave(line, line.length / 2, line.length - 1, -1.0, roughness);
        double[] minMax = new double[]{Double.MAX_VALUE, Double.MIN_VALUE};
        int height = image.height();
        int width = image.width();
        int channels = image.nChannels();
        int step = image.widthStep();
        int start = 0;
        if (image.roi() != null) {
            height = image.roi().height();
            width = image.roi().width();
            start = image.roi().yOffset() * step / 4 + image.roi().xOffset() * channels;
        }
        FloatBuffer fb = image.getFloatBuffer(start);
        double[] h = H == null ? null : H.get();
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                for (int z = 0; z < channels; ++z) {
                    double sum = 0.0;
                    if (h == null) {
                        sum += line[x];
                    } else {
                        double x2;
                        for (x2 = (h[0] * (double)x + h[1] * (double)y + h[2]) / (h[6] * (double)x + h[7] * (double)y + h[8]); x2 < 0.0; x2 += (double)line.length) {
                        }
                        int xi2 = (int)x2;
                        double xn = x2 - (double)xi2;
                        sum += line[xi2 % line.length] * (1.0 - xn) + line[(xi2 + 1) % line.length] * xn;
                    }
                    minMax[0] = Math.min(minMax[0], sum);
                    minMax[1] = Math.max(minMax[1], sum);
                    fb.put(y * step / 4 + x * channels + z, (float)sum);
                }
            }
        }
        opencv_core.cvConvertScale((CvArr)image, (CvArr)image, (double)(1.0 / (minMax[1] - minMax[0])), (double)(-minMax[0] / (minMax[1] - minMax[0])));
    }

    public static void main(String[] args) {
        String version = JavaCV.class.getPackage().getImplementationVersion();
        if (version == null) {
            version = "unknown";
        }
        System.out.println("JavaCV version " + version + "\nCopyright (C) 2009-2018 Samuel Audet <samuel.audet@gmail.com>\nProject site: https://github.com/bytedeco/javacv");
        System.exit(0);
    }
}

