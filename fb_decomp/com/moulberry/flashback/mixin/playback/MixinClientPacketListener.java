/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.playback;

import com.moulberry.flashback.Flashback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ClientPacketListener.class}, priority=1100)
public class MixinClientPacketListener {
    @Unique
    private Screen screenBeforeHandleCustomPayload = null;

    @Inject(method={"handleCustomPayload"}, at={@At(value="HEAD")})
    public void handleCustomPayloadHead(CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            this.screenBeforeHandleCustomPayload = Minecraft.getInstance().screen;
        }
    }

    @Inject(method={"handleCustomPayload"}, at={@At(value="RETURN")})
    public void handleCustomPayloadReturn(CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            Minecraft.getInstance().setScreen(this.screenBeforeHandleCustomPayload);
        }
    }
}

