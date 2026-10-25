/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class Func_AVCodecContext_Pointer_int_int
extends FunctionPointer {
    public Func_AVCodecContext_Pointer_int_int(Pointer p) {
        super(p);
    }

    protected Func_AVCodecContext_Pointer_int_int() {
        this.allocate();
    }

    private native void allocate();

    public native int call(AVCodecContext var1, Pointer var2, int var3, int var4);

    static {
        Loader.load();
    }
}

