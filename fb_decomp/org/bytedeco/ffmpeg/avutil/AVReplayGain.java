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
public class AVReplayGain
extends Pointer {
    public AVReplayGain() {
        super((Pointer)null);
        this.allocate();
    }

    public AVReplayGain(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVReplayGain(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVReplayGain position(long position) {
        return (AVReplayGain)super.position(position);
    }

    public AVReplayGain getPointer(long i) {
        return (AVReplayGain)new AVReplayGain(this).offsetAddress(i);
    }

    public native int track_gain();

    public native AVReplayGain track_gain(int var1);

    @Cast(value={"uint32_t"})
    public native int track_peak();

    public native AVReplayGain track_peak(int var1);

    public native int album_gain();

    public native AVReplayGain album_gain(int var1);

    @Cast(value={"uint32_t"})
    public native int album_peak();

    public native AVReplayGain album_peak(int var1);

    static {
        Loader.load();
    }
}

