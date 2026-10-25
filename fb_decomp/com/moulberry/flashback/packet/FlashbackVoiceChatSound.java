/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.handler.codec.DecoderException
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 *  net.minecraft.world.phys.Vec3
 */
package com.moulberry.flashback.packet;

import com.moulberry.flashback.Flashback;
import io.netty.handler.codec.DecoderException;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;

public interface FlashbackVoiceChatSound
extends CustomPacketPayload {
    public static final CustomPacketPayload.Type<FlashbackVoiceChatSound> TYPE = new CustomPacketPayload.Type(Flashback.createIdentifier("voice_chat_sound"));
    public static final StreamCodec<FriendlyByteBuf, FlashbackVoiceChatSound> STREAM_CODEC = new FlashbackVoiceChatSoundStreamCodec();
    public static final byte TYPE_STATIC_SOUND = 0;
    public static final byte TYPE_LOCATIONAL_SOUND = 1;
    public static final byte TYPE_ENTITY_SOUND = 2;

    default public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public UUID source();

    public short[] samples();

    public void writeExtraData(FriendlyByteBuf var1);

    public static class FlashbackVoiceChatSoundStreamCodec
    implements StreamCodec<FriendlyByteBuf, FlashbackVoiceChatSound> {
        public FlashbackVoiceChatSound decode(FriendlyByteBuf friendlyByteBuf) {
            UUID uuid = friendlyByteBuf.readUUID();
            int sampleCount = friendlyByteBuf.readVarInt();
            short[] samples = new short[sampleCount];
            for (int i = 0; i < sampleCount; ++i) {
                samples[i] = friendlyByteBuf.readShort();
            }
            byte type = friendlyByteBuf.readByte();
            switch (type) {
                case 0: {
                    return new SoundStatic(uuid, samples);
                }
                case 1: {
                    Vec3 position = new Vec3(friendlyByteBuf.readDouble(), friendlyByteBuf.readDouble(), friendlyByteBuf.readDouble());
                    float distance = friendlyByteBuf.readFloat();
                    return new SoundLocational(uuid, samples, position, distance);
                }
                case 2: {
                    boolean whispering = friendlyByteBuf.readBoolean();
                    float distance = friendlyByteBuf.readFloat();
                    return new SoundEntity(uuid, samples, whispering, distance);
                }
            }
            throw new DecoderException("Unknown voice chat type: " + type);
        }

        public void encode(FriendlyByteBuf friendlyByteBuf, FlashbackVoiceChatSound packet) {
            friendlyByteBuf.writeUUID(packet.source());
            short[] samples = packet.samples();
            friendlyByteBuf.writeVarInt(samples.length);
            for (short sample : samples) {
                friendlyByteBuf.writeShort((int)sample);
            }
            packet.writeExtraData(friendlyByteBuf);
        }
    }

    public record SoundEntity(UUID source, short[] samples, boolean whispering, float distance) implements FlashbackVoiceChatSound
    {
        @Override
        public void writeExtraData(FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeByte(2);
            friendlyByteBuf.writeBoolean(this.whispering);
            friendlyByteBuf.writeFloat(this.distance);
        }
    }

    public record SoundLocational(UUID source, short[] samples, Vec3 position, float distance) implements FlashbackVoiceChatSound
    {
        @Override
        public void writeExtraData(FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeByte(1);
            friendlyByteBuf.writeDouble(this.position.x());
            friendlyByteBuf.writeDouble(this.position.y());
            friendlyByteBuf.writeDouble(this.position.z());
            friendlyByteBuf.writeFloat(this.distance);
        }
    }

    public record SoundStatic(UUID source, short[] samples) implements FlashbackVoiceChatSound
    {
        @Override
        public void writeExtraData(FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeByte(0);
        }
    }
}

