/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVProgram
extends Pointer {
    public AVProgram() {
        super((Pointer)null);
        this.allocate();
    }

    public AVProgram(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVProgram(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVProgram position(long position) {
        return (AVProgram)super.position(position);
    }

    public AVProgram getPointer(long i) {
        return (AVProgram)new AVProgram(this).offsetAddress(i);
    }

    public native int id();

    public native AVProgram id(int var1);

    public native int flags();

    public native AVProgram flags(int var1);

    @Cast(value={"AVDiscard"})
    public native int discard();

    public native AVProgram discard(int var1);

    @Cast(value={"unsigned int*"})
    public native IntPointer stream_index();

    public native AVProgram stream_index(IntPointer var1);

    @Cast(value={"unsigned int"})
    public native int nb_stream_indexes();

    public native AVProgram nb_stream_indexes(int var1);

    public native AVDictionary metadata();

    public native AVProgram metadata(AVDictionary var1);

    public native int program_num();

    public native AVProgram program_num(int var1);

    public native int pmt_pid();

    public native AVProgram pmt_pid(int var1);

    public native int pcr_pid();

    public native AVProgram pcr_pid(int var1);

    public native int pmt_version();

    public native AVProgram pmt_version(int var1);

    @Cast(value={"int64_t"})
    public native long start_time();

    public native AVProgram start_time(long var1);

    @Cast(value={"int64_t"})
    public native long end_time();

    public native AVProgram end_time(long var1);

    @Cast(value={"int64_t"})
    public native long pts_wrap_reference();

    public native AVProgram pts_wrap_reference(long var1);

    public native int pts_wrap_behavior();

    public native AVProgram pts_wrap_behavior(int var1);

    static {
        Loader.load();
    }
}

