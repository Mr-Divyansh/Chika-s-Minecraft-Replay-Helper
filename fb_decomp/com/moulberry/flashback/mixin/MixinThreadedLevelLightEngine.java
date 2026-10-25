/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.server.level.ThreadedLevelLightEngine
 *  net.minecraft.server.level.ThreadedLevelLightEngine$TaskType
 *  net.minecraft.world.level.chunk.LightChunkGetter
 *  net.minecraft.world.level.lighting.LevelLightEngine
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.ext.ThreadedLevelLightEngineExt;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={ThreadedLevelLightEngine.class}, priority=1010)
public abstract class MixinThreadedLevelLightEngine
extends LevelLightEngine
implements ThreadedLevelLightEngineExt {
    @Shadow
    protected abstract void addTask(int var1, int var2, ThreadedLevelLightEngine.TaskType var3, Runnable var4);

    public MixinThreadedLevelLightEngine(LightChunkGetter lightChunkGetter, boolean bl, boolean bl2) {
        super(lightChunkGetter, bl, bl2);
    }

    @Override
    public void flashback$submitPost(int x, int z, Runnable runnable) {
        this.addTask(x, z, ThreadedLevelLightEngine.TaskType.POST_UPDATE, runnable);
    }

    @WrapOperation(method={"runUpdate"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/level/lighting/LevelLightEngine;runLightUpdates()I")}, require=0)
    public int runUpdate_runLightUpdates(ThreadedLevelLightEngine instance, Operation<Integer> original) {
        if (Flashback.isInReplay()) {
            try {
                return (Integer)original.call(new Object[]{instance});
            }
            catch (Exception e) {
                Flashback.LOGGER.error("Exception occurred while processing light updates", (Throwable)e);
                return 0;
            }
        }
        return (Integer)original.call(new Object[]{instance});
    }
}

