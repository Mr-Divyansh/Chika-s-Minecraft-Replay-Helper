/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.client.resources.SkinManager
 */
package com.moulberry.flashback.exporting;

import com.moulberry.flashback.Flashback;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.SkinManager;

public class PerfectFrames {
    private static boolean isEnabled = false;
    static List<Consumer<Boolean>> frexFlawlessFrames = new ArrayList<Consumer<Boolean>>();
    private static final Set<UUID> ignoreSkinLoading = new HashSet<UUID>();

    public static void enable() {
        isEnabled = true;
        ignoreSkinLoading.clear();
        for (Consumer<Boolean> frexFlawlessFramesConsumer : frexFlawlessFrames) {
            frexFlawlessFramesConsumer.accept(true);
        }
    }

    public static void disable() {
        isEnabled = false;
        ignoreSkinLoading.clear();
        for (Consumer<Boolean> frexFlawlessFramesConsumer : frexFlawlessFrames) {
            frexFlawlessFramesConsumer.accept(false);
        }
    }

    public static boolean isEnabled() {
        return isEnabled;
    }

    public static void waitUntilFrameReady() {
        long start = System.currentTimeMillis();
        while (!PerfectFrames.isFrameReady(start)) {
            LockSupport.parkNanos("waiting for frame to be ready", 100000L);
            Minecraft.getInstance().runAllTasks();
        }
    }

    private static boolean isFrameReady(long start) {
        block4: {
            long current = System.currentTimeMillis();
            boolean forceFrameReady = current - start > 15000L;
            ClientLevel level = Minecraft.getInstance().level;
            SkinManager skinManager = Minecraft.getInstance().getSkinManager();
            if (level == null) break block4;
            if (forceFrameReady) {
                Flashback.LOGGER.error("Waiting for skin loading took too long, ignoring...");
                for (AbstractClientPlayer player : level.players()) {
                    PlayerInfo playerInfo;
                    if (player == Minecraft.getInstance().player || (playerInfo = player.getPlayerInfo()) == null || skinManager.get(playerInfo.getProfile()).isDone()) continue;
                    Flashback.LOGGER.error("Took too long to load skin for {}", (Object)player.getUUID());
                    ignoreSkinLoading.add(player.getUUID());
                }
            } else {
                for (AbstractClientPlayer player : level.players()) {
                    PlayerInfo playerInfo;
                    if (player == Minecraft.getInstance().player || ignoreSkinLoading.contains(player.getUUID()) || (playerInfo = player.getPlayerInfo()) == null || skinManager.get(playerInfo.getProfile()).isDone()) continue;
                    return false;
                }
            }
        }
        return true;
    }
}

