/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.swscale;

import org.bytedeco.ffmpeg.presets.swscale;
import org.bytedeco.ffmpeg.swscale.SwsVector;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={swscale.class})
public class SwsFilter
extends Pointer {
    public SwsFilter() {
        super((Pointer)null);
        this.allocate();
    }

    public SwsFilter(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public SwsFilter(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public SwsFilter position(long position) {
        return (SwsFilter)super.position(position);
    }

    public SwsFilter getPointer(long i) {
        return (SwsFilter)new SwsFilter(this).offsetAddress(i);
    }

    public native SwsVector lumH();

    public native SwsFilter lumH(SwsVector var1);

    public native SwsVector lumV();

    public native SwsFilter lumV(SwsVector var1);

    public native SwsVector chrH();

    public native SwsFilter chrH(SwsVector var1);

    public native SwsVector chrV();

    public native SwsFilter chrV(SwsVector var1);

    static {
        Loader.load();
    }
}

