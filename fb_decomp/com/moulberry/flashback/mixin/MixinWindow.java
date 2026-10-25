/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Window
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin;

import com.mojang.blaze3d.platform.Window;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.ext.WindowExt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Window.class})
public abstract class MixinWindow
implements WindowExt {
    @Shadow
    private int framebufferWidth;
    @Shadow
    private int framebufferHeight;
    @Shadow
    private int width;
    @Shadow
    private int height;
    @Shadow
    private int guiScale;
    @Shadow
    private int guiScaledWidth;
    @Shadow
    private int guiScaledHeight;
    @Shadow
    private int windowedWidth;
    @Shadow
    @Final
    private long handle;
    @Shadow
    public boolean isResized;
    @Unique
    private int lastModifiedFramebufferWidth = -1;
    @Unique
    private int lastModifiedFramebufferHeight = -1;

    @Shadow
    public abstract int getWidth();

    @Shadow
    public abstract int getHeight();

    @Unique
    private float calculateWidthScaleFactor() {
        return Math.max(0.125f, Math.min(8.0f, (float)this.framebufferWidth / (float)this.width));
    }

    @Unique
    private float calculateHeightScaleFactor() {
        return Math.max(0.125f, Math.min(8.0f, (float)this.framebufferHeight / (float)this.height));
    }

    @Unique
    private int calculateNewFramebufferWidth() {
        if (Flashback.EXPORT_JOB != null && Flashback.EXPORT_JOB.shouldChangeFramebufferSize()) {
            return Flashback.EXPORT_JOB.getWidth();
        }
        if (ReplayUI.shouldModifyViewport()) {
            return ReplayUI.getNewGameWidth(this.calculateWidthScaleFactor());
        }
        return -1;
    }

    @Unique
    private int calculateNewFramebufferHeight() {
        if (Flashback.EXPORT_JOB != null && Flashback.EXPORT_JOB.shouldChangeFramebufferSize()) {
            return Flashback.EXPORT_JOB.getHeight();
        }
        if (ReplayUI.shouldModifyViewport()) {
            return ReplayUI.getNewGameHeight(this.calculateHeightScaleFactor());
        }
        return -1;
    }

    @Override
    public void flashback$checkForOverrideResize() {
        int overrideWidth = this.calculateNewFramebufferWidth();
        int overrideHeight = this.calculateNewFramebufferHeight();
        if (this.lastModifiedFramebufferWidth != overrideWidth || this.lastModifiedFramebufferHeight != overrideHeight) {
            this.lastModifiedFramebufferWidth = overrideWidth;
            this.lastModifiedFramebufferHeight = overrideHeight;
            this.isResized = true;
        }
    }

    @Inject(method={"getWidth"}, at={@At(value="HEAD")}, cancellable=true)
    public void getWidth(CallbackInfoReturnable<Integer> cir) {
        int width = this.calculateNewFramebufferWidth();
        if (width != -1) {
            cir.setReturnValue((Object)width);
        }
    }

    @Inject(method={"getHeight"}, at={@At(value="HEAD")}, cancellable=true)
    public void getHeight(CallbackInfoReturnable<Integer> cir) {
        int width = this.calculateNewFramebufferHeight();
        if (width != -1) {
            cir.setReturnValue((Object)width);
        }
    }

    @Inject(method={"getScreenWidth"}, at={@At(value="HEAD")}, cancellable=true)
    public void getScreenWidth(CallbackInfoReturnable<Integer> cir) {
        if (ReplayUI.shouldModifyViewport()) {
            cir.setReturnValue((Object)ReplayUI.getNewGameWidth(1.0f));
        }
    }

    @Inject(method={"getScreenHeight"}, at={@At(value="HEAD")}, cancellable=true)
    public void getScreenHeight(CallbackInfoReturnable<Integer> cir) {
        if (ReplayUI.shouldModifyViewport()) {
            cir.setReturnValue((Object)ReplayUI.getNewGameHeight(1.0f));
        }
    }

    @Inject(method={"onResize"}, at={@At(value="HEAD")}, cancellable=true)
    public void onResize(long l, int i, int j, CallbackInfo ci) {
        if (l != this.handle) {
            ci.cancel();
        }
    }

    @Inject(method={"calculateScale"}, at={@At(value="HEAD")}, cancellable=true)
    public void calculateScale(int scale, boolean forceEven, CallbackInfoReturnable<Integer> cir) {
        if (Flashback.EXPORT_JOB != null && Flashback.EXPORT_JOB.shouldChangeFramebufferSize()) {
            int j;
            int fbw = Flashback.EXPORT_JOB.getWidth();
            int fbh = Flashback.EXPORT_JOB.getHeight();
            for (j = 1; j != scale && j < fbw && j < fbh && fbw / (j + 1) >= 320 && fbh / (j + 1) >= 240; ++j) {
            }
            if (forceEven && j % 2 != 0) {
                ++j;
            }
            cir.setReturnValue((Object)j);
        } else if (ReplayUI.shouldModifyViewport()) {
            int j;
            int fbw = ReplayUI.getNewGameWidth(this.calculateWidthScaleFactor());
            int fbh = ReplayUI.getNewGameHeight(this.calculateHeightScaleFactor());
            for (j = 1; j != scale && j < fbw && j < fbh && fbw / (j + 1) >= 320 && fbh / (j + 1) >= 240; ++j) {
            }
            if (forceEven && j % 2 != 0) {
                ++j;
            }
            cir.setReturnValue((Object)j);
        }
    }

    @Inject(method={"setGuiScale"}, at={@At(value="HEAD")}, cancellable=true)
    public void setGuiScale(int scale, CallbackInfo ci) {
        if (Flashback.EXPORT_JOB != null && Flashback.EXPORT_JOB.shouldChangeFramebufferSize()) {
            int fbw = Flashback.EXPORT_JOB.getWidth();
            int fbh = Flashback.EXPORT_JOB.getHeight();
            this.guiScale = scale;
            int i = (int)((double)fbw / (double)scale);
            this.guiScaledWidth = (double)fbw / (double)scale > (double)i ? i + 1 : i;
            int j = (int)((double)fbh / (double)scale);
            this.guiScaledHeight = (double)fbh / (double)scale > (double)j ? j + 1 : j;
            ci.cancel();
        } else if (ReplayUI.shouldModifyViewport()) {
            int fbw = ReplayUI.getNewGameWidth(this.calculateWidthScaleFactor());
            int fbh = ReplayUI.getNewGameHeight(this.calculateHeightScaleFactor());
            this.guiScale = scale;
            int i = (int)((double)fbw / (double)scale);
            this.guiScaledWidth = (double)fbw / (double)scale > (double)i ? i + 1 : i;
            int j = (int)((double)fbh / (double)scale);
            this.guiScaledHeight = (double)fbh / (double)scale > (double)j ? j + 1 : j;
            ci.cancel();
        }
    }
}

