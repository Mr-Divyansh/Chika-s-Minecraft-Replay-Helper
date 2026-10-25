/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avdevice;

import org.bytedeco.ffmpeg.presets.avdevice;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avdevice.class})
public class AVDeviceRect
extends Pointer {
    public AVDeviceRect() {
        super((Pointer)null);
        this.allocate();
    }

    public AVDeviceRect(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVDeviceRect(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVDeviceRect position(long position) {
        return (AVDeviceRect)super.position(position);
    }

    public AVDeviceRect getPointer(long i) {
        return (AVDeviceRect)new AVDeviceRect(this).offsetAddress(i);
    }

    public native int x();

    public native AVDeviceRect x(int var1);

    public native int y();

    public native AVDeviceRect y(int var1);

    public native int width();

    public native AVDeviceRect width(int var1);

    public native int height();

    public native AVDeviceRect height(int var1);

    static {
        Loader.load();
    }
}

