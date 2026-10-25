/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVComponentDescriptor;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVPixFmtDescriptor
extends Pointer {
    public AVPixFmtDescriptor() {
        super((Pointer)null);
        this.allocate();
    }

    public AVPixFmtDescriptor(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVPixFmtDescriptor(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVPixFmtDescriptor position(long position) {
        return (AVPixFmtDescriptor)super.position(position);
    }

    public AVPixFmtDescriptor getPointer(long i) {
        return (AVPixFmtDescriptor)new AVPixFmtDescriptor(this).offsetAddress(i);
    }

    @Cast(value={"const char*"})
    public native BytePointer name();

    public native AVPixFmtDescriptor name(BytePointer var1);

    @Cast(value={"uint8_t"})
    public native byte nb_components();

    public native AVPixFmtDescriptor nb_components(byte var1);

    @Cast(value={"uint8_t"})
    public native byte log2_chroma_w();

    public native AVPixFmtDescriptor log2_chroma_w(byte var1);

    @Cast(value={"uint8_t"})
    public native byte log2_chroma_h();

    public native AVPixFmtDescriptor log2_chroma_h(byte var1);

    @Cast(value={"uint64_t"})
    public native long flags();

    public native AVPixFmtDescriptor flags(long var1);

    @ByRef
    public native AVComponentDescriptor comp(int var1);

    public native AVPixFmtDescriptor comp(int var1, AVComponentDescriptor var2);

    @MemberGetter
    public native AVComponentDescriptor comp();

    @Cast(value={"const char*"})
    public native BytePointer alias();

    public native AVPixFmtDescriptor alias(BytePointer var1);

    static {
        Loader.load();
    }
}

