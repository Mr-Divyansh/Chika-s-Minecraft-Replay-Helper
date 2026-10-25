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

public class FlashbackClearEntities
implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FlashbackClearEntities> TYPE = new CustomPacketPayload.Type(Flashback.createIdentifier("clear_entities"));
    public static final FlashbackClearEntities INSTANCE = new FlashbackClearEntities();

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

