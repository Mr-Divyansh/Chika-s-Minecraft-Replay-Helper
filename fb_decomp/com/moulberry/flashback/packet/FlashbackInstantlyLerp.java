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

public class FlashbackInstantlyLerp
implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FlashbackInstantlyLerp> TYPE = new CustomPacketPayload.Type(Flashback.createIdentifier("instantly_lerp"));
    public static final FlashbackInstantlyLerp INSTANCE = new FlashbackInstantlyLerp();

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

