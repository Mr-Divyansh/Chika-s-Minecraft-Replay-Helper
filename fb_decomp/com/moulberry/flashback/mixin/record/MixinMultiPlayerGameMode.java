/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.client.multiplayer.MultiPlayerGameMode
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.SoundEngine$PlayResult
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.Identifier
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.record;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.Flashback;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={MultiPlayerGameMode.class})
public class MixinMultiPlayerGameMode {
    @WrapOperation(method={"continueDestroyBlock"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/sounds/SoundManager;play(Lnet/minecraft/client/resources/sounds/SoundInstance;)Lnet/minecraft/client/sounds/SoundEngine$PlayResult;")})
    public SoundEngine.PlayResult playBreakingSound(SoundManager instance, SoundInstance soundInstance, Operation<SoundEngine.PlayResult> original) {
        SoundEngine.PlayResult result = (SoundEngine.PlayResult)original.call(new Object[]{instance, soundInstance});
        if (Flashback.RECORDER != null && !Flashback.RECORDER.isPaused()) {
            if (soundInstance.getSound() == null) {
                return null;
            }
            Optional builtinSoundEvent = BuiltInRegistries.SOUND_EVENT.get(soundInstance.getIdentifier());
            Holder holder = builtinSoundEvent.isEmpty() ? Holder.direct((Object)SoundEvent.createVariableRangeEvent((Identifier)soundInstance.getIdentifier())) : (Holder)builtinSoundEvent.get();
            SoundSource soundSource = soundInstance.getSource();
            float volume = soundInstance.getVolume();
            float pitch = soundInstance.getPitch();
            double x = soundInstance.getX();
            double y = soundInstance.getY();
            double z = soundInstance.getZ();
            Flashback.RECORDER.writeSound((Holder<SoundEvent>)holder, soundSource, x, y, z, volume, pitch, ThreadLocalRandom.current().nextLong());
        }
        return result;
    }
}

