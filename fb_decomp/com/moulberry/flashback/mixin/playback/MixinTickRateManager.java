/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.server.ServerTickRateManager
 *  net.minecraft.world.TickRateManager
 *  net.minecraft.world.entity.Entity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.playback;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.ext.MinecraftExt;
import com.moulberry.flashback.playback.ReplayPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.server.ServerTickRateManager;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={TickRateManager.class})
public abstract class MixinTickRateManager {
    @Shadow
    protected boolean runGameElements;
    @Unique
    private final boolean isServerTickRateManager = this instanceof ServerTickRateManager;

    @Shadow
    public abstract boolean runsNormally();

    @Inject(method={"tick"}, at={@At(value="RETURN")})
    public void tick(CallbackInfo ci) {
        if (Flashback.isInReplay() && this.isServerTickRateManager) {
            this.runGameElements = false;
        }
    }

    @Inject(method={"isEntityFrozen"}, at={@At(value="HEAD")}, cancellable=true)
    public void isEntityFrozen(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (Flashback.isInReplay()) {
            if (this.isServerTickRateManager) {
                cir.setReturnValue((Object)(!(entity instanceof ReplayPlayer) ? 1 : 0));
            } else if (entity == Minecraft.getInstance().player) {
                cir.setReturnValue((Object)((MinecraftExt)Minecraft.getInstance()).flashback$overridingLocalPlayerTimer());
            } else {
                cir.setReturnValue((Object)(!this.runsNormally() ? 1 : 0));
            }
        }
    }
}

