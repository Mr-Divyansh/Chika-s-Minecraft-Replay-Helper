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
public class AVRegionOfInterest
extends Pointer {
    public AVRegionOfInterest() {
        super((Pointer)null);
        this.allocate();
    }

    public AVRegionOfInterest(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVRegionOfInterest(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVRegionOfInterest position(long position) {
        return (AVRegionOfInterest)super.position(position);
    }

    public AVRegionOfInterest getPointer(long i) {
        return (AVRegionOfInterest)new AVRegionOfInterest(this).offsetAddress(i);
    }

    @Cast(value={"uint32_t"})
    public native int self_size();

    public native AVRegionOfInterest self_size(int var1);

    public native int top();

    public native AVRegionOfInterest top(int var1);

    public native int bottom();

    public native AVRegionOfInterest bottom(int var1);

    public native int left();

    public native AVRegionOfInterest left(int var1);

    public native int right();

    public native AVRegionOfInterest right(int var1);

    @ByRef
    public native AVRational qoffset();

    public native AVRegionOfInterest qoffset(AVRational var1);

    static {
        Loader.load();
    }
}

