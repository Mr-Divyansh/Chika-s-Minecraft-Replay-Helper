/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.TitleScreen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.Component
 */
package com.moulberry.flashback.editor.ui.windows;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.editor.ui.windows.ExportQueueWindow;
import com.moulberry.flashback.editor.ui.windows.ExportScreenshotWindow;
import com.moulberry.flashback.editor.ui.windows.PreferencesWindow;
import com.moulberry.flashback.editor.ui.windows.StartExportWindow;
import com.moulberry.flashback.editor.ui.windows.WindowType;
import com.moulberry.flashback.exporting.ExportJobQueue;
import com.moulberry.flashback.screen.select_replay.SelectReplayScreen;
import imgui.moulberry90.ImGui;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class MainMenuBar {
    public static void render() {
        if (ImGui.beginMainMenuBar()) {
            MainMenuBar.renderInner();
            ImGui.endMainMenuBar();
        }
    }

    public static void renderInner() {
        FlashbackConfigV1 config = Flashback.getConfig();
        if (ImGui.beginMenu(I18n.get((String)"flashback.menu.file", (Object[])new Object[0]) + "##File")) {
            String name;
            if (ImGui.menuItem(I18n.get((String)"flashback.menu.file.export_video", (Object[])new Object[0]) + "##ExportVideo")) {
                StartExportWindow.open();
            }
            if (!ExportJobQueue.queuedJobs.isEmpty() && ImGui.menuItem((name = I18n.get((String)"flashback.menu.file.export_queue", (Object[])new Object[]{ExportJobQueue.count()})) + "###QueuedJobs")) {
                ExportQueueWindow.open();
            }
            if (ImGui.menuItem(I18n.get((String)"flashback.export_screenshot", (Object[])new Object[0]) + "##ExportScreenshot")) {
                ExportScreenshotWindow.open();
            }
            ImGui.separator();
            if (ImGui.menuItem(I18n.get((String)"flashback.select_replay.open", (Object[])new Object[0]) + "##Open")) {
                Flashback.openReplayFromFileBrowser();
            }
            if (!config.internal.recentReplays.isEmpty() && ImGui.beginMenu(I18n.get((String)"flashback.open_recent_replay", (Object[])new Object[0]) + "##OpenRecentReplay")) {
                Path replayFolder = Flashback.getReplayFolder();
                for (String recentReplay : config.internal.recentReplays) {
                    Path path = Path.of(recentReplay, new String[0]);
                    if (!Files.exists(path, new LinkOption[0])) continue;
                    String display = path.toString();
                    try {
                        Path relative = replayFolder.relativize(path);
                        String relativeStr = relative.toString();
                        if (!relativeStr.contains("..")) {
                            display = relativeStr;
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if (!ImGui.menuItem(display)) continue;
                    Flashback.openReplayWorld(path);
                    break;
                }
                ImGui.endMenu();
            }
            if (ImGui.menuItem(I18n.get((String)"flashback.exit_replay", (Object[])new Object[0]) + "##ExitReplay")) {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.level != null) {
                    minecraft.level.disconnect((Component)Component.empty());
                }
                minecraft.disconnectWithProgressScreen();
                minecraft.setScreen((Screen)new SelectReplayScreen((Screen)new TitleScreen()));
            }
            ImGui.endMenu();
        }
        if (ImGui.menuItem(I18n.get((String)"flashback.preferences", (Object[])new Object[0]) + "##Preferences")) {
            PreferencesWindow.open();
        }
        ImGui.separator();
        if (ImGui.menuItem(I18n.get((String)"flashback.player_list", (Object[])new Object[0]) + "##PlayerList")) {
            WindowType.PLAYER_LIST.toggle();
        }
        if (ImGui.menuItem(I18n.get((String)"flashback.movement", (Object[])new Object[0]) + "##Movement")) {
            WindowType.MOVEMENT.toggle();
        }
        if (ImGui.menuItem(I18n.get((String)"flashback.render_filter", (Object[])new Object[0]) + "##RenderFilter")) {
            WindowType.RENDER_FILTER.toggle();
        }
        if (ImGui.menuItem(I18n.get((String)"flashback.keybinds", (Object[])new Object[0]) + "##Keybinds")) {
            WindowType.KEYBINDS.toggle();
        }
        ImGui.separator();
        if (ImGui.menuItem(I18n.get((String)"flashback.hide_replay_ui", (Object[])new Object[0]) + "##HideReplayUI")) {
            Minecraft.getInstance().options.hideGui = true;
        }
    }
}

