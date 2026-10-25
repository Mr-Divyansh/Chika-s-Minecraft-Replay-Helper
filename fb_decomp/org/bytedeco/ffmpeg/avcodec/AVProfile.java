/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVProfile
extends Pointer {
    public AVProfile() {
        super((Pointer)null);
        this.allocate();
    }

    public AVProfile(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVProfile(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVProfile position(long position) {
        return (AVProfile)super.position(position);
    }

    public AVProfile getPointer(long i) {
        return (AVProfile)new AVProfile(this).offsetAddress(i);
    }

    public native int profile();

    public native AVProfile profile(int var1);

    @Cast(value={"const char*"})
    public native BytePointer name();

    public native AVProfile name(BytePointer var1);

    static {
        Loader.load();
    }
}

