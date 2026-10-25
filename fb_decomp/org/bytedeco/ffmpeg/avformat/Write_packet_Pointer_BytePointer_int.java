/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class Write_packet_Pointer_BytePointer_int
extends FunctionPointer {
    public Write_packet_Pointer_BytePointer_int(Pointer p) {
        super(p);
    }

    protected Write_packet_Pointer_BytePointer_int() {
        this.allocate();
    }

    private native void allocate();

    public native int call(Pointer var1, @Cast(value={"const uint8_t*"}) BytePointer var2, int var3);

    static {
        Loader.load();
    }
}

