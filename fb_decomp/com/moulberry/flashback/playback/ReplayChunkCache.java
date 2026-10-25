/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.playback;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.SneakyThrow;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import org.jetbrains.annotations.Nullable;

public class ReplayChunkCache {
    public static final int CHUNK_CACHE_SIZE = 10000;
    private final Int2ObjectMap<SoftReference<List<ClientboundLevelChunkWithLightPacket>>> levelChunkCachedPackets = new Int2ObjectOpenHashMap();
    private final IntSet missingChunkCacheFiles = new IntOpenHashSet();
    private final FileSystem playbackFileSystem;

    public ReplayChunkCache(FileSystem playbackFileSystem) {
        this.playbackFileSystem = playbackFileSystem;
    }

    public void clear() {
        this.levelChunkCachedPackets.clear();
    }

    @Nullable
    public ClientboundLevelChunkWithLightPacket getOrLoad(int index, RegistryAccess registryAccess, StreamCodec<ByteBuf, Packet<? super ClientGamePacketListener>> gamePacketCodec) {
        int indexInCache;
        List<ClientboundLevelChunkWithLightPacket> packets;
        int cacheIndex = index / 10000;
        SoftReference packetListReference = (SoftReference)this.levelChunkCachedPackets.get(cacheIndex);
        List<ClientboundLevelChunkWithLightPacket> list = packets = packetListReference == null ? null : (List<ClientboundLevelChunkWithLightPacket>)packetListReference.get();
        if (packets == null) {
            if (this.missingChunkCacheFiles.contains(cacheIndex)) {
                return null;
            }
            String pathString = "/level_chunk_caches/" + cacheIndex;
            Path levelChunkCachePath = this.playbackFileSystem.getPath(pathString, new String[0]);
            if (Files.exists(levelChunkCachePath, new LinkOption[0])) {
                try {
                    packets = ReplayChunkCache.loadLevelChunkCache(levelChunkCachePath, registryAccess, gamePacketCodec);
                    this.levelChunkCachedPackets.put(cacheIndex, new SoftReference<List<ClientboundLevelChunkWithLightPacket>>(packets));
                    Flashback.LOGGER.info("Loaded {} with {} entries", (Object)pathString, (Object)packets.size());
                }
                catch (IOException e) {
                    SneakyThrow.sneakyThrow(e);
                }
            } else {
                Flashback.LOGGER.error("Chunk index {} was requested, but cache file {} was missing", (Object)index, (Object)pathString);
                this.missingChunkCacheFiles.add(cacheIndex);
                return null;
            }
        }
        return (indexInCache = index - cacheIndex * 10000) < packets.size() ? (ClientboundLevelChunkWithLightPacket)packets.get(indexInCache) : null;
    }

    private static List<ClientboundLevelChunkWithLightPacket> loadLevelChunkCache(Path levelChunkCachePath, RegistryAccess registryAccess, StreamCodec<ByteBuf, Packet<? super ClientGamePacketListener>> gamePacketCodec) throws IOException {
        ArrayList<ClientboundLevelChunkWithLightPacket> packets;
        block13: {
            packets = new ArrayList<ClientboundLevelChunkWithLightPacket>();
            InputStream is = Files.newInputStream(levelChunkCachePath, new OpenOption[0]);
            block7: while (true) {
                while (true) {
                    byte[] sizeBuffer;
                    if ((sizeBuffer = is.readNBytes(4)).length < 4) {
                        break block13;
                    }
                    int size = (sizeBuffer[0] & 0xFF) << 24 | (sizeBuffer[1] & 0xFF) << 16 | (sizeBuffer[2] & 0xFF) << 8 | sizeBuffer[3] & 0xFF;
                    byte[] chunk = is.readNBytes(size);
                    if (chunk.length < size) {
                        Flashback.LOGGER.error("Ran out of bytes while reading level_chunk_cache, needed {}, had {}", (Object)size, (Object)chunk.length);
                        break block13;
                    }
                    RegistryFriendlyByteBuf registryFriendlyByteBuf = new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer((byte[])chunk), registryAccess);
                    try {
                        Packet packet = (Packet)gamePacketCodec.decode((Object)registryFriendlyByteBuf);
                        if (packet instanceof ClientboundLevelChunkWithLightPacket) {
                            ClientboundLevelChunkWithLightPacket levelChunkWithLightPacket = (ClientboundLevelChunkWithLightPacket)packet;
                            packets.add(levelChunkWithLightPacket);
                            continue block7;
                        }
                        throw new IllegalStateException("Level chunk cache contains wrong packet: " + String.valueOf(packet));
                    }
                    catch (Exception e) {
                        Flashback.LOGGER.error("Encountered error while reading level_chunk_cache", (Throwable)e);
                        continue;
                    }
                    break;
                }
                break;
            }
            finally {
                if (is != null) {
                    is.close();
                }
            }
        }
        return packets;
    }
}

