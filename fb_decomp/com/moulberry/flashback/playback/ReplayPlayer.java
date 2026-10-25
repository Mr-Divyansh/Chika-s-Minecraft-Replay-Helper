/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.network.protocol.game.CommonPlayerSpawnInfo
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ClientInformation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.stats.Stat
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeHolder
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.playback;

import com.mojang.authlib.GameProfile;
import com.moulberry.flashback.ext.ServerLevelExt;
import java.util.Collection;
import java.util.UUID;
import net.minecraft.network.protocol.game.CommonPlayerSpawnInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class ReplayPlayer
extends ServerPlayer {
    public boolean followLocalPlayerNextTick = false;
    public UUID spectatingUuid = null;
    public int spectatingUuidTickCount = 0;
    public int forceRespectateTickCount = 0;
    public UUID lastFirstPersonDataUUID = null;
    public int lastFirstPersonSelectedSlot = -1;
    public ItemStack[] lastFirstPersonHotbarItems = new ItemStack[9];
    public float lastFirstPersonExperienceProgress = 0.0f;
    public int lastFirstPersonTotalExperience = 0;
    public int lastFirstPersonExperienceLevel = 0;
    public int lastFirstPersonFoodLevel = 0;
    public float lastFirstPersonSaturationLevel = 0.0f;

    public ReplayPlayer(MinecraftServer minecraftServer, ServerLevel serverLevel, GameProfile gameProfile, ClientInformation clientInformation) {
        super(minecraftServer, serverLevel, gameProfile, clientInformation);
    }

    public CommonPlayerSpawnInfo createCommonSpawnInfo(ServerLevel serverLevel) {
        return new CommonPlayerSpawnInfo(serverLevel.dimensionTypeRegistration(), serverLevel.dimension(), ((ServerLevelExt)serverLevel).flashback$getSeedHash(), this.gameMode.getGameModeForPlayer(), this.gameMode.getPreviousGameModeForPlayer(), serverLevel.isDebug(), serverLevel.isFlat(), this.getLastDeathLocation(), this.getPortalCooldown(), serverLevel.getSeaLevel());
    }

    public void setCamera(@Nullable Entity entity) {
        super.setCamera(entity);
        if (entity == null) {
            this.spectatingUuid = null;
        }
    }

    public int awardRecipes(Collection<RecipeHolder<?>> collection) {
        return 0;
    }

    public void awardStat(Stat<?> stat, int i) {
    }

    public void resetStat(Stat<?> stat) {
    }

    public void indicateDamage(double d, double e) {
    }

    public void handleDamageEvent(DamageSource damageSource) {
    }

    public boolean hurtClient(DamageSource damageSource) {
        return false;
    }

    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f) {
        return false;
    }

    public boolean isInvulnerableTo(ServerLevel serverLevel, DamageSource damageSource) {
        return true;
    }
}
