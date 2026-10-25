/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVBuffer;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVBufferRef
extends Pointer {
    public AVBufferRef() {
        super((Pointer)null);
        this.allocate();
    }

    public AVBufferRef(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVBufferRef(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVBufferRef position(long position) {
        return (AVBufferRef)super.position(position);
    }

    public AVBufferRef getPointer(long i) {
        return (AVBufferRef)new AVBufferRef(this).offsetAddress(i);
    }

    public native AVBuffer buffer();

    public native AVBufferRef buffer(AVBuffer var1);

    @Cast(value={"uint8_t*"})
    public native BytePointer data();

    public native AVBufferRef data(BytePointer var1);

    @Cast(value={"size_t"})
    public native long size();

    public native AVBufferRef size(long var1);

    static {
        Loader.load();
    }
}

