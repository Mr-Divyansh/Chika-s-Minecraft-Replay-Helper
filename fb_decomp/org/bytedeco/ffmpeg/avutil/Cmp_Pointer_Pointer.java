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
public class Cmp_Pointer_Pointer
extends FunctionPointer {
    public Cmp_Pointer_Pointer(Pointer p) {
        super(p);
    }

    protected Cmp_Pointer_Pointer() {
        this.allocate();
    }

    private native void allocate();

    public native int call(Pointer var1, Pointer var2);

    static {
        Loader.load();
    }
}

