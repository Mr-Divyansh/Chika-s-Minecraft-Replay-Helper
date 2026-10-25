/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.WorldStem
 *  net.minecraft.server.packs.repository.PackRepository
 *  net.minecraft.world.level.gamerules.GameRules
 *  net.minecraft.world.level.storage.LevelStorageSource$LevelStorageAccess
 */
package com.moulberry.flashback.ext;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.LevelStorageSource;

public interface MinecraftExt {
    public void flashback$applyKeyframes();

    public void flashback$startReplayServer(LevelStorageSource.LevelStorageAccess var1, PackRepository var2, WorldStem var3, Optional<GameRules> var4, StartReplayServerInfo var5);

    public float flashback$getLocalPlayerPartialTick(float var1);

    public boolean flashback$overridingLocalPlayerTimer();

    public record StartReplayServerInfo(UUID playbackUUID, Path path) {
    }
}

