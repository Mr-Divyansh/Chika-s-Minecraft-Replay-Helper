/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.ChannelFutureListener
 *  net.minecraft.network.Connection
 *  net.minecraft.network.ConnectionProtocol
 *  net.minecraft.network.PacketListener
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.record;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.ext.ConnectionExt;
import com.moulberry.flashback.record.IgnoredPacketSet;
import com.moulberry.flashback.record.Recorder;
import io.netty.channel.ChannelFutureListener;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Connection.class})
public class MixinConnection
implements ConnectionExt {
    @Unique
    private boolean filterUnnecessaryPackets = false;

    @Override
    public void flashback$setFilterUnnecessaryPackets() {
        this.filterUnnecessaryPackets = true;
    }

    @Inject(method={"genericsFtw"}, at={@At(value="HEAD")})
    private static void genericsFtw(Packet<?> packet, PacketListener packetListener, CallbackInfo ci) {
        Recorder recorder = Flashback.RECORDER;
        if (recorder != null) {
            if (packetListener instanceof ClientGamePacketListener) {
                recorder.writePacketAsync(packet, ConnectionProtocol.PLAY);
            } else if (packetListener instanceof ClientConfigurationPacketListener) {
                recorder.writePacketAsync(packet, ConnectionProtocol.CONFIGURATION);
            }
        }
    }

    @Inject(method={"send(Lnet/minecraft/network/protocol/Packet;Lio/netty/channel/ChannelFutureListener;Z)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void send(Packet<?> packet, @Nullable ChannelFutureListener channelFutureListener, boolean bl, CallbackInfo ci) {
        if (this.filterUnnecessaryPackets && IgnoredPacketSet.isIgnoredInReplay(packet)) {
            ci.cancel();
        }
    }
}

