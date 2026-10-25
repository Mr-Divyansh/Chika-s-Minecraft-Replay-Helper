/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.swscale;

import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.presets.swscale;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={swscale.class})
public class SwsContext
extends Pointer {
    public SwsContext() {
        super((Pointer)null);
        this.allocate();
    }

    public SwsContext(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public SwsContext(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public SwsContext position(long position) {
        return (SwsContext)super.position(position);
    }

    public SwsContext getPointer(long i) {
        return (SwsContext)new SwsContext(this).offsetAddress(i);
    }

    @Const
    public native AVClass av_class();

    public native SwsContext av_class(AVClass var1);

    public native Pointer opaque();

    public native SwsContext opaque(Pointer var1);

    @Cast(value={"unsigned"})
    public native int flags();

    public native SwsContext flags(int var1);

    public native double scaler_params(int var1);

    public native SwsContext scaler_params(int var1, double var2);

    @MemberGetter
    public native DoublePointer scaler_params();

    public native int threads();

    public native SwsContext threads(int var1);

    @Cast(value={"SwsDither"})
    public native int dither();

    public native SwsContext dither(int var1);

    @Cast(value={"SwsAlphaBlend"})
    public native int alpha_blend();

    public native SwsContext alpha_blend(int var1);

    public native int gamma_flag();

    public native SwsContext gamma_flag(int var1);

    public native int src_w();

    public native SwsContext src_w(int var1);

    public native int src_h();

    public native SwsContext src_h(int var1);

    public native int dst_w();

    public native SwsContext dst_w(int var1);

    public native int dst_h();

    public native SwsContext dst_h(int var1);

    public native int src_format();

    public native SwsContext src_format(int var1);

    public native int dst_format();

    public native SwsContext dst_format(int var1);

    public native int src_range();

    public native SwsContext src_range(int var1);

    public native int dst_range();

    public native SwsContext dst_range(int var1);

    public native int src_v_chr_pos();

    public native SwsContext src_v_chr_pos(int var1);

    public native int src_h_chr_pos();

    public native SwsContext src_h_chr_pos(int var1);

    public native int dst_v_chr_pos();

    public native SwsContext dst_v_chr_pos(int var1);

    public native int dst_h_chr_pos();

    public native SwsContext dst_h_chr_pos(int var1);

    public native int intent();

    public native SwsContext intent(int var1);

    static {
        Loader.load();
    }
}

