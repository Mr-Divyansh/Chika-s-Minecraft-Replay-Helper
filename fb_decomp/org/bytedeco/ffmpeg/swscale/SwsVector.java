/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.swscale;

import org.bytedeco.ffmpeg.presets.swscale;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={swscale.class})
public class SwsVector
extends Pointer {
    public SwsVector() {
        super((Pointer)null);
        this.allocate();
    }

    public SwsVector(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public SwsVector(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public SwsVector position(long position) {
        return (SwsVector)super.position(position);
    }

    public SwsVector getPointer(long i) {
        return (SwsVector)new SwsVector(this).offsetAddress(i);
    }

    public native DoublePointer coeff();

    public native SwsVector coeff(DoublePointer var1);

    public native int length();

    public native SwsVector length(int var1);

    static {
        Loader.load();
    }
}

