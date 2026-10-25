/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVPanScan
extends Pointer {
    public AVPanScan() {
        super((Pointer)null);
        this.allocate();
    }

    public AVPanScan(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVPanScan(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVPanScan position(long position) {
        return (AVPanScan)super.position(position);
    }

    public AVPanScan getPointer(long i) {
        return (AVPanScan)new AVPanScan(this).offsetAddress(i);
    }

    public native int id();

    public native AVPanScan id(int var1);

    public native int width();

    public native AVPanScan width(int var1);

    public native int height();

    public native AVPanScan height(int var1);

    @Name(value={"position"})
    public native short _position(int var1, int var2);

    public native AVPanScan _position(int var1, int var2, short var3);

    @MemberGetter
    @Cast(value={"int16_t(* /*[3]*/ )[2]"})
    @Name(value={"position"})
    public native ShortPointer _position();

    static {
        Loader.load();
    }
}

