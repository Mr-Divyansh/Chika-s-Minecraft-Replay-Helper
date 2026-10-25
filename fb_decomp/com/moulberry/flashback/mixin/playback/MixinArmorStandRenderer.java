/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.client.renderer.entity.ArmorStandRenderer
 *  net.minecraft.client.renderer.entity.state.ArmorStandRenderState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.playback;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.Flashback;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={ArmorStandRenderer.class})
public class MixinArmorStandRenderer {
    @WrapOperation(method={"setupRotations(Lnet/minecraft/client/renderer/entity/state/ArmorStandRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;FF)V"}, at={@At(value="FIELD", target="Lnet/minecraft/client/renderer/entity/state/ArmorStandRenderState;wiggle:F")})
    public float setupRotations_wiggle(ArmorStandRenderState instance, Operation<Float> original) {
        if (Flashback.isInReplay()) {
            return 0.0f;
        }
        return ((Float)original.call(new Object[]{instance})).floatValue();
    }
}

