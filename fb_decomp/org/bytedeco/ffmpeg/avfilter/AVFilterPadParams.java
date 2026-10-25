/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avfilter;

import org.bytedeco.ffmpeg.presets.avfilter;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avfilter.class})
public class AVFilterPadParams
extends Pointer {
    public AVFilterPadParams() {
        super((Pointer)null);
        this.allocate();
    }

    public AVFilterPadParams(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVFilterPadParams(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVFilterPadParams position(long position) {
        return (AVFilterPadParams)super.position(position);
    }

    public AVFilterPadParams getPointer(long i) {
        return (AVFilterPadParams)new AVFilterPadParams(this).offsetAddress(i);
    }

    @Cast(value={"char*"})
    public native BytePointer label();

    public native AVFilterPadParams label(BytePointer var1);

    static {
        Loader.load();
    }
}

