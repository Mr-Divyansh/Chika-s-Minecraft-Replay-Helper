/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.presets;

import org.bytedeco.ffmpeg.presets.avformat;
import org.bytedeco.ffmpeg.presets.swresample;
import org.bytedeco.ffmpeg.presets.swscale;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(inherit={avformat.class, swresample.class, swscale.class}, target="org.bytedeco.ffmpeg.avfilter", global="org.bytedeco.ffmpeg.global.avfilter", value={@Platform(cinclude={"<libavfilter/avfilter.h>", "<libavfilter/buffersink.h>", "<libavfilter/buffersrc.h>", "<libavfilter/version_major.h>", "<libavfilter/version.h>"}, link={"avfilter@.11"}), @Platform(value={"windows"}, preload={"avfilter-11"})})
public class avfilter
implements InfoMapper {
    @Override
    public void map(InfoMap infoMap) {
        infoMap.put(new Info("AVFilterPool", "AVFilterCommand", "AVFilterChannelLayouts", "FFFrameQueue").cast().pointerTypes("Pointer")).put(new Info("LIBAVFILTER_VERSION").cppTypes(new String[0])).put(new Info("LIBAVFILTER_VERSION_INT", "LIBAVFILTER_IDENT").translate(false)).put(new Info("FF_API_SWS_PARAM_OPTION", "FF_API_BUFFERSINK_ALLOC", "FF_API_PAD_COUNT", "FF_API_LIBPLACEBO_OPTS", "FF_API_LINK_PUBLIC", "FF_API_BUFFERSINK_OPTS", "FF_API_CONTEXT_PUBLIC", "FF_API_LIBNPP_SUPPORT").define().translate().cppTypes("bool")).put(new Info("AV_HAVE_INCOMPATIBLE_LIBAV_ABI || !FF_API_OLD_GRAPH_PARSE").define(true)).put(new Info("!FF_API_FOO_COUNT", "FF_INTERNAL_FIELDS").define(false));
    }
}

