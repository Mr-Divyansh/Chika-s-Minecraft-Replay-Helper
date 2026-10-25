/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVPacketList
extends Pointer {
    public AVPacketList() {
        super((Pointer)null);
        this.allocate();
    }

    public AVPacketList(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVPacketList(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVPacketList position(long position) {
        return (AVPacketList)super.position(position);
    }

    public AVPacketList getPointer(long i) {
        return (AVPacketList)new AVPacketList(this).offsetAddress(i);
    }

    @ByRef
    public native AVPacket pkt();

    public native AVPacketList pkt(AVPacket var1);

    public native AVPacketList next();

    public native AVPacketList next(AVPacketList var1);

    static {
        Loader.load();
    }
}

