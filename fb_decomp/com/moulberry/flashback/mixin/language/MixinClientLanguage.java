/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.llamalad7.mixinextras.sugar.Local
 *  net.minecraft.client.resources.language.ClientLanguage
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.util.GsonHelper
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.language;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.GsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={ClientLanguage.class})
public class MixinClientLanguage {
    @Unique
    private static final Gson FLASHBACK_GSON = new Gson();

    @WrapOperation(method={"appendFrom"}, at={@At(value="INVOKE", target="Lnet/minecraft/locale/Language;loadFromJson(Ljava/io/InputStream;Ljava/util/function/BiConsumer;)V")})
    private static void appendFrom_loadFromFile(InputStream inputStream, BiConsumer<String, String> biConsumer, Operation<Void> original, @Local Resource resource) {
        if (Objects.equals(resource.sourcePackId(), "flashback")) {
            JsonObject jsonObject = (JsonObject)FLASHBACK_GSON.fromJson((Reader)new InputStreamReader(inputStream, StandardCharsets.UTF_8), JsonObject.class);
            for (Map.Entry entry : jsonObject.entrySet()) {
                if (((JsonElement)entry.getValue()).isJsonNull()) continue;
                biConsumer.accept((String)entry.getKey(), GsonHelper.convertToString((JsonElement)((JsonElement)entry.getValue()), (String)((String)entry.getKey())));
            }
        } else {
            original.call(new Object[]{inputStream, biConsumer});
        }
    }
}

