/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.player.RemotePlayer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.Vec3
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.movement;

import com.moulberry.flashback.EnhancedFlight;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.combo_options.MovementDirection;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Player.class}, priority=1100)
public abstract class MixinPlayerEntity
extends LivingEntity {
    protected MixinPlayerEntity() {
        super(null, null);
    }

    @Shadow
    public abstract void travel(Vec3 var1);

    @Shadow
    protected abstract float getFlyingSpeed();

    @Inject(method={"getFlyingSpeed"}, at={@At(value="HEAD")}, cancellable=true)
    public void getFlyingSpeed(CallbackInfoReturnable<Float> cir) {
        MixinPlayerEntity mixinPlayerEntity = this;
        if (mixinPlayerEntity instanceof LocalPlayer) {
            LocalPlayer player = (LocalPlayer)mixinPlayerEntity;
            if (Flashback.isInReplay()) {
                boolean doAirplaneFlight;
                FlashbackConfigV1 config = Flashback.getConfig();
                boolean bl = doAirplaneFlight = config.editorMovement.flightDirection == MovementDirection.CAMERA || (double)config.editorMovement.flightMomentum < 0.98;
                if (doAirplaneFlight && player.getAbilities().flying && !player.isPassenger() && !player.isFallFlying()) {
                    cir.setReturnValue((Object)Float.valueOf(EnhancedFlight.getFlightSpeed(player, config)));
                }
            }
        }
    }

    @Inject(method={"travel"}, at={@At(value="HEAD")}, cancellable=true)
    public void travel(Vec3 movementInput, CallbackInfo ci) {
        MixinPlayerEntity mixinPlayerEntity = this;
        if (mixinPlayerEntity instanceof LocalPlayer) {
            LocalPlayer player = (LocalPlayer)mixinPlayerEntity;
            if (Flashback.isInReplay()) {
                boolean doAirplaneFlight;
                FlashbackConfigV1 config = Flashback.getConfig();
                boolean bl = doAirplaneFlight = config.editorMovement.flightDirection == MovementDirection.CAMERA || (double)config.editorMovement.flightMomentum < 0.98 || config.editorMovement.flightLockX || config.editorMovement.flightLockY || config.editorMovement.flightLockZ;
                if (doAirplaneFlight && player.getAbilities().flying && !player.isPassenger() && !player.isFallFlying()) {
                    EnhancedFlight.doFlight(player, config, movementInput, this.getFlyingSpeed(), x$0 -> super.travel(x$0));
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method={"getName"}, at={@At(value="HEAD")}, cancellable=true)
    public void getName(CallbackInfoReturnable<Component> cir) {
        String nameOverride;
        EditorState editorState;
        if (this instanceof RemotePlayer && (editorState = EditorStateManager.getCurrent()) != null && (nameOverride = editorState.nameOverride.get(this.uuid)) != null) {
            cir.setReturnValue((Object)Component.literal((String)nameOverride));
        }
    }
}

