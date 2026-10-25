/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVBufferRef;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVFrameSideData
extends Pointer {
    public AVFrameSideData() {
        super((Pointer)null);
        this.allocate();
    }

    public AVFrameSideData(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVFrameSideData(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVFrameSideData position(long position) {
        return (AVFrameSideData)super.position(position);
    }

    public AVFrameSideData getPointer(long i) {
        return (AVFrameSideData)new AVFrameSideData(this).offsetAddress(i);
    }

    @Cast(value={"AVFrameSideDataType"})
    public native int type();

    public native AVFrameSideData type(int var1);

    @Cast(value={"uint8_t*"})
    public native BytePointer data();

    public native AVFrameSideData data(BytePointer var1);

    @Cast(value={"size_t"})
    public native long size();

    public native AVFrameSideData size(long var1);

    public native AVDictionary metadata();

    public native AVFrameSideData metadata(AVDictionary var1);

    public native AVBufferRef buf();

    public native AVFrameSideData buf(AVBufferRef var1);

    static {
        Loader.load();
    }
}

