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
public class av_alias16
extends Pointer {
    public av_alias16() {
        super((Pointer)null);
        this.allocate();
    }

    public av_alias16(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public av_alias16(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public av_alias16 position(long position) {
        return (av_alias16)super.position(position);
    }

    public av_alias16 getPointer(long i) {
        return (av_alias16)new av_alias16(this).offsetAddress(i);
    }

    @Cast(value={"uint16_t"})
    public native short u16();

    public native av_alias16 u16(short var1);

    @Cast(value={"uint8_t"})
    public native byte u8(int var1);

    public native av_alias16 u8(int var1, byte var2);

    @MemberGetter
    @Cast(value={"uint8_t*"})
    public native BytePointer u8();

    static {
        Loader.load();
    }
}

