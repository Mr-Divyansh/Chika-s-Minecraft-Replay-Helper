/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.global.opencv_highgui
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.Mat
 *  org.bytedeco.opencv.opencv_videoio.VideoCapture
 *  org.bytedeco.opencv.opencv_videoio.VideoWriter
 */
package org.bytedeco.javacv;

import java.io.File;
import java.util.Map;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_highgui;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.bytedeco.opencv.opencv_videoio.VideoWriter;

public class OpenCVFrameGrabber
extends FrameGrabber {
    private static FrameGrabber.Exception loadingException = null;
    private int deviceNumber = 0;
    private String filename = null;
    private int apiPreference = 0;
    private VideoCapture capture = null;
    private Mat returnMatrix = null;
    private final OpenCVFrameConverter converter = new OpenCVFrameConverter.ToMat();
    private final Mat mat = new Mat();

    public static String[] getDeviceDescriptions() throws FrameGrabber.Exception {
        OpenCVFrameGrabber.tryLoad();
        throw new UnsupportedOperationException("Device enumeration not support by OpenCV.");
    }

    public static OpenCVFrameGrabber createDefault(File deviceFile) throws FrameGrabber.Exception {
        return new OpenCVFrameGrabber(deviceFile);
    }

    public static OpenCVFrameGrabber createDefault(String devicePath) throws FrameGrabber.Exception {
        return new OpenCVFrameGrabber(devicePath);
    }

    public static OpenCVFrameGrabber createDefault(int deviceNumber) throws FrameGrabber.Exception {
        return new OpenCVFrameGrabber(deviceNumber);
    }

    public static void tryLoad() throws FrameGrabber.Exception {
        if (loadingException != null) {
            throw loadingException;
        }
        try {
            Loader.load(opencv_highgui.class);
        }
        catch (Throwable t) {
            loadingException = new FrameGrabber.Exception("Failed to load " + OpenCVFrameGrabber.class, t);
            throw loadingException;
        }
    }

    public OpenCVFrameGrabber(int deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public OpenCVFrameGrabber(File file) {
        this(file.getAbsolutePath());
    }

    public OpenCVFrameGrabber(File file, int apiPreference) {
        this(file.getAbsolutePath(), apiPreference);
    }

    public OpenCVFrameGrabber(String filename) {
        this.filename = filename;
    }

    public OpenCVFrameGrabber(String filename, int apiPreference) {
        this.filename = filename;
        this.apiPreference = apiPreference;
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
    public String getFormat() {
        if (this.capture == null) {
            return super.getFormat();
        }
        int fourcc = (int)this.capture.get(6);
        return "" + (char)(fourcc & 0xFF) + (char)(fourcc >> 8 & 0xFF) + (char)(fourcc >> 16 & 0xFF) + (char)(fourcc >> 24 & 0xFF);
    }

    @Override
    public int getImageWidth() {
        if (this.returnMatrix != null) {
            return this.returnMatrix.cols();
        }
        return this.capture == null ? super.getImageWidth() : (int)this.capture.get(3);
    }

    @Override
    public int getImageHeight() {
        if (this.returnMatrix != null) {
            return this.returnMatrix.rows();
        }
        return this.capture == null ? super.getImageHeight() : (int)this.capture.get(4);
    }

    @Override
    public int getPixelFormat() {
        return this.capture == null ? super.getPixelFormat() : (int)this.capture.get(16);
    }

    @Override
    public double getFrameRate() {
        return this.capture == null ? super.getFrameRate() : (double)((int)this.capture.get(5));
    }

    @Override
    public void setImageMode(FrameGrabber.ImageMode imageMode) {
        if (imageMode != this.imageMode) {
            this.returnMatrix = null;
        }
        super.setImageMode(imageMode);
    }

    @Override
    public int getFrameNumber() {
        return this.capture == null ? super.getFrameNumber() : (int)this.capture.get(1);
    }

    @Override
    public void setFrameNumber(int frameNumber) throws FrameGrabber.Exception {
        if (this.capture == null) {
            super.setFrameNumber(frameNumber);
        } else if (!this.capture.set(1, (double)frameNumber)) {
            throw new FrameGrabber.Exception("set() Error: Could not set CAP_PROP_POS_FRAMES to " + frameNumber + ".");
        }
    }

    @Override
    public long getTimestamp() {
        return this.capture == null ? super.getTimestamp() : Math.round(this.capture.get(0) * 1000.0);
    }

    @Override
    public void setTimestamp(long timestamp) throws FrameGrabber.Exception {
        if (this.capture == null) {
            super.setTimestamp(timestamp);
        } else if (!this.capture.set(0, (double)timestamp / 1000.0)) {
            throw new FrameGrabber.Exception("set() Error: Could not set CAP_PROP_POS_MSEC to " + (double)timestamp / 1000.0 + ".");
        }
    }

    @Override
    public int getLengthInFrames() {
        return this.capture == null ? super.getLengthInFrames() : (int)this.capture.get(7);
    }

    @Override
    public long getLengthInTime() {
        return Math.round((double)((long)this.getLengthInFrames() * 1000000L) / this.getFrameRate());
    }

    public double getOption(int propId) {
        if (this.capture != null) {
            return this.capture.get(propId);
        }
        return Double.parseDouble((String)this.options.get(Integer.toString(propId)));
    }

    public void setOption(int propId, double value) {
        this.options.put(Integer.toString(propId), Double.toString(value));
        if (this.capture != null) {
            this.capture.set(propId, value);
        }
    }

    @Override
    public void start() throws FrameGrabber.Exception {
        this.capture = this.filename != null && this.filename.length() > 0 ? (this.apiPreference > 0 ? new VideoCapture(this.filename, this.apiPreference) : new VideoCapture(this.filename)) : new VideoCapture(this.deviceNumber);
        if (this.format != null && this.format.length() >= 4) {
            this.format = this.format.toUpperCase();
            byte cc0 = (byte)this.format.charAt(0);
            byte cc1 = (byte)this.format.charAt(1);
            byte cc2 = (byte)this.format.charAt(2);
            byte cc3 = (byte)this.format.charAt(3);
            this.capture.set(6, (double)VideoWriter.fourcc((byte)cc0, (byte)cc1, (byte)cc2, (byte)cc3));
        }
        if (this.imageWidth > 0 && !this.capture.set(3, (double)this.imageWidth)) {
            this.capture.set(3, (double)this.imageWidth);
        }
        if (this.imageHeight > 0 && !this.capture.set(4, (double)this.imageHeight)) {
            this.capture.set(4, (double)this.imageHeight);
        }
        if (this.frameRate > 0.0) {
            this.capture.set(5, this.frameRate);
        }
        if (this.bpp > 0) {
            this.capture.set(8, (double)this.bpp);
        }
        if (this.imageMode == FrameGrabber.ImageMode.RAW) {
            this.capture.set(16, 0.0);
        }
        for (Map.Entry e : this.options.entrySet()) {
            this.capture.set(Integer.parseInt((String)e.getKey()), Double.parseDouble((String)e.getValue()));
        }
        Mat mat = new Mat();
        try {
            int count = 0;
            while (count++ < 100 && !this.capture.read(mat)) {
                Thread.sleep(100L);
            }
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        if (!this.capture.read(mat)) {
            throw new FrameGrabber.Exception("read() Error: Could not read frame in start().");
        }
        if (!this.triggerMode && !this.capture.grab()) {
            throw new FrameGrabber.Exception("grab() Error: Could not grab frame. (Has start() been called?)");
        }
    }

    @Override
    public void stop() throws FrameGrabber.Exception {
        if (this.capture != null) {
            this.capture.release();
            this.capture = null;
        }
    }

    @Override
    public void trigger() throws FrameGrabber.Exception {
        Mat mat = new Mat();
        for (int i = 0; i < this.numBuffers + 1; ++i) {
            this.capture.read(mat);
        }
        if (!this.capture.grab()) {
            throw new FrameGrabber.Exception("grab() Error: Could not grab frame. (Has start() been called?)");
        }
    }

    @Override
    public Frame grab() throws FrameGrabber.Exception {
        if (!this.capture.retrieve(this.mat)) {
            throw new FrameGrabber.Exception("retrieve() Error: Could not retrieve frame. (Has start() been called?)");
        }
        if (!this.triggerMode && !this.capture.grab()) {
            throw new FrameGrabber.Exception("grab() Error: Could not grab frame. (Has start() been called?)");
        }
        if (this.imageMode == FrameGrabber.ImageMode.GRAY && this.mat.channels() > 1) {
            if (this.returnMatrix == null) {
                this.returnMatrix = new Mat(new int[]{this.mat.rows(), this.mat.cols(), this.mat.depth(), 1});
            }
            opencv_imgproc.cvtColor((Mat)this.mat, (Mat)this.returnMatrix, (int)6);
        } else if (this.imageMode == FrameGrabber.ImageMode.COLOR && this.mat.channels() == 1) {
            if (this.returnMatrix == null) {
                this.returnMatrix = new Mat(new int[]{this.mat.rows(), this.mat.cols(), this.mat.depth(), 3});
            }
            opencv_imgproc.cvtColor((Mat)this.mat, (Mat)this.returnMatrix, (int)8);
        } else {
            this.returnMatrix = this.mat;
        }
        return this.converter.convert(this.returnMatrix);
    }
}

