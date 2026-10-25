/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avfilter;

import org.bytedeco.ffmpeg.avfilter.AVFilterContext;
import org.bytedeco.ffmpeg.avfilter.AVFilterFormatsConfig;
import org.bytedeco.ffmpeg.avfilter.AVFilterPad;
import org.bytedeco.ffmpeg.avutil.AVChannelLayout;
import org.bytedeco.ffmpeg.avutil.AVFrameSideData;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.presets.avfilter;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avfilter.class})
public class AVFilterLink
extends Pointer {
    public AVFilterLink() {
        super((Pointer)null);
        this.allocate();
    }

    public AVFilterLink(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVFilterLink(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVFilterLink position(long position) {
        return (AVFilterLink)super.position(position);
    }

    public AVFilterLink getPointer(long i) {
        return (AVFilterLink)new AVFilterLink(this).offsetAddress(i);
    }

    public native AVFilterContext src();

    public native AVFilterLink src(AVFilterContext var1);

    public native AVFilterPad srcpad();

    public native AVFilterLink srcpad(AVFilterPad var1);

    public native AVFilterContext dst();

    public native AVFilterLink dst(AVFilterContext var1);

    public native AVFilterPad dstpad();

    public native AVFilterLink dstpad(AVFilterPad var1);

    @Cast(value={"AVMediaType"})
    public native int type();

    public native AVFilterLink type(int var1);

    public native int format();

    public native AVFilterLink format(int var1);

    public native int w();

    public native AVFilterLink w(int var1);

    public native int h();

    public native AVFilterLink h(int var1);

    @ByRef
    public native AVRational sample_aspect_ratio();

    public native AVFilterLink sample_aspect_ratio(AVRational var1);

    @Cast(value={"AVColorSpace"})
    public native int colorspace();

    public native AVFilterLink colorspace(int var1);

    @Cast(value={"AVColorRange"})
    public native int color_range();

    public native AVFilterLink color_range(int var1);

    public native int sample_rate();

    public native AVFilterLink sample_rate(int var1);

    @ByRef
    public native AVChannelLayout ch_layout();

    public native AVFilterLink ch_layout(AVChannelLayout var1);

    @ByRef
    public native AVRational time_base();

    public native AVFilterLink time_base(AVRational var1);

    public native AVFrameSideData side_data(int var1);

    public native AVFilterLink side_data(int var1, AVFrameSideData var2);

    @Cast(value={"AVFrameSideData**"})
    public native PointerPointer side_data();

    public native AVFilterLink side_data(PointerPointer var1);

    public native int nb_side_data();

    public native AVFilterLink nb_side_data(int var1);

    @Cast(value={"AVAlphaMode"})
    public native int alpha_mode();

    public native AVFilterLink alpha_mode(int var1);

    @ByRef
    public native AVFilterFormatsConfig incfg();

    public native AVFilterLink incfg(AVFilterFormatsConfig var1);

    @ByRef
    public native AVFilterFormatsConfig outcfg();

    public native AVFilterLink outcfg(AVFilterFormatsConfig var1);

    static {
        Loader.load();
    }
}

