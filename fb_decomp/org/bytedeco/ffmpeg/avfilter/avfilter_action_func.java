/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avfilter;

import org.bytedeco.ffmpeg.avfilter.AVFilterContext;
import org.bytedeco.ffmpeg.presets.avfilter;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avfilter.class})
public class avfilter_action_func
extends FunctionPointer {
    public avfilter_action_func(Pointer p) {
        super(p);
    }

    protected avfilter_action_func() {
        this.allocate();
    }

    private native void allocate();

    public native int call(AVFilterContext var1, Pointer var2, int var3, int var4);

    static {
        Loader.load();
    }
}

