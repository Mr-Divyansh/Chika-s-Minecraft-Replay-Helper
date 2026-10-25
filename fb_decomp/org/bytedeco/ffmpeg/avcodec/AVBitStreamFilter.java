/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVBitStreamFilter
extends Pointer {
    public AVBitStreamFilter() {
        super((Pointer)null);
        this.allocate();
    }

    public AVBitStreamFilter(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVBitStreamFilter(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVBitStreamFilter position(long position) {
        return (AVBitStreamFilter)super.position(position);
    }

    public AVBitStreamFilter getPointer(long i) {
        return (AVBitStreamFilter)new AVBitStreamFilter(this).offsetAddress(i);
    }

    @Cast(value={"const char*"})
    public native BytePointer name();

    public native AVBitStreamFilter name(BytePointer var1);

    @Cast(value={"const AVCodecID*"})
    public native IntPointer codec_ids();

    public native AVBitStreamFilter codec_ids(IntPointer var1);

    @Const
    public native AVClass priv_class();

    public native AVBitStreamFilter priv_class(AVClass var1);

    static {
        Loader.load();
    }
}

