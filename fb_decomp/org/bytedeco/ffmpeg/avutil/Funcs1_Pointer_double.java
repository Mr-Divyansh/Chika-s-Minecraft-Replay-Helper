/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class Funcs1_Pointer_double
extends FunctionPointer {
    public Funcs1_Pointer_double(Pointer p) {
        super(p);
    }

    protected Funcs1_Pointer_double() {
        this.allocate();
    }

    private native void allocate();

    public native double call(Pointer var1, double var2);

    static {
        Loader.load();
    }
}

