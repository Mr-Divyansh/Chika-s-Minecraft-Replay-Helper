/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.pipeline.RenderTarget
 *  com.mojang.blaze3d.pipeline.TextureTarget
 *  com.mojang.blaze3d.platform.Window
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.renderpearl.api.GpuFormat
 *  com.mojang.renderpearl.api.buffers.GpuBuffer
 *  com.mojang.renderpearl.api.buffers.GpuBufferSlice$MappedView
 *  net.minecraft.client.Minecraft
 *  org.joml.Vector4f
 *  org.joml.Vector4fc
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package yueyue.flashback.capture;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.buffers.GpuBuffer;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yueyue.flashback.UiConfig;
import yueyue.flashback.UiRuntime;

public final class UiCapture {
    public static final int MAX_DIMENSION = 4096;
    public static final long MAX_PIXELS = 0x870000L;
    public static final int MAX_PNG_BYTES = 0x2000000;
    private static final Logger LOGGER = LoggerFactory.getLogger((String)"YueyueUiCapture");
    private static final AtomicBoolean DOWNLOAD_PENDING = new AtomicBoolean();
    private static final AtomicLong SERIAL = new AtomicLong();
    private static final AtomicInteger FAILURES = new AtomicInteger();
    private static final Object ENCODER_LOCK = new Object();
    public static volatile UiImage latest;
    private static volatile Object activeSession;
    private static volatile long generation;
    private static volatile RawFrame pendingFrame;
    private static Thread encoderThread;
    private static TextureTarget frameTarget;
    private static long lastCaptureNanos;
    private static boolean frameActive;
    private static boolean frameScreenOpen;
    private static boolean frameCrosshair;
    private static int frameMouseX;
    private static int frameMouseY;
    private static int frameCursorScale;
    private static long frameGeneration;
    private static Object frameSession;
    private static boolean replayingMeshes;

    private UiCapture() {
    }

    public static boolean isReplayingMeshes() {
        return replayingMeshes;
    }

    public static void setReplayingMeshes(boolean replaying) {
        replayingMeshes = replaying;
    }

    public static void beginFrame() {
        frameActive = false;
        Object session = UiRuntime.recordingSession();
        if (session != activeSession) {
            activeSession = session;
            ++generation;
            latest = null;
            lastCaptureNanos = 0L;
            pendingFrame = null;
            FAILURES.set(0);
        }
        if (session == null || !UiConfig.current.record || FAILURES.get() >= 3) {
            UiCapture.invalidateSample();
            return;
        }
        if (!UiRuntime.recordingEnabled()) {
            return;
        }
        long now = System.nanoTime();
        if (now - lastCaptureNanos < 50000000L || DOWNLOAD_PENDING.get()) {
            return;
        }
        RenderTarget main = Minecraft.getInstance().getMainRenderTarget();
        int width = main.width;
        int height = main.height;
        if (!UiCapture.validDimensions(width, height)) {
            UiCapture.invalidateSample();
            return;
        }
        try {
            if (frameTarget == null || UiCapture.frameTarget.width != width || UiCapture.frameTarget.height != height) {
                if (frameTarget != null) {
                    frameTarget.destroyBuffers();
                    frameTarget = null;
                }
                frameTarget = new TextureTarget("Yueyue recorded UI", width, height, true);
            }
            RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(frameTarget.getColorTexture(), 0, frameTarget.getDepthTexture(), 0.0);
            lastCaptureNanos = now;
            frameGeneration = generation;
            frameSession = session;
            Minecraft client = Minecraft.getInstance();
            Window window = client.getWindow();
            frameScreenOpen = client.screen != null;
            frameCrosshair = !frameScreenOpen && !client.options.hideGui && client.options.getCameraType().isFirstPerson();
            frameMouseX = (int)Math.floor(client.mouseHandler.xpos() * (double)width / (double)Math.max(1, window.getScreenWidth()));
            frameMouseY = (int)Math.floor(client.mouseHandler.ypos() * (double)height / (double)Math.max(1, window.getScreenHeight()));
            frameCursorScale = Math.max(1, Math.min(3, window.getGuiScale() / 2));
            frameActive = true;
        }
        catch (RuntimeException failure) {
            UiCapture.invalidateSample();
            UiCapture.fail("Could not prepare the UI capture target", failure);
        }
    }

