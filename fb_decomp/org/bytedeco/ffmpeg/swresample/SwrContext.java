/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.swresample;

import org.bytedeco.ffmpeg.presets.swresample;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Opaque;
import org.bytedeco.javacpp.annotation.Properties;

@Opaque
@Properties(inherit={swresample.class})
public class SwrContext
extends Pointer {
    public SwrContext() {
        super((Pointer)null);
    }

    public SwrContext(Pointer p) {
        super(p);
    }
}

