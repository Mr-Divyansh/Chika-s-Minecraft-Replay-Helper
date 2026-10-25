/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.client.player.RemotePlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.phys.Vec3
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.playback;

import com.mojang.authlib.GameProfile;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.ext.RemotePlayerExt;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RemotePlayer.class})
public class MixinRemotePlayer
extends AbstractClientPlayer
implements RemotePlayerExt {
    @Unique
    private boolean wasSwinging = false;
    @Unique
    private float xBobO = 0.0f;
    @Unique
    private float xBob = 0.0f;
    @Unique
    private float yBobO = 0.0f;
    @Unique
    private float yBob = 0.0f;
    @Unique
    private Vec3 lastPosition = null;

    private MixinRemotePlayer(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(method={"aiStep"}, at={@At(value="RETURN")})
    public void aiStep(CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            if (!this.wasSwinging && this.swinging) {
                this.resetAttackStrengthTicker();
            }
            this.wasSwinging = this.swinging;
            this.xBobO = this.xBob;
            this.xBob += Mth.wrapDegrees((float)(this.getXRot() - this.xBob)) * 0.5f;
            this.yBobO = this.yBob;
            this.yBob += Mth.wrapDegrees((float)(this.getYRot() - this.yBob)) * 0.5f;
            if (this.lastPosition != null && this.avatarState().getInterpolatedWalkDistance(0.0f) == this.avatarState().getInterpolatedWalkDistance(1.0f)) {
                double dx = this.lastPosition.x - this.position().x;
                double dz = this.lastPosition.z - this.position().z;
                this.addWalkedDistance((float)Math.sqrt(dx * dx + dz * dz) * 0.6f);
            }
            this.lastPosition = this.position();
        }
    }

    @Override
    public float flashback$getXBob(float partialTick) {
        return Mth.lerp((float)partialTick, (float)this.xBobO, (float)this.xBob);
    }

    @Override
    public float flashback$getYBob(float partialTick) {
        return Mth.lerp((float)partialTick, (float)this.yBobO, (float)this.yBob);
    }
}

