/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.avcodec.AVProfile;
import org.bytedeco.ffmpeg.avutil.AVChannelLayout;
import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVCodec
extends Pointer {
    public AVCodec() {
        super((Pointer)null);
        this.allocate();
    }

    public AVCodec(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVCodec(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVCodec position(long position) {
        return (AVCodec)super.position(position);
    }

    public AVCodec getPointer(long i) {
        return (AVCodec)new AVCodec(this).offsetAddress(i);
    }

    @Cast(value={"const char*"})
    public native BytePointer name();

    public native AVCodec name(BytePointer var1);

    @Cast(value={"const char*"})
    public native BytePointer long_name();

    public native AVCodec long_name(BytePointer var1);

    @Cast(value={"AVMediaType"})
    public native int type();

    public native AVCodec type(int var1);

    @Cast(value={"AVCodecID"})
    public native int id();

    public native AVCodec id(int var1);

    public native int capabilities();

    public native AVCodec capabilities(int var1);

    @Cast(value={"uint8_t"})
    public native byte max_lowres();

    public native AVCodec max_lowres(byte var1);

    @Const
    @Deprecated
    public native AVRational supported_framerates();

    public native AVCodec supported_framerates(AVRational var1);

    @Cast(value={"const AVPixelFormat*"})
    @Deprecated
    public native IntPointer pix_fmts();

    public native AVCodec pix_fmts(IntPointer var1);

    @Const
    @Deprecated
    public native IntPointer supported_samplerates();

    public native AVCodec supported_samplerates(IntPointer var1);

    @Cast(value={"const AVSampleFormat*"})
    @Deprecated
    public native IntPointer sample_fmts();

    public native AVCodec sample_fmts(IntPointer var1);

    @Const
    public native AVClass priv_class();

    public native AVCodec priv_class(AVClass var1);

    @Const
    public native AVProfile profiles();

    public native AVCodec profiles(AVProfile var1);

    @Cast(value={"const char*"})
    public native BytePointer wrapper_name();

    public native AVCodec wrapper_name(BytePointer var1);

    @Const
    @Deprecated
    public native AVChannelLayout ch_layouts();

    public native AVCodec ch_layouts(AVChannelLayout var1);

    static {
        Loader.load();
    }
}

