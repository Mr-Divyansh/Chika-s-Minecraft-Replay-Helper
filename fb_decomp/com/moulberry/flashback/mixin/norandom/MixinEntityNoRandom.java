/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.norandom;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.exporting.ExportJob;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={Entity.class})
public class MixinEntityNoRandom {
    @WrapOperation(method={"<init>"}, at={@At(value="INVOKE", target="Lnet/minecraft/util/RandomSource;create()Lnet/minecraft/util/RandomSource;")})
    public RandomSource initRandom(Operation<RandomSource> original) {
        ExportJob exportJob = Flashback.EXPORT_JOB;
        if (exportJob != null && exportJob.getSettings().resetRng()) {
            return RandomSource.create((long)exportJob.getInitialEntitySeedForCurrentClientTick());
        }
        return (RandomSource)original.call(new Object[0]);
    }
}

