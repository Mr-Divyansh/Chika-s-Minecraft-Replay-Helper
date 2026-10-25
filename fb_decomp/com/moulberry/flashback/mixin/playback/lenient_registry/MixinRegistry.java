/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.ModifyReturnValue
 *  com.mojang.serialization.DataResult
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.playback.lenient_registry;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.DataResult;
import com.moulberry.flashback.Flashback;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={Registry.class})
public interface MixinRegistry {
    @ModifyReturnValue(method={"lambda$referenceHolderWithLifecycle$0"}, at={@At(value="RETURN")})
    default public DataResult<Holder.Reference<?>> referenceHolderWithLifecycle_get(DataResult<Holder.Reference<?>> original) {
        if (original.isSuccess()) {
            return original;
        }
        if (Flashback.isInReplay()) {
            if (this == BuiltInRegistries.PARTICLE_TYPE) {
                return DataResult.success((Object)((Holder.Reference)BuiltInRegistries.PARTICLE_TYPE.get(0).get()));
            }
            MixinRegistry mixinRegistry = this;
            if (mixinRegistry instanceof DefaultedRegistry) {
                DefaultedRegistry defaultedRegistry = (DefaultedRegistry)mixinRegistry;
                return DataResult.success((Object)((Holder.Reference)defaultedRegistry.get(defaultedRegistry.getDefaultKey()).get()));
            }
        }
        return original;
    }
}

