/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVIODirEntry
extends Pointer {
    public AVIODirEntry() {
        super((Pointer)null);
        this.allocate();
    }

    public AVIODirEntry(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVIODirEntry(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVIODirEntry position(long position) {
        return (AVIODirEntry)super.position(position);
    }

    public AVIODirEntry getPointer(long i) {
        return (AVIODirEntry)new AVIODirEntry(this).offsetAddress(i);
    }

    @Cast(value={"char*"})
    public native BytePointer name();

    public native AVIODirEntry name(BytePointer var1);

    public native int type();

    public native AVIODirEntry type(int var1);

    public native int utf8();

    public native AVIODirEntry utf8(int var1);

    @Cast(value={"int64_t"})
    public native long size();

    public native AVIODirEntry size(long var1);

    @Cast(value={"int64_t"})
    public native long modification_timestamp();

    public native AVIODirEntry modification_timestamp(long var1);

    @Cast(value={"int64_t"})
    public native long access_timestamp();

    public native AVIODirEntry access_timestamp(long var1);

    @Cast(value={"int64_t"})
    public native long status_change_timestamp();

    public native AVIODirEntry status_change_timestamp(long var1);

    @Cast(value={"int64_t"})
    public native long user_id();

    public native AVIODirEntry user_id(long var1);

    @Cast(value={"int64_t"})
    public native long group_id();

    public native AVIODirEntry group_id(long var1);

    @Cast(value={"int64_t"})
    public native long filemode();

    public native AVIODirEntry filemode(long var1);

    static {
        Loader.load();
    }
}

