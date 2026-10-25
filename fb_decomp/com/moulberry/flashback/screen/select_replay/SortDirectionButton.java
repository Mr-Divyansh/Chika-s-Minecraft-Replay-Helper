/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.renderer.RenderPipelines
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 */
package com.moulberry.flashback.screen.select_replay;

import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class SortDirectionButton
extends Button {
    private static final Identifier DOWN_ARROW = Identifier.parse((String)"flashback:down_arrow.png");
    private static final Identifier UP_ARROW = Identifier.parse((String)"flashback:up_arrow.png");
    public boolean sortDescending;

    public SortDirectionButton(int x, int y, int width, int height, Component component, Consumer<Boolean> changed, boolean initialSortDescending) {
        super(x, y, width, height, component, button -> {
            SortDirectionButton sortButton = (SortDirectionButton)button;
            sortButton.sortDescending = !sortButton.sortDescending;
            changed.accept(sortButton.sortDescending);
        }, DEFAULT_NARRATION);
        this.sortDescending = initialSortDescending;
    }

    protected void extractContents(GuiGraphicsExtractor guiGraphics, int i, int j, float f) {
        super.extractDefaultSprite(guiGraphics);
        int size = 16;
        int paddingX = (this.getWidth() - 16) / 2;
        int paddingY = (this.getHeight() - 16) / 2;
        int x = this.getX() + paddingX;
        int y = this.getY() + paddingY;
        if (this.sortDescending) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, DOWN_ARROW, x, y, 0.0f, 0.0f, 16, 16, 16, 16);
        } else {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, UP_ARROW, x, y, 0.0f, 0.0f, 16, 16, 16, 16);
        }
    }
}

