/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.v2.WrapWithCondition
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.login.ServerboundHelloPacket
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.network.ServerLoginPacketListenerImpl
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.replay_server;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.authlib.GameProfile;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.playback.ReplayServer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ServerLoginPacketListenerImpl.class})
public abstract class MixinServerLoginPacketListenerImpl {
    @Shadow
    @Final
    MinecraftServer server;
    @Shadow
    @Nullable
    String requestedUsername;

    @Shadow
    abstract void startClientVerification(GameProfile var1);

    @Inject(method={"handleHello"}, at={@At(value="HEAD")}, cancellable=true)
    public void handleHello(ServerboundHelloPacket serverboundHelloPacket, CallbackInfo ci) {
        if (this.server instanceof ReplayServer) {
            this.requestedUsername = ReplayServer.REPLAY_VIEWER_NAME;
            UUID replayViewerUUID = UUID.nameUUIDFromBytes(serverboundHelloPacket.name().getBytes(StandardCharsets.UTF_8));
            GameProfile gameProfile = new GameProfile(replayViewerUUID, ReplayServer.REPLAY_VIEWER_NAME);
            this.startClientVerification(gameProfile);
            ci.cancel();
        }
    }

    @WrapWithCondition(method={"tick"}, at={@At(value="INVOKE", target="Lnet/minecraft/server/network/ServerLoginPacketListenerImpl;disconnect(Lnet/minecraft/network/chat/Component;)V")})
    public boolean dontDisconnectInReplay(ServerLoginPacketListenerImpl instance, Component component) {
        Flashback.updateIsInReplay();
        return !Flashback.isInReplay();
    }
}

