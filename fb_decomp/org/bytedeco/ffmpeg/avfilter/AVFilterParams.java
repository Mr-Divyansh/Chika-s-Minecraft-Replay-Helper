/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avfilter;

import org.bytedeco.ffmpeg.avfilter.AVFilterContext;
import org.bytedeco.ffmpeg.avfilter.AVFilterPadParams;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.presets.avfilter;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avfilter.class})
public class AVFilterParams
extends Pointer {
    public AVFilterParams() {
        super((Pointer)null);
        this.allocate();
    }

    public AVFilterParams(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVFilterParams(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVFilterParams position(long position) {
        return (AVFilterParams)super.position(position);
    }

    public AVFilterParams getPointer(long i) {
        return (AVFilterParams)new AVFilterParams(this).offsetAddress(i);
    }

    public native AVFilterContext filter();

    public native AVFilterParams filter(AVFilterContext var1);

    @Cast(value={"char*"})
    public native BytePointer filter_name();

    public native AVFilterParams filter_name(BytePointer var1);

    @Cast(value={"char*"})
    public native BytePointer instance_name();

    public native AVFilterParams instance_name(BytePointer var1);

    public native AVDictionary opts();

    public native AVFilterParams opts(AVDictionary var1);

    public native AVFilterPadParams inputs(int var1);

    public native AVFilterParams inputs(int var1, AVFilterPadParams var2);

    @Cast(value={"AVFilterPadParams**"})
    public native PointerPointer inputs();

    public native AVFilterParams inputs(PointerPointer var1);

    @Cast(value={"unsigned"})
    public native int nb_inputs();

    public native AVFilterParams nb_inputs(int var1);

    public native AVFilterPadParams outputs(int var1);

    public native AVFilterParams outputs(int var1, AVFilterPadParams var2);

    @Cast(value={"AVFilterPadParams**"})
    public native PointerPointer outputs();

    public native AVFilterParams outputs(PointerPointer var1);

    @Cast(value={"unsigned"})
    public native int nb_outputs();

    public native AVFilterParams nb_outputs(int var1);

    static {
        Loader.load();
    }
}

