/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jogamp.common.os.Platform
 *  com.jogamp.opencl.CLBuffer
 *  com.jogamp.opencl.CLEventList
 *  com.jogamp.opencl.CLImage2d
 *  com.jogamp.opencl.CLImageFormat
 *  com.jogamp.opencl.CLImageFormat$ChannelOrder
 *  com.jogamp.opencl.CLImageFormat$ChannelType
 *  com.jogamp.opencl.CLKernel
 *  com.jogamp.opencl.CLMemory
 *  com.jogamp.opencl.CLMemory$Mem
 *  org.bytedeco.opencv.opencv_core.CvMat
 */
package org.bytedeco.javacv;

import com.jogamp.common.os.Platform;
import com.jogamp.opencl.CLBuffer;
import com.jogamp.opencl.CLEventList;
import com.jogamp.opencl.CLImage2d;
import com.jogamp.opencl.CLImageFormat;
import com.jogamp.opencl.CLKernel;
import com.jogamp.opencl.CLMemory;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.bytedeco.javacv.CameraDevice;
import org.bytedeco.javacv.ImageTransformer;
import org.bytedeco.javacv.ImageTransformerCL;
import org.bytedeco.javacv.JavaCVCL;
import org.bytedeco.javacv.ProCamTransformer;
import org.bytedeco.javacv.ProjectiveColorTransformerCL;
import org.bytedeco.javacv.ProjectorDevice;
import org.bytedeco.opencv.opencv_core.CvMat;

