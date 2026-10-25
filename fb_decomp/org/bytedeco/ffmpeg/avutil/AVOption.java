/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avutil;

import org.bytedeco.ffmpeg.avutil.AVOptionArrayDef;
import org.bytedeco.ffmpeg.avutil.AVRational;
import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avutil.class})
public class AVOption
extends Pointer {
    public AVOption() {
        super((Pointer)null);
        this.allocate();
    }

    public AVOption(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVOption(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVOption position(long position) {
        return (AVOption)super.position(position);
    }

    public AVOption getPointer(long i) {
        return (AVOption)new AVOption(this).offsetAddress(i);
    }

    @Cast(value={"const char*"})
    public native BytePointer name();

    public native AVOption name(BytePointer var1);

    @Cast(value={"const char*"})
    public native BytePointer help();

    public native AVOption help(BytePointer var1);

    public native int offset();

    public native AVOption offset(int var1);

    @Cast(value={"AVOptionType"})
    public native int type();

    public native AVOption type(int var1);

    @Name(value={"default_val.i64"})
    @Cast(value={"int64_t"})
    public native long default_val_i64();

    public native AVOption default_val_i64(long var1);

    @Name(value={"default_val.dbl"})
    public native double default_val_dbl();

    public native AVOption default_val_dbl(double var1);

    @Name(value={"default_val.str"})
    @Cast(value={"const char*"})
    public native BytePointer default_val_str();

    public native AVOption default_val_str(BytePointer var1);

    @Name(value={"default_val.q"})
    @ByRef
    public native AVRational default_val_q();

    public native AVOption default_val_q(AVRational var1);

    @Name(value={"default_val.arr"})
    @Const
    public native AVOptionArrayDef default_val_arr();

    public native AVOption default_val_arr(AVOptionArrayDef var1);

    public native double min();

    public native AVOption min(double var1);

    public native double max();

    public native AVOption max(double var1);

    public native int flags();

    public native AVOption flags(int var1);

    @Cast(value={"const char*"})
    public native BytePointer unit();

    public native AVOption unit(BytePointer var1);

    static {
        Loader.load();
    }
}

