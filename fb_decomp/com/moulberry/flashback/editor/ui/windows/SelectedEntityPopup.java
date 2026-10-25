/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.yggdrasil.ProfileResult
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.world.entity.Avatar
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.player.PlayerModelPart
 *  net.minecraft.world.level.GameType
 *  net.minecraft.world.scores.DisplaySlot
 *  net.minecraft.world.scores.Objective
 *  net.minecraft.world.scores.PlayerTeam
 *  net.minecraft.world.scores.Scoreboard
 */
package com.moulberry.flashback.editor.ui.windows;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.moulberry.flashback.FilePlayerSkin;
import com.moulberry.flashback.Utils;
import com.moulberry.flashback.combo_options.GlowingOverride;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.exporting.AsyncFileDialogs;
import com.moulberry.flashback.state.EditorState;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.type.ImString;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.level.GameType;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

public class SelectedEntityPopup {
    private static ImString changeSkinInput = ImGuiHelper.createResizableImString("");
    private static ImString changeNameInput;

    public static void open(Entity entity, EditorState editorState) {
        String nameOverride = editorState.nameOverride.get(entity.getUUID());
        if (nameOverride != null) {
            changeNameInput.set(nameOverride);
        } else {
            changeNameInput.set("");
        }
        GameProfile skinOverride = editorState.skinOverride.get(entity.getUUID());
        if (skinOverride != null) {
            changeSkinInput.set(skinOverride.id().toString());
        } else {
            changeSkinInput.set("");
        }
    }

