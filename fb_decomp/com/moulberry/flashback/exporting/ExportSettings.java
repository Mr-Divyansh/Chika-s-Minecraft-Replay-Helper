/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.exporting;

import com.moulberry.flashback.combo_options.AudioCodec;
import com.moulberry.flashback.combo_options.ExportProjection;
import com.moulberry.flashback.combo_options.VideoCodec;
import com.moulberry.flashback.combo_options.VideoContainer;
import com.moulberry.flashback.state.EditorState;
import java.nio.file.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record ExportSettings(@Nullable String name, EditorState editorState, Vec3 initialCameraPosition, float initialCameraYaw, float initialCameraPitch, int resolutionX, int resolutionY, int startTick, int endTick, ExportProjection projection, float orthographicZoom, double framerate, boolean resetRng, boolean depthMap, VideoContainer container, VideoCodec codec, String encoder, int bitrate, boolean transparent, boolean ssaa, boolean noGui, boolean stereoAudio, AudioCodec audioCodec, Path output, @Nullable String pngSequenceFormat) {
    public boolean recordAudio() {
        return this.audioCodec != null;
    }
}

