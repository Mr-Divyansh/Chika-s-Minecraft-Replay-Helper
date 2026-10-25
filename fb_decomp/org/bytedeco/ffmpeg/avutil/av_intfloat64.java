/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class av_intfloat64
extends Pointer {
    public av_intfloat64() {
        super((Pointer)null);
        this.allocate();
    }

    public av_intfloat64(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public av_intfloat64(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public av_intfloat64 position(long position) {
        return (av_intfloat64)super.position(position);
    }

    public av_intfloat64 getPointer(long i) {
        return (av_intfloat64)new av_intfloat64(this).offsetAddress(i);
    }

    @Cast(value={"uint64_t"})
    public native long i();

    public native av_intfloat64 i(long var1);

    public native double f();

    public native av_intfloat64 f(double var1);

    static {
        Loader.load();
    }
}

