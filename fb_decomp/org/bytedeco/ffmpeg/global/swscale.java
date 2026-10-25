/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.global;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.swscale.SwsContext;
import org.bytedeco.ffmpeg.swscale.SwsFilter;
import org.bytedeco.ffmpeg.swscale.SwsVector;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.ByPtrPtr;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.NoException;

public class swscale
extends org.bytedeco.ffmpeg.presets.swscale {
    public static final int SWS_DITHER_NONE = 0;
    public static final int SWS_DITHER_AUTO = 1;
    public static final int SWS_DITHER_BAYER = 2;
    public static final int SWS_DITHER_ED = 3;
    public static final int SWS_DITHER_A_DITHER = 4;
    public static final int SWS_DITHER_X_DITHER = 5;
    public static final int SWS_DITHER_NB = 6;
    public static final int SWS_DITHER_MAX_ENUM = Integer.MAX_VALUE;
    public static final int SWS_ALPHA_BLEND_NONE = 0;
    public static final int SWS_ALPHA_BLEND_UNIFORM = 1;
    public static final int SWS_ALPHA_BLEND_CHECKERBOARD = 2;
    public static final int SWS_ALPHA_BLEND_NB = 3;
    public static final int SWS_ALPHA_BLEND_MAX_ENUM = Integer.MAX_VALUE;
    public static final int SWS_FAST_BILINEAR = 1;
    public static final int SWS_BILINEAR = 2;
    public static final int SWS_BICUBIC = 4;
    public static final int SWS_X = 8;
    public static final int SWS_POINT = 16;
    public static final int SWS_AREA = 32;
    public static final int SWS_BICUBLIN = 64;
    public static final int SWS_GAUSS = 128;
    public static final int SWS_SINC = 256;
    public static final int SWS_LANCZOS = 512;
    public static final int SWS_SPLINE = 1024;
    public static final int SWS_STRICT = 2048;
    public static final int SWS_PRINT_INFO = 4096;
    public static final int SWS_FULL_CHR_H_INT = 8192;
    public static final int SWS_FULL_CHR_H_INP = 16384;
    public static final int SWS_ACCURATE_RND = 262144;
    public static final int SWS_BITEXACT = 524288;
    public static final int SWS_UNSTABLE = 0x100000;
    public static final int SWS_DIRECT_BGR = 32768;
    public static final int SWS_ERROR_DIFFUSION = 0x800000;
    public static final int SWS_INTENT_PERCEPTUAL = 0;
    public static final int SWS_INTENT_RELATIVE_COLORIMETRIC = 1;
    public static final int SWS_INTENT_SATURATION = 2;
    public static final int SWS_INTENT_ABSOLUTE_COLORIMETRIC = 3;
    public static final int SWS_INTENT_NB = 4;
    public static final int SWS_SRC_V_CHR_DROP_MASK = 196608;
    public static final int SWS_SRC_V_CHR_DROP_SHIFT = 16;
    public static final int SWS_PARAM_DEFAULT = 123456;
    public static final double SWS_MAX_REDUCE_CUTOFF = 0.002;
    public static final int SWS_CS_ITU709 = 1;
    public static final int SWS_CS_FCC = 4;
    public static final int SWS_CS_ITU601 = 5;
    public static final int SWS_CS_ITU624 = 5;
    public static final int SWS_CS_SMPTE170M = 5;
    public static final int SWS_CS_SMPTE240M = 7;
    public static final int SWS_CS_DEFAULT = 5;
    public static final int SWS_CS_BT2020 = 9;
    public static final int LIBSWSCALE_VERSION_MAJOR = 9;
    public static final int LIBSWSCALE_VERSION_MINOR = 5;
    public static final int LIBSWSCALE_VERSION_MICRO = 102;
    public static final int LIBSWSCALE_VERSION_INT;
    public static final int LIBSWSCALE_BUILD;
    public static final String LIBSWSCALE_IDENT;

    @NoException
    @Cast(value={"unsigned"})
    public static native int swscale_version();

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer swscale_configuration();

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer swscale_license();

    @NoException
    @Const
    public static native AVClass sws_get_class();

    @NoException
    public static native SwsContext sws_alloc_context();

    @NoException
    public static native void sws_free_context(@Cast(value={"SwsContext**"}) PointerPointer var0);

    @NoException
    public static native void sws_free_context(@ByPtrPtr SwsContext var0);

    @NoException
    public static native int sws_test_format(@Cast(value={"AVPixelFormat"}) int var0, int var1);

    @NoException
    public static native int sws_test_hw_format(@Cast(value={"AVPixelFormat"}) int var0);

    @NoException
    public static native int sws_test_colorspace(@Cast(value={"AVColorSpace"}) int var0, int var1);

    @NoException
    public static native int sws_test_primaries(@Cast(value={"AVColorPrimaries"}) int var0, int var1);

    @NoException
    public static native int sws_test_transfer(@Cast(value={"AVColorTransferCharacteristic"}) int var0, int var1);

    @NoException
    public static native int sws_test_frame(@Const AVFrame var0, int var1);

    @NoException
    public static native int sws_frame_setup(SwsContext var0, @Const AVFrame var1, @Const AVFrame var2);

    @NoException
    public static native int sws_is_noop(@Const AVFrame var0, @Const AVFrame var1);

    @NoException
    public static native int sws_scale_frame(SwsContext var0, AVFrame var1, @Const AVFrame var2);

    @NoException
    @Const
    public static native IntPointer sws_getCoefficients(int var0);

    @NoException
    public static native int sws_isSupportedInput(@Cast(value={"AVPixelFormat"}) int var0);

    @NoException
    public static native int sws_isSupportedOutput(@Cast(value={"AVPixelFormat"}) int var0);

    @NoException
    public static native int sws_isSupportedEndiannessConversion(@Cast(value={"AVPixelFormat"}) int var0);

    @NoException
    public static native int sws_init_context(SwsContext var0, SwsFilter var1, SwsFilter var2);

    @NoException
    public static native void sws_freeContext(SwsContext var0);

    @NoException
    public static native SwsContext sws_getContext(int var0, int var1, @Cast(value={"AVPixelFormat"}) int var2, int var3, int var4, @Cast(value={"AVPixelFormat"}) int var5, int var6, SwsFilter var7, SwsFilter var8, @Const DoublePointer var9);

    @NoException
    public static native SwsContext sws_getContext(int var0, int var1, @Cast(value={"AVPixelFormat"}) int var2, int var3, int var4, @Cast(value={"AVPixelFormat"}) int var5, int var6, SwsFilter var7, SwsFilter var8, @Const DoubleBuffer var9);

    @NoException
    public static native SwsContext sws_getContext(int var0, int var1, @Cast(value={"AVPixelFormat"}) int var2, int var3, int var4, @Cast(value={"AVPixelFormat"}) int var5, int var6, SwsFilter var7, SwsFilter var8, @Const double[] var9);

    @NoException
    public static native int sws_scale(SwsContext var0, @Cast(value={"const uint8_t*const*"}) PointerPointer var1, @Const IntPointer var2, int var3, int var4, @Cast(value={"uint8_t*const*"}) PointerPointer var5, @Const IntPointer var6);

    @NoException
    public static native int sws_scale(SwsContext var0, @Cast(value={"const uint8_t*const*"}) @ByPtrPtr BytePointer var1, @Const IntPointer var2, int var3, int var4, @Cast(value={"uint8_t*const*"}) @ByPtrPtr BytePointer var5, @Const IntPointer var6);

    @NoException
    public static native int sws_scale(SwsContext var0, @Cast(value={"const uint8_t*const*"}) @ByPtrPtr ByteBuffer var1, @Const IntBuffer var2, int var3, int var4, @Cast(value={"uint8_t*const*"}) @ByPtrPtr ByteBuffer var5, @Const IntBuffer var6);

    @NoException
    public static native int sws_scale(SwsContext var0, @Cast(value={"const uint8_t*const*"}) @ByPtrPtr byte[] var1, @Const int[] var2, int var3, int var4, @Cast(value={"uint8_t*const*"}) @ByPtrPtr byte[] var5, @Const int[] var6);

    @NoException
    public static native int sws_frame_start(SwsContext var0, AVFrame var1, @Const AVFrame var2);

    @NoException
    public static native void sws_frame_end(SwsContext var0);

    @NoException
    public static native int sws_send_slice(SwsContext var0, @Cast(value={"unsigned int"}) int var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    public static native int sws_receive_slice(SwsContext var0, @Cast(value={"unsigned int"}) int var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    @Cast(value={"unsigned int"})
    public static native int sws_receive_slice_alignment(@Const SwsContext var0);

    @NoException
    public static native int sws_setColorspaceDetails(SwsContext var0, @Const IntPointer var1, int var2, @Const IntPointer var3, int var4, int var5, int var6, int var7);

    @NoException
    public static native int sws_setColorspaceDetails(SwsContext var0, @Const IntBuffer var1, int var2, @Const IntBuffer var3, int var4, int var5, int var6, int var7);

    @NoException
    public static native int sws_setColorspaceDetails(SwsContext var0, @Const int[] var1, int var2, @Const int[] var3, int var4, int var5, int var6, int var7);

    @NoException
    public static native int sws_getColorspaceDetails(SwsContext var0, @Cast(value={"int**"}) PointerPointer var1, IntPointer var2, @Cast(value={"int**"}) PointerPointer var3, IntPointer var4, IntPointer var5, IntPointer var6, IntPointer var7);

    @NoException
    public static native int sws_getColorspaceDetails(SwsContext var0, @ByPtrPtr IntPointer var1, IntPointer var2, @ByPtrPtr IntPointer var3, IntPointer var4, IntPointer var5, IntPointer var6, IntPointer var7);

    @NoException
    public static native int sws_getColorspaceDetails(SwsContext var0, @ByPtrPtr IntBuffer var1, IntBuffer var2, @ByPtrPtr IntBuffer var3, IntBuffer var4, IntBuffer var5, IntBuffer var6, IntBuffer var7);

    @NoException
    public static native int sws_getColorspaceDetails(SwsContext var0, @ByPtrPtr int[] var1, int[] var2, @ByPtrPtr int[] var3, int[] var4, int[] var5, int[] var6, int[] var7);

    @NoException
    public static native SwsVector sws_allocVec(int var0);

    @NoException
    public static native SwsVector sws_getGaussianVec(double var0, double var2);

    @NoException
    public static native void sws_scaleVec(SwsVector var0, double var1);

    @NoException
    public static native void sws_normalizeVec(SwsVector var0, double var1);

    @NoException
    public static native void sws_freeVec(SwsVector var0);

    @NoException
    public static native SwsFilter sws_getDefaultFilter(float var0, float var1, float var2, float var3, float var4, float var5, int var6);

    @NoException
    public static native void sws_freeFilter(SwsFilter var0);

    @NoException
    public static native SwsContext sws_getCachedContext(SwsContext var0, int var1, int var2, @Cast(value={"AVPixelFormat"}) int var3, int var4, int var5, @Cast(value={"AVPixelFormat"}) int var6, int var7, SwsFilter var8, SwsFilter var9, @Const DoublePointer var10);

    @NoException
    public static native SwsContext sws_getCachedContext(SwsContext var0, int var1, int var2, @Cast(value={"AVPixelFormat"}) int var3, int var4, int var5, @Cast(value={"AVPixelFormat"}) int var6, int var7, SwsFilter var8, SwsFilter var9, @Const DoubleBuffer var10);

    @NoException
    public static native SwsContext sws_getCachedContext(SwsContext var0, int var1, int var2, @Cast(value={"AVPixelFormat"}) int var3, int var4, int var5, @Cast(value={"AVPixelFormat"}) int var6, int var7, SwsFilter var8, SwsFilter var9, @Const double[] var10);

    @NoException
    public static native void sws_convertPalette8ToPacked32(@Cast(value={"const uint8_t*"}) BytePointer var0, @Cast(value={"uint8_t*"}) BytePointer var1, int var2, @Cast(value={"const uint8_t*"}) BytePointer var3);

    @NoException
    public static native void sws_convertPalette8ToPacked32(@Cast(value={"const uint8_t*"}) ByteBuffer var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, int var2, @Cast(value={"const uint8_t*"}) ByteBuffer var3);

    @NoException
    public static native void sws_convertPalette8ToPacked32(@Cast(value={"const uint8_t*"}) byte[] var0, @Cast(value={"uint8_t*"}) byte[] var1, int var2, @Cast(value={"const uint8_t*"}) byte[] var3);

    @NoException
    public static native void sws_convertPalette8ToPacked24(@Cast(value={"const uint8_t*"}) BytePointer var0, @Cast(value={"uint8_t*"}) BytePointer var1, int var2, @Cast(value={"const uint8_t*"}) BytePointer var3);

    @NoException
    public static native void sws_convertPalette8ToPacked24(@Cast(value={"const uint8_t*"}) ByteBuffer var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, int var2, @Cast(value={"const uint8_t*"}) ByteBuffer var3);

    @NoException
    public static native void sws_convertPalette8ToPacked24(@Cast(value={"const uint8_t*"}) byte[] var0, @Cast(value={"uint8_t*"}) byte[] var1, int var2, @Cast(value={"const uint8_t*"}) byte[] var3);

    @MemberGetter
    public static native int LIBSWSCALE_VERSION_INT();

    @MemberGetter
    public static native String LIBSWSCALE_IDENT();

    static {
        Loader.load();
        LIBSWSCALE_BUILD = LIBSWSCALE_VERSION_INT = swscale.LIBSWSCALE_VERSION_INT();
        LIBSWSCALE_IDENT = swscale.LIBSWSCALE_IDENT();
    }
}

