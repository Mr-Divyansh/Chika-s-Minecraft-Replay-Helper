/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl
 *  net.minecraft.network.PacketListener
 *  net.minecraft.network.PacketProcessor
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.PacketUtils
 *  net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket
 *  net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.playback;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.packet.FinishedServerTick;
import java.net.URL;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.network.PacketListener;
import net.minecraft.network.PacketProcessor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ClientCommonPacketListenerImpl.class})
public abstract class MixinClientCommonPacketListenerImpl {
    @Shadow
    @Final
    protected Minecraft minecraft;

    @Shadow
    @Nullable
    private static URL parseResourcePackUrl(String string) {
        return null;
    }

    @Inject(method={"handleResourcePackPush"}, at={@At(value="HEAD")}, cancellable=true)
    public void handleResourcePackPush(ClientboundResourcePackPushPacket clientboundResourcePackPushPacket, CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            PacketUtils.ensureRunningOnSameThread((Packet)clientboundResourcePackPushPacket, (PacketListener)((ClientCommonPacketListenerImpl)this), (PacketProcessor)this.minecraft.packetProcessor());
            UUID uuid = clientboundResourcePackPushPacket.id();
            URL uRL = MixinClientCommonPacketListenerImpl.parseResourcePackUrl(clientboundResourcePackPushPacket.url());
            if (uRL != null) {
                String string = clientboundResourcePackPushPacket.hash();
                this.minecraft.getDownloadedPackSource().allowServerPacks();
                this.minecraft.getDownloadedPackSource().pushPack(uuid, uRL, string);
            }
            ci.cancel();
        }
    }

    @Inject(method={"handleCustomPayload(Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void handleCustomPayload(ClientboundCustomPayloadPacket clientboundCustomPayloadPacket, CallbackInfo ci) {
        if (clientboundCustomPayloadPacket.payload() == FinishedServerTick.INSTANCE) {
            if (Flashback.EXPORT_JOB != null) {
                Flashback.EXPORT_JOB.onFinishedServerTick();
            }
            ci.cancel();
        }
    }
}

