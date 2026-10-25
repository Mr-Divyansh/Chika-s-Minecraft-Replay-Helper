/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.FunctionPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVIOInterruptCB
extends Pointer {
    public AVIOInterruptCB() {
        super((Pointer)null);
        this.allocate();
    }

    public AVIOInterruptCB(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVIOInterruptCB(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVIOInterruptCB position(long position) {
        return (AVIOInterruptCB)super.position(position);
    }

    public AVIOInterruptCB getPointer(long i) {
        return (AVIOInterruptCB)new AVIOInterruptCB(this).offsetAddress(i);
    }

    public native Callback_Pointer callback();

    public native AVIOInterruptCB callback(Callback_Pointer var1);

    public native Pointer opaque();

    public native AVIOInterruptCB opaque(Pointer var1);

    static {
        Loader.load();
    }

    public static class Callback_Pointer
    extends FunctionPointer {
        public Callback_Pointer(Pointer p) {
            super(p);
        }

        protected Callback_Pointer() {
            this.allocate();
        }

        private native void allocate();

        public native int call(Pointer var1);

        static {
            Loader.load();
        }
    }
}

