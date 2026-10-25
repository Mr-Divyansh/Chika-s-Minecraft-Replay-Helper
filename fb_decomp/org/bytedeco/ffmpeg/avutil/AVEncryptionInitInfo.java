/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVEncryptionInitInfo
extends Pointer {
    public AVEncryptionInitInfo() {
        super((Pointer)null);
        this.allocate();
    }

    public AVEncryptionInitInfo(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVEncryptionInitInfo(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVEncryptionInitInfo position(long position) {
        return (AVEncryptionInitInfo)super.position(position);
    }

    public AVEncryptionInitInfo getPointer(long i) {
        return (AVEncryptionInitInfo)new AVEncryptionInitInfo(this).offsetAddress(i);
    }

    @Cast(value={"uint8_t*"})
    public native BytePointer system_id();

    public native AVEncryptionInitInfo system_id(BytePointer var1);

    @Cast(value={"uint32_t"})
    public native int system_id_size();

    public native AVEncryptionInitInfo system_id_size(int var1);

    @Cast(value={"uint8_t*"})
    public native BytePointer key_ids(int var1);

    public native AVEncryptionInitInfo key_ids(int var1, BytePointer var2);

    @Cast(value={"uint8_t**"})
    public native PointerPointer key_ids();

    public native AVEncryptionInitInfo key_ids(PointerPointer var1);

    @Cast(value={"uint32_t"})
    public native int num_key_ids();

    public native AVEncryptionInitInfo num_key_ids(int var1);

    @Cast(value={"uint32_t"})
    public native int key_id_size();

    public native AVEncryptionInitInfo key_id_size(int var1);

    @Cast(value={"uint8_t*"})
    public native BytePointer data();

    public native AVEncryptionInitInfo data(BytePointer var1);

    @Cast(value={"uint32_t"})
    public native int data_size();

    public native AVEncryptionInitInfo data_size(int var1);

    public native AVEncryptionInitInfo next();

    public native AVEncryptionInitInfo next(AVEncryptionInitInfo var1);

    static {
        Loader.load();
    }
}

