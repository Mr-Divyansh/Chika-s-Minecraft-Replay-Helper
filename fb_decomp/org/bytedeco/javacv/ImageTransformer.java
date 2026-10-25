/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.opencv_core.CvMat
 *  org.bytedeco.opencv.opencv_core.CvRect
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import org.bytedeco.opencv.opencv_core.CvMat;
import org.bytedeco.opencv.opencv_core.CvRect;
import org.bytedeco.opencv.opencv_core.IplImage;

public interface ImageTransformer {
    public Parameters createParameters();

    public void transform(Data[] var1, CvRect var2, Parameters[] var3, boolean[] var4);

    public void transform(CvMat var1, CvMat var2, Parameters var3, boolean var4);

    public static interface Parameters
    extends Cloneable {
        public int size();

        public double[] get();

        public double get(int var1);

        public void set(double ... var1);

        public void set(int var1, double var2);

        public void set(Parameters var1);

        public void reset(boolean var1);

        public double getConstraintError();

        public void compose(Parameters var1, boolean var2, Parameters var3, boolean var4);

        public boolean preoptimize();

        public double[] getSubspace();

        public void setSubspace(double ... var1);

        public Parameters clone();
    }

    public static class Data {
        public IplImage srcImg;
        public IplImage subImg;
        public IplImage srcDotImg;
        public IplImage mask;
        public double zeroThreshold;
        public double outlierThreshold;
        public int pyramidLevel;
        public IplImage transImg;
        public IplImage dstImg;
        public int dstCount;
        public int dstCountZero;
        public int dstCountOutlier;
        public double srcDstDot;
        public DoubleBuffer dstDstDot;

        public Data() {
            this(null, null, null, null, 0.0, 0.0, 0, null, null, 0);
        }

        public Data(IplImage srcImg, IplImage subImg, IplImage srcDotImg, IplImage mask, double zeroThreshold, double outlierThreshold, int pyramidLevel, IplImage transImg, IplImage dstImg, int dstDstDotLength) {
            this.srcImg = srcImg;
            this.subImg = subImg;
            this.srcDotImg = srcDotImg;
            this.mask = mask;
            this.zeroThreshold = zeroThreshold;
            this.outlierThreshold = outlierThreshold;
            this.pyramidLevel = pyramidLevel;
            this.transImg = transImg;
            this.dstImg = dstImg;
            this.dstDstDot = dstDstDotLength == 0 ? null : ByteBuffer.allocateDirect(dstDstDotLength * 8).order(ByteOrder.nativeOrder()).asDoubleBuffer();
        }
    }
}

