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
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.world.entity.Entity
 *  org.joml.Vector3d
 *  org.joml.Vector3dc
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
import com.moulberry.flashback.combo_options.TrackingBodyPart;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeTrackEntity;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import com.moulberry.flashback.keyframe.types.TrackEntityKeyframeType;
import com.moulberry.flashback.spline.CatmullRom;
import com.moulberry.flashback.spline.Hermite;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.type.ImString;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.Entity;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class TrackEntityKeyframe
extends Keyframe {
    public UUID target;
    public TrackingBodyPart trackingBodyPart;
    public float yawOffset;
    public float pitchOffset;
    public final Vector3d positionOffset;
    public final Vector3d viewOffset;
    public float roll;

    public TrackEntityKeyframe(UUID target, TrackingBodyPart trackingBodyPart, float yawOffset, float pitchOffset, Vector3d positionOffset, Vector3d viewOffset, float roll) {
        this(target, trackingBodyPart, yawOffset, pitchOffset, positionOffset, viewOffset, roll, InterpolationType.getDefault());
    }

    public TrackEntityKeyframe(UUID target, TrackingBodyPart trackingBodyPart, float yawOffset, float pitchOffset, Vector3d positionOffset, Vector3d viewOffset, float roll, InterpolationType interpolationType) {
        this.target = target;
        this.trackingBodyPart = trackingBodyPart;
        this.yawOffset = yawOffset;
        this.pitchOffset = pitchOffset;
        this.positionOffset = positionOffset;
        this.viewOffset = viewOffset;
        this.roll = roll;
        this.interpolationType(interpolationType);
    }

    @Override
    public KeyframeType<?> keyframeType() {
        return TrackEntityKeyframeType.INSTANCE;
    }

    @Override
    public Keyframe copy() {
        return new TrackEntityKeyframe(this.target, this.trackingBodyPart, this.yawOffset, this.pitchOffset, new Vector3d((Vector3dc)this.positionOffset), new Vector3d((Vector3dc)this.viewOffset), this.roll, this.interpolationType());
    }

    @Override
    public void renderEditKeyframe(Consumer<Consumer<Keyframe>> update) {
        TrackingBodyPart trackingBodyPart;
        ImString target = new ImString(this.target.toString());
        target.inputData.isResizable = true;
        if (ImGui.inputText(I18n.get((String)"flashback.entity_uuid", (Object[])new Object[0]), target)) {
            try {
                Entity entity;
                String uuidStr = ImGuiHelper.getString(target);
                UUID uuid = UUID.fromString(uuidStr);
                ClientLevel level = Minecraft.getInstance().level;
                if (level != null && (entity = (Entity)level.getEntities().get(uuid)) != null && entity != Minecraft.getInstance().player) {
                    update.accept(keyframe -> {
                        ((TrackEntityKeyframe)keyframe).target = uuid;
                    });
                }
            }
            catch (Exception uuidStr) {
                // empty catch block
            }
        }
        if ((trackingBodyPart = ImGuiHelper.enumCombo(I18n.get((String)"flashback.body_part", (Object[])new Object[0]), this.trackingBodyPart)) != this.trackingBodyPart) {
            update.accept(keyframe -> {
                ((TrackEntityKeyframe)keyframe).trackingBodyPart = trackingBodyPart;
            });
        }
        float[] input = new float[]{this.yawOffset};
        if (ImGuiHelper.inputFloat(I18n.get((String)"flashback.yaw_offset", (Object[])new Object[0]), input) && input[0] != this.yawOffset) {
            update.accept(keyframe -> {
                ((TrackEntityKeyframe)keyframe).yawOffset = input[0];
            });
        }
        input[0] = this.pitchOffset;
        if (ImGuiHelper.inputFloat(I18n.get((String)"flashback.pitch_offset", (Object[])new Object[0]), input) && input[0] != this.pitchOffset) {
            update.accept(keyframe -> {
                ((TrackEntityKeyframe)keyframe).pitchOffset = input[0];
            });
        }
        float[] positionOffset = new float[]{(float)this.positionOffset.x, (float)this.positionOffset.y, (float)this.positionOffset.z};
        if (ImGuiHelper.inputFloat(I18n.get((String)"flashback.position_offset", (Object[])new Object[0]), positionOffset)) {
            if ((double)positionOffset[0] != this.positionOffset.x) {
                update.accept(keyframe -> {
                    ((TrackEntityKeyframe)keyframe).positionOffset.x = positionOffset[0];
                });
            }
            if ((double)positionOffset[1] != this.positionOffset.y) {
                update.accept(keyframe -> {
                    ((TrackEntityKeyframe)keyframe).positionOffset.y = positionOffset[1];
                });
            }
            if ((double)positionOffset[2] != this.positionOffset.z) {
                update.accept(keyframe -> {
                    ((TrackEntityKeyframe)keyframe).positionOffset.z = positionOffset[2];
                });
            }
        }
        float[] viewOffset = new float[]{(float)this.viewOffset.x, (float)this.viewOffset.y, (float)this.viewOffset.z};
        if (ImGuiHelper.inputFloat(I18n.get((String)"flashback.view_offset", (Object[])new Object[0]), viewOffset)) {
            if ((double)viewOffset[0] != this.viewOffset.x) {
                update.accept(keyframe -> {
                    ((TrackEntityKeyframe)keyframe).viewOffset.x = viewOffset[0];
                });
            }
            if ((double)viewOffset[1] != this.viewOffset.y) {
                update.accept(keyframe -> {
                    ((TrackEntityKeyframe)keyframe).viewOffset.y = viewOffset[1];
                });
            }
            if ((double)viewOffset[2] != this.viewOffset.z) {
                update.accept(keyframe -> {
                    ((TrackEntityKeyframe)keyframe).viewOffset.z = viewOffset[2];
                });
            }
        }
        input[0] = this.roll;
        if (ImGuiHelper.inputFloat(I18n.get((String)"flashback.roll", (Object[])new Object[0]), input) && input[0] != this.roll) {
            update.accept(keyframe -> {
                ((TrackEntityKeyframe)keyframe).roll = input[0];
            });
        }
    }

    @Override
    public KeyframeChange createChange() {
        return new KeyframeChangeTrackEntity(this.target, this.trackingBodyPart, this.yawOffset, this.pitchOffset, this.positionOffset, this.viewOffset, this.roll);
    }

    @Override
    public KeyframeChange createSmoothInterpolatedChange(Keyframe p1, Keyframe p2, Keyframe p3, float t0, float t1, float t2, float t3, float amount) {
        float time1 = t1 - t0;
        float time2 = t2 - t0;
        float time3 = t3 - t0;
        UUID target = (double)amount < 0.5 ? ((TrackEntityKeyframe)p1).target : ((TrackEntityKeyframe)p2).target;
        TrackingBodyPart trackingBodyPart = (double)amount < 0.5 ? ((TrackEntityKeyframe)p1).trackingBodyPart : ((TrackEntityKeyframe)p2).trackingBodyPart;
        float yawOffset = CatmullRom.value(this.yawOffset, ((TrackEntityKeyframe)p1).yawOffset, ((TrackEntityKeyframe)p2).yawOffset, ((TrackEntityKeyframe)p3).yawOffset, time1, time2, time3, amount);
        float pitchOffset = CatmullRom.value(this.pitchOffset, ((TrackEntityKeyframe)p1).pitchOffset, ((TrackEntityKeyframe)p2).pitchOffset, ((TrackEntityKeyframe)p3).pitchOffset, time1, time2, time3, amount);
        Vector3d positionOffset = CatmullRom.position(this.positionOffset, ((TrackEntityKeyframe)p1).positionOffset, ((TrackEntityKeyframe)p2).positionOffset, ((TrackEntityKeyframe)p3).positionOffset, time1, time2, time3, amount);
        Vector3d viewOffset = CatmullRom.position(this.viewOffset, ((TrackEntityKeyframe)p1).viewOffset, ((TrackEntityKeyframe)p2).viewOffset, ((TrackEntityKeyframe)p3).viewOffset, time1, time2, time3, amount);
        float roll = CatmullRom.degrees(this.roll, ((TrackEntityKeyframe)p1).roll, ((TrackEntityKeyframe)p2).roll, ((TrackEntityKeyframe)p3).roll, time1, time2, time3, amount);
        return new KeyframeChangeTrackEntity(target, trackingBodyPart, yawOffset, pitchOffset, positionOffset, viewOffset, roll);
    }

    @Override
    public KeyframeChange createHermiteInterpolatedChange(Map<Float, Keyframe> keyframes, float amount) {
        float lowestTickDelta = Float.MAX_VALUE;
        UUID target = null;
        TrackingBodyPart trackingBodyPart = null;
        for (Map.Entry<Float, Keyframe> entry : keyframes.entrySet()) {
            float tickDelta = Math.abs(entry.getKey().floatValue() - amount);
            if (!(tickDelta < lowestTickDelta)) continue;
            lowestTickDelta = tickDelta;
            target = ((TrackEntityKeyframe)entry.getValue()).target;
            trackingBodyPart = ((TrackEntityKeyframe)entry.getValue()).trackingBodyPart;
        }
        float yawOffset = (float)Hermite.value(Maps.transformValues(keyframes, k -> ((TrackEntityKeyframe)k).yawOffset), amount);
        float pitchOffset = (float)Hermite.value(Maps.transformValues(keyframes, k -> ((TrackEntityKeyframe)k).pitchOffset), amount);
        Vector3d positionOffset = Hermite.position(Maps.transformValues(keyframes, k -> ((TrackEntityKeyframe)k).positionOffset), amount);
        Vector3d viewOffset = Hermite.position(Maps.transformValues(keyframes, k -> ((TrackEntityKeyframe)k).viewOffset), amount);
        double roll = Hermite.degrees(Maps.transformValues(keyframes, k -> ((TrackEntityKeyframe)k).roll), amount);
        return new KeyframeChangeTrackEntity(target, trackingBodyPart, yawOffset, pitchOffset, positionOffset, viewOffset, (float)roll);
    }

    public static class TypeAdapter
    implements JsonSerializer<TrackEntityKeyframe>,
    JsonDeserializer<TrackEntityKeyframe> {
        public TrackEntityKeyframe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            UUID target = (UUID)context.deserialize(jsonObject.get("target"), UUID.class);
            TrackingBodyPart bodyPart = (TrackingBodyPart)context.deserialize(jsonObject.get("bodyPart"), TrackingBodyPart.class);
            float yawOffset = jsonObject.get("yawOffset").getAsFloat();
            float pitchOffset = jsonObject.get("pitchOffset").getAsFloat();
            Vector3d positionOffset = (Vector3d)context.deserialize(jsonObject.get("positionOffset"), Vector3d.class);
            Vector3d viewOffset = (Vector3d)context.deserialize(jsonObject.get("viewOffset"), Vector3d.class);
            float roll = 0.0f;
            if (jsonObject.has("roll")) {
                roll = jsonObject.get("roll").getAsFloat();
            }
            InterpolationType interpolationType = (InterpolationType)context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class);
            if (positionOffset == null) {
                positionOffset = new Vector3d();
            }
            if (viewOffset == null) {
                viewOffset = new Vector3d();
            }
            return new TrackEntityKeyframe(target, bodyPart, yawOffset, pitchOffset, positionOffset, viewOffset, roll, interpolationType);
        }

        public JsonElement serialize(TrackEntityKeyframe src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("target", context.serialize((Object)src.target));
            jsonObject.add("bodyPart", context.serialize((Object)src.trackingBodyPart));
            jsonObject.addProperty("yawOffset", (Number)Float.valueOf(src.yawOffset));
            jsonObject.addProperty("pitchOffset", (Number)Float.valueOf(src.pitchOffset));
            jsonObject.add("positionOffset", context.serialize((Object)src.positionOffset));
            jsonObject.add("viewOffset", context.serialize((Object)src.viewOffset));
            jsonObject.addProperty("type", "track_entity");
            jsonObject.addProperty("roll", (Number)Float.valueOf(src.roll));
            jsonObject.add("interpolation_type", context.serialize((Object)src.interpolationType()));
            return jsonObject;
        }
    }
}

