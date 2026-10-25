/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class unaligned_16
extends Pointer {
    public unaligned_16() {
        super((Pointer)null);
        this.allocate();
    }

    public unaligned_16(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public unaligned_16(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public unaligned_16 position(long position) {
        return (unaligned_16)super.position(position);
    }

    public unaligned_16 getPointer(long i) {
        return (unaligned_16)new unaligned_16(this).offsetAddress(i);
    }

    @Cast(value={"uint16_t"})
    public native short l();

    public native unaligned_16 l(short var1);

    static {
        Loader.load();
    }
}

