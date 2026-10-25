/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVMasteringDisplayMetadata
extends Pointer {
    public AVMasteringDisplayMetadata() {
        super((Pointer)null);
        this.allocate();
    }

    public AVMasteringDisplayMetadata(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVMasteringDisplayMetadata(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVMasteringDisplayMetadata position(long position) {
        return (AVMasteringDisplayMetadata)super.position(position);
    }

    public AVMasteringDisplayMetadata getPointer(long i) {
        return (AVMasteringDisplayMetadata)new AVMasteringDisplayMetadata(this).offsetAddress(i);
    }

    @ByRef
    public native AVRational display_primaries(int var1, int var2);

    public native AVMasteringDisplayMetadata display_primaries(int var1, int var2, AVRational var3);

    @MemberGetter
    @Cast(value={"AVRational(* /*[3]*/ )[2]"})
    public native AVRational display_primaries();

    @ByRef
    public native AVRational white_point(int var1);

    public native AVMasteringDisplayMetadata white_point(int var1, AVRational var2);

    @MemberGetter
    public native AVRational white_point();

    @ByRef
    public native AVRational min_luminance();

    public native AVMasteringDisplayMetadata min_luminance(AVRational var1);

    @ByRef
    public native AVRational max_luminance();

    public native AVMasteringDisplayMetadata max_luminance(AVRational var1);

    public native int has_primaries();

    public native AVMasteringDisplayMetadata has_primaries(int var1);

    public native int has_luminance();

    public native AVMasteringDisplayMetadata has_luminance(int var1);

    static {
        Loader.load();
    }
}

