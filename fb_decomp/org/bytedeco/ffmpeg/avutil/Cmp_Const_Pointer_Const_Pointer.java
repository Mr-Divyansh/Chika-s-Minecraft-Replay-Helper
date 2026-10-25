/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class Cmp_Const_Pointer_Const_Pointer
extends FunctionPointer {
    public Cmp_Const_Pointer_Const_Pointer(Pointer p) {
        super(p);
    }

    protected Cmp_Const_Pointer_Const_Pointer() {
        this.allocate();
    }

    private native void allocate();

    public native int call(@Const Pointer var1, @Const Pointer var2);

    static {
        Loader.load();
    }
}

