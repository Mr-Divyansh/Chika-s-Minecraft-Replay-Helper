/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVProducerReferenceTime
extends Pointer {
    public AVProducerReferenceTime() {
        super((Pointer)null);
        this.allocate();
    }

    public AVProducerReferenceTime(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVProducerReferenceTime(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVProducerReferenceTime position(long position) {
        return (AVProducerReferenceTime)super.position(position);
    }

    public AVProducerReferenceTime getPointer(long i) {
        return (AVProducerReferenceTime)new AVProducerReferenceTime(this).offsetAddress(i);
    }

    @Cast(value={"int64_t"})
    public native long wallclock();

    public native AVProducerReferenceTime wallclock(long var1);

    public native int flags();

    public native AVProducerReferenceTime flags(int var1);

    static {
        Loader.load();
    }
}

