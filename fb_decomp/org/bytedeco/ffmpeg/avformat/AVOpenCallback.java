/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVIOInterruptCB;
import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVOpenCallback
extends FunctionPointer {
    public AVOpenCallback(Pointer p) {
        super(p);
    }

    protected AVOpenCallback() {
        this.allocate();
    }

    private native void allocate();

    public native int call(AVFormatContext var1, @Cast(value={"AVIOContext**"}) PointerPointer var2, @Cast(value={"const char*"}) BytePointer var3, int var4, @Const AVIOInterruptCB var5, @Cast(value={"AVDictionary**"}) PointerPointer var6);

    static {
        Loader.load();
    }
}

