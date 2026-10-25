/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByVal;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class Callback_Pointer_int_BytePointer_Pointer
extends FunctionPointer {
    public Callback_Pointer_int_BytePointer_Pointer(Pointer p) {
        super(p);
    }

    protected Callback_Pointer_int_BytePointer_Pointer() {
        this.allocate();
    }

    private native void allocate();

    public native void call(Pointer var1, int var2, @Cast(value={"const char*"}) BytePointer var3, @ByVal @Cast(value={"va_list*"}) Pointer var4);

    static {
        Loader.load();
    }
}

