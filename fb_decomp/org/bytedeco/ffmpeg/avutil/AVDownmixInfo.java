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
public class AVDownmixInfo
extends Pointer {
    public AVDownmixInfo() {
        super((Pointer)null);
        this.allocate();
    }

    public AVDownmixInfo(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVDownmixInfo(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVDownmixInfo position(long position) {
        return (AVDownmixInfo)super.position(position);
    }

    public AVDownmixInfo getPointer(long i) {
        return (AVDownmixInfo)new AVDownmixInfo(this).offsetAddress(i);
    }

    @Cast(value={"AVDownmixType"})
    public native int preferred_downmix_type();

    public native AVDownmixInfo preferred_downmix_type(int var1);

    public native double center_mix_level();

    public native AVDownmixInfo center_mix_level(double var1);

    public native double center_mix_level_ltrt();

    public native AVDownmixInfo center_mix_level_ltrt(double var1);

    public native double surround_mix_level();

    public native AVDownmixInfo surround_mix_level(double var1);

    public native double surround_mix_level_ltrt();

    public native AVDownmixInfo surround_mix_level_ltrt(double var1);

    public native double lfe_mix_level();

    public native AVDownmixInfo lfe_mix_level(double var1);

    static {
        Loader.load();
    }
}

