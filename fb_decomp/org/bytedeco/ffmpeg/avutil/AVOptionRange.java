/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVOptionRange
extends Pointer {
    public AVOptionRange() {
        super((Pointer)null);
        this.allocate();
    }

    public AVOptionRange(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVOptionRange(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVOptionRange position(long position) {
        return (AVOptionRange)super.position(position);
    }

    public AVOptionRange getPointer(long i) {
        return (AVOptionRange)new AVOptionRange(this).offsetAddress(i);
    }

    @Cast(value={"const char*"})
    public native BytePointer str();

    public native AVOptionRange str(BytePointer var1);

    public native double value_min();

    public native AVOptionRange value_min(double var1);

    public native double value_max();

    public native AVOptionRange value_max(double var1);

    public native double component_min();

    public native AVOptionRange component_min(double var1);

    public native double component_max();

    public native AVOptionRange component_max(double var1);

    public native int is_range();

    public native AVOptionRange is_range(int var1);

    static {
        Loader.load();
    }
}

