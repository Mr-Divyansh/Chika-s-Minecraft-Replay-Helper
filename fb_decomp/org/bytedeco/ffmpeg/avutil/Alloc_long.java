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
public class Alloc_long
extends FunctionPointer {
    public Alloc_long(Pointer p) {
        super(p);
    }

    protected Alloc_long() {
        this.allocate();
    }

    private native void allocate();

    public native AVBufferRef call(@Cast(value={"size_t"}) long var1);

    static {
        Loader.load();
    }
}

