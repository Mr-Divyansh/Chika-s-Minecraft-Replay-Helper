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
public class AVBlowfish
extends Pointer {
    public AVBlowfish() {
        super((Pointer)null);
        this.allocate();
    }

    public AVBlowfish(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVBlowfish(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVBlowfish position(long position) {
        return (AVBlowfish)super.position(position);
    }

    public AVBlowfish getPointer(long i) {
        return (AVBlowfish)new AVBlowfish(this).offsetAddress(i);
    }

    @Cast(value={"uint32_t"})
    public native int p(int var1);

    public native AVBlowfish p(int var1, int var2);

    @MemberGetter
    @Cast(value={"uint32_t*"})
    public native IntPointer p();

    @Cast(value={"uint32_t"})
    public native int s(int var1, int var2);

    public native AVBlowfish s(int var1, int var2, int var3);

    @MemberGetter
    @Cast(value={"uint32_t(* /*[4]*/ )[256]"})
    public native IntPointer s();

    static {
        Loader.load();
    }
}

