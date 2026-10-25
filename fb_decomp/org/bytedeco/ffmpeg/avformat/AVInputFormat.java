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
public class AVInputFormat
extends Pointer {
    public AVInputFormat() {
        super((Pointer)null);
        this.allocate();
    }

    public AVInputFormat(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVInputFormat(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVInputFormat position(long position) {
        return (AVInputFormat)super.position(position);
    }

    public AVInputFormat getPointer(long i) {
        return (AVInputFormat)new AVInputFormat(this).offsetAddress(i);
    }

    @Cast(value={"const char*"})
    public native BytePointer name();

    public native AVInputFormat name(BytePointer var1);

    @Cast(value={"const char*"})
    public native BytePointer long_name();

    public native AVInputFormat long_name(BytePointer var1);

    public native int flags();

    public native AVInputFormat flags(int var1);

    @Cast(value={"const char*"})
    public native BytePointer extensions();

    public native AVInputFormat extensions(BytePointer var1);

    @MemberGetter
    @Const
    public native AVCodecTag codec_tag(int var1);

    @MemberGetter
    @Cast(value={"const AVCodecTag*const*"})
    public native PointerPointer codec_tag();

    @Const
    public native AVClass priv_class();

    public native AVInputFormat priv_class(AVClass var1);

    @Cast(value={"const char*"})
    public native BytePointer mime_type();

    public native AVInputFormat mime_type(BytePointer var1);

    static {
        Loader.load();
    }
}

