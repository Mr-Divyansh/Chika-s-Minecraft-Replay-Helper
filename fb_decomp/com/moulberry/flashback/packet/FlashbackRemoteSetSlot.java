/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 *  net.minecraft.world.item.ItemStack
 */
package com.moulberry.flashback.packet;

import com.moulberry.flashback.Flashback;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record FlashbackRemoteSetSlot(int entityId, int slot, ItemStack itemStack) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<FlashbackRemoteSetSlot> TYPE = new CustomPacketPayload.Type(Flashback.createIdentifier("remote_set_slot"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FlashbackRemoteSetSlot> STREAM_CODEC = new RemoteSetSlotStreamCodec();

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class RemoteSetSlotStreamCodec
    implements StreamCodec<RegistryFriendlyByteBuf, FlashbackRemoteSetSlot> {
        public FlashbackRemoteSetSlot decode(RegistryFriendlyByteBuf friendlyByteBuf) {
            int entityId = friendlyByteBuf.readVarInt();
            byte slot = friendlyByteBuf.readByte();
            ItemStack itemStack = (ItemStack)ItemStack.OPTIONAL_STREAM_CODEC.decode((Object)friendlyByteBuf);
            return new FlashbackRemoteSetSlot(entityId, slot, itemStack);
        }

        public void encode(RegistryFriendlyByteBuf friendlyByteBuf, FlashbackRemoteSetSlot remoteHotbarSlot) {
            friendlyByteBuf.writeVarInt(remoteHotbarSlot.entityId);
            friendlyByteBuf.writeByte(remoteHotbarSlot.slot);
            ItemStack.OPTIONAL_STREAM_CODEC.encode((Object)friendlyByteBuf, (Object)remoteHotbarSlot.itemStack);
        }
    }
}

