/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVComplexInt32
extends Pointer {
    public AVComplexInt32() {
        super((Pointer)null);
        this.allocate();
    }

    public AVComplexInt32(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVComplexInt32(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVComplexInt32 position(long position) {
        return (AVComplexInt32)super.position(position);
    }

    public AVComplexInt32 getPointer(long i) {
        return (AVComplexInt32)new AVComplexInt32(this).offsetAddress(i);
    }

    public native int re();

    public native AVComplexInt32 re(int var1);

    public native int im();

    public native AVComplexInt32 im(int var1);

    static {
        Loader.load();
    }
}

