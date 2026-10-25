/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.avcodec.AVSubtitleRect;
import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVSubtitle
extends Pointer {
    public AVSubtitle() {
        super((Pointer)null);
        this.allocate();
    }

    public AVSubtitle(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVSubtitle(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVSubtitle position(long position) {
        return (AVSubtitle)super.position(position);
    }

    public AVSubtitle getPointer(long i) {
        return (AVSubtitle)new AVSubtitle(this).offsetAddress(i);
    }

    @Cast(value={"uint16_t"})
    public native short format();

    public native AVSubtitle format(short var1);

    @Cast(value={"uint32_t"})
    public native int start_display_time();

    public native AVSubtitle start_display_time(int var1);

    @Cast(value={"uint32_t"})
    public native int end_display_time();

    public native AVSubtitle end_display_time(int var1);

    @Cast(value={"unsigned"})
    public native int num_rects();

    public native AVSubtitle num_rects(int var1);

    public native AVSubtitleRect rects(int var1);

    public native AVSubtitle rects(int var1, AVSubtitleRect var2);

    @Cast(value={"AVSubtitleRect**"})
    public native PointerPointer rects();

    public native AVSubtitle rects(PointerPointer var1);

    @Cast(value={"int64_t"})
    public native long pts();

    public native AVSubtitle pts(long var1);

    static {
        Loader.load();
    }
}

