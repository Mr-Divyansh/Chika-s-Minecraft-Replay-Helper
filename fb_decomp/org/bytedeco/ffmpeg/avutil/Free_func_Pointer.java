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
public class Free_func_Pointer
extends FunctionPointer {
    public Free_func_Pointer(Pointer p) {
        super(p);
    }

    protected Free_func_Pointer() {
        this.allocate();
    }

    private native void allocate();

    public native void call(Pointer var1);

    static {
        Loader.load();
    }
}

