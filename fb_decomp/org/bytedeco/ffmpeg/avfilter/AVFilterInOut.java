/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avfilter;

import org.bytedeco.ffmpeg.avfilter.AVFilterContext;
import org.bytedeco.ffmpeg.presets.avfilter;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avfilter.class})
public class AVFilterInOut
extends Pointer {
    public AVFilterInOut() {
        super((Pointer)null);
        this.allocate();
    }

    public AVFilterInOut(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVFilterInOut(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVFilterInOut position(long position) {
        return (AVFilterInOut)super.position(position);
    }

    public AVFilterInOut getPointer(long i) {
        return (AVFilterInOut)new AVFilterInOut(this).offsetAddress(i);
    }

    @Cast(value={"char*"})
    public native BytePointer name();

    public native AVFilterInOut name(BytePointer var1);

    public native AVFilterContext filter_ctx();

    public native AVFilterInOut filter_ctx(AVFilterContext var1);

    public native int pad_idx();

    public native AVFilterInOut pad_idx(int var1);

    public native AVFilterInOut next();

    public native AVFilterInOut next(AVFilterInOut var1);

    static {
        Loader.load();
    }
}

