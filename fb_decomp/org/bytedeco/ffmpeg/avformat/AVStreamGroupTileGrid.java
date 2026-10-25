/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.avcodec.AVPacketSideData;
import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVStreamGroupTileGrid
extends Pointer {
    public AVStreamGroupTileGrid() {
        super((Pointer)null);
        this.allocate();
    }

    public AVStreamGroupTileGrid(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVStreamGroupTileGrid(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVStreamGroupTileGrid position(long position) {
        return (AVStreamGroupTileGrid)super.position(position);
    }

    public AVStreamGroupTileGrid getPointer(long i) {
        return (AVStreamGroupTileGrid)new AVStreamGroupTileGrid(this).offsetAddress(i);
    }

    @Const
    public native AVClass av_class();

    public native AVStreamGroupTileGrid av_class(AVClass var1);

    @Cast(value={"unsigned int"})
    public native int nb_tiles();

    public native AVStreamGroupTileGrid nb_tiles(int var1);

    public native int coded_width();

    public native AVStreamGroupTileGrid coded_width(int var1);

    public native int coded_height();

    public native AVStreamGroupTileGrid coded_height(int var1);

    @Name(value={"offsets", ".idx"})
    @Cast(value={"unsigned int"})
    public native int offsets_idx(int var1);

    public native AVStreamGroupTileGrid offsets_idx(int var1, int var2);

    @Name(value={"offsets", ".horizontal"})
    public native int offsets_horizontal(int var1);

    public native AVStreamGroupTileGrid offsets_horizontal(int var1, int var2);

    @Name(value={"offsets", ".vertical"})
    public native int offsets_vertical(int var1);

    public native AVStreamGroupTileGrid offsets_vertical(int var1, int var2);

    @Cast(value={"uint8_t"})
    public native byte background(int var1);

    public native AVStreamGroupTileGrid background(int var1, byte var2);

    @MemberGetter
    @Cast(value={"uint8_t*"})
    public native BytePointer background();

    public native int horizontal_offset();

    public native AVStreamGroupTileGrid horizontal_offset(int var1);

    public native int vertical_offset();

    public native AVStreamGroupTileGrid vertical_offset(int var1);

    public native int width();

    public native AVStreamGroupTileGrid width(int var1);

    public native int height();

    public native AVStreamGroupTileGrid height(int var1);

    public native AVPacketSideData coded_side_data();

    public native AVStreamGroupTileGrid coded_side_data(AVPacketSideData var1);

    public native int nb_coded_side_data();

    public native AVStreamGroupTileGrid nb_coded_side_data(int var1);

    static {
        Loader.load();
    }
}

