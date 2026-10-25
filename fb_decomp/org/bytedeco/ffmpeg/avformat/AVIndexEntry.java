/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avformat;

import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.NoOffset;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avformat.class})
public class AVIndexEntry
extends Pointer {
    public static final int AVINDEX_KEYFRAME = 1;
    public static final int AVINDEX_DISCARD_FRAME = 2;

    public AVIndexEntry() {
        super((Pointer)null);
        this.allocate();
    }

    public AVIndexEntry(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVIndexEntry(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVIndexEntry position(long position) {
        return (AVIndexEntry)super.position(position);
    }

    public AVIndexEntry getPointer(long i) {
        return (AVIndexEntry)new AVIndexEntry(this).offsetAddress(i);
    }

    @Cast(value={"int64_t"})
    public native long pos();

    public native AVIndexEntry pos(long var1);

    @Cast(value={"int64_t"})
    public native long timestamp();

    public native AVIndexEntry timestamp(long var1);

    @NoOffset
    public native int flags();

    public native AVIndexEntry flags(int var1);

    @NoOffset
    public native int size();

    public native AVIndexEntry size(int var1);

    public native int min_distance();

    public native AVIndexEntry min_distance(int var1);

    static {
        Loader.load();
    }
}

