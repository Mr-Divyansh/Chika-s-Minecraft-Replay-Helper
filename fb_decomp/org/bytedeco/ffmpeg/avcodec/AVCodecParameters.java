/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.avcodec.AVPacketSideData;
import org.bytedeco.ffmpeg.avutil.AVChannelLayout;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVCodecParameters
extends Pointer {
    public AVCodecParameters() {
        super((Pointer)null);
        this.allocate();
    }

    public AVCodecParameters(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVCodecParameters(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVCodecParameters position(long position) {
        return (AVCodecParameters)super.position(position);
    }

    public AVCodecParameters getPointer(long i) {
        return (AVCodecParameters)new AVCodecParameters(this).offsetAddress(i);
    }

    @Cast(value={"AVMediaType"})
    public native int codec_type();

    public native AVCodecParameters codec_type(int var1);

    @Cast(value={"AVCodecID"})
    public native int codec_id();

    public native AVCodecParameters codec_id(int var1);

    @Cast(value={"uint32_t"})
    public native int codec_tag();

    public native AVCodecParameters codec_tag(int var1);

    @Cast(value={"uint8_t*"})
    public native BytePointer extradata();

    public native AVCodecParameters extradata(BytePointer var1);

    public native int extradata_size();

    public native AVCodecParameters extradata_size(int var1);

    public native AVPacketSideData coded_side_data();

    public native AVCodecParameters coded_side_data(AVPacketSideData var1);

    public native int nb_coded_side_data();

    public native AVCodecParameters nb_coded_side_data(int var1);

    public native int format();

    public native AVCodecParameters format(int var1);

    @Cast(value={"int64_t"})
    public native long bit_rate();

    public native AVCodecParameters bit_rate(long var1);

    public native int bits_per_coded_sample();

    public native AVCodecParameters bits_per_coded_sample(int var1);

    public native int bits_per_raw_sample();

    public native AVCodecParameters bits_per_raw_sample(int var1);

    public native int profile();

    public native AVCodecParameters profile(int var1);

    public native int level();

    public native AVCodecParameters level(int var1);

    public native int width();

    public native AVCodecParameters width(int var1);

    public native int height();

    public native AVCodecParameters height(int var1);

    @ByRef
    public native AVRational sample_aspect_ratio();

    public native AVCodecParameters sample_aspect_ratio(AVRational var1);

    @ByRef
    public native AVRational framerate();

    public native AVCodecParameters framerate(AVRational var1);

    @Cast(value={"AVFieldOrder"})
    public native int field_order();

    public native AVCodecParameters field_order(int var1);

    @Cast(value={"AVColorRange"})
    public native int color_range();

    public native AVCodecParameters color_range(int var1);

    @Cast(value={"AVColorPrimaries"})
    public native int color_primaries();

    public native AVCodecParameters color_primaries(int var1);

    @Cast(value={"AVColorTransferCharacteristic"})
    public native int color_trc();

    public native AVCodecParameters color_trc(int var1);

    @Cast(value={"AVColorSpace"})
    public native int color_space();

    public native AVCodecParameters color_space(int var1);

    @Cast(value={"AVChromaLocation"})
    public native int chroma_location();

    public native AVCodecParameters chroma_location(int var1);

    public native int video_delay();

    public native AVCodecParameters video_delay(int var1);

    @ByRef
    public native AVChannelLayout ch_layout();

    public native AVCodecParameters ch_layout(AVChannelLayout var1);

    public native int sample_rate();

    public native AVCodecParameters sample_rate(int var1);

    public native int block_align();

    public native AVCodecParameters block_align(int var1);

    public native int frame_size();

    public native AVCodecParameters frame_size(int var1);

    public native int initial_padding();

    public native AVCodecParameters initial_padding(int var1);

    public native int trailing_padding();

    public native AVCodecParameters trailing_padding(int var1);

    public native int seek_preroll();

    public native AVCodecParameters seek_preroll(int var1);

    @Cast(value={"AVAlphaMode"})
    public native int alpha_mode();

    public native AVCodecParameters alpha_mode(int var1);

    static {
        Loader.load();
    }
}

