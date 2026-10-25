/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avfilter;

import org.bytedeco.ffmpeg.avfilter.AVFilterParams;
import org.bytedeco.ffmpeg.presets.avfilter;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avfilter.class})
public class AVFilterChain
extends Pointer {
    public AVFilterChain() {
        super((Pointer)null);
        this.allocate();
    }

    public AVFilterChain(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVFilterChain(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVFilterChain position(long position) {
        return (AVFilterChain)super.position(position);
    }

    public AVFilterChain getPointer(long i) {
        return (AVFilterChain)new AVFilterChain(this).offsetAddress(i);
    }

    public native AVFilterParams filters(int var1);

    public native AVFilterChain filters(int var1, AVFilterParams var2);

    @Cast(value={"AVFilterParams**"})
    public native PointerPointer filters();

    public native AVFilterChain filters(PointerPointer var1);

    @Cast(value={"size_t"})
    public native long nb_filters();

    public native AVFilterChain nb_filters(long var1);

    static {
        Loader.load();
    }
}

