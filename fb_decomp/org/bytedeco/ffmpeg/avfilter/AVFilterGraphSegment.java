/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avfilter;

import org.bytedeco.ffmpeg.avfilter.AVFilterChain;
import org.bytedeco.ffmpeg.avfilter.AVFilterGraph;
import org.bytedeco.ffmpeg.presets.avfilter;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avfilter.class})
public class AVFilterGraphSegment
extends Pointer {
    public AVFilterGraphSegment() {
        super((Pointer)null);
        this.allocate();
    }

    public AVFilterGraphSegment(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVFilterGraphSegment(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVFilterGraphSegment position(long position) {
        return (AVFilterGraphSegment)super.position(position);
    }

    public AVFilterGraphSegment getPointer(long i) {
        return (AVFilterGraphSegment)new AVFilterGraphSegment(this).offsetAddress(i);
    }

    public native AVFilterGraph graph();

    public native AVFilterGraphSegment graph(AVFilterGraph var1);

    public native AVFilterChain chains(int var1);

    public native AVFilterGraphSegment chains(int var1, AVFilterChain var2);

    @Cast(value={"AVFilterChain**"})
    public native PointerPointer chains();

    public native AVFilterGraphSegment chains(PointerPointer var1);

    @Cast(value={"size_t"})
    public native long nb_chains();

    public native AVFilterGraphSegment nb_chains(long var1);

    @Cast(value={"char*"})
    public native BytePointer scale_sws_opts();

    public native AVFilterGraphSegment scale_sws_opts(BytePointer var1);

    static {
        Loader.load();
    }
}

