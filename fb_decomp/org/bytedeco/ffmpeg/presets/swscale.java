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

@Properties(inherit={avutil.class}, target="org.bytedeco.ffmpeg.swscale", global="org.bytedeco.ffmpeg.global.swscale", value={@Platform(cinclude={"<libswscale/swscale.h>", "<libswscale/version_major.h>", "<libswscale/version.h>"}, link={"swscale@.9"}), @Platform(value={"windows"}, preload={"swscale-9"})})
public class swscale
implements InfoMapper {
    @Override
    public void map(InfoMap infoMap) {
        infoMap.put(new Info("LIBSWSCALE_VERSION").cppTypes(new String[0])).put(new Info("LIBSWSCALE_VERSION_INT", "LIBSWSCALE_IDENT").translate(false));
    }
}

