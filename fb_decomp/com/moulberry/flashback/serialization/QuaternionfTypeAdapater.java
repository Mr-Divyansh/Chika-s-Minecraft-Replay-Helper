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
 *  org.joml.Quaternionf
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
import org.joml.Quaternionf;

public class QuaternionfTypeAdapater
implements JsonSerializer<Quaternionf>,
JsonDeserializer<Quaternionf> {
    public Quaternionf deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        if (jsonArray.size() != 4) {
            throw new IllegalStateException("Error deserializing Quaternionf, expected array to have length 4, was length " + jsonArray.size() + " instead");
        }
        double x = jsonArray.get(0).getAsDouble();
        double y = jsonArray.get(1).getAsDouble();
        double z = jsonArray.get(2).getAsDouble();
        double w = jsonArray.get(3).getAsDouble();
        return new Quaternionf(x, y, z, w);
    }

    public JsonElement serialize(Quaternionf src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add((Number)Float.valueOf(src.x));
        jsonArray.add((Number)Float.valueOf(src.y));
        jsonArray.add((Number)Float.valueOf(src.z));
        jsonArray.add((Number)Float.valueOf(src.w));
        return jsonArray;
    }
}

