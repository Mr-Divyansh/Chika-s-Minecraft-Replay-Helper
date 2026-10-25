/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.editor.ui.windows;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.editor.ui.WindowOpenState;
import com.moulberry.flashback.editor.ui.windows.ImGuiWindowRenderer;
import com.moulberry.flashback.editor.ui.windows.KeybindsWindow;
import com.moulberry.flashback.editor.ui.windows.MovementWindow;
import com.moulberry.flashback.editor.ui.windows.PlayerListWindow;
import com.moulberry.flashback.editor.ui.windows.RenderFilterWindow;
import imgui.moulberry90.type.ImBoolean;
import java.util.Set;

public enum WindowType {
    PLAYER_LIST("player_list", PlayerListWindow::render),
    MOVEMENT("movement", MovementWindow::render),
    RENDER_FILTER("render_filter", RenderFilterWindow::render),
    KEYBINDS("keybinds", KeybindsWindow::render);

    private final String windowId;
    private final ImGuiWindowRenderer renderMethod;
    private final ImBoolean open = new ImBoolean();
    private WindowOpenState openState = WindowOpenState.UNKNOWN;

    private WindowType(String windowId, ImGuiWindowRenderer renderMethod) {
        this.windowId = windowId;
        this.renderMethod = renderMethod;
    }

    public void setOpen(boolean open) {
        FlashbackConfigV1 config = Flashback.getConfig();
        Set<String> openedWindows = config.internal.openedWindows;
        boolean changed = open ? openedWindows.add(this.windowId) : openedWindows.remove(this.windowId);
        if (changed) {
            config.delayedSaveToDefaultFolder();
        }
    }

    public void toggle() {
        FlashbackConfigV1 config = Flashback.getConfig();
        Set<String> openedWindows = config.internal.openedWindows;
        boolean playerListIsOpen = openedWindows.contains(this.windowId);
        if (playerListIsOpen) {
            openedWindows.remove(this.windowId);
        } else {
            openedWindows.add(this.windowId);
        }
        config.delayedSaveToDefaultFolder();
    }

    public static void renderAll() {
        FlashbackConfigV1 config = Flashback.getConfig();
        Set<String> openWindows = config.internal.openedWindows;
        for (WindowType windowType : WindowType.values()) {
            if (openWindows.contains(windowType.windowId)) {
                boolean justOpened = windowType.openState == WindowOpenState.CLOSED;
                windowType.open.set(true);
                windowType.renderMethod.render(windowType.open, justOpened);
                if (!windowType.open.get()) {
                    openWindows.remove(windowType.windowId);
                    config.delayedSaveToDefaultFolder();
                }
                windowType.openState = WindowOpenState.OPEN;
                continue;
            }
            windowType.openState = WindowOpenState.CLOSED;
        }
    }
}

