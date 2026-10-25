/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVBPrint
extends Pointer {
    public AVBPrint() {
        super((Pointer)null);
        this.allocate();
    }

    public AVBPrint(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVBPrint(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVBPrint position(long position) {
        return (AVBPrint)super.position(position);
    }

    public AVBPrint getPointer(long i) {
        return (AVBPrint)new AVBPrint(this).offsetAddress(i);
    }

    @Cast(value={"char*"})
    public native BytePointer str();

    public native AVBPrint str(BytePointer var1);

    @Cast(value={"unsigned"})
    public native int len();

    public native AVBPrint len(int var1);

    @Cast(value={"unsigned"})
    public native int size();

    public native AVBPrint size(int var1);

    @Cast(value={"unsigned"})
    public native int size_max();

    public native AVBPrint size_max(int var1);

    @Cast(value={"char"})
    public native byte reserved_internal_buffer(int var1);

    public native AVBPrint reserved_internal_buffer(int var1, byte var2);

    @MemberGetter
    @Cast(value={"char*"})
    public native BytePointer reserved_internal_buffer();

    @Cast(value={"char"})
    public native byte reserved_padding(int var1);

    public native AVBPrint reserved_padding(int var1, byte var2);

    @MemberGetter
    @Cast(value={"char*"})
    public native BytePointer reserved_padding();

    static {
        Loader.load();
    }
}

