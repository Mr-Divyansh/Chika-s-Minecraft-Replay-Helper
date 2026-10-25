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

public class FinishedServerTick
implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FinishedServerTick> TYPE = new CustomPacketPayload.Type(Flashback.createIdentifier("finished_server_tick"));
    public static final FinishedServerTick INSTANCE = new FinishedServerTick();

    private FinishedServerTick() {
    }

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

