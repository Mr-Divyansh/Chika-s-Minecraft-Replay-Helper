/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class Fifo_transfer_Pointer_Pointer_Pointer_int
extends FunctionPointer {
    public Fifo_transfer_Pointer_Pointer_Pointer_int(Pointer p) {
        super(p);
    }

    protected Fifo_transfer_Pointer_Pointer_Pointer_int() {
        this.allocate();
    }

    private native void allocate();

    public native int call(Pointer var1, Pointer var2, Pointer var3, @Cast(value={"unsigned"}) int var4);

    static {
        Loader.load();
    }
}

