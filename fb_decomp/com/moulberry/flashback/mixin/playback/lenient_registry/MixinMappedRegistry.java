/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.HolderSet$Named
 *  net.minecraft.core.MappedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.tags.TagKey
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.playback.lenient_registry;

import com.moulberry.flashback.Flashback;
import java.util.List;
import java.util.Map;
import net.minecraft.core.HolderSet;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={MappedRegistry.class})
public abstract class MixinMappedRegistry {
    @Shadow
    private boolean frozen;
    @Shadow
    @Final
    private Map<TagKey, HolderSet.Named> frozenTags;

    @Inject(method={"freeze"}, at={@At(value="HEAD")})
    public void freeze(CallbackInfoReturnable<Registry> cir) {
        if (!this.frozen && Flashback.isInReplay()) {
            for (Map.Entry<TagKey, HolderSet.Named> entry : this.frozenTags.entrySet()) {
                if (entry.getValue().isBound()) continue;
                entry.getValue().bind(List.of());
            }
        }
    }
}

