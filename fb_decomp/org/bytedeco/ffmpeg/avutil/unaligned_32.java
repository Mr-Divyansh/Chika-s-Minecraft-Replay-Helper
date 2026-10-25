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
public class unaligned_32
extends Pointer {
    public unaligned_32() {
        super((Pointer)null);
        this.allocate();
    }

    public unaligned_32(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public unaligned_32(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public unaligned_32 position(long position) {
        return (unaligned_32)super.position(position);
    }

    public unaligned_32 getPointer(long i) {
        return (unaligned_32)new unaligned_32(this).offsetAddress(i);
    }

    @Cast(value={"uint32_t"})
    public native int l();

    public native unaligned_32 l(int var1);

    static {
        Loader.load();
    }
}

