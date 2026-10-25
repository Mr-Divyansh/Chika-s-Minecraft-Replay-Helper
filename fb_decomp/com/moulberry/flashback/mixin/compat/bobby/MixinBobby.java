/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.moulberry.mixinconstraints.annotations.IfModLoaded
 *  de.johni0702.minecraft.bobby.Bobby
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.server.IntegratedServer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Pseudo
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.compat.bobby;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import de.johni0702.minecraft.bobby.Bobby;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded(value="bobby")
@Pseudo
@Mixin(value={Bobby.class})
public class MixinBobby {
    @WrapOperation(method={"isEnabled"}, require=0, remap=false, at={@At(value="INVOKE", target="Lnet/minecraft/client/Minecraft;getSingleplayerServer()Lnet/minecraft/client/server/IntegratedServer;")})
    public IntegratedServer isEnabled_getSingleplayerServer(Minecraft instance, Operation<IntegratedServer> original) {
        IntegratedServer server = (IntegratedServer)original.call(new Object[]{instance});
        if (server instanceof ReplayServer) {
            return null;
        }
        return server;
    }
}

