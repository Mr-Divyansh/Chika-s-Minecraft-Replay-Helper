/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.global;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import org.bytedeco.ffmpeg.avutil.AVAES;
import org.bytedeco.ffmpeg.avutil.AVAESCTR;
import org.bytedeco.ffmpeg.avutil.AVAudioFifo;
import org.bytedeco.ffmpeg.avutil.AVBPrint;
import org.bytedeco.ffmpeg.avutil.AVBlowfish;
import org.bytedeco.ffmpeg.avutil.AVBufferPool;
import org.bytedeco.ffmpeg.avutil.AVBufferRef;
import org.bytedeco.ffmpeg.avutil.AVCAMELLIA;
import org.bytedeco.ffmpeg.avutil.AVCAST5;
import org.bytedeco.ffmpeg.avutil.AVChannelLayout;
import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.avutil.AVContainerFifo;
import org.bytedeco.ffmpeg.avutil.AVContentLightMetadata;
import org.bytedeco.ffmpeg.avutil.AVDES;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.avutil.AVDictionaryEntry;
import org.bytedeco.ffmpeg.avutil.AVDownmixInfo;
import org.bytedeco.ffmpeg.avutil.AVDynamicHDRPlus;
import org.bytedeco.ffmpeg.avutil.AVEncryptionInfo;
import org.bytedeco.ffmpeg.avutil.AVEncryptionInitInfo;
import org.bytedeco.ffmpeg.avutil.AVExpr;
import org.bytedeco.ffmpeg.avutil.AVFifo;
import org.bytedeco.ffmpeg.avutil.AVFifoCB;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.avutil.AVFrameSideData;
import org.bytedeco.ffmpeg.avutil.AVHMAC;
import org.bytedeco.ffmpeg.avutil.AVHWFramesConstraints;
import org.bytedeco.ffmpeg.avutil.AVHashContext;
import org.bytedeco.ffmpeg.avutil.AVLFG;
import org.bytedeco.ffmpeg.avutil.AVMD5;
import org.bytedeco.ffmpeg.avutil.AVMasteringDisplayMetadata;
import org.bytedeco.ffmpeg.avutil.AVOption;
import org.bytedeco.ffmpeg.avutil.AVOptionRanges;
import org.bytedeco.ffmpeg.avutil.AVPixFmtDescriptor;
import org.bytedeco.ffmpeg.avutil.AVRC4;
import org.bytedeco.ffmpeg.avutil.AVRIPEMD;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.avutil.AVSHA;
import org.bytedeco.ffmpeg.avutil.AVSHA512;
import org.bytedeco.ffmpeg.avutil.AVSideDataDescriptor;
import org.bytedeco.ffmpeg.avutil.AVSphericalMapping;
import org.bytedeco.ffmpeg.avutil.AVStereo3D;
import org.bytedeco.ffmpeg.avutil.AVTEA;
import org.bytedeco.ffmpeg.avutil.AVTWOFISH;
import org.bytedeco.ffmpeg.avutil.AVTXContext;
import org.bytedeco.ffmpeg.avutil.AVThreadMessageQueue;
import org.bytedeco.ffmpeg.avutil.AVTimecode;
import org.bytedeco.ffmpeg.avutil.AVTreeNode;
import org.bytedeco.ffmpeg.avutil.AVXTEA;
import org.bytedeco.ffmpeg.avutil.Alloc_Pointer_long;
import org.bytedeco.ffmpeg.avutil.Alloc_long;
import org.bytedeco.ffmpeg.avutil.Callback_Pointer_int_BytePointer_Pointer;
import org.bytedeco.ffmpeg.avutil.Callback_Pointer_int_String_Pointer;
import org.bytedeco.ffmpeg.avutil.Cmp_Const_Pointer_Const_Pointer;
import org.bytedeco.ffmpeg.avutil.Cmp_Pointer_Pointer;
import org.bytedeco.ffmpeg.avutil.Container_alloc_Pointer;
import org.bytedeco.ffmpeg.avutil.Container_free_Pointer_Pointer;
import org.bytedeco.ffmpeg.avutil.Container_reset_Pointer_Pointer;
import org.bytedeco.ffmpeg.avutil.Enu_Pointer_Pointer;
import org.bytedeco.ffmpeg.avutil.Fifo_transfer_Pointer_Pointer_Pointer_int;
import org.bytedeco.ffmpeg.avutil.Free_Pointer_ByteBuffer;
import org.bytedeco.ffmpeg.avutil.Free_Pointer_BytePointer;
import org.bytedeco.ffmpeg.avutil.Free_Pointer_byte__;
import org.bytedeco.ffmpeg.avutil.Free_func_Pointer;
import org.bytedeco.ffmpeg.avutil.LogCallback;
import org.bytedeco.ffmpeg.avutil.Pool_free_Pointer;
import org.bytedeco.ffmpeg.avutil.av_pixelutils_sad_fn;
import org.bytedeco.ffmpeg.avutil.av_tx_fn;
import org.bytedeco.ffmpeg.avutil.tm;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.LongPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacpp.SizeTPointer;
import org.bytedeco.javacpp.annotation.ByPtrPtr;
import org.bytedeco.javacpp.annotation.ByVal;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.NoException;

