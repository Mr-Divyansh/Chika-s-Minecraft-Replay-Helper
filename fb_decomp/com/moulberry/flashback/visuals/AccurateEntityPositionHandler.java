/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  net.minecraft.client.DeltaTracker
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector2f
 *  org.joml.Vector3d
 */
package com.moulberry.flashback.visuals;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.action.PositionAndAngle;
import com.moulberry.flashback.packet.FlashbackAccurateEntityPosition;
import com.moulberry.flashback.playback.ReplayServer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector3d;

public class AccurateEntityPositionHandler {
    private static Int2ObjectMap<List<PositionAndAngle>> currentData = null;
    private static Int2ObjectMap<List<PositionAndAngle>> pendingData = null;

    public static void tick() {
        currentData = pendingData;
        pendingData = null;
    }

    public static void update(FlashbackAccurateEntityPosition data) {
        if (pendingData == null) {
            pendingData = new Int2ObjectOpenHashMap();
        }
        pendingData.put(data.entityId(), data.positionAndAngles());
    }

    @Nullable
    public static Vector2f getAccurateRotation(Entity entity, float partialTick) {
        if (currentData != null && currentData.containsKey(entity.getId())) {
            List positionAndAngles = (List)currentData.get(entity.getId());
            float amount = partialTick * (float)(positionAndAngles.size() - 1);
            int floorAmount = (int)amount;
            PositionAndAngle floorPosition = (PositionAndAngle)positionAndAngles.get(floorAmount);
            int ceilAmount = floorAmount + 1;
            if (ceilAmount >= positionAndAngles.size()) {
                return new Vector2f(floorPosition.pitch(), floorPosition.yaw());
            }
            PositionAndAngle ceilPosition = (PositionAndAngle)positionAndAngles.get(ceilAmount);
            float partialAmount = amount - (float)floorAmount;
            float yaw = floorPosition.yaw() + Mth.wrapDegrees((float)(ceilPosition.yaw() - floorPosition.yaw())) * partialAmount;
            float pitch = floorPosition.pitch() + Mth.wrapDegrees((float)(ceilPosition.pitch() - floorPosition.pitch())) * partialAmount;
            return new Vector2f(Mth.wrapDegrees((float)pitch), Mth.wrapDegrees((float)yaw));
        }
        return null;
    }

    @Nullable
    public static Vector3d getAccuratePosition(Entity entity, float partialTick) {
        if (entity.isPassenger() || !Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            return null;
        }
        if (currentData != null && currentData.containsKey(entity.getId())) {
            List positionAndAngles = (List)currentData.get(entity.getId());
            float amount = partialTick * (float)(positionAndAngles.size() - 1);
            int floorAmount = (int)amount;
            PositionAndAngle floorPosition = (PositionAndAngle)positionAndAngles.get(floorAmount);
            int ceilAmount = floorAmount + 1;
            if (ceilAmount >= positionAndAngles.size()) {
                return new Vector3d(floorPosition.x(), floorPosition.y(), floorPosition.z());
            }
            PositionAndAngle ceilPosition = (PositionAndAngle)positionAndAngles.get(ceilAmount);
            float partialAmount = amount - (float)floorAmount;
            double x = floorPosition.x() + (ceilPosition.x() - floorPosition.x()) * (double)partialAmount;
            double y = floorPosition.y() + (ceilPosition.y() - floorPosition.y()) * (double)partialAmount;
            double z = floorPosition.z() + (ceilPosition.z() - floorPosition.z()) * (double)partialAmount;
            return new Vector3d(x, y, z);
        }
        return null;
    }

    public static void apply(ClientLevel level, DeltaTracker deltaTracker) {
        if (currentData == null || level == null) {
            return;
        }
        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(true);
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer != null) {
            for (Int2ObjectMap.Entry entry : currentData.int2ObjectEntrySet()) {
                Entity entity = level.getEntity(entry.getIntKey());
                if (entity == null) continue;
                List positionAndAngles = (List)entry.getValue();
                float amount = partialTick * (float)(positionAndAngles.size() - 1);
                int floorAmount = (int)amount;
                PositionAndAngle floorPosition = (PositionAndAngle)positionAndAngles.get(floorAmount);
                int ceilAmount = floorAmount + 1;
                if (ceilAmount >= positionAndAngles.size()) {
                    AccurateEntityPositionHandler.applyPosition(entity, floorPosition.x(), floorPosition.y(), floorPosition.z(), floorPosition.yaw(), floorPosition.pitch());
                    continue;
                }
                PositionAndAngle ceilPosition = (PositionAndAngle)positionAndAngles.get(ceilAmount);
                float partialAmount = amount - (float)floorAmount;
                double x = floorPosition.x() + (ceilPosition.x() - floorPosition.x()) * (double)partialAmount;
                double y = floorPosition.y() + (ceilPosition.y() - floorPosition.y()) * (double)partialAmount;
                double z = floorPosition.z() + (ceilPosition.z() - floorPosition.z()) * (double)partialAmount;
                float yaw = floorPosition.yaw() + Mth.wrapDegrees((float)(ceilPosition.yaw() - floorPosition.yaw())) * partialAmount;
                float pitch = floorPosition.pitch() + Mth.wrapDegrees((float)(ceilPosition.pitch() - floorPosition.pitch())) * partialAmount;
                AccurateEntityPositionHandler.applyPosition(entity, x, y, z, Mth.wrapDegrees((float)yaw), Mth.wrapDegrees((float)pitch));
            }
        }
    }

    private static void applyPosition(Entity entity, double x, double y, double z, float yaw, float pitch) {
        if (!entity.isPassenger() && Minecraft.getInstance().getCameraEntity() == entity && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            entity.snapTo(x, y, z, yaw, pitch);
        }
        entity.setYRot(yaw);
        entity.setXRot(pitch);
        entity.setYHeadRot(yaw);
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            livingEntity.lerpHeadSteps = 0;
            livingEntity.yHeadRotO = livingEntity.yHeadRot;
            livingEntity.yRotO = livingEntity.getYRot();
            livingEntity.xRotO = livingEntity.getXRot();
        }
    }
}

