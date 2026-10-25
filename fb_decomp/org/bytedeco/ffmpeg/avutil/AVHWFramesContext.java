/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVBufferPool;
import org.bytedeco.ffmpeg.avutil.AVBufferRef;
import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.avutil.AVHWDeviceContext;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVHWFramesContext
extends Pointer {
    public AVHWFramesContext() {
        super((Pointer)null);
        this.allocate();
    }

    public AVHWFramesContext(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVHWFramesContext(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVHWFramesContext position(long position) {
        return (AVHWFramesContext)super.position(position);
    }

    public AVHWFramesContext getPointer(long i) {
        return (AVHWFramesContext)new AVHWFramesContext(this).offsetAddress(i);
    }

    @Const
    public native AVClass av_class();

    public native AVHWFramesContext av_class(AVClass var1);

    public native AVBufferRef device_ref();

    public native AVHWFramesContext device_ref(AVBufferRef var1);

    public native AVHWDeviceContext device_ctx();

    public native AVHWFramesContext device_ctx(AVHWDeviceContext var1);

    public native Pointer hwctx();

    public native AVHWFramesContext hwctx(Pointer var1);

    @Name(value={"free"})
    public native Free_AVHWFramesContext _free();

    public native AVHWFramesContext _free(Free_AVHWFramesContext var1);

    public native Pointer user_opaque();

    public native AVHWFramesContext user_opaque(Pointer var1);

    public native AVBufferPool pool();

    public native AVHWFramesContext pool(AVBufferPool var1);

    public native int initial_pool_size();

    public native AVHWFramesContext initial_pool_size(int var1);

    @Cast(value={"AVPixelFormat"})
    public native int format();

    public native AVHWFramesContext format(int var1);

    @Cast(value={"AVPixelFormat"})
    public native int sw_format();

    public native AVHWFramesContext sw_format(int var1);

    public native int width();

    public native AVHWFramesContext width(int var1);

    public native int height();

    public native AVHWFramesContext height(int var1);

    static {
        Loader.load();
    }

    public static class Free_AVHWFramesContext
    extends FunctionPointer {
        public Free_AVHWFramesContext(Pointer p) {
            super(p);
        }

        protected Free_AVHWFramesContext() {
            this.allocate();
        }

        private native void allocate();

        public native void call(AVHWFramesContext var1);

        static {
            Loader.load();
        }
    }
}

