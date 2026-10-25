/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.v2.WrapWithCondition
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.players.PlayerList
 *  net.minecraft.world.clock.ServerClockManager
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.playback;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.playback.ReplayServer;
import java.util.Map;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.clock.ServerClockManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ServerClockManager.class})
public class MixinServerClockManager {
    @Shadow
    @Final
    private Map clocks;

    @Inject(method={"init"}, at={@At(value="HEAD")})
    public void init(MinecraftServer server, CallbackInfo ci) {
        if (server instanceof ReplayServer) {
            this.clocks.clear();
        }
    }

    @WrapWithCondition(method={"modifyClock"}, at={@At(value="INVOKE", target="Lnet/minecraft/server/players/PlayerList;broadcastAll(Lnet/minecraft/network/protocol/Packet;)V")})
    public boolean modifyClock_broadcast(PlayerList instance, Packet<?> packet) {
        return !Flashback.isInReplay();
    }
}

