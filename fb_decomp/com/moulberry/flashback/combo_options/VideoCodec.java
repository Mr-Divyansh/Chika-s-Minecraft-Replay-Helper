/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.combo_options;

import com.moulberry.flashback.combo_options.ComboOption;
import com.moulberry.flashback.combo_options.VideoContainer;
import com.moulberry.flashback.exporting.PixelFormatHelper;
import java.util.ArrayList;
import java.util.Set;
import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVCodecHWConfig;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacpp.Pointer;

public enum VideoCodec implements ComboOption
{
    H264("H264 (AVC)", 27, null),
    H265("H265 (HEVC)", 173, null),
    AV1("AV1", 225, Set.of(VideoContainer.MP4)),
    VP9("VP9", 167, null),
    PRO_RES("Apple ProRes", 147, null),
    QUICK_TIME("QuickTime", 55, null),
    WEBP("WebP", 171, Set.of(VideoContainer.WEBP)),
    GIF("GIF", 97, Set.of(VideoContainer.GIF)),
    PNG("PNG", 61, Set.of(VideoContainer.PNG_SEQUENCE)),
    EXR("EXR", 178, Set.of(VideoContainer.EXR_SEQUENCE));

    private final String text;
    private final int codecId;
    private String[] encoders;
    private boolean supportsTransparency = false;
    private final Set<VideoContainer> validContainers;

    private VideoCodec(String text, int codecId, Set<VideoContainer> validContainers) {
        this.text = text;
        this.codecId = codecId;
        this.validContainers = validContainers;
    }

    @Override
    public String text() {
        return this.text;
    }

    public int codecId() {
        return this.codecId;
    }

    public Set<VideoContainer> validContainers() {
        return this.validContainers;
    }

    public boolean supportsTransparency() {
        if (this == VP9 || this == H264 || this == H265) {
            return false;
        }
        if (this.encoders == null) {
            this.getEncoders();
        }
        return this.supportsTransparency;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    public String[] getEncoders() {
        block29: {
            block28: {
                if (this.encoders != null) break block29;
                encodersHardware = new ArrayList<String>();
                encodersHybrid = new ArrayList<String>();
                encodersSoftware = new ArrayList<String>();
                encodersAvoid = new ArrayList<String>();
                pointer = new Pointer();
                while (true) lbl-1000:
                // 9 sources

                {
                    codec = avcodec.av_codec_iterate(pointer);
                    try {
                        try {
                            if (codec != null) {
                                if (codec.id() != this.codecId || avcodec.av_codec_is_encoder(codec) == 0 || !VideoCodec.doesEncoderWork(codec)) ** GOTO lbl-1000
                                capabilities = codec.capabilities();
                                name = codec.name().getString();
                                this.supportsTransparency = false;
                                i = 0;
                                while ((pixFmt = codec.pix_fmts().get(i)) != -1) {
                                    if (PixelFormatHelper.doesPixelFormatSupportTransparency(pixFmt)) {
                                        this.supportsTransparency = true;
                                        break;
                                    }
                                    ++i;
                                }
                                if ((capabilities & 262144) != 0) {
                                    encodersHardware.add(name);
                                }
                                if ((capabilities & 524288) != 0 || VideoCodec.codecHasHwConfig(codec)) {
                                    encodersHybrid.add(name);
                                }
                                if ((capabilities & 512) != 0 || name.equals("libaom-av1")) {
                                    encodersAvoid.add(name);
                                }
                                encodersSoftware.add(name);
                            }
                            break block28;
                        }
                        finally {
                            if (codec == null) ** GOTO lbl-1000
                            codec.close();
                        }
                    }
                    finally {
                        if (codec == null) continue;
                        codec.close();
                        continue;
                    }
                    break;
                }
                ** GOTO lbl-1000
                finally {
                    pointer.close();
                }
            }
            encoders = new ArrayList<String>();
            encoders.addAll(encodersHardware);
            encoders.addAll(encodersHybrid);
            encoders.addAll(encodersSoftware);
            encoders.addAll(encodersAvoid);
            this.encoders = encoders.toArray(new String[0]);
        }
        return this.encoders;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static boolean doesEncoderWork(AVCodec codec) {
        AVCodecContext codecContext = null;
        AVDictionary options = new AVDictionary(null);
        try {
            codecContext = avcodec.avcodec_alloc_context3(codec);
            if (codecContext == null) {
                boolean bl = false;
                return bl;
            }
            codecContext.codec_id(codec.id());
            codecContext.codec_type(0);
            codecContext.bit_rate(400000L);
            codecContext.width(1920);
            codecContext.height(1080);
            AVRational frameRate = avutil.av_d2q(60.0, 1001000);
            AVRational supportedFramerates = codec.supported_framerates();
            if (supportedFramerates != null) {
                int idx = avutil.av_find_nearest_q_idx(frameRate, supportedFramerates);
                frameRate = supportedFramerates.position(idx);
            }
            AVRational time_base = avutil.av_inv_q(frameRate);
            codecContext.time_base(time_base);
            int pixelFormat = PixelFormatHelper.getBestPixelFormat(codec.name().getString(), 26, false);
            codecContext.pix_fmt(pixelFormat);
            if (pixelFormat == 190 || pixelFormat == 179) {
                boolean bl = false;
                return bl;
            }
            if ((codec.capabilities() & 0x200) != 0) {
                codecContext.strict_std_compliance(-2);
            }
            boolean bl = avcodec.avcodec_open2(codecContext, codec, options) >= 0;
            return bl;
        }
        finally {
            if (codecContext != null) {
                avcodec.avcodec_free_context(codecContext);
                codecContext.close();
            }
            avutil.av_dict_free(options);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean codecHasHwConfig(AVCodec codec) {
        try (AVCodecHWConfig config = avcodec.avcodec_get_hw_config(codec, 0);){
            if (config == null) return false;
            config.close();
            boolean bl = true;
            return bl;
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }
}

