/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.playback;

import com.moulberry.flashback.Flashback;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LivingEntity.class})
public class MixinLivingEntity {
    @Inject(method={"updateDirtyEffects"}, at={@At(value="HEAD")}, cancellable=true)
    public void updateDirtyEffects(CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            ci.cancel();
        }
    }

    @Inject(method={"updateInvisibilityStatus"}, at={@At(value="HEAD")}, cancellable=true)
    public void updateInvisibilityStatus(CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            ci.cancel();
        }
    }

    @Inject(method={"updateGlowingStatus"}, at={@At(value="HEAD")}, cancellable=true)
    public void updateGlowingStatus(CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            ci.cancel();
        }
    }
}

