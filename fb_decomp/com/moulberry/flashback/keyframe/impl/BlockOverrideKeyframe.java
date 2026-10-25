/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.IdMap
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.PalettedContainer
 *  net.minecraft.world.level.chunk.Strategy
 */
package com.moulberry.flashback.keyframe.impl;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import com.moulberry.flashback.keyframe.types.SpeedKeyframeType;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.IdMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.Strategy;

public class BlockOverrideKeyframe
extends Keyframe {
    private static final long MIN_POSITION_LONG = BlockPos.asLong((int)-33554432, (int)-2048, (int)-33554432);
    public static final BlockState EMPTY_STATE;
    private final Strategy<BlockState> containerStrategy = Strategy.createForBlockStates((IdMap)Block.BLOCK_STATE_REGISTRY);
    public Long2ObjectMap<PalettedContainer<BlockState>> blocks = new Long2ObjectOpenHashMap();
    private long lastPos = MIN_POSITION_LONG;
    private PalettedContainer<BlockState> lastContainer = null;
    private static final Codec<PalettedContainer<BlockState>> BLOCK_STATE_CODEC;

    public BlockOverrideKeyframe() {
        this.interpolationType(InterpolationType.LINEAR);
    }

    @Override
    public KeyframeType<?> keyframeType() {
        return SpeedKeyframeType.INSTANCE;
    }

    public void setBlock(int x, int y, int z, BlockState blockState) {
        long chunk = BlockPos.asLong((int)(x >> 4), (int)(y >> 4), (int)(z >> 4));
        if (chunk != this.lastPos) {
            this.lastPos = chunk;
            this.lastContainer = (PalettedContainer)this.blocks.computeIfAbsent(chunk, l -> new PalettedContainer((Object)EMPTY_STATE, this.containerStrategy));
        }
        this.lastContainer.set(x & 0xF, y & 0xF, z & 0xF, (Object)blockState);
    }

    @Override
    public Keyframe copy() {
        BlockOverrideKeyframe keyframe = new BlockOverrideKeyframe();
        for (Long2ObjectMap.Entry entry : this.blocks.long2ObjectEntrySet()) {
            keyframe.blocks.put(entry.getLongKey(), (Object)((PalettedContainer)entry.getValue()).copy());
        }
        return keyframe;
    }

    @Override
    public KeyframeChange createChange() {
        return null;
    }

    @Override
    public KeyframeChange createSmoothInterpolatedChange(Keyframe p1, Keyframe p2, Keyframe p3, float t0, float t1, float t2, float t3, float amount) {
        return null;
    }

    @Override
    public KeyframeChange createHermiteInterpolatedChange(Map<Float, Keyframe> keyframes, float amount) {
        return null;
    }

    static {
        if (MIN_POSITION_LONG != -9223371899415820288L) {
            throw new RuntimeException("BlockPos representation changed!");
        }
        EMPTY_STATE = Blocks.VOID_AIR.defaultBlockState();
        BLOCK_STATE_CODEC = PalettedContainer.codecRW((Codec)BlockState.CODEC, (Strategy)Strategy.createForBlockStates((IdMap)Block.BLOCK_STATE_REGISTRY), (Object)EMPTY_STATE);
    }

    public static class TypeAdapter
    implements JsonSerializer<BlockOverrideKeyframe>,
    JsonDeserializer<BlockOverrideKeyframe> {
        public BlockOverrideKeyframe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            BlockOverrideKeyframe blockOverrideKeyframe = new BlockOverrideKeyframe();
            JsonObject chunks = jsonObject.getAsJsonObject("chunks");
            for (String key : chunks.keySet()) {
                long pos = Long.parseLong(key);
                Optional result = BLOCK_STATE_CODEC.decode((DynamicOps)JsonOps.COMPRESSED, (Object)chunks.get(key)).result();
                if (!result.isPresent()) continue;
                PalettedContainer container = (PalettedContainer)((Pair)result.get()).getFirst();
                blockOverrideKeyframe.blocks.put(pos, (Object)container);
            }
            return blockOverrideKeyframe;
        }

        public JsonElement serialize(BlockOverrideKeyframe src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject chunks = new JsonObject();
            for (Long2ObjectMap.Entry entry : src.blocks.long2ObjectEntrySet()) {
                Optional result = BLOCK_STATE_CODEC.encodeStart((DynamicOps)JsonOps.COMPRESSED, (Object)((PalettedContainer)entry.getValue())).result();
                if (!result.isPresent()) continue;
                chunks.add("" + entry.getLongKey(), (JsonElement)result.get());
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("chunks", (JsonElement)chunks);
            jsonObject.addProperty("type", "block_override");
            return jsonObject;
        }
    }
}

