/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.cvkernels$KernelData
 *  org.bytedeco.opencv.global.opencv_calib3d
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.CvMat
 *  org.bytedeco.opencv.opencv_core.CvRect
 *  org.bytedeco.opencv.opencv_core.CvScalar
 *  org.bytedeco.opencv.opencv_core.IplImage
 *  org.bytedeco.opencv.opencv_core.Mat
 */
package org.bytedeco.javacv;

import org.bytedeco.javacv.ImageTransformer;
import org.bytedeco.javacv.JavaCV;
import org.bytedeco.javacv.ProjectiveDevice;
import org.bytedeco.javacv.cvkernels;
import org.bytedeco.opencv.cvkernels;
import org.bytedeco.opencv.global.opencv_calib3d;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.CvMat;
import org.bytedeco.opencv.opencv_core.CvRect;
import org.bytedeco.opencv.opencv_core.CvScalar;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;

public class ProjectiveTransformer
implements ImageTransformer {
    protected static ThreadLocal<CvMat> H3x3 = CvMat.createThreadLocal((int)3, (int)3);
    protected static ThreadLocal<CvMat> pts4x1 = CvMat.createThreadLocal((int)4, (int)1, (int)6, (int)2);
    protected CvMat K1 = null;
    protected CvMat K2 = null;
    protected CvMat invK1 = null;
    protected CvMat invK2 = null;
    protected CvMat R = null;
    protected CvMat t = null;
    protected CvMat n = null;
    protected double[] referencePoints1 = null;
    protected double[] referencePoints2 = null;
    protected CvScalar fillColor = opencv_core.cvScalar((double)0.0, (double)0.0, (double)0.0, (double)1.0);
    protected cvkernels.KernelData kernelData = null;
    protected CvMat[] H = null;

    public ProjectiveTransformer() {
        this(null, null, null, null, null, new double[0], null);
    }

    public ProjectiveTransformer(double[] referencePoints) {
        this(null, null, null, null, null, referencePoints, null);
    }

    public ProjectiveTransformer(ProjectiveDevice d1, ProjectiveDevice d2, CvMat n, double[] referencePoints1, double[] referencePoints2) {
        this(d1.cameraMatrix, d2.cameraMatrix, d2.R, d2.T, n, referencePoints1, referencePoints2);
    }

    public ProjectiveTransformer(CvMat K1, CvMat K2, CvMat R, CvMat t, CvMat n, double[] referencePoints1, double[] referencePoints2) {
        this.K1 = K1 == null ? null : K1.clone();
        this.K2 = K2 == null ? null : K2.clone();
        this.invK1 = K1 == null ? null : K1.clone();
        CvMat cvMat = this.invK2 = K2 == null ? null : K2.clone();
        if (K1 != null) {
            opencv_core.cvInvert((CvArr)K1, (CvArr)this.invK1);
        }
        if (K2 != null) {
            opencv_core.cvInvert((CvArr)K2, (CvArr)this.invK2);
        }
        this.R = R == null ? null : R.clone();
        this.t = t == null ? null : t.clone();
        this.n = n == null ? null : n.clone();
        this.referencePoints1 = referencePoints1 == null ? null : (double[])referencePoints1.clone();
        this.referencePoints2 = referencePoints2 == null ? null : (double[])referencePoints2.clone();
    }

    public CvScalar getFillColor() {
        return this.fillColor;
    }

    public void setFillColor(CvScalar fillColor) {
        this.fillColor = fillColor;
    }

    public double[] getReferencePoints1() {
        return this.referencePoints1;
    }

    public double[] getReferencePoints2() {
        return this.referencePoints2;
    }

    public CvMat getK1() {
        return this.K1;
    }

    public CvMat getK2() {
        return this.K2;
    }

    public CvMat getInvK1() {
        return this.invK1;
    }

    public CvMat getInvK2() {
        return this.invK2;
    }

    public CvMat getR() {
        return this.R;
    }

    public CvMat getT() {
        return this.t;
    }

    public CvMat getN() {
        return this.n;
    }

    protected void prepareHomography(CvMat H, int pyramidLevel, Parameters p, boolean inverse) {
        if (this.K2 != null && this.invK1 != null && this.R != null && this.t != null && p.fakeIdentity) {
            opencv_core.cvSetIdentity((CvArr)H);
            return;
        }
        if (inverse) {
            H.put(p.getH());
        } else {
            opencv_core.cvInvert((CvArr)p.getH(), (CvArr)H);
        }
        if (pyramidLevel > 0) {
            int scale = 1 << pyramidLevel;
            H.put(2, H.get(2) / (double)scale);
            H.put(5, H.get(5) / (double)scale);
            H.put(6, H.get(6) * (double)scale);
            H.put(7, H.get(7) * (double)scale);
        }
    }

    public void transform(IplImage srcImage, IplImage dstImage, CvRect roi, int pyramidLevel, ImageTransformer.Parameters parameters, boolean inverse) {
        Parameters p = (Parameters)parameters;
        if (this.K2 != null && this.invK1 != null && this.R != null && this.t != null && p.fakeIdentity) {
            if (srcImage != dstImage) {
                opencv_core.cvCopy((CvArr)srcImage, (CvArr)dstImage);
            }
            return;
        }
        CvMat H = H3x3.get();
        this.prepareHomography(H, pyramidLevel, p, true);
        if (roi != null && (roi.x() != 0 || roi.y() != 0)) {
            int x = roi.x();
            int y = roi.y();
            if (inverse) {
                H.put(2, H.get(0) * (double)x + H.get(1) * (double)y + H.get(2));
                H.put(5, H.get(3) * (double)x + H.get(4) * (double)y + H.get(5));
                H.put(8, H.get(6) * (double)x + H.get(7) * (double)y + H.get(8));
            } else {
                H.put(0, H.get(0) - (double)x * H.get(6));
                H.put(1, H.get(1) - (double)x * H.get(7));
                H.put(2, H.get(2) - (double)x * H.get(8));
                H.put(3, H.get(3) - (double)y * H.get(6));
                H.put(4, H.get(4) - (double)y * H.get(7));
                H.put(5, H.get(5) - (double)y * H.get(8));
            }
        }
        dstImage.origin(srcImage.origin());
        if (roi == null) {
            opencv_core.cvResetImageROI((IplImage)dstImage);
        } else {
            opencv_core.cvSetImageROI((IplImage)dstImage, (CvRect)roi);
        }
        opencv_imgproc.cvWarpPerspective((CvArr)srcImage, (CvArr)dstImage, (CvMat)H, (int)(9 | (inverse ? 16 : 0)), (CvScalar)this.getFillColor());
    }

    @Override
    public void transform(CvMat srcPts, CvMat dstPts, ImageTransformer.Parameters parameters, boolean inverse) {
        CvMat H;
        Parameters p = (Parameters)parameters;
        if (inverse) {
            H = H3x3.get();
            opencv_core.cvInvert((CvArr)p.getH(), (CvArr)H);
        } else {
            H = p.getH();
        }
        opencv_core.cvPerspectiveTransform((CvArr)srcPts, (CvArr)dstPts, (CvMat)H);
    }

    @Override
    public void transform(ImageTransformer.Data[] data, CvRect roi, ImageTransformer.Parameters[] parameters, boolean[] inverses) {
        int i;
        assert (data.length == parameters.length);
        if (this.kernelData == null || this.kernelData.capacity() < (long)data.length) {
            this.kernelData = new cvkernels.KernelData((long)data.length);
        }
        if (this.H == null || this.H.length < data.length) {
            this.H = new CvMat[data.length];
            for (i = 0; i < this.H.length; ++i) {
                this.H[i] = CvMat.create((int)3, (int)3);
            }
        }
        for (i = 0; i < data.length; ++i) {
            this.kernelData.position((long)i);
            this.kernelData.srcImg(data[i].srcImg);
            this.kernelData.srcImg2(null);
            this.kernelData.subImg(data[i].subImg);
            this.kernelData.srcDotImg(data[i].srcDotImg);
            this.kernelData.mask(data[i].mask);
            this.kernelData.zeroThreshold(data[i].zeroThreshold);
            this.kernelData.outlierThreshold(data[i].outlierThreshold);
            this.prepareHomography(this.H[i], data[i].pyramidLevel, (Parameters)parameters[i], inverses == null ? false : inverses[i]);
            this.kernelData.H1(this.H[i]);
            this.kernelData.H2(null);
            this.kernelData.X(null);
            this.kernelData.transImg(data[i].transImg);
            this.kernelData.dstImg(data[i].dstImg);
            this.kernelData.dstDstDot(data[i].dstDstDot);
        }
        long fullCapacity = this.kernelData.capacity();
        this.kernelData.capacity((long)data.length);
        cvkernels.multiWarpColorTransform(this.kernelData, roi, this.getFillColor());
        this.kernelData.capacity(fullCapacity);
        for (int i2 = 0; i2 < data.length; ++i2) {
            this.kernelData.position((long)i2);
            data[i2].dstCount = this.kernelData.dstCount();
            data[i2].dstCountZero = this.kernelData.dstCountZero();
            data[i2].dstCountOutlier = this.kernelData.dstCountOutlier();
            data[i2].srcDstDot = this.kernelData.srcDstDot();
        }
    }

    @Override
    public Parameters createParameters() {
        return new Parameters();
    }

    public class Parameters
    implements ImageTransformer.Parameters {
        protected double[] projectiveParameters = null;
        private CvMat H = CvMat.create((int)3, (int)3);
        private CvMat n2 = null;
        private CvMat R2 = null;
        private CvMat t2 = null;
        private double constraintError = 0.0;
        private boolean updateNeeded = true;
        protected boolean fakeIdentity = false;

        protected Parameters() {
            this.reset(false);
        }

        public boolean isUpdateNeeded() {
            return this.updateNeeded;
        }

        public void setUpdateNeeded(boolean updateNeeded) {
            this.updateNeeded = updateNeeded;
        }

        @Override
        public int size() {
            return this.projectiveParameters.length;
        }

        @Override
        public double[] get() {
            double[] p = new double[this.size()];
            for (int i = 0; i < p.length; ++i) {
                p[i] = this.get(i);
            }
            return p;
        }

        @Override
        public double get(int i) {
            return this.projectiveParameters[i];
        }

        @Override
        public void set(double ... p) {
            for (int i = 0; i < p.length; ++i) {
                this.set(i, p[i]);
            }
        }

        @Override
        public void set(int i, double p) {
            if (this.projectiveParameters[i] != p) {
                this.projectiveParameters[i] = p;
                this.setUpdateNeeded(true);
            }
        }

        @Override
        public void set(ImageTransformer.Parameters p) {
            this.set(p.get());
            this.fakeIdentity = ((Parameters)p).fakeIdentity;
        }

        @Override
        public void reset(boolean asIdentity) {
            this.setUpdateNeeded(true);
            if (ProjectiveTransformer.this.referencePoints1 != null && (ProjectiveTransformer.this.referencePoints1.length == 0 || ProjectiveTransformer.this.referencePoints1.length == 8)) {
                this.projectiveParameters = ProjectiveTransformer.this.referencePoints1.length == 0 ? new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0} : (double[])ProjectiveTransformer.this.referencePoints1.clone();
            } else if (ProjectiveTransformer.this.K2 != null && ProjectiveTransformer.this.invK1 != null) {
                this.projectiveParameters = ProjectiveTransformer.this.R != null && ProjectiveTransformer.this.t != null ? new double[]{ProjectiveTransformer.this.referencePoints1[0], ProjectiveTransformer.this.referencePoints1[2], ProjectiveTransformer.this.referencePoints1[4]} : (ProjectiveTransformer.this.n != null ? new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0} : new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
            }
        }

        @Override
        public double getConstraintError() {
            this.update();
            return this.constraintError;
        }

        public void set(CvMat setH, boolean inverse) {
            if (this.projectiveParameters.length == 8 && ProjectiveTransformer.this.referencePoints1 != null) {
                if (inverse) {
                    opencv_core.cvInvert((CvArr)setH, (CvArr)this.H);
                } else if (setH != this.H) {
                    opencv_core.cvCopy((CvArr)setH, (CvArr)this.H);
                }
                if (ProjectiveTransformer.this.referencePoints1.length == 0) {
                    for (int i = 0; i < 8; ++i) {
                        this.projectiveParameters[i] = this.H.get(i) / this.H.get(8);
                    }
                } else {
                    CvMat pts = pts4x1.get().put(ProjectiveTransformer.this.referencePoints1);
                    opencv_core.cvPerspectiveTransform((CvArr)pts, (CvArr)pts, (CvMat)this.H);
                    pts.get(this.projectiveParameters);
                }
            } else {
                throw new UnsupportedOperationException("Set homography operation not supported.");
            }
            this.setUpdateNeeded(true);
        }

        @Override
        public void compose(ImageTransformer.Parameters p1, boolean inverse1, ImageTransformer.Parameters p2, boolean inverse2) {
            Parameters pp1 = (Parameters)p1;
            Parameters pp2 = (Parameters)p2;
            if (ProjectiveTransformer.this.K2 != null && ProjectiveTransformer.this.invK1 != null && ProjectiveTransformer.this.R != null && ProjectiveTransformer.this.t != null && pp1.fakeIdentity) {
                return;
            }
            this.compose(pp1.getH(), inverse1, pp2.getH(), inverse2);
        }

        public void compose(CvMat H1, boolean inverse1, CvMat H2, boolean inverse2) {
            if (inverse1 && inverse2) {
                opencv_core.cvMatMul((CvArr)H2, (CvArr)H1, (CvArr)this.H);
                opencv_core.cvInvert((CvArr)this.H, (CvArr)this.H);
            } else if (inverse1) {
                opencv_core.cvInvert((CvArr)H1, (CvArr)this.H);
                opencv_core.cvMatMul((CvArr)this.H, (CvArr)H2, (CvArr)this.H);
            } else if (inverse2) {
                opencv_core.cvInvert((CvArr)H2, (CvArr)this.H);
                opencv_core.cvMatMul((CvArr)H1, (CvArr)this.H, (CvArr)this.H);
            } else {
                opencv_core.cvMatMul((CvArr)H1, (CvArr)H2, (CvArr)this.H);
            }
            this.set(this.H, false);
        }

        public CvMat getH() {
            this.update();
            return this.H;
        }

        public CvMat getN() {
            this.update();
            return this.n2;
        }

        public CvMat getR() {
            this.update();
            return this.R2;
        }

        public CvMat getT() {
            this.update();
            return this.t2;
        }

        protected void update() {
            if (!this.isUpdateNeeded()) {
                return;
            }
            if (ProjectiveTransformer.this.referencePoints1 != null && (ProjectiveTransformer.this.referencePoints1.length == 0 || ProjectiveTransformer.this.referencePoints1.length == 8)) {
                if (ProjectiveTransformer.this.referencePoints1.length == 0) {
                    this.H.put(0, this.projectiveParameters, 0, 8);
                    this.H.put(8, 1.0);
                } else {
                    JavaCV.getPerspectiveTransform(ProjectiveTransformer.this.referencePoints1, this.projectiveParameters, this.H);
                }
            } else if (ProjectiveTransformer.this.K2 != null && ProjectiveTransformer.this.invK1 != null) {
                if (ProjectiveTransformer.this.R != null && ProjectiveTransformer.this.t != null) {
                    double[] src = ProjectiveTransformer.this.referencePoints2;
                    double[] dst = new double[]{this.projectiveParameters[0], ProjectiveTransformer.this.referencePoints1[1], this.projectiveParameters[1], ProjectiveTransformer.this.referencePoints1[3], this.projectiveParameters[2], ProjectiveTransformer.this.referencePoints1[5]};
                    if (this.R2 == null) {
                        this.R2 = CvMat.create((int)3, (int)3);
                    }
                    if (this.t2 == null) {
                        this.t2 = CvMat.create((int)3, (int)1);
                    }
                    opencv_core.cvTranspose((CvArr)ProjectiveTransformer.this.R, (CvArr)this.R2);
                    opencv_core.cvGEMM((CvArr)this.R2, (CvArr)ProjectiveTransformer.this.t, (double)-1.0, null, (double)0.0, (CvArr)this.t2, (int)0);
                    JavaCV.getPerspectiveTransform(src, dst, ProjectiveTransformer.this.invK2, ProjectiveTransformer.this.K1, this.R2, this.t2, this.H);
                } else {
                    if (ProjectiveTransformer.this.n != null) {
                        this.n2 = ProjectiveTransformer.this.n;
                    } else {
                        if (this.n2 == null) {
                            this.n2 = CvMat.create((int)3, (int)1);
                        }
                        this.n2.put(0, this.projectiveParameters, 8, 3);
                    }
                    if (this.R2 == null) {
                        this.R2 = CvMat.create((int)3, (int)3);
                    }
                    if (this.t2 == null) {
                        this.t2 = CvMat.create((int)3, (int)1);
                    }
                    this.t2.put(0, this.projectiveParameters, 0, 3);
                    opencv_calib3d.Rodrigues((Mat)opencv_core.cvarrToMat((CvArr)this.t2), (Mat)opencv_core.cvarrToMat((CvArr)this.R2), null);
                    this.t2.put(0, this.projectiveParameters, 3, 3);
                    opencv_core.cvGEMM((CvArr)this.t2, (CvArr)this.n2, (double)-1.0, (CvArr)this.R2, (double)1.0, (CvArr)this.H, (int)2);
                }
            }
            this.setUpdateNeeded(false);
        }

        @Override
        public boolean preoptimize() {
            return false;
        }

        @Override
        public double[] getSubspace() {
            return null;
        }

        @Override
        public void setSubspace(double ... p) {
        }

        @Override
        public Parameters clone() {
            Parameters p = new Parameters();
            p.set(this);
            return p;
        }

        public String toString() {
            String s = "[";
            double[] p = this.get();
            for (int i = 0; i < p.length; ++i) {
                s = s + (float)p[i];
                if (i >= p.length - 1) continue;
                s = s + ", ";
            }
            s = s + "]";
            return s;
        }
    }
}

