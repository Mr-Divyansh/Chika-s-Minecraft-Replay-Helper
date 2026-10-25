/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVCodecParameters;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avcodec.AVPacketSideData;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVIOContext;
import org.bytedeco.ffmpeg.avformat.AVInputFormat;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.avformat.Read_packet_Pointer_BytePointer_int;
import org.bytedeco.ffmpeg.avformat.Seek_Pointer_long_int;
import org.bytedeco.ffmpeg.avutil.AVChannelLayout;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.avutil.AVDictionaryEntry;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avdevice;
import org.bytedeco.ffmpeg.global.avformat;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.ffmpeg.global.swresample;
import org.bytedeco.ffmpeg.global.swscale;
import org.bytedeco.ffmpeg.swresample.SwrContext;
import org.bytedeco.ffmpeg.swscale.SwsContext;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.PointerScope;
import org.bytedeco.javacv.FFmpegLogCallback;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

public class FFmpegFrameGrabber
extends FrameGrabber {
    private static Exception loadingException = null;
    static Map<Pointer, InputStream> inputStreams;
    static ReadCallback readCallback;
    static SeekCallback seekCallback;
    private InputStream inputStream;
    private boolean closeInputStream;
    private int maximumSize;
    private AVIOContext avio;
    private String filename;
    private AVFormatContext oc;
    private AVStream video_st;
    private AVStream audio_st;
    private AVCodecContext video_c;
    private AVCodecContext audio_c;
    private AVFrame picture;
    private AVFrame picture_rgb;
    private BytePointer[] image_ptr;
    private Buffer[] image_buf;
    private AVFrame samples_frame;
    private BytePointer[] samples_ptr;
    private Buffer[] samples_buf;
    private BytePointer[] samples_ptr_out;
    private Buffer[] samples_buf_out;
    private PointerPointer plane_ptr;
    private PointerPointer plane_ptr2;
    private AVPacket pkt;
    private SwsContext img_convert_ctx;
    private SwrContext samples_convert_ctx;
    private int samples_channels;
    private int samples_format;
    private int samples_rate;
    private boolean frameGrabbed;
    private Frame frame;
    private int[] streams;
    private AVChannelLayout default_layout;
    private volatile boolean started = false;

    public static String[] getDeviceDescriptions() throws Exception {
        FFmpegFrameGrabber.tryLoad();
        throw new UnsupportedOperationException("Device enumeration not support by FFmpeg.");
    }

    public static FFmpegFrameGrabber createDefault(File deviceFile) throws Exception {
        return new FFmpegFrameGrabber(deviceFile);
    }

    public static FFmpegFrameGrabber createDefault(String devicePath) throws Exception {
        return new FFmpegFrameGrabber(devicePath);
    }

    public static FFmpegFrameGrabber createDefault(int deviceNumber) throws Exception {
        throw new Exception(FFmpegFrameGrabber.class + " does not support device numbers.");
    }

    public static void tryLoad() throws Exception {
        if (loadingException != null) {
            throw loadingException;
        }
        try {
            Loader.load(avutil.class);
            Loader.load(swresample.class);
            Loader.load(avcodec.class);
            Loader.load(avformat.class);
            Loader.load(swscale.class);
            avcodec.av_jni_set_java_vm(Loader.getJavaVM(), null);
            avformat.avformat_network_init();
            Loader.load(avdevice.class);
            avdevice.avdevice_register_all();
        }
        catch (Throwable t) {
            if (t instanceof Exception) {
                loadingException = (Exception)t;
                throw loadingException;
            }
            loadingException = new Exception("Failed to load " + FFmpegFrameGrabber.class, t);
            throw loadingException;
        }
    }

    public FFmpegFrameGrabber(URL url) {
        this(url.toString());
    }

    public FFmpegFrameGrabber(File file) {
        this(file.getAbsolutePath());
    }

    public FFmpegFrameGrabber(String filename) {
        this.filename = filename;
        this.pixelFormat = -1;
        this.sampleFormat = -1;
    }

    public FFmpegFrameGrabber(InputStream inputStream) {
        this(inputStream, 0x7FFFFFF7);
    }

    public FFmpegFrameGrabber(InputStream inputStream, int maximumSize) {
        this.inputStream = inputStream;
        this.closeInputStream = true;
        this.pixelFormat = -1;
        this.sampleFormat = -1;
        this.maximumSize = maximumSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void release() throws Exception {
        Class<avcodec> clazz = avcodec.class;
        synchronized (avcodec.class) {
            this.releaseUnsafe();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    public synchronized void releaseUnsafe() throws Exception {
        int i;
        this.started = false;
        if (this.plane_ptr != null && this.plane_ptr2 != null) {
            this.plane_ptr.releaseReference();
            this.plane_ptr2.releaseReference();
            this.plane_ptr2 = null;
            this.plane_ptr = null;
        }
        if (this.pkt != null) {
            if (this.pkt.stream_index() != -1) {
                avcodec.av_packet_unref(this.pkt);
            }
            this.pkt.releaseReference();
            this.pkt = null;
        }
        if (this.default_layout != null) {
            this.default_layout.releaseReference();
            this.default_layout = null;
        }
        if (this.image_ptr != null) {
            for (i = 0; i < this.image_ptr.length; ++i) {
                if (this.imageMode == FrameGrabber.ImageMode.RAW) continue;
                avutil.av_free(this.image_ptr[i]);
            }
            this.image_ptr = null;
        }
        if (this.picture_rgb != null) {
            avutil.av_frame_free(this.picture_rgb);
            this.picture_rgb = null;
        }
        if (this.picture != null) {
            avutil.av_frame_free(this.picture);
            this.picture = null;
        }
        if (this.video_c != null) {
            avcodec.avcodec_free_context(this.video_c);
            this.video_c = null;
        }
        if (this.samples_frame != null) {
            avutil.av_frame_free(this.samples_frame);
            this.samples_frame = null;
        }
        if (this.audio_c != null) {
            avcodec.avcodec_free_context(this.audio_c);
            this.audio_c = null;
        }
        if (this.inputStream == null && this.oc != null && !this.oc.isNull()) {
            avformat.avformat_close_input(this.oc);
            this.oc = null;
        }
        if (this.img_convert_ctx != null) {
            swscale.sws_freeContext(this.img_convert_ctx);
            this.img_convert_ctx = null;
        }
        if (this.samples_ptr_out != null) {
            for (i = 0; i < this.samples_ptr_out.length; ++i) {
                avutil.av_free(this.samples_ptr_out[i].position(0L));
            }
            this.samples_ptr_out = null;
            this.samples_buf_out = null;
        }
        if (this.samples_convert_ctx != null) {
            swresample.swr_free(this.samples_convert_ctx);
            this.samples_convert_ctx.releaseReference();
            this.samples_convert_ctx = null;
        }
        this.frameGrabbed = false;
        this.frame = null;
        this.timestamp = 0L;
        this.frameNumber = 0;
        if (this.inputStream != null) {
            try {
                if (this.oc == null) {
                    if (this.closeInputStream) {
                        this.inputStream.close();
                    }
                } else if (this.maximumSize > 0) {
                    try {
                        this.inputStream.reset();
                    }
                    catch (IOException ex) {
                        System.err.println("Error on InputStream.reset(): " + ex);
                    }
                }
            }
            catch (IOException ex) {
                throw new Exception("Error on InputStream.close(): ", ex);
            }
            finally {
                inputStreams.remove(this.oc);
                if (this.avio != null) {
                    if (this.avio.buffer() != null) {
                        avutil.av_free(this.avio.buffer());
                        this.avio.buffer(null);
                    }
                    avutil.av_free(this.avio);
                    this.avio = null;
                }
                if (this.oc != null) {
                    avformat.avformat_close_input(this.oc);
                    this.oc = null;
                }
            }
        }
        Pointer.trimMemory();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.release();
    }

    public boolean isCloseInputStream() {
        return this.closeInputStream;
    }

    public void setCloseInputStream(boolean closeInputStream) {
        this.closeInputStream = closeInputStream;
    }

    public boolean hasVideo() {
        return this.video_st != null;
    }

    public boolean hasAudio() {
        return this.audio_st != null;
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
        if (this.oc == null) {
            return super.getFormat();
        }
        return this.oc.iformat().name().getString();
    }

    @Override
    public int getImageWidth() {
        return this.imageWidth > 0 || this.video_c == null ? super.getImageWidth() : this.video_c.width();
    }

    @Override
    public int getImageHeight() {
        return this.imageHeight > 0 || this.video_c == null ? super.getImageHeight() : this.video_c.height();
    }

    @Override
    public int getAudioChannels() {
        return this.audioChannels > 0 || this.audio_c == null ? super.getAudioChannels() : this.audio_c.ch_layout().nb_channels();
    }

    @Override
    public int getPixelFormat() {
        if (this.imageMode == FrameGrabber.ImageMode.COLOR || this.imageMode == FrameGrabber.ImageMode.GRAY) {
            if (this.pixelFormat == -1) {
                return this.imageMode == FrameGrabber.ImageMode.COLOR ? 3 : 8;
            }
            return this.pixelFormat;
        }
        if (this.video_c != null) {
            return this.video_c.pix_fmt();
        }
        return super.getPixelFormat();
    }

    @Override
    public int getVideoCodec() {
        return this.video_c == null ? super.getVideoCodec() : this.video_c.codec_id();
    }

    @Override
    public String getVideoCodecName() {
        return this.video_c == null ? super.getVideoCodecName() : this.video_c.codec().name().getString();
    }

    @Override
    public int getVideoBitrate() {
        return this.video_c == null ? super.getVideoBitrate() : (int)this.video_c.bit_rate();
    }

    @Override
    public double getAspectRatio() {
        if (this.video_st == null) {
            return super.getAspectRatio();
        }
        AVRational r = avformat.av_guess_sample_aspect_ratio(this.oc, this.video_st, this.picture);
        double a = (double)r.num() / (double)r.den();
        return a == 0.0 ? 1.0 : a;
    }

    @Override
    public double getFrameRate() {
        return this.getVideoFrameRate();
    }

    public double getAudioFrameRate() {
        if (this.audio_st == null) {
            return 0.0;
        }
        if (this.samples_frame == null || this.samples_frame.nb_samples() == 0) {
            try {
                this.grabFrame(true, false, false, false, false);
                this.frameGrabbed = true;
            }
            catch (Exception e) {
                return 0.0;
            }
        }
        if (this.samples_frame != null && this.samples_frame.nb_samples() != 0) {
            return (double)this.getSampleRate() / (double)this.samples_frame.nb_samples();
        }
        return 0.0;
    }

    public double getVideoFrameRate() {
        if (this.video_st == null) {
            return super.getFrameRate();
        }
        AVRational r = this.video_st.avg_frame_rate();
        if (r.num() == 0 && r.den() == 0) {
            r = this.video_st.r_frame_rate();
        }
        return (double)r.num() / (double)r.den();
    }

    @Override
    public int getAudioCodec() {
        return this.audio_c == null ? super.getAudioCodec() : this.audio_c.codec_id();
    }

    @Override
    public String getAudioCodecName() {
        return this.audio_c == null ? super.getAudioCodecName() : this.audio_c.codec().name().getString();
    }

    @Override
    public int getAudioBitrate() {
        return this.audio_c == null ? super.getAudioBitrate() : (int)this.audio_c.bit_rate();
    }

    @Override
    public int getSampleFormat() {
        if (this.sampleMode == FrameGrabber.SampleMode.SHORT || this.sampleMode == FrameGrabber.SampleMode.FLOAT) {
            if (this.sampleFormat == -1) {
                return this.sampleMode == FrameGrabber.SampleMode.SHORT ? 1 : 3;
            }
            return this.sampleFormat;
        }
        if (this.audio_c != null) {
            return this.audio_c.sample_fmt();
        }
        return super.getSampleFormat();
    }

    @Override
    public int getSampleRate() {
        return this.sampleRate > 0 || this.audio_c == null ? super.getSampleRate() : this.audio_c.sample_rate();
    }

    @Override
    public Map<String, String> getMetadata() {
        if (this.oc == null) {
            return super.getMetadata();
        }
        AVDictionaryEntry entry = null;
        HashMap<String, String> metadata = new HashMap<String, String>();
        while ((entry = avutil.av_dict_get(this.oc.metadata(), "", entry, 2)) != null) {
            metadata.put(entry.key().getString(this.charset), entry.value().getString(this.charset));
        }
        return metadata;
    }

    @Override
    public Map<String, String> getVideoMetadata() {
        if (this.video_st == null) {
            return super.getVideoMetadata();
        }
        AVDictionaryEntry entry = null;
        HashMap<String, String> metadata = new HashMap<String, String>();
        while ((entry = avutil.av_dict_get(this.video_st.metadata(), "", entry, 2)) != null) {
            metadata.put(entry.key().getString(this.charset), entry.value().getString(this.charset));
        }
        return metadata;
    }

    @Override
    public Map<String, String> getAudioMetadata() {
        if (this.audio_st == null) {
            return super.getAudioMetadata();
        }
        AVDictionaryEntry entry = null;
        HashMap<String, String> metadata = new HashMap<String, String>();
        while ((entry = avutil.av_dict_get(this.audio_st.metadata(), "", entry, 2)) != null) {
            metadata.put(entry.key().getString(this.charset), entry.value().getString(this.charset));
        }
        return metadata;
    }

    @Override
    public String getMetadata(String key) {
        if (this.oc == null) {
            return super.getMetadata(key);
        }
        AVDictionaryEntry entry = avutil.av_dict_get(this.oc.metadata(), key, null, 0);
        return entry == null || entry.value() == null ? null : entry.value().getString(this.charset);
    }

    @Override
    public String getVideoMetadata(String key) {
        if (this.video_st == null) {
            return super.getVideoMetadata(key);
        }
        AVDictionaryEntry entry = avutil.av_dict_get(this.video_st.metadata(), key, null, 0);
        return entry == null || entry.value() == null ? null : entry.value().getString(this.charset);
    }

    @Override
    public String getAudioMetadata(String key) {
        if (this.audio_st == null) {
            return super.getAudioMetadata(key);
        }
        AVDictionaryEntry entry = avutil.av_dict_get(this.audio_st.metadata(), key, null, 0);
        return entry == null || entry.value() == null ? null : entry.value().getString(this.charset);
    }

    @Override
    public Map<String, Buffer> getVideoSideData() {
        if (this.video_st == null) {
            return super.getVideoSideData();
        }
        this.videoSideData = new HashMap();
        for (int i = 0; i < this.video_st.codecpar().nb_coded_side_data(); ++i) {
            AVPacketSideData sd = this.video_st.codecpar().coded_side_data().position(i);
            String key = avcodec.av_packet_side_data_name(sd.type()).getString();
            ByteBuffer value = sd.data().capacity(sd.size()).asBuffer();
            this.videoSideData.put(key, value);
        }
        return this.videoSideData;
    }

    @Override
    public Buffer getVideoSideData(String key) {
        return this.getVideoSideData().get(key);
    }

    public double getDisplayRotation() {
        ByteBuffer b = (ByteBuffer)this.getVideoSideData("Display Matrix");
        return b != null ? avutil.av_display_rotation_get(new IntPointer(new BytePointer(b))) : 0.0;
    }

    @Override
    public Map<String, Buffer> getAudioSideData() {
        if (this.audio_st == null) {
            return super.getAudioSideData();
        }
        this.audioSideData = new HashMap();
        for (int i = 0; i < this.audio_st.codecpar().nb_coded_side_data(); ++i) {
            AVPacketSideData sd = this.audio_st.codecpar().coded_side_data().position(i);
            String key = avcodec.av_packet_side_data_name(sd.type()).getString();
            ByteBuffer value = sd.data().capacity(sd.size()).asBuffer();
            this.audioSideData.put(key, value);
        }
        return this.audioSideData;
    }

    @Override
    public Buffer getAudioSideData(String key) {
        return this.getAudioSideData().get(key);
    }

    @Override
    public void setFrameNumber(int frameNumber) throws Exception {
        if (this.hasVideo()) {
            this.setTimestamp(Math.round((double)(1000000L * (long)frameNumber + 500000L) / this.getFrameRate()));
        } else {
            this.frameNumber = frameNumber;
        }
    }

    public void setVideoFrameNumber(int frameNumber) throws Exception {
        if (this.hasVideo()) {
            this.setVideoTimestamp(Math.round((double)(1000000L * (long)frameNumber + 500000L) / this.getFrameRate()));
        } else {
            this.frameNumber = frameNumber;
        }
    }

    public void setAudioFrameNumber(int frameNumber) throws Exception {
        if (this.hasAudio()) {
            this.setAudioTimestamp(Math.round((double)(1000000L * (long)frameNumber + 500000L) / this.getAudioFrameRate()));
        }
    }

    @Override
    public void setTimestamp(long timestamp) throws Exception {
        this.setTimestamp(timestamp, false);
    }

    public void setTimestamp(long timestamp, boolean checkFrame) throws Exception {
        this.setTimestamp(timestamp, checkFrame ? EnumSet.of(Frame.Type.VIDEO, Frame.Type.AUDIO) : null);
    }

    public void setVideoTimestamp(long timestamp) throws Exception {
        this.setTimestamp(timestamp, EnumSet.of(Frame.Type.VIDEO));
    }

    public void setAudioTimestamp(long timestamp) throws Exception {
        this.setTimestamp(timestamp, EnumSet.of(Frame.Type.AUDIO));
    }

    private synchronized void setTimestamp(long timestamp, EnumSet<Frame.Type> frameTypesToSeek) throws Exception {
        if (this.oc == null) {
            this.timestamp = timestamp;
        } else {
            long ts0;
            timestamp = timestamp * 1000000L / 1000000L;
            long l = ts0 = this.oc.start_time() != avutil.AV_NOPTS_VALUE ? this.oc.start_time() : 0L;
            if (frameTypesToSeek != null && (frameTypesToSeek.contains((Object)Frame.Type.VIDEO) || frameTypesToSeek.contains((Object)Frame.Type.AUDIO)) && (this.hasVideo() || this.hasAudio())) {
                if (frameTypesToSeek.contains((Object)Frame.Type.VIDEO) && !this.hasVideo() || frameTypesToSeek.contains((Object)Frame.Type.AUDIO) && !this.hasAudio()) {
                    frameTypesToSeek = EnumSet.of(Frame.Type.VIDEO, Frame.Type.AUDIO);
                }
                if (frameTypesToSeek.size() == 1) {
                    AVRational time_base;
                    if (frameTypesToSeek.contains((Object)Frame.Type.VIDEO)) {
                        if (this.video_st != null && this.video_st.start_time() != avutil.AV_NOPTS_VALUE) {
                            time_base = this.video_st.time_base();
                            ts0 = 1000000L * this.video_st.start_time() * (long)time_base.num() / (long)time_base.den();
                        }
                    } else if (frameTypesToSeek.contains((Object)Frame.Type.AUDIO) && this.audio_st != null && this.audio_st.start_time() != avutil.AV_NOPTS_VALUE) {
                        time_base = this.audio_st.time_base();
                        ts0 = 1000000L * this.audio_st.start_time() * (long)time_base.num() / (long)time_base.den();
                    }
                }
                long early_ts = timestamp;
                timestamp += ts0;
                early_ts += ts0;
                long initialSeekPosition = Long.MIN_VALUE;
                long maxSeekSteps = 0L;
                long count = 0L;
                Frame seekFrame = null;
                do {
                    int ret;
                    if ((ret = avformat.avformat_seek_file(this.oc, -1, 0L, early_ts, early_ts, 1)) < 0) {
                        throw new Exception("avformat_seek_file() error " + ret + ": Could not seek file to timestamp " + timestamp + ".");
                    }
                    if (this.video_c != null) {
                        avcodec.avcodec_flush_buffers(this.video_c);
                    }
                    if (this.audio_c != null) {
                        avcodec.avcodec_flush_buffers(this.audio_c);
                    }
                    if (this.pkt.stream_index() != -1) {
                        avcodec.av_packet_unref(this.pkt);
                        this.pkt.stream_index(-1);
                    }
                    if ((seekFrame = this.grabFrame(frameTypesToSeek.contains((Object)Frame.Type.AUDIO), frameTypesToSeek.contains((Object)Frame.Type.VIDEO), false, false, false)) == null) {
                        return;
                    }
                    initialSeekPosition = seekFrame.timestamp;
                    if (early_ts == 0L) break;
                    if ((early_ts -= 500000L) >= 0L) continue;
                    early_ts = 0L;
                } while (initialSeekPosition > timestamp);
                double frameDuration = 0.0;
                if (seekFrame.image != null && this.getFrameRate() > 0.0) {
                    frameDuration = 1000000.0 / this.getFrameRate();
                } else if (seekFrame.samples != null && this.samples_frame != null && this.getSampleRate() > 0) {
                    frameDuration = (double)(1000000 * this.samples_frame.nb_samples()) / (double)this.getSampleRate();
                }
                if (frameDuration > 0.0) {
                    maxSeekSteps = 0L;
                    if ((double)(timestamp - initialSeekPosition + 1L) > frameDuration) {
                        maxSeekSteps = (long)((double)(10L * (timestamp - initialSeekPosition)) / frameDuration);
                    }
                } else if (initialSeekPosition < timestamp) {
                    maxSeekSteps = 1000L;
                }
                double delta = 0.0;
                count = 0L;
                while (count < maxSeekSteps) {
                    seekFrame = this.grabFrame(frameTypesToSeek.contains((Object)Frame.Type.AUDIO), frameTypesToSeek.contains((Object)Frame.Type.VIDEO), false, false, false);
                    if (seekFrame == null) {
                        return;
                    }
                    ++count;
                    double ts = seekFrame.timestamp;
                    frameDuration = 0.0;
                    if (seekFrame.image != null && this.getFrameRate() > 0.0) {
                        frameDuration = 1000000.0 / this.getFrameRate();
                    } else if (seekFrame.samples != null && this.samples_frame != null && this.getSampleRate() > 0) {
                        frameDuration = (double)(1000000 * this.samples_frame.nb_samples()) / (double)this.getSampleRate();
                    }
                    delta = 0.0;
                    if (frameDuration > 0.0 && Math.abs(delta = (ts - (double)ts0) / frameDuration - (double)Math.round((ts - (double)ts0) / frameDuration)) > 0.2) {
                        delta = 0.0;
                    }
                    if (!((ts -= delta * frameDuration) + frameDuration > (double)timestamp)) continue;
                    break;
                }
            } else {
                int ret = avformat.avformat_seek_file(this.oc, -1, Long.MIN_VALUE, timestamp += ts0, Long.MAX_VALUE, 1);
                if (ret < 0) {
                    throw new Exception("avformat_seek_file() error " + ret + ": Could not seek file to timestamp " + timestamp + ".");
                }
                if (this.video_c != null) {
                    avcodec.avcodec_flush_buffers(this.video_c);
                }
                if (this.audio_c != null) {
                    avcodec.avcodec_flush_buffers(this.audio_c);
                }
                if (this.pkt.stream_index() != -1) {
                    avcodec.av_packet_unref(this.pkt);
                    this.pkt.stream_index(-1);
                }
                int count = 0;
                while (this.timestamp > timestamp + 1L && this.grabFrame(true, true, false, false) != null && count++ < 1000) {
                }
                count = 0;
                while (this.timestamp < timestamp - 1L && this.grabFrame(true, true, false, false) != null && count++ < 1000) {
                }
            }
            this.frameGrabbed = true;
        }
    }

    @Override
    public int getLengthInFrames() {
        return this.getLengthInVideoFrames();
    }

    @Override
    public long getLengthInTime() {
        return this.oc.duration() * 1000000L / 1000000L;
    }

    public int getLengthInVideoFrames() {
        return (int)Math.round((double)this.getLengthInTime() * this.getFrameRate() / 1000000.0);
    }

    public int getLengthInAudioFrames() {
        double afr = this.getAudioFrameRate();
        if (afr > 0.0) {
            return (int)((double)this.getLengthInTime() * afr / 1000000.0);
        }
        return 0;
    }

    public AVFormatContext getFormatContext() {
        return this.oc;
    }

    @Override
    public void start() throws Exception {
        this.start(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void start(boolean findStreamInfo) throws Exception {
        Class<avcodec> clazz = avcodec.class;
        synchronized (avcodec.class) {
            this.startUnsafe(findStreamInfo);
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return;
        }
    }

    public void startUnsafe() throws Exception {
        this.startUnsafe(true);
    }

    /*
     * WARNING - void declaration
     */
    public synchronized void startUnsafe(boolean findStreamInfo) throws Exception {
        try (PointerScope scope = new PointerScope();){
            void var8_10;
            int ret;
            if (this.oc != null && !this.oc.isNull()) {
                throw new Exception("start() has already been called: Call stop() before calling start() again.");
            }
            this.img_convert_ctx = null;
            this.oc = new AVFormatContext(null);
            this.video_c = null;
            this.audio_c = null;
            this.plane_ptr = (PointerPointer)new PointerPointer(8L).retainReference();
            this.plane_ptr2 = (PointerPointer)new PointerPointer(8L).retainReference();
            this.pkt = (AVPacket)new AVPacket().retainReference();
            this.frameGrabbed = false;
            this.frame = new Frame();
            this.timestamp = 0L;
            this.frameNumber = 0;
            this.default_layout = (AVChannelLayout)new AVChannelLayout().retainReference();
            this.pkt.stream_index(-1);
            AVInputFormat f = null;
            if (this.format != null && this.format.length() > 0 && (f = avformat.av_find_input_format(this.format)) == null) {
                throw new Exception("av_find_input_format() error: Could not find input format \"" + this.format + "\".");
            }
            AVDictionary options = new AVDictionary(null);
            if (this.frameRate > 0.0) {
                AVRational r = avutil.av_d2q(this.frameRate, 1001000);
                avutil.av_dict_set(options, "framerate", r.num() + "/" + r.den(), 0);
            }
            if (this.pixelFormat >= 0) {
                avutil.av_dict_set(options, "pixel_format", avutil.av_get_pix_fmt_name(this.pixelFormat).getString(), 0);
            } else if (this.imageMode != FrameGrabber.ImageMode.RAW) {
                avutil.av_dict_set(options, "pixel_format", this.imageMode == FrameGrabber.ImageMode.COLOR ? "bgr24" : "gray8", 0);
            }
            if (this.imageWidth > 0 && this.imageHeight > 0) {
                avutil.av_dict_set(options, "video_size", this.imageWidth + "x" + this.imageHeight, 0);
            }
            if (this.sampleRate > 0) {
                avutil.av_dict_set(options, "sample_rate", "" + this.sampleRate, 0);
            }
            if (this.audioChannels > 0) {
                avutil.av_dict_set(options, "channels", "" + this.audioChannels, 0);
            }
            for (Map.Entry entry : this.options.entrySet()) {
                avutil.av_dict_set(options, (String)entry.getKey(), (String)entry.getValue(), 0);
            }
            if (this.inputStream != null) {
                if (!this.inputStream.markSupported()) {
                    this.inputStream = new BufferedInputStream(this.inputStream);
                }
                this.inputStream.mark(this.maximumSize);
                this.oc = avformat.avformat_alloc_context();
                this.avio = avformat.avio_alloc_context(new BytePointer(avutil.av_malloc(4096L)), 4096, 0, (Pointer)this.oc, readCallback, null, (Seek_Pointer_long_int)(this.maximumSize > 0 ? seekCallback : null));
                this.oc.pb(this.avio);
                this.filename = this.inputStream.toString();
                inputStreams.put(this.oc, this.inputStream);
            }
            if ((ret = avformat.avformat_open_input(this.oc, this.filename, f, options)) < 0) {
                avutil.av_dict_set(options, "pixel_format", null, 0);
                ret = avformat.avformat_open_input(this.oc, this.filename, f, options);
                if (ret < 0) {
                    throw new Exception("avformat_open_input() error " + ret + ": Could not open input \"" + this.filename + "\". (Has setFormat() been called?)");
                }
            }
            FFmpegLogCallback.logRejectedOptions(options, "avformat_open_input");
            avutil.av_dict_free(options);
            this.oc.max_delay(this.maxDelay);
            if (findStreamInfo && (ret = avformat.avformat_find_stream_info(this.oc, (PointerPointer)null)) < 0) {
                throw new Exception("avformat_find_stream_info() error " + ret + ": Could not find stream information.");
            }
            if (avutil.av_log_get_level() >= 32) {
                avformat.av_dump_format(this.oc, 0, this.filename, 0);
            }
            int nb_streams = this.oc.nb_streams();
            boolean bl = false;
            while (var8_10 < nb_streams) {
                AVStream st = this.oc.streams((int)var8_10);
                AVCodecParameters par = st.codecpar();
                if (this.videoStream < 0 && par.codec_type() == 0 && st.disposition() == this.videoDisposition) {
                    this.videoStream = var8_10;
                } else if (this.audioStream < 0 && par.codec_type() == 1 && st.disposition() == this.audioDisposition) {
                    this.audioStream = var8_10;
                }
                ++var8_10;
            }
            this.audio_st = null;
            this.video_st = null;
            Object var8_11 = null;
            AVCodecParameters audio_par = null;
            this.streams = new int[nb_streams];
            for (int i = 0; i < nb_streams; ++i) {
                AVStream st = this.oc.streams(i);
                AVCodecParameters par = st.codecpar();
                this.streams[i] = par.codec_type();
                if (this.video_st == null && par.codec_type() == 0 && par.codec_id() != 0 && (this.videoStream < 0 || this.videoStream == i)) {
                    this.video_st = st;
                    AVCodecParameters aVCodecParameters = par;
                    this.videoStream = i;
                    continue;
                }
                if (this.audio_st != null || par.codec_type() != 1 || par.codec_id() == 0 || this.audioStream >= 0 && this.audioStream != i) continue;
                this.audio_st = st;
                audio_par = par;
                this.audioStream = i;
            }
            if (this.video_st == null && this.audio_st == null) {
                throw new Exception("Did not find a video or audio stream inside \"" + this.filename + "\" for videoStream == " + this.videoStream + " and audioStream == " + this.audioStream + ".");
            }
            if (this.video_st != null) {
                void var8_12;
                AVCodec codec = avcodec.avcodec_find_decoder_by_name(this.videoCodecName);
                if (codec == null) {
                    codec = avcodec.avcodec_find_decoder(var8_12.codec_id());
                }
                if (codec == null) {
                    throw new Exception("avcodec_find_decoder() error: Unsupported video format or codec not found: " + var8_12.codec_id() + ".");
                }
                this.video_c = avcodec.avcodec_alloc_context3(codec);
                if (this.video_c == null) {
                    throw new Exception("avcodec_alloc_context3() error: Could not allocate video decoding context.");
                }
                ret = avcodec.avcodec_parameters_to_context(this.video_c, this.video_st.codecpar());
                if (ret < 0) {
                    this.releaseUnsafe();
                    throw new Exception("avcodec_parameters_to_context() error " + ret + ": Could not copy the video stream parameters.");
                }
                options = new AVDictionary(null);
                for (Map.Entry e : this.videoOptions.entrySet()) {
                    avutil.av_dict_set(options, (String)e.getKey(), (String)e.getValue(), 0);
                }
                this.video_c.thread_count(0);
                ret = avcodec.avcodec_open2(this.video_c, codec, options);
                if (ret < 0) {
                    throw new Exception("avcodec_open2() error " + ret + ": Could not open video codec.");
                }
                FFmpegLogCallback.logRejectedOptions(options, "avcodec_open2");
                avutil.av_dict_free(options);
                if (this.video_c.time_base().num() > 1000 && this.video_c.time_base().den() == 1) {
                    this.video_c.time_base().den(1000);
                }
                if ((this.picture = avutil.av_frame_alloc()) == null) {
                    throw new Exception("av_frame_alloc() error: Could not allocate raw picture frame.");
                }
                this.picture_rgb = avutil.av_frame_alloc();
                if (this.picture_rgb == null) {
                    throw new Exception("av_frame_alloc() error: Could not allocate RGB picture frame.");
                }
                this.initPictureRGB();
            }
            if (this.audio_st != null) {
                AVCodec codec = avcodec.avcodec_find_decoder_by_name(this.audioCodecName);
                if (codec == null) {
                    codec = avcodec.avcodec_find_decoder(audio_par.codec_id());
                }
                if (codec == null) {
                    throw new Exception("avcodec_find_decoder() error: Unsupported audio format or codec not found: " + audio_par.codec_id() + ".");
                }
                this.audio_c = avcodec.avcodec_alloc_context3(codec);
                if (this.audio_c == null) {
                    throw new Exception("avcodec_alloc_context3() error: Could not allocate audio decoding context.");
                }
                ret = avcodec.avcodec_parameters_to_context(this.audio_c, this.audio_st.codecpar());
                if (ret < 0) {
                    this.releaseUnsafe();
                    throw new Exception("avcodec_parameters_to_context() error " + ret + ": Could not copy the audio stream parameters.");
                }
                options = new AVDictionary(null);
                for (Map.Entry e : this.audioOptions.entrySet()) {
                    avutil.av_dict_set(options, (String)e.getKey(), (String)e.getValue(), 0);
                }
                this.audio_c.thread_count(0);
                ret = avcodec.avcodec_open2(this.audio_c, codec, options);
                if (ret < 0) {
                    throw new Exception("avcodec_open2() error " + ret + ": Could not open audio codec.");
                }
                FFmpegLogCallback.logRejectedOptions(options, "avcodec_open2");
                avutil.av_dict_free(options);
                this.samples_frame = avutil.av_frame_alloc();
                if (this.samples_frame == null) {
                    throw new Exception("av_frame_alloc() error: Could not allocate audio frame.");
                }
                this.samples_ptr = new BytePointer[]{null};
                this.samples_buf = new Buffer[]{null};
            }
            this.started = true;
        }
    }

    private void initPictureRGB() {
        int width = this.imageWidth > 0 ? this.imageWidth : this.video_c.width();
        int height = this.imageHeight > 0 ? this.imageHeight : this.video_c.height();
        switch (this.imageMode) {
            case COLOR: 
            case GRAY: {
                if (this.image_ptr != null) {
                    this.image_buf = null;
                    BytePointer[] temp = this.image_ptr;
                    this.image_ptr = null;
                    avutil.av_free(temp[0]);
                }
                int fmt = this.getPixelFormat();
                int align = 64;
                int stride = width;
                for (int i = 1; i <= align; i += i) {
                    stride = width + (i - 1) & ~(i - 1);
                    avutil.av_image_fill_linesizes(this.picture_rgb.linesize(), fmt, stride);
                    if ((this.picture_rgb.linesize(0) & align - 1) == 0) break;
                }
                int size = avutil.av_image_get_buffer_size(fmt, stride, height, 1);
                this.image_ptr = new BytePointer[]{new BytePointer(avutil.av_malloc(size)).capacity(size)};
                this.image_buf = new Buffer[]{this.image_ptr[0].asBuffer()};
                avutil.av_image_fill_arrays(new PointerPointer(this.picture_rgb), this.picture_rgb.linesize(), this.image_ptr[0], fmt, stride, height, 1);
                this.picture_rgb.format(fmt);
                this.picture_rgb.width(width);
                this.picture_rgb.height(height);
                break;
            }
            case RAW: {
                this.image_ptr = new BytePointer[]{null};
                this.image_buf = new Buffer[]{null};
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
    }

    @Override
    public void stop() throws Exception {
        this.release();
    }

    @Override
    public synchronized void trigger() throws Exception {
        if (this.oc == null || this.oc.isNull()) {
            throw new Exception("Could not trigger: No AVFormatContext. (Has start() been called?)");
        }
        if (this.pkt.stream_index() != -1) {
            avcodec.av_packet_unref(this.pkt);
            this.pkt.stream_index(-1);
        }
        for (int i = 0; i < this.numBuffers + 1; ++i) {
            if (avformat.av_read_frame(this.oc, this.pkt) < 0) {
                return;
            }
            avcodec.av_packet_unref(this.pkt);
        }
    }

    private void processImage() throws Exception {
        this.frame.imageWidth = this.imageWidth > 0 ? this.imageWidth : this.video_c.width();
        this.frame.imageHeight = this.imageHeight > 0 ? this.imageHeight : this.video_c.height();
        this.frame.imageDepth = 8;
        switch (this.imageMode) {
            case COLOR: 
            case GRAY: {
                if (this.deinterlace) {
                    throw new Exception("Cannot deinterlace: Functionality moved to FFmpegFrameFilter.");
                }
                if (this.frame.imageWidth != this.picture_rgb.width() || this.frame.imageHeight != this.picture_rgb.height()) {
                    this.initPictureRGB();
                }
                avutil.av_frame_copy_props(this.picture_rgb, this.picture);
                this.img_convert_ctx = swscale.sws_getCachedContext(this.img_convert_ctx, this.video_c.width(), this.video_c.height(), this.video_c.pix_fmt(), this.frame.imageWidth, this.frame.imageHeight, this.getPixelFormat(), this.imageScalingFlags != 0 ? this.imageScalingFlags : 2, null, null, (DoublePointer)null);
                if (this.img_convert_ctx == null) {
                    throw new Exception("sws_getCachedContext() error: Cannot initialize the conversion context.");
                }
                swscale.sws_scale(this.img_convert_ctx, new PointerPointer(this.picture), this.picture.linesize(), 0, this.video_c.height(), new PointerPointer(this.picture_rgb), this.picture_rgb.linesize());
                this.frame.imageStride = this.picture_rgb.linesize(0);
                this.frame.image = this.image_buf;
                this.frame.opaque = this.picture_rgb;
                break;
            }
            case RAW: {
                this.frame.imageStride = this.picture.linesize(0);
                BytePointer ptr = this.picture.data(0);
                if (ptr != null && !ptr.equals(this.image_ptr[0])) {
                    this.image_ptr[0] = ptr.capacity(this.frame.imageHeight * this.frame.imageStride);
                    this.image_buf[0] = ptr.asBuffer();
                }
                this.frame.image = this.image_buf;
                this.frame.opaque = this.picture;
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        this.frame.image[0].limit(this.frame.imageHeight * this.frame.imageStride);
        this.frame.imageChannels = this.frame.imageStride / this.frame.imageWidth;
    }

    private void processSamples() throws Exception {
        int sample_format = this.samples_frame.format();
        int planes = avutil.av_sample_fmt_is_planar(sample_format) != 0 ? this.samples_frame.ch_layout().nb_channels() : 1;
        int data_size = avutil.av_samples_get_buffer_size((IntPointer)null, this.audio_c.ch_layout().nb_channels(), this.samples_frame.nb_samples(), this.audio_c.sample_fmt(), 1) / planes;
        if (this.samples_buf == null || this.samples_buf.length != planes) {
            this.samples_ptr = new BytePointer[planes];
            this.samples_buf = new Buffer[planes];
        }
        this.frame.sampleRate = this.audio_c.sample_rate();
        this.frame.audioChannels = this.audio_c.ch_layout().nb_channels();
        this.frame.samples = this.samples_buf;
        this.frame.opaque = this.samples_frame;
        int sample_size = data_size / avutil.av_get_bytes_per_sample(sample_format);
        for (int i = 0; i < planes; ++i) {
            BytePointer p = this.samples_frame.data(i);
            if (!p.equals(this.samples_ptr[i]) || this.samples_ptr[i].capacity() < (long)data_size) {
                this.samples_ptr[i] = p.capacity(data_size);
                ByteBuffer b = p.asBuffer();
                switch (sample_format) {
                    case 0: 
                    case 5: {
                        this.samples_buf[i] = b;
                        break;
                    }
                    case 1: 
                    case 6: {
                        this.samples_buf[i] = b.asShortBuffer();
                        break;
                    }
                    case 2: 
                    case 7: {
                        this.samples_buf[i] = b.asIntBuffer();
                        break;
                    }
                    case 3: 
                    case 8: {
                        this.samples_buf[i] = b.asFloatBuffer();
                        break;
                    }
                    case 4: 
                    case 9: {
                        this.samples_buf[i] = b.asDoubleBuffer();
                        break;
                    }
                    default: {
                        assert (false);
                        break;
                    }
                }
            }
            this.samples_buf[i].position(0).limit(sample_size);
        }
        if (this.audio_c.ch_layout().nb_channels() != this.getAudioChannels() || this.audio_c.sample_fmt() != this.getSampleFormat() || this.audio_c.sample_rate() != this.getSampleRate()) {
            int i;
            int ret;
            if (this.samples_convert_ctx == null || this.samples_channels != this.getAudioChannels() || this.samples_format != this.getSampleFormat() || this.samples_rate != this.getSampleRate()) {
                if (this.samples_convert_ctx == null) {
                    this.samples_convert_ctx = (SwrContext)new SwrContext().retainReference();
                }
                avutil.av_channel_layout_default(this.default_layout, this.getAudioChannels());
                ret = swresample.swr_alloc_set_opts2(this.samples_convert_ctx, this.default_layout, this.getSampleFormat(), this.getSampleRate(), this.audio_c.ch_layout(), this.audio_c.sample_fmt(), this.audio_c.sample_rate(), 0, null);
                if (ret < 0) {
                    throw new Exception("swr_alloc_set_opts2() error " + ret + ": Cannot allocate the conversion context.");
                }
                ret = swresample.swr_init(this.samples_convert_ctx);
                if (ret < 0) {
                    throw new Exception("swr_init() error " + ret + ": Cannot initialize the conversion context.");
                }
                this.samples_channels = this.getAudioChannels();
                this.samples_format = this.getSampleFormat();
                this.samples_rate = this.getSampleRate();
            }
            int sample_size_in = this.samples_frame.nb_samples();
            int planes_out = avutil.av_sample_fmt_is_planar(this.samples_format) != 0 ? this.samples_frame.ch_layout().nb_channels() : 1;
            int sample_size_out = swresample.swr_get_out_samples(this.samples_convert_ctx, sample_size_in);
            int sample_bytes_out = avutil.av_get_bytes_per_sample(this.samples_format);
            int buffer_size_out = sample_size_out * sample_bytes_out * (planes_out > 1 ? 1 : this.samples_channels);
            if (this.samples_buf_out == null || this.samples_buf.length != planes_out || this.samples_ptr_out[0].capacity() < (long)buffer_size_out) {
                for (i = 0; this.samples_ptr_out != null && i < this.samples_ptr_out.length; ++i) {
                    avutil.av_free(this.samples_ptr_out[i].position(0L));
                }
                this.samples_ptr_out = new BytePointer[planes_out];
                this.samples_buf_out = new Buffer[planes_out];
                block16: for (i = 0; i < planes_out; ++i) {
                    this.samples_ptr_out[i] = new BytePointer(avutil.av_malloc(buffer_size_out)).capacity(buffer_size_out);
                    ByteBuffer b = this.samples_ptr_out[i].asBuffer();
                    switch (this.samples_format) {
                        case 0: 
                        case 5: {
                            this.samples_buf_out[i] = b;
                            continue block16;
                        }
                        case 1: 
                        case 6: {
                            this.samples_buf_out[i] = b.asShortBuffer();
                            continue block16;
                        }
                        case 2: 
                        case 7: {
                            this.samples_buf_out[i] = b.asIntBuffer();
                            continue block16;
                        }
                        case 3: 
                        case 8: {
                            this.samples_buf_out[i] = b.asFloatBuffer();
                            continue block16;
                        }
                        case 4: 
                        case 9: {
                            this.samples_buf_out[i] = b.asDoubleBuffer();
                            continue block16;
                        }
                        default: {
                            assert (false);
                            continue block16;
                        }
                    }
                }
            }
            this.frame.sampleRate = this.samples_rate;
            this.frame.audioChannels = this.samples_channels;
            this.frame.samples = this.samples_buf_out;
            ret = swresample.swr_convert(this.samples_convert_ctx, this.plane_ptr.put(this.samples_ptr_out), sample_size_out, this.plane_ptr2.put(this.samples_ptr), sample_size_in);
            if (ret < 0) {
                throw new Exception("swr_convert() error " + ret + ": Cannot convert audio samples.");
            }
            for (i = 0; i < planes_out; ++i) {
                this.samples_ptr_out[i].position(0L).limit(ret * (planes_out > 1 ? 1 : this.samples_channels));
                this.samples_buf_out[i].position(0).limit(ret * (planes_out > 1 ? 1 : this.samples_channels));
            }
        }
    }

    @Override
    public Frame grab() throws Exception {
        return this.grabFrame(true, true, true, false, true);
    }

    public Frame grabImage() throws Exception {
        return this.grabFrame(false, true, true, false, false);
    }

    public Frame grabSamples() throws Exception {
        return this.grabFrame(true, false, true, false, false);
    }

    public Frame grabKeyFrame() throws Exception {
        return this.grabFrame(false, true, true, true, false);
    }

    public Frame grabFrame(boolean doAudio, boolean doVideo, boolean doProcessing, boolean keyFrames) throws Exception {
        return this.grabFrame(doAudio, doVideo, doProcessing, keyFrames, true);
    }

    public synchronized Frame grabFrame(boolean doAudio, boolean doVideo, boolean doProcessing, boolean keyFrames, boolean doData) throws Exception {
        try (PointerScope scope = new PointerScope();){
            boolean readPacket;
            if (this.oc == null || this.oc.isNull()) {
                throw new Exception("Could not grab: No AVFormatContext. (Has start() been called?)");
            }
            if (!(doVideo && this.video_st != null || doAudio && this.audio_st != null || doData)) {
                Frame frame = null;
                return frame;
            }
            if (!this.started) {
                throw new Exception("start() was not called successfully!");
            }
            boolean videoFrameGrabbed = this.frameGrabbed && this.frame.image != null;
            boolean audioFrameGrabbed = this.frameGrabbed && this.frame.samples != null;
            boolean dataFrameGrabbed = this.frameGrabbed && this.frame.data != null;
            this.frameGrabbed = false;
            if (doVideo && videoFrameGrabbed) {
                if (doProcessing) {
                    this.processImage();
                }
                this.frame.keyFrame = (this.picture.flags() & 2) != 0;
                Frame frame = this.frame;
                return frame;
            }
            if (doAudio && audioFrameGrabbed) {
                if (doProcessing) {
                    this.processSamples();
                }
                this.frame.keyFrame = (this.samples_frame.flags() & 2) != 0;
                Frame frame = this.frame;
                return frame;
            }
            if (doData && dataFrameGrabbed) {
                Frame frame = this.frame;
                return frame;
            }
            this.frame.keyFrame = false;
            this.frame.imageWidth = 0;
            this.frame.imageHeight = 0;
            this.frame.imageDepth = 0;
            this.frame.imageChannels = 0;
            this.frame.imageStride = 0;
            this.frame.image = null;
            this.frame.sampleRate = 0;
            this.frame.audioChannels = 0;
            this.frame.samples = null;
            this.frame.data = null;
            this.frame.opaque = null;
            this.frame.type = null;
            boolean done = false;
            boolean bl = readPacket = this.pkt.stream_index() == -1;
            block40: while (!done) {
                AVRational time_base;
                long pts;
                int ret = 0;
                if (readPacket) {
                    if (this.pkt.stream_index() != -1) {
                        avcodec.av_packet_unref(this.pkt);
                        this.pkt.stream_index(-1);
                    }
                    if ((ret = avformat.av_read_frame(this.oc, this.pkt)) < 0) {
                        if (ret == avutil.AVERROR_EAGAIN()) {
                            try {
                                Thread.sleep(10L);
                                continue;
                            }
                            catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                                Frame frame = null;
                                if (scope != null) {
                                    if (var7_7 != null) {
                                        try {
                                            scope.close();
                                        }
                                        catch (Throwable throwable) {
                                            var7_7.addSuppressed(throwable);
                                        }
                                    } else {
                                        scope.close();
                                    }
                                }
                                return frame;
                            }
                        }
                        if (doVideo && this.video_st != null || doAudio && this.audio_st != null) {
                            this.pkt.stream_index(doVideo && this.video_st != null ? this.video_st.index() : this.audio_st.index());
                            this.pkt.flags(1);
                            this.pkt.data(null);
                            this.pkt.size(0);
                        } else {
                            this.pkt.stream_index(-1);
                            Frame ex = null;
                            return ex;
                        }
                    }
                }
                this.frame.streamIndex = this.pkt.stream_index();
                if (doVideo && this.video_st != null && this.frame.streamIndex == this.video_st.index() && (!keyFrames || this.pkt.flags() == 1)) {
                    if (readPacket) {
                        ret = avcodec.avcodec_send_packet(this.video_c, this.pkt);
                        if (this.pkt.data() == null && this.pkt.size() == 0) {
                            this.pkt.stream_index(-1);
                        }
                        if (ret == avutil.AVERROR_EAGAIN() || ret == avutil.AVERROR_EOF() || ret < 0) {
                            // empty if block
                        }
                    }
                    while (!done) {
                        ret = avcodec.avcodec_receive_frame(this.video_c, this.picture);
                        if (ret == avutil.AVERROR_EAGAIN() || ret == avutil.AVERROR_EOF()) {
                            if (this.pkt.data() == null && this.pkt.size() == 0) {
                                this.pkt.stream_index(-1);
                                doVideo = false;
                                if (doAudio) {
                                    readPacket = false;
                                    continue block40;
                                }
                                Frame ex = null;
                                return ex;
                            }
                            readPacket = true;
                            continue block40;
                        }
                        if (ret < 0) {
                            readPacket = true;
                            continue block40;
                        }
                        if (keyFrames && this.picture.pict_type() != 1) continue;
                        pts = this.picture.best_effort_timestamp();
                        time_base = this.video_st.time_base();
                        this.timestamp = 1000000L * pts * (long)time_base.num() / (long)time_base.den();
                        long ts0 = this.oc.start_time() != avutil.AV_NOPTS_VALUE ? this.oc.start_time() : 0L;
                        this.frameNumber = (int)Math.round((double)(this.timestamp - ts0) * this.getFrameRate() / 1000000.0);
                        this.frame.image = this.image_buf;
                        if (doProcessing) {
                            this.processImage();
                        }
                        done = true;
                        this.frame.timestamp = this.timestamp;
                        this.frame.keyFrame = (this.picture.flags() & 2) != 0;
                        this.frame.pictType = (char)avutil.av_get_picture_type_char(this.picture.pict_type());
                        this.frame.type = Frame.Type.VIDEO;
                    }
                    continue;
                }
                if (doAudio && this.audio_st != null && this.frame.streamIndex == this.audio_st.index()) {
                    if (!readPacket || (ret = avcodec.avcodec_send_packet(this.audio_c, this.pkt)) < 0) {
                        // empty if block
                    }
                    while (!done) {
                        ret = avcodec.avcodec_receive_frame(this.audio_c, this.samples_frame);
                        if (ret == avutil.AVERROR_EAGAIN() || ret == avutil.AVERROR_EOF()) {
                            if (this.pkt.data() == null && this.pkt.size() == 0) {
                                this.pkt.stream_index(-1);
                                doAudio = false;
                                Frame pts2 = null;
                                return pts2;
                            }
                            readPacket = true;
                            continue block40;
                        }
                        if (ret < 0) {
                            readPacket = true;
                            continue block40;
                        }
                        pts = this.samples_frame.best_effort_timestamp();
                        time_base = this.audio_st.time_base();
                        this.timestamp = 1000000L * pts * (long)time_base.num() / (long)time_base.den();
                        this.frame.samples = this.samples_buf;
                        if (doProcessing) {
                            this.processSamples();
                        }
                        done = true;
                        this.frame.timestamp = this.timestamp;
                        this.frame.keyFrame = (this.samples_frame.flags() & 2) != 0;
                        this.frame.type = Frame.Type.AUDIO;
                    }
                    continue;
                }
                if (readPacket && doData && this.frame.streamIndex > -1 && this.frame.streamIndex < this.streams.length && this.streams[this.frame.streamIndex] != 0 && this.streams[this.frame.streamIndex] != 1) {
                    this.frame.data = this.pkt.data().position(0L).capacity(this.pkt.size()).asByteBuffer();
                    this.frame.opaque = this.pkt;
                    done = true;
                    switch (this.streams[this.frame.streamIndex]) {
                        case 2: {
                            this.frame.type = Frame.Type.DATA;
                            continue block40;
                        }
                        case 3: {
                            this.frame.type = Frame.Type.SUBTITLE;
                            continue block40;
                        }
                        case 4: {
                            this.frame.type = Frame.Type.ATTACHMENT;
                            continue block40;
                        }
                    }
                    this.frame.type = null;
                    continue;
                }
                readPacket = true;
            }
            Frame frame = this.frame;
            return frame;
        }
    }

    public synchronized AVPacket grabPacket() throws Exception {
        if (this.oc == null || this.oc.isNull()) {
            throw new Exception("Could not grab: No AVFormatContext. (Has start() been called?)");
        }
        if (!this.started) {
            throw new Exception("start() was not called successfully!");
        }
        if (avformat.av_read_frame(this.oc, this.pkt) < 0) {
            return null;
        }
        return this.pkt;
    }

    static {
        try {
            FFmpegFrameGrabber.tryLoad();
        }
        catch (Exception exception) {
            // empty catch block
        }
        inputStreams = Collections.synchronizedMap(new HashMap());
        readCallback = (ReadCallback)new ReadCallback().retainReference();
        seekCallback = (SeekCallback)new SeekCallback().retainReference();
    }

    static class SeekCallback
    extends Seek_Pointer_long_int {
        SeekCallback() {
        }

        @Override
        public long call(Pointer opaque, long offset, int whence) {
            try {
                long skipped;
                long remaining;
                InputStream is = inputStreams.get(opaque);
                long size = 0L;
                switch (whence) {
                    case 0: {
                        is.reset();
                        break;
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        long n;
                        is.reset();
                        while ((n = is.skip(Long.MAX_VALUE)) != 0L) {
                            size += n;
                        }
                        offset += size;
                        is.reset();
                        break;
                    }
                    case 65536: {
                        long n;
                        remaining = 0L;
                        while ((n = is.skip(Long.MAX_VALUE)) != 0L) {
                            remaining += n;
                        }
                        is.reset();
                        while ((n = is.skip(Long.MAX_VALUE)) != 0L) {
                            size += n;
                        }
                        offset = size - remaining;
                        is.reset();
                        break;
                    }
                    default: {
                        return -1L;
                    }
                }
                for (remaining = offset; remaining > 0L && (skipped = is.skip(remaining)) != 0L; remaining -= skipped) {
                }
                return whence == 65536 ? size : 0L;
            }
            catch (Throwable t) {
                System.err.println("Error on InputStream.reset() or skip(): " + t);
                return -1L;
            }
        }
    }

    static class ReadCallback
    extends Read_packet_Pointer_BytePointer_int {
        ReadCallback() {
        }

        @Override
        public int call(Pointer opaque, BytePointer buf, int buf_size) {
            try {
                byte[] b = new byte[buf_size];
                InputStream is = inputStreams.get(opaque);
                int size = is.read(b, 0, buf_size);
                if (size < 0) {
                    return avutil.AVERROR_EOF();
                }
                buf.put(b, 0, size);
                return size;
            }
            catch (Throwable t) {
                System.err.println("Error on InputStream.read(): " + t);
                return -1;
            }
        }
    }

    public static class Exception
    extends FrameGrabber.Exception {
        public Exception(String message) {
            super(message + " (For more details, make sure FFmpegLogCallback.set() has been called.)");
        }

        public Exception(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

