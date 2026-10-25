/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.IplImage
 *  org.bytedeco.videoinput.global.videoInputLib
 *  org.bytedeco.videoinput.videoInput
 */
package org.bytedeco.javacv;

import java.io.File;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.videoinput.global.videoInputLib;
import org.bytedeco.videoinput.videoInput;

public class VideoInputFrameGrabber
extends FrameGrabber {
    private static FrameGrabber.Exception loadingException = null;
    private int deviceNumber = 0;
    private videoInput myVideoInput = null;
    private IplImage bgrImage = null;
    private IplImage grayImage = null;
    private BytePointer bgrImageData = null;
    private FrameConverter converter = new OpenCVFrameConverter.ToIplImage();

    public static String[] getDeviceDescriptions() throws FrameGrabber.Exception {
        VideoInputFrameGrabber.tryLoad();
        int count = videoInput.listDevices();
        String[] descriptions = new String[count];
        for (int i = 0; i < descriptions.length; ++i) {
            descriptions[i] = videoInput.getDeviceName((int)i).getString();
        }
        return descriptions;
    }

    public static VideoInputFrameGrabber createDefault(File deviceFile) throws FrameGrabber.Exception {
        throw new FrameGrabber.Exception(VideoInputFrameGrabber.class + " does not support device files.");
    }

    public static VideoInputFrameGrabber createDefault(String devicePath) throws FrameGrabber.Exception {
        throw new FrameGrabber.Exception(VideoInputFrameGrabber.class + " does not support device paths.");
    }

    public static VideoInputFrameGrabber createDefault(int deviceNumber) throws FrameGrabber.Exception {
        return new VideoInputFrameGrabber(deviceNumber);
    }

    public static void tryLoad() throws FrameGrabber.Exception {
        if (loadingException != null) {
            throw loadingException;
        }
        try {
            Loader.load(videoInputLib.class);
        }
        catch (Throwable t) {
            loadingException = new FrameGrabber.Exception("Failed to load " + VideoInputFrameGrabber.class, t);
            throw loadingException;
        }
    }

    public VideoInputFrameGrabber(int deviceNumber) {
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

    @Override
    public double getGamma() {
        if (this.gamma == 0.0) {
            return 2.2;
        }
        return this.gamma;
    }

    @Override
    public int getImageWidth() {
        return this.myVideoInput == null ? super.getImageWidth() : this.myVideoInput.getWidth(this.deviceNumber);
    }

    @Override
    public int getImageHeight() {
        return this.myVideoInput == null ? super.getImageHeight() : this.myVideoInput.getHeight(this.deviceNumber);
    }

    @Override
    public void start() throws FrameGrabber.Exception {
        this.start(-1);
    }

    public void start(int connection) throws FrameGrabber.Exception {
        this.myVideoInput = new videoInput();
        if (this.frameRate > 0.0) {
            this.myVideoInput.setIdealFramerate(this.deviceNumber, (int)this.frameRate);
        }
        if (!this.myVideoInput.setupDevice(this.deviceNumber, this.imageWidth > 0 ? this.imageWidth : 640, this.imageHeight > 0 ? this.imageHeight : 480, connection)) {
            this.myVideoInput = null;
            throw new FrameGrabber.Exception("videoInput.setupDevice() Error: Could not setup device.");
        }
        if (this.format != null && this.format.length() > 0) {
            int f;
            int n = this.format.equals("VI_NTSC_M") ? 0 : (this.format.equals("VI_PAL_B") ? 1 : (this.format.equals("VI_PAL_D") ? 2 : (this.format.equals("VI_PAL_G") ? 3 : (this.format.equals("VI_PAL_H") ? 4 : (this.format.equals("VI_PAL_I") ? 5 : (this.format.equals("VI_PAL_M") ? 6 : (this.format.equals("VI_PAL_N") ? 7 : (this.format.equals("VI_PAL_NC") ? 8 : (this.format.equals("VI_SECAM_B") ? 9 : (this.format.equals("VI_SECAM_D") ? 10 : (this.format.equals("VI_SECAM_G") ? 11 : (this.format.equals("VI_SECAM_H") ? 12 : (this.format.equals("VI_SECAM_K") ? 13 : (this.format.equals("VI_SECAM_K1") ? 14 : (this.format.equals("VI_SECAM_L") ? 15 : (this.format.equals("VI_NTSC_M_J") ? 16 : (f = this.format.equals("VI_NTSC_433") ? 17 : -1)))))))))))))))));
            if (f >= 0 && !this.myVideoInput.setFormat(this.deviceNumber, f)) {
                throw new FrameGrabber.Exception("videoInput.setFormat() Error: Could not set format " + this.format + ".");
            }
        }
    }

    @Override
    public void stop() throws FrameGrabber.Exception {
        if (this.myVideoInput != null) {
            this.myVideoInput.stopDevice(this.deviceNumber);
            this.myVideoInput = null;
        }
    }

    @Override
    public void trigger() throws FrameGrabber.Exception {
        if (this.myVideoInput == null) {
            throw new FrameGrabber.Exception("videoInput is null. (Has start() been called?)");
        }
        int w = this.myVideoInput.getWidth(this.deviceNumber);
        int h = this.myVideoInput.getHeight(this.deviceNumber);
        if (this.bgrImage == null || this.bgrImage.width() != w || this.bgrImage.height() != h) {
            this.bgrImage = IplImage.create((int)w, (int)h, (int)8, (int)3);
            this.bgrImageData = this.bgrImage.imageData();
        }
        for (int i = 0; i < this.numBuffers + 1; ++i) {
            this.myVideoInput.getPixels(this.deviceNumber, this.bgrImageData, false, true);
        }
    }

    @Override
    public Frame grab() throws FrameGrabber.Exception {
        if (this.myVideoInput == null) {
            throw new FrameGrabber.Exception("videoInput is null. (Has start() been called?)");
        }
        int w = this.myVideoInput.getWidth(this.deviceNumber);
        int h = this.myVideoInput.getHeight(this.deviceNumber);
        if (this.bgrImage == null || this.bgrImage.width() != w || this.bgrImage.height() != h) {
            this.bgrImage = IplImage.create((int)w, (int)h, (int)8, (int)3);
            this.bgrImageData = this.bgrImage.imageData();
        }
        if (!this.myVideoInput.getPixels(this.deviceNumber, this.bgrImageData, false, true)) {
            throw new FrameGrabber.Exception("videoInput.getPixels() Error: Could not get pixels.");
        }
        this.timestamp = System.nanoTime() / 1000L;
        if (this.imageMode == FrameGrabber.ImageMode.GRAY) {
            if (this.grayImage == null) {
                this.grayImage = IplImage.create((int)w, (int)h, (int)8, (int)1);
            }
            opencv_imgproc.cvCvtColor((CvArr)this.bgrImage, (CvArr)this.grayImage, (int)6);
            return this.converter.convert(this.grayImage);
        }
        return this.converter.convert(this.bgrImage);
    }
}