    public static void render(Entity entity, EditorState editorState) {
        boolean isHiddenDuringExport;
        Player player;
        UUID uuid = entity.getUUID();
        ImGui.textUnformatted(I18n.get((String)"flashback.entity_label", (Object[])new Object[]{uuid}));
        ImGui.separator();
        if (ImGui.button(I18n.get((String)"flashback.look_at", (Object[])new Object[0]))) {
            Minecraft.getInstance().getCameraEntity().lookAt(EntityAnchorArgument.Anchor.EYES, entity.getEyePosition());
        }
        ImGui.sameLine();
        if (ImGui.button(I18n.get((String)"flashback.spectate", (Object[])new Object[0]))) {
            Minecraft.getInstance().player.connection.sendCommand("spectate " + String.valueOf(entity.getUUID()));
            ImGui.closeCurrentPopup();
        }
        ImGui.sameLine();
        if (ImGui.button(I18n.get((String)"flashback.copy_uuid", (Object[])new Object[0]))) {
            Minecraft.getInstance().keyboardHandler.setClipboard(entity.getUUID().toString());
            ReplayUI.setInfoOverlay("Copied '" + String.valueOf(entity.getUUID()) + "'");
            ImGui.closeCurrentPopup();
        }
        if (uuid.equals(editorState.audioSourceEntity)) {
            if (ImGui.button(I18n.get((String)"flashback.unset_audio_source", (Object[])new Object[0]))) {
                editorState.audioSourceEntity = null;
                editorState.markDirty();
            }
        } else if (ImGui.button(I18n.get((String)"flashback.set_audio_source", (Object[])new Object[0]))) {
            editorState.audioSourceEntity = entity.getUUID();
            editorState.markDirty();
        }
        if (FabricLoader.getInstance().isModLoaded("voicechat")) {
            boolean isMuted = editorState.muteVoice.contains(entity.getUUID());
            if (ImGui.checkbox(I18n.get((String)"flashback.mute_voice", (Object[])new Object[0]), isMuted)) {
                if (isMuted) {
                    editorState.muteVoice.remove(entity.getUUID());
                } else {
                    editorState.muteVoice.add(entity.getUUID());
                }
                editorState.markDirty();
            }
        }
        if (editorState.hideAllSpectators && entity instanceof Player && (player = (Player)entity).gameMode() == GameType.SPECTATOR) {
            isHiddenDuringExport = true;
            ImGui.beginDisabled();
            ImGui.checkbox(I18n.get((String)"flashback.hide_during_export", (Object[])new Object[0]), true);
            ImGui.endDisabled();
            ImGui.setItemTooltip(I18n.get((String)"flashback.hidden_because_spectator", (Object[])new Object[0]));
        } else {
            isHiddenDuringExport = editorState.hideDuringExport.contains(entity.getUUID());
            if (ImGui.checkbox(I18n.get((String)"flashback.hide_during_export", (Object[])new Object[0]), isHiddenDuringExport)) {
                if (isHiddenDuringExport) {
                    editorState.hideDuringExport.remove(entity.getUUID());
                } else {
                    editorState.hideDuringExport.add(entity.getUUID());
                }
                editorState.markDirty();
            }
        }
        if (!isHiddenDuringExport) {
            if (entity instanceof AbstractClientPlayer) {
                player = (AbstractClientPlayer)entity;
                if (editorState.hideCape.contains(player.getUUID())) {
                    if (ImGui.checkbox(I18n.get((String)"flashback.hide_cape", (Object[])new Object[0]), true)) {
                        editorState.hideCape.remove(player.getUUID());
                    }
                } else if (player.isModelPartShown(PlayerModelPart.CAPE) && player.getSkin().cape() != null && ImGui.checkbox(I18n.get((String)"flashback.hide_cape", (Object[])new Object[0]), false)) {
                    editorState.hideCape.add(player.getUUID());
                }
                boolean hideNametag = editorState.hideNametags.contains(entity.getUUID());
                if (ImGui.checkbox(I18n.get((String)"flashback.render_nametag", (Object[])new Object[0]), !hideNametag)) {
                    if (hideNametag) {
                        editorState.hideNametags.remove(entity.getUUID());
                    } else {
                        editorState.hideNametags.add(entity.getUUID());
                    }
                }
                if (!hideNametag) {
                    PlayerTeam team;
                    String nameTitle = I18n.get((String)"flashback.name", (Object[])new Object[0]);
                    boolean changedName = ImGui.inputTextWithHint(nameTitle + "##SetNameInput", player.getScoreboardName(), changeNameInput);
                    if (changedName) {
                        String string = ImGuiHelper.getString(changeNameInput);
                        if (string.isEmpty()) {
                            editorState.nameOverride.remove(entity.getUUID());
                        } else {
                            editorState.nameOverride.put(entity.getUUID(), string);
                        }
                    }
                    if (editorState.hideTeamPrefix.contains(player.getUUID())) {
                        if (ImGui.checkbox(I18n.get((String)"flashback.hide_team_prefix", (Object[])new Object[0]), true)) {
                            editorState.hideTeamPrefix.remove(player.getUUID());
                        }
                    } else {
                        team = player.getTeam();
                        if (team != null && !Utils.isComponentEmpty(team.getPlayerPrefix()) && ImGui.checkbox(I18n.get((String)"flashback.hide_team_prefix", (Object[])new Object[0]), false)) {
                            editorState.hideTeamPrefix.add(player.getUUID());
                        }
                    }
                    if (editorState.hideTeamSuffix.contains(player.getUUID())) {
                        if (ImGui.checkbox(I18n.get((String)"flashback.hide_team_suffix", (Object[])new Object[0]), true)) {
                            editorState.hideTeamSuffix.remove(player.getUUID());
                        }
                    } else {
                        team = player.getTeam();
                        if (team != null && !Utils.isComponentEmpty(team.getPlayerSuffix()) && ImGui.checkbox(I18n.get((String)"flashback.hide_team_suffix", (Object[])new Object[0]), false)) {
                            editorState.hideTeamSuffix.add(player.getUUID());
                        }
                    }
                    if (editorState.hideBelowName.contains(player.getUUID())) {
                        if (ImGui.checkbox(I18n.get((String)"flashback.hide_text_below_name", (Object[])new Object[0]), true)) {
                            editorState.hideBelowName.remove(player.getUUID());
                        }
                    } else {
                        Scoreboard scoreboard = player.level().getScoreboard();
                        Objective objective = scoreboard.getDisplayObjective(DisplaySlot.BELOW_NAME);
                        if (objective != null && ImGui.checkbox(I18n.get((String)"flashback.hide_text_below_name", (Object[])new Object[0]), false)) {
                            editorState.hideBelowName.add(player.getUUID());
                        }
                    }
                }
                SelectedEntityPopup.showGlowingDropdown(entity, editorState);
                ImGuiHelper.separatorWithText(I18n.get((String)"flashback.change_skin_and_cape", (Object[])new Object[0]));
                ImGui.setNextItemWidth(320.0f);
                ImGui.inputTextWithHint("##SetSkinInput", "e.g. d0e05de7-6067-454d-beae-c6d19d886191", changeSkinInput);
                if (!changeSkinInput.isEmpty()) {
                    String string = ImGuiHelper.getString(changeSkinInput);
                    try {
                        ProfileResult profile;
                        UUID changeSkinUuid = UUID.fromString(string);
                        if (ImGui.button(I18n.get((String)"flashback.apply_skin_from_uuid", (Object[])new Object[0])) && (profile = Minecraft.getInstance().services().sessionService().fetchProfile(changeSkinUuid, true)) != null) {
                            editorState.skinOverride.put(entity.getUUID(), profile.profile());
                            editorState.skinOverrideFromFile.remove(entity.getUUID());
                        }
                    }
                    catch (Exception changeSkinUuid) {
                        // empty catch block
                    }
                }
                if (ImGui.button(I18n.get((String)"flashback.upload_skin_from_file", (Object[])new Object[0]))) {
                    Path gameDir = FabricLoader.getInstance().getGameDir();
                    CompletableFuture<String> future = AsyncFileDialogs.openFileDialog(gameDir.toString(), "Skin Texture", "png");
                    future.thenAccept(pathStr -> {
                        if (pathStr != null) {
                            editorState.skinOverride.remove(entity.getUUID());
                            editorState.skinOverrideFromFile.put(entity.getUUID(), new FilePlayerSkin((String)pathStr));
                        }
                    });
                }
                if ((editorState.skinOverride.containsKey(entity.getUUID()) || editorState.skinOverrideFromFile.containsKey(entity.getUUID())) && ImGui.button(I18n.get((String)"flashback.reset_skin", (Object[])new Object[0]))) {
                    editorState.skinOverride.remove(entity.getUUID());
                    editorState.skinOverrideFromFile.remove(entity.getUUID());
                    changeSkinInput.set("");
                }
            } else {
                SelectedEntityPopup.showGlowingDropdown(entity, editorState);
            }
            if (entity instanceof LivingEntity && ImGui.collapsingHeader("Equipment")) {
                EnumSet<EquipmentSlot> hiddenEquipment = editorState.hiddenEquipment.get(entity.getUUID());
                hiddenEquipment = hiddenEquipment == null ? EnumSet.noneOf(EquipmentSlot.class) : hiddenEquipment.clone();
                boolean changed = false;
                for (EquipmentSlot value : EquipmentSlot.values()) {
                    if (entity instanceof Player && (value == EquipmentSlot.BODY || value == EquipmentSlot.SADDLE)) continue;
                    boolean hidden = hiddenEquipment.contains(value);
                    if (!ImGui.checkbox(value.getName(), !hidden)) continue;
                    if (hidden) {
                        hiddenEquipment.remove(value);
                    } else {
                        hiddenEquipment.add(value);
                    }
                    changed = true;
                }
                if (changed) {
                    if (hiddenEquipment.isEmpty()) {
                        editorState.hiddenEquipment.remove(entity.getUUID());
                    } else {
                        editorState.hiddenEquipment.put(entity.getUUID(), hiddenEquipment);
                    }
                }
            }
            if (entity instanceof Avatar) {
                Avatar avatar = (Avatar)entity;
                if (ImGui.collapsingHeader("Model Parts")) {
                    boolean overriding;
                    EnumSet<Object> hiddenModelParts = editorState.hiddenModelParts.get(entity.getUUID());
                    boolean changed = false;
                    EnumSet<PlayerModelPart> actualHiddenModelParts = EnumSet.noneOf(PlayerModelPart.class);
                    for (PlayerModelPart value : PlayerModelPart.values()) {
                        if (avatar.isModelPartShown(value)) continue;
                        actualHiddenModelParts.add(value);
                    }
                    boolean bl = overriding = hiddenModelParts != null;
                    if (ImGui.checkbox("Override", overriding)) {
                        hiddenModelParts = overriding ? null : actualHiddenModelParts;
                        changed = true;
                    }
                    if (hiddenModelParts != null) {
                        for (PlayerModelPart value : PlayerModelPart.values()) {
                            boolean hidden = hiddenModelParts.contains(value);
                            if (!ImGui.checkbox(value.getName().getString(), !hidden)) continue;
                            if (hidden) {
                                hiddenModelParts.remove(value);
                            } else {
                                hiddenModelParts.add(value);
                            }
                            changed = true;
                        }
                    } else {
                        ImGui.beginDisabled();
                        for (PlayerModelPart value : PlayerModelPart.values()) {
                            ImGui.checkbox(value.getName().getString(), !actualHiddenModelParts.contains(value));
                        }
                        ImGui.endDisabled();
                    }
                    if (changed) {
                        if (hiddenModelParts == null) {
                            editorState.hiddenModelParts.remove(entity.getUUID());
                        } else {
                            editorState.hiddenModelParts.put(entity.getUUID(), hiddenModelParts);
                        }
                    }
                }
            }
        }
    }

    private static void showGlowingDropdown(Entity entity, EditorState editorState) {
        GlowingOverride newGlowingOverride;
        GlowingOverride glowingOverride = GlowingOverride.DEFAULT;
        if (editorState.glowingOverride.containsKey(entity.getUUID())) {
            glowingOverride = editorState.glowingOverride.get(entity.getUUID());
        }
        if ((newGlowingOverride = ImGuiHelper.enumCombo(I18n.get((String)"flashback.glowing", (Object[])new Object[0]), glowingOverride)) != glowingOverride) {
            if (newGlowingOverride == GlowingOverride.DEFAULT) {
                editorState.glowingOverride.remove(entity.getUUID());
            } else {
                editorState.glowingOverride.put(entity.getUUID(), newGlowingOverride);
            }
        }
    }

    static {
        SelectedEntityPopup.changeSkinInput.inputData.allowedChars = "0123456789abcdef-";
        changeNameInput = ImGuiHelper.createResizableImString("");
    }
}

