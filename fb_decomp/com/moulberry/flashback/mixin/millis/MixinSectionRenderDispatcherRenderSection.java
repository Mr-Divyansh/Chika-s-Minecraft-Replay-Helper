/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  net.minecraft.client.renderer.chunk.SectionRenderDispatcher$RenderSection
 *  org.spongepowered.asm.mixin.Mixin
 */
package com.moulberry.flashback.mixin.millis;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moulberry.flashback.Flashback;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={SectionRenderDispatcher.RenderSection.class})
public class MixinSectionRenderDispatcherRenderSection {
    @WrapMethod(method={"getVisibility"})
    public float getVisibility(long now, Operation<Float> original) {
        if (Flashback.isInReplay()) {
            return 1.0f;
        }
        return ((Float)original.call(new Object[]{now})).floatValue();
    }
}

