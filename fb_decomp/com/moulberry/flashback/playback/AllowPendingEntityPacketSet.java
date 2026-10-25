/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.network.protocol.game.ClientboundRotateHeadPacket
 *  net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
 *  net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket
 *  net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket
 *  net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket
 *  net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket
 *  net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket
 */
package com.moulberry.flashback.playback;

import java.util.Set;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;

public class AllowPendingEntityPacketSet {
    private static final Set<Class<? extends Packet<?>>> ALLOW_PENDING_ENTITY = Set.of(ClientboundAddEntityPacket.class, ClientboundSetEntityDataPacket.class, ClientboundUpdateAttributesPacket.class, ClientboundSetEntityMotionPacket.class, ClientboundSetEquipmentPacket.class, ClientboundUpdateMobEffectPacket.class, ClientboundRotateHeadPacket.class, ClientboundTeleportEntityPacket.class);

    public static boolean allowPendingEntity(Packet<?> packet) {
        return ALLOW_PENDING_ENTITY.contains(packet.getClass());
    }
}

