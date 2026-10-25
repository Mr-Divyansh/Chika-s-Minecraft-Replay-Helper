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
public class AVCPBProperties
extends Pointer {
    public AVCPBProperties() {
        super((Pointer)null);
        this.allocate();
    }

    public AVCPBProperties(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVCPBProperties(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVCPBProperties position(long position) {
        return (AVCPBProperties)super.position(position);
    }

    public AVCPBProperties getPointer(long i) {
        return (AVCPBProperties)new AVCPBProperties(this).offsetAddress(i);
    }

    @Cast(value={"int64_t"})
    public native long max_bitrate();

    public native AVCPBProperties max_bitrate(long var1);

    @Cast(value={"int64_t"})
    public native long min_bitrate();

    public native AVCPBProperties min_bitrate(long var1);

    @Cast(value={"int64_t"})
    public native long avg_bitrate();

    public native AVCPBProperties avg_bitrate(long var1);

    @Cast(value={"int64_t"})
    public native long buffer_size();

    public native AVCPBProperties buffer_size(long var1);

    @Cast(value={"uint64_t"})
    public native long vbv_delay();

    public native AVCPBProperties vbv_delay(long var1);

    static {
        Loader.load();
    }
}

