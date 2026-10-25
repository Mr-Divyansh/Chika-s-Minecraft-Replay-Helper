/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 */
package com.moulberry.flashback.packet;

import com.moulberry.flashback.Flashback;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class FlashbackClearParticles
implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FlashbackClearParticles> TYPE = new CustomPacketPayload.Type(Flashback.createIdentifier("clear_particles"));
    public static final FlashbackClearParticles INSTANCE = new FlashbackClearParticles();

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

