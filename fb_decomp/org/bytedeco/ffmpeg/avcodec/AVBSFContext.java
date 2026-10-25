/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.avcodec.AVBitStreamFilter;
import org.bytedeco.ffmpeg.avcodec.AVCodecParameters;
import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVBSFContext
extends Pointer {
    public AVBSFContext() {
        super((Pointer)null);
        this.allocate();
    }

    public AVBSFContext(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVBSFContext(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVBSFContext position(long position) {
        return (AVBSFContext)super.position(position);
    }

    public AVBSFContext getPointer(long i) {
        return (AVBSFContext)new AVBSFContext(this).offsetAddress(i);
    }

    @Const
    public native AVClass av_class();

    public native AVBSFContext av_class(AVClass var1);

    @Const
    public native AVBitStreamFilter filter();

    public native AVBSFContext filter(AVBitStreamFilter var1);

    public native Pointer priv_data();

    public native AVBSFContext priv_data(Pointer var1);

    public native AVCodecParameters par_in();

    public native AVBSFContext par_in(AVCodecParameters var1);

    public native AVCodecParameters par_out();

    public native AVBSFContext par_out(AVCodecParameters var1);

    @ByRef
    public native AVRational time_base_in();

    public native AVBSFContext time_base_in(AVRational var1);

    @ByRef
    public native AVRational time_base_out();

    public native AVBSFContext time_base_out(AVRational var1);

    static {
        Loader.load();
    }
}

