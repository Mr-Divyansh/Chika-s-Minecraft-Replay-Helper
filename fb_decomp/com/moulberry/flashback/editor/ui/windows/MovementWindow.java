/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.editor.ui.windows;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.combo_options.MovementDirection;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.ImGuiViewport;
import imgui.moulberry90.type.ImBoolean;
import net.minecraft.client.resources.language.I18n;

public class MovementWindow {
    private static boolean wasDocked = false;

    public static void render(ImBoolean open, boolean newlyOpened) {
        String title;
        if (newlyOpened) {
            ImGuiViewport viewport = ImGui.getMainViewport();
            ImGui.setNextWindowPos(viewport.getCenterX(), viewport.getCenterY(), 8, 0.5f, 0.5f);
        }
        ImGui.setNextWindowSizeConstraints(250.0f, 50.0f, 5000.0f, 5000.0f);
        int flags = 4096;
        if (!wasDocked) {
            flags |= 0x40;
        }
        if (ImGui.begin((title = I18n.get((String)"flashback.movement", (Object[])new Object[0])) + "###Movement", open, flags)) {
            wasDocked = ImGui.isWindowDocked();
            FlashbackConfigV1 config = Flashback.getConfig();
            MovementDirection newDirection = ImGuiHelper.enumCombo(I18n.get((String)"flashback.movement_direction", (Object[])new Object[0]), config.editorMovement.flightDirection);
            if (newDirection != config.editorMovement.flightDirection) {
                config.editorMovement.flightDirection = newDirection;
                config.delayedSaveToDefaultFolder();
            }
            float[] momentum = new float[]{config.editorMovement.flightMomentum};
            ImGui.sliderFloat(I18n.get((String)"flashback.momentum", (Object[])new Object[0]), momentum, 0.0f, 1.0f);
            if (config.editorMovement.flightMomentum != momentum[0]) {
                config.editorMovement.flightMomentum = momentum[0];
                config.delayedSaveToDefaultFolder();
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.lock_x", (Object[])new Object[0]), config.editorMovement.flightLockX)) {
                config.editorMovement.flightLockX = !config.editorMovement.flightLockX;
                config.delayedSaveToDefaultFolder();
            }
            ImGui.sameLine();
            if (ImGui.checkbox(I18n.get((String)"flashback.lock_y", (Object[])new Object[0]), config.editorMovement.flightLockY)) {
                config.editorMovement.flightLockY = !config.editorMovement.flightLockY;
                config.delayedSaveToDefaultFolder();
            }
            ImGui.sameLine();
            if (ImGui.checkbox(I18n.get((String)"flashback.lock_z", (Object[])new Object[0]), config.editorMovement.flightLockZ)) {
                config.editorMovement.flightLockZ = !config.editorMovement.flightLockZ;
                config.delayedSaveToDefaultFolder();
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.lock_yaw", (Object[])new Object[0]), config.editorMovement.flightLockYaw)) {
                config.editorMovement.flightLockYaw = !config.editorMovement.flightLockYaw;
                config.delayedSaveToDefaultFolder();
            }
            ImGui.sameLine();
            if (ImGui.checkbox(I18n.get((String)"flashback.lock_pitch", (Object[])new Object[0]), config.editorMovement.flightLockPitch)) {
                config.editorMovement.flightLockPitch = !config.editorMovement.flightLockPitch;
                config.delayedSaveToDefaultFolder();
            }
        }
        ImGui.end();
    }
}

