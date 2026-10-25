/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Opaque;
import org.bytedeco.javacpp.annotation.Properties;

@Opaque
@Properties(inherit={avutil.class})
public class AVContainerFifo
extends Pointer {
    public AVContainerFifo() {
        super((Pointer)null);
    }

    public AVContainerFifo(Pointer p) {
        super(p);
    }
}

