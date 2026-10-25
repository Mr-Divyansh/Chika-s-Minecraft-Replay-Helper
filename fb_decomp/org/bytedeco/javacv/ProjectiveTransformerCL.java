/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jogamp.opencl.CLBuffer
 *  com.jogamp.opencl.CLEventList
 *  com.jogamp.opencl.CLImage2d
 *  com.jogamp.opencl.CLKernel
 *  com.jogamp.opencl.CLMemory
 *  com.jogamp.opencl.CLMemory$Mem
 *  org.bytedeco.opencv.opencv_core.CvMat
 */
package org.bytedeco.javacv;

import com.jogamp.opencl.CLBuffer;
import com.jogamp.opencl.CLEventList;
import com.jogamp.opencl.CLImage2d;
import com.jogamp.opencl.CLKernel;
import com.jogamp.opencl.CLMemory;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.bytedeco.javacv.ImageTransformer;
import org.bytedeco.javacv.ImageTransformerCL;
import org.bytedeco.javacv.JavaCVCL;
import org.bytedeco.javacv.ProjectiveDevice;
import org.bytedeco.javacv.ProjectiveTransformer;
import org.bytedeco.opencv.opencv_core.CvMat;

public class ProjectiveTransformerCL
extends ProjectiveTransformer
implements ImageTransformerCL {
    protected final JavaCVCL context;
    protected final CLBuffer<FloatBuffer> HBuffer;
    private CLKernel oneKernel;
    private CLKernel subKernel;
    private CLKernel dotKernel;
    private CLKernel reduceKernel;

    public ProjectiveTransformerCL(JavaCVCL context) {
        this(context, null, null, null, null, null, new double[0], null);
    }

    public ProjectiveTransformerCL(JavaCVCL context, double[] referencePoints) {
        this(context, null, null, null, null, null, referencePoints, null);
    }

    public ProjectiveTransformerCL(JavaCVCL context, ProjectiveDevice d1, ProjectiveDevice d2, CvMat n, double[] referencePoints1, double[] referencePoints2) {
        this(context, d1.cameraMatrix, d2.cameraMatrix, d2.R, d2.T, n, referencePoints1, referencePoints2);
    }

    public ProjectiveTransformerCL(JavaCVCL context, CvMat K1, CvMat K2, CvMat R, CvMat t, CvMat n, double[] referencePoints1, double[] referencePoints2) {
        super(K1, K2, R, t, n, referencePoints1, referencePoints2);
        int dotSize = this.createParameters().size();
        this.context = context;
        this.HBuffer = context.getCLContext().createFloatBuffer(dotSize * 9, new CLMemory.Mem[]{CLMemory.Mem.READ_ONLY});
        if (this.getClass() == ProjectiveTransformerCL.class) {
            CLKernel[] kernels = context.buildKernels("-cl-fast-relaxed-math -cl-mad-enable -DDOT_SIZE=" + dotSize, "ImageTransformer.cl:ProjectiveTransformer.cl", "transformOne", "transformSub", "transformDot", "reduceOutputData");
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

    protected void prepareHomographies(CLBuffer HBuffer, int pyramidLevel, ImageTransformer.Parameters[] parameters, boolean[] inverses) {
        FloatBuffer floatH = (FloatBuffer)HBuffer.getBuffer().rewind();
        CvMat H = (CvMat)H3x3.get();
        for (int i = 0; i < parameters.length; ++i) {
            this.prepareHomography(H, pyramidLevel, (ProjectiveTransformer.Parameters)parameters[i], inverses == null ? false : inverses[i]);
            for (int j = 0; j < 9; ++j) {
                floatH.put((float)H.get(j));
            }
        }
        floatH.rewind();
    }

    @Override
    public void transform(CLImage2d srcImg, CLImage2d subImg, CLImage2d srcDotImg, CLImage2d transImg, CLImage2d dstImg, CLImage2d maskImg, ImageTransformer.Parameters[] parameters, boolean[] inverses, ImageTransformerCL.InputData inputData, ImageTransformerCL.OutputData outputData) {
        this.prepareHomographies(this.HBuffer, inputData.pyramidLevel, parameters, inverses);
        int dotSize = parameters[0].size();
        int localSize = parameters.length > 1 ? parameters.length : (inputData.roiWidth > 32 ? 64 : 32);
        int globalSize = JavaCVCL.alignCeil(inputData.roiWidth, localSize);
        int reduceSize = globalSize / localSize;
        CLBuffer<ByteBuffer> inputBuffer = inputData.getBuffer(this.context);
        CLBuffer<ByteBuffer> outputBuffer = outputData.getBuffer(this.context, dotSize, reduceSize);
        CLEventList list = new CLEventList(1);
        this.context.writeBuffer(this.HBuffer, false);
        if (inputData.autoWrite) {
            inputData.writeBuffer(this.context);
        }
        CLKernel kernel = null;
        if (subImg == null) {
            assert (parameters.length == 1);
            kernel = this.oneKernel.putArg((CLMemory)srcImg).putArg((CLMemory)(dstImg == null ? transImg : dstImg)).putArg((CLMemory)maskImg).putArg(this.HBuffer).putArg(inputBuffer).putArg(outputBuffer).rewind();
        } else if (srcDotImg == null) {
            assert (parameters.length == 1);
            kernel = this.subKernel.putArg((CLMemory)srcImg).putArg((CLMemory)subImg).putArg((CLMemory)transImg).putArg((CLMemory)dstImg).putArg((CLMemory)maskImg).putArg(this.HBuffer).putArg(inputBuffer).putArg(outputBuffer).rewind();
        } else {
            assert (parameters.length == dotSize);
            kernel = this.dotKernel.putArg((CLMemory)srcImg).putArg((CLMemory)subImg).putArg((CLMemory)srcDotImg).putArg((CLMemory)maskImg).putArg(this.HBuffer).putArg(inputBuffer).putArg(outputBuffer).rewind();
        }
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

