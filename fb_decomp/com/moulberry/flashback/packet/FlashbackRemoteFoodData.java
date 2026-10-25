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

public record FlashbackRemoteFoodData(int entityId, int foodLevel, float saturationLevel) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<FlashbackRemoteFoodData> TYPE = new CustomPacketPayload.Type(Flashback.createIdentifier("remote_food_data"));
    public static final StreamCodec<FriendlyByteBuf, FlashbackRemoteFoodData> STREAM_CODEC = new FlashbackRemoteFoodDataStreamCodec();

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class FlashbackRemoteFoodDataStreamCodec
    implements StreamCodec<FriendlyByteBuf, FlashbackRemoteFoodData> {
        public FlashbackRemoteFoodData decode(FriendlyByteBuf friendlyByteBuf) {
            int entityId = friendlyByteBuf.readVarInt();
            int foodLevel = friendlyByteBuf.readVarInt();
            float saturationLevel = friendlyByteBuf.readFloat();
            return new FlashbackRemoteFoodData(entityId, foodLevel, saturationLevel);
        }

        public void encode(FriendlyByteBuf friendlyByteBuf, FlashbackRemoteFoodData remoteHotbarSlot) {
            friendlyByteBuf.writeVarInt(remoteHotbarSlot.entityId);
            friendlyByteBuf.writeVarInt(remoteHotbarSlot.foodLevel);
            friendlyByteBuf.writeFloat(remoteHotbarSlot.saturationLevel);
        }
    }
}

