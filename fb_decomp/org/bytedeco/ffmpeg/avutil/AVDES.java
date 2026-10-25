/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.LongPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVDES
extends Pointer {
    public AVDES() {
        super((Pointer)null);
        this.allocate();
    }

    public AVDES(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVDES(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVDES position(long position) {
        return (AVDES)super.position(position);
    }

    public AVDES getPointer(long i) {
        return (AVDES)new AVDES(this).offsetAddress(i);
    }

    @Cast(value={"uint64_t"})
    public native long round_keys(int var1, int var2);

    public native AVDES round_keys(int var1, int var2, long var3);

    @MemberGetter
    @Cast(value={"uint64_t(* /*[3]*/ )[16]"})
    public native LongPointer round_keys();

    public native int triple_des();

    public native AVDES triple_des(int var1);

    static {
        Loader.load();
    }
}

