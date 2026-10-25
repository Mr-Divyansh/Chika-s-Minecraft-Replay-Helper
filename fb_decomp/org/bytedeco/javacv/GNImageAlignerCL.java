/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jogamp.opencl.CLImage2d
 *  com.jogamp.opencl.CLImageFormat
 *  com.jogamp.opencl.CLImageFormat$ChannelOrder
 *  com.jogamp.opencl.CLImageFormat$ChannelType
 *  com.jogamp.opencl.CLMemory$Mem
 *  com.jogamp.opencl.CLObject
 *  com.jogamp.opencl.gl.CLGLContext
 *  com.jogamp.opencl.gl.CLGLImage2d
 *  com.jogamp.opengl.GL2
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.CvRect
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import com.jogamp.opencl.CLImage2d;
import com.jogamp.opencl.CLImageFormat;
import com.jogamp.opencl.CLMemory;
import com.jogamp.opencl.CLObject;
import com.jogamp.opencl.gl.CLGLContext;
import com.jogamp.opencl.gl.CLGLImage2d;
import com.jogamp.opengl.GL2;
import java.util.Arrays;
import org.bytedeco.javacv.GNImageAligner;
import org.bytedeco.javacv.ImageAlignerCL;
import org.bytedeco.javacv.ImageTransformer;
import org.bytedeco.javacv.ImageTransformerCL;
import org.bytedeco.javacv.JavaCV;
import org.bytedeco.javacv.JavaCVCL;
import org.bytedeco.javacv.Parallel;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.CvRect;
import org.bytedeco.opencv.opencv_core.IplImage;

