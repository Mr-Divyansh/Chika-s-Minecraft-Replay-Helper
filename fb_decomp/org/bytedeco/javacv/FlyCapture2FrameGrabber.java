/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.flycapture.FlyCapture2.BusManager
 *  org.bytedeco.flycapture.FlyCapture2.Camera
 *  org.bytedeco.flycapture.FlyCapture2.CameraInfo
 *  org.bytedeco.flycapture.FlyCapture2.Error
 *  org.bytedeco.flycapture.FlyCapture2.FC2Config
 *  org.bytedeco.flycapture.FlyCapture2.Image
 *  org.bytedeco.flycapture.FlyCapture2.PGRGuid
 *  org.bytedeco.flycapture.FlyCapture2.Property
 *  org.bytedeco.flycapture.FlyCapture2.TimeStamp
 *  org.bytedeco.flycapture.FlyCapture2.TriggerMode
 *  org.bytedeco.flycapture.global.FlyCapture2
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import org.bytedeco.flycapture.FlyCapture2.BusManager;
import org.bytedeco.flycapture.FlyCapture2.Camera;
import org.bytedeco.flycapture.FlyCapture2.CameraInfo;
import org.bytedeco.flycapture.FlyCapture2.Error;
import org.bytedeco.flycapture.FlyCapture2.FC2Config;
import org.bytedeco.flycapture.FlyCapture2.Image;
import org.bytedeco.flycapture.FlyCapture2.PGRGuid;
import org.bytedeco.flycapture.FlyCapture2.Property;
import org.bytedeco.flycapture.FlyCapture2.TimeStamp;
import org.bytedeco.flycapture.FlyCapture2.TriggerMode;
import org.bytedeco.flycapture.global.FlyCapture2;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.IplImage;

