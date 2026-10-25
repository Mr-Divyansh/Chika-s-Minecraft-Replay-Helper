/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket
 *  net.minecraft.network.protocol.game.VecDeltaCodec
 *  net.minecraft.server.level.ServerEntity
 *  net.minecraft.server.level.ServerEntity$Synchronizer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.PositionMoveRotation
 *  net.minecraft.world.entity.boss.enderdragon.EnderDragonPart
 *  net.minecraft.world.phys.Vec3
 */
package com.moulberry.flashback;

import com.moulberry.flashback.UnsafeWrapper;
import java.util.ArrayList;
import java.util.function.Predicate;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket;
import net.minecraft.network.protocol.game.VecDeltaCodec;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import net.minecraft.world.phys.Vec3;

public class PacketHelper {
    private static ServerEntity.Synchronizer EMPTY_SYNCHRONIZER = new ServerEntity.Synchronizer(){

        public void sendToTrackingPlayers(Packet<? super ClientGamePacketListener> packet) {
        }

        public void sendToTrackingPlayersAndSelf(Packet<? super ClientGamePacketListener> packet) {
        }

        public void sendToTrackingPlayersFiltered(Packet<? super ClientGamePacketListener> packet, Predicate<ServerPlayer> predicate) {
        }
    };

    public static boolean shouldIgnoreEntity(Entity entity) {
        return entity == null || entity.isRemoved() || entity instanceof EnderDragonPart || entity.getType().clientTrackingRange() <= 0;
    }

    public static Packet<ClientGamePacketListener> createTeleportForUnknown(int id, double x, double y, double z, byte yRot, byte xRot, boolean onGround) {
        return new ClientboundEntityPositionSyncPacket(id, new PositionMoveRotation(new Vec3(x, y, z), Vec3.ZERO, (float)yRot, (float)xRot), onGround);
    }

    public static Packet<ClientGamePacketListener> createAddEntity(Entity entity) {
        ServerEntity serverEntity = null;
        try {
            serverEntity = new ServerEntity(null, entity, 1, false, EMPTY_SYNCHRONIZER);
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (serverEntity == null) {
            try {
                serverEntity = (ServerEntity)UnsafeWrapper.UNSAFE.allocateInstance(ServerEntity.class);
                serverEntity.positionCodec = new VecDeltaCodec();
                serverEntity.lastPassengers = new ArrayList();
                serverEntity.synchronizer = EMPTY_SYNCHRONIZER;
                serverEntity.entity = entity;
                serverEntity.positionCodec.setBase(entity.trackingPosition());
                serverEntity.lastSentMovement = entity.getDeltaMovement();
                serverEntity.lastSentYRot = (byte)Mth.floor((float)(entity.getYRot() * 256.0f / 360.0f));
                serverEntity.lastSentXRot = (byte)Mth.floor((float)(entity.getXRot() * 256.0f / 360.0f));
                serverEntity.lastSentYHeadRot = (byte)Mth.floor((float)(entity.getYHeadRot() * 256.0f / 360.0f));
                serverEntity.wasOnGround = entity.onGround();
                serverEntity.trackedDataValues = entity.getEntityData().getNonDefaultValues();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        try {
            return entity.getAddEntityPacket(serverEntity);
        }
        catch (Exception exception) {
            return PacketHelper.createAddEntity(entity, 0);
        }
    }

    public static ClientboundAddEntityPacket createAddEntity(Entity entity, int data) {
        return new ClientboundAddEntityPacket(entity.getId(), entity.getUUID(), entity.getX(), entity.getY(), entity.getZ(), entity.getXRot(), entity.getYRot(), entity.getType(), data, entity.getDeltaMovement(), (double)entity.getYHeadRot());
    }
}

