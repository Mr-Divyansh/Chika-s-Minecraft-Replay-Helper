/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 */
package com.moulberry.flashback.packet;

import com.moulberry.flashback.Flashback;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record FlashbackRawCustomPayload(byte[] packetBytes, boolean configPhase) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<FlashbackRawCustomPayload> TYPE = new CustomPacketPayload.Type(Flashback.createIdentifier("raw_custom_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FlashbackRawCustomPayload> STREAM_CODEC = new ProcessPacketRawStreamCodec();

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class ProcessPacketRawStreamCodec
    implements StreamCodec<RegistryFriendlyByteBuf, FlashbackRawCustomPayload> {
        public FlashbackRawCustomPayload decode(RegistryFriendlyByteBuf friendlyByteBuf) {
            byte[] packetBytes = friendlyByteBuf.readByteArray();
            boolean configPhase = friendlyByteBuf.readBoolean();
            return new FlashbackRawCustomPayload(packetBytes, configPhase);
        }

        public void encode(RegistryFriendlyByteBuf friendlyByteBuf, FlashbackRawCustomPayload packet) {
            friendlyByteBuf.writeByteArray(packet.packetBytes());
            friendlyByteBuf.writeBoolean(packet.configPhase());
        }
    }
}

