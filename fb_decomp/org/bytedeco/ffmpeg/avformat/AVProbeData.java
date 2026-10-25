/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVProbeData
extends Pointer {
    public AVProbeData() {
        super((Pointer)null);
        this.allocate();
    }

    public AVProbeData(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVProbeData(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVProbeData position(long position) {
        return (AVProbeData)super.position(position);
    }

    public AVProbeData getPointer(long i) {
        return (AVProbeData)new AVProbeData(this).offsetAddress(i);
    }

    @Cast(value={"const char*"})
    public native BytePointer filename();

    public native AVProbeData filename(BytePointer var1);

    @Cast(value={"unsigned char*"})
    public native BytePointer buf();

    public native AVProbeData buf(BytePointer var1);

    public native int buf_size();

    public native AVProbeData buf_size(int var1);

    @Cast(value={"const char*"})
    public native BytePointer mime_type();

    public native AVProbeData mime_type(BytePointer var1);

    static {
        Loader.load();
    }
}

