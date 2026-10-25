/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.GameType
 */
package com.moulberry.flashback.editor.ui.windows;

import com.mojang.authlib.GameProfile;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.ImGuiViewport;
import imgui.moulberry90.type.ImBoolean;
import imgui.moulberry90.type.ImString;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

public class PlayerListWindow {
    private static String lastSearch = null;
    private static long lastUpdate = 0L;
    private static boolean includeNpcs = false;
    private static boolean hasNpcs = false;
    private static final ImString search = ImGuiHelper.createResizableImString("");
    private static final List<PlayerInfo> searchedPlayers = new ArrayList<PlayerInfo>();

    private static void updatePlayerList() {
        searchedPlayers.clear();
        hasNpcs = false;
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection == null) {
            return;
        }
        ClientLevel level = Minecraft.getInstance().level;
        Entity camera = Minecraft.getInstance().getCameraEntity();
        record SearchEntry(double distanceSq, PlayerInfo info) {
        }
        ArrayList<SearchEntry> searchEntries = new ArrayList<SearchEntry>();
        ArrayList<SearchEntry> searchEntriesLowPriority = new ArrayList<SearchEntry>();
        String searchLower = lastSearch.toLowerCase(Locale.ROOT);
        boolean blankSearch = lastSearch.isBlank();
        for (PlayerInfo playerInfo : connection.getOnlinePlayers()) {
            Player player;
            GameProfile profile = playerInfo.getProfile();
            if (profile.id().equals(Minecraft.getInstance().player.getUUID())) continue;
            if (profile.id().version() != 4) {
                hasNpcs = true;
                if (!includeNpcs) continue;
            }
            double distanceSq = Double.MAX_VALUE;
            if (level != null && camera != null && (player = level.getPlayerByUUID(profile.id())) != null) {
                distanceSq = player.distanceToSqr(camera);
            }
            SearchEntry searchEntry = new SearchEntry(distanceSq, playerInfo);
            if (blankSearch) {
                searchEntries.add(searchEntry);
                continue;
            }
            String nameLower = profile.name().toLowerCase(Locale.ROOT);
            if (nameLower.startsWith(searchLower)) {
                searchEntries.add(searchEntry);
                continue;
            }
            if (!nameLower.contains(searchLower)) continue;
            searchEntriesLowPriority.add(searchEntry);
        }
        searchEntries.sort(Comparator.comparingDouble(SearchEntry::distanceSq));
        searchEntriesLowPriority.sort(Comparator.comparingDouble(SearchEntry::distanceSq));
        if (searchEntries.size() < 16) {
            searchEntries.addAll(searchEntriesLowPriority);
        }
        int count = Math.min(16, searchEntries.size());
        for (int i = 0; i < count; ++i) {
            searchedPlayers.add(((SearchEntry)searchEntries.get((int)i)).info);
        }
    }

    public static void render(ImBoolean open, boolean newlyOpened) {
        if (newlyOpened) {
            ImGuiViewport viewport = ImGui.getMainViewport();
            ImGui.setNextWindowPos(viewport.getCenterX(), viewport.getCenterY(), 8, 0.5f, 0.5f);
        }
        ImGui.setNextWindowSizeConstraints(250.0f, 50.0f, 5000.0f, 5000.0f);
        String title = I18n.get((String)"flashback.player_list", (Object[])new Object[0]);
        if (ImGui.begin(title + "###PlayerList", open, 4160)) {
            String searchStr = I18n.get((String)"flashback.search", (Object[])new Object[0]);
            ImGui.setNextItemWidth(ImGui.getContentRegionAvailX() - ImGuiHelper.calcTextWidth(searchStr) - 32.0f);
            if (ImGui.inputText(searchStr, search)) {
                lastSearch = null;
            }
            long currentTime = System.currentTimeMillis();
            if (lastSearch == null || currentTime > lastUpdate + 3000L || currentTime < lastUpdate) {
                lastSearch = ImGuiHelper.getString(search);
                lastUpdate = currentTime;
                PlayerListWindow.updatePlayerList();
            }
            if ((hasNpcs || includeNpcs) && ImGui.checkbox(I18n.get((String)"flashback.include_npcs", (Object[])new Object[0]), includeNpcs)) {
                includeNpcs = !includeNpcs;
                lastSearch = null;
            }
            EditorState editorState = EditorStateManager.getCurrent();
            ImGui.separator();
            for (int i = 0; i < searchedPlayers.size(); ++i) {
                ImGui.pushID(i);
                PlayerInfo playerInfo = searchedPlayers.get(i);
                GameProfile profile = playerInfo.getProfile();
                ImGui.textUnformatted(profile.name());
                ImGui.sameLine();
                if (ImGui.smallButton(I18n.get((String)"flashback.tp", (Object[])new Object[0]))) {
                    Minecraft.getInstance().getConnection().sendCommand("teleport " + String.valueOf(profile.id()));
                    lastUpdate = currentTime;
                }
                if (editorState != null) {
                    ImGui.sameLine();
                    if (editorState.hideAllSpectators && playerInfo.getGameMode() == GameType.SPECTATOR) {
                        ImGui.beginDisabled();
                        ImGui.smallButton(I18n.get((String)"flashback.show", (Object[])new Object[0]));
                        ImGui.endDisabled();
                        ImGui.setItemTooltip(I18n.get((String)"flashback.hidden_because_spectator", (Object[])new Object[0]));
                    } else if (editorState.hideDuringExport.contains(profile.id())) {
                        if (ImGui.smallButton(I18n.get((String)"flashback.show", (Object[])new Object[0]))) {
                            editorState.hideDuringExport.remove(profile.id());
                            lastUpdate = currentTime;
                        }
                    } else if (ImGui.smallButton(I18n.get((String)"flashback.hide", (Object[])new Object[0]))) {
                        editorState.hideDuringExport.add(profile.id());
                        lastUpdate = currentTime;
                    }
                }
                ImGui.sameLine();
                if (ImGui.smallButton(I18n.get((String)"flashback.spectate", (Object[])new Object[0]))) {
                    Minecraft.getInstance().getConnection().sendCommand("spectate " + String.valueOf(profile.id()));
                    lastUpdate = currentTime;
                }
                ImGui.popID();
            }
            ImGui.separator();
            if (editorState != null) {
                if (ImGui.button(I18n.get((String)"flashback.hide_all", (Object[])new Object[0]))) {
                    PlayerListWindow.changeVisibilityOfAll(editorState, false);
                    lastUpdate = currentTime;
                }
                ImGui.sameLine();
                if (ImGui.button(I18n.get((String)"flashback.show_all", (Object[])new Object[0]))) {
                    PlayerListWindow.changeVisibilityOfAll(editorState, true);
                    lastUpdate = currentTime;
                }
                if (ImGui.checkbox(I18n.get((String)"flashback.hide_all_spectators", (Object[])new Object[0]), editorState.hideAllSpectators)) {
                    editorState.hideAllSpectators = !editorState.hideAllSpectators;
                }
            }
        }
        ImGui.end();
    }

    private static void changeVisibilityOfAll(EditorState editorState, boolean visible) {
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection == null) {
            return;
        }
        String searchLower = lastSearch.toLowerCase(Locale.ROOT);
        boolean blankSearch = lastSearch.isBlank();
        for (PlayerInfo playerInfo : connection.getOnlinePlayers()) {
            GameProfile profile = playerInfo.getProfile();
            if (profile.id().equals(Minecraft.getInstance().player.getUUID()) || profile.id().version() != 4 && !includeNpcs) continue;
            if (blankSearch) {
                if (visible) {
                    editorState.hideDuringExport.remove(profile.id());
                    continue;
                }
                editorState.hideDuringExport.add(profile.id());
                continue;
            }
            String nameLower = profile.name().toLowerCase(Locale.ROOT);
            if (!nameLower.contains(searchLower)) continue;
            if (visible) {
                editorState.hideDuringExport.remove(profile.id());
                continue;
            }
            editorState.hideDuringExport.add(profile.id());
        }
    }
}

