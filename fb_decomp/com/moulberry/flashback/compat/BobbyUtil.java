/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  de.johni0702.minecraft.bobby.FakeChunkManager
 *  de.johni0702.minecraft.bobby.ext.ClientChunkCacheExt
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  net.minecraft.client.multiplayer.ClientChunkCache
 *  net.minecraft.world.level.chunk.LevelChunk
 */
package com.moulberry.flashback.compat;

import de.johni0702.minecraft.bobby.FakeChunkManager;
import de.johni0702.minecraft.bobby.ext.ClientChunkCacheExt;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.util.List;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.world.level.chunk.LevelChunk;

public class BobbyUtil {
    public static void addBobbyChunks(ClientChunkCache clientChunkCache, List<LevelChunk> chunks, LongOpenHashSet seenChunkPositions) {
        FakeChunkManager bobbyChunkCache = ((ClientChunkCacheExt)clientChunkCache).bobby_getFakeChunkManager();
        if (bobbyChunkCache == null) {
            return;
        }
        for (LevelChunk chunk : bobbyChunkCache.getFakeChunks()) {
            if (!seenChunkPositions.add(chunk.getPos().pack())) continue;
            chunks.add(chunk);
        }
    }
}

