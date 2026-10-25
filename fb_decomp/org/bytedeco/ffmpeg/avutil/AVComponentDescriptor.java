/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVComponentDescriptor
extends Pointer {
    public AVComponentDescriptor() {
        super((Pointer)null);
        this.allocate();
    }

    public AVComponentDescriptor(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVComponentDescriptor(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVComponentDescriptor position(long position) {
        return (AVComponentDescriptor)super.position(position);
    }

    public AVComponentDescriptor getPointer(long i) {
        return (AVComponentDescriptor)new AVComponentDescriptor(this).offsetAddress(i);
    }

    public native int plane();

    public native AVComponentDescriptor plane(int var1);

    public native int step();

    public native AVComponentDescriptor step(int var1);

    public native int offset();

    public native AVComponentDescriptor offset(int var1);

    public native int shift();

    public native AVComponentDescriptor shift(int var1);

    public native int depth();

    public native AVComponentDescriptor depth(int var1);

    static {
        Loader.load();
    }
}

