/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.CycleButton
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.CommonComponents
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.screen.select_replay;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.RegistryMetaHelper;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.screen.ReplaySummary;
import com.moulberry.flashback.screen.select_replay.ReplaySelectionEntry;
import com.moulberry.flashback.screen.select_replay.ReplaySelectionList;
import com.moulberry.flashback.screen.select_replay.ReplaySorting;
import com.moulberry.flashback.screen.select_replay.SortDirectionButton;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class SelectReplayScreen
extends Screen {
    protected final Screen lastScreen;
    public final Path path;
    private Button selectButton;
    private Button editButton;
    private Button deleteButton;
    protected EditBox searchBox;
    private ReplaySelectionList list;
    private LinkedHashMap<String, LinkedHashSet<String>> currentNamespacesForRegistries;

    public SelectReplayScreen(Screen screen) {
        this(screen, Flashback.getReplayFolder());
    }

    public SelectReplayScreen(Screen screen, Path path) {
        super((Component)Component.translatable((String)"flashback.select_replay"));
        this.lastScreen = screen;
        this.path = path;
        this.currentNamespacesForRegistries = RegistryMetaHelper.calculateNamespacesForRegistries();
    }

    protected void init() {
        this.searchBox = new EditBox(this.font, this.width / 2 - 128, 22, 126, 20, this.searchBox, (Component)Component.translatable((String)"selectWorld.search"));
        this.searchBox.setResponder(string -> {
            FlashbackConfigV1 config = Flashback.getConfig();
            this.list.updateFilter((String)string, config.internal.replaySorting, config.internal.sortDescending);
        });
        this.addWidget((GuiEventListener)this.searchBox);
        FlashbackConfigV1 config = Flashback.getConfig();
        this.addRenderableWidget((GuiEventListener)CycleButton.builder(ReplaySorting::component, (Object)((Object)config.internal.replaySorting)).withValues((Object[])ReplaySorting.values()).create(this.width / 2 + 2, 22, 125, 20, (Component)Component.translatable((String)"flashback.sort"), (button, sorting) -> {
            FlashbackConfigV1 configuration = Flashback.getConfig();
            configuration.internal.replaySorting = sorting;
            configuration.delayedSaveToDefaultFolder();
            this.list.updateFilter(this.searchBox.getValue(), configuration.internal.replaySorting, configuration.internal.sortDescending);
        }));
        this.addRenderableWidget((GuiEventListener)new SortDirectionButton(this.width / 2 + 131, 22, 20, 20, (Component)Component.translatable((String)"flashback.sort_direction"), sortDescending -> {
            FlashbackConfigV1 configuration = Flashback.getConfig();
            configuration.internal.sortDescending = sortDescending;
            configuration.delayedSaveToDefaultFolder();
            this.list.updateFilter(this.searchBox.getValue(), configuration.internal.replaySorting, configuration.internal.sortDescending);
        }, config.internal.sortDescending));
        this.list = (ReplaySelectionList)this.addRenderableWidget((GuiEventListener)new ReplaySelectionList(this, this.minecraft, this.width, this.height - 112, 48, 36, this.searchBox.getValue(), config.internal.replaySorting, config.internal.sortDescending, this.currentNamespacesForRegistries, this.list));
        this.selectButton = (Button)this.addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable((String)"flashback.select_replay.open"), this::tryOpenReplay).bounds(this.width / 2 - 151, this.height - 52, 150, 20).build());
        this.addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable((String)"flashback.flashback_options"), btn -> Flashback.openConfigScreen(this)).bounds(this.width / 2 + 1, this.height - 52, 150, 20).build());
        this.editButton = (Button)this.addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable((String)"selectWorld.edit"), this::tryEditReplay).bounds(this.width / 2 - 151, this.height - 28, 98, 20).build());
        this.deleteButton = (Button)this.addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable((String)"selectWorld.delete"), this::tryDeleteReplay).bounds(this.width / 2 - 49, this.height - 28, 98, 20).build());
        this.addRenderableWidget((GuiEventListener)Button.builder((Component)CommonComponents.GUI_BACK, button -> this.minecraft.setScreen(this.lastScreen)).bounds(this.width / 2 + 53, this.height - 28, 98, 20).build());
        this.updateButtonStatus(null);
    }

    protected void setInitialFocus() {
        this.setInitialFocus((GuiEventListener)this.searchBox);
    }

    public void onClose() {
        this.minecraft.setScreen(this.lastScreen);
    }

    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        this.searchBox.extractRenderState(graphics, mouseX, mouseY, a);
        graphics.centeredText(this.font, this.title, this.width / 2, 8, -1);
    }

    public void updateButtonStatus(@Nullable ReplaySummary replaySummary) {
        if (this.selectButton == null) {
            return;
        }
        if (replaySummary == null) {
            this.selectButton.active = false;
            this.editButton.active = false;
            this.deleteButton.active = false;
        } else {
            this.selectButton.active = replaySummary.canOpen();
            this.editButton.active = true;
            this.deleteButton.active = true;
        }
    }

    public void removed() {
        if (this.list != null) {
            this.list.clearEntries();
        }
    }

    private void tryOpenReplay(Button button) {
        ReplaySelectionEntry.ReplayListEntry entry = this.list.getReplayListEntry();
        if (entry != null) {
            entry.openReplay();
        }
    }

    private void tryEditReplay(Button button) {
        ReplaySelectionEntry.ReplayListEntry entry = this.list.getReplayListEntry();
        if (entry != null) {
            entry.editReplay();
        }
    }

    private void tryDeleteReplay(Button button) {
        ReplaySelectionEntry.ReplayListEntry entry = this.list.getReplayListEntry();
        if (entry != null) {
            entry.deleteReplay();
        }
    }
}

