/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVSubsampleEncryptionInfo
extends Pointer {
    public AVSubsampleEncryptionInfo() {
        super((Pointer)null);
        this.allocate();
    }

    public AVSubsampleEncryptionInfo(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVSubsampleEncryptionInfo(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVSubsampleEncryptionInfo position(long position) {
        return (AVSubsampleEncryptionInfo)super.position(position);
    }

    public AVSubsampleEncryptionInfo getPointer(long i) {
        return (AVSubsampleEncryptionInfo)new AVSubsampleEncryptionInfo(this).offsetAddress(i);
    }

    @Cast(value={"unsigned int"})
    public native int bytes_of_clear_data();

    public native AVSubsampleEncryptionInfo bytes_of_clear_data(int var1);

    @Cast(value={"unsigned int"})
    public native int bytes_of_protected_data();

    public native AVSubsampleEncryptionInfo bytes_of_protected_data(int var1);

    static {
        Loader.load();
    }
}

