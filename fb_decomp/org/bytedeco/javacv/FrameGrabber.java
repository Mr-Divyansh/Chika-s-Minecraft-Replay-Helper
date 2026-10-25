/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import java.beans.PropertyEditorSupport;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.bytedeco.javacv.Frame;

public abstract class FrameGrabber
implements Closeable {
    public static final List<String> list = new LinkedList<String>(Arrays.asList("DC1394", "FlyCapture", "FlyCapture2", "OpenKinect", "OpenKinect2", "RealSense", "RealSense2", "PS3Eye", "VideoInput", "OpenCV", "FFmpeg", "IPCamera"));
    public static final long SENSOR_PATTERN_RGGB = 0L;
    public static final long SENSOR_PATTERN_GBRG = 0x100000000L;
    public static final long SENSOR_PATTERN_GRBG = 1L;
    public static final long SENSOR_PATTERN_BGGR = 0x100000001L;
    protected int videoStream = -1;
    protected int audioStream = -1;
    protected int videoDisposition = 0;
    protected int audioDisposition = 0;
    protected String format = null;
    protected String videoCodecName = null;
    protected String audioCodecName = null;
    protected int imageWidth = 0;
    protected int imageHeight = 0;
    protected int audioChannels = 0;
    protected ImageMode imageMode = ImageMode.COLOR;
    protected long sensorPattern = -1L;
    protected int pixelFormat = -1;
    protected int videoCodec;
    protected int videoBitrate = 0;
    protected int imageScalingFlags = 0;
    protected double aspectRatio = 0.0;
    protected double frameRate = 0.0;
    protected SampleMode sampleMode = SampleMode.SHORT;
    protected int sampleFormat = -1;
    protected int audioCodec;
    protected int audioBitrate = 0;
    protected int sampleRate = 0;
    protected boolean triggerMode = false;
    protected int bpp = 0;
    protected int timeout = 10000;
    protected int numBuffers = 4;
    protected double gamma = 0.0;
    protected boolean deinterlace = false;
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
    protected int maxDelay = -1;
    protected long startTime = 0L;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Future<Void> future = null;
    private Frame delayedFrame = null;
    private long delayedTime = 0L;

    public static void init() {
        for (String name : list) {
            try {
                Class<? extends FrameGrabber> c = FrameGrabber.get(name);
                c.getMethod("tryLoad", new Class[0]).invoke(null, new Object[0]);
            }
            catch (Throwable t) {}
        }
    }

    public static Class<? extends FrameGrabber> getDefault() {
        for (String name : list) {
            try {
                boolean mayContainCameras;
                Class<? extends FrameGrabber> c;
                block6: {
                    c = FrameGrabber.get(name);
                    c.getMethod("tryLoad", new Class[0]).invoke(null, new Object[0]);
                    mayContainCameras = false;
                    try {
                        String[] s = (String[])c.getMethod("getDeviceDescriptions", new Class[0]).invoke(null, new Object[0]);
                        if (s.length > 0) {
                            mayContainCameras = true;
                        }
                    }
                    catch (Throwable t) {
                        if (!(t.getCause() instanceof UnsupportedOperationException)) break block6;
                        mayContainCameras = true;
                    }
                }
                if (!mayContainCameras) continue;
                return c;
            }
            catch (Throwable t) {
            }
        }
        return null;
    }

    public static Class<? extends FrameGrabber> get(String className) throws Exception {
        className = FrameGrabber.class.getPackage().getName() + "." + className;
        try {
            return Class.forName(className).asSubclass(FrameGrabber.class);
        }
        catch (ClassNotFoundException e) {
            String className2 = className + "FrameGrabber";
            try {
                return Class.forName(className2).asSubclass(FrameGrabber.class);
            }
            catch (ClassNotFoundException ex) {
                throw new Exception("Could not get FrameGrabber class for " + className + " or " + className2, e);
            }
        }
    }

    public static FrameGrabber create(Class<? extends FrameGrabber> c, Class p, Object o) throws Exception {
        Throwable cause = null;
        try {
            return c.getConstructor(p).newInstance(o);
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
        throw new Exception("Could not create new " + c.getSimpleName() + "(" + o + ")", cause);
    }

    public static FrameGrabber createDefault(File deviceFile) throws Exception {
        return FrameGrabber.create(FrameGrabber.getDefault(), File.class, deviceFile);
    }

    public static FrameGrabber createDefault(String devicePath) throws Exception {
        return FrameGrabber.create(FrameGrabber.getDefault(), String.class, devicePath);
    }

    public static FrameGrabber createDefault(int deviceNumber) throws Exception {
        try {
            return FrameGrabber.create(FrameGrabber.getDefault(), Integer.TYPE, deviceNumber);
        }
        catch (Exception ex) {
            return FrameGrabber.create(FrameGrabber.getDefault(), Integer.class, deviceNumber);
        }
    }

    public static FrameGrabber create(String className, File deviceFile) throws Exception {
        return FrameGrabber.create(FrameGrabber.get(className), File.class, deviceFile);
    }

    public static FrameGrabber create(String className, String devicePath) throws Exception {
        return FrameGrabber.create(FrameGrabber.get(className), String.class, devicePath);
    }

    public static FrameGrabber create(String className, int deviceNumber) throws Exception {
        try {
            return FrameGrabber.create(FrameGrabber.get(className), Integer.TYPE, deviceNumber);
        }
        catch (Exception ex) {
            return FrameGrabber.create(FrameGrabber.get(className), Integer.class, deviceNumber);
        }
    }

    public int getVideoStream() {
        return this.videoStream;
    }

    public void setVideoStream(int videoStream) {
        this.videoStream = videoStream;
    }

    public int getAudioStream() {
        return this.audioStream;
    }

    public void setAudioStream(int audioStream) {
        this.audioStream = audioStream;
    }

    public void setVideoDisposition(int videoDisposition) {
        this.videoDisposition = videoDisposition;
    }

    public int getVideoDisposition() {
        return this.videoDisposition;
    }

    public void setAudioDisposition(int audioDisposition) {
        this.audioDisposition = audioDisposition;
    }

    public int getAudioDisposition() {
        return this.audioDisposition;
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

    public ImageMode getImageMode() {
        return this.imageMode;
    }

    public void setImageMode(ImageMode imageMode) {
        this.imageMode = imageMode;
    }

    public long getSensorPattern() {
        return this.sensorPattern;
    }

    public void setSensorPattern(long sensorPattern) {
        this.sensorPattern = sensorPattern;
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

    public SampleMode getSampleMode() {
        return this.sampleMode;
    }

    public void setSampleMode(SampleMode samplesMode) {
        this.sampleMode = samplesMode;
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

    public boolean isTriggerMode() {
        return this.triggerMode;
    }

    public void setTriggerMode(boolean triggerMode) {
        this.triggerMode = triggerMode;
    }

    public int getBitsPerPixel() {
        return this.bpp;
    }

    public void setBitsPerPixel(int bitsPerPixel) {
        this.bpp = bitsPerPixel;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getNumBuffers() {
        return this.numBuffers;
    }

    public void setNumBuffers(int numBuffers) {
        this.numBuffers = numBuffers;
    }

    public double getGamma() {
        return this.gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public boolean isDeinterlace() {
        return this.deinterlace;
    }

    public void setDeinterlace(boolean deinterlace) {
        this.deinterlace = deinterlace;
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

    public void setFrameNumber(int frameNumber) throws Exception {
        this.frameNumber = frameNumber;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) throws Exception {
        this.timestamp = timestamp;
    }

    public int getMaxDelay() {
        return this.maxDelay;
    }

    public void setMaxDelay(int maxDelay) {
        this.maxDelay = maxDelay;
    }

    public int getLengthInFrames() {
        return 0;
    }

    public long getLengthInTime() {
        return 0L;
    }

    public abstract void start() throws Exception;

    public abstract void stop() throws Exception;

    public abstract void trigger() throws Exception;

    @Override
    public void close() throws Exception {
        this.stop();
        this.release();
    }

    public abstract Frame grab() throws Exception;

    public Frame grabFrame() throws Exception {
        return this.grab();
    }

    public abstract void release() throws Exception;

    public void restart() throws Exception {
        this.stop();
        this.start();
    }

    public void flush() throws Exception {
        for (int i = 0; i < this.numBuffers + 1; ++i) {
            this.grab();
        }
    }

    public void delayedGrab(final long delayTime) {
        this.delayedFrame = null;
        this.delayedTime = 0L;
        final long start = System.nanoTime() / 1000L;
        if (this.future != null && !this.future.isDone()) {
            return;
        }
        this.future = this.executor.submit(new Callable<Void>(){

            @Override
            public Void call() throws Exception {
                do {
                    FrameGrabber.this.delayedFrame = FrameGrabber.this.grab();
                    FrameGrabber.this.delayedTime = System.nanoTime() / 1000L - start;
                } while (FrameGrabber.this.delayedTime < delayTime);
                return null;
            }
        });
    }

    public long getDelayedTime() throws InterruptedException, ExecutionException {
        if (this.future == null) {
            return 0L;
        }
        this.future.get();
        return this.delayedTime;
    }

    public Frame getDelayedFrame() throws InterruptedException, ExecutionException {
        if (this.future == null) {
            return null;
        }
        this.future.get();
        return this.delayedFrame;
    }

    public Array createArray(FrameGrabber[] frameGrabbers) {
        return new Array(frameGrabbers);
    }

    public Frame grabAtFrameRate() throws Exception, InterruptedException {
        Frame frame = this.grab();
        if (frame != null) {
            this.waitForTimestamp(frame);
        }
        return frame;
    }

    public boolean waitForTimestamp(Frame frame) throws InterruptedException {
        if (this.startTime == 0L) {
            this.startTime = System.nanoTime() / 1000L - frame.timestamp;
        } else {
            long delay = frame.timestamp - (System.nanoTime() / 1000L - this.startTime);
            if (delay > 0L) {
                Thread.sleep(delay / 1000L, (int)(delay % 1000L) * 1000);
                return true;
            }
        }
        return false;
    }

    public void resetStartTime() {
        this.startTime = 0L;
    }

    public static class Array {
        private Frame[] grabbedFrames = null;
        private long[] latencies = null;
        private long[] bestLatencies = null;
        private long lastNewestTimestamp = 0L;
        private long bestInterval = Long.MAX_VALUE;
        protected FrameGrabber[] frameGrabbers = null;

        protected Array(FrameGrabber[] frameGrabbers) {
            this.setFrameGrabbers(frameGrabbers);
        }

        public FrameGrabber[] getFrameGrabbers() {
            return this.frameGrabbers;
        }

        public void setFrameGrabbers(FrameGrabber[] frameGrabbers) {
            this.frameGrabbers = frameGrabbers;
            this.grabbedFrames = new Frame[frameGrabbers.length];
            this.latencies = new long[frameGrabbers.length];
            this.bestLatencies = null;
            this.lastNewestTimestamp = 0L;
        }

        public int size() {
            return this.frameGrabbers.length;
        }

        public void start() throws Exception {
            for (FrameGrabber f : this.frameGrabbers) {
                f.start();
            }
        }

        public void stop() throws Exception {
            for (FrameGrabber f : this.frameGrabbers) {
                f.stop();
            }
        }

        public void trigger() throws Exception {
            for (FrameGrabber f : this.frameGrabbers) {
                if (!f.isTriggerMode()) continue;
                f.trigger();
            }
        }

        public Frame[] grab() throws Exception {
            int i;
            if (this.frameGrabbers.length == 1) {
                this.grabbedFrames[0] = this.frameGrabbers[0].grab();
                return this.grabbedFrames;
            }
            long newestTimestamp = 0L;
            boolean unsynchronized = false;
            for (i = 0; i < this.frameGrabbers.length; ++i) {
                this.grabbedFrames[i] = this.frameGrabbers[i].grab();
                if (this.grabbedFrames[i] != null) {
                    newestTimestamp = Math.max(newestTimestamp, this.frameGrabbers[i].getTimestamp());
                }
                if (this.frameGrabbers[i].getClass() == this.frameGrabbers[(i + 1) % this.frameGrabbers.length].getClass()) continue;
                unsynchronized = true;
            }
            if (unsynchronized) {
                return this.grabbedFrames;
            }
            for (i = 0; i < this.frameGrabbers.length; ++i) {
                if (this.grabbedFrames[i] == null) continue;
                this.latencies[i] = newestTimestamp - Math.max(0L, this.frameGrabbers[i].getTimestamp());
            }
            if (this.bestLatencies == null) {
                this.bestLatencies = Arrays.copyOf(this.latencies, this.latencies.length);
            } else {
                int sum1 = 0;
                int sum2 = 0;
                for (int i2 = 0; i2 < this.frameGrabbers.length; ++i2) {
                    sum1 = (int)((long)sum1 + this.latencies[i2]);
                    sum2 = (int)((long)sum2 + this.bestLatencies[i2]);
                }
                if (sum1 < sum2) {
                    this.bestLatencies = Arrays.copyOf(this.latencies, this.latencies.length);
                }
            }
            this.bestInterval = Math.min(this.bestInterval, newestTimestamp - this.lastNewestTimestamp);
            for (i = 0; i < this.bestLatencies.length; ++i) {
                this.bestLatencies[i] = Math.min(this.bestLatencies[i], this.bestInterval * 9L / 10L);
            }
            for (int j = 0; j < 2; ++j) {
                block5: for (int i3 = 0; i3 < this.frameGrabbers.length; ++i3) {
                    if (this.frameGrabbers[i3].isTriggerMode() || this.grabbedFrames[i3] == null) continue;
                    int latency = (int)(newestTimestamp - Math.max(0L, this.frameGrabbers[i3].getTimestamp()));
                    while ((double)((long)latency - this.bestLatencies[i3]) > 0.1 * (double)this.bestLatencies[i3]) {
                        this.grabbedFrames[i3] = this.frameGrabbers[i3].grab();
                        if (this.grabbedFrames[i3] == null) continue block5;
                        latency = (int)(newestTimestamp - Math.max(0L, this.frameGrabbers[i3].getTimestamp()));
                        if (latency >= 0) continue;
                        newestTimestamp = Math.max(0L, this.frameGrabbers[i3].getTimestamp());
                        continue block5;
                    }
                }
            }
            this.lastNewestTimestamp = newestTimestamp;
            return this.grabbedFrames;
        }

        public void release() throws Exception {
            for (FrameGrabber f : this.frameGrabbers) {
                f.release();
            }
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

    public static enum SampleMode {
        SHORT,
        FLOAT,
        RAW;

    }

    public static enum ImageMode {
        COLOR,
        GRAY,
        RAW;

    }

    public static class PropertyEditor
    extends PropertyEditorSupport {
        @Override
        public String getAsText() {
            Class c = (Class)this.getValue();
            return c == null ? "null" : c.getSimpleName().split("FrameGrabber")[0];
        }

        @Override
        public void setAsText(String s) {
            if (s == null) {
                this.setValue(null);
            }
            try {
                this.setValue(FrameGrabber.get(s));
            }
            catch (Exception ex) {
                throw new IllegalArgumentException(ex);
            }
        }

        @Override
        public String[] getTags() {
            return list.toArray(new String[list.size()]);
        }
    }
}

