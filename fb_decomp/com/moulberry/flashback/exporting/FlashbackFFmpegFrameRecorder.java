/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.exporting;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avcodec.AVPacketSideData;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVIOContext;
import org.bytedeco.ffmpeg.avformat.AVOutputFormat;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.avutil.AVChannelLayout;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avformat;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.ffmpeg.global.swresample;
import org.bytedeco.ffmpeg.global.swscale;
import org.bytedeco.ffmpeg.swresample.SwrContext;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.PointerScope;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacv.FFmpegLogCallback;

public class FlashbackFFmpegFrameRecorder {
    private static Exception loadingException = null;
    private String format;
    private String videoCodecName;
    private int imageWidth;
    private int imageHeight;
    private int audioChannels;
    private int pixelFormat;
    private int videoCodec;
    private int videoBitrate;
    private int gopSize = -1;
    private int videoProfile = -1;
    private double frameRate;
    private double videoQuality = -1.0;
    private int sampleFormat;
    private int audioCodec;
    private int audioBitrate;
    private int sampleRate;
    private double audioQuality = -1.0;
    private Charset charset = Charset.defaultCharset();
    private Map<String, String> options = new HashMap<String, String>();
    private Map<String, String> videoOptions = new HashMap<String, String>();
    private Map<String, String> audioOptions = new HashMap<String, String>();
    private Map<String, String> metadata = new HashMap<String, String>();
    private Map<String, String> videoMetadata = new HashMap<String, String>();
    private Map<String, String> audioMetadata = new HashMap<String, String>();
    private Map<String, Buffer> videoSideData = new HashMap<String, Buffer>();
    private Map<String, Buffer> audioSideData = new HashMap<String, Buffer>();
    private int maxBFrames = -1;
    private int trellis = -1;
    private int maxDelay = -1;
    private String filename;
    private AVFrame picture;
    private AVFrame frame;
    private Pointer[] samples_in;
    private BytePointer[] samples_out;
    private BytePointer audio_outbuf;
    private int audio_outbuf_size;
    private int audio_input_frame_size;
    private AVOutputFormat oformat;
    private AVFormatContext oc;
    private AVCodec video_codec;
    private AVCodec audio_codec;
    private AVCodecContext video_c;
    private AVCodecContext audio_c;
    private AVStream video_st;
    private AVStream audio_st;
    private SwrContext samples_convert_ctx;
    private int samples_channels;
    private int samples_format;
    private int samples_rate;
    private PointerPointer plane_ptr;
    private PointerPointer plane_ptr2;
    private AVPacket video_pkt;
    private AVPacket audio_pkt;
    private boolean wrote_samples = false;
    private AVChannelLayout default_layout;
    private volatile boolean started = false;

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
        }
        catch (Throwable t) {
            if (t instanceof Exception) {
                loadingException = (Exception)t;
                throw loadingException;
            }
            loadingException = new Exception("Failed to load " + String.valueOf(FlashbackFFmpegFrameRecorder.class), t);
            throw loadingException;
        }
    }

    public FlashbackFFmpegFrameRecorder(String filename, int imageWidth, int imageHeight, int audioChannels) {
        this.filename = filename;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.audioChannels = audioChannels;
        this.pixelFormat = -1;
        this.videoCodec = 0;
        this.videoBitrate = 400000;
        this.frameRate = 30.0;
        this.sampleFormat = -1;
        this.audioCodec = 0;
        this.audioBitrate = 64000;
        this.sampleRate = 44100;
    }

    public void setMetadata(String key, String value) {
        this.metadata.put(key, value);
    }

    public void setMuxerOption(String key, String value) {
        this.options.put(key, value);
    }

    public void setVideoOption(String key, String value) {
        this.videoOptions.put(key, value);
    }

    public void setAudioOption(String key, String value) {
        this.audioOptions.put(key, value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void release() throws Exception {
        Class<avcodec> clazz = avcodec.class;
        synchronized (avcodec.class) {
            this.releaseUnsafe();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    public synchronized void releaseUnsafe() {
        int i;
        this.started = false;
        if (this.plane_ptr != null && this.plane_ptr2 != null) {
            this.plane_ptr.releaseReference();
            this.plane_ptr2.releaseReference();
            this.plane_ptr2 = null;
            this.plane_ptr = null;
        }
        if (this.video_pkt != null && this.audio_pkt != null) {
            this.video_pkt.releaseReference();
            this.audio_pkt.releaseReference();
            this.audio_pkt = null;
            this.video_pkt = null;
        }
        if (this.default_layout != null) {
            this.default_layout.releaseReference();
            this.default_layout = null;
        }
        if (this.video_c != null) {
            avcodec.avcodec_free_context(this.video_c);
            this.video_c = null;
        }
        if (this.audio_c != null) {
            avcodec.avcodec_free_context(this.audio_c);
            this.audio_c = null;
        }
        if (this.picture != null) {
            avutil.av_frame_free(this.picture);
            this.picture = null;
        }
        if (this.frame != null) {
            avutil.av_frame_free(this.frame);
            this.frame = null;
        }
        if (this.samples_in != null) {
            for (i = 0; i < this.samples_in.length; ++i) {
                if (this.samples_in[i] == null) continue;
                this.samples_in[i].releaseReference();
            }
            this.samples_in = null;
        }
        if (this.samples_out != null) {
            for (i = 0; i < this.samples_out.length; ++i) {
                avutil.av_free(this.samples_out[i].position(0L));
            }
            this.samples_out = null;
        }
        if (this.audio_outbuf != null) {
            avutil.av_free(this.audio_outbuf);
            this.audio_outbuf = null;
        }
        if (this.video_st != null && this.video_st.metadata() != null) {
            avutil.av_dict_free(this.video_st.metadata());
            this.video_st.metadata(null);
        }
        if (this.audio_st != null && this.audio_st.metadata() != null) {
            avutil.av_dict_free(this.audio_st.metadata());
            this.audio_st.metadata(null);
        }
        this.video_st = null;
        this.audio_st = null;
        this.filename = null;
        if (this.oc != null && !this.oc.isNull()) {
            if ((this.oformat.flags() & 1) == 0) {
                avformat.avio_close(this.oc.pb());
            }
            avformat.avformat_free_context(this.oc);
            this.oc = null;
        }
        if (this.samples_convert_ctx != null) {
            swresample.swr_free(this.samples_convert_ctx);
            this.samples_convert_ctx.releaseReference();
            this.samples_convert_ctx = null;
        }
        Pointer.trimMemory();
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setPixelFormat(int pixelFormat) {
        this.pixelFormat = pixelFormat;
    }

    public void setVideoCodec(int videoCodec) {
        this.videoCodec = videoCodec;
    }

    public void setVideoCodecName(String videoCodecName) {
        this.videoCodecName = videoCodecName;
    }

    public void setVideoBitrate(int videoBitrate) {
        this.videoBitrate = videoBitrate;
    }

    public void setGopSize(int gopSize) {
        this.gopSize = gopSize;
    }

    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }

    public void setSampleFormat(int sampleFormat) {
        this.sampleFormat = sampleFormat;
    }

    public void setAudioCodec(int audioCodec) {
        this.audioCodec = audioCodec;
    }

    public void setAudioBitrate(int audioBitrate) {
        this.audioBitrate = audioBitrate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void start() throws Exception {
        Class<avcodec> clazz = avcodec.class;
        synchronized (avcodec.class) {
            this.startUnsafe();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    public synchronized void startUnsafe() throws Exception {
        try (PointerScope scope = new PointerScope();){
            int ret;
            AVDictionary options;
            if (this.oc != null && !this.oc.isNull()) {
                throw new Exception("start() has already been called: Call stop() before calling start() again.");
            }
            this.picture = null;
            this.frame = null;
            this.audio_outbuf = null;
            this.oc = new AVFormatContext(null);
            this.video_c = null;
            this.audio_c = null;
            this.video_st = null;
            this.audio_st = null;
            this.plane_ptr = (PointerPointer)new PointerPointer(8L).retainReference();
            this.plane_ptr2 = (PointerPointer)new PointerPointer(8L).retainReference();
            this.video_pkt = (AVPacket)new AVPacket().retainReference();
            this.audio_pkt = (AVPacket)new AVPacket().retainReference();
            this.wrote_samples = false;
            this.default_layout = (AVChannelLayout)new AVChannelLayout().retainReference();
            String format_name = this.format == null || this.format.isEmpty() ? null : this.format;
            this.oformat = avformat.av_guess_format(format_name, this.filename, null);
            if (this.oformat == null) {
                throw new Exception("av_guess_format() error: Could not guess output format for \"" + this.filename + "\" and " + this.format + " format.");
            }
            format_name = this.oformat.name().getString();
            if (avformat.avformat_alloc_output_context2(this.oc, null, format_name, this.filename) < 0) {
                throw new Exception("avformat_alloc_context2() error:\tCould not allocate format context");
            }
            this.oc.oformat(this.oformat);
            this.oc.url(new BytePointer(avutil.av_calloc(this.filename.getBytes().length + 1, 1L)).putString(this.filename));
            this.oc.max_delay(this.maxDelay);
            if (this.imageWidth > 0 && this.imageHeight > 0) {
                if (this.videoCodec == 0) {
                    this.videoCodec = this.oformat.video_codec();
                }
                if ((this.video_codec = avcodec.avcodec_find_encoder_by_name(this.videoCodecName)) == null && (this.video_codec = avcodec.avcodec_find_encoder(this.videoCodec)) == null) {
                    this.releaseUnsafe();
                    throw new Exception("avcodec_find_encoder() error: Video codec not found.");
                }
                AVRational frame_rate = avutil.av_d2q(this.frameRate, 1001000);
                AVRational supported_framerates = this.video_codec.supported_framerates();
                if (supported_framerates != null) {
                    int idx = avutil.av_find_nearest_q_idx(frame_rate, supported_framerates);
                    frame_rate = supported_framerates.position(idx);
                }
                if ((this.video_st = avformat.avformat_new_stream(this.oc, null)) == null) {
                    this.releaseUnsafe();
                    throw new Exception("avformat_new_stream() error: Could not allocate video stream.");
                }
                this.video_c = avcodec.avcodec_alloc_context3(this.video_codec);
                if (this.video_c == null) {
                    this.releaseUnsafe();
                    throw new Exception("avcodec_alloc_context3() error: Could not allocate video encoding context.");
                }
                this.video_c.codec_id(this.video_codec.id());
                this.video_c.codec_type(0);
                this.video_c.bit_rate(this.videoBitrate);
                if (this.imageWidth % 2 != 0 || this.imageHeight % 2 != 0) {
                    throw new Exception("image dimensions must be a multiple of 2");
                }
                this.video_c.width(this.imageWidth);
                this.video_c.height(this.imageHeight);
                AVRational time_base = avutil.av_inv_q(frame_rate);
                this.video_c.time_base(time_base);
                this.video_st.time_base(time_base);
                this.video_st.avg_frame_rate(frame_rate);
                if (this.gopSize >= 0) {
                    this.video_c.gop_size(this.gopSize);
                }
                if (this.videoProfile >= 0) {
                    this.video_c.profile(this.videoProfile);
                }
                if (this.videoQuality >= 0.0) {
                    this.video_c.flags(this.video_c.flags() | 2);
                    this.video_c.global_quality((int)Math.round(118.0 * this.videoQuality));
                }
                if (this.pixelFormat != -1) {
                    this.video_c.pix_fmt(this.pixelFormat);
                } else if (this.video_c.codec_id() == 13 || this.video_c.codec_id() == 61 || this.video_c.codec_id() == 25 || this.video_c.codec_id() == 33) {
                    this.video_c.pix_fmt(avutil.AV_PIX_FMT_RGB32);
                } else if (this.video_c.codec_id() == 11) {
                    this.video_c.pix_fmt(3);
                } else if (this.video_c.codec_id() == 7 || this.video_c.codec_id() == 8) {
                    this.video_c.pix_fmt(12);
                } else {
                    this.video_c.pix_fmt(0);
                }
                if (this.video_c.codec_id() == 4) {
                    if (this.imageWidth <= 128 && this.imageHeight <= 96) {
                        this.video_c.width(128).height(96);
                    } else if (this.imageWidth <= 176 && this.imageHeight <= 144) {
                        this.video_c.width(176).height(144);
                    } else if (this.imageWidth <= 352 && this.imageHeight <= 288) {
                        this.video_c.width(352).height(288);
                    } else if (this.imageWidth <= 704 && this.imageHeight <= 576) {
                        this.video_c.width(704).height(576);
                    } else {
                        this.video_c.width(1408).height(1152);
                    }
                } else if (this.video_c.codec_id() == 27 && this.videoProfile < 0) {
                    this.video_c.profile(578);
                }
                if ((this.oformat.flags() & 0x40) != 0) {
                    this.video_c.flags(this.video_c.flags() | 0x400000);
                }
                if ((this.video_codec.capabilities() & 0x200) != 0) {
                    this.video_c.strict_std_compliance(-2);
                }
                if (this.maxBFrames >= 0) {
                    this.video_c.max_b_frames(this.maxBFrames);
                    this.video_c.has_b_frames(this.maxBFrames == 0 ? 0 : 1);
                }
                if (this.trellis >= 0) {
                    this.video_c.trellis(this.trellis);
                }
            }
            if (this.audioChannels > 0 && this.audioBitrate > 0 && this.sampleRate > 0) {
                if (this.audioCodec == 0) {
                    this.audioCodec = this.oformat.audio_codec();
                }
                if ((this.audio_codec = avcodec.avcodec_find_encoder(this.audioCodec)) == null) {
                    this.releaseUnsafe();
                    throw new Exception("avcodec_find_encoder() error: Audio codec not found.");
                }
                AVRational sample_rate = avutil.av_d2q(this.sampleRate, 1001000);
                this.audio_st = avformat.avformat_new_stream(this.oc, null);
                if (this.audio_st == null) {
                    this.releaseUnsafe();
                    throw new Exception("avformat_new_stream() error: Could not allocate audio stream.");
                }
                this.audio_c = avcodec.avcodec_alloc_context3(this.audio_codec);
                if (this.audio_c == null) {
                    this.releaseUnsafe();
                    throw new Exception("avcodec_alloc_context3() error: Could not allocate audio encoding context.");
                }
                this.audio_c.codec_id(this.audio_codec.id());
                this.audio_c.codec_type(1);
                this.audio_c.bit_rate(this.audioBitrate);
                this.audio_c.sample_rate(this.sampleRate);
                avutil.av_channel_layout_default(this.default_layout, this.audioChannels);
                this.audio_c.ch_layout(this.default_layout);
                if (this.sampleFormat != -1) {
                    this.audio_c.sample_fmt(this.sampleFormat);
                } else {
                    this.audio_c.sample_fmt(8);
                    IntPointer formats = this.audio_c.codec().sample_fmts();
                    int i = 0;
                    while (formats.get(i) != -1) {
                        if (formats.get(i) == 1) {
                            this.audio_c.sample_fmt(1);
                            break;
                        }
                        ++i;
                    }
                }
                AVRational time_base = avutil.av_inv_q(sample_rate);
                this.audio_c.time_base(time_base);
                this.audio_st.time_base(time_base);
                switch (this.audio_c.sample_fmt()) {
                    case 0: 
                    case 5: {
                        this.audio_c.bits_per_raw_sample(8);
                        break;
                    }
                    case 1: 
                    case 6: {
                        this.audio_c.bits_per_raw_sample(16);
                        break;
                    }
                    case 2: 
                    case 7: {
                        this.audio_c.bits_per_raw_sample(32);
                        break;
                    }
                    case 3: 
                    case 8: {
                        this.audio_c.bits_per_raw_sample(32);
                        break;
                    }
                    case 4: 
                    case 9: {
                        this.audio_c.bits_per_raw_sample(64);
                        break;
                    }
                    default: {
                        assert (false);
                        break;
                    }
                }
                if (this.audioQuality >= 0.0) {
                    this.audio_c.flags(this.audio_c.flags() | 2);
                    this.audio_c.global_quality((int)Math.round(118.0 * this.audioQuality));
                }
                if ((this.oformat.flags() & 0x40) != 0) {
                    this.audio_c.flags(this.audio_c.flags() | 0x400000);
                }
                if ((this.audio_codec.capabilities() & 0x200) != 0) {
                    this.audio_c.strict_std_compliance(-2);
                }
            }
            if (this.video_st != null) {
                options = new AVDictionary(null);
                if (this.videoQuality >= 0.0) {
                    avutil.av_dict_set(options, "crf", "" + this.videoQuality, 0);
                }
                for (Map.Entry<String, String> e : this.videoOptions.entrySet()) {
                    avutil.av_dict_set(options, e.getKey(), e.getValue(), 0);
                }
                this.video_c.thread_count(0);
                ret = avcodec.avcodec_open2(this.video_c, this.video_codec, options);
                if (ret < 0) {
                    this.releaseUnsafe();
                    avutil.av_dict_free(options);
                    throw new Exception("avcodec_open2() error " + ret + ": Could not open video codec.");
                }
                FFmpegLogCallback.logRejectedOptions(options, "avcodec_open2");
                avutil.av_dict_free(options);
                this.picture = avutil.av_frame_alloc();
                if (this.picture == null) {
                    this.releaseUnsafe();
                    throw new Exception("av_frame_alloc() error: Could not allocate picture.");
                }
                this.picture.pts(0L);
                ret = avcodec.avcodec_parameters_from_context(this.video_st.codecpar(), this.video_c);
                if (ret < 0) {
                    this.releaseUnsafe();
                    throw new Exception("avcodec_parameters_from_context() error " + ret + ": Could not copy the video stream parameters.");
                }
                AVDictionary metadata = new AVDictionary(null);
                for (Map.Entry<String, String> entry : this.videoMetadata.entrySet()) {
                    avutil.av_dict_set(metadata, new BytePointer(entry.getKey(), this.charset), new BytePointer(entry.getValue(), this.charset), 0);
                }
                this.video_st.metadata(metadata);
                for (Map.Entry<String, Object> entry : this.videoSideData.entrySet()) {
                    void var9_34;
                    int type = -1;
                    boolean bl = false;
                    while (var9_34 < 41) {
                        BytePointer s = avcodec.av_packet_side_data_name((int)var9_34);
                        if (s != null && !s.isNull() && entry.getKey().equals(s.getString())) {
                            type = var9_34;
                            break;
                        }
                        ++var9_34;
                    }
                    Pointer pointer = new Pointer((Buffer)entry.getValue());
                    AVPacketSideData coded_side_data = this.video_st.codecpar().coded_side_data();
                    int[] nb_coded_side_data = new int[]{this.video_st.codecpar().nb_coded_side_data()};
                    AVPacketSideData b = avcodec.av_packet_side_data_new(coded_side_data, nb_coded_side_data, type, pointer.capacity(), 0);
                    this.video_st.codecpar().coded_side_data(coded_side_data);
                    this.video_st.codecpar().nb_coded_side_data(nb_coded_side_data[0]);
                    if (b == null || b.isNull() || b.data() == null || b.data().isNull()) continue;
                    b.data().capacity(pointer.capacity()).put(pointer);
                }
            }
            if (this.audio_st != null) {
                void var7_24;
                options = new AVDictionary(null);
                if (this.audioQuality >= 0.0) {
                    avutil.av_dict_set(options, "crf", "" + this.audioQuality, 0);
                }
                for (Map.Entry<String, String> e : this.audioOptions.entrySet()) {
                    avutil.av_dict_set(options, e.getKey(), e.getValue(), 0);
                }
                this.audio_c.thread_count(0);
                ret = avcodec.avcodec_open2(this.audio_c, this.audio_codec, options);
                if (ret < 0) {
                    this.releaseUnsafe();
                    avutil.av_dict_free(options);
                    throw new Exception("avcodec_open2() error " + ret + ": Could not open audio codec.");
                }
                FFmpegLogCallback.logRejectedOptions(options, "avcodec_open2");
                avutil.av_dict_free(options);
                this.audio_outbuf_size = 262144;
                this.audio_outbuf = new BytePointer(avutil.av_malloc(this.audio_outbuf_size));
                if (this.audio_c.frame_size() <= 1) {
                    this.audio_outbuf_size = 16384;
                    this.audio_input_frame_size = this.audio_outbuf_size / this.audio_c.ch_layout().nb_channels();
                    switch (this.audio_c.codec_id()) {
                        case 65536: 
                        case 65537: 
                        case 65538: 
                        case 65539: {
                            this.audio_input_frame_size >>= 1;
                            break;
                        }
                    }
                } else {
                    this.audio_input_frame_size = this.audio_c.frame_size();
                }
                int planes = avutil.av_sample_fmt_is_planar(this.audio_c.sample_fmt()) != 0 ? this.audio_c.ch_layout().nb_channels() : 1;
                int data_size = avutil.av_samples_get_buffer_size((IntPointer)null, this.audio_c.ch_layout().nb_channels(), this.audio_input_frame_size, this.audio_c.sample_fmt(), 1) / planes;
                this.samples_out = new BytePointer[planes];
                boolean bl = false;
                while (var7_24 < this.samples_out.length) {
                    this.samples_out[var7_24] = new BytePointer(avutil.av_malloc(data_size)).capacity(data_size);
                    ++var7_24;
                }
                this.samples_in = new Pointer[8];
                this.frame = avutil.av_frame_alloc();
                if (this.frame == null) {
                    this.releaseUnsafe();
                    throw new Exception("av_frame_alloc() error: Could not allocate audio frame.");
                }
                this.frame.pts(0L);
                ret = avcodec.avcodec_parameters_from_context(this.audio_st.codecpar(), this.audio_c);
                if (ret < 0) {
                    this.releaseUnsafe();
                    throw new Exception("avcodec_parameters_from_context() error " + ret + ": Could not copy the audio stream parameters.");
                }
                AVDictionary aVDictionary = new AVDictionary(null);
                for (Map.Entry<String, String> entry : this.audioMetadata.entrySet()) {
                    avutil.av_dict_set(aVDictionary, new BytePointer(entry.getKey(), this.charset), new BytePointer(entry.getValue(), this.charset), 0);
                }
                this.audio_st.metadata(aVDictionary);
                for (Map.Entry<String, Object> entry : this.audioSideData.entrySet()) {
                    int type = -1;
                    for (int i = 0; i < 41; ++i) {
                        BytePointer s = avcodec.av_packet_side_data_name(i);
                        if (s == null || s.isNull() || !entry.getKey().equals(s.getString())) continue;
                        type = i;
                        break;
                    }
                    Pointer p = new Pointer((Buffer)entry.getValue());
                    AVPacketSideData coded_side_data = this.audio_st.codecpar().coded_side_data();
                    int[] nb_coded_side_data = new int[]{this.audio_st.codecpar().nb_coded_side_data()};
                    AVPacketSideData b = avcodec.av_packet_side_data_new(coded_side_data, nb_coded_side_data, type, p.capacity(), 0);
                    this.audio_st.codecpar().coded_side_data(coded_side_data);
                    this.audio_st.codecpar().nb_coded_side_data(nb_coded_side_data[0]);
                    if (b == null || b.isNull() || b.data() == null || b.data().isNull()) continue;
                    b.data().capacity(p.capacity()).put(p);
                }
            }
            options = new AVDictionary(null);
            for (Map.Entry<String, String> e : this.options.entrySet()) {
                avutil.av_dict_set(options, e.getKey(), e.getValue(), 0);
            }
            if ((this.oformat.flags() & 1) == 0) {
                AVIOContext pb = new AVIOContext(null);
                ret = avformat.avio_open2(pb, this.filename, 2, null, options);
                if (ret < 0) {
                    String errorMsg = "avio_open2 error() error " + ret + ": Could not open '" + this.filename + "'";
                    this.releaseUnsafe();
                    avutil.av_dict_free(options);
                    throw new Exception(errorMsg);
                }
                FFmpegLogCallback.logRejectedOptions(options, "avio_open2");
                this.oc.pb(pb);
            }
            AVDictionary metadata = new AVDictionary(null);
            for (Map.Entry<String, String> entry : this.metadata.entrySet()) {
                avutil.av_dict_set(metadata, new BytePointer(entry.getKey(), this.charset), new BytePointer(entry.getValue(), this.charset), 0);
            }
            ret = avformat.avformat_write_header(this.oc.metadata(metadata), options);
            if (ret < 0) {
                String errorMsg = "avformat_write_header error() error " + ret + ": Could not write header to '" + this.filename + "'";
                this.releaseUnsafe();
                avutil.av_dict_free(options);
                throw new Exception(errorMsg);
            }
            FFmpegLogCallback.logRejectedOptions(options, "avformat_write_header");
            avutil.av_dict_free(options);
            if (avutil.av_log_get_level() >= 32) {
                avformat.av_dump_format(this.oc, 0, this.filename, 1);
            }
            this.started = true;
        }
    }

    public synchronized void flush() throws Exception {
        while (this.video_st != null && this.flushImage()) {
        }
        while (this.audio_st != null && this.recordSamples(null)) {
        }
        if (this.video_st != null || this.audio_st != null) {
            avformat.av_interleaved_write_frame(this.oc, null);
        } else {
            avformat.av_write_frame(this.oc, null);
        }
    }

    public void stop() throws Exception {
        if (this.oc != null) {
            try {
                this.flush();
                avformat.av_write_trailer(this.oc);
            }
            finally {
                this.release();
            }
        }
    }

    private synchronized boolean flushImage() throws Exception {
        if (this.video_st == null) {
            throw new Exception("No video output stream (Is imageWidth > 0 && imageHeight > 0 and has start() been called?)");
        }
        if (!this.started) {
            throw new Exception("start() was not called successfully!");
        }
        int ret = avcodec.avcodec_send_frame(this.video_c, null);
        boolean gotVideoPacket = false;
        while (ret >= 0) {
            avcodec.av_new_packet(this.video_pkt, 0);
            ret = avcodec.avcodec_receive_packet(this.video_c, this.video_pkt);
            if (ret == avutil.AVERROR_EAGAIN() || ret == avutil.AVERROR_EOF()) {
                avcodec.av_packet_unref(this.video_pkt);
                break;
            }
            if (ret < 0) {
                avcodec.av_packet_unref(this.video_pkt);
                throw new Exception("avcodec_receive_packet() error " + ret + ": Error during video encoding.");
            }
            gotVideoPacket = true;
            avcodec.av_packet_rescale_ts(this.video_pkt, this.video_c.time_base(), this.video_st.time_base());
            this.video_pkt.stream_index(this.video_st.index());
            this.writePacket(this.video_pkt);
        }
        return gotVideoPacket;
    }

    public synchronized void recordImage(int width, int height, int pixelFormat, ByteBuffer image) throws Exception {
        try (PointerScope scope = new PointerScope();){
            if (this.video_st == null) {
                throw new Exception("No video output stream (Is imageWidth > 0 && imageHeight > 0 and has start() been called?)");
            }
            if (!this.started) {
                throw new Exception("start() was not called successfully!");
            }
            BytePointer data = new BytePointer(image).position(0L);
            if (this.video_c.pix_fmt() != pixelFormat) {
                throw new IllegalArgumentException("Pixel format doesn't match encoder pixel format");
            }
            if (this.video_c.width() != width) {
                throw new IllegalArgumentException("Width doesn't match encoder width");
            }
            if (this.video_c.height() != height) {
                throw new IllegalArgumentException("Height doesn't match encoder height");
            }
            avutil.av_image_fill_arrays(new PointerPointer(this.picture), this.picture.linesize(), data, pixelFormat, width, height, 1);
            this.picture.format(pixelFormat);
            this.picture.width(width);
            this.picture.height(height);
            this.picture.quality(this.video_c.global_quality());
            int ret = avcodec.avcodec_send_frame(this.video_c, this.picture);
            if (ret < 0) {
                throw new Exception("avcodec_send_frame() error " + ret + ": Error sending a video frame for encoding.");
            }
            this.picture.pts(this.picture.pts() + 1L);
            ret = avcodec.av_new_packet(this.video_pkt, 0);
            if (ret < 0) {
                throw new Exception("av_new_packet() error: " + ret);
            }
            ret = avcodec.avcodec_receive_packet(this.video_c, this.video_pkt);
            if (ret == avutil.AVERROR_EAGAIN() || ret == avutil.AVERROR_EOF()) {
                avcodec.av_packet_unref(this.video_pkt);
                return;
            }
            if (ret < 0) {
                avcodec.av_packet_unref(this.video_pkt);
                throw new Exception("avcodec_receive_packet() error " + ret + ": Error during video encoding.");
            }
            avcodec.av_packet_rescale_ts(this.video_pkt, this.video_c.time_base(), this.video_st.time_base());
            this.video_pkt.stream_index(this.video_st.index());
            this.writePacket(this.video_pkt);
        }
    }

    public synchronized boolean recordSamples(Buffer ... samples) throws Exception {
        try (PointerScope scope = new PointerScope();){
            int ret;
            boolean formatChanged;
            if (this.audio_st == null) {
                throw new Exception("No audio output stream (Is audioChannels > 0 and has start() been called?)");
            }
            if (!this.started) {
                throw new Exception("start() was not called successfully!");
            }
            if (samples == null && this.samples_convert_ctx == null) {
                boolean bl = false;
                return bl;
            }
            int sampleRate = this.audio_c.sample_rate();
            int audioChannels = this.audio_c.ch_layout().nb_channels();
            int inputSize = samples != null ? samples[0].limit() - samples[0].position() : 0;
            int inputFormat = this.samples_format;
            int inputChannels = samples != null && samples.length > 1 ? 1 : audioChannels;
            int inputDepth = 0;
            int outputFormat = this.audio_c.sample_fmt();
            int outputChannels = this.samples_out.length > 1 ? 1 : this.audio_c.ch_layout().nb_channels();
            int outputDepth = avutil.av_get_bytes_per_sample(outputFormat);
            if (samples != null && samples[0] instanceof ByteBuffer) {
                inputFormat = samples.length > 1 ? 5 : 0;
                inputDepth = 1;
                for (i = 0; i < samples.length; ++i) {
                    b = (ByteBuffer)samples[i];
                    if (this.samples_in[i] instanceof BytePointer && this.samples_in[i].capacity() >= (long)inputSize && ((ByteBuffer)b).hasArray()) {
                        ((BytePointer)this.samples_in[i]).position(0L).put(((ByteBuffer)b).array(), b.position(), inputSize);
                        continue;
                    }
                    if (this.samples_in[i] != null) {
                        this.samples_in[i].releaseReference();
                    }
                    this.samples_in[i] = new BytePointer((ByteBuffer)b).retainReference();
                }
            } else if (samples != null && samples[0] instanceof ShortBuffer) {
                inputFormat = samples.length > 1 ? 6 : 1;
                inputDepth = 2;
                for (i = 0; i < samples.length; ++i) {
                    b = (ShortBuffer)samples[i];
                    if (this.samples_in[i] instanceof ShortPointer && this.samples_in[i].capacity() >= (long)inputSize && ((ShortBuffer)b).hasArray()) {
                        ((ShortPointer)this.samples_in[i]).position(0L).put(((ShortBuffer)b).array(), samples[i].position(), inputSize);
                        continue;
                    }
                    if (this.samples_in[i] != null) {
                        this.samples_in[i].releaseReference();
                    }
                    this.samples_in[i] = new ShortPointer((ShortBuffer)b).retainReference();
                }
            } else if (samples != null && samples[0] instanceof IntBuffer) {
                inputFormat = samples.length > 1 ? 7 : 2;
                inputDepth = 4;
                for (i = 0; i < samples.length; ++i) {
                    b = (IntBuffer)samples[i];
                    if (this.samples_in[i] instanceof IntPointer && this.samples_in[i].capacity() >= (long)inputSize && ((IntBuffer)b).hasArray()) {
                        ((IntPointer)this.samples_in[i]).position(0L).put(((IntBuffer)b).array(), samples[i].position(), inputSize);
                        continue;
                    }
                    if (this.samples_in[i] != null) {
                        this.samples_in[i].releaseReference();
                    }
                    this.samples_in[i] = new IntPointer((IntBuffer)b).retainReference();
                }
            } else if (samples != null && samples[0] instanceof FloatBuffer) {
                inputFormat = samples.length > 1 ? 8 : 3;
                inputDepth = 4;
                for (i = 0; i < samples.length; ++i) {
                    b = (FloatBuffer)samples[i];
                    if (this.samples_in[i] instanceof FloatPointer && this.samples_in[i].capacity() >= (long)inputSize && ((FloatBuffer)b).hasArray()) {
                        ((FloatPointer)this.samples_in[i]).position(0L).put(((FloatBuffer)b).array(), b.position(), inputSize);
                        continue;
                    }
                    if (this.samples_in[i] != null) {
                        this.samples_in[i].releaseReference();
                    }
                    this.samples_in[i] = new FloatPointer((FloatBuffer)b).retainReference();
                }
            } else if (samples != null && samples[0] instanceof DoubleBuffer) {
                inputFormat = samples.length > 1 ? 9 : 4;
                inputDepth = 8;
                for (i = 0; i < samples.length; ++i) {
                    b = (DoubleBuffer)samples[i];
                    if (this.samples_in[i] instanceof DoublePointer && this.samples_in[i].capacity() >= (long)inputSize && ((DoubleBuffer)b).hasArray()) {
                        ((DoublePointer)this.samples_in[i]).position(0L).put(((DoubleBuffer)b).array(), b.position(), inputSize);
                        continue;
                    }
                    if (this.samples_in[i] != null) {
                        this.samples_in[i].releaseReference();
                    }
                    this.samples_in[i] = new DoublePointer((DoubleBuffer)b).retainReference();
                }
            } else if (samples != null) {
                throw new Exception("Audio samples Buffer has unsupported type: " + String.valueOf(samples));
            }
            boolean bl = formatChanged = this.samples_channels != audioChannels || this.samples_format != inputFormat || this.samples_rate != sampleRate;
            if (samples != null && (this.samples_convert_ctx == null || formatChanged)) {
                if (this.samples_convert_ctx == null) {
                    this.samples_convert_ctx = (SwrContext)new SwrContext().retainReference();
                }
                if ((ret = swresample.swr_alloc_set_opts2(this.samples_convert_ctx, this.audio_c.ch_layout(), outputFormat, this.audio_c.sample_rate(), this.default_layout, inputFormat, sampleRate, 0, null)) < 0) {
                    throw new Exception("swr_alloc_set_opts2() error " + ret + ": Cannot allocate the conversion context.");
                }
                ret = swresample.swr_init(this.samples_convert_ctx);
                if (ret < 0) {
                    throw new Exception("swr_init() error " + ret + ": Cannot initialize the conversion context.");
                }
                this.samples_channels = audioChannels;
                this.samples_format = inputFormat;
                this.samples_rate = sampleRate;
            }
            for (int i = 0; samples != null && i < samples.length; ++i) {
                ((Pointer)this.samples_in[i].position(this.samples_in[i].position() * (long)inputDepth)).limit((this.samples_in[i].position() + (long)inputSize) * (long)inputDepth);
            }
            while (true) {
                int i;
                int inputCount = (int)Math.min(samples != null ? (this.samples_in[0].limit() - this.samples_in[0].position()) / (long)(inputChannels * inputDepth) : 0L, Integer.MAX_VALUE);
                int outputCount = (int)Math.min((this.samples_out[0].limit() - this.samples_out[0].position()) / (long)(outputChannels * outputDepth), Integer.MAX_VALUE);
                inputCount = Math.min(inputCount, (outputCount * sampleRate + this.audio_c.sample_rate() - 1) / this.audio_c.sample_rate());
                for (i = 0; samples != null && i < samples.length; ++i) {
                    this.plane_ptr.put(i, this.samples_in[i]);
                }
                for (i = 0; i < this.samples_out.length; ++i) {
                    this.plane_ptr2.put(i, this.samples_out[i]);
                }
                if (samples == null && inputCount == 0 && this.plane_ptr != null) {
                    this.plane_ptr.releaseReference();
                    this.plane_ptr = null;
                }
                if ((ret = swresample.swr_convert(this.samples_convert_ctx, this.plane_ptr2, outputCount, this.plane_ptr, inputCount)) < 0) {
                    throw new Exception("swr_convert() error " + ret + ": Cannot convert audio samples.");
                }
                if (ret == 0 && inputCount == 0) break;
                for (i = 0; samples != null && i < samples.length; ++i) {
                    this.samples_in[i].position(this.samples_in[i].position() + (long)(inputCount * inputChannels * inputDepth));
                }
                for (i = 0; i < this.samples_out.length; ++i) {
                    this.samples_out[i].position(this.samples_out[i].position() + (long)(ret * outputChannels * outputDepth));
                }
                if (samples != null && this.samples_out[0].position() < this.samples_out[0].limit()) continue;
                this.writeSamples(this.audio_input_frame_size);
            }
            if (samples == null && this.samples_out[0].position() > 0L) {
                double sampleDivisor = Math.floor((int)Math.min(this.samples_out[0].limit(), Integer.MAX_VALUE) / this.audio_input_frame_size);
                this.writeSamples((int)Math.floor((double)((int)this.samples_out[0].position()) / sampleDivisor));
                boolean bl2 = this.writeFrame(null);
                return bl2;
            }
            boolean bl3 = samples != null ? (this.frame.flags() & 2) != 0 : this.writeFrame(null);
            return bl3;
        }
    }

    private void writeSamples(int nb_samples) throws Exception {
        if (this.samples_out == null || this.samples_out.length == 0) {
            return;
        }
        this.frame.nb_samples(nb_samples);
        avcodec.avcodec_fill_audio_frame(this.frame, this.audio_c.ch_layout().nb_channels(), this.audio_c.sample_fmt(), this.samples_out[0], (int)this.samples_out[0].position(), 0);
        for (int i = 0; i < this.samples_out.length; ++i) {
            int linesize = 0;
            linesize = this.samples_out[0].position() > 0L && this.samples_out[0].position() < this.samples_out[0].limit() ? (int)this.samples_out[i].position() + 31 & 0xFFFFFFE0 : (int)Math.min(this.samples_out[i].limit(), Integer.MAX_VALUE);
            this.frame.data(i, this.samples_out[i].position(0L));
            this.frame.linesize(i, linesize);
        }
        this.frame.ch_layout(this.audio_c.ch_layout());
        this.frame.format(this.audio_c.sample_fmt());
        this.frame.quality(this.audio_c.global_quality());
        this.writeFrame(this.frame);
        this.wrote_samples = true;
    }

    private boolean writeFrame(AVFrame frame) throws Exception {
        int ret = avcodec.avcodec_send_frame(this.audio_c, frame);
        if (ret < 0 && frame != null) {
            throw new Exception("avcodec_send_frame() error " + ret + ": Error sending an audio frame for encoding.");
        }
        if (frame != null) {
            frame.pts(frame.pts() + (long)frame.nb_samples());
        }
        boolean gotAudioPacket = false;
        while (ret >= 0) {
            avcodec.av_new_packet(this.audio_pkt, this.audio_outbuf_size);
            ret = avcodec.avcodec_receive_packet(this.audio_c, this.audio_pkt);
            if (ret == avutil.AVERROR_EAGAIN() || ret == avutil.AVERROR_EOF()) {
                avcodec.av_packet_unref(this.audio_pkt);
                break;
            }
            if (ret < 0) {
                avcodec.av_packet_unref(this.audio_pkt);
                throw new Exception("avcodec_receive_packet() error " + ret + ": Error during audio encoding.");
            }
            gotAudioPacket = true;
            avcodec.av_packet_rescale_ts(this.audio_pkt, this.audio_c.time_base(), this.audio_st.time_base());
            this.audio_pkt.flags(this.audio_pkt.flags() | 1);
            this.audio_pkt.stream_index(this.audio_st.index());
            this.writePacket(this.audio_pkt);
            if (frame != null || this.wrote_samples) continue;
            break;
        }
        return gotAudioPacket;
    }

    private void writePacket(AVPacket avPacket) throws Exception {
        int ret = avformat.av_interleaved_write_frame(this.oc, avPacket);
        if (ret < 0) {
            avcodec.av_packet_unref(avPacket);
            throw new Exception("av_interleaved_write_frame() error " + ret + " while writing interleaved packet.");
        }
    }

    static {
        try {
            FlashbackFFmpegFrameRecorder.tryLoad();
        }
        catch (Exception exception) {
            // empty catch block
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

