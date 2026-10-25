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
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.keyframe.impl;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeFreeze;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import com.moulberry.flashback.keyframe.types.FreezeKeyframeType;
import imgui.moulberry90.ImGui;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.resources.language.I18n;

public class FreezeKeyframe
extends Keyframe {
    private boolean frozen;
    private int frozenDelay;

    public FreezeKeyframe(boolean frozen, int frozenDelay) {
        this(frozen, frozenDelay, InterpolationType.HOLD);
    }

    public FreezeKeyframe(boolean frozen, int frozenDelay, InterpolationType interpolationType) {
        this.frozen = frozen;
        this.frozenDelay = frozenDelay;
        this.interpolationType(interpolationType);
    }

    @Override
    public KeyframeType<?> keyframeType() {
        return FreezeKeyframeType.INSTANCE;
    }

    @Override
    public Keyframe copy() {
        return new FreezeKeyframe(this.frozen, this.frozenDelay, this.interpolationType());
    }

    @Override
    public void renderEditKeyframe(Consumer<Consumer<Keyframe>> update) {
        ImGui.setNextItemWidth(160.0f);
        if (ImGui.checkbox(I18n.get((String)"flashback.frozen", (Object[])new Object[0]), this.frozen)) {
            boolean newFrozen = !this.frozen;
            update.accept(keyframe -> {
                ((FreezeKeyframe)keyframe).frozen = newFrozen;
            });
        }
        int[] delay = new int[]{this.frozenDelay};
        ImGui.setNextItemWidth(160.0f);
        if (ImGui.sliderInt(I18n.get((String)"flashback.delay", (Object[])new Object[0]), delay, 0, 10)) {
            delay[0] = Math.max(0, Math.min(10, delay[0]));
            update.accept(keyframe -> {
                ((FreezeKeyframe)keyframe).frozenDelay = delay[0];
            });
        }
        ImGuiHelper.tooltip(I18n.get((String)"flashback.freeze_delay_tooltip", (Object[])new Object[0]));
    }

    @Override
    public KeyframeChange createChange() {
        return new KeyframeChangeFreeze(this.frozen, Math.max(0, Math.min(10, this.frozenDelay)));
    }

    @Override
    public KeyframeChange createSmoothInterpolatedChange(Keyframe p1, Keyframe p2, Keyframe p3, float t0, float t1, float t2, float t3, float amount) {
        return this.createChange();
    }

    @Override
    public KeyframeChange createHermiteInterpolatedChange(Map<Float, Keyframe> keyframes, float amount) {
        return this.createChange();
    }

    public static class TypeAdapter
    implements JsonSerializer<FreezeKeyframe>,
    JsonDeserializer<FreezeKeyframe> {
        public FreezeKeyframe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            boolean freeze = jsonObject.get("freeze").getAsBoolean();
            int freezeDelay = jsonObject.has("freezeDelay") ? jsonObject.get("freezeDelay").getAsInt() : 0;
            InterpolationType interpolationType = (InterpolationType)context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class);
            return new FreezeKeyframe(freeze, freezeDelay, interpolationType);
        }

        public JsonElement serialize(FreezeKeyframe src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("freeze", Boolean.valueOf(src.frozen));
            jsonObject.addProperty("freezeDelay", (Number)src.frozenDelay);
            jsonObject.addProperty("type", "freeze");
            jsonObject.add("interpolation_type", context.serialize((Object)src.interpolationType()));
            return jsonObject;
        }
    }
}

