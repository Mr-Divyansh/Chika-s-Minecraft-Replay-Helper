/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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

import com.moulberry.flashback.Flashback;
import java.time.Duration;
import java.util.Objects;
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

public class UnsupportedLoaderScreen
extends Screen {
    private Screen lastScreen;
    private final Component title;
    private final Component messageText;
    private MultiLineLabel message = MultiLineLabel.EMPTY;
    private long lastInitTime;
    private int countdown = 15;

    public UnsupportedLoaderScreen(Screen lastScreen, Component title, Component message) {
        super(title);
        this.lastScreen = lastScreen;
        this.title = title;
        this.messageText = message;
    }

    public Component getNarrationMessage() {
        return CommonComponents.joinForNarration((Component[])new Component[]{super.getNarrationMessage(), this.messageText});
    }

    protected void init() {
        super.init();
        this.lastInitTime = System.currentTimeMillis();
        this.message = MultiLineLabel.create((Font)this.font, (Component)this.messageText, (int)(this.width - 50));
        int lines = this.message.getLineCount();
        Objects.requireNonNull(this.font);
        int height = lines * 9;
        int buttonY = Mth.clamp((int)(90 + height + 12), (int)(this.height / 6 + 96), (int)(this.height - 24));
        this.clearWidgets();
        if (this.countdown == 0) {
            Button button = Button.builder((Component)Component.translatable((String)"flashback.i_understand"), b -> {
                Flashback.getConfig().internal.nextUnsupportedModLoaderWarning = System.currentTimeMillis() + Duration.ofDays(7L).toMillis();
                Flashback.getConfig().delayedSaveToDefaultFolder();
                Minecraft.getInstance().setScreen(this.lastScreen);
            }).bounds((this.width - 150) / 2, buttonY, 150, 20).build();
            this.addRenderableWidget((GuiEventListener)button);
        } else if (this.countdown <= 10) {
            Button button = Button.builder((Component)Component.translatable((String)"flashback.i_understand_countdown", (Object[])new Object[]{Component.literal((String)String.valueOf(this.countdown))}), b -> {}).bounds((this.width - 150) / 2, buttonY, 150, 20).build();
            button.active = false;
            this.addRenderableWidget((GuiEventListener)button);
        }
    }

    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int i, int j, float f) {
        super.extractRenderState(guiGraphics, i, j, f);
        guiGraphics.centeredText(this.font, this.title, this.width / 2, 70, -1);
        int n = this.width / 2;
        Objects.requireNonNull(this.font);
        this.message.visitLines(TextAlignment.CENTER, n, 90, 9, guiGraphics.textRenderer());
        if (this.countdown > 0 && System.currentTimeMillis() - this.lastInitTime > 1000L) {
            --this.countdown;
            this.init();
        }
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }
}

