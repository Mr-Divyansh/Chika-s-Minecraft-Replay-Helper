/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.avcodec.AVCodecParameters;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVStream
extends Pointer {
    public static final int AVSTREAM_EVENT_FLAG_METADATA_UPDATED = 1;
    public static final int AVSTREAM_EVENT_FLAG_NEW_PACKETS = 2;

    public AVStream() {
        super((Pointer)null);
        this.allocate();
    }

    public AVStream(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVStream(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVStream position(long position) {
        return (AVStream)super.position(position);
    }

    public AVStream getPointer(long i) {
        return (AVStream)new AVStream(this).offsetAddress(i);
    }

    @Const
    public native AVClass av_class();

    public native AVStream av_class(AVClass var1);

    public native int index();

    public native AVStream index(int var1);

    public native int id();

    public native AVStream id(int var1);

    public native AVCodecParameters codecpar();

    public native AVStream codecpar(AVCodecParameters var1);

    public native Pointer priv_data();

    public native AVStream priv_data(Pointer var1);

    @ByRef
    public native AVRational time_base();

    public native AVStream time_base(AVRational var1);

    @Cast(value={"int64_t"})
    public native long start_time();

    public native AVStream start_time(long var1);

    @Cast(value={"int64_t"})
    public native long duration();

    public native AVStream duration(long var1);

    @Cast(value={"int64_t"})
    public native long nb_frames();

    public native AVStream nb_frames(long var1);

    public native int disposition();

    public native AVStream disposition(int var1);

    @Cast(value={"AVDiscard"})
    public native int discard();

    public native AVStream discard(int var1);

    @ByRef
    public native AVRational sample_aspect_ratio();

    public native AVStream sample_aspect_ratio(AVRational var1);

    public native AVDictionary metadata();

    public native AVStream metadata(AVDictionary var1);

    @ByRef
    public native AVRational avg_frame_rate();

    public native AVStream avg_frame_rate(AVRational var1);

    @ByRef
    public native AVPacket attached_pic();

    public native AVStream attached_pic(AVPacket var1);

    public native int event_flags();

    public native AVStream event_flags(int var1);

    @ByRef
    public native AVRational r_frame_rate();

    public native AVStream r_frame_rate(AVRational var1);

    public native int pts_wrap_bits();

    public native AVStream pts_wrap_bits(int var1);

    static {
        Loader.load();
    }
}

