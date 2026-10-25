/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVHDRPlusPercentile
extends Pointer {
    public AVHDRPlusPercentile() {
        super((Pointer)null);
        this.allocate();
    }

    public AVHDRPlusPercentile(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVHDRPlusPercentile(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVHDRPlusPercentile position(long position) {
        return (AVHDRPlusPercentile)super.position(position);
    }

    public AVHDRPlusPercentile getPointer(long i) {
        return (AVHDRPlusPercentile)new AVHDRPlusPercentile(this).offsetAddress(i);
    }

    @Cast(value={"uint8_t"})
    public native byte percentage();

    public native AVHDRPlusPercentile percentage(byte var1);

    @ByRef
    public native AVRational percentile();

    public native AVHDRPlusPercentile percentile(AVRational var1);

    static {
        Loader.load();
    }
}

