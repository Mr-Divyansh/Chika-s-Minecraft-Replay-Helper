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
public class av_intfloat32
extends Pointer {
    public av_intfloat32() {
        super((Pointer)null);
        this.allocate();
    }

    public av_intfloat32(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public av_intfloat32(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public av_intfloat32 position(long position) {
        return (av_intfloat32)super.position(position);
    }

    public av_intfloat32 getPointer(long i) {
        return (av_intfloat32)new av_intfloat32(this).offsetAddress(i);
    }

    @Cast(value={"uint32_t"})
    public native int i();

    public native av_intfloat32 i(int var1);

    public native float f();

    public native av_intfloat32 f(float var1);

    static {
        Loader.load();
    }
}

