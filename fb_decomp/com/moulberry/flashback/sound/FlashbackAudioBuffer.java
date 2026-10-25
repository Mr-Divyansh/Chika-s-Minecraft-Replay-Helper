/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.audio.SoundBuffer
 *  net.minecraft.client.Minecraft
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.sound;

import com.mojang.blaze3d.audio.SoundBuffer;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.GlobalCleaner;
import com.moulberry.flashback.sound.FFmpegAudioReader;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;

public class FlashbackAudioBuffer {
    public static FlashbackAudioBuffer EMPTY = new FlashbackAudioBuffer(48000, new byte[0], ByteBuffer.allocateDirect(0), new AudioFormat(48000.0f, 8, 1, true, false));
    public final int sampleRate;
    private final ByteBuffer rawAudioData;
    private final AudioFormat format;
    private final byte[] waveform;
    private final InnerCleanableState innerState;
    private byte[] averagedWaveform = null;

    public FlashbackAudioBuffer(int sampleRate, byte[] waveform, ByteBuffer rawAudioData, AudioFormat audioFormat) {
        this.sampleRate = sampleRate;
        this.waveform = waveform;
        this.rawAudioData = rawAudioData;
        this.format = audioFormat;
        InnerCleanableState innerCleanableState = new InnerCleanableState();
        GlobalCleaner.INSTANCE.register(this, innerCleanableState);
        this.innerState = innerCleanableState;
    }

    public void invalidate(boolean discard) {
        SoundBuffer soundBuffer = this.innerState.soundBuffer;
        this.innerState.soundBuffer = null;
        if (discard && soundBuffer != null) {
            soundBuffer.discardAlBuffer();
        }
    }

    public SoundBuffer soundBuffer() {
        if (this.innerState.soundBuffer == null) {
            this.innerState.soundBuffer = new SoundBuffer(this.rawAudioData, this.format);
        }
        return this.innerState.soundBuffer;
    }

    public float durationInSeconds() {
        return (float)this.waveform.length / (float)this.sampleRate;
    }

    public byte[] getAveragedWaveform(int length) {
        if (this.waveform.length == length) {
            return this.waveform;
        }
        if (this.averagedWaveform != null && this.averagedWaveform.length == length) {
            return this.averagedWaveform;
        }
        this.averagedWaveform = new byte[length];
        if (length == 0) {
            return this.averagedWaveform;
        }
        if (length > this.waveform.length) {
            System.arraycopy(this.waveform, 0, this.averagedWaveform, 0, this.waveform.length);
            return this.averagedWaveform;
        }
        for (int i = 0; i < length; ++i) {
            int from = (int)((long)i * (long)this.waveform.length / (long)length);
            int to = Math.min(this.waveform.length, (int)((long)(i + 1) * (long)this.waveform.length / (long)length));
            if (to <= from) continue;
            long total = 0L;
            for (int j = from; j < to; ++j) {
                total += (long)(this.waveform[j] - -128);
            }
            this.averagedWaveform[i] = (byte)(total / (long)(to - from) + -128L);
        }
        return this.averagedWaveform;
    }

    @Nullable
    static FFmpegAudioReader.RawAudioData readRaw(Path path) {
        FFmpegAudioReader.RawAudioData rawAudioData;
        BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(path, new OpenOption[0]));
        try {
            rawAudioData = FFmpegAudioReader.read(inputStream);
        }
        catch (Throwable throwable) {
            try {
                try {
                    ((InputStream)inputStream).close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        ((InputStream)inputStream).close();
        return rawAudioData;
    }

    @Nullable
    static FlashbackAudioBuffer load(Path path) {
        FFmpegAudioReader.RawAudioData rawData = FlashbackAudioBuffer.readRaw(path);
        if (rawData == null) {
            return null;
        }
        ByteBuffer byteBuffer = rawData.byteBuffer();
        AudioFormat format = rawData.audioFormat();
        byteBuffer = byteBuffer.order(format.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        int channels = format.getChannels();
        if (channels != 1 && channels != 2) {
            throw new RuntimeException("Unsupported channel count");
        }
        int bits = format.getSampleSizeInBits();
        if (bits != 8 && bits != 16) {
            throw new RuntimeException("Unsupported sample size");
        }
        if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            throw new RuntimeException("Encoding must be PCM_SIGNED");
        }
        boolean stereo = channels == 2;
        boolean readShort = bits == 16;
        int sampleCount = byteBuffer.remaining() / format.getFrameSize();
        byte[] waveform = new byte[sampleCount];
        float maxAmplitude = 1.0f;
        int amplitudeSampleWindow = (int)Math.ceil(format.getSampleRate() / 40.0f);
        while (byteBuffer.hasRemaining()) {
            long totalAmplitude = 0L;
            int totalSamples = 0;
            for (int i = 0; i < amplitudeSampleWindow && byteBuffer.hasRemaining(); ++i) {
                totalAmplitude = readShort ? (totalAmplitude += (long)Math.abs(byteBuffer.getShort())) : (totalAmplitude += (long)Math.abs(byteBuffer.get()));
                ++totalSamples;
            }
            float amplitudeOverWindow = (float)((double)totalAmplitude / (double)totalSamples);
            maxAmplitude = Math.max(maxAmplitude, amplitudeOverWindow);
        }
        byteBuffer.position(0);
        for (int i = 0; i < sampleCount; ++i) {
            float other;
            float v;
            if (readShort) {
                v = (float)Math.abs(byteBuffer.getShort()) / maxAmplitude;
                if (stereo) {
                    other = (float)Math.abs(byteBuffer.getShort()) / maxAmplitude;
                    v = (v + other) / 2.0f;
                }
                waveform[i] = (byte)(Math.max(0.0f, Math.min(255.0f, v * 255.0f)) + -128.0f);
                continue;
            }
            v = (float)Math.abs(byteBuffer.get()) / maxAmplitude;
            if (stereo) {
                other = (float)Math.abs(byteBuffer.get()) / maxAmplitude;
                v = (v + other) / 2.0f;
            }
            waveform[i] = (byte)(Math.max(0.0f, Math.min(255.0f, v * 255.0f)) + -128.0f);
        }
        byteBuffer.position(0);
        return new FlashbackAudioBuffer((int)format.getSampleRate(), waveform, byteBuffer, format);
    }

    private static class InnerCleanableState
    implements Runnable {
        private SoundBuffer soundBuffer = null;

        private InnerCleanableState() {
        }

        @Override
        public void run() {
            if (this.soundBuffer == null) {
                return;
            }
            SoundBuffer soundBuffer = this.soundBuffer;
            Minecraft.getInstance().execute(() -> {
                Flashback.LOGGER.info("Releasing audio buffer data because it's no longer used");
                soundBuffer.discardAlBuffer();
            });
            this.soundBuffer = null;
        }
    }
}

