/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  net.minecraft.core.RegistryAccess
 */
package com.moulberry.flashback.playback;

import com.moulberry.flashback.io.ReplayReader;
import com.moulberry.flashback.record.FlashbackChunkMeta;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.core.RegistryAccess;

public class PlayableChunk {
    public final FlashbackChunkMeta chunkMeta;
    public final Path path;
    private SoftReference<ReplayReader> replayReader;

    public PlayableChunk(FlashbackChunkMeta chunkMeta, Path path) {
        this.chunkMeta = chunkMeta;
        this.path = path;
    }

    public ReplayReader getOrLoadReplayReader(RegistryAccess registryAccess) {
        ReplayReader replayReader;
        ReplayReader replayReader2 = replayReader = this.replayReader == null ? null : this.replayReader.get();
        if (replayReader == null) {
            try {
                byte[] bytes = Files.readAllBytes(this.path);
                replayReader = new ReplayReader(Unpooled.wrappedBuffer((byte[])bytes), registryAccess);
                this.replayReader = new SoftReference<ReplayReader>(replayReader);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        replayReader.changeRegistryAccess(registryAccess);
        return replayReader;
    }
}

