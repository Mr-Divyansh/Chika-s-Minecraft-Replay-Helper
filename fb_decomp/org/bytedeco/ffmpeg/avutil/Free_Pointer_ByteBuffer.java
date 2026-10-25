/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import java.nio.ByteBuffer;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class Free_Pointer_ByteBuffer
extends FunctionPointer {
    public Free_Pointer_ByteBuffer(Pointer p) {
        super(p);
    }

    protected Free_Pointer_ByteBuffer() {
        this.allocate();
    }

    private native void allocate();

    public native void call(Pointer var1, @Cast(value={"uint8_t*"}) ByteBuffer var2);

    static {
        Loader.load();
    }
}

