/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avfilter;

import org.bytedeco.ffmpeg.avfilter.AVFilterContext;
import org.bytedeco.ffmpeg.avfilter.avfilter_action_func;
import org.bytedeco.ffmpeg.presets.avfilter;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avfilter.class})
public class avfilter_execute_func
extends FunctionPointer {
    public avfilter_execute_func(Pointer p) {
        super(p);
    }

    protected avfilter_execute_func() {
        this.allocate();
    }

    private native void allocate();

    public native int call(AVFilterContext var1, avfilter_action_func var2, Pointer var3, IntPointer var4, int var5);

    static {
        Loader.load();
    }
}