public class ProCamTransformerCL
extends ProCamTransformer
implements ImageTransformerCL {
    private static final ThreadLocal<CvMat> H13x3 = CvMat.createThreadLocal((int)3, (int)3);
    private static final ThreadLocal<CvMat> H23x3 = CvMat.createThreadLocal((int)3, (int)3);
    private static final ThreadLocal<CvMat> X4x4 = CvMat.createThreadLocal((int)4, (int)4);
    protected final JavaCVCL context;
    protected final int nullSize;
    protected final CLBuffer<FloatBuffer> H1Buffer;
    protected final CLBuffer<FloatBuffer> H2Buffer;
    protected final CLBuffer<FloatBuffer> XBuffer;
    protected CLImage2d[] projectorImageCL = null;
    protected CLImage2d[] surfaceImageCL = null;
    private CLKernel oneKernel;
    private CLKernel subKernel;
    private CLKernel dotKernel;
    private CLKernel reduceKernel;

    public ProCamTransformerCL(JavaCVCL context, double[] referencePoints, CameraDevice camera, ProjectorDevice projector) {
        this(context, referencePoints, camera, projector, null);
    }

    public ProCamTransformerCL(JavaCVCL context, double[] referencePoints, CameraDevice camera, ProjectorDevice projector, CvMat n) {
        super(referencePoints, camera, projector, n);
        int dotSize = this.createParameters().size();
        this.context = context;
        this.nullSize = Platform.is32Bit() ? 4 : 8;
        this.H1Buffer = this.surfaceTransformer == null ? null : context.getCLContext().createFloatBuffer(dotSize * 9, new CLMemory.Mem[]{CLMemory.Mem.READ_ONLY});
        this.H2Buffer = context.getCLContext().createFloatBuffer(dotSize * 9, new CLMemory.Mem[]{CLMemory.Mem.READ_ONLY});
        this.XBuffer = context.getCLContext().createFloatBuffer(dotSize * 16, new CLMemory.Mem[]{CLMemory.Mem.READ_ONLY});
        if (this.getClass() == ProCamTransformerCL.class) {
            CLKernel[] kernels = context.buildKernels("-cl-fast-relaxed-math -cl-mad-enable -cl-nv-maxrregcount=32 -DDOT_SIZE=" + dotSize, "ImageTransformer.cl:ProCamTransformer.cl", "transformOne", "transformSub", "transformDot", "reduceOutputData");
            this.oneKernel = kernels[0];
            this.subKernel = kernels[1];
            this.dotKernel = kernels[2];
            this.reduceKernel = kernels[3];
        }
    }

    @Override
    public JavaCVCL getContext() {
        return this.context;
    }

    public ProjectiveColorTransformerCL getSurfaceTransformerCL() {
        return (ProjectiveColorTransformerCL)this.surfaceTransformer;
    }

    public ProjectiveColorTransformerCL getProjectorTransformerCL() {
        return (ProjectiveColorTransformerCL)this.projectorTransformer;
    }

    public CLImage2d getProjectorImageCL(int pyramidLevel) {
        return this.projectorImageCL[pyramidLevel];
    }

    public void setProjectorImageCL(CLImage2d projectorImage0, int minPyramidLevel, int maxPyramidLevel) {
        if (this.projectorImageCL == null || this.projectorImageCL.length != maxPyramidLevel + 1) {
            this.projectorImageCL = new CLImage2d[maxPyramidLevel + 1];
        }
        this.projectorImageCL[minPyramidLevel] = projectorImage0;
        for (int i = minPyramidLevel + 1; i <= maxPyramidLevel; ++i) {
            if (this.projectorImageCL[i] == null) {
                int w = this.projectorImageCL[i - 1].width / 2;
                int h = this.projectorImageCL[i - 1].height / 2;
                CLImageFormat format = new CLImageFormat(CLImageFormat.ChannelOrder.RGBA, CLImageFormat.ChannelType.FLOAT);
                this.projectorImageCL[i] = this.context.getCLContext().createImage2d(w, h, format, new CLMemory.Mem[0]);
            }
            this.context.pyrDown(this.projectorImageCL[i - 1], this.projectorImageCL[i]);
        }
    }

    public CLImage2d getSurfaceImageCL(int pyramidLevel) {
        return this.surfaceImageCL[pyramidLevel];
    }

    public void setSurfaceImageCL(CLImage2d surfaceImage0, int pyramidLevels) {
        if (this.surfaceImageCL == null || this.surfaceImageCL.length != pyramidLevels) {
            this.surfaceImageCL = new CLImage2d[pyramidLevels];
        }
        this.surfaceImageCL[0] = surfaceImage0;
        for (int i = 1; i < pyramidLevels; ++i) {
            if (this.surfaceImageCL[i] == null) {
                int w = this.surfaceImageCL[i - 1].width / 2;
                int h = this.surfaceImageCL[i - 1].height / 2;
                CLImageFormat format = new CLImageFormat(CLImageFormat.ChannelOrder.RGBA, CLImageFormat.ChannelType.FLOAT);
                this.surfaceImageCL[i] = this.context.getCLContext().createImage2d(w, h, format, new CLMemory.Mem[0]);
            }
            this.context.pyrDown(this.surfaceImageCL[i - 1], this.surfaceImageCL[i]);
        }
    }

    protected void prepareTransforms(CLBuffer H1Buffer, CLBuffer H2Buffer, CLBuffer XBuffer, int pyramidLevel, ImageTransformer.Parameters[] parameters) {
        FloatBuffer floatH1 = this.surfaceTransformer == null ? null : (FloatBuffer)H1Buffer.getBuffer().rewind();
        FloatBuffer floatH2 = (FloatBuffer)H2Buffer.getBuffer().rewind();
        FloatBuffer floatX = (FloatBuffer)XBuffer.getBuffer().rewind();
        CvMat H1 = H13x3.get();
        CvMat H2 = H23x3.get();
        CvMat X = X4x4.get();
        for (int i = 0; i < parameters.length; ++i) {
            int j;
            this.prepareTransforms(this.surfaceTransformer == null ? null : H1, H2, X, pyramidLevel, (ProCamTransformer.Parameters)parameters[i]);
            for (j = 0; j < 9; ++j) {
                if (this.surfaceTransformer != null) {
                    floatH1.put((float)H1.get(j));
                }
                floatH2.put((float)H2.get(j));
            }
            for (j = 0; j < 16; ++j) {
                floatX.put((float)X.get(j));
            }
        }
        if (this.surfaceTransformer != null) {
            floatH1.rewind();
        }
        floatH2.rewind();
        floatX.rewind();
    }

    @Override
    public void transform(CLImage2d srcImg, CLImage2d subImg, CLImage2d srcDotImg, CLImage2d transImg, CLImage2d dstImg, CLImage2d maskImg, ImageTransformer.Parameters[] parameters, boolean[] inverses, ImageTransformerCL.InputData inputData, ImageTransformerCL.OutputData outputData) {
        if (inverses != null) {
            for (int i = 0; i < inverses.length; ++i) {
                if (!inverses[i]) continue;
                throw new UnsupportedOperationException("Inverse transform not supported.");
            }
        }
        this.prepareTransforms(this.H1Buffer, this.H2Buffer, this.XBuffer, inputData.pyramidLevel, parameters);
        int dotSize = parameters[0].size();
        int localSize = parameters.length > 1 ? parameters.length : (inputData.roiWidth > 32 ? 64 : 32);
        int globalSize = JavaCVCL.alignCeil(inputData.roiWidth, localSize);
        int reduceSize = globalSize / localSize;
        CLBuffer<ByteBuffer> inputBuffer = inputData.getBuffer(this.context);
        CLBuffer<ByteBuffer> outputBuffer = outputData.getBuffer(this.context, dotSize, reduceSize);
        CLEventList list = new CLEventList(1);
        if (this.surfaceTransformer != null) {
            this.context.writeBuffer(this.H1Buffer, false);
        }
        this.context.writeBuffer(this.H2Buffer, false);
        this.context.writeBuffer(this.XBuffer, false);
        if (inputData.autoWrite) {
            inputData.writeBuffer(this.context);
        }
        CLImage2d srcImg2 = this.projectorImageCL[inputData.pyramidLevel];
        CLKernel kernel = null;
        if (subImg == null) {
            assert (parameters.length == 1);
            kernel = this.oneKernel.putArg((CLMemory)srcImg2).putArg((CLMemory)srcImg).putArg((CLMemory)(dstImg == null ? transImg : dstImg)).putArg((CLMemory)maskImg).putArg(this.H2Buffer);
        } else if (srcDotImg == null) {
            assert (parameters.length == 1);
            kernel = this.subKernel.putArg((CLMemory)srcImg2).putArg((CLMemory)srcImg).putArg((CLMemory)subImg).putArg((CLMemory)transImg).putArg((CLMemory)dstImg).putArg((CLMemory)maskImg).putArg(this.H2Buffer);
        } else {
            assert (parameters.length == dotSize);
            kernel = this.dotKernel.putArg((CLMemory)srcImg2).putArg((CLMemory)srcImg).putArg((CLMemory)subImg).putArg((CLMemory)srcDotImg).putArg((CLMemory)maskImg).putArg(this.H2Buffer);
        }
        if (this.H1Buffer != null) {
            kernel.putArg(this.H1Buffer);
        } else {
            kernel.putNullArg(this.nullSize);
        }
        kernel.putArg(this.XBuffer).putArg(inputBuffer).putArg(outputBuffer).rewind();
        this.context.executeKernel(kernel, inputData.roiX, 0L, 0L, globalSize, 1L, parameters.length, localSize, 1L, parameters.length, list);
        if (reduceSize > 1) {
            this.reduceKernel.putArg(outputBuffer).rewind();
            this.context.executeKernel(this.reduceKernel, 0L, reduceSize, reduceSize);
        }
        if (outputData.autoRead) {
            outputData.readBuffer(this.context);
        }
    }
}

