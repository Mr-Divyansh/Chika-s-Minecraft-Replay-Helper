/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.NativeImage
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.exporting;

import com.mojang.blaze3d.platform.NativeImage;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.SneakyThrow;
import com.moulberry.flashback.exporting.ExportSettings;
import com.moulberry.flashback.exporting.ImageFrame;
import com.moulberry.flashback.exporting.VideoWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;

public class PNGSequenceVideoWriter
implements VideoWriter {
    private final ExportSettings settings;
    private int sequenceNumber = 0;
    private final AtomicBoolean finishEncodeThread = new AtomicBoolean(false);
    private final AtomicBoolean finishedWriting = new AtomicBoolean(false);
    private final AtomicReference<Throwable> threadedError = new AtomicReference<Object>(null);
    private final ArrayBlockingQueue<NativeImage> encodeQueue;

    public PNGSequenceVideoWriter(ExportSettings settings) {
        this.settings = settings;
        this.encodeQueue = new ArrayBlockingQueue(32);
        this.createEncodeThread().start();
    }

    private Thread createEncodeThread() {
        boolean outputIsDirectory = Files.isDirectory(this.settings.output(), new LinkOption[0]);
        boolean encodeMultiple = outputIsDirectory || this.settings.startTick() != this.settings.endTick();
        Thread encodeThread = new Thread(() -> {
            while (true) {
                NativeImage src;
                try {
                    src = this.encodeQueue.poll(10L, TimeUnit.MILLISECONDS);
                }
                catch (InterruptedException e) {
                    throw SneakyThrow.sneakyThrow(e);
                }
                try {
                    if (src == null) {
                        if (!this.finishEncodeThread.get()) continue;
                        this.finishedWriting.set(true);
                        return;
                    }
                    if (!this.settings.transparent() && src.format().hasAlpha()) {
                        int alpha = 255 << src.format().alphaOffset();
                        for (int y = 0; y < src.getHeight(); ++y) {
                            for (int x = 0; x < src.getWidth(); ++x) {
                                src.setPixel(x, y, src.getPixel(x, y) | alpha);
                            }
                        }
                    }
                    ++this.sequenceNumber;
                    Path output = this.settings.output();
                    if (encodeMultiple) {
                        Object filename;
                        String format = this.settings.pngSequenceFormat();
                        if (format == null) {
                            format = "%04d";
                        }
                        if (outputIsDirectory) {
                            try {
                                filename = String.format(format, this.sequenceNumber);
                            }
                            catch (Exception e) {
                                filename = String.format("%04d", this.sequenceNumber);
                            }
                            if (!((String)filename).endsWith(".png")) {
                                filename = (String)filename + ".png";
                            }
                            src.writeToFile(output.resolve((String)filename));
                            continue;
                        }
                        filename = output.getFileName().toString() + "-";
                        try {
                            filename = (String)filename + String.format(format, this.sequenceNumber);
                        }
                        catch (Exception e) {
                            filename = (String)filename + String.format("%04d", this.sequenceNumber);
                        }
                        if (!((String)filename).endsWith(".png")) {
                            filename = (String)filename + ".png";
                        }
                        src.writeToFile(output.getParent().resolve((String)filename));
                        continue;
                    }
                    src.writeToFile(this.settings.output());
                    continue;
                }
                catch (Throwable t) {
                    this.threadedError.set(t);
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

    private void checkEncodeError(@Nullable AutoCloseable closeable) {
        Throwable t = this.threadedError.get();
        if (t != null) {
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void finish(Consumer<String> wait) {
        this.checkEncodeError(null);
        while (!this.encodeQueue.isEmpty()) {
            this.checkEncodeError(null);
            LockSupport.parkNanos("waiting for encode queue to empty", 100000L);
            wait.accept("encode queue");
        }
        this.finishEncodeThread.set(true);
        while (!this.finishedWriting.get()) {
            LockSupport.parkNanos("waiting for encoder thread to finish", 100000L);
            wait.accept("thread finish");
        }
        this.checkEncodeError(null);
    }

    @Override
    public void close() {
        for (NativeImage src : this.encodeQueue) {
            src.close();
        }
        this.finishEncodeThread.set(true);
        while (!this.finishedWriting.get()) {
            LockSupport.parkNanos("waiting for encoder thread to finish", 100000L);
        }
    }
}

