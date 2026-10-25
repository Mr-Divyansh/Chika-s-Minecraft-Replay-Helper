/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.mojang.authlib.properties.PropertyMap
 *  com.mojang.authlib.properties.PropertyMap$Serializer
 *  org.joml.Quaterniond
 *  org.joml.Quaternionf
 *  org.joml.Vector3d
 *  org.joml.Vector3f
 */
package com.moulberry.flashback;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeRegistry;
import com.moulberry.flashback.keyframe.KeyframeType;
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
import com.moulberry.flashback.serialization.QuaterniondTypeAdapater;
import com.moulberry.flashback.serialization.QuaternionfTypeAdapater;
import com.moulberry.flashback.serialization.Vector3dTypeAdapater;
import com.moulberry.flashback.serialization.Vector3fTypeAdapater;
import com.moulberry.flashback.state.EditorSceneHistoryAction;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class FlashbackGson {
    public static Gson PRETTY = FlashbackGson.build().setPrettyPrinting().create();
    public static Gson COMPRESSED = FlashbackGson.build().create();

    private static GsonBuilder build() {
        return new GsonBuilder().registerTypeAdapter(Vector3f.class, (Object)new Vector3fTypeAdapater()).registerTypeAdapter(Quaternionf.class, (Object)new QuaternionfTypeAdapater()).registerTypeAdapter(Vector3d.class, (Object)new Vector3dTypeAdapater()).registerTypeAdapter(Quaterniond.class, (Object)new QuaterniondTypeAdapater()).registerTypeAdapter(PropertyMap.class, (Object)new PropertyMap.Serializer()).registerTypeHierarchyAdapter(KeyframeType.class, (Object)new KeyframeRegistry.TypeAdapter()).registerTypeAdapter(CameraKeyframe.class, (Object)new CameraKeyframe.TypeAdapter()).registerTypeAdapter(CameraOrbitKeyframe.class, (Object)new CameraOrbitKeyframe.TypeAdapter()).registerTypeAdapter(TrackEntityKeyframe.class, (Object)new TrackEntityKeyframe.TypeAdapter()).registerTypeAdapter(FOVKeyframe.class, (Object)new FOVKeyframe.TypeAdapter()).registerTypeAdapter(CameraShakeKeyframe.class, (Object)new CameraShakeKeyframe.TypeAdapter()).registerTypeAdapter(TickrateKeyframe.class, (Object)new TickrateKeyframe.TypeAdapter()).registerTypeAdapter(TimelapseKeyframe.class, (Object)new TimelapseKeyframe.TypeAdapter()).registerTypeAdapter(TimeOfDayKeyframe.class, (Object)new TimeOfDayKeyframe.TypeAdapter()).registerTypeAdapter(FreezeKeyframe.class, (Object)new FreezeKeyframe.TypeAdapter()).registerTypeAdapter(BlockOverrideKeyframe.class, (Object)new BlockOverrideKeyframe.TypeAdapter()).registerTypeAdapter(AudioKeyframe.class, (Object)new AudioKeyframe.TypeAdapter()).registerTypeAdapter(Keyframe.class, (Object)new Keyframe.TypeAdapter()).registerTypeAdapter(EditorSceneHistoryAction.SetKeyframe.class, (Object)new EditorSceneHistoryAction.SetKeyframe.TypeAdapter()).registerTypeAdapter(EditorSceneHistoryAction.RemoveKeyframe.class, (Object)new EditorSceneHistoryAction.RemoveKeyframe.TypeAdapter()).registerTypeAdapter(EditorSceneHistoryAction.AddTrack.class, (Object)new EditorSceneHistoryAction.AddTrack.TypeAdapter()).registerTypeAdapter(EditorSceneHistoryAction.RemoveTrack.class, (Object)new EditorSceneHistoryAction.RemoveTrack.TypeAdapter()).registerTypeAdapter(EditorSceneHistoryAction.class, (Object)new EditorSceneHistoryAction.TypeAdapter());
    }
}

