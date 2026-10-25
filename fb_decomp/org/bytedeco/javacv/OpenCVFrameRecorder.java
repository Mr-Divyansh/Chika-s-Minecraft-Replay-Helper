/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.global.opencv_highgui
 *  org.bytedeco.opencv.opencv_core.Mat
 *  org.bytedeco.opencv.opencv_core.Size
 *  org.bytedeco.opencv.opencv_videoio.VideoWriter
 */
package org.bytedeco.javacv;

import java.io.File;
import java.util.Map;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_highgui;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_videoio.VideoWriter;

public class OpenCVFrameRecorder
extends FrameRecorder {
    private static FrameRecorder.Exception loadingException = null;
    private static final boolean windows = Loader.getPlatform().startsWith("windows");
    private String filename;
    private VideoWriter writer = null;
    private OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();

    public static OpenCVFrameRecorder createDefault(File f, int w, int h) throws FrameRecorder.Exception {
        return new OpenCVFrameRecorder(f, w, h);
    }

    public static OpenCVFrameRecorder createDefault(String f, int w, int h) throws FrameRecorder.Exception {
        return new OpenCVFrameRecorder(f, w, h);
    }

    public static void tryLoad() throws FrameRecorder.Exception {
        if (loadingException != null) {
            throw loadingException;
        }
        try {
            Loader.load(opencv_highgui.class);
        }
        catch (Throwable t) {
            loadingException = new FrameRecorder.Exception("Failed to load " + OpenCVFrameRecorder.class, t);
            throw loadingException;
        }
    }

    public OpenCVFrameRecorder(File file, int imageWidth, int imageHeight) {
        this(file.getAbsolutePath(), imageWidth, imageHeight);
    }

    public OpenCVFrameRecorder(String filename, int imageWidth, int imageHeight) {
        this.filename = filename;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.pixelFormat = 1;
        this.videoCodec = windows ? -1 : VideoWriter.fourcc((byte)73, (byte)89, (byte)85, (byte)86);
        this.frameRate = 30.0;
    }

    @Override
    public void release() throws FrameRecorder.Exception {
        if (this.writer != null) {
            this.writer.release();
            this.writer = null;
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.release();
    }

    public double getOption(int propId) {
        if (this.writer != null) {
            return this.writer.get(propId);
        }
        return Double.parseDouble((String)this.options.get(Integer.toString(propId)));
    }

    public void setOption(int propId, double value) {
        this.options.put(Integer.toString(propId), Double.toString(value));
        if (this.writer != null) {
            this.writer.set(propId, value);
        }
    }

    @Override
    public void start() throws FrameRecorder.Exception {
        this.writer = new VideoWriter(this.filename, this.fourCCCodec(), this.frameRate, new Size(this.imageWidth, this.imageHeight), this.isColour());
        for (Map.Entry e : this.options.entrySet()) {
            this.writer.set(Integer.parseInt((String)e.getKey()), Double.parseDouble((String)e.getValue()));
        }
    }

    private boolean isColour() {
        return this.pixelFormat != 0;
    }

    private int fourCCCodec() {
        return this.videoCodec;
    }

    @Override
    public void flush() throws FrameRecorder.Exception {
    }

    @Override
    public void stop() throws FrameRecorder.Exception {
        this.release();
    }

    @Override
    public void record(Frame frame) throws FrameRecorder.Exception {
        Mat mat = this.converter.convert(frame);
        if (this.writer == null) {
            throw new FrameRecorder.Exception("Cannot record: There is no writer (Has start() been called?)");
        }
        this.writer.write(mat);
        frame.keyFrame = true;
    }
}

