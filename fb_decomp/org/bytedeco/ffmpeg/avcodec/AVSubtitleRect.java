/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVSubtitleRect
extends Pointer {
    public AVSubtitleRect() {
        super((Pointer)null);
        this.allocate();
    }

    public AVSubtitleRect(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVSubtitleRect(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVSubtitleRect position(long position) {
        return (AVSubtitleRect)super.position(position);
    }

    public AVSubtitleRect getPointer(long i) {
        return (AVSubtitleRect)new AVSubtitleRect(this).offsetAddress(i);
    }

    public native int x();

    public native AVSubtitleRect x(int var1);

    public native int y();

    public native AVSubtitleRect y(int var1);

    public native int w();

    public native AVSubtitleRect w(int var1);

    public native int h();

    public native AVSubtitleRect h(int var1);

    public native int nb_colors();

    public native AVSubtitleRect nb_colors(int var1);

    @Cast(value={"uint8_t*"})
    public native BytePointer data(int var1);

    public native AVSubtitleRect data(int var1, BytePointer var2);

    @MemberGetter
    @Cast(value={"uint8_t**"})
    public native PointerPointer data();

    public native int linesize(int var1);

    public native AVSubtitleRect linesize(int var1, int var2);

    @MemberGetter
    public native IntPointer linesize();

    public native int flags();

    public native AVSubtitleRect flags(int var1);

    @Cast(value={"AVSubtitleType"})
    public native int type();

    public native AVSubtitleRect type(int var1);

    @Cast(value={"char*"})
    public native BytePointer text();

    public native AVSubtitleRect text(BytePointer var1);

    @Cast(value={"char*"})
    public native BytePointer ass();

    public native AVSubtitleRect ass(BytePointer var1);

    static {
        Loader.load();
    }
}

