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
public class unaligned_64
extends Pointer {
    public unaligned_64() {
        super((Pointer)null);
        this.allocate();
    }

    public unaligned_64(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public unaligned_64(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public unaligned_64 position(long position) {
        return (unaligned_64)super.position(position);
    }

    public unaligned_64 getPointer(long i) {
        return (unaligned_64)new unaligned_64(this).offsetAddress(i);
    }

    @Cast(value={"uint64_t"})
    public native long l();

    public native unaligned_64 l(long var1);

    static {
        Loader.load();
    }
}

