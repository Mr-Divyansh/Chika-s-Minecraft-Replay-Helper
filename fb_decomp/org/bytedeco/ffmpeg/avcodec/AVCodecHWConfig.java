/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVCodecHWConfig
extends Pointer {
    public AVCodecHWConfig() {
        super((Pointer)null);
        this.allocate();
    }

    public AVCodecHWConfig(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVCodecHWConfig(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVCodecHWConfig position(long position) {
        return (AVCodecHWConfig)super.position(position);
    }

    public AVCodecHWConfig getPointer(long i) {
        return (AVCodecHWConfig)new AVCodecHWConfig(this).offsetAddress(i);
    }

    @Cast(value={"AVPixelFormat"})
    public native int pix_fmt();

    public native AVCodecHWConfig pix_fmt(int var1);

    public native int methods();

    public native AVCodecHWConfig methods(int var1);

    @Cast(value={"AVHWDeviceType"})
    public native int device_type();

    public native AVCodecHWConfig device_type(int var1);

    static {
        Loader.load();
    }
}

