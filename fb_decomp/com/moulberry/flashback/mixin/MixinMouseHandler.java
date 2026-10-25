/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.MouseHandler
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.ui.CustomImGuiImplGlfw;
import com.moulberry.flashback.editor.ui.ReplayUI;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={MouseHandler.class})
public class MixinMouseHandler {
    @Inject(method={"isMouseGrabbed"}, at={@At(value="HEAD")}, cancellable=true)
    public void isMouseGrabbed(CallbackInfoReturnable<Boolean> cir) {
        if (ReplayUI.isActive()) {
            cir.setReturnValue((Object)(ReplayUI.imguiGlfw.getMouseHandledBy() == CustomImGuiImplGlfw.MouseHandledBy.GAME ? 1 : 0));
        } else if (Flashback.isExporting()) {
            cir.setReturnValue((Object)false);
        }
    }

    @Inject(method={"onButton", "onScroll", "onMove"}, at={@At(value="HEAD")}, cancellable=true)
    public void onUseMouse(CallbackInfo ci) {
        if (Flashback.isExporting()) {
            ci.cancel();
        }
    }

    @Inject(method={"grabMouse"}, at={@At(value="HEAD")}, cancellable=true)
    public void grabMouse(CallbackInfo ci) {
        if (ReplayUI.isActive() || Flashback.isExporting()) {
            ci.cancel();
        }
    }

    @Inject(method={"releaseMouse"}, at={@At(value="HEAD")}, cancellable=true)
    public void releaseMouse(CallbackInfo ci) {
        if (ReplayUI.isActive()) {
            ci.cancel();
        }
    }
}

