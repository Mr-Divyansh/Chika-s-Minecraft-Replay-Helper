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
public class Container_alloc_Pointer
extends FunctionPointer {
    public Container_alloc_Pointer(Pointer p) {
        super(p);
    }

    protected Container_alloc_Pointer() {
        this.allocate();
    }

    private native void allocate();

    public native Pointer call(Pointer var1);

    static {
        Loader.load();
    }
}

