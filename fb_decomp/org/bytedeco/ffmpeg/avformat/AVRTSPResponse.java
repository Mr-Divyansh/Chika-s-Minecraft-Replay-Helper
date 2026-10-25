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
public class AVRTSPResponse
extends Pointer {
    public AVRTSPResponse() {
        super((Pointer)null);
        this.allocate();
    }

    public AVRTSPResponse(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVRTSPResponse(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVRTSPResponse position(long position) {
        return (AVRTSPResponse)super.position(position);
    }

    public AVRTSPResponse getPointer(long i) {
        return (AVRTSPResponse)new AVRTSPResponse(this).offsetAddress(i);
    }

    public native int status_code();

    public native AVRTSPResponse status_code(int var1);

    @Cast(value={"char*"})
    public native BytePointer reason();

    public native AVRTSPResponse reason(BytePointer var1);

    @Cast(value={"size_t"})
    public native long body_len();

    public native AVRTSPResponse body_len(long var1);

    @Cast(value={"unsigned char*"})
    public native BytePointer body();

    public native AVRTSPResponse body(BytePointer var1);

    static {
        Loader.load();
    }
}

