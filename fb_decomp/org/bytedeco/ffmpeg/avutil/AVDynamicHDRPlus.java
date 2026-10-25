/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVHDRPlusColorTransformParams;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVDynamicHDRPlus
extends Pointer {
    public AVDynamicHDRPlus() {
        super((Pointer)null);
        this.allocate();
    }

    public AVDynamicHDRPlus(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVDynamicHDRPlus(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVDynamicHDRPlus position(long position) {
        return (AVDynamicHDRPlus)super.position(position);
    }

    public AVDynamicHDRPlus getPointer(long i) {
        return (AVDynamicHDRPlus)new AVDynamicHDRPlus(this).offsetAddress(i);
    }

    @Cast(value={"uint8_t"})
    public native byte itu_t_t35_country_code();

    public native AVDynamicHDRPlus itu_t_t35_country_code(byte var1);

    @Cast(value={"uint8_t"})
    public native byte application_version();

    public native AVDynamicHDRPlus application_version(byte var1);

    @Cast(value={"uint8_t"})
    public native byte num_windows();

    public native AVDynamicHDRPlus num_windows(byte var1);

    @ByRef
    public native AVHDRPlusColorTransformParams params(int var1);

    public native AVDynamicHDRPlus params(int var1, AVHDRPlusColorTransformParams var2);

    @MemberGetter
    public native AVHDRPlusColorTransformParams params();

    @ByRef
    public native AVRational targeted_system_display_maximum_luminance();

    public native AVDynamicHDRPlus targeted_system_display_maximum_luminance(AVRational var1);

    @Cast(value={"uint8_t"})
    public native byte targeted_system_display_actual_peak_luminance_flag();

    public native AVDynamicHDRPlus targeted_system_display_actual_peak_luminance_flag(byte var1);

    @Cast(value={"uint8_t"})
    public native byte num_rows_targeted_system_display_actual_peak_luminance();

    public native AVDynamicHDRPlus num_rows_targeted_system_display_actual_peak_luminance(byte var1);

    @Cast(value={"uint8_t"})
    public native byte num_cols_targeted_system_display_actual_peak_luminance();

    public native AVDynamicHDRPlus num_cols_targeted_system_display_actual_peak_luminance(byte var1);

    @ByRef
    public native AVRational targeted_system_display_actual_peak_luminance(int var1, int var2);

    public native AVDynamicHDRPlus targeted_system_display_actual_peak_luminance(int var1, int var2, AVRational var3);

    @MemberGetter
    @Cast(value={"AVRational(* /*[25]*/ )[25]"})
    public native AVRational targeted_system_display_actual_peak_luminance();

    @Cast(value={"uint8_t"})
    public native byte mastering_display_actual_peak_luminance_flag();

    public native AVDynamicHDRPlus mastering_display_actual_peak_luminance_flag(byte var1);

    @Cast(value={"uint8_t"})
    public native byte num_rows_mastering_display_actual_peak_luminance();

    public native AVDynamicHDRPlus num_rows_mastering_display_actual_peak_luminance(byte var1);

    @Cast(value={"uint8_t"})
    public native byte num_cols_mastering_display_actual_peak_luminance();

    public native AVDynamicHDRPlus num_cols_mastering_display_actual_peak_luminance(byte var1);

    @ByRef
    public native AVRational mastering_display_actual_peak_luminance(int var1, int var2);

    public native AVDynamicHDRPlus mastering_display_actual_peak_luminance(int var1, int var2, AVRational var3);

    @MemberGetter
    @Cast(value={"AVRational(* /*[25]*/ )[25]"})
    public native AVRational mastering_display_actual_peak_luminance();

    static {
        Loader.load();
    }
}

