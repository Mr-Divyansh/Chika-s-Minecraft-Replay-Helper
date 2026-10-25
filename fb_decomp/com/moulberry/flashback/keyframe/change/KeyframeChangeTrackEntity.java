/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Vector3d
 *  org.joml.Vector3dc
 */
package com.moulberry.flashback.keyframe.change;

import com.moulberry.flashback.Interpolation;
import com.moulberry.flashback.combo_options.TrackingBodyPart;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public record KeyframeChangeTrackEntity(UUID target, TrackingBodyPart trackingBodyPart, float yawOffset, float pitchOffset, Vector3d positionOffset, Vector3d viewOffset, float roll) implements KeyframeChange
{
    @Override
    public void apply(KeyframeHandler keyframeHandler) {
        float yaw;
        Vec3 limbPosition;
        Minecraft minecraft = keyframeHandler.getMinecraft();
        if (minecraft == null) {
            return;
        }
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        Entity entity = (Entity)level.getEntities().get(this.target);
        if (entity == null) {
            return;
        }
        float partialTick = minecraft.deltaTracker.getGameTimeDeltaPartialTick(true);
        float pitch = switch (this.trackingBodyPart) {
            case TrackingBodyPart.HEAD -> {
                LivingEntity livingEntity;
                limbPosition = entity.getEyePosition(partialTick);
                if (entity instanceof LivingEntity) {
                    livingEntity = (LivingEntity)entity;
                    yaw = Mth.rotLerp((float)partialTick, (float)livingEntity.yHeadRotO, (float)livingEntity.yHeadRot);
                    yield livingEntity.getViewXRot(partialTick);
                }
                yaw = entity.getYHeadRot();
                yield entity.getXRot();
            }
            case TrackingBodyPart.BODY -> {
                LivingEntity livingEntity;
                limbPosition = entity.getPosition(partialTick).add(0.0, (double)(entity.getBbHeight() * 0.5f), 0.0);
                if (entity instanceof LivingEntity) {
                    livingEntity = (LivingEntity)entity;
                    yaw = Mth.rotLerp((float)partialTick, (float)livingEntity.yBodyRotO, (float)livingEntity.yBodyRot);
                } else {
                    yaw = entity.getYRot();
                }
                yield 0.0f;
            }
            case TrackingBodyPart.ROOT -> {
                limbPosition = entity.getPosition(partialTick);
                yaw = 0.0f;
                yield 0.0f;
            }
            default -> throw new UnsupportedOperationException();
        };
        pitch *= (float)Math.cos(Math.toRadians(this.yawOffset));
        Vector3d offset = new Vector3d((Vector3dc)this.viewOffset);
        offset.rotateX(Math.toRadians(-(pitch += this.pitchOffset)));
        offset.rotateY(Math.toRadians(180.0f - (yaw += this.yawOffset)));
        Vector3d position = new Vector3d(limbPosition.x + this.positionOffset.x + offset.x, limbPosition.y + this.positionOffset.y + offset.y, limbPosition.z + this.positionOffset.z + offset.z);
        LocalPlayer player = minecraft.player;
        if (player != null) {
            position.y -= (double)player.getEyeHeight();
        }
        keyframeHandler.applyCameraPosition(position, yaw, pitch, this.roll);
    }

    @Override
    public KeyframeChange interpolate(KeyframeChange to, double amount) {
        KeyframeChangeTrackEntity other = (KeyframeChangeTrackEntity)to;
        return new KeyframeChangeTrackEntity(amount < 0.5 ? this.target : other.target, amount < 0.5 ? this.trackingBodyPart : other.trackingBodyPart, (float)Interpolation.linear((double)this.yawOffset, (double)other.yawOffset, amount), (float)Interpolation.linear((double)this.pitchOffset, (double)other.pitchOffset, amount), this.positionOffset.lerp((Vector3dc)other.positionOffset, amount, new Vector3d()), this.viewOffset.lerp((Vector3dc)other.viewOffset, amount, new Vector3d()), (float)Interpolation.linearAngle((double)this.roll, (double)other.roll, amount));
    }
}

