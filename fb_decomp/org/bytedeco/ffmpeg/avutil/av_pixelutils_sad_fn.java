/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class av_pixelutils_sad_fn
extends FunctionPointer {
    public av_pixelutils_sad_fn(Pointer p) {
        super(p);
    }

    protected av_pixelutils_sad_fn() {
        this.allocate();
    }

    private native void allocate();

    public native int call(@Cast(value={"const uint8_t*"}) BytePointer var1, @Cast(value={"ptrdiff_t"}) long var2, @Cast(value={"const uint8_t*"}) BytePointer var4, @Cast(value={"ptrdiff_t"}) long var5);

    static {
        Loader.load();
    }
}

