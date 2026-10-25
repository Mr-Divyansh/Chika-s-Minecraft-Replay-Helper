/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVBufferRef;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class Alloc_Pointer_long
extends FunctionPointer {
    public Alloc_Pointer_long(Pointer p) {
        super(p);
    }

    protected Alloc_Pointer_long() {
        this.allocate();
    }

    private native void allocate();

    public native AVBufferRef call(Pointer var1, @Cast(value={"size_t"}) long var2);

    static {
        Loader.load();
    }
}

