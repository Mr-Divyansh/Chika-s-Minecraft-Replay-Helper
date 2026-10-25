/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVHWAccel
extends Pointer {
    public AVHWAccel() {
        super((Pointer)null);
        this.allocate();
    }

    public AVHWAccel(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVHWAccel(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVHWAccel position(long position) {
        return (AVHWAccel)super.position(position);
    }

    public AVHWAccel getPointer(long i) {
        return (AVHWAccel)new AVHWAccel(this).offsetAddress(i);
    }

    @Cast(value={"const char*"})
    public native BytePointer name();

    public native AVHWAccel name(BytePointer var1);

    @Cast(value={"AVMediaType"})
    public native int type();

    public native AVHWAccel type(int var1);

    @Cast(value={"AVCodecID"})
    public native int id();

    public native AVHWAccel id(int var1);

    @Cast(value={"AVPixelFormat"})
    public native int pix_fmt();

    public native AVHWAccel pix_fmt(int var1);

    public native int capabilities();

    public native AVHWAccel capabilities(int var1);

    static {
        Loader.load();
    }
}

