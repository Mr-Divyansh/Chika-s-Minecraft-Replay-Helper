/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVRational
extends Pointer {
    public AVRational() {
        super((Pointer)null);
        this.allocate();
    }

    public AVRational(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVRational(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVRational position(long position) {
        return (AVRational)super.position(position);
    }

    public AVRational getPointer(long i) {
        return (AVRational)new AVRational(this).offsetAddress(i);
    }

    public native int num();

    public native AVRational num(int var1);

    public native int den();

    public native AVRational den(int var1);

    static {
        Loader.load();
    }
}

