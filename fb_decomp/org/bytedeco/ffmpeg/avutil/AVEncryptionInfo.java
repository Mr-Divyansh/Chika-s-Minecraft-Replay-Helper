/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVSubsampleEncryptionInfo;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVEncryptionInfo
extends Pointer {
    public AVEncryptionInfo() {
        super((Pointer)null);
        this.allocate();
    }

    public AVEncryptionInfo(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVEncryptionInfo(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVEncryptionInfo position(long position) {
        return (AVEncryptionInfo)super.position(position);
    }

    public AVEncryptionInfo getPointer(long i) {
        return (AVEncryptionInfo)new AVEncryptionInfo(this).offsetAddress(i);
    }

    @Cast(value={"uint32_t"})
    public native int scheme();

    public native AVEncryptionInfo scheme(int var1);

    @Cast(value={"uint32_t"})
    public native int crypt_byte_block();

    public native AVEncryptionInfo crypt_byte_block(int var1);

    @Cast(value={"uint32_t"})
    public native int skip_byte_block();

    public native AVEncryptionInfo skip_byte_block(int var1);

    @Cast(value={"uint8_t*"})
    public native BytePointer key_id();

    public native AVEncryptionInfo key_id(BytePointer var1);

    @Cast(value={"uint32_t"})
    public native int key_id_size();

    public native AVEncryptionInfo key_id_size(int var1);

    @Cast(value={"uint8_t*"})
    public native BytePointer iv();

    public native AVEncryptionInfo iv(BytePointer var1);

    @Cast(value={"uint32_t"})
    public native int iv_size();

    public native AVEncryptionInfo iv_size(int var1);

    public native AVSubsampleEncryptionInfo subsamples();

    public native AVEncryptionInfo subsamples(AVSubsampleEncryptionInfo var1);

    @Cast(value={"uint32_t"})
    public native int subsample_count();

    public native AVEncryptionInfo subsample_count(int var1);

    static {
        Loader.load();
    }
}

