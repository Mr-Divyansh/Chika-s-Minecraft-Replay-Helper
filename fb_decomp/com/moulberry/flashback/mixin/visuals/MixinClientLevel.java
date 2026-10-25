/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.visuals;

import com.moulberry.flashback.Flashback;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ClientLevel.class})
public class MixinClientLevel {
    @Inject(method={"tickNonPassenger"}, at={@At(value="RETURN")}, require=0)
    public void tickNonPassengerEnd(Entity entity, CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            entity.xRotO = Mth.wrapDegrees((float)(entity.xRotO - entity.getXRot())) + entity.getXRot();
            entity.yRotO = Mth.wrapDegrees((float)(entity.yRotO - entity.getYRot())) + entity.getYRot();
        }
    }

    @Inject(method={"tickPassenger"}, at={@At(value="RETURN")}, require=0)
    public void tickPassengerEnd(Entity vehicle, Entity entity, CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            entity.xRotO = Mth.wrapDegrees((float)(entity.xRotO - entity.getXRot())) + entity.getXRot();
            entity.yRotO = Mth.wrapDegrees((float)(entity.yRotO - entity.getYRot())) + entity.getYRot();
        }
    }
}

