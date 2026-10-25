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
public class AVOptionArrayDef
extends Pointer {
    public AVOptionArrayDef() {
        super((Pointer)null);
        this.allocate();
    }

    public AVOptionArrayDef(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVOptionArrayDef(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVOptionArrayDef position(long position) {
        return (AVOptionArrayDef)super.position(position);
    }

    public AVOptionArrayDef getPointer(long i) {
        return (AVOptionArrayDef)new AVOptionArrayDef(this).offsetAddress(i);
    }

    @Cast(value={"const char*"})
    public native BytePointer def();

    public native AVOptionArrayDef def(BytePointer var1);

    @Cast(value={"unsigned"})
    public native int size_min();

    public native AVOptionArrayDef size_min(int var1);

    @Cast(value={"unsigned"})
    public native int size_max();

    public native AVOptionArrayDef size_max(int var1);

    @Cast(value={"char"})
    public native byte sep();

    public native AVOptionArrayDef sep(byte var1);

    static {
        Loader.load();
    }
}

