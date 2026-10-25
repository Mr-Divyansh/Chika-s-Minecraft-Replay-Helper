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
public class Funcs2_Pointer_double_double
extends FunctionPointer {
    public Funcs2_Pointer_double_double(Pointer p) {
        super(p);
    }

    protected Funcs2_Pointer_double_double() {
        this.allocate();
    }

    private native void allocate();

    public native double call(Pointer var1, double var2, double var4);

    static {
        Loader.load();
    }
}

