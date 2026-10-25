/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.avcodec.AVProfile;
import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVCodecDescriptor
extends Pointer {
    public AVCodecDescriptor() {
        super((Pointer)null);
        this.allocate();
    }

    public AVCodecDescriptor(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVCodecDescriptor(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVCodecDescriptor position(long position) {
        return (AVCodecDescriptor)super.position(position);
    }

    public AVCodecDescriptor getPointer(long i) {
        return (AVCodecDescriptor)new AVCodecDescriptor(this).offsetAddress(i);
    }

    @Cast(value={"AVCodecID"})
    public native int id();

    public native AVCodecDescriptor id(int var1);

    @Cast(value={"AVMediaType"})
    public native int type();

    public native AVCodecDescriptor type(int var1);

    @Cast(value={"const char*"})
    public native BytePointer name();

    public native AVCodecDescriptor name(BytePointer var1);

    @Cast(value={"const char*"})
    public native BytePointer long_name();

    public native AVCodecDescriptor long_name(BytePointer var1);

    public native int props();

    public native AVCodecDescriptor props(int var1);

    @MemberGetter
    @Cast(value={"const char*"})
    public native BytePointer mime_types(int var1);

    @MemberGetter
    @Cast(value={"const char*const*"})
    public native PointerPointer mime_types();

    @Const
    public native AVProfile profiles();

    public native AVCodecDescriptor profiles(AVProfile var1);

    static {
        Loader.load();
    }
}

