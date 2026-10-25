/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avfilter;

import org.bytedeco.ffmpeg.avfilter.AVFilterPad;
import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.presets.avfilter;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avfilter.class})
public class AVFilter
extends Pointer {
    public AVFilter() {
        super((Pointer)null);
        this.allocate();
    }

    public AVFilter(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVFilter(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVFilter position(long position) {
        return (AVFilter)super.position(position);
    }

    public AVFilter getPointer(long i) {
        return (AVFilter)new AVFilter(this).offsetAddress(i);
    }

    @Cast(value={"const char*"})
    public native BytePointer name();

    public native AVFilter name(BytePointer var1);

    @Cast(value={"const char*"})
    public native BytePointer description();

    public native AVFilter description(BytePointer var1);

    @Const
    public native AVFilterPad inputs();

    public native AVFilter inputs(AVFilterPad var1);

    @Const
    public native AVFilterPad outputs();

    public native AVFilter outputs(AVFilterPad var1);

    @Const
    public native AVClass priv_class();

    public native AVFilter priv_class(AVClass var1);

    public native int flags();

    public native AVFilter flags(int var1);

    static {
        Loader.load();
    }
}

