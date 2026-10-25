/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.entity.PersistentEntitySectionManager
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.replay_server;

import com.moulberry.flashback.Flashback;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={PersistentEntitySectionManager.class})
public class MixinPersistentEntitySectionManager {
    @Inject(method={"saveAll"}, at={@At(value="HEAD")}, cancellable=true)
    public void saveAll(CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            ci.cancel();
        }
    }
}

