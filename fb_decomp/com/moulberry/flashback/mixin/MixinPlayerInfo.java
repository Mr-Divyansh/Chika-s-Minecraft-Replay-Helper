/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.Flashback;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={PlayerInfo.class})
public class MixinPlayerInfo {
    @WrapOperation(method={"createSkinLookup"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/Minecraft;isLocalPlayer(Ljava/util/UUID;)Z")})
    private static boolean createSkinLookup_isLocalPlayer(Minecraft instance, UUID uuid, Operation<Boolean> original) {
        if (Flashback.isInReplay()) {
            return true;
        }
        return (Boolean)original.call(new Object[]{instance, uuid});
    }
}

