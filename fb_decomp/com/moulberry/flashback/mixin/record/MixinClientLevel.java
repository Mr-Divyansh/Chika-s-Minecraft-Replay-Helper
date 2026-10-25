/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.network.ConnectionProtocol
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.record;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.record.Recorder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ClientLevel.class})
public class MixinClientLevel {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    @Final
    private BlockStatePredictionHandler blockStatePredictionHandler;

    @Inject(method={"setBlock"}, at={@At(value="HEAD")})
    public void setBlock(BlockPos blockPos, BlockState blockState, int i, int j, CallbackInfoReturnable<Boolean> cir) {
        Recorder recorder;
        if (this.blockStatePredictionHandler.isPredicting() && (recorder = Flashback.RECORDER) != null && !recorder.isPaused()) {
            recorder.writePacketAsync((Packet<?>)new ClientboundBlockUpdatePacket(blockPos.immutable(), blockState), ConnectionProtocol.PLAY);
        }
    }

    @Inject(method={"levelEvent"}, at={@At(value="HEAD")})
    public void levelEvent(Entity player, int type, BlockPos blockPos, int data, CallbackInfo ci) {
        if (Flashback.RECORDER != null && !Flashback.RECORDER.isPaused()) {
            Flashback.RECORDER.writeLevelEvent(type, blockPos, data, false);
        }
    }

    @Inject(method={"globalLevelEvent"}, at={@At(value="HEAD")})
    public void globalLevelEvent(int type, BlockPos blockPos, int data, CallbackInfo ci) {
        if (Flashback.RECORDER != null && !Flashback.RECORDER.isPaused()) {
            Flashback.RECORDER.writeLevelEvent(type, blockPos, data, true);
        }
    }

    @Inject(method={"playSeededSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V"}, at={@At(value="RETURN")})
    public void playSeededSoundEntity(@Nullable Entity player, Entity entity, Holder<SoundEvent> holder, SoundSource soundSource, float volume, float pitch, long seed, CallbackInfo ci) {
        if (player != null && player == this.minecraft.player && Flashback.RECORDER != null && !Flashback.RECORDER.isPaused()) {
            Flashback.RECORDER.writeEntitySound(holder, soundSource, entity, volume, pitch, seed);
        }
    }

    @Inject(method={"playSeededSound(Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V"}, at={@At(value="RETURN")})
    public void playSeededSoundNormal(@Nullable Entity player, double x, double y, double z, Holder<SoundEvent> holder, SoundSource soundSource, float volume, float pitch, long seed, CallbackInfo ci) {
        if (player != null && player == this.minecraft.player && Flashback.RECORDER != null && !Flashback.RECORDER.isPaused()) {
            Flashback.RECORDER.writeSound(holder, soundSource, x, y, z, volume, pitch, seed);
        }
    }
}

