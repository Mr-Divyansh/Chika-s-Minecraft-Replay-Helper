/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.presets;

import org.bytedeco.ffmpeg.presets.avutil;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(inherit={avutil.class}, target="org.bytedeco.ffmpeg.swresample", global="org.bytedeco.ffmpeg.global.swresample", value={@Platform(cinclude={"<libswresample/swresample.h>", "<libswresample/version_major.h>", "<libswresample/version.h>"}, link={"swresample@.6"}), @Platform(value={"windows"}, preload={"swresample-6"})})
public class swresample
implements InfoMapper {
    @Override
    public void map(InfoMap infoMap) {
        infoMap.put(new Info("LIBSWRESAMPLE_VERSION").cppTypes(new String[0])).put(new Info("LIBSWRESAMPLE_VERSION_INT", "LIBSWRESAMPLE_IDENT").translate(false));
    }
}

