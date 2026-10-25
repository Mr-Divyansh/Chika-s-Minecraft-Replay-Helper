/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.keyframe.impl;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeTickrate;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import com.moulberry.flashback.keyframe.types.SpeedKeyframeType;
import com.moulberry.flashback.spline.CatmullRom;
import com.moulberry.flashback.spline.Hermite;
import imgui.moulberry90.ImGui;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.resources.language.I18n;

public class TickrateKeyframe
extends Keyframe {
    private float tickrate;

    public TickrateKeyframe(float tickrate) {
        this(tickrate, InterpolationType.getDefault());
    }

    public TickrateKeyframe(float tickrate, InterpolationType interpolationType) {
        this.tickrate = tickrate;
        this.interpolationType(interpolationType);
    }

    @Override
    public KeyframeType<?> keyframeType() {
        return SpeedKeyframeType.INSTANCE;
    }

    @Override
    public Keyframe copy() {
        return new TickrateKeyframe(this.tickrate, this.interpolationType());
    }

    @Override
    public void renderEditKeyframe(Consumer<Consumer<Keyframe>> update) {
        float tickrate;
        ImGui.setNextItemWidth(160.0f);
        float[] input = new float[]{this.tickrate / 20.0f};
        if (ImGui.sliderFloat(I18n.get((String)"flashback.keyframe.speed", (Object[])new Object[0]), input, 0.1f, 10.0f) && this.tickrate != (tickrate = Math.max(0.01f, input[0] * 20.0f))) {
            update.accept(keyframe -> {
                ((TickrateKeyframe)keyframe).tickrate = tickrate;
            });
        }
    }

    @Override
    public KeyframeChange createChange() {
        return new KeyframeChangeTickrate(this.tickrate);
    }

    @Override
    public KeyframeChange createSmoothInterpolatedChange(Keyframe p1, Keyframe p2, Keyframe p3, float t0, float t1, float t2, float t3, float amount) {
        float time1 = t1 - t0;
        float time2 = t2 - t0;
        float time3 = t3 - t0;
        float tickrate = CatmullRom.value(this.tickrate, ((TickrateKeyframe)p1).tickrate, ((TickrateKeyframe)p2).tickrate, ((TickrateKeyframe)p3).tickrate, time1, time2, time3, amount);
        return new KeyframeChangeTickrate(tickrate);
    }

    @Override
    public KeyframeChange createHermiteInterpolatedChange(Map<Float, Keyframe> keyframes, float amount) {
        float tickrate = (float)Hermite.value(Maps.transformValues(keyframes, k -> ((TickrateKeyframe)k).tickrate), amount);
        return new KeyframeChangeTickrate(tickrate);
    }

    public static class TypeAdapter
    implements JsonSerializer<TickrateKeyframe>,
    JsonDeserializer<TickrateKeyframe> {
        public TickrateKeyframe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            float tickrate = jsonObject.get("tickrate").getAsFloat();
            InterpolationType interpolationType = (InterpolationType)context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class);
            return new TickrateKeyframe(tickrate, interpolationType);
        }

        public JsonElement serialize(TickrateKeyframe src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("tickrate", (Number)Float.valueOf(src.tickrate));
            jsonObject.addProperty("type", "tickrate");
            jsonObject.add("interpolation_type", context.serialize((Object)src.interpolationType()));
            return jsonObject;
        }
    }
}

