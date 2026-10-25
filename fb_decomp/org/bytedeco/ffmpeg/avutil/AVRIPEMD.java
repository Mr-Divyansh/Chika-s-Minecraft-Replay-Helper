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
public class AVRIPEMD
extends Pointer {
    public AVRIPEMD() {
        super((Pointer)null);
    }

    public AVRIPEMD(Pointer p) {
        super(p);
    }
}

