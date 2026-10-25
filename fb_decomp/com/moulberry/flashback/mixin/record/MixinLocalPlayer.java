/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.level.Level
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.record;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.Flashback;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={LocalPlayer.class})
public class MixinLocalPlayer {
    @WrapOperation(method={"playSound"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V")})
    public void playSound(Level instance, double x, double y, double z, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch, boolean global, Operation<Void> original) {
        if (Flashback.RECORDER != null && !Flashback.RECORDER.isPaused()) {
            Optional builtinSoundEvent = BuiltInRegistries.SOUND_EVENT.get(soundEvent.location());
            Holder holder = builtinSoundEvent.isEmpty() ? Holder.direct((Object)soundEvent) : (Holder)builtinSoundEvent.get();
            Flashback.RECORDER.writeSound((Holder<SoundEvent>)holder, soundSource, x, y, z, volume, pitch, ThreadLocalRandom.current().nextLong());
        }
        original.call(new Object[]{instance, x, y, z, soundEvent, soundSource, Float.valueOf(volume), Float.valueOf(pitch), global});
    }
}

