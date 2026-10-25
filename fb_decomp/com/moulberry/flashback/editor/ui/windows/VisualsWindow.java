/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.editor.ui.windows;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.combo_options.Sizing;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.impl.CameraShakeKeyframe;
import com.moulberry.flashback.keyframe.impl.FOVKeyframe;
import com.moulberry.flashback.keyframe.impl.TimeOfDayKeyframe;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.record.FlashbackMeta;
import com.moulberry.flashback.state.EditorScene;
import com.moulberry.flashback.state.EditorSceneHistoryAction;
import com.moulberry.flashback.state.EditorSceneHistoryEntry;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import com.moulberry.flashback.state.KeyframeTrack;
import com.moulberry.flashback.visuals.ReplayVisuals;
import imgui.moulberry90.ImGui;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;

public class VisualsWindow {
    private static final float[] floatBuffer = new float[]{0.0f};
    private static final int[] intBuffer = new int[]{0};

    public static void render() {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer == null) {
            return;
        }
        FlashbackMeta metadata = replayServer.getMetadata();
        EditorState editorState = EditorStateManager.get(metadata.replayIdentifier);
        ReplayVisuals visuals = editorState.replayVisuals;
        if (ImGui.begin(I18n.get((String)"flashback.visuals", (Object[])new Object[0]) + "###Visuals")) {
            ImGuiHelper.separatorWithText(I18n.get((String)"flashback.visuals.gui", (Object[])new Object[0]));
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.gui.chat", (Object[])new Object[0]), visuals.showChat)) {
                visuals.showChat = !visuals.showChat;
                editorState.markDirty();
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.gui.boss_bar", (Object[])new Object[0]), visuals.showBossBar)) {
                visuals.showBossBar = !visuals.showBossBar;
                editorState.markDirty();
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.gui.title_text", (Object[])new Object[0]), visuals.showTitleText)) {
                visuals.showTitleText = !visuals.showTitleText;
                editorState.markDirty();
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.gui.scoreboard", (Object[])new Object[0]), visuals.showScoreboard)) {
                visuals.showScoreboard = !visuals.showScoreboard;
                editorState.markDirty();
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.gui.action_bar", (Object[])new Object[0]), visuals.showActionBar)) {
                visuals.showActionBar = !visuals.showActionBar;
                editorState.markDirty();
            }
            if (Minecraft.getInstance().getCameraEntity() != null && Minecraft.getInstance().getCameraEntity() != Minecraft.getInstance().player && ImGui.checkbox(I18n.get((String)"flashback.visuals.gui.hotbar", (Object[])new Object[0]), visuals.showHotbar)) {
                visuals.showHotbar = !visuals.showHotbar;
                editorState.markDirty();
            }
            ImGuiHelper.separatorWithText(I18n.get((String)"flashback.visuals.world", (Object[])new Object[0]));
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.world.render_blocks", (Object[])new Object[0]), visuals.renderBlocks)) {
                visuals.renderBlocks = !visuals.renderBlocks;
                editorState.markDirty();
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.world.render_entities", (Object[])new Object[0]), visuals.renderEntities)) {
                visuals.renderEntities = !visuals.renderEntities;
                editorState.markDirty();
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.world.render_players", (Object[])new Object[0]), visuals.renderPlayers)) {
                visuals.renderPlayers = !visuals.renderPlayers;
                editorState.markDirty();
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.world.render_particles", (Object[])new Object[0]), visuals.renderParticles)) {
                visuals.renderParticles = !visuals.renderParticles;
                editorState.markDirty();
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.world.render_sky", (Object[])new Object[0]), visuals.renderSky)) {
                visuals.renderSky = !visuals.renderSky;
                editorState.markDirty();
            }
            if (!visuals.renderSky) {
                if (ImGui.colorButton(I18n.get((String)"flashback.visuals.world.sky_colour", (Object[])new Object[0]), visuals.skyColour)) {
                    ImGui.openPopup("##EditSkyColour");
                }
                ImGui.sameLine();
                ImGui.textUnformatted(I18n.get((String)"flashback.visuals.world.sky_colour", (Object[])new Object[0]));
                if (ImGuiHelper.beginPopup("##EditSkyColour")) {
                    ImGui.colorPicker3(I18n.get((String)"flashback.visuals.world.sky_colour", (Object[])new Object[0]), visuals.skyColour);
                    ImGui.endPopup();
                }
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.world.render_nametags", (Object[])new Object[0]), visuals.renderNametags)) {
                visuals.renderNametags = !visuals.renderNametags;
                editorState.markDirty();
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.world.render_beacon_beams", (Object[])new Object[0]), visuals.renderBeaconBeams)) {
                visuals.renderBeaconBeams = !visuals.renderBeaconBeams;
                editorState.markDirty();
            }
            ImGuiHelper.separatorWithText(I18n.get((String)"flashback.visuals.overrides", (Object[])new Object[0]));
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.overrides.override_fog", (Object[])new Object[0]), visuals.overrideFog)) {
                visuals.overrideFog = !visuals.overrideFog;
                editorState.markDirty();
            }
            if (visuals.overrideFog) {
                VisualsWindow.floatBuffer[0] = visuals.overrideFogStart;
                if (ImGui.sliderFloat(I18n.get((String)"flashback.fog_start", (Object[])new Object[0]), floatBuffer, 0.0f, 512.0f)) {
                    visuals.overrideFogStart = floatBuffer[0];
                    editorState.markDirty();
                }
                VisualsWindow.floatBuffer[0] = visuals.overrideFogEnd;
                if (ImGui.sliderFloat(I18n.get((String)"flashback.fog_end", (Object[])new Object[0]), floatBuffer, 0.0f, 512.0f)) {
                    visuals.overrideFogEnd = floatBuffer[0];
                    editorState.markDirty();
                }
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.overrides.override_fog_colour", (Object[])new Object[0]), visuals.overrideFogColour)) {
                visuals.overrideFogColour = !visuals.overrideFogColour;
                editorState.markDirty();
            }
            if (visuals.overrideFogColour) {
                if (ImGui.colorButton(I18n.get((String)"flashback.visuals.overrides.fog_colour", (Object[])new Object[0]), visuals.fogColour)) {
                    ImGui.openPopup("##EditFogColour");
                }
                ImGui.sameLine();
                ImGui.textUnformatted(I18n.get((String)"flashback.visuals.overrides.fog_colour", (Object[])new Object[0]));
                if (ImGuiHelper.beginPopup("##EditFogColour")) {
                    ImGui.colorPicker3(I18n.get((String)"flashback.visuals.overrides.fog_colour", (Object[])new Object[0]), visuals.fogColour);
                    ImGui.endPopup();
                }
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.overrides.override_fov", (Object[])new Object[0]), visuals.overrideFov)) {
                visuals.overrideFov = !visuals.overrideFov;
                Minecraft.getInstance().levelRenderer.needsUpdate();
                editorState.markDirty();
            }
            if (visuals.overrideFov) {
                if (visuals.overrideFovAmount < 0.0f) {
                    visuals.overrideFovAmount = Flashback.getConfig().internal.defaultOverrideFov;
                }
                ImGui.sameLine();
                if (ImGui.smallButton("+")) {
                    VisualsWindow.addKeyframe(editorState, replayServer, new FOVKeyframe(visuals.overrideFovAmount));
                }
                ImGuiHelper.tooltip(I18n.get((String)"flashback.add_fov_keyframe", (Object[])new Object[0]));
                VisualsWindow.floatBuffer[0] = visuals.overrideFovAmount;
                if (ImGui.sliderFloat(I18n.get((String)"flashback.fov", (Object[])new Object[0]), floatBuffer, 1.0f, 110.0f, "%.1f")) {
                    visuals.setFov(floatBuffer[0]);
                    editorState.markDirty();
                }
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.overrides.override_time", (Object[])new Object[0]), visuals.overrideTimeOfDay >= 0L)) {
                visuals.overrideTimeOfDay = visuals.overrideTimeOfDay >= 0L ? -1L : (long)((int)(Minecraft.getInstance().level.getDefaultClockTime() % 24000L));
                editorState.markDirty();
            }
            if (visuals.overrideTimeOfDay >= 0L) {
                ImGui.sameLine();
                if (ImGui.smallButton("+")) {
                    VisualsWindow.addKeyframe(editorState, replayServer, new TimeOfDayKeyframe((int)visuals.overrideTimeOfDay));
                }
                ImGuiHelper.tooltip(I18n.get((String)"flashback.add_time_keyframe", (Object[])new Object[0]));
                VisualsWindow.intBuffer[0] = (int)visuals.overrideTimeOfDay;
                if (ImGui.sliderInt(I18n.get((String)"flashback.time", (Object[])new Object[0]), intBuffer, 0, 24000)) {
                    visuals.overrideTimeOfDay = intBuffer[0];
                    editorState.markDirty();
                }
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.overrides.night_vision", (Object[])new Object[0]), visuals.overrideNightVision)) {
                boolean bl = visuals.overrideNightVision = !visuals.overrideNightVision;
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.visuals.overrides.camera_shake", (Object[])new Object[0]), visuals.overrideCameraShake)) {
                visuals.overrideCameraShake = !visuals.overrideCameraShake;
                editorState.markDirty();
            }
            if (visuals.overrideCameraShake) {
                ImGui.sameLine();
                if (ImGui.smallButton("+")) {
                    if (visuals.cameraShakeSplitParams) {
                        VisualsWindow.addKeyframe(editorState, replayServer, new CameraShakeKeyframe(visuals.cameraShakeXFrequency, visuals.cameraShakeXAmplitude, visuals.cameraShakeYFrequency, visuals.cameraShakeYAmplitude, true));
                    } else {
                        float frequency = (visuals.cameraShakeXFrequency + visuals.cameraShakeYFrequency) / 2.0f;
                        float amplitude = (visuals.cameraShakeXAmplitude + visuals.cameraShakeYAmplitude) / 2.0f;
                        VisualsWindow.addKeyframe(editorState, replayServer, new CameraShakeKeyframe(frequency, amplitude, frequency, amplitude, false));
                    }
                }
                ImGuiHelper.tooltip(I18n.get((String)"flashback.add_camera_shake_keyframe", (Object[])new Object[0]));
                if (ImGui.checkbox(I18n.get((String)"flashback.split_yx", (Object[])new Object[0]), visuals.cameraShakeSplitParams)) {
                    visuals.cameraShakeSplitParams = !visuals.cameraShakeSplitParams;
                    editorState.markDirty();
                }
                if (visuals.cameraShakeSplitParams) {
                    ImGui.setNextItemWidth(100.0f);
                    VisualsWindow.floatBuffer[0] = visuals.cameraShakeXFrequency;
                    if (ImGui.sliderFloat(I18n.get((String)"flashback.frequency_x", (Object[])new Object[0]), floatBuffer, 0.1f, 10.0f, "%.1f")) {
                        visuals.cameraShakeXFrequency = floatBuffer[0];
                        editorState.markDirty();
                    }
                    ImGui.setNextItemWidth(100.0f);
                    VisualsWindow.floatBuffer[0] = visuals.cameraShakeXAmplitude;
                    if (ImGui.sliderFloat(I18n.get((String)"flashback.amplitude_x", (Object[])new Object[0]), floatBuffer, 0.0f, 10.0f, "%.1f")) {
                        visuals.cameraShakeXAmplitude = floatBuffer[0];
                        editorState.markDirty();
                    }
                    ImGui.setNextItemWidth(100.0f);
                    VisualsWindow.floatBuffer[0] = visuals.cameraShakeYFrequency;
                    if (ImGui.sliderFloat(I18n.get((String)"flashback.frequency_y", (Object[])new Object[0]), floatBuffer, 0.1f, 10.0f, "%.1f")) {
                        visuals.cameraShakeYFrequency = floatBuffer[0];
                        editorState.markDirty();
                    }
                    ImGui.setNextItemWidth(100.0f);
                    VisualsWindow.floatBuffer[0] = visuals.cameraShakeYAmplitude;
                    if (ImGui.sliderFloat(I18n.get((String)"flashback.amplitude_y", (Object[])new Object[0]), floatBuffer, 0.0f, 10.0f, "%.1f")) {
                        visuals.cameraShakeYAmplitude = floatBuffer[0];
                        editorState.markDirty();
                    }
                } else {
                    ImGui.setNextItemWidth(100.0f);
                    VisualsWindow.floatBuffer[0] = (visuals.cameraShakeXFrequency + visuals.cameraShakeYFrequency) / 2.0f;
                    if (ImGui.sliderFloat(I18n.get((String)"flashback.frequency", (Object[])new Object[0]), floatBuffer, 0.1f, 10.0f, "%.1f")) {
                        visuals.cameraShakeXFrequency = floatBuffer[0];
                        visuals.cameraShakeYFrequency = floatBuffer[0];
                        editorState.markDirty();
                    }
                    ImGui.setNextItemWidth(100.0f);
                    VisualsWindow.floatBuffer[0] = (visuals.cameraShakeXAmplitude + visuals.cameraShakeYAmplitude) / 2.0f;
                    if (ImGui.sliderFloat(I18n.get((String)"flashback.amplitude", (Object[])new Object[0]), floatBuffer, 0.0f, 10.0f, "%.1f")) {
                        visuals.cameraShakeXAmplitude = floatBuffer[0];
                        visuals.cameraShakeYAmplitude = floatBuffer[0];
                        editorState.markDirty();
                    }
                }
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.camera_roll", (Object[])new Object[0]), visuals.overrideRoll)) {
                visuals.overrideRoll = !visuals.overrideRoll;
                Minecraft.getInstance().levelRenderer.needsUpdate();
                editorState.markDirty();
            }
            if (visuals.overrideRoll) {
                VisualsWindow.floatBuffer[0] = visuals.overrideRollAmount;
                if (ImGui.sliderFloat(I18n.get((String)"flashback.roll", (Object[])new Object[0]), floatBuffer, -180.0f, 180.0f, "%.1f")) {
                    visuals.overrideRollAmount = floatBuffer[0];
                    Minecraft.getInstance().levelRenderer.needsUpdate();
                    editorState.markDirty();
                }
            }
            visuals.overrideWeatherMode = ImGuiHelper.enumCombo(I18n.get((String)"flashback.weather", (Object[])new Object[0]), visuals.overrideWeatherMode);
            ImGuiHelper.separatorWithText(I18n.get((String)"flashback.other", (Object[])new Object[0]));
            if (ImGui.checkbox(I18n.get((String)"flashback.rule_of_thirds_guide", (Object[])new Object[0]), visuals.ruleOfThirdsGuide)) {
                visuals.ruleOfThirdsGuide = !visuals.ruleOfThirdsGuide;
                editorState.markDirty();
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.center_guide", (Object[])new Object[0]), visuals.centerGuide)) {
                visuals.centerGuide = !visuals.centerGuide;
                editorState.markDirty();
            }
            if (replayServer.hasRtcData()) {
                FlashbackConfigV1 config = Flashback.getConfig();
                if (ImGui.checkbox(I18n.get((String)"flashback.overlay.rtc_overlay", (Object[])new Object[0]), config.overlay.rtcOverlay)) {
                    config.overlay.rtcOverlay = !config.overlay.rtcOverlay;
                    config.delayedSaveToDefaultFolder();
                }
                ImGui.setItemTooltip(I18n.get((String)"flashback.overlay.rtc_overlay.description", (Object[])new Object[0]));
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.camera_path", (Object[])new Object[0]), visuals.cameraPath)) {
                visuals.cameraPath = !visuals.cameraPath;
                editorState.markDirty();
            }
            visuals.sizing = ImGuiHelper.enumCombo(I18n.get((String)"flashback.sizing", (Object[])new Object[0]), visuals.sizing);
            if (visuals.sizing == Sizing.CHANGE_ASPECT_RATIO) {
                visuals.changeAspectRatio = ImGuiHelper.enumCombo(I18n.get((String)"flashback.aspect", (Object[])new Object[0]), visuals.changeAspectRatio);
                editorState.markDirty();
            }
            if (!editorState.hideDuringExport.isEmpty() && ImGui.button(I18n.get((String)"flashback.unhide_all_entities", (Object[])new Object[0]))) {
                editorState.hideDuringExport.clear();
                editorState.markDirty();
            }
            if (replayServer.hasServerResourcePack && ImGui.checkbox(I18n.get((String)"flashback.disable_server_resource_packs", (Object[])new Object[0]), visuals.disableServerResourcePack)) {
                visuals.disableServerResourcePack = !visuals.disableServerResourcePack;
            }
        }
        ImGui.end();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void addKeyframe(EditorState editorState, ReplayServer replayServer, Keyframe keyframe) {
        KeyframeType<?> keyframeType = keyframe.keyframeType();
        long stamp = editorState.acquireWrite();
        try {
            KeyframeTrack keyframeTrack;
            int i;
            EditorScene scene = editorState.getCurrentScene(stamp);
            for (i = 0; i < scene.keyframeTracks.size(); ++i) {
                keyframeTrack = scene.keyframeTracks.get(i);
                if (!keyframeTrack.enabled || keyframeTrack.keyframeType != keyframeType) continue;
                scene.setKeyframe(i, replayServer.getReplayTick(), keyframe);
                return;
            }
            for (i = 0; i < scene.keyframeTracks.size(); ++i) {
                keyframeTrack = scene.keyframeTracks.get(i);
                if (keyframeTrack.keyframeType != keyframeType) continue;
                scene.setKeyframe(i, replayServer.getReplayTick(), keyframe);
                return;
            }
            String description = I18n.get((String)"flashback.added_named_keyframe", (Object[])new Object[]{keyframeType.name()});
            int newKeyframeTrackIndex = scene.keyframeTracks.size();
            scene.push(new EditorSceneHistoryEntry(List.of(new EditorSceneHistoryAction.RemoveTrack(keyframeType, newKeyframeTrackIndex)), List.of(new EditorSceneHistoryAction.AddTrack(keyframeType, newKeyframeTrackIndex), new EditorSceneHistoryAction.SetKeyframe(keyframeType, newKeyframeTrackIndex, replayServer.getReplayTick(), keyframe)), description));
            editorState.markDirty();
        }
        finally {
            editorState.release(stamp);
        }
    }
}