public class avutil
extends org.bytedeco.ffmpeg.presets.avutil {
    public static final int AVMEDIA_TYPE_UNKNOWN = -1;
    public static final int AVMEDIA_TYPE_VIDEO = 0;
    public static final int AVMEDIA_TYPE_AUDIO = 1;
    public static final int AVMEDIA_TYPE_DATA = 2;
    public static final int AVMEDIA_TYPE_SUBTITLE = 3;
    public static final int AVMEDIA_TYPE_ATTACHMENT = 4;
    public static final int AVMEDIA_TYPE_NB = 5;
    public static final int FF_LAMBDA_SHIFT = 7;
    public static final int FF_LAMBDA_SCALE = 128;
    public static final int FF_QP2LAMBDA = 118;
    public static final int FF_LAMBDA_MAX = Short.MAX_VALUE;
    public static final int FF_QUALITY_SCALE = 128;
    public static final long AV_NOPTS_VALUE;
    public static final int AV_TIME_BASE = 1000000;
    public static final int AV_PICTURE_TYPE_NONE = 0;
    public static final int AV_PICTURE_TYPE_I = 1;
    public static final int AV_PICTURE_TYPE_P = 2;
    public static final int AV_PICTURE_TYPE_B = 3;
    public static final int AV_PICTURE_TYPE_S = 4;
    public static final int AV_PICTURE_TYPE_SI = 5;
    public static final int AV_PICTURE_TYPE_SP = 6;
    public static final int AV_PICTURE_TYPE_BI = 7;
    public static final int AV_FOURCC_MAX_STRING_SIZE = 32;
    public static final int AVERROR_BSF_NOT_FOUND;
    public static final int AVERROR_BUG;
    public static final int AVERROR_BUFFER_TOO_SMALL;
    public static final int AVERROR_DECODER_NOT_FOUND;
    public static final int AVERROR_DEMUXER_NOT_FOUND;
    public static final int AVERROR_ENCODER_NOT_FOUND;
    public static final int AVERROR_EOF;
    public static final int AVERROR_EXIT;
    public static final int AVERROR_EXTERNAL;
    public static final int AVERROR_FILTER_NOT_FOUND;
    public static final int AVERROR_INVALIDDATA;
    public static final int AVERROR_MUXER_NOT_FOUND;
    public static final int AVERROR_OPTION_NOT_FOUND;
    public static final int AVERROR_PATCHWELCOME;
    public static final int AVERROR_PROTOCOL_NOT_FOUND;
    public static final int AVERROR_STREAM_NOT_FOUND;
    public static final int AVERROR_BUG2;
    public static final int AVERROR_UNKNOWN;
    public static final int AVERROR_EXPERIMENTAL = -733130664;
    public static final int AVERROR_INPUT_CHANGED = -1668179713;
    public static final int AVERROR_OUTPUT_CHANGED = -1668179714;
    public static final int AVERROR_HTTP_BAD_REQUEST;
    public static final int AVERROR_HTTP_UNAUTHORIZED;
    public static final int AVERROR_HTTP_FORBIDDEN;
    public static final int AVERROR_HTTP_NOT_FOUND;
    public static final int AVERROR_HTTP_TOO_MANY_REQUESTS;
    public static final int AVERROR_HTTP_OTHER_4XX;
    public static final int AVERROR_HTTP_SERVER_ERROR;
    public static final int AV_ERROR_MAX_STRING_SIZE = 64;
    public static final double M_E = Math.E;
    public static final double M_Ef = 2.7182817459106445;
    public static final double M_LN2 = 0.6931471805599453;
    public static final double M_LN2f = 0.6931471824645996;
    public static final double M_LN10 = 2.302585092994046;
    public static final double M_LN10f = 2.3025851249694824;
    public static final double M_LOG2_10 = 3.321928094887362;
    public static final double M_LOG2_10f = 3.321928024291992;
    public static final double M_PHI = 1.618033988749895;
    public static final double M_PHIf = 1.6180340051651;
    public static final double M_PI = Math.PI;
    public static final double M_PIf = 3.1415927410125732;
    public static final double M_PI_2 = 1.5707963267948966;
    public static final double M_PI_2f = 1.5707963705062866;
    public static final double M_PI_4 = 0.7853981633974483;
    public static final double M_PI_4f = 0.7853981852531433;
    public static final double M_1_PI = 0.3183098861837907;
    public static final double M_1_PIf = 0.31830987334251404;
    public static final double M_2_PI = 0.6366197723675814;
    public static final double M_2_PIf = 0.6366197466850281;
    public static final double M_2_SQRTPI = 1.1283791670955126;
    public static final double M_2_SQRTPIf = 1.128379225730896;
    public static final double M_SQRT1_2 = 0.7071067811865476;
    public static final double M_SQRT1_2f = 0.7071067690849304;
    public static final double M_SQRT2 = 1.4142135623730951;
    public static final double M_SQRT2f = 1.4142135381698608;
    public static final double NAN;
    public static final double INFINITY;
    public static final int AV_ROUND_ZERO = 0;
    public static final int AV_ROUND_INF = 1;
    public static final int AV_ROUND_DOWN = 2;
    public static final int AV_ROUND_UP = 3;
    public static final int AV_ROUND_NEAR_INF = 5;
    public static final int AV_ROUND_PASS_MINMAX = 8192;
    public static final int AV_CLASS_CATEGORY_NA = 0;
    public static final int AV_CLASS_CATEGORY_INPUT = 1;
    public static final int AV_CLASS_CATEGORY_OUTPUT = 2;
    public static final int AV_CLASS_CATEGORY_MUXER = 3;
    public static final int AV_CLASS_CATEGORY_DEMUXER = 4;
    public static final int AV_CLASS_CATEGORY_ENCODER = 5;
    public static final int AV_CLASS_CATEGORY_DECODER = 6;
    public static final int AV_CLASS_CATEGORY_FILTER = 7;
    public static final int AV_CLASS_CATEGORY_BITSTREAM_FILTER = 8;
    public static final int AV_CLASS_CATEGORY_SWSCALER = 9;
    public static final int AV_CLASS_CATEGORY_SWRESAMPLER = 10;
    public static final int AV_CLASS_CATEGORY_HWDEVICE = 11;
    public static final int AV_CLASS_CATEGORY_DEVICE_VIDEO_OUTPUT = 40;
    public static final int AV_CLASS_CATEGORY_DEVICE_VIDEO_INPUT = 41;
    public static final int AV_CLASS_CATEGORY_DEVICE_AUDIO_OUTPUT = 42;
    public static final int AV_CLASS_CATEGORY_DEVICE_AUDIO_INPUT = 43;
    public static final int AV_CLASS_CATEGORY_DEVICE_OUTPUT = 44;
    public static final int AV_CLASS_CATEGORY_DEVICE_INPUT = 45;
    public static final int AV_CLASS_CATEGORY_NB = 46;
    public static final int AV_CLASS_STATE_INITIALIZED = 1;
    public static final int AV_LOG_QUIET = -8;
    public static final int AV_LOG_PANIC = 0;
    public static final int AV_LOG_FATAL = 8;
    public static final int AV_LOG_ERROR = 16;
    public static final int AV_LOG_WARNING = 24;
    public static final int AV_LOG_INFO = 32;
    public static final int AV_LOG_VERBOSE = 40;
    public static final int AV_LOG_DEBUG = 48;
    public static final int AV_LOG_TRACE = 56;
    public static final int AV_LOG_MAX_OFFSET = 64;
    public static final int AV_LOG_SKIP_REPEATED = 1;
    public static final int AV_LOG_PRINT_LEVEL = 2;
    public static final int AV_LOG_PRINT_TIME = 4;
    public static final int AV_LOG_PRINT_DATETIME = 8;
    public static final int AV_BUFFER_FLAG_READONLY = 1;
    public static final int AVPALETTE_SIZE = 1024;
    public static final int AVPALETTE_COUNT = 256;
    public static final int AV_VIDEO_MAX_PLANES = 4;
    public static final int AV_PIX_FMT_NONE = -1;
    public static final int AV_PIX_FMT_YUV420P = 0;
    public static final int AV_PIX_FMT_YUYV422 = 1;
    public static final int AV_PIX_FMT_RGB24 = 2;
    public static final int AV_PIX_FMT_BGR24 = 3;
    public static final int AV_PIX_FMT_YUV422P = 4;
    public static final int AV_PIX_FMT_YUV444P = 5;
    public static final int AV_PIX_FMT_YUV410P = 6;
    public static final int AV_PIX_FMT_YUV411P = 7;
    public static final int AV_PIX_FMT_GRAY8 = 8;
    public static final int AV_PIX_FMT_MONOWHITE = 9;
    public static final int AV_PIX_FMT_MONOBLACK = 10;
    public static final int AV_PIX_FMT_PAL8 = 11;
    public static final int AV_PIX_FMT_YUVJ420P = 12;
    public static final int AV_PIX_FMT_YUVJ422P = 13;
    public static final int AV_PIX_FMT_YUVJ444P = 14;
    public static final int AV_PIX_FMT_UYVY422 = 15;
    public static final int AV_PIX_FMT_UYYVYY411 = 16;
    public static final int AV_PIX_FMT_BGR8 = 17;
    public static final int AV_PIX_FMT_BGR4 = 18;
    public static final int AV_PIX_FMT_BGR4_BYTE = 19;
    public static final int AV_PIX_FMT_RGB8 = 20;
    public static final int AV_PIX_FMT_RGB4 = 21;
    public static final int AV_PIX_FMT_RGB4_BYTE = 22;
    public static final int AV_PIX_FMT_NV12 = 23;
    public static final int AV_PIX_FMT_NV21 = 24;
    public static final int AV_PIX_FMT_ARGB = 25;
    public static final int AV_PIX_FMT_RGBA = 26;
    public static final int AV_PIX_FMT_ABGR = 27;
    public static final int AV_PIX_FMT_BGRA = 28;
    public static final int AV_PIX_FMT_GRAY16BE = 29;
    public static final int AV_PIX_FMT_GRAY16LE = 30;
    public static final int AV_PIX_FMT_YUV440P = 31;
    public static final int AV_PIX_FMT_YUVJ440P = 32;
    public static final int AV_PIX_FMT_YUVA420P = 33;
    public static final int AV_PIX_FMT_RGB48BE = 34;
    public static final int AV_PIX_FMT_RGB48LE = 35;
    public static final int AV_PIX_FMT_RGB565BE = 36;
    public static final int AV_PIX_FMT_RGB565LE = 37;
    public static final int AV_PIX_FMT_RGB555BE = 38;
    public static final int AV_PIX_FMT_RGB555LE = 39;
    public static final int AV_PIX_FMT_BGR565BE = 40;
    public static final int AV_PIX_FMT_BGR565LE = 41;
    public static final int AV_PIX_FMT_BGR555BE = 42;
    public static final int AV_PIX_FMT_BGR555LE = 43;
    public static final int AV_PIX_FMT_VAAPI = 44;
    public static final int AV_PIX_FMT_YUV420P16LE = 45;
    public static final int AV_PIX_FMT_YUV420P16BE = 46;
    public static final int AV_PIX_FMT_YUV422P16LE = 47;
    public static final int AV_PIX_FMT_YUV422P16BE = 48;
    public static final int AV_PIX_FMT_YUV444P16LE = 49;
    public static final int AV_PIX_FMT_YUV444P16BE = 50;
    public static final int AV_PIX_FMT_DXVA2_VLD = 51;
    public static final int AV_PIX_FMT_RGB444LE = 52;
    public static final int AV_PIX_FMT_RGB444BE = 53;
    public static final int AV_PIX_FMT_BGR444LE = 54;
    public static final int AV_PIX_FMT_BGR444BE = 55;
    public static final int AV_PIX_FMT_YA8 = 56;
    public static final int AV_PIX_FMT_Y400A = 56;
    public static final int AV_PIX_FMT_GRAY8A = 56;
    public static final int AV_PIX_FMT_BGR48BE = 57;
    public static final int AV_PIX_FMT_BGR48LE = 58;
    public static final int AV_PIX_FMT_YUV420P9BE = 59;
    public static final int AV_PIX_FMT_YUV420P9LE = 60;
    public static final int AV_PIX_FMT_YUV420P10BE = 61;
    public static final int AV_PIX_FMT_YUV420P10LE = 62;
    public static final int AV_PIX_FMT_YUV422P10BE = 63;
    public static final int AV_PIX_FMT_YUV422P10LE = 64;
    public static final int AV_PIX_FMT_YUV444P9BE = 65;
    public static final int AV_PIX_FMT_YUV444P9LE = 66;
    public static final int AV_PIX_FMT_YUV444P10BE = 67;
    public static final int AV_PIX_FMT_YUV444P10LE = 68;
    public static final int AV_PIX_FMT_YUV422P9BE = 69;
    public static final int AV_PIX_FMT_YUV422P9LE = 70;
    public static final int AV_PIX_FMT_GBRP = 71;
    public static final int AV_PIX_FMT_GBR24P = 71;
    public static final int AV_PIX_FMT_GBRP9BE = 72;
    public static final int AV_PIX_FMT_GBRP9LE = 73;
    public static final int AV_PIX_FMT_GBRP10BE = 74;
    public static final int AV_PIX_FMT_GBRP10LE = 75;
    public static final int AV_PIX_FMT_GBRP16BE = 76;
    public static final int AV_PIX_FMT_GBRP16LE = 77;
    public static final int AV_PIX_FMT_YUVA422P = 78;
    public static final int AV_PIX_FMT_YUVA444P = 79;
    public static final int AV_PIX_FMT_YUVA420P9BE = 80;
    public static final int AV_PIX_FMT_YUVA420P9LE = 81;
    public static final int AV_PIX_FMT_YUVA422P9BE = 82;
    public static final int AV_PIX_FMT_YUVA422P9LE = 83;
    public static final int AV_PIX_FMT_YUVA444P9BE = 84;
    public static final int AV_PIX_FMT_YUVA444P9LE = 85;
    public static final int AV_PIX_FMT_YUVA420P10BE = 86;
    public static final int AV_PIX_FMT_YUVA420P10LE = 87;
    public static final int AV_PIX_FMT_YUVA422P10BE = 88;
    public static final int AV_PIX_FMT_YUVA422P10LE = 89;
    public static final int AV_PIX_FMT_YUVA444P10BE = 90;
    public static final int AV_PIX_FMT_YUVA444P10LE = 91;
    public static final int AV_PIX_FMT_YUVA420P16BE = 92;
    public static final int AV_PIX_FMT_YUVA420P16LE = 93;
    public static final int AV_PIX_FMT_YUVA422P16BE = 94;
    public static final int AV_PIX_FMT_YUVA422P16LE = 95;
    public static final int AV_PIX_FMT_YUVA444P16BE = 96;
    public static final int AV_PIX_FMT_YUVA444P16LE = 97;
    public static final int AV_PIX_FMT_VDPAU = 98;
    public static final int AV_PIX_FMT_XYZ12LE = 99;
    public static final int AV_PIX_FMT_XYZ12BE = 100;
    public static final int AV_PIX_FMT_NV16 = 101;
    public static final int AV_PIX_FMT_NV20LE = 102;
    public static final int AV_PIX_FMT_NV20BE = 103;
    public static final int AV_PIX_FMT_RGBA64BE = 104;
    public static final int AV_PIX_FMT_RGBA64LE = 105;
    public static final int AV_PIX_FMT_BGRA64BE = 106;
    public static final int AV_PIX_FMT_BGRA64LE = 107;
    public static final int AV_PIX_FMT_YVYU422 = 108;
    public static final int AV_PIX_FMT_YA16BE = 109;
    public static final int AV_PIX_FMT_YA16LE = 110;
    public static final int AV_PIX_FMT_GBRAP = 111;
    public static final int AV_PIX_FMT_GBRAP16BE = 112;
    public static final int AV_PIX_FMT_GBRAP16LE = 113;
    public static final int AV_PIX_FMT_QSV = 114;
    public static final int AV_PIX_FMT_MMAL = 115;
    public static final int AV_PIX_FMT_D3D11VA_VLD = 116;
    public static final int AV_PIX_FMT_CUDA = 117;
    public static final int AV_PIX_FMT_0RGB = 118;
    public static final int AV_PIX_FMT_RGB0 = 119;
    public static final int AV_PIX_FMT_0BGR = 120;
    public static final int AV_PIX_FMT_BGR0 = 121;
    public static final int AV_PIX_FMT_YUV420P12BE = 122;
    public static final int AV_PIX_FMT_YUV420P12LE = 123;
    public static final int AV_PIX_FMT_YUV420P14BE = 124;
    public static final int AV_PIX_FMT_YUV420P14LE = 125;
    public static final int AV_PIX_FMT_YUV422P12BE = 126;
    public static final int AV_PIX_FMT_YUV422P12LE = 127;
    public static final int AV_PIX_FMT_YUV422P14BE = 128;
    public static final int AV_PIX_FMT_YUV422P14LE = 129;
    public static final int AV_PIX_FMT_YUV444P12BE = 130;
    public static final int AV_PIX_FMT_YUV444P12LE = 131;
    public static final int AV_PIX_FMT_YUV444P14BE = 132;
    public static final int AV_PIX_FMT_YUV444P14LE = 133;
    public static final int AV_PIX_FMT_GBRP12BE = 134;
    public static final int AV_PIX_FMT_GBRP12LE = 135;
    public static final int AV_PIX_FMT_GBRP14BE = 136;
    public static final int AV_PIX_FMT_GBRP14LE = 137;
    public static final int AV_PIX_FMT_YUVJ411P = 138;
    public static final int AV_PIX_FMT_BAYER_BGGR8 = 139;
    public static final int AV_PIX_FMT_BAYER_RGGB8 = 140;
    public static final int AV_PIX_FMT_BAYER_GBRG8 = 141;
    public static final int AV_PIX_FMT_BAYER_GRBG8 = 142;
    public static final int AV_PIX_FMT_BAYER_BGGR16LE = 143;
    public static final int AV_PIX_FMT_BAYER_BGGR16BE = 144;
    public static final int AV_PIX_FMT_BAYER_RGGB16LE = 145;
    public static final int AV_PIX_FMT_BAYER_RGGB16BE = 146;
    public static final int AV_PIX_FMT_BAYER_GBRG16LE = 147;
    public static final int AV_PIX_FMT_BAYER_GBRG16BE = 148;
    public static final int AV_PIX_FMT_BAYER_GRBG16LE = 149;
    public static final int AV_PIX_FMT_BAYER_GRBG16BE = 150;
    public static final int AV_PIX_FMT_YUV440P10LE = 151;
    public static final int AV_PIX_FMT_YUV440P10BE = 152;
    public static final int AV_PIX_FMT_YUV440P12LE = 153;
    public static final int AV_PIX_FMT_YUV440P12BE = 154;
    public static final int AV_PIX_FMT_AYUV64LE = 155;
    public static final int AV_PIX_FMT_AYUV64BE = 156;
    public static final int AV_PIX_FMT_VIDEOTOOLBOX = 157;
    public static final int AV_PIX_FMT_P010LE = 158;
    public static final int AV_PIX_FMT_P010BE = 159;
    public static final int AV_PIX_FMT_GBRAP12BE = 160;
    public static final int AV_PIX_FMT_GBRAP12LE = 161;
    public static final int AV_PIX_FMT_GBRAP10BE = 162;
    public static final int AV_PIX_FMT_GBRAP10LE = 163;
    public static final int AV_PIX_FMT_MEDIACODEC = 164;
    public static final int AV_PIX_FMT_GRAY12BE = 165;
    public static final int AV_PIX_FMT_GRAY12LE = 166;
    public static final int AV_PIX_FMT_GRAY10BE = 167;
    public static final int AV_PIX_FMT_GRAY10LE = 168;
    public static final int AV_PIX_FMT_P016LE = 169;
    public static final int AV_PIX_FMT_P016BE = 170;
    public static final int AV_PIX_FMT_D3D11 = 171;
    public static final int AV_PIX_FMT_GRAY9BE = 172;
    public static final int AV_PIX_FMT_GRAY9LE = 173;
    public static final int AV_PIX_FMT_GBRPF32BE = 174;
    public static final int AV_PIX_FMT_GBRPF32LE = 175;
    public static final int AV_PIX_FMT_GBRAPF32BE = 176;
    public static final int AV_PIX_FMT_GBRAPF32LE = 177;
    public static final int AV_PIX_FMT_DRM_PRIME = 178;
    public static final int AV_PIX_FMT_OPENCL = 179;
    public static final int AV_PIX_FMT_GRAY14BE = 180;
    public static final int AV_PIX_FMT_GRAY14LE = 181;
    public static final int AV_PIX_FMT_GRAYF32BE = 182;
    public static final int AV_PIX_FMT_GRAYF32LE = 183;
    public static final int AV_PIX_FMT_YUVA422P12BE = 184;
    public static final int AV_PIX_FMT_YUVA422P12LE = 185;
    public static final int AV_PIX_FMT_YUVA444P12BE = 186;
    public static final int AV_PIX_FMT_YUVA444P12LE = 187;
    public static final int AV_PIX_FMT_NV24 = 188;
    public static final int AV_PIX_FMT_NV42 = 189;
    public static final int AV_PIX_FMT_VULKAN = 190;
    public static final int AV_PIX_FMT_Y210BE = 191;
    public static final int AV_PIX_FMT_Y210LE = 192;
    public static final int AV_PIX_FMT_X2RGB10LE = 193;
    public static final int AV_PIX_FMT_X2RGB10BE = 194;
    public static final int AV_PIX_FMT_X2BGR10LE = 195;
    public static final int AV_PIX_FMT_X2BGR10BE = 196;
    public static final int AV_PIX_FMT_P210BE = 197;
    public static final int AV_PIX_FMT_P210LE = 198;
    public static final int AV_PIX_FMT_P410BE = 199;
    public static final int AV_PIX_FMT_P410LE = 200;
    public static final int AV_PIX_FMT_P216BE = 201;
    public static final int AV_PIX_FMT_P216LE = 202;
    public static final int AV_PIX_FMT_P416BE = 203;
    public static final int AV_PIX_FMT_P416LE = 204;
    public static final int AV_PIX_FMT_VUYA = 205;
    public static final int AV_PIX_FMT_RGBAF16BE = 206;
    public static final int AV_PIX_FMT_RGBAF16LE = 207;
    public static final int AV_PIX_FMT_VUYX = 208;
    public static final int AV_PIX_FMT_P012LE = 209;
    public static final int AV_PIX_FMT_P012BE = 210;
    public static final int AV_PIX_FMT_Y212BE = 211;
    public static final int AV_PIX_FMT_Y212LE = 212;
    public static final int AV_PIX_FMT_XV30BE = 213;
    public static final int AV_PIX_FMT_XV30LE = 214;
    public static final int AV_PIX_FMT_XV36BE = 215;
    public static final int AV_PIX_FMT_XV36LE = 216;
    public static final int AV_PIX_FMT_RGBF32BE = 217;
    public static final int AV_PIX_FMT_RGBF32LE = 218;
    public static final int AV_PIX_FMT_RGBAF32BE = 219;
    public static final int AV_PIX_FMT_RGBAF32LE = 220;
    public static final int AV_PIX_FMT_P212BE = 221;
    public static final int AV_PIX_FMT_P212LE = 222;
    public static final int AV_PIX_FMT_P412BE = 223;
    public static final int AV_PIX_FMT_P412LE = 224;
    public static final int AV_PIX_FMT_GBRAP14BE = 225;
    public static final int AV_PIX_FMT_GBRAP14LE = 226;
    public static final int AV_PIX_FMT_D3D12 = 227;
    public static final int AV_PIX_FMT_AYUV = 228;
    public static final int AV_PIX_FMT_UYVA = 229;
    public static final int AV_PIX_FMT_VYU444 = 230;
    public static final int AV_PIX_FMT_V30XBE = 231;
    public static final int AV_PIX_FMT_V30XLE = 232;
    public static final int AV_PIX_FMT_RGBF16BE = 233;
    public static final int AV_PIX_FMT_RGBF16LE = 234;
    public static final int AV_PIX_FMT_RGBA128BE = 235;
    public static final int AV_PIX_FMT_RGBA128LE = 236;
    public static final int AV_PIX_FMT_RGB96BE = 237;
    public static final int AV_PIX_FMT_RGB96LE = 238;
    public static final int AV_PIX_FMT_Y216BE = 239;
    public static final int AV_PIX_FMT_Y216LE = 240;
    public static final int AV_PIX_FMT_XV48BE = 241;
    public static final int AV_PIX_FMT_XV48LE = 242;
    public static final int AV_PIX_FMT_GBRPF16BE = 243;
    public static final int AV_PIX_FMT_GBRPF16LE = 244;
    public static final int AV_PIX_FMT_GBRAPF16BE = 245;
    public static final int AV_PIX_FMT_GBRAPF16LE = 246;
    public static final int AV_PIX_FMT_GRAYF16BE = 247;
    public static final int AV_PIX_FMT_GRAYF16LE = 248;
    public static final int AV_PIX_FMT_AMF_SURFACE = 249;
    public static final int AV_PIX_FMT_GRAY32BE = 250;
    public static final int AV_PIX_FMT_GRAY32LE = 251;
    public static final int AV_PIX_FMT_YAF32BE = 252;
    public static final int AV_PIX_FMT_YAF32LE = 253;
    public static final int AV_PIX_FMT_YAF16BE = 254;
    public static final int AV_PIX_FMT_YAF16LE = 255;
    public static final int AV_PIX_FMT_GBRAP32BE = 256;
    public static final int AV_PIX_FMT_GBRAP32LE = 257;
    public static final int AV_PIX_FMT_YUV444P10MSBBE = 258;
    public static final int AV_PIX_FMT_YUV444P10MSBLE = 259;
    public static final int AV_PIX_FMT_YUV444P12MSBBE = 260;
    public static final int AV_PIX_FMT_YUV444P12MSBLE = 261;
    public static final int AV_PIX_FMT_GBRP10MSBBE = 262;
    public static final int AV_PIX_FMT_GBRP10MSBLE = 263;
    public static final int AV_PIX_FMT_GBRP12MSBBE = 264;
    public static final int AV_PIX_FMT_GBRP12MSBLE = 265;
    public static final int AV_PIX_FMT_OHCODEC = 266;
    public static final int AV_PIX_FMT_NB = 267;
    public static final int AV_PIX_FMT_RGB32;
    public static final int AV_PIX_FMT_RGB32_1;
    public static final int AV_PIX_FMT_BGR32;
    public static final int AV_PIX_FMT_BGR32_1;
    public static final int AV_PIX_FMT_0RGB32;
    public static final int AV_PIX_FMT_0BGR32;
    public static final int AV_PIX_FMT_GRAY9;
    public static final int AV_PIX_FMT_GRAY10;
    public static final int AV_PIX_FMT_GRAY12;
    public static final int AV_PIX_FMT_GRAY14;
    public static final int AV_PIX_FMT_GRAY16;
    public static final int AV_PIX_FMT_GRAY32;
    public static final int AV_PIX_FMT_YA16;
    public static final int AV_PIX_FMT_RGB48;
    public static final int AV_PIX_FMT_RGB565;
    public static final int AV_PIX_FMT_RGB555;
    public static final int AV_PIX_FMT_RGB444;
    public static final int AV_PIX_FMT_RGBA64;
    public static final int AV_PIX_FMT_BGR48;
    public static final int AV_PIX_FMT_BGR565;
    public static final int AV_PIX_FMT_BGR555;
    public static final int AV_PIX_FMT_BGR444;
    public static final int AV_PIX_FMT_BGRA64;
    public static final int AV_PIX_FMT_YUV420P9;
    public static final int AV_PIX_FMT_YUV422P9;
    public static final int AV_PIX_FMT_YUV444P9;
    public static final int AV_PIX_FMT_YUV420P10;
    public static final int AV_PIX_FMT_YUV422P10;
    public static final int AV_PIX_FMT_YUV440P10;
    public static final int AV_PIX_FMT_YUV444P10;
    public static final int AV_PIX_FMT_YUV420P12;
    public static final int AV_PIX_FMT_YUV422P12;
    public static final int AV_PIX_FMT_YUV440P12;
    public static final int AV_PIX_FMT_YUV444P12;
    public static final int AV_PIX_FMT_YUV420P14;
    public static final int AV_PIX_FMT_YUV422P14;
    public static final int AV_PIX_FMT_YUV444P14;
    public static final int AV_PIX_FMT_YUV420P16;
    public static final int AV_PIX_FMT_YUV422P16;
    public static final int AV_PIX_FMT_YUV444P16;
    public static final int AV_PIX_FMT_YUV444P10MSB;
    public static final int AV_PIX_FMT_YUV444P12MSB;
    public static final int AV_PIX_FMT_GBRP9;
    public static final int AV_PIX_FMT_GBRP10;
    public static final int AV_PIX_FMT_GBRP12;
    public static final int AV_PIX_FMT_GBRP14;
    public static final int AV_PIX_FMT_GBRP16;
    public static final int AV_PIX_FMT_GBRAP10;
    public static final int AV_PIX_FMT_GBRAP12;
    public static final int AV_PIX_FMT_GBRAP14;
    public static final int AV_PIX_FMT_GBRAP16;
    public static final int AV_PIX_FMT_GBRAP32;
    public static final int AV_PIX_FMT_GBRP10MSB;
    public static final int AV_PIX_FMT_GBRP12MSB;
    public static final int AV_PIX_FMT_BAYER_BGGR16;
    public static final int AV_PIX_FMT_BAYER_RGGB16;
    public static final int AV_PIX_FMT_BAYER_GBRG16;
    public static final int AV_PIX_FMT_BAYER_GRBG16;
    public static final int AV_PIX_FMT_GBRPF16;
    public static final int AV_PIX_FMT_GBRAPF16;
    public static final int AV_PIX_FMT_GBRPF32;
    public static final int AV_PIX_FMT_GBRAPF32;
    public static final int AV_PIX_FMT_GRAYF16;
    public static final int AV_PIX_FMT_GRAYF32;
    public static final int AV_PIX_FMT_YAF16;
    public static final int AV_PIX_FMT_YAF32;
    public static final int AV_PIX_FMT_YUVA420P9;
    public static final int AV_PIX_FMT_YUVA422P9;
    public static final int AV_PIX_FMT_YUVA444P9;
    public static final int AV_PIX_FMT_YUVA420P10;
    public static final int AV_PIX_FMT_YUVA422P10;
    public static final int AV_PIX_FMT_YUVA444P10;
    public static final int AV_PIX_FMT_YUVA422P12;
    public static final int AV_PIX_FMT_YUVA444P12;
    public static final int AV_PIX_FMT_YUVA420P16;
    public static final int AV_PIX_FMT_YUVA422P16;
    public static final int AV_PIX_FMT_YUVA444P16;
    public static final int AV_PIX_FMT_XYZ12;
    public static final int AV_PIX_FMT_NV20;
    public static final int AV_PIX_FMT_AYUV64;
    public static final int AV_PIX_FMT_P010;
    public static final int AV_PIX_FMT_P012;
    public static final int AV_PIX_FMT_P016;
    public static final int AV_PIX_FMT_Y210;
    public static final int AV_PIX_FMT_Y212;
    public static final int AV_PIX_FMT_Y216;
    public static final int AV_PIX_FMT_XV30;
    public static final int AV_PIX_FMT_XV36;
    public static final int AV_PIX_FMT_XV48;
    public static final int AV_PIX_FMT_V30X;
    public static final int AV_PIX_FMT_X2RGB10;
    public static final int AV_PIX_FMT_X2BGR10;
    public static final int AV_PIX_FMT_P210;
    public static final int AV_PIX_FMT_P410;
    public static final int AV_PIX_FMT_P212;
    public static final int AV_PIX_FMT_P412;
    public static final int AV_PIX_FMT_P216;
    public static final int AV_PIX_FMT_P416;
    public static final int AV_PIX_FMT_RGBF16;
    public static final int AV_PIX_FMT_RGBAF16;
    public static final int AV_PIX_FMT_RGBF32;
    public static final int AV_PIX_FMT_RGBAF32;
    public static final int AV_PIX_FMT_RGB96;
    public static final int AV_PIX_FMT_RGBA128;
    public static final int AVCOL_PRI_RESERVED0 = 0;
    public static final int AVCOL_PRI_BT709 = 1;
    public static final int AVCOL_PRI_UNSPECIFIED = 2;
    public static final int AVCOL_PRI_RESERVED = 3;
    public static final int AVCOL_PRI_BT470M = 4;
    public static final int AVCOL_PRI_BT470BG = 5;
    public static final int AVCOL_PRI_SMPTE170M = 6;
    public static final int AVCOL_PRI_SMPTE240M = 7;
    public static final int AVCOL_PRI_FILM = 8;
    public static final int AVCOL_PRI_BT2020 = 9;
    public static final int AVCOL_PRI_SMPTE428 = 10;
    public static final int AVCOL_PRI_SMPTEST428_1 = 10;
    public static final int AVCOL_PRI_SMPTE431 = 11;
    public static final int AVCOL_PRI_SMPTE432 = 12;
    public static final int AVCOL_PRI_EBU3213 = 22;
    public static final int AVCOL_PRI_JEDEC_P22 = 22;
    public static final int AVCOL_PRI_NB = 23;
    public static final int AVCOL_PRI_EXT_BASE = 256;
    public static final int AVCOL_PRI_V_GAMUT = 256;
    public static final int AVCOL_PRI_EXT_NB = 257;
    public static final int AVCOL_TRC_RESERVED0 = 0;
    public static final int AVCOL_TRC_BT709 = 1;
    public static final int AVCOL_TRC_UNSPECIFIED = 2;
    public static final int AVCOL_TRC_RESERVED = 3;
    public static final int AVCOL_TRC_GAMMA22 = 4;
    public static final int AVCOL_TRC_GAMMA28 = 5;
    public static final int AVCOL_TRC_SMPTE170M = 6;
    public static final int AVCOL_TRC_SMPTE240M = 7;
    public static final int AVCOL_TRC_LINEAR = 8;
    public static final int AVCOL_TRC_LOG = 9;
    public static final int AVCOL_TRC_LOG_SQRT = 10;
    public static final int AVCOL_TRC_IEC61966_2_4 = 11;
    public static final int AVCOL_TRC_BT1361_ECG = 12;
    public static final int AVCOL_TRC_IEC61966_2_1 = 13;
    public static final int AVCOL_TRC_BT2020_10 = 14;
    public static final int AVCOL_TRC_BT2020_12 = 15;
    public static final int AVCOL_TRC_SMPTE2084 = 16;
    public static final int AVCOL_TRC_SMPTEST2084 = 16;
    public static final int AVCOL_TRC_SMPTE428 = 17;
    public static final int AVCOL_TRC_SMPTEST428_1 = 17;
    public static final int AVCOL_TRC_ARIB_STD_B67 = 18;
    public static final int AVCOL_TRC_NB = 19;
    public static final int AVCOL_TRC_EXT_BASE = 256;
    public static final int AVCOL_TRC_V_LOG = 256;
    public static final int AVCOL_TRC_EXT_NB = 257;
    public static final int AVCOL_SPC_RGB = 0;
    public static final int AVCOL_SPC_BT709 = 1;
    public static final int AVCOL_SPC_UNSPECIFIED = 2;
    public static final int AVCOL_SPC_RESERVED = 3;
    public static final int AVCOL_SPC_FCC = 4;
    public static final int AVCOL_SPC_BT470BG = 5;
    public static final int AVCOL_SPC_SMPTE170M = 6;
    public static final int AVCOL_SPC_SMPTE240M = 7;
    public static final int AVCOL_SPC_YCGCO = 8;
    public static final int AVCOL_SPC_YCOCG = 8;
    public static final int AVCOL_SPC_BT2020_NCL = 9;
    public static final int AVCOL_SPC_BT2020_CL = 10;
    public static final int AVCOL_SPC_SMPTE2085 = 11;
    public static final int AVCOL_SPC_CHROMA_DERIVED_NCL = 12;
    public static final int AVCOL_SPC_CHROMA_DERIVED_CL = 13;
    public static final int AVCOL_SPC_ICTCP = 14;
    public static final int AVCOL_SPC_IPT_C2 = 15;
    public static final int AVCOL_SPC_YCGCO_RE = 16;
    public static final int AVCOL_SPC_YCGCO_RO = 17;
    public static final int AVCOL_SPC_NB = 18;
    public static final int AVCOL_RANGE_UNSPECIFIED = 0;
    public static final int AVCOL_RANGE_MPEG = 1;
    public static final int AVCOL_RANGE_JPEG = 2;
    public static final int AVCOL_RANGE_NB = 3;
    public static final int AVCHROMA_LOC_UNSPECIFIED = 0;
    public static final int AVCHROMA_LOC_LEFT = 1;
    public static final int AVCHROMA_LOC_CENTER = 2;
    public static final int AVCHROMA_LOC_TOPLEFT = 3;
    public static final int AVCHROMA_LOC_TOP = 4;
    public static final int AVCHROMA_LOC_BOTTOMLEFT = 5;
    public static final int AVCHROMA_LOC_BOTTOM = 6;
    public static final int AVCHROMA_LOC_NB = 7;
    public static final int AVALPHA_MODE_UNSPECIFIED = 0;
    public static final int AVALPHA_MODE_PREMULTIPLIED = 1;
    public static final int AVALPHA_MODE_STRAIGHT = 2;
    public static final int AVALPHA_MODE_NB = 3;
    public static final int AV_FRAME_DATA_PANSCAN = 0;
    public static final int AV_FRAME_DATA_A53_CC = 1;
    public static final int AV_FRAME_DATA_STEREO3D = 2;
    public static final int AV_FRAME_DATA_MATRIXENCODING = 3;
    public static final int AV_FRAME_DATA_DOWNMIX_INFO = 4;
    public static final int AV_FRAME_DATA_REPLAYGAIN = 5;
    public static final int AV_FRAME_DATA_DISPLAYMATRIX = 6;
    public static final int AV_FRAME_DATA_AFD = 7;
    public static final int AV_FRAME_DATA_MOTION_VECTORS = 8;
    public static final int AV_FRAME_DATA_SKIP_SAMPLES = 9;
    public static final int AV_FRAME_DATA_AUDIO_SERVICE_TYPE = 10;
    public static final int AV_FRAME_DATA_MASTERING_DISPLAY_METADATA = 11;
    public static final int AV_FRAME_DATA_GOP_TIMECODE = 12;
    public static final int AV_FRAME_DATA_SPHERICAL = 13;
    public static final int AV_FRAME_DATA_CONTENT_LIGHT_LEVEL = 14;
    public static final int AV_FRAME_DATA_ICC_PROFILE = 15;
    public static final int AV_FRAME_DATA_S12M_TIMECODE = 16;
    public static final int AV_FRAME_DATA_DYNAMIC_HDR_PLUS = 17;
    public static final int AV_FRAME_DATA_REGIONS_OF_INTEREST = 18;
    public static final int AV_FRAME_DATA_VIDEO_ENC_PARAMS = 19;
    public static final int AV_FRAME_DATA_SEI_UNREGISTERED = 20;
    public static final int AV_FRAME_DATA_FILM_GRAIN_PARAMS = 21;
    public static final int AV_FRAME_DATA_DETECTION_BBOXES = 22;
    public static final int AV_FRAME_DATA_DOVI_RPU_BUFFER = 23;
    public static final int AV_FRAME_DATA_DOVI_METADATA = 24;
    public static final int AV_FRAME_DATA_DYNAMIC_HDR_VIVID = 25;
    public static final int AV_FRAME_DATA_AMBIENT_VIEWING_ENVIRONMENT = 26;
    public static final int AV_FRAME_DATA_VIDEO_HINT = 27;
    public static final int AV_FRAME_DATA_LCEVC = 28;
    public static final int AV_FRAME_DATA_VIEW_ID = 29;
    public static final int AV_FRAME_DATA_3D_REFERENCE_DISPLAYS = 30;
    public static final int AV_FRAME_DATA_EXIF = 31;
    public static final int AV_AFD_SAME = 8;
    public static final int AV_AFD_4_3 = 9;
    public static final int AV_AFD_16_9 = 10;
    public static final int AV_AFD_14_9 = 11;
    public static final int AV_AFD_4_3_SP_14_9 = 13;
    public static final int AV_AFD_16_9_SP_14_9 = 14;
    public static final int AV_AFD_SP_4_3 = 15;
    public static final int AV_SIDE_DATA_PROP_GLOBAL = 1;
    public static final int AV_SIDE_DATA_PROP_MULTI = 2;
    public static final int AV_SIDE_DATA_PROP_SIZE_DEPENDENT = 4;
    public static final int AV_SIDE_DATA_PROP_COLOR_DEPENDENT = 8;
    public static final int AV_SIDE_DATA_PROP_CHANNEL_DEPENDENT = 16;
    public static final int AV_FRAME_CROP_UNALIGNED = 1;
    public static final int AV_FRAME_SIDE_DATA_FLAG_UNIQUE = 1;
    public static final int AV_FRAME_SIDE_DATA_FLAG_REPLACE = 2;
    public static final int AV_FRAME_SIDE_DATA_FLAG_NEW_REF = 4;
    public static final int AV_SAMPLE_FMT_NONE = -1;
    public static final int AV_SAMPLE_FMT_U8 = 0;
    public static final int AV_SAMPLE_FMT_S16 = 1;
    public static final int AV_SAMPLE_FMT_S32 = 2;
    public static final int AV_SAMPLE_FMT_FLT = 3;
    public static final int AV_SAMPLE_FMT_DBL = 4;
    public static final int AV_SAMPLE_FMT_U8P = 5;
    public static final int AV_SAMPLE_FMT_S16P = 6;
    public static final int AV_SAMPLE_FMT_S32P = 7;
    public static final int AV_SAMPLE_FMT_FLTP = 8;
    public static final int AV_SAMPLE_FMT_DBLP = 9;
    public static final int AV_SAMPLE_FMT_S64 = 10;
    public static final int AV_SAMPLE_FMT_S64P = 11;
    public static final int AV_SAMPLE_FMT_NB = 12;
    public static final int AV_CHAN_NONE = -1;
    public static final int AV_CHAN_FRONT_LEFT = 0;
    public static final int AV_CHAN_FRONT_RIGHT = 1;
    public static final int AV_CHAN_FRONT_CENTER = 2;
    public static final int AV_CHAN_LOW_FREQUENCY = 3;
    public static final int AV_CHAN_BACK_LEFT = 4;
    public static final int AV_CHAN_BACK_RIGHT = 5;
    public static final int AV_CHAN_FRONT_LEFT_OF_CENTER = 6;
    public static final int AV_CHAN_FRONT_RIGHT_OF_CENTER = 7;
    public static final int AV_CHAN_BACK_CENTER = 8;
    public static final int AV_CHAN_SIDE_LEFT = 9;
    public static final int AV_CHAN_SIDE_RIGHT = 10;
    public static final int AV_CHAN_TOP_CENTER = 11;
    public static final int AV_CHAN_TOP_FRONT_LEFT = 12;
    public static final int AV_CHAN_TOP_FRONT_CENTER = 13;
    public static final int AV_CHAN_TOP_FRONT_RIGHT = 14;
    public static final int AV_CHAN_TOP_BACK_LEFT = 15;
    public static final int AV_CHAN_TOP_BACK_CENTER = 16;
    public static final int AV_CHAN_TOP_BACK_RIGHT = 17;
    public static final int AV_CHAN_STEREO_LEFT = 29;
    public static final int AV_CHAN_STEREO_RIGHT = 30;
    public static final int AV_CHAN_WIDE_LEFT = 31;
    public static final int AV_CHAN_WIDE_RIGHT = 32;
    public static final int AV_CHAN_SURROUND_DIRECT_LEFT = 33;
    public static final int AV_CHAN_SURROUND_DIRECT_RIGHT = 34;
    public static final int AV_CHAN_LOW_FREQUENCY_2 = 35;
    public static final int AV_CHAN_TOP_SIDE_LEFT = 36;
    public static final int AV_CHAN_TOP_SIDE_RIGHT = 37;
    public static final int AV_CHAN_BOTTOM_FRONT_CENTER = 38;
    public static final int AV_CHAN_BOTTOM_FRONT_LEFT = 39;
    public static final int AV_CHAN_BOTTOM_FRONT_RIGHT = 40;
    public static final int AV_CHAN_SIDE_SURROUND_LEFT = 41;
    public static final int AV_CHAN_SIDE_SURROUND_RIGHT = 42;
    public static final int AV_CHAN_TOP_SURROUND_LEFT = 43;
    public static final int AV_CHAN_TOP_SURROUND_RIGHT = 44;
    public static final int AV_CHAN_BINAURAL_LEFT = 61;
    public static final int AV_CHAN_BINAURAL_RIGHT = 62;
    public static final int AV_CHAN_UNUSED = 512;
    public static final int AV_CHAN_UNKNOWN = 768;
    public static final int AV_CHAN_AMBISONIC_BASE = 1024;
    public static final int AV_CHAN_AMBISONIC_END = 2047;
    public static final int AV_CHANNEL_ORDER_UNSPEC = 0;
    public static final int AV_CHANNEL_ORDER_NATIVE = 1;
    public static final int AV_CHANNEL_ORDER_CUSTOM = 2;
    public static final int AV_CHANNEL_ORDER_AMBISONIC = 3;
    public static final int FF_CHANNEL_ORDER_NB = 4;
    public static final long AV_CH_FRONT_LEFT = 1L;
    public static final long AV_CH_FRONT_RIGHT = 2L;
    public static final long AV_CH_FRONT_CENTER = 4L;
    public static final long AV_CH_LOW_FREQUENCY = 8L;
    public static final long AV_CH_BACK_LEFT = 16L;
    public static final long AV_CH_BACK_RIGHT = 32L;
    public static final long AV_CH_FRONT_LEFT_OF_CENTER = 64L;
    public static final long AV_CH_FRONT_RIGHT_OF_CENTER = 128L;
    public static final long AV_CH_BACK_CENTER = 256L;
    public static final long AV_CH_SIDE_LEFT = 512L;
    public static final long AV_CH_SIDE_RIGHT = 1024L;
    public static final long AV_CH_TOP_CENTER = 2048L;
    public static final long AV_CH_TOP_FRONT_LEFT = 4096L;
    public static final long AV_CH_TOP_FRONT_CENTER = 8192L;
    public static final long AV_CH_TOP_FRONT_RIGHT = 16384L;
    public static final long AV_CH_TOP_BACK_LEFT = 32768L;
    public static final long AV_CH_TOP_BACK_CENTER = 65536L;
    public static final long AV_CH_TOP_BACK_RIGHT = 131072L;
    public static final long AV_CH_STEREO_LEFT = 0x20000000L;
    public static final long AV_CH_STEREO_RIGHT = 0x40000000L;
    public static final long AV_CH_WIDE_LEFT = 0x80000000L;
    public static final long AV_CH_WIDE_RIGHT = 0x100000000L;
    public static final long AV_CH_SURROUND_DIRECT_LEFT = 0x200000000L;
    public static final long AV_CH_SURROUND_DIRECT_RIGHT = 0x400000000L;
    public static final long AV_CH_LOW_FREQUENCY_2 = 0x800000000L;
    public static final long AV_CH_TOP_SIDE_LEFT = 0x1000000000L;
    public static final long AV_CH_TOP_SIDE_RIGHT = 0x2000000000L;
    public static final long AV_CH_BOTTOM_FRONT_CENTER = 0x4000000000L;
    public static final long AV_CH_BOTTOM_FRONT_LEFT = 0x8000000000L;
    public static final long AV_CH_BOTTOM_FRONT_RIGHT = 0x10000000000L;
    public static final long AV_CH_SIDE_SURROUND_LEFT = 0x20000000000L;
    public static final long AV_CH_SIDE_SURROUND_RIGHT = 0x40000000000L;
    public static final long AV_CH_TOP_SURROUND_LEFT = 0x80000000000L;
    public static final long AV_CH_TOP_SURROUND_RIGHT = 0x100000000000L;
    public static final long AV_CH_BINAURAL_LEFT = 0x2000000000000000L;
    public static final long AV_CH_BINAURAL_RIGHT = 0x4000000000000000L;
    public static final long AV_CH_LAYOUT_MONO = 4L;
    public static final long AV_CH_LAYOUT_STEREO = 3L;
    public static final long AV_CH_LAYOUT_2POINT1 = 11L;
    public static final long AV_CH_LAYOUT_2_1 = 259L;
    public static final long AV_CH_LAYOUT_SURROUND = 7L;
    public static final long AV_CH_LAYOUT_3POINT1 = 15L;
    public static final long AV_CH_LAYOUT_4POINT0 = 263L;
    public static final long AV_CH_LAYOUT_4POINT1 = 271L;
    public static final long AV_CH_LAYOUT_2_2 = 1539L;
    public static final long AV_CH_LAYOUT_QUAD = 51L;
    public static final long AV_CH_LAYOUT_5POINT0 = 1543L;
    public static final long AV_CH_LAYOUT_5POINT1 = 1551L;
    public static final long AV_CH_LAYOUT_5POINT0_BACK = 55L;
    public static final long AV_CH_LAYOUT_5POINT1_BACK = 63L;
    public static final long AV_CH_LAYOUT_6POINT0 = 1799L;
    public static final long AV_CH_LAYOUT_6POINT0_FRONT = 1731L;
    public static final long AV_CH_LAYOUT_HEXAGONAL = 311L;
    public static final long AV_CH_LAYOUT_3POINT1POINT2 = 20495L;
    public static final long AV_CH_LAYOUT_6POINT1 = 1807L;
    public static final long AV_CH_LAYOUT_6POINT1_BACK = 319L;
    public static final long AV_CH_LAYOUT_6POINT1_FRONT = 1739L;
    public static final long AV_CH_LAYOUT_7POINT0 = 1591L;
    public static final long AV_CH_LAYOUT_7POINT0_FRONT = 1735L;
    public static final long AV_CH_LAYOUT_7POINT1 = 1599L;
    public static final long AV_CH_LAYOUT_7POINT1_WIDE = 1743L;
    public static final long AV_CH_LAYOUT_7POINT1_WIDE_BACK = 255L;
    public static final long AV_CH_LAYOUT_5POINT1POINT2 = 22031L;
    public static final long AV_CH_LAYOUT_5POINT1POINT2_BACK = 20543L;
    public static final long AV_CH_LAYOUT_OCTAGONAL = 1847L;
    public static final long AV_CH_LAYOUT_CUBE = 184371L;
    public static final long AV_CH_LAYOUT_5POINT1POINT4_BACK = 185871L;
    public static final long AV_CH_LAYOUT_7POINT1POINT2 = 22079L;
    public static final long AV_CH_LAYOUT_7POINT1POINT4_BACK = 185919L;
    public static final long AV_CH_LAYOUT_7POINT2POINT3 = 34359825983L;
    public static final long AV_CH_LAYOUT_9POINT1POINT4_BACK = 186111L;
    public static final long AV_CH_LAYOUT_9POINT1POINT6 = 206158616319L;
    public static final long AV_CH_LAYOUT_HEXADECAGONAL = 6442710839L;
    public static final long AV_CH_LAYOUT_BINAURAL = 0x6000000000000000L;
    public static final long AV_CH_LAYOUT_STEREO_DOWNMIX = 0x60000000L;
    public static final long AV_CH_LAYOUT_22POINT2 = 2164663779327L;
    public static final long AV_CH_LAYOUT_7POINT1_TOP_BACK = 20543L;
    public static final int AV_MATRIX_ENCODING_NONE = 0;
    public static final int AV_MATRIX_ENCODING_DOLBY = 1;
    public static final int AV_MATRIX_ENCODING_DPLII = 2;
    public static final int AV_MATRIX_ENCODING_DPLIIX = 3;
    public static final int AV_MATRIX_ENCODING_DPLIIZ = 4;
    public static final int AV_MATRIX_ENCODING_DOLBYEX = 5;
    public static final int AV_MATRIX_ENCODING_DOLBYHEADPHONE = 6;
    public static final int AV_MATRIX_ENCODING_NB = 7;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_MONO;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_STEREO;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_2POINT1;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_2_1;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_SURROUND;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_3POINT1;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_4POINT0;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_4POINT1;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_2_2;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_QUAD;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_5POINT0;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_5POINT1;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_5POINT0_BACK;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_5POINT1_BACK;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_6POINT0;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_6POINT0_FRONT;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_3POINT1POINT2;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_HEXAGONAL;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_6POINT1;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_6POINT1_BACK;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_6POINT1_FRONT;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_7POINT0;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_7POINT0_FRONT;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_7POINT1;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_7POINT1_WIDE;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_7POINT1_WIDE_BACK;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_5POINT1POINT2;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_5POINT1POINT2_BACK;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_OCTAGONAL;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_CUBE;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_5POINT1POINT4_BACK;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_7POINT1POINT2;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_7POINT1POINT4_BACK;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_7POINT2POINT3;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_9POINT1POINT4_BACK;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_9POINT1POINT6;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_HEXADECAGONAL;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_BINAURAL;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_STEREO_DOWNMIX;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_22POINT2;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_7POINT1_TOP_BACK;
    public static final AVChannelLayout AV_CHANNEL_LAYOUT_AMBISONIC_FIRST_ORDER;
    public static final int AV_CHANNEL_LAYOUT_RETYPE_FLAG_LOSSLESS = 1;
    public static final int AV_CHANNEL_LAYOUT_RETYPE_FLAG_CANONICAL = 2;
    public static final int AV_CPU_FLAG_FORCE = Integer.MIN_VALUE;
    public static final int AV_CPU_FLAG_MMX = 1;
    public static final int AV_CPU_FLAG_MMXEXT = 2;
    public static final int AV_CPU_FLAG_MMX2 = 2;
    public static final int AV_CPU_FLAG_3DNOW = 4;
    public static final int AV_CPU_FLAG_SSE = 8;
    public static final int AV_CPU_FLAG_SSE2 = 16;
    public static final int AV_CPU_FLAG_SSE2SLOW = 0x40000000;
    public static final int AV_CPU_FLAG_3DNOWEXT = 32;
    public static final int AV_CPU_FLAG_SSE3 = 64;
    public static final int AV_CPU_FLAG_SSE3SLOW = 0x20000000;
    public static final int AV_CPU_FLAG_SSSE3 = 128;
    public static final int AV_CPU_FLAG_SSSE3SLOW = 0x4000000;
    public static final int AV_CPU_FLAG_ATOM = 0x10000000;
    public static final int AV_CPU_FLAG_SSE4 = 256;
    public static final int AV_CPU_FLAG_SSE42 = 512;
    public static final int AV_CPU_FLAG_AESNI = 524288;
    public static final int AV_CPU_FLAG_CLMUL = 0x400000;
    public static final int AV_CPU_FLAG_AVX = 16384;
    public static final int AV_CPU_FLAG_AVXSLOW = 0x8000000;
    public static final int AV_CPU_FLAG_XOP = 1024;
    public static final int AV_CPU_FLAG_FMA4 = 2048;
    public static final int AV_CPU_FLAG_CMOV = 4096;
    public static final int AV_CPU_FLAG_AVX2 = 32768;
    public static final int AV_CPU_FLAG_FMA3 = 65536;
    public static final int AV_CPU_FLAG_BMI1 = 131072;
    public static final int AV_CPU_FLAG_BMI2 = 262144;
    public static final int AV_CPU_FLAG_AVX512 = 0x100000;
    public static final int AV_CPU_FLAG_AVX512ICL = 0x200000;
    public static final int AV_CPU_FLAG_SLOW_GATHER = 0x2000000;
    public static final int AV_CPU_FLAG_ALTIVEC = 1;
    public static final int AV_CPU_FLAG_VSX = 2;
    public static final int AV_CPU_FLAG_POWER8 = 4;
    public static final int AV_CPU_FLAG_ARMV5TE = 1;
    public static final int AV_CPU_FLAG_ARMV6 = 2;
    public static final int AV_CPU_FLAG_ARMV6T2 = 4;
    public static final int AV_CPU_FLAG_VFP = 8;
    public static final int AV_CPU_FLAG_VFPV3 = 16;
    public static final int AV_CPU_FLAG_NEON = 32;
    public static final int AV_CPU_FLAG_ARMV8 = 64;
    public static final int AV_CPU_FLAG_VFP_VM = 128;
    public static final int AV_CPU_FLAG_DOTPROD = 256;
    public static final int AV_CPU_FLAG_I8MM = 512;
    public static final int AV_CPU_FLAG_SVE = 1024;
    public static final int AV_CPU_FLAG_SVE2 = 2048;
    public static final int AV_CPU_FLAG_SME = 4096;
    public static final int AV_CPU_FLAG_ARM_CRC = 8192;
    public static final int AV_CPU_FLAG_SME2 = 16384;
    public static final int AV_CPU_FLAG_SME_I16I64 = 32768;
    public static final int AV_CPU_FLAG_SETEND = 65536;
    public static final int AV_CPU_FLAG_MMI = 1;
    public static final int AV_CPU_FLAG_MSA = 2;
    public static final int AV_CPU_FLAG_LSX = 1;
    public static final int AV_CPU_FLAG_LASX = 2;
    public static final int AV_CPU_FLAG_RVI = 1;
    public static final int AV_CPU_FLAG_RVF = 2;
    public static final int AV_CPU_FLAG_RVD = 4;
    public static final int AV_CPU_FLAG_RVV_I32 = 8;
    public static final int AV_CPU_FLAG_RVV_F32 = 16;
    public static final int AV_CPU_FLAG_RVV_I64 = 32;
    public static final int AV_CPU_FLAG_RVV_F64 = 64;
    public static final int AV_CPU_FLAG_RVB_BASIC = 128;
    public static final int AV_CPU_FLAG_RVB_ADDR = 256;
    public static final int AV_CPU_FLAG_RV_ZVBB = 512;
    public static final int AV_CPU_FLAG_RV_MISALIGNED = 1024;
    public static final int AV_CPU_FLAG_RVB = 2048;
    public static final int AV_CPU_FLAG_SIMD128 = 1;
    public static final int AV_DICT_MATCH_CASE = 1;
    public static final int AV_DICT_IGNORE_SUFFIX = 2;
    public static final int AV_DICT_DONT_STRDUP_KEY = 4;
    public static final int AV_DICT_DONT_STRDUP_VAL = 8;
    public static final int AV_DICT_DONT_OVERWRITE = 16;
    public static final int AV_DICT_APPEND = 32;
    public static final int AV_DICT_MULTIKEY = 64;
    public static final int AV_DICT_DEDUP = 128;
    public static final int AV_OPT_TYPE_FLAGS = 1;
    public static final int AV_OPT_TYPE_INT = 2;
    public static final int AV_OPT_TYPE_INT64 = 3;
    public static final int AV_OPT_TYPE_DOUBLE = 4;
    public static final int AV_OPT_TYPE_FLOAT = 5;
    public static final int AV_OPT_TYPE_STRING = 6;
    public static final int AV_OPT_TYPE_RATIONAL = 7;
    public static final int AV_OPT_TYPE_BINARY = 8;
    public static final int AV_OPT_TYPE_DICT = 9;
    public static final int AV_OPT_TYPE_UINT64 = 10;
    public static final int AV_OPT_TYPE_CONST = 11;
    public static final int AV_OPT_TYPE_IMAGE_SIZE = 12;
    public static final int AV_OPT_TYPE_PIXEL_FMT = 13;
    public static final int AV_OPT_TYPE_SAMPLE_FMT = 14;
    public static final int AV_OPT_TYPE_VIDEO_RATE = 15;
    public static final int AV_OPT_TYPE_DURATION = 16;
    public static final int AV_OPT_TYPE_COLOR = 17;
    public static final int AV_OPT_TYPE_BOOL = 18;
    public static final int AV_OPT_TYPE_CHLAYOUT = 19;
    public static final int AV_OPT_TYPE_UINT = 20;
    public static final int AV_OPT_TYPE_FLAG_ARRAY = 65536;
    public static final int AV_OPT_FLAG_ENCODING_PARAM = 1;
    public static final int AV_OPT_FLAG_DECODING_PARAM = 2;
    public static final int AV_OPT_FLAG_AUDIO_PARAM = 8;
    public static final int AV_OPT_FLAG_VIDEO_PARAM = 16;
    public static final int AV_OPT_FLAG_SUBTITLE_PARAM = 32;
    public static final int AV_OPT_FLAG_EXPORT = 64;
    public static final int AV_OPT_FLAG_READONLY = 128;
    public static final int AV_OPT_FLAG_BSF_PARAM = 256;
    public static final int AV_OPT_FLAG_RUNTIME_PARAM = 32768;
    public static final int AV_OPT_FLAG_FILTERING_PARAM = 65536;
    public static final int AV_OPT_FLAG_DEPRECATED = 131072;
    public static final int AV_OPT_FLAG_CHILD_CONSTS = 262144;
    public static final int AV_OPT_SEARCH_CHILDREN = 1;
    public static final int AV_OPT_SEARCH_FAKE_OBJ = 2;
    public static final int AV_OPT_ALLOW_NULL = 4;
    public static final int AV_OPT_ARRAY_REPLACE = 8;
    public static final int AV_OPT_MULTI_COMPONENT_RANGE = 4096;
    public static final int AV_OPT_FLAG_IMPLICIT_KEY = 1;
    public static final int AV_OPT_SERIALIZE_SKIP_DEFAULTS = 1;
    public static final int AV_OPT_SERIALIZE_OPT_FLAGS_EXACT = 2;
    public static final int AV_OPT_SERIALIZE_SEARCH_CHILDREN = 4;
    public static final int AV_PIX_FMT_FLAG_BE = 1;
    public static final int AV_PIX_FMT_FLAG_PAL = 2;
    public static final int AV_PIX_FMT_FLAG_BITSTREAM = 4;
    public static final int AV_PIX_FMT_FLAG_HWACCEL = 8;
    public static final int AV_PIX_FMT_FLAG_PLANAR = 16;
    public static final int AV_PIX_FMT_FLAG_RGB = 32;
    public static final int AV_PIX_FMT_FLAG_ALPHA = 128;
    public static final int AV_PIX_FMT_FLAG_BAYER = 256;
    public static final int AV_PIX_FMT_FLAG_FLOAT = 512;
    public static final int AV_PIX_FMT_FLAG_XYZ = 1024;
    public static final int FF_LOSS_RESOLUTION = 1;
    public static final int FF_LOSS_DEPTH = 2;
    public static final int FF_LOSS_COLORSPACE = 4;
    public static final int FF_LOSS_ALPHA = 8;
    public static final int FF_LOSS_COLORQUANT = 16;
    public static final int FF_LOSS_CHROMA = 32;
    public static final int FF_LOSS_EXCESS_RESOLUTION = 64;
    public static final int FF_LOSS_EXCESS_DEPTH = 128;
    public static final int AV_DOWNMIX_TYPE_UNKNOWN = 0;
    public static final int AV_DOWNMIX_TYPE_LORO = 1;
    public static final int AV_DOWNMIX_TYPE_LTRT = 2;
    public static final int AV_DOWNMIX_TYPE_DPLII = 3;
    public static final int AV_DOWNMIX_TYPE_NB = 4;
    public static final int AV_STEREO3D_2D = 0;
    public static final int AV_STEREO3D_SIDEBYSIDE = 1;
    public static final int AV_STEREO3D_TOPBOTTOM = 2;
    public static final int AV_STEREO3D_FRAMESEQUENCE = 3;
    public static final int AV_STEREO3D_CHECKERBOARD = 4;
    public static final int AV_STEREO3D_SIDEBYSIDE_QUINCUNX = 5;
    public static final int AV_STEREO3D_LINES = 6;
    public static final int AV_STEREO3D_COLUMNS = 7;
    public static final int AV_STEREO3D_UNSPEC = 8;
    public static final int AV_STEREO3D_VIEW_PACKED = 0;
    public static final int AV_STEREO3D_VIEW_LEFT = 1;
    public static final int AV_STEREO3D_VIEW_RIGHT = 2;
    public static final int AV_STEREO3D_VIEW_UNSPEC = 3;
    public static final int AV_PRIMARY_EYE_NONE = 0;
    public static final int AV_PRIMARY_EYE_LEFT = 1;
    public static final int AV_PRIMARY_EYE_RIGHT = 2;
    public static final int AV_STEREO3D_FLAG_INVERT = 1;
    public static final String FFMPEG_VERSION = "8.1.2";
    public static final int AV_FIFO_FLAG_AUTO_GROW = 1;
    public static final int AV_HWDEVICE_TYPE_NONE = 0;
    public static final int AV_HWDEVICE_TYPE_VDPAU = 1;
    public static final int AV_HWDEVICE_TYPE_CUDA = 2;
    public static final int AV_HWDEVICE_TYPE_VAAPI = 3;
    public static final int AV_HWDEVICE_TYPE_DXVA2 = 4;
    public static final int AV_HWDEVICE_TYPE_QSV = 5;
    public static final int AV_HWDEVICE_TYPE_VIDEOTOOLBOX = 6;
    public static final int AV_HWDEVICE_TYPE_D3D11VA = 7;
    public static final int AV_HWDEVICE_TYPE_DRM = 8;
    public static final int AV_HWDEVICE_TYPE_OPENCL = 9;
    public static final int AV_HWDEVICE_TYPE_MEDIACODEC = 10;
    public static final int AV_HWDEVICE_TYPE_VULKAN = 11;
    public static final int AV_HWDEVICE_TYPE_D3D12VA = 12;
    public static final int AV_HWDEVICE_TYPE_AMF = 13;
    public static final int AV_HWDEVICE_TYPE_OHCODEC = 14;
    public static final int AV_HWDEVICE_TYPE_V4L2REQUEST = 15;
    public static final int AV_HWFRAME_TRANSFER_DIRECTION_FROM = 0;
    public static final int AV_HWFRAME_TRANSFER_DIRECTION_TO = 1;
    public static final int AV_HWFRAME_MAP_READ = 1;
    public static final int AV_HWFRAME_MAP_WRITE = 2;
    public static final int AV_HWFRAME_MAP_OVERWRITE = 4;
    public static final int AV_HWFRAME_MAP_DIRECT = 8;
    public static final int AES_CTR_KEY_SIZE = 16;
    public static final int AES_CTR_IV_SIZE = 8;
    public static final int AV_BF_ROUNDS = 16;
    public static final int AV_CRC_8_ATM = 0;
    public static final int AV_CRC_16_ANSI = 1;
    public static final int AV_CRC_16_CCITT = 2;
    public static final int AV_CRC_32_IEEE = 3;
    public static final int AV_CRC_32_IEEE_LE = 4;
    public static final int AV_CRC_16_ANSI_LE = 5;
    public static final int AV_CRC_24_IEEE = 6;
    public static final int AV_CRC_8_EBU = 7;
    public static final int AV_CRC_MAX = 8;
    public static final int AV_HMAC_MD5 = 0;
    public static final int AV_HMAC_SHA1 = 1;
    public static final int AV_HMAC_SHA224 = 2;
    public static final int AV_HMAC_SHA256 = 3;
    public static final int AV_HMAC_SHA384 = 4;
    public static final int AV_HMAC_SHA512 = 5;
    public static final int AV_ESCAPE_MODE_AUTO = 0;
    public static final int AV_ESCAPE_MODE_BACKSLASH = 1;
    public static final int AV_ESCAPE_MODE_QUOTE = 2;
    public static final int AV_ESCAPE_MODE_XML = 3;
    public static final int AV_ESCAPE_FLAG_WHITESPACE = 1;
    public static final int AV_ESCAPE_FLAG_STRICT = 2;
    public static final int AV_ESCAPE_FLAG_XML_SINGLE_QUOTES = 4;
    public static final int AV_ESCAPE_FLAG_XML_DOUBLE_QUOTES = 8;
    public static final int AV_UTF8_FLAG_ACCEPT_INVALID_BIG_CODES = 1;
    public static final int AV_UTF8_FLAG_ACCEPT_NON_CHARACTERS = 2;
    public static final int AV_UTF8_FLAG_ACCEPT_SURROGATES = 4;
    public static final int AV_UTF8_FLAG_EXCLUDE_XML_INVALID_CONTROL_CODES = 8;
    public static final int AV_UTF8_FLAG_ACCEPT_ALL = 7;
    public static final int AV_BPRINT_SIZE_UNLIMITED = -1;
    public static final int AV_BPRINT_SIZE_AUTOMATIC = 1;
    public static final int AV_BPRINT_SIZE_COUNT_ONLY = 0;
    public static final int AV_HASH_MAX_SIZE = 64;
    public static final int AV_HDR_PLUS_OVERLAP_PROCESS_WEIGHTED_AVERAGING = 0;
    public static final int AV_HDR_PLUS_OVERLAP_PROCESS_LAYERING = 1;
    public static final int AV_HDR_PLUS_MAX_PAYLOAD_SIZE = 907;
    public static final int AV_SPHERICAL_EQUIRECTANGULAR = 0;
    public static final int AV_SPHERICAL_CUBEMAP = 1;
    public static final int AV_SPHERICAL_EQUIRECTANGULAR_TILE = 2;
    public static final int AV_SPHERICAL_HALF_EQUIRECTANGULAR = 3;
    public static final int AV_SPHERICAL_RECTILINEAR = 4;
    public static final int AV_SPHERICAL_FISHEYE = 5;
    public static final int AV_SPHERICAL_PARAMETRIC_IMMERSIVE = 6;
    public static final int AV_THREAD_MESSAGE_NONBLOCK = 1;
    public static final int AV_TIMECODE_STR_SIZE = 23;
    public static final int AV_TIMECODE_FLAG_DROPFRAME = 1;
    public static final int AV_TIMECODE_FLAG_24HOURSMAX = 2;
    public static final int AV_TIMECODE_FLAG_ALLOWNEGATIVE = 4;
    public static final int AV_TS_MAX_STRING_SIZE = 32;
    public static final int AV_CONTAINER_FIFO_FLAG_REF = 1;
    public static final int AV_CONTAINER_FIFO_FLAG_USER = 65536;
    public static final int AV_TX_FLOAT_FFT = 0;
    public static final int AV_TX_DOUBLE_FFT = 2;
    public static final int AV_TX_INT32_FFT = 4;
    public static final int AV_TX_FLOAT_MDCT = 1;
    public static final int AV_TX_DOUBLE_MDCT = 3;
    public static final int AV_TX_INT32_MDCT = 5;
    public static final int AV_TX_FLOAT_RDFT = 6;
    public static final int AV_TX_DOUBLE_RDFT = 7;
    public static final int AV_TX_INT32_RDFT = 8;
    public static final int AV_TX_FLOAT_DCT = 9;
    public static final int AV_TX_DOUBLE_DCT = 10;
    public static final int AV_TX_INT32_DCT = 11;
    public static final int AV_TX_FLOAT_DCT_I = 12;
    public static final int AV_TX_DOUBLE_DCT_I = 13;
    public static final int AV_TX_INT32_DCT_I = 14;
    public static final int AV_TX_FLOAT_DST_I = 15;
    public static final int AV_TX_DOUBLE_DST_I = 16;
    public static final int AV_TX_INT32_DST_I = 17;
    public static final int AV_TX_NB = 18;
    public static final long AV_TX_INPLACE = 1L;
    public static final long AV_TX_UNALIGNED = 2L;
    public static final long AV_TX_FULL_IMDCT = 4L;
    public static final long AV_TX_REAL_TO_REAL = 8L;
    public static final long AV_TX_REAL_TO_IMAGINARY = 16L;
    public static final int LIBAVUTIL_VERSION_MAJOR = 60;
    public static final int LIBAVUTIL_VERSION_MINOR = 26;
    public static final int LIBAVUTIL_VERSION_MICRO = 102;
    public static final int LIBAVUTIL_VERSION_INT;
    public static final int LIBAVUTIL_BUILD;
    public static final String LIBAVUTIL_IDENT;
    public static final boolean FF_API_MOD_UINTP2 = true;
    public static final boolean FF_API_RISCV_FD_ZBA = true;
    public static final boolean FF_API_VULKAN_FIXED_QUEUES = true;
    public static final boolean FF_API_OPT_INT_LIST = true;
    public static final boolean FF_API_OPT_PTR = true;
    public static final boolean FF_API_CPU_FLAG_FORCE = true;
    public static final boolean FF_API_DOVI_L11_INVALID_PROPS = true;
    public static final boolean FF_API_ASSERT_FPU = true;
    public static final boolean FF_API_VULKAN_SYNC_QUEUES = true;

    @NoException
    @Cast(value={"unsigned"})
    public static native int avutil_version();

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_version_info();

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer avutil_configuration();

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer avutil_license();

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_get_media_type_string(@Cast(value={"AVMediaType"}) int var0);

    @MemberGetter
    @Cast(value={"int64_t"})
    public static native long AV_NOPTS_VALUE();

    @NoException
    @Cast(value={"char"})
    public static native byte av_get_picture_type_char(@Cast(value={"AVPictureType"}) int var0);

    @NoException
    public static native Pointer av_x_if_null(@Const Pointer var0, @Const Pointer var1);

    @NoException
    @Cast(value={"unsigned"})
    @Deprecated
    public static native int av_int_list_length_for_size(@Cast(value={"unsigned"}) int var0, @Const Pointer var1, @Cast(value={"uint64_t"}) long var2);

    @NoException
    @ByVal
    public static native AVRational av_get_time_base_q();

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_fourcc_make_string(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"uint32_t"}) int var1);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_fourcc_make_string(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"uint32_t"}) int var1);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_fourcc_make_string(@Cast(value={"char*"}) byte[] var0, @Cast(value={"uint32_t"}) int var1);

    @MemberGetter
    public static native int AVERROR_BSF_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_BUG();

    @MemberGetter
    public static native int AVERROR_BUFFER_TOO_SMALL();

    @MemberGetter
    public static native int AVERROR_DECODER_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_DEMUXER_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_ENCODER_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_EOF();

    @MemberGetter
    public static native int AVERROR_EXIT();

    @MemberGetter
    public static native int AVERROR_EXTERNAL();

    @MemberGetter
    public static native int AVERROR_FILTER_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_INVALIDDATA();

    @MemberGetter
    public static native int AVERROR_MUXER_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_OPTION_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_PATCHWELCOME();

    @MemberGetter
    public static native int AVERROR_PROTOCOL_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_STREAM_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_BUG2();

    @MemberGetter
    public static native int AVERROR_UNKNOWN();

    @MemberGetter
    public static native int AVERROR_HTTP_BAD_REQUEST();

    @MemberGetter
    public static native int AVERROR_HTTP_UNAUTHORIZED();

    @MemberGetter
    public static native int AVERROR_HTTP_FORBIDDEN();

    @MemberGetter
    public static native int AVERROR_HTTP_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_HTTP_TOO_MANY_REQUESTS();

    @MemberGetter
    public static native int AVERROR_HTTP_OTHER_4XX();

    @MemberGetter
    public static native int AVERROR_HTTP_SERVER_ERROR();

    @NoException
    public static native int av_strerror(int var0, @Cast(value={"char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int av_strerror(int var0, @Cast(value={"char*"}) ByteBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int av_strerror(int var0, @Cast(value={"char*"}) byte[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_make_error_string(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"size_t"}) long var1, int var3);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_make_error_string(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"size_t"}) long var1, int var3);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_make_error_string(@Cast(value={"char*"}) byte[] var0, @Cast(value={"size_t"}) long var1, int var3);

    @NoException
    public static native Pointer av_malloc(@Cast(value={"size_t"}) long var0);

    @NoException
    public static native Pointer av_mallocz(@Cast(value={"size_t"}) long var0);

    @NoException
    public static native Pointer av_malloc_array(@Cast(value={"size_t"}) long var0, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native Pointer av_calloc(@Cast(value={"size_t"}) long var0, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native Pointer av_realloc(Pointer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native int av_reallocp(Pointer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native Pointer av_realloc_f(Pointer var0, @Cast(value={"size_t"}) long var1, @Cast(value={"size_t"}) long var3);

    @NoException
    public static native Pointer av_realloc_array(Pointer var0, @Cast(value={"size_t"}) long var1, @Cast(value={"size_t"}) long var3);

    @NoException
    public static native int av_reallocp_array(Pointer var0, @Cast(value={"size_t"}) long var1, @Cast(value={"size_t"}) long var3);

    @NoException
    public static native Pointer av_fast_realloc(Pointer var0, @Cast(value={"unsigned int*"}) IntPointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native Pointer av_fast_realloc(Pointer var0, @Cast(value={"unsigned int*"}) IntBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native Pointer av_fast_realloc(Pointer var0, @Cast(value={"unsigned int*"}) int[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_fast_malloc(Pointer var0, @Cast(value={"unsigned int*"}) IntPointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_fast_malloc(Pointer var0, @Cast(value={"unsigned int*"}) IntBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_fast_malloc(Pointer var0, @Cast(value={"unsigned int*"}) int[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_fast_mallocz(Pointer var0, @Cast(value={"unsigned int*"}) IntPointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_fast_mallocz(Pointer var0, @Cast(value={"unsigned int*"}) IntBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_fast_mallocz(Pointer var0, @Cast(value={"unsigned int*"}) int[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_free(Pointer var0);

    @NoException
    public static native void av_freep(Pointer var0);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_strdup(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_strdup(String var0);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_strndup(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_strndup(String var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native Pointer av_memdup(@Const Pointer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native void av_memcpy_backptr(@Cast(value={"uint8_t*"}) BytePointer var0, int var1, int var2);

    @NoException
    public static native void av_memcpy_backptr(@Cast(value={"uint8_t*"}) ByteBuffer var0, int var1, int var2);

    @NoException
    public static native void av_memcpy_backptr(@Cast(value={"uint8_t*"}) byte[] var0, int var1, int var2);

    @NoException
    public static native void av_dynarray_add(Pointer var0, IntPointer var1, Pointer var2);

    @NoException
    public static native void av_dynarray_add(Pointer var0, IntBuffer var1, Pointer var2);

    @NoException
    public static native void av_dynarray_add(Pointer var0, int[] var1, Pointer var2);

    @NoException
    public static native int av_dynarray_add_nofree(Pointer var0, IntPointer var1, Pointer var2);

    @NoException
    public static native int av_dynarray_add_nofree(Pointer var0, IntBuffer var1, Pointer var2);

    @NoException
    public static native int av_dynarray_add_nofree(Pointer var0, int[] var1, Pointer var2);

    @NoException
    public static native Pointer av_dynarray2_add(@Cast(value={"void**"}) PointerPointer var0, IntPointer var1, @Cast(value={"size_t"}) long var2, @Cast(value={"const uint8_t*"}) BytePointer var4);

    @NoException
    public static native Pointer av_dynarray2_add(@Cast(value={"void**"}) @ByPtrPtr Pointer var0, IntPointer var1, @Cast(value={"size_t"}) long var2, @Cast(value={"const uint8_t*"}) BytePointer var4);

    @NoException
    public static native Pointer av_dynarray2_add(@Cast(value={"void**"}) @ByPtrPtr Pointer var0, IntBuffer var1, @Cast(value={"size_t"}) long var2, @Cast(value={"const uint8_t*"}) ByteBuffer var4);

    @NoException
    public static native Pointer av_dynarray2_add(@Cast(value={"void**"}) @ByPtrPtr Pointer var0, int[] var1, @Cast(value={"size_t"}) long var2, @Cast(value={"const uint8_t*"}) byte[] var4);

    @NoException
    public static native int av_size_mult(@Cast(value={"size_t"}) long var0, @Cast(value={"size_t"}) long var2, @Cast(value={"size_t*"}) SizeTPointer var4);

    @NoException
    public static native void av_max_alloc(@Cast(value={"size_t"}) long var0);

    @NoException
    @Cast(value={"int64_t"})
    public static native long av_gettime();

    @NoException
    @Cast(value={"int64_t"})
    public static native long av_gettime_relative();

    @NoException
    public static native int av_gettime_relative_is_monotonic();

    @NoException
    public static native int av_usleep(@Cast(value={"unsigned"}) int var0);

    @MemberGetter
    public static native double NAN();

    @MemberGetter
    public static native double INFINITY();

    @NoException
    @Cast(value={"int64_t"})
    @Const
    public static native long av_gcd(@Cast(value={"int64_t"}) long var0, @Cast(value={"int64_t"}) long var2);

    @NoException
    @Cast(value={"int64_t"})
    @Const
    public static native long av_rescale(@Cast(value={"int64_t"}) long var0, @Cast(value={"int64_t"}) long var2, @Cast(value={"int64_t"}) long var4);

    @NoException
    @Cast(value={"int64_t"})
    @Const
    public static native long av_rescale_rnd(@Cast(value={"int64_t"}) long var0, @Cast(value={"int64_t"}) long var2, @Cast(value={"int64_t"}) long var4, @Cast(value={"AVRounding"}) int var6);

    @NoException
    @Cast(value={"int64_t"})
    @Const
    public static native long av_rescale_q(@Cast(value={"int64_t"}) long var0, @ByVal AVRational var2, @ByVal AVRational var3);

    @NoException
    @Cast(value={"int64_t"})
    @Const
    public static native long av_rescale_q_rnd(@Cast(value={"int64_t"}) long var0, @ByVal AVRational var2, @ByVal AVRational var3, @Cast(value={"AVRounding"}) int var4);

    @NoException
    public static native int av_compare_ts(@Cast(value={"int64_t"}) long var0, @ByVal AVRational var2, @Cast(value={"int64_t"}) long var3, @ByVal AVRational var5);

    @NoException
    @Cast(value={"int64_t"})
    public static native long av_compare_mod(@Cast(value={"uint64_t"}) long var0, @Cast(value={"uint64_t"}) long var2, @Cast(value={"uint64_t"}) long var4);

    @NoException
    @Cast(value={"int64_t"})
    public static native long av_rescale_delta(@ByVal AVRational var0, @Cast(value={"int64_t"}) long var1, @ByVal AVRational var3, int var4, @Cast(value={"int64_t*"}) LongPointer var5, @ByVal AVRational var6);

    @NoException
    @Cast(value={"int64_t"})
    public static native long av_rescale_delta(@ByVal AVRational var0, @Cast(value={"int64_t"}) long var1, @ByVal AVRational var3, int var4, @Cast(value={"int64_t*"}) LongBuffer var5, @ByVal AVRational var6);

    @NoException
    @Cast(value={"int64_t"})
    public static native long av_rescale_delta(@ByVal AVRational var0, @Cast(value={"int64_t"}) long var1, @ByVal AVRational var3, int var4, @Cast(value={"int64_t*"}) long[] var5, @ByVal AVRational var6);

    @NoException
    @Cast(value={"int64_t"})
    public static native long av_add_stable(@ByVal AVRational var0, @Cast(value={"int64_t"}) long var1, @ByVal AVRational var3, @Cast(value={"int64_t"}) long var4);

    @NoException
    public static native double av_bessel_i0(double var0);

    @NoException
    @ByVal
    public static native AVRational av_make_q(int var0, int var1);

    @NoException
    public static native int av_cmp_q(@ByVal AVRational var0, @ByVal AVRational var1);

    @NoException
    public static native double av_q2d(@ByVal AVRational var0);

    @NoException
    public static native int av_reduce(IntPointer var0, IntPointer var1, @Cast(value={"int64_t"}) long var2, @Cast(value={"int64_t"}) long var4, @Cast(value={"int64_t"}) long var6);

    @NoException
    public static native int av_reduce(IntBuffer var0, IntBuffer var1, @Cast(value={"int64_t"}) long var2, @Cast(value={"int64_t"}) long var4, @Cast(value={"int64_t"}) long var6);

    @NoException
    public static native int av_reduce(int[] var0, int[] var1, @Cast(value={"int64_t"}) long var2, @Cast(value={"int64_t"}) long var4, @Cast(value={"int64_t"}) long var6);

    @NoException
    @ByVal
    @Const
    public static native AVRational av_mul_q(@ByVal AVRational var0, @ByVal AVRational var1);

    @NoException
    @ByVal
    @Const
    public static native AVRational av_div_q(@ByVal AVRational var0, @ByVal AVRational var1);

    @NoException
    @ByVal
    @Const
    public static native AVRational av_add_q(@ByVal AVRational var0, @ByVal AVRational var1);

    @NoException
    @ByVal
    @Const
    public static native AVRational av_sub_q(@ByVal AVRational var0, @ByVal AVRational var1);

    @NoException
    @ByVal
    public static native AVRational av_inv_q(@ByVal AVRational var0);

    @NoException
    @ByVal
    @Const
    public static native AVRational av_d2q(double var0, int var2);

    @NoException
    public static native int av_nearer_q(@ByVal AVRational var0, @ByVal AVRational var1, @ByVal AVRational var2);

    @NoException
    public static native int av_find_nearest_q_idx(@ByVal AVRational var0, @Const AVRational var1);

    @NoException
    @Cast(value={"uint32_t"})
    public static native int av_q2intfloat(@ByVal AVRational var0);

    @NoException
    @ByVal
    public static native AVRational av_gcd_q(@ByVal AVRational var0, @ByVal AVRational var1, int var2, @ByVal AVRational var3);

    @NoException
    public static native void av_log(Pointer var0, int var1, @Cast(value={"const char*"}) BytePointer var2);

    @NoException
    public static native void av_log(Pointer var0, int var1, String var2);

    @NoException
    public static native void av_log_once(Pointer var0, int var1, int var2, IntPointer var3, @Cast(value={"const char*"}) BytePointer var4);

    @NoException
    public static native void av_log_once(Pointer var0, int var1, int var2, IntBuffer var3, String var4);

    @NoException
    public static native void av_log_once(Pointer var0, int var1, int var2, int[] var3, @Cast(value={"const char*"}) BytePointer var4);

    @NoException
    public static native void av_log_once(Pointer var0, int var1, int var2, IntPointer var3, String var4);

    @NoException
    public static native void av_log_once(Pointer var0, int var1, int var2, IntBuffer var3, @Cast(value={"const char*"}) BytePointer var4);

    @NoException
    public static native void av_log_once(Pointer var0, int var1, int var2, int[] var3, String var4);

    @NoException
    public static native void av_vlog(Pointer var0, int var1, @Cast(value={"const char*"}) BytePointer var2, @ByVal @Cast(value={"va_list*"}) Pointer var3);

    @NoException
    public static native void av_vlog(Pointer var0, int var1, String var2, @ByVal @Cast(value={"va_list*"}) Pointer var3);

    @NoException
    public static native int av_log_get_level();

    @NoException
    public static native void av_log_set_level(int var0);

    @NoException
    public static native void av_log_set_callback(Callback_Pointer_int_BytePointer_Pointer var0);

    @NoException
    public static native void av_log_set_callback(Callback_Pointer_int_String_Pointer var0);

    @NoException
    public static native void av_log_default_callback(Pointer var0, int var1, @Cast(value={"const char*"}) BytePointer var2, @ByVal @Cast(value={"va_list*"}) Pointer var3);

    @NoException
    public static native void av_log_default_callback(Pointer var0, int var1, String var2, @ByVal @Cast(value={"va_list*"}) Pointer var3);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_default_item_name(Pointer var0);

    @NoException
    @Cast(value={"AVClassCategory"})
    public static native int av_default_get_category(Pointer var0);

    @NoException
    public static native void av_log_format_line(Pointer var0, int var1, @Cast(value={"const char*"}) BytePointer var2, @ByVal @Cast(value={"va_list*"}) Pointer var3, @Cast(value={"char*"}) BytePointer var4, int var5, IntPointer var6);

    @NoException
    public static native void av_log_format_line(Pointer var0, int var1, String var2, @ByVal @Cast(value={"va_list*"}) Pointer var3, @Cast(value={"char*"}) ByteBuffer var4, int var5, IntBuffer var6);

    @NoException
    public static native void av_log_format_line(Pointer var0, int var1, @Cast(value={"const char*"}) BytePointer var2, @ByVal @Cast(value={"va_list*"}) Pointer var3, @Cast(value={"char*"}) byte[] var4, int var5, int[] var6);

    @NoException
    public static native void av_log_format_line(Pointer var0, int var1, String var2, @ByVal @Cast(value={"va_list*"}) Pointer var3, @Cast(value={"char*"}) BytePointer var4, int var5, IntPointer var6);

    @NoException
    public static native void av_log_format_line(Pointer var0, int var1, @Cast(value={"const char*"}) BytePointer var2, @ByVal @Cast(value={"va_list*"}) Pointer var3, @Cast(value={"char*"}) ByteBuffer var4, int var5, IntBuffer var6);

    @NoException
    public static native void av_log_format_line(Pointer var0, int var1, String var2, @ByVal @Cast(value={"va_list*"}) Pointer var3, @Cast(value={"char*"}) byte[] var4, int var5, int[] var6);

    @NoException
    public static native int av_log_format_line2(Pointer var0, int var1, @Cast(value={"const char*"}) BytePointer var2, @ByVal @Cast(value={"va_list*"}) Pointer var3, @Cast(value={"char*"}) BytePointer var4, int var5, IntPointer var6);

    @NoException
    public static native int av_log_format_line2(Pointer var0, int var1, String var2, @ByVal @Cast(value={"va_list*"}) Pointer var3, @Cast(value={"char*"}) ByteBuffer var4, int var5, IntBuffer var6);

    @NoException
    public static native int av_log_format_line2(Pointer var0, int var1, @Cast(value={"const char*"}) BytePointer var2, @ByVal @Cast(value={"va_list*"}) Pointer var3, @Cast(value={"char*"}) byte[] var4, int var5, int[] var6);

    @NoException
    public static native int av_log_format_line2(Pointer var0, int var1, String var2, @ByVal @Cast(value={"va_list*"}) Pointer var3, @Cast(value={"char*"}) BytePointer var4, int var5, IntPointer var6);

    @NoException
    public static native int av_log_format_line2(Pointer var0, int var1, @Cast(value={"const char*"}) BytePointer var2, @ByVal @Cast(value={"va_list*"}) Pointer var3, @Cast(value={"char*"}) ByteBuffer var4, int var5, IntBuffer var6);

    @NoException
    public static native int av_log_format_line2(Pointer var0, int var1, String var2, @ByVal @Cast(value={"va_list*"}) Pointer var3, @Cast(value={"char*"}) byte[] var4, int var5, int[] var6);

    @NoException
    public static native void av_log_set_flags(int var0);

    @NoException
    public static native int av_log_get_flags();

    @NoException
    public static native AVBufferRef av_buffer_alloc(@Cast(value={"size_t"}) long var0);

    @NoException
    public static native AVBufferRef av_buffer_allocz(@Cast(value={"size_t"}) long var0);

    @NoException
    public static native AVBufferRef av_buffer_create(@Cast(value={"uint8_t*"}) BytePointer var0, @Cast(value={"size_t"}) long var1, Free_Pointer_BytePointer var3, Pointer var4, int var5);

    @NoException
    public static native AVBufferRef av_buffer_create(@Cast(value={"uint8_t*"}) ByteBuffer var0, @Cast(value={"size_t"}) long var1, Free_Pointer_ByteBuffer var3, Pointer var4, int var5);

    @NoException
    public static native AVBufferRef av_buffer_create(@Cast(value={"uint8_t*"}) byte[] var0, @Cast(value={"size_t"}) long var1, Free_Pointer_byte__ var3, Pointer var4, int var5);

    @NoException
    public static native void av_buffer_default_free(Pointer var0, @Cast(value={"uint8_t*"}) BytePointer var1);

    @NoException
    public static native void av_buffer_default_free(Pointer var0, @Cast(value={"uint8_t*"}) ByteBuffer var1);

    @NoException
    public static native void av_buffer_default_free(Pointer var0, @Cast(value={"uint8_t*"}) byte[] var1);

    @NoException
    public static native AVBufferRef av_buffer_ref(@Const AVBufferRef var0);

    @NoException
    public static native void av_buffer_unref(@Cast(value={"AVBufferRef**"}) PointerPointer var0);

    @NoException
    public static native void av_buffer_unref(@ByPtrPtr AVBufferRef var0);

    @NoException
    public static native int av_buffer_is_writable(@Const AVBufferRef var0);

    @NoException
    public static native Pointer av_buffer_get_opaque(@Const AVBufferRef var0);

    @NoException
    public static native int av_buffer_get_ref_count(@Const AVBufferRef var0);

    @NoException
    public static native int av_buffer_make_writable(@Cast(value={"AVBufferRef**"}) PointerPointer var0);

    @NoException
    public static native int av_buffer_make_writable(@ByPtrPtr AVBufferRef var0);

    @NoException
    public static native int av_buffer_realloc(@Cast(value={"AVBufferRef**"}) PointerPointer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native int av_buffer_realloc(@ByPtrPtr AVBufferRef var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native int av_buffer_replace(@Cast(value={"AVBufferRef**"}) PointerPointer var0, @Const AVBufferRef var1);

    @NoException
    public static native int av_buffer_replace(@ByPtrPtr AVBufferRef var0, @Const AVBufferRef var1);

    @NoException
    public static native AVBufferPool av_buffer_pool_init(@Cast(value={"size_t"}) long var0, Alloc_long var2);

    @NoException
    public static native AVBufferPool av_buffer_pool_init2(@Cast(value={"size_t"}) long var0, Pointer var2, Alloc_Pointer_long var3, Pool_free_Pointer var4);

    @NoException
    public static native void av_buffer_pool_uninit(@Cast(value={"AVBufferPool**"}) PointerPointer var0);

    @NoException
    public static native void av_buffer_pool_uninit(@ByPtrPtr AVBufferPool var0);

    @NoException
    public static native AVBufferRef av_buffer_pool_get(AVBufferPool var0);

    @NoException
    public static native Pointer av_buffer_pool_buffer_get_opaque(@Const AVBufferRef var0);

    @MemberGetter
    public static native int AV_PIX_FMT_RGB32();

    @MemberGetter
    public static native int AV_PIX_FMT_RGB32_1();

    @MemberGetter
    public static native int AV_PIX_FMT_BGR32();

    @MemberGetter
    public static native int AV_PIX_FMT_BGR32_1();

    @MemberGetter
    public static native int AV_PIX_FMT_0RGB32();

    @MemberGetter
    public static native int AV_PIX_FMT_0BGR32();

    @MemberGetter
    public static native int AV_PIX_FMT_GRAY9();

    @MemberGetter
    public static native int AV_PIX_FMT_GRAY10();

    @MemberGetter
    public static native int AV_PIX_FMT_GRAY12();

    @MemberGetter
    public static native int AV_PIX_FMT_GRAY14();

    @MemberGetter
    public static native int AV_PIX_FMT_GRAY16();

    @MemberGetter
    public static native int AV_PIX_FMT_GRAY32();

    @MemberGetter
    public static native int AV_PIX_FMT_YA16();

    @MemberGetter
    public static native int AV_PIX_FMT_RGB48();

    @MemberGetter
    public static native int AV_PIX_FMT_RGB565();

    @MemberGetter
    public static native int AV_PIX_FMT_RGB555();

    @MemberGetter
    public static native int AV_PIX_FMT_RGB444();

    @MemberGetter
    public static native int AV_PIX_FMT_RGBA64();

    @MemberGetter
    public static native int AV_PIX_FMT_BGR48();

    @MemberGetter
    public static native int AV_PIX_FMT_BGR565();

    @MemberGetter
    public static native int AV_PIX_FMT_BGR555();

    @MemberGetter
    public static native int AV_PIX_FMT_BGR444();

    @MemberGetter
    public static native int AV_PIX_FMT_BGRA64();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV420P9();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV422P9();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV444P9();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV420P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV422P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV440P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV444P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV420P12();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV422P12();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV440P12();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV444P12();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV420P14();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV422P14();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV444P14();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV420P16();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV422P16();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV444P16();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV444P10MSB();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV444P12MSB();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRP9();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRP10();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRP12();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRP14();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRP16();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRAP10();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRAP12();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRAP14();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRAP16();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRAP32();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRP10MSB();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRP12MSB();

    @MemberGetter
    public static native int AV_PIX_FMT_BAYER_BGGR16();

    @MemberGetter
    public static native int AV_PIX_FMT_BAYER_RGGB16();

    @MemberGetter
    public static native int AV_PIX_FMT_BAYER_GBRG16();

    @MemberGetter
    public static native int AV_PIX_FMT_BAYER_GRBG16();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRPF16();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRAPF16();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRPF32();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRAPF32();

    @MemberGetter
    public static native int AV_PIX_FMT_GRAYF16();

    @MemberGetter
    public static native int AV_PIX_FMT_GRAYF32();

    @MemberGetter
    public static native int AV_PIX_FMT_YAF16();

    @MemberGetter
    public static native int AV_PIX_FMT_YAF32();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA420P9();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA422P9();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA444P9();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA420P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA422P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA444P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA422P12();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA444P12();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA420P16();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA422P16();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA444P16();

    @MemberGetter
    public static native int AV_PIX_FMT_XYZ12();

    @MemberGetter
    public static native int AV_PIX_FMT_NV20();

    @MemberGetter
    public static native int AV_PIX_FMT_AYUV64();

    @MemberGetter
    public static native int AV_PIX_FMT_P010();

    @MemberGetter
    public static native int AV_PIX_FMT_P012();

    @MemberGetter
    public static native int AV_PIX_FMT_P016();

    @MemberGetter
    public static native int AV_PIX_FMT_Y210();

    @MemberGetter
    public static native int AV_PIX_FMT_Y212();

    @MemberGetter
    public static native int AV_PIX_FMT_Y216();

    @MemberGetter
    public static native int AV_PIX_FMT_XV30();

    @MemberGetter
    public static native int AV_PIX_FMT_XV36();

    @MemberGetter
    public static native int AV_PIX_FMT_XV48();

    @MemberGetter
    public static native int AV_PIX_FMT_V30X();

    @MemberGetter
    public static native int AV_PIX_FMT_X2RGB10();

    @MemberGetter
    public static native int AV_PIX_FMT_X2BGR10();

    @MemberGetter
    public static native int AV_PIX_FMT_P210();

    @MemberGetter
    public static native int AV_PIX_FMT_P410();

    @MemberGetter
    public static native int AV_PIX_FMT_P212();

    @MemberGetter
    public static native int AV_PIX_FMT_P412();

    @MemberGetter
    public static native int AV_PIX_FMT_P216();

    @MemberGetter
    public static native int AV_PIX_FMT_P416();

    @MemberGetter
    public static native int AV_PIX_FMT_RGBF16();

    @MemberGetter
    public static native int AV_PIX_FMT_RGBAF16();

    @MemberGetter
    public static native int AV_PIX_FMT_RGBF32();

    @MemberGetter
    public static native int AV_PIX_FMT_RGBAF32();

    @MemberGetter
    public static native int AV_PIX_FMT_RGB96();

    @MemberGetter
    public static native int AV_PIX_FMT_RGBA128();

    @NoException
    public static native AVFrame av_frame_alloc();

    @NoException
    public static native void av_frame_free(@Cast(value={"AVFrame**"}) PointerPointer var0);

    @NoException
    public static native void av_frame_free(@ByPtrPtr AVFrame var0);

    @NoException
    public static native int av_frame_ref(AVFrame var0, @Const AVFrame var1);

    @NoException
    public static native int av_frame_replace(AVFrame var0, @Const AVFrame var1);

    @NoException
    public static native AVFrame av_frame_clone(@Const AVFrame var0);

    @NoException
    public static native void av_frame_unref(AVFrame var0);

    @NoException
    public static native void av_frame_move_ref(AVFrame var0, AVFrame var1);

    @NoException
    public static native int av_frame_get_buffer(AVFrame var0, int var1);

    @NoException
    public static native int av_frame_is_writable(AVFrame var0);

    @NoException
    public static native int av_frame_make_writable(AVFrame var0);

    @NoException
    public static native int av_frame_copy(AVFrame var0, @Const AVFrame var1);

    @NoException
    public static native int av_frame_copy_props(AVFrame var0, @Const AVFrame var1);

    @NoException
    public static native AVBufferRef av_frame_get_plane_buffer(@Const AVFrame var0, int var1);

    @NoException
    public static native AVFrameSideData av_frame_new_side_data(AVFrame var0, @Cast(value={"AVFrameSideDataType"}) int var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native AVFrameSideData av_frame_new_side_data_from_buf(AVFrame var0, @Cast(value={"AVFrameSideDataType"}) int var1, AVBufferRef var2);

    @NoException
    public static native AVFrameSideData av_frame_get_side_data(@Const AVFrame var0, @Cast(value={"AVFrameSideDataType"}) int var1);

    @NoException
    public static native void av_frame_remove_side_data(AVFrame var0, @Cast(value={"AVFrameSideDataType"}) int var1);

    @NoException
    public static native int av_frame_apply_cropping(AVFrame var0, int var1);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_frame_side_data_name(@Cast(value={"AVFrameSideDataType"}) int var0);

    @NoException
    @Const
    public static native AVSideDataDescriptor av_frame_side_data_desc(@Cast(value={"AVFrameSideDataType"}) int var0);

    @NoException
    public static native void av_frame_side_data_free(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntPointer var1);

    @NoException
    public static native void av_frame_side_data_free(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntBuffer var1);

    @NoException
    public static native void av_frame_side_data_free(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, int[] var1);

    @NoException
    public static native AVFrameSideData av_frame_side_data_new(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntPointer var1, @Cast(value={"AVFrameSideDataType"}) int var2, @Cast(value={"size_t"}) long var3, @Cast(value={"unsigned int"}) int var5);

    @NoException
    public static native AVFrameSideData av_frame_side_data_new(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntBuffer var1, @Cast(value={"AVFrameSideDataType"}) int var2, @Cast(value={"size_t"}) long var3, @Cast(value={"unsigned int"}) int var5);

    @NoException
    public static native AVFrameSideData av_frame_side_data_new(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, int[] var1, @Cast(value={"AVFrameSideDataType"}) int var2, @Cast(value={"size_t"}) long var3, @Cast(value={"unsigned int"}) int var5);

    @NoException
    public static native AVFrameSideData av_frame_side_data_add(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntPointer var1, @Cast(value={"AVFrameSideDataType"}) int var2, @Cast(value={"AVBufferRef**"}) PointerPointer var3, @Cast(value={"unsigned int"}) int var4);

    @NoException
    public static native AVFrameSideData av_frame_side_data_add(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntPointer var1, @Cast(value={"AVFrameSideDataType"}) int var2, @ByPtrPtr AVBufferRef var3, @Cast(value={"unsigned int"}) int var4);

    @NoException
    public static native AVFrameSideData av_frame_side_data_add(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntBuffer var1, @Cast(value={"AVFrameSideDataType"}) int var2, @ByPtrPtr AVBufferRef var3, @Cast(value={"unsigned int"}) int var4);

    @NoException
    public static native AVFrameSideData av_frame_side_data_add(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, int[] var1, @Cast(value={"AVFrameSideDataType"}) int var2, @ByPtrPtr AVBufferRef var3, @Cast(value={"unsigned int"}) int var4);

    @NoException
    public static native int av_frame_side_data_clone(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntPointer var1, @Const AVFrameSideData var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    public static native int av_frame_side_data_clone(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntBuffer var1, @Const AVFrameSideData var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    public static native int av_frame_side_data_clone(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, int[] var1, @Const AVFrameSideData var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    @Const
    public static native AVFrameSideData av_frame_side_data_get_c(@Cast(value={"const AVFrameSideData*const*"}) PointerPointer var0, int var1, @Cast(value={"AVFrameSideDataType"}) int var2);

    @NoException
    @Const
    public static native AVFrameSideData av_frame_side_data_get_c(@Const @ByPtrPtr AVFrameSideData var0, int var1, @Cast(value={"AVFrameSideDataType"}) int var2);

    @NoException
    @Const
    public static native AVFrameSideData av_frame_side_data_get(@Cast(value={"AVFrameSideData*const*"}) PointerPointer var0, int var1, @Cast(value={"AVFrameSideDataType"}) int var2);

    @NoException
    @Const
    public static native AVFrameSideData av_frame_side_data_get(@ByPtrPtr AVFrameSideData var0, int var1, @Cast(value={"AVFrameSideDataType"}) int var2);

    @NoException
    public static native void av_frame_side_data_remove(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntPointer var1, @Cast(value={"AVFrameSideDataType"}) int var2);

    @NoException
    public static native void av_frame_side_data_remove(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntBuffer var1, @Cast(value={"AVFrameSideDataType"}) int var2);

    @NoException
    public static native void av_frame_side_data_remove(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, int[] var1, @Cast(value={"AVFrameSideDataType"}) int var2);

    @NoException
    public static native void av_frame_side_data_remove_by_props(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntPointer var1, int var2);

    @NoException
    public static native void av_frame_side_data_remove_by_props(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntBuffer var1, int var2);

    @NoException
    public static native void av_frame_side_data_remove_by_props(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, int[] var1, int var2);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_get_sample_fmt_name(@Cast(value={"AVSampleFormat"}) int var0);

    @NoException
    @Cast(value={"AVSampleFormat"})
    public static native int av_get_sample_fmt(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    @Cast(value={"AVSampleFormat"})
    public static native int av_get_sample_fmt(String var0);

    @NoException
    @Cast(value={"AVSampleFormat"})
    public static native int av_get_alt_sample_fmt(@Cast(value={"AVSampleFormat"}) int var0, int var1);

    @NoException
    @Cast(value={"AVSampleFormat"})
    public static native int av_get_packed_sample_fmt(@Cast(value={"AVSampleFormat"}) int var0);

    @NoException
    @Cast(value={"AVSampleFormat"})
    public static native int av_get_planar_sample_fmt(@Cast(value={"AVSampleFormat"}) int var0);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_get_sample_fmt_string(@Cast(value={"char*"}) BytePointer var0, int var1, @Cast(value={"AVSampleFormat"}) int var2);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_get_sample_fmt_string(@Cast(value={"char*"}) ByteBuffer var0, int var1, @Cast(value={"AVSampleFormat"}) int var2);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_get_sample_fmt_string(@Cast(value={"char*"}) byte[] var0, int var1, @Cast(value={"AVSampleFormat"}) int var2);

    @NoException
    public static native int av_get_bytes_per_sample(@Cast(value={"AVSampleFormat"}) int var0);

    @NoException
    public static native int av_sample_fmt_is_planar(@Cast(value={"AVSampleFormat"}) int var0);

    @NoException
    public static native int av_samples_get_buffer_size(IntPointer var0, int var1, int var2, @Cast(value={"AVSampleFormat"}) int var3, int var4);

    @NoException
    public static native int av_samples_get_buffer_size(IntBuffer var0, int var1, int var2, @Cast(value={"AVSampleFormat"}) int var3, int var4);

    @NoException
    public static native int av_samples_get_buffer_size(int[] var0, int var1, int var2, @Cast(value={"AVSampleFormat"}) int var3, int var4);

    @NoException
    public static native int av_samples_fill_arrays(@Cast(value={"uint8_t**"}) PointerPointer var0, IntPointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, int var4, @Cast(value={"AVSampleFormat"}) int var5, int var6);

    @NoException
    public static native int av_samples_fill_arrays(@Cast(value={"uint8_t**"}) @ByPtrPtr BytePointer var0, IntPointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, int var4, @Cast(value={"AVSampleFormat"}) int var5, int var6);

    @NoException
    public static native int av_samples_fill_arrays(@Cast(value={"uint8_t**"}) @ByPtrPtr ByteBuffer var0, IntBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, int var4, @Cast(value={"AVSampleFormat"}) int var5, int var6);

    @NoException
    public static native int av_samples_fill_arrays(@Cast(value={"uint8_t**"}) @ByPtrPtr byte[] var0, int[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, int var4, @Cast(value={"AVSampleFormat"}) int var5, int var6);

    @NoException
    public static native int av_samples_alloc(@Cast(value={"uint8_t**"}) PointerPointer var0, IntPointer var1, int var2, int var3, @Cast(value={"AVSampleFormat"}) int var4, int var5);

    @NoException
    public static native int av_samples_alloc(@Cast(value={"uint8_t**"}) @ByPtrPtr BytePointer var0, IntPointer var1, int var2, int var3, @Cast(value={"AVSampleFormat"}) int var4, int var5);

    @NoException
    public static native int av_samples_alloc(@Cast(value={"uint8_t**"}) @ByPtrPtr ByteBuffer var0, IntBuffer var1, int var2, int var3, @Cast(value={"AVSampleFormat"}) int var4, int var5);

    @NoException
    public static native int av_samples_alloc(@Cast(value={"uint8_t**"}) @ByPtrPtr byte[] var0, int[] var1, int var2, int var3, @Cast(value={"AVSampleFormat"}) int var4, int var5);

    @NoException
    public static native int av_samples_alloc_array_and_samples(@Cast(value={"uint8_t***"}) @ByPtrPtr PointerPointer var0, IntPointer var1, int var2, int var3, @Cast(value={"AVSampleFormat"}) int var4, int var5);

    @NoException
    public static native int av_samples_alloc_array_and_samples(@Cast(value={"uint8_t***"}) @ByPtrPtr PointerPointer var0, IntBuffer var1, int var2, int var3, @Cast(value={"AVSampleFormat"}) int var4, int var5);

    @NoException
    public static native int av_samples_alloc_array_and_samples(@Cast(value={"uint8_t***"}) @ByPtrPtr PointerPointer var0, int[] var1, int var2, int var3, @Cast(value={"AVSampleFormat"}) int var4, int var5);

    @NoException
    public static native int av_samples_copy(@Cast(value={"uint8_t*const*"}) PointerPointer var0, @Cast(value={"uint8_t*const*"}) PointerPointer var1, int var2, int var3, int var4, int var5, @Cast(value={"AVSampleFormat"}) int var6);

    @NoException
    public static native int av_samples_copy(@Cast(value={"uint8_t*const*"}) @ByPtrPtr BytePointer var0, @Cast(value={"uint8_t*const*"}) @ByPtrPtr BytePointer var1, int var2, int var3, int var4, int var5, @Cast(value={"AVSampleFormat"}) int var6);

    @NoException
    public static native int av_samples_copy(@Cast(value={"uint8_t*const*"}) @ByPtrPtr ByteBuffer var0, @Cast(value={"uint8_t*const*"}) @ByPtrPtr ByteBuffer var1, int var2, int var3, int var4, int var5, @Cast(value={"AVSampleFormat"}) int var6);

    @NoException
    public static native int av_samples_copy(@Cast(value={"uint8_t*const*"}) @ByPtrPtr byte[] var0, @Cast(value={"uint8_t*const*"}) @ByPtrPtr byte[] var1, int var2, int var3, int var4, int var5, @Cast(value={"AVSampleFormat"}) int var6);

    @NoException
    public static native int av_samples_set_silence(@Cast(value={"uint8_t*const*"}) PointerPointer var0, int var1, int var2, int var3, @Cast(value={"AVSampleFormat"}) int var4);

    @NoException
    public static native int av_samples_set_silence(@Cast(value={"uint8_t*const*"}) @ByPtrPtr BytePointer var0, int var1, int var2, int var3, @Cast(value={"AVSampleFormat"}) int var4);

    @NoException
    public static native int av_samples_set_silence(@Cast(value={"uint8_t*const*"}) @ByPtrPtr ByteBuffer var0, int var1, int var2, int var3, @Cast(value={"AVSampleFormat"}) int var4);

    @NoException
    public static native int av_samples_set_silence(@Cast(value={"uint8_t*const*"}) @ByPtrPtr byte[] var0, int var1, int var2, int var3, @Cast(value={"AVSampleFormat"}) int var4);

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_MONO();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_STEREO();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_2POINT1();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_2_1();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_SURROUND();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_3POINT1();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_4POINT0();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_4POINT1();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_2_2();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_QUAD();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_5POINT0();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_5POINT1();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_5POINT0_BACK();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_5POINT1_BACK();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_6POINT0();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_6POINT0_FRONT();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_3POINT1POINT2();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_HEXAGONAL();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_6POINT1();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_6POINT1_BACK();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_6POINT1_FRONT();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_7POINT0();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_7POINT0_FRONT();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_7POINT1();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_7POINT1_WIDE();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_7POINT1_WIDE_BACK();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_5POINT1POINT2();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_5POINT1POINT2_BACK();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_OCTAGONAL();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_CUBE();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_5POINT1POINT4_BACK();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_7POINT1POINT2();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_7POINT1POINT4_BACK();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_7POINT2POINT3();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_9POINT1POINT4_BACK();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_9POINT1POINT6();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_HEXADECAGONAL();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_BINAURAL();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_STEREO_DOWNMIX();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_22POINT2();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_7POINT1_TOP_BACK();

    @MemberGetter
    @ByVal
    public static native AVChannelLayout AV_CHANNEL_LAYOUT_AMBISONIC_FIRST_ORDER();

    @NoException
    public static native int av_channel_name(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"size_t"}) long var1, @Cast(value={"AVChannel"}) int var3);

    @NoException
    public static native int av_channel_name(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"size_t"}) long var1, @Cast(value={"AVChannel"}) int var3);

    @NoException
    public static native int av_channel_name(@Cast(value={"char*"}) byte[] var0, @Cast(value={"size_t"}) long var1, @Cast(value={"AVChannel"}) int var3);

    @NoException
    public static native void av_channel_name_bprint(AVBPrint var0, @Cast(value={"AVChannel"}) int var1);

    @NoException
    public static native int av_channel_description(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"size_t"}) long var1, @Cast(value={"AVChannel"}) int var3);

    @NoException
    public static native int av_channel_description(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"size_t"}) long var1, @Cast(value={"AVChannel"}) int var3);

    @NoException
    public static native int av_channel_description(@Cast(value={"char*"}) byte[] var0, @Cast(value={"size_t"}) long var1, @Cast(value={"AVChannel"}) int var3);

    @NoException
    public static native void av_channel_description_bprint(AVBPrint var0, @Cast(value={"AVChannel"}) int var1);

    @NoException
    @Cast(value={"AVChannel"})
    public static native int av_channel_from_string(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    @Cast(value={"AVChannel"})
    public static native int av_channel_from_string(String var0);

    @NoException
    public static native int av_channel_layout_custom_init(AVChannelLayout var0, int var1);

    @NoException
    public static native int av_channel_layout_from_mask(AVChannelLayout var0, @Cast(value={"uint64_t"}) long var1);

    @NoException
    public static native int av_channel_layout_from_string(AVChannelLayout var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    public static native int av_channel_layout_from_string(AVChannelLayout var0, String var1);

    @NoException
    public static native void av_channel_layout_default(AVChannelLayout var0, int var1);

    @NoException
    @Const
    public static native AVChannelLayout av_channel_layout_standard(@Cast(value={"void**"}) PointerPointer var0);

    @NoException
    @Const
    public static native AVChannelLayout av_channel_layout_standard(@Cast(value={"void**"}) @ByPtrPtr Pointer var0);

    @NoException
    public static native void av_channel_layout_uninit(AVChannelLayout var0);

    @NoException
    public static native int av_channel_layout_copy(AVChannelLayout var0, @Const AVChannelLayout var1);

    @NoException
    public static native int av_channel_layout_describe(@Const AVChannelLayout var0, @Cast(value={"char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int av_channel_layout_describe(@Const AVChannelLayout var0, @Cast(value={"char*"}) ByteBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int av_channel_layout_describe(@Const AVChannelLayout var0, @Cast(value={"char*"}) byte[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int av_channel_layout_describe_bprint(@Const AVChannelLayout var0, AVBPrint var1);

    @NoException
    @Cast(value={"AVChannel"})
    public static native int av_channel_layout_channel_from_index(@Const AVChannelLayout var0, @Cast(value={"unsigned int"}) int var1);

    @NoException
    public static native int av_channel_layout_index_from_channel(@Const AVChannelLayout var0, @Cast(value={"AVChannel"}) int var1);

    @NoException
    public static native int av_channel_layout_index_from_string(@Const AVChannelLayout var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    public static native int av_channel_layout_index_from_string(@Const AVChannelLayout var0, String var1);

    @NoException
    @Cast(value={"AVChannel"})
    public static native int av_channel_layout_channel_from_string(@Const AVChannelLayout var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    @Cast(value={"AVChannel"})
    public static native int av_channel_layout_channel_from_string(@Const AVChannelLayout var0, String var1);

    @NoException
    @Cast(value={"uint64_t"})
    public static native long av_channel_layout_subset(@Const AVChannelLayout var0, @Cast(value={"uint64_t"}) long var1);

    @NoException
    public static native int av_channel_layout_check(@Const AVChannelLayout var0);

    @NoException
    public static native int av_channel_layout_compare(@Const AVChannelLayout var0, @Const AVChannelLayout var1);

    @NoException
    public static native int av_channel_layout_ambisonic_order(@Const AVChannelLayout var0);

    @NoException
    public static native int av_channel_layout_retype(AVChannelLayout var0, @Cast(value={"AVChannelOrder"}) int var1, int var2);

    @NoException
    public static native int av_get_cpu_flags();

    @NoException
    public static native void av_force_cpu_flags(int var0);

    @NoException
    public static native int av_parse_cpu_caps(@Cast(value={"unsigned*"}) IntPointer var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    public static native int av_parse_cpu_caps(@Cast(value={"unsigned*"}) IntBuffer var0, String var1);

    @NoException
    public static native int av_parse_cpu_caps(@Cast(value={"unsigned*"}) int[] var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    public static native int av_parse_cpu_caps(@Cast(value={"unsigned*"}) IntPointer var0, String var1);

    @NoException
    public static native int av_parse_cpu_caps(@Cast(value={"unsigned*"}) IntBuffer var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    public static native int av_parse_cpu_caps(@Cast(value={"unsigned*"}) int[] var0, String var1);

    @NoException
    public static native int av_cpu_count();

    @NoException
    public static native void av_cpu_force_count(int var0);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_cpu_max_align();

    @NoException
    public static native AVDictionaryEntry av_dict_get(@Const AVDictionary var0, @Cast(value={"const char*"}) BytePointer var1, @Const AVDictionaryEntry var2, int var3);

    @NoException
    public static native AVDictionaryEntry av_dict_get(@Const AVDictionary var0, String var1, @Const AVDictionaryEntry var2, int var3);

    @NoException
    @Const
    public static native AVDictionaryEntry av_dict_iterate(@Const AVDictionary var0, @Const AVDictionaryEntry var1);

    @NoException
    public static native int av_dict_count(@Const AVDictionary var0);

    @NoException
    public static native int av_dict_set(@Cast(value={"AVDictionary**"}) PointerPointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, int var3);

    @NoException
    public static native int av_dict_set(@ByPtrPtr AVDictionary var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, int var3);

    @NoException
    public static native int av_dict_set(@ByPtrPtr AVDictionary var0, String var1, String var2, int var3);

    @NoException
    public static native int av_dict_set_int(@Cast(value={"AVDictionary**"}) PointerPointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"int64_t"}) long var2, int var4);

    @NoException
    public static native int av_dict_set_int(@ByPtrPtr AVDictionary var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"int64_t"}) long var2, int var4);

    @NoException
    public static native int av_dict_set_int(@ByPtrPtr AVDictionary var0, String var1, @Cast(value={"int64_t"}) long var2, int var4);

    @NoException
    public static native int av_dict_parse_string(@Cast(value={"AVDictionary**"}) PointerPointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"const char*"}) BytePointer var3, int var4);

    @NoException
    public static native int av_dict_parse_string(@ByPtrPtr AVDictionary var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"const char*"}) BytePointer var3, int var4);

    @NoException
    public static native int av_dict_parse_string(@ByPtrPtr AVDictionary var0, String var1, String var2, String var3, int var4);

    @NoException
    public static native int av_dict_copy(@Cast(value={"AVDictionary**"}) PointerPointer var0, @Const AVDictionary var1, int var2);

    @NoException
    public static native int av_dict_copy(@ByPtrPtr AVDictionary var0, @Const AVDictionary var1, int var2);

    @NoException
    public static native void av_dict_free(@Cast(value={"AVDictionary**"}) PointerPointer var0);

    @NoException
    public static native void av_dict_free(@ByPtrPtr AVDictionary var0);

    @NoException
    public static native int av_dict_get_string(@Const AVDictionary var0, @Cast(value={"char**"}) PointerPointer var1, @Cast(value={"const char"}) byte var2, @Cast(value={"const char"}) byte var3);

    @NoException
    public static native int av_dict_get_string(@Const AVDictionary var0, @Cast(value={"char**"}) @ByPtrPtr BytePointer var1, @Cast(value={"const char"}) byte var2, @Cast(value={"const char"}) byte var3);

    @NoException
    public static native int av_dict_get_string(@Const AVDictionary var0, @Cast(value={"char**"}) @ByPtrPtr ByteBuffer var1, @Cast(value={"const char"}) byte var2, @Cast(value={"const char"}) byte var3);

    @NoException
    public static native int av_dict_get_string(@Const AVDictionary var0, @Cast(value={"char**"}) @ByPtrPtr byte[] var1, @Cast(value={"const char"}) byte var2, @Cast(value={"const char"}) byte var3);

    @NoException
    public static native void av_opt_set_defaults(Pointer var0);

    @NoException
    public static native void av_opt_set_defaults2(Pointer var0, int var1, int var2);

    @NoException
    public static native void av_opt_free(Pointer var0);

    @NoException
    @Const
    public static native AVOption av_opt_next(@Const Pointer var0, @Const AVOption var1);

    @NoException
    public static native Pointer av_opt_child_next(Pointer var0, Pointer var1);

    @NoException
    @Const
    public static native AVClass av_opt_child_class_iterate(@Const AVClass var0, @Cast(value={"void**"}) PointerPointer var1);

    @NoException
    @Const
    public static native AVClass av_opt_child_class_iterate(@Const AVClass var0, @Cast(value={"void**"}) @ByPtrPtr Pointer var1);

    @NoException
    @Const
    public static native AVOption av_opt_find(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, int var3, int var4);

    @NoException
    @Const
    public static native AVOption av_opt_find(Pointer var0, String var1, String var2, int var3, int var4);

    @NoException
    @Const
    public static native AVOption av_opt_find2(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, int var3, int var4, @Cast(value={"void**"}) PointerPointer var5);

    @NoException
    @Const
    public static native AVOption av_opt_find2(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, int var3, int var4, @Cast(value={"void**"}) @ByPtrPtr Pointer var5);

    @NoException
    @Const
    public static native AVOption av_opt_find2(Pointer var0, String var1, String var2, int var3, int var4, @Cast(value={"void**"}) @ByPtrPtr Pointer var5);

    @NoException
    public static native int av_opt_show2(Pointer var0, Pointer var1, int var2, int var3);

    @NoException
    public static native int av_opt_get_key_value(@Cast(value={"const char**"}) PointerPointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"unsigned"}) int var3, @Cast(value={"char**"}) PointerPointer var4, @Cast(value={"char**"}) PointerPointer var5);

    @NoException
    public static native int av_opt_get_key_value(@Cast(value={"const char**"}) @ByPtrPtr BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"unsigned"}) int var3, @Cast(value={"char**"}) @ByPtrPtr BytePointer var4, @Cast(value={"char**"}) @ByPtrPtr BytePointer var5);

    @NoException
    public static native int av_opt_get_key_value(@Cast(value={"const char**"}) @ByPtrPtr ByteBuffer var0, String var1, String var2, @Cast(value={"unsigned"}) int var3, @Cast(value={"char**"}) @ByPtrPtr ByteBuffer var4, @Cast(value={"char**"}) @ByPtrPtr ByteBuffer var5);

    @NoException
    public static native int av_opt_get_key_value(@Cast(value={"const char**"}) @ByPtrPtr byte[] var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"unsigned"}) int var3, @Cast(value={"char**"}) @ByPtrPtr byte[] var4, @Cast(value={"char**"}) @ByPtrPtr byte[] var5);

    @NoException
    public static native int av_opt_get_key_value(@Cast(value={"const char**"}) @ByPtrPtr BytePointer var0, String var1, String var2, @Cast(value={"unsigned"}) int var3, @Cast(value={"char**"}) @ByPtrPtr BytePointer var4, @Cast(value={"char**"}) @ByPtrPtr BytePointer var5);

    @NoException
    public static native int av_opt_get_key_value(@Cast(value={"const char**"}) @ByPtrPtr ByteBuffer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"unsigned"}) int var3, @Cast(value={"char**"}) @ByPtrPtr ByteBuffer var4, @Cast(value={"char**"}) @ByPtrPtr ByteBuffer var5);

    @NoException
    public static native int av_opt_get_key_value(@Cast(value={"const char**"}) @ByPtrPtr byte[] var0, String var1, String var2, @Cast(value={"unsigned"}) int var3, @Cast(value={"char**"}) @ByPtrPtr byte[] var4, @Cast(value={"char**"}) @ByPtrPtr byte[] var5);

    @NoException
    public static native int av_set_options_string(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"const char*"}) BytePointer var3);

    @NoException
    public static native int av_set_options_string(Pointer var0, String var1, String var2, String var3);

    @NoException
    public static native int av_opt_set_from_string(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*const*"}) PointerPointer var2, @Cast(value={"const char*"}) BytePointer var3, @Cast(value={"const char*"}) BytePointer var4);

    @NoException
    public static native int av_opt_set_from_string(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var2, @Cast(value={"const char*"}) BytePointer var3, @Cast(value={"const char*"}) BytePointer var4);

    @NoException
    public static native int av_opt_set_from_string(Pointer var0, String var1, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var2, String var3, String var4);

    @NoException
    public static native int av_opt_set_from_string(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var2, @Cast(value={"const char*"}) BytePointer var3, @Cast(value={"const char*"}) BytePointer var4);

    @NoException
    public static native int av_opt_set_from_string(Pointer var0, String var1, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var2, String var3, String var4);

    @NoException
    public static native int av_opt_set_from_string(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var2, @Cast(value={"const char*"}) BytePointer var3, @Cast(value={"const char*"}) BytePointer var4);

    @NoException
    public static native int av_opt_set_from_string(Pointer var0, String var1, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var2, String var3, String var4);

    @NoException
    public static native int av_opt_set_dict(Pointer var0, @Cast(value={"AVDictionary**"}) PointerPointer var1);

    @NoException
    public static native int av_opt_set_dict(Pointer var0, @ByPtrPtr AVDictionary var1);

    @NoException
    public static native int av_opt_set_dict2(Pointer var0, @Cast(value={"AVDictionary**"}) PointerPointer var1, int var2);

    @NoException
    public static native int av_opt_set_dict2(Pointer var0, @ByPtrPtr AVDictionary var1, int var2);

    @NoException
    public static native int av_opt_copy(Pointer var0, @Const Pointer var1);

    @NoException
    public static native int av_opt_set(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, int var3);

    @NoException
    public static native int av_opt_set(Pointer var0, String var1, String var2, int var3);

    @NoException
    public static native int av_opt_set_int(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"int64_t"}) long var2, int var4);

    @NoException
    public static native int av_opt_set_int(Pointer var0, String var1, @Cast(value={"int64_t"}) long var2, int var4);

    @NoException
    public static native int av_opt_set_double(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, double var2, int var4);

    @NoException
    public static native int av_opt_set_double(Pointer var0, String var1, double var2, int var4);

    @NoException
    public static native int av_opt_set_q(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @ByVal AVRational var2, int var3);

    @NoException
    public static native int av_opt_set_q(Pointer var0, String var1, @ByVal AVRational var2, int var3);

    @NoException
    public static native int av_opt_set_bin(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, int var4);

    @NoException
    public static native int av_opt_set_bin(Pointer var0, String var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, int var4);

    @NoException
    public static native int av_opt_set_bin(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, int var4);

    @NoException
    public static native int av_opt_set_bin(Pointer var0, String var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, int var4);

    @NoException
    public static native int av_opt_set_bin(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, int var4);

    @NoException
    public static native int av_opt_set_bin(Pointer var0, String var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, int var4);

    @NoException
    public static native int av_opt_set_image_size(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, int var3, int var4);

    @NoException
    public static native int av_opt_set_image_size(Pointer var0, String var1, int var2, int var3, int var4);

    @NoException
    public static native int av_opt_set_pixel_fmt(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"AVPixelFormat"}) int var2, int var3);

    @NoException
    public static native int av_opt_set_pixel_fmt(Pointer var0, String var1, @Cast(value={"AVPixelFormat"}) int var2, int var3);

    @NoException
    public static native int av_opt_set_sample_fmt(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"AVSampleFormat"}) int var2, int var3);

    @NoException
    public static native int av_opt_set_sample_fmt(Pointer var0, String var1, @Cast(value={"AVSampleFormat"}) int var2, int var3);

    @NoException
    public static native int av_opt_set_video_rate(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @ByVal AVRational var2, int var3);

    @NoException
    public static native int av_opt_set_video_rate(Pointer var0, String var1, @ByVal AVRational var2, int var3);

    @NoException
    public static native int av_opt_set_chlayout(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Const AVChannelLayout var2, int var3);

    @NoException
    public static native int av_opt_set_chlayout(Pointer var0, String var1, @Const AVChannelLayout var2, int var3);

    @NoException
    public static native int av_opt_set_dict_val(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Const AVDictionary var2, int var3);

    @NoException
    public static native int av_opt_set_dict_val(Pointer var0, String var1, @Const AVDictionary var2, int var3);

    @NoException
    public static native int av_opt_set_array(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"unsigned int"}) int var3, @Cast(value={"unsigned int"}) int var4, @Cast(value={"AVOptionType"}) int var5, @Const Pointer var6);

    @NoException
    public static native int av_opt_set_array(Pointer var0, String var1, int var2, @Cast(value={"unsigned int"}) int var3, @Cast(value={"unsigned int"}) int var4, @Cast(value={"AVOptionType"}) int var5, @Const Pointer var6);

    @NoException
    public static native int av_opt_get(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"uint8_t**"}) PointerPointer var3);

    @NoException
    public static native int av_opt_get(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"uint8_t**"}) @ByPtrPtr BytePointer var3);

    @NoException
    public static native int av_opt_get(Pointer var0, String var1, int var2, @Cast(value={"uint8_t**"}) @ByPtrPtr ByteBuffer var3);

    @NoException
    public static native int av_opt_get(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"uint8_t**"}) @ByPtrPtr byte[] var3);

    @NoException
    public static native int av_opt_get(Pointer var0, String var1, int var2, @Cast(value={"uint8_t**"}) @ByPtrPtr BytePointer var3);

    @NoException
    public static native int av_opt_get(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"uint8_t**"}) @ByPtrPtr ByteBuffer var3);

    @NoException
    public static native int av_opt_get(Pointer var0, String var1, int var2, @Cast(value={"uint8_t**"}) @ByPtrPtr byte[] var3);

    @NoException
    public static native int av_opt_get_int(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"int64_t*"}) LongPointer var3);

    @NoException
    public static native int av_opt_get_int(Pointer var0, String var1, int var2, @Cast(value={"int64_t*"}) LongBuffer var3);

    @NoException
    public static native int av_opt_get_int(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"int64_t*"}) long[] var3);

    @NoException
    public static native int av_opt_get_int(Pointer var0, String var1, int var2, @Cast(value={"int64_t*"}) LongPointer var3);

    @NoException
    public static native int av_opt_get_int(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"int64_t*"}) LongBuffer var3);

    @NoException
    public static native int av_opt_get_int(Pointer var0, String var1, int var2, @Cast(value={"int64_t*"}) long[] var3);

    @NoException
    public static native int av_opt_get_double(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, DoublePointer var3);

    @NoException
    public static native int av_opt_get_double(Pointer var0, String var1, int var2, DoubleBuffer var3);

    @NoException
    public static native int av_opt_get_double(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, double[] var3);

    @NoException
    public static native int av_opt_get_double(Pointer var0, String var1, int var2, DoublePointer var3);

    @NoException
    public static native int av_opt_get_double(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, DoubleBuffer var3);

    @NoException
    public static native int av_opt_get_double(Pointer var0, String var1, int var2, double[] var3);

    @NoException
    public static native int av_opt_get_q(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, AVRational var3);

    @NoException
    public static native int av_opt_get_q(Pointer var0, String var1, int var2, AVRational var3);

    @NoException
    public static native int av_opt_get_image_size(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, IntPointer var3, IntPointer var4);

    @NoException
    public static native int av_opt_get_image_size(Pointer var0, String var1, int var2, IntBuffer var3, IntBuffer var4);

    @NoException
    public static native int av_opt_get_image_size(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, int[] var3, int[] var4);

    @NoException
    public static native int av_opt_get_image_size(Pointer var0, String var1, int var2, IntPointer var3, IntPointer var4);

    @NoException
    public static native int av_opt_get_image_size(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, IntBuffer var3, IntBuffer var4);

    @NoException
    public static native int av_opt_get_image_size(Pointer var0, String var1, int var2, int[] var3, int[] var4);

    @NoException
    public static native int av_opt_get_pixel_fmt(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"AVPixelFormat*"}) IntPointer var3);

    @NoException
    public static native int av_opt_get_pixel_fmt(Pointer var0, String var1, int var2, @Cast(value={"AVPixelFormat*"}) IntBuffer var3);

    @NoException
    public static native int av_opt_get_pixel_fmt(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"AVPixelFormat*"}) int[] var3);

    @NoException
    public static native int av_opt_get_pixel_fmt(Pointer var0, String var1, int var2, @Cast(value={"AVPixelFormat*"}) IntPointer var3);

    @NoException
    public static native int av_opt_get_pixel_fmt(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"AVPixelFormat*"}) IntBuffer var3);

    @NoException
    public static native int av_opt_get_pixel_fmt(Pointer var0, String var1, int var2, @Cast(value={"AVPixelFormat*"}) int[] var3);

    @NoException
    public static native int av_opt_get_sample_fmt(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"AVSampleFormat*"}) IntPointer var3);

    @NoException
    public static native int av_opt_get_sample_fmt(Pointer var0, String var1, int var2, @Cast(value={"AVSampleFormat*"}) IntBuffer var3);

    @NoException
    public static native int av_opt_get_sample_fmt(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"AVSampleFormat*"}) int[] var3);

    @NoException
    public static native int av_opt_get_sample_fmt(Pointer var0, String var1, int var2, @Cast(value={"AVSampleFormat*"}) IntPointer var3);

    @NoException
    public static native int av_opt_get_sample_fmt(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"AVSampleFormat*"}) IntBuffer var3);

    @NoException
    public static native int av_opt_get_sample_fmt(Pointer var0, String var1, int var2, @Cast(value={"AVSampleFormat*"}) int[] var3);

    @NoException
    public static native int av_opt_get_video_rate(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, AVRational var3);

    @NoException
    public static native int av_opt_get_video_rate(Pointer var0, String var1, int var2, AVRational var3);

    @NoException
    public static native int av_opt_get_chlayout(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, AVChannelLayout var3);

    @NoException
    public static native int av_opt_get_chlayout(Pointer var0, String var1, int var2, AVChannelLayout var3);

    @NoException
    public static native int av_opt_get_dict_val(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"AVDictionary**"}) PointerPointer var3);

    @NoException
    public static native int av_opt_get_dict_val(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @ByPtrPtr AVDictionary var3);

    @NoException
    public static native int av_opt_get_dict_val(Pointer var0, String var1, int var2, @ByPtrPtr AVDictionary var3);

    @NoException
    public static native int av_opt_get_array_size(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"unsigned int*"}) IntPointer var3);

    @NoException
    public static native int av_opt_get_array_size(Pointer var0, String var1, int var2, @Cast(value={"unsigned int*"}) IntBuffer var3);

    @NoException
    public static native int av_opt_get_array_size(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"unsigned int*"}) int[] var3);

    @NoException
    public static native int av_opt_get_array_size(Pointer var0, String var1, int var2, @Cast(value={"unsigned int*"}) IntPointer var3);

    @NoException
    public static native int av_opt_get_array_size(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"unsigned int*"}) IntBuffer var3);

    @NoException
    public static native int av_opt_get_array_size(Pointer var0, String var1, int var2, @Cast(value={"unsigned int*"}) int[] var3);

    @NoException
    public static native int av_opt_get_array(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, @Cast(value={"unsigned int"}) int var3, @Cast(value={"unsigned int"}) int var4, @Cast(value={"AVOptionType"}) int var5, Pointer var6);

    @NoException
    public static native int av_opt_get_array(Pointer var0, String var1, int var2, @Cast(value={"unsigned int"}) int var3, @Cast(value={"unsigned int"}) int var4, @Cast(value={"AVOptionType"}) int var5, Pointer var6);

    @NoException
    public static native int av_opt_eval_flags(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, IntPointer var3);

    @NoException
    public static native int av_opt_eval_flags(Pointer var0, @Const AVOption var1, String var2, IntBuffer var3);

    @NoException
    public static native int av_opt_eval_flags(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, int[] var3);

    @NoException
    public static native int av_opt_eval_flags(Pointer var0, @Const AVOption var1, String var2, IntPointer var3);

    @NoException
    public static native int av_opt_eval_flags(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, IntBuffer var3);

    @NoException
    public static native int av_opt_eval_flags(Pointer var0, @Const AVOption var1, String var2, int[] var3);

    @NoException
    public static native int av_opt_eval_int(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, IntPointer var3);

    @NoException
    public static native int av_opt_eval_int(Pointer var0, @Const AVOption var1, String var2, IntBuffer var3);

    @NoException
    public static native int av_opt_eval_int(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, int[] var3);

    @NoException
    public static native int av_opt_eval_int(Pointer var0, @Const AVOption var1, String var2, IntPointer var3);

    @NoException
    public static native int av_opt_eval_int(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, IntBuffer var3);

    @NoException
    public static native int av_opt_eval_int(Pointer var0, @Const AVOption var1, String var2, int[] var3);

    @NoException
    public static native int av_opt_eval_uint(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"unsigned*"}) IntPointer var3);

    @NoException
    public static native int av_opt_eval_uint(Pointer var0, @Const AVOption var1, String var2, @Cast(value={"unsigned*"}) IntBuffer var3);

    @NoException
    public static native int av_opt_eval_uint(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"unsigned*"}) int[] var3);

    @NoException
    public static native int av_opt_eval_uint(Pointer var0, @Const AVOption var1, String var2, @Cast(value={"unsigned*"}) IntPointer var3);

    @NoException
    public static native int av_opt_eval_uint(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"unsigned*"}) IntBuffer var3);

    @NoException
    public static native int av_opt_eval_uint(Pointer var0, @Const AVOption var1, String var2, @Cast(value={"unsigned*"}) int[] var3);

    @NoException
    public static native int av_opt_eval_int64(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"int64_t*"}) LongPointer var3);

    @NoException
    public static native int av_opt_eval_int64(Pointer var0, @Const AVOption var1, String var2, @Cast(value={"int64_t*"}) LongBuffer var3);

    @NoException
    public static native int av_opt_eval_int64(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"int64_t*"}) long[] var3);

    @NoException
    public static native int av_opt_eval_int64(Pointer var0, @Const AVOption var1, String var2, @Cast(value={"int64_t*"}) LongPointer var3);

    @NoException
    public static native int av_opt_eval_int64(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"int64_t*"}) LongBuffer var3);

    @NoException
    public static native int av_opt_eval_int64(Pointer var0, @Const AVOption var1, String var2, @Cast(value={"int64_t*"}) long[] var3);

    @NoException
    public static native int av_opt_eval_float(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, FloatPointer var3);

    @NoException
    public static native int av_opt_eval_float(Pointer var0, @Const AVOption var1, String var2, FloatBuffer var3);

    @NoException
    public static native int av_opt_eval_float(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, float[] var3);

    @NoException
    public static native int av_opt_eval_float(Pointer var0, @Const AVOption var1, String var2, FloatPointer var3);

    @NoException
    public static native int av_opt_eval_float(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, FloatBuffer var3);

    @NoException
    public static native int av_opt_eval_float(Pointer var0, @Const AVOption var1, String var2, float[] var3);

    @NoException
    public static native int av_opt_eval_double(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, DoublePointer var3);

    @NoException
    public static native int av_opt_eval_double(Pointer var0, @Const AVOption var1, String var2, DoubleBuffer var3);

    @NoException
    public static native int av_opt_eval_double(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, double[] var3);

    @NoException
    public static native int av_opt_eval_double(Pointer var0, @Const AVOption var1, String var2, DoublePointer var3);

    @NoException
    public static native int av_opt_eval_double(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, DoubleBuffer var3);

    @NoException
    public static native int av_opt_eval_double(Pointer var0, @Const AVOption var1, String var2, double[] var3);

    @NoException
    public static native int av_opt_eval_q(Pointer var0, @Const AVOption var1, @Cast(value={"const char*"}) BytePointer var2, AVRational var3);

    @NoException
    public static native int av_opt_eval_q(Pointer var0, @Const AVOption var1, String var2, AVRational var3);

    @NoException
    @Deprecated
    public static native Pointer av_opt_ptr(@Const AVClass var0, Pointer var1, @Cast(value={"const char*"}) BytePointer var2);

    @NoException
    @Deprecated
    public static native Pointer av_opt_ptr(@Const AVClass var0, Pointer var1, String var2);

    @NoException
    public static native int av_opt_is_set_to_default(Pointer var0, @Const AVOption var1);

    @NoException
    public static native int av_opt_is_set_to_default_by_name(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2);

    @NoException
    public static native int av_opt_is_set_to_default_by_name(Pointer var0, String var1, int var2);

    @NoException
    public static native int av_opt_flag_is_set(Pointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2);

    @NoException
    public static native int av_opt_flag_is_set(Pointer var0, String var1, String var2);

    @NoException
    public static native int av_opt_serialize(Pointer var0, int var1, int var2, @Cast(value={"char**"}) PointerPointer var3, @Cast(value={"const char"}) byte var4, @Cast(value={"const char"}) byte var5);

    @NoException
    public static native int av_opt_serialize(Pointer var0, int var1, int var2, @Cast(value={"char**"}) @ByPtrPtr BytePointer var3, @Cast(value={"const char"}) byte var4, @Cast(value={"const char"}) byte var5);

    @NoException
    public static native int av_opt_serialize(Pointer var0, int var1, int var2, @Cast(value={"char**"}) @ByPtrPtr ByteBuffer var3, @Cast(value={"const char"}) byte var4, @Cast(value={"const char"}) byte var5);

    @NoException
    public static native int av_opt_serialize(Pointer var0, int var1, int var2, @Cast(value={"char**"}) @ByPtrPtr byte[] var3, @Cast(value={"const char"}) byte var4, @Cast(value={"const char"}) byte var5);

    @NoException
    public static native void av_opt_freep_ranges(@Cast(value={"AVOptionRanges**"}) PointerPointer var0);

    @NoException
    public static native void av_opt_freep_ranges(@ByPtrPtr AVOptionRanges var0);

    @NoException
    public static native int av_opt_query_ranges(@Cast(value={"AVOptionRanges**"}) PointerPointer var0, Pointer var1, @Cast(value={"const char*"}) BytePointer var2, int var3);

    @NoException
    public static native int av_opt_query_ranges(@ByPtrPtr AVOptionRanges var0, Pointer var1, @Cast(value={"const char*"}) BytePointer var2, int var3);

    @NoException
    public static native int av_opt_query_ranges(@ByPtrPtr AVOptionRanges var0, Pointer var1, String var2, int var3);

    @NoException
    public static native int av_opt_query_ranges_default(@Cast(value={"AVOptionRanges**"}) PointerPointer var0, Pointer var1, @Cast(value={"const char*"}) BytePointer var2, int var3);

    @NoException
    public static native int av_opt_query_ranges_default(@ByPtrPtr AVOptionRanges var0, Pointer var1, @Cast(value={"const char*"}) BytePointer var2, int var3);

    @NoException
    public static native int av_opt_query_ranges_default(@ByPtrPtr AVOptionRanges var0, Pointer var1, String var2, int var3);

    @NoException
    public static native int av_get_bits_per_pixel(@Const AVPixFmtDescriptor var0);

    @NoException
    public static native int av_get_padded_bits_per_pixel(@Const AVPixFmtDescriptor var0);

    @NoException
    @Const
    public static native AVPixFmtDescriptor av_pix_fmt_desc_get(@Cast(value={"AVPixelFormat"}) int var0);

    @NoException
    @Const
    public static native AVPixFmtDescriptor av_pix_fmt_desc_next(@Const AVPixFmtDescriptor var0);

    @NoException
    @Cast(value={"AVPixelFormat"})
    public static native int av_pix_fmt_desc_get_id(@Const AVPixFmtDescriptor var0);

    @NoException
    public static native int av_pix_fmt_get_chroma_sub_sample(@Cast(value={"AVPixelFormat"}) int var0, IntPointer var1, IntPointer var2);

    @NoException
    public static native int av_pix_fmt_get_chroma_sub_sample(@Cast(value={"AVPixelFormat"}) int var0, IntBuffer var1, IntBuffer var2);

    @NoException
    public static native int av_pix_fmt_get_chroma_sub_sample(@Cast(value={"AVPixelFormat"}) int var0, int[] var1, int[] var2);

    @NoException
    public static native int av_pix_fmt_count_planes(@Cast(value={"AVPixelFormat"}) int var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_color_range_name(@Cast(value={"AVColorRange"}) int var0);

    @NoException
    public static native int av_color_range_from_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    public static native int av_color_range_from_name(String var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_color_primaries_name(@Cast(value={"AVColorPrimaries"}) int var0);

    @NoException
    public static native int av_color_primaries_from_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    public static native int av_color_primaries_from_name(String var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_color_transfer_name(@Cast(value={"AVColorTransferCharacteristic"}) int var0);

    @NoException
    public static native int av_color_transfer_from_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    public static native int av_color_transfer_from_name(String var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_color_space_name(@Cast(value={"AVColorSpace"}) int var0);

    @NoException
    public static native int av_color_space_from_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    public static native int av_color_space_from_name(String var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_chroma_location_name(@Cast(value={"AVChromaLocation"}) int var0);

    @NoException
    public static native int av_chroma_location_from_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    public static native int av_chroma_location_from_name(String var0);

    @NoException
    public static native int av_chroma_location_enum_to_pos(IntPointer var0, IntPointer var1, @Cast(value={"AVChromaLocation"}) int var2);

    @NoException
    public static native int av_chroma_location_enum_to_pos(IntBuffer var0, IntBuffer var1, @Cast(value={"AVChromaLocation"}) int var2);

    @NoException
    public static native int av_chroma_location_enum_to_pos(int[] var0, int[] var1, @Cast(value={"AVChromaLocation"}) int var2);

    @NoException
    @Cast(value={"AVChromaLocation"})
    public static native int av_chroma_location_pos_to_enum(int var0, int var1);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_alpha_mode_name(@Cast(value={"AVAlphaMode"}) int var0);

    @NoException
    @Cast(value={"AVAlphaMode"})
    public static native int av_alpha_mode_from_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    @Cast(value={"AVAlphaMode"})
    public static native int av_alpha_mode_from_name(String var0);

    @NoException
    @Cast(value={"AVPixelFormat"})
    public static native int av_get_pix_fmt(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    @Cast(value={"AVPixelFormat"})
    public static native int av_get_pix_fmt(String var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_get_pix_fmt_name(@Cast(value={"AVPixelFormat"}) int var0);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_get_pix_fmt_string(@Cast(value={"char*"}) BytePointer var0, int var1, @Cast(value={"AVPixelFormat"}) int var2);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_get_pix_fmt_string(@Cast(value={"char*"}) ByteBuffer var0, int var1, @Cast(value={"AVPixelFormat"}) int var2);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_get_pix_fmt_string(@Cast(value={"char*"}) byte[] var0, int var1, @Cast(value={"AVPixelFormat"}) int var2);

    @NoException
    public static native void av_read_image_line2(Pointer var0, @Cast(value={"const uint8_t**"}) PointerPointer var1, @Const IntPointer var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7, int var8, int var9);

    @NoException
    public static native void av_read_image_line2(Pointer var0, @Cast(value={"const uint8_t**"}) @ByPtrPtr BytePointer var1, @Const IntPointer var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7, int var8, int var9);

    @NoException
    public static native void av_read_image_line2(Pointer var0, @Cast(value={"const uint8_t**"}) @ByPtrPtr ByteBuffer var1, @Const IntBuffer var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7, int var8, int var9);

    @NoException
    public static native void av_read_image_line2(Pointer var0, @Cast(value={"const uint8_t**"}) @ByPtrPtr byte[] var1, @Const int[] var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7, int var8, int var9);

    @NoException
    public static native void av_read_image_line(@Cast(value={"uint16_t*"}) ShortPointer var0, @Cast(value={"const uint8_t**"}) PointerPointer var1, @Const IntPointer var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7, int var8);

    @NoException
    public static native void av_read_image_line(@Cast(value={"uint16_t*"}) ShortPointer var0, @Cast(value={"const uint8_t**"}) @ByPtrPtr BytePointer var1, @Const IntPointer var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7, int var8);

    @NoException
    public static native void av_read_image_line(@Cast(value={"uint16_t*"}) ShortBuffer var0, @Cast(value={"const uint8_t**"}) @ByPtrPtr ByteBuffer var1, @Const IntBuffer var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7, int var8);

    @NoException
    public static native void av_read_image_line(@Cast(value={"uint16_t*"}) short[] var0, @Cast(value={"const uint8_t**"}) @ByPtrPtr byte[] var1, @Const int[] var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7, int var8);

    @NoException
    public static native void av_write_image_line2(@Const Pointer var0, @Cast(value={"uint8_t**"}) PointerPointer var1, @Const IntPointer var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7, int var8);

    @NoException
    public static native void av_write_image_line2(@Const Pointer var0, @Cast(value={"uint8_t**"}) @ByPtrPtr BytePointer var1, @Const IntPointer var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7, int var8);

    @NoException
    public static native void av_write_image_line2(@Const Pointer var0, @Cast(value={"uint8_t**"}) @ByPtrPtr ByteBuffer var1, @Const IntBuffer var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7, int var8);

    @NoException
    public static native void av_write_image_line2(@Const Pointer var0, @Cast(value={"uint8_t**"}) @ByPtrPtr byte[] var1, @Const int[] var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7, int var8);

    @NoException
    public static native void av_write_image_line(@Cast(value={"const uint16_t*"}) ShortPointer var0, @Cast(value={"uint8_t**"}) PointerPointer var1, @Const IntPointer var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7);

    @NoException
    public static native void av_write_image_line(@Cast(value={"const uint16_t*"}) ShortPointer var0, @Cast(value={"uint8_t**"}) @ByPtrPtr BytePointer var1, @Const IntPointer var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7);

    @NoException
    public static native void av_write_image_line(@Cast(value={"const uint16_t*"}) ShortBuffer var0, @Cast(value={"uint8_t**"}) @ByPtrPtr ByteBuffer var1, @Const IntBuffer var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7);

    @NoException
    public static native void av_write_image_line(@Cast(value={"const uint16_t*"}) short[] var0, @Cast(value={"uint8_t**"}) @ByPtrPtr byte[] var1, @Const int[] var2, @Const AVPixFmtDescriptor var3, int var4, int var5, int var6, int var7);

    @NoException
    @Cast(value={"AVPixelFormat"})
    public static native int av_pix_fmt_swap_endianness(@Cast(value={"AVPixelFormat"}) int var0);

    @NoException
    public static native int av_get_pix_fmt_loss(@Cast(value={"AVPixelFormat"}) int var0, @Cast(value={"AVPixelFormat"}) int var1, int var2);

    @NoException
    @Cast(value={"AVPixelFormat"})
    public static native int av_find_best_pix_fmt_of_2(@Cast(value={"AVPixelFormat"}) int var0, @Cast(value={"AVPixelFormat"}) int var1, @Cast(value={"AVPixelFormat"}) int var2, int var3, IntPointer var4);

    @NoException
    @Cast(value={"AVPixelFormat"})
    public static native int av_find_best_pix_fmt_of_2(@Cast(value={"AVPixelFormat"}) int var0, @Cast(value={"AVPixelFormat"}) int var1, @Cast(value={"AVPixelFormat"}) int var2, int var3, IntBuffer var4);

    @NoException
    @Cast(value={"AVPixelFormat"})
    public static native int av_find_best_pix_fmt_of_2(@Cast(value={"AVPixelFormat"}) int var0, @Cast(value={"AVPixelFormat"}) int var1, @Cast(value={"AVPixelFormat"}) int var2, int var3, int[] var4);

    @NoException
    public static native void av_image_fill_max_pixsteps(IntPointer var0, IntPointer var1, @Const AVPixFmtDescriptor var2);

    @NoException
    public static native void av_image_fill_max_pixsteps(IntBuffer var0, IntBuffer var1, @Const AVPixFmtDescriptor var2);

    @NoException
    public static native void av_image_fill_max_pixsteps(int[] var0, int[] var1, @Const AVPixFmtDescriptor var2);

    @NoException
    public static native int av_image_get_linesize(@Cast(value={"AVPixelFormat"}) int var0, int var1, int var2);

    @NoException
    public static native int av_image_fill_linesizes(IntPointer var0, @Cast(value={"AVPixelFormat"}) int var1, int var2);

    @NoException
    public static native int av_image_fill_linesizes(IntBuffer var0, @Cast(value={"AVPixelFormat"}) int var1, int var2);

    @NoException
    public static native int av_image_fill_linesizes(int[] var0, @Cast(value={"AVPixelFormat"}) int var1, int var2);

    @NoException
    public static native int av_image_fill_plane_sizes(@Cast(value={"size_t*"}) SizeTPointer var0, @Cast(value={"AVPixelFormat"}) int var1, int var2, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var3);

    @NoException
    public static native int av_image_fill_pointers(@Cast(value={"uint8_t**"}) PointerPointer var0, @Cast(value={"AVPixelFormat"}) int var1, int var2, @Cast(value={"uint8_t*"}) BytePointer var3, @Const IntPointer var4);

    @NoException
    public static native int av_image_fill_pointers(@Cast(value={"uint8_t**"}) @ByPtrPtr BytePointer var0, @Cast(value={"AVPixelFormat"}) int var1, int var2, @Cast(value={"uint8_t*"}) BytePointer var3, @Const IntPointer var4);

    @NoException
    public static native int av_image_fill_pointers(@Cast(value={"uint8_t**"}) @ByPtrPtr ByteBuffer var0, @Cast(value={"AVPixelFormat"}) int var1, int var2, @Cast(value={"uint8_t*"}) ByteBuffer var3, @Const IntBuffer var4);

    @NoException
    public static native int av_image_fill_pointers(@Cast(value={"uint8_t**"}) @ByPtrPtr byte[] var0, @Cast(value={"AVPixelFormat"}) int var1, int var2, @Cast(value={"uint8_t*"}) byte[] var3, @Const int[] var4);

    @NoException
    public static native int av_image_alloc(@Cast(value={"uint8_t**"}) PointerPointer var0, IntPointer var1, int var2, int var3, @Cast(value={"AVPixelFormat"}) int var4, int var5);

    @NoException
    public static native int av_image_alloc(@Cast(value={"uint8_t**"}) @ByPtrPtr BytePointer var0, IntPointer var1, int var2, int var3, @Cast(value={"AVPixelFormat"}) int var4, int var5);

    @NoException
    public static native int av_image_alloc(@Cast(value={"uint8_t**"}) @ByPtrPtr ByteBuffer var0, IntBuffer var1, int var2, int var3, @Cast(value={"AVPixelFormat"}) int var4, int var5);

    @NoException
    public static native int av_image_alloc(@Cast(value={"uint8_t**"}) @ByPtrPtr byte[] var0, int[] var1, int var2, int var3, @Cast(value={"AVPixelFormat"}) int var4, int var5);

    @NoException
    public static native void av_image_copy_plane(@Cast(value={"uint8_t*"}) BytePointer var0, int var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, int var4, int var5);

    @NoException
    public static native void av_image_copy_plane(@Cast(value={"uint8_t*"}) ByteBuffer var0, int var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, int var4, int var5);

    @NoException
    public static native void av_image_copy_plane(@Cast(value={"uint8_t*"}) byte[] var0, int var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, int var4, int var5);

    @NoException
    public static native void av_image_copy_plane_uc_from(@Cast(value={"uint8_t*"}) BytePointer var0, @Cast(value={"ptrdiff_t"}) long var1, @Cast(value={"const uint8_t*"}) BytePointer var3, @Cast(value={"ptrdiff_t"}) long var4, @Cast(value={"ptrdiff_t"}) long var6, int var8);

    @NoException
    public static native void av_image_copy_plane_uc_from(@Cast(value={"uint8_t*"}) ByteBuffer var0, @Cast(value={"ptrdiff_t"}) long var1, @Cast(value={"const uint8_t*"}) ByteBuffer var3, @Cast(value={"ptrdiff_t"}) long var4, @Cast(value={"ptrdiff_t"}) long var6, int var8);

    @NoException
    public static native void av_image_copy_plane_uc_from(@Cast(value={"uint8_t*"}) byte[] var0, @Cast(value={"ptrdiff_t"}) long var1, @Cast(value={"const uint8_t*"}) byte[] var3, @Cast(value={"ptrdiff_t"}) long var4, @Cast(value={"ptrdiff_t"}) long var6, int var8);

    @NoException
    public static native void av_image_copy(@Cast(value={"uint8_t*const*"}) PointerPointer var0, @Const IntPointer var1, @Cast(value={"const uint8_t*const*"}) PointerPointer var2, @Const IntPointer var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6);

    @NoException
    public static native void av_image_copy(@Cast(value={"uint8_t*const*"}) @ByPtrPtr BytePointer var0, @Const IntPointer var1, @Cast(value={"const uint8_t*const*"}) @ByPtrPtr BytePointer var2, @Const IntPointer var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6);

    @NoException
    public static native void av_image_copy(@Cast(value={"uint8_t*const*"}) @ByPtrPtr ByteBuffer var0, @Const IntBuffer var1, @Cast(value={"const uint8_t*const*"}) @ByPtrPtr ByteBuffer var2, @Const IntBuffer var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6);

    @NoException
    public static native void av_image_copy(@Cast(value={"uint8_t*const*"}) @ByPtrPtr byte[] var0, @Const int[] var1, @Cast(value={"const uint8_t*const*"}) @ByPtrPtr byte[] var2, @Const int[] var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6);

    @NoException
    public static native void av_image_copy2(@Cast(value={"uint8_t*const*"}) PointerPointer var0, @Const IntPointer var1, @Cast(value={"uint8_t*const*"}) PointerPointer var2, @Const IntPointer var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6);

    @NoException
    public static native void av_image_copy2(@Cast(value={"uint8_t*const*"}) @ByPtrPtr BytePointer var0, @Const IntPointer var1, @Cast(value={"uint8_t*const*"}) @ByPtrPtr BytePointer var2, @Const IntPointer var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6);

    @NoException
    public static native void av_image_copy2(@Cast(value={"uint8_t*const*"}) @ByPtrPtr ByteBuffer var0, @Const IntBuffer var1, @Cast(value={"uint8_t*const*"}) @ByPtrPtr ByteBuffer var2, @Const IntBuffer var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6);

    @NoException
    public static native void av_image_copy2(@Cast(value={"uint8_t*const*"}) @ByPtrPtr byte[] var0, @Const int[] var1, @Cast(value={"uint8_t*const*"}) @ByPtrPtr byte[] var2, @Const int[] var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6);

    @NoException
    public static native void av_image_copy_uc_from(@Cast(value={"uint8_t*const*"}) PointerPointer var0, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var1, @Cast(value={"const uint8_t*const*"}) PointerPointer var2, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6);

    @NoException
    public static native void av_image_copy_uc_from(@Cast(value={"uint8_t*const*"}) @ByPtrPtr BytePointer var0, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var1, @Cast(value={"const uint8_t*const*"}) @ByPtrPtr BytePointer var2, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6);

    @NoException
    public static native void av_image_copy_uc_from(@Cast(value={"uint8_t*const*"}) @ByPtrPtr ByteBuffer var0, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var1, @Cast(value={"const uint8_t*const*"}) @ByPtrPtr ByteBuffer var2, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6);

    @NoException
    public static native void av_image_copy_uc_from(@Cast(value={"uint8_t*const*"}) @ByPtrPtr byte[] var0, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var1, @Cast(value={"const uint8_t*const*"}) @ByPtrPtr byte[] var2, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6);

    @NoException
    public static native int av_image_fill_arrays(@Cast(value={"uint8_t**"}) PointerPointer var0, IntPointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, @Cast(value={"AVPixelFormat"}) int var3, int var4, int var5, int var6);

    @NoException
    public static native int av_image_fill_arrays(@Cast(value={"uint8_t**"}) @ByPtrPtr BytePointer var0, IntPointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, @Cast(value={"AVPixelFormat"}) int var3, int var4, int var5, int var6);

    @NoException
    public static native int av_image_fill_arrays(@Cast(value={"uint8_t**"}) @ByPtrPtr ByteBuffer var0, IntBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, @Cast(value={"AVPixelFormat"}) int var3, int var4, int var5, int var6);

    @NoException
    public static native int av_image_fill_arrays(@Cast(value={"uint8_t**"}) @ByPtrPtr byte[] var0, int[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, @Cast(value={"AVPixelFormat"}) int var3, int var4, int var5, int var6);

    @NoException
    public static native int av_image_get_buffer_size(@Cast(value={"AVPixelFormat"}) int var0, int var1, int var2, int var3);

    @NoException
    public static native int av_image_copy_to_buffer(@Cast(value={"uint8_t*"}) BytePointer var0, int var1, @Cast(value={"const uint8_t*const*"}) PointerPointer var2, @Const IntPointer var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6, int var7);

    @NoException
    public static native int av_image_copy_to_buffer(@Cast(value={"uint8_t*"}) BytePointer var0, int var1, @Cast(value={"const uint8_t*const*"}) @ByPtrPtr BytePointer var2, @Const IntPointer var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6, int var7);

    @NoException
    public static native int av_image_copy_to_buffer(@Cast(value={"uint8_t*"}) ByteBuffer var0, int var1, @Cast(value={"const uint8_t*const*"}) @ByPtrPtr ByteBuffer var2, @Const IntBuffer var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6, int var7);

    @NoException
    public static native int av_image_copy_to_buffer(@Cast(value={"uint8_t*"}) byte[] var0, int var1, @Cast(value={"const uint8_t*const*"}) @ByPtrPtr byte[] var2, @Const int[] var3, @Cast(value={"AVPixelFormat"}) int var4, int var5, int var6, int var7);

    @NoException
    public static native int av_image_check_size(@Cast(value={"unsigned int"}) int var0, @Cast(value={"unsigned int"}) int var1, int var2, Pointer var3);

    @NoException
    public static native int av_image_check_size2(@Cast(value={"unsigned int"}) int var0, @Cast(value={"unsigned int"}) int var1, @Cast(value={"int64_t"}) long var2, @Cast(value={"AVPixelFormat"}) int var4, int var5, Pointer var6);

    @NoException
    public static native int av_image_check_sar(@Cast(value={"unsigned int"}) int var0, @Cast(value={"unsigned int"}) int var1, @ByVal AVRational var2);

    @NoException
    public static native int av_image_fill_black(@Cast(value={"uint8_t*const*"}) PointerPointer var0, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var1, @Cast(value={"AVPixelFormat"}) int var2, @Cast(value={"AVColorRange"}) int var3, int var4, int var5);

    @NoException
    public static native int av_image_fill_black(@Cast(value={"uint8_t*const*"}) @ByPtrPtr BytePointer var0, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var1, @Cast(value={"AVPixelFormat"}) int var2, @Cast(value={"AVColorRange"}) int var3, int var4, int var5);

    @NoException
    public static native int av_image_fill_black(@Cast(value={"uint8_t*const*"}) @ByPtrPtr ByteBuffer var0, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var1, @Cast(value={"AVPixelFormat"}) int var2, @Cast(value={"AVColorRange"}) int var3, int var4, int var5);

    @NoException
    public static native int av_image_fill_black(@Cast(value={"uint8_t*const*"}) @ByPtrPtr byte[] var0, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var1, @Cast(value={"AVPixelFormat"}) int var2, @Cast(value={"AVColorRange"}) int var3, int var4, int var5);

    @NoException
    public static native int av_image_fill_color(@Cast(value={"uint8_t*const*"}) PointerPointer var0, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var1, @Cast(value={"AVPixelFormat"}) int var2, @Cast(value={"const uint32_t*"}) IntPointer var3, int var4, int var5, int var6);

    @NoException
    public static native int av_image_fill_color(@Cast(value={"uint8_t*const*"}) @ByPtrPtr BytePointer var0, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var1, @Cast(value={"AVPixelFormat"}) int var2, @Cast(value={"const uint32_t*"}) IntPointer var3, int var4, int var5, int var6);

    @NoException
    public static native int av_image_fill_color(@Cast(value={"uint8_t*const*"}) @ByPtrPtr ByteBuffer var0, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var1, @Cast(value={"AVPixelFormat"}) int var2, @Cast(value={"const uint32_t*"}) IntBuffer var3, int var4, int var5, int var6);

    @NoException
    public static native int av_image_fill_color(@Cast(value={"uint8_t*const*"}) @ByPtrPtr byte[] var0, @Cast(value={"const ptrdiff_t*"}) SizeTPointer var1, @Cast(value={"AVPixelFormat"}) int var2, @Cast(value={"const uint32_t*"}) int[] var3, int var4, int var5, int var6);

    @NoException
    public static native AVDownmixInfo av_downmix_info_update_side_data(AVFrame var0);

    @NoException
    public static native AVStereo3D av_stereo3d_alloc();

    @NoException
    public static native AVStereo3D av_stereo3d_alloc_size(@Cast(value={"size_t*"}) SizeTPointer var0);

    @NoException
    public static native AVStereo3D av_stereo3d_create_side_data(AVFrame var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_stereo3d_type_name(@Cast(value={"unsigned int"}) int var0);

    @NoException
    public static native int av_stereo3d_from_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    public static native int av_stereo3d_from_name(String var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_stereo3d_view_name(@Cast(value={"unsigned int"}) int var0);

    @NoException
    public static native int av_stereo3d_view_from_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    public static native int av_stereo3d_view_from_name(String var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_stereo3d_primary_eye_name(@Cast(value={"unsigned int"}) int var0);

    @NoException
    public static native int av_stereo3d_primary_eye_from_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    public static native int av_stereo3d_primary_eye_from_name(String var0);

    @NoException
    public static native AVFifo av_fifo_alloc2(@Cast(value={"size_t"}) long var0, @Cast(value={"size_t"}) long var2, @Cast(value={"unsigned int"}) int var4);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_fifo_elem_size(@Const AVFifo var0);

    @NoException
    public static native void av_fifo_auto_grow_limit(AVFifo var0, @Cast(value={"size_t"}) long var1);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_fifo_can_read(@Const AVFifo var0);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_fifo_can_write(@Const AVFifo var0);

    @NoException
    public static native int av_fifo_grow2(AVFifo var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native int av_fifo_write(AVFifo var0, @Const Pointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int av_fifo_write_from_cb(AVFifo var0, AVFifoCB var1, Pointer var2, @Cast(value={"size_t*"}) SizeTPointer var3);

    @NoException
    public static native int av_fifo_read(AVFifo var0, Pointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int av_fifo_read_to_cb(AVFifo var0, AVFifoCB var1, Pointer var2, @Cast(value={"size_t*"}) SizeTPointer var3);

    @NoException
    public static native int av_fifo_peek(@Const AVFifo var0, Pointer var1, @Cast(value={"size_t"}) long var2, @Cast(value={"size_t"}) long var4);

    @NoException
    public static native int av_fifo_peek_to_cb(@Const AVFifo var0, AVFifoCB var1, Pointer var2, @Cast(value={"size_t*"}) SizeTPointer var3, @Cast(value={"size_t"}) long var4);

    @NoException
    public static native void av_fifo_drain2(AVFifo var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native void av_fifo_reset2(AVFifo var0);

    @NoException
    public static native void av_fifo_freep2(@Cast(value={"AVFifo**"}) PointerPointer var0);

    @NoException
    public static native void av_fifo_freep2(@ByPtrPtr AVFifo var0);

    @NoException
    public static native void av_audio_fifo_free(AVAudioFifo var0);

    @NoException
    public static native AVAudioFifo av_audio_fifo_alloc(@Cast(value={"AVSampleFormat"}) int var0, int var1, int var2);

    @NoException
    public static native int av_audio_fifo_realloc(AVAudioFifo var0, int var1);

    @NoException
    public static native int av_audio_fifo_write(AVAudioFifo var0, @Cast(value={"void*const*"}) PointerPointer var1, int var2);

    @NoException
    public static native int av_audio_fifo_write(AVAudioFifo var0, @Cast(value={"void*const*"}) @ByPtrPtr Pointer var1, int var2);

    @NoException
    public static native int av_audio_fifo_peek(@Const AVAudioFifo var0, @Cast(value={"void*const*"}) PointerPointer var1, int var2);

    @NoException
    public static native int av_audio_fifo_peek(@Const AVAudioFifo var0, @Cast(value={"void*const*"}) @ByPtrPtr Pointer var1, int var2);

    @NoException
    public static native int av_audio_fifo_peek_at(@Const AVAudioFifo var0, @Cast(value={"void*const*"}) PointerPointer var1, int var2, int var3);

    @NoException
    public static native int av_audio_fifo_peek_at(@Const AVAudioFifo var0, @Cast(value={"void*const*"}) @ByPtrPtr Pointer var1, int var2, int var3);

    @NoException
    public static native int av_audio_fifo_read(AVAudioFifo var0, @Cast(value={"void*const*"}) PointerPointer var1, int var2);

    @NoException
    public static native int av_audio_fifo_read(AVAudioFifo var0, @Cast(value={"void*const*"}) @ByPtrPtr Pointer var1, int var2);

    @NoException
    public static native int av_audio_fifo_drain(AVAudioFifo var0, int var1);

    @NoException
    public static native void av_audio_fifo_reset(AVAudioFifo var0);

    @NoException
    public static native int av_audio_fifo_size(AVAudioFifo var0);

    @NoException
    public static native int av_audio_fifo_space(AVAudioFifo var0);

    @NoException
    @Cast(value={"AVHWDeviceType"})
    public static native int av_hwdevice_find_type_by_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    @Cast(value={"AVHWDeviceType"})
    public static native int av_hwdevice_find_type_by_name(String var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_hwdevice_get_type_name(@Cast(value={"AVHWDeviceType"}) int var0);

    @NoException
    @Cast(value={"AVHWDeviceType"})
    public static native int av_hwdevice_iterate_types(@Cast(value={"AVHWDeviceType"}) int var0);

    @NoException
    public static native AVBufferRef av_hwdevice_ctx_alloc(@Cast(value={"AVHWDeviceType"}) int var0);

    @NoException
    public static native int av_hwdevice_ctx_init(AVBufferRef var0);

    @NoException
    public static native int av_hwdevice_ctx_create(@Cast(value={"AVBufferRef**"}) PointerPointer var0, @Cast(value={"AVHWDeviceType"}) int var1, @Cast(value={"const char*"}) BytePointer var2, AVDictionary var3, int var4);

    @NoException
    public static native int av_hwdevice_ctx_create(@ByPtrPtr AVBufferRef var0, @Cast(value={"AVHWDeviceType"}) int var1, @Cast(value={"const char*"}) BytePointer var2, AVDictionary var3, int var4);

    @NoException
    public static native int av_hwdevice_ctx_create(@ByPtrPtr AVBufferRef var0, @Cast(value={"AVHWDeviceType"}) int var1, String var2, AVDictionary var3, int var4);

    @NoException
    public static native int av_hwdevice_ctx_create_derived(@Cast(value={"AVBufferRef**"}) PointerPointer var0, @Cast(value={"AVHWDeviceType"}) int var1, AVBufferRef var2, int var3);

    @NoException
    public static native int av_hwdevice_ctx_create_derived(@ByPtrPtr AVBufferRef var0, @Cast(value={"AVHWDeviceType"}) int var1, AVBufferRef var2, int var3);

    @NoException
    public static native int av_hwdevice_ctx_create_derived_opts(@Cast(value={"AVBufferRef**"}) PointerPointer var0, @Cast(value={"AVHWDeviceType"}) int var1, AVBufferRef var2, AVDictionary var3, int var4);

    @NoException
    public static native int av_hwdevice_ctx_create_derived_opts(@ByPtrPtr AVBufferRef var0, @Cast(value={"AVHWDeviceType"}) int var1, AVBufferRef var2, AVDictionary var3, int var4);

    @NoException
    public static native AVBufferRef av_hwframe_ctx_alloc(AVBufferRef var0);

    @NoException
    public static native int av_hwframe_ctx_init(AVBufferRef var0);

    @NoException
    public static native int av_hwframe_get_buffer(AVBufferRef var0, AVFrame var1, int var2);

    @NoException
    public static native int av_hwframe_transfer_data(AVFrame var0, @Const AVFrame var1, int var2);

    @NoException
    public static native int av_hwframe_transfer_get_formats(AVBufferRef var0, @Cast(value={"AVHWFrameTransferDirection"}) int var1, @Cast(value={"AVPixelFormat**"}) PointerPointer var2, int var3);

    @NoException
    public static native int av_hwframe_transfer_get_formats(AVBufferRef var0, @Cast(value={"AVHWFrameTransferDirection"}) int var1, @Cast(value={"AVPixelFormat**"}) @ByPtrPtr IntPointer var2, int var3);

    @NoException
    public static native int av_hwframe_transfer_get_formats(AVBufferRef var0, @Cast(value={"AVHWFrameTransferDirection"}) int var1, @Cast(value={"AVPixelFormat**"}) @ByPtrPtr IntBuffer var2, int var3);

    @NoException
    public static native int av_hwframe_transfer_get_formats(AVBufferRef var0, @Cast(value={"AVHWFrameTransferDirection"}) int var1, @Cast(value={"AVPixelFormat**"}) @ByPtrPtr int[] var2, int var3);

    @NoException
    public static native Pointer av_hwdevice_hwconfig_alloc(AVBufferRef var0);

    @NoException
    public static native AVHWFramesConstraints av_hwdevice_get_hwframe_constraints(AVBufferRef var0, @Const Pointer var1);

    @NoException
    public static native void av_hwframe_constraints_free(@Cast(value={"AVHWFramesConstraints**"}) PointerPointer var0);

    @NoException
    public static native void av_hwframe_constraints_free(@ByPtrPtr AVHWFramesConstraints var0);

    @NoException
    public static native int av_hwframe_map(AVFrame var0, @Const AVFrame var1, int var2);

    @NoException
    public static native int av_hwframe_ctx_create_derived(@Cast(value={"AVBufferRef**"}) PointerPointer var0, @Cast(value={"AVPixelFormat"}) int var1, AVBufferRef var2, AVBufferRef var3, int var4);

    @NoException
    public static native int av_hwframe_ctx_create_derived(@ByPtrPtr AVBufferRef var0, @Cast(value={"AVPixelFormat"}) int var1, AVBufferRef var2, AVBufferRef var3, int var4);

    @NoException
    @Cast(value={"AVAdler"})
    public static native int av_adler32_update(@Cast(value={"AVAdler"}) int var0, @Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"AVAdler"})
    public static native int av_adler32_update(@Cast(value={"AVAdler"}) int var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"AVAdler"})
    public static native int av_adler32_update(@Cast(value={"AVAdler"}) int var0, @Cast(value={"const uint8_t*"}) byte[] var1, @Cast(value={"size_t"}) long var2);

    @MemberGetter
    public static native int av_aes_size();

    @NoException
    public static native AVAES av_aes_alloc();

    @NoException
    public static native int av_aes_init(AVAES var0, @Cast(value={"const uint8_t*"}) BytePointer var1, int var2, int var3);

    @NoException
    public static native int av_aes_init(AVAES var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, int var2, int var3);

    @NoException
    public static native int av_aes_init(AVAES var0, @Cast(value={"const uint8_t*"}) byte[] var1, int var2, int var3);

    @NoException
    public static native void av_aes_crypt(AVAES var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, @Cast(value={"uint8_t*"}) BytePointer var4, int var5);

    @NoException
    public static native void av_aes_crypt(AVAES var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, @Cast(value={"uint8_t*"}) ByteBuffer var4, int var5);

    @NoException
    public static native void av_aes_crypt(AVAES var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, @Cast(value={"uint8_t*"}) byte[] var4, int var5);

    @NoException
    public static native AVAESCTR av_aes_ctr_alloc();

    @NoException
    public static native int av_aes_ctr_init(AVAESCTR var0, @Cast(value={"const uint8_t*"}) BytePointer var1);

    @NoException
    public static native int av_aes_ctr_init(AVAESCTR var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1);

    @NoException
    public static native int av_aes_ctr_init(AVAESCTR var0, @Cast(value={"const uint8_t*"}) byte[] var1);

    @NoException
    public static native void av_aes_ctr_free(AVAESCTR var0);

    @NoException
    public static native void av_aes_ctr_crypt(AVAESCTR var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3);

    @NoException
    public static native void av_aes_ctr_crypt(AVAESCTR var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3);

    @NoException
    public static native void av_aes_ctr_crypt(AVAESCTR var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3);

    @NoException
    @Cast(value={"const uint8_t*"})
    public static native BytePointer av_aes_ctr_get_iv(AVAESCTR var0);

    @NoException
    public static native void av_aes_ctr_set_random_iv(AVAESCTR var0);

    @NoException
    public static native void av_aes_ctr_set_iv(AVAESCTR var0, @Cast(value={"const uint8_t*"}) BytePointer var1);

    @NoException
    public static native void av_aes_ctr_set_iv(AVAESCTR var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1);

    @NoException
    public static native void av_aes_ctr_set_iv(AVAESCTR var0, @Cast(value={"const uint8_t*"}) byte[] var1);

    @NoException
    public static native void av_aes_ctr_set_full_iv(AVAESCTR var0, @Cast(value={"const uint8_t*"}) BytePointer var1);

    @NoException
    public static native void av_aes_ctr_set_full_iv(AVAESCTR var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1);

    @NoException
    public static native void av_aes_ctr_set_full_iv(AVAESCTR var0, @Cast(value={"const uint8_t*"}) byte[] var1);

    @NoException
    public static native void av_aes_ctr_increment_iv(AVAESCTR var0);

    @NoException
    public static native int av_base64_decode(@Cast(value={"uint8_t*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2);

    @NoException
    public static native int av_base64_decode(@Cast(value={"uint8_t*"}) ByteBuffer var0, String var1, int var2);

    @NoException
    public static native int av_base64_decode(@Cast(value={"uint8_t*"}) byte[] var0, @Cast(value={"const char*"}) BytePointer var1, int var2);

    @NoException
    public static native int av_base64_decode(@Cast(value={"uint8_t*"}) BytePointer var0, String var1, int var2);

    @NoException
    public static native int av_base64_decode(@Cast(value={"uint8_t*"}) ByteBuffer var0, @Cast(value={"const char*"}) BytePointer var1, int var2);

    @NoException
    public static native int av_base64_decode(@Cast(value={"uint8_t*"}) byte[] var0, String var1, int var2);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_base64_encode(@Cast(value={"char*"}) BytePointer var0, int var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_base64_encode(@Cast(value={"char*"}) ByteBuffer var0, int var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_base64_encode(@Cast(value={"char*"}) byte[] var0, int var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3);

    @NoException
    public static native AVBlowfish av_blowfish_alloc();

    @NoException
    public static native void av_blowfish_init(AVBlowfish var0, @Cast(value={"const uint8_t*"}) BytePointer var1, int var2);

    @NoException
    public static native void av_blowfish_init(AVBlowfish var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, int var2);

    @NoException
    public static native void av_blowfish_init(AVBlowfish var0, @Cast(value={"const uint8_t*"}) byte[] var1, int var2);

    @NoException
    public static native void av_blowfish_crypt_ecb(AVBlowfish var0, @Cast(value={"uint32_t*"}) IntPointer var1, @Cast(value={"uint32_t*"}) IntPointer var2, int var3);

    @NoException
    public static native void av_blowfish_crypt_ecb(AVBlowfish var0, @Cast(value={"uint32_t*"}) IntBuffer var1, @Cast(value={"uint32_t*"}) IntBuffer var2, int var3);

    @NoException
    public static native void av_blowfish_crypt_ecb(AVBlowfish var0, @Cast(value={"uint32_t*"}) int[] var1, @Cast(value={"uint32_t*"}) int[] var2, int var3);

    @NoException
    public static native void av_blowfish_crypt(AVBlowfish var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, @Cast(value={"uint8_t*"}) BytePointer var4, int var5);

    @NoException
    public static native void av_blowfish_crypt(AVBlowfish var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, @Cast(value={"uint8_t*"}) ByteBuffer var4, int var5);

    @NoException
    public static native void av_blowfish_crypt(AVBlowfish var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, @Cast(value={"uint8_t*"}) byte[] var4, int var5);

    @MemberGetter
    public static native int av_cast5_size();

    @NoException
    public static native AVCAST5 av_cast5_alloc();

    @NoException
    public static native int av_cast5_init(AVCAST5 var0, @Cast(value={"const uint8_t*"}) BytePointer var1, int var2);

    @NoException
    public static native int av_cast5_init(AVCAST5 var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, int var2);

    @NoException
    public static native int av_cast5_init(AVCAST5 var0, @Cast(value={"const uint8_t*"}) byte[] var1, int var2);

    @NoException
    public static native void av_cast5_crypt(AVCAST5 var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, int var4);

    @NoException
    public static native void av_cast5_crypt(AVCAST5 var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, int var4);

    @NoException
    public static native void av_cast5_crypt(AVCAST5 var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, int var4);

    @NoException
    public static native void av_cast5_crypt2(AVCAST5 var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, @Cast(value={"uint8_t*"}) BytePointer var4, int var5);

    @NoException
    public static native void av_cast5_crypt2(AVCAST5 var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, @Cast(value={"uint8_t*"}) ByteBuffer var4, int var5);

    @NoException
    public static native void av_cast5_crypt2(AVCAST5 var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, @Cast(value={"uint8_t*"}) byte[] var4, int var5);

    @MemberGetter
    public static native int av_camellia_size();

    @NoException
    public static native AVCAMELLIA av_camellia_alloc();

    @NoException
    public static native int av_camellia_init(AVCAMELLIA var0, @Cast(value={"const uint8_t*"}) BytePointer var1, int var2);

    @NoException
    public static native int av_camellia_init(AVCAMELLIA var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, int var2);

    @NoException
    public static native int av_camellia_init(AVCAMELLIA var0, @Cast(value={"const uint8_t*"}) byte[] var1, int var2);

    @NoException
    public static native void av_camellia_crypt(AVCAMELLIA var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, @Cast(value={"uint8_t*"}) BytePointer var4, int var5);

    @NoException
    public static native void av_camellia_crypt(AVCAMELLIA var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, @Cast(value={"uint8_t*"}) ByteBuffer var4, int var5);

    @NoException
    public static native void av_camellia_crypt(AVCAMELLIA var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, @Cast(value={"uint8_t*"}) byte[] var4, int var5);

    @NoException
    public static native int av_crc_init(@Cast(value={"AVCRC*"}) IntPointer var0, int var1, int var2, @Cast(value={"uint32_t"}) int var3, int var4);

    @NoException
    public static native int av_crc_init(@Cast(value={"AVCRC*"}) IntBuffer var0, int var1, int var2, @Cast(value={"uint32_t"}) int var3, int var4);

    @NoException
    public static native int av_crc_init(@Cast(value={"AVCRC*"}) int[] var0, int var1, int var2, @Cast(value={"uint32_t"}) int var3, int var4);

    @NoException
    @Cast(value={"const AVCRC*"})
    public static native IntPointer av_crc_get_table(@Cast(value={"AVCRCId"}) int var0);

    @NoException
    @Cast(value={"uint32_t"})
    public static native int av_crc(@Cast(value={"const AVCRC*"}) IntPointer var0, @Cast(value={"uint32_t"}) int var1, @Cast(value={"const uint8_t*"}) BytePointer var2, @Cast(value={"size_t"}) long var3);

    @NoException
    @Cast(value={"uint32_t"})
    public static native int av_crc(@Cast(value={"const AVCRC*"}) IntBuffer var0, @Cast(value={"uint32_t"}) int var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, @Cast(value={"size_t"}) long var3);

    @NoException
    @Cast(value={"uint32_t"})
    public static native int av_crc(@Cast(value={"const AVCRC*"}) int[] var0, @Cast(value={"uint32_t"}) int var1, @Cast(value={"const uint8_t*"}) byte[] var2, @Cast(value={"size_t"}) long var3);

    @NoException
    public static native AVDES av_des_alloc();

    @NoException
    public static native int av_des_init(AVDES var0, @Cast(value={"const uint8_t*"}) BytePointer var1, int var2, int var3);

    @NoException
    public static native int av_des_init(AVDES var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, int var2, int var3);

    @NoException
    public static native int av_des_init(AVDES var0, @Cast(value={"const uint8_t*"}) byte[] var1, int var2, int var3);

    @NoException
    public static native void av_des_crypt(AVDES var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, @Cast(value={"uint8_t*"}) BytePointer var4, int var5);

    @NoException
    public static native void av_des_crypt(AVDES var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, @Cast(value={"uint8_t*"}) ByteBuffer var4, int var5);

    @NoException
    public static native void av_des_crypt(AVDES var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, @Cast(value={"uint8_t*"}) byte[] var4, int var5);

    @NoException
    public static native void av_des_mac(AVDES var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3);

    @NoException
    public static native void av_des_mac(AVDES var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3);

    @NoException
    public static native void av_des_mac(AVDES var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3);

    @NoException
    public static native void av_lfg_init(AVLFG var0, @Cast(value={"unsigned int"}) int var1);

    @NoException
    public static native int av_lfg_init_from_data(AVLFG var0, @Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    public static native int av_lfg_init_from_data(AVLFG var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    public static native int av_lfg_init_from_data(AVLFG var0, @Cast(value={"const uint8_t*"}) byte[] var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    @Cast(value={"unsigned int"})
    public static native int av_lfg_get(AVLFG var0);

    @NoException
    @Cast(value={"unsigned int"})
    public static native int av_mlfg_get(AVLFG var0);

    @NoException
    public static native void av_bmg_get(AVLFG var0, DoublePointer var1);

    @NoException
    public static native void av_bmg_get(AVLFG var0, DoubleBuffer var1);

    @NoException
    public static native void av_bmg_get(AVLFG var0, double[] var1);

    @NoException
    public static native AVHMAC av_hmac_alloc(@Cast(value={"AVHMACType"}) int var0);

    @NoException
    public static native void av_hmac_free(AVHMAC var0);

    @NoException
    public static native void av_hmac_init(AVHMAC var0, @Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    public static native void av_hmac_init(AVHMAC var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    public static native void av_hmac_init(AVHMAC var0, @Cast(value={"const uint8_t*"}) byte[] var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    public static native void av_hmac_update(AVHMAC var0, @Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    public static native void av_hmac_update(AVHMAC var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    public static native void av_hmac_update(AVHMAC var0, @Cast(value={"const uint8_t*"}) byte[] var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    public static native int av_hmac_final(AVHMAC var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    public static native int av_hmac_final(AVHMAC var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    public static native int av_hmac_final(AVHMAC var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"unsigned int"}) int var2);

    @NoException
    public static native int av_hmac_calc(AVHMAC var0, @Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"unsigned int"}) int var2, @Cast(value={"const uint8_t*"}) BytePointer var3, @Cast(value={"unsigned int"}) int var4, @Cast(value={"uint8_t*"}) BytePointer var5, @Cast(value={"unsigned int"}) int var6);

    @NoException
    public static native int av_hmac_calc(AVHMAC var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, @Cast(value={"unsigned int"}) int var2, @Cast(value={"const uint8_t*"}) ByteBuffer var3, @Cast(value={"unsigned int"}) int var4, @Cast(value={"uint8_t*"}) ByteBuffer var5, @Cast(value={"unsigned int"}) int var6);

    @NoException
    public static native int av_hmac_calc(AVHMAC var0, @Cast(value={"const uint8_t*"}) byte[] var1, @Cast(value={"unsigned int"}) int var2, @Cast(value={"const uint8_t*"}) byte[] var3, @Cast(value={"unsigned int"}) int var4, @Cast(value={"uint8_t*"}) byte[] var5, @Cast(value={"unsigned int"}) int var6);

    @MemberGetter
    public static native int av_md5_size();

    @NoException
    public static native AVMD5 av_md5_alloc();

    @NoException
    public static native void av_md5_init(AVMD5 var0);

    @NoException
    public static native void av_md5_update(AVMD5 var0, @Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_md5_update(AVMD5 var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_md5_update(AVMD5 var0, @Cast(value={"const uint8_t*"}) byte[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_md5_final(AVMD5 var0, @Cast(value={"uint8_t*"}) BytePointer var1);

    @NoException
    public static native void av_md5_final(AVMD5 var0, @Cast(value={"uint8_t*"}) ByteBuffer var1);

    @NoException
    public static native void av_md5_final(AVMD5 var0, @Cast(value={"uint8_t*"}) byte[] var1);

    @NoException
    public static native void av_md5_sum(@Cast(value={"uint8_t*"}) BytePointer var0, @Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_md5_sum(@Cast(value={"uint8_t*"}) ByteBuffer var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_md5_sum(@Cast(value={"uint8_t*"}) byte[] var0, @Cast(value={"const uint8_t*"}) byte[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native AVRC4 av_rc4_alloc();

    @NoException
    public static native int av_rc4_init(AVRC4 var0, @Cast(value={"const uint8_t*"}) BytePointer var1, int var2, int var3);

    @NoException
    public static native int av_rc4_init(AVRC4 var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, int var2, int var3);

    @NoException
    public static native int av_rc4_init(AVRC4 var0, @Cast(value={"const uint8_t*"}) byte[] var1, int var2, int var3);

    @NoException
    public static native void av_rc4_crypt(AVRC4 var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, @Cast(value={"uint8_t*"}) BytePointer var4, int var5);

    @NoException
    public static native void av_rc4_crypt(AVRC4 var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, @Cast(value={"uint8_t*"}) ByteBuffer var4, int var5);

    @NoException
    public static native void av_rc4_crypt(AVRC4 var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, @Cast(value={"uint8_t*"}) byte[] var4, int var5);

    @MemberGetter
    public static native int av_ripemd_size();

    @NoException
    public static native AVRIPEMD av_ripemd_alloc();

    @NoException
    public static native int av_ripemd_init(AVRIPEMD var0, int var1);

    @NoException
    public static native void av_ripemd_update(AVRIPEMD var0, @Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_ripemd_update(AVRIPEMD var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_ripemd_update(AVRIPEMD var0, @Cast(value={"const uint8_t*"}) byte[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_ripemd_final(AVRIPEMD var0, @Cast(value={"uint8_t*"}) BytePointer var1);

    @NoException
    public static native void av_ripemd_final(AVRIPEMD var0, @Cast(value={"uint8_t*"}) ByteBuffer var1);

    @NoException
    public static native void av_ripemd_final(AVRIPEMD var0, @Cast(value={"uint8_t*"}) byte[] var1);

    @MemberGetter
    public static native int av_tea_size();

    @NoException
    public static native AVTEA av_tea_alloc();

    @NoException
    public static native void av_tea_init(AVTEA var0, @Cast(value={"const uint8_t*"}) BytePointer var1, int var2);

    @NoException
    public static native void av_tea_init(AVTEA var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, int var2);

    @NoException
    public static native void av_tea_init(AVTEA var0, @Cast(value={"const uint8_t*"}) byte[] var1, int var2);

    @NoException
    public static native void av_tea_crypt(AVTEA var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, @Cast(value={"uint8_t*"}) BytePointer var4, int var5);

    @NoException
    public static native void av_tea_crypt(AVTEA var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, @Cast(value={"uint8_t*"}) ByteBuffer var4, int var5);

    @NoException
    public static native void av_tea_crypt(AVTEA var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, @Cast(value={"uint8_t*"}) byte[] var4, int var5);

    @MemberGetter
    public static native int av_twofish_size();

    @NoException
    public static native AVTWOFISH av_twofish_alloc();

    @NoException
    public static native int av_twofish_init(AVTWOFISH var0, @Cast(value={"const uint8_t*"}) BytePointer var1, int var2);

    @NoException
    public static native int av_twofish_init(AVTWOFISH var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, int var2);

    @NoException
    public static native int av_twofish_init(AVTWOFISH var0, @Cast(value={"const uint8_t*"}) byte[] var1, int var2);

    @NoException
    public static native void av_twofish_crypt(AVTWOFISH var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, @Cast(value={"uint8_t*"}) BytePointer var4, int var5);

    @NoException
    public static native void av_twofish_crypt(AVTWOFISH var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, @Cast(value={"uint8_t*"}) ByteBuffer var4, int var5);

    @NoException
    public static native void av_twofish_crypt(AVTWOFISH var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, @Cast(value={"uint8_t*"}) byte[] var4, int var5);

    @MemberGetter
    public static native int av_sha_size();

    @NoException
    public static native AVSHA av_sha_alloc();

    @NoException
    public static native int av_sha_init(AVSHA var0, int var1);

    @NoException
    public static native void av_sha_update(AVSHA var0, @Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_sha_update(AVSHA var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_sha_update(AVSHA var0, @Cast(value={"const uint8_t*"}) byte[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_sha_final(AVSHA var0, @Cast(value={"uint8_t*"}) BytePointer var1);

    @NoException
    public static native void av_sha_final(AVSHA var0, @Cast(value={"uint8_t*"}) ByteBuffer var1);

    @NoException
    public static native void av_sha_final(AVSHA var0, @Cast(value={"uint8_t*"}) byte[] var1);

    @MemberGetter
    public static native int av_sha512_size();

    @NoException
    public static native AVSHA512 av_sha512_alloc();

    @NoException
    public static native int av_sha512_init(AVSHA512 var0, int var1);

    @NoException
    public static native void av_sha512_update(AVSHA512 var0, @Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_sha512_update(AVSHA512 var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_sha512_update(AVSHA512 var0, @Cast(value={"const uint8_t*"}) byte[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_sha512_final(AVSHA512 var0, @Cast(value={"uint8_t*"}) BytePointer var1);

    @NoException
    public static native void av_sha512_final(AVSHA512 var0, @Cast(value={"uint8_t*"}) ByteBuffer var1);

    @NoException
    public static native void av_sha512_final(AVSHA512 var0, @Cast(value={"uint8_t*"}) byte[] var1);

    @NoException
    public static native AVXTEA av_xtea_alloc();

    @NoException
    public static native void av_xtea_init(AVXTEA var0, @Cast(value={"const uint8_t*"}) BytePointer var1);

    @NoException
    public static native void av_xtea_init(AVXTEA var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1);

    @NoException
    public static native void av_xtea_init(AVXTEA var0, @Cast(value={"const uint8_t*"}) byte[] var1);

    @NoException
    public static native void av_xtea_le_init(AVXTEA var0, @Cast(value={"const uint8_t*"}) BytePointer var1);

    @NoException
    public static native void av_xtea_le_init(AVXTEA var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1);

    @NoException
    public static native void av_xtea_le_init(AVXTEA var0, @Cast(value={"const uint8_t*"}) byte[] var1);

    @NoException
    public static native void av_xtea_crypt(AVXTEA var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, @Cast(value={"uint8_t*"}) BytePointer var4, int var5);

    @NoException
    public static native void av_xtea_crypt(AVXTEA var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, @Cast(value={"uint8_t*"}) ByteBuffer var4, int var5);

    @NoException
    public static native void av_xtea_crypt(AVXTEA var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, @Cast(value={"uint8_t*"}) byte[] var4, int var5);

    @NoException
    public static native void av_xtea_le_crypt(AVXTEA var0, @Cast(value={"uint8_t*"}) BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3, @Cast(value={"uint8_t*"}) BytePointer var4, int var5);

    @NoException
    public static native void av_xtea_le_crypt(AVXTEA var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, int var3, @Cast(value={"uint8_t*"}) ByteBuffer var4, int var5);

    @NoException
    public static native void av_xtea_le_crypt(AVXTEA var0, @Cast(value={"uint8_t*"}) byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, int var3, @Cast(value={"uint8_t*"}) byte[] var4, int var5);

    @NoException
    public static native int av_strstart(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char**"}) PointerPointer var2);

    @NoException
    public static native int av_strstart(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char**"}) @ByPtrPtr BytePointer var2);

    @NoException
    public static native int av_strstart(String var0, String var1, @Cast(value={"const char**"}) @ByPtrPtr ByteBuffer var2);

    @NoException
    public static native int av_strstart(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char**"}) @ByPtrPtr byte[] var2);

    @NoException
    public static native int av_strstart(String var0, String var1, @Cast(value={"const char**"}) @ByPtrPtr BytePointer var2);

    @NoException
    public static native int av_strstart(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char**"}) @ByPtrPtr ByteBuffer var2);

    @NoException
    public static native int av_strstart(String var0, String var1, @Cast(value={"const char**"}) @ByPtrPtr byte[] var2);

    @NoException
    public static native int av_stristart(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char**"}) PointerPointer var2);

    @NoException
    public static native int av_stristart(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char**"}) @ByPtrPtr BytePointer var2);

    @NoException
    public static native int av_stristart(String var0, String var1, @Cast(value={"const char**"}) @ByPtrPtr ByteBuffer var2);

    @NoException
    public static native int av_stristart(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char**"}) @ByPtrPtr byte[] var2);

    @NoException
    public static native int av_stristart(String var0, String var1, @Cast(value={"const char**"}) @ByPtrPtr BytePointer var2);

    @NoException
    public static native int av_stristart(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char**"}) @ByPtrPtr ByteBuffer var2);

    @NoException
    public static native int av_stristart(String var0, String var1, @Cast(value={"const char**"}) @ByPtrPtr byte[] var2);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_stristr(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_stristr(String var0, String var1);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_strnstr(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_strnstr(String var0, String var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcpy(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcpy(@Cast(value={"char*"}) ByteBuffer var0, String var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcpy(@Cast(value={"char*"}) byte[] var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcpy(@Cast(value={"char*"}) BytePointer var0, String var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcpy(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcpy(@Cast(value={"char*"}) byte[] var0, String var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcat(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcat(@Cast(value={"char*"}) ByteBuffer var0, String var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcat(@Cast(value={"char*"}) byte[] var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcat(@Cast(value={"char*"}) BytePointer var0, String var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcat(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcat(@Cast(value={"char*"}) byte[] var0, String var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcatf(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"size_t"}) long var1, @Cast(value={"const char*"}) BytePointer var3);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcatf(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"size_t"}) long var1, String var3);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcatf(@Cast(value={"char*"}) byte[] var0, @Cast(value={"size_t"}) long var1, @Cast(value={"const char*"}) BytePointer var3);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcatf(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"size_t"}) long var1, String var3);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcatf(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"size_t"}) long var1, @Cast(value={"const char*"}) BytePointer var3);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strlcatf(@Cast(value={"char*"}) byte[] var0, @Cast(value={"size_t"}) long var1, String var3);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strnlen(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_strnlen(String var0, @Cast(value={"size_t"}) long var1);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_asprintf(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_asprintf(String var0);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_get_token(@Cast(value={"const char**"}) PointerPointer var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_get_token(@Cast(value={"const char**"}) @ByPtrPtr BytePointer var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_get_token(@Cast(value={"const char**"}) @ByPtrPtr ByteBuffer var0, String var1);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_get_token(@Cast(value={"const char**"}) @ByPtrPtr byte[] var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_get_token(@Cast(value={"const char**"}) @ByPtrPtr BytePointer var0, String var1);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_get_token(@Cast(value={"const char**"}) @ByPtrPtr ByteBuffer var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_get_token(@Cast(value={"const char**"}) @ByPtrPtr byte[] var0, String var1);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_strtok(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"char**"}) PointerPointer var2);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_strtok(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"char**"}) @ByPtrPtr BytePointer var2);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_strtok(@Cast(value={"char*"}) ByteBuffer var0, String var1, @Cast(value={"char**"}) @ByPtrPtr ByteBuffer var2);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_strtok(@Cast(value={"char*"}) byte[] var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"char**"}) @ByPtrPtr byte[] var2);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_strtok(@Cast(value={"char*"}) BytePointer var0, String var1, @Cast(value={"char**"}) @ByPtrPtr BytePointer var2);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_strtok(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"char**"}) @ByPtrPtr ByteBuffer var2);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_strtok(@Cast(value={"char*"}) byte[] var0, String var1, @Cast(value={"char**"}) @ByPtrPtr byte[] var2);

    @NoException
    @Const
    public static native int av_isdigit(int var0);

    @NoException
    @Const
    public static native int av_isgraph(int var0);

    @NoException
    @Const
    public static native int av_isspace(int var0);

    @NoException
    @Const
    public static native int av_toupper(int var0);

    @NoException
    @Const
    public static native int av_tolower(int var0);

    @NoException
    @Const
    public static native int av_isxdigit(int var0);

    @NoException
    public static native int av_strcasecmp(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    public static native int av_strcasecmp(String var0, String var1);

    @NoException
    public static native int av_strncasecmp(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int av_strncasecmp(String var0, String var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_strireplace(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_strireplace(String var0, String var1, String var2);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_basename(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    public static native String av_basename(String var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_dirname(@Cast(value={"char*"}) BytePointer var0);

    @NoException
    public static native String av_dirname(@Cast(value={"char*"}) ByteBuffer var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_dirname(@Cast(value={"char*"}) byte[] var0);

    @NoException
    public static native int av_match_name(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    public static native int av_match_name(String var0, String var1);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_append_path_component(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_append_path_component(String var0, String var1);

    @NoException
    public static native int av_escape(@Cast(value={"char**"}) PointerPointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"AVEscapeMode"}) int var3, int var4);

    @NoException
    public static native int av_escape(@Cast(value={"char**"}) @ByPtrPtr BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"AVEscapeMode"}) int var3, int var4);

    @NoException
    public static native int av_escape(@Cast(value={"char**"}) @ByPtrPtr ByteBuffer var0, String var1, String var2, @Cast(value={"AVEscapeMode"}) int var3, int var4);

    @NoException
    public static native int av_escape(@Cast(value={"char**"}) @ByPtrPtr byte[] var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"AVEscapeMode"}) int var3, int var4);

    @NoException
    public static native int av_escape(@Cast(value={"char**"}) @ByPtrPtr BytePointer var0, String var1, String var2, @Cast(value={"AVEscapeMode"}) int var3, int var4);

    @NoException
    public static native int av_escape(@Cast(value={"char**"}) @ByPtrPtr ByteBuffer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"AVEscapeMode"}) int var3, int var4);

    @NoException
    public static native int av_escape(@Cast(value={"char**"}) @ByPtrPtr byte[] var0, String var1, String var2, @Cast(value={"AVEscapeMode"}) int var3, int var4);

    @NoException
    public static native int av_utf8_decode(IntPointer var0, @Cast(value={"const uint8_t**"}) PointerPointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    public static native int av_utf8_decode(IntPointer var0, @Cast(value={"const uint8_t**"}) @ByPtrPtr BytePointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    public static native int av_utf8_decode(IntBuffer var0, @Cast(value={"const uint8_t**"}) @ByPtrPtr ByteBuffer var1, @Cast(value={"const uint8_t*"}) ByteBuffer var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    public static native int av_utf8_decode(int[] var0, @Cast(value={"const uint8_t**"}) @ByPtrPtr byte[] var1, @Cast(value={"const uint8_t*"}) byte[] var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    public static native int av_match_list(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"char"}) byte var2);

    @NoException
    public static native int av_match_list(String var0, String var1, @Cast(value={"char"}) byte var2);

    @NoException
    public static native int av_sscanf(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    public static native int av_sscanf(String var0, String var1);

    @NoException
    public static native void av_bprint_init(AVBPrint var0, @Cast(value={"unsigned"}) int var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native void av_bprint_init_for_buffer(AVBPrint var0, @Cast(value={"char*"}) BytePointer var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native void av_bprint_init_for_buffer(AVBPrint var0, @Cast(value={"char*"}) ByteBuffer var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native void av_bprint_init_for_buffer(AVBPrint var0, @Cast(value={"char*"}) byte[] var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native void av_bprintf(AVBPrint var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    public static native void av_bprintf(AVBPrint var0, String var1);

    @NoException
    public static native void av_vbprintf(AVBPrint var0, @Cast(value={"const char*"}) BytePointer var1, @ByVal @Cast(value={"va_list*"}) Pointer var2);

    @NoException
    public static native void av_vbprintf(AVBPrint var0, String var1, @ByVal @Cast(value={"va_list*"}) Pointer var2);

    @NoException
    public static native void av_bprint_chars(AVBPrint var0, @Cast(value={"char"}) byte var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native void av_bprint_append_data(AVBPrint var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native void av_bprint_append_data(AVBPrint var0, String var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native void av_bprint_strftime(AVBPrint var0, @Cast(value={"const char*"}) BytePointer var1, @Const tm var2);

    @NoException
    public static native void av_bprint_strftime(AVBPrint var0, String var1, @Const tm var2);

    @NoException
    public static native void av_bprint_get_buffer(AVBPrint var0, @Cast(value={"unsigned"}) int var1, @Cast(value={"unsigned char**"}) PointerPointer var2, @Cast(value={"unsigned*"}) IntPointer var3);

    @NoException
    public static native void av_bprint_get_buffer(AVBPrint var0, @Cast(value={"unsigned"}) int var1, @Cast(value={"unsigned char**"}) @ByPtrPtr BytePointer var2, @Cast(value={"unsigned*"}) IntPointer var3);

    @NoException
    public static native void av_bprint_get_buffer(AVBPrint var0, @Cast(value={"unsigned"}) int var1, @Cast(value={"unsigned char**"}) @ByPtrPtr ByteBuffer var2, @Cast(value={"unsigned*"}) IntBuffer var3);

    @NoException
    public static native void av_bprint_get_buffer(AVBPrint var0, @Cast(value={"unsigned"}) int var1, @Cast(value={"unsigned char**"}) @ByPtrPtr byte[] var2, @Cast(value={"unsigned*"}) int[] var3);

    @NoException
    public static native void av_bprint_clear(AVBPrint var0);

    @NoException
    public static native int av_bprint_is_complete(@Const AVBPrint var0);

    @NoException
    public static native int av_bprint_finalize(AVBPrint var0, @Cast(value={"char**"}) PointerPointer var1);

    @NoException
    public static native int av_bprint_finalize(AVBPrint var0, @Cast(value={"char**"}) @ByPtrPtr BytePointer var1);

    @NoException
    public static native int av_bprint_finalize(AVBPrint var0, @Cast(value={"char**"}) @ByPtrPtr ByteBuffer var1);

    @NoException
    public static native int av_bprint_finalize(AVBPrint var0, @Cast(value={"char**"}) @ByPtrPtr byte[] var1);

    @NoException
    public static native void av_bprint_escape(AVBPrint var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"AVEscapeMode"}) int var3, int var4);

    @NoException
    public static native void av_bprint_escape(AVBPrint var0, String var1, String var2, @Cast(value={"AVEscapeMode"}) int var3, int var4);

    @NoException
    @Const
    public static native int av_log2(@Cast(value={"unsigned"}) int var0);

    @NoException
    @Const
    public static native int av_log2_16bit(@Cast(value={"unsigned"}) int var0);

    @NoException
    @Const
    public static native int av_clip_c(int var0, int var1, int var2);

    @NoException
    @Cast(value={"int64_t"})
    @Const
    public static native long av_clip64_c(@Cast(value={"int64_t"}) long var0, @Cast(value={"int64_t"}) long var2, @Cast(value={"int64_t"}) long var4);

    @NoException
    @Cast(value={"uint8_t"})
    @Const
    public static native byte av_clip_uint8_c(int var0);

    @NoException
    @Const
    public static native byte av_clip_int8_c(int var0);

    @NoException
    @Cast(value={"uint16_t"})
    @Const
    public static native short av_clip_uint16_c(int var0);

    @NoException
    @Const
    public static native short av_clip_int16_c(int var0);

    @NoException
    @Const
    public static native int av_clipl_int32_c(@Cast(value={"int64_t"}) long var0);

    @NoException
    @Const
    public static native int av_clip_intp2_c(int var0, int var1);

    @NoException
    @Cast(value={"unsigned"})
    @Const
    public static native int av_clip_uintp2_c(int var0, int var1);

    @NoException
    @Cast(value={"unsigned"})
    @Const
    public static native int av_zero_extend_c(@Cast(value={"unsigned"}) int var0, @Cast(value={"unsigned"}) int var1);

    @NoException
    @Cast(value={"unsigned"})
    @Deprecated
    @Const
    public static native int av_mod_uintp2_c(@Cast(value={"unsigned"}) int var0, @Cast(value={"unsigned"}) int var1);

    @NoException
    public static native int av_sat_add32_c(int var0, int var1);

    @NoException
    public static native int av_sat_dadd32_c(int var0, int var1);

    @NoException
    public static native int av_sat_sub32_c(int var0, int var1);

    @NoException
    public static native int av_sat_dsub32_c(int var0, int var1);

    @NoException
    @Cast(value={"int64_t"})
    public static native long av_sat_add64_c(@Cast(value={"int64_t"}) long var0, @Cast(value={"int64_t"}) long var2);

    @NoException
    @Cast(value={"int64_t"})
    public static native long av_sat_sub64_c(@Cast(value={"int64_t"}) long var0, @Cast(value={"int64_t"}) long var2);

    @NoException
    @Const
    public static native float av_clipf_c(float var0, float var1, float var2);

    @NoException
    @Const
    public static native double av_clipd_c(double var0, double var2, double var4);

    @NoException
    @Const
    public static native int av_ceil_log2_c(int var0);

    @NoException
    @Const
    public static native int av_popcount_c(@Cast(value={"uint32_t"}) int var0);

    @NoException
    @Const
    public static native int av_popcount64_c(@Cast(value={"uint64_t"}) long var0);

    @NoException
    @Const
    public static native int av_parity_c(@Cast(value={"uint32_t"}) int var0);

    @NoException
    public static native double av_display_rotation_get(@Const IntPointer var0);

    @NoException
    public static native double av_display_rotation_get(@Const IntBuffer var0);

    @NoException
    public static native double av_display_rotation_get(@Const int[] var0);

    @NoException
    public static native void av_display_rotation_set(IntPointer var0, double var1);

    @NoException
    public static native void av_display_rotation_set(IntBuffer var0, double var1);

    @NoException
    public static native void av_display_rotation_set(int[] var0, double var1);

    @NoException
    public static native void av_display_matrix_flip(IntPointer var0, int var1, int var2);

    @NoException
    public static native void av_display_matrix_flip(IntBuffer var0, int var1, int var2);

    @NoException
    public static native void av_display_matrix_flip(int[] var0, int var1, int var2);

    @NoException
    public static native int av_expr_parse_and_eval(DoublePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*const*"}) PointerPointer var2, @Const DoublePointer var3, @Cast(value={"const char*const*"}) PointerPointer var4, @Cast(value={"double (**)(void*, double)"}) PointerPointer var5, @Cast(value={"const char*const*"}) PointerPointer var6, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var7, Pointer var8, int var9, Pointer var10);

    @NoException
    public static native int av_expr_parse_and_eval(DoublePointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var2, @Const DoublePointer var3, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var4, @Cast(value={"double (**)(void*, double)"}) PointerPointer var5, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var6, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var7, Pointer var8, int var9, Pointer var10);

    @NoException
    public static native int av_expr_parse_and_eval(DoubleBuffer var0, String var1, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var2, @Const DoubleBuffer var3, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var4, @Cast(value={"double (**)(void*, double)"}) PointerPointer var5, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var6, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var7, Pointer var8, int var9, Pointer var10);

    @NoException
    public static native int av_expr_parse_and_eval(double[] var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var2, @Const double[] var3, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var4, @Cast(value={"double (**)(void*, double)"}) PointerPointer var5, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var6, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var7, Pointer var8, int var9, Pointer var10);

    @NoException
    public static native int av_expr_parse_and_eval(DoublePointer var0, String var1, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var2, @Const DoublePointer var3, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var4, @Cast(value={"double (**)(void*, double)"}) PointerPointer var5, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var6, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var7, Pointer var8, int var9, Pointer var10);

    @NoException
    public static native int av_expr_parse_and_eval(DoubleBuffer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var2, @Const DoubleBuffer var3, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var4, @Cast(value={"double (**)(void*, double)"}) PointerPointer var5, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var6, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var7, Pointer var8, int var9, Pointer var10);

    @NoException
    public static native int av_expr_parse_and_eval(double[] var0, String var1, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var2, @Const double[] var3, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var4, @Cast(value={"double (**)(void*, double)"}) PointerPointer var5, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var6, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var7, Pointer var8, int var9, Pointer var10);

    @NoException
    public static native int av_expr_parse(@Cast(value={"AVExpr**"}) PointerPointer var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*const*"}) PointerPointer var2, @Cast(value={"const char*const*"}) PointerPointer var3, @Cast(value={"double (**)(void*, double)"}) PointerPointer var4, @Cast(value={"const char*const*"}) PointerPointer var5, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var6, int var7, Pointer var8);

    @NoException
    public static native int av_expr_parse(@ByPtrPtr AVExpr var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var2, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var3, @Cast(value={"double (**)(void*, double)"}) PointerPointer var4, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var5, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var6, int var7, Pointer var8);

    @NoException
    public static native int av_expr_parse(@ByPtrPtr AVExpr var0, String var1, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var2, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var3, @Cast(value={"double (**)(void*, double)"}) PointerPointer var4, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var5, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var6, int var7, Pointer var8);

    @NoException
    public static native int av_expr_parse(@ByPtrPtr AVExpr var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var2, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var3, @Cast(value={"double (**)(void*, double)"}) PointerPointer var4, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var5, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var6, int var7, Pointer var8);

    @NoException
    public static native int av_expr_parse(@ByPtrPtr AVExpr var0, String var1, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var2, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var3, @Cast(value={"double (**)(void*, double)"}) PointerPointer var4, @Cast(value={"const char*const*"}) @ByPtrPtr BytePointer var5, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var6, int var7, Pointer var8);

    @NoException
    public static native int av_expr_parse(@ByPtrPtr AVExpr var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var2, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var3, @Cast(value={"double (**)(void*, double)"}) PointerPointer var4, @Cast(value={"const char*const*"}) @ByPtrPtr ByteBuffer var5, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var6, int var7, Pointer var8);

    @NoException
    public static native int av_expr_parse(@ByPtrPtr AVExpr var0, String var1, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var2, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var3, @Cast(value={"double (**)(void*, double)"}) PointerPointer var4, @Cast(value={"const char*const*"}) @ByPtrPtr byte[] var5, @Cast(value={"double (**)(void*, double, double)"}) PointerPointer var6, int var7, Pointer var8);

    @NoException
    public static native double av_expr_eval(AVExpr var0, @Const DoublePointer var1, Pointer var2);

    @NoException
    public static native double av_expr_eval(AVExpr var0, @Const DoubleBuffer var1, Pointer var2);

    @NoException
    public static native double av_expr_eval(AVExpr var0, @Const double[] var1, Pointer var2);

    @NoException
    public static native int av_expr_count_vars(AVExpr var0, @Cast(value={"unsigned*"}) IntPointer var1, int var2);

    @NoException
    public static native int av_expr_count_vars(AVExpr var0, @Cast(value={"unsigned*"}) IntBuffer var1, int var2);

    @NoException
    public static native int av_expr_count_vars(AVExpr var0, @Cast(value={"unsigned*"}) int[] var1, int var2);

    @NoException
    public static native int av_expr_count_func(AVExpr var0, @Cast(value={"unsigned*"}) IntPointer var1, int var2, int var3);

    @NoException
    public static native int av_expr_count_func(AVExpr var0, @Cast(value={"unsigned*"}) IntBuffer var1, int var2, int var3);

    @NoException
    public static native int av_expr_count_func(AVExpr var0, @Cast(value={"unsigned*"}) int[] var1, int var2, int var3);

    @NoException
    public static native void av_expr_free(AVExpr var0);

    @NoException
    public static native double av_strtod(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"char**"}) PointerPointer var1);

    @NoException
    public static native double av_strtod(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"char**"}) @ByPtrPtr BytePointer var1);

    @NoException
    public static native double av_strtod(String var0, @Cast(value={"char**"}) @ByPtrPtr ByteBuffer var1);

    @NoException
    public static native double av_strtod(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"char**"}) @ByPtrPtr byte[] var1);

    @NoException
    public static native double av_strtod(String var0, @Cast(value={"char**"}) @ByPtrPtr BytePointer var1);

    @NoException
    public static native double av_strtod(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"char**"}) @ByPtrPtr ByteBuffer var1);

    @NoException
    public static native double av_strtod(String var0, @Cast(value={"char**"}) @ByPtrPtr byte[] var1);

    @NoException
    public static native AVEncryptionInfo av_encryption_info_alloc(@Cast(value={"uint32_t"}) int var0, @Cast(value={"uint32_t"}) int var1, @Cast(value={"uint32_t"}) int var2);

    @NoException
    public static native AVEncryptionInfo av_encryption_info_clone(@Const AVEncryptionInfo var0);

    @NoException
    public static native void av_encryption_info_free(AVEncryptionInfo var0);

    @NoException
    public static native AVEncryptionInfo av_encryption_info_get_side_data(@Cast(value={"const uint8_t*"}) BytePointer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native AVEncryptionInfo av_encryption_info_get_side_data(@Cast(value={"const uint8_t*"}) ByteBuffer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native AVEncryptionInfo av_encryption_info_get_side_data(@Cast(value={"const uint8_t*"}) byte[] var0, @Cast(value={"size_t"}) long var1);

    @NoException
    @Cast(value={"uint8_t*"})
    public static native BytePointer av_encryption_info_add_side_data(@Const AVEncryptionInfo var0, @Cast(value={"size_t*"}) SizeTPointer var1);

    @NoException
    public static native AVEncryptionInitInfo av_encryption_init_info_alloc(@Cast(value={"uint32_t"}) int var0, @Cast(value={"uint32_t"}) int var1, @Cast(value={"uint32_t"}) int var2, @Cast(value={"uint32_t"}) int var3);

    @NoException
    public static native void av_encryption_init_info_free(AVEncryptionInitInfo var0);

    @NoException
    public static native AVEncryptionInitInfo av_encryption_init_info_get_side_data(@Cast(value={"const uint8_t*"}) BytePointer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native AVEncryptionInitInfo av_encryption_init_info_get_side_data(@Cast(value={"const uint8_t*"}) ByteBuffer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native AVEncryptionInitInfo av_encryption_init_info_get_side_data(@Cast(value={"const uint8_t*"}) byte[] var0, @Cast(value={"size_t"}) long var1);

    @NoException
    @Cast(value={"uint8_t*"})
    public static native BytePointer av_encryption_init_info_add_side_data(@Const AVEncryptionInitInfo var0, @Cast(value={"size_t*"}) SizeTPointer var1);

    @NoException
    public static native int av_file_map(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"uint8_t**"}) PointerPointer var1, @Cast(value={"size_t*"}) SizeTPointer var2, int var3, Pointer var4);

    @NoException
    public static native int av_file_map(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"uint8_t**"}) @ByPtrPtr BytePointer var1, @Cast(value={"size_t*"}) SizeTPointer var2, int var3, Pointer var4);

    @NoException
    public static native int av_file_map(String var0, @Cast(value={"uint8_t**"}) @ByPtrPtr ByteBuffer var1, @Cast(value={"size_t*"}) SizeTPointer var2, int var3, Pointer var4);

    @NoException
    public static native int av_file_map(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"uint8_t**"}) @ByPtrPtr byte[] var1, @Cast(value={"size_t*"}) SizeTPointer var2, int var3, Pointer var4);

    @NoException
    public static native int av_file_map(String var0, @Cast(value={"uint8_t**"}) @ByPtrPtr BytePointer var1, @Cast(value={"size_t*"}) SizeTPointer var2, int var3, Pointer var4);

    @NoException
    public static native int av_file_map(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"uint8_t**"}) @ByPtrPtr ByteBuffer var1, @Cast(value={"size_t*"}) SizeTPointer var2, int var3, Pointer var4);

    @NoException
    public static native int av_file_map(String var0, @Cast(value={"uint8_t**"}) @ByPtrPtr byte[] var1, @Cast(value={"size_t*"}) SizeTPointer var2, int var3, Pointer var4);

    @NoException
    public static native void av_file_unmap(@Cast(value={"uint8_t*"}) BytePointer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native void av_file_unmap(@Cast(value={"uint8_t*"}) ByteBuffer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native void av_file_unmap(@Cast(value={"uint8_t*"}) byte[] var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native int av_hash_alloc(@Cast(value={"AVHashContext**"}) PointerPointer var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    public static native int av_hash_alloc(@ByPtrPtr AVHashContext var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    public static native int av_hash_alloc(@ByPtrPtr AVHashContext var0, String var1);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_hash_names(int var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_hash_get_name(@Const AVHashContext var0);

    @NoException
    public static native int av_hash_get_size(@Const AVHashContext var0);

    @NoException
    public static native void av_hash_init(AVHashContext var0);

    @NoException
    public static native void av_hash_update(AVHashContext var0, @Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_hash_update(AVHashContext var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_hash_update(AVHashContext var0, @Cast(value={"const uint8_t*"}) byte[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_hash_final(AVHashContext var0, @Cast(value={"uint8_t*"}) BytePointer var1);

    @NoException
    public static native void av_hash_final(AVHashContext var0, @Cast(value={"uint8_t*"}) ByteBuffer var1);

    @NoException
    public static native void av_hash_final(AVHashContext var0, @Cast(value={"uint8_t*"}) byte[] var1);

    @NoException
    public static native void av_hash_final_bin(AVHashContext var0, @Cast(value={"uint8_t*"}) BytePointer var1, int var2);

    @NoException
    public static native void av_hash_final_bin(AVHashContext var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, int var2);

    @NoException
    public static native void av_hash_final_bin(AVHashContext var0, @Cast(value={"uint8_t*"}) byte[] var1, int var2);

    @NoException
    public static native void av_hash_final_hex(AVHashContext var0, @Cast(value={"uint8_t*"}) BytePointer var1, int var2);

    @NoException
    public static native void av_hash_final_hex(AVHashContext var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, int var2);

    @NoException
    public static native void av_hash_final_hex(AVHashContext var0, @Cast(value={"uint8_t*"}) byte[] var1, int var2);

    @NoException
    public static native void av_hash_final_b64(AVHashContext var0, @Cast(value={"uint8_t*"}) BytePointer var1, int var2);

    @NoException
    public static native void av_hash_final_b64(AVHashContext var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, int var2);

    @NoException
    public static native void av_hash_final_b64(AVHashContext var0, @Cast(value={"uint8_t*"}) byte[] var1, int var2);

    @NoException
    public static native void av_hash_freep(@Cast(value={"AVHashContext**"}) PointerPointer var0);

    @NoException
    public static native void av_hash_freep(@ByPtrPtr AVHashContext var0);

    @NoException
    public static native AVDynamicHDRPlus av_dynamic_hdr_plus_alloc(@Cast(value={"size_t*"}) SizeTPointer var0);

    @NoException
    public static native AVDynamicHDRPlus av_dynamic_hdr_plus_create_side_data(AVFrame var0);

    @NoException
    public static native int av_dynamic_hdr_plus_from_t35(AVDynamicHDRPlus var0, @Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int av_dynamic_hdr_plus_from_t35(AVDynamicHDRPlus var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int av_dynamic_hdr_plus_from_t35(AVDynamicHDRPlus var0, @Cast(value={"const uint8_t*"}) byte[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int av_dynamic_hdr_plus_to_t35(@Const AVDynamicHDRPlus var0, @Cast(value={"uint8_t**"}) PointerPointer var1, @Cast(value={"size_t*"}) SizeTPointer var2);

    @NoException
    public static native int av_dynamic_hdr_plus_to_t35(@Const AVDynamicHDRPlus var0, @Cast(value={"uint8_t**"}) @ByPtrPtr BytePointer var1, @Cast(value={"size_t*"}) SizeTPointer var2);

    @NoException
    public static native int av_dynamic_hdr_plus_to_t35(@Const AVDynamicHDRPlus var0, @Cast(value={"uint8_t**"}) @ByPtrPtr ByteBuffer var1, @Cast(value={"size_t*"}) SizeTPointer var2);

    @NoException
    public static native int av_dynamic_hdr_plus_to_t35(@Const AVDynamicHDRPlus var0, @Cast(value={"uint8_t**"}) @ByPtrPtr byte[] var1, @Cast(value={"size_t*"}) SizeTPointer var2);

    @NoException
    public static native float av_int2float(@Cast(value={"uint32_t"}) int var0);

    @NoException
    @Cast(value={"uint32_t"})
    public static native int av_float2int(float var0);

    @NoException
    public static native double av_int2double(@Cast(value={"uint64_t"}) long var0);

    @NoException
    @Cast(value={"uint64_t"})
    public static native long av_double2int(double var0);

    @NoException
    public static native AVMasteringDisplayMetadata av_mastering_display_metadata_alloc();

    @NoException
    public static native AVMasteringDisplayMetadata av_mastering_display_metadata_alloc_size(@Cast(value={"size_t*"}) SizeTPointer var0);

    @NoException
    public static native AVMasteringDisplayMetadata av_mastering_display_metadata_create_side_data(AVFrame var0);

    @NoException
    public static native AVContentLightMetadata av_content_light_metadata_alloc(@Cast(value={"size_t*"}) SizeTPointer var0);

    @NoException
    public static native AVContentLightMetadata av_content_light_metadata_create_side_data(AVFrame var0);

    @NoException
    @Cast(value={"AVMurMur3*"})
    public static native Pointer av_murmur3_alloc();

    @NoException
    public static native void av_murmur3_init_seeded(@Cast(value={"AVMurMur3*"}) Pointer var0, @Cast(value={"uint64_t"}) long var1);

    @NoException
    public static native void av_murmur3_init(@Cast(value={"AVMurMur3*"}) Pointer var0);

    @NoException
    public static native void av_murmur3_update(@Cast(value={"AVMurMur3*"}) Pointer var0, @Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_murmur3_update(@Cast(value={"AVMurMur3*"}) Pointer var0, @Cast(value={"const uint8_t*"}) ByteBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_murmur3_update(@Cast(value={"AVMurMur3*"}) Pointer var0, @Cast(value={"const uint8_t*"}) byte[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_murmur3_final(@Cast(value={"AVMurMur3*"}) Pointer var0, @Cast(value={"uint8_t*"}) BytePointer var1);

    @NoException
    public static native void av_murmur3_final(@Cast(value={"AVMurMur3*"}) Pointer var0, @Cast(value={"uint8_t*"}) ByteBuffer var1);

    @NoException
    public static native void av_murmur3_final(@Cast(value={"AVMurMur3*"}) Pointer var0, @Cast(value={"uint8_t*"}) byte[] var1);

    @NoException
    public static native int av_parse_ratio(AVRational var0, @Cast(value={"const char*"}) BytePointer var1, int var2, int var3, Pointer var4);

    @NoException
    public static native int av_parse_ratio(AVRational var0, String var1, int var2, int var3, Pointer var4);

    @NoException
    public static native int av_parse_video_size(IntPointer var0, IntPointer var1, @Cast(value={"const char*"}) BytePointer var2);

    @NoException
    public static native int av_parse_video_size(IntBuffer var0, IntBuffer var1, String var2);

    @NoException
    public static native int av_parse_video_size(int[] var0, int[] var1, @Cast(value={"const char*"}) BytePointer var2);

    @NoException
    public static native int av_parse_video_size(IntPointer var0, IntPointer var1, String var2);

    @NoException
    public static native int av_parse_video_size(IntBuffer var0, IntBuffer var1, @Cast(value={"const char*"}) BytePointer var2);

    @NoException
    public static native int av_parse_video_size(int[] var0, int[] var1, String var2);

    @NoException
    public static native int av_parse_video_rate(AVRational var0, @Cast(value={"const char*"}) BytePointer var1);

    @NoException
    public static native int av_parse_video_rate(AVRational var0, String var1);

    @NoException
    public static native int av_parse_color(@Cast(value={"uint8_t*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, Pointer var3);

    @NoException
    public static native int av_parse_color(@Cast(value={"uint8_t*"}) ByteBuffer var0, String var1, int var2, Pointer var3);

    @NoException
    public static native int av_parse_color(@Cast(value={"uint8_t*"}) byte[] var0, @Cast(value={"const char*"}) BytePointer var1, int var2, Pointer var3);

    @NoException
    public static native int av_parse_color(@Cast(value={"uint8_t*"}) BytePointer var0, String var1, int var2, Pointer var3);

    @NoException
    public static native int av_parse_color(@Cast(value={"uint8_t*"}) ByteBuffer var0, @Cast(value={"const char*"}) BytePointer var1, int var2, Pointer var3);

    @NoException
    public static native int av_parse_color(@Cast(value={"uint8_t*"}) byte[] var0, String var1, int var2, Pointer var3);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_get_known_color_name(int var0, @Cast(value={"const uint8_t**"}) PointerPointer var1);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_get_known_color_name(int var0, @Cast(value={"const uint8_t**"}) @ByPtrPtr BytePointer var1);

    @NoException
    public static native String av_get_known_color_name(int var0, @Cast(value={"const uint8_t**"}) @ByPtrPtr ByteBuffer var1);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_get_known_color_name(int var0, @Cast(value={"const uint8_t**"}) @ByPtrPtr byte[] var1);

    @NoException
    public static native int av_parse_time(@Cast(value={"int64_t*"}) LongPointer var0, @Cast(value={"const char*"}) BytePointer var1, int var2);

    @NoException
    public static native int av_parse_time(@Cast(value={"int64_t*"}) LongBuffer var0, String var1, int var2);

    @NoException
    public static native int av_parse_time(@Cast(value={"int64_t*"}) long[] var0, @Cast(value={"const char*"}) BytePointer var1, int var2);

    @NoException
    public static native int av_parse_time(@Cast(value={"int64_t*"}) LongPointer var0, String var1, int var2);

    @NoException
    public static native int av_parse_time(@Cast(value={"int64_t*"}) LongBuffer var0, @Cast(value={"const char*"}) BytePointer var1, int var2);

    @NoException
    public static native int av_parse_time(@Cast(value={"int64_t*"}) long[] var0, String var1, int var2);

    @NoException
    public static native int av_find_info_tag(@Cast(value={"char*"}) BytePointer var0, int var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"const char*"}) BytePointer var3);

    @NoException
    public static native int av_find_info_tag(@Cast(value={"char*"}) ByteBuffer var0, int var1, String var2, String var3);

    @NoException
    public static native int av_find_info_tag(@Cast(value={"char*"}) byte[] var0, int var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"const char*"}) BytePointer var3);

    @NoException
    public static native int av_find_info_tag(@Cast(value={"char*"}) BytePointer var0, int var1, String var2, String var3);

    @NoException
    public static native int av_find_info_tag(@Cast(value={"char*"}) ByteBuffer var0, int var1, @Cast(value={"const char*"}) BytePointer var2, @Cast(value={"const char*"}) BytePointer var3);

    @NoException
    public static native int av_find_info_tag(@Cast(value={"char*"}) byte[] var0, int var1, String var2, String var3);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_small_strptime(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"const char*"}) BytePointer var1, tm var2);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_small_strptime(String var0, String var1, tm var2);

    @NoException
    @ByVal
    @Cast(value={"time_t*"})
    public static native Pointer av_timegm(tm var0);

    @NoException
    public static native av_pixelutils_sad_fn av_pixelutils_get_sad_fn(int var0, int var1, int var2, Pointer var3);

    @NoException
    @Cast(value={"uint32_t"})
    public static native int av_get_random_seed();

    @NoException
    public static native int av_random_bytes(@Cast(value={"uint8_t*"}) BytePointer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native int av_random_bytes(@Cast(value={"uint8_t*"}) ByteBuffer var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native int av_random_bytes(@Cast(value={"uint8_t*"}) byte[] var0, @Cast(value={"size_t"}) long var1);

    @NoException
    public static native AVSphericalMapping av_spherical_alloc(@Cast(value={"size_t*"}) SizeTPointer var0);

    @NoException
    public static native void av_spherical_tile_bounds(@Const AVSphericalMapping var0, @Cast(value={"size_t"}) long var1, @Cast(value={"size_t"}) long var3, @Cast(value={"size_t*"}) SizeTPointer var5, @Cast(value={"size_t*"}) SizeTPointer var6, @Cast(value={"size_t*"}) SizeTPointer var7, @Cast(value={"size_t*"}) SizeTPointer var8);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_spherical_projection_name(@Cast(value={"AVSphericalProjection"}) int var0);

    @NoException
    public static native int av_spherical_from_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    public static native int av_spherical_from_name(String var0);

    @NoException
    public static native int av_thread_message_queue_alloc(@Cast(value={"AVThreadMessageQueue**"}) PointerPointer var0, @Cast(value={"unsigned"}) int var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native int av_thread_message_queue_alloc(@ByPtrPtr AVThreadMessageQueue var0, @Cast(value={"unsigned"}) int var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native void av_thread_message_queue_free(@Cast(value={"AVThreadMessageQueue**"}) PointerPointer var0);

    @NoException
    public static native void av_thread_message_queue_free(@ByPtrPtr AVThreadMessageQueue var0);

    @NoException
    public static native int av_thread_message_queue_send(AVThreadMessageQueue var0, Pointer var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native int av_thread_message_queue_recv(AVThreadMessageQueue var0, Pointer var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native void av_thread_message_queue_set_err_send(AVThreadMessageQueue var0, int var1);

    @NoException
    public static native void av_thread_message_queue_set_err_recv(AVThreadMessageQueue var0, int var1);

    @NoException
    public static native void av_thread_message_queue_set_free_func(AVThreadMessageQueue var0, Free_func_Pointer var1);

    @NoException
    public static native int av_thread_message_queue_nb_elems(AVThreadMessageQueue var0);

    @NoException
    public static native void av_thread_message_flush(AVThreadMessageQueue var0);

    @NoException
    public static native int av_timecode_adjust_ntsc_framenum2(int var0, int var1);

    @NoException
    @Cast(value={"uint32_t"})
    public static native int av_timecode_get_smpte_from_framenum(@Const AVTimecode var0, int var1);

    @NoException
    @Cast(value={"uint32_t"})
    public static native int av_timecode_get_smpte(@ByVal AVRational var0, int var1, int var2, int var3, int var4, int var5);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_timecode_make_string(@Const AVTimecode var0, @Cast(value={"char*"}) BytePointer var1, int var2);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_timecode_make_string(@Const AVTimecode var0, @Cast(value={"char*"}) ByteBuffer var1, int var2);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_timecode_make_string(@Const AVTimecode var0, @Cast(value={"char*"}) byte[] var1, int var2);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_timecode_make_smpte_tc_string2(@Cast(value={"char*"}) BytePointer var0, @ByVal AVRational var1, @Cast(value={"uint32_t"}) int var2, int var3, int var4);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_timecode_make_smpte_tc_string2(@Cast(value={"char*"}) ByteBuffer var0, @ByVal AVRational var1, @Cast(value={"uint32_t"}) int var2, int var3, int var4);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_timecode_make_smpte_tc_string2(@Cast(value={"char*"}) byte[] var0, @ByVal AVRational var1, @Cast(value={"uint32_t"}) int var2, int var3, int var4);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_timecode_make_smpte_tc_string(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"uint32_t"}) int var1, int var2);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_timecode_make_smpte_tc_string(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"uint32_t"}) int var1, int var2);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_timecode_make_smpte_tc_string(@Cast(value={"char*"}) byte[] var0, @Cast(value={"uint32_t"}) int var1, int var2);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_timecode_make_mpeg_tc_string(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"uint32_t"}) int var1);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_timecode_make_mpeg_tc_string(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"uint32_t"}) int var1);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_timecode_make_mpeg_tc_string(@Cast(value={"char*"}) byte[] var0, @Cast(value={"uint32_t"}) int var1);

    @NoException
    public static native int av_timecode_init(AVTimecode var0, @ByVal AVRational var1, int var2, int var3, Pointer var4);

    @NoException
    public static native int av_timecode_init_from_components(AVTimecode var0, @ByVal AVRational var1, int var2, int var3, int var4, int var5, int var6, Pointer var7);

    @NoException
    public static native int av_timecode_init_from_string(AVTimecode var0, @ByVal AVRational var1, @Cast(value={"const char*"}) BytePointer var2, Pointer var3);

    @NoException
    public static native int av_timecode_init_from_string(AVTimecode var0, @ByVal AVRational var1, String var2, Pointer var3);

    @NoException
    public static native int av_timecode_check_frame_rate(@ByVal AVRational var0);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_ts_make_string(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"int64_t"}) long var1);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_ts_make_string(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"int64_t"}) long var1);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_ts_make_string(@Cast(value={"char*"}) byte[] var0, @Cast(value={"int64_t"}) long var1);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_ts_make_time_string2(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"int64_t"}) long var1, @ByVal AVRational var3);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_ts_make_time_string2(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"int64_t"}) long var1, @ByVal AVRational var3);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_ts_make_time_string2(@Cast(value={"char*"}) byte[] var0, @Cast(value={"int64_t"}) long var1, @ByVal AVRational var3);

    @NoException
    @Cast(value={"char*"})
    public static native BytePointer av_ts_make_time_string(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"int64_t"}) long var1, @Const AVRational var3);

    @NoException
    @Cast(value={"char*"})
    public static native ByteBuffer av_ts_make_time_string(@Cast(value={"char*"}) ByteBuffer var0, @Cast(value={"int64_t"}) long var1, @Const AVRational var3);

    @NoException
    @Cast(value={"char*"})
    public static native byte[] av_ts_make_time_string(@Cast(value={"char*"}) byte[] var0, @Cast(value={"int64_t"}) long var1, @Const AVRational var3);

    @MemberGetter
    public static native int av_tree_node_size();

    @NoException
    public static native AVTreeNode av_tree_node_alloc();

    @NoException
    public static native Pointer av_tree_find(@Const AVTreeNode var0, Pointer var1, @Cast(value={"int (*)(const void*, const void*)"}) Cmp_Const_Pointer_Const_Pointer var2, @Cast(value={"void**"}) PointerPointer var3);

    @NoException
    public static native Pointer av_tree_find(@Const AVTreeNode var0, Pointer var1, @Cast(value={"int (*)(const void*, const void*)"}) Cmp_Const_Pointer_Const_Pointer var2, @Cast(value={"void**"}) @ByPtrPtr Pointer var3);

    @NoException
    public static native Pointer av_tree_insert(@Cast(value={"AVTreeNode**"}) PointerPointer var0, Pointer var1, @Cast(value={"int (*)(const void*, const void*)"}) Cmp_Const_Pointer_Const_Pointer var2, @Cast(value={"AVTreeNode**"}) PointerPointer var3);

    @NoException
    public static native Pointer av_tree_insert(@ByPtrPtr AVTreeNode var0, Pointer var1, @Cast(value={"int (*)(const void*, const void*)"}) Cmp_Const_Pointer_Const_Pointer var2, @ByPtrPtr AVTreeNode var3);

    @NoException
    public static native void av_tree_destroy(AVTreeNode var0);

    @NoException
    public static native void av_tree_enumerate(AVTreeNode var0, Pointer var1, Cmp_Pointer_Pointer var2, Enu_Pointer_Pointer var3);

    @NoException
    public static native AVContainerFifo av_container_fifo_alloc(Pointer var0, Container_alloc_Pointer var1, Container_reset_Pointer_Pointer var2, Container_free_Pointer_Pointer var3, Fifo_transfer_Pointer_Pointer_Pointer_int var4, @Cast(value={"unsigned"}) int var5);

    @NoException
    public static native AVContainerFifo av_container_fifo_alloc_avframe(@Cast(value={"unsigned"}) int var0);

    @NoException
    public static native void av_container_fifo_free(@Cast(value={"AVContainerFifo**"}) PointerPointer var0);

    @NoException
    public static native void av_container_fifo_free(@ByPtrPtr AVContainerFifo var0);

    @NoException
    public static native int av_container_fifo_write(AVContainerFifo var0, Pointer var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native int av_container_fifo_read(AVContainerFifo var0, Pointer var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native int av_container_fifo_peek(AVContainerFifo var0, @Cast(value={"void**"}) PointerPointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int av_container_fifo_peek(AVContainerFifo var0, @Cast(value={"void**"}) @ByPtrPtr Pointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_container_fifo_drain(AVContainerFifo var0, @Cast(value={"size_t"}) long var1);

    @NoException
    @Cast(value={"size_t"})
    public static native long av_container_fifo_can_read(@Const AVContainerFifo var0);

    @NoException
    public static native int av_tx_init(@Cast(value={"AVTXContext**"}) PointerPointer var0, @ByPtrPtr av_tx_fn var1, @Cast(value={"AVTXType"}) int var2, int var3, int var4, @Const Pointer var5, @Cast(value={"uint64_t"}) long var6);

    @NoException
    public static native int av_tx_init(@ByPtrPtr AVTXContext var0, @ByPtrPtr av_tx_fn var1, @Cast(value={"AVTXType"}) int var2, int var3, int var4, @Const Pointer var5, @Cast(value={"uint64_t"}) long var6);

    @NoException
    public static native void av_tx_uninit(@Cast(value={"AVTXContext**"}) PointerPointer var0);

    @NoException
    public static native void av_tx_uninit(@ByPtrPtr AVTXContext var0);

    @MemberGetter
    public static native int LIBAVUTIL_VERSION_INT();

    @MemberGetter
    public static native String LIBAVUTIL_IDENT();

    public static native int MKTAG(@Cast(value={"char"}) byte var0, @Cast(value={"char"}) byte var1, @Cast(value={"char"}) byte var2, @Cast(value={"char"}) byte var3);

    public static native int MKBETAG(@Cast(value={"char"}) byte var0, @Cast(value={"char"}) byte var1, @Cast(value={"char"}) byte var2, @Cast(value={"char"}) byte var3);

    public static native LogCallback logCallback();

    public static native void logCallback(LogCallback var0);

    @NoException
    public static native void log_callback(Pointer var0, int var1, @Cast(value={"const char*"}) BytePointer var2, @ByVal @Cast(value={"va_list*"}) Pointer var3);

    @NoException
    public static native void log_callback(Pointer var0, int var1, String var2, @ByVal @Cast(value={"va_list*"}) Pointer var3);

    @NoException
    public static native void setLogCallback(LogCallback var0);

    static {
        Loader.load();
        AV_NOPTS_VALUE = avutil.AV_NOPTS_VALUE();
        AVERROR_BSF_NOT_FOUND = avutil.AVERROR_BSF_NOT_FOUND();
        AVERROR_BUG = avutil.AVERROR_BUG();
        AVERROR_BUFFER_TOO_SMALL = avutil.AVERROR_BUFFER_TOO_SMALL();
        AVERROR_DECODER_NOT_FOUND = avutil.AVERROR_DECODER_NOT_FOUND();
        AVERROR_DEMUXER_NOT_FOUND = avutil.AVERROR_DEMUXER_NOT_FOUND();
        AVERROR_ENCODER_NOT_FOUND = avutil.AVERROR_ENCODER_NOT_FOUND();
        AVERROR_EOF = avutil.AVERROR_EOF();
        AVERROR_EXIT = avutil.AVERROR_EXIT();
        AVERROR_EXTERNAL = avutil.AVERROR_EXTERNAL();
        AVERROR_FILTER_NOT_FOUND = avutil.AVERROR_FILTER_NOT_FOUND();
        AVERROR_INVALIDDATA = avutil.AVERROR_INVALIDDATA();
        AVERROR_MUXER_NOT_FOUND = avutil.AVERROR_MUXER_NOT_FOUND();
        AVERROR_OPTION_NOT_FOUND = avutil.AVERROR_OPTION_NOT_FOUND();
        AVERROR_PATCHWELCOME = avutil.AVERROR_PATCHWELCOME();
        AVERROR_PROTOCOL_NOT_FOUND = avutil.AVERROR_PROTOCOL_NOT_FOUND();
        AVERROR_STREAM_NOT_FOUND = avutil.AVERROR_STREAM_NOT_FOUND();
        AVERROR_BUG2 = avutil.AVERROR_BUG2();
        AVERROR_UNKNOWN = avutil.AVERROR_UNKNOWN();
        AVERROR_HTTP_BAD_REQUEST = avutil.AVERROR_HTTP_BAD_REQUEST();
        AVERROR_HTTP_UNAUTHORIZED = avutil.AVERROR_HTTP_UNAUTHORIZED();
        AVERROR_HTTP_FORBIDDEN = avutil.AVERROR_HTTP_FORBIDDEN();
        AVERROR_HTTP_NOT_FOUND = avutil.AVERROR_HTTP_NOT_FOUND();
        AVERROR_HTTP_TOO_MANY_REQUESTS = avutil.AVERROR_HTTP_TOO_MANY_REQUESTS();
        AVERROR_HTTP_OTHER_4XX = avutil.AVERROR_HTTP_OTHER_4XX();
        AVERROR_HTTP_SERVER_ERROR = avutil.AVERROR_HTTP_SERVER_ERROR();
        NAN = avutil.NAN();
        INFINITY = avutil.INFINITY();
        AV_PIX_FMT_RGB32 = avutil.AV_PIX_FMT_RGB32();
        AV_PIX_FMT_RGB32_1 = avutil.AV_PIX_FMT_RGB32_1();
        AV_PIX_FMT_BGR32 = avutil.AV_PIX_FMT_BGR32();
        AV_PIX_FMT_BGR32_1 = avutil.AV_PIX_FMT_BGR32_1();
        AV_PIX_FMT_0RGB32 = avutil.AV_PIX_FMT_0RGB32();
        AV_PIX_FMT_0BGR32 = avutil.AV_PIX_FMT_0BGR32();
        AV_PIX_FMT_GRAY9 = avutil.AV_PIX_FMT_GRAY9();
        AV_PIX_FMT_GRAY10 = avutil.AV_PIX_FMT_GRAY10();
        AV_PIX_FMT_GRAY12 = avutil.AV_PIX_FMT_GRAY12();
        AV_PIX_FMT_GRAY14 = avutil.AV_PIX_FMT_GRAY14();
        AV_PIX_FMT_GRAY16 = avutil.AV_PIX_FMT_GRAY16();
        AV_PIX_FMT_GRAY32 = avutil.AV_PIX_FMT_GRAY32();
        AV_PIX_FMT_YA16 = avutil.AV_PIX_FMT_YA16();
        AV_PIX_FMT_RGB48 = avutil.AV_PIX_FMT_RGB48();
        AV_PIX_FMT_RGB565 = avutil.AV_PIX_FMT_RGB565();
        AV_PIX_FMT_RGB555 = avutil.AV_PIX_FMT_RGB555();
        AV_PIX_FMT_RGB444 = avutil.AV_PIX_FMT_RGB444();
        AV_PIX_FMT_RGBA64 = avutil.AV_PIX_FMT_RGBA64();
        AV_PIX_FMT_BGR48 = avutil.AV_PIX_FMT_BGR48();
        AV_PIX_FMT_BGR565 = avutil.AV_PIX_FMT_BGR565();
        AV_PIX_FMT_BGR555 = avutil.AV_PIX_FMT_BGR555();
        AV_PIX_FMT_BGR444 = avutil.AV_PIX_FMT_BGR444();
        AV_PIX_FMT_BGRA64 = avutil.AV_PIX_FMT_BGRA64();
        AV_PIX_FMT_YUV420P9 = avutil.AV_PIX_FMT_YUV420P9();
        AV_PIX_FMT_YUV422P9 = avutil.AV_PIX_FMT_YUV422P9();
        AV_PIX_FMT_YUV444P9 = avutil.AV_PIX_FMT_YUV444P9();
        AV_PIX_FMT_YUV420P10 = avutil.AV_PIX_FMT_YUV420P10();
        AV_PIX_FMT_YUV422P10 = avutil.AV_PIX_FMT_YUV422P10();
        AV_PIX_FMT_YUV440P10 = avutil.AV_PIX_FMT_YUV440P10();
        AV_PIX_FMT_YUV444P10 = avutil.AV_PIX_FMT_YUV444P10();
        AV_PIX_FMT_YUV420P12 = avutil.AV_PIX_FMT_YUV420P12();
        AV_PIX_FMT_YUV422P12 = avutil.AV_PIX_FMT_YUV422P12();
        AV_PIX_FMT_YUV440P12 = avutil.AV_PIX_FMT_YUV440P12();
        AV_PIX_FMT_YUV444P12 = avutil.AV_PIX_FMT_YUV444P12();
        AV_PIX_FMT_YUV420P14 = avutil.AV_PIX_FMT_YUV420P14();
        AV_PIX_FMT_YUV422P14 = avutil.AV_PIX_FMT_YUV422P14();
        AV_PIX_FMT_YUV444P14 = avutil.AV_PIX_FMT_YUV444P14();
        AV_PIX_FMT_YUV420P16 = avutil.AV_PIX_FMT_YUV420P16();
        AV_PIX_FMT_YUV422P16 = avutil.AV_PIX_FMT_YUV422P16();
        AV_PIX_FMT_YUV444P16 = avutil.AV_PIX_FMT_YUV444P16();
        AV_PIX_FMT_YUV444P10MSB = avutil.AV_PIX_FMT_YUV444P10MSB();
        AV_PIX_FMT_YUV444P12MSB = avutil.AV_PIX_FMT_YUV444P12MSB();
        AV_PIX_FMT_GBRP9 = avutil.AV_PIX_FMT_GBRP9();
        AV_PIX_FMT_GBRP10 = avutil.AV_PIX_FMT_GBRP10();
        AV_PIX_FMT_GBRP12 = avutil.AV_PIX_FMT_GBRP12();
        AV_PIX_FMT_GBRP14 = avutil.AV_PIX_FMT_GBRP14();
        AV_PIX_FMT_GBRP16 = avutil.AV_PIX_FMT_GBRP16();
        AV_PIX_FMT_GBRAP10 = avutil.AV_PIX_FMT_GBRAP10();
        AV_PIX_FMT_GBRAP12 = avutil.AV_PIX_FMT_GBRAP12();
        AV_PIX_FMT_GBRAP14 = avutil.AV_PIX_FMT_GBRAP14();
        AV_PIX_FMT_GBRAP16 = avutil.AV_PIX_FMT_GBRAP16();
        AV_PIX_FMT_GBRAP32 = avutil.AV_PIX_FMT_GBRAP32();
        AV_PIX_FMT_GBRP10MSB = avutil.AV_PIX_FMT_GBRP10MSB();
        AV_PIX_FMT_GBRP12MSB = avutil.AV_PIX_FMT_GBRP12MSB();
        AV_PIX_FMT_BAYER_BGGR16 = avutil.AV_PIX_FMT_BAYER_BGGR16();
        AV_PIX_FMT_BAYER_RGGB16 = avutil.AV_PIX_FMT_BAYER_RGGB16();
        AV_PIX_FMT_BAYER_GBRG16 = avutil.AV_PIX_FMT_BAYER_GBRG16();
        AV_PIX_FMT_BAYER_GRBG16 = avutil.AV_PIX_FMT_BAYER_GRBG16();
        AV_PIX_FMT_GBRPF16 = avutil.AV_PIX_FMT_GBRPF16();
        AV_PIX_FMT_GBRAPF16 = avutil.AV_PIX_FMT_GBRAPF16();
        AV_PIX_FMT_GBRPF32 = avutil.AV_PIX_FMT_GBRPF32();
        AV_PIX_FMT_GBRAPF32 = avutil.AV_PIX_FMT_GBRAPF32();
        AV_PIX_FMT_GRAYF16 = avutil.AV_PIX_FMT_GRAYF16();
        AV_PIX_FMT_GRAYF32 = avutil.AV_PIX_FMT_GRAYF32();
        AV_PIX_FMT_YAF16 = avutil.AV_PIX_FMT_YAF16();
        AV_PIX_FMT_YAF32 = avutil.AV_PIX_FMT_YAF32();
        AV_PIX_FMT_YUVA420P9 = avutil.AV_PIX_FMT_YUVA420P9();
        AV_PIX_FMT_YUVA422P9 = avutil.AV_PIX_FMT_YUVA422P9();
        AV_PIX_FMT_YUVA444P9 = avutil.AV_PIX_FMT_YUVA444P9();
        AV_PIX_FMT_YUVA420P10 = avutil.AV_PIX_FMT_YUVA420P10();
        AV_PIX_FMT_YUVA422P10 = avutil.AV_PIX_FMT_YUVA422P10();
        AV_PIX_FMT_YUVA444P10 = avutil.AV_PIX_FMT_YUVA444P10();
        AV_PIX_FMT_YUVA422P12 = avutil.AV_PIX_FMT_YUVA422P12();
        AV_PIX_FMT_YUVA444P12 = avutil.AV_PIX_FMT_YUVA444P12();
        AV_PIX_FMT_YUVA420P16 = avutil.AV_PIX_FMT_YUVA420P16();
        AV_PIX_FMT_YUVA422P16 = avutil.AV_PIX_FMT_YUVA422P16();
        AV_PIX_FMT_YUVA444P16 = avutil.AV_PIX_FMT_YUVA444P16();
        AV_PIX_FMT_XYZ12 = avutil.AV_PIX_FMT_XYZ12();
        AV_PIX_FMT_NV20 = avutil.AV_PIX_FMT_NV20();
        AV_PIX_FMT_AYUV64 = avutil.AV_PIX_FMT_AYUV64();
        AV_PIX_FMT_P010 = avutil.AV_PIX_FMT_P010();
        AV_PIX_FMT_P012 = avutil.AV_PIX_FMT_P012();
        AV_PIX_FMT_P016 = avutil.AV_PIX_FMT_P016();
        AV_PIX_FMT_Y210 = avutil.AV_PIX_FMT_Y210();
        AV_PIX_FMT_Y212 = avutil.AV_PIX_FMT_Y212();
        AV_PIX_FMT_Y216 = avutil.AV_PIX_FMT_Y216();
        AV_PIX_FMT_XV30 = avutil.AV_PIX_FMT_XV30();
        AV_PIX_FMT_XV36 = avutil.AV_PIX_FMT_XV36();
        AV_PIX_FMT_XV48 = avutil.AV_PIX_FMT_XV48();
        AV_PIX_FMT_V30X = avutil.AV_PIX_FMT_V30X();
        AV_PIX_FMT_X2RGB10 = avutil.AV_PIX_FMT_X2RGB10();
        AV_PIX_FMT_X2BGR10 = avutil.AV_PIX_FMT_X2BGR10();
        AV_PIX_FMT_P210 = avutil.AV_PIX_FMT_P210();
        AV_PIX_FMT_P410 = avutil.AV_PIX_FMT_P410();
        AV_PIX_FMT_P212 = avutil.AV_PIX_FMT_P212();
        AV_PIX_FMT_P412 = avutil.AV_PIX_FMT_P412();
        AV_PIX_FMT_P216 = avutil.AV_PIX_FMT_P216();
        AV_PIX_FMT_P416 = avutil.AV_PIX_FMT_P416();
        AV_PIX_FMT_RGBF16 = avutil.AV_PIX_FMT_RGBF16();
        AV_PIX_FMT_RGBAF16 = avutil.AV_PIX_FMT_RGBAF16();
        AV_PIX_FMT_RGBF32 = avutil.AV_PIX_FMT_RGBF32();
        AV_PIX_FMT_RGBAF32 = avutil.AV_PIX_FMT_RGBAF32();
        AV_PIX_FMT_RGB96 = avutil.AV_PIX_FMT_RGB96();
        AV_PIX_FMT_RGBA128 = avutil.AV_PIX_FMT_RGBA128();
        AV_CHANNEL_LAYOUT_MONO = avutil.AV_CHANNEL_LAYOUT_MONO();
        AV_CHANNEL_LAYOUT_STEREO = avutil.AV_CHANNEL_LAYOUT_STEREO();
        AV_CHANNEL_LAYOUT_2POINT1 = avutil.AV_CHANNEL_LAYOUT_2POINT1();
        AV_CHANNEL_LAYOUT_2_1 = avutil.AV_CHANNEL_LAYOUT_2_1();
        AV_CHANNEL_LAYOUT_SURROUND = avutil.AV_CHANNEL_LAYOUT_SURROUND();
        AV_CHANNEL_LAYOUT_3POINT1 = avutil.AV_CHANNEL_LAYOUT_3POINT1();
        AV_CHANNEL_LAYOUT_4POINT0 = avutil.AV_CHANNEL_LAYOUT_4POINT0();
        AV_CHANNEL_LAYOUT_4POINT1 = avutil.AV_CHANNEL_LAYOUT_4POINT1();
        AV_CHANNEL_LAYOUT_2_2 = avutil.AV_CHANNEL_LAYOUT_2_2();
        AV_CHANNEL_LAYOUT_QUAD = avutil.AV_CHANNEL_LAYOUT_QUAD();
        AV_CHANNEL_LAYOUT_5POINT0 = avutil.AV_CHANNEL_LAYOUT_5POINT0();
        AV_CHANNEL_LAYOUT_5POINT1 = avutil.AV_CHANNEL_LAYOUT_5POINT1();
        AV_CHANNEL_LAYOUT_5POINT0_BACK = avutil.AV_CHANNEL_LAYOUT_5POINT0_BACK();
        AV_CHANNEL_LAYOUT_5POINT1_BACK = avutil.AV_CHANNEL_LAYOUT_5POINT1_BACK();
        AV_CHANNEL_LAYOUT_6POINT0 = avutil.AV_CHANNEL_LAYOUT_6POINT0();
        AV_CHANNEL_LAYOUT_6POINT0_FRONT = avutil.AV_CHANNEL_LAYOUT_6POINT0_FRONT();
        AV_CHANNEL_LAYOUT_3POINT1POINT2 = avutil.AV_CHANNEL_LAYOUT_3POINT1POINT2();
        AV_CHANNEL_LAYOUT_HEXAGONAL = avutil.AV_CHANNEL_LAYOUT_HEXAGONAL();
        AV_CHANNEL_LAYOUT_6POINT1 = avutil.AV_CHANNEL_LAYOUT_6POINT1();
        AV_CHANNEL_LAYOUT_6POINT1_BACK = avutil.AV_CHANNEL_LAYOUT_6POINT1_BACK();
        AV_CHANNEL_LAYOUT_6POINT1_FRONT = avutil.AV_CHANNEL_LAYOUT_6POINT1_FRONT();
        AV_CHANNEL_LAYOUT_7POINT0 = avutil.AV_CHANNEL_LAYOUT_7POINT0();
        AV_CHANNEL_LAYOUT_7POINT0_FRONT = avutil.AV_CHANNEL_LAYOUT_7POINT0_FRONT();
        AV_CHANNEL_LAYOUT_7POINT1 = avutil.AV_CHANNEL_LAYOUT_7POINT1();
        AV_CHANNEL_LAYOUT_7POINT1_WIDE = avutil.AV_CHANNEL_LAYOUT_7POINT1_WIDE();
        AV_CHANNEL_LAYOUT_7POINT1_WIDE_BACK = avutil.AV_CHANNEL_LAYOUT_7POINT1_WIDE_BACK();
        AV_CHANNEL_LAYOUT_5POINT1POINT2 = avutil.AV_CHANNEL_LAYOUT_5POINT1POINT2();
        AV_CHANNEL_LAYOUT_5POINT1POINT2_BACK = avutil.AV_CHANNEL_LAYOUT_5POINT1POINT2_BACK();
        AV_CHANNEL_LAYOUT_OCTAGONAL = avutil.AV_CHANNEL_LAYOUT_OCTAGONAL();
        AV_CHANNEL_LAYOUT_CUBE = avutil.AV_CHANNEL_LAYOUT_CUBE();
        AV_CHANNEL_LAYOUT_5POINT1POINT4_BACK = avutil.AV_CHANNEL_LAYOUT_5POINT1POINT4_BACK();
        AV_CHANNEL_LAYOUT_7POINT1POINT2 = avutil.AV_CHANNEL_LAYOUT_7POINT1POINT2();
        AV_CHANNEL_LAYOUT_7POINT1POINT4_BACK = avutil.AV_CHANNEL_LAYOUT_7POINT1POINT4_BACK();
        AV_CHANNEL_LAYOUT_7POINT2POINT3 = avutil.AV_CHANNEL_LAYOUT_7POINT2POINT3();
        AV_CHANNEL_LAYOUT_9POINT1POINT4_BACK = avutil.AV_CHANNEL_LAYOUT_9POINT1POINT4_BACK();
        AV_CHANNEL_LAYOUT_9POINT1POINT6 = avutil.AV_CHANNEL_LAYOUT_9POINT1POINT6();
        AV_CHANNEL_LAYOUT_HEXADECAGONAL = avutil.AV_CHANNEL_LAYOUT_HEXADECAGONAL();
        AV_CHANNEL_LAYOUT_BINAURAL = avutil.AV_CHANNEL_LAYOUT_BINAURAL();
        AV_CHANNEL_LAYOUT_STEREO_DOWNMIX = avutil.AV_CHANNEL_LAYOUT_STEREO_DOWNMIX();
        AV_CHANNEL_LAYOUT_22POINT2 = avutil.AV_CHANNEL_LAYOUT_22POINT2();
        AV_CHANNEL_LAYOUT_7POINT1_TOP_BACK = avutil.AV_CHANNEL_LAYOUT_7POINT1_TOP_BACK();
        AV_CHANNEL_LAYOUT_AMBISONIC_FIRST_ORDER = avutil.AV_CHANNEL_LAYOUT_AMBISONIC_FIRST_ORDER();
        LIBAVUTIL_BUILD = LIBAVUTIL_VERSION_INT = avutil.LIBAVUTIL_VERSION_INT();
        LIBAVUTIL_IDENT = avutil.LIBAVUTIL_IDENT();
    }
}

