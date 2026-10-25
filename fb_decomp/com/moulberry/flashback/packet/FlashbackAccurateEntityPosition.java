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
import com.moulberry.flashback.action.PositionAndAngle;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record FlashbackAccurateEntityPosition(int entityId, List<PositionAndAngle> positionAndAngles) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<FlashbackAccurateEntityPosition> TYPE = new CustomPacketPayload.Type(Flashback.createIdentifier("accurate_entity_position"));
    public static final StreamCodec<FriendlyByteBuf, FlashbackAccurateEntityPosition> STREAM_CODEC = new AccurateEntityPositionStreamCodec();

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class AccurateEntityPositionStreamCodec
    implements StreamCodec<FriendlyByteBuf, FlashbackAccurateEntityPosition> {
        public FlashbackAccurateEntityPosition decode(FriendlyByteBuf friendlyByteBuf) {
            int entityId = friendlyByteBuf.readVarInt();
            int interpolatedCount = friendlyByteBuf.readVarInt();
            ArrayList<PositionAndAngle> interpolatedPositions = new ArrayList<PositionAndAngle>(interpolatedCount);
            for (int i = 0; i < interpolatedCount; ++i) {
                double x = friendlyByteBuf.readDouble();
                double y = friendlyByteBuf.readDouble();
                double z = friendlyByteBuf.readDouble();
                float yaw = friendlyByteBuf.readFloat();
                float pitch = friendlyByteBuf.readFloat();
                interpolatedPositions.add(new PositionAndAngle(x, y, z, yaw, pitch));
            }
            return new FlashbackAccurateEntityPosition(entityId, interpolatedPositions);
        }

        public void encode(FriendlyByteBuf friendlyByteBuf, FlashbackAccurateEntityPosition accurateEntityPosition) {
            friendlyByteBuf.writeVarInt(accurateEntityPosition.entityId);
            friendlyByteBuf.writeVarInt(accurateEntityPosition.positionAndAngles.size());
            for (PositionAndAngle interpolatedPosition : accurateEntityPosition.positionAndAngles) {
                friendlyByteBuf.writeDouble(interpolatedPosition.x());
                friendlyByteBuf.writeDouble(interpolatedPosition.y());
                friendlyByteBuf.writeDouble(interpolatedPosition.z());
                friendlyByteBuf.writeFloat(interpolatedPosition.yaw());
                friendlyByteBuf.writeFloat(interpolatedPosition.pitch());
            }
        }
    }
}

