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
 */
package com.moulberry.flashback.state;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.state.EditorScene;
import com.moulberry.flashback.state.KeyframeTrack;
import java.lang.reflect.Type;
import java.lang.runtime.SwitchBootstraps;
import java.util.Objects;

public interface EditorSceneHistoryAction {
    public void apply(EditorScene var1);

    public static class TypeAdapter
    implements JsonSerializer<EditorSceneHistoryAction>,
    JsonDeserializer<EditorSceneHistoryAction> {
        public EditorSceneHistoryAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String type;
            JsonObject jsonObject = json.getAsJsonObject();
            return switch (type = jsonObject.get("action_type").getAsString()) {
                case "set_keyframe" -> (EditorSceneHistoryAction)context.deserialize(json, SetKeyframe.class);
                case "remove_keyframe" -> (EditorSceneHistoryAction)context.deserialize(json, RemoveKeyframe.class);
                case "add_track" -> (EditorSceneHistoryAction)context.deserialize(json, AddTrack.class);
                case "remove_track" -> (EditorSceneHistoryAction)context.deserialize(json, RemoveTrack.class);
                default -> throw new IllegalStateException("Unknown action type: " + type);
            };
        }

        public JsonElement serialize(EditorSceneHistoryAction src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject;
            EditorSceneHistoryAction editorSceneHistoryAction = src;
            Objects.requireNonNull(editorSceneHistoryAction);
            EditorSceneHistoryAction editorSceneHistoryAction2 = editorSceneHistoryAction;
            int n = 0;
            switch (SwitchBootstraps.typeSwitch("typeSwitch", new Object[]{SetKeyframe.class, RemoveKeyframe.class, AddTrack.class, RemoveTrack.class}, (EditorSceneHistoryAction)editorSceneHistoryAction2, n)) {
                case 0: {
                    SetKeyframe setKeyframe = (SetKeyframe)editorSceneHistoryAction2;
                    jsonObject = (JsonObject)context.serialize((Object)setKeyframe);
                    jsonObject.addProperty("action_type", "set_keyframe");
                    break;
                }
                case 1: {
                    RemoveKeyframe removeKeyframe = (RemoveKeyframe)editorSceneHistoryAction2;
                    jsonObject = (JsonObject)context.serialize((Object)removeKeyframe);
                    jsonObject.addProperty("action_type", "remove_keyframe");
                    break;
                }
                case 2: {
                    AddTrack addTrack = (AddTrack)editorSceneHistoryAction2;
                    jsonObject = (JsonObject)context.serialize((Object)addTrack);
                    jsonObject.addProperty("action_type", "add_track");
                    break;
                }
                case 3: {
                    RemoveTrack removeTrack = (RemoveTrack)editorSceneHistoryAction2;
                    jsonObject = (JsonObject)context.serialize((Object)removeTrack);
                    jsonObject.addProperty("action_type", "remove_track");
                    break;
                }
                default: {
                    throw new IllegalStateException("Unknown action type: " + String.valueOf(src.getClass()));
                }
            }
            return jsonObject;
        }
    }

    public record RemoveTrack(KeyframeType<?> type, int trackIndex) implements EditorSceneHistoryAction
    {
        @Override
        public void apply(EditorScene editorScene) {
            if (this.trackIndex < editorScene.keyframeTracks.size()) {
                KeyframeTrack keyframeTrack = editorScene.keyframeTracks.get(this.trackIndex);
                if (keyframeTrack.keyframeType == this.type) {
                    editorScene.keyframeTracks.remove(this.trackIndex);
                }
            }
        }

        public static class TypeAdapter
        implements JsonSerializer<RemoveTrack>,
        JsonDeserializer<RemoveTrack> {
            public RemoveTrack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                KeyframeType type = (KeyframeType)context.deserialize(jsonObject.get("keyframe_type"), KeyframeType.class);
                int trackIndex = jsonObject.get("trackIndex").getAsInt();
                return new RemoveTrack(type, trackIndex);
            }

            public JsonElement serialize(RemoveTrack src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action_type", "remove_track");
                jsonObject.add("keyframe_type", context.serialize(src.type));
                jsonObject.addProperty("trackIndex", (Number)src.trackIndex);
                return jsonObject;
            }
        }
    }

    public record AddTrack(KeyframeType<?> type, int trackIndex) implements EditorSceneHistoryAction
    {
        @Override
        public void apply(EditorScene editorScene) {
            if (this.trackIndex <= editorScene.keyframeTracks.size()) {
                editorScene.keyframeTracks.add(this.trackIndex, new KeyframeTrack(this.type));
            }
        }

        public static class TypeAdapter
        implements JsonSerializer<AddTrack>,
        JsonDeserializer<AddTrack> {
            public AddTrack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                KeyframeType type = (KeyframeType)context.deserialize(jsonObject.get("keyframe_type"), KeyframeType.class);
                int trackIndex = jsonObject.get("trackIndex").getAsInt();
                return new AddTrack(type, trackIndex);
            }

            public JsonElement serialize(AddTrack src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action_type", "add_track");
                jsonObject.add("keyframe_type", context.serialize(src.type));
                jsonObject.addProperty("trackIndex", (Number)src.trackIndex);
                return jsonObject;
            }
        }
    }

    public record RemoveKeyframe(KeyframeType<?> type, int trackIndex, int tick) implements EditorSceneHistoryAction
    {
        @Override
        public void apply(EditorScene editorScene) {
            if (this.trackIndex < editorScene.keyframeTracks.size()) {
                KeyframeTrack track = editorScene.keyframeTracks.get(this.trackIndex);
                if (track.keyframeType == this.type) {
                    track.keyframesByTick.remove(this.tick);
                }
            }
        }

        public static class TypeAdapter
        implements JsonSerializer<RemoveKeyframe>,
        JsonDeserializer<RemoveKeyframe> {
            public RemoveKeyframe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                KeyframeType type = (KeyframeType)context.deserialize(jsonObject.get("keyframe_type"), KeyframeType.class);
                int trackIndex = jsonObject.get("trackIndex").getAsInt();
                int tick = jsonObject.get("tick").getAsInt();
                return new RemoveKeyframe(type, trackIndex, tick);
            }

            public JsonElement serialize(RemoveKeyframe src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action_type", "remove_keyframe");
                jsonObject.add("keyframe_type", context.serialize(src.type));
                jsonObject.addProperty("trackIndex", (Number)src.trackIndex);
                jsonObject.addProperty("tick", (Number)src.tick);
                return jsonObject;
            }
        }
    }

    public record SetKeyframe(KeyframeType<?> type, int trackIndex, int tick, Keyframe keyframe) implements EditorSceneHistoryAction
    {
        @Override
        public void apply(EditorScene editorScene) {
            if (this.trackIndex < editorScene.keyframeTracks.size()) {
                KeyframeTrack track = editorScene.keyframeTracks.get(this.trackIndex);
                if (track.keyframeType == this.type) {
                    track.keyframesByTick.put(this.tick, this.keyframe.copy());
                }
            }
        }

        public static class TypeAdapter
        implements JsonSerializer<SetKeyframe>,
        JsonDeserializer<SetKeyframe> {
            public SetKeyframe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                KeyframeType type = (KeyframeType)context.deserialize(jsonObject.get("keyframe_type"), KeyframeType.class);
                int trackIndex = jsonObject.get("trackIndex").getAsInt();
                int tick = jsonObject.get("tick").getAsInt();
                Keyframe keyframe = (Keyframe)context.deserialize(jsonObject.get("keyframe"), Keyframe.class);
                return new SetKeyframe(type, trackIndex, tick, keyframe);
            }

            public JsonElement serialize(SetKeyframe src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action_type", "set_keyframe");
                jsonObject.add("keyframe_type", context.serialize(src.type));
                jsonObject.addProperty("trackIndex", (Number)src.trackIndex);
                jsonObject.addProperty("tick", (Number)src.tick);
                jsonObject.add("keyframe", context.serialize((Object)src.keyframe));
                return jsonObject;
            }
        }
    }
}

