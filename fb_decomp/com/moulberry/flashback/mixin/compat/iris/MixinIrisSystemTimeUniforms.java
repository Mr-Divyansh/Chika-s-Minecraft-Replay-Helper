/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.moulberry.mixinconstraints.annotations.IfModLoaded
 *  net.irisshaders.iris.uniforms.SystemTimeUniforms$Timer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Pseudo
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.compat.iris;

import com.moulberry.flashback.Flashback;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import net.irisshaders.iris.uniforms.SystemTimeUniforms;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@IfModLoaded(value="iris")
@Pseudo
@Mixin(value={SystemTimeUniforms.Timer.class}, remap=false)
public class MixinIrisSystemTimeUniforms {
    @Inject(method={"getFrameTimeCounter"}, at={@At(value="HEAD")}, require=0, cancellable=true)
    public void getFrameTimeCounter(CallbackInfoReturnable<Float> cir) {
        if (Flashback.isExporting()) {
            double currentTick = Flashback.EXPORT_JOB.getCurrentTickDouble();
            cir.setReturnValue((Object)Float.valueOf((float)(currentTick / 20.0)));
        }
    }

    @Inject(method={"getLastFrameTime"}, at={@At(value="HEAD")}, require=0, cancellable=true)
    public void getLastFrameTime(CallbackInfoReturnable<Float> cir) {
        if (Flashback.isExporting()) {
            cir.setReturnValue((Object)Float.valueOf((float)(1.0 / Flashback.EXPORT_JOB.getSettings().framerate())));
        }
    }
}

