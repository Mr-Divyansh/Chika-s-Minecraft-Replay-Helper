/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.presets;

import org.bytedeco.ffmpeg.presets.avfilter;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(inherit={avfilter.class}, target="org.bytedeco.ffmpeg.avdevice", global="org.bytedeco.ffmpeg.global.avdevice", value={@Platform(cinclude={"<libavdevice/avdevice.h>", "<libavdevice/version_major.h>", "<libavdevice/version.h>"}, link={"avdevice@.62"}), @Platform(value={"windows"}, preload={"avdevice-62"})})
public class avdevice
implements InfoMapper {
    @Override
    public void map(InfoMap infoMap) {
        infoMap.putFirst(new Info("AVDeviceInfoList").pointerTypes("AVDeviceInfoList")).putFirst(new Info("AVDeviceCapabilitiesQuery").pointerTypes("AVDeviceCapabilitiesQuery")).put(new Info("LIBAVDEVICE_VERSION").cppTypes(new String[0])).put(new Info("LIBAVDEVICE_VERSION_INT", "LIBAVDEVICE_IDENT").translate(false)).put(new Info("FF_API_DEVICE_CAPABILITIES", "FF_API_BKTR_DEVICE", "FF_API_OPENGL_DEVICE", "FF_API_SDL2_DEVICE", "FF_API_ALSA_CHANNELS").define().translate().cppTypes("bool")).put(new Info("av_device_capabilities").skip());
    }
}

