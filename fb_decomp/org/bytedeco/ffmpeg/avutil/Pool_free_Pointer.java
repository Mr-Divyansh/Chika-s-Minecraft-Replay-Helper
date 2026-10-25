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
public class Pool_free_Pointer
extends FunctionPointer {
    public Pool_free_Pointer(Pointer p) {
        super(p);
    }

    protected Pool_free_Pointer() {
        this.allocate();
    }

    private native void allocate();

    public native void call(Pointer var1);

    static {
        Loader.load();
    }
}

