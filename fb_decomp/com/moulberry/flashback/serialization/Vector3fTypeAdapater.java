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
 *  org.joml.Vector3f
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
import org.joml.Vector3f;

public class Vector3fTypeAdapater
implements JsonSerializer<Vector3f>,
JsonDeserializer<Vector3f> {
    public Vector3f deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        if (jsonArray.size() != 3) {
            throw new IllegalStateException("Error deserializing Vector3f, expected array to have length 3, was length " + jsonArray.size() + " instead");
        }
        float x = jsonArray.get(0).getAsFloat();
        float y = jsonArray.get(1).getAsFloat();
        float z = jsonArray.get(2).getAsFloat();
        return new Vector3f(x, y, z);
    }

    public JsonElement serialize(Vector3f src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add((Number)Float.valueOf(src.x));
        jsonArray.add((Number)Float.valueOf(src.y));
        jsonArray.add((Number)Float.valueOf(src.z));
        return jsonArray;
    }
}

