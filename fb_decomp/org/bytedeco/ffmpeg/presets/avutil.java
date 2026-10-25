/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.presets;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.NoException;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(inherit={javacpp.class}, target="org.bytedeco.ffmpeg.avutil", global="org.bytedeco.ffmpeg.global.avutil", value={@Platform(define={"__STDC_CONSTANT_MACROS", "__STDC_FORMAT_MACROS", "STRING_BYTES_CHARSET \"UTF-8\""}, cinclude={"<libavutil/avutil.h>", "<libavutil/error.h>", "<libavutil/mem.h>", "<libavutil/time.h>", "<libavutil/mathematics.h>", "<libavutil/rational.h>", "<libavutil/log.h>", "<libavutil/buffer.h>", "<libavutil/pixfmt.h>", "<libavutil/frame.h>", "<libavutil/samplefmt.h>", "<libavutil/channel_layout.h>", "<libavutil/cpu.h>", "<libavutil/dict.h>", "<libavutil/opt.h>", "<libavutil/pixdesc.h>", "<libavutil/imgutils.h>", "<libavutil/downmix_info.h>", "<libavutil/stereo3d.h>", "<libavutil/ffversion.h>", "<libavutil/motion_vector.h>", "<libavutil/fifo.h>", "<libavutil/audio_fifo.h>", "<libavutil/hwcontext.h>", "<libavutil/adler32.h>", "<libavutil/aes.h>", "<libavutil/aes_ctr.h>", "<libavutil/base64.h>", "<libavutil/blowfish.h>", "<libavutil/cast5.h>", "<libavutil/camellia.h>", "<libavutil/crc.h>", "<libavutil/des.h>", "<libavutil/lfg.h>", "<libavutil/hmac.h>", "<libavutil/md5.h>", "<libavutil/rc4.h>", "<libavutil/ripemd.h>", "<libavutil/tea.h>", "<libavutil/twofish.h>", "<libavutil/sha.h>", "<libavutil/sha512.h>", "<libavutil/xtea.h>", "<libavutil/avstring.h>", "<libavutil/bprint.h>", "<libavutil/common.h>", "<libavutil/display.h>", "<libavutil/eval.h>", "<libavutil/encryption_info.h>", "<libavutil/file.h>", "<libavutil/hash.h>", "<libavutil/hdr_dynamic_metadata.h>", "<libavutil/intfloat.h>", "<libavutil/intreadwrite.h>", "<libavutil/mastering_display_metadata.h>", "<libavutil/murmur3.h>", "<libavutil/parseutils.h>", "<libavutil/pixelutils.h>", "<libavutil/random_seed.h>", "<libavutil/replaygain.h>", "<libavutil/spherical.h>", "<libavutil/threadmessage.h>", "<libavutil/timecode.h>", "<libavutil/timestamp.h>", "<libavutil/tree.h>", "<libavutil/container_fifo.h>", "<libavutil/tx.h>", "<libavutil/version.h>", "<libavutil/macros.h>", "log_callback.h"}, includepath={"/usr/local/include/ffmpeg/", "/opt/local/include/ffmpeg/", "/usr/include/ffmpeg/"}, link={"avutil@.60"}, compiler={"default", "nodeprecated"}), @Platform(value={"linux-x86"}, preload={"va@.2", "drm@.2", "va-drm@.2"}, preloadpath={"/usr/lib32/", "/usr/lib/"}), @Platform(value={"linux-x86_64"}, preloadpath={"/usr/lib64/", "/usr/lib/"}), @Platform(value={"macosx"}, preload={"atomic@.1"}, preloadpath={"/usr/local/lib/gcc/13/", "/usr/local/lib/gcc/12/", "/usr/local/lib/gcc/11/", "/usr/local/lib/gcc/10/", "/usr/local/lib/gcc/9/", "/usr/local/lib/gcc/8/", "/usr/local/lib/gcc/7/", "/usr/local/lib/gcc/6/", "/usr/local/lib/gcc/5/"}), @Platform(value={"windows"}, includepath={"C:/MinGW/local/include/ffmpeg/", "C:/MinGW/include/ffmpeg/"}, preload={"libwinpthread-1", "avutil-60"}), @Platform(extension={"-gpl"})})
public class avutil
implements InfoMapper {
    @Override
    public void map(InfoMap infoMap) {
        infoMap.put(new Info("AV_NOPTS_VALUE").cppTypes("int64_t").translate(false)).put(new Info("NAN", "INFINITY").cppTypes("double")).put(new Info("AV_TIME_BASE_Q", "PixelFormat", "CodecID", "AVCOL_SPC_YCGCO", "AVCOL_SPC_YCOCG", "FF_CEIL_RSHIFT", "av_ceil_log2", "av_clip", "av_clip64", "av_clip_uint8", "av_clip_int8", "av_clip_uint16", "av_clip_int16", "av_clipl_int32", "av_clip_intp2", "av_clip_uintp2", "av_mod_uintp2", "av_sat_add32", "av_sat_dadd32", "av_sat_sub32", "av_sat_dsub32", "av_clipf", "av_clipd", "av_popcount", "av_popcount64", "av_parity", "av_sat_add64", "av_sat_sub64", "av_zero_extend", "LIBAVUTIL_VERSION").cppTypes(new String[0]).translate()).put(new Info("LIBAVUTIL_VERSION_INT", "LIBAVUTIL_IDENT").translate(false)).put(new Info("FF_API_D2STR", "FF_API_DECLARE_ALIGNED", "FF_API_COLORSPACE_NAME", "FF_API_AV_MALLOCZ_ARRAY", "FF_API_FIFO_PEEK2", "FF_API_FIFO_OLD_API", "FF_API_XVMC", "FF_API_OLD_CHANNEL_LAYOUT", "FF_API_AV_FOPEN_UTF8", "FF_API_PKT_DURATION", "FF_API_REORDERED_OPAQUE", "FF_API_FRAME_PICTURE_NUMBER", "FF_API_HDR_VIVID_THREE_SPLINE", "FF_API_FRAME_PKT", "FF_API_INTERLACED_FRAME", "FF_API_FRAME_KEY", "FF_API_PALETTE_HAS_CHANGED", "FF_API_VULKAN_CONTIGUOUS_MEMORY", "FF_API_H274_FILM_GRAIN_VCS", "FF_API_MOD_UINTP2", "FF_API_RISCV_FD_ZBA", "FF_API_VULKAN_FIXED_QUEUES", "FF_API_OPT_INT_LIST", "FF_API_OPT_PTR", "FF_API_CPU_FLAG_FORCE", "FF_API_DOVI_L11_INVALID_PROPS", "FF_API_ASSERT_FPU", "FF_API_VULKAN_SYNC_QUEUES").define().translate().cppTypes("bool")).put(new Info("av_const").annotations("@Const")).put(new Info("FF_CONST_AVUTIL55").annotations(new String[0])).put(new Info("av_malloc_attrib", "av_alloc_size", "av_always_inline", "av_warn_unused_result", "av_alias").cppTypes(new String[0]).annotations(new String[0])).put(new Info("attribute_deprecated").annotations("@Deprecated")).put(new Info("DWORD", "UINT").cast().valueTypes("int").pointerTypes("IntPointer", "IntBuffer", "int[]")).put(new Info("AVPanScan", "AVCodecContext", "AVMurMur3", "CUcontext", "CUstream", "ID3D11Device", "ID3D11DeviceContext", "ID3D11Texture2D", "ID3D11VideoContext", "ID3D11VideoDevice", "IDirect3DDeviceManager9", "IDirect3DSurface9", "IDirectXVideoDecoder", "mfxFrameSurface1", "mfxSession", "VAConfigID", "VASurfaceID", "VASurfaceAttrib", "VADisplay", "VdpDevice", "VdpGetProcAddress").cast().pointerTypes("Pointer")).put(new Info("FF_API_VAAPI").define()).put(new Info("AV_PIX_FMT_ABI_GIT_MASTER", "AV_HAVE_INCOMPATIBLE_LIBAV_ABI", "!FF_API_XVMC", "FF_API_GET_BITS_PER_SAMPLE_FMT", "FF_API_FIND_OPT").define(false)).put(new Info("FF_API_BUFFER_SIZE_T", "FF_API_CRYPTO_SIZE_T").define(true)).put(new Info("ff_check_pixfmt_descriptors").skip()).put(new Info("AV_CH_FRONT_LEFT", "AV_CH_FRONT_RIGHT", "AV_CH_FRONT_CENTER", "AV_CH_LOW_FREQUENCY", "AV_CH_BACK_LEFT", "AV_CH_BACK_RIGHT", "AV_CH_FRONT_LEFT_OF_CENTER", "AV_CH_FRONT_RIGHT_OF_CENTER", "AV_CH_BACK_CENTER", "AV_CH_SIDE_LEFT", "AV_CH_SIDE_RIGHT", "AV_CH_TOP_CENTER", "AV_CH_TOP_FRONT_LEFT", "AV_CH_TOP_FRONT_CENTER", "AV_CH_TOP_FRONT_RIGHT", "AV_CH_TOP_BACK_LEFT", "AV_CH_TOP_BACK_CENTER", "AV_CH_TOP_BACK_RIGHT", "AV_CH_STEREO_LEFT", "AV_CH_STEREO_RIGHT", "AV_CH_WIDE_LEFT", "AV_CH_WIDE_RIGHT", "AV_CH_SURROUND_DIRECT_LEFT", "AV_CH_SURROUND_DIRECT_RIGHT", "AV_CH_LOW_FREQUENCY_2", "AV_CH_TOP_SIDE_LEFT", "AV_CH_TOP_SIDE_RIGHT", "AV_CH_BOTTOM_FRONT_CENTER", "AV_CH_BOTTOM_FRONT_LEFT", "AV_CH_BOTTOM_FRONT_RIGHT", "AV_CH_SIDE_SURROUND_LEFT", "AV_CH_SIDE_SURROUND_RIGHT", "AV_CH_TOP_SURROUND_LEFT", "AV_CH_TOP_SURROUND_RIGHT", "AV_CH_LAYOUT_NATIVE", "AV_CH_LAYOUT_MONO", "AV_CH_LAYOUT_STEREO", "AV_CH_LAYOUT_2POINT1", "AV_CH_LAYOUT_2_1", "AV_CH_LAYOUT_SURROUND", "AV_CH_LAYOUT_3POINT1", "AV_CH_LAYOUT_4POINT0", "AV_CH_LAYOUT_4POINT1", "AV_CH_LAYOUT_2_2", "AV_CH_LAYOUT_QUAD", "AV_CH_LAYOUT_5POINT0", "AV_CH_LAYOUT_5POINT1", "AV_CH_LAYOUT_5POINT0_BACK", "AV_CH_LAYOUT_5POINT1_BACK", "AV_CH_LAYOUT_6POINT0", "AV_CH_LAYOUT_6POINT0_FRONT", "AV_CH_LAYOUT_HEXAGONAL", "AV_CH_LAYOUT_3POINT1POINT2", "AV_CH_LAYOUT_6POINT1", "AV_CH_LAYOUT_6POINT1_BACK", "AV_CH_LAYOUT_6POINT1_FRONT", "AV_CH_LAYOUT_7POINT0", "AV_CH_LAYOUT_7POINT0_FRONT", "AV_CH_LAYOUT_7POINT1", "AV_CH_LAYOUT_7POINT1_WIDE", "AV_CH_LAYOUT_7POINT1_WIDE_BACK", "AV_CH_LAYOUT_5POINT1POINT2_BACK", "AV_CH_LAYOUT_OCTAGONAL", "AV_CH_LAYOUT_CUBE", "AV_CH_LAYOUT_5POINT1POINT4_BACK", "AV_CH_LAYOUT_7POINT1POINT2", "AV_CH_LAYOUT_7POINT1POINT4_BACK", "AV_CH_LAYOUT_HEXADECAGONAL", "AV_CH_LAYOUT_STEREO_DOWNMIX", "AV_CH_LAYOUT_22POINT2", "AV_CH_LAYOUT_7POINT1_TOP_BACK").translate(true).cppTypes("long")).put(new Info("AV_CHANNEL_LAYOUT_MONO", "AV_CHANNEL_LAYOUT_STEREO", "AV_CHANNEL_LAYOUT_2POINT1", "AV_CHANNEL_LAYOUT_2_1", "AV_CHANNEL_LAYOUT_SURROUND", "AV_CHANNEL_LAYOUT_3POINT1", "AV_CHANNEL_LAYOUT_4POINT0", "AV_CHANNEL_LAYOUT_4POINT1", "AV_CHANNEL_LAYOUT_2_2", "AV_CHANNEL_LAYOUT_QUAD", "AV_CHANNEL_LAYOUT_5POINT0", "AV_CHANNEL_LAYOUT_5POINT1", "AV_CHANNEL_LAYOUT_5POINT0_BACK", "AV_CHANNEL_LAYOUT_5POINT1_BACK", "AV_CHANNEL_LAYOUT_6POINT0", "AV_CHANNEL_LAYOUT_6POINT0_FRONT", "AV_CHANNEL_LAYOUT_3POINT1POINT2", "AV_CHANNEL_LAYOUT_HEXAGONAL", "AV_CHANNEL_LAYOUT_6POINT1", "AV_CHANNEL_LAYOUT_6POINT1_BACK", "AV_CHANNEL_LAYOUT_6POINT1_FRONT", "AV_CHANNEL_LAYOUT_7POINT0", "AV_CHANNEL_LAYOUT_7POINT0_FRONT", "AV_CHANNEL_LAYOUT_7POINT1", "AV_CHANNEL_LAYOUT_7POINT1_WIDE", "AV_CHANNEL_LAYOUT_7POINT1_WIDE_BACK", "AV_CHANNEL_LAYOUT_7POINT1_TOP_BACK", "AV_CHANNEL_LAYOUT_5POINT1POINT2", "AV_CHANNEL_LAYOUT_5POINT1POINT2_BACK", "AV_CHANNEL_LAYOUT_OCTAGONAL", "AV_CHANNEL_LAYOUT_CUBE", "AV_CHANNEL_LAYOUT_5POINT1POINT4_BACK", "AV_CHANNEL_LAYOUT_7POINT1POINT2", "AV_CHANNEL_LAYOUT_7POINT1POINT4_BACK", "AV_CHANNEL_LAYOUT_7POINT2POINT3", "AV_CHANNEL_LAYOUT_9POINT1POINT4_BACK", "AV_CHANNEL_LAYOUT_9POINT1POINT6", "AV_CHANNEL_LAYOUT_HEXADECAGONAL", "AV_CHANNEL_LAYOUT_BINAURAL", "AV_CHANNEL_LAYOUT_STEREO_DOWNMIX", "AV_CHANNEL_LAYOUT_22POINT2", "AV_CHANNEL_LAYOUT_AMBISONIC_FIRST_ORDER").translate(false).cppTypes("AVChannelLayout")).put(new Info("MKTAG", "MKBETAG").cppTypes("int", "char", "char", "char", "char")).put(new Info("int (*)(const void*, const void*)").cast().pointerTypes("Cmp_Const_Pointer_Const_Pointer")).put(new Info("int (*)(void*, void*, int)").pointerTypes("Int_func_Pointer_Pointer_int"));
    }

    @MemberGetter
    @Name(value={"AVERROR(EACCES)"})
    public static native int AVERROR_EACCES();

    @MemberGetter
    @Name(value={"AVERROR(EAGAIN)"})
    public static native int AVERROR_EAGAIN();

    @MemberGetter
    @Name(value={"AVERROR(EBADF)"})
    public static native int AVERROR_EBADF();

    @MemberGetter
    @Name(value={"AVERROR(EDOM)"})
    public static native int AVERROR_EDOM();

    @MemberGetter
    @Name(value={"AVERROR(EEXIST)"})
    public static native int AVERROR_EEXIST();

    @MemberGetter
    @Name(value={"AVERROR(EFAULT)"})
    public static native int AVERROR_EFAULT();

    @MemberGetter
    @Name(value={"AVERROR(EFBIG)"})
    public static native int AVERROR_EFBIG();

    @MemberGetter
    @Name(value={"AVERROR(EILSEQ)"})
    public static native int AVERROR_EILSEQ();

    @MemberGetter
    @Name(value={"AVERROR(EINTR)"})
    public static native int AVERROR_EINTR();

    @MemberGetter
    @Name(value={"AVERROR(EINVAL)"})
    public static native int AVERROR_EINVAL();

    @MemberGetter
    @Name(value={"AVERROR(EIO)"})
    public static native int AVERROR_EIO();

    @MemberGetter
    @Name(value={"AVERROR(ENAMETOOLONG)"})
    public static native int AVERROR_ENAMETOOLONG();

    @MemberGetter
    @Name(value={"AVERROR(ENODEV)"})
    public static native int AVERROR_ENODEV();

    @MemberGetter
    @Name(value={"AVERROR(ENOENT)"})
    public static native int AVERROR_ENOENT();

    @MemberGetter
    @Name(value={"AVERROR(ENOMEM)"})
    public static native int AVERROR_ENOMEM();

    @MemberGetter
    @Name(value={"AVERROR(ENOSPC)"})
    public static native int AVERROR_ENOSPC();

    @MemberGetter
    @Name(value={"AVERROR(ENOSYS)"})
    public static native int AVERROR_ENOSYS();

    @MemberGetter
    @Name(value={"AVERROR(ENXIO)"})
    public static native int AVERROR_ENXIO();

    @MemberGetter
    @Name(value={"AVERROR(EPERM)"})
    public static native int AVERROR_EPERM();

    @MemberGetter
    @Name(value={"AVERROR(EPIPE)"})
    public static native int AVERROR_EPIPE();

    @MemberGetter
    @Name(value={"AVERROR(ERANGE)"})
    public static native int AVERROR_ERANGE();

    @MemberGetter
    @Name(value={"AVERROR(ESPIPE)"})
    public static native int AVERROR_ESPIPE();

    @MemberGetter
    @Name(value={"AVERROR(EXDEV)"})
    public static native int AVERROR_EXDEV();

    @MemberGetter
    @Cast(value={"void (*)(void*, int, const char*, va_list)"})
    public static native Pointer av_log_default_callback();

    @NoException
    public static native void av_log_set_callback(@Cast(value={"void (*)(void*, int, const char*, va_list)"}) Pointer var0);

    static {
        Loader.checkVersion("org.bytedeco", "ffmpeg");
    }
}

