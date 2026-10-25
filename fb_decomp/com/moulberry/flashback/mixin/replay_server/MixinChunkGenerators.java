/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.MapCodec
 *  net.minecraft.core.Registry
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.chunk.ChunkGenerators
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.replay_server;

import com.mojang.serialization.MapCodec;
import com.moulberry.flashback.playback.EmptyLevelSource;
import net.minecraft.core.Registry;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGenerators;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ChunkGenerators.class})
public class MixinChunkGenerators {
    @Inject(method={"bootstrap"}, at={@At(value="HEAD")})
    private static void bootstrap(Registry<MapCodec<? extends ChunkGenerator>> registry, CallbackInfoReturnable<MapCodec<? extends ChunkGenerator>> cir) {
        Registry.register(registry, (String)"flashback/empty", EmptyLevelSource.CODEC);
    }
}

