/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.contextualbar.ExperienceBarRenderer
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.visuals;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.Flashback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.contextualbar.ExperienceBarRenderer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={ExperienceBarRenderer.class})
public class MixinExperienceBarRenderer {
    @Shadow
    @Final
    private Minecraft minecraft;

    @WrapOperation(method={"extractBackground"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/player/LocalPlayer;getXpNeededForNextLevel()I")}, require=0)
    public int renderExperienceBar_getXpNeededForNextLevel(LocalPlayer instance, Operation<Integer> original) {
        Entity entity;
        if (Flashback.isInReplay() && (entity = this.minecraft.getCameraEntity()) instanceof Player) {
            Player player = (Player)entity;
            return player.getXpNeededForNextLevel();
        }
        return (Integer)original.call(new Object[]{instance});
    }

    @WrapOperation(method={"extractBackground"}, at={@At(value="FIELD", target="Lnet/minecraft/client/player/LocalPlayer;experienceProgress:F")}, require=0)
    public float renderExperienceBar_experienceProgress(LocalPlayer instance, Operation<Float> original) {
        Entity entity;
        if (Flashback.isInReplay() && (entity = this.minecraft.getCameraEntity()) instanceof Player) {
            Player player = (Player)entity;
            return player.experienceProgress;
        }
        return ((Float)original.call(new Object[]{instance})).floatValue();
    }
}

