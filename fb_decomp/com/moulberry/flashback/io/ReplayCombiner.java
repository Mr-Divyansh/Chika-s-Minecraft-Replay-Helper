/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  it.unimi.dsi.fastutil.ints.Int2IntMap
 *  it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket
 *  net.minecraft.network.protocol.game.GameProtocols
 *  net.minecraft.resources.Identifier
 */
package com.moulberry.flashback.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.FlashbackGson;
import com.moulberry.flashback.action.Action;
import com.moulberry.flashback.action.ActionLevelChunkCached;
import com.moulberry.flashback.action.ActionRegistry;
import com.moulberry.flashback.record.FlashbackChunkMeta;
import com.moulberry.flashback.record.FlashbackMeta;
import com.moulberry.flashback.record.ReplayMarker;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.GameProtocols;
import net.minecraft.resources.Identifier;

public class ReplayCombiner {
    public static void combine(RegistryAccess registryAccess, String replayName, Path first, Path second, Path output) throws Exception {
        StreamCodec gamePacketCodec = GameProtocols.CLIENTBOUND_TEMPLATE.bind(RegistryFriendlyByteBuf.decorator((RegistryAccess)registryAccess)).codec();
        try (FileSystem firstFileSystem = FileSystems.newFileSystem(first);
             FileSystem secondFileSystem = FileSystems.newFileSystem(second);){
            ArrayList<ClientboundLevelChunkWithLightPacket> levelChunkPackets = new ArrayList<ClientboundLevelChunkWithLightPacket>();
            Int2IntOpenHashMap levelChunkMappingsFirst = new Int2IntOpenHashMap();
            Int2IntOpenHashMap levelChunkMappingsSecond = new Int2IntOpenHashMap();
            ReplayCombiner.extractChunks(registryAccess, firstFileSystem, (StreamCodec<ByteBuf, Packet<? super ClientGamePacketListener>>)gamePacketCodec, levelChunkPackets, (Int2IntMap)levelChunkMappingsFirst);
            ReplayCombiner.extractChunks(registryAccess, secondFileSystem, (StreamCodec<ByteBuf, Packet<? super ClientGamePacketListener>>)gamePacketCodec, levelChunkPackets, (Int2IntMap)levelChunkMappingsSecond);
            Path metadataPath = firstFileSystem.getPath("/metadata.json", new String[0]);
            String metadataJson = Files.readString(metadataPath);
            FlashbackMeta firstMetadata = FlashbackMeta.fromJson((JsonObject)FlashbackGson.COMPRESSED.fromJson(metadataJson, JsonObject.class));
            if (firstMetadata == null) {
                throw new RuntimeException("Unable to load /metadata.json from first replay");
            }
            Path secondMetadataPath = secondFileSystem.getPath("/metadata.json", new String[0]);
            String secondMetadataJson = Files.readString(secondMetadataPath);
            FlashbackMeta secondMetadata = FlashbackMeta.fromJson((JsonObject)FlashbackGson.COMPRESSED.fromJson(secondMetadataJson, JsonObject.class));
            if (secondMetadata == null) {
                throw new RuntimeException("Unable to load /metadata.json from second replay");
            }
            if (firstMetadata.dataVersion != secondMetadata.dataVersion) {
                throw new RuntimeException("Replays were created on different versions of the game, unable to combine");
            }
            firstMetadata.replayIdentifier = UUID.randomUUID();
            firstMetadata.name = replayName;
            for (Map.Entry<Integer, ReplayMarker> entry : secondMetadata.replayMarkers.entrySet()) {
                firstMetadata.replayMarkers.put(entry.getKey() + firstMetadata.totalTicks, entry.getValue());
            }
            firstMetadata.totalTicks += secondMetadata.totalTicks;
            record ReplayChunk(Path path, Int2IntMap levelChunkMappings) {
            }
            HashMap<Object, ReplayChunk> newReplayChunks = new HashMap<Object, ReplayChunk>();
            for (String string : firstMetadata.chunks.keySet()) {
                newReplayChunks.put(string, new ReplayChunk(firstFileSystem.getPath("/" + string, new String[0]), (Int2IntMap)levelChunkMappingsFirst));
            }
            boolean bl = true;
            for (Map.Entry<String, FlashbackChunkMeta> entry : secondMetadata.chunks.entrySet()) {
                boolean bl2;
                String newName = "c" + firstMetadata.chunks.size() + ".flashback";
                if (bl2) {
                    bl2 = false;
                    entry.getValue().forcePlaySnapshot = true;
                }
                firstMetadata.chunks.put(newName, entry.getValue());
                newReplayChunks.put(newName, new ReplayChunk(secondFileSystem.getPath("/" + entry.getKey(), new String[0]), (Int2IntMap)levelChunkMappingsSecond));
            }
            FileOutputStream fileOutputStream = new FileOutputStream(output.toFile());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            ZipOutputStream zipOut = new ZipOutputStream(bufferedOutputStream);
            zipOut.setLevel(1);
            ZipEntry zipEntry = new ZipEntry("metadata.json");
            zipOut.putNextEntry(zipEntry);
            zipOut.write(FlashbackGson.COMPRESSED.toJson((JsonElement)firstMetadata.toJson()).getBytes(StandardCharsets.UTF_8));
            zipOut.closeEntry();
            int lastCacheIndex = -1;
            RegistryFriendlyByteBuf chunkCacheOutput = null;
            for (int i = 0; i < levelChunkPackets.size(); ++i) {
                int cacheIndex = i / 10000;
                if (chunkCacheOutput == null) {
                    lastCacheIndex = cacheIndex;
                    chunkCacheOutput = new RegistryFriendlyByteBuf(Unpooled.buffer(), registryAccess);
                } else if (cacheIndex != lastCacheIndex) {
                    byte[] bytes = new byte[chunkCacheOutput.writerIndex()];
                    chunkCacheOutput.getBytes(0, bytes);
                    zipEntry = new ZipEntry("level_chunk_caches/" + lastCacheIndex);
                    zipOut.putNextEntry(zipEntry);
                    zipOut.write(bytes);
                    zipOut.closeEntry();
                    lastCacheIndex = cacheIndex;
                    chunkCacheOutput = new RegistryFriendlyByteBuf(Unpooled.buffer(), registryAccess);
                }
                int startWriterIndex = chunkCacheOutput.writerIndex();
                chunkCacheOutput.writeInt(-1);
                gamePacketCodec.encode((Object)chunkCacheOutput, (Object)((Packet)levelChunkPackets.get(i)));
                int endWriterIndex = chunkCacheOutput.writerIndex();
                int size = endWriterIndex - startWriterIndex - 4;
                chunkCacheOutput.writerIndex(startWriterIndex);
                chunkCacheOutput.writeInt(size);
                chunkCacheOutput.writerIndex(endWriterIndex);
            }
            if (chunkCacheOutput != null) {
                byte[] bytes = new byte[chunkCacheOutput.writerIndex()];
                chunkCacheOutput.getBytes(0, bytes);
                zipEntry = new ZipEntry("level_chunk_caches/" + lastCacheIndex);
                zipOut.putNextEntry(zipEntry);
                zipOut.write(bytes);
                zipOut.closeEntry();
            }
            zipEntry = new ZipEntry("icon.png");
            zipOut.putNextEntry(zipEntry);
            Files.copy(firstFileSystem.getPath("/icon.png", new String[0]), zipOut);
            zipOut.closeEntry();
            for (Map.Entry entry : newReplayChunks.entrySet()) {
                zipEntry = new ZipEntry((String)entry.getKey());
                zipOut.putNextEntry(zipEntry);
                Path path = ((ReplayChunk)entry.getValue()).path();
                byte[] replayChunk = Files.readAllBytes(path);
                RegistryFriendlyByteBuf inputBuf = new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer((byte[])replayChunk), registryAccess);
                FriendlyByteBuf outputBuf = new FriendlyByteBuf(Unpooled.buffer());
                int magic = inputBuf.readInt();
                if (magic != -679417724) {
                    throw new RuntimeException("Invalid magic");
                }
                outputBuf.writeInt(magic);
                int levelChunkCachedActionId = -1;
                int actions = inputBuf.readVarInt();
                outputBuf.writeVarInt(actions);
                for (int i = 0; i < actions; ++i) {
                    Identifier actionName = inputBuf.readIdentifier();
                    outputBuf.writeIdentifier(actionName);
                    Action action = ActionRegistry.getAction(actionName);
                    if (!(action instanceof ActionLevelChunkCached)) continue;
                    levelChunkCachedActionId = i;
                }
                if (levelChunkCachedActionId == -1) {
                    Files.copy(((ReplayChunk)entry.getValue()).path, zipOut);
                } else {
                    int snapshotSize = inputBuf.readInt();
                    if (snapshotSize < 0) {
                        throw new RuntimeException("Invalid snapshot size: " + snapshotSize + " (0x" + Integer.toHexString(snapshotSize) + ")");
                    }
                    int snapshotInputEnd = inputBuf.readerIndex() + snapshotSize;
                    int snapshotOutputWriterIndex = outputBuf.writerIndex();
                    outputBuf.writeInt(-559038737);
                    int snapshotStartWriterIndex = outputBuf.writerIndex();
                    int snapshotEndWriterIndex = outputBuf.writerIndex();
                    while (inputBuf.readerIndex() < inputBuf.writerIndex()) {
                        boolean inSnapshot = inputBuf.readerIndex() < snapshotInputEnd;
                        int id = inputBuf.readVarInt();
                        int size = inputBuf.readInt();
                        if (id == levelChunkCachedActionId) {
                            int cachedChunkId = inputBuf.readVarInt();
                            if (!((ReplayChunk)entry.getValue()).levelChunkMappings.containsKey(cachedChunkId)) {
                                throw new RuntimeException("Missing cached chunk id " + cachedChunkId);
                            }
                            int newCachedChunkId = ((ReplayChunk)entry.getValue()).levelChunkMappings.get(cachedChunkId);
                            outputBuf.writeVarInt(id);
                            int sizeWriterIndex = outputBuf.writerIndex();
                            outputBuf.writeInt(0);
                            int cachedIdWriterIndex = outputBuf.writerIndex();
                            outputBuf.writeVarInt(newCachedChunkId);
                            int endWriterIndex = outputBuf.writerIndex();
                            outputBuf.writerIndex(sizeWriterIndex);
                            outputBuf.writeInt(endWriterIndex - cachedIdWriterIndex);
                            outputBuf.writerIndex(endWriterIndex);
                        } else {
                            outputBuf.writeVarInt(id);
                            outputBuf.writeInt(size);
                            outputBuf.writeBytes((ByteBuf)inputBuf, size);
                        }
                        if (!inSnapshot) continue;
                        snapshotEndWriterIndex = outputBuf.writerIndex();
                    }
                    int endWriterIndex = outputBuf.writerIndex();
                    outputBuf.writerIndex(snapshotOutputWriterIndex);
                    outputBuf.writeInt(snapshotEndWriterIndex - snapshotStartWriterIndex);
                    outputBuf.writerIndex(endWriterIndex);
                    byte[] bytes = new byte[outputBuf.writerIndex()];
                    outputBuf.getBytes(0, bytes);
                    zipOut.write(bytes);
                }
                zipOut.closeEntry();
            }
            zipOut.close();
            bufferedOutputStream.close();
            fileOutputStream.close();
        }
    }

    private static void extractChunks(RegistryAccess registryAccess, FileSystem fileSystem, StreamCodec<ByteBuf, Packet<? super ClientGamePacketListener>> gamePacketCodec, List<ClientboundLevelChunkWithLightPacket> packets, Int2IntMap levelChunkMappings) throws IOException {
        Path levelChunkCachePath = fileSystem.getPath("/level_chunk_cache", new String[0]);
        if (Files.exists(levelChunkCachePath, new LinkOption[0])) {
            ReplayCombiner.loadLevelChunkCache(gamePacketCodec, registryAccess, levelChunkCachePath, 0, packets, levelChunkMappings);
        }
        int index = 0;
        while (Files.exists(levelChunkCachePath = fileSystem.getPath("/level_chunk_caches/" + index, new String[0]), new LinkOption[0])) {
            ReplayCombiner.loadLevelChunkCache(gamePacketCodec, registryAccess, levelChunkCachePath, index * 10000, packets, levelChunkMappings);
            ++index;
        }
    }

    private static void loadLevelChunkCache(StreamCodec<ByteBuf, Packet<? super ClientGamePacketListener>> gamePacketCodec, RegistryAccess registryAccess, Path levelChunkCachePath, int chunkCacheIndex, List<ClientboundLevelChunkWithLightPacket> packets, Int2IntMap levelChunkMappings) throws IOException {
        try (InputStream is = Files.newInputStream(levelChunkCachePath, new OpenOption[0]);){
            byte[] sizeBuffer;
            while ((sizeBuffer = is.readNBytes(4)).length >= 4) {
                int size = (sizeBuffer[0] & 0xFF) << 24 | (sizeBuffer[1] & 0xFF) << 16 | (sizeBuffer[2] & 0xFF) << 8 | sizeBuffer[3] & 0xFF;
                byte[] chunk = is.readNBytes(size);
                if (chunk.length < size) {
                    Flashback.LOGGER.error("Ran out of bytes while reading level_chunk_cache, needed {}, had {}", (Object)size, (Object)chunk.length);
                    break;
                }
                RegistryFriendlyByteBuf registryFriendlyByteBuf = new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer((byte[])chunk), registryAccess);
                try {
                    Packet packet = (Packet)gamePacketCodec.decode((Object)registryFriendlyByteBuf);
                    if (!(packet instanceof ClientboundLevelChunkWithLightPacket)) {
                        throw new IllegalStateException("Level chunk cache contains wrong packet: " + String.valueOf(packet));
                    }
                    ClientboundLevelChunkWithLightPacket levelChunkWithLightPacket = (ClientboundLevelChunkWithLightPacket)packet;
                    levelChunkMappings.put(chunkCacheIndex, packets.size());
                    packets.add(levelChunkWithLightPacket);
                }
                catch (Exception e) {
                    Flashback.LOGGER.error("Encountered error while reading level_chunk_cache", (Throwable)e);
                }
                ++chunkCacheIndex;
            }
        }
    }
}

