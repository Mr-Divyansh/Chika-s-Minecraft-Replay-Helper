/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.components.Tooltip
 *  net.minecraft.client.renderer.RenderPipelines
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 */
package com.moulberry.flashback.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class FlashbackButton
extends Button {
    private static final Identifier ICON_PIXELATED = Identifier.parse((String)"flashback:icon_pixelated.png");
    private final Identifier icon;

    public FlashbackButton(int x, int y, int width, int height, Component component, Button.OnPress onPress) {
        this(x, y, width, height, component, onPress, ICON_PIXELATED);
    }

    public FlashbackButton(int x, int y, int width, int height, Component component, Button.OnPress onPress, Identifier icon) {
        super(x, y, width, height, component, onPress, DEFAULT_NARRATION);
        this.icon = icon;
    }

    public FlashbackButton flashbackWithTooltip() {
        this.setTooltip(Tooltip.create((Component)this.getMessage()));
        return this;
    }

    protected void extractContents(GuiGraphicsExtractor guiGraphics, int i, int j, float f) {
        super.extractDefaultSprite(guiGraphics);
        int size = 16;
        int paddingX = (this.getWidth() - 16) / 2;
        int paddingY = (this.getHeight() - 16) / 2;
        int x = this.getX() + paddingX;
        int y = this.getY() + paddingY;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.icon, x, y, 0.0f, 0.0f, 16, 16, 16, 16, (int)(this.alpha * 255.0f) << 24 | 0xFFFFFF);
    }
}

