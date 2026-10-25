/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVChannelCustom;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVChannelLayout
extends Pointer {
    public AVChannelLayout() {
        super((Pointer)null);
        this.allocate();
    }

    public AVChannelLayout(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVChannelLayout(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVChannelLayout position(long position) {
        return (AVChannelLayout)super.position(position);
    }

    public AVChannelLayout getPointer(long i) {
        return (AVChannelLayout)new AVChannelLayout(this).offsetAddress(i);
    }

    @Cast(value={"AVChannelOrder"})
    public native int order();

    public native AVChannelLayout order(int var1);

    public native int nb_channels();

    public native AVChannelLayout nb_channels(int var1);

    @Name(value={"u.mask"})
    @Cast(value={"uint64_t"})
    public native long u_mask();

    public native AVChannelLayout u_mask(long var1);

    @Name(value={"u.map"})
    public native AVChannelCustom u_map();

    public native AVChannelLayout u_map(AVChannelCustom var1);

    public native Pointer opaque();

    public native AVChannelLayout opaque(Pointer var1);

    static {
        Loader.load();
    }
}

