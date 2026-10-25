/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.opencv_core.IplImage
 *  org.bytedeco.opencv.opencv_core.Mat
 *  org.opencv.core.Mat
 */
package org.bytedeco.javacv;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.opencv.core.Mat;

public abstract class OpenCVFrameConverter<F>
extends FrameConverter<F> {
    IplImage img;
    org.bytedeco.opencv.opencv_core.Mat mat;
    Mat orgOpenCvCoreMat;

    public static int getFrameDepth(int depth) {
        switch (depth) {
            case 0: 
            case 8: {
                return 8;
            }
            case -2147483640: 
            case 1: {
                return -8;
            }
            case 2: 
            case 16: {
                return 16;
            }
            case -2147483632: 
            case 3: {
                return -16;
            }
            case 5: 
            case 32: {
                return 32;
            }
            case -2147483616: 
            case 4: {
                return -32;
            }
            case 6: 
            case 64: {
                return 64;
            }
        }
        return -1;
    }

    public static int getIplImageDepth(int depth) {
        switch (depth) {
            case 8: {
                return 8;
            }
            case -8: {
                return -2147483640;
            }
            case 16: {
                return 16;
            }
            case -16: {
                return -2147483632;
            }
            case 32: {
                return 32;
            }
            case -32: {
                return -2147483616;
            }
            case 64: {
                return 64;
            }
        }
        return -1;
    }

    static boolean isEqual(Frame frame, IplImage img) {
        return img != null && frame != null && frame.image != null && frame.image.length > 0 && frame.imageWidth == img.width() && frame.imageHeight == img.height() && frame.imageChannels == img.nChannels() && OpenCVFrameConverter.getIplImageDepth(frame.imageDepth) == img.depth() && new Pointer(frame.image[0].position(0)).address() == img.imageData().address() && frame.imageStride * Math.abs(frame.imageDepth) / 8 == img.widthStep();
    }

    public IplImage convertToIplImage(Frame frame) {
        if (frame == null || frame.image == null) {
            return null;
        }
        if (frame.opaque instanceof IplImage) {
            return (IplImage)frame.opaque;
        }
        if (!OpenCVFrameConverter.isEqual(frame, this.img)) {
            int depth = OpenCVFrameConverter.getIplImageDepth(frame.imageDepth);
            if (this.img != null) {
                this.img.releaseReference();
            }
            this.img = depth < 0 ? null : (IplImage)IplImage.create((int)frame.imageWidth, (int)frame.imageHeight, (int)depth, (int)frame.imageChannels, (Pointer)new Pointer(frame.image[0].position(0))).widthStep(frame.imageStride * Math.abs(frame.imageDepth) / 8).imageSize(frame.image[0].capacity() * Math.abs(frame.imageDepth) / 8).retainReference();
        }
        return this.img;
    }

    @Override
    public Frame convert(IplImage img) {
        if (img == null) {
            return null;
        }
        if (!OpenCVFrameConverter.isEqual(this.frame, img)) {
            this.frame = new Frame();
            this.frame.imageWidth = img.width();
            this.frame.imageHeight = img.height();
            this.frame.imageDepth = OpenCVFrameConverter.getFrameDepth(img.depth());
            this.frame.imageChannels = img.nChannels();
            this.frame.imageStride = img.widthStep() * 8 / Math.abs(this.frame.imageDepth);
            this.frame.image = new Buffer[]{img.createBuffer()};
        }
        this.frame.opaque = img;
        return this.frame;
    }

    public static int getMatDepth(int depth) {
        switch (depth) {
            case 8: {
                return 0;
            }
            case -8: {
                return 1;
            }
            case 16: {
                return 2;
            }
            case -16: {
                return 3;
            }
            case 32: {
                return 5;
            }
            case -32: {
                return 4;
            }
            case 64: {
                return 6;
            }
        }
        return -1;
    }

    static boolean isEqual(Frame frame, org.bytedeco.opencv.opencv_core.Mat mat) {
        return mat != null && frame != null && frame.image != null && frame.image.length > 0 && frame.imageWidth == mat.cols() && frame.imageHeight == mat.rows() && frame.imageChannels == mat.channels() && OpenCVFrameConverter.getMatDepth(frame.imageDepth) == mat.depth() && new Pointer(frame.image[0].position(0)).address() == mat.data().address() && frame.imageStride * Math.abs(frame.imageDepth) / 8 == (int)mat.step();
    }

    public org.bytedeco.opencv.opencv_core.Mat convertToMat(Frame frame) {
        if (frame == null || frame.image == null) {
            return null;
        }
        if (frame.opaque instanceof org.bytedeco.opencv.opencv_core.Mat) {
            return (org.bytedeco.opencv.opencv_core.Mat)frame.opaque;
        }
        if (!OpenCVFrameConverter.isEqual(frame, this.mat)) {
            int depth = OpenCVFrameConverter.getMatDepth(frame.imageDepth);
            if (this.mat != null) {
                this.mat.releaseReference();
            }
            this.mat = depth < 0 ? null : (org.bytedeco.opencv.opencv_core.Mat)new org.bytedeco.opencv.opencv_core.Mat(frame.imageHeight, frame.imageWidth, opencv_core.CV_MAKETYPE((int)depth, (int)frame.imageChannels), new Pointer(frame.image[0].position(0)), (long)(frame.imageStride * Math.abs(frame.imageDepth) / 8)).retainReference();
        }
        return this.mat;
    }

    @Override
    public Frame convert(org.bytedeco.opencv.opencv_core.Mat mat) {
        if (mat == null) {
            return null;
        }
        if (!OpenCVFrameConverter.isEqual(this.frame, mat)) {
            this.frame = new Frame();
            this.frame.imageWidth = mat.cols();
            this.frame.imageHeight = mat.rows();
            this.frame.imageDepth = OpenCVFrameConverter.getFrameDepth(mat.depth());
            this.frame.imageChannels = mat.channels();
            this.frame.imageStride = (int)mat.step() * 8 / Math.abs(this.frame.imageDepth);
            this.frame.image = new Buffer[]{mat.createBuffer()};
        }
        this.frame.opaque = mat;
        return this.frame;
    }

    static boolean isEqual(Frame frame, Mat mat) {
        return mat != null && frame != null && frame.image != null && frame.image.length > 0 && frame.imageWidth == mat.cols() && frame.imageHeight == mat.rows() && frame.imageChannels == mat.channels() && OpenCVFrameConverter.getMatDepth(frame.imageDepth) == mat.depth() && new Pointer(frame.image[0].position(0)).address() == mat.dataAddr();
    }

    public Mat convertToOrgOpenCvCoreMat(Frame frame) {
        if (frame == null || frame.image == null) {
            return null;
        }
        if (frame.opaque instanceof Mat) {
            return (Mat)frame.opaque;
        }
        if (!OpenCVFrameConverter.isEqual(frame, this.mat)) {
            int depth = OpenCVFrameConverter.getMatDepth(frame.imageDepth);
            this.orgOpenCvCoreMat = depth < 0 ? null : new Mat(frame.imageHeight, frame.imageWidth, opencv_core.CV_MAKETYPE((int)depth, (int)frame.imageChannels), new BytePointer(new Pointer(frame.image[0].position(0))).capacity(frame.image[0].capacity() * Math.abs(frame.imageDepth) / 8).asByteBuffer(), (long)(frame.imageStride * Math.abs(frame.imageDepth) / 8));
        }
        return this.orgOpenCvCoreMat;
    }

    @Override
    public Frame convert(final Mat mat) {
        if (mat == null) {
            return null;
        }
        if (!OpenCVFrameConverter.isEqual(this.frame, mat)) {
            this.frame = new Frame();
            this.frame.imageWidth = mat.cols();
            this.frame.imageHeight = mat.rows();
            this.frame.imageDepth = OpenCVFrameConverter.getFrameDepth(mat.depth());
            this.frame.imageChannels = mat.channels();
            this.frame.imageStride = (int)mat.step1();
            ByteBuffer byteBuffer = new BytePointer(){
                {
                    this.address = mat.dataAddr();
                }
            }.capacity((long)mat.rows() * mat.step1() * mat.elemSize1()).asByteBuffer();
            switch (mat.depth()) {
                case 0: 
                case 1: {
                    this.frame.image = new Buffer[]{byteBuffer};
                    break;
                }
                case 2: 
                case 3: {
                    this.frame.image = new Buffer[]{byteBuffer.asShortBuffer()};
                    break;
                }
                case 5: {
                    this.frame.image = new Buffer[]{byteBuffer.asFloatBuffer()};
                    break;
                }
                case 4: {
                    this.frame.image = new Buffer[]{byteBuffer.asIntBuffer()};
                    break;
                }
                case 6: {
                    this.frame.image = new Buffer[]{byteBuffer.asDoubleBuffer()};
                    break;
                }
                default: {
                    this.frame.image = null;
                }
            }
        }
        this.frame.opaque = mat;
        return this.frame;
    }

    @Override
    public void close() {
        super.close();
        if (this.img != null) {
            this.img.releaseReference();
            this.img = null;
        }
        if (this.mat != null) {
            this.mat.releaseReference();
            this.mat = null;
        }
        if (this.orgOpenCvCoreMat != null) {
            this.orgOpenCvCoreMat.release();
            this.orgOpenCvCoreMat = null;
        }
    }

    static {
        Loader.load(opencv_core.class);
    }

    public static class ToOrgOpenCvCoreMat
    extends OpenCVFrameConverter<Mat> {
        @Override
        public Frame convert(Mat mat) {
            return super.convert(mat);
        }

        @Override
        public Mat convert(Frame frame) {
            return this.convertToOrgOpenCvCoreMat(frame);
        }
    }

    public static class ToMat
    extends OpenCVFrameConverter<org.bytedeco.opencv.opencv_core.Mat> {
        @Override
        public Frame convert(org.bytedeco.opencv.opencv_core.Mat mat) {
            return super.convert(mat);
        }

        @Override
        public org.bytedeco.opencv.opencv_core.Mat convert(Frame frame) {
            return this.convertToMat(frame);
        }
    }

    public static class ToIplImage
    extends OpenCVFrameConverter<IplImage> {
        @Override
        public Frame convert(IplImage img) {
            return super.convert(img);
        }

        @Override
        public IplImage convert(Frame frame) {
            return this.convertToIplImage(frame);
        }
    }
}

