/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVStreamGroupLCEVC
extends Pointer {
    public AVStreamGroupLCEVC() {
        super((Pointer)null);
        this.allocate();
    }

    public AVStreamGroupLCEVC(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVStreamGroupLCEVC(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVStreamGroupLCEVC position(long position) {
        return (AVStreamGroupLCEVC)super.position(position);
    }

    public AVStreamGroupLCEVC getPointer(long i) {
        return (AVStreamGroupLCEVC)new AVStreamGroupLCEVC(this).offsetAddress(i);
    }

    @Const
    public native AVClass av_class();

    public native AVStreamGroupLCEVC av_class(AVClass var1);

    @Cast(value={"unsigned int"})
    public native int lcevc_index();

    public native AVStreamGroupLCEVC lcevc_index(int var1);

    public native int width();

    public native AVStreamGroupLCEVC width(int var1);

    public native int height();

    public native AVStreamGroupLCEVC height(int var1);

    static {
        Loader.load();
    }
}

