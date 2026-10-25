/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.editor.ui.windows;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.ReplayUI;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.ImVec2;
import imgui.moulberry90.type.ImString;
import net.minecraft.client.resources.language.I18n;

public class PreferencesWindow {
    private static boolean open = false;
    private static boolean close = false;

    public static void render() {
        if (open) {
            open = false;
            ImGui.openPopup("###Preferences");
        }
        boolean wasOpen = ImGui.isPopupOpen("###Preferences");
        ImVec2 center = ImGui.getMainViewport().getCenter();
        ImGui.setNextWindowPos(center.x, center.y, 8, 0.5f, 0.5f);
        ImGui.setNextWindowSize(ReplayUI.scaleUi(400), 0.0f);
        String title = I18n.get((String)"flashback.preferences", (Object[])new Object[0]);
        if (ImGuiHelper.beginPopupModalCloseable(title + "###Preferences", 2)) {
            if (close) {
                close = false;
                ImGui.closeCurrentPopup();
                ImGuiHelper.endPopupModalCloseable();
                return;
            }
            FlashbackConfigV1 config = Flashback.getConfig();
            ImGuiHelper.separatorWithText("Exporting");
            ImString imString = ImGuiHelper.createResizableImString(config.exporting.defaultExportFilename);
            ImGui.setNextItemWidth(ReplayUI.scaleUi(200));
            if (ImGui.inputText(I18n.get((String)"flashback.export_filename", (Object[])new Object[0]), imString)) {
                config.exporting.defaultExportFilename = ImGuiHelper.getString(imString);
                config.delayedSaveToDefaultFolder();
            }
            ImGuiHelper.tooltip(I18n.get((String)"flashback.export_filename_tooltip", (Object[])new Object[0]));
            ImGuiHelper.separatorWithText(I18n.get((String)"flashback.keyframes", (Object[])new Object[0]));
            ImGui.setNextItemWidth(ReplayUI.scaleUi(200));
            config.keyframes.defaultInterpolationType = ImGuiHelper.enumCombo(I18n.get((String)"flashback.default_interpolation", (Object[])new Object[0]), config.keyframes.defaultInterpolationType);
            ImGuiHelper.tooltip(I18n.get((String)"flashback.default_interpolation_description", (Object[])new Object[0]));
            if (ImGui.checkbox(I18n.get((String)"flashback.use_realtime_interpolation", (Object[])new Object[0]), config.keyframes.useRealtimeInterpolation)) {
                config.keyframes.useRealtimeInterpolation = !config.keyframes.useRealtimeInterpolation;
            }
            ImGuiHelper.tooltip(I18n.get((String)"flashback.use_realtime_interpolation_description", (Object[])new Object[0]));
            if (ImGui.collapsingHeader(I18n.get((String)"flashback.advanced", (Object[])new Object[0]))) {
                ImGui.textWrapped(I18n.get((String)"flashback.advanced_description", (Object[])new Object[0]));
                if (ImGui.checkbox(I18n.get((String)"flashback.disable_first_person_updates", (Object[])new Object[0]), config.advanced.disableIncreasedFirstPersonUpdates)) {
                    config.advanced.disableIncreasedFirstPersonUpdates = !config.advanced.disableIncreasedFirstPersonUpdates;
                    config.delayedSaveToDefaultFolder();
                }
                if (ImGui.checkbox(I18n.get((String)"flashback.disable_third_person_cancel", (Object[])new Object[0]), config.advanced.disableThirdPersonCancel)) {
                    config.advanced.disableThirdPersonCancel = !config.advanced.disableThirdPersonCancel;
                    config.delayedSaveToDefaultFolder();
                }
                ImGui.setNextItemWidth(ReplayUI.scaleUi(200));
                int[] value = new int[]{config.exporting.exportRenderDummyFrames};
                if (ImGui.sliderInt(I18n.get((String)"flashback.dummy_render_frames", (Object[])new Object[0]), value, 0, 100)) {
                    config.exporting.exportRenderDummyFrames = value[0];
                    config.delayedSaveToDefaultFolder();
                }
                ImGuiHelper.tooltip(I18n.get((String)"flashback.dummy_render_frames_description", (Object[])new Object[0]));
            }
            ImGuiHelper.endPopupModalCloseable();
        }
        if (wasOpen && !ImGui.isPopupOpen("###Preferences")) {
            Flashback.getConfig().saveToDefaultFolder();
        }
        close = false;
    }

    public static void open() {
        open = true;
    }
}