    public static boolean validDimensions(int width, int height) {
        return width > 0 && height > 0 && width <= 4096 && height <= 4096 && (long)width * (long)height <= 0x870000L;
    }

    public static RenderTarget target() {
        return frameActive ? frameTarget : null;
    }

    public static void finishFrame() {
        if (!frameActive) {
            return;
        }
        frameActive = false;
        if (!DOWNLOAD_PENDING.compareAndSet(false, true)) {
            return;
        }
        int width = UiCapture.frameTarget.width;
        int height = UiCapture.frameTarget.height;
        long sampledGeneration = frameGeneration;
        Object session = frameSession;
        boolean screenOpen = frameScreenOpen;
        boolean crosshair = frameCrosshair;
        int mouseX = frameMouseX;
        int mouseY = frameMouseY;
        int cursorScale = frameCursorScale;
        long serial = SERIAL.incrementAndGet();
        GpuBuffer buffer = null;
        try {
            GpuBuffer ownedBuffer = buffer = RenderSystem.getDevice().createBuffer(() -> "Yueyue UI readback", 9, 4L * (long)width * (long)height);
            RenderSystem.getDevice().createCommandEncoder().copyTextureToBuffer(frameTarget.getColorTexture(), ownedBuffer, 0L, () -> {
                try (GpuBuffer.MappedView mapped = RenderSystem.getDevice().createCommandEncoder().mapBuffer(ownedBuffer, true, false);){
                    if (!UiCapture.sessionCurrent(session, sampledGeneration)) {
                        return;
                    }
                    byte[] rgba = new byte[Math.multiplyExact(Math.multiplyExact(width, height), 4)];
                    ByteBuffer bytes = mapped.data().duplicate();
                    bytes.get(rgba);
                    UiCapture.enqueue(new RawFrame(serial, width, height, rgba, screenOpen, crosshair, mouseX, mouseY, cursorScale, session, sampledGeneration));
                }
                catch (RuntimeException failure) {
                    UiCapture.fail("Could not read the recorded UI texture", failure);
                }
                finally {
                    ownedBuffer.close();
                    DOWNLOAD_PENDING.set(false);
                }
            }, 0);
        }
        catch (RuntimeException failure) {
            if (buffer != null) {
                buffer.close();
            }
            DOWNLOAD_PENDING.set(false);
            UiCapture.fail("Could not queue the UI texture readback", failure);
        }
    }

