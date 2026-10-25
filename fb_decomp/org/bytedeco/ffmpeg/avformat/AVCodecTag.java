/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Opaque;
import org.bytedeco.javacpp.annotation.Properties;

@Opaque
@Properties(inherit={avformat.class})
public class AVCodecTag
extends Pointer {
    public AVCodecTag() {
        super((Pointer)null);
    }

    public AVCodecTag(Pointer p) {
        super(p);
    }
}

