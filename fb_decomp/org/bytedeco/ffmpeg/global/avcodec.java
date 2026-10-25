/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.global;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.bytedeco.ffmpeg.avcodec.AVBSFContext;
import org.bytedeco.ffmpeg.avcodec.AVBSFList;
import org.bytedeco.ffmpeg.avcodec.AVBitStreamFilter;
import org.bytedeco.ffmpeg.avcodec.AVCPBProperties;
import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVCodecDescriptor;
import org.bytedeco.ffmpeg.avcodec.AVCodecHWConfig;
import org.bytedeco.ffmpeg.avcodec.AVCodecParameters;
import org.bytedeco.ffmpeg.avcodec.AVCodecParser;
import org.bytedeco.ffmpeg.avcodec.AVCodecParserContext;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avcodec.AVPacketSideData;
import org.bytedeco.ffmpeg.avcodec.AVSubtitle;
import org.bytedeco.ffmpeg.avcodec.Func_AVCodecContext_Pointer;
import org.bytedeco.ffmpeg.avcodec.Func_AVCodecContext_Pointer_int_int;
import org.bytedeco.ffmpeg.avutil.AVBufferRef;
import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.avutil.AVContainerFifo;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.avutil.AVFrameSideData;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.SizeTPointer;
import org.bytedeco.javacpp.annotation.ByPtrPtr;
import org.bytedeco.javacpp.annotation.ByVal;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.NoException;
import org.bytedeco.javacpp.annotation.Platform;

