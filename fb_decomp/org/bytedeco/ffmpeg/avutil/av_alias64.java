/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class av_alias64
extends Pointer {
    public av_alias64() {
        super((Pointer)null);
        this.allocate();
    }

    public av_alias64(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public av_alias64(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public av_alias64 position(long position) {
        return (av_alias64)super.position(position);
    }

    public av_alias64 getPointer(long i) {
        return (av_alias64)new av_alias64(this).offsetAddress(i);
    }

    @Cast(value={"uint64_t"})
    public native long u64();

    public native av_alias64 u64(long var1);

    @Cast(value={"uint32_t"})
    public native int u32(int var1);

    public native av_alias64 u32(int var1, int var2);

    @MemberGetter
    @Cast(value={"uint32_t*"})
    public native IntPointer u32();

    @Cast(value={"uint16_t"})
    public native short u16(int var1);

    public native av_alias64 u16(int var1, short var2);

    @MemberGetter
    @Cast(value={"uint16_t*"})
    public native ShortPointer u16();

    @Cast(value={"uint8_t"})
    public native byte u8(int var1);

    public native av_alias64 u8(int var1, byte var2);

    @MemberGetter
    @Cast(value={"uint8_t*"})
    public native BytePointer u8();

    public native double f64();

    public native av_alias64 f64(double var1);

    public native float f32(int var1);

    public native av_alias64 f32(int var1, float var2);

    @MemberGetter
    public native FloatPointer f32();

    static {
        Loader.load();
    }
}

