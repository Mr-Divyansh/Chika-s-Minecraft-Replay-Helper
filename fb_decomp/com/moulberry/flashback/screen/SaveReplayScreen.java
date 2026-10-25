/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
 *  net.minecraft.client.gui.screens.ConfirmScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.CommonComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.FileUtil
 *  org.apache.commons.io.FileUtils
 */
package com.moulberry.flashback.screen;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.FlashbackTextComponents;
import com.moulberry.flashback.exporting.AsyncFileDialogs;
import com.moulberry.flashback.record.ReplayExporter;
import com.moulberry.flashback.screen.BottomTextWidget;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FileUtil;
import org.apache.commons.io.FileUtils;

public class SaveReplayScreen
extends Screen {
    private Path recordFolder;
    private String replayName;
    private Path savePath = null;
    private boolean generateFileFromReplayName = true;
    private Button filePathButton;
    private Screen previousScreen;

    public SaveReplayScreen(Screen previousScreen, Path recordFolder, String replayName) {
        super(FlashbackTextComponents.SAVE_REPLAY);
        this.recordFolder = recordFolder;
        this.setReplayName(replayName);
        if (this.savePath == null) {
            this.savePath = Flashback.getReplayFolder().resolve(String.valueOf(UUID.randomUUID()) + ".zip");
        }
        if (!(previousScreen instanceof SaveReplayScreen)) {
            this.previousScreen = previousScreen;
        }
    }

    protected void setInitialFocus() {
    }

    protected void init() {
        super.init();
        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().padding(4, 4, 4, 0);
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(2);
        rowHelper.addChild((LayoutElement)new StringWidget(204, 20, FlashbackTextComponents.SAVE_REPLAY, this.font), 2);
        rowHelper.addChild((LayoutElement)new BottomTextWidget(204, 10, FlashbackTextComponents.REPLAY_NAME, this.font).alignLeft(), 2);
        EditBox replayNameEditBox = new EditBox(this.font, 0, 0, 204, 20, (Component)Component.literal((String)this.replayName));
        replayNameEditBox.setMaxLength(128);
        replayNameEditBox.setValue(this.replayName);
        replayNameEditBox.setResponder(this::setReplayName);
        rowHelper.addChild((LayoutElement)replayNameEditBox, 2);
        rowHelper.addChild((LayoutElement)new BottomTextWidget(204, 10, FlashbackTextComponents.FILENAME, this.font).alignLeft(), 2);
        this.filePathButton = Button.builder((Component)Component.literal((String)this.savePath.getFileName().toString()), button -> {
            CompletableFuture<String> future = AsyncFileDialogs.saveFileDialog(this.savePath.getParent().toString(), this.savePath.getFileName().toString(), "Replay Archive", "zip");
            future.thenAccept(pathStr -> {
                if (pathStr != null) {
                    this.savePath = Path.of(pathStr, new String[0]);
                    this.generateFileFromReplayName = false;
                }
            });
        }).width(204).build();
        rowHelper.addChild((LayoutElement)this.filePathButton, 2);
        rowHelper.addChild((LayoutElement)new BottomTextWidget(204, 10, CommonComponents.EMPTY, this.font), 2);
        rowHelper.addChild((LayoutElement)Button.builder((Component)FlashbackTextComponents.SAVE_REPLAY, button -> {
            this.saveReplay();
            Minecraft.getInstance().setScreen(this.previousScreen);
        }).width(98).build(), 1);
        rowHelper.addChild((LayoutElement)Button.builder((Component)FlashbackTextComponents.DELETE_REPLAY, button -> Minecraft.getInstance().setScreen((Screen)new ConfirmScreen(value -> {
            if (value) {
                this.deleteReplay();
                Minecraft.getInstance().setScreen(this.previousScreen);
            } else {
                Minecraft.getInstance().setScreen((Screen)this);
            }
        }, FlashbackTextComponents.CONFIRM_DELETE_REPLAY, FlashbackTextComponents.CONFIRM_DELETE_REPLAY_MESSAGE))).width(98).build(), 1);
        gridLayout.arrangeElements();
        FrameLayout.alignInRectangle((LayoutElement)gridLayout, (int)0, (int)0, (int)this.width, (int)this.height, (float)0.5f, (float)0.5f);
        SaveReplayScreen saveReplayScreen = this;
        gridLayout.visitWidgets(x$0 -> saveReplayScreen.addRenderableWidget((GuiEventListener)x$0));
        this.setInitialFocus((GuiEventListener)replayNameEditBox);
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    public void tick() {
        super.tick();
        if (!Files.exists(this.recordFolder, new LinkOption[0])) {
            Minecraft.getInstance().setScreen((Screen)new AlertScreen(() -> Minecraft.getInstance().setScreen(this.previousScreen), FlashbackTextComponents.ERROR_SAVING_REPLAY, FlashbackTextComponents.RECORDING_FOLDER_MISSING));
        }
    }

    private void saveReplay() {
        if (!Files.exists(this.recordFolder, new LinkOption[0])) {
            Minecraft.getInstance().setScreen((Screen)new AlertScreen(() -> Minecraft.getInstance().setScreen(this.previousScreen), FlashbackTextComponents.ERROR_SAVING_REPLAY, FlashbackTextComponents.RECORDING_FOLDER_MISSING));
            return;
        }
        ReplayExporter.export(this.recordFolder, this.savePath, this.replayName);
        Flashback.removePendingReplaySave(this.recordFolder);
    }

    private void deleteReplay() {
        try {
            FileUtils.deleteDirectory((File)this.recordFolder.toFile());
        }
        catch (Exception e) {
            Flashback.LOGGER.error("Exception deleting record folder", (Throwable)e);
        }
        Flashback.removePendingReplaySave(this.recordFolder);
    }

    private void setReplayName(String replayName) {
        this.replayName = replayName;
        if (!this.replayName.isEmpty() && (this.savePath == null || this.generateFileFromReplayName)) {
            Object filename;
            Path replayDir = Flashback.getReplayFolder();
            if (!Files.exists(replayDir, new LinkOption[0])) {
                try {
                    Files.createDirectories(replayDir, new FileAttribute[0]);
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            try {
                filename = FileUtil.findAvailableName((Path)replayDir, (String)this.replayName.replace(' ', '_'), (String)".zip");
            }
            catch (IOException e) {
                Flashback.LOGGER.error("Error while trying to determine filename", (Throwable)e);
                filename = String.valueOf(UUID.randomUUID()) + ".zip";
            }
            this.savePath = replayDir.resolve((String)filename);
            if (this.filePathButton != null) {
                this.filePathButton.setMessage((Component)Component.literal((String)this.savePath.getFileName().toString()));
            }
        }
    }
}

