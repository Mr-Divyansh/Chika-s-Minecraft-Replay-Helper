/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.ffmpeg.presets;

import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

public class postproc
implements InfoMapper {
    @Override
    public void map(InfoMap infoMap) {
        infoMap.put(new Info("QP_STORE_T").cppTypes(new String[0]).valueTypes("byte").pointerTypes("BytePointer")).put(new Info("LIBPOSTPROC_VERSION").cppTypes(new String[0])).put(new Info("LIBPOSTPROC_VERSION_INT", "LIBPOSTPROC_BUILD", "LIBPOSTPROC_IDENT").skip()).put(new Info("LIBPOSTPROC_VERSION_INT < (52<<16)").define(false));
    }
}

