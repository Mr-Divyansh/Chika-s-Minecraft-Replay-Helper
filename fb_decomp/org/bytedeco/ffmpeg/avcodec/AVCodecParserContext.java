/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.avcodec.AVCodecParser;
import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.LongPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVCodecParserContext
extends Pointer {
    public static final int AV_PARSER_PTS_NB = 4;
    public static final int PARSER_FLAG_COMPLETE_FRAMES = 1;
    public static final int PARSER_FLAG_ONCE = 2;
    public static final int PARSER_FLAG_FETCHED_OFFSET = 4;
    public static final int PARSER_FLAG_USE_CODEC_TS = 4096;

    public AVCodecParserContext() {
        super((Pointer)null);
        this.allocate();
    }

    public AVCodecParserContext(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVCodecParserContext(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVCodecParserContext position(long position) {
        return (AVCodecParserContext)super.position(position);
    }

    public AVCodecParserContext getPointer(long i) {
        return (AVCodecParserContext)new AVCodecParserContext(this).offsetAddress(i);
    }

    public native Pointer priv_data();

    public native AVCodecParserContext priv_data(Pointer var1);

    @Const
    public native AVCodecParser parser();

    public native AVCodecParserContext parser(AVCodecParser var1);

    @Cast(value={"int64_t"})
    public native long frame_offset();

    public native AVCodecParserContext frame_offset(long var1);

    @Cast(value={"int64_t"})
    public native long cur_offset();

    public native AVCodecParserContext cur_offset(long var1);

    @Cast(value={"int64_t"})
    public native long next_frame_offset();

    public native AVCodecParserContext next_frame_offset(long var1);

    public native int pict_type();

    public native AVCodecParserContext pict_type(int var1);

    public native int repeat_pict();

    public native AVCodecParserContext repeat_pict(int var1);

    @Cast(value={"int64_t"})
    public native long pts();

    public native AVCodecParserContext pts(long var1);

    @Cast(value={"int64_t"})
    public native long dts();

    public native AVCodecParserContext dts(long var1);

    @Cast(value={"int64_t"})
    public native long last_pts();

    public native AVCodecParserContext last_pts(long var1);

    @Cast(value={"int64_t"})
    public native long last_dts();

    public native AVCodecParserContext last_dts(long var1);

    public native int fetch_timestamp();

    public native AVCodecParserContext fetch_timestamp(int var1);

    public native int cur_frame_start_index();

    public native AVCodecParserContext cur_frame_start_index(int var1);

    @Cast(value={"int64_t"})
    public native long cur_frame_offset(int var1);

    public native AVCodecParserContext cur_frame_offset(int var1, long var2);

    @MemberGetter
    @Cast(value={"int64_t*"})
    public native LongPointer cur_frame_offset();

    @Cast(value={"int64_t"})
    public native long cur_frame_pts(int var1);

    public native AVCodecParserContext cur_frame_pts(int var1, long var2);

    @MemberGetter
    @Cast(value={"int64_t*"})
    public native LongPointer cur_frame_pts();

    @Cast(value={"int64_t"})
    public native long cur_frame_dts(int var1);

    public native AVCodecParserContext cur_frame_dts(int var1, long var2);

    @MemberGetter
    @Cast(value={"int64_t*"})
    public native LongPointer cur_frame_dts();

    public native int flags();

    public native AVCodecParserContext flags(int var1);

    @Cast(value={"int64_t"})
    public native long offset();

    public native AVCodecParserContext offset(long var1);

    @Cast(value={"int64_t"})
    public native long cur_frame_end(int var1);

    public native AVCodecParserContext cur_frame_end(int var1, long var2);

    @MemberGetter
    @Cast(value={"int64_t*"})
    public native LongPointer cur_frame_end();

    public native int key_frame();

    public native AVCodecParserContext key_frame(int var1);

    public native int dts_sync_point();

    public native AVCodecParserContext dts_sync_point(int var1);

    public native int dts_ref_dts_delta();

    public native AVCodecParserContext dts_ref_dts_delta(int var1);

    public native int pts_dts_delta();

    public native AVCodecParserContext pts_dts_delta(int var1);

    @Cast(value={"int64_t"})
    public native long cur_frame_pos(int var1);

    public native AVCodecParserContext cur_frame_pos(int var1, long var2);

    @MemberGetter
    @Cast(value={"int64_t*"})
    public native LongPointer cur_frame_pos();

    @Cast(value={"int64_t"})
    public native long pos();

    public native AVCodecParserContext pos(long var1);

    @Cast(value={"int64_t"})
    public native long last_pos();

    public native AVCodecParserContext last_pos(long var1);

    public native int duration();

    public native AVCodecParserContext duration(int var1);

    @Cast(value={"AVFieldOrder"})
    public native int field_order();

    public native AVCodecParserContext field_order(int var1);

    @Cast(value={"AVPictureStructure"})
    public native int picture_structure();

    public native AVCodecParserContext picture_structure(int var1);

    public native int output_picture_number();

    public native AVCodecParserContext output_picture_number(int var1);

    public native int width();

    public native AVCodecParserContext width(int var1);

    public native int height();

    public native AVCodecParserContext height(int var1);

    public native int coded_width();

    public native AVCodecParserContext coded_width(int var1);

    public native int coded_height();

    public native AVCodecParserContext coded_height(int var1);

    public native int format();

    public native AVCodecParserContext format(int var1);

    static {
        Loader.load();
    }
}

