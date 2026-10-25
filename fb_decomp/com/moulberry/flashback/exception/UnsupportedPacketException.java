/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.protocol.Packet
 */
package com.moulberry.flashback.exception;

import net.minecraft.network.protocol.Packet;

public class UnsupportedPacketException
extends RuntimeException {
    public UnsupportedPacketException(Packet<?> packet) {
        super("Packet " + String.valueOf(packet.type().id()) + " is not supported");
    }
}

