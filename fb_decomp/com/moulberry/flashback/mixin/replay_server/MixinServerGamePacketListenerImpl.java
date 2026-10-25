/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.v2.WrapWithCondition
 *  net.minecraft.network.Connection
 *  net.minecraft.network.PacketListener
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener
 *  net.minecraft.network.protocol.configuration.ConfigurationProtocols
 *  net.minecraft.network.protocol.game.ServerboundConfigurationAcknowledgedPacket
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.network.CommonListenerCookie
 *  net.minecraft.server.network.ConfigurationTask
 *  net.minecraft.server.network.ServerCommonPacketListenerImpl
 *  net.minecraft.server.network.ServerConfigurationPacketListenerImpl
 *  net.minecraft.server.network.ServerGamePacketListenerImpl
 *  org.slf4j.Logger
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.replay_server;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.ext.ServerConfigurationPacketListenerImplExt;
import com.moulberry.flashback.ext.ServerGamePacketListenerImplExt;
import java.util.List;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener;
import net.minecraft.network.protocol.configuration.ConfigurationProtocols;
import net.minecraft.network.protocol.game.ServerboundConfigurationAcknowledgedPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ConfigurationTask;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ServerGamePacketListenerImpl.class})
public abstract class MixinServerGamePacketListenerImpl
extends ServerCommonPacketListenerImpl
implements ServerGamePacketListenerImplExt {
    @Shadow
    public ServerPlayer player;
    @Unique
    private List<Packet<? super ClientConfigurationPacketListener>> pendingConfigurationPackets = null;
    @Unique
    private List<ConfigurationTask> pendingTasks = null;

    @Shadow
    public abstract void switchToConfig();

    public MixinServerGamePacketListenerImpl(MinecraftServer minecraftServer, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraftServer, connection, commonListenerCookie);
    }

    @Override
    public void flashback$switchToConfigWithTasks(List<Packet<? super ClientConfigurationPacketListener>> initialPackets, List<ConfigurationTask> tasks) {
        this.switchToConfig();
        this.pendingConfigurationPackets = initialPackets;
        this.pendingTasks = tasks;
    }

    @Inject(method={"switchToConfig"}, at={@At(value="HEAD")})
    public void switchToConfig(CallbackInfo ci) {
        this.pendingConfigurationPackets = null;
        this.pendingTasks = null;
    }

    @Inject(method={"handleConfigurationAcknowledged"}, at={@At(value="HEAD")}, cancellable=true)
    public void handleConfigurationAcknowledged(ServerboundConfigurationAcknowledgedPacket serverboundConfigurationAcknowledgedPacket, CallbackInfo ci) {
        if (this.pendingTasks != null) {
            ServerConfigurationPacketListenerImpl configurationHandler = new ServerConfigurationPacketListenerImpl(this.server, this.connection, this.createCookie(this.player.clientInformation()));
            ((ServerConfigurationPacketListenerImplExt)configurationHandler).flashback$startConfiguration(this.pendingConfigurationPackets, this.pendingTasks);
            this.pendingTasks = null;
            this.connection.setupInboundProtocol(ConfigurationProtocols.SERVERBOUND, (PacketListener)configurationHandler);
            ci.cancel();
        }
    }

    @WrapWithCondition(method={"onDisconnect"}, at={@At(value="INVOKE", target="Lorg/slf4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", remap=false)})
    public boolean onDisconnect_info(Logger instance, String s, Object o1, Object o2) {
        return !Flashback.isInReplay();
    }
}

