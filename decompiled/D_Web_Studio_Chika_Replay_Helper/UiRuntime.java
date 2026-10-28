/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.NativeImage
 *  com.mojang.blaze3d.platform.Window
 *  com.moulberry.flashback.Flashback
 *  com.moulberry.flashback.action.Action
 *  com.moulberry.flashback.action.ActionRegistry
 *  com.moulberry.flashback.exporting.ExportSettings
 *  com.moulberry.flashback.playback.ReplayPlayer
 *  com.moulberry.flashback.playback.ReplayServer
 *  com.moulberry.flashback.record.Recorder
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.minecraft.client.CameraType
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.RenderPipelines
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.Entity
 *  org.slf4j.LoggerFactory
 */
package D_Web_Studio_Chika_Replay_Helper;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.action.Action;
import com.moulberry.flashback.action.ActionRegistry;
import com.moulberry.flashback.exporting.ExportSettings;
import com.moulberry.flashback.playback.ReplayPlayer;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.record.Recorder;
import java.io.IOException;
import java.util.UUID;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import org.slf4j.LoggerFactory;
import D_Web_Studio_Chika_Replay_Helper.capture.UiCapture;

public final class UiRuntime {
    private static volatile Playback playback;
    private static UiFrame uploaded;
    private static DynamicTexture texture;
    private static final Identifier TEXTURE;
    private static Entity previousCamera;
    private static CameraType previousType;
    private static boolean following;
    private static int followingId;
    private static UiFrame lastMouseFrame;
    private static boolean hasMouseSample;
    private static int currentMouseX;
    private static int currentMouseY;
    private static int currentCursorScale;
    private static int previousMouseX;
    private static int previousMouseY;
    private static int previousCursorScale;
    private static long currentMouseTime;
    private static long previousMouseTime;

    private UiRuntime() {
    }

    public static void initialize() {
        ActionRegistry.register((Action)UiAction.INSTANCE);
        ClientTickEvents.END_CLIENT_TICK.register(client -> UiRuntime.updateCamera());
        try {
            com.moulberry.flashback.configuration.FlashbackConfigV1 config = Flashback.getConfig();
            if (config != null && config.recording.localPlayerUpdatesPerSecond < 120) {
                config.recording.localPlayerUpdatesPerSecond = 120;
            }
        }
        catch (RuntimeException ignored) {
        }
    }

    public static Object recordingSession() {
        return Flashback.RECORDER;
    }

    public static boolean recordingEnabled() {
        Minecraft client = Minecraft.getInstance();
        Recorder recorder = Flashback.RECORDER;
        return UiConfig.current.record && recorder != null && recorder.readyToWrite() && !recorder.isPaused() && !Flashback.isInReplay() && client.level != null && client.player != null && !client.isPaused();
    }

    public static void accept(ReplayServer owner, UiFrame frame) {
        playback = new Playback(owner, frame);
        if (frame != null && frame.screenOpen() && frame.width() > 0 && frame != lastMouseFrame) {
            if (hasMouseSample) {
                previousMouseX = currentMouseX;
                previousMouseY = currentMouseY;
                previousCursorScale = currentCursorScale;
                previousMouseTime = currentMouseTime;
            }
            currentMouseX = frame.mouseX();
            currentMouseY = frame.mouseY();
            currentCursorScale = Math.max(1, frame.cursorScale());
            currentMouseTime = System.nanoTime();
            if (!hasMouseSample) {
                previousMouseX = currentMouseX;
                previousMouseY = currentMouseY;
                previousCursorScale = currentCursorScale;
                previousMouseTime = currentMouseTime;
                hasMouseSample = true;
            }
            lastMouseFrame = frame;
        } else if (frame == null || !frame.screenOpen()) {
            hasMouseSample = false;
            lastMouseFrame = null;
        }
    }

    public static UiFrame currentFrame() {
        Playback value = playback;
        return value != null && value.owner() == Flashback.getReplayServer() ? value.frame() : null;
    }

    public static boolean visible() {
        return UiRuntime.visible(UiRuntime.currentFrame());
    }

    public static boolean visible(UiFrame frame) {
        if (!Flashback.isInReplay() || frame == null || frame.png().length == 0) {
            return false;
        }
        if (Flashback.EXPORT_JOB != null) {
            ExportSettings settings = Flashback.EXPORT_JOB.getSettings();
            boolean include = UiConfig.current.include;
            boolean noGui = false;
            try {
                include = ((UiExportOptions)(Object)settings).yueyue$include();
            } catch (RuntimeException ignored) {
            }
            try {
                noGui = settings.noGui();
            } catch (RuntimeException ignored) {
            }
            return include && !noGui;
        }
        return UiConfig.current.preview;
    }

    private static boolean safeFollow(ExportSettings settings) {
        try {
            return ((UiExportOptions)(Object)settings).yueyue$follow();
        } catch (RuntimeException ignored) {
            return UiConfig.current.follow;
        }
    }