public class GNImageAlignerCL
extends GNImageAligner
implements ImageAlignerCL {
    private final JavaCVCL context;
    private CLImage2d[] templateCL;
    private CLImage2d[] targetCL;
    private CLImage2d[] transformedCL;
    private CLImage2d[] residualCL;
    private CLGLImage2d[] maskCL;
    private int[] maskrb;
    private int[] maskfb;
    private CLImage2d[] imagesCL = new CLImage2d[5];
    private ImageTransformerCL.InputData inputData;
    private ImageTransformerCL.OutputData outputData;
    private boolean[] templateChanged;

    public GNImageAlignerCL(ImageTransformerCL transformer, ImageTransformer.Parameters initialParameters, CLImage2d template0, double[] roiPts, CLImage2d target0) {
        this(transformer, initialParameters, template0, roiPts, target0, new GNImageAligner.Settings());
    }

    public GNImageAlignerCL(ImageTransformerCL transformer, ImageTransformer.Parameters initialParameters, CLImage2d template0, double[] roiPts, CLImage2d target0, GNImageAligner.Settings settings) {
        super(transformer, initialParameters);
        this.setSettings(settings);
        this.context = transformer.getContext();
        int minLevel = settings.pyramidLevelMin;
        int maxLevel = settings.pyramidLevelMax;
        this.template = new IplImage[maxLevel + 1];
        this.target = new IplImage[maxLevel + 1];
        this.transformed = new IplImage[maxLevel + 1];
        this.residual = new IplImage[maxLevel + 1];
        this.mask = new IplImage[maxLevel + 1];
        this.templateCL = new CLImage2d[maxLevel + 1];
        this.targetCL = new CLImage2d[maxLevel + 1];
        this.transformedCL = new CLImage2d[maxLevel + 1];
        this.residualCL = new CLImage2d[maxLevel + 1];
        this.maskCL = new CLGLImage2d[maxLevel + 1];
        this.maskrb = new int[maxLevel + 1];
        this.maskfb = new int[maxLevel + 1];
        int w = template0 != null ? template0.width : target0.width;
        int h = template0 != null ? template0.height : target0.height;
        CLGLContext c = this.context.getCLGLContext();
        GL2 gl = this.context.getGL2();
        gl.glGenRenderbuffers(maxLevel + 1, this.maskrb, 0);
        gl.glGenFramebuffers(maxLevel + 1, this.maskfb, 0);
        CLImageFormat f = new CLImageFormat(CLImageFormat.ChannelOrder.RGBA, CLImageFormat.ChannelType.FLOAT);
        for (int i = minLevel; i <= maxLevel; ++i) {
            this.templateCL[i] = i == minLevel && template0 != null ? template0 : c.createImage2d(w, h, f, new CLMemory.Mem[0]);
            this.targetCL[i] = i == minLevel && target0 != null ? target0 : c.createImage2d(w, h, f, new CLMemory.Mem[0]);
            this.transformedCL[i] = c.createImage2d(w, h, f, new CLMemory.Mem[0]);
            this.residualCL[i] = c.createImage2d(w, h, f, new CLMemory.Mem[0]);
            gl.glBindRenderbuffer(36161, this.maskrb[i]);
            gl.glBindFramebuffer(36160, this.maskfb[i]);
            gl.glRenderbufferStorage(36161, 32832, w, h);
            gl.glFramebufferRenderbuffer(36160, 36064, 36161, this.maskrb[i]);
            assert (gl.glCheckFramebufferStatus(36160) == 36053);
            this.maskCL[i] = c.createFromGLRenderbuffer(this.maskrb[i], new CLMemory.Mem[0]);
            System.out.println(this.maskCL[i] + " " + this.maskCL[i].getElementSize() + " " + this.maskCL[i].getFormat());
            w /= 2;
            h /= 2;
        }
        this.inputData = new ImageTransformerCL.InputData();
        this.outputData = new ImageTransformerCL.OutputData(false);
        this.templateChanged = new boolean[maxLevel + 1];
        Arrays.fill(this.templateChanged, true);
        this.setConstrained(settings.constrained);
        this.setTemplateImageCL(template0, roiPts);
        this.setTargetImageCL(target0);
    }

    public void release() {
        int minLevel = this.settings.pyramidLevelMin;
        int maxLevel = this.settings.pyramidLevelMax;
        if (this.templateCL != null && this.targetCL != null && this.transformedCL != null && this.residualCL != null && this.maskCL != null) {
            for (int i = minLevel; i <= maxLevel; ++i) {
                if (i > minLevel) {
                    this.templateCL[i].release();
                }
                if (i > minLevel) {
                    this.targetCL[i].release();
                }
                this.transformedCL[i].release();
                this.residualCL[i].release();
                this.maskCL[i].release();
            }
            this.maskCL = null;
            this.residualCL = null;
            this.transformedCL = null;
            this.targetCL = null;
            this.templateCL = null;
        }
        this.context.getGLContext().makeCurrent();
        GL2 gl = this.context.getGL2();
        if (this.maskfb != null) {
            gl.glDeleteFramebuffers(maxLevel + 1, this.maskfb, 0);
            this.maskfb = null;
        }
        if (this.maskrb != null) {
            gl.glDeleteRenderbuffers(maxLevel + 1, this.maskrb, 0);
            this.maskrb = null;
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.release();
    }

    @Override
    public IplImage getTemplateImage() {
        return this.getTemplateImage(true);
    }

    public IplImage getTemplateImage(boolean blocking) {
        if (this.templateChanged[this.pyramidLevel]) {
            this.templateChanged[this.pyramidLevel] = false;
            this.template[this.pyramidLevel] = this.context.readImage(this.getTemplateImageCL(), this.template[this.pyramidLevel], blocking);
            return this.template[this.pyramidLevel];
        }
        return this.template[this.pyramidLevel];
    }

    @Override
    public void setTemplateImage(IplImage template0, double[] roiPts) {
        this.context.writeImage(this.templateCL[this.settings.pyramidLevelMin], template0, false);
        this.setTemplateImageCL(this.templateCL[this.settings.pyramidLevelMin], roiPts);
    }

    @Override
    public IplImage getTargetImage() {
        return this.getTargetImage(true);
    }

    public IplImage getTargetImage(boolean blocking) {
        this.target[this.pyramidLevel] = this.context.readImage(this.getTargetImageCL(), this.target[this.pyramidLevel], blocking);
        return this.target[this.pyramidLevel];
    }

    @Override
    public void setTargetImage(IplImage target0) {
        this.context.writeImage(this.targetCL[this.settings.pyramidLevelMin], target0, false);
        this.setTargetImageCL(this.targetCL[this.settings.pyramidLevelMin]);
    }

    @Override
    public IplImage getTransformedImage() {
        return this.getTransformedImage(true);
    }

    public IplImage getTransformedImage(boolean blocking) {
        this.transformed[this.pyramidLevel] = this.context.readImage(this.getTransformedImageCL(), this.transformed[this.pyramidLevel], blocking);
        return this.transformed[this.pyramidLevel];
    }

    @Override
    public IplImage getResidualImage() {
        return this.getResidualImage(true);
    }

    public IplImage getResidualImage(boolean blocking) {
        this.residual[this.pyramidLevel] = this.context.readImage(this.getResidualImageCL(), this.residual[this.pyramidLevel], blocking);
        return this.residual[this.pyramidLevel];
    }

    @Override
    public IplImage getMaskImage() {
        return this.getMaskImage(true);
    }

    public IplImage getMaskImage(boolean blocking) {
        this.context.acquireGLObject((CLObject)this.maskCL[this.pyramidLevel]);
        this.mask[this.pyramidLevel] = this.context.readImage(this.getMaskImageCL(), this.mask[this.pyramidLevel], blocking);
        this.context.releaseGLObject((CLObject)this.maskCL[this.pyramidLevel]);
        return this.mask[this.pyramidLevel];
    }

    @Override
    public double getRMSE() {
        if (this.residualUpdateNeeded) {
            this.doRoi();
            this.doResidual();
        }
        return this.RMSE;
    }

    @Override
    public int getPixelCount() {
        if (this.residualUpdateNeeded) {
            this.doRoi();
            this.doResidual();
        }
        return this.outputData.dstCount;
    }

    @Override
    public int getOutlierCount() {
        return this.outputData.dstCountOutlier;
    }

    @Override
    public CvRect getRoi() {
        if (this.residualUpdateNeeded) {
            this.doRoi();
        }
        return this.roi.x(this.inputData.roiX).y(this.inputData.roiY).width(this.inputData.roiWidth).height(this.inputData.roiHeight);
    }

    @Override
    public IplImage[] getImages() {
        return this.getImages(true);
    }

    public IplImage[] getImages(boolean blocking) {
        this.images[0] = this.getTemplateImage(false);
        this.images[1] = this.getTargetImage(false);
        this.images[2] = this.getTransformedImage(false);
        this.images[3] = this.getResidualImage(false);
        this.images[4] = this.getMaskImage(blocking);
        return this.images;
    }

    @Override
    public CLImage2d getTemplateImageCL() {
        return this.templateCL[this.pyramidLevel];
    }

    @Override
    public void setTemplateImageCL(CLImage2d template0, double[] roiPts) {
        int minLevel = this.settings.pyramidLevelMin;
        int maxLevel = this.settings.pyramidLevelMax;
        if (roiPts == null && template0 != null) {
            int w = template0.width << minLevel;
            int h = template0.height << minLevel;
            this.srcRoiPts.put(new double[]{0.0, 0.0, w, 0.0, w, h, 0.0, h});
        } else {
            this.srcRoiPts.put(roiPts);
        }
        if (template0 == null) {
            return;
        }
        this.templateCL[minLevel] = template0;
        for (int i = minLevel + 1; i <= maxLevel; ++i) {
            this.context.pyrDown(this.templateCL[i - 1], this.templateCL[i]);
        }
        this.setPyramidLevel(maxLevel);
        Arrays.fill(this.templateChanged, true);
    }

    @Override
    public CLImage2d getTargetImageCL() {
        return this.targetCL[this.pyramidLevel];
    }

    @Override
    public void setTargetImageCL(CLImage2d target0) {
        int minLevel = this.settings.pyramidLevelMin;
        int maxLevel = this.settings.pyramidLevelMax;
        this.targetCL[minLevel] = target0;
        for (int i = minLevel + 1; i <= maxLevel; ++i) {
            this.context.pyrDown(this.targetCL[i - 1], this.targetCL[i]);
        }
        this.setPyramidLevel(maxLevel);
    }

    @Override
    public CLImage2d getTransformedImageCL() {
        if (this.residualUpdateNeeded) {
            this.doRoi();
            this.doResidual();
        }
        return this.transformedCL[this.pyramidLevel];
    }

    @Override
    public CLImage2d getResidualImageCL() {
        if (this.residualUpdateNeeded) {
            this.doRoi();
            this.doResidual();
        }
        return this.residualCL[this.pyramidLevel];
    }

    @Override
    public CLImage2d getMaskImageCL() {
        return this.maskCL[this.pyramidLevel];
    }

    @Override
    public CLImage2d[] getImagesCL() {
        this.imagesCL[0] = this.templateCL[this.pyramidLevel];
        this.imagesCL[1] = this.targetCL[this.pyramidLevel];
        this.imagesCL[2] = this.transformedCL[this.pyramidLevel];
        this.imagesCL[3] = this.residualCL[this.pyramidLevel];
        this.imagesCL[4] = this.maskCL[this.pyramidLevel];
        return this.imagesCL;
    }

    @Override
    protected void doHessianGradient(final double[] scale) {
        final double constraintError = this.parameters.getConstraintError();
        final double stepSize = this.settings.stepSize;
        opencv_core.cvSetZero((CvArr)this.gradient);
        opencv_core.cvSetZero((CvArr)this.hessian);
        Parallel.loop(0, this.n, new Parallel.Looper(){

            @Override
            public void loop(int from, int to, int looperID) {
                for (int i = from; i < to; ++i) {
                    GNImageAlignerCL.this.tempParameters[i].set(GNImageAlignerCL.this.parameters);
                    GNImageAlignerCL.this.tempParameters[i].set(i, GNImageAlignerCL.this.tempParameters[i].get(i) + stepSize);
                    scale[i] = GNImageAlignerCL.this.tempParameters[i].get(i) - GNImageAlignerCL.this.parameters.get(i);
                    GNImageAlignerCL.this.constraintGrad[i] = GNImageAlignerCL.this.tempParameters[i].getConstraintError() - constraintError;
                }
            }
        });
        this.inputData.zeroThreshold = this.settings.thresholdsZero[Math.min(this.settings.thresholdsZero.length - 1, this.pyramidLevel)];
        this.inputData.outlierThreshold = this.settings.thresholdsOutlier[Math.min(this.settings.thresholdsOutlier.length - 1, this.pyramidLevel)];
        if (this.settings.thresholdsMulRMSE) {
            this.inputData.zeroThreshold *= this.RMSE;
            this.inputData.outlierThreshold *= this.RMSE;
        }
        this.inputData.pyramidLevel = this.pyramidLevel;
        this.context.acquireGLObject((CLObject)this.maskCL[this.pyramidLevel]);
        ((ImageTransformerCL)this.transformer).transform(this.templateCL[this.pyramidLevel], this.transformedCL[this.pyramidLevel], this.residualCL[this.pyramidLevel], null, null, (CLImage2d)this.maskCL[this.pyramidLevel], this.tempParameters, null, this.inputData, this.outputData);
        this.context.releaseGLObject((CLObject)this.maskCL[this.pyramidLevel]);
        this.doRegularization(this.updateScale);
        this.outputData.readBuffer(this.context);
        for (int i = 0; i < this.n; ++i) {
            this.gradient.put(i, this.gradient.get(i) - (double)this.outputData.srcDstDot.get(i));
            for (int j = 0; j < this.n; ++j) {
                this.hessian.put(i, j, this.hessian.get(i, j) + (double)this.outputData.dstDstDot.get(i * this.n + j));
            }
        }
    }

    @Override
    protected void doRoi() {
        this.transformer.transform(this.srcRoiPts, this.dstRoiPts, this.parameters, false);
        double[] pts = this.dstRoiPts.get();
        int i = 0;
        while (i < pts.length) {
            int n = i++;
            pts[n] = pts[n] / (double)(1 << this.pyramidLevel);
        }
        this.roi.x(0).y(0).width(this.maskCL[this.pyramidLevel].width).height(this.maskCL[this.pyramidLevel].height);
        JavaCV.boundingRect(pts, this.roi, 3, 3, 16, 1);
        this.inputData.roiX = this.roi.x();
        this.inputData.roiY = this.roi.y();
        this.inputData.roiWidth = this.roi.width();
        this.inputData.roiHeight = this.roi.height();
        GL2 gl = this.context.getGL2();
        gl.glBindFramebuffer(36160, this.maskfb[this.pyramidLevel]);
        gl.glMatrixMode(5889);
        gl.glLoadIdentity();
        this.context.getGLU().gluOrtho2D(0.0f, (float)this.maskCL[this.pyramidLevel].width, 0.0f, (float)this.maskCL[this.pyramidLevel].height);
        gl.glMatrixMode(5888);
        gl.glLoadIdentity();
        gl.glViewport(0, 0, this.maskCL[this.pyramidLevel].width, this.maskCL[this.pyramidLevel].height);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClear(16384);
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glBegin(9);
        gl.glVertex2d(pts[0], pts[1]);
        gl.glVertex2d(pts[2] + 1.0, pts[3]);
        gl.glVertex2d(pts[4] + 1.0, pts[5] + 1.0);
        gl.glVertex2d(pts[6], pts[7] + 1.0);
        gl.glEnd();
    }

    @Override
    protected void doResidual() {
        this.parameters.getConstraintError();
        this.inputData.zeroThreshold = this.settings.thresholdsZero[Math.min(this.settings.thresholdsZero.length - 1, this.pyramidLevel)];
        this.inputData.outlierThreshold = this.settings.thresholdsOutlier[Math.min(this.settings.thresholdsOutlier.length - 1, this.pyramidLevel)];
        if (this.settings.thresholdsMulRMSE) {
            this.inputData.zeroThreshold *= this.RMSE;
            this.inputData.outlierThreshold *= this.RMSE;
        }
        this.inputData.pyramidLevel = this.pyramidLevel;
        this.context.acquireGLObject((CLObject)this.maskCL[this.pyramidLevel]);
        ((ImageTransformerCL)this.transformer).transform(this.templateCL[this.pyramidLevel], this.targetCL[this.pyramidLevel], null, this.transformedCL[this.pyramidLevel], this.residualCL[this.pyramidLevel], (CLImage2d)this.maskCL[this.pyramidLevel], this.parametersArray, null, this.inputData, this.outputData);
        this.context.releaseGLObject((CLObject)this.maskCL[this.pyramidLevel]);
        this.outputData.readBuffer(this.context);
        double dstDstDot = this.outputData.dstDstDot.get(0);
        int dstCount = this.outputData.dstCount;
        this.RMSE = dstCount < this.n ? Double.NaN : Math.sqrt(dstDstDot / (double)dstCount);
        this.residualUpdateNeeded = false;
    }
}

