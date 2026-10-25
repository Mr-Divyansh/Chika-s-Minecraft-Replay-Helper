/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avfilter;

import org.bytedeco.ffmpeg.avfilter.AVFilter;
import org.bytedeco.ffmpeg.avfilter.AVFilterGraph;
import org.bytedeco.ffmpeg.avfilter.AVFilterLink;
import org.bytedeco.ffmpeg.avfilter.AVFilterPad;
import org.bytedeco.ffmpeg.avutil.AVBufferRef;
import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.presets.avfilter;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avfilter.class})
public class AVFilterContext
extends Pointer {
    public AVFilterContext() {
        super((Pointer)null);
        this.allocate();
    }

    public AVFilterContext(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVFilterContext(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVFilterContext position(long position) {
        return (AVFilterContext)super.position(position);
    }

    public AVFilterContext getPointer(long i) {
        return (AVFilterContext)new AVFilterContext(this).offsetAddress(i);
    }

    @Const
    public native AVClass av_class();

    public native AVFilterContext av_class(AVClass var1);

    @Const
    public native AVFilter filter();

    public native AVFilterContext filter(AVFilter var1);

    @Cast(value={"char*"})
    public native BytePointer name();

    public native AVFilterContext name(BytePointer var1);

    public native AVFilterPad input_pads();

    public native AVFilterContext input_pads(AVFilterPad var1);

    public native AVFilterLink inputs(int var1);

    public native AVFilterContext inputs(int var1, AVFilterLink var2);

    @Cast(value={"AVFilterLink**"})
    public native PointerPointer inputs();

    public native AVFilterContext inputs(PointerPointer var1);

    @Cast(value={"unsigned"})
    public native int nb_inputs();

    public native AVFilterContext nb_inputs(int var1);

    public native AVFilterPad output_pads();

    public native AVFilterContext output_pads(AVFilterPad var1);

    public native AVFilterLink outputs(int var1);

    public native AVFilterContext outputs(int var1, AVFilterLink var2);

    @Cast(value={"AVFilterLink**"})
    public native PointerPointer outputs();

    public native AVFilterContext outputs(PointerPointer var1);

    @Cast(value={"unsigned"})
    public native int nb_outputs();

    public native AVFilterContext nb_outputs(int var1);

    public native Pointer priv();

    public native AVFilterContext priv(Pointer var1);

    public native AVFilterGraph graph();

    public native AVFilterContext graph(AVFilterGraph var1);

    public native int thread_type();

    public native AVFilterContext thread_type(int var1);

    public native int nb_threads();

    public native AVFilterContext nb_threads(int var1);

    @Cast(value={"AVFilterCommand*"})
    @Deprecated
    public native Pointer command_queue();

    public native AVFilterContext command_queue(Pointer var1);

    @Cast(value={"char*"})
    public native BytePointer enable_str();

    public native AVFilterContext enable_str(BytePointer var1);

    @Deprecated
    public native Pointer enable();

    public native AVFilterContext enable(Pointer var1);

    public native DoublePointer var_values();

    public native AVFilterContext var_values(DoublePointer var1);

    public native int is_disabled();

    public native AVFilterContext is_disabled(int var1);

    public native AVBufferRef hw_device_ctx();

    public native AVFilterContext hw_device_ctx(AVBufferRef var1);

    @Cast(value={"unsigned"})
    @Deprecated
    public native int ready();

    public native AVFilterContext ready(int var1);

    public native int extra_hw_frames();

    public native AVFilterContext extra_hw_frames(int var1);

    static {
        Loader.load();
    }
}

