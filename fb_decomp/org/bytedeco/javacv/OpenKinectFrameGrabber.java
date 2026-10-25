/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.libfreenect.freenect_context
 *  org.bytedeco.libfreenect.global.freenect
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.libfreenect.freenect_context;
import org.bytedeco.libfreenect.global.freenect;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.IplImage;

public class OpenKinectFrameGrabber
extends FrameGrabber {
    private static FrameGrabber.Exception loadingException = null;
    private int deviceNumber = 0;
    private boolean depth = false;
    private BytePointer rawDepthImageData = new BytePointer((Pointer)null);
    private BytePointer rawVideoImageData = new BytePointer((Pointer)null);
    private BytePointer rawIRImageData = new BytePointer((Pointer)null);
    private IplImage rawDepthImage = null;
    private IplImage rawVideoImage = null;
    private IplImage rawIRImage = null;
    private IplImage returnImage = null;
    private FrameConverter converter = new OpenCVFrameConverter.ToIplImage();
    private int[] timestamp = new int[]{0};
    private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
    private int depthFormat = -1;
    private int videoFormat = -1;

    public static String[] getDeviceDescriptions() throws FrameGrabber.Exception {
        OpenKinectFrameGrabber.tryLoad();
        freenect_context ctx = new freenect_context(null);
        int err = freenect.freenect_init((freenect_context)ctx, null);
        if (err < 0) {
            throw new FrameGrabber.Exception("freenect_init() Error " + err + ": Failed to init context.");
        }
        int count = freenect.freenect_num_devices((freenect_context)ctx);
        if (count < 0) {
            throw new FrameGrabber.Exception("freenect_num_devices() Error " + err + ": Failed to get number of devices.");
        }
        String[] descriptions = new String[count];
        for (int i = 0; i < descriptions.length; ++i) {
            descriptions[i] = "Kinect #" + i;
        }
        err = freenect.freenect_shutdown((freenect_context)ctx);
        if (err < 0) {
            throw new FrameGrabber.Exception("freenect_shutdown() Error " + err + ": Failed to shutdown context.");
        }
        return descriptions;
    }

    public static OpenKinectFrameGrabber createDefault(File deviceFile) throws FrameGrabber.Exception {
        throw new FrameGrabber.Exception(OpenKinectFrameGrabber.class + " does not support device files.");
    }

    public static OpenKinectFrameGrabber createDefault(String devicePath) throws FrameGrabber.Exception {
        throw new FrameGrabber.Exception(OpenKinectFrameGrabber.class + " does not support device paths.");
    }

    public static OpenKinectFrameGrabber createDefault(int deviceNumber) throws FrameGrabber.Exception {
        return new OpenKinectFrameGrabber(deviceNumber);
    }

    public static void tryLoad() throws FrameGrabber.Exception {
        if (loadingException != null) {
            throw loadingException;
        }
        try {
            Loader.load(freenect.class);
        }
        catch (Throwable t) {
            loadingException = new FrameGrabber.Exception("Failed to load " + OpenKinectFrameGrabber.class, t);
            throw loadingException;
        }
    }

    public OpenKinectFrameGrabber(int deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    @Override
    public void release() throws FrameGrabber.Exception {
        this.stop();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.release();
    }

    public ByteOrder getByteOrder() {
        return this.byteOrder;
    }

    public void setByteOrder(ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }

    public int getDepthFormat() {
        return this.depthFormat;
    }

    public void setDepthFormat(int depthFormat) {
        this.depthFormat = depthFormat;
    }

    public int getVideoFormat() {
        return this.videoFormat;
    }

    public void setVideoFormat(int videoFormat) {
        this.videoFormat = videoFormat;
    }

    @Override
    public double getGamma() {
        if (this.gamma == 0.0) {
            return 2.2;
        }
        return this.gamma;
    }

    @Override
    public void setImageMode(FrameGrabber.ImageMode imageMode) {
        if (imageMode != this.imageMode) {
            this.returnImage = null;
        }
        super.setImageMode(imageMode);
    }

    @Override
    public void start() throws FrameGrabber.Exception {
        this.depth = "depth".equalsIgnoreCase(this.format);
    }

    @Override
    public void stop() throws FrameGrabber.Exception {
        freenect.freenect_sync_stop();
    }

    @Override
    public void trigger() throws FrameGrabber.Exception {
        for (int i = 0; i < this.numBuffers + 1; ++i) {
            int err;
            int fmt;
            if (this.depth) {
                fmt = this.depthFormat < 0 ? this.bpp : this.depthFormat;
                err = freenect.freenect_sync_get_depth((Pointer)this.rawDepthImageData, (int[])this.timestamp, (int)this.deviceNumber, (int)fmt);
                if (err == 0) continue;
                throw new FrameGrabber.Exception("freenect_sync_get_depth() Error " + err + ": Failed to get depth synchronously.");
            }
            fmt = this.videoFormat < 0 ? this.bpp : this.videoFormat;
            err = freenect.freenect_sync_get_video((Pointer)this.rawVideoImageData, (int[])this.timestamp, (int)this.deviceNumber, (int)fmt);
            if (err == 0) continue;
            throw new FrameGrabber.Exception("freenect_sync_get_video() Error " + err + ": Failed to get video synchronously.");
        }
    }

    public IplImage grabDepth() throws FrameGrabber.Exception {
        int fmt = this.depthFormat < 0 ? this.bpp : this.depthFormat;
        int iplDepth = 16;
        int channels = 1;
        switch (fmt) {
            case 0: 
            case 1: 
            case 4: 
            case 5: {
                iplDepth = 16;
                channels = 1;
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        int err = freenect.freenect_sync_get_depth((Pointer)this.rawDepthImageData, (int[])this.timestamp, (int)this.deviceNumber, (int)fmt);
        if (err != 0) {
            throw new FrameGrabber.Exception("freenect_sync_get_depth() Error " + err + ": Failed to get depth synchronously.");
        }
        int w = 640;
        int h = 480;
        if (this.rawDepthImage == null || this.rawDepthImage.width() != w || this.rawDepthImage.height() != h) {
            this.rawDepthImage = IplImage.createHeader((int)w, (int)h, (int)iplDepth, (int)channels);
        }
        opencv_core.cvSetData((CvArr)this.rawDepthImage, (Pointer)this.rawDepthImageData, (int)(w * channels * iplDepth / 8));
        if (iplDepth > 8 && !ByteOrder.nativeOrder().equals(this.byteOrder)) {
            ByteBuffer bb = this.rawDepthImage.getByteBuffer();
            ShortBuffer in = bb.order(ByteOrder.BIG_ENDIAN).asShortBuffer();
            ShortBuffer out = bb.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
            out.put(in);
        }
        ((FrameGrabber)this).timestamp = this.timestamp[0];
        return this.rawDepthImage;
    }

    public IplImage grabVideo() throws FrameGrabber.Exception {
        int fmt = this.videoFormat < 0 ? this.bpp : this.videoFormat;
        int iplDepth = 8;
        int channels = 3;
        switch (fmt) {
            case 0: {
                iplDepth = 8;
                channels = 3;
                break;
            }
            case 1: 
            case 2: {
                iplDepth = 8;
                channels = 1;
                break;
            }
            case 3: {
                iplDepth = 16;
                channels = 1;
                break;
            }
            case 5: {
                iplDepth = 8;
                channels = 3;
                break;
            }
            case 6: {
                iplDepth = 8;
                channels = 2;
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        int err = freenect.freenect_sync_get_video((Pointer)this.rawVideoImageData, (int[])this.timestamp, (int)this.deviceNumber, (int)fmt);
        if (err != 0) {
            throw new FrameGrabber.Exception("freenect_sync_get_video() Error " + err + ": Failed to get video synchronously.");
        }
        int w = 640;
        int h = 480;
        if (this.rawVideoImage == null || this.rawVideoImage.width() != w || this.rawVideoImage.height() != h) {
            this.rawVideoImage = IplImage.createHeader((int)w, (int)h, (int)iplDepth, (int)channels);
        }
        opencv_core.cvSetData((CvArr)this.rawVideoImage, (Pointer)this.rawVideoImageData, (int)(w * channels * iplDepth / 8));
        if (iplDepth > 8 && !ByteOrder.nativeOrder().equals(this.byteOrder)) {
            ByteBuffer bb = this.rawVideoImage.getByteBuffer();
            ShortBuffer in = bb.order(ByteOrder.BIG_ENDIAN).asShortBuffer();
            ShortBuffer out = bb.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
            out.put(in);
        }
        if (channels == 3) {
            opencv_imgproc.cvCvtColor((CvArr)this.rawVideoImage, (CvArr)this.rawVideoImage, (int)4);
        }
        ((FrameGrabber)this).timestamp = this.timestamp[0];
        return this.rawVideoImage;
    }

    public IplImage grabIR() throws FrameGrabber.Exception {
        int iplDepth = 8;
        int channels = 1;
        int err = freenect.freenect_sync_get_video((Pointer)this.rawIRImageData, (int[])this.timestamp, (int)this.deviceNumber, (int)2);
        if (err != 0) {
            throw new FrameGrabber.Exception("freenect_sync_get_video() Error " + err + ": Failed to get video synchronously.");
        }
        int w = 640;
        int h = 480;
        if (this.rawIRImage == null || this.rawIRImage.width() != w || this.rawIRImage.height() != h) {
            this.rawIRImage = IplImage.createHeader((int)w, (int)h, (int)iplDepth, (int)channels);
        }
        opencv_core.cvSetData((CvArr)this.rawIRImage, (Pointer)this.rawIRImageData, (int)(w * channels * iplDepth / 8));
        ((FrameGrabber)this).timestamp = this.timestamp[0];
        return this.rawIRImage;
    }

    @Override
    public Frame grab() throws FrameGrabber.Exception {
        IplImage image = this.depth ? this.grabDepth() : this.grabVideo();
        int w = image.width();
        int h = image.height();
        int iplDepth = image.depth();
        int channels = image.nChannels();
        if (this.imageMode == FrameGrabber.ImageMode.COLOR && channels == 1) {
            if (this.returnImage == null) {
                this.returnImage = IplImage.create((int)w, (int)h, (int)iplDepth, (int)3);
            }
            opencv_imgproc.cvCvtColor((CvArr)image, (CvArr)this.returnImage, (int)8);
            return this.converter.convert(this.returnImage);
        }
        if (this.imageMode == FrameGrabber.ImageMode.GRAY && channels == 3) {
            if (this.returnImage == null) {
                this.returnImage = IplImage.create((int)w, (int)h, (int)iplDepth, (int)1);
            }
            opencv_imgproc.cvCvtColor((CvArr)image, (CvArr)this.returnImage, (int)6);
            return this.converter.convert(this.returnImage);
        }
        return this.converter.convert(image);
    }
}

