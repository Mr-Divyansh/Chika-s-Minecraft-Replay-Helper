/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avdevice;

import org.bytedeco.ffmpeg.presets.avdevice;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avdevice.class})
public class AVDeviceInfo
extends Pointer {
    public AVDeviceInfo() {
        super((Pointer)null);
        this.allocate();
    }

    public AVDeviceInfo(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVDeviceInfo(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVDeviceInfo position(long position) {
        return (AVDeviceInfo)super.position(position);
    }

    public AVDeviceInfo getPointer(long i) {
        return (AVDeviceInfo)new AVDeviceInfo(this).offsetAddress(i);
    }

    @Cast(value={"char*"})
    public native BytePointer device_name();

    public native AVDeviceInfo device_name(BytePointer var1);

    @Cast(value={"char*"})
    public native BytePointer device_description();

    public native AVDeviceInfo device_description(BytePointer var1);

    @Cast(value={"AVMediaType*"})
    public native IntPointer media_types();

    public native AVDeviceInfo media_types(IntPointer var1);

    public native int nb_media_types();

    public native AVDeviceInfo nb_media_types(int var1);

    static {
        Loader.load();
    }
}

