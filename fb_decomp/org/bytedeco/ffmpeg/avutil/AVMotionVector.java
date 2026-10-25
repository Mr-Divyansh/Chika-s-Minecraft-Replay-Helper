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
public class AVMotionVector
extends Pointer {
    public AVMotionVector() {
        super((Pointer)null);
        this.allocate();
    }

    public AVMotionVector(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVMotionVector(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVMotionVector position(long position) {
        return (AVMotionVector)super.position(position);
    }

    public AVMotionVector getPointer(long i) {
        return (AVMotionVector)new AVMotionVector(this).offsetAddress(i);
    }

    public native int source();

    public native AVMotionVector source(int var1);

    @Cast(value={"uint8_t"})
    public native byte w();

    public native AVMotionVector w(byte var1);

    @Cast(value={"uint8_t"})
    public native byte h();

    public native AVMotionVector h(byte var1);

    public native short src_x();

    public native AVMotionVector src_x(short var1);

    public native short src_y();

    public native AVMotionVector src_y(short var1);

    public native short dst_x();

    public native AVMotionVector dst_x(short var1);

    public native short dst_y();

    public native AVMotionVector dst_y(short var1);

    @Cast(value={"uint64_t"})
    public native long flags();

    public native AVMotionVector flags(long var1);

    public native int motion_x();

    public native AVMotionVector motion_x(int var1);

    public native int motion_y();

    public native AVMotionVector motion_y(int var1);

    @Cast(value={"uint16_t"})
    public native short motion_scale();

    public native AVMotionVector motion_scale(short var1);

    static {
        Loader.load();
    }
}

