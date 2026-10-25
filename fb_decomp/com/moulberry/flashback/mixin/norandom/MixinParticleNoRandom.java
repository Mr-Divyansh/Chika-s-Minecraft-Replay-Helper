/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.util.RandomSource
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.norandom;

import com.moulberry.flashback.Flashback;
import java.util.Random;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Particle.class})
public class MixinParticleNoRandom {
    @Shadow
    @Final
    protected RandomSource random;

    @Inject(method={"<init>(Lnet/minecraft/client/multiplayer/ClientLevel;DDD)V"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/particle/Particle;setSize(FF)V")})
    private void newParticle(ClientLevel clientLevel, double d, double e, double f, CallbackInfo ci) {
        Random random;
        if (Flashback.isExporting() && (random = Flashback.EXPORT_JOB.getParticleRandom()) != null) {
            this.random.setSeed(random.nextLong());
        }
    }
}

