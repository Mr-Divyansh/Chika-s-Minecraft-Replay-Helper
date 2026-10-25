/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 */
package com.moulberry.flashback.screen;

import java.util.Objects;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

public class BottomTextWidget
extends AbstractWidget {
    private final Font font;
    public float horizontalAlignment = 0.5f;

    public BottomTextWidget(int width, int height, Component component, Font font) {
        super(0, 0, width, height, component);
        this.font = font;
        this.active = false;
    }

    public BottomTextWidget alignLeft() {
        this.horizontalAlignment = 0.0f;
        return this;
    }

    public void extractWidgetRenderState(GuiGraphicsExtractor guiGraphics, int i, int j, float f) {
        Component component = this.getMessage();
        int width = this.getWidth();
        int textWidth = this.font.width((FormattedText)component);
        int x = this.getX() + Math.round((float)(width - textWidth) * this.horizontalAlignment);
        int n = this.getY() + this.getHeight();
        Objects.requireNonNull(this.font);
        int y = n - 9 + 1;
        guiGraphics.text(this.font, component.getVisualOrderText(), x, y, -1);
    }

    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }
}

