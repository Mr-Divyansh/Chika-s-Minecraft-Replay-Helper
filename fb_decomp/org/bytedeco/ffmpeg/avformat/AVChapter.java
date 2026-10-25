/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVChapter
extends Pointer {
    public AVChapter() {
        super((Pointer)null);
        this.allocate();
    }

    public AVChapter(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVChapter(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVChapter position(long position) {
        return (AVChapter)super.position(position);
    }

    public AVChapter getPointer(long i) {
        return (AVChapter)new AVChapter(this).offsetAddress(i);
    }

    @Cast(value={"int64_t"})
    public native long id();

    public native AVChapter id(long var1);

    @ByRef
    public native AVRational time_base();

    public native AVChapter time_base(AVRational var1);

    @Cast(value={"int64_t"})
    public native long start();

    public native AVChapter start(long var1);

    @Cast(value={"int64_t"})
    public native long end();

    public native AVChapter end(long var1);

    public native AVDictionary metadata();

    public native AVChapter metadata(AVDictionary var1);

    static {
        Loader.load();
    }
}

