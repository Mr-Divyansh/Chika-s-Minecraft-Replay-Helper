/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData$BlockEntityInfo
 *  net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket
 */
package com.moulberry.flashback;

import io.netty.buffer.Unpooled;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;

public class CachedChunkPacket {
    private static final VarHandle LONG_ARRAY = MethodHandles.byteArrayViewVarHandle(long[].class, ByteOrder.nativeOrder());
    private final int x;
    private final int z;
    private final byte[] bigHash;
    public final long longHashCode;
    public int index;

    public CachedChunkPacket(ClientboundLevelChunkWithLightPacket packet, int index) {
        this.x = packet.getX();
        this.z = packet.getZ();
        this.bigHash = CachedChunkPacket.computePacketBigHash(packet);
        if (this.bigHash.length == 64) {
            long hash = 982374698276290847L;
            for (int i = 0; i < 8; ++i) {
                hash ^= LONG_ARRAY.get(this.bigHash, i);
                hash *= 209648290153981L;
                hash += 164923702968709L;
            }
            this.longHashCode = hash;
        } else if (this.bigHash.length == 32) {
            long hash = 150939871908751L;
            for (int i = 0; i < 4; ++i) {
                hash ^= LONG_ARRAY.get(this.bigHash, i);
                hash *= 209648290153981L;
                hash += 164923702968709L;
            }
            this.longHashCode = hash;
        } else {
            this.longHashCode = Arrays.hashCode(this.bigHash);
        }
        this.index = index;
    }

    private static byte[] computePacketBigHash(ClientboundLevelChunkWithLightPacket packet) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException e) {
            try {
                digest = MessageDigest.getInstance("SHA-256");
            }
            catch (NoSuchAlgorithmException e2) {
                throw new RuntimeException(e2);
            }
        }
        digest.update(CachedChunkPacket.intToByteArray(packet.getX()));
        digest.update(CachedChunkPacket.intToByteArray(packet.getZ()));
        digest.update(packet.getChunkData().buffer);
        FriendlyByteBuf frenBuffer = new FriendlyByteBuf(Unpooled.buffer());
        packet.lightData.write(frenBuffer);
        digest.update(frenBuffer.array(), 0, frenBuffer.writerIndex());
        frenBuffer.resetWriterIndex();
        digest.update(frenBuffer.array(), 0, frenBuffer.writerIndex());
        frenBuffer.resetWriterIndex();
        ArrayList<ClientboundLevelChunkPacketData.BlockEntityInfo> copy = new ArrayList<ClientboundLevelChunkPacketData.BlockEntityInfo>(packet.getChunkData().blockEntitiesData);
        copy.sort(Comparator.comparingInt(a -> a.y << 8 | a.packedXZ));
        for (ClientboundLevelChunkPacketData.BlockEntityInfo blockEntitiesData : copy) {
            digest.update((byte)blockEntitiesData.packedXZ);
            digest.update(CachedChunkPacket.intToByteArray(blockEntitiesData.y));
            digest.update(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey((Object)blockEntitiesData.type).toString().getBytes(StandardCharsets.UTF_8));
            if (blockEntitiesData.tag != null) {
                frenBuffer.writeNbt((Tag)blockEntitiesData.tag);
                digest.update(frenBuffer.array(), 0, frenBuffer.writerIndex());
                frenBuffer.resetWriterIndex();
                continue;
            }
            digest.update("NO_TAG".getBytes(StandardCharsets.UTF_8));
        }
        return digest.digest();
    }

    private static byte[] intToByteArray(int value) {
        return new byte[]{(byte)(value >>> 24), (byte)(value >>> 16), (byte)(value >>> 8), (byte)value};
    }

    public int hashCode() {
        return (int)this.longHashCode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CachedChunkPacket)) {
            return false;
        }
        CachedChunkPacket that = (CachedChunkPacket)o;
        if (this.longHashCode != that.longHashCode) {
            return false;
        }
        if (this.x != that.x || this.z != that.z) {
            return false;
        }
        return Arrays.equals(this.bigHash, that.bigHash);
    }
}

