/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.core.Registry$PendingTags
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.playback;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.playback.ReplayServer;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={MinecraftServer.class})
public class MixinMinecraftServer {
    @WrapOperation(method={"lambda$reloadResources$1"}, at={@At(value="INVOKE", target="Lnet/minecraft/tags/TagLoader;loadTagsForExistingRegistries(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/core/RegistryAccess;)Ljava/util/List;")})
    public List<Registry.PendingTags<?>> loadTagsForExistingRegistries(ResourceManager resourceManager, RegistryAccess registryAccess, Operation<List<Registry.PendingTags<?>>> original) {
        MixinMinecraftServer mixinMinecraftServer = this;
        if (mixinMinecraftServer instanceof ReplayServer) {
            ReplayServer replayServer = (ReplayServer)((Object)mixinMinecraftServer);
            if (replayServer.overridePendingTags != null) {
                return replayServer.overridePendingTags;
            }
        }
        return (List)original.call(new Object[]{resourceManager, registryAccess});
    }
}

