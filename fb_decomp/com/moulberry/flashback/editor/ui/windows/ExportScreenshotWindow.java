/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.editor.ui.windows;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.Utils;
import com.moulberry.flashback.combo_options.ExportProjection;
import com.moulberry.flashback.combo_options.VideoCodec;
import com.moulberry.flashback.combo_options.VideoContainer;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.windows.StartExportWindow;
import com.moulberry.flashback.exporting.AsyncFileDialogs;
import com.moulberry.flashback.exporting.ExportJob;
import com.moulberry.flashback.exporting.ExportSettings;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import imgui.moulberry90.ImGui;
import java.nio.file.Path;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;

public class ExportScreenshotWindow {
    private static boolean open = false;
    private static boolean close = false;

    public static void render() {
        String title;
        if (open) {
            open = false;
            ImGui.openPopup("###ExportScreenshot");
        }
        if (ImGuiHelper.beginPopupModalCloseable((title = I18n.get((String)"flashback.export_screenshot", (Object[])new Object[0])) + "###ExportScreenshot", 64)) {
            boolean isFullscreen;
            int resY;
            int resX;
            if (close) {
                close = false;
                ImGui.closeCurrentPopup();
                ImGuiHelper.endPopupModalCloseable();
                return;
            }
            FlashbackConfigV1 config = Flashback.getConfig();
            config.forceDefaultExportSettings.apply(config.internalExport);
            if (config.internalExport.resolution == null || config.internalExport.resolution.length != 2) {
                config.internalExport.resolution = new int[]{1920, 1080};
            }
            ImGuiHelper.inputInt(I18n.get((String)"flashback.resolution", (Object[])new Object[0]), config.internalExport.resolution);
            config.internalExport.resolution[0] = Math.max(1, config.internalExport.resolution[0]);
            config.internalExport.resolution[1] = Math.max(1, config.internalExport.resolution[1]);
            EditorState editorState = EditorStateManager.getCurrent();
            config.internalExport.projection = ImGuiHelper.enumCombo(I18n.get((String)"flashback.projection", (Object[])new Object[0]), config.internalExport.projection);
            if (config.internalExport.projection == ExportProjection.ORTHOGRAPHIC) {
                ImGui.sliderFloat("Ortho Zoom", config.internalExport.orthographicZoom, 0.0f, 10.0f);
            } else if (config.internalExport.projection == ExportProjection.CUBE_MAP) {
                resX = config.internalExport.resolution[0];
                resY = config.internalExport.resolution[1];
                if (resX % 4 != 0 || resY % 3 != 0 || resX != resY * 4 / 3) {
                    ImGui.text("Warning: Resolution should be 4:3 for cube map export");
                }
            } else if (config.internalExport.projection == ExportProjection.EQUIRECTANGULAR && (resX = config.internalExport.resolution[0]) != (resY = config.internalExport.resolution[1]) * 2) {
                ImGui.text("Warning: Resolution should be 2:1 for equirectangular export");
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.ssaa", (Object[])new Object[0]), config.internalExport.ssaa)) {
                config.internalExport.ssaa = !config.internalExport.ssaa;
            }
            ImGuiHelper.tooltip(I18n.get((String)"flashback.ssaa_tooltip", (Object[])new Object[0]));
            ImGui.sameLine();
            if (ImGui.checkbox(I18n.get((String)"flashback.no_gui", (Object[])new Object[0]), config.internalExport.noGui)) {
                config.internalExport.noGui = !config.internalExport.noGui;
            }
            ImGuiHelper.tooltip(I18n.get((String)"flashback.no_gui_tooltip", (Object[])new Object[0]));
            if (editorState != null && !editorState.replayVisuals.renderSky && ImGui.checkbox(I18n.get((String)"flashback.transparent_sky", (Object[])new Object[0]), config.internalExport.transparentBackground)) {
                boolean bl = config.internalExport.transparentBackground = !config.internalExport.transparentBackground;
            }
            if (isFullscreen = Minecraft.getInstance().getWindow().isFullscreen()) {
                ImGui.separator();
                ImGui.textWrapped(I18n.get((String)"flashback.export_disable_fullscreen", (Object[])new Object[0]));
            }
            if (isFullscreen) {
                ImGui.beginDisabled();
            }
            if (editorState != null && ImGui.button(I18n.get((String)"flashback.take_screenshot", (Object[])new Object[0])) && !isFullscreen) {
                String defaultName = StartExportWindow.getDefaultFilename(null, "png", config);
                String defaultExportPathString = config.internalExport.defaultExportPath;
                AsyncFileDialogs.saveFileDialog(defaultExportPathString, defaultName, "PNG", "png").thenAccept(pathStr -> {
                    if (pathStr != null) {
                        Path path = Path.of(pathStr, new String[0]);
                        config.internalExport.defaultExportPath = path.getParent().toString();
                        LocalPlayer player = Minecraft.getInstance().player;
                        int tick = Flashback.getReplayServer().getReplayTick();
                        boolean transparent = config.internalExport.transparentBackground && !editorState.replayVisuals.renderSky;
                        boolean ssaa = config.internalExport.ssaa;
                        boolean noGui = config.internalExport.noGui;
                        EditorState copiedEditorState = editorState.copyWithoutKeyframes();
                        VideoCodec codec = VideoContainer.PNG_SEQUENCE.getSupportedVideoCodecs(transparent)[0];
                        String encoder = codec.getEncoders()[0];
                        ExportSettings settings = new ExportSettings(null, copiedEditorState, player.position(), player.getYRot(), player.getXRot(), config.internalExport.resolution[0], config.internalExport.resolution[1], tick, tick, config.internalExport.projection, config.internalExport.orthographicZoom[0], 1.0, false, false, VideoContainer.PNG_SEQUENCE, codec, encoder, 0, transparent, ssaa, noGui, false, null, path, null);
                        close = true;
                        ++Utils.exportSequenceCount;
                        Flashback.EXPORT_JOB = new ExportJob(settings);
                        config.delayedSaveToDefaultFolder();
                    }
                });
            }
            if (isFullscreen) {
                ImGui.endDisabled();
            }
            ImGuiHelper.endPopupModalCloseable();
        }
        close = false;
    }

    public static void open() {
        open = true;
    }
}