public class avcodec
extends org.bytedeco.ffmpeg.presets.avcodec {
    public static final int AV_CODEC_ID_NONE = 0;
    public static final int AV_CODEC_ID_MPEG1VIDEO = 1;
    public static final int AV_CODEC_ID_MPEG2VIDEO = 2;
    public static final int AV_CODEC_ID_H261 = 3;
    public static final int AV_CODEC_ID_H263 = 4;
    public static final int AV_CODEC_ID_RV10 = 5;
    public static final int AV_CODEC_ID_RV20 = 6;
    public static final int AV_CODEC_ID_MJPEG = 7;
    public static final int AV_CODEC_ID_MJPEGB = 8;
    public static final int AV_CODEC_ID_LJPEG = 9;
    public static final int AV_CODEC_ID_SP5X = 10;
    public static final int AV_CODEC_ID_JPEGLS = 11;
    public static final int AV_CODEC_ID_MPEG4 = 12;
    public static final int AV_CODEC_ID_RAWVIDEO = 13;
    public static final int AV_CODEC_ID_MSMPEG4V1 = 14;
    public static final int AV_CODEC_ID_MSMPEG4V2 = 15;
    public static final int AV_CODEC_ID_MSMPEG4V3 = 16;
    public static final int AV_CODEC_ID_WMV1 = 17;
    public static final int AV_CODEC_ID_WMV2 = 18;
    public static final int AV_CODEC_ID_H263P = 19;
    public static final int AV_CODEC_ID_H263I = 20;
    public static final int AV_CODEC_ID_FLV1 = 21;
    public static final int AV_CODEC_ID_SVQ1 = 22;
    public static final int AV_CODEC_ID_SVQ3 = 23;
    public static final int AV_CODEC_ID_DVVIDEO = 24;
    public static final int AV_CODEC_ID_HUFFYUV = 25;
    public static final int AV_CODEC_ID_CYUV = 26;
    public static final int AV_CODEC_ID_H264 = 27;
    public static final int AV_CODEC_ID_INDEO3 = 28;
    public static final int AV_CODEC_ID_VP3 = 29;
    public static final int AV_CODEC_ID_THEORA = 30;
    public static final int AV_CODEC_ID_ASV1 = 31;
    public static final int AV_CODEC_ID_ASV2 = 32;
    public static final int AV_CODEC_ID_FFV1 = 33;
    public static final int AV_CODEC_ID_4XM = 34;
    public static final int AV_CODEC_ID_VCR1 = 35;
    public static final int AV_CODEC_ID_CLJR = 36;
    public static final int AV_CODEC_ID_MDEC = 37;
    public static final int AV_CODEC_ID_ROQ = 38;
    public static final int AV_CODEC_ID_INTERPLAY_VIDEO = 39;
    public static final int AV_CODEC_ID_XAN_WC3 = 40;
    public static final int AV_CODEC_ID_XAN_WC4 = 41;
    public static final int AV_CODEC_ID_RPZA = 42;
    public static final int AV_CODEC_ID_CINEPAK = 43;
    public static final int AV_CODEC_ID_WS_VQA = 44;
    public static final int AV_CODEC_ID_MSRLE = 45;
    public static final int AV_CODEC_ID_MSVIDEO1 = 46;
    public static final int AV_CODEC_ID_IDCIN = 47;
    public static final int AV_CODEC_ID_8BPS = 48;
    public static final int AV_CODEC_ID_SMC = 49;
    public static final int AV_CODEC_ID_FLIC = 50;
    public static final int AV_CODEC_ID_TRUEMOTION1 = 51;
    public static final int AV_CODEC_ID_VMDVIDEO = 52;
    public static final int AV_CODEC_ID_MSZH = 53;
    public static final int AV_CODEC_ID_ZLIB = 54;
    public static final int AV_CODEC_ID_QTRLE = 55;
    public static final int AV_CODEC_ID_TSCC = 56;
    public static final int AV_CODEC_ID_ULTI = 57;
    public static final int AV_CODEC_ID_QDRAW = 58;
    public static final int AV_CODEC_ID_VIXL = 59;
    public static final int AV_CODEC_ID_QPEG = 60;
    public static final int AV_CODEC_ID_PNG = 61;
    public static final int AV_CODEC_ID_PPM = 62;
    public static final int AV_CODEC_ID_PBM = 63;
    public static final int AV_CODEC_ID_PGM = 64;
    public static final int AV_CODEC_ID_PGMYUV = 65;
    public static final int AV_CODEC_ID_PAM = 66;
    public static final int AV_CODEC_ID_FFVHUFF = 67;
    public static final int AV_CODEC_ID_RV30 = 68;
    public static final int AV_CODEC_ID_RV40 = 69;
    public static final int AV_CODEC_ID_VC1 = 70;
    public static final int AV_CODEC_ID_WMV3 = 71;
    public static final int AV_CODEC_ID_LOCO = 72;
    public static final int AV_CODEC_ID_WNV1 = 73;
    public static final int AV_CODEC_ID_AASC = 74;
    public static final int AV_CODEC_ID_INDEO2 = 75;
    public static final int AV_CODEC_ID_FRAPS = 76;
    public static final int AV_CODEC_ID_TRUEMOTION2 = 77;
    public static final int AV_CODEC_ID_BMP = 78;
    public static final int AV_CODEC_ID_CSCD = 79;
    public static final int AV_CODEC_ID_MMVIDEO = 80;
    public static final int AV_CODEC_ID_ZMBV = 81;
    public static final int AV_CODEC_ID_AVS = 82;
    public static final int AV_CODEC_ID_SMACKVIDEO = 83;
    public static final int AV_CODEC_ID_NUV = 84;
    public static final int AV_CODEC_ID_KMVC = 85;
    public static final int AV_CODEC_ID_FLASHSV = 86;
    public static final int AV_CODEC_ID_CAVS = 87;
    public static final int AV_CODEC_ID_JPEG2000 = 88;
    public static final int AV_CODEC_ID_VMNC = 89;
    public static final int AV_CODEC_ID_VP5 = 90;
    public static final int AV_CODEC_ID_VP6 = 91;
    public static final int AV_CODEC_ID_VP6F = 92;
    public static final int AV_CODEC_ID_TARGA = 93;
    public static final int AV_CODEC_ID_DSICINVIDEO = 94;
    public static final int AV_CODEC_ID_TIERTEXSEQVIDEO = 95;
    public static final int AV_CODEC_ID_TIFF = 96;
    public static final int AV_CODEC_ID_GIF = 97;
    public static final int AV_CODEC_ID_DXA = 98;
    public static final int AV_CODEC_ID_DNXHD = 99;
    public static final int AV_CODEC_ID_THP = 100;
    public static final int AV_CODEC_ID_SGI = 101;
    public static final int AV_CODEC_ID_C93 = 102;
    public static final int AV_CODEC_ID_BETHSOFTVID = 103;
    public static final int AV_CODEC_ID_PTX = 104;
    public static final int AV_CODEC_ID_TXD = 105;
    public static final int AV_CODEC_ID_VP6A = 106;
    public static final int AV_CODEC_ID_AMV = 107;
    public static final int AV_CODEC_ID_VB = 108;
    public static final int AV_CODEC_ID_PCX = 109;
    public static final int AV_CODEC_ID_SUNRAST = 110;
    public static final int AV_CODEC_ID_INDEO4 = 111;
    public static final int AV_CODEC_ID_INDEO5 = 112;
    public static final int AV_CODEC_ID_MIMIC = 113;
    public static final int AV_CODEC_ID_RL2 = 114;
    public static final int AV_CODEC_ID_ESCAPE124 = 115;
    public static final int AV_CODEC_ID_DIRAC = 116;
    public static final int AV_CODEC_ID_BFI = 117;
    public static final int AV_CODEC_ID_CMV = 118;
    public static final int AV_CODEC_ID_MOTIONPIXELS = 119;
    public static final int AV_CODEC_ID_TGV = 120;
    public static final int AV_CODEC_ID_TGQ = 121;
    public static final int AV_CODEC_ID_TQI = 122;
    public static final int AV_CODEC_ID_AURA = 123;
    public static final int AV_CODEC_ID_AURA2 = 124;
    public static final int AV_CODEC_ID_V210X = 125;
    public static final int AV_CODEC_ID_TMV = 126;
    public static final int AV_CODEC_ID_V210 = 127;
    public static final int AV_CODEC_ID_DPX = 128;
    public static final int AV_CODEC_ID_MAD = 129;
    public static final int AV_CODEC_ID_FRWU = 130;
    public static final int AV_CODEC_ID_FLASHSV2 = 131;
    public static final int AV_CODEC_ID_CDGRAPHICS = 132;
    public static final int AV_CODEC_ID_R210 = 133;
    public static final int AV_CODEC_ID_ANM = 134;
    public static final int AV_CODEC_ID_BINKVIDEO = 135;
    public static final int AV_CODEC_ID_IFF_ILBM = 136;
    public static final int AV_CODEC_ID_IFF_BYTERUN1 = 136;
    public static final int AV_CODEC_ID_KGV1 = 137;
    public static final int AV_CODEC_ID_YOP = 138;
    public static final int AV_CODEC_ID_VP8 = 139;
    public static final int AV_CODEC_ID_PICTOR = 140;
    public static final int AV_CODEC_ID_ANSI = 141;
    public static final int AV_CODEC_ID_A64_MULTI = 142;
    public static final int AV_CODEC_ID_A64_MULTI5 = 143;
    public static final int AV_CODEC_ID_R10K = 144;
    public static final int AV_CODEC_ID_MXPEG = 145;
    public static final int AV_CODEC_ID_LAGARITH = 146;
    public static final int AV_CODEC_ID_PRORES = 147;
    public static final int AV_CODEC_ID_JV = 148;
    public static final int AV_CODEC_ID_DFA = 149;
    public static final int AV_CODEC_ID_WMV3IMAGE = 150;
    public static final int AV_CODEC_ID_VC1IMAGE = 151;
    public static final int AV_CODEC_ID_UTVIDEO = 152;
    public static final int AV_CODEC_ID_BMV_VIDEO = 153;
    public static final int AV_CODEC_ID_VBLE = 154;
    public static final int AV_CODEC_ID_DXTORY = 155;
    public static final int AV_CODEC_ID_V410 = 156;
    public static final int AV_CODEC_ID_XWD = 157;
    public static final int AV_CODEC_ID_CDXL = 158;
    public static final int AV_CODEC_ID_XBM = 159;
    public static final int AV_CODEC_ID_ZEROCODEC = 160;
    public static final int AV_CODEC_ID_MSS1 = 161;
    public static final int AV_CODEC_ID_MSA1 = 162;
    public static final int AV_CODEC_ID_TSCC2 = 163;
    public static final int AV_CODEC_ID_MTS2 = 164;
    public static final int AV_CODEC_ID_CLLC = 165;
    public static final int AV_CODEC_ID_MSS2 = 166;
    public static final int AV_CODEC_ID_VP9 = 167;
    public static final int AV_CODEC_ID_AIC = 168;
    public static final int AV_CODEC_ID_ESCAPE130 = 169;
    public static final int AV_CODEC_ID_G2M = 170;
    public static final int AV_CODEC_ID_WEBP = 171;
    public static final int AV_CODEC_ID_HNM4_VIDEO = 172;
    public static final int AV_CODEC_ID_HEVC = 173;
    public static final int AV_CODEC_ID_H265 = 173;
    public static final int AV_CODEC_ID_FIC = 174;
    public static final int AV_CODEC_ID_ALIAS_PIX = 175;
    public static final int AV_CODEC_ID_BRENDER_PIX = 176;
    public static final int AV_CODEC_ID_PAF_VIDEO = 177;
    public static final int AV_CODEC_ID_EXR = 178;
    public static final int AV_CODEC_ID_VP7 = 179;
    public static final int AV_CODEC_ID_SANM = 180;
    public static final int AV_CODEC_ID_SGIRLE = 181;
    public static final int AV_CODEC_ID_MVC1 = 182;
    public static final int AV_CODEC_ID_MVC2 = 183;
    public static final int AV_CODEC_ID_HQX = 184;
    public static final int AV_CODEC_ID_TDSC = 185;
    public static final int AV_CODEC_ID_HQ_HQA = 186;
    public static final int AV_CODEC_ID_HAP = 187;
    public static final int AV_CODEC_ID_DDS = 188;
    public static final int AV_CODEC_ID_DXV = 189;
    public static final int AV_CODEC_ID_SCREENPRESSO = 190;
    public static final int AV_CODEC_ID_RSCC = 191;
    public static final int AV_CODEC_ID_AVS2 = 192;
    public static final int AV_CODEC_ID_PGX = 193;
    public static final int AV_CODEC_ID_AVS3 = 194;
    public static final int AV_CODEC_ID_MSP2 = 195;
    public static final int AV_CODEC_ID_VVC = 196;
    public static final int AV_CODEC_ID_H266 = 196;
    public static final int AV_CODEC_ID_Y41P = 197;
    public static final int AV_CODEC_ID_AVRP = 198;
    public static final int AV_CODEC_ID_012V = 199;
    public static final int AV_CODEC_ID_AVUI = 200;
    public static final int AV_CODEC_ID_TARGA_Y216 = 201;
    public static final int AV_CODEC_ID_V308 = 202;
    public static final int AV_CODEC_ID_V408 = 203;
    public static final int AV_CODEC_ID_YUV4 = 204;
    public static final int AV_CODEC_ID_AVRN = 205;
    public static final int AV_CODEC_ID_CPIA = 206;
    public static final int AV_CODEC_ID_XFACE = 207;
    public static final int AV_CODEC_ID_SNOW = 208;
    public static final int AV_CODEC_ID_SMVJPEG = 209;
    public static final int AV_CODEC_ID_APNG = 210;
    public static final int AV_CODEC_ID_DAALA = 211;
    public static final int AV_CODEC_ID_CFHD = 212;
    public static final int AV_CODEC_ID_TRUEMOTION2RT = 213;
    public static final int AV_CODEC_ID_M101 = 214;
    public static final int AV_CODEC_ID_MAGICYUV = 215;
    public static final int AV_CODEC_ID_SHEERVIDEO = 216;
    public static final int AV_CODEC_ID_YLC = 217;
    public static final int AV_CODEC_ID_PSD = 218;
    public static final int AV_CODEC_ID_PIXLET = 219;
    public static final int AV_CODEC_ID_SPEEDHQ = 220;
    public static final int AV_CODEC_ID_FMVC = 221;
    public static final int AV_CODEC_ID_SCPR = 222;
    public static final int AV_CODEC_ID_CLEARVIDEO = 223;
    public static final int AV_CODEC_ID_XPM = 224;
    public static final int AV_CODEC_ID_AV1 = 225;
    public static final int AV_CODEC_ID_BITPACKED = 226;
    public static final int AV_CODEC_ID_MSCC = 227;
    public static final int AV_CODEC_ID_SRGC = 228;
    public static final int AV_CODEC_ID_SVG = 229;
    public static final int AV_CODEC_ID_GDV = 230;
    public static final int AV_CODEC_ID_FITS = 231;
    public static final int AV_CODEC_ID_IMM4 = 232;
    public static final int AV_CODEC_ID_PROSUMER = 233;
    public static final int AV_CODEC_ID_MWSC = 234;
    public static final int AV_CODEC_ID_WCMV = 235;
    public static final int AV_CODEC_ID_RASC = 236;
    public static final int AV_CODEC_ID_HYMT = 237;
    public static final int AV_CODEC_ID_ARBC = 238;
    public static final int AV_CODEC_ID_AGM = 239;
    public static final int AV_CODEC_ID_LSCR = 240;
    public static final int AV_CODEC_ID_VP4 = 241;
    public static final int AV_CODEC_ID_IMM5 = 242;
    public static final int AV_CODEC_ID_MVDV = 243;
    public static final int AV_CODEC_ID_MVHA = 244;
    public static final int AV_CODEC_ID_CDTOONS = 245;
    public static final int AV_CODEC_ID_MV30 = 246;
    public static final int AV_CODEC_ID_NOTCHLC = 247;
    public static final int AV_CODEC_ID_PFM = 248;
    public static final int AV_CODEC_ID_MOBICLIP = 249;
    public static final int AV_CODEC_ID_PHOTOCD = 250;
    public static final int AV_CODEC_ID_IPU = 251;
    public static final int AV_CODEC_ID_ARGO = 252;
    public static final int AV_CODEC_ID_CRI = 253;
    public static final int AV_CODEC_ID_SIMBIOSIS_IMX = 254;
    public static final int AV_CODEC_ID_SGA_VIDEO = 255;
    public static final int AV_CODEC_ID_GEM = 256;
    public static final int AV_CODEC_ID_VBN = 257;
    public static final int AV_CODEC_ID_JPEGXL = 258;
    public static final int AV_CODEC_ID_QOI = 259;
    public static final int AV_CODEC_ID_PHM = 260;
    public static final int AV_CODEC_ID_RADIANCE_HDR = 261;
    public static final int AV_CODEC_ID_WBMP = 262;
    public static final int AV_CODEC_ID_MEDIA100 = 263;
    public static final int AV_CODEC_ID_VQC = 264;
    public static final int AV_CODEC_ID_PDV = 265;
    public static final int AV_CODEC_ID_EVC = 266;
    public static final int AV_CODEC_ID_RTV1 = 267;
    public static final int AV_CODEC_ID_VMIX = 268;
    public static final int AV_CODEC_ID_LEAD = 269;
    public static final int AV_CODEC_ID_DNXUC = 270;
    public static final int AV_CODEC_ID_RV60 = 271;
    public static final int AV_CODEC_ID_JPEGXL_ANIM = 272;
    public static final int AV_CODEC_ID_APV = 273;
    public static final int AV_CODEC_ID_PRORES_RAW = 274;
    public static final int AV_CODEC_ID_JPEGXS = 275;
    public static final int AV_CODEC_ID_FIRST_AUDIO = 65536;
    public static final int AV_CODEC_ID_PCM_S16LE = 65536;
    public static final int AV_CODEC_ID_PCM_S16BE = 65537;
    public static final int AV_CODEC_ID_PCM_U16LE = 65538;
    public static final int AV_CODEC_ID_PCM_U16BE = 65539;
    public static final int AV_CODEC_ID_PCM_S8 = 65540;
    public static final int AV_CODEC_ID_PCM_U8 = 65541;
    public static final int AV_CODEC_ID_PCM_MULAW = 65542;
    public static final int AV_CODEC_ID_PCM_ALAW = 65543;
    public static final int AV_CODEC_ID_PCM_S32LE = 65544;
    public static final int AV_CODEC_ID_PCM_S32BE = 65545;
    public static final int AV_CODEC_ID_PCM_U32LE = 65546;
    public static final int AV_CODEC_ID_PCM_U32BE = 65547;
    public static final int AV_CODEC_ID_PCM_S24LE = 65548;
    public static final int AV_CODEC_ID_PCM_S24BE = 65549;
    public static final int AV_CODEC_ID_PCM_U24LE = 65550;
    public static final int AV_CODEC_ID_PCM_U24BE = 65551;
    public static final int AV_CODEC_ID_PCM_S24DAUD = 65552;
    public static final int AV_CODEC_ID_PCM_ZORK = 65553;
    public static final int AV_CODEC_ID_PCM_S16LE_PLANAR = 65554;
    public static final int AV_CODEC_ID_PCM_DVD = 65555;
    public static final int AV_CODEC_ID_PCM_F32BE = 65556;
    public static final int AV_CODEC_ID_PCM_F32LE = 65557;
    public static final int AV_CODEC_ID_PCM_F64BE = 65558;
    public static final int AV_CODEC_ID_PCM_F64LE = 65559;
    public static final int AV_CODEC_ID_PCM_BLURAY = 65560;
    public static final int AV_CODEC_ID_PCM_LXF = 65561;
    public static final int AV_CODEC_ID_S302M = 65562;
    public static final int AV_CODEC_ID_PCM_S8_PLANAR = 65563;
    public static final int AV_CODEC_ID_PCM_S24LE_PLANAR = 65564;
    public static final int AV_CODEC_ID_PCM_S32LE_PLANAR = 65565;
    public static final int AV_CODEC_ID_PCM_S16BE_PLANAR = 65566;
    public static final int AV_CODEC_ID_PCM_S64LE = 65567;
    public static final int AV_CODEC_ID_PCM_S64BE = 65568;
    public static final int AV_CODEC_ID_PCM_F16LE = 65569;
    public static final int AV_CODEC_ID_PCM_F24LE = 65570;
    public static final int AV_CODEC_ID_PCM_VIDC = 65571;
    public static final int AV_CODEC_ID_PCM_SGA = 65572;
    public static final int AV_CODEC_ID_ADPCM_IMA_QT = 69632;
    public static final int AV_CODEC_ID_ADPCM_IMA_WAV = 69633;
    public static final int AV_CODEC_ID_ADPCM_IMA_DK3 = 69634;
    public static final int AV_CODEC_ID_ADPCM_IMA_DK4 = 69635;
    public static final int AV_CODEC_ID_ADPCM_IMA_WS = 69636;
    public static final int AV_CODEC_ID_ADPCM_IMA_SMJPEG = 69637;
    public static final int AV_CODEC_ID_ADPCM_MS = 69638;
    public static final int AV_CODEC_ID_ADPCM_4XM = 69639;
    public static final int AV_CODEC_ID_ADPCM_XA = 69640;
    public static final int AV_CODEC_ID_ADPCM_ADX = 69641;
    public static final int AV_CODEC_ID_ADPCM_EA = 69642;
    public static final int AV_CODEC_ID_ADPCM_G726 = 69643;
    public static final int AV_CODEC_ID_ADPCM_CT = 69644;
    public static final int AV_CODEC_ID_ADPCM_SWF = 69645;
    public static final int AV_CODEC_ID_ADPCM_YAMAHA = 69646;
    public static final int AV_CODEC_ID_ADPCM_SBPRO_4 = 69647;
    public static final int AV_CODEC_ID_ADPCM_SBPRO_3 = 69648;
    public static final int AV_CODEC_ID_ADPCM_SBPRO_2 = 69649;
    public static final int AV_CODEC_ID_ADPCM_THP = 69650;
    public static final int AV_CODEC_ID_ADPCM_IMA_AMV = 69651;
    public static final int AV_CODEC_ID_ADPCM_EA_R1 = 69652;
    public static final int AV_CODEC_ID_ADPCM_EA_R3 = 69653;
    public static final int AV_CODEC_ID_ADPCM_EA_R2 = 69654;
    public static final int AV_CODEC_ID_ADPCM_IMA_EA_SEAD = 69655;
    public static final int AV_CODEC_ID_ADPCM_IMA_EA_EACS = 69656;
    public static final int AV_CODEC_ID_ADPCM_EA_XAS = 69657;
    public static final int AV_CODEC_ID_ADPCM_EA_MAXIS_XA = 69658;
    public static final int AV_CODEC_ID_ADPCM_IMA_ISS = 69659;
    public static final int AV_CODEC_ID_ADPCM_G722 = 69660;
    public static final int AV_CODEC_ID_ADPCM_IMA_APC = 69661;
    public static final int AV_CODEC_ID_ADPCM_VIMA = 69662;
    public static final int AV_CODEC_ID_ADPCM_AFC = 69663;
    public static final int AV_CODEC_ID_ADPCM_IMA_OKI = 69664;
    public static final int AV_CODEC_ID_ADPCM_DTK = 69665;
    public static final int AV_CODEC_ID_ADPCM_IMA_RAD = 69666;
    public static final int AV_CODEC_ID_ADPCM_G726LE = 69667;
    public static final int AV_CODEC_ID_ADPCM_THP_LE = 69668;
    public static final int AV_CODEC_ID_ADPCM_PSX = 69669;
    public static final int AV_CODEC_ID_ADPCM_AICA = 69670;
    public static final int AV_CODEC_ID_ADPCM_IMA_DAT4 = 69671;
    public static final int AV_CODEC_ID_ADPCM_MTAF = 69672;
    public static final int AV_CODEC_ID_ADPCM_AGM = 69673;
    public static final int AV_CODEC_ID_ADPCM_ARGO = 69674;
    public static final int AV_CODEC_ID_ADPCM_IMA_SSI = 69675;
    public static final int AV_CODEC_ID_ADPCM_ZORK = 69676;
    public static final int AV_CODEC_ID_ADPCM_IMA_APM = 69677;
    public static final int AV_CODEC_ID_ADPCM_IMA_ALP = 69678;
    public static final int AV_CODEC_ID_ADPCM_IMA_MTF = 69679;
    public static final int AV_CODEC_ID_ADPCM_IMA_CUNNING = 69680;
    public static final int AV_CODEC_ID_ADPCM_IMA_MOFLEX = 69681;
    public static final int AV_CODEC_ID_ADPCM_IMA_ACORN = 69682;
    public static final int AV_CODEC_ID_ADPCM_XMD = 69683;
    public static final int AV_CODEC_ID_ADPCM_IMA_XBOX = 69684;
    public static final int AV_CODEC_ID_ADPCM_SANYO = 69685;
    public static final int AV_CODEC_ID_ADPCM_IMA_HVQM4 = 69686;
    public static final int AV_CODEC_ID_ADPCM_IMA_PDA = 69687;
    public static final int AV_CODEC_ID_ADPCM_N64 = 69688;
    public static final int AV_CODEC_ID_ADPCM_IMA_HVQM2 = 69689;
    public static final int AV_CODEC_ID_ADPCM_IMA_MAGIX = 69690;
    public static final int AV_CODEC_ID_ADPCM_PSXC = 69691;
    public static final int AV_CODEC_ID_ADPCM_CIRCUS = 69692;
    public static final int AV_CODEC_ID_ADPCM_IMA_ESCAPE = 69693;
    public static final int AV_CODEC_ID_AMR_NB = 73728;
    public static final int AV_CODEC_ID_AMR_WB = 73729;
    public static final int AV_CODEC_ID_RA_144 = 77824;
    public static final int AV_CODEC_ID_RA_288 = 77825;
    public static final int AV_CODEC_ID_ROQ_DPCM = 81920;
    public static final int AV_CODEC_ID_INTERPLAY_DPCM = 81921;
    public static final int AV_CODEC_ID_XAN_DPCM = 81922;
    public static final int AV_CODEC_ID_SOL_DPCM = 81923;
    public static final int AV_CODEC_ID_SDX2_DPCM = 81924;
    public static final int AV_CODEC_ID_GREMLIN_DPCM = 81925;
    public static final int AV_CODEC_ID_DERF_DPCM = 81926;
    public static final int AV_CODEC_ID_WADY_DPCM = 81927;
    public static final int AV_CODEC_ID_CBD2_DPCM = 81928;
    public static final int AV_CODEC_ID_MP2 = 86016;
    public static final int AV_CODEC_ID_MP3 = 86017;
    public static final int AV_CODEC_ID_AAC = 86018;
    public static final int AV_CODEC_ID_AC3 = 86019;
    public static final int AV_CODEC_ID_DTS = 86020;
    public static final int AV_CODEC_ID_VORBIS = 86021;
    public static final int AV_CODEC_ID_DVAUDIO = 86022;
    public static final int AV_CODEC_ID_WMAV1 = 86023;
    public static final int AV_CODEC_ID_WMAV2 = 86024;
    public static final int AV_CODEC_ID_MACE3 = 86025;
    public static final int AV_CODEC_ID_MACE6 = 86026;
    public static final int AV_CODEC_ID_VMDAUDIO = 86027;
    public static final int AV_CODEC_ID_FLAC = 86028;
    public static final int AV_CODEC_ID_MP3ADU = 86029;
    public static final int AV_CODEC_ID_MP3ON4 = 86030;
    public static final int AV_CODEC_ID_SHORTEN = 86031;
    public static final int AV_CODEC_ID_ALAC = 86032;
    public static final int AV_CODEC_ID_WESTWOOD_SND1 = 86033;
    public static final int AV_CODEC_ID_GSM = 86034;
    public static final int AV_CODEC_ID_QDM2 = 86035;
    public static final int AV_CODEC_ID_COOK = 86036;
    public static final int AV_CODEC_ID_TRUESPEECH = 86037;
    public static final int AV_CODEC_ID_TTA = 86038;
    public static final int AV_CODEC_ID_SMACKAUDIO = 86039;
    public static final int AV_CODEC_ID_QCELP = 86040;
    public static final int AV_CODEC_ID_WAVPACK = 86041;
    public static final int AV_CODEC_ID_DSICINAUDIO = 86042;
    public static final int AV_CODEC_ID_IMC = 86043;
    public static final int AV_CODEC_ID_MUSEPACK7 = 86044;
    public static final int AV_CODEC_ID_MLP = 86045;
    public static final int AV_CODEC_ID_GSM_MS = 86046;
    public static final int AV_CODEC_ID_ATRAC3 = 86047;
    public static final int AV_CODEC_ID_APE = 86048;
    public static final int AV_CODEC_ID_NELLYMOSER = 86049;
    public static final int AV_CODEC_ID_MUSEPACK8 = 86050;
    public static final int AV_CODEC_ID_SPEEX = 86051;
    public static final int AV_CODEC_ID_WMAVOICE = 86052;
    public static final int AV_CODEC_ID_WMAPRO = 86053;
    public static final int AV_CODEC_ID_WMALOSSLESS = 86054;
    public static final int AV_CODEC_ID_ATRAC3P = 86055;
    public static final int AV_CODEC_ID_EAC3 = 86056;
    public static final int AV_CODEC_ID_SIPR = 86057;
    public static final int AV_CODEC_ID_MP1 = 86058;
    public static final int AV_CODEC_ID_TWINVQ = 86059;
    public static final int AV_CODEC_ID_TRUEHD = 86060;
    public static final int AV_CODEC_ID_MP4ALS = 86061;
    public static final int AV_CODEC_ID_ATRAC1 = 86062;
    public static final int AV_CODEC_ID_BINKAUDIO_RDFT = 86063;
    public static final int AV_CODEC_ID_BINKAUDIO_DCT = 86064;
    public static final int AV_CODEC_ID_AAC_LATM = 86065;
    public static final int AV_CODEC_ID_QDMC = 86066;
    public static final int AV_CODEC_ID_CELT = 86067;
    public static final int AV_CODEC_ID_G723_1 = 86068;
    public static final int AV_CODEC_ID_G729 = 86069;
    public static final int AV_CODEC_ID_8SVX_EXP = 86070;
    public static final int AV_CODEC_ID_8SVX_FIB = 86071;
    public static final int AV_CODEC_ID_BMV_AUDIO = 86072;
    public static final int AV_CODEC_ID_RALF = 86073;
    public static final int AV_CODEC_ID_IAC = 86074;
    public static final int AV_CODEC_ID_ILBC = 86075;
    public static final int AV_CODEC_ID_OPUS = 86076;
    public static final int AV_CODEC_ID_COMFORT_NOISE = 86077;
    public static final int AV_CODEC_ID_TAK = 86078;
    public static final int AV_CODEC_ID_METASOUND = 86079;
    public static final int AV_CODEC_ID_PAF_AUDIO = 86080;
    public static final int AV_CODEC_ID_ON2AVC = 86081;
    public static final int AV_CODEC_ID_DSS_SP = 86082;
    public static final int AV_CODEC_ID_CODEC2 = 86083;
    public static final int AV_CODEC_ID_FFWAVESYNTH = 86084;
    public static final int AV_CODEC_ID_SONIC = 86085;
    public static final int AV_CODEC_ID_SONIC_LS = 86086;
    public static final int AV_CODEC_ID_EVRC = 86087;
    public static final int AV_CODEC_ID_SMV = 86088;
    public static final int AV_CODEC_ID_DSD_LSBF = 86089;
    public static final int AV_CODEC_ID_DSD_MSBF = 86090;
    public static final int AV_CODEC_ID_DSD_LSBF_PLANAR = 86091;
    public static final int AV_CODEC_ID_DSD_MSBF_PLANAR = 86092;
    public static final int AV_CODEC_ID_4GV = 86093;
    public static final int AV_CODEC_ID_INTERPLAY_ACM = 86094;
    public static final int AV_CODEC_ID_XMA1 = 86095;
    public static final int AV_CODEC_ID_XMA2 = 86096;
    public static final int AV_CODEC_ID_DST = 86097;
    public static final int AV_CODEC_ID_ATRAC3AL = 86098;
    public static final int AV_CODEC_ID_ATRAC3PAL = 86099;
    public static final int AV_CODEC_ID_DOLBY_E = 86100;
    public static final int AV_CODEC_ID_APTX = 86101;
    public static final int AV_CODEC_ID_APTX_HD = 86102;
    public static final int AV_CODEC_ID_SBC = 86103;
    public static final int AV_CODEC_ID_ATRAC9 = 86104;
    public static final int AV_CODEC_ID_HCOM = 86105;
    public static final int AV_CODEC_ID_ACELP_KELVIN = 86106;
    public static final int AV_CODEC_ID_MPEGH_3D_AUDIO = 86107;
    public static final int AV_CODEC_ID_SIREN = 86108;
    public static final int AV_CODEC_ID_HCA = 86109;
    public static final int AV_CODEC_ID_FASTAUDIO = 86110;
    public static final int AV_CODEC_ID_MSNSIREN = 86111;
    public static final int AV_CODEC_ID_DFPWM = 86112;
    public static final int AV_CODEC_ID_BONK = 86113;
    public static final int AV_CODEC_ID_MISC4 = 86114;
    public static final int AV_CODEC_ID_APAC = 86115;
    public static final int AV_CODEC_ID_FTR = 86116;
    public static final int AV_CODEC_ID_WAVARC = 86117;
    public static final int AV_CODEC_ID_RKA = 86118;
    public static final int AV_CODEC_ID_AC4 = 86119;
    public static final int AV_CODEC_ID_OSQ = 86120;
    public static final int AV_CODEC_ID_QOA = 86121;
    public static final int AV_CODEC_ID_LC3 = 86122;
    public static final int AV_CODEC_ID_G728 = 86123;
    public static final int AV_CODEC_ID_AHX = 86124;
    public static final int AV_CODEC_ID_FIRST_SUBTITLE = 94208;
    public static final int AV_CODEC_ID_DVD_SUBTITLE = 94208;
    public static final int AV_CODEC_ID_DVB_SUBTITLE = 94209;
    public static final int AV_CODEC_ID_TEXT = 94210;
    public static final int AV_CODEC_ID_XSUB = 94211;
    public static final int AV_CODEC_ID_SSA = 94212;
    public static final int AV_CODEC_ID_MOV_TEXT = 94213;
    public static final int AV_CODEC_ID_HDMV_PGS_SUBTITLE = 94214;
    public static final int AV_CODEC_ID_DVB_TELETEXT = 94215;
    public static final int AV_CODEC_ID_SRT = 94216;
    public static final int AV_CODEC_ID_MICRODVD = 94217;
    public static final int AV_CODEC_ID_EIA_608 = 94218;
    public static final int AV_CODEC_ID_JACOSUB = 94219;
    public static final int AV_CODEC_ID_SAMI = 94220;
    public static final int AV_CODEC_ID_REALTEXT = 94221;
    public static final int AV_CODEC_ID_STL = 94222;
    public static final int AV_CODEC_ID_SUBVIEWER1 = 94223;
    public static final int AV_CODEC_ID_SUBVIEWER = 94224;
    public static final int AV_CODEC_ID_SUBRIP = 94225;
    public static final int AV_CODEC_ID_WEBVTT = 94226;
    public static final int AV_CODEC_ID_MPL2 = 94227;
    public static final int AV_CODEC_ID_VPLAYER = 94228;
    public static final int AV_CODEC_ID_PJS = 94229;
    public static final int AV_CODEC_ID_ASS = 94230;
    public static final int AV_CODEC_ID_HDMV_TEXT_SUBTITLE = 94231;
    public static final int AV_CODEC_ID_TTML = 94232;
    public static final int AV_CODEC_ID_ARIB_CAPTION = 94233;
    public static final int AV_CODEC_ID_IVTV_VBI = 94234;
    public static final int AV_CODEC_ID_FIRST_UNKNOWN = 98304;
    public static final int AV_CODEC_ID_TTF = 98304;
    public static final int AV_CODEC_ID_SCTE_35 = 98305;
    public static final int AV_CODEC_ID_EPG = 98306;
    public static final int AV_CODEC_ID_BINTEXT = 98307;
    public static final int AV_CODEC_ID_XBIN = 98308;
    public static final int AV_CODEC_ID_IDF = 98309;
    public static final int AV_CODEC_ID_OTF = 98310;
    public static final int AV_CODEC_ID_SMPTE_KLV = 98311;
    public static final int AV_CODEC_ID_DVD_NAV = 98312;
    public static final int AV_CODEC_ID_TIMED_ID3 = 98313;
    public static final int AV_CODEC_ID_BIN_DATA = 98314;
    public static final int AV_CODEC_ID_SMPTE_2038 = 98315;
    public static final int AV_CODEC_ID_LCEVC = 98316;
    public static final int AV_CODEC_ID_SMPTE_436M_ANC = 98317;
    public static final int AV_CODEC_ID_PROBE = 102400;
    public static final int AV_CODEC_ID_MPEG2TS = 131072;
    public static final int AV_CODEC_ID_MPEG4SYSTEMS = 131073;
    public static final int AV_CODEC_ID_FFMETADATA = 135168;
    public static final int AV_CODEC_ID_WRAPPED_AVFRAME = 135169;
    public static final int AV_CODEC_ID_VNULL = 135170;
    public static final int AV_CODEC_ID_ANULL = 135171;
    public static final int AV_CODEC_PROP_INTRA_ONLY = 1;
    public static final int AV_CODEC_PROP_LOSSY = 2;
    public static final int AV_CODEC_PROP_LOSSLESS = 4;
    public static final int AV_CODEC_PROP_REORDER = 8;
    public static final int AV_CODEC_PROP_FIELDS = 16;
    public static final int AV_CODEC_PROP_ENHANCEMENT = 32;
    public static final int AV_CODEC_PROP_BITMAP_SUB = 65536;
    public static final int AV_CODEC_PROP_TEXT_SUB = 131072;
    public static final int AV_INPUT_BUFFER_PADDING_SIZE = 64;
    public static final int AV_EF_CRCCHECK = 1;
    public static final int AV_EF_BITSTREAM = 2;
    public static final int AV_EF_BUFFER = 4;
    public static final int AV_EF_EXPLODE = 8;
    public static final int AV_EF_IGNORE_ERR = 32768;
    public static final int AV_EF_CAREFUL = 65536;
    public static final int AV_EF_COMPLIANT = 131072;
    public static final int AV_EF_AGGRESSIVE = 262144;
    public static final int FF_COMPLIANCE_VERY_STRICT = 2;
    public static final int FF_COMPLIANCE_STRICT = 1;
    public static final int FF_COMPLIANCE_NORMAL = 0;
    public static final int FF_COMPLIANCE_UNOFFICIAL = -1;
    public static final int FF_COMPLIANCE_EXPERIMENTAL = -2;
    public static final int AV_PROFILE_UNKNOWN = -99;
    public static final int AV_PROFILE_RESERVED = -100;
    public static final int AV_PROFILE_AAC_MAIN = 0;
    public static final int AV_PROFILE_AAC_LOW = 1;
    public static final int AV_PROFILE_AAC_SSR = 2;
    public static final int AV_PROFILE_AAC_LTP = 3;
    public static final int AV_PROFILE_AAC_HE = 4;
    public static final int AV_PROFILE_AAC_HE_V2 = 28;
    public static final int AV_PROFILE_AAC_LD = 22;
    public static final int AV_PROFILE_AAC_ELD = 38;
    public static final int AV_PROFILE_AAC_USAC = 41;
    public static final int AV_PROFILE_MPEG2_AAC_LOW = 128;
    public static final int AV_PROFILE_MPEG2_AAC_HE = 131;
    public static final int AV_PROFILE_DNXHD = 0;
    public static final int AV_PROFILE_DNXHR_LB = 1;
    public static final int AV_PROFILE_DNXHR_SQ = 2;
    public static final int AV_PROFILE_DNXHR_HQ = 3;
    public static final int AV_PROFILE_DNXHR_HQX = 4;
    public static final int AV_PROFILE_DNXHR_444 = 5;
    public static final int AV_PROFILE_DTS = 20;
    public static final int AV_PROFILE_DTS_ES = 30;
    public static final int AV_PROFILE_DTS_96_24 = 40;
    public static final int AV_PROFILE_DTS_HD_HRA = 50;
    public static final int AV_PROFILE_DTS_HD_MA = 60;
    public static final int AV_PROFILE_DTS_EXPRESS = 70;
    public static final int AV_PROFILE_DTS_HD_MA_X = 61;
    public static final int AV_PROFILE_DTS_HD_MA_X_IMAX = 62;
    public static final int AV_PROFILE_EAC3_DDP_ATMOS = 30;
    public static final int AV_PROFILE_TRUEHD_ATMOS = 30;
    public static final int AV_PROFILE_MPEG2_422 = 0;
    public static final int AV_PROFILE_MPEG2_HIGH = 1;
    public static final int AV_PROFILE_MPEG2_SS = 2;
    public static final int AV_PROFILE_MPEG2_SNR_SCALABLE = 3;
    public static final int AV_PROFILE_MPEG2_MAIN = 4;
    public static final int AV_PROFILE_MPEG2_SIMPLE = 5;
    public static final int AV_PROFILE_H264_CONSTRAINED = 512;
    public static final int AV_PROFILE_H264_INTRA = 2048;
    public static final int AV_PROFILE_H264_BASELINE = 66;
    public static final int AV_PROFILE_H264_CONSTRAINED_BASELINE = 578;
    public static final int AV_PROFILE_H264_MAIN = 77;
    public static final int AV_PROFILE_H264_EXTENDED = 88;
    public static final int AV_PROFILE_H264_HIGH = 100;
    public static final int AV_PROFILE_H264_HIGH_10 = 110;
    public static final int AV_PROFILE_H264_HIGH_10_INTRA = 2158;
    public static final int AV_PROFILE_H264_MULTIVIEW_HIGH = 118;
    public static final int AV_PROFILE_H264_HIGH_422 = 122;
    public static final int AV_PROFILE_H264_HIGH_422_INTRA = 2170;
    public static final int AV_PROFILE_H264_STEREO_HIGH = 128;
    public static final int AV_PROFILE_H264_HIGH_444 = 144;
    public static final int AV_PROFILE_H264_HIGH_444_PREDICTIVE = 244;
    public static final int AV_PROFILE_H264_HIGH_444_INTRA = 2292;
    public static final int AV_PROFILE_H264_CAVLC_444 = 44;
    public static final int AV_PROFILE_VC1_SIMPLE = 0;
    public static final int AV_PROFILE_VC1_MAIN = 1;
    public static final int AV_PROFILE_VC1_COMPLEX = 2;
    public static final int AV_PROFILE_VC1_ADVANCED = 3;
    public static final int AV_PROFILE_MPEG4_SIMPLE = 0;
    public static final int AV_PROFILE_MPEG4_SIMPLE_SCALABLE = 1;
    public static final int AV_PROFILE_MPEG4_CORE = 2;
    public static final int AV_PROFILE_MPEG4_MAIN = 3;
    public static final int AV_PROFILE_MPEG4_N_BIT = 4;
    public static final int AV_PROFILE_MPEG4_SCALABLE_TEXTURE = 5;
    public static final int AV_PROFILE_MPEG4_SIMPLE_FACE_ANIMATION = 6;
    public static final int AV_PROFILE_MPEG4_BASIC_ANIMATED_TEXTURE = 7;
    public static final int AV_PROFILE_MPEG4_HYBRID = 8;
    public static final int AV_PROFILE_MPEG4_ADVANCED_REAL_TIME = 9;
    public static final int AV_PROFILE_MPEG4_CORE_SCALABLE = 10;
    public static final int AV_PROFILE_MPEG4_ADVANCED_CODING = 11;
    public static final int AV_PROFILE_MPEG4_ADVANCED_CORE = 12;
    public static final int AV_PROFILE_MPEG4_ADVANCED_SCALABLE_TEXTURE = 13;
    public static final int AV_PROFILE_MPEG4_SIMPLE_STUDIO = 14;
    public static final int AV_PROFILE_MPEG4_ADVANCED_SIMPLE = 15;
    public static final int AV_PROFILE_JPEG2000_CSTREAM_RESTRICTION_0 = 1;
    public static final int AV_PROFILE_JPEG2000_CSTREAM_RESTRICTION_1 = 2;
    public static final int AV_PROFILE_JPEG2000_CSTREAM_NO_RESTRICTION = 32768;
    public static final int AV_PROFILE_JPEG2000_DCINEMA_2K = 3;
    public static final int AV_PROFILE_JPEG2000_DCINEMA_4K = 4;
    public static final int AV_PROFILE_VP9_0 = 0;
    public static final int AV_PROFILE_VP9_1 = 1;
    public static final int AV_PROFILE_VP9_2 = 2;
    public static final int AV_PROFILE_VP9_3 = 3;
    public static final int AV_PROFILE_HEVC_MAIN = 1;
    public static final int AV_PROFILE_HEVC_MAIN_10 = 2;
    public static final int AV_PROFILE_HEVC_MAIN_STILL_PICTURE = 3;
    public static final int AV_PROFILE_HEVC_REXT = 4;
    public static final int AV_PROFILE_HEVC_MULTIVIEW_MAIN = 6;
    public static final int AV_PROFILE_HEVC_SCC = 9;
    public static final int AV_PROFILE_VVC_MAIN_10 = 1;
    public static final int AV_PROFILE_VVC_MAIN_10_444 = 33;
    public static final int AV_PROFILE_AV1_MAIN = 0;
    public static final int AV_PROFILE_AV1_HIGH = 1;
    public static final int AV_PROFILE_AV1_PROFESSIONAL = 2;
    public static final int AV_PROFILE_MJPEG_HUFFMAN_BASELINE_DCT = 192;
    public static final int AV_PROFILE_MJPEG_HUFFMAN_EXTENDED_SEQUENTIAL_DCT = 193;
    public static final int AV_PROFILE_MJPEG_HUFFMAN_PROGRESSIVE_DCT = 194;
    public static final int AV_PROFILE_MJPEG_HUFFMAN_LOSSLESS = 195;
    public static final int AV_PROFILE_MJPEG_JPEG_LS = 247;
    public static final int AV_PROFILE_SBC_MSBC = 1;
    public static final int AV_PROFILE_PRORES_PROXY = 0;
    public static final int AV_PROFILE_PRORES_LT = 1;
    public static final int AV_PROFILE_PRORES_STANDARD = 2;
    public static final int AV_PROFILE_PRORES_HQ = 3;
    public static final int AV_PROFILE_PRORES_4444 = 4;
    public static final int AV_PROFILE_PRORES_XQ = 5;
    public static final int AV_PROFILE_PRORES_RAW = 0;
    public static final int AV_PROFILE_PRORES_RAW_HQ = 1;
    public static final int AV_PROFILE_ARIB_PROFILE_A = 0;
    public static final int AV_PROFILE_ARIB_PROFILE_C = 1;
    public static final int AV_PROFILE_KLVA_SYNC = 0;
    public static final int AV_PROFILE_KLVA_ASYNC = 1;
    public static final int AV_PROFILE_EVC_BASELINE = 0;
    public static final int AV_PROFILE_EVC_MAIN = 1;
    public static final int AV_PROFILE_APV_422_10 = 33;
    public static final int AV_PROFILE_APV_422_12 = 44;
    public static final int AV_PROFILE_APV_444_10 = 55;
    public static final int AV_PROFILE_APV_444_12 = 66;
    public static final int AV_PROFILE_APV_4444_10 = 77;
    public static final int AV_PROFILE_APV_4444_12 = 88;
    public static final int AV_PROFILE_APV_400_10 = 99;
    public static final int AV_LEVEL_UNKNOWN = -99;
    public static final int AV_FIELD_UNKNOWN = 0;
    public static final int AV_FIELD_PROGRESSIVE = 1;
    public static final int AV_FIELD_TT = 2;
    public static final int AV_FIELD_BB = 3;
    public static final int AV_FIELD_TB = 4;
    public static final int AV_FIELD_BT = 5;
    public static final int AVDISCARD_NONE = -16;
    public static final int AVDISCARD_DEFAULT = 0;
    public static final int AVDISCARD_NONREF = 8;
    public static final int AVDISCARD_BIDIR = 16;
    public static final int AVDISCARD_NONINTRA = 24;
    public static final int AVDISCARD_NONKEY = 32;
    public static final int AVDISCARD_ALL = 48;
    public static final int AV_AUDIO_SERVICE_TYPE_MAIN = 0;
    public static final int AV_AUDIO_SERVICE_TYPE_EFFECTS = 1;
    public static final int AV_AUDIO_SERVICE_TYPE_VISUALLY_IMPAIRED = 2;
    public static final int AV_AUDIO_SERVICE_TYPE_HEARING_IMPAIRED = 3;
    public static final int AV_AUDIO_SERVICE_TYPE_DIALOGUE = 4;
    public static final int AV_AUDIO_SERVICE_TYPE_COMMENTARY = 5;
    public static final int AV_AUDIO_SERVICE_TYPE_EMERGENCY = 6;
    public static final int AV_AUDIO_SERVICE_TYPE_VOICE_OVER = 7;
    public static final int AV_AUDIO_SERVICE_TYPE_KARAOKE = 8;
    public static final int AV_AUDIO_SERVICE_TYPE_NB = 9;
    public static final int AV_PKT_DATA_PALETTE = 0;
    public static final int AV_PKT_DATA_NEW_EXTRADATA = 1;
    public static final int AV_PKT_DATA_PARAM_CHANGE = 2;
    public static final int AV_PKT_DATA_H263_MB_INFO = 3;
    public static final int AV_PKT_DATA_REPLAYGAIN = 4;
    public static final int AV_PKT_DATA_DISPLAYMATRIX = 5;
    public static final int AV_PKT_DATA_STEREO3D = 6;
    public static final int AV_PKT_DATA_AUDIO_SERVICE_TYPE = 7;
    public static final int AV_PKT_DATA_QUALITY_STATS = 8;
    public static final int AV_PKT_DATA_FALLBACK_TRACK = 9;
    public static final int AV_PKT_DATA_CPB_PROPERTIES = 10;
    public static final int AV_PKT_DATA_SKIP_SAMPLES = 11;
    public static final int AV_PKT_DATA_JP_DUALMONO = 12;
    public static final int AV_PKT_DATA_STRINGS_METADATA = 13;
    public static final int AV_PKT_DATA_SUBTITLE_POSITION = 14;
    public static final int AV_PKT_DATA_MATROSKA_BLOCKADDITIONAL = 15;
    public static final int AV_PKT_DATA_WEBVTT_IDENTIFIER = 16;
    public static final int AV_PKT_DATA_WEBVTT_SETTINGS = 17;
    public static final int AV_PKT_DATA_METADATA_UPDATE = 18;
    public static final int AV_PKT_DATA_MPEGTS_STREAM_ID = 19;
    public static final int AV_PKT_DATA_MASTERING_DISPLAY_METADATA = 20;
    public static final int AV_PKT_DATA_SPHERICAL = 21;
    public static final int AV_PKT_DATA_CONTENT_LIGHT_LEVEL = 22;
    public static final int AV_PKT_DATA_A53_CC = 23;
    public static final int AV_PKT_DATA_ENCRYPTION_INIT_INFO = 24;
    public static final int AV_PKT_DATA_ENCRYPTION_INFO = 25;
    public static final int AV_PKT_DATA_AFD = 26;
    public static final int AV_PKT_DATA_PRFT = 27;
    public static final int AV_PKT_DATA_ICC_PROFILE = 28;
    public static final int AV_PKT_DATA_DOVI_CONF = 29;
    public static final int AV_PKT_DATA_S12M_TIMECODE = 30;
    public static final int AV_PKT_DATA_DYNAMIC_HDR10_PLUS = 31;
    public static final int AV_PKT_DATA_IAMF_MIX_GAIN_PARAM = 32;
    public static final int AV_PKT_DATA_IAMF_DEMIXING_INFO_PARAM = 33;
    public static final int AV_PKT_DATA_IAMF_RECON_GAIN_INFO_PARAM = 34;
    public static final int AV_PKT_DATA_AMBIENT_VIEWING_ENVIRONMENT = 35;
    public static final int AV_PKT_DATA_FRAME_CROPPING = 36;
    public static final int AV_PKT_DATA_LCEVC = 37;
    public static final int AV_PKT_DATA_3D_REFERENCE_DISPLAYS = 38;
    public static final int AV_PKT_DATA_RTCP_SR = 39;
    public static final int AV_PKT_DATA_EXIF = 40;
    public static final int AV_PKT_DATA_NB = 41;
    public static final int AV_PKT_FLAG_KEY = 1;
    public static final int AV_PKT_FLAG_CORRUPT = 2;
    public static final int AV_PKT_FLAG_DISCARD = 4;
    public static final int AV_PKT_FLAG_TRUSTED = 8;
    public static final int AV_PKT_FLAG_DISPOSABLE = 16;
    public static final int AV_SIDE_DATA_PARAM_CHANGE_SAMPLE_RATE = 4;
    public static final int AV_SIDE_DATA_PARAM_CHANGE_DIMENSIONS = 8;
    public static final int AV_CODEC_CAP_DRAW_HORIZ_BAND = 1;
    public static final int AV_CODEC_CAP_DR1 = 2;
    public static final int AV_CODEC_CAP_DELAY = 32;
    public static final int AV_CODEC_CAP_SMALL_LAST_FRAME = 64;
    public static final int AV_CODEC_CAP_EXPERIMENTAL = 512;
    public static final int AV_CODEC_CAP_CHANNEL_CONF = 1024;
    public static final int AV_CODEC_CAP_FRAME_THREADS = 4096;
    public static final int AV_CODEC_CAP_SLICE_THREADS = 8192;
    public static final int AV_CODEC_CAP_PARAM_CHANGE = 16384;
    public static final int AV_CODEC_CAP_OTHER_THREADS = 32768;
    public static final int AV_CODEC_CAP_VARIABLE_FRAME_SIZE = 65536;
    public static final int AV_CODEC_CAP_AVOID_PROBING = 131072;
    public static final int AV_CODEC_CAP_HARDWARE = 262144;
    public static final int AV_CODEC_CAP_HYBRID = 524288;
    public static final int AV_CODEC_CAP_ENCODER_REORDERED_OPAQUE = 0x100000;
    public static final int AV_CODEC_CAP_ENCODER_FLUSH = 0x200000;
    public static final int AV_CODEC_CAP_ENCODER_RECON_FRAME = 0x400000;
    public static final int AV_CODEC_HW_CONFIG_METHOD_HW_DEVICE_CTX = 1;
    public static final int AV_CODEC_HW_CONFIG_METHOD_HW_FRAMES_CTX = 2;
    public static final int AV_CODEC_HW_CONFIG_METHOD_INTERNAL = 4;
    public static final int AV_CODEC_HW_CONFIG_METHOD_AD_HOC = 8;
    public static final int AV_CODEC_FLAG_UNALIGNED = 1;
    public static final int AV_CODEC_FLAG_QSCALE = 2;
    public static final int AV_CODEC_FLAG_4MV = 4;
    public static final int AV_CODEC_FLAG_OUTPUT_CORRUPT = 8;
    public static final int AV_CODEC_FLAG_QPEL = 16;
    public static final int AV_CODEC_FLAG_RECON_FRAME = 64;
    public static final int AV_CODEC_FLAG_COPY_OPAQUE = 128;
    public static final int AV_CODEC_FLAG_FRAME_DURATION = 256;
    public static final int AV_CODEC_FLAG_PASS1 = 512;
    public static final int AV_CODEC_FLAG_PASS2 = 1024;
    public static final int AV_CODEC_FLAG_LOOP_FILTER = 2048;
    public static final int AV_CODEC_FLAG_GRAY = 8192;
    public static final int AV_CODEC_FLAG_PSNR = 32768;
    public static final int AV_CODEC_FLAG_INTERLACED_DCT = 262144;
    public static final int AV_CODEC_FLAG_LOW_DELAY = 524288;
    public static final int AV_CODEC_FLAG_GLOBAL_HEADER = 0x400000;
    public static final int AV_CODEC_FLAG_BITEXACT = 0x800000;
    public static final int AV_CODEC_FLAG_AC_PRED = 0x1000000;
    public static final int AV_CODEC_FLAG_INTERLACED_ME = 0x20000000;
    public static final int AV_CODEC_FLAG_CLOSED_GOP = Integer.MIN_VALUE;
    public static final int AV_CODEC_FLAG2_FAST = 1;
    public static final int AV_CODEC_FLAG2_NO_OUTPUT = 4;
    public static final int AV_CODEC_FLAG2_LOCAL_HEADER = 8;
    public static final int AV_CODEC_FLAG2_CHUNKS = 32768;
    public static final int AV_CODEC_FLAG2_IGNORE_CROP = 65536;
    public static final int AV_CODEC_FLAG2_SHOW_ALL = 0x400000;
    public static final int AV_CODEC_FLAG2_EXPORT_MVS = 0x10000000;
    public static final int AV_CODEC_FLAG2_SKIP_MANUAL = 0x20000000;
    public static final int AV_CODEC_FLAG2_RO_FLUSH_NOOP = 0x40000000;
    public static final int AV_CODEC_FLAG2_ICC_PROFILES = Integer.MIN_VALUE;
    public static final int AV_CODEC_EXPORT_DATA_MVS = 1;
    public static final int AV_CODEC_EXPORT_DATA_PRFT = 2;
    public static final int AV_CODEC_EXPORT_DATA_VIDEO_ENC_PARAMS = 4;
    public static final int AV_CODEC_EXPORT_DATA_FILM_GRAIN = 8;
    public static final int AV_CODEC_EXPORT_DATA_ENHANCEMENTS = 16;
    public static final int AV_GET_BUFFER_FLAG_REF = 1;
    public static final int AV_GET_ENCODE_BUFFER_FLAG_REF = 1;
    public static final int AV_CODEC_RECEIVE_FRAME_FLAG_SYNCHRONOUS = 1;
    public static final int AV_HWACCEL_CODEC_CAP_EXPERIMENTAL = 512;
    public static final int AV_HWACCEL_FLAG_IGNORE_LEVEL = 1;
    public static final int AV_HWACCEL_FLAG_ALLOW_HIGH_DEPTH = 2;
    public static final int AV_HWACCEL_FLAG_ALLOW_PROFILE_MISMATCH = 4;
    public static final int AV_HWACCEL_FLAG_UNSAFE_OUTPUT = 8;
    public static final int SUBTITLE_NONE = 0;
    public static final int SUBTITLE_BITMAP = 1;
    public static final int SUBTITLE_TEXT = 2;
    public static final int SUBTITLE_ASS = 3;
    public static final int AV_SUBTITLE_FLAG_FORCED = 1;
    public static final int AV_CODEC_CONFIG_PIX_FORMAT = 0;
    public static final int AV_CODEC_CONFIG_FRAME_RATE = 1;
    public static final int AV_CODEC_CONFIG_SAMPLE_RATE = 2;
    public static final int AV_CODEC_CONFIG_SAMPLE_FORMAT = 3;
    public static final int AV_CODEC_CONFIG_CHANNEL_LAYOUT = 4;
    public static final int AV_CODEC_CONFIG_COLOR_RANGE = 5;
    public static final int AV_CODEC_CONFIG_COLOR_SPACE = 6;
    public static final int AV_CODEC_CONFIG_ALPHA_MODE = 7;
    public static final int AV_PICTURE_STRUCTURE_UNKNOWN = 0;
    public static final int AV_PICTURE_STRUCTURE_TOP_FIELD = 1;
    public static final int AV_PICTURE_STRUCTURE_BOTTOM_FIELD = 2;
    public static final int AV_PICTURE_STRUCTURE_FRAME = 3;
    public static final int LIBAVCODEC_VERSION_MAJOR = 62;
    public static final boolean FF_API_INIT_PACKET = true;
    public static final boolean FF_API_V408_CODECID = true;
    public static final boolean FF_API_CODEC_PROPS = true;
    public static final boolean FF_API_EXR_GAMMA = true;
    public static final boolean FF_API_INTRA_DC_PRECISION = true;
    public static final boolean FF_API_NVDEC_OLD_PIX_FMTS = true;
    public static final boolean FF_API_PARSER_PRIVATE = true;
    public static final boolean FF_API_PARSER_CODECID = true;
    public static final boolean FF_CODEC_OMX = true;
    public static final boolean FF_CODEC_SONIC_ENC = true;
    public static final boolean FF_CODEC_SONIC_DEC = true;
    public static final int LIBAVCODEC_VERSION_MINOR = 28;
    public static final int LIBAVCODEC_VERSION_MICRO = 102;
    public static final int LIBAVCODEC_VERSION_INT;
    public static final int LIBAVCODEC_BUILD;
    public static final String LIBAVCODEC_IDENT;

    @NoException
    @Cast(value={"AVMediaType"})
    public static native int avcodec_get_type(@Cast(value={"AVCodecID"}) int var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer avcodec_get_name(@Cast(value={"AVCodecID"}) int var0);

    @NoException
    public static native int av_get_bits_per_sample(@Cast(value={"AVCodecID"}) int var0);

    @NoException
    public static native int av_get_exact_bits_per_sample(@Cast(value={"AVCodecID"}) int var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer avcodec_profile_name(@Cast(value={"AVCodecID"}) int var0, int var1);

    @NoException
    @Cast(value={"AVCodecID"})
    public static native int av_get_pcm_codec(@Cast(value={"AVSampleFormat"}) int var0, int var1);

    @NoException
    @Const
    public static native AVCodecDescriptor avcodec_descriptor_get(@Cast(value={"AVCodecID"}) int var0);

    @NoException
    @Const
    public static native AVCodecDescriptor avcodec_descriptor_next(@Const AVCodecDescriptor var0);

    @NoException
    @Const
    public static native AVCodecDescriptor avcodec_descriptor_get_by_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    @Const
    public static native AVCodecDescriptor avcodec_descriptor_get_by_name(String var0);

    @NoException
    public static native AVCPBProperties av_cpb_properties_alloc(@Cast(value={"size_t*"}) SizeTPointer var0);

    @NoException
    @Cast(value={"unsigned int"})
    public static native int av_xiphlacing(@Cast(value={"unsigned char*"}) BytePointer var0, @Cast(value={"unsigned int"}) int var1);

    @NoException
    @Cast(value={"unsigned int"})
    public static native int av_xiphlacing(@Cast(value={"unsigned char*"}) ByteBuffer var0, @Cast(value={"unsigned int"}) int var1);

    @NoException
    @Cast(value={"unsigned int"})
    public static native int av_xiphlacing(@Cast(value={"unsigned char*"}) byte[] var0, @Cast(value={"unsigned int"}) int var1);

    @NoException
    public static native AVCodecParameters avcodec_parameters_alloc();

    @NoException
    public static native void avcodec_parameters_free(@Cast(value={"AVCodecParameters**"}) PointerPointer var0);

    @NoException
    public static native void avcodec_parameters_free(@ByPtrPtr AVCodecParameters var0);

    @NoException
    public static native int avcodec_parameters_copy(AVCodecParameters var0, @Const AVCodecParameters var1);

    @NoException
    public static native int av_get_audio_frame_duration2(AVCodecParameters var0, int var1);

    @NoException
    public static native AVPacketSideData av_packet_side_data_new(@Cast(value={"AVPacketSideData**"}) PointerPointer var0, IntPointer var1, @Cast(value={"AVPacketSideDataType"}) int var2, @Cast(value={"size_t"}) long var3, int var5);

    @NoException
    public static native AVPacketSideData av_packet_side_data_new(@ByPtrPtr AVPacketSideData var0, IntPointer var1, @Cast(value={"AVPacketSideDataType"}) int var2, @Cast(value={"size_t"}) long var3, int var5);

    @NoException
    public static native AVPacketSideData av_packet_side_data_new(@ByPtrPtr AVPacketSideData var0, IntBuffer var1, @Cast(value={"AVPacketSideDataType"}) int var2, @Cast(value={"size_t"}) long var3, int var5);

    @NoException
    public static native AVPacketSideData av_packet_side_data_new(@ByPtrPtr AVPacketSideData var0, int[] var1, @Cast(value={"AVPacketSideDataType"}) int var2, @Cast(value={"size_t"}) long var3, int var5);

    @NoException
    public static native AVPacketSideData av_packet_side_data_add(@Cast(value={"AVPacketSideData**"}) PointerPointer var0, IntPointer var1, @Cast(value={"AVPacketSideDataType"}) int var2, Pointer var3, @Cast(value={"size_t"}) long var4, int var6);

    @NoException
    public static native AVPacketSideData av_packet_side_data_add(@ByPtrPtr AVPacketSideData var0, IntPointer var1, @Cast(value={"AVPacketSideDataType"}) int var2, Pointer var3, @Cast(value={"size_t"}) long var4, int var6);

    @NoException
    public static native AVPacketSideData av_packet_side_data_add(@ByPtrPtr AVPacketSideData var0, IntBuffer var1, @Cast(value={"AVPacketSideDataType"}) int var2, Pointer var3, @Cast(value={"size_t"}) long var4, int var6);

    @NoException
    public static native AVPacketSideData av_packet_side_data_add(@ByPtrPtr AVPacketSideData var0, int[] var1, @Cast(value={"AVPacketSideDataType"}) int var2, Pointer var3, @Cast(value={"size_t"}) long var4, int var6);

    @NoException
    @Const
    public static native AVPacketSideData av_packet_side_data_get(@Const AVPacketSideData var0, int var1, @Cast(value={"AVPacketSideDataType"}) int var2);

    @NoException
    public static native void av_packet_side_data_remove(AVPacketSideData var0, IntPointer var1, @Cast(value={"AVPacketSideDataType"}) int var2);

    @NoException
    public static native void av_packet_side_data_remove(AVPacketSideData var0, IntBuffer var1, @Cast(value={"AVPacketSideDataType"}) int var2);

    @NoException
    public static native void av_packet_side_data_remove(AVPacketSideData var0, int[] var1, @Cast(value={"AVPacketSideDataType"}) int var2);

    @NoException
    public static native void av_packet_side_data_free(@Cast(value={"AVPacketSideData**"}) PointerPointer var0, IntPointer var1);

    @NoException
    public static native void av_packet_side_data_free(@ByPtrPtr AVPacketSideData var0, IntPointer var1);

    @NoException
    public static native void av_packet_side_data_free(@ByPtrPtr AVPacketSideData var0, IntBuffer var1);

    @NoException
    public static native void av_packet_side_data_free(@ByPtrPtr AVPacketSideData var0, int[] var1);

    @NoException
    public static native int av_packet_side_data_from_frame(@Cast(value={"AVPacketSideData**"}) PointerPointer var0, IntPointer var1, @Const AVFrameSideData var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    public static native int av_packet_side_data_from_frame(@ByPtrPtr AVPacketSideData var0, IntPointer var1, @Const AVFrameSideData var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    public static native int av_packet_side_data_from_frame(@ByPtrPtr AVPacketSideData var0, IntBuffer var1, @Const AVFrameSideData var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    public static native int av_packet_side_data_from_frame(@ByPtrPtr AVPacketSideData var0, int[] var1, @Const AVFrameSideData var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    public static native int av_packet_side_data_to_frame(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntPointer var1, @Const AVPacketSideData var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    public static native int av_packet_side_data_to_frame(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, IntBuffer var1, @Const AVPacketSideData var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    public static native int av_packet_side_data_to_frame(@Cast(value={"AVFrameSideData***"}) @ByPtrPtr PointerPointer var0, int[] var1, @Const AVPacketSideData var2, @Cast(value={"unsigned int"}) int var3);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_packet_side_data_name(@Cast(value={"AVPacketSideDataType"}) int var0);

    @NoException
    public static native AVPacket av_packet_alloc();

    @NoException
    public static native AVPacket av_packet_clone(@Const AVPacket var0);

    @NoException
    public static native void av_packet_free(@Cast(value={"AVPacket**"}) PointerPointer var0);

    @NoException
    public static native void av_packet_free(@ByPtrPtr AVPacket var0);

    @NoException
    @Deprecated
    public static native void av_init_packet(AVPacket var0);

    @NoException
    public static native int av_new_packet(AVPacket var0, int var1);

    @NoException
    public static native void av_shrink_packet(AVPacket var0, int var1);

    @NoException
    public static native int av_grow_packet(AVPacket var0, int var1);

    @NoException
    public static native int av_packet_from_data(AVPacket var0, @Cast(value={"uint8_t*"}) BytePointer var1, int var2);

    @NoException
    public static native int av_packet_from_data(AVPacket var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, int var2);

    @NoException
    public static native int av_packet_from_data(AVPacket var0, @Cast(value={"uint8_t*"}) byte[] var1, int var2);

    @NoException
    @Cast(value={"uint8_t*"})
    public static native BytePointer av_packet_new_side_data(AVPacket var0, @Cast(value={"AVPacketSideDataType"}) int var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int av_packet_add_side_data(AVPacket var0, @Cast(value={"AVPacketSideDataType"}) int var1, @Cast(value={"uint8_t*"}) BytePointer var2, @Cast(value={"size_t"}) long var3);

    @NoException
    public static native int av_packet_add_side_data(AVPacket var0, @Cast(value={"AVPacketSideDataType"}) int var1, @Cast(value={"uint8_t*"}) ByteBuffer var2, @Cast(value={"size_t"}) long var3);

    @NoException
    public static native int av_packet_add_side_data(AVPacket var0, @Cast(value={"AVPacketSideDataType"}) int var1, @Cast(value={"uint8_t*"}) byte[] var2, @Cast(value={"size_t"}) long var3);

    @NoException
    public static native int av_packet_shrink_side_data(AVPacket var0, @Cast(value={"AVPacketSideDataType"}) int var1, @Cast(value={"size_t"}) long var2);

    @NoException
    @Cast(value={"uint8_t*"})
    public static native BytePointer av_packet_get_side_data(@Const AVPacket var0, @Cast(value={"AVPacketSideDataType"}) int var1, @Cast(value={"size_t*"}) SizeTPointer var2);

    @NoException
    @Cast(value={"uint8_t*"})
    public static native BytePointer av_packet_pack_dictionary(AVDictionary var0, @Cast(value={"size_t*"}) SizeTPointer var1);

    @NoException
    public static native int av_packet_unpack_dictionary(@Cast(value={"const uint8_t*"}) BytePointer var0, @Cast(value={"size_t"}) long var1, @Cast(value={"AVDictionary**"}) PointerPointer var3);

    @NoException
    public static native int av_packet_unpack_dictionary(@Cast(value={"const uint8_t*"}) BytePointer var0, @Cast(value={"size_t"}) long var1, @ByPtrPtr AVDictionary var3);

    @NoException
    public static native int av_packet_unpack_dictionary(@Cast(value={"const uint8_t*"}) ByteBuffer var0, @Cast(value={"size_t"}) long var1, @ByPtrPtr AVDictionary var3);

    @NoException
    public static native int av_packet_unpack_dictionary(@Cast(value={"const uint8_t*"}) byte[] var0, @Cast(value={"size_t"}) long var1, @ByPtrPtr AVDictionary var3);

    @NoException
    public static native void av_packet_free_side_data(AVPacket var0);

    @NoException
    public static native int av_packet_ref(AVPacket var0, @Const AVPacket var1);

    @NoException
    public static native void av_packet_unref(AVPacket var0);

    @NoException
    public static native void av_packet_move_ref(AVPacket var0, AVPacket var1);

    @NoException
    public static native int av_packet_copy_props(AVPacket var0, @Const AVPacket var1);

    @NoException
    public static native int av_packet_make_refcounted(AVPacket var0);

    @NoException
    public static native int av_packet_make_writable(AVPacket var0);

    @NoException
    public static native void av_packet_rescale_ts(AVPacket var0, @ByVal AVRational var1, @ByVal AVRational var2);

    @NoException
    public static native AVContainerFifo av_container_fifo_alloc_avpacket(@Cast(value={"unsigned"}) int var0);

    @NoException
    @Const
    public static native AVBitStreamFilter av_bsf_get_by_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    @Const
    public static native AVBitStreamFilter av_bsf_get_by_name(String var0);

    @NoException
    @Const
    public static native AVBitStreamFilter av_bsf_iterate(@Cast(value={"void**"}) PointerPointer var0);

    @NoException
    @Const
    public static native AVBitStreamFilter av_bsf_iterate(@Cast(value={"void**"}) @ByPtrPtr Pointer var0);

    @NoException
    public static native int av_bsf_alloc(@Const AVBitStreamFilter var0, @Cast(value={"AVBSFContext**"}) PointerPointer var1);

    @NoException
    public static native int av_bsf_alloc(@Const AVBitStreamFilter var0, @ByPtrPtr AVBSFContext var1);

    @NoException
    public static native int av_bsf_init(AVBSFContext var0);

    @NoException
    public static native int av_bsf_send_packet(AVBSFContext var0, AVPacket var1);

    @NoException
    public static native int av_bsf_receive_packet(AVBSFContext var0, AVPacket var1);

    @NoException
    public static native void av_bsf_flush(AVBSFContext var0);

    @NoException
    public static native void av_bsf_free(@Cast(value={"AVBSFContext**"}) PointerPointer var0);

    @NoException
    public static native void av_bsf_free(@ByPtrPtr AVBSFContext var0);

    @NoException
    @Const
    public static native AVClass av_bsf_get_class();

    @NoException
    public static native AVBSFList av_bsf_list_alloc();

    @NoException
    public static native void av_bsf_list_free(@Cast(value={"AVBSFList**"}) PointerPointer var0);

    @NoException
    public static native void av_bsf_list_free(@ByPtrPtr AVBSFList var0);

    @NoException
    public static native int av_bsf_list_append(AVBSFList var0, AVBSFContext var1);

    @NoException
    public static native int av_bsf_list_append2(AVBSFList var0, @Cast(value={"const char*"}) BytePointer var1, @Cast(value={"AVDictionary**"}) PointerPointer var2);

    @NoException
    public static native int av_bsf_list_append2(AVBSFList var0, @Cast(value={"const char*"}) BytePointer var1, @ByPtrPtr AVDictionary var2);

    @NoException
    public static native int av_bsf_list_append2(AVBSFList var0, String var1, @ByPtrPtr AVDictionary var2);

    @NoException
    public static native int av_bsf_list_finalize(@Cast(value={"AVBSFList**"}) PointerPointer var0, @Cast(value={"AVBSFContext**"}) PointerPointer var1);

    @NoException
    public static native int av_bsf_list_finalize(@ByPtrPtr AVBSFList var0, @ByPtrPtr AVBSFContext var1);

    @NoException
    public static native int av_bsf_list_parse_str(@Cast(value={"const char*"}) BytePointer var0, @Cast(value={"AVBSFContext**"}) PointerPointer var1);

    @NoException
    public static native int av_bsf_list_parse_str(@Cast(value={"const char*"}) BytePointer var0, @ByPtrPtr AVBSFContext var1);

    @NoException
    public static native int av_bsf_list_parse_str(String var0, @ByPtrPtr AVBSFContext var1);

    @NoException
    public static native int av_bsf_get_null_filter(@Cast(value={"AVBSFContext**"}) PointerPointer var0);

    @NoException
    public static native int av_bsf_get_null_filter(@ByPtrPtr AVBSFContext var0);

    @NoException
    @Const
    public static native AVCodec av_codec_iterate(@Cast(value={"void**"}) PointerPointer var0);

    @NoException
    @Const
    public static native AVCodec av_codec_iterate(@Cast(value={"void**"}) @ByPtrPtr Pointer var0);

    @NoException
    @Const
    public static native AVCodec avcodec_find_decoder(@Cast(value={"AVCodecID"}) int var0);

    @NoException
    @Const
    public static native AVCodec avcodec_find_decoder_by_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    @Const
    public static native AVCodec avcodec_find_decoder_by_name(String var0);

    @NoException
    @Const
    public static native AVCodec avcodec_find_encoder(@Cast(value={"AVCodecID"}) int var0);

    @NoException
    @Const
    public static native AVCodec avcodec_find_encoder_by_name(@Cast(value={"const char*"}) BytePointer var0);

    @NoException
    @Const
    public static native AVCodec avcodec_find_encoder_by_name(String var0);

    @NoException
    public static native int av_codec_is_encoder(@Const AVCodec var0);

    @NoException
    public static native int av_codec_is_decoder(@Const AVCodec var0);

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer av_get_profile_name(@Const AVCodec var0, int var1);

    @NoException
    @Const
    public static native AVCodecHWConfig avcodec_get_hw_config(@Const AVCodec var0, int var1);

    @NoException
    @Cast(value={"unsigned"})
    public static native int avcodec_version();

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer avcodec_configuration();

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer avcodec_license();

    @NoException
    public static native AVCodecContext avcodec_alloc_context3(@Const AVCodec var0);

    @NoException
    public static native void avcodec_free_context(@Cast(value={"AVCodecContext**"}) PointerPointer var0);

    @NoException
    public static native void avcodec_free_context(@ByPtrPtr AVCodecContext var0);

    @NoException
    @Const
    public static native AVClass avcodec_get_class();

    @NoException
    @Const
    public static native AVClass avcodec_get_subtitle_rect_class();

    @NoException
    public static native int avcodec_parameters_from_context(AVCodecParameters var0, @Const AVCodecContext var1);

    @NoException
    public static native int avcodec_parameters_to_context(AVCodecContext var0, @Const AVCodecParameters var1);

    @NoException
    public static native int avcodec_open2(AVCodecContext var0, @Const AVCodec var1, @Cast(value={"AVDictionary**"}) PointerPointer var2);

    @NoException
    public static native int avcodec_open2(AVCodecContext var0, @Const AVCodec var1, @ByPtrPtr AVDictionary var2);

    @NoException
    public static native void avsubtitle_free(AVSubtitle var0);

    @NoException
    public static native int avcodec_default_get_buffer2(AVCodecContext var0, AVFrame var1, int var2);

    @NoException
    public static native int avcodec_default_get_encode_buffer(AVCodecContext var0, AVPacket var1, int var2);

    @NoException
    public static native void avcodec_align_dimensions(AVCodecContext var0, IntPointer var1, IntPointer var2);

    @NoException
    public static native void avcodec_align_dimensions(AVCodecContext var0, IntBuffer var1, IntBuffer var2);

    @NoException
    public static native void avcodec_align_dimensions(AVCodecContext var0, int[] var1, int[] var2);

    @NoException
    public static native void avcodec_align_dimensions2(AVCodecContext var0, IntPointer var1, IntPointer var2, IntPointer var3);

    @NoException
    public static native void avcodec_align_dimensions2(AVCodecContext var0, IntBuffer var1, IntBuffer var2, IntBuffer var3);

    @NoException
    public static native void avcodec_align_dimensions2(AVCodecContext var0, int[] var1, int[] var2, int[] var3);

    @NoException
    public static native int avcodec_decode_subtitle2(AVCodecContext var0, AVSubtitle var1, IntPointer var2, @Const AVPacket var3);

    @NoException
    public static native int avcodec_decode_subtitle2(AVCodecContext var0, AVSubtitle var1, IntBuffer var2, @Const AVPacket var3);

    @NoException
    public static native int avcodec_decode_subtitle2(AVCodecContext var0, AVSubtitle var1, int[] var2, @Const AVPacket var3);

    @NoException
    public static native int avcodec_send_packet(AVCodecContext var0, @Const AVPacket var1);

    @NoException
    public static native int avcodec_receive_frame_flags(AVCodecContext var0, AVFrame var1, @Cast(value={"unsigned"}) int var2);

    @NoException
    public static native int avcodec_receive_frame(AVCodecContext var0, AVFrame var1);

    @NoException
    public static native int avcodec_send_frame(AVCodecContext var0, @Const AVFrame var1);

    @NoException
    public static native int avcodec_receive_packet(AVCodecContext var0, AVPacket var1);

    @NoException
    public static native int avcodec_get_hw_frames_parameters(AVCodecContext var0, AVBufferRef var1, @Cast(value={"AVPixelFormat"}) int var2, @Cast(value={"AVBufferRef**"}) PointerPointer var3);

    @NoException
    public static native int avcodec_get_hw_frames_parameters(AVCodecContext var0, AVBufferRef var1, @Cast(value={"AVPixelFormat"}) int var2, @ByPtrPtr AVBufferRef var3);

    @NoException
    public static native int avcodec_get_supported_config(@Const AVCodecContext var0, @Const AVCodec var1, @Cast(value={"AVCodecConfig"}) int var2, @Cast(value={"unsigned"}) int var3, @Cast(value={"const void**"}) PointerPointer var4, IntPointer var5);

    @NoException
    public static native int avcodec_get_supported_config(@Const AVCodecContext var0, @Const AVCodec var1, @Cast(value={"AVCodecConfig"}) int var2, @Cast(value={"unsigned"}) int var3, @Cast(value={"const void**"}) @ByPtrPtr Pointer var4, IntPointer var5);

    @NoException
    public static native int avcodec_get_supported_config(@Const AVCodecContext var0, @Const AVCodec var1, @Cast(value={"AVCodecConfig"}) int var2, @Cast(value={"unsigned"}) int var3, @Cast(value={"const void**"}) @ByPtrPtr Pointer var4, IntBuffer var5);

    @NoException
    public static native int avcodec_get_supported_config(@Const AVCodecContext var0, @Const AVCodec var1, @Cast(value={"AVCodecConfig"}) int var2, @Cast(value={"unsigned"}) int var3, @Cast(value={"const void**"}) @ByPtrPtr Pointer var4, int[] var5);

    @NoException
    @Const
    public static native AVCodecParser av_parser_iterate(@Cast(value={"void**"}) PointerPointer var0);

    @NoException
    @Const
    public static native AVCodecParser av_parser_iterate(@Cast(value={"void**"}) @ByPtrPtr Pointer var0);

    @NoException
    public static native AVCodecParserContext av_parser_init(int var0);

    @NoException
    public static native int av_parser_parse2(AVCodecParserContext var0, AVCodecContext var1, @Cast(value={"uint8_t**"}) PointerPointer var2, IntPointer var3, @Cast(value={"const uint8_t*"}) BytePointer var4, int var5, @Cast(value={"int64_t"}) long var6, @Cast(value={"int64_t"}) long var8, @Cast(value={"int64_t"}) long var10);

    @NoException
    public static native int av_parser_parse2(AVCodecParserContext var0, AVCodecContext var1, @Cast(value={"uint8_t**"}) @ByPtrPtr BytePointer var2, IntPointer var3, @Cast(value={"const uint8_t*"}) BytePointer var4, int var5, @Cast(value={"int64_t"}) long var6, @Cast(value={"int64_t"}) long var8, @Cast(value={"int64_t"}) long var10);

    @NoException
    public static native int av_parser_parse2(AVCodecParserContext var0, AVCodecContext var1, @Cast(value={"uint8_t**"}) @ByPtrPtr ByteBuffer var2, IntBuffer var3, @Cast(value={"const uint8_t*"}) ByteBuffer var4, int var5, @Cast(value={"int64_t"}) long var6, @Cast(value={"int64_t"}) long var8, @Cast(value={"int64_t"}) long var10);

    @NoException
    public static native int av_parser_parse2(AVCodecParserContext var0, AVCodecContext var1, @Cast(value={"uint8_t**"}) @ByPtrPtr byte[] var2, int[] var3, @Cast(value={"const uint8_t*"}) byte[] var4, int var5, @Cast(value={"int64_t"}) long var6, @Cast(value={"int64_t"}) long var8, @Cast(value={"int64_t"}) long var10);

    @NoException
    public static native void av_parser_close(AVCodecParserContext var0);

    @NoException
    public static native int avcodec_encode_subtitle(AVCodecContext var0, @Cast(value={"uint8_t*"}) BytePointer var1, int var2, @Const AVSubtitle var3);

    @NoException
    public static native int avcodec_encode_subtitle(AVCodecContext var0, @Cast(value={"uint8_t*"}) ByteBuffer var1, int var2, @Const AVSubtitle var3);

    @NoException
    public static native int avcodec_encode_subtitle(AVCodecContext var0, @Cast(value={"uint8_t*"}) byte[] var1, int var2, @Const AVSubtitle var3);

    @NoException
    @Cast(value={"unsigned int"})
    public static native int avcodec_pix_fmt_to_codec_tag(@Cast(value={"AVPixelFormat"}) int var0);

    @NoException
    @Cast(value={"AVPixelFormat"})
    public static native int avcodec_find_best_pix_fmt_of_list(@Cast(value={"const AVPixelFormat*"}) IntPointer var0, @Cast(value={"AVPixelFormat"}) int var1, int var2, IntPointer var3);

    @NoException
    @Cast(value={"AVPixelFormat"})
    public static native int avcodec_find_best_pix_fmt_of_list(@Cast(value={"const AVPixelFormat*"}) IntBuffer var0, @Cast(value={"AVPixelFormat"}) int var1, int var2, IntBuffer var3);

    @NoException
    @Cast(value={"AVPixelFormat"})
    public static native int avcodec_find_best_pix_fmt_of_list(@Cast(value={"const AVPixelFormat*"}) int[] var0, @Cast(value={"AVPixelFormat"}) int var1, int var2, int[] var3);

    @NoException
    @Cast(value={"AVPixelFormat"})
    public static native int avcodec_default_get_format(AVCodecContext var0, @Cast(value={"const AVPixelFormat*"}) IntPointer var1);

    @NoException
    @Cast(value={"AVPixelFormat"})
    public static native int avcodec_default_get_format(AVCodecContext var0, @Cast(value={"const AVPixelFormat*"}) IntBuffer var1);

    @NoException
    @Cast(value={"AVPixelFormat"})
    public static native int avcodec_default_get_format(AVCodecContext var0, @Cast(value={"const AVPixelFormat*"}) int[] var1);

    @NoException
    public static native void avcodec_string(@Cast(value={"char*"}) BytePointer var0, int var1, AVCodecContext var2, int var3);

    @NoException
    public static native void avcodec_string(@Cast(value={"char*"}) ByteBuffer var0, int var1, AVCodecContext var2, int var3);

    @NoException
    public static native void avcodec_string(@Cast(value={"char*"}) byte[] var0, int var1, AVCodecContext var2, int var3);

    @NoException
    public static native int avcodec_default_execute(AVCodecContext var0, Func_AVCodecContext_Pointer var1, Pointer var2, IntPointer var3, int var4, int var5);

    @NoException
    public static native int avcodec_default_execute(AVCodecContext var0, Func_AVCodecContext_Pointer var1, Pointer var2, IntBuffer var3, int var4, int var5);

    @NoException
    public static native int avcodec_default_execute(AVCodecContext var0, Func_AVCodecContext_Pointer var1, Pointer var2, int[] var3, int var4, int var5);

    @NoException
    public static native int avcodec_default_execute2(AVCodecContext var0, Func_AVCodecContext_Pointer_int_int var1, Pointer var2, IntPointer var3, int var4);

    @NoException
    public static native int avcodec_default_execute2(AVCodecContext var0, Func_AVCodecContext_Pointer_int_int var1, Pointer var2, IntBuffer var3, int var4);

    @NoException
    public static native int avcodec_default_execute2(AVCodecContext var0, Func_AVCodecContext_Pointer_int_int var1, Pointer var2, int[] var3, int var4);

    @NoException
    public static native int avcodec_fill_audio_frame(AVFrame var0, int var1, @Cast(value={"AVSampleFormat"}) int var2, @Cast(value={"const uint8_t*"}) BytePointer var3, int var4, int var5);

    @NoException
    public static native int avcodec_fill_audio_frame(AVFrame var0, int var1, @Cast(value={"AVSampleFormat"}) int var2, @Cast(value={"const uint8_t*"}) ByteBuffer var3, int var4, int var5);

    @NoException
    public static native int avcodec_fill_audio_frame(AVFrame var0, int var1, @Cast(value={"AVSampleFormat"}) int var2, @Cast(value={"const uint8_t*"}) byte[] var3, int var4, int var5);

    @NoException
    public static native void avcodec_flush_buffers(AVCodecContext var0);

    @NoException
    public static native int av_get_audio_frame_duration(AVCodecContext var0, int var1);

    @NoException
    public static native void av_fast_padded_malloc(Pointer var0, @Cast(value={"unsigned int*"}) IntPointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_fast_padded_malloc(Pointer var0, @Cast(value={"unsigned int*"}) IntBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_fast_padded_malloc(Pointer var0, @Cast(value={"unsigned int*"}) int[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_fast_padded_mallocz(Pointer var0, @Cast(value={"unsigned int*"}) IntPointer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_fast_padded_mallocz(Pointer var0, @Cast(value={"unsigned int*"}) IntBuffer var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native void av_fast_padded_mallocz(Pointer var0, @Cast(value={"unsigned int*"}) int[] var1, @Cast(value={"size_t"}) long var2);

    @NoException
    public static native int avcodec_is_open(AVCodecContext var0);

    @NoException
    public static native int av_jni_set_java_vm(Pointer var0, Pointer var1);

    @NoException
    public static native Pointer av_jni_get_java_vm(Pointer var0);

    @NoException
    @Platform(value={"android"})
    public static native int av_jni_set_android_app_ctx(Pointer var0, Pointer var1);

    @NoException
    @Platform(value={"android"})
    public static native Pointer av_jni_get_android_app_ctx();

    @MemberGetter
    public static native int LIBAVCODEC_VERSION_INT();

    @MemberGetter
    public static native String LIBAVCODEC_IDENT();

    static {
        Loader.load();
        LIBAVCODEC_BUILD = LIBAVCODEC_VERSION_INT = avcodec.LIBAVCODEC_VERSION_INT();
        LIBAVCODEC_IDENT = avcodec.LIBAVCODEC_IDENT();
    }
}

