/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.avcodec;

import org.bytedeco.ffmpeg.presets.avcodec;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit={avcodec.class})
public class AVRTCPSenderReport
extends Pointer {
    public AVRTCPSenderReport() {
        super((Pointer)null);
        this.allocate();
    }

    public AVRTCPSenderReport(long size) {
        super((Pointer)null);
        this.allocateArray(size);
    }

    public AVRTCPSenderReport(Pointer p) {
        super(p);
    }

    private native void allocate();

    private native void allocateArray(long var1);

    public AVRTCPSenderReport position(long position) {
        return (AVRTCPSenderReport)super.position(position);
    }

    public AVRTCPSenderReport getPointer(long i) {
        return (AVRTCPSenderReport)new AVRTCPSenderReport(this).offsetAddress(i);
    }

    @Cast(value={"uint32_t"})
    public native int ssrc();

    public native AVRTCPSenderReport ssrc(int var1);

    @Cast(value={"uint64_t"})
    public native long ntp_timestamp();

    public native AVRTCPSenderReport ntp_timestamp(long var1);

    @Cast(value={"uint32_t"})
    public native int rtp_timestamp();

    public native AVRTCPSenderReport rtp_timestamp(int var1);

    @Cast(value={"uint32_t"})
    public native int sender_nb_packets();

    public native AVRTCPSenderReport sender_nb_packets(int var1);

    @Cast(value={"uint32_t"})
    public native int sender_nb_bytes();

    public native AVRTCPSenderReport sender_nb_bytes(int var1);

    static {
        Loader.load();
    }
}

