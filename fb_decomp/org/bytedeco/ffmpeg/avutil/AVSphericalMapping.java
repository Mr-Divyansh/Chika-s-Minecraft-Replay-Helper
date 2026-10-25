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
public class AVSphericalMapping
extends Pointer {
    public AVSphericalMapping() {
        super((Pointer)null);
        this.allocate();
    }

    public AVSphericalMapping(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVSphericalMapping(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVSphericalMapping position(long position) {
        return (AVSphericalMapping)super.position(position);
    }

    public AVSphericalMapping getPointer(long i) {
        return (AVSphericalMapping)new AVSphericalMapping(this).offsetAddress(i);
    }

    @Cast(value={"AVSphericalProjection"})
    public native int projection();

    public native AVSphericalMapping projection(int var1);

    public native int yaw();

    public native AVSphericalMapping yaw(int var1);

    public native int pitch();

    public native AVSphericalMapping pitch(int var1);

    public native int roll();

    public native AVSphericalMapping roll(int var1);

    @Cast(value={"uint32_t"})
    public native int bound_left();

    public native AVSphericalMapping bound_left(int var1);

    @Cast(value={"uint32_t"})
    public native int bound_top();

    public native AVSphericalMapping bound_top(int var1);

    @Cast(value={"uint32_t"})
    public native int bound_right();

    public native AVSphericalMapping bound_right(int var1);

    @Cast(value={"uint32_t"})
    public native int bound_bottom();

    public native AVSphericalMapping bound_bottom(int var1);

    @Cast(value={"uint32_t"})
    public native int padding();

    public native AVSphericalMapping padding(int var1);

    static {
        Loader.load();
    }
}

