/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avfilter;

import org.bytedeco.ffmpeg.avutil.AVBufferRef;
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
public class AVBufferSrcParameters
extends Pointer {
    public AVBufferSrcParameters() {
        super((Pointer)null);
        this.allocate();
    }

    public AVBufferSrcParameters(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVBufferSrcParameters(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVBufferSrcParameters position(long position) {
        return (AVBufferSrcParameters)super.position(position);
    }

    public AVBufferSrcParameters getPointer(long i) {
        return (AVBufferSrcParameters)new AVBufferSrcParameters(this).offsetAddress(i);
    }

    public native int format();

    public native AVBufferSrcParameters format(int var1);

    @ByRef
    public native AVRational time_base();

    public native AVBufferSrcParameters time_base(AVRational var1);

    public native int width();

    public native AVBufferSrcParameters width(int var1);

    public native int height();

    public native AVBufferSrcParameters height(int var1);

    @ByRef
    public native AVRational sample_aspect_ratio();

    public native AVBufferSrcParameters sample_aspect_ratio(AVRational var1);

    @ByRef
    public native AVRational frame_rate();

    public native AVBufferSrcParameters frame_rate(AVRational var1);

    public native AVBufferRef hw_frames_ctx();

    public native AVBufferSrcParameters hw_frames_ctx(AVBufferRef var1);

    public native int sample_rate();

    public native AVBufferSrcParameters sample_rate(int var1);

    @ByRef
    public native AVChannelLayout ch_layout();

    public native AVBufferSrcParameters ch_layout(AVChannelLayout var1);

    @Cast(value={"AVColorSpace"})
    public native int color_space();

    public native AVBufferSrcParameters color_space(int var1);

    @Cast(value={"AVColorRange"})
    public native int color_range();

    public native AVBufferSrcParameters color_range(int var1);

    public native AVFrameSideData side_data(int var1);

    public native AVBufferSrcParameters side_data(int var1, AVFrameSideData var2);

    @Cast(value={"AVFrameSideData**"})
    public native PointerPointer side_data();

    public native AVBufferSrcParameters side_data(PointerPointer var1);

    public native int nb_side_data();

    public native AVBufferSrcParameters nb_side_data(int var1);

    @Cast(value={"AVAlphaMode"})
    public native int alpha_mode();

    public native AVBufferSrcParameters alpha_mode(int var1);

    static {
        Loader.load();
    }
}

