/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class av_format_control_message
extends FunctionPointer {
    public av_format_control_message(Pointer p) {
        super(p);
    }

    protected av_format_control_message() {
        this.allocate();
    }

    private native void allocate();

    public native int call(AVFormatContext var1, int var2, Pointer var3, @Cast(value={"size_t"}) long var4);

    static {
        Loader.load();
    }
}

