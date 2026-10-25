/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.bawnorton.mixinsquared.TargetHandler
 *  com.moulberry.mixinconstraints.annotations.IfModLoaded
 *  de.johni0702.minecraft.bobby.BobbyConfig
 *  net.minecraft.client.server.IntegratedServer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Pseudo
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package com.moulberry.flashback.mixin.compat.bobby;

import com.bawnorton.mixinsquared.TargetHandler;
import com.moulberry.flashback.Flashback;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import de.johni0702.minecraft.bobby.BobbyConfig;
import net.minecraft.client.server.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@IfModLoaded(value="bobby")
@Pseudo
@Mixin(value={IntegratedServer.class}, priority=1500)
public class MixinIntegratedServer {
    @TargetHandler(mixin="de.johni0702.minecraft.bobby.mixin.IntegratedServerMixin", name="bobbyViewDistanceOverwrite")
    @Redirect(method={"@MixinSquared:Handler"}, at=@At(value="INVOKE", target="Lde/johni0702/minecraft/bobby/BobbyConfig;getViewDistanceOverwrite()I"))
    public int flashback$overrideViewDistanceOverwrite(BobbyConfig instance) {
        if (Flashback.isInReplay()) {
            return 0;
        }
        return instance.getViewDistanceOverwrite();
    }
}

