/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  org.joml.Vector3d
 */
package com.moulberry.flashback.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.joml.Vector3d;

public class Vector3dTypeAdapater
implements JsonSerializer<Vector3d>,
JsonDeserializer<Vector3d> {
    public Vector3d deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        if (jsonArray.size() != 3) {
            throw new IllegalStateException("Error deserializing Vector3f, expected array to have length 3, was length " + jsonArray.size() + " instead");
        }
        double x = jsonArray.get(0).getAsDouble();
        double y = jsonArray.get(1).getAsDouble();
        double z = jsonArray.get(2).getAsDouble();
        return new Vector3d(x, y, z);
    }

    public JsonElement serialize(Vector3d src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add((Number)src.x);
        jsonArray.add((Number)src.y);
        jsonArray.add((Number)src.z);
        return jsonArray;
    }
}

