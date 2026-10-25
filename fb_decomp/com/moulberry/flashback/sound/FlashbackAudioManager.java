/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  com.mojang.blaze3d.audio.Channel
 *  com.mojang.blaze3d.audio.Library$Pool
 *  net.minecraft.client.sounds.ChannelAccess
 *  net.minecraft.client.sounds.ChannelAccess$ChannelHandle
 *  net.minecraft.client.sounds.SoundEngine
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.lwjgl.openal.AL10
 */
package com.moulberry.flashback.sound;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.audio.Channel;
import com.mojang.blaze3d.audio.Library;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.sound.FlashbackAudioBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.openal.AL10;

public class FlashbackAudioManager {
    private static final LoadingCache<Path, FlashbackAudioBuffer> audioBufferCache = CacheBuilder.newBuilder().weakValues().build((CacheLoader)new CacheLoader<Path, FlashbackAudioBuffer>(){

        public FlashbackAudioBuffer load(@NotNull Path path) {
            FlashbackAudioBuffer loaded = FlashbackAudioBuffer.load(path);
            if (loaded != null) {
                return loaded;
            }
            return FlashbackAudioBuffer.EMPTY;
        }
    });
    private static final List<PlayingAudioInstance> instances = new ArrayList<PlayingAudioInstance>();
    private static boolean handling = false;

    public static FlashbackAudioBuffer getBuffer(Path path) {
        return (FlashbackAudioBuffer)audioBufferCache.getUnchecked((Object)path);
    }

    public static void invalidateLoadedBuffers() {
        for (FlashbackAudioBuffer audioBuffer : audioBufferCache.asMap().values()) {
            audioBuffer.invalidate(true);
        }
    }

    public static void startHandling() {
        if (handling) {
            throw new IllegalStateException();
        }
        handling = true;
    }

    public static void finishHandling() {
        if (!handling) {
            throw new IllegalStateException();
        }
        handling = false;
        for (PlayingAudioInstance instance : instances) {
            if (!instance.claimed) {
                instance.channelHandle.execute(Channel::stop);
            }
            instance.claimed = false;
        }
    }

    public static void playAt(SoundEngine soundEngine, FlashbackAudioBuffer audioBuffer, int startTick, float seconds, float speed) {
        float clampedSpeed;
        if (!handling) {
            return;
        }
        PlayingAudioInstance instance = FlashbackAudioManager.findMatchingInstance(audioBuffer, startTick, seconds);
        if (instance == null) {
            instance = new PlayingAudioInstance(audioBuffer);
            instances.add(instance);
        }
        boolean updateSpeed = instance.speed != (clampedSpeed = Math.max(0.1f, Math.min(10.0f, speed)));
        instance.startTick = startTick;
        instance.seconds = seconds;
        instance.speed = clampedSpeed;
        instance.claimed = true;
        if (instance.channelHandle == null || instance.channelHandle.isStopped()) {
            ChannelAccess channelAccess = soundEngine.channelAccess;
            CompletableFuture handleFuture = channelAccess.createHandle(Library.Pool.STATIC);
            instance.channelHandle = (ChannelAccess.ChannelHandle)handleFuture.join();
            if (instance.channelHandle == null) {
                ReplayUI.setInfoOverlay("Unable to play audio: out of channels");
                return;
            }
            instance.channelHandle.execute(channel -> {
                channel.setPitch(clampedSpeed);
                channel.setVolume(1.0f);
                channel.disableAttenuation();
                channel.setLooping(false);
                channel.setSelfPosition(Vec3.ZERO);
                channel.setRelative(false);
                AL10.alGetError();
                channel.attachStaticBuffer(audioBuffer.soundBuffer());
                if (AL10.alGetError() != 0) {
                    Flashback.LOGGER.error("Error attaching sound buffer, invalidating and trying again");
                    audioBuffer.invalidate(false);
                    channel.attachStaticBuffer(audioBuffer.soundBuffer());
                    if (AL10.alGetError() != 0) {
                        Flashback.LOGGER.error("Error attaching sound buffer a second time... bailing");
                        return;
                    }
                }
                AL10.alSourcef((int)channel.source, (int)4132, (float)seconds);
                channel.play();
            });
        } else {
            instance.channelHandle.execute(channel -> {
                float secOffset;
                channel.unpause();
                if (updateSpeed) {
                    channel.setPitch(clampedSpeed);
                }
                if (Math.abs((secOffset = AL10.alGetSourcef((int)channel.source, (int)4132)) - seconds) > 0.05f) {
                    AL10.alSourcef((int)channel.source, (int)4132, (float)seconds);
                }
            });
        }
    }

    @Nullable
    private static PlayingAudioInstance findMatchingInstance(FlashbackAudioBuffer audioBuffer, int startTick, float seconds) {
        if (instances.isEmpty()) {
            return null;
        }
        for (PlayingAudioInstance instance : instances) {
            if (instance.audioBuffer != audioBuffer || instance.claimed || instance.startTick != startTick) continue;
            return instance;
        }
        float bestSecondsDelta = Float.MAX_VALUE;
        PlayingAudioInstance bestInstance = null;
        for (PlayingAudioInstance instance : instances) {
            float delta;
            if (instance.audioBuffer != audioBuffer || instance.claimed || !((delta = Math.abs(instance.seconds - seconds)) < bestSecondsDelta)) continue;
            bestSecondsDelta = delta;
            bestInstance = instance;
        }
        return bestInstance;
    }

    public static void stopAll() {
        if (handling) {
            throw new IllegalStateException();
        }
        for (PlayingAudioInstance instance : instances) {
            instance.channelHandle.execute(Channel::stop);
        }
        instances.clear();
    }

    public static void pauseAll() {
        if (handling) {
            throw new IllegalStateException();
        }
        for (PlayingAudioInstance instance : instances) {
            instance.channelHandle.execute(Channel::pause);
        }
    }

    private static class PlayingAudioInstance {
        private final FlashbackAudioBuffer audioBuffer;
        private ChannelAccess.ChannelHandle channelHandle;
        private int startTick;
        private float seconds;
        private float speed = 1.0f;
        private boolean claimed = false;

        public PlayingAudioInstance(FlashbackAudioBuffer audioBuffer) {
            this.audioBuffer = audioBuffer;
        }
    }
}

