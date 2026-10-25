/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.impl.networking.UntrackedPacketListener
 *  net.minecraft.network.Connection
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.network.CommonListenerCookie
 *  net.minecraft.server.network.ServerGamePacketListenerImpl
 */
package com.moulberry.flashback.playback;

import net.fabricmc.fabric.impl.networking.UntrackedPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class FlashbackFakePlayerPacketListener
extends ServerGamePacketListenerImpl
implements UntrackedPacketListener {
    public FlashbackFakePlayerPacketListener(MinecraftServer server, Connection connection, ServerPlayer player, CommonListenerCookie cookie) {
        super(server, connection, player, cookie);
    }
}

