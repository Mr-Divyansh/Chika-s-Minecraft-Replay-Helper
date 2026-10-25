/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVXTEA
extends Pointer {
    public AVXTEA() {
        super((Pointer)null);
        this.allocate();
    }

    public AVXTEA(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVXTEA(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVXTEA position(long position) {
        return (AVXTEA)super.position(position);
    }

    public AVXTEA getPointer(long i) {
        return (AVXTEA)new AVXTEA(this).offsetAddress(i);
    }

    @Cast(value={"uint32_t"})
    public native int key(int var1);

    public native AVXTEA key(int var1, int var2);

    @MemberGetter
    @Cast(value={"uint32_t*"})
    public native IntPointer key();

    static {
        Loader.load();
    }
}

