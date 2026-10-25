/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.moulberry.mixinconstraints.annotations.IfModLoaded
 *  de.johni0702.minecraft.bobby.ChunkSerializer
 *  de.johni0702.minecraft.bobby.FakeChunkManager
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.world.level.chunk.LevelChunk
 *  org.apache.commons.lang3.tuple.Pair
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Pseudo
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.compat.bobby;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.record.FlashbackMeta;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import de.johni0702.minecraft.bobby.ChunkSerializer;
import de.johni0702.minecraft.bobby.FakeChunkManager;
import java.util.function.Supplier;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.level.chunk.LevelChunk;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@IfModLoaded(value="bobby")
@Pseudo
@Mixin(value={FakeChunkManager.class}, remap=false)
public class MixinBobbyFakeChunkManager {
    @Inject(method={"getCurrentWorldOrServerName"}, require=0, at={@At(value="HEAD")}, cancellable=true)
    private static void getCurrentWorldOrServerName(ClientPacketListener networkHandler, CallbackInfoReturnable<String> cir) {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer != null) {
            FlashbackMeta meta = replayServer.getMetadata();
            if (meta != null && meta.bobbyWorldName != null) {
                Flashback.LOGGER.info("Overriding Bobby World Name: {}", (Object)meta.bobbyWorldName);
                cir.setReturnValue((Object)meta.bobbyWorldName);
            } else {
                cir.setReturnValue((Object)"unknown_flashback");
            }
        }
    }

    @Inject(method={"save"}, at={@At(value="HEAD")}, require=0, cancellable=true)
    public void save(LevelChunk chunk, CallbackInfoReturnable<Supplier<LevelChunk>> cir) {
        if (Flashback.isInReplay()) {
            Pair copy = ChunkSerializer.shallowCopy((LevelChunk)chunk);
            cir.setReturnValue((Object)((Supplier)copy.getRight()));
        }
    }
}

