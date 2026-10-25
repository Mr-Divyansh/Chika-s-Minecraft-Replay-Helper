/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.input.InputQuirks
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.editor.ui.windows;

import com.moulberry.flashback.editor.keybinds.Keybind;
import com.moulberry.flashback.editor.keybinds.Keybinds;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.ImGuiViewport;
import imgui.moulberry90.type.ImBoolean;
import java.util.Set;
import net.minecraft.client.input.InputQuirks;
import net.minecraft.client.resources.language.I18n;

public class KeybindsWindow {
    private static String backedUpKeybind = null;
    private static int backupKey;
    private static boolean backupShiftMod;
    private static boolean backupCtrlMod;
    private static boolean backupAltMod;
    private static boolean backupSuperMod;

    public static void render(ImBoolean open, boolean newlyOpened) {
        ImGuiViewport viewport = ImGui.getMainViewport();
        if (newlyOpened) {
            ImGui.setNextWindowPos(viewport.getCenterX(), viewport.getCenterY(), 8, 0.5f, 0.5f);
            ImGui.setNextWindowSize(viewport.getSizeX() / 4.0f, viewport.getSizeY() / 2.0f, 8);
        }
        float warningSymbolWidth = ImGuiHelper.calcTextWidth("\u26a0");
        ImGui.setNextWindowSizeConstraints(250.0f, 50.0f, 5000.0f, 5000.0f);
        int flags = 4096;
        String title = I18n.get((String)"flashback.keybinds", (Object[])new Object[0]);
        if (ImGui.begin(title + "###Keybinds", open, flags)) {
            for (Keybind keybind : Keybinds.KEYBINDS) {
                String descriptionRaw = keybind.getDescriptionRaw();
                String description = keybind.getDescription();
                if (ImGui.selectable(description + "##" + descriptionRaw, false, 1)) {
                    ImGui.openPopup(descriptionRaw + "##EditKeybind");
                }
                ImGui.sameLine();
                String longKeyIdent = keybind.longKeyIdentifier();
                StringBuilder conflicts = null;
                if (keybind.getKey() != 0) {
                    Set<Keybind> keybindsForKey = Keybinds.keybindsForKey.get(keybind.getKey());
                    for (Keybind other : keybindsForKey) {
                        if (other == keybind || !keybind.hasSameMods(other)) continue;
                        if (conflicts == null) {
                            conflicts = new StringBuilder(I18n.get((String)"flashback.keybinds.conflicts_with", (Object[])new Object[0]));
                        }
                        conflicts.append('\n').append(other.getDescription());
                    }
                }
                float availX = ImGui.getContentRegionAvailX();
                float width = ImGuiHelper.calcTextWidth(longKeyIdent) + ImGui.getStyle().getFramePaddingX() * 2.0f;
                if (conflicts != null) {
                    width += warningSymbolWidth + ImGui.getStyle().getItemSpacingX();
                }
                ImGui.sameLine(0.0f, availX - width);
                if (conflicts != null) {
                    ImGui.text("\u26a0");
                    ImGuiHelper.tooltip(conflicts.toString());
                    ImGui.sameLine();
                }
                ImGui.smallButton(longKeyIdent);
                if (!ImGuiHelper.beginPopup(descriptionRaw + "##EditKeybind")) continue;
                ImGuiHelper.setEditingKeybind(keybind);
                if (backedUpKeybind == null || !backedUpKeybind.equals(keybind.getDescriptionRaw())) {
                    backedUpKeybind = keybind.getDescriptionRaw();
                    backupKey = keybind.getKey();
                    backupShiftMod = keybind.isShiftMod();
                    backupCtrlMod = keybind.isCtrlMod();
                    backupAltMod = keybind.isAltMod();
                    backupSuperMod = keybind.isSuperMod();
                }
                ImGui.text(description);
                ImGui.button(longKeyIdent + "##KeybindPreview", -1.0f, 0.0f);
                if (keybind.isForceScrollKey()) {
                    if (ImGui.isItemClicked()) {
                        long window = ImGui.getWindowViewport().getPlatformHandle();
                        boolean shiftDown = Keybind.isShiftDownGLFW(window);
                        boolean ctrlDown = Keybind.isCtrlDownGLFW(window);
                        boolean altDown = Keybind.isAltDownGLFW(window);
                        boolean superDown = Keybind.isSuperDownGLFW(window);
                        keybind.set(17, shiftDown, InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY ? superDown : ctrlDown, altDown, InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY ? ctrlDown : superDown);
                    }
                } else if (ImGui.isItemHovered()) {
                    for (int i = 0; i < 5; ++i) {
                        if (!ImGui.isMouseClicked(i)) continue;
                        long window = ImGui.getWindowViewport().getPlatformHandle();
                        boolean shiftDown = Keybind.isShiftDownGLFW(window);
                        boolean ctrlDown = Keybind.isCtrlDownGLFW(window);
                        boolean altDown = Keybind.isAltDownGLFW(window);
                        boolean superDown = Keybind.isSuperDownGLFW(window);
                        keybind.set(-i - 1, shiftDown, InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY ? superDown : ctrlDown, altDown, InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY ? ctrlDown : superDown);
                    }
                }
                if (ImGui.button(I18n.get((String)"gui.ok", (Object[])new Object[0]))) {
                    backedUpKeybind = null;
                    ImGui.closeCurrentPopup();
                }
                ImGui.sameLine();
                if (ImGui.button(I18n.get((String)"gui.cancel", (Object[])new Object[0]))) {
                    keybind.set(backupKey, backupShiftMod, backupCtrlMod, backupAltMod, backupSuperMod);
                    backedUpKeybind = null;
                    ImGui.closeCurrentPopup();
                }
                ImGui.sameLine();
                if (ImGui.button(I18n.get((String)"flashback.keybinds.unbind", (Object[])new Object[0]))) {
                    keybind.clear();
                    backedUpKeybind = null;
                    ImGui.closeCurrentPopup();
                }
                ImGui.endPopup();
            }
        }
        ImGui.end();
    }
}

