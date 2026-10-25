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
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.keyframe;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.impl.AudioKeyframe;
import com.moulberry.flashback.keyframe.impl.BlockOverrideKeyframe;
import com.moulberry.flashback.keyframe.impl.CameraKeyframe;
import com.moulberry.flashback.keyframe.impl.CameraOrbitKeyframe;
import com.moulberry.flashback.keyframe.impl.CameraShakeKeyframe;
import com.moulberry.flashback.keyframe.impl.FOVKeyframe;
import com.moulberry.flashback.keyframe.impl.FreezeKeyframe;
import com.moulberry.flashback.keyframe.impl.TickrateKeyframe;
import com.moulberry.flashback.keyframe.impl.TimeOfDayKeyframe;
import com.moulberry.flashback.keyframe.impl.TimelapseKeyframe;
import com.moulberry.flashback.keyframe.impl.TrackEntityKeyframe;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import imgui.moulberry90.ImDrawList;
import java.lang.reflect.Type;
import java.lang.runtime.SwitchBootstraps;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;

public abstract class Keyframe {
    private InterpolationType interpolationType = InterpolationType.getDefault();

    public InterpolationType interpolationType() {
        return this.interpolationType;
    }

    public void interpolationType(InterpolationType interpolationType) {
        this.interpolationType = Objects.requireNonNullElse(interpolationType, InterpolationType.getDefault());
    }

    public abstract KeyframeType<?> keyframeType();

    public abstract Keyframe copy();

    @Nullable
    public abstract KeyframeChange createChange();

    @Nullable
    public abstract KeyframeChange createSmoothInterpolatedChange(Keyframe var1, Keyframe var2, Keyframe var3, float var4, float var5, float var6, float var7, float var8);

    @Nullable
    public abstract KeyframeChange createHermiteInterpolatedChange(Map<Float, Keyframe> var1, float var2);

    public float getCustomWidthInTicks() {
        return -1.0f;
    }

    public void renderEditKeyframe(Consumer<Consumer<Keyframe>> update) {
    }

    public void drawOnTimeline(ImDrawList drawList, int keyframeSize, float x, float y, int colour, float timelineScale, float minTimelineX, float maxTimelineX, int tick, TreeMap<Integer, Keyframe> keyframeTimes) {
        int easeSize = keyframeSize / 5;
        switch (this.interpolationType) {
            case SMOOTH: {
                drawList.addCircleFilled(x, y, (float)keyframeSize, colour);
                break;
            }
            case LINEAR: {
                drawList.addTriangleFilled(x - (float)keyframeSize, y, x, y - (float)keyframeSize, x, y + (float)keyframeSize, colour);
                drawList.addTriangleFilled(x + (float)keyframeSize, y, x, y + (float)keyframeSize, x, y - (float)keyframeSize, colour);
                break;
            }
            case EASE_IN: {
                drawList.addTriangleFilled(x - (float)keyframeSize, y - (float)keyframeSize, x - (float)easeSize, y - (float)keyframeSize, x - (float)easeSize, y, colour);
                drawList.addTriangleFilled(x - (float)keyframeSize, y + (float)keyframeSize, x - (float)easeSize, y, x - (float)easeSize, y + (float)keyframeSize, colour);
                drawList.addTriangleFilled(x + (float)keyframeSize, y, x + (float)easeSize, y + (float)keyframeSize, x + (float)easeSize, y - (float)keyframeSize, colour);
                drawList.addRectFilled(x - (float)easeSize, y - (float)keyframeSize, x + (float)easeSize, y + (float)keyframeSize, colour);
                break;
            }
            case EASE_OUT: {
                drawList.addTriangleFilled(x - (float)keyframeSize, y, x - (float)easeSize, y - (float)keyframeSize, x - (float)easeSize, y + (float)keyframeSize, colour);
                drawList.addTriangleFilled(x + (float)keyframeSize, y - (float)keyframeSize, x + (float)easeSize, y, x + (float)easeSize, y - (float)keyframeSize, colour);
                drawList.addTriangleFilled(x + (float)keyframeSize, y + (float)keyframeSize, x + (float)easeSize, y + (float)keyframeSize, x + (float)easeSize, y, colour);
                drawList.addRectFilled(x - (float)easeSize, y - (float)keyframeSize, x + (float)easeSize, y + (float)keyframeSize, colour);
                break;
            }
            case EASE_IN_OUT: {
                drawList.addTriangleFilled(x - (float)keyframeSize, y - (float)keyframeSize, x - (float)easeSize, y - (float)keyframeSize, x - (float)easeSize, y, colour);
                drawList.addTriangleFilled(x - (float)keyframeSize, y + (float)keyframeSize, x - (float)easeSize, y, x - (float)easeSize, y + (float)keyframeSize, colour);
                drawList.addTriangleFilled(x + (float)keyframeSize, y - (float)keyframeSize, x + (float)easeSize, y, x + (float)easeSize, y - (float)keyframeSize, colour);
                drawList.addTriangleFilled(x + (float)keyframeSize, y + (float)keyframeSize, x + (float)easeSize, y + (float)keyframeSize, x + (float)easeSize, y, colour);
                drawList.addRectFilled(x - (float)easeSize, y - (float)keyframeSize, x + (float)easeSize, y + (float)keyframeSize, colour);
                break;
            }
            case HOLD: {
                drawList.addRectFilled(x - (float)keyframeSize, y - (float)keyframeSize, x + (float)keyframeSize, y + (float)keyframeSize, colour);
                break;
            }
            case HERMITE: {
                drawList.addTriangleFilled(x, y - (float)keyframeSize, x + (float)keyframeSize, y + (float)keyframeSize, x - (float)keyframeSize, y + (float)keyframeSize, colour);
            }
        }
    }

