/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jogamp.opencl.CLBuffer
 *  com.jogamp.opencl.CLImage2d
 *  com.jogamp.opencl.CLMemory$Mem
 */
package org.bytedeco.javacv;

import com.jogamp.opencl.CLBuffer;
import com.jogamp.opencl.CLImage2d;
import com.jogamp.opencl.CLMemory;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.bytedeco.javacv.ImageTransformer;
import org.bytedeco.javacv.JavaCVCL;

public interface ImageTransformerCL
extends ImageTransformer {
    public JavaCVCL getContext();

    public void transform(CLImage2d var1, CLImage2d var2, CLImage2d var3, CLImage2d var4, CLImage2d var5, CLImage2d var6, ImageTransformer.Parameters[] var7, boolean[] var8, InputData var9, OutputData var10);

    public static class OutputData {
        public int dstCount = 0;
        public int dstCountZero = 0;
        public int dstCountOutlier = 0;
        public FloatBuffer srcDstDot = null;
        public FloatBuffer dstDstDot = null;
        CLBuffer<ByteBuffer> buffer = null;
        boolean autoRead = true;

        public OutputData() {
            this(true);
        }

        public OutputData(boolean autoRead) {
            this.autoRead = autoRead;
        }

        CLBuffer<ByteBuffer> getBuffer(JavaCVCL context, int dotSize, int reduceSize) {
            int structSize = 4 * (4 + dotSize + dotSize * dotSize);
            if (this.buffer == null || this.buffer.getCLSize() < (long)(structSize * reduceSize)) {
                if (this.buffer != null) {
                    this.buffer.release();
                }
                this.buffer = context.getCLContext().createByteBuffer(structSize * reduceSize, new CLMemory.Mem[0]);
                ByteBuffer byteBuffer = (ByteBuffer)this.buffer.getBuffer();
                byteBuffer.position(16);
                this.srcDstDot = byteBuffer.asFloatBuffer();
                byteBuffer.position(4 * (4 + dotSize));
                this.dstDstDot = byteBuffer.asFloatBuffer();
                byteBuffer.rewind();
            }
            return this.buffer;
        }

        public CLBuffer<ByteBuffer> readBuffer(JavaCVCL context) {
            context.readBuffer(this.buffer, true);
            ByteBuffer byteBuffer = (ByteBuffer)this.buffer.getBuffer();
            this.dstCount = byteBuffer.getInt(4);
            this.dstCountZero = byteBuffer.getInt(8);
            this.dstCountOutlier = byteBuffer.getInt(12);
            return this.buffer;
        }
    }

    public static class InputData {
        public int pyramidLevel = 0;
        public int roiX = 0;
        public int roiY = 0;
        public int roiWidth = 0;
        public int roiHeight = 0;
        public double zeroThreshold = 0.0;
        public double outlierThreshold = 0.0;
        CLBuffer<ByteBuffer> buffer = null;
        boolean autoWrite = true;

        public InputData() {
            this(true);
        }

        public InputData(boolean autoWrite) {
            this.autoWrite = autoWrite;
        }

        CLBuffer<ByteBuffer> getBuffer(JavaCVCL context) {
            int structSize = 16;
            if (this.buffer == null || this.buffer.getCLSize() < (long)structSize) {
                if (this.buffer != null) {
                    this.buffer.release();
                }
                this.buffer = context.getCLContext().createByteBuffer(structSize, new CLMemory.Mem[]{CLMemory.Mem.READ_ONLY});
            }
            return this.buffer;
        }

        public CLBuffer<ByteBuffer> writeBuffer(JavaCVCL context) {
            this.getBuffer(context);
            ByteBuffer byteBuffer = (ByteBuffer)((ByteBuffer)this.buffer.getBuffer()).rewind();
            byteBuffer.putInt(this.roiY).putInt(this.roiHeight).putFloat((float)this.zeroThreshold).putFloat((float)this.outlierThreshold).rewind();
            context.writeBuffer(this.buffer, false);
            return this.buffer;
        }
    }
}

