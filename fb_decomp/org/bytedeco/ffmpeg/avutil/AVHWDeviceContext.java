/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVClass;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVHWDeviceContext
extends Pointer {
    public AVHWDeviceContext() {
        super((Pointer)null);
        this.allocate();
    }

    public AVHWDeviceContext(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVHWDeviceContext(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVHWDeviceContext position(long position) {
        return (AVHWDeviceContext)super.position(position);
    }

    public AVHWDeviceContext getPointer(long i) {
        return (AVHWDeviceContext)new AVHWDeviceContext(this).offsetAddress(i);
    }

    @Const
    public native AVClass av_class();

    public native AVHWDeviceContext av_class(AVClass var1);

    @Cast(value={"AVHWDeviceType"})
    public native int type();

    public native AVHWDeviceContext type(int var1);

    public native Pointer hwctx();

    public native AVHWDeviceContext hwctx(Pointer var1);

    @Name(value={"free"})
    public native Free_AVHWDeviceContext _free();

    public native AVHWDeviceContext _free(Free_AVHWDeviceContext var1);

    public native Pointer user_opaque();

    public native AVHWDeviceContext user_opaque(Pointer var1);

    static {
        Loader.load();
    }

    public static class Free_AVHWDeviceContext
    extends FunctionPointer {
        public Free_AVHWDeviceContext(Pointer p) {
            super(p);
        }

        protected Free_AVHWDeviceContext() {
            this.allocate();
        }

        private native void allocate();

        public native void call(AVHWDeviceContext var1);

        static {
            Loader.load();
        }
    }
}

