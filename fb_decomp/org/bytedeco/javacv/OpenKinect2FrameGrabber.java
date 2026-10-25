/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.libfreenect2.Frame
 *  org.bytedeco.libfreenect2.FrameListener
 *  org.bytedeco.libfreenect2.FrameMap
 *  org.bytedeco.libfreenect2.Freenect2
 *  org.bytedeco.libfreenect2.Freenect2Device
 *  org.bytedeco.libfreenect2.PacketPipeline
 *  org.bytedeco.libfreenect2.SyncMultiFrameListener
 *  org.bytedeco.libfreenect2.global.freenect2
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import java.io.File;
import java.nio.ByteOrder;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.libfreenect2.FrameListener;
import org.bytedeco.libfreenect2.FrameMap;
import org.bytedeco.libfreenect2.Freenect2;
import org.bytedeco.libfreenect2.Freenect2Device;
import org.bytedeco.libfreenect2.PacketPipeline;
import org.bytedeco.libfreenect2.SyncMultiFrameListener;
import org.bytedeco.libfreenect2.global.freenect2;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.IplImage;

public class OpenKinect2FrameGrabber
extends FrameGrabber {
    public static int DEFAULT_DEPTH_WIDTH = 640;
    public static int DEFAULT_DEPTH_HEIGHT = 480;
    public static int DEFAULT_COLOR_WIDTH = 640;
    public static int DEFAULT_COLOR_HEIGHT = 480;
    private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
    private int depthImageWidth = DEFAULT_DEPTH_WIDTH;
    private int depthImageHeight = DEFAULT_DEPTH_HEIGHT;
    private int depthFrameRate = 60;
    private int IRImageWidth = DEFAULT_DEPTH_WIDTH;
    private int IRImageHeight = DEFAULT_DEPTH_HEIGHT;
    private int IRFrameRate = 60;
    private SyncMultiFrameListener frameListener;
    private static FrameGrabber.Exception loadingException = null;
    private static Freenect2 freenect2Context = null;
    private boolean colorEnabled = false;
    private boolean depthEnabled = false;
    private boolean IREnabled = false;
    private int deviceNumber = 0;
    private String serial = null;
    private Freenect2Device device = null;
    private int frameTypes = 0;
    private IplImage rawVideoImage = null;
    private IplImage videoImageRGBA = null;
    private boolean hasFirstGoodColorImage = false;
    private BytePointer videoBuffer = null;
    private IplImage rawIRImage = null;
    private IplImage rawDepthImage = null;
    private FrameMap frames = new FrameMap();

    public static String[] getDeviceDescriptions() throws FrameGrabber.Exception {
        OpenKinect2FrameGrabber.tryLoad();
        String[] desc = new String[freenect2Context.enumerateDevices()];
        for (int i = 0; i < desc.length; ++i) {
            desc[i] = freenect2Context.getDeviceSerialNumber(i).getString();
        }
        return desc;
    }

    public ByteOrder getByteOrder() {
        return this.byteOrder;
    }

    public void setByteOrder(ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }

    public static OpenKinect2FrameGrabber createDefault(int deviceNumber) throws FrameGrabber.Exception {
        return new OpenKinect2FrameGrabber(deviceNumber);
    }

    public static OpenKinect2FrameGrabber createDefault(File deviceFile) throws FrameGrabber.Exception {
        throw new FrameGrabber.Exception(OpenKinect2FrameGrabber.class + " does not support File devices.");
    }

    public static OpenKinect2FrameGrabber createDefault(String devicePath) throws FrameGrabber.Exception {
        throw new FrameGrabber.Exception(OpenKinect2FrameGrabber.class + " does not support path.");
    }

    public static void tryLoad() throws FrameGrabber.Exception {
        if (loadingException != null) {
            loadingException.printStackTrace();
            throw loadingException;
        }
        try {
            if (freenect2Context != null) {
                return;
            }
            Loader.load(freenect2.class);
            freenect2Context = new Freenect2();
        }
        catch (Throwable t) {
            loadingException = new FrameGrabber.Exception("Failed to load " + OpenKinect2FrameGrabber.class, t);
            throw loadingException;
        }
    }

    public OpenKinect2FrameGrabber(int deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public void enableColorStream() {
        if (!this.colorEnabled) {
            this.frameTypes |= 1;
            this.colorEnabled = true;
        }
    }

    public void enableDepthStream() {
        if (!this.depthEnabled) {
            this.frameTypes |= 4;
            this.depthEnabled = true;
        }
    }

    public void enableIRStream() {
        if (!this.IREnabled) {
            this.frameTypes |= 2;
            this.IREnabled = true;
        }
    }

    @Override
    public void release() throws FrameGrabber.Exception {
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.release();
    }

    @Override
    public void start() throws FrameGrabber.Exception {
        this.startDevice(null);
    }

    public void startDevice(PacketPipeline pipeline) throws FrameGrabber.Exception {
        if (freenect2Context == null) {
            try {
                OpenKinect2FrameGrabber.tryLoad();
            }
            catch (FrameGrabber.Exception e) {
                System.out.println("Exception in the TryLoad !" + e);
                e.printStackTrace();
            }
        }
        if (freenect2Context == null) {
            throw new FrameGrabber.Exception("FATAL error: OpenKinect2 camera: driver could not load.");
        }
        if (freenect2Context.enumerateDevices() == 0) {
            throw new FrameGrabber.Exception("FATAL error: OpenKinect2: no device connected!");
        }
        this.device = null;
        this.serial = freenect2Context.getDeviceSerialNumber(this.deviceNumber).getString();
        this.device = pipeline != null ? freenect2Context.openDevice(this.serial, pipeline) : freenect2Context.openDevice(this.serial);
        this.frameListener = new SyncMultiFrameListener(this.frameTypes);
        if (this.colorEnabled) {
            this.device.setColorFrameListener((FrameListener)this.frameListener);
        }
        if (this.depthEnabled || this.IREnabled) {
            this.device.setIrAndDepthFrameListener((FrameListener)this.frameListener);
        }
        this.rawVideoImage = IplImage.createHeader((int)1920, (int)1080, (int)8, (int)4);
        this.device.start();
        System.out.println("OpenKinect2 device started.");
        System.out.println("Serial: " + this.device.getSerialNumber().getString());
        System.out.println("Firmware: " + this.device.getFirmwareVersion().getString());
    }

    @Override
    public void stop() throws FrameGrabber.Exception {
        this.device.stop();
        this.frameNumber = 0;
    }

    protected void grabVideo() {
        int iplDepth = 8;
        org.bytedeco.libfreenect2.Frame rgb = this.frames.get(1);
        int channels = (int)rgb.bytes_per_pixel();
        int deviceWidth = (int)rgb.width();
        int deviceHeight = (int)rgb.height();
        BytePointer rawVideoImageData = rgb.data();
        if (this.rawVideoImage == null) {
            this.rawVideoImage = IplImage.createHeader((int)deviceWidth, (int)deviceHeight, (int)iplDepth, (int)channels);
        }
        opencv_core.cvSetData((CvArr)this.rawVideoImage, (Pointer)rawVideoImageData, (int)(deviceWidth * channels * iplDepth / 8));
        if (this.videoImageRGBA == null) {
            this.videoImageRGBA = this.rawVideoImage.clone();
        }
        opencv_imgproc.cvCvtColor((CvArr)this.rawVideoImage, (CvArr)this.videoImageRGBA, (int)5);
    }

    protected void grabIR() {
        org.bytedeco.libfreenect2.Frame IRImage = this.frames.get(2);
        int channels = 1;
        int iplDepth = 32;
        int bpp = (int)IRImage.bytes_per_pixel();
        int deviceWidth = (int)IRImage.width();
        int deviceHeight = (int)IRImage.height();
        BytePointer rawIRData = IRImage.data();
        if (this.rawIRImage == null) {
            this.rawIRImage = IplImage.createHeader((int)deviceWidth, (int)deviceHeight, (int)iplDepth, (int)channels);
        }
        opencv_core.cvSetData((CvArr)this.rawIRImage, (Pointer)rawIRData, (int)(deviceWidth * channels * iplDepth / 8));
    }

    protected void grabDepth() {
        org.bytedeco.libfreenect2.Frame depthImage = this.frames.get(4);
        int channels = 1;
        int iplDepth = 32;
        int bpp = (int)depthImage.bytes_per_pixel();
        int deviceWidth = (int)depthImage.width();
        int deviceHeight = (int)depthImage.height();
        BytePointer rawDepthData = depthImage.data();
        if (this.rawDepthImage == null) {
            this.rawDepthImage = IplImage.createHeader((int)deviceWidth, (int)deviceHeight, (int)iplDepth, (int)channels);
        }
        opencv_core.cvSetData((CvArr)this.rawDepthImage, (Pointer)rawDepthData, (int)(deviceWidth * channels * iplDepth / 8));
    }

    @Override
    public Frame grab() throws FrameGrabber.Exception {
        if (!this.frameListener.waitForNewFrame(this.frames, 10000)) {
            System.out.println("Openkinect2: timeout!");
        }
        ++this.frameNumber;
        if (this.colorEnabled) {
            this.grabVideo();
        }
        if (this.IREnabled) {
            this.grabIR();
        }
        if (this.depthEnabled) {
            this.grabDepth();
        }
        this.frameListener.release(this.frames);
        return null;
    }

    public IplImage getVideoImage() {
        return this.videoImageRGBA;
    }

    public IplImage getIRImage() {
        return this.rawIRImage;
    }

    public IplImage getDepthImage() {
        return this.rawDepthImage;
    }

    @Override
    public void trigger() throws FrameGrabber.Exception {
    }

    public int getDepthImageWidth() {
        return this.depthImageWidth;
    }

    public void setDepthImageWidth(int depthImageWidth) {
        this.depthImageWidth = depthImageWidth;
    }

    public int getDepthImageHeight() {
        return this.depthImageHeight;
    }

    public void setDepthImageHeight(int depthImageHeight) {
        this.depthImageHeight = depthImageHeight;
    }

    public int getIRImageWidth() {
        return this.IRImageWidth;
    }

    public void setIRImageWidth(int IRImageWidth) {
        this.IRImageWidth = IRImageWidth;
    }

    public int getIRImageHeight() {
        return this.IRImageHeight;
    }

    public void setIRImageHeight(int IRImageHeight) {
        this.IRImageHeight = IRImageHeight;
    }

    public int getDepthFrameRate() {
        return this.depthFrameRate;
    }

    public void setDepthFrameRate(int frameRate) {
        this.depthFrameRate = frameRate;
    }
}

