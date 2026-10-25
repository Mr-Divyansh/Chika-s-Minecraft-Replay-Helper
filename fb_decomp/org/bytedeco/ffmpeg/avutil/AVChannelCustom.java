/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVChannelCustom
extends Pointer {
    public AVChannelCustom() {
        super((Pointer)null);
        this.allocate();
    }

    public AVChannelCustom(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVChannelCustom(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVChannelCustom position(long position) {
        return (AVChannelCustom)super.position(position);
    }

    public AVChannelCustom getPointer(long i) {
        return (AVChannelCustom)new AVChannelCustom(this).offsetAddress(i);
    }

    @Cast(value={"AVChannel"})
    public native int id();

    public native AVChannelCustom id(int var1);

    @Cast(value={"char"})
    public native byte name(int var1);

    public native AVChannelCustom name(int var1, byte var2);

    @MemberGetter
    @Cast(value={"char*"})
    public native BytePointer name();

    public native Pointer opaque();

    public native AVChannelCustom opaque(Pointer var1);

    static {
        Loader.load();
    }
}

