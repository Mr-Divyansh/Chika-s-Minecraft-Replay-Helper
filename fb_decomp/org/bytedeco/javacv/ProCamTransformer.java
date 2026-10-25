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
 *  org.bytedeco.opencv.opencv_core.IplROI
 *  org.bytedeco.opencv.opencv_core.Mat
 */
package org.bytedeco.javacv;

import org.bytedeco.javacv.CameraDevice;
import org.bytedeco.javacv.ImageTransformer;
import org.bytedeco.javacv.JavaCV;
import org.bytedeco.javacv.ProjectiveColorTransformer;
import org.bytedeco.javacv.ProjectorDevice;
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
import org.bytedeco.opencv.opencv_core.IplROI;
import org.bytedeco.opencv.opencv_core.Mat;

public class ProCamTransformer
implements ImageTransformer {
    protected CameraDevice camera = null;
    protected ProjectorDevice projector = null;
    protected ProjectiveColorTransformer surfaceTransformer = null;
    protected ProjectiveColorTransformer projectorTransformer = null;
    protected IplImage[] projectorImage = null;
    protected IplImage[] surfaceImage = null;
    protected CvScalar fillColor = opencv_core.cvScalar((double)0.0, (double)0.0, (double)0.0, (double)1.0);
    protected CvRect roi = new CvRect();
    protected CvMat frontoParallelH = null;
    protected CvMat invFrontoParallelH = null;
    protected CvMat invCameraMatrix = null;
    protected cvkernels.KernelData kernelData = null;
    protected CvMat[] H1 = null;
    protected CvMat[] H2 = null;
    protected CvMat[] X = null;

    public ProCamTransformer(double[] referencePoints, CameraDevice camera, ProjectorDevice projector) {
        this(referencePoints, camera, projector, null);
    }

    public ProCamTransformer(double[] referencePoints, CameraDevice camera, ProjectorDevice projector, CvMat n) {
        this.camera = camera;
        this.projector = projector;
        if (referencePoints != null) {
            this.surfaceTransformer = new ProjectiveColorTransformer(camera.cameraMatrix, camera.cameraMatrix, null, null, n, referencePoints, null, null, 3, 0);
        }
        double[] referencePoints1 = new double[]{0.0, 0.0, camera.imageWidth / 2, camera.imageHeight, camera.imageWidth, 0.0};
        double[] referencePoints2 = new double[]{0.0, 0.0, projector.imageWidth / 2, projector.imageHeight, projector.imageWidth, 0.0};
        if (n != null) {
            this.invCameraMatrix = CvMat.create((int)3, (int)3);
            opencv_core.cvInvert((CvArr)camera.cameraMatrix, (CvArr)this.invCameraMatrix);
            JavaCV.perspectiveTransform(referencePoints2, referencePoints1, this.invCameraMatrix, projector.cameraMatrix, projector.R, projector.T, n, true);
        }
        this.projectorTransformer = new ProjectiveColorTransformer(camera.cameraMatrix, projector.cameraMatrix, projector.R, projector.T, null, referencePoints1, referencePoints2, projector.colorMixingMatrix, 1, 3);
        if (referencePoints != null && n != null) {
            this.frontoParallelH = camera.getFrontoParallelH(referencePoints, n, CvMat.create((int)3, (int)3));
            this.invFrontoParallelH = this.frontoParallelH.clone();
            opencv_core.cvInvert((CvArr)this.frontoParallelH, (CvArr)this.invFrontoParallelH);
        }
    }

    public int getNumGains() {
        return this.projectorTransformer.getNumGains();
    }

    public int getNumBiases() {
        return this.projectorTransformer.getNumBiases();
    }

    public CvScalar getFillColor() {
        return this.fillColor;
    }

    public void setFillColor(CvScalar fillColor) {
        this.fillColor = fillColor;
    }

    public ProjectiveColorTransformer getSurfaceTransformer() {
        return this.surfaceTransformer;
    }

    public ProjectiveColorTransformer getProjectorTransformer() {
        return this.projectorTransformer;
    }

    public IplImage getProjectorImage(int pyramidLevel) {
        return this.projectorImage[pyramidLevel];
    }

    public void setProjectorImage(IplImage projectorImage0, int minLevel, int maxLevel) {
        this.setProjectorImage(projectorImage0, minLevel, maxLevel, true);
    }

    public void setProjectorImage(IplImage projectorImage0, int minLevel, int maxLevel, boolean convertToFloat) {
        if (this.projectorImage == null || this.projectorImage.length != maxLevel + 1) {
            this.projectorImage = new IplImage[maxLevel + 1];
        }
        if (projectorImage0.depth() == 32 || !convertToFloat) {
            this.projectorImage[minLevel] = projectorImage0;
        } else {
            IplROI ir;
            if (this.projectorImage[minLevel] == null) {
                this.projectorImage[minLevel] = IplImage.create((int)projectorImage0.width(), (int)projectorImage0.height(), (int)32, (int)projectorImage0.nChannels(), (int)projectorImage0.origin());
            }
            if ((ir = projectorImage0.roi()) != null) {
                int align = 1 << maxLevel + 1;
                this.roi.x(Math.max(0, (int)Math.floor((double)ir.xOffset() / (double)align) * align));
                this.roi.y(Math.max(0, (int)Math.floor((double)ir.yOffset() / (double)align) * align));
                this.roi.width(Math.min(projectorImage0.width(), (int)Math.ceil((double)ir.width() / (double)align) * align));
                this.roi.height(Math.min(projectorImage0.height(), (int)Math.ceil((double)ir.height() / (double)align) * align));
                opencv_core.cvSetImageROI((IplImage)projectorImage0, (CvRect)this.roi);
                opencv_core.cvSetImageROI((IplImage)this.projectorImage[minLevel], (CvRect)this.roi);
            } else {
                opencv_core.cvResetImageROI((IplImage)projectorImage0);
                opencv_core.cvResetImageROI((IplImage)this.projectorImage[minLevel]);
            }
            opencv_core.cvConvertScale((CvArr)projectorImage0, (CvArr)this.projectorImage[minLevel], (double)0.00392156862745098, (double)0.0);
        }
        for (int i = minLevel + 1; i <= maxLevel; ++i) {
            IplROI ir;
            int w = this.projectorImage[i - 1].width() / 2;
            int h = this.projectorImage[i - 1].height() / 2;
            int d = this.projectorImage[i - 1].depth();
            int c = this.projectorImage[i - 1].nChannels();
            int o = this.projectorImage[i - 1].origin();
            if (this.projectorImage[i] == null) {
                this.projectorImage[i] = IplImage.create((int)w, (int)h, (int)d, (int)c, (int)o);
            }
            if ((ir = this.projectorImage[i - 1].roi()) != null) {
                this.roi.x(ir.xOffset() / 2);
                this.roi.width(ir.width() / 2);
                this.roi.y(ir.yOffset() / 2);
                this.roi.height(ir.height() / 2);
                opencv_core.cvSetImageROI((IplImage)this.projectorImage[i], (CvRect)this.roi);
            } else {
                opencv_core.cvResetImageROI((IplImage)this.projectorImage[i]);
            }
            opencv_imgproc.cvPyrDown((CvArr)this.projectorImage[i - 1], (CvArr)this.projectorImage[i], (int)7);
            opencv_core.cvResetImageROI((IplImage)this.projectorImage[i - 1]);
        }
    }

    public IplImage getSurfaceImage(int pyramidLevel) {
        return this.surfaceImage[pyramidLevel];
    }

    public void setSurfaceImage(IplImage surfaceImage0, int pyramidLevels) {
        if (this.surfaceImage == null || this.surfaceImage.length != pyramidLevels) {
            this.surfaceImage = new IplImage[pyramidLevels];
        }
        this.surfaceImage[0] = surfaceImage0;
        opencv_core.cvResetImageROI((IplImage)surfaceImage0);
        for (int i = 1; i < pyramidLevels; ++i) {
            int w = this.surfaceImage[i - 1].width() / 2;
            int h = this.surfaceImage[i - 1].height() / 2;
            int d = this.surfaceImage[i - 1].depth();
            int c = this.surfaceImage[i - 1].nChannels();
            int o = this.surfaceImage[i - 1].origin();
            if (this.surfaceImage[i] == null) {
                this.surfaceImage[i] = IplImage.create((int)w, (int)h, (int)d, (int)c, (int)o);
            } else {
                opencv_core.cvResetImageROI((IplImage)this.surfaceImage[i]);
            }
            opencv_imgproc.cvPyrDown((CvArr)this.surfaceImage[i - 1], (CvArr)this.surfaceImage[i], (int)7);
        }
    }

    protected void prepareTransforms(CvMat H1, CvMat H2, CvMat X, int pyramidLevel, Parameters p) {
        ProjectiveColorTransformer.Parameters cameraParameters = p.getSurfaceParameters();
        ProjectiveColorTransformer.Parameters projectorParameters = p.getProjectorParameters();
        if (this.surfaceTransformer != null) {
            opencv_core.cvInvert((CvArr)cameraParameters.getH(), (CvArr)H1);
        }
        opencv_core.cvInvert((CvArr)projectorParameters.getH(), (CvArr)H2);
        if (pyramidLevel > 0) {
            int scale = 1 << pyramidLevel;
            if (this.surfaceTransformer != null) {
                H1.put(2, H1.get(2) / (double)scale);
                H1.put(5, H1.get(5) / (double)scale);
                H1.put(6, H1.get(6) * (double)scale);
                H1.put(7, H1.get(7) * (double)scale);
            }
            H2.put(2, H2.get(2) / (double)scale);
            H2.put(5, H2.get(5) / (double)scale);
            H2.put(6, H2.get(6) * (double)scale);
            H2.put(7, H2.get(7) * (double)scale);
        }
        double[] x = this.projector.colorMixingMatrix.get();
        double[] a = projectorParameters.getColorParameters();
        double a2 = a[0];
        X.put(new double[]{a2 * x[0], a2 * x[1], a2 * x[2], a[1], a2 * x[3], a2 * x[4], a2 * x[5], a[2], a2 * x[6], a2 * x[7], a2 * x[8], a[3], 0.0, 0.0, 0.0, 1.0});
    }

    public void transform(IplImage srcImage, IplImage dstImage, CvRect roi, int pyramidLevel, ImageTransformer.Parameters parameters, boolean inverse) {
        if (inverse) {
            throw new UnsupportedOperationException("Inverse transform not supported.");
        }
        Parameters p = (Parameters)parameters;
        ProjectiveColorTransformer.Parameters cameraParameters = p.getSurfaceParameters();
        ProjectiveColorTransformer.Parameters projectorParameters = p.getProjectorParameters();
        if (p.tempImage == null || p.tempImage.length <= pyramidLevel) {
            Parameters.access$002(p, new IplImage[pyramidLevel + 1]);
        }
        ((Parameters)p).tempImage[pyramidLevel] = IplImage.createIfNotCompatible((IplImage)p.tempImage[pyramidLevel], (IplImage)dstImage);
        if (roi == null) {
            opencv_core.cvResetImageROI((IplImage)p.tempImage[pyramidLevel]);
        } else {
            opencv_core.cvSetImageROI((IplImage)p.tempImage[pyramidLevel], (CvRect)roi);
        }
        if (this.surfaceTransformer != null) {
            this.surfaceTransformer.transform(srcImage, p.tempImage[pyramidLevel], roi, pyramidLevel, cameraParameters, false);
        }
        this.projectorTransformer.transform(this.projectorImage[pyramidLevel], dstImage, roi, pyramidLevel, projectorParameters, false);
        if (this.surfaceTransformer != null) {
            opencv_core.cvMul((CvArr)dstImage, (CvArr)p.tempImage[pyramidLevel], (CvArr)dstImage, (double)(1.0 / dstImage.highValue()));
        } else {
            opencv_core.cvCopy((CvArr)p.tempImage[pyramidLevel], (CvArr)dstImage);
        }
    }

    @Override
    public void transform(CvMat srcPts, CvMat dstPts, ImageTransformer.Parameters parameters, boolean inverse) {
        if (this.surfaceTransformer != null) {
            this.surfaceTransformer.transform(srcPts, dstPts, ((Parameters)parameters).surfaceParameters, inverse);
        } else if (dstPts != srcPts) {
            dstPts.put(srcPts);
        }
    }

    @Override
    public void transform(ImageTransformer.Data[] data, CvRect roi, ImageTransformer.Parameters[] parameters, boolean[] inverses) {
        int i;
        assert (data.length == parameters.length);
        if (this.kernelData == null || this.kernelData.capacity() < (long)data.length) {
            this.kernelData = new cvkernels.KernelData((long)data.length);
        }
        if ((this.H1 == null || this.H1.length < data.length) && this.surfaceTransformer != null) {
            this.H1 = new CvMat[data.length];
            for (i = 0; i < this.H1.length; ++i) {
                this.H1[i] = CvMat.create((int)3, (int)3);
            }
        }
        if (this.H2 == null || this.H2.length < data.length) {
            this.H2 = new CvMat[data.length];
            for (i = 0; i < this.H2.length; ++i) {
                this.H2[i] = CvMat.create((int)3, (int)3);
            }
        }
        if (this.X == null || this.X.length < data.length) {
            this.X = new CvMat[data.length];
            for (i = 0; i < this.X.length; ++i) {
                this.X[i] = CvMat.create((int)4, (int)4);
            }
        }
        for (i = 0; i < data.length; ++i) {
            this.kernelData.position((long)i);
            this.kernelData.srcImg(this.projectorImage[data[i].pyramidLevel]);
            this.kernelData.srcImg2(this.surfaceTransformer == null ? null : data[i].srcImg);
            this.kernelData.subImg(data[i].subImg);
            this.kernelData.srcDotImg(data[i].srcDotImg);
            this.kernelData.mask(data[i].mask);
            this.kernelData.zeroThreshold(data[i].zeroThreshold);
            this.kernelData.outlierThreshold(data[i].outlierThreshold);
            if (inverses != null && inverses[i]) {
                throw new UnsupportedOperationException("Inverse transform not supported.");
            }
            this.prepareTransforms(this.surfaceTransformer == null ? null : this.H1[i], this.H2[i], this.X[i], data[i].pyramidLevel, (Parameters)parameters[i]);
            this.kernelData.H1(this.H2[i]);
            this.kernelData.H2(this.surfaceTransformer == null ? null : this.H1[i]);
            this.kernelData.X(this.X[i]);
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
        private ProjectiveColorTransformer.Parameters surfaceParameters = null;
        private ProjectiveColorTransformer.Parameters projectorParameters = null;
        private IplImage[] tempImage = null;
        private CvMat H = CvMat.create((int)3, (int)3);
        private CvMat R = CvMat.create((int)3, (int)3);
        private CvMat n = CvMat.create((int)3, (int)1);
        private CvMat t = CvMat.create((int)3, (int)1);

        protected Parameters() {
            this.reset(false);
        }

        protected Parameters(ProjectiveColorTransformer.Parameters surfaceParameters, ProjectiveColorTransformer.Parameters projectorParameters) {
            this.reset(surfaceParameters, projectorParameters);
        }

        public ProjectiveColorTransformer.Parameters getSurfaceParameters() {
            return this.surfaceParameters;
        }

        public ProjectiveColorTransformer.Parameters getProjectorParameters() {
            return this.projectorParameters;
        }

        private int getSizeForSurface() {
            return ProCamTransformer.this.surfaceTransformer == null ? 0 : this.surfaceParameters.size() - ProCamTransformer.this.surfaceTransformer.getNumGains() - ProCamTransformer.this.surfaceTransformer.getNumBiases();
        }

        private int getSizeForProjector() {
            return this.projectorParameters.size();
        }

        @Override
        public int size() {
            return this.getSizeForSurface() + this.getSizeForProjector();
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
            if (i < this.getSizeForSurface()) {
                return this.surfaceParameters.get(i);
            }
            return this.projectorParameters.get(i - this.getSizeForSurface());
        }

        @Override
        public void set(double ... p) {
            for (int i = 0; i < p.length; ++i) {
                this.set(i, p[i]);
            }
        }

        @Override
        public void set(int i, double p) {
            if (i < this.getSizeForSurface()) {
                this.surfaceParameters.set(i, p);
            } else {
                this.projectorParameters.set(i - this.getSizeForSurface(), p);
            }
        }

        @Override
        public void set(ImageTransformer.Parameters p) {
            Parameters pcp = (Parameters)p;
            if (ProCamTransformer.this.surfaceTransformer != null) {
                this.surfaceParameters.set(pcp.getSurfaceParameters());
                this.surfaceParameters.resetColor(false);
            }
            this.projectorParameters.set(pcp.getProjectorParameters());
        }

        @Override
        public void reset(boolean asIdentity) {
            this.reset(null, null);
        }

        public void reset(ProjectiveColorTransformer.Parameters surfaceParameters, ProjectiveColorTransformer.Parameters projectorParameters) {
            if (surfaceParameters == null && ProCamTransformer.this.surfaceTransformer != null) {
                surfaceParameters = ProCamTransformer.this.surfaceTransformer.createParameters();
            }
            if (projectorParameters == null) {
                projectorParameters = ProCamTransformer.this.projectorTransformer.createParameters();
            }
            this.surfaceParameters = surfaceParameters;
            this.projectorParameters = projectorParameters;
            this.setSubspace(this.getSubspace());
        }

        @Override
        public double getConstraintError() {
            double error = ProCamTransformer.this.surfaceTransformer == null ? 0.0 : this.surfaceParameters.getConstraintError();
            this.projectorParameters.update();
            return error;
        }

        @Override
        public void compose(ImageTransformer.Parameters p1, boolean inverse1, ImageTransformer.Parameters p2, boolean inverse2) {
            throw new UnsupportedOperationException("Compose operation not supported.");
        }

        @Override
        public boolean preoptimize() {
            double[] p = this.setSubspaceInternal(this.getSubspaceInternal());
            if (p != null) {
                this.set(8, p[8]);
                this.set(9, p[9]);
                this.set(10, p[10]);
                return true;
            }
            return false;
        }

        @Override
        public void setSubspace(double ... p) {
            double[] dst = this.setSubspaceInternal(p);
            if (dst != null) {
                this.set(dst);
            }
        }

        @Override
        public double[] getSubspace() {
            return this.getSubspaceInternal();
        }

        private double[] setSubspaceInternal(double ... p) {
            if (ProCamTransformer.this.invFrontoParallelH == null) {
                return null;
            }
            double[] dst = new double[11];
            this.t.put(new double[]{p[0], p[1], p[2]});
            opencv_calib3d.Rodrigues((Mat)opencv_core.cvarrToMat((CvArr)this.t), (Mat)opencv_core.cvarrToMat((CvArr)this.R), null);
            this.t.put(new double[]{p[3], p[4], p[5]});
            this.H.put(new double[]{this.R.get(0), this.R.get(1), this.t.get(0), this.R.get(3), this.R.get(4), this.t.get(1), this.R.get(6), this.R.get(7), this.t.get(2)});
            opencv_core.cvMatMul((CvArr)this.H, (CvArr)ProCamTransformer.this.invFrontoParallelH, (CvArr)this.H);
            opencv_core.cvMatMul((CvArr)ProCamTransformer.this.surfaceTransformer.getK2(), (CvArr)this.H, (CvArr)this.H);
            opencv_core.cvMatMul((CvArr)this.H, (CvArr)ProCamTransformer.this.surfaceTransformer.getInvK1(), (CvArr)this.H);
            opencv_core.cvGEMM((CvArr)this.R, (CvArr)this.t, (double)1.0, null, (double)0.0, (CvArr)this.t, (int)1);
            double scale = 1.0 / this.t.get(2);
            this.n.put(new double[]{0.0, 0.0, 1.0});
            opencv_core.cvGEMM((CvArr)this.R, (CvArr)this.n, (double)scale, null, (double)0.0, (CvArr)this.n, (int)0);
            double[] src = ProCamTransformer.this.projectorTransformer.getReferencePoints2();
            JavaCV.perspectiveTransform(src, dst, ProCamTransformer.this.projectorTransformer.getInvK1(), ProCamTransformer.this.projectorTransformer.getK2(), ProCamTransformer.this.projectorTransformer.getR(), ProCamTransformer.this.projectorTransformer.getT(), this.n, true);
            dst[8] = dst[0];
            dst[9] = dst[2];
            dst[10] = dst[4];
            JavaCV.perspectiveTransform(ProCamTransformer.this.surfaceTransformer.getReferencePoints1(), dst, this.H);
            return dst;
        }

        private double[] getSubspaceInternal() {
            if (ProCamTransformer.this.frontoParallelH == null) {
                return null;
            }
            opencv_core.cvMatMul((CvArr)ProCamTransformer.this.surfaceTransformer.getK1(), (CvArr)ProCamTransformer.this.frontoParallelH, (CvArr)this.H);
            opencv_core.cvMatMul((CvArr)this.surfaceParameters.getH(), (CvArr)this.H, (CvArr)this.H);
            opencv_core.cvMatMul((CvArr)ProCamTransformer.this.surfaceTransformer.getInvK2(), (CvArr)this.H, (CvArr)this.H);
            JavaCV.HtoRt(this.H, this.R, this.t);
            opencv_calib3d.Rodrigues((Mat)opencv_core.cvarrToMat((CvArr)this.R), (Mat)opencv_core.cvarrToMat((CvArr)this.n), null);
            double[] p = new double[]{this.n.get(0), this.n.get(1), this.n.get(2), this.t.get(0), this.t.get(1), this.t.get(2)};
            return p;
        }

        public CvMat getN() {
            double[] src = ProCamTransformer.this.projectorTransformer.getReferencePoints2();
            double[] dst = (double[])ProCamTransformer.this.projectorTransformer.getReferencePoints1().clone();
            dst[0] = this.projectorParameters.get(0);
            dst[2] = this.projectorParameters.get(1);
            dst[4] = this.projectorParameters.get(2);
            opencv_core.cvTranspose((CvArr)ProCamTransformer.this.projectorTransformer.getR(), (CvArr)this.R);
            opencv_core.cvGEMM((CvArr)this.R, (CvArr)ProCamTransformer.this.projectorTransformer.getT(), (double)-1.0, null, (double)0.0, (CvArr)this.t, (int)0);
            JavaCV.getPlaneParameters(src, dst, ProCamTransformer.this.projectorTransformer.getInvK2(), ProCamTransformer.this.projectorTransformer.getK1(), this.R, this.t, this.n);
            double d = 1.0 + opencv_core.cvDotProduct((CvArr)this.n, (CvArr)ProCamTransformer.this.projectorTransformer.getT());
            opencv_core.cvGEMM((CvArr)this.R, (CvArr)this.n, (double)(1.0 / d), null, (double)0.0, (CvArr)this.n, (int)0);
            return this.n;
        }

        public CvMat getN0() {
            this.n = this.getN();
            if (ProCamTransformer.this.surfaceTransformer == null) {
                return this.n;
            }
            ProCamTransformer.this.camera.getFrontoParallelH(this.surfaceParameters.get(), this.n, this.R);
            opencv_core.cvInvert((CvArr)this.surfaceParameters.getH(), (CvArr)this.H);
            opencv_core.cvMatMul((CvArr)this.H, (CvArr)ProCamTransformer.this.surfaceTransformer.getK2(), (CvArr)this.H);
            opencv_core.cvMatMul((CvArr)this.H, (CvArr)this.R, (CvArr)this.H);
            opencv_core.cvMatMul((CvArr)ProCamTransformer.this.surfaceTransformer.getInvK1(), (CvArr)this.H, (CvArr)this.H);
            JavaCV.HtoRt(this.H, this.R, this.t);
            opencv_core.cvGEMM((CvArr)this.R, (CvArr)this.t, (double)1.0, null, (double)0.0, (CvArr)this.t, (int)1);
            double scale = 1.0 / this.t.get(2);
            this.n.put(new double[]{0.0, 0.0, 1.0});
            opencv_core.cvGEMM((CvArr)this.R, (CvArr)this.n, (double)scale, null, (double)0.0, (CvArr)this.n, (int)0);
            return this.n;
        }

        @Override
        public Parameters clone() {
            Parameters p = new Parameters();
            p.surfaceParameters = this.surfaceParameters == null ? null : this.surfaceParameters.clone();
            p.projectorParameters = this.projectorParameters.clone();
            return p;
        }

        public String toString() {
            if (this.surfaceParameters != null) {
                return this.surfaceParameters.toString() + this.projectorParameters.toString();
            }
            return this.projectorParameters.toString();
        }

        static /* synthetic */ IplImage[] access$002(Parameters x0, IplImage[] x1) {
            x0.tempImage = x1;
            return x1;
        }
    }
}

