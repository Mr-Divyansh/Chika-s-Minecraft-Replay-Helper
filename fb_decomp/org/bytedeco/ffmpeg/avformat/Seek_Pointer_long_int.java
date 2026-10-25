/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class Seek_Pointer_long_int
extends FunctionPointer {
    public Seek_Pointer_long_int(Pointer p) {
        super(p);
    }

    protected Seek_Pointer_long_int() {
        this.allocate();
    }

    private native void allocate();

    @Cast(value={"int64_t"})
    public native long call(Pointer var1, @Cast(value={"int64_t"}) long var2, int var4);

    static {
        Loader.load();
    }
}