public class FlyCapture2FrameGrabber
extends FrameGrabber {
    private static FrameGrabber.Exception loadingException = null;
    public static final int INITIALIZE = 0;
    public static final int TRIGGER_INQ = 1328;
    public static final int IS_CAMERA_POWER = 1024;
    public static final int CAMERA_POWER = 1552;
    public static final int SOFTWARE_TRIGGER = 1580;
    public static final int SOFT_ASYNC_TRIGGER = 4140;
    public static final int IMAGE_DATA_FORMAT = 4168;
    private BusManager busMgr = new BusManager();
    private Camera camera;
    private CameraInfo cameraInfo;
    private Image raw_image = new Image();
    private Image conv_image = new Image();
    private IplImage temp_image;
    private IplImage return_image = null;
    private FrameConverter converter = new OpenCVFrameConverter.ToIplImage();
    private final int[] regOut = new int[1];
    private final float[] outFloat = new float[1];
    private final float[] gammaOut = new float[1];
    static final int VIDEOMODE_ANY = -1;

    public static String[] getDeviceDescriptions() throws FrameGrabber.Exception {
        FlyCapture2FrameGrabber.tryLoad();
        BusManager busMgr = new BusManager();
        int[] numCameras = new int[1];
        busMgr.GetNumOfCameras(numCameras);
        String[] descriptions = new String[numCameras[0]];
        for (int i = 0; i < numCameras[0]; ++i) {
            CameraInfo camInfo;
            Camera cam;
            PGRGuid guid = new PGRGuid();
            Error error = busMgr.GetCameraFromIndex(i, guid);
            if (error.notEquals(0)) {
                FlyCapture2FrameGrabber.PrintError(error);
                System.exit(-1);
            }
            if ((error = (cam = new Camera()).Connect(guid)).notEquals(0)) {
                FlyCapture2FrameGrabber.PrintError(error);
            }
            if ((error = cam.GetCameraInfo(camInfo = new CameraInfo())).notEquals(0)) {
                FlyCapture2FrameGrabber.PrintError(error);
            }
            descriptions[i] = FlyCapture2FrameGrabber.CameraInfo(camInfo);
        }
        return descriptions;
    }

    static void PrintError(Error error) {
        error.PrintErrorTrace();
    }

    static String CameraInfo(CameraInfo pCamInfo) {
        return "\n*** CAMERA INFORMATION ***\nSerial number - " + pCamInfo.serialNumber() + "\nCamera model - " + pCamInfo.modelName().getString() + "\nCamera vendor - " + pCamInfo.vendorName().getString() + "\nSensor - " + pCamInfo.sensorInfo().getString() + "\nResolution - " + pCamInfo.sensorResolution().getString() + "\nFirmware version - " + pCamInfo.firmwareVersion().getString() + "\nFirmware build time - " + pCamInfo.firmwareBuildTime().getString() + "\n";
    }

    public static FlyCapture2FrameGrabber createDefault(File deviceFile) throws FrameGrabber.Exception {
        return null;
    }

    public static FlyCapture2FrameGrabber createDefault(String devicePath) throws FrameGrabber.Exception {
        return null;
    }

    public static FlyCapture2FrameGrabber createDefault(int deviceNumber) throws FrameGrabber.Exception {
        return new FlyCapture2FrameGrabber(deviceNumber);
    }

    public static void tryLoad() throws FrameGrabber.Exception {
        if (loadingException != null) {
            loadingException.printStackTrace();
            throw loadingException;
        }
        try {
            Loader.load(FlyCapture2.class);
        }
        catch (Throwable t) {
            loadingException = new FrameGrabber.Exception("Failed to load " + FlyCapture2FrameGrabber.class, t);
            throw loadingException;
        }
    }

    public FlyCapture2FrameGrabber(int deviceNumber) throws FrameGrabber.Exception {
        int[] numCameras = new int[1];
        this.busMgr.GetNumOfCameras(numCameras);
        PGRGuid guid = new PGRGuid();
        Error error = this.busMgr.GetCameraFromIndex(deviceNumber, guid);
        if (error.notEquals(0)) {
            FlyCapture2FrameGrabber.PrintError(error);
            System.exit(-1);
        }
        this.camera = new Camera();
        error = this.camera.Connect(guid);
        if (error.notEquals(0)) {
            FlyCapture2FrameGrabber.PrintError(error);
        }
        this.cameraInfo = new CameraInfo();
        error = this.camera.GetCameraInfo(this.cameraInfo);
        if (error.notEquals(0)) {
            FlyCapture2FrameGrabber.PrintError(error);
        }
    }

    @Override
    public void release() throws FrameGrabber.Exception {
        if (this.camera != null) {
            this.stop();
            this.camera.Disconnect();
            this.camera = null;
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.release();
    }

    @Override
    public double getGamma() {
        return Float.isNaN(this.gammaOut[0]) || Float.isInfinite(this.gammaOut[0]) || this.gammaOut[0] == 0.0f ? 2.2 : (double)this.gammaOut[0];
    }

    @Override
    public int getImageWidth() {
        return this.return_image == null ? super.getImageWidth() : this.return_image.width();
    }

    @Override
    public int getImageHeight() {
        return this.return_image == null ? super.getImageHeight() : this.return_image.height();
    }

    @Override
    public double getFrameRate() {
        if (this.camera == null || this.camera.isNull()) {
            return super.getFrameRate();
        }
        IntPointer videoMode = new IntPointer(1L);
        IntPointer frameRate = new IntPointer(1L);
        this.camera.GetVideoModeAndFrameRate(videoMode, frameRate);
        return frameRate.get(0L);
    }

    @Override
    public void setImageMode(FrameGrabber.ImageMode imageMode) {
        if (imageMode != this.imageMode) {
            this.temp_image = null;
            this.return_image = null;
        }
        super.setImageMode(imageMode);
    }

    @Override
    public void start() throws FrameGrabber.Exception {
        int f = 4;
        if (this.frameRate <= 0.0) {
            f = 4;
        } else if (this.frameRate <= 1.876) {
            f = 0;
        } else if (this.frameRate <= 3.76) {
            f = 1;
        } else if (this.frameRate <= 7.51) {
            f = 2;
        } else if (this.frameRate <= 15.01) {
            f = 3;
        } else if (this.frameRate <= 30.01) {
            f = 4;
        } else if (this.frameRate <= 60.01) {
            f = 5;
        } else if (this.frameRate <= 120.01) {
            f = 6;
        } else if (this.frameRate <= 240.01) {
            f = 7;
        }
        int c = -1;
        if (this.imageMode == FrameGrabber.ImageMode.COLOR || this.imageMode == FrameGrabber.ImageMode.RAW) {
            if (this.imageWidth <= 0 || this.imageHeight <= 0) {
                c = -1;
            } else if (this.imageWidth <= 640 && this.imageHeight <= 480) {
                c = 4;
            } else if (this.imageWidth <= 800 && this.imageHeight <= 600) {
                c = 8;
            } else if (this.imageWidth <= 1024 && this.imageHeight <= 768) {
                c = 12;
            } else if (this.imageWidth <= 1280 && this.imageHeight <= 960) {
                c = 16;
            } else if (this.imageWidth <= 1600 && this.imageHeight <= 1200) {
                c = 20;
            }
        } else if (this.imageMode == FrameGrabber.ImageMode.GRAY) {
            if (this.imageWidth <= 0 || this.imageHeight <= 0) {
                c = -1;
            } else if (this.imageWidth <= 640 && this.imageHeight <= 480) {
                c = this.bpp > 8 ? 6 : 5;
            } else if (this.imageWidth <= 800 && this.imageHeight <= 600) {
                c = this.bpp > 8 ? 10 : 9;
            } else if (this.imageWidth <= 1024 && this.imageHeight <= 768) {
                c = this.bpp > 8 ? 14 : 13;
            } else if (this.imageWidth <= 1280 && this.imageHeight <= 960) {
                c = this.bpp > 8 ? 18 : 17;
            } else if (this.imageWidth <= 1600 && this.imageHeight <= 1200) {
                c = this.bpp > 8 ? 22 : 21;
            }
        }
        TriggerMode tm2 = new TriggerMode();
        Error error = this.camera.GetTriggerMode(tm2);
        if (error.notEquals(0)) {
            FlyCapture2FrameGrabber.PrintError(error);
            throw new FrameGrabber.Exception("GetTriggerMode() Error " + error.GetDescription());
        }
        tm2.onOff(this.triggerMode);
        tm2.source(7);
        tm2.mode(14);
        tm2.parameter(0);
        error = this.camera.SetTriggerMode(tm2);
        if (error.notEquals(0)) {
            tm2.onOff(true);
            tm2.source(7);
            tm2.mode(0);
            tm2.parameter(0);
            error = this.camera.SetTriggerMode(tm2);
            if (error.notEquals(0)) {
                FlyCapture2FrameGrabber.PrintError(error);
                throw new FrameGrabber.Exception("SetTriggerMode() Error " + error.GetDescription());
            }
        }
        if (this.triggerMode) {
            this.waitForTriggerReady();
        }
        if ((error = this.camera.ReadRegister(4168, this.regOut)).notEquals(0)) {
            FlyCapture2FrameGrabber.PrintError(error);
            throw new FrameGrabber.Exception("ReadRegister(IMAGE_DATA_FORMAT, regOut) Error " + error.GetDescription());
        }
        int reg = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN) ? this.regOut[0] | 1 : this.regOut[0] & 0xFFFFFFFE;
        error = this.camera.WriteRegister(4168, reg);
        if (error.notEquals(0)) {
            FlyCapture2FrameGrabber.PrintError(error);
            throw new FrameGrabber.Exception("WriteRegister(IMAGE_DATA_FORMAT, reg) Error " + error.GetDescription());
        }
        Property gammaProp = new Property(6);
        if (this.gamma != 0.0) {
            error = this.camera.GetProperty(gammaProp);
            if (error.notEquals(0)) {
                throw new FrameGrabber.Exception("GetProperty(gammaProp) Error " + error.GetDescription());
            }
            gammaProp.onOff(true);
            gammaProp.absControl(true);
            gammaProp.absValue((float)this.gamma);
            this.camera.SetProperty(gammaProp);
            error = this.camera.SetProperty(gammaProp);
            if (error.notEquals(0)) {
                FlyCapture2FrameGrabber.PrintError(error);
                throw new FrameGrabber.Exception("SetProperty(gammaProp) Error " + error.GetDescription());
            }
        }
        this.gammaOut[0] = (error = this.camera.GetProperty(gammaProp)).notEquals(0) ? 2.2f : gammaProp.absValue();
        error = this.camera.StartCapture();
        if (error.notEquals(0)) {
            FlyCapture2FrameGrabber.PrintError(error);
            throw new FrameGrabber.Exception("StartCapture() Error " + error.GetDescription());
        }
        FC2Config config = new FC2Config();
        error = this.camera.GetConfiguration(config);
        if (error.notEquals(0)) {
            FlyCapture2FrameGrabber.PrintError(error);
            throw new FrameGrabber.Exception("GetConfiguration() Error " + error.GetDescription());
        }
        config.grabTimeout(this.timeout);
        error = this.camera.SetConfiguration(config);
        if (error.notEquals(0)) {
            FlyCapture2FrameGrabber.PrintError(error);
            throw new FrameGrabber.Exception("SetConfiguration() Error " + error.GetDescription());
        }
    }

    private void waitForTriggerReady() throws FrameGrabber.Exception {
        long time = System.currentTimeMillis();
        do {
            Error error;
            if (!(error = this.camera.ReadRegister(1580, this.regOut)).notEquals(0)) continue;
            FlyCapture2FrameGrabber.PrintError(error);
            throw new FrameGrabber.Exception("GetTriggerMode() Error " + error.GetDescription());
        } while (System.currentTimeMillis() - time <= (long)this.timeout && this.regOut[0] >>> 31 != 0);
    }

    @Override
    public void stop() throws FrameGrabber.Exception {
        Error error = this.camera.StopCapture();
        if (error.notEquals(0)) {
            FlyCapture2FrameGrabber.PrintError(error);
            throw new FrameGrabber.Exception("flycapture camera StopCapture() Error " + error);
        }
        this.temp_image = null;
        this.return_image = null;
        this.timestamp = 0L;
        this.frameNumber = 0;
    }

    @Override
    public void trigger() throws FrameGrabber.Exception {
        this.waitForTriggerReady();
        Error error = this.camera.FireSoftwareTrigger();
        if (error.notEquals(0)) {
            throw new FrameGrabber.Exception("flycaptureSetCameraRegister() Error " + error);
        }
    }

    private int getNumChannels(int pixelFormat) {
        switch (pixelFormat) {
            case -2147483640: 
            case 0x800000: 
            case 0x2000000: 
            case 0x8000000: {
                return 3;
            }
            case -2147483648: 
            case 0x200000: 
            case 0x400000: 
            case 0x1000000: 
            case 0x4000000: {
                return 1;
            }
            case 0x40000008: {
                return 4;
            }
        }
        return -1;
    }

    private int getDepth(int pixelFormat) {
        switch (pixelFormat) {
            case -2147483648: 
            case -2147483640: 
            case 0x400000: 
            case 0x8000000: 
            case 0x40000008: {
                return 8;
            }
            case 0x200000: 
            case 0x2000000: 
            case 0x4000000: {
                return 16;
            }
            case 0x800000: 
            case 0x1000000: {
                return -2147483632;
            }
        }
        return 8;
    }

    private void setPixelFormat(Image image, int pixelFormat) {
        image.SetDimensions(image.GetRows(), image.GetCols(), image.GetStride(), pixelFormat, image.GetBayerTileFormat());
    }

    private void setStride(Image image, int stride) {
        image.SetDimensions(image.GetRows(), image.GetCols(), stride, image.GetPixelFormat(), image.GetBayerTileFormat());
    }

    @Override
    public Frame grab() throws FrameGrabber.Exception {
        Error error = this.camera.RetrieveBuffer(this.raw_image);
        if (error.notEquals(0)) {
            throw new FrameGrabber.Exception("flycaptureGrabImage2() Error " + error + " (Has start() been called?)");
        }
        int w = this.raw_image.GetCols();
        int h = this.raw_image.GetRows();
        int format = this.raw_image.GetPixelFormat();
        int depth = this.getDepth(format);
        int stride = this.raw_image.GetStride();
        int size = h * stride;
        int numChannels = this.getNumChannels(format);
        error = this.camera.ReadRegister(4168, this.regOut);
        if (error.notEquals(0)) {
            throw new FrameGrabber.Exception("flycaptureGetCameraRegister() Error " + error);
        }
        ByteOrder frameEndian = (this.regOut[0] & 1) != 0 ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
        boolean alreadySwapped = false;
        boolean colorbayer = this.raw_image.GetBayerTileFormat() != 0;
        boolean colorrgb = format == 0x8000000 || format == 0x2000000 || format == -2147483640 || format == 0x40000008;
        boolean coloryuv = format == 0x40000000 || format == 0x20000000 || format == 0x10000000;
        BytePointer imageData = this.raw_image.GetData().capacity(this.raw_image.GetDataSize());
        if ((depth == 8 || frameEndian.equals(ByteOrder.nativeOrder())) && (this.imageMode == FrameGrabber.ImageMode.RAW || this.imageMode == FrameGrabber.ImageMode.COLOR && numChannels == 3 || this.imageMode == FrameGrabber.ImageMode.GRAY && numChannels == 1 && !colorbayer)) {
            if (this.return_image == null) {
                this.return_image = IplImage.createHeader((int)w, (int)h, (int)depth, (int)numChannels);
            }
            this.return_image.widthStep(stride);
            this.return_image.imageSize(size);
            this.return_image.imageData(imageData);
        } else {
            if (this.return_image == null) {
                this.return_image = IplImage.create((int)w, (int)h, (int)depth, (int)(this.imageMode == FrameGrabber.ImageMode.COLOR ? 3 : 1));
            }
            if (this.temp_image == null) {
                this.temp_image = this.imageMode == FrameGrabber.ImageMode.COLOR && (numChannels > 1 || depth > 8) && !coloryuv && !colorbayer ? IplImage.create((int)w, (int)h, (int)depth, (int)numChannels) : (this.imageMode == FrameGrabber.ImageMode.GRAY && colorbayer ? IplImage.create((int)w, (int)h, (int)depth, (int)3) : (this.imageMode == FrameGrabber.ImageMode.GRAY && colorrgb ? IplImage.createHeader((int)w, (int)h, (int)depth, (int)3) : (this.imageMode == FrameGrabber.ImageMode.COLOR && numChannels == 1 && !coloryuv && !colorbayer ? IplImage.createHeader((int)w, (int)h, (int)depth, (int)1) : this.return_image)));
            }
            this.setStride(this.conv_image, this.temp_image.widthStep());
            this.conv_image.SetData(this.temp_image.imageData(), this.temp_image.width() * this.temp_image.height() * this.temp_image.depth());
            if (depth == 8) {
                this.setPixelFormat(this.conv_image, this.imageMode == FrameGrabber.ImageMode.RAW ? 0x400000 : (this.temp_image.nChannels() == 1 ? Integer.MIN_VALUE : -2147483640));
            } else {
                this.setPixelFormat(this.conv_image, this.imageMode == FrameGrabber.ImageMode.RAW ? 0x200000 : (this.temp_image.nChannels() == 1 ? 0x4000000 : 0x2000000));
            }
            if (depth != 8 && this.conv_image.GetPixelFormat() == format && this.conv_image.GetStride() == stride) {
                ShortBuffer in = imageData.asByteBuffer().order(frameEndian).asShortBuffer();
                ShortBuffer out = this.temp_image.getByteBuffer().order(ByteOrder.nativeOrder()).asShortBuffer();
                out.put(in);
                alreadySwapped = true;
            } else if (this.imageMode == FrameGrabber.ImageMode.GRAY && colorrgb || this.imageMode == FrameGrabber.ImageMode.COLOR && numChannels == 1 && !coloryuv && !colorbayer) {
                this.temp_image.widthStep(stride);
                this.temp_image.imageSize(size);
                this.temp_image.imageData(imageData);
            } else if (!colorrgb && (colorbayer || coloryuv || numChannels > 1) && (error = this.raw_image.Convert(this.conv_image)).notEquals(0)) {
                FlyCapture2FrameGrabber.PrintError(error);
                throw new FrameGrabber.Exception("raw_image.Convert Error " + error);
            }
            if (!alreadySwapped && depth != 8 && !frameEndian.equals(ByteOrder.nativeOrder())) {
                ByteBuffer bb = this.temp_image.getByteBuffer();
                ShortBuffer in = bb.order(frameEndian).asShortBuffer();
                ShortBuffer out = bb.order(ByteOrder.nativeOrder()).asShortBuffer();
                out.put(in);
            }
            if (this.imageMode == FrameGrabber.ImageMode.COLOR && numChannels == 1 && !coloryuv && !colorbayer) {
                opencv_imgproc.cvCvtColor((CvArr)this.temp_image, (CvArr)this.return_image, (int)8);
            } else if (this.imageMode == FrameGrabber.ImageMode.GRAY && (colorbayer || colorrgb)) {
                opencv_imgproc.cvCvtColor((CvArr)this.temp_image, (CvArr)this.return_image, (int)6);
            }
        }
        int bayerFormat = this.cameraInfo.bayerTileFormat();
        switch (bayerFormat) {
            case 4: {
                this.sensorPattern = 0x100000001L;
                break;
            }
            case 3: {
                this.sensorPattern = 0x100000000L;
                break;
            }
            case 2: {
                this.sensorPattern = 1L;
                break;
            }
            case 1: {
                this.sensorPattern = 0L;
                break;
            }
            default: {
                this.sensorPattern = -1L;
            }
        }
        TimeStamp timeStamp = this.raw_image.GetTimeStamp();
        this.timestamp = timeStamp.seconds() * 1000000L + (long)timeStamp.microSeconds();
        return this.converter.convert(this.return_image);
    }
}

