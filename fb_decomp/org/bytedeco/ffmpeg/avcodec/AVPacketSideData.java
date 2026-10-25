/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVPacketSideData
extends Pointer {
    public AVPacketSideData() {
        super((Pointer)null);
        this.allocate();
    }

    public AVPacketSideData(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVPacketSideData(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVPacketSideData position(long position) {
        return (AVPacketSideData)super.position(position);
    }

    public AVPacketSideData getPointer(long i) {
        return (AVPacketSideData)new AVPacketSideData(this).offsetAddress(i);
    }

    @Cast(value={"uint8_t*"})
    public native BytePointer data();

    public native AVPacketSideData data(BytePointer var1);

    @Cast(value={"size_t"})
    public native long size();

    public native AVPacketSideData size(long var1);

    @Cast(value={"AVPacketSideDataType"})
    public native int type();

    public native AVPacketSideData type(int var1);

    static {
        Loader.load();
    }
}

