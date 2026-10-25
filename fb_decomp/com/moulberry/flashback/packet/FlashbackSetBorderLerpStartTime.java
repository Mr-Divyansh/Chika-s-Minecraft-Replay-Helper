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

public record FlashbackSetBorderLerpStartTime(long time) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<FlashbackSetBorderLerpStartTime> TYPE = new CustomPacketPayload.Type(Flashback.createIdentifier("set_border_lerp_start_time"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FlashbackSetBorderLerpStartTime> STREAM_CODEC = new SetBorderLerpStartTimeCodec();

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class SetBorderLerpStartTimeCodec
    implements StreamCodec<RegistryFriendlyByteBuf, FlashbackSetBorderLerpStartTime> {
        public FlashbackSetBorderLerpStartTime decode(RegistryFriendlyByteBuf friendlyByteBuf) {
            long millis = friendlyByteBuf.readLong();
            return new FlashbackSetBorderLerpStartTime(millis);
        }

        public void encode(RegistryFriendlyByteBuf friendlyByteBuf, FlashbackSetBorderLerpStartTime setBorderLerpStartTime) {
            friendlyByteBuf.writeLong(setBorderLerpStartTime.time);
        }
    }
}