    public static void updateCamera() {
        boolean follow = false;
        Minecraft client = Minecraft.getInstance();
        UiFrame frame = UiRuntime.currentFrame();
        follow = Flashback.isInReplay() && frame != null && (Flashback.EXPORT_JOB == null ? UiConfig.current.follow : safeFollow(Flashback.EXPORT_JOB.getSettings()));
        if (follow && frame != null && client.level != null) {
            ReplayServer replay = Flashback.getReplayServer();
            Entity entity = replay == null ? null : client.level.getEntity(replay.getLocalPlayerId());
            if (entity == null || entity == client.player) {
                entity = client.level.players().stream().filter(player -> player != client.player && player.getUUID().equals(frame.player())).findFirst().orElse(null);
            }
            if (entity != null) {
                if (!following) {
                    previousCamera = client.getCameraEntity();
                    previousType = client.options.getCameraType();
                }
                following = true;
                if (followingId != entity.getId()) {
                    followingId = entity.getId();
                    UiRuntime.syncReplayCamera(replay, followingId);
                }
                client.setCameraEntity(entity);
                client.options.setCameraType(CameraType.FIRST_PERSON);
                return;
            }
        }
        if (following) {
            UiRuntime.syncReplayCamera(Flashback.getReplayServer(), previousCamera == null ? Integer.MIN_VALUE : previousCamera.getId());
            client.setCameraEntity((Entity)(previousCamera != null && !previousCamera.isRemoved() && previousCamera.level() == client.level ? previousCamera : client.player));
            if (previousType != null) {
                client.options.setCameraType(previousType);
            }
            previousCamera = null;
            previousType = null;
            following = false;
            followingId = Integer.MIN_VALUE;
        }
        if (!Flashback.isInReplay()) {
            playback = null;
            UiRuntime.releaseTexture();
        }
        if (Flashback.RECORDER == null) {
            UiCapture.close();
        }
    }

    private static void syncReplayCamera(ReplayServer replay, int targetId) {
        LocalPlayer viewer = Minecraft.getInstance().player;
        if (replay == null || viewer == null) {
            return;
        }
        UUID viewerId = viewer.getUUID();
        replay.execute(() -> {
            for (ReplayPlayer player : replay.getReplayViewers()) {
                if (!player.getUUID().equals(viewerId)) continue;
                Entity target = player.level().getEntity(targetId);
                player.setCamera((Entity)(target == null ? player : target));
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean extract(GuiGraphicsExtractor graphics, UiFrame frame) {
        if (!UiRuntime.visible(frame)) {
            return false;
        }
        if (frame != uploaded) {
            NativeImage image = null;
            DynamicTexture created = null;
            try {
                UiFrame.validatePng(frame.png(), frame.width(), frame.height());
                image = NativeImage.read((byte[])frame.png());
                UiRuntime.releaseTexture();
                created = new DynamicTexture(() -> "Yueyue recorded UI", image);
                image = null;
                Minecraft.getInstance().getTextureManager().register(TEXTURE, (AbstractTexture)created);
                texture = created;
                created = null;
                uploaded = frame;
            }
            catch (IOException | RuntimeException e) {
                LoggerFactory.getLogger((String)"yueyue-flashback").warn("Could not decode recorded UI", (Throwable)e);
                UiRuntime.releaseTexture();
                boolean bl = false;
                return bl;
            }
            finally {
                if (image != null) {
                    image.close();
                }
                if (created != null) {
                    created.close();
                }
            }
        }
        Window window = Minecraft.getInstance().getWindow();
        int width = window.getGuiScaledWidth();
        int height = window.getGuiScaledHeight();
        float scale = Math.min((float)width / (float)frame.width(), (float)height / (float)frame.height());
        int drawnWidth = Math.max(1, Math.round((float)frame.width() * scale));
        int drawnHeight = Math.max(1, Math.round((float)frame.height() * scale));
        if (frame.screenOpen()) {
            graphics.blurBeforeThisStratum();
        }
        graphics.blit(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, TEXTURE, (width - drawnWidth) / 2, (height - drawnHeight) / 2, 0.0f, 0.0f, drawnWidth, drawnHeight, frame.width(), frame.height(), frame.width(), frame.height());
        if (frame.screenOpen() && hasMouseSample) {
            long now = System.nanoTime();
            double t = 1.0D;
            if (currentMouseTime > previousMouseTime) {
                t = (double)(now - previousMouseTime) / (double)(currentMouseTime - previousMouseTime);
            }
            if (t < 0.0D) t = 0.0D;
            if (t > 1.0D) t = 1.0D;
            int mx = (int)Math.round(previousMouseX + (currentMouseX - previousMouseX) * t);
            int my = (int)Math.round(previousMouseY + (currentMouseY - previousMouseY) * t);
            int offsetX = (width - drawnWidth) / 2;
            int offsetY = (height - drawnHeight) / 2;
            int guiX = offsetX + (int)Math.round(mx * scale);
            int guiY = offsetY + (int)Math.round(my * scale);
            UiRuntime.drawCursor(graphics, guiX, guiY, currentCursorScale);
        }
        return true;
    }

    private static void drawCursor(GuiGraphicsExtractor graphics, int baseX, int baseY, int scale) {
        String[] shape = new String[]{"X..........", "XX.........", "XWX........", "XWWX.......", "XWWWX......", "XWWWWX.....", "XWWWWWX....", "XWWWWWWX...", "XWWWWWWWX..", "XWWWWXXXXX.", "XWWXWX.....", "XWX.WX.....", "XX..XWX....", "X...XWX....", ".....XX...."};
        for (int y = 0; y < shape.length; ++y) {
            for (int x = 0; x < shape[y].length(); ++x) {
                char pixel = shape[y].charAt(x);
                if (pixel == '.') continue;
                int color = pixel == 'W' ? 0xFFFFFFFF : 0xFF000000;
                graphics.fill(baseX + x * scale, baseY + y * scale, baseX + x * scale + scale, baseY + y * scale + scale, color);
            }
        }
    }

    private static void releaseTexture() {
        if (texture != null) {
            Minecraft.getInstance().getTextureManager().release(TEXTURE);
        }
        texture = null;
        uploaded = null;
    }

    static {
        TEXTURE = Identifier.fromNamespaceAndPath((String)"d_web_studio_chika_replay_helper", (String)"ui_track");
        followingId = Integer.MIN_VALUE;
    }

    private record Playback(ReplayServer owner, UiFrame frame) {
    }
}

