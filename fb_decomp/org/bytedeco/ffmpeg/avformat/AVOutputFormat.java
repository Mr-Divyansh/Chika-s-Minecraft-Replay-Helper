/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.avformat.AVCodecTag;
import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVOutputFormat
extends Pointer {
    public AVOutputFormat() {
        super((Pointer)null);
        this.allocate();
    }

    public AVOutputFormat(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVOutputFormat(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVOutputFormat position(long position) {
        return (AVOutputFormat)super.position(position);
    }

    public AVOutputFormat getPointer(long i) {
        return (AVOutputFormat)new AVOutputFormat(this).offsetAddress(i);
    }

    @Cast(value={"const char*"})
    public native BytePointer name();

    public native AVOutputFormat name(BytePointer var1);

    @Cast(value={"const char*"})
    public native BytePointer long_name();

    public native AVOutputFormat long_name(BytePointer var1);

    @Cast(value={"const char*"})
    public native BytePointer mime_type();

    public native AVOutputFormat mime_type(BytePointer var1);

    @Cast(value={"const char*"})
    public native BytePointer extensions();

    public native AVOutputFormat extensions(BytePointer var1);

    @Cast(value={"AVCodecID"})
    public native int audio_codec();

    public native AVOutputFormat audio_codec(int var1);

    @Cast(value={"AVCodecID"})
    public native int video_codec();

    public native AVOutputFormat video_codec(int var1);

    @Cast(value={"AVCodecID"})
    public native int subtitle_codec();

    public native AVOutputFormat subtitle_codec(int var1);

    public native int flags();

    public native AVOutputFormat flags(int var1);

    @MemberGetter
    @Const
    public native AVCodecTag codec_tag(int var1);

    @MemberGetter
    @Cast(value={"const AVCodecTag*const*"})
    public native PointerPointer codec_tag();

    @Const
    public native AVClass priv_class();

    public native AVOutputFormat priv_class(AVClass var1);

    static {
        Loader.load();
    }
}

