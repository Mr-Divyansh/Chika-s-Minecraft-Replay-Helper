/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVOptionRange;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVOptionRanges
extends Pointer {
    public AVOptionRanges() {
        super((Pointer)null);
        this.allocate();
    }

    public AVOptionRanges(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVOptionRanges(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVOptionRanges position(long position) {
        return (AVOptionRanges)super.position(position);
    }

    public AVOptionRanges getPointer(long i) {
        return (AVOptionRanges)new AVOptionRanges(this).offsetAddress(i);
    }

    public native AVOptionRange range(int var1);

    public native AVOptionRanges range(int var1, AVOptionRange var2);

    @Cast(value={"AVOptionRange**"})
    public native PointerPointer range();

    public native AVOptionRanges range(PointerPointer var1);

    public native int nb_ranges();

    public native AVOptionRanges nb_ranges(int var1);

    public native int nb_components();

    public native AVOptionRanges nb_components(int var1);

    static {
        Loader.load();
    }
}

