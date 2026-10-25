/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avdevice;

import org.bytedeco.ffmpeg.avdevice.AVDeviceInfo;
import org.bytedeco.ffmpeg.presets.avdevice;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avdevice.class})
public class AVDeviceInfoList
extends Pointer {
    public AVDeviceInfoList() {
        super((Pointer)null);
        this.allocate();
    }

    public AVDeviceInfoList(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVDeviceInfoList(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVDeviceInfoList position(long position) {
        return (AVDeviceInfoList)super.position(position);
    }

    public AVDeviceInfoList getPointer(long i) {
        return (AVDeviceInfoList)new AVDeviceInfoList(this).offsetAddress(i);
    }

    public native AVDeviceInfo devices(int var1);

    public native AVDeviceInfoList devices(int var1, AVDeviceInfo var2);

    @Cast(value={"AVDeviceInfo**"})
    public native PointerPointer devices();

    public native AVDeviceInfoList devices(PointerPointer var1);

    public native int nb_devices();

    public native AVDeviceInfoList nb_devices(int var1);

    public native int default_device();

    public native AVDeviceInfoList default_device(int var1);

    static {
        Loader.load();
    }
}

