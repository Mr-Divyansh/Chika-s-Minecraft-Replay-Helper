/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.record;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.moulberry.flashback.FlashbackGson;
import com.moulberry.flashback.record.FlashbackChunkMeta;
import com.moulberry.flashback.record.ReplayMarker;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class FlashbackMeta {
    public UUID replayIdentifier = UUID.randomUUID();
    public String name = "Unnamed";
    public String versionString = null;
    public String worldName = null;
    public String bobbyWorldName = null;
    public int dataVersion = 0;
    public int protocolVersion = 0;
    public TreeMap<Integer, ReplayMarker> replayMarkers = new TreeMap();
    public int totalTicks = -1;
    public LinkedHashMap<String, FlashbackChunkMeta> chunks = new LinkedHashMap();
    public LinkedHashMap<String, LinkedHashSet<String>> namespacesForRegistries = null;
    public Map<String, File> distantHorizonPaths = new HashMap<String, File>();

    public JsonObject toJson() {
        JsonObject meta = new JsonObject();
        meta.addProperty("uuid", this.replayIdentifier.toString());
        meta.addProperty("name", this.name);
        if (this.versionString != null) {
            meta.addProperty("version_string", this.versionString);
        }
        if (this.worldName != null) {
            meta.addProperty("world_name", this.worldName);
        }
        if (this.dataVersion != 0) {
            meta.addProperty("data_version", (Number)this.dataVersion);
        }
        if (this.protocolVersion != 0) {
            meta.addProperty("protocol_version", (Number)this.protocolVersion);
        }
        if (this.bobbyWorldName != null) {
            meta.addProperty("bobby_world_name", this.bobbyWorldName);
        }
        if (this.totalTicks > 0) {
            meta.addProperty("total_ticks", (Number)this.totalTicks);
        }
        if (!this.replayMarkers.isEmpty()) {
            JsonObject jsonMarkers = new JsonObject();
            for (Map.Entry<Integer, ReplayMarker> entry : this.replayMarkers.entrySet()) {
                jsonMarkers.add(String.valueOf(entry.getKey()), FlashbackGson.COMPRESSED.toJsonTree((Object)entry.getValue()));
            }
            meta.add("markers", (JsonElement)jsonMarkers);
        }
        if (!this.distantHorizonPaths.isEmpty()) {
            JsonObject distantHorizonPaths = new JsonObject();
            for (Map.Entry<Object, Object> entry : this.distantHorizonPaths.entrySet()) {
                distantHorizonPaths.addProperty((String)entry.getKey(), ((File)entry.getValue()).getPath());
            }
            meta.add("distantHorizonPaths", (JsonElement)distantHorizonPaths);
        }
        if (this.namespacesForRegistries != null) {
            JsonObject registriesObj = new JsonObject();
            for (Map.Entry<Object, Object> entry : this.namespacesForRegistries.entrySet()) {
                JsonArray namespacesList = new JsonArray();
                for (String namespace : (LinkedHashSet)entry.getValue()) {
                    namespacesList.add(namespace);
                }
                registriesObj.add((String)entry.getKey(), (JsonElement)namespacesList);
            }
            meta.add("customNamespacesForRegistries", (JsonElement)registriesObj);
        }
        JsonObject chunksJson = new JsonObject();
        for (Map.Entry<Object, Object> entry : this.chunks.entrySet()) {
            chunksJson.add((String)entry.getKey(), (JsonElement)((FlashbackChunkMeta)entry.getValue()).toJson());
        }
        meta.add("chunks", (JsonElement)chunksJson);
        return meta;
    }

    @Nullable
    public static FlashbackMeta fromJson(JsonObject meta) {
        FlashbackMeta flashbackMeta = new FlashbackMeta();
        if (!meta.has("uuid")) {
            return null;
        }
        flashbackMeta.replayIdentifier = UUID.fromString(meta.get("uuid").getAsString());
        if (!meta.has("name")) {
            return null;
        }
        flashbackMeta.name = meta.get("name").getAsString();
        if (meta.has("version_string")) {
            flashbackMeta.versionString = meta.get("version_string").getAsString();
        }
        if (meta.has("world_name")) {
            flashbackMeta.worldName = meta.get("world_name").getAsString();
        }
        if (meta.has("data_version")) {
            flashbackMeta.dataVersion = meta.get("data_version").getAsInt();
        }
        if (meta.has("protocol_version")) {
            flashbackMeta.protocolVersion = meta.get("protocol_version").getAsInt();
        }
        if (meta.has("bobby_world_name")) {
            flashbackMeta.bobbyWorldName = meta.get("bobby_world_name").getAsString();
        }
        if (meta.has("total_ticks")) {
            flashbackMeta.totalTicks = meta.get("total_ticks").getAsInt();
        }
        if (meta.has("markers")) {
            JsonObject markers = meta.getAsJsonObject("markers");
            for (Map.Entry entry : markers.entrySet()) {
                try {
                    int tick = Integer.parseInt((String)entry.getKey());
                    flashbackMeta.replayMarkers.put(tick, (ReplayMarker)FlashbackGson.COMPRESSED.fromJson((JsonElement)entry.getValue(), ReplayMarker.class));
                }
                catch (Exception tick) {}
            }
        }
        if (meta.has("distantHorizonPaths")) {
            JsonObject distantHorizonPaths = meta.getAsJsonObject("distantHorizonPaths");
            for (Map.Entry entry : distantHorizonPaths.entrySet()) {
                flashbackMeta.distantHorizonPaths.put((String)entry.getKey(), new File(((JsonElement)entry.getValue()).getAsString()));
            }
        }
        if (meta.has("customNamespacesForRegistries")) {
            flashbackMeta.namespacesForRegistries = new LinkedHashMap();
            JsonObject registriesObj = meta.getAsJsonObject("customNamespacesForRegistries");
            for (String registryName : registriesObj.keySet()) {
                LinkedHashSet<String> namespaces = new LinkedHashSet<String>();
                for (JsonElement jsonElement : registriesObj.get(registryName).getAsJsonArray()) {
                    namespaces.add(jsonElement.getAsString());
                }
                flashbackMeta.namespacesForRegistries.put(registryName, namespaces);
            }
        }
        if (!meta.has("chunks")) {
            return null;
        }
        JsonObject chunksJson = meta.getAsJsonObject("chunks");
        for (Map.Entry entry : chunksJson.entrySet()) {
            FlashbackChunkMeta chunkMeta = FlashbackChunkMeta.fromJson(((JsonElement)entry.getValue()).getAsJsonObject());
            if (chunkMeta == null) {
                return null;
            }
            flashbackMeta.chunks.put((String)entry.getKey(), chunkMeta);
        }
        return flashbackMeta;
    }
}

