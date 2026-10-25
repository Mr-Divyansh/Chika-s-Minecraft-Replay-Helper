/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.EntityType
 */
package com.moulberry.flashback.editor.ui.windows;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.ImGuiListClipper;
import imgui.moulberry90.ImGuiViewport;
import imgui.moulberry90.callback.ImListClipperCallback;
import imgui.moulberry90.type.ImBoolean;
import imgui.moulberry90.type.ImString;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;

public class RenderFilterWindow {
    private static boolean wasDocked = false;
    private static ImString entitySearch = ImGuiHelper.createResizableImString("");
    private static String lastEntitySearch = null;
    private static List<EntityType<?>> searchedEntityTypes = new ArrayList();
    private static ImString particleSearch = ImGuiHelper.createResizableImString("");
    private static String lastParticleSearch = null;
    private static List<Identifier> searchedParticleTypes = new ArrayList<Identifier>();

    public static void render(ImBoolean open, boolean newlyOpened) {
        String title;
        ImGuiViewport viewport = ImGui.getMainViewport();
        if (newlyOpened) {
            ImGui.setNextWindowPos(viewport.getCenterX(), viewport.getCenterY(), 8, 0.5f, 0.5f);
        }
        ImGui.setNextWindowSizeConstraints(250.0f, 50.0f, 5000.0f, viewport.getSizeY() / 2.0f);
        int flags = 4096;
        if (!wasDocked) {
            flags |= 0x40;
        }
        if (ImGui.begin((title = I18n.get((String)"flashback.render_filter", (Object[])new Object[0])) + "###Render Filter", open, flags)) {
            wasDocked = ImGui.isWindowDocked();
            FlashbackConfigV1 config = Flashback.getConfig();
            if (!config.internal.signedRenderFilter) {
                String name = Minecraft.getInstance().getGameProfile().name();
                ImGui.pushTextWrapPos(ReplayUI.scaleUi(300));
                ImGui.textWrapped(I18n.get((String)"flashback.render_filter_affirmation", (Object[])new Object[]{name}));
                if (ImGui.checkbox(I18n.get((String)"flashback.render_filter_sign", (Object[])new Object[]{name}), false)) {
                    config.internal.signedRenderFilter = true;
                    config.delayedSaveToDefaultFolder();
                }
                ImGui.popTextWrapPos();
                ImGui.end();
                return;
            }
            final EditorState editorState = EditorStateManager.getCurrent();
            if (editorState == null) {
                ImGui.end();
                return;
            }
            if (ImGui.beginTabBar("##Select")) {
                Identifier resourceLocation;
                Object contains;
                String searchString;
                if (ImGui.beginTabItem(I18n.get((String)"flashback.entities", (Object[])new Object[0]))) {
                    ImGui.inputText(I18n.get((String)"flashback.search", (Object[])new Object[0]), entitySearch);
                    searchString = ImGuiHelper.getString(entitySearch).trim().toLowerCase(Locale.ROOT);
                    if (!searchString.equals(lastEntitySearch)) {
                        lastEntitySearch = searchString;
                        searchedEntityTypes = new ArrayList();
                        contains = new ArrayList();
                        for (EntityType entityType : BuiltInRegistries.ENTITY_TYPE) {
                            String name = I18n.get((String)entityType.getDescriptionId(), (Object[])new Object[0]).trim().toLowerCase(Locale.ROOT);
                            if (name.startsWith(lastEntitySearch)) {
                                searchedEntityTypes.add(entityType);
                                continue;
                            }
                            if (!name.contains(lastEntitySearch)) continue;
                            contains.add(entityType);
                        }
                        searchedEntityTypes.addAll((Collection<EntityType<?>>)contains);
                    }
                    if (searchedEntityTypes.isEmpty()) {
                        ImGui.textUnformatted(I18n.get((String)"flashback.no_entities_found", (Object[])new Object[0]));
                    } else {
                        if (ImGui.smallButton(I18n.get((String)"flashback.enable_all", (Object[])new Object[0]))) {
                            editorState.filteredEntities.clear();
                        }
                        ImGui.sameLine();
                        if (ImGui.smallButton(I18n.get((String)"flashback.disable_all", (Object[])new Object[0]))) {
                            for (Iterator entityType : BuiltInRegistries.ENTITY_TYPE) {
                                resourceLocation = BuiltInRegistries.ENTITY_TYPE.getKey((Object)entityType);
                                editorState.filteredEntities.add(resourceLocation.toString());
                            }
                        }
                        if (ImGui.beginChild("##Scroller", 0, 300)) {
                            ImGuiListClipper.forEach(searchedEntityTypes.size(), new ImListClipperCallback(){

                                @Override
                                public void accept(int i) {
                                    EntityType<?> entityType = searchedEntityTypes.get(i);
                                    Identifier resourceLocation = BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
                                    boolean filtered = editorState.filteredEntities.contains(resourceLocation.toString());
                                    String name = I18n.get((String)entityType.getDescriptionId(), (Object[])new Object[0]);
                                    if (ImGui.checkbox(name + "###" + String.valueOf(resourceLocation), !filtered)) {
                                        if (filtered) {
                                            editorState.filteredEntities.remove(resourceLocation.toString());
                                        } else {
                                            editorState.filteredEntities.add(resourceLocation.toString());
                                        }
                                    }
                                }
                            });
                        }
                        ImGui.endChild();
                    }
                    ImGui.endTabItem();
                }
                if (ImGui.beginTabItem(I18n.get((String)"flashback.particles", (Object[])new Object[0]))) {
                    ImGui.inputText(I18n.get((String)"flashback.search", (Object[])new Object[0]), particleSearch);
                    searchString = ImGuiHelper.getString(particleSearch).trim().toLowerCase(Locale.ROOT);
                    if (!searchString.equals(lastParticleSearch)) {
                        lastParticleSearch = searchString;
                        searchedParticleTypes = new ArrayList<Identifier>();
                        contains = new ArrayList();
                        for (ParticleType particleType : BuiltInRegistries.PARTICLE_TYPE) {
                            Identifier resourceLocation2 = BuiltInRegistries.PARTICLE_TYPE.getKey((Object)particleType);
                            if (resourceLocation2 == null) continue;
                            String name = resourceLocation2.getNamespace().equals("minecraft") ? resourceLocation2.getPath().toLowerCase(Locale.ROOT) : resourceLocation2.toString().toLowerCase(Locale.ROOT);
                            if (name.startsWith(lastParticleSearch)) {
                                searchedParticleTypes.add(resourceLocation2);
                                continue;
                            }
                            if (!name.contains(lastParticleSearch)) continue;
                            contains.add(resourceLocation2);
                        }
                        searchedParticleTypes.addAll((Collection<Identifier>)contains);
                    }
                    if (searchedParticleTypes.isEmpty()) {
                        ImGui.textUnformatted(I18n.get((String)"flashback.no_particles_found", (Object[])new Object[0]));
                    } else {
                        if (ImGui.smallButton(I18n.get((String)"flashback.enable_all", (Object[])new Object[0]))) {
                            editorState.filteredParticles.clear();
                        }
                        ImGui.sameLine();
                        if (ImGui.smallButton(I18n.get((String)"flashback.disable_all", (Object[])new Object[0]))) {
                            for (ParticleType particleType : BuiltInRegistries.PARTICLE_TYPE) {
                                resourceLocation = BuiltInRegistries.PARTICLE_TYPE.getKey((Object)particleType);
                                if (resourceLocation == null) continue;
                                editorState.filteredParticles.add(resourceLocation.toString());
                            }
                        }
                        if (ImGui.beginChild("##Scroller", 0, 300)) {
                            ImGuiListClipper.forEach(searchedParticleTypes.size(), new ImListClipperCallback(){

                                @Override
                                public void accept(int i) {
                                    Identifier particleType = searchedParticleTypes.get(i);
                                    boolean filtered = editorState.filteredParticles.contains(particleType.toString());
                                    String name = particleType.getNamespace().equals("minecraft") ? particleType.getPath().toLowerCase(Locale.ROOT) : particleType.toString().toLowerCase(Locale.ROOT);
                                    if (ImGui.checkbox(name, !filtered)) {
                                        if (filtered) {
                                            editorState.filteredParticles.remove(particleType.toString());
                                        } else {
                                            editorState.filteredParticles.add(particleType.toString());
                                        }
                                    }
                                }
                            });
                        }
                        ImGui.endChild();
                    }
                    ImGui.pushTextWrapPos(ReplayUI.scaleUi(300));
                    ImGui.textWrapped(I18n.get((String)"flashback.particles_note", (Object[])new Object[0]));
                    ImGui.popTextWrapPos();
                    ImGui.endTabItem();
                }
                ImGui.endTabBar();
            }
        }
        ImGui.end();
    }
}

