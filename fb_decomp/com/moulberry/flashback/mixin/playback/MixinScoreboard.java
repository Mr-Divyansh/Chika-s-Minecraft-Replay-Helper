/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.numbers.NumberFormat
 *  net.minecraft.world.scores.Objective
 *  net.minecraft.world.scores.Scoreboard
 *  net.minecraft.world.scores.criteria.ObjectiveCriteria
 *  net.minecraft.world.scores.criteria.ObjectiveCriteria$RenderType
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.playback;

import com.moulberry.flashback.Flashback;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Scoreboard.class})
public abstract class MixinScoreboard {
    @Shadow
    @Final
    private Object2ObjectMap<String, Objective> objectivesByName;

    @Shadow
    public abstract void removeObjective(Objective var1);

    @Inject(method={"addObjective"}, at={@At(value="HEAD")})
    public void addObjective(String string, ObjectiveCriteria objectiveCriteria, Component component, ObjectiveCriteria.RenderType renderType, boolean bl, NumberFormat numberFormat, CallbackInfoReturnable<Objective> cir) {
        if (Flashback.isInReplay() && this.objectivesByName.containsKey((Object)string)) {
            this.removeObjective((Objective)this.objectivesByName.get((Object)string));
        }
    }
}

