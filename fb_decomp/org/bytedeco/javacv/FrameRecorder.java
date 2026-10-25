/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.bytedeco.javacv.Frame;

public abstract class FrameRecorder
implements Closeable {
    public static final List<String> list = new LinkedList<String>(Arrays.asList("FFmpeg", "OpenCV"));
    protected String format;
    protected String videoCodecName;
    protected String audioCodecName;
    protected int imageWidth;
    protected int imageHeight;
    protected int audioChannels;
    protected int pixelFormat;
    protected int videoCodec;
    protected int videoBitrate;
    protected int imageScalingFlags;
    protected int gopSize = -1;
    protected int videoProfile = -1;
    protected double aspectRatio;
    protected double frameRate;
    protected double videoQuality = -1.0;
    protected int sampleFormat;
    protected int audioCodec;
    protected int audioBitrate;
    protected int sampleRate;
    protected double audioQuality = -1.0;
    protected boolean interleaved;
    protected Charset charset = Charset.defaultCharset();
    protected Map<String, String> options = new HashMap<String, String>();
    protected Map<String, String> videoOptions = new HashMap<String, String>();
    protected Map<String, String> audioOptions = new HashMap<String, String>();
    protected Map<String, String> metadata = new HashMap<String, String>();
    protected Map<String, String> videoMetadata = new HashMap<String, String>();
    protected Map<String, String> audioMetadata = new HashMap<String, String>();
    protected Map<String, Buffer> videoSideData = new HashMap<String, Buffer>();
    protected Map<String, Buffer> audioSideData = new HashMap<String, Buffer>();
    protected int frameNumber = 0;
    protected long timestamp = 0L;
    protected int maxBFrames = -1;
    protected int trellis = -1;
    protected int maxDelay = -1;

    public static void init() {
        for (String name : list) {
            try {
                Class<? extends FrameRecorder> c = FrameRecorder.get(name);
                c.getMethod("tryLoad", new Class[0]).invoke(null, new Object[0]);
            }
            catch (Throwable throwable) {}
        }
    }

    public static Class<? extends FrameRecorder> getDefault() {
        for (String name : list) {
            try {
                Class<? extends FrameRecorder> c = FrameRecorder.get(name);
                c.getMethod("tryLoad", new Class[0]).invoke(null, new Object[0]);
                return c;
            }
            catch (Throwable throwable) {
            }
        }
        return null;
    }

    public static Class<? extends FrameRecorder> get(String className) throws Exception {
        className = FrameRecorder.class.getPackage().getName() + "." + className;
        try {
            return Class.forName(className).asSubclass(FrameRecorder.class);
        }
        catch (ClassNotFoundException e) {
            String className2 = className + "FrameRecorder";
            try {
                return Class.forName(className2).asSubclass(FrameRecorder.class);
            }
            catch (ClassNotFoundException ex) {
                throw new Exception("Could not get FrameRecorder class for " + className + " or " + className2, e);
            }
        }
    }

    public static FrameRecorder create(Class<? extends FrameRecorder> c, Class p, Object o, int w, int h) throws Exception {
        Throwable cause = null;
        try {
            return c.getConstructor(p, Integer.TYPE, Integer.TYPE).newInstance(o, w, h);
        }
        catch (InstantiationException ex) {
            cause = ex;
        }
        catch (IllegalAccessException ex) {
            cause = ex;
        }
        catch (IllegalArgumentException ex) {
            cause = ex;
        }
        catch (NoSuchMethodException ex) {
            cause = ex;
        }
        catch (InvocationTargetException ex) {
            cause = ex.getCause();
        }
        throw new Exception("Could not create new " + c.getSimpleName() + "(" + o + ", " + w + ", " + h + ")", cause);
    }

    public static FrameRecorder createDefault(File file, int width, int height) throws Exception {
        return FrameRecorder.create(FrameRecorder.getDefault(), File.class, file, width, height);
    }

    public static FrameRecorder createDefault(String filename, int width, int height) throws Exception {
        return FrameRecorder.create(FrameRecorder.getDefault(), String.class, filename, width, height);
    }

    public static FrameRecorder create(String className, File file, int width, int height) throws Exception {
        return FrameRecorder.create(FrameRecorder.get(className), File.class, file, width, height);
    }

    public static FrameRecorder create(String className, String filename, int width, int height) throws Exception {
        return FrameRecorder.create(FrameRecorder.get(className), String.class, filename, width, height);
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getVideoCodecName() {
        return this.videoCodecName;
    }

    public void setVideoCodecName(String videoCodecName) {
        this.videoCodecName = videoCodecName;
    }

    public String getAudioCodecName() {
        return this.audioCodecName;
    }

    public void setAudioCodecName(String audioCodecName) {
        this.audioCodecName = audioCodecName;
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

    public int getAudioChannels() {
        return this.audioChannels;
    }

    public void setAudioChannels(int audioChannels) {
        this.audioChannels = audioChannels;
    }

    public int getPixelFormat() {
        return this.pixelFormat;
    }

    public void setPixelFormat(int pixelFormat) {
        this.pixelFormat = pixelFormat;
    }

    public int getVideoCodec() {
        return this.videoCodec;
    }

    public void setVideoCodec(int videoCodec) {
        this.videoCodec = videoCodec;
    }

    public int getVideoBitrate() {
        return this.videoBitrate;
    }

    public void setVideoBitrate(int videoBitrate) {
        this.videoBitrate = videoBitrate;
    }

    public int getImageScalingFlags() {
        return this.imageScalingFlags;
    }

    public void setImageScalingFlags(int imageScalingFlags) {
        this.imageScalingFlags = imageScalingFlags;
    }

    public int getGopSize() {
        return this.gopSize;
    }

    public void setGopSize(int gopSize) {
        this.gopSize = gopSize;
    }

    public int getVideoProfile() {
        return this.videoProfile;
    }

    public void setVideoProfile(int videoProfile) {
        this.videoProfile = videoProfile;
    }

    public double getAspectRatio() {
        return this.aspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public double getFrameRate() {
        return this.frameRate;
    }

    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }

    public double getVideoQuality() {
        return this.videoQuality;
    }

    public void setVideoQuality(double videoQuality) {
        this.videoQuality = videoQuality;
    }

    public int getSampleFormat() {
        return this.sampleFormat;
    }

    public void setSampleFormat(int sampleFormat) {
        this.sampleFormat = sampleFormat;
    }

    public int getAudioCodec() {
        return this.audioCodec;
    }

    public void setAudioCodec(int audioCodec) {
        this.audioCodec = audioCodec;
    }

    public int getAudioBitrate() {
        return this.audioBitrate;
    }

    public void setAudioBitrate(int audioBitrate) {
        this.audioBitrate = audioBitrate;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public double getAudioQuality() {
        return this.audioQuality;
    }

    public void setAudioQuality(double audioQuality) {
        this.audioQuality = audioQuality;
    }

    public boolean isInterleaved() {
        return this.interleaved;
    }

    public void setInterleaved(boolean interleaved) {
        this.interleaved = interleaved;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public Map<String, String> getOptions() {
        return this.options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public Map<String, String> getVideoOptions() {
        return this.videoOptions;
    }

    public void setVideoOptions(Map<String, String> options) {
        this.videoOptions = options;
    }

    public Map<String, String> getAudioOptions() {
        return this.audioOptions;
    }

    public void setAudioOptions(Map<String, String> options) {
        this.audioOptions = options;
    }

    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Map<String, String> getVideoMetadata() {
        return this.videoMetadata;
    }

    public void setVideoMetadata(Map<String, String> metadata) {
        this.videoMetadata = metadata;
    }

    public Map<String, String> getAudioMetadata() {
        return this.audioMetadata;
    }

    public void setAudioMetadata(Map<String, String> metadata) {
        this.audioMetadata = metadata;
    }

    public String getOption(String key) {
        return this.options.get(key);
    }

    public void setOption(String key, String value) {
        this.options.put(key, value);
    }

    public String getVideoOption(String key) {
        return this.videoOptions.get(key);
    }

    public void setVideoOption(String key, String value) {
        this.videoOptions.put(key, value);
    }

    public String getAudioOption(String key) {
        return this.audioOptions.get(key);
    }

    public void setAudioOption(String key, String value) {
        this.audioOptions.put(key, value);
    }

    public String getMetadata(String key) {
        return this.metadata.get(key);
    }

    public void setMetadata(String key, String value) {
        this.metadata.put(key, value);
    }

    public String getVideoMetadata(String key) {
        return this.videoMetadata.get(key);
    }

    public void setVideoMetadata(String key, String value) {
        this.videoMetadata.put(key, value);
    }

    public String getAudioMetadata(String key) {
        return this.audioMetadata.get(key);
    }

    public void setAudioMetadata(String key, String value) {
        this.audioMetadata.put(key, value);
    }

    public Map<String, Buffer> getVideoSideData() {
        return this.videoSideData;
    }

    public void setVideoSideData(Map<String, Buffer> videoSideData) {
        this.videoSideData = videoSideData;
    }

    public Buffer getVideoSideData(String key) {
        return this.videoSideData.get(key);
    }

    public void setVideoSideData(String key, Buffer value) {
        this.videoSideData.put(key, value);
    }

    public Map<String, Buffer> getAudioSideData() {
        return this.audioSideData;
    }

    public void setAudioSideData(Map<String, Buffer> audioSideData) {
        this.audioSideData = audioSideData;
    }

    public Buffer getAudioSideData(String key) {
        return this.audioSideData.get(key);
    }

    public void setAudioSideData(String key, Buffer value) {
        this.audioSideData.put(key, value);
    }

    public int getFrameNumber() {
        return this.frameNumber;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getMaxBFrames() {
        return this.maxBFrames;
    }

    public void setMaxBFrames(int maxBFrames) {
        this.maxBFrames = maxBFrames;
    }

    public int getTrellis() {
        return this.trellis;
    }

    public void setTrellis(int trellis) {
        this.trellis = trellis;
    }

    public int getMaxDelay() {
        return this.maxDelay;
    }

    public void setMaxDelay(int maxDelay) {
        this.maxDelay = maxDelay;
    }

    public abstract void start() throws Exception;

    public abstract void flush() throws Exception;

    public abstract void stop() throws Exception;

    public abstract void record(Frame var1) throws Exception;

    public abstract void release() throws Exception;

    @Override
    public void close() throws Exception {
        this.stop();
        this.release();
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

