/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.librealsense.context
 *  org.bytedeco.librealsense.device
 *  org.bytedeco.librealsense.global.RealSense
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import java.io.File;
import java.nio.ByteOrder;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.librealsense.context;
import org.bytedeco.librealsense.device;
import org.bytedeco.librealsense.global.RealSense;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.IplImage;

public class RealSenseFrameGrabber
extends FrameGrabber {
    public static int DEFAULT_DEPTH_WIDTH = 640;
    public static int DEFAULT_DEPTH_HEIGHT = 480;
    public static int DEFAULT_COLOR_WIDTH = 1280;
    public static int DEFAULT_COLOR_HEIGHT = 720;
    public static int DEFAULT_COLOR_FRAMERATE = 30;
    private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
    private int depthImageWidth = DEFAULT_DEPTH_WIDTH;
    private int depthImageHeight = DEFAULT_DEPTH_HEIGHT;
    private int depthFrameRate = 30;
    private int IRImageWidth = DEFAULT_DEPTH_WIDTH;
    private int IRImageHeight = DEFAULT_DEPTH_HEIGHT;
    private int IRFrameRate = 30;
    private static FrameGrabber.Exception loadingException = null;
    private static context context = null;
    private int deviceNumber = 0;
    private device device = null;
    private static device globalDevice = null;
    private boolean depth = false;
    private boolean colorEnabled = false;
    private boolean depthEnabled = false;
    private boolean IREnabled = false;
    private FrameConverter converter = new OpenCVFrameConverter.ToIplImage();
    private boolean startedOnce = false;
    private boolean behaveAsColorFrameGrabber = false;
    private Pointer rawDepthImageData = new Pointer((Pointer)null);
    private Pointer rawVideoImageData = new Pointer((Pointer)null);
    private Pointer rawIRImageData = new Pointer((Pointer)null);
    private IplImage rawDepthImage = null;
    private IplImage rawVideoImage = null;
    private IplImage rawIRImage = null;
    private IplImage returnImage = null;

    public static String[] getDeviceDescriptions() throws FrameGrabber.Exception {
        RealSenseFrameGrabber.tryLoad();
        String[] desc = new String[context.get_device_count()];
        for (int i = 0; i < desc.length; ++i) {
            desc[i] = context.get_device(i).get_name().getString();
        }
        return desc;
    }

    public ByteOrder getByteOrder() {
        return this.byteOrder;
    }

    public void setByteOrder(ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }

    public static RealSenseFrameGrabber createDefault(int deviceNumber) throws FrameGrabber.Exception {
        return new RealSenseFrameGrabber(deviceNumber);
    }

    public static RealSenseFrameGrabber createDefault(File deviceFile) throws FrameGrabber.Exception {
        throw new FrameGrabber.Exception(RealSenseFrameGrabber.class + " does not support File devices.");
    }

    public static RealSenseFrameGrabber createDefault(String devicePath) throws FrameGrabber.Exception {
        throw new FrameGrabber.Exception(RealSenseFrameGrabber.class + " does not support path.");
    }

    public static void tryLoad() throws FrameGrabber.Exception {
        if (loadingException != null) {
            loadingException.printStackTrace();
            throw loadingException;
        }
        try {
            if (context != null) {
                return;
            }
            Loader.load(RealSense.class);
            context = new context();
            System.out.println("RealSense devices found: " + context.get_device_count());
        }
        catch (Throwable t) {
            loadingException = new FrameGrabber.Exception("Failed to load " + RealSenseFrameGrabber.class, t);
            throw loadingException;
        }
    }

    public RealSenseFrameGrabber(int deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public static void main(String[] args) {
        context context2 = new context();
        System.out.println("Devices found: " + context2.get_device_count());
        device device2 = context2.get_device(0);
        System.out.println("Using device 0, an " + device2.get_name());
        System.out.println(" Serial number: " + device2.get_serial());
    }

    public void enableColorStream() {
        if (!this.colorEnabled) {
            if (this.imageWidth == 0) {
                this.imageWidth = DEFAULT_COLOR_WIDTH;
            }
            if (this.imageHeight == 0) {
                this.imageHeight = DEFAULT_COLOR_HEIGHT;
            }
            if (this.frameRate == 0.0) {
                this.frameRate = DEFAULT_COLOR_FRAMERATE;
            }
            this.colorEnabled = true;
        }
    }

    public void disableColorStream() {
        if (this.colorEnabled) {
            this.device.disable_stream(1);
            this.colorEnabled = false;
        }
    }

    public void enableDepthStream() {
        if (!this.depthEnabled) {
            this.depthEnabled = true;
        }
    }

    public void disableDepthStream() {
        if (this.depthEnabled) {
            this.device.disable_stream(0);
            this.depthEnabled = false;
        }
    }

    public void enableIRStream() {
        if (!this.IREnabled) {
            this.IREnabled = true;
        }
    }

    public void disableIRStream() {
        if (this.IREnabled) {
            this.device.disable_stream(2);
            this.IREnabled = false;
        }
    }

    @Override
    public void release() throws FrameGrabber.Exception {
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.release();
    }

    public device getRealSenseDevice() {
        return this.device;
    }

    public float getDepthScale() {
        return this.device.get_depth_scale();
    }

    @Override
    public double getFrameRate() {
        return super.getFrameRate();
    }

    public device loadDevice() throws FrameGrabber.Exception {
        if (context == null) {
            context = new context();
        }
        if (context == null || context.get_device_count() <= this.deviceNumber) {
            throw new FrameGrabber.Exception("FATAL error: Realsense camera: " + this.deviceNumber + " not connected/found");
        }
        this.device = context.get_device(this.deviceNumber);
        return this.device;
    }

    @Override
    public void start() throws FrameGrabber.Exception {
        if (globalDevice != null) {
            globalDevice.close();
            context.close();
            globalDevice = null;
            context = null;
        }
        if (context == null) {
            context = new context();
        }
        if (context == null || context.get_device_count() <= this.deviceNumber) {
            throw new FrameGrabber.Exception("FATAL error: Realsense camera: " + this.deviceNumber + " not connected/found");
        }
        if (this.device == null) {
            this.device = context.get_device(this.deviceNumber);
        }
        globalDevice = this.device;
        if (this.format != null) {
            switch (this.format) {
                case "rgb": {
                    this.enableColorStream();
                    break;
                }
                case "ir": {
                    this.enableIRStream();
                    break;
                }
                case "depth": {
                    this.enableDepthStream();
                }
            }
        }
        if (this.colorEnabled) {
            this.device.enable_stream(1, this.imageWidth, this.imageHeight, 5, (int)this.frameRate);
        }
        if (this.IREnabled) {
            this.device.enable_stream(2, this.IRImageWidth, this.IRImageHeight, 9, this.IRFrameRate);
        }
        if (this.depthEnabled) {
            this.device.enable_stream(0, this.depthImageWidth, this.depthImageHeight, 1, this.depthFrameRate);
        }
        if (!(this.colorEnabled || this.IREnabled || this.depthEnabled)) {
            this.enableColorStream();
            this.device.enable_stream(1, this.imageWidth, this.imageHeight, 5, (int)this.frameRate);
            this.behaveAsColorFrameGrabber = true;
        }
        this.device.start();
    }

    @Override
    public void stop() throws FrameGrabber.Exception {
        this.device.stop();
        this.frameNumber = 0;
    }

    public IplImage grabDepth() {
        if (!this.depthEnabled) {
            System.out.println("Depth stream not enabled, impossible to get the image.");
            return null;
        }
        this.rawDepthImageData = this.device.get_frame_data(0);
        int iplDepth = 16;
        int channels = 1;
        int deviceWidth = this.device.get_stream_width(0);
        int deviceHeight = this.device.get_stream_height(0);
        if (this.rawDepthImage == null || this.rawDepthImage.width() != deviceWidth || this.rawDepthImage.height() != deviceHeight) {
            this.rawDepthImage = IplImage.createHeader((int)deviceWidth, (int)deviceHeight, (int)iplDepth, (int)channels);
        }
        opencv_core.cvSetData((CvArr)this.rawDepthImage, (Pointer)this.rawDepthImageData, (int)(deviceWidth * channels * iplDepth / 8));
        return this.rawDepthImage;
    }

    public IplImage grabVideo() {
        if (!this.colorEnabled) {
            System.out.println("Color stream not enabled, impossible to get the image.");
            return null;
        }
        int iplDepth = 8;
        int channels = 3;
        this.rawVideoImageData = this.device.get_frame_data(1);
        int deviceWidth = this.device.get_stream_width(1);
        int deviceHeight = this.device.get_stream_height(1);
        if (this.rawVideoImage == null || this.rawVideoImage.width() != deviceWidth || this.rawVideoImage.height() != deviceHeight) {
            this.rawVideoImage = IplImage.createHeader((int)deviceWidth, (int)deviceHeight, (int)iplDepth, (int)channels);
        }
        opencv_core.cvSetData((CvArr)this.rawVideoImage, (Pointer)this.rawVideoImageData, (int)(deviceWidth * channels * iplDepth / 8));
        return this.rawVideoImage;
    }

    public IplImage grabIR() {
        if (!this.IREnabled) {
            System.out.println("IR stream not enabled, impossible to get the image.");
            return null;
        }
        int iplDepth = 8;
        int channels = 1;
        this.rawIRImageData = this.device.get_frame_data(2);
        int deviceWidth = this.device.get_stream_width(2);
        int deviceHeight = this.device.get_stream_height(2);
        if (this.rawIRImage == null || this.rawIRImage.width() != deviceWidth || this.rawIRImage.height() != deviceHeight) {
            this.rawIRImage = IplImage.createHeader((int)deviceWidth, (int)deviceHeight, (int)iplDepth, (int)channels);
        }
        opencv_core.cvSetData((CvArr)this.rawIRImage, (Pointer)this.rawIRImageData, (int)(deviceWidth * channels * iplDepth / 8));
        return this.rawIRImage;
    }

    @Override
    public Frame grab() throws FrameGrabber.Exception {
        this.device.wait_for_frames();
        if (this.colorEnabled && this.behaveAsColorFrameGrabber) {
            IplImage image = this.grabVideo();
            if (this.returnImage == null) {
                int deviceWidth = this.device.get_stream_width(1);
                int deviceHeight = this.device.get_stream_height(1);
                this.returnImage = IplImage.create((int)deviceWidth, (int)deviceHeight, (int)8, (int)1);
            }
            opencv_imgproc.cvCvtColor((CvArr)image, (CvArr)this.returnImage, (int)6);
            return this.converter.convert(this.returnImage);
        }
        if (this.IREnabled) {
            return this.converter.convert(this.grabIR());
        }
        if (this.depthEnabled) {
            IplImage image = this.grabDepth();
            if (this.returnImage == null) {
                int deviceWidth = this.device.get_stream_width(0);
                int deviceHeight = this.device.get_stream_height(0);
                this.returnImage = IplImage.create((int)deviceWidth, (int)deviceHeight, (int)8, (int)1);
            }
            return this.converter.convert(this.returnImage);
        }
        return null;
    }

    @Override
    public void trigger() throws FrameGrabber.Exception {
        this.device.wait_for_frames();
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

    public int getIRFrameRate() {
        return this.IRFrameRate;
    }

    public void setIRFrameRate(int IRFrameRate) {
        this.IRFrameRate = IRFrameRate;
    }

    @Override
    public double getGamma() {
        if (this.gamma == 0.0) {
            return 2.2;
        }
        return this.gamma;
    }

    public void setPreset(int preset) {
        RealSense.apply_ivcam_preset((device)this.device, (int)preset);
    }

    public void setShortRange() {
        this.setPreset(0);
    }

    public void setLongRange() {
        this.setPreset(1);
    }

    public void setMidRange() {
        this.setPreset(9);
    }

    public void setDefaultPreset() {
        this.setPreset(8);
    }

    public void setObjectScanningPreset() {
        this.setPreset(4);
    }

    public void setCursorPreset() {
        this.setPreset(7);
    }

    public void setGestureRecognitionPreset() {
        this.setPreset(3);
    }

    public void setBackgroundSegmentationPreset() {
        this.setPreset(2);
    }

    public void setIROnlyPreset() {
        this.setPreset(10);
    }

    public void setOption(int option, int value) {
        this.device.set_option(option, (double)value);
    }

    public void set(int value) {
        this.setOption(0, value);
    }

    public void setColorBrightness(int value) {
        this.setOption(1, value);
    }

    public void setColorContrast(int value) {
        this.setOption(2, value);
    }

    public void setColorExposure(int value) {
        this.setOption(3, value);
    }

    public void setColorGain(int value) {
        this.setOption(4, value);
    }

    public void setColorGamma(int value) {
        this.setOption(5, value);
    }

    public void setColorHue(int value) {
        this.setOption(6, value);
    }

    public void setColorSaturation(int value) {
        this.setOption(7, value);
    }

    public void setColorSharpness(int value) {
        this.setOption(8, value);
    }

    public void setColorWhiteBalance(int value) {
        this.setOption(9, value);
    }

    public void setColorEnableAutoExposure(int value) {
        this.setOption(10, value);
    }

    public void setColorEnableAutoWhiteBalance(int value) {
        this.setOption(11, value);
    }

    public void setLaserPower(int value) {
        this.setOption(12, value);
    }

    public void setAccuracy(int value) {
        this.setOption(13, value);
    }

    public void setMotionRange(int value) {
        this.setOption(14, value);
    }

    public void setFilterOption(int value) {
        this.setOption(15, value);
    }

    public void setConfidenceThreshold(int value) {
        this.setOption(16, value);
    }

    public void setDynamicFPS(int value) {
        this.setOption(17, value);
    }

    public void setLR_AutoExposureEnabled(int value) {
        this.setOption(28, value);
    }

    public void setLR_Gain(int value) {
        this.setOption(29, value);
    }

    public void setLR_Exposure(int value) {
        this.setOption(30, value);
    }

    public void setEmitterEnabled(int value) {
        this.setOption(31, value);
    }

    public void setDepthUnits(int value) {
        this.setOption(32, value);
    }

    public void setDepthClampMin(int value) {
        this.setOption(33, value);
    }

    public void setDepthClampMax(int value) {
        this.setOption(34, value);
    }

    public void setDisparityMultiplier(int value) {
        this.setOption(35, value);
    }

    public void setDisparityShift(int value) {
        this.setOption(36, value);
    }

    public void setAutoExposureMeanIntensitySetPoint(int value) {
        this.setOption(37, value);
    }

    public void setAutoExposureBrightRatioSetPoint(int value) {
        this.setOption(38, value);
    }

    public void setAutoExposureKpGain(int value) {
        this.setOption(39, value);
    }

    public void setAutoExposureKpExposure(int value) {
        this.setOption(40, value);
    }

    public void setAutoExposureKpDarkThreshold(int value) {
        this.setOption(41, value);
    }

    public void setAutoExposureTopEdge(int value) {
        this.setOption(42, value);
    }

    public void setAutoExposureBottomEdge(int value) {
        this.setOption(43, value);
    }

    public void setAutoExposureLeftEdge(int value) {
        this.setOption(44, value);
    }

    public void setAutoExposureRightEdge(int value) {
        this.setOption(45, value);
    }

    public void setDepthControlEstimateMedianDecrement(int value) {
        this.setOption(46, value);
    }

    public void setDepthControlEstimateMedianIncrement(int value) {
        this.setOption(47, value);
    }

    public void setDepthControlMedianThreshold(int value) {
        this.setOption(48, value);
    }

    public void setDepthControlMinimumThreshold(int value) {
        this.setOption(49, value);
    }

    public void setDepthControlScoreMaximumThreshold(int value) {
        this.setOption(50, value);
    }

    public void setDepthControlTextureCountThreshold(int value) {
        this.setOption(51, value);
    }

    public void setDepthControlTextureDifference(int value) {
        this.setOption(52, value);
    }

    public void setDepthControlSecondPeakThreshold(int value) {
        this.setOption(53, value);
    }

    public void setDepthControlNeighborThreshold(int value) {
        this.setOption(54, value);
    }

    public void setDepthControlLRThreshold(int value) {
        this.setOption(55, value);
    }

    public void setFisheyeExposure(int value) {
        this.setOption(56, value);
    }

    public void setFisheyeGain(int value) {
        this.setOption(57, value);
    }

    public void setFisheyeStobe(int value) {
        this.setOption(58, value);
    }

    public void setFisheyeExternalTrigger(int value) {
        this.setOption(59, value);
    }

    public void setFisheyeEnableAutoExposure(int value) {
        this.setOption(60, value);
    }

    public void setFisheyeAutoExposureMode(int value) {
        this.setOption(61, value);
    }

    public void setFisheyeAutoExposureAntiflickerRate(int value) {
        this.setOption(62, value);
    }

    public void setFisheyeAutoExposurePixelSampleRate(int value) {
        this.setOption(63, value);
    }

    public void setFisheyeAutoExposureSkipFrames(int value) {
        this.setOption(64, value);
    }

    public void setFramesQueueSize(int value) {
        this.setOption(65, value);
    }

    public void setHardwareLoggerEnabled(int value) {
        this.setOption(66, value);
    }
}

