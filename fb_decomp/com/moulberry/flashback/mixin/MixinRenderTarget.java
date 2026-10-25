/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.pipeline.RenderTarget
 *  com.mojang.blaze3d.platform.Window
 *  net.minecraft.client.Minecraft
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Window;
import com.moulberry.flashback.FramebufferUtils;
import com.moulberry.flashback.WindowSizeTracker;
import com.moulberry.flashback.editor.ui.ReplayUI;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderTarget.class}, priority=800)
public abstract class MixinRenderTarget {
    @Inject(method={"blitToScreen()V"}, at={@At(value="HEAD")}, cancellable=true)
    public void blitToScreen(CallbackInfo ci) {
        if (this == Minecraft.getInstance().getMainRenderTarget() && ReplayUI.isActive() && ReplayUI.frameWidth > 1 && ReplayUI.frameHeight > 1) {
            Window window = Minecraft.getInstance().getWindow();
            float frameTop = (float)ReplayUI.frameY / (float)ReplayUI.viewportSizeY;
            float frameLeft = (float)ReplayUI.frameX / (float)ReplayUI.viewportSizeX;
            float frameWidth = (float)Math.max(1, ReplayUI.frameWidth) / (float)ReplayUI.viewportSizeX;
            float frameHeight = (float)Math.max(1, ReplayUI.frameHeight) / (float)ReplayUI.viewportSizeY;
            int framebufferWidth = WindowSizeTracker.getWidth(window);
            int framebufferHeight = WindowSizeTracker.getHeight(window);
            FramebufferUtils.blitToScreenPartial((RenderTarget)this, framebufferWidth, framebufferHeight, frameLeft, frameTop, frameLeft + frameWidth, frameTop + frameHeight);
            ci.cancel();
        }
    }
}

