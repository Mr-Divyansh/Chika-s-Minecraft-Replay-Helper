/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVSideDataDescriptor
extends Pointer {
    public AVSideDataDescriptor() {
        super((Pointer)null);
        this.allocate();
    }

    public AVSideDataDescriptor(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVSideDataDescriptor(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVSideDataDescriptor position(long position) {
        return (AVSideDataDescriptor)super.position(position);
    }

    public AVSideDataDescriptor getPointer(long i) {
        return (AVSideDataDescriptor)new AVSideDataDescriptor(this).offsetAddress(i);
    }

    @Cast(value={"const char*"})
    public native BytePointer name();

    public native AVSideDataDescriptor name(BytePointer var1);

    @Cast(value={"unsigned"})
    public native int props();

    public native AVSideDataDescriptor props(int var1);

    static {
        Loader.load();
    }
}

