/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Vector3f
 */
package com.moulberry.flashback;

import com.moulberry.flashback.combo_options.MovementDirection;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class EnhancedFlight {
    public static float getFlightSpeed(LocalPlayer player, FlashbackConfigV1 config) {
        float momentum = (float)Math.sqrt(config.editorMovement.flightMomentum);
        float sprintMult = Mth.lerp((float)momentum, (float)2.5f, (float)2.0f);
        float baseMult = Mth.lerp((float)momentum, (float)10.0f, (float)1.0f);
        float sprintMultiplier = Minecraft.getInstance().options.keySprint.isDown() || player.isSprinting() ? sprintMult : 1.0f;
        return player.getAbilities().getFlyingSpeed() * sprintMultiplier * baseMult;
    }

    public static void doFlight(LocalPlayer player, FlashbackConfigV1 config, Vec3 movementInput, float flyingSpeed, Consumer<Vec3> superTravel) {
        boolean cameraDirection = config.editorMovement.flightDirection == MovementDirection.CAMERA;
        boolean originalHasNoGravity = player.isNoGravity();
        double oldX = player.getX();
        double oldY = player.getY();
        double oldZ = player.getZ();
        if (cameraDirection) {
            double sin = -Math.sin(Math.toRadians(player.getXRot()));
            double cos = Math.cos(Math.toRadians(player.getXRot()));
            movementInput = new Vec3(movementInput.x, sin * movementInput.z, cos * movementInput.z);
            player.setNoGravity(true);
        }
        float movementY = 0.0f;
        if (player.input.keyPresses.shift()) {
            movementY -= 1.0f;
        }
        if (player.input.keyPresses.jump()) {
            movementY += 1.0f;
        }
        if (movementY != 0.0f) {
            player.move(MoverType.SELF, new Vec3(0.0, (double)(flyingSpeed * movementY) * 0.98, 0.0));
            if (movementY < 0.0f && !Minecraft.getInstance().gameMode.isSpectator()) {
                double expectedY = oldY + (double)(flyingSpeed * movementY) * 0.98;
                if (Math.abs(player.getY() - expectedY) > 1.0E-5) {
                    player.setOnGround(true);
                    player.getAbilities().flying = false;
                    player.onUpdateAbilities();
                }
            }
        }
        double beforeY = player.getDeltaMovement().y;
        superTravel.accept(movementInput);
        double momentum = Math.cbrt(config.editorMovement.flightMomentum);
        Vector3f deltaMovement = player.getDeltaMovement().toVector3f();
        if (config.editorMovement.flightLockX) {
            player.setPos(oldX, player.getY(), player.getZ());
            deltaMovement.x = 0.0f;
        }
        if (config.editorMovement.flightLockY) {
            player.setPos(player.getX(), oldY, player.getZ());
            deltaMovement.y = 0.0f;
        }
        if (config.editorMovement.flightLockZ) {
            player.setPos(player.getX(), player.getY(), oldZ);
            deltaMovement.z = 0.0f;
        }
        if (cameraDirection) {
            player.setDeltaMovement((double)deltaMovement.x * momentum, (double)deltaMovement.y * 0.92857 * momentum, (double)deltaMovement.z * momentum);
        } else {
            player.setDeltaMovement((double)deltaMovement.x * momentum, beforeY * 0.6 * momentum, (double)deltaMovement.z * momentum);
        }
        if (cameraDirection && movementY >= 0.0f) {
            player.setOnGround(false);
        }
        player.setNoGravity(originalHasNoGravity);
        player.resetFallDistance();
        if (player.isFallFlying()) {
            player.stopFallFlying();
        }
    }
}

