/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import java.io.Closeable;
import java.io.IOException;
import org.bytedeco.javacv.FFmpegFrameFilter;
import org.bytedeco.javacv.Frame;

public abstract class FrameFilter
implements Closeable {
    protected String filters;
    protected int imageWidth;
    protected int imageHeight;
    protected int pixelFormat;
    protected double frameRate;
    protected double aspectRatio;
    protected int videoInputs;
    protected String[] videoFilterArgs;
    protected String afilters;
    protected int audioChannels;
    protected int sampleFormat;
    protected int sampleRate;
    protected int audioInputs;
    protected String[] audioFilterArgs;

    public static FrameFilter createDefault(String filtersDescr, int imageWidth, int imageHeight) throws Exception {
        return new FFmpegFrameFilter(filtersDescr, imageWidth, imageHeight);
    }

    public String getFilters() {
        return this.filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public int getImageWidth() {
        return this.imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return this.imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getPixelFormat() {
        return this.pixelFormat;
    }

    public void setPixelFormat(int pixelFormat) {
        this.pixelFormat = pixelFormat;
    }

    public double getFrameRate() {
        return this.frameRate;
    }

    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }

    public double getAspectRatio() {
        return this.aspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public int getVideoInputs() {
        return this.videoInputs;
    }

    public void setVideoInputs(int videoInputs) {
        this.videoInputs = videoInputs;
    }

    public String[] getVideoFilterArgs() {
        return this.videoFilterArgs;
    }

    public void setVideoFilterArgs(String[] videoFilterArgs) {
        this.videoFilterArgs = videoFilterArgs;
    }

    public int getAudioChannels() {
        return this.audioChannels;
    }

    public void setAudioChannels(int audioChannels) {
        this.audioChannels = audioChannels;
    }

    public int getSampleFormat() {
        return this.sampleFormat;
    }

    public void setSampleFormat(int sampleFormat) {
        this.sampleFormat = sampleFormat;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public int getAudioInputs() {
        return this.audioInputs;
    }

    public void setAudioInputs(int audioInputs) {
        this.audioInputs = audioInputs;
    }

    public String[] getAudioFilterArgs() {
        return this.audioFilterArgs;
    }

    public void setAudioFilterArgs(String[] audioFilterArgs) {
        this.audioFilterArgs = audioFilterArgs;
    }

    public abstract void start() throws Exception;

    public abstract void stop() throws Exception;

    public abstract void push(Frame var1) throws Exception;

    public abstract Frame pull() throws Exception;

    public abstract void release() throws Exception;

    @Override
    public void close() throws Exception {
        this.stop();
        this.release();
    }

    public void restart() throws Exception {
        this.stop();
        this.start();
    }

    public void flush() throws Exception {
        while (this.pull() != null) {
        }
    }

    public static class Exception
    extends IOException {
        public Exception(String message) {
            super(message);
        }

        public Exception(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

