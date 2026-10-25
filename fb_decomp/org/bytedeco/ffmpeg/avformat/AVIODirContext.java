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
public class AVIODirContext
extends Pointer {
    public AVIODirContext() {
        super((Pointer)null);
    }

    public AVIODirContext(Pointer p) {
        super(p);
    }
}

