/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVTXContext;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class av_tx_fn
extends FunctionPointer {
    public av_tx_fn(Pointer p) {
        super(p);
    }

    protected av_tx_fn() {
        this.allocate();
    }

    private native void allocate();

    public native void call(AVTXContext var1, Pointer var2, Pointer var3, @Cast(value={"ptrdiff_t"}) long var4);

    static {
        Loader.load();
    }
}

