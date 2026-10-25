/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.client.MouseHandler
 *  net.minecraft.client.OptionInstance
 *  net.minecraft.client.OptionInstance$UnitDouble
 *  net.minecraft.client.OptionInstance$ValueSet
 *  net.minecraft.client.Options
 *  net.minecraft.network.chat.Component
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.visuals;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.Utils;
import com.moulberry.flashback.ext.OptionsExt;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={MouseHandler.class})
public class MixinMouseHandler {
    @WrapOperation(method={"turnPlayer"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/Options;sensitivity()Lnet/minecraft/client/OptionInstance;")})
    public OptionInstance<Double> turnPlayer_sensitivity(Options instance, Operation<OptionInstance<Double>> original) {
        OptionInstance sensitivity = (OptionInstance)original.call(new Object[]{instance});
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && editorState.replayVisuals.overrideFov) {
            float newFov = editorState.replayVisuals.overrideFovAmount;
            if ((double)newFov > 20.0) {
                return (OptionInstance)original.call(new Object[]{instance});
            }
            float oldFov = ((OptionsExt)instance).flashback$getOriginalFov();
            if (newFov < oldFov) {
                float newFocalLength = Utils.fovToFocalLength(newFov);
                float oldFocalLength = Utils.fovToFocalLength(oldFov);
                float sensitivityMultiplier = oldFocalLength / newFocalLength;
                double newSensitivity = (Double)sensitivity.get() * (double)sensitivityMultiplier;
                sensitivity = new OptionInstance("options.sensitivity", OptionInstance.noTooltip(), (component, double_) -> {
                    if (double_ == 0.0) {
                        return Options.genericValueLabel((Component)component, (Component)Component.translatable((String)"options.sensitivity.min"));
                    }
                    if (double_ == 1.0) {
                        return Options.genericValueLabel((Component)component, (Component)Component.translatable((String)"options.sensitivity.max"));
                    }
                    return Component.translatable((String)"options.percent_value", (Object[])new Object[]{component, (int)(double_ * 200.0)});
                }, (OptionInstance.ValueSet)OptionInstance.UnitDouble.INSTANCE, (Object)newSensitivity, double_ -> {});
            }
        }
        return sensitivity;
    }
}

