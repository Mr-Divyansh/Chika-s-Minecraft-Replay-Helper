/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.lwjgl.system.MemoryUtil
 */
package com.moulberry.flashback.exporting;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.SneakyThrow;
import com.moulberry.flashback.combo_options.AudioCodec;
import com.moulberry.flashback.exporting.EncoderQuirks;
import com.moulberry.flashback.exporting.ExportSettings;
import com.moulberry.flashback.exporting.FlashbackFFmpegFrameRecorder;
import com.moulberry.flashback.exporting.ImageFrame;
import com.moulberry.flashback.exporting.PixelFormatHelper;
import com.moulberry.flashback.exporting.VideoWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.ffmpeg.global.swscale;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacv.FFmpegLogCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryUtil;

public class AsyncFFmpegVideoWriter
implements AutoCloseable,
VideoWriter {
    private ExportSettings settings;
    private String filename;
    private boolean started = false;
    @Nullable
    private ArrayBlockingQueue<ImageFrame> rescaleQueue;
    private ArrayBlockingQueue<ImageFrame> encodeQueue;
    @Nullable
    private ArrayBlockingQueue<Long> reusePictureData;
    private final AtomicBoolean finishRescaleThread = new AtomicBoolean(false);
    private final AtomicBoolean finishEncodeThread = new AtomicBoolean(false);
    private final AtomicBoolean finishedWriting = new AtomicBoolean(false);
    private final AtomicReference<Throwable> threadedError = new AtomicReference<Object>(null);

    public AsyncFFmpegVideoWriter(ExportSettings settings, String filename) {
        this.settings = settings;
        this.filename = filename;
    }

    public void tryStart(int srcPixelFormat) {
        if (this.started) {
            return;
        }
        this.started = true;
        try {
            FFmpegLogCallback.set();
            boolean wantTransparency = this.settings.transparent();
            int dstPixelFormat = PixelFormatHelper.getBestPixelFormat(this.settings.encoder(), srcPixelFormat, wantTransparency);
            Flashback.LOGGER.info("Starting export. Container={}. Codec={}. Encoder={}, Format={}", new Object[]{this.settings.container().text(), this.settings.codec().text(), this.settings.encoder(), PixelFormatHelper.pixelFormatToString(dstPixelFormat)});
            int width = this.settings.resolutionX();
            int height = this.settings.resolutionY();
            double scaleUpFactor = 1.0;
            double scaleDownFactor = 1.0;
            int minimumSize = EncoderQuirks.minimumFrameSize(this.settings.encoder());
            scaleUpFactor = Math.max(scaleUpFactor, (double)minimumSize / (double)width);
            scaleUpFactor = Math.max(scaleUpFactor, (double)minimumSize / (double)height);
            int maximumSize = EncoderQuirks.maximumFrameSize(this.settings.encoder());
            scaleDownFactor = Math.min(scaleDownFactor, (double)maximumSize / (double)width);
            scaleDownFactor = Math.min(scaleDownFactor, (double)maximumSize / (double)height);
            int maximumArea = EncoderQuirks.maximumFrameArea(this.settings.encoder());
            scaleDownFactor = Math.min(scaleDownFactor, Math.sqrt((double)maximumArea / (double)width / (double)height));
            if (scaleUpFactor != 1.0 && scaleDownFactor != 1.0) {
                width = Math.max(minimumSize, Math.min(maximumSize, width));
                height = Math.max(minimumSize, Math.min(maximumSize, height));
            } else if (scaleUpFactor != 1.0) {
                width = (int)Math.ceil(scaleUpFactor * (double)width);
                height = (int)Math.ceil(scaleUpFactor * (double)height);
            } else if (scaleDownFactor != 1.0) {
                width = (int)Math.floor(scaleDownFactor * (double)width);
                height = (int)Math.floor(scaleDownFactor * (double)height);
            }
            boolean needsRescale = srcPixelFormat != dstPixelFormat || width != this.settings.resolutionX() || height != this.settings.resolutionY();
            int maxBitrate = (int)Math.min(2.88E8, 4096.0 + (double)((long)avutil.av_image_get_buffer_size(dstPixelFormat, width, height, 1) * 8L) * this.settings.framerate());
            if (this.settings.encoder().equals("libsvtav1")) {
                maxBitrate = Math.min(100000000, maxBitrate);
            }
            int bitrate = this.settings.bitrate() <= 0 ? maxBitrate : Math.min(this.settings.bitrate(), maxBitrate);
            double fps = this.settings.framerate();
            String extension = this.settings.container().extension();
            int audioChannels = 0;
            if (this.settings.recordAudio()) {
                audioChannels = this.settings.audioCodec() == AudioCodec.VORBIS || this.settings.stereoAudio() ? 2 : 1;
            }
            FlashbackFFmpegFrameRecorder recorder = new FlashbackFFmpegFrameRecorder(this.filename, width, height, audioChannels);
            recorder.setVideoBitrate(bitrate);
            recorder.setVideoCodec(this.settings.codec().codecId());
            recorder.setVideoCodecName(this.settings.encoder());
            recorder.setFormat(extension);
            recorder.setFrameRate(fps);
            recorder.setPixelFormat(dstPixelFormat);
            recorder.setGopSize((int)Math.max(20.0, Math.min(240.0, Math.ceil(fps * 2.0))));
            if (this.settings.container().isImageSequence() && this.settings.pngSequenceFormat() == null) {
                recorder.setMuxerOption("update", "1");
            }
            if (this.settings.encoder().equals("exr")) {
                recorder.setVideoOption("compression", "zip1");
            }
            if (this.settings.bitrate() == 0) {
                if (this.settings.encoder().endsWith("_nvenc")) {
                    recorder.setVideoOption("preset", "p7");
                } else if (this.settings.encoder().endsWith("_amf")) {
                    recorder.setVideoOption("quality", "quality");
                } else if (this.settings.encoder().equals("libx264")) {
                    recorder.setVideoOption("preset", "slower");
                }
            }
            if (this.settings.recordAudio()) {
                recorder.setAudioCodec(this.settings.audioCodec().codecId());
                recorder.setSampleFormat(8);
                recorder.setSampleRate(48000);
                recorder.setAudioBitrate(256000);
            }
            recorder.start();
            this.encodeQueue = new ArrayBlockingQueue(needsRescale ? 24 : 32);
            this.rescaleQueue = needsRescale ? new ArrayBlockingQueue(8) : null;
            this.reusePictureData = needsRescale ? new ArrayBlockingQueue(32) : null;
            Thread encodeThread = this.createEncodeThread(recorder);
            if (needsRescale) {
                Thread rescaleThread = this.createRescaleThread(width, height, dstPixelFormat);
                rescaleThread.start();
            }
            encodeThread.start();
        }
        catch (IOException e) {
            throw SneakyThrow.sneakyThrow(e);
        }
    }

    @NotNull
    private Thread createEncodeThread(FlashbackFFmpegFrameRecorder recorder) {
        Thread encodeThread = new Thread(() -> {
            while (true) {
                ImageFrame src;
                try {
                    src = this.encodeQueue.poll(10L, TimeUnit.MILLISECONDS);
                }
                catch (InterruptedException e) {
                    continue;
                }
                try {
                    if (src == null) {
                        if (!this.finishEncodeThread.get()) continue;
                        recorder.stop();
                        recorder.release();
                        this.finishedWriting.set(true);
                        return;
                    }
                    ByteBuffer buffer = MemoryUtil.memByteBuffer((long)src.pixels, (int)((int)src.size));
                    recorder.recordImage(src.width, src.height, src.ffmpegPixelFormat(), buffer);
                    if (src.audioBuffer != null) {
                        recorder.recordSamples(src.audioBuffer);
                    }
                    if (this.reusePictureData == null || !this.reusePictureData.offer(src.pixels)) continue;
                    src = null;
                    continue;
                }
                catch (Throwable t) {
                    try {
                        recorder.release();
                    }
                    catch (FlashbackFFmpegFrameRecorder.Exception e) {
                        e.printStackTrace();
                    }
                    this.threadedError.set(t);
                    this.finishRescaleThread.set(true);
                    this.finishEncodeThread.set(true);
                    this.finishedWriting.set(true);
                    return;
                }
                finally {
                    if (src == null) continue;
                    src.close();
                    continue;
                }
                break;
            }
        });
        encodeThread.setName("Video Encode Thread");
        return encodeThread;
    }

    private Thread createRescaleThread(int dstWidth, int dstHeight, int dstPixelFormat) {
        int dstSize = avutil.av_image_get_buffer_size(dstPixelFormat, dstWidth, dstHeight, 1);
        AVFrame picture = avutil.av_frame_alloc();
        if (picture == null) {
            throw new RuntimeException("av_frame_alloc() error: Could not allocate picture.");
        }
        AVFrame tmp_picture = avutil.av_frame_alloc();
        if (tmp_picture == null) {
            throw new RuntimeException("av_frame_alloc() error: Could not allocate tmp_picture.");
        }
        PointerPointer tmp_picture_ptr = new PointerPointer(tmp_picture);
        PointerPointer picture_ptr = new PointerPointer(picture);
        Flashback.LOGGER.info("Rescaling to pixel format: {}", (Object)PixelFormatHelper.pixelFormatToString(dstPixelFormat));
        boolean useItu709Colorspace = PixelFormatHelper.isYuvFormat(dstPixelFormat);
        Thread scaleThread = new Thread(() -> this.lambda$createRescaleThread$0(picture, tmp_picture, dstWidth, dstHeight, dstPixelFormat, useItu709Colorspace, dstSize, tmp_picture_ptr, picture_ptr));
        scaleThread.setName("Image Rescale Thread");
        return scaleThread;
    }

    private void checkEncodeError(@Nullable AutoCloseable closeable) {
        Throwable t = this.threadedError.get();
        if (t != null) {
            this.finishRescaleThread.set(true);
            this.finishEncodeThread.set(true);
            this.finishedWriting.set(true);
            if (closeable != null) {
                try {
                    closeable.close();
                }
                catch (Exception e) {
                    Flashback.LOGGER.error("Error while trying to close passed AutoClosable", (Throwable)e);
                }
            }
            SneakyThrow.sneakyThrow(t);
        }
    }

    @Override
    public void encode(ImageFrame src) {
        this.tryStart(src.ffmpegPixelFormat());
        this.checkEncodeError(src);
        if (this.finishRescaleThread.get() || this.finishEncodeThread.get() || this.finishedWriting.get()) {
            src.close();
            throw new IllegalStateException("Cannot encode after finish()");
        }
        while (true) {
            try {
                if (this.rescaleQueue != null) {
                    this.rescaleQueue.put(src);
                    break;
                }
                this.encodeQueue.put(src);
            }
            catch (InterruptedException interruptedException) {
                this.checkEncodeError(src);
                continue;
            }
            break;
        }
    }

    @Override
    public void finish(Consumer<String> wait) {
        if (!this.started) {
            return;
        }
        this.checkEncodeError(null);
        if (this.rescaleQueue != null) {
            while (!this.rescaleQueue.isEmpty()) {
                this.checkEncodeError(null);
                LockSupport.parkNanos("waiting for rescale queue to empty", 100000L);
                wait.accept("rescale");
            }
        }
        while (!this.encodeQueue.isEmpty()) {
            this.checkEncodeError(null);
            LockSupport.parkNanos("waiting for encode queue to empty", 100000L);
            wait.accept("encode queue");
        }
        this.finishRescaleThread.set(true);
        if (this.rescaleQueue == null) {
            this.finishEncodeThread.set(true);
        }
        while (!this.finishedWriting.get()) {
            LockSupport.parkNanos("waiting for encoder thread to finish", 100000L);
            wait.accept("thread finish");
        }
        this.checkEncodeError(null);
    }

    @Override
    public void close() {
        if (!this.started) {
            return;
        }
        if (this.rescaleQueue != null) {
            for (ImageFrame src : this.rescaleQueue) {
                src.close();
            }
        }
        for (ImageFrame src : this.encodeQueue) {
            src.close();
        }
        this.finishRescaleThread.set(true);
        this.finishEncodeThread.set(true);
        while (!this.finishedWriting.get()) {
            LockSupport.parkNanos("waiting for encoder thread to finish", 100000L);
        }
        if (this.reusePictureData != null) {
            for (Long address : this.reusePictureData) {
                if (address == null || address == 0L) continue;
                MemoryUtil.nmemFree((long)address);
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    private /* synthetic */ void lambda$createRescaleThread$0(AVFrame picture, AVFrame tmp_picture, int dstWidth, int dstHeight, int dstPixelFormat, boolean useItu709Colorspace, final int dstSize, PointerPointer tmp_picture_ptr, PointerPointer picture_ptr) {
        img_convert_ctx = null;
        try {
            while (true) lbl-1000:
            // 4 sources

            {
                src = this.rescaleQueue.poll(10L, TimeUnit.MILLISECONDS);
                try {
                    if (src == null) {
                        if (!this.finishRescaleThread.get()) ** GOTO lbl-1000
                        avutil.av_frame_free(picture);
                        avutil.av_frame_free(tmp_picture);
                        swscale.sws_freeContext(img_convert_ctx);
                        this.finishEncodeThread.set(true);
                        return;
                    }
                    if ((img_convert_ctx = swscale.sws_getCachedContext(img_convert_ctx, src.width, src.height, src.ffmpegPixelFormat(), dstWidth, dstHeight, dstPixelFormat, 270848, null, null, (DoublePointer)null)) == null) {
                        throw new RuntimeException("sws_getCachedContext() error: Cannot initialize the conversion context.");
                    }
                    if (useItu709Colorspace) {
                        coefficients = swscale.sws_getCoefficients(1);
                        swscale.sws_setColorspaceDetails(img_convert_ctx, coefficients, 1, coefficients, 0, 0, 65536, 65536);
                    }
                    data = new BytePointer(this){
                        {
                            Objects.requireNonNull(this$0);
                            this.address = src.pixels;
                            this.position = 0L;
                            this.limit = src.size;
                            this.capacity = src.size;
                        }
                    };
                    tempPointerAddressLong = this.reusePictureData.poll();
                    if (tempPointerAddressLong == null && (tempPointerAddressLong = Long.valueOf(MemoryUtil.nmemAlloc((long)dstSize))) == 0L) {
                        throw new OutOfMemoryError();
                    }
                    tempPointerAddress = tempPointerAddressLong;
                    tempPointer = new BytePointer(this){
                        {
                            Objects.requireNonNull(this$0);
                            this.address = tempPointerAddress;
                            this.position = 0L;
                            this.limit = dstSize;
                            this.capacity = dstSize;
                        }
                    };
                    avutil.av_image_fill_arrays(tmp_picture_ptr, tmp_picture.linesize(), data, src.ffmpegPixelFormat(), src.width, src.height, 1);
                    avutil.av_image_fill_arrays(picture_ptr, picture.linesize(), tempPointer, dstPixelFormat, dstWidth, dstHeight, 1);
                    tmp_picture.format(src.ffmpegPixelFormat());
                    tmp_picture.width(src.width);
                    tmp_picture.height(src.height);
                    picture.format(dstPixelFormat);
                    picture.width(dstWidth);
                    picture.height(dstHeight);
                    swscale.sws_scale(img_convert_ctx, tmp_picture_ptr, tmp_picture.linesize(), 0, src.height, picture_ptr, picture.linesize());
                    this.encodeQueue.put(new ImageFrame(tempPointerAddress, dstWidth, dstHeight, dstSize, dstPixelFormat, src.audioBuffer));
                }
                finally {
                    if (src == null) continue;
                    src.close();
                    continue;
                }
                break;
            }
        }
        catch (Throwable t) {
            try {
                avutil.av_frame_free(picture);
                avutil.av_frame_free(tmp_picture);
                swscale.sws_freeContext(img_convert_ctx);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            this.threadedError.set(t);
            this.finishRescaleThread.set(true);
            this.finishEncodeThread.set(true);
            this.finishedWriting.set(true);
            return;
        }
        ** GOTO lbl-1000
    }
}

