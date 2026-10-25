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
package com.moulberry.flashback.keyframe.impl;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangePlayAudio;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import com.moulberry.flashback.keyframe.types.AudioKeyframeType;
import com.moulberry.flashback.sound.FlashbackAudioBuffer;
import com.moulberry.flashback.sound.FlashbackAudioManager;
import imgui.moulberry90.ImDrawList;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;

public class AudioKeyframe
extends Keyframe {
    public Path path;
    private FlashbackAudioBuffer audioBuffer = null;

    public AudioKeyframe(Path path) {
        this.path = path;
        this.interpolationType(InterpolationType.LINEAR);
    }

    @Override
    public KeyframeType<?> keyframeType() {
        return AudioKeyframeType.INSTANCE;
    }

    @Override
    public Keyframe copy() {
        return new AudioKeyframe(this.path);
    }

    @Override
    public InterpolationType interpolationType() {
        return InterpolationType.LINEAR;
    }

    @Override
    public void renderEditKeyframe(Consumer<Consumer<Keyframe>> update) {
    }

    @Override
    public KeyframeChange createChange() {
        return null;
    }

    @Override
    public KeyframeChange createSmoothInterpolatedChange(Keyframe p1, Keyframe p2, Keyframe p3, float t0, float t1, float t2, float t3, float amount) {
        throw new UnsupportedOperationException();
    }

    @Override
    public KeyframeChange createHermiteInterpolatedChange(Map<Float, Keyframe> keyframes, float amount) {
        throw new UnsupportedOperationException();
    }

    private void ensureAudioBufferLoaded() {
        if (this.audioBuffer == null) {
            this.audioBuffer = FlashbackAudioManager.getBuffer(this.path);
        }
    }

    @Nullable
    public KeyframeChangePlayAudio createAudioChange(int startTick, float seconds) {
        this.ensureAudioBufferLoaded();
        if (this.audioBuffer == FlashbackAudioBuffer.EMPTY) {
            return null;
        }
        if (seconds > this.audioBuffer.durationInSeconds()) {
            return null;
        }
        return new KeyframeChangePlayAudio(this.audioBuffer, startTick, seconds);
    }

    @Override
    public float getCustomWidthInTicks() {
        this.ensureAudioBufferLoaded();
        if (this.audioBuffer == FlashbackAudioBuffer.EMPTY) {
            return -1.0f;
        }
        return this.audioBuffer.durationInSeconds() * 20.0f;
    }

    @Override
    public void drawOnTimeline(ImDrawList drawList, int keyframeSize, float x, float y, int colour, float timelineScale, float minTimelineX, float maxTimelineX, int tick, TreeMap<Integer, Keyframe> keyframeTimes) {
        int waveformLength;
        this.ensureAudioBufferLoaded();
        int alpha = colour & 0xFF000000;
        if (this.audioBuffer == FlashbackAudioBuffer.EMPTY) {
            drawList.addRectFilled(x - (float)keyframeSize, y - (float)keyframeSize, x + (float)keyframeSize, y + (float)keyframeSize, alpha | 0x155FFF);
            drawList.addText(x + (float)keyframeSize + 4.0f, y - (float)keyframeSize, -15376385, "Error loading audio");
            return;
        }
        float durationInTicks = this.audioBuffer.durationInSeconds() * 20.0f;
        int drawLength = waveformLength = (int)(durationInTicks / timelineScale);
        Map.Entry<Integer, Keyframe> next = keyframeTimes.ceilingEntry(tick + 1);
        if (next != null) {
            int nextTick = next.getKey();
            drawLength = Math.min(drawLength, (int)((float)(nextTick - tick) / timelineScale));
        }
        int minSample = Math.max(0, (int)(minTimelineX - x));
        int maxSample = Math.min(drawLength, (int)(maxTimelineX - x));
        byte[] waveform = this.audioBuffer.getAveragedWaveform(waveformLength);
        drawList.addRectFilled(x, y - (float)keyframeSize, x + (float)drawLength, y + (float)keyframeSize, alpha);
        drawList.addRect(x, y - (float)keyframeSize - 1.0f, x + (float)drawLength, y + (float)keyframeSize + 1.0f, colour);
        for (int i = minSample; i < maxSample; ++i) {
            float max = y + (float)keyframeSize;
            float min = max - (float)(2 * keyframeSize * (waveform[i] - -128)) / 255.0f;
            drawList.addRectFilled(x + (float)i, min, x + (float)i + 1.0f, max, alpha | 0xE37D77);
        }
    }

    public static class TypeAdapter
    implements JsonSerializer<AudioKeyframe>,
    JsonDeserializer<AudioKeyframe> {
        public AudioKeyframe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String path = jsonObject.get("path").getAsString();
            return new AudioKeyframe(Path.of(path, new String[0]));
        }

        public JsonElement serialize(AudioKeyframe src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("path", src.path.toString());
            jsonObject.addProperty("type", "audio");
            return jsonObject;
        }
    }
}

