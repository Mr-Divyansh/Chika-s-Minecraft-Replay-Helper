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
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yueyue.flashback.UiFrame;
import yueyue.flashback.UiRuntime;
import yueyue.flashback.mixin.HudInvoker;

@Mixin(value={Gui.class})
public abstract class ReplayGuiMixin {
    @Shadow
    @Final
    private GuiRenderState guiRenderState;

    @Inject(method={"extractRenderState"}, at={@At(value="HEAD")}, cancellable=true)
    private void yueyue$overlay(DeltaTracker delta, boolean first, boolean second, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();
        UiFrame frame = UiRuntime.currentFrame();
        if (client.gui.screen() != null || client.gui.overlay() != null || !UiRuntime.visible(frame)) {
            return;
        }
        GuiGraphicsExtractor graphics = new GuiGraphicsExtractor(client, this.guiRenderState, 0, 0);
        if (UiRuntime.extract(graphics, frame)) {
            if (frame.crosshair()) {
                ((HudInvoker)client.gui.hud).yueyue$crosshair(graphics, delta);
            }
            ci.cancel();
        }
    }
}

