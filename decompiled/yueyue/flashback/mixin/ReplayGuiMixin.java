/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.DeltaTracker
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.renderer.state.gui.GuiRenderState
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package yueyue.flashback.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yueyue.flashback.UiFrame;
import yueyue.flashback.UiRuntime;

@Mixin(value={Gui.class})
public abstract class ReplayGuiMixin {
    @Inject(method={"extractRenderState"}, at={@At(value="HEAD")}, cancellable=true)
    private void yueyue$overlay(GuiGraphicsExtractor graphics, DeltaTracker delta, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();
        UiFrame frame = UiRuntime.currentFrame();
        if (client.screen != null || client.getOverlay() != null || !UiRuntime.visible(frame)) {
            return;
        }
        if (UiRuntime.extract(graphics, frame)) {
            if (frame.crosshair()) {
                ((HudInvoker)this).yueyue$crosshair(graphics, delta);
            }
            ci.cancel();
        }
    }
}