    public static class TypeAdapter
    implements JsonSerializer<Keyframe>,
    JsonDeserializer<Keyframe> {
        public Keyframe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            if (!jsonObject.has("type")) {
                throw new RuntimeException("Unable to determine type of keyframe for: " + String.valueOf(jsonObject));
            }
            String type = jsonObject.get("type").getAsString();
            Keyframe keyframe = switch (type) {
                case "camera" -> (Keyframe)context.deserialize(json, CameraKeyframe.class);
                case "camera_orbit" -> (Keyframe)context.deserialize(json, CameraOrbitKeyframe.class);
                case "track_entity" -> (Keyframe)context.deserialize(json, TrackEntityKeyframe.class);
                case "fov" -> (Keyframe)context.deserialize(json, FOVKeyframe.class);
                case "tickrate" -> (Keyframe)context.deserialize(json, TickrateKeyframe.class);
                case "freeze" -> (Keyframe)context.deserialize(json, FreezeKeyframe.class);
                case "timelapse" -> (Keyframe)context.deserialize(json, TimelapseKeyframe.class);
                case "time" -> (Keyframe)context.deserialize(json, TimeOfDayKeyframe.class);
                case "camera_shake" -> (Keyframe)context.deserialize(json, CameraShakeKeyframe.class);
                case "block_override" -> (Keyframe)context.deserialize(json, BlockOverrideKeyframe.class);
                case "audio" -> (Keyframe)context.deserialize(json, AudioKeyframe.class);
                default -> throw new IllegalStateException("Unknown keyframe type: " + type);
            };
            keyframe.interpolationType((InterpolationType)context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class));
            return keyframe;
        }

        public JsonElement serialize(Keyframe src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject;
            Keyframe keyframe = src;
            Objects.requireNonNull(keyframe);
            Keyframe keyframe2 = keyframe;
            int n = 0;
            switch (SwitchBootstraps.typeSwitch("typeSwitch", new Object[]{CameraKeyframe.class, CameraOrbitKeyframe.class, FOVKeyframe.class, TickrateKeyframe.class, FreezeKeyframe.class, TimeOfDayKeyframe.class, CameraShakeKeyframe.class, BlockOverrideKeyframe.class, AudioKeyframe.class}, (Keyframe)keyframe2, n)) {
                case 0: {
                    CameraKeyframe cameraKeyframe = (CameraKeyframe)keyframe2;
                    jsonObject = (JsonObject)context.serialize((Object)cameraKeyframe);
                    jsonObject.addProperty("type", "camera");
                    break;
                }
                case 1: {
                    CameraOrbitKeyframe cameraOrbitKeyframe = (CameraOrbitKeyframe)keyframe2;
                    jsonObject = (JsonObject)context.serialize((Object)cameraOrbitKeyframe);
                    jsonObject.addProperty("type", "camera_orbit");
                    break;
                }
                case 2: {
                    FOVKeyframe fovKeyframe = (FOVKeyframe)keyframe2;
                    jsonObject = (JsonObject)context.serialize((Object)fovKeyframe);
                    jsonObject.addProperty("type", "fov");
                    break;
                }
                case 3: {
                    TickrateKeyframe tickrateKeyframe = (TickrateKeyframe)keyframe2;
                    jsonObject = (JsonObject)context.serialize((Object)tickrateKeyframe);
                    jsonObject.addProperty("type", "tickrate");
                    break;
                }
                case 4: {
                    FreezeKeyframe freezeKeyframe = (FreezeKeyframe)keyframe2;
                    jsonObject = (JsonObject)context.serialize((Object)freezeKeyframe);
                    jsonObject.addProperty("type", "freeze");
                    break;
                }
                case 5: {
                    TimeOfDayKeyframe timeOfDayKeyframe = (TimeOfDayKeyframe)keyframe2;
                    jsonObject = (JsonObject)context.serialize((Object)timeOfDayKeyframe);
                    jsonObject.addProperty("type", "time");
                    break;
                }
                case 6: {
                    CameraShakeKeyframe cameraShakeKeyframe = (CameraShakeKeyframe)keyframe2;
                    jsonObject = (JsonObject)context.serialize((Object)cameraShakeKeyframe);
                    jsonObject.addProperty("type", "camera_shake");
                    break;
                }
                case 7: {
                    BlockOverrideKeyframe blockOverrideKeyframe = (BlockOverrideKeyframe)keyframe2;
                    jsonObject = (JsonObject)context.serialize((Object)blockOverrideKeyframe);
                    jsonObject.addProperty("type", "block_override");
                    break;
                }
                case 8: {
                    AudioKeyframe audioKeyframe = (AudioKeyframe)keyframe2;
                    jsonObject = (JsonObject)context.serialize((Object)audioKeyframe);
                    jsonObject.addProperty("type", "audio");
                    break;
                }
                default: {
                    throw new IllegalStateException("Unknown keyframe type: " + String.valueOf(src.getClass()));
                }
            }
            jsonObject.add("interpolation_type", context.serialize((Object)src.interpolationType));
            return jsonObject;
        }
    }
}

