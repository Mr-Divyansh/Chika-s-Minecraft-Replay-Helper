/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.StringWidget
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.layouts.FrameLayout
 *  net.minecraft.client.gui.layouts.GridLayout
 *  net.minecraft.client.gui.layouts.GridLayout$RowHelper
 *  net.minecraft.client.gui.layouts.LayoutElement
 *  net.minecraft.client.gui.screens.AlertScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.CommonComponents
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.screen;

import com.google.gson.JsonElement;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.FlashbackGson;
import com.moulberry.flashback.FlashbackTextComponents;
import com.moulberry.flashback.record.FlashbackMeta;
import com.moulberry.flashback.screen.BottomTextWidget;
import com.moulberry.flashback.screen.CombineReplayScreen;
import com.moulberry.flashback.screen.ReplaySummary;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class EditReplayScreen
extends Screen {
    @Nullable
    private final Screen lastScreen;
    private final FlashbackMeta metadata;
    private String changedReplayName;
    private Button saveChangesButton;
    private final Path replayPath;

    public EditReplayScreen(@Nullable Screen lastScreen, ReplaySummary summary) {
        super(FlashbackTextComponents.EDIT_REPLAY);
        this.lastScreen = lastScreen;
        this.metadata = summary.getReplayMetadata();
        this.changedReplayName = summary.getReplayName();
        this.replayPath = summary.getPath();
    }

    protected void setInitialFocus() {
    }

    protected void init() {
        super.init();
        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().padding(4, 4, 4, 0);
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(2);
        rowHelper.addChild((LayoutElement)new StringWidget(204, 20, FlashbackTextComponents.EDIT_REPLAY, this.font), 2);
        rowHelper.addChild((LayoutElement)new BottomTextWidget(204, 10, FlashbackTextComponents.REPLAY_NAME, this.font).alignLeft(), 2);
        EditBox replayNameEditBox = new EditBox(this.font, 0, 0, 204, 20, (Component)Component.literal((String)this.changedReplayName));
        replayNameEditBox.setMaxLength(128);
        replayNameEditBox.setValue(this.changedReplayName);
        replayNameEditBox.setResponder(this::setReplayName);
        rowHelper.addChild((LayoutElement)replayNameEditBox, 2);
        rowHelper.addChild((LayoutElement)new BottomTextWidget(204, 10, CommonComponents.EMPTY, this.font), 2);
        rowHelper.addChild((LayoutElement)Button.builder((Component)FlashbackTextComponents.COMBINE_WITH_OTHER_REPLAY, button -> this.minecraft.setScreen((Screen)new CombineReplayScreen(this, this.replayPath, null, null))).width(204).build(), 2);
        this.saveChangesButton = (Button)rowHelper.addChild((LayoutElement)Button.builder((Component)FlashbackTextComponents.SAVE_CHANGES, button -> this.applyChanges()).width(98).build());
        this.saveChangesButton.active = false;
        rowHelper.addChild((LayoutElement)Button.builder((Component)CommonComponents.GUI_BACK, button -> this.minecraft.setScreen(this.lastScreen)).width(98).build());
        gridLayout.arrangeElements();
        FrameLayout.alignInRectangle((LayoutElement)gridLayout, (int)0, (int)0, (int)this.width, (int)this.height, (float)0.5f, (float)0.5f);
        EditReplayScreen editReplayScreen = this;
        gridLayout.visitWidgets(x$0 -> editReplayScreen.addRenderableWidget((GuiEventListener)x$0));
        this.updateSaveChangesActive();
        this.setInitialFocus((GuiEventListener)replayNameEditBox);
    }

    private void applyChanges() {
        this.metadata.name = this.changedReplayName;
        try (FileSystem fileSystem = FileSystems.newFileSystem(this.replayPath);){
            Path metadataPath = fileSystem.getPath("/metadata.json", new String[0]);
            String metadataJson = FlashbackGson.PRETTY.toJson((JsonElement)this.metadata.toJson());
            Files.writeString(metadataPath, (CharSequence)metadataJson, new OpenOption[0]);
        }
        catch (IOException e) {
            Flashback.LOGGER.error("Unable to edit replay", (Throwable)e);
            Minecraft.getInstance().setScreen((Screen)new AlertScreen(() -> Minecraft.getInstance().setScreen(this.lastScreen), FlashbackTextComponents.UNABLE_TO_EDIT_REPLAY, (Component)Component.literal((String)e.toString())));
        }
        this.updateSaveChangesActive();
        this.clearFocus();
    }

    private void setReplayName(String name) {
        this.changedReplayName = name;
        this.updateSaveChangesActive();
    }

    private void updateSaveChangesActive() {
        this.saveChangesButton.active = !Objects.equals(this.metadata.name, this.changedReplayName);
    }

    public void onClose() {
        this.minecraft.setScreen(this.lastScreen);
    }
}

