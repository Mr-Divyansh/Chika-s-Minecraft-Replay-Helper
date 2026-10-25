/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVRC4
extends Pointer {
    public AVRC4() {
        super((Pointer)null);
        this.allocate();
    }

    public AVRC4(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVRC4(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVRC4 position(long position) {
        return (AVRC4)super.position(position);
    }

    public AVRC4 getPointer(long i) {
        return (AVRC4)new AVRC4(this).offsetAddress(i);
    }

    @Cast(value={"uint8_t"})
    public native byte state(int var1);

    public native AVRC4 state(int var1, byte var2);

    @MemberGetter
    @Cast(value={"uint8_t*"})
    public native BytePointer state();

    public native int x();

    public native AVRC4 x(int var1);

    public native int y();

    public native AVRC4 y(int var1);

    static {
        Loader.load();
    }
}

