/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.network.chat.ChatType$Bound
 *  net.minecraft.network.chat.OutgoingChatMessage
 *  net.minecraft.network.protocol.status.ServerStatus
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ClientInformation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.stats.Stat
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.item.crafting.RecipeHolder
 */
package com.moulberry.flashback.playback;

import com.mojang.authlib.GameProfile;
import java.util.Collection;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.crafting.RecipeHolder;

public class FlashbackFakePlayer
extends ServerPlayer {
    public FlashbackFakePlayer(MinecraftServer minecraftServer, ServerLevel serverLevel, GameProfile gameProfile, ClientInformation clientInformation) {
        super(minecraftServer, serverLevel, gameProfile, clientInformation);
    }

    public void remove(Entity.RemovalReason removalReason) {
        if (removalReason == Entity.RemovalReason.DISCARDED && !this.hasDisconnected()) {
            this.disconnect();
            this.level().getServer().getPlayerList().remove((ServerPlayer)this);
        } else {
            super.remove(removalReason);
        }
    }

    public void sendChatMessage(OutgoingChatMessage outgoingChatMessage, boolean bl, ChatType.Bound bound) {
    }

    public void sendServerStatus(ServerStatus serverStatus) {
    }

    public int awardRecipes(Collection<RecipeHolder<?>> collection) {
        return 0;
    }

    public void awardStat(Stat<?> stat, int i) {
    }

    public void resetStat(Stat<?> stat) {
    }
}

