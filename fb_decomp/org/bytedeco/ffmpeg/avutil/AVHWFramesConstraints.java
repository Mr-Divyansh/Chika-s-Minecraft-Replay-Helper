/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVHWFramesConstraints
extends Pointer {
    public AVHWFramesConstraints() {
        super((Pointer)null);
        this.allocate();
    }

    public AVHWFramesConstraints(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVHWFramesConstraints(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVHWFramesConstraints position(long position) {
        return (AVHWFramesConstraints)super.position(position);
    }

    public AVHWFramesConstraints getPointer(long i) {
        return (AVHWFramesConstraints)new AVHWFramesConstraints(this).offsetAddress(i);
    }

    @Cast(value={"AVPixelFormat*"})
    public native IntPointer valid_hw_formats();

    public native AVHWFramesConstraints valid_hw_formats(IntPointer var1);

    @Cast(value={"AVPixelFormat*"})
    public native IntPointer valid_sw_formats();

    public native AVHWFramesConstraints valid_sw_formats(IntPointer var1);

    public native int min_width();

    public native AVHWFramesConstraints min_width(int var1);

    public native int min_height();

    public native AVHWFramesConstraints min_height(int var1);

    public native int max_width();

    public native AVHWFramesConstraints max_width(int var1);

    public native int max_height();

    public native AVHWFramesConstraints max_height(int var1);

    static {
        Loader.load();
    }
}

