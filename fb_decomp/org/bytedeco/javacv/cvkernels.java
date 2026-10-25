/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.cvkernels
 *  org.bytedeco.opencv.cvkernels$KernelData
 *  org.bytedeco.opencv.opencv_core.CvRect
 *  org.bytedeco.opencv.opencv_core.CvScalar
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacv.Parallel;
import org.bytedeco.opencv.cvkernels;
import org.bytedeco.opencv.opencv_core.CvRect;
import org.bytedeco.opencv.opencv_core.CvScalar;
import org.bytedeco.opencv.opencv_core.IplImage;

public class cvkernels
extends org.bytedeco.opencv.cvkernels {
    private static ThreadLocal<ParallelData[]> parallelData = new ThreadLocal<ParallelData[]>(){

        @Override
        protected ParallelData[] initialValue() {
            ParallelData[] pd = new ParallelData[Parallel.getNumThreads()];
            for (int i = 0; i < pd.length; ++i) {
                pd[i] = new ParallelData();
            }
            return pd;
        }
    };

    public static void multiWarpColorTransform(cvkernels.KernelData data, CvRect roi, final CvScalar fillColor) {
        int h;
        int w;
        int y;
        int x;
        final int size = (int)data.capacity();
        final ParallelData[] pd = parallelData.get();
        for (int i = 0; i < pd.length; ++i) {
            cvkernels.KernelData d;
            int j;
            if (pd[i].data == null || pd[i].data.capacity() < (long)size) {
                pd[i].data = new cvkernels.KernelData((long)size);
                for (j = 0; j < size; ++j) {
                    d = pd[i].data.position((long)j);
                    data.position((long)j);
                    if (data.dstDstDot() == null) continue;
                    d.dstDstDot(ByteBuffer.allocateDirect(data.dstDstDot().capacity() * 8).order(ByteOrder.nativeOrder()).asDoubleBuffer());
                }
            }
            for (j = 0; j < size; ++j) {
                d = pd[i].data.position((long)j);
                d.put((Pointer)data.position((long)j));
                d.dstDstDot(d.dstDstDot());
            }
        }
        IplImage img = data.position(0L).srcImg();
        final int depth = img.depth();
        if (roi != null) {
            x = roi.x();
            y = roi.y();
            w = roi.width();
            h = roi.height();
        } else {
            x = 0;
            y = 0;
            w = img.width();
            h = img.height();
        }
        Parallel.loop(y, y + h, pd.length, new Parallel.Looper(){

            @Override
            public void loop(int from, int to, int looperID) {
                CvRect r = pd[looperID].roi.x(x).y(from).width(w).height(to - from);
                if (depth == 32) {
                    org.bytedeco.opencv.cvkernels.multiWarpColorTransform32F((cvkernels.KernelData)pd[looperID].data.position(0L), (int)size, (CvRect)r, (CvScalar)fillColor);
                } else if (depth == 8) {
                    org.bytedeco.opencv.cvkernels.multiWarpColorTransform8U((cvkernels.KernelData)pd[looperID].data.position(0L), (int)size, (CvRect)r, (CvScalar)fillColor);
                } else assert (false);
            }
        });
        for (int i = 0; i < size; ++i) {
            int dstCount = 0;
            int dstCountZero = 0;
            int dstCountOutlier = 0;
            double srcDstDot = 0.0;
            double[] dstDstDot = null;
            if (data.dstDstDot() != null) {
                dstDstDot = new double[data.dstDstDot().capacity()];
            }
            for (int j = 0; j < pd.length; ++j) {
                cvkernels.KernelData d = pd[j].data.position((long)i);
                dstCount += d.dstCount();
                dstCountZero += d.dstCountZero();
                dstCountOutlier += d.dstCountOutlier();
                srcDstDot += d.srcDstDot();
                if (dstDstDot == null || d.dstDstDot() == null) continue;
                for (int k = 0; k < dstDstDot.length; ++k) {
                    int n = k;
                    dstDstDot[n] = dstDstDot[n] + d.dstDstDot().get(k);
                }
            }
            data.position((long)i);
            data.dstCount(dstCount);
            data.dstCountZero(dstCountZero);
            data.dstCountOutlier(dstCountOutlier);
            data.srcDstDot(srcDstDot);
            if (dstDstDot == null || data.dstDstDot() == null) continue;
            data.dstDstDot().position(0);
            data.dstDstDot().put(dstDstDot);
        }
    }

    private static class ParallelData {
        cvkernels.KernelData data = null;
        CvRect roi = new CvRect();

        private ParallelData() {
        }
    }
}

