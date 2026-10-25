/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.cvkernels$KernelData
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.CvMat
 *  org.bytedeco.opencv.opencv_core.CvRect
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import java.util.Arrays;
import org.bytedeco.javacv.ImageTransformer;
import org.bytedeco.javacv.ProjectiveTransformer;
import org.bytedeco.javacv.cvkernels;
import org.bytedeco.opencv.cvkernels;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.CvMat;
import org.bytedeco.opencv.opencv_core.CvRect;
import org.bytedeco.opencv.opencv_core.IplImage;

public class ProjectiveColorTransformer
extends ProjectiveTransformer {
    protected static ThreadLocal<CvMat> X24x4 = CvMat.createThreadLocal((int)4, (int)4);
    protected static ThreadLocal<CvMat> temp3x1 = CvMat.createThreadLocal((int)3, (int)1);
    protected CvMat X = null;
    protected int numGains = 0;
    protected int numBiases = 0;
    protected CvMat[] X2 = null;

    public ProjectiveColorTransformer(CvMat K1, CvMat K2, CvMat R, CvMat t, CvMat n, double[] referencePoints1, double[] referencePoints2, CvMat X, int numGains, int numBiases) {
        super(K1, K2, R, t, n, referencePoints1, referencePoints2);
        this.X = X == null ? null : X.clone();
        this.numGains = numGains;
        this.numBiases = numBiases;
    }

    public CvMat getX() {
        return this.X;
    }

    public int getNumGains() {
        return this.numGains;
    }

    public int getNumBiases() {
        return this.numBiases;
    }

    public void transformColor(IplImage srcImage, IplImage dstImage, CvRect roi, int pyramidLevel, ImageTransformer.Parameters parameters, boolean inverse) {
        Parameters p = (Parameters)parameters;
        if (Arrays.equals(p.getColorParameters(), p.getIdentityColorParameters()) && (this.X == null || p.fakeIdentity) || this.X == null && this.numGains == 0 && this.numBiases == 0) {
            if (srcImage != dstImage) {
                opencv_core.cvCopy((CvArr)srcImage, (CvArr)dstImage);
            }
            return;
        }
        CvMat X2 = X24x4.get();
        this.prepareColorTransform(X2, pyramidLevel, p, inverse);
        X2.rows(3);
        if (roi == null) {
            opencv_core.cvResetImageROI((IplImage)dstImage);
        } else {
            opencv_core.cvSetImageROI((IplImage)dstImage, (CvRect)roi);
        }
        X2.put(0, 3, X2.get(0, 3) * dstImage.highValue());
        X2.put(1, 3, X2.get(1, 3) * dstImage.highValue());
        X2.put(2, 3, X2.get(2, 3) * dstImage.highValue());
        opencv_core.cvTransform((CvArr)srcImage, (CvArr)dstImage, (CvMat)X2, null);
        X2.rows(4);
    }

    protected void prepareColorTransform(CvMat X2, int pyramidLevel, Parameters p, boolean inverse) {
        CvMat A = p.getA();
        CvMat b = p.getB();
        opencv_core.cvSetIdentity((CvArr)X2);
        X2.rows(3);
        X2.cols(3);
        if (p.fakeIdentity && !inverse) {
            X2.put(A);
        } else if (A != null && this.X != null) {
            opencv_core.cvMatMul((CvArr)this.X, (CvArr)A, (CvArr)X2);
        } else if (this.X == null) {
            X2.put(A);
        } else if (A == null) {
            X2.put(this.X);
        }
        X2.rows(4);
        X2.cols(4);
        if (b != null) {
            X2.put(0, 3, b.get(0));
            X2.put(1, 3, b.get(1));
            X2.put(2, 3, b.get(2));
        }
        if (inverse) {
            opencv_core.cvInvert((CvArr)X2, (CvArr)X2, (int)1);
        }
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
        if (this.X2 == null || this.X2.length < data.length) {
            this.X2 = new CvMat[data.length];
            for (i = 0; i < this.X2.length; ++i) {
                this.X2[i] = CvMat.create((int)4, (int)4);
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
            boolean inverse = inverses == null ? false : inverses[i];
            this.prepareHomography(this.H[i], data[i].pyramidLevel, (Parameters)parameters[i], inverse);
            this.prepareColorTransform(this.X2[i], data[i].pyramidLevel, (Parameters)parameters[i], inverse);
            this.kernelData.H1(this.H[i]);
            this.kernelData.H2(null);
            this.kernelData.X(this.X2[i]);
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
    extends ProjectiveTransformer.Parameters {
        protected double[] colorParameters;
        protected double[] identityColorParameters;
        private CvMat A;
        private CvMat b;

        protected Parameters() {
            super(ProjectiveColorTransformer.this);
            this.colorParameters = null;
            this.identityColorParameters = null;
            this.A = null;
            this.b = null;
            this.identityColorParameters = new double[ProjectiveColorTransformer.this.numGains + ProjectiveColorTransformer.this.numBiases];
            if (ProjectiveColorTransformer.this.numGains > 0) {
                this.A = CvMat.create((int)3, (int)3);
                opencv_core.cvSetIdentity((CvArr)this.A);
            }
            if (ProjectiveColorTransformer.this.numBiases > 0) {
                this.b = CvMat.create((int)3, (int)1);
                opencv_core.cvSetZero((CvArr)this.b);
            }
            switch (ProjectiveColorTransformer.this.numGains) {
                case 0: {
                    assert (this.A == null);
                    break;
                }
                case 1: {
                    this.identityColorParameters[0] = (this.A.get(0) + this.A.get(4) + this.A.get(8)) / 3.0;
                    break;
                }
                case 3: {
                    this.identityColorParameters[0] = this.A.get(0);
                    this.identityColorParameters[1] = this.A.get(4);
                    this.identityColorParameters[2] = this.A.get(8);
                    break;
                }
                case 9: {
                    this.A.get(0, this.identityColorParameters, 0, 9);
                    break;
                }
                default: {
                    assert (false);
                    break;
                }
            }
            switch (ProjectiveColorTransformer.this.numBiases) {
                case 0: {
                    assert (this.b == null);
                    break;
                }
                case 1: {
                    this.identityColorParameters[ProjectiveColorTransformer.this.numGains] = (this.b.get(0) + this.b.get(1) + this.b.get(2)) / 3.0;
                    break;
                }
                case 3: {
                    this.b.get(0, this.identityColorParameters, ProjectiveColorTransformer.this.numGains, 3);
                    break;
                }
                default: {
                    assert (false);
                    break;
                }
            }
            this.reset(false);
        }

        public double[] getColorParameters() {
            return this.colorParameters;
        }

        public double[] getIdentityColorParameters() {
            return this.identityColorParameters;
        }

        @Override
        public int size() {
            return super.size() + ProjectiveColorTransformer.this.numGains + ProjectiveColorTransformer.this.numBiases;
        }

        @Override
        public double get(int i) {
            int s = super.size();
            if (i < s) {
                return super.get(i);
            }
            return this.colorParameters[i - s];
        }

        @Override
        public void set(int i, double p) {
            int s = super.size();
            if (i < s) {
                super.set(i, p);
            } else if (this.colorParameters[i - s] != p) {
                this.colorParameters[i - s] = p;
                this.setUpdateNeeded(true);
            }
        }

        @Override
        public void reset(boolean asIdentity) {
            super.reset(asIdentity);
            this.resetColor(asIdentity);
        }

        public void resetColor(boolean asIdentity) {
            if (!(this.identityColorParameters == null || Arrays.equals(this.colorParameters, this.identityColorParameters) && this.fakeIdentity == asIdentity)) {
                this.fakeIdentity = asIdentity;
                this.colorParameters = (double[])this.identityColorParameters.clone();
                this.setUpdateNeeded(true);
            }
        }

        @Override
        public void compose(ImageTransformer.Parameters p1, boolean inverse1, ImageTransformer.Parameters p2, boolean inverse2) {
            super.compose(p1, inverse1, p2, inverse2);
            this.composeColor(p1, inverse1, p2, inverse2);
        }

        public void composeColor(ImageTransformer.Parameters p1, boolean inverse1, ImageTransformer.Parameters p2, boolean inverse2) {
            assert (!inverse1 && !inverse2);
            Parameters pp1 = (Parameters)p1;
            Parameters pp2 = (Parameters)p2;
            CvMat A1 = pp1.getA();
            CvMat b1 = pp1.getB();
            CvMat A2 = pp2.getA();
            CvMat b2 = pp2.getB();
            if (this.b != null) {
                if (pp1.fakeIdentity && ProjectiveColorTransformer.this.X != null) {
                    CvMat temp = temp3x1.get();
                    opencv_core.cvMatMul((CvArr)ProjectiveColorTransformer.this.X, (CvArr)b1, (CvArr)temp);
                    b1 = temp;
                }
                if (A2 == null && b2 == null) {
                    opencv_core.cvCopy((CvArr)b1, (CvArr)this.b);
                } else if (b1 == null) {
                    opencv_core.cvCopy((CvArr)b2, (CvArr)this.b);
                } else if (b2 == null) {
                    opencv_core.cvMatMul((CvArr)A2, (CvArr)b1, (CvArr)this.b);
                } else {
                    opencv_core.cvGEMM((CvArr)A2, (CvArr)b1, (double)1.0, (CvArr)b2, (double)1.0, (CvArr)this.b, (int)0);
                }
            }
            if (this.A != null) {
                if (A1 == null) {
                    opencv_core.cvCopy((CvArr)A2, (CvArr)this.A);
                } else if (A2 == null) {
                    opencv_core.cvCopy((CvArr)A1, (CvArr)this.A);
                } else {
                    opencv_core.cvMatMul((CvArr)A2, (CvArr)A1, (CvArr)this.A);
                }
            }
            switch (ProjectiveColorTransformer.this.numGains) {
                case 0: {
                    assert (this.A == null);
                    break;
                }
                case 1: {
                    this.colorParameters[0] = (this.A.get(0) + this.A.get(4) + this.A.get(8)) / 3.0;
                    break;
                }
                case 3: {
                    this.colorParameters[0] = this.A.get(0);
                    this.colorParameters[1] = this.A.get(4);
                    this.colorParameters[2] = this.A.get(8);
                    break;
                }
                case 9: {
                    this.A.get(0, this.colorParameters, 0, 9);
                    break;
                }
                default: {
                    assert (false);
                    break;
                }
            }
            switch (ProjectiveColorTransformer.this.numBiases) {
                case 0: {
                    assert (this.b == null);
                    break;
                }
                case 1: {
                    this.colorParameters[ProjectiveColorTransformer.this.numGains] = (this.b.get(0) + this.b.get(1) + this.b.get(2)) / 3.0;
                    break;
                }
                case 3: {
                    this.b.get(0, this.colorParameters, ProjectiveColorTransformer.this.numGains, 3);
                    break;
                }
                default: {
                    assert (false);
                    break;
                }
            }
        }

        public CvMat getA() {
            this.update();
            return this.A;
        }

        public CvMat getB() {
            this.update();
            return this.b;
        }

        @Override
        protected void update() {
            if (!this.isUpdateNeeded()) {
                return;
            }
            switch (ProjectiveColorTransformer.this.numGains) {
                case 0: {
                    assert (this.A == null);
                    break;
                }
                case 1: {
                    this.A.put(0, this.colorParameters[0]);
                    this.A.put(4, this.colorParameters[0]);
                    this.A.put(8, this.colorParameters[0]);
                    break;
                }
                case 3: {
                    this.A.put(0, this.colorParameters[0]);
                    this.A.put(4, this.colorParameters[1]);
                    this.A.put(8, this.colorParameters[2]);
                    break;
                }
                case 9: {
                    this.A.put(0, this.colorParameters, 0, 9);
                    break;
                }
                default: {
                    assert (false);
                    break;
                }
            }
            switch (ProjectiveColorTransformer.this.numBiases) {
                case 0: {
                    assert (this.b == null);
                    break;
                }
                case 1: {
                    this.b.put(0, this.colorParameters[ProjectiveColorTransformer.this.numGains]);
                    this.b.put(1, this.colorParameters[ProjectiveColorTransformer.this.numGains]);
                    this.b.put(2, this.colorParameters[ProjectiveColorTransformer.this.numGains]);
                    break;
                }
                case 3: {
                    this.b.put(0, this.colorParameters, ProjectiveColorTransformer.this.numGains, 3);
                    break;
                }
                default: {
                    assert (false);
                    break;
                }
            }
            super.update();
            this.setUpdateNeeded(false);
        }

        @Override
        public Parameters clone() {
            Parameters p = new Parameters();
            p.set(this);
            return p;
        }
    }
}

