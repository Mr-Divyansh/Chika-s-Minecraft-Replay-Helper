/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.impl.biome.modification.BiomeModificationImpl
 *  net.minecraft.core.RegistryAccess
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.compat.fabric;

import com.moulberry.flashback.Flashback;
import net.fabricmc.fabric.impl.biome.modification.BiomeModificationImpl;
import net.minecraft.core.RegistryAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={BiomeModificationImpl.class})
public class MixinFabricBiomeModificationImpl {
    @Inject(method={"finalizeWorldGen"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    public void finalizeWorldGen(RegistryAccess impl, CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            ci.cancel();
        }
    }
}

