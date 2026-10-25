/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVBufferRef;
import org.bytedeco.ffmpeg.avutil.AVChannelLayout;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.avutil.AVFrameSideData;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVFrame
extends Pointer {
    public static final int AV_NUM_DATA_POINTERS = 8;
    public static final int AV_FRAME_FLAG_CORRUPT = 1;
    public static final int AV_FRAME_FLAG_KEY = 2;
    public static final int AV_FRAME_FLAG_DISCARD = 4;
    public static final int AV_FRAME_FLAG_INTERLACED = 8;
    public static final int AV_FRAME_FLAG_TOP_FIELD_FIRST = 16;
    public static final int AV_FRAME_FLAG_LOSSLESS = 32;
    public static final int FF_DECODE_ERROR_INVALID_BITSTREAM = 1;
    public static final int FF_DECODE_ERROR_MISSING_REFERENCE = 2;
    public static final int FF_DECODE_ERROR_CONCEALMENT_ACTIVE = 4;
    public static final int FF_DECODE_ERROR_DECODE_SLICES = 8;

    public AVFrame() {
        super((Pointer)null);
        this.allocate();
    }

    public AVFrame(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVFrame(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVFrame position(long position) {
        return (AVFrame)super.position(position);
    }

    public AVFrame getPointer(long i) {
        return (AVFrame)new AVFrame(this).offsetAddress(i);
    }

    @Cast(value={"uint8_t*"})
    public native BytePointer data(int var1);

    public native AVFrame data(int var1, BytePointer var2);

    @MemberGetter
    @Cast(value={"uint8_t**"})
    public native PointerPointer data();

    public native int linesize(int var1);

    public native AVFrame linesize(int var1, int var2);

    @MemberGetter
    public native IntPointer linesize();

    @Cast(value={"uint8_t*"})
    public native BytePointer extended_data(int var1);

    public native AVFrame extended_data(int var1, BytePointer var2);

    @Cast(value={"uint8_t**"})
    public native PointerPointer extended_data();

    public native AVFrame extended_data(PointerPointer var1);

    public native int width();

    public native AVFrame width(int var1);

    public native int height();

    public native AVFrame height(int var1);

    public native int nb_samples();

    public native AVFrame nb_samples(int var1);

    public native int format();

    public native AVFrame format(int var1);

    @Cast(value={"AVPictureType"})
    public native int pict_type();

    public native AVFrame pict_type(int var1);

    @ByRef
    public native AVRational sample_aspect_ratio();

    public native AVFrame sample_aspect_ratio(AVRational var1);

    @Cast(value={"int64_t"})
    public native long pts();

    public native AVFrame pts(long var1);

    @Cast(value={"int64_t"})
    public native long pkt_dts();

    public native AVFrame pkt_dts(long var1);

    @ByRef
    public native AVRational time_base();

    public native AVFrame time_base(AVRational var1);

    public native int quality();

    public native AVFrame quality(int var1);

    public native Pointer opaque();

    public native AVFrame opaque(Pointer var1);

    public native int repeat_pict();

    public native AVFrame repeat_pict(int var1);

    public native int sample_rate();

    public native AVFrame sample_rate(int var1);

    public native AVBufferRef buf(int var1);

    public native AVFrame buf(int var1, AVBufferRef var2);

    @MemberGetter
    @Cast(value={"AVBufferRef**"})
    public native PointerPointer buf();

    public native AVBufferRef extended_buf(int var1);

    public native AVFrame extended_buf(int var1, AVBufferRef var2);

    @Cast(value={"AVBufferRef**"})
    public native PointerPointer extended_buf();

    public native AVFrame extended_buf(PointerPointer var1);

    public native int nb_extended_buf();

    public native AVFrame nb_extended_buf(int var1);

    public native AVFrameSideData side_data(int var1);

    public native AVFrame side_data(int var1, AVFrameSideData var2);

    @Cast(value={"AVFrameSideData**"})
    public native PointerPointer side_data();

    public native AVFrame side_data(PointerPointer var1);

    public native int nb_side_data();

    public native AVFrame nb_side_data(int var1);

    public native int flags();

    public native AVFrame flags(int var1);

    @Cast(value={"AVColorRange"})
    public native int color_range();

    public native AVFrame color_range(int var1);

    @Cast(value={"AVColorPrimaries"})
    public native int color_primaries();

    public native AVFrame color_primaries(int var1);

    @Cast(value={"AVColorTransferCharacteristic"})
    public native int color_trc();

    public native AVFrame color_trc(int var1);

    @Cast(value={"AVColorSpace"})
    public native int colorspace();

    public native AVFrame colorspace(int var1);

    @Cast(value={"AVChromaLocation"})
    public native int chroma_location();

    public native AVFrame chroma_location(int var1);

    @Cast(value={"int64_t"})
    public native long best_effort_timestamp();

    public native AVFrame best_effort_timestamp(long var1);

    public native AVDictionary metadata();

    public native AVFrame metadata(AVDictionary var1);

    public native int decode_error_flags();

    public native AVFrame decode_error_flags(int var1);

    public native AVBufferRef hw_frames_ctx();

    public native AVFrame hw_frames_ctx(AVBufferRef var1);

    public native AVBufferRef opaque_ref();

    public native AVFrame opaque_ref(AVBufferRef var1);

    @Cast(value={"size_t"})
    public native long crop_top();

    public native AVFrame crop_top(long var1);

    @Cast(value={"size_t"})
    public native long crop_bottom();

    public native AVFrame crop_bottom(long var1);

    @Cast(value={"size_t"})
    public native long crop_left();

    public native AVFrame crop_left(long var1);

    @Cast(value={"size_t"})
    public native long crop_right();

    public native AVFrame crop_right(long var1);

    public native Pointer private_ref();

    public native AVFrame private_ref(Pointer var1);

    @ByRef
    public native AVChannelLayout ch_layout();

    public native AVFrame ch_layout(AVChannelLayout var1);

    @Cast(value={"int64_t"})
    public native long duration();

    public native AVFrame duration(long var1);

    @Cast(value={"AVAlphaMode"})
    public native int alpha_mode();

    public native AVFrame alpha_mode(int var1);

    static {
        Loader.load();
    }
}

