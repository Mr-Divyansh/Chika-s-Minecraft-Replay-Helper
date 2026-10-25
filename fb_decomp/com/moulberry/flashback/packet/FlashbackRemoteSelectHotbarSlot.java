/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 */
package com.moulberry.flashback.packet;

import com.moulberry.flashback.Flashback;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record FlashbackRemoteSelectHotbarSlot(int entityId, int slot) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<FlashbackRemoteSelectHotbarSlot> TYPE = new CustomPacketPayload.Type(Flashback.createIdentifier("remote_select_hotbar_slot"));
    public static final StreamCodec<FriendlyByteBuf, FlashbackRemoteSelectHotbarSlot> STREAM_CODEC = new RemoteSelectHotbarSlotStreamCodec();

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class RemoteSelectHotbarSlotStreamCodec
    implements StreamCodec<FriendlyByteBuf, FlashbackRemoteSelectHotbarSlot> {
        public FlashbackRemoteSelectHotbarSlot decode(FriendlyByteBuf friendlyByteBuf) {
            int entityId = friendlyByteBuf.readVarInt();
            byte slot = friendlyByteBuf.readByte();
            return new FlashbackRemoteSelectHotbarSlot(entityId, slot);
        }

        public void encode(FriendlyByteBuf friendlyByteBuf, FlashbackRemoteSelectHotbarSlot remoteHotbarSlot) {
            friendlyByteBuf.writeVarInt(remoteHotbarSlot.entityId);
            friendlyByteBuf.writeByte(remoteHotbarSlot.slot);
        }
    }
}

