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
public class AVLFG
extends Pointer {
    public AVLFG() {
        super((Pointer)null);
        this.allocate();
    }

    public AVLFG(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVLFG(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVLFG position(long position) {
        return (AVLFG)super.position(position);
    }

    public AVLFG getPointer(long i) {
        return (AVLFG)new AVLFG(this).offsetAddress(i);
    }

    @Cast(value={"unsigned int"})
    public native int state(int var1);

    public native AVLFG state(int var1, int var2);

    @MemberGetter
    @Cast(value={"unsigned int*"})
    public native IntPointer state();

    public native int index();

    public native AVLFG index(int var1);

    static {
        Loader.load();
    }
}

