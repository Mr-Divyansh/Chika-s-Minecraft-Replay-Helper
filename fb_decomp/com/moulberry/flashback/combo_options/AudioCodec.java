/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.combo_options;

import com.moulberry.flashback.combo_options.ComboOption;
import java.util.ArrayList;
import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avcodec.AVCodecHWConfig;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacpp.Pointer;

public enum AudioCodec implements ComboOption
{
    AAC("AAC", 86018),
    MP3("MP3", 86017),
    OPUS("Opus", 86076),
    VORBIS("Vorbis", 86021);

    private final String text;
    private final int codecId;
    private String[] encoders;

    private AudioCodec(String text, int codecId) {
        this.text = text;
        this.codecId = codecId;
    }

    @Override
    public String text() {
        return this.text;
    }

    public int codecId() {
        return this.codecId;
    }

    /*
     * Unable to fully structure code
     */
    public String[] getEncoders() {
        block17: {
            block16: {
                if (this.encoders != null) break block17;
                encodersHardware = new ArrayList<String>();
                encodersHybrid = new ArrayList<String>();
                encodersSoftware = new ArrayList<String>();
                encodersAvoid = new ArrayList<String>();
                pointer = new Pointer();
                while (true) lbl-1000:
                // 7 sources

                {
                    codec = avcodec.av_codec_iterate(pointer);
                    try {
                        if (codec != null) {
                            if (codec.id() != this.codecId || avcodec.av_codec_is_encoder(codec) == 0) ** GOTO lbl-1000
                            capabilities = codec.capabilities();
                            name = codec.name().getString();
                            if ((capabilities & 262144) != 0) {
                                encodersHardware.add(name);
                            }
                            if ((capabilities & 524288) != 0 || AudioCodec.codecHasHwConfig(codec)) {
                                encodersHybrid.add(name);
                            }
                            if ((capabilities & 512) != 0) {
                                encodersAvoid.add(name);
                            }
                            encodersSoftware.add(name);
                        }
                        break block16;
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

    private static boolean codecHasHwConfig(AVCodec codec) {
        boolean bl;
        block8: {
            AVCodecHWConfig config = avcodec.avcodec_get_hw_config(codec, 0);
            try {
                boolean bl2 = bl = config != null;
                if (config == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (config != null) {
                        try {
                            config.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Throwable t) {
                    return false;
                }
            }
            config.close();
        }
        return bl;
    }
}

