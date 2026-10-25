/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.TextAlignment
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.MultiLineLabel
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.CommonComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 */
package com.moulberry.flashback.screen;

import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class RecoverRecordingsScreen
extends Screen {
    private Screen lastScreen;
    private final Component title;
    private final Component messageText;
    private MultiLineLabel message = MultiLineLabel.EMPTY;
    private Consumer<RecoveryOption> handler;

    public RecoverRecordingsScreen(Screen lastScreen, Component title, Component message, Consumer<RecoveryOption> handler) {
        super(title);
        this.lastScreen = lastScreen;
        this.title = title;
        this.messageText = message;
        this.handler = handler;
    }

    public Component getNarrationMessage() {
        return CommonComponents.joinForNarration((Component[])new Component[]{super.getNarrationMessage(), this.messageText});
    }

    protected void init() {
        super.init();
        this.message = MultiLineLabel.create((Font)this.font, (Component)this.messageText, (int)(this.width - 50));
        int lines = this.message.getLineCount();
        Objects.requireNonNull(this.font);
        int height = lines * 9;
        int buttonY = Mth.clamp((int)(90 + height + 12), (int)(this.height / 6 + 96), (int)(this.height - 24));
        this.addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable((String)"flashback.recover").withStyle(ChatFormatting.GREEN), b -> {
            Minecraft.getInstance().setScreen(this.lastScreen);
            this.handler.accept(RecoveryOption.RECOVER);
        }).bounds((this.width - 304) / 2, buttonY, 96, 20).build());
        this.addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable((String)"flashback.skip").withStyle(ChatFormatting.YELLOW), b -> {
            Minecraft.getInstance().setScreen(this.lastScreen);
            this.handler.accept(RecoveryOption.SKIP);
        }).bounds((this.width - 304) / 2 + 104, buttonY, 96, 20).build());
        this.addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable((String)"flashback.delete").withStyle(ChatFormatting.RED), b -> {
            Minecraft.getInstance().setScreen(this.lastScreen);
            this.handler.accept(RecoveryOption.DELETE);
        }).bounds((this.width - 304) / 2 + 208, buttonY, 96, 20).build());
    }

    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int i, int j, float f) {
        super.extractRenderState(guiGraphics, i, j, f);
        guiGraphics.centeredText(this.font, this.title, this.width / 2, 70, -1);
        int n = this.width / 2;
        Objects.requireNonNull(this.font);
        this.message.visitLines(TextAlignment.CENTER, n, 90, 9, guiGraphics.textRenderer());
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    public static enum RecoveryOption {
        RECOVER,
        SKIP,
        DELETE;

    }
}

