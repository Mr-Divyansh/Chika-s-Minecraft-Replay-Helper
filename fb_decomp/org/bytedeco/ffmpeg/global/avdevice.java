/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.global;

import org.bytedeco.ffmpeg.avdevice.AVDeviceInfoList;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVInputFormat;
import org.bytedeco.ffmpeg.avformat.AVOutputFormat;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.annotation.ByPtrPtr;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.NoException;

public class avdevice
extends org.bytedeco.ffmpeg.presets.avdevice {
    public static final int AV_APP_TO_DEV_NONE;
    public static final int AV_APP_TO_DEV_WINDOW_SIZE;
    public static final int AV_APP_TO_DEV_WINDOW_REPAINT;
    public static final int AV_APP_TO_DEV_PAUSE;
    public static final int AV_APP_TO_DEV_PLAY;
    public static final int AV_APP_TO_DEV_TOGGLE_PAUSE;
    public static final int AV_APP_TO_DEV_SET_VOLUME;
    public static final int AV_APP_TO_DEV_MUTE;
    public static final int AV_APP_TO_DEV_UNMUTE;
    public static final int AV_APP_TO_DEV_TOGGLE_MUTE;
    public static final int AV_APP_TO_DEV_GET_VOLUME;
    public static final int AV_APP_TO_DEV_GET_MUTE;
    public static final int AV_DEV_TO_APP_NONE;
    public static final int AV_DEV_TO_APP_CREATE_WINDOW_BUFFER;
    public static final int AV_DEV_TO_APP_PREPARE_WINDOW_BUFFER;
    public static final int AV_DEV_TO_APP_DISPLAY_WINDOW_BUFFER;
    public static final int AV_DEV_TO_APP_DESTROY_WINDOW_BUFFER;
    public static final int AV_DEV_TO_APP_BUFFER_OVERFLOW;
    public static final int AV_DEV_TO_APP_BUFFER_UNDERFLOW;
    public static final int AV_DEV_TO_APP_BUFFER_READABLE;
    public static final int AV_DEV_TO_APP_BUFFER_WRITABLE;
    public static final int AV_DEV_TO_APP_MUTE_STATE_CHANGED;
    public static final int AV_DEV_TO_APP_VOLUME_LEVEL_CHANGED;
    public static final int LIBAVDEVICE_VERSION_MAJOR = 62;
    public static final boolean FF_API_ALSA_CHANNELS = true;
    public static final int LIBAVDEVICE_VERSION_MINOR = 3;
    public static final int LIBAVDEVICE_VERSION_MICRO = 102;
    public static final int LIBAVDEVICE_VERSION_INT;
    public static final int LIBAVDEVICE_BUILD;
    public static final String LIBAVDEVICE_IDENT;

    @NoException
    @Cast(value={"unsigned"})
    public static native int avdevice_version();

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer avdevice_configuration();

    @NoException
    @Cast(value={"const char*"})
    public static native BytePointer avdevice_license();

    @NoException
    public static native void avdevice_register_all();

    @NoException
    @Const
    public static native AVInputFormat av_input_audio_device_next(@Const AVInputFormat var0);

    @NoException
    @Const
    public static native AVInputFormat av_input_video_device_next(@Const AVInputFormat var0);

    @NoException
    @Const
    public static native AVOutputFormat av_output_audio_device_next(@Const AVOutputFormat var0);

    @NoException
    @Const
    public static native AVOutputFormat av_output_video_device_next(@Const AVOutputFormat var0);

    @MemberGetter
    public static native int AV_APP_TO_DEV_NONE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_WINDOW_SIZE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_WINDOW_REPAINT();

    @MemberGetter
    public static native int AV_APP_TO_DEV_PAUSE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_PLAY();

    @MemberGetter
    public static native int AV_APP_TO_DEV_TOGGLE_PAUSE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_SET_VOLUME();

    @MemberGetter
    public static native int AV_APP_TO_DEV_MUTE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_UNMUTE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_TOGGLE_MUTE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_GET_VOLUME();

    @MemberGetter
    public static native int AV_APP_TO_DEV_GET_MUTE();

    @MemberGetter
    public static native int AV_DEV_TO_APP_NONE();

    @MemberGetter
    public static native int AV_DEV_TO_APP_CREATE_WINDOW_BUFFER();

    @MemberGetter
    public static native int AV_DEV_TO_APP_PREPARE_WINDOW_BUFFER();

    @MemberGetter
    public static native int AV_DEV_TO_APP_DISPLAY_WINDOW_BUFFER();

    @MemberGetter
    public static native int AV_DEV_TO_APP_DESTROY_WINDOW_BUFFER();

    @MemberGetter
    public static native int AV_DEV_TO_APP_BUFFER_OVERFLOW();

    @MemberGetter
    public static native int AV_DEV_TO_APP_BUFFER_UNDERFLOW();

    @MemberGetter
    public static native int AV_DEV_TO_APP_BUFFER_READABLE();

    @MemberGetter
    public static native int AV_DEV_TO_APP_BUFFER_WRITABLE();

    @MemberGetter
    public static native int AV_DEV_TO_APP_MUTE_STATE_CHANGED();

    @MemberGetter
    public static native int AV_DEV_TO_APP_VOLUME_LEVEL_CHANGED();

    @NoException
    public static native int avdevice_app_to_dev_control_message(AVFormatContext var0, @Cast(value={"AVAppToDevMessageType"}) int var1, Pointer var2, @Cast(value={"size_t"}) long var3);

    @NoException
    public static native int avdevice_dev_to_app_control_message(AVFormatContext var0, @Cast(value={"AVDevToAppMessageType"}) int var1, Pointer var2, @Cast(value={"size_t"}) long var3);

    @NoException
    public static native int avdevice_list_devices(AVFormatContext var0, @Cast(value={"AVDeviceInfoList**"}) PointerPointer var1);

    @NoException
    public static native int avdevice_list_devices(AVFormatContext var0, @ByPtrPtr AVDeviceInfoList var1);

    @NoException
    public static native void avdevice_free_list_devices(@Cast(value={"AVDeviceInfoList**"}) PointerPointer var0);

    @NoException
    public static native void avdevice_free_list_devices(@ByPtrPtr AVDeviceInfoList var0);

    @NoException
    public static native int avdevice_list_input_sources(@Const AVInputFormat var0, @Cast(value={"const char*"}) BytePointer var1, AVDictionary var2, @Cast(value={"AVDeviceInfoList**"}) PointerPointer var3);

    @NoException
    public static native int avdevice_list_input_sources(@Const AVInputFormat var0, @Cast(value={"const char*"}) BytePointer var1, AVDictionary var2, @ByPtrPtr AVDeviceInfoList var3);

    @NoException
    public static native int avdevice_list_input_sources(@Const AVInputFormat var0, String var1, AVDictionary var2, @ByPtrPtr AVDeviceInfoList var3);

    @NoException
    public static native int avdevice_list_output_sinks(@Const AVOutputFormat var0, @Cast(value={"const char*"}) BytePointer var1, AVDictionary var2, @Cast(value={"AVDeviceInfoList**"}) PointerPointer var3);

    @NoException
    public static native int avdevice_list_output_sinks(@Const AVOutputFormat var0, @Cast(value={"const char*"}) BytePointer var1, AVDictionary var2, @ByPtrPtr AVDeviceInfoList var3);

    @NoException
    public static native int avdevice_list_output_sinks(@Const AVOutputFormat var0, String var1, AVDictionary var2, @ByPtrPtr AVDeviceInfoList var3);

    @MemberGetter
    public static native int LIBAVDEVICE_VERSION_INT();

    @MemberGetter
    public static native String LIBAVDEVICE_IDENT();

    static {
        Loader.load();
        AV_APP_TO_DEV_NONE = avdevice.AV_APP_TO_DEV_NONE();
        AV_APP_TO_DEV_WINDOW_SIZE = avdevice.AV_APP_TO_DEV_WINDOW_SIZE();
        AV_APP_TO_DEV_WINDOW_REPAINT = avdevice.AV_APP_TO_DEV_WINDOW_REPAINT();
        AV_APP_TO_DEV_PAUSE = avdevice.AV_APP_TO_DEV_PAUSE();
        AV_APP_TO_DEV_PLAY = avdevice.AV_APP_TO_DEV_PLAY();
        AV_APP_TO_DEV_TOGGLE_PAUSE = avdevice.AV_APP_TO_DEV_TOGGLE_PAUSE();
        AV_APP_TO_DEV_SET_VOLUME = avdevice.AV_APP_TO_DEV_SET_VOLUME();
        AV_APP_TO_DEV_MUTE = avdevice.AV_APP_TO_DEV_MUTE();
        AV_APP_TO_DEV_UNMUTE = avdevice.AV_APP_TO_DEV_UNMUTE();
        AV_APP_TO_DEV_TOGGLE_MUTE = avdevice.AV_APP_TO_DEV_TOGGLE_MUTE();
        AV_APP_TO_DEV_GET_VOLUME = avdevice.AV_APP_TO_DEV_GET_VOLUME();
        AV_APP_TO_DEV_GET_MUTE = avdevice.AV_APP_TO_DEV_GET_MUTE();
        AV_DEV_TO_APP_NONE = avdevice.AV_DEV_TO_APP_NONE();
        AV_DEV_TO_APP_CREATE_WINDOW_BUFFER = avdevice.AV_DEV_TO_APP_CREATE_WINDOW_BUFFER();
        AV_DEV_TO_APP_PREPARE_WINDOW_BUFFER = avdevice.AV_DEV_TO_APP_PREPARE_WINDOW_BUFFER();
        AV_DEV_TO_APP_DISPLAY_WINDOW_BUFFER = avdevice.AV_DEV_TO_APP_DISPLAY_WINDOW_BUFFER();
        AV_DEV_TO_APP_DESTROY_WINDOW_BUFFER = avdevice.AV_DEV_TO_APP_DESTROY_WINDOW_BUFFER();
        AV_DEV_TO_APP_BUFFER_OVERFLOW = avdevice.AV_DEV_TO_APP_BUFFER_OVERFLOW();
        AV_DEV_TO_APP_BUFFER_UNDERFLOW = avdevice.AV_DEV_TO_APP_BUFFER_UNDERFLOW();
        AV_DEV_TO_APP_BUFFER_READABLE = avdevice.AV_DEV_TO_APP_BUFFER_READABLE();
        AV_DEV_TO_APP_BUFFER_WRITABLE = avdevice.AV_DEV_TO_APP_BUFFER_WRITABLE();
        AV_DEV_TO_APP_MUTE_STATE_CHANGED = avdevice.AV_DEV_TO_APP_MUTE_STATE_CHANGED();
        AV_DEV_TO_APP_VOLUME_LEVEL_CHANGED = avdevice.AV_DEV_TO_APP_VOLUME_LEVEL_CHANGED();
        LIBAVDEVICE_BUILD = LIBAVDEVICE_VERSION_INT = avdevice.LIBAVDEVICE_VERSION_INT();
        LIBAVDEVICE_IDENT = avdevice.LIBAVDEVICE_IDENT();
    }
}

