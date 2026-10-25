/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.llamalad7.mixinextras.sugar.Local
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
 *  net.minecraft.network.syncher.SynchedEntityData$DataValue
 *  net.minecraft.server.level.ServerEntity
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  org.jspecify.annotations.Nullable
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 */
package com.moulberry.flashback.mixin.replay_server;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.playback.ReplayServer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value={ServerEntity.class})
public class MixinServerEntity {
    @Shadow
    public @Nullable List<// Could not load outer class - annotation placement on inner may be incorrect
    SynchedEntityData.DataValue<?>> trackedDataValues;
    @Shadow
    public Entity entity;

    @ModifyVariable(method={"<init>"}, at=@At(value="HEAD"), argsOnly=true)
    private static int init_modifyUpdateInterval(int updateInterval, @Local(argsOnly=true) ServerLevel level) {
        if (updateInterval < 20 && level != null && level.getServer() instanceof ReplayServer) {
            return 1;
        }
        return updateInterval;
    }

    @WrapOperation(method={"addPairing"}, at={@At(value="INVOKE", target="Lnet/minecraft/server/level/ServerEntity;sendPairingData(Lnet/minecraft/server/level/ServerPlayer;Ljava/util/function/Consumer;)V")})
    public void addPairing_sendPairingData(ServerEntity instance, ServerPlayer serverPlayer, Consumer<Packet<ClientGamePacketListener>> consumer, Operation<Void> original) {
        if (Flashback.isInReplay()) {
            ArrayList delayed = new ArrayList();
            original.call(new Object[]{instance, serverPlayer, packet -> {
                if (packet instanceof ClientboundSetEntityDataPacket) {
                    ClientboundSetEntityDataPacket setEntityDataPacket = (ClientboundSetEntityDataPacket)packet;
                    delayed.add(setEntityDataPacket);
                } else {
                    consumer.accept((Packet<ClientGamePacketListener>)packet);
                }
            }});
            for (ClientboundSetEntityDataPacket setEntityDataPacket : delayed) {
                consumer.accept((Packet<ClientGamePacketListener>)setEntityDataPacket);
            }
        } else {
            original.call(new Object[]{instance, serverPlayer, consumer});
        }
    }
}

