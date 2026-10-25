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
public class ff_pad_helper_AVBPrint
extends Pointer {
    public ff_pad_helper_AVBPrint() {
        super((Pointer)null);
        this.allocate();
    }

    public ff_pad_helper_AVBPrint(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public ff_pad_helper_AVBPrint(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public ff_pad_helper_AVBPrint position(long position) {
        return (ff_pad_helper_AVBPrint)super.position(position);
    }

    public ff_pad_helper_AVBPrint getPointer(long i) {
        return (ff_pad_helper_AVBPrint)new ff_pad_helper_AVBPrint(this).offsetAddress(i);
    }

    @Cast(value={"char*"})
    public native BytePointer str();

    public native ff_pad_helper_AVBPrint str(BytePointer var1);

    @Cast(value={"unsigned"})
    public native int len();

    public native ff_pad_helper_AVBPrint len(int var1);

    @Cast(value={"unsigned"})
    public native int size();

    public native ff_pad_helper_AVBPrint size(int var1);

    @Cast(value={"unsigned"})
    public native int size_max();

    public native ff_pad_helper_AVBPrint size_max(int var1);

    @Cast(value={"char"})
    public native byte reserved_internal_buffer(int var1);

    public native ff_pad_helper_AVBPrint reserved_internal_buffer(int var1, byte var2);

    @MemberGetter
    @Cast(value={"char*"})
    public native BytePointer reserved_internal_buffer();

    static {
        Loader.load();
    }
}

