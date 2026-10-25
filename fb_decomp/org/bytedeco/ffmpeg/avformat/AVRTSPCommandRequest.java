/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVRTSPCommandRequest
extends Pointer {
    public AVRTSPCommandRequest() {
        super((Pointer)null);
        this.allocate();
    }

    public AVRTSPCommandRequest(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVRTSPCommandRequest(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVRTSPCommandRequest position(long position) {
        return (AVRTSPCommandRequest)super.position(position);
    }

    public AVRTSPCommandRequest getPointer(long i) {
        return (AVRTSPCommandRequest)new AVRTSPCommandRequest(this).offsetAddress(i);
    }

    public native AVDictionary headers();

    public native AVRTSPCommandRequest headers(AVDictionary var1);

    @Cast(value={"size_t"})
    public native long body_len();

    public native AVRTSPCommandRequest body_len(long var1);

    @Cast(value={"char*"})
    public native BytePointer body();

    public native AVRTSPCommandRequest body(BytePointer var1);

    static {
        Loader.load();
    }
}