    private static boolean sessionCurrent(Object session, long sampledGeneration) {
        return session == activeSession && sampledGeneration == generation && session == UiRuntime.recordingSession();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void enqueue(RawFrame frame) {
        Object object = ENCODER_LOCK;
        synchronized (object) {
            pendingFrame = frame;
            if (encoderThread == null || !encoderThread.isAlive()) {
                encoderThread = new Thread(UiCapture::encodeLoop, "Yueyue UI PNG encoder");
                encoderThread.setDaemon(true);
                encoderThread.start();
            }
            ENCODER_LOCK.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void encodeLoop() {
        RawFrame previous = null;
        while (!Thread.currentThread().isInterrupted()) {
            RawFrame frame;
            Object object = ENCODER_LOCK;
            synchronized (object) {
                while (pendingFrame == null) {
                    try {
                        ENCODER_LOCK.wait();
                    }
                    catch (InterruptedException interrupted) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                frame = pendingFrame;
                pendingFrame = null;
            }
            if (!UiCapture.sessionCurrent(frame.session(), frame.generation())) {
                previous = null;
                continue;
            }
            if (frame.screenOpen()) {
                UiCapture.drawCursor(frame);
            }
            if (UiCapture.samePixels(previous, frame)) continue;
            try {
                byte[] png = UiCapture.encodePng(frame.width(), frame.height(), frame.rgba());
                if (!UiCapture.sessionCurrent(frame.session(), frame.generation())) continue;
                latest = new UiImage(frame.serial(), frame.width(), frame.height(), png, frame.screenOpen(), frame.crosshair(), frame.session());
                FAILURES.set(0);
                previous = frame;
            }
            catch (IOException | RuntimeException failure) {
                UiCapture.fail("Could not encode a recorded UI frame", failure);
            }
        }
    }

    private static boolean samePixels(RawFrame previous, RawFrame frame) {
        return previous != null && previous.session() == frame.session() && previous.generation() == frame.generation() && previous.width() == frame.width() && previous.height() == frame.height() && previous.screenOpen() == frame.screenOpen() && previous.crosshair() == frame.crosshair() && Arrays.equals(previous.rgba(), frame.rgba());
    }

    private static void drawCursor(RawFrame frame) {
        if (frame.mouseX() < 0 || frame.mouseY() < 0 || frame.mouseX() >= frame.width() || frame.mouseY() >= frame.height()) {
            return;
        }
        String[] shape = new String[]{"X..........", "XX.........", "XWX........", "XWWX.......", "XWWWX......", "XWWWWX.....", "XWWWWWX....", "XWWWWWWX...", "XWWWWWWWX..", "XWWWWXXXXX.", "XWWXWX.....", "XWX.WX.....", "XX..XWX....", "X...XWX....", ".....XX...."};
        int scale = frame.cursorScale();
        for (int y = 0; y < shape.length; ++y) {
            for (int x = 0; x < shape[y].length(); ++x) {
                char pixel = shape[y].charAt(x);
                if (pixel == '.') continue;
                int color = pixel == 'W' ? -1 : 0;
                for (int dy = 0; dy < scale; ++dy) {
                    int targetY = frame.mouseY() + y * scale + dy;
                    if (targetY < 0 || targetY >= frame.height()) continue;
                    for (int dx = 0; dx < scale; ++dx) {
                        int targetX = frame.mouseX() + x * scale + dx;
                        if (targetX < 0 || targetX >= frame.width()) continue;
                        int offset = ((frame.height() - targetY - 1) * frame.width() + targetX) * 4;
                        frame.rgba()[offset] = (byte)color;
                        frame.rgba()[offset + 1] = (byte)color;
                        frame.rgba()[offset + 2] = (byte)color;
                        frame.rgba()[offset + 3] = -1;
                    }
                }
            }
        }
    }

    static byte[] encodePng(int width, int height, byte[] rgba) throws IOException {
        BufferedImage image = new BufferedImage(width, height, 2);
        int[] row = new int[width];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int offset = (y * width + x) * 4;
                row[x] = (rgba[offset + 3] & 0xFF) << 24 | (rgba[offset] & 0xFF) << 16 | (rgba[offset + 1] & 0xFF) << 8 | rgba[offset + 2] & 0xFF;
            }
            image.setRGB(0, height - y - 1, width, 1, row, 0, width);
        }
        try {
            byte[] byArray;
            try (ByteArrayOutputStream output = new ByteArrayOutputStream();){
                if (!ImageIO.write((RenderedImage)image, "png", output)) {
                    throw new IOException("No PNG writer is available");
                }
                if (output.size() > 0x2000000) {
                    throw new IOException("UI PNG exceeds the frame size limit");
                }
                byArray = output.toByteArray();
            }
            return byArray;
        }
        finally {
            image.flush();
        }
    }

    private static void fail(String message, Exception failure) {
        if (FAILURES.incrementAndGet() <= 3) {
            LOGGER.warn(message, (Throwable)failure);
        }
    }

    public static void abortFrame(RuntimeException failure) {
        frameActive = false;
        UiCapture.invalidateSample();
        UiCapture.fail("Could not draw the recorded UI layer", failure);
    }

    private static void invalidateSample() {
        ++generation;
        latest = null;
        pendingFrame = null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void close() {
        frameActive = false;
        replayingMeshes = false;
        activeSession = null;
        ++generation;
        latest = null;
        pendingFrame = null;
        Object object = ENCODER_LOCK;
        synchronized (object) {
            if (encoderThread != null) {
                encoderThread.interrupt();
                encoderThread = null;
            }
        }
        if (frameTarget != null) {
            frameTarget.destroyBuffers();
            frameTarget = null;
        }
    }

    private record RawFrame(long serial, int width, int height, byte[] rgba, boolean screenOpen, boolean crosshair, int mouseX, int mouseY, int cursorScale, Object session, long generation) {
    }
}

