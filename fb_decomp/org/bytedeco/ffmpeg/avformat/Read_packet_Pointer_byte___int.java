/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class Read_packet_Pointer_byte___int
extends FunctionPointer {
    public Read_packet_Pointer_byte___int(Pointer p) {
        super(p);
    }

    protected Read_packet_Pointer_byte___int() {
        this.allocate();
    }

    private native void allocate();

    public native int call(Pointer var1, @Cast(value={"uint8_t*"}) byte[] var2, int var3);

    static {
        Loader.load();
    }
}

