/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVTimecode
extends Pointer {
    public AVTimecode() {
        super((Pointer)null);
        this.allocate();
    }

    public AVTimecode(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVTimecode(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVTimecode position(long position) {
        return (AVTimecode)super.position(position);
    }

    public AVTimecode getPointer(long i) {
        return (AVTimecode)new AVTimecode(this).offsetAddress(i);
    }

    public native int start();

    public native AVTimecode start(int var1);

    @Cast(value={"uint32_t"})
    public native int flags();

    public native AVTimecode flags(int var1);

    @ByRef
    public native AVRational rate();

    public native AVTimecode rate(AVRational var1);

    @Cast(value={"unsigned"})
    public native int fps();

    public native AVTimecode fps(int var1);

    static {
        Loader.load();
    }
}

