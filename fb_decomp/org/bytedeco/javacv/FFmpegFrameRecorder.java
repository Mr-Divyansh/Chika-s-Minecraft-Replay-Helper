/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Collections;
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
import org.bytedeco.ffmpeg.avformat.Seek_Pointer_long_int;
import org.bytedeco.ffmpeg.avformat.Write_packet_Pointer_BytePointer_int;
import org.bytedeco.ffmpeg.avutil.AVChannelLayout;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
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
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.PointerScope;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacv.FFmpegLogCallback;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.Seekable;

public class FFmpegFrameRecorder
extends FrameRecorder {
    private static Exception loadingException = null;
    static Map<Pointer, OutputStream> outputStreams;
    static WriteCallback writeCallback;
    static SeekCallback seekCallback;
    private OutputStream outputStream;
    private boolean closeOutputStream;
    private AVIOContext avio;
    private String filename;
    private AVFrame picture;
    private AVFrame tmp_picture;
    private BytePointer picture_buf;
    private BytePointer video_outbuf;
    private int video_outbuf_size;
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
    private SwsContext img_convert_ctx;
    private SwrContext samples_convert_ctx;
    private int samples_channels;
    private int samples_format;
    private int samples_rate;
    private PointerPointer plane_ptr;
    private PointerPointer plane_ptr2;
    private AVPacket video_pkt;
    private AVPacket audio_pkt;
    private int[] got_video_packet;
    private int[] got_audio_packet;
    private boolean wrote_samples = false;
    private AVFormatContext ifmt_ctx;
    private IntPointer display_matrix;
    private AVChannelLayout default_layout;
    private volatile boolean started = false;

    public static FFmpegFrameRecorder createDefault(File f, int w, int h) throws Exception {
        return new FFmpegFrameRecorder(f, w, h);
    }

    public static FFmpegFrameRecorder createDefault(String f, int w, int h) throws Exception {
        return new FFmpegFrameRecorder(f, w, h);
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
            loadingException = new Exception("Failed to load " + FFmpegFrameRecorder.class, t);
            throw loadingException;
        }
    }

    public FFmpegFrameRecorder(URL url, int audioChannels) {
        this(url.toString(), 0, 0, audioChannels);
    }

    public FFmpegFrameRecorder(File file, int audioChannels) {
        this(file, 0, 0, audioChannels);
    }

    public FFmpegFrameRecorder(String filename, int audioChannels) {
        this(filename, 0, 0, audioChannels);
    }

    public FFmpegFrameRecorder(URL url, int imageWidth, int imageHeight) {
        this(url.toString(), imageWidth, imageHeight, 0);
    }

    public FFmpegFrameRecorder(File file, int imageWidth, int imageHeight) {
        this(file, imageWidth, imageHeight, 0);
    }

    public FFmpegFrameRecorder(String filename, int imageWidth, int imageHeight) {
        this(filename, imageWidth, imageHeight, 0);
    }

    public FFmpegFrameRecorder(URL url, int imageWidth, int imageHeight, int audioChannels) {
        this(url.toString(), imageWidth, imageHeight, audioChannels);
    }

    public FFmpegFrameRecorder(File file, int imageWidth, int imageHeight, int audioChannels) {
        this(file.getAbsolutePath(), imageWidth, imageHeight, audioChannels);
    }

    public FFmpegFrameRecorder(String filename, int imageWidth, int imageHeight, int audioChannels) {
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
        this.interleaved = true;
    }

    public FFmpegFrameRecorder(OutputStream outputStream, int audioChannels) {
        this(outputStream.toString(), audioChannels);
        this.outputStream = outputStream;
        this.closeOutputStream = true;
    }

    public FFmpegFrameRecorder(OutputStream outputStream, int imageWidth, int imageHeight) {
        this(outputStream.toString(), imageWidth, imageHeight);
        this.outputStream = outputStream;
        this.closeOutputStream = true;
    }

    public FFmpegFrameRecorder(OutputStream outputStream, int imageWidth, int imageHeight, int audioChannels) {
        this(outputStream.toString(), imageWidth, imageHeight, audioChannels);
        this.outputStream = outputStream;
        this.closeOutputStream = true;
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
        if (this.display_matrix != null) {
            this.display_matrix.releaseReference();
        }
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
        if (this.picture_buf != null) {
            avutil.av_free(this.picture_buf);
            this.picture_buf = null;
        }
        if (this.picture != null) {
            avutil.av_frame_free(this.picture);
            this.picture = null;
        }
        if (this.tmp_picture != null) {
            avutil.av_frame_free(this.tmp_picture);
            this.tmp_picture = null;
        }
        if (this.video_outbuf != null) {
            avutil.av_free(this.video_outbuf);
            this.video_outbuf = null;
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
        AVFormatContext outputStreamKey = this.oc;
        if (this.oc != null && !this.oc.isNull()) {
            if (this.outputStream == null && (this.oformat.flags() & 1) == 0) {
                avformat.avio_close(this.oc.pb());
            }
            avformat.avformat_free_context(this.oc);
            this.oc = null;
        }
        if (this.img_convert_ctx != null) {
            swscale.sws_freeContext(this.img_convert_ctx);
            this.img_convert_ctx = null;
        }
        if (this.samples_convert_ctx != null) {
            swresample.swr_free(this.samples_convert_ctx);
            this.samples_convert_ctx.releaseReference();
            this.samples_convert_ctx = null;
        }
        if (this.outputStream != null) {
            try {
                if (this.closeOutputStream) {
                    this.outputStream.close();
                }
            }
            catch (IOException ex) {
                throw new Exception("Error on OutputStream.close(): ", ex);
            }
            finally {
                this.outputStream = null;
                outputStreams.remove(outputStreamKey);
                if (this.avio != null) {
                    if (this.avio.buffer() != null) {
                        avutil.av_free(this.avio.buffer());
                        this.avio.buffer(null);
                    }
                    avutil.av_free(this.avio);
                    this.avio = null;
                }
            }
        }
        Pointer.trimMemory();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.release();
    }

    public boolean isCloseOutputStream() {
        return this.closeOutputStream;
    }

    public void setCloseOutputStream(boolean closeOutputStream) {
        this.closeOutputStream = closeOutputStream;
    }

    public void setDisplayRotation(double angle) {
        if (this.display_matrix == null) {
            this.display_matrix = (IntPointer)new IntPointer(9L).retainReference();
        }
        avutil.av_display_rotation_set(this.display_matrix, -angle);
        this.setVideoSideData("Display Matrix", this.display_matrix.asByteBuffer());
    }

    @Override
    public int getFrameNumber() {
        return this.picture == null ? super.getFrameNumber() : (int)this.picture.pts();
    }

    @Override
    public void setFrameNumber(int frameNumber) {
        if (this.picture == null) {
            super.setFrameNumber(frameNumber);
        } else {
            this.picture.pts(frameNumber);
        }
    }

    @Override
    public long getTimestamp() {
        return Math.round((double)((long)this.getFrameNumber() * 1000000L) / this.getFrameRate());
    }

    @Override
    public void setTimestamp(long timestamp) {
        this.setFrameNumber((int)Math.round((double)timestamp * this.getFrameRate() / 1000000.0));
    }

    public void start(AVFormatContext inputFormatContext) throws Exception {
        this.ifmt_ctx = inputFormatContext;
        this.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void start() throws Exception {
        Class<avcodec> clazz = avcodec.class;
        synchronized (avcodec.class) {
            this.startUnsafe();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    public synchronized void startUnsafe() throws Exception {
        try (PointerScope scope = new PointerScope();){
            int ret;
            if (this.oc != null && !this.oc.isNull()) {
                throw new Exception("start() has already been called: Call stop() before calling start() again.");
            }
            this.picture = null;
            this.tmp_picture = null;
            this.picture_buf = null;
            this.frame = null;
            this.video_outbuf = null;
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
            this.got_video_packet = new int[1];
            this.got_audio_packet = new int[1];
            this.default_layout = (AVChannelLayout)new AVChannelLayout().retainReference();
            String format_name = this.format == null || this.format.length() == 0 ? null : this.format;
            this.oformat = avformat.av_guess_format(format_name, this.filename, null);
            if (this.oformat == null) {
                int proto = this.filename.indexOf("://");
                if (proto > 0) {
                    format_name = this.filename.substring(0, proto);
                }
                if ((this.oformat = avformat.av_guess_format(format_name, this.filename, null)) == null) {
                    throw new Exception("av_guess_format() error: Could not guess output format for \"" + this.filename + "\" and " + this.format + " format.");
                }
            }
            if (avformat.avformat_alloc_output_context2(this.oc, null, format_name = this.oformat.name().getString(), this.filename) < 0) {
                throw new Exception("avformat_alloc_context2() error:\tCould not allocate format context");
            }
            if (this.outputStream != null) {
                this.avio = avformat.avio_alloc_context(new BytePointer(avutil.av_malloc(4096L)), 4096, 1, (Pointer)this.oc, null, writeCallback, (Seek_Pointer_long_int)(this.outputStream instanceof Seekable ? seekCallback : null));
                this.oc.pb(this.avio);
                this.filename = this.outputStream.toString();
                outputStreams.put(this.oc, this.outputStream);
            }
            this.oc.oformat(this.oformat);
            this.oc.url(new BytePointer(avutil.av_malloc(this.filename.getBytes().length + 1)).putString(this.filename));
            this.oc.max_delay(this.maxDelay);
            AVStream inpVideoStream = null;
            AVStream inpAudioStream = null;
            if (this.ifmt_ctx != null) {
                for (int idx = 0; idx < this.ifmt_ctx.nb_streams(); ++idx) {
                    AVStream inputStream = this.ifmt_ctx.streams(idx);
                    if (inputStream.codecpar().codec_type() == 0) {
                        inpVideoStream = inputStream;
                        this.videoCodec = inpVideoStream.codecpar().codec_id();
                        if ((long)inpVideoStream.r_frame_rate().num() == avutil.AV_NOPTS_VALUE || inpVideoStream.r_frame_rate().den() == 0) continue;
                        this.frameRate = (double)inpVideoStream.r_frame_rate().num() * 1.0 / (double)inpVideoStream.r_frame_rate().den();
                        continue;
                    }
                    if (inputStream.codecpar().codec_type() != 1) continue;
                    inpAudioStream = inputStream;
                    this.audioCodec = inpAudioStream.codecpar().codec_id();
                }
            }
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
                if (inpVideoStream != null) {
                    ret = avcodec.avcodec_parameters_copy(this.video_st.codecpar(), inpVideoStream.codecpar());
                    if (ret < 0) {
                        this.releaseUnsafe();
                        throw new Exception("avcodec_parameters_copy() error " + ret + ": Failed to copy video stream codec parameters from input to output");
                    }
                    this.videoBitrate = (int)inpVideoStream.codecpar().bit_rate();
                    this.pixelFormat = inpVideoStream.codecpar().format();
                    this.aspectRatio = (double)inpVideoStream.codecpar().sample_aspect_ratio().num() * 1.0 / (double)inpVideoStream.codecpar().sample_aspect_ratio().den();
                    this.video_c.codec_tag(0);
                }
                this.video_c.codec_id(this.video_codec.id());
                this.video_c.codec_type(0);
                this.video_c.bit_rate(this.videoBitrate);
                if (this.imageWidth % 2 == 1) {
                    int roundedWidth = this.imageWidth + 1;
                    this.imageHeight = (roundedWidth * this.imageHeight + this.imageWidth / 2) / this.imageWidth;
                    this.imageWidth = roundedWidth;
                }
                this.video_c.width(this.imageWidth);
                this.video_c.height(this.imageHeight);
                if (this.aspectRatio > 0.0) {
                    AVRational r = avutil.av_d2q(this.aspectRatio, 255);
                    this.video_c.sample_aspect_ratio(r);
                    this.video_st.sample_aspect_ratio(r);
                }
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
                if (this.video_c.codec_id() == 2) {
                    this.video_c.max_b_frames(2);
                } else if (this.video_c.codec_id() == 1) {
                    this.video_c.mb_decision(2);
                } else if (this.video_c.codec_id() == 4) {
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
                if ((this.audio_codec = avcodec.avcodec_find_encoder_by_name(this.audioCodecName)) == null && (this.audio_codec = avcodec.avcodec_find_encoder(this.audioCodec)) == null) {
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
                if (inpAudioStream != null && this.audioChannels > 0) {
                    ret = avcodec.avcodec_parameters_copy(this.audio_st.codecpar(), inpAudioStream.codecpar());
                    if (ret < 0) {
                        throw new Exception("avcodec_parameters_copy() error " + ret + ": Failed to copy audio stream codec parameters from input to output");
                    }
                    this.audioBitrate = (int)inpAudioStream.codecpar().bit_rate();
                    this.sampleRate = inpAudioStream.codecpar().sample_rate();
                    this.audioChannels = inpAudioStream.codecpar().ch_layout().nb_channels();
                    this.sampleFormat = inpAudioStream.codecpar().format();
                    this.audio_c.codec_tag(0);
                    this.audio_st.duration(inpAudioStream.duration());
                    this.audio_st.time_base().num(inpAudioStream.time_base().num());
                    this.audio_st.time_base().den(inpAudioStream.time_base().den());
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
            if (this.video_st != null && inpVideoStream == null) {
                AVDictionary options = new AVDictionary(null);
                if (this.videoQuality >= 0.0) {
                    avutil.av_dict_set(options, "crf", "" + this.videoQuality, 0);
                }
                for (Map.Entry e : this.videoOptions.entrySet()) {
                    avutil.av_dict_set(options, (String)e.getKey(), (String)e.getValue(), 0);
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
                this.video_outbuf = null;
                this.picture = avutil.av_frame_alloc();
                if (this.picture == null) {
                    this.releaseUnsafe();
                    throw new Exception("av_frame_alloc() error: Could not allocate picture.");
                }
                this.picture.pts(0L);
                int size = avutil.av_image_get_buffer_size(this.video_c.pix_fmt(), this.video_c.width(), this.video_c.height(), 1);
                this.picture_buf = new BytePointer(avutil.av_malloc(size));
                if (this.picture_buf.isNull()) {
                    this.releaseUnsafe();
                    throw new Exception("av_malloc() error: Could not allocate picture buffer.");
                }
                this.tmp_picture = avutil.av_frame_alloc();
                if (this.tmp_picture == null) {
                    this.releaseUnsafe();
                    throw new Exception("av_frame_alloc() error: Could not allocate temporary picture.");
                }
                ret = avcodec.avcodec_parameters_from_context(this.video_st.codecpar(), this.video_c);
                if (ret < 0) {
                    this.releaseUnsafe();
                    throw new Exception("avcodec_parameters_from_context() error " + ret + ": Could not copy the video stream parameters.");
                }
                AVDictionary metadata = new AVDictionary(null);
                for (Map.Entry e : this.videoMetadata.entrySet()) {
                    avutil.av_dict_set(metadata, new BytePointer((String)e.getKey(), this.charset), new BytePointer((String)e.getValue(), this.charset), 0);
                }
                this.video_st.metadata(metadata);
                for (Map.Entry e : this.videoSideData.entrySet()) {
                    int type = -1;
                    for (int i = 0; i < 41; ++i) {
                        BytePointer s = avcodec.av_packet_side_data_name(i);
                        if (s == null || s.isNull() || !((String)e.getKey()).equals(s.getString())) continue;
                        type = i;
                        break;
                    }
                    Pointer p = new Pointer((Buffer)e.getValue());
                    AVPacketSideData coded_side_data = this.video_st.codecpar().coded_side_data();
                    int[] nb_coded_side_data = new int[]{this.video_st.codecpar().nb_coded_side_data()};
                    AVPacketSideData b = avcodec.av_packet_side_data_new(coded_side_data, nb_coded_side_data, type, p.capacity(), 0);
                    this.video_st.codecpar().coded_side_data(coded_side_data);
                    this.video_st.codecpar().nb_coded_side_data(nb_coded_side_data[0]);
                    if (b == null || b.isNull() || b.data() == null || b.data().isNull()) continue;
                    b.data().capacity(p.capacity()).put(p);
                }
            }
            if (this.audio_st != null && inpAudioStream == null) {
                AVDictionary options = new AVDictionary(null);
                if (this.audioQuality >= 0.0) {
                    avutil.av_dict_set(options, "crf", "" + this.audioQuality, 0);
                }
                for (Map.Entry e : this.audioOptions.entrySet()) {
                    avutil.av_dict_set(options, (String)e.getKey(), (String)e.getValue(), 0);
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
                for (int i = 0; i < this.samples_out.length; ++i) {
                    this.samples_out[i] = new BytePointer(avutil.av_malloc(data_size)).capacity(data_size);
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
                AVDictionary metadata = new AVDictionary(null);
                for (Map.Entry e : this.audioMetadata.entrySet()) {
                    avutil.av_dict_set(metadata, new BytePointer((String)e.getKey(), this.charset), new BytePointer((String)e.getValue(), this.charset), 0);
                }
                this.audio_st.metadata(metadata);
                for (Map.Entry e : this.audioSideData.entrySet()) {
                    int type = -1;
                    for (int i = 0; i < 41; ++i) {
                        BytePointer s = avcodec.av_packet_side_data_name(i);
                        if (s == null || s.isNull() || !((String)e.getKey()).equals(s.getString())) continue;
                        type = i;
                        break;
                    }
                    Pointer p = new Pointer((Buffer)e.getValue());
                    AVPacketSideData coded_side_data = this.audio_st.codecpar().coded_side_data();
                    int[] nb_coded_side_data = new int[]{this.audio_st.codecpar().nb_coded_side_data()};
                    AVPacketSideData b = avcodec.av_packet_side_data_new(coded_side_data, nb_coded_side_data, type, p.capacity(), 0);
                    this.audio_st.codecpar().coded_side_data(coded_side_data);
                    this.audio_st.codecpar().nb_coded_side_data(nb_coded_side_data[0]);
                    if (b == null || b.isNull() || b.data() == null || b.data().isNull()) continue;
                    b.data().capacity(p.capacity()).put(p);
                }
            }
            AVDictionary options = new AVDictionary(null);
            for (Map.Entry e : this.options.entrySet()) {
                avutil.av_dict_set(options, (String)e.getKey(), (String)e.getValue(), 0);
            }
            if (this.outputStream == null && (this.oformat.flags() & 1) == 0) {
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
            for (Map.Entry e : this.metadata.entrySet()) {
                avutil.av_dict_set(metadata, new BytePointer((String)e.getKey(), this.charset), new BytePointer((String)e.getValue(), this.charset), 0);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized void flush() throws Exception {
        AVFormatContext aVFormatContext = this.oc;
        synchronized (aVFormatContext) {
            while (this.video_st != null && this.ifmt_ctx == null && this.recordImage(0, 0, 0, 0, 0, -1, null)) {
            }
            while (this.audio_st != null && this.ifmt_ctx == null && this.recordSamples(0, 0, (Buffer[])null)) {
            }
            if (this.interleaved && (this.video_st != null || this.audio_st != null)) {
                avformat.av_interleaved_write_frame(this.oc, null);
            } else {
                avformat.av_write_frame(this.oc, null);
            }
        }
    }

    @Override
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

    @Override
    public void record(Frame frame) throws Exception {
        this.record(frame, frame != null && frame.opaque instanceof AVFrame ? ((AVFrame)frame.opaque).format() : -1);
    }

    public synchronized void record(Frame frame, int pixelFormat) throws Exception {
        if (frame == null || frame.image == null && frame.samples == null && frame.data == null) {
            this.recordImage(0, 0, 0, 0, 0, pixelFormat, null);
        } else {
            if (frame.image != null) {
                frame.keyFrame = this.recordImage(frame.imageWidth, frame.imageHeight, frame.imageDepth, frame.imageChannels, frame.imageStride, pixelFormat, frame.image);
            }
            if (frame.samples != null) {
                frame.keyFrame = this.recordSamples(frame.sampleRate, frame.audioChannels, frame.samples);
            }
        }
    }

    public synchronized boolean recordImage(int width, int height, int depth, int channels, int stride, int pixelFormat, Buffer ... image) throws Exception {
        try (PointerScope scope = new PointerScope();){
            if (this.video_st == null) {
                throw new Exception("No video output stream (Is imageWidth > 0 && imageHeight > 0 and has start() been called?)");
            }
            if (!this.started) {
                throw new Exception("start() was not called successfully!");
            }
            if (image != null && image.length != 0) {
                BytePointer data;
                int step = stride * Math.abs(depth) / 8;
                BytePointer bytePointer = data = image[0] instanceof ByteBuffer ? new BytePointer((ByteBuffer)image[0]).position(0L) : new BytePointer((Pointer)new Pointer(image[0]).position(0L));
                if (pixelFormat == -1) {
                    if ((depth == 8 || depth == -8) && channels == 3) {
                        pixelFormat = 3;
                    } else if ((depth == 8 || depth == -8) && channels == 1) {
                        pixelFormat = 8;
                    } else if ((depth == 16 || depth == -16) && channels == 1) {
                        pixelFormat = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN) ? 29 : 30;
                    } else if ((depth == 8 || depth == -8) && channels == 4) {
                        pixelFormat = 26;
                    } else if ((depth == 8 || depth == -8) && channels == 2) {
                        pixelFormat = 24;
                    } else {
                        throw new Exception("Could not guess pixel format of image: depth=" + depth + ", channels=" + channels);
                    }
                }
                if (pixelFormat == 24) {
                    step = width;
                }
                if (this.video_c.pix_fmt() != pixelFormat || this.video_c.width() != width || this.video_c.height() != height) {
                    this.img_convert_ctx = swscale.sws_getCachedContext(this.img_convert_ctx, width, height, pixelFormat, this.video_c.width(), this.video_c.height(), this.video_c.pix_fmt(), this.imageScalingFlags != 0 ? this.imageScalingFlags : 2, null, null, (DoublePointer)null);
                    if (this.img_convert_ctx == null) {
                        throw new Exception("sws_getCachedContext() error: Cannot initialize the conversion context.");
                    }
                    avutil.av_image_fill_arrays(new PointerPointer(this.tmp_picture), this.tmp_picture.linesize(), data, pixelFormat, width, height, 1);
                    avutil.av_image_fill_arrays(new PointerPointer(this.picture), this.picture.linesize(), this.picture_buf, this.video_c.pix_fmt(), this.video_c.width(), this.video_c.height(), 1);
                    this.tmp_picture.linesize(0, step);
                    this.tmp_picture.format(pixelFormat);
                    this.tmp_picture.width(width);
                    this.tmp_picture.height(height);
                    this.picture.format(this.video_c.pix_fmt());
                    this.picture.width(this.video_c.width());
                    this.picture.height(this.video_c.height());
                    swscale.sws_scale(this.img_convert_ctx, new PointerPointer(this.tmp_picture), this.tmp_picture.linesize(), 0, height, new PointerPointer(this.picture), this.picture.linesize());
                } else {
                    avutil.av_image_fill_arrays(new PointerPointer(this.picture), this.picture.linesize(), data, pixelFormat, width, height, 1);
                    this.picture.linesize(0, step);
                    this.picture.format(pixelFormat);
                    this.picture.width(width);
                    this.picture.height(height);
                }
            }
            this.picture.quality(this.video_c.global_quality());
            int ret = avcodec.avcodec_send_frame(this.video_c, image == null || image.length == 0 ? null : this.picture);
            if (ret < 0 && image != null && image.length != 0) {
                throw new Exception("avcodec_send_frame() error " + ret + ": Error sending a video frame for encoding.");
            }
            this.picture.pts(this.picture.pts() + 1L);
            this.got_video_packet[0] = 0;
            while (ret >= 0) {
                avcodec.av_new_packet(this.video_pkt, this.video_outbuf_size);
                ret = avcodec.avcodec_receive_packet(this.video_c, this.video_pkt);
                if (ret == avutil.AVERROR_EAGAIN() || ret == avutil.AVERROR_EOF()) {
                    avcodec.av_packet_unref(this.video_pkt);
                    break;
                }
                if (ret < 0) {
                    avcodec.av_packet_unref(this.video_pkt);
                    throw new Exception("avcodec_receive_packet() error " + ret + ": Error during video encoding.");
                }
                this.got_video_packet[0] = 1;
                if (this.video_pkt.pts() != avutil.AV_NOPTS_VALUE) {
                    this.video_pkt.pts(avutil.av_rescale_q(this.video_pkt.pts(), this.video_c.time_base(), this.video_st.time_base()));
                }
                if (this.video_pkt.dts() != avutil.AV_NOPTS_VALUE) {
                    this.video_pkt.dts(avutil.av_rescale_q(this.video_pkt.dts(), this.video_c.time_base(), this.video_st.time_base()));
                }
                this.video_pkt.stream_index(this.video_st.index());
                this.writePacket(0, this.video_pkt);
            }
            boolean bl = image != null ? (this.video_pkt.flags() & 1) != 0 : this.got_video_packet[0] != 0;
            return bl;
        }
    }

    public boolean recordSamples(Buffer ... samples) throws Exception {
        return this.recordSamples(0, 0, samples);
    }

    public synchronized boolean recordSamples(int sampleRate, int audioChannels, Buffer ... samples) throws Exception {
        try (PointerScope scope = new PointerScope();){
            int ret;
            boolean formatChanged;
            Buffer b;
            int i;
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
            if (sampleRate <= 0) {
                sampleRate = this.audio_c.sample_rate();
            }
            if (audioChannels <= 0) {
                audioChannels = this.audio_c.ch_layout().nb_channels();
            }
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
                throw new Exception("Audio samples Buffer has unsupported type: " + samples);
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
            for (int i2 = 0; samples != null && i2 < samples.length; ++i2) {
                ((Pointer)this.samples_in[i2].position(this.samples_in[i2].position() * (long)inputDepth)).limit((this.samples_in[i2].position() + (long)inputSize) * (long)inputDepth);
            }
            while (true) {
                int i3;
                int inputCount = (int)Math.min(samples != null ? (this.samples_in[0].limit() - this.samples_in[0].position()) / (long)(inputChannels * inputDepth) : 0L, Integer.MAX_VALUE);
                int outputCount = (int)Math.min((this.samples_out[0].limit() - this.samples_out[0].position()) / (long)(outputChannels * outputDepth), Integer.MAX_VALUE);
                inputCount = Math.min(inputCount, (outputCount * sampleRate + this.audio_c.sample_rate() - 1) / this.audio_c.sample_rate());
                for (i3 = 0; samples != null && i3 < samples.length; ++i3) {
                    this.plane_ptr.put(i3, this.samples_in[i3]);
                }
                for (i3 = 0; i3 < this.samples_out.length; ++i3) {
                    this.plane_ptr2.put(i3, this.samples_out[i3]);
                }
                if (samples == null && inputCount == 0 && this.plane_ptr != null) {
                    this.plane_ptr.releaseReference();
                    this.plane_ptr = null;
                }
                if ((ret = swresample.swr_convert(this.samples_convert_ctx, this.plane_ptr2, outputCount, this.plane_ptr, inputCount)) < 0) {
                    throw new Exception("swr_convert() error " + ret + ": Cannot convert audio samples.");
                }
                if (ret == 0 && inputCount == 0) break;
                for (i3 = 0; samples != null && i3 < samples.length; ++i3) {
                    this.samples_in[i3].position(this.samples_in[i3].position() + (long)(inputCount * inputChannels * inputDepth));
                }
                for (i3 = 0; i3 < this.samples_out.length; ++i3) {
                    this.samples_out[i3].position(this.samples_out[i3].position() + (long)(ret * outputChannels * outputDepth));
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
        this.got_audio_packet[0] = 0;
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
            this.got_audio_packet[0] = 1;
            if (this.audio_pkt.pts() != avutil.AV_NOPTS_VALUE) {
                this.audio_pkt.pts(avutil.av_rescale_q(this.audio_pkt.pts(), this.audio_c.time_base(), this.audio_st.time_base()));
            }
            if (this.audio_pkt.dts() != avutil.AV_NOPTS_VALUE) {
                this.audio_pkt.dts(avutil.av_rescale_q(this.audio_pkt.dts(), this.audio_c.time_base(), this.audio_st.time_base()));
            }
            this.audio_pkt.flags(this.audio_pkt.flags() | 1);
            this.audio_pkt.stream_index(this.audio_st.index());
            this.writePacket(1, this.audio_pkt);
            if (frame != null || this.wrote_samples) continue;
        }
        return this.got_audio_packet[0] != 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void writePacket(int mediaType, AVPacket avPacket) throws Exception {
        AVStream avStream;
        AVStream aVStream = mediaType == 0 ? this.video_st : (avStream = mediaType == 1 ? this.audio_st : null);
        String mediaTypeStr = mediaType == 0 ? "video" : (mediaType == 1 ? "audio" : "unsupported media stream type");
        AVFormatContext aVFormatContext = this.oc;
        synchronized (aVFormatContext) {
            if (this.interleaved && avStream != null) {
                int ret = avformat.av_interleaved_write_frame(this.oc, avPacket);
                if (ret < 0) {
                    avcodec.av_packet_unref(avPacket);
                    throw new Exception("av_interleaved_write_frame() error " + ret + " while writing interleaved " + mediaTypeStr + " packet.");
                }
            } else {
                int ret = avformat.av_write_frame(this.oc, avPacket);
                if (ret < 0) {
                    avcodec.av_packet_unref(avPacket);
                    throw new Exception("av_write_frame() error " + ret + " while writing " + mediaTypeStr + " packet.");
                }
            }
        }
        avcodec.av_packet_unref(avPacket);
    }

    public synchronized boolean recordPacket(AVPacket pkt) throws Exception {
        if (this.ifmt_ctx == null) {
            throw new Exception("No input format context (Has start(AVFormatContext) been called?)");
        }
        if (!this.started) {
            throw new Exception("start() was not called successfully!");
        }
        if (pkt == null) {
            return false;
        }
        AVStream in_stream = this.ifmt_ctx.streams(pkt.stream_index());
        pkt.pos(-1L);
        if (in_stream.codecpar().codec_type() == 0 && this.video_st != null) {
            pkt.stream_index(this.video_st.index());
            pkt.duration((int)avutil.av_rescale_q(pkt.duration(), in_stream.time_base(), this.video_st.time_base()));
            pkt.pts(avutil.av_rescale_q_rnd(pkt.pts(), in_stream.time_base(), this.video_st.time_base(), 8197));
            pkt.dts(avutil.av_rescale_q_rnd(pkt.dts(), in_stream.time_base(), this.video_st.time_base(), 8197));
            this.writePacket(0, pkt);
        } else if (in_stream.codecpar().codec_type() == 1 && this.audio_st != null && this.audioChannels > 0) {
            pkt.stream_index(this.audio_st.index());
            pkt.duration((int)avutil.av_rescale_q(pkt.duration(), in_stream.time_base(), this.audio_st.time_base()));
            pkt.pts(avutil.av_rescale_q_rnd(pkt.pts(), in_stream.time_base(), this.audio_st.time_base(), 8197));
            pkt.dts(avutil.av_rescale_q_rnd(pkt.dts(), in_stream.time_base(), this.audio_st.time_base(), 8197));
            this.writePacket(1, pkt);
        }
        return true;
    }

    static {
        try {
            FFmpegFrameRecorder.tryLoad();
        }
        catch (Exception exception) {
            // empty catch block
        }
        outputStreams = Collections.synchronizedMap(new HashMap());
        writeCallback = (WriteCallback)new WriteCallback().retainReference();
        seekCallback = (SeekCallback)new SeekCallback().retainReference();
    }

    static class SeekCallback
    extends Seek_Pointer_long_int {
        SeekCallback() {
        }

        @Override
        public long call(Pointer opaque, long offset, int whence) {
            try {
                OutputStream os = outputStreams.get(opaque);
                ((Seekable)((Object)os)).seek(offset, whence);
                return 0L;
            }
            catch (Throwable t) {
                System.err.println("Error on OutputStream.seek(): " + t);
                return -1L;
            }
        }
    }

    static class WriteCallback
    extends Write_packet_Pointer_BytePointer_int {
        WriteCallback() {
        }

        @Override
        public int call(Pointer opaque, BytePointer buf, int buf_size) {
            try {
                byte[] b = new byte[buf_size];
                OutputStream os = outputStreams.get(opaque);
                buf.get(b, 0, buf_size);
                os.write(b, 0, buf_size);
                return buf_size;
            }
            catch (Throwable t) {
                System.err.println("Error on OutputStream.write(): " + t);
                return -1;
            }
        }
    }

    public static class Exception
    extends FrameRecorder.Exception {
        public Exception(String message) {
            super(message + " (For more details, make sure FFmpegLogCallback.set() has been called.)");
        }

        public Exception(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

