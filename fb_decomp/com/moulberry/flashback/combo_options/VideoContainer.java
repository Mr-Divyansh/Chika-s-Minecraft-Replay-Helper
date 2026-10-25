/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.combo_options;

import com.moulberry.flashback.combo_options.AudioCodec;
import com.moulberry.flashback.combo_options.ComboOption;
import com.moulberry.flashback.combo_options.VideoCodec;
import java.util.ArrayList;
import java.util.Set;
import org.bytedeco.ffmpeg.avformat.AVOutputFormat;
import org.bytedeco.ffmpeg.global.avformat;

public enum VideoContainer implements ComboOption
{
    MP4("MP4", "mp4"),
    MKV("MKV", "mkv"),
    AVI("AVI", "avi"),
    MOV("MOV", "mov"),
    PNG_SEQUENCE("PNG Sequence", "png"),
    EXR_SEQUENCE("EXR Sequence", "exr"),
    WEBP("WebP", "webp"),
    WEBM("WebM", "webm"),
    GIF("GIF", "gif");

    private final String text;
    private final String extension;
    private VideoCodec[] supportedVideoCodecs = null;
    private VideoCodec[] supportedVideoCodecsWithTransparency = null;
    private AudioCodec[] supportedAudioCodecs = null;
    private boolean isImageSequence = false;

    private VideoContainer(String text, String extension) {
        this.text = text;
        this.extension = extension;
    }

    @Override
    public String text() {
        return this.text;
    }

    public String extension() {
        return this.extension;
    }

    public static VideoContainer[] findSupportedContainers(boolean transparency) {
        ArrayList<VideoContainer> containers = new ArrayList<VideoContainer>();
        for (VideoContainer videoContainer : VideoContainer.values()) {
            if (videoContainer.getSupportedVideoCodecs(transparency).length == 0) continue;
            containers.add(videoContainer);
        }
        return containers.toArray(new VideoContainer[0]);
    }

    public boolean isImageSequence() {
        this.getSupportedVideoCodecs(false);
        return this.isImageSequence;
    }

    public VideoCodec[] getSupportedVideoCodecs(boolean transparency) {
        VideoCodec[] codecs;
        VideoCodec[] videoCodecArray = codecs = transparency ? this.supportedVideoCodecsWithTransparency : this.supportedVideoCodecs;
        if (codecs == null) {
            ArrayList<VideoCodec> supportedCodecs = new ArrayList<VideoCodec>();
            try (AVOutputFormat outputFormat = avformat.av_guess_format(this.extension, "test." + this.extension, null);){
                for (VideoCodec codec : VideoCodec.values()) {
                    int ret;
                    Set<VideoContainer> validContainers = codec.validContainers();
                    if (validContainers != null && !validContainers.contains(this) || codec.getEncoders().length == 0 || transparency && !codec.supportsTransparency()) continue;
                    if (outputFormat.name().getString().equals("image2")) {
                        this.isImageSequence = true;
                    }
                    if ((ret = avformat.avformat_query_codec(outputFormat, codec.codecId(), 0)) != 1) continue;
                    supportedCodecs.add(codec);
                }
            }
            codecs = supportedCodecs.toArray(new VideoCodec[0]);
            if (transparency) {
                this.supportedVideoCodecsWithTransparency = codecs;
            } else {
                this.supportedVideoCodecs = codecs;
            }
        }
        return codecs;
    }

    public AudioCodec[] getSupportedAudioCodecs() {
        if (this.supportedAudioCodecs == null) {
            ArrayList<AudioCodec> supportedCodecs = new ArrayList<AudioCodec>();
            if (!this.isImageSequence()) {
                try (AVOutputFormat outputFormat = avformat.av_guess_format(this.extension, "test." + this.extension, null);){
                    for (AudioCodec codec : AudioCodec.values()) {
                        int ret;
                        if (codec.getEncoders().length == 0 || (ret = avformat.avformat_query_codec(outputFormat, codec.codecId(), 0)) != 1) continue;
                        supportedCodecs.add(codec);
                    }
                }
            }
            this.supportedAudioCodecs = supportedCodecs.toArray(new AudioCodec[0]);
        }
        return this.supportedAudioCodecs;
    }

    public String mimeType() {
        return switch (this.ordinal()) {
            default -> throw new MatchException(null, null);
            case 0 -> "video/mp4";
            case 1 -> "video/mkv";
            case 2 -> "video/x-msvideo";
            case 3 -> "video/quicktime";
            case 4 -> "image/png";
            case 5 -> "image/x-exr";
            case 6 -> "image/webp";
            case 7 -> "video/webm";
            case 8 -> "image/gif";
        };
    }
}

