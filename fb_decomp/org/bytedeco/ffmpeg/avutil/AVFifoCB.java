/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.SizeTPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVFifoCB
extends FunctionPointer {
    public AVFifoCB(Pointer p) {
        super(p);
    }

    protected AVFifoCB() {
        this.allocate();
    }

    private native void allocate();

    public native int call(Pointer var1, Pointer var2, @Cast(value={"size_t*"}) SizeTPointer var3);

    static {
        Loader.load();
    }
}

