/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.ProjectionType
 *  com.mojang.blaze3d.buffers.GpuBufferSlice
 *  com.mojang.blaze3d.pipeline.RenderTarget
 *  com.mojang.blaze3d.platform.NativeImage
 *  com.mojang.blaze3d.platform.Window
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.textures.GpuTextureView
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Camera
 *  net.minecraft.client.DeltaTracker
 *  net.minecraft.client.DeltaTracker$Timer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.Font$DisplayMode
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.Projection
 *  net.minecraft.client.renderer.ProjectionMatrixBuffer
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.core.Holder
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.Util
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.openal.SOFTLoopback
 */
package com.moulberry.flashback.exporting;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.FramebufferUtils;
import com.moulberry.flashback.FreezeSlowdownFormula;
import com.moulberry.flashback.Utils;
import com.moulberry.flashback.WindowSizeTracker;
import com.moulberry.flashback.combo_options.ExportProjection;
import com.moulberry.flashback.combo_options.VideoContainer;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.editor.ui.windows.ExportDoneWindow;
import com.moulberry.flashback.exporting.AsyncFFmpegVideoWriter;
import com.moulberry.flashback.exporting.ExportJobQueue;
import com.moulberry.flashback.exporting.ExportSettings;
import com.moulberry.flashback.exporting.ImageFrame;
import com.moulberry.flashback.exporting.PerfectFrames;
import com.moulberry.flashback.exporting.SaveableFramebuffer;
import com.moulberry.flashback.exporting.SaveableFramebufferQueue;
import com.moulberry.flashback.exporting.VideoWriter;
import com.moulberry.flashback.exporting.taskbar.TaskbarManager;
import com.moulberry.flashback.keyframe.handler.MinecraftKeyframeHandler;
import com.moulberry.flashback.keyframe.handler.TickrateKeyframeCapture;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.sound.FlashbackAudioManager;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.visuals.AccurateEntityPositionHandler;
import java.io.IOException;
import java.lang.invoke.CallSite;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.openal.SOFTLoopback;

public class ExportJob {
    private final ExportSettings settings;
    private boolean running = false;
    private boolean shouldChangeFramebufferSize = false;
    private long lastRenderMillis;
    private long escapeCancelStartMillis = -1L;
    private long renderStartTime;
    private final Random particleRandom;
    private long currentClientTickSeed = 0L;
    private long currentInitialEntityTickSeed = 0L;
    private boolean showingDebug = false;
    private boolean pressedDebugKey = false;
    private long serverTickTimeNanos = 0L;
    private long clientTickTimeNanos = 0L;
    private long renderTimeNanos = 0L;
    private long encodeTimeNanos = 0L;
    private boolean patreonLinkClicked = false;
    private int extraDummyFrames = 0;
    public int progressCount = 0;
    public int progressOutOf = 0;
    private double currentTickDouble = 0.0;
    private double audioSamples = 0.0;
    private final AtomicBoolean finishedServerTick = new AtomicBoolean(false);
    private NativeImage firstFrame = null;
    private int writtenFrames = 0;
    public Runnable saveDepthAfterLevel = null;
    private static ProjectionMatrixBuffer projectionBuffers = null;
    private static Projection projection;
    private static RenderTarget displayTarget;

    public ExportJob(ExportSettings settings) {
        this.settings = settings;
        this.particleRandom = this.settings.resetRng() ? new Random(2000L) : null;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void onFinishedServerTick() {
        this.finishedServerTick.set(true);
    }

    public boolean shouldChangeFramebufferSize() {
        return this.running && this.shouldChangeFramebufferSize;
    }

    public int getWidth() {
        int resolutionX = this.settings.resolutionX();
        ExportProjection projection = this.settings.projection();
        if (projection == ExportProjection.CUBE_MAP) {
            resolutionX = (resolutionX + 3) / 4;
        }
        if (this.settings.ssaa()) {
            resolutionX *= 2;
        }
        return resolutionX;
    }

    public int getHeight() {
        int resolutionY = this.settings.resolutionY();
        ExportProjection projection = this.settings.projection();
        if (projection == ExportProjection.CUBE_MAP) {
            resolutionY = (resolutionY + 2) / 3;
        }
        if (this.settings.ssaa()) {
            resolutionY *= 2;
        }
        return resolutionY;
    }

    public double getCurrentTickDouble() {
        return this.currentTickDouble;
    }

    public Random getParticleRandom() {
        return this.particleRandom;
    }

    public long getSeedForCurrentClientTick() {
        return this.currentClientTickSeed;
    }

    public long getInitialEntitySeedForCurrentClientTick() {
        ++this.currentInitialEntityTickSeed;
        return this.currentInitialEntityTickSeed;
    }

    public ExportSettings getSettings() {
        return this.settings;
    }

    public void run() {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (this.running || replayServer == null) {
            throw new IllegalStateException("run() called twice");
        }
        this.running = true;
        Minecraft.getInstance().mouseHandler.releaseMouse();
        Minecraft.getInstance().getSoundManager().stop();
        Minecraft.getInstance().getSoundManager().tick(true);
        TaskbarManager.launchTaskbarManager();
        UUID uuid = UUID.randomUUID();
        TempFileInfo tempFileInfo = null;
        if (!this.settings.container().isImageSequence() || this.settings.pngSequenceFormat() == null) {
            String tempFileName = "replay_export_temp/" + String.valueOf(uuid) + "." + this.settings.container().extension();
            Path exportTempFile = Path.of(tempFileName, new String[0]);
            Path exportTempFolder = exportTempFile.getParent();
            tempFileInfo = new TempFileInfo(tempFileName, exportTempFile, exportTempFolder);
        }
        boolean keepTempFile = false;
        int oldGuiScale = (Integer)Minecraft.getInstance().options.guiScale().get();
        this.extraDummyFrames = Flashback.getConfig().exporting.exportRenderDummyFrames;
        try {
            if (tempFileInfo != null) {
                Files.createDirectories(tempFileInfo.folder, new FileAttribute[0]);
            }
            int resolutionX = this.settings.resolutionX();
            int resolutionY = this.settings.resolutionY();
            if (this.settings.projection() == ExportProjection.CUBE_MAP || this.settings.projection() == ExportProjection.EQUIRECTANGULAR) {
                if (this.settings.projection() == ExportProjection.CUBE_MAP) {
                    resolutionX = (resolutionX + 3) / 4;
                    resolutionY = (resolutionY + 2) / 3;
                }
                Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
                camera.enablePanoramicMode();
            }
            try (VideoWriter encoder = ExportJob.createVideoWriter(this.settings, tempFileInfo);
                 SaveableFramebufferQueue downloader = new SaveableFramebufferQueue(resolutionX, resolutionY);){
                this.doExport(encoder, downloader);
            }
            Path outputLocation = this.settings.output();
            boolean errorMovingToOutput = false;
            if (tempFileInfo != null) {
                try {
                    Files.move(tempFileInfo.file, this.settings.output(), StandardCopyOption.REPLACE_EXISTING);
                }
                catch (IOException e) {
                    Flashback.LOGGER.error("Error while moving temp file to export output", (Throwable)e);
                    outputLocation = tempFileInfo.file;
                    errorMovingToOutput = true;
                    keepTempFile = true;
                }
            }
            try {
                long size = 0L;
                double duration = (double)this.writtenFrames / this.settings.framerate();
                if (this.settings.container() != VideoContainer.PNG_SEQUENCE && Files.exists(outputLocation, new LinkOption[0]) && Files.isRegularFile(outputLocation, new LinkOption[0])) {
                    size = Files.size(outputLocation);
                }
                ExportDoneWindow.addFinishedExportEntry(new ExportDoneWindow.FinishedExportEntry(this.settings, outputLocation, errorMovingToOutput, this.firstFrame, duration, size));
                this.firstFrame = null;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            this.running = false;
            this.shouldChangeFramebufferSize = false;
            if (this.settings.projection() == ExportProjection.CUBE_MAP || this.settings.projection() == ExportProjection.EQUIRECTANGULAR) {
                Minecraft.getInstance().gameRenderer.getMainCamera().disablePanoramicMode();
            }
            Minecraft.getInstance().options.guiScale().set((Object)oldGuiScale);
            Minecraft.getInstance().resizeGui();
            Minecraft.getInstance().getWindow().resetIsResized();
            replayServer.replayPaused = true;
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                level.tickRateManager().setFrozen(true);
            }
            Minecraft.getInstance().getSoundManager().stop();
            Minecraft.getInstance().getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.NOTE_BLOCK_CHIME, (float)1.0f));
            Minecraft.getInstance().getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.NOTE_BLOCK_BELL, (float)1.0f));
            if (this.firstFrame != null) {
                this.firstFrame.close();
                this.firstFrame = null;
            }
            if (tempFileInfo != null) {
                if (!keepTempFile) {
                    try {
                        Files.deleteIfExists(tempFileInfo.file);
                    }
                    catch (IOException iOException) {}
                }
                try {
                    Files.deleteIfExists(tempFileInfo.folder);
                }
                catch (IOException iOException) {}
            }
        }
    }

    private static VideoWriter createVideoWriter(ExportSettings settings, TempFileInfo tempFileInfo) {
        String filename;
        if (tempFileInfo != null) {
            filename = tempFileInfo.name;
        } else if (settings.pngSequenceFormat() != null) {
            Path folder;
            Object prefix;
            if (Files.isDirectory(settings.output(), new LinkOption[0])) {
                prefix = "";
                folder = settings.output();
            } else {
                prefix = settings.output().getFileName().toString() + "-";
                folder = settings.output().getParent();
            }
            String formatter = (String)prefix + settings.pngSequenceFormat();
            String extension = "." + settings.container().extension();
            if (!formatter.endsWith(extension)) {
                formatter = formatter + extension;
            }
            filename = folder.resolve(formatter).toString();
        } else {
            throw new IllegalStateException("settings.pngSequenceFormat() must not be null");
        }
        return new AsyncFFmpegVideoWriter(settings, filename);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doExport(VideoWriter videoWriter, SaveableFramebufferQueue downloader) {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer == null) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        Random random = new Random(1000L);
        Random mathRandom = this.settings.resetRng() ? Utils.getInternalMathRandom() : null;
        this.setup(replayServer);
        this.updateRandoms(random, mathRandom);
        this.shouldChangeFramebufferSize = true;
        if (this.settings.ssaa()) {
            mc.options.guiScale().set((Object)((Integer)mc.options.guiScale().get() * 2));
        }
        mc.resizeGui();
        mc.getWindow().resetIsResized();
        List<TickInfo> ticks = ExportJob.calculateTicks(this.settings.editorState(), this.settings.startTick(), this.settings.endTick(), this.settings.framerate());
        int clientTickCount = 0;
        this.renderStartTime = System.currentTimeMillis();
        double lastClientTickDouble = 0.0;
        for (int tickIndex = 0; tickIndex < ticks.size(); ++tickIndex) {
            TickInfo tickInfo = ticks.get(tickIndex);
            boolean frozen = tickInfo.frozen;
            this.currentTickDouble = tickInfo.serverTick;
            int targetServerTick = this.settings.startTick() + (int)this.currentTickDouble;
            float deltaTicksFloat = (float)(tickInfo.clientTick - lastClientTickDouble);
            lastClientTickDouble = tickInfo.clientTick;
            double partialClientTick = tickInfo.clientTick - (double)((int)tickInfo.clientTick);
            long start = System.nanoTime();
            this.setServerTickAndWait(replayServer, targetServerTick, false);
            this.serverTickTimeNanos += System.nanoTime() - start;
            while (clientTickCount < (int)tickInfo.clientTick) {
                start = System.nanoTime();
                this.updateRandoms(random, mathRandom);
                this.runClientTick(frozen);
                this.clientTickTimeNanos += System.nanoTime() - start;
                ++clientTickCount;
            }
            this.updateClientFreeze(frozen);
            DeltaTracker.Timer timer = mc.deltaTracker;
            timer.updateFrozenState(frozen);
            timer.updatePauseState(false);
            timer.deltaTicks = deltaTicksFloat;
            timer.realtimeDeltaTicks = deltaTicksFloat;
            timer.deltaTickResidual = (float)partialClientTick;
            timer.pausedDeltaTickResidual = (float)partialClientTick;
            AccurateEntityPositionHandler.apply(mc.level, (DeltaTracker)timer);
            if (frozen) {
                FlashbackAudioManager.pauseAll();
            } else {
                FlashbackAudioManager.startHandling();
            }
            try {
                MinecraftKeyframeHandler keyframeHandler = new MinecraftKeyframeHandler(mc);
                this.settings.editorState().applyKeyframes(keyframeHandler, (float)((double)this.settings.startTick() + this.currentTickDouble));
            }
            finally {
                if (!frozen) {
                    FlashbackAudioManager.finishHandling();
                }
            }
            long pauseScreenStart = System.currentTimeMillis();
            int additionalDummyFrames = this.extraDummyFrames;
            if (tickIndex == 0) {
                additionalDummyFrames = Math.max(this.extraDummyFrames, 60);
            }
            while (mc.getOverlay() != null || mc.screen != null || additionalDummyFrames > 0) {
                boolean overlayOrScreen;
                boolean bl = overlayOrScreen = mc.getOverlay() != null || mc.screen != null;
                if (overlayOrScreen) {
                    this.runClientTick(frozen);
                }
                if (additionalDummyFrames > 0) {
                    --additionalDummyFrames;
                }
                RenderTarget renderTarget = mc.mainRenderTarget;
                ExportJob.render(renderTarget, mc.deltaTracker);
                if (overlayOrScreen) {
                    this.finishFrame(renderTarget, new ArrayList<String>(List.of("Waiting for overlay to disappear")), true, false);
                } else if (tickIndex == 0) {
                    this.finishFrame(renderTarget, new ArrayList<CallSite>(List.of("Warming up... " + Math.max(0, 60 - additionalDummyFrames) + "/60")), true, false);
                }
                if (overlayOrScreen) {
                    LockSupport.parkNanos("waiting for pause overlay to disappear", 50000000L);
                    long currentTime = System.currentTimeMillis();
                    if (pauseScreenStart > currentTime) {
                        pauseScreenStart = currentTime;
                    }
                    if (currentTime - pauseScreenStart > 5000L) {
                        mc.setScreen(null);
                    }
                    if (currentTime - pauseScreenStart > 15000L) {
                        mc.setOverlay(null);
                    }
                }
                this.updateClientFreeze(frozen);
                timer.updateFrozenState(frozen);
                timer.updatePauseState(false);
                timer.deltaTicks = deltaTicksFloat;
                timer.realtimeDeltaTicks = deltaTicksFloat;
                timer.deltaTickResidual = (float)partialClientTick;
                timer.pausedDeltaTickResidual = (float)partialClientTick;
            }
            RenderTarget renderTarget = Minecraft.getInstance().mainRenderTarget;
            ExportProjection projection = this.settings.projection();
            if (projection == ExportProjection.CUBE_MAP || projection == ExportProjection.EQUIRECTANGULAR) {
                LocalPlayer player = mc.player;
                for (int i = 0; i < 6; ++i) {
                    if (i < 4) {
                        player.setYRot(90.0f * (float)i - 90.0f);
                        player.setXRot(0.0f);
                    } else if (i == 4) {
                        player.setYRot(0.0f);
                        player.setXRot(-90.0f);
                    } else if (i == 5) {
                        player.setYRot(0.0f);
                        player.setXRot(90.0f);
                    }
                    player.setOldRot();
                    SaveableFramebuffer saveable = downloader.take();
                    if (this.settings.depthMap()) {
                        this.saveDepthAfterLevel = () -> downloader.startDepthDownload(renderTarget, saveable);
                    }
                    PerfectFrames.waitUntilFrameReady();
                    start = System.nanoTime();
                    ExportJob.render(renderTarget, timer);
                    this.renderTimeNanos += System.nanoTime() - start;
                    FloatBuffer audioBuffer = null;
                    if (this.settings.recordAudio() && i == 0) {
                        long device = Minecraft.getInstance().getSoundManager().soundEngine.library.currentDevice;
                        this.audioSamples += 48000.0 / this.settings.framerate();
                        int renderSamples = (int)this.audioSamples;
                        this.audioSamples -= (double)renderSamples;
                        int channels = this.settings.stereoAudio() ? 2 : 1;
                        audioBuffer = ByteBuffer.allocateDirect(renderSamples * 4 * channels).order(ByteOrder.nativeOrder()).asFloatBuffer();
                        SOFTLoopback.alcRenderSamplesSOFT((long)device, (FloatBuffer)audioBuffer, (int)renderSamples);
                    }
                    saveable.audioBuffer = audioBuffer;
                    if (this.settings.depthMap()) {
                        this.tryDepthDownload();
                        continue;
                    }
                    downloader.startDownload(renderTarget, saveable, this.settings.ssaa());
                }
            } else {
                SaveableFramebuffer saveable = downloader.take();
                if (this.settings.depthMap()) {
                    this.saveDepthAfterLevel = () -> downloader.startDepthDownload(renderTarget, saveable);
                }
                PerfectFrames.waitUntilFrameReady();
                start = System.nanoTime();
                ExportJob.render(renderTarget, timer);
                this.renderTimeNanos += System.nanoTime() - start;
                FloatBuffer audioBuffer = null;
                if (this.settings.recordAudio()) {
                    long device = Minecraft.getInstance().getSoundManager().soundEngine.library.currentDevice;
                    this.audioSamples += 48000.0 / this.settings.framerate();
                    int renderSamples = (int)this.audioSamples;
                    this.audioSamples -= (double)renderSamples;
                    int channels = this.settings.stereoAudio() ? 2 : 1;
                    audioBuffer = ByteBuffer.allocateDirect(renderSamples * 4 * channels).order(ByteOrder.nativeOrder()).asFloatBuffer();
                    SOFTLoopback.alcRenderSamplesSOFT((long)device, (FloatBuffer)audioBuffer, (int)renderSamples);
                }
                saveable.audioBuffer = audioBuffer;
                if (this.settings.depthMap()) {
                    this.tryDepthDownload();
                } else {
                    downloader.startDownload(renderTarget, saveable, this.settings.ssaa());
                }
            }
            this.submitDownloadedFrames(videoWriter, downloader, false);
            this.shouldChangeFramebufferSize = false;
            boolean cancel = this.finishFrameNormal(renderTarget, tickIndex, ticks.size());
            this.shouldChangeFramebufferSize = true;
            if (!cancel) continue;
            ExportJobQueue.drainingQueue = false;
            break;
        }
        this.submitDownloadedFrames(videoWriter, downloader, true);
        long finishStart = System.currentTimeMillis();
        videoWriter.finish(info -> {
            long time = System.currentTimeMillis() - finishStart;
            String progress = "Finalizing video (" + info + ")... " + time / 1000L + "s";
            this.finishFrameFinal(Minecraft.getInstance().mainRenderTarget, progress, false);
        });
    }

    public void tryDepthDownload() {
        if (this.saveDepthAfterLevel != null) {
            this.saveDepthAfterLevel.run();
            this.saveDepthAfterLevel = null;
        }
    }

    private static void render(RenderTarget renderTarget, DeltaTracker.Timer timer) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null) {
            minecraft.level.update();
        }
        minecraft.gameRenderer.update((DeltaTracker)timer, true);
        minecraft.gameRenderer.extract((DeltaTracker)timer, true);
        RenderSystem.executePendingTasks();
        FramebufferUtils.clear(renderTarget, 0);
        minecraft.gameRenderer.render((DeltaTracker)timer, true);
    }

    private void updateRandoms(Random random, Random mathRandom) {
        if (!this.settings.resetRng()) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        long connectionSeed = random.nextLong();
        long levelSeed = random.nextLong();
        long entitySeed = random.nextLong();
        long mathSeed = random.nextLong();
        long particleSeed = random.nextLong();
        this.currentClientTickSeed = random.nextLong();
        this.currentInitialEntityTickSeed = random.nextLong();
        if (minecraft.getConnection() != null) {
            minecraft.getConnection().random.setSeed(connectionSeed);
        }
        if (minecraft.level != null) {
            minecraft.level.random.setSeed(levelSeed);
            for (Entity entity : minecraft.level.entitiesForRendering()) {
                if (entity == null) continue;
                entity.getRandom().setSeed(entitySeed ^ entity.getUUID().getMostSignificantBits());
            }
        }
        if (mathRandom != null) {
            mathRandom.setSeed(mathSeed);
        }
        this.particleRandom.setSeed(particleSeed);
    }

    private void setup(ReplayServer replayServer) {
        Minecraft minecraft = Minecraft.getInstance();
        replayServer.setDesiredTickRate(20.0f, true);
        if (replayServer.getReplayTick() != this.settings.startTick()) {
            int currentTick = Math.max(0, this.settings.startTick() - 40);
            this.setServerTickAndWait(replayServer, currentTick, true);
            this.runClientTick(false);
            minecraft.particleEngine.clearParticles();
            if (minecraft.level != null) {
                for (Entity entity : minecraft.level.entitiesForRendering()) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity)entity;
                        livingEntity.walkAnimation.stop();
                    }
                    entity.tickCount = 0;
                }
            }
            while (currentTick < this.settings.startTick()) {
                this.setServerTickAndWait(replayServer, ++currentTick, true);
                this.runClientTick(false);
            }
            LocalPlayer player = minecraft.player;
            if (player != null) {
                Vec3 position = this.settings.initialCameraPosition();
                player.snapTo(position.x, position.y, position.z, this.settings.initialCameraYaw(), this.settings.initialCameraPitch());
                player.getInterpolation().cancel();
                player.setDeltaMovement(Vec3.ZERO);
            }
            this.settings.editorState().applyKeyframes(new MinecraftKeyframeHandler(Minecraft.getInstance()), this.settings.startTick());
            this.runClientTick(false);
            this.setServerTickAndWait(replayServer, this.settings.startTick(), true);
            this.runClientTick(false);
        }
        minecraft.setScreen(null);
    }

    private void setServerTickAndWait(ReplayServer replayServer, int targetTick, boolean force) {
        if (force || replayServer.getReplayTick() != targetTick) {
            this.finishedServerTick.set(false);
            replayServer.jumpToTick = targetTick;
            replayServer.replayPaused = true;
            replayServer.sendFinishedServerTick.set(true);
            while (!this.finishedServerTick.compareAndExchange(true, false)) {
                LockSupport.parkNanos("waiting for server thread", 100000L);
            }
        }
    }

    private void runClientTick(boolean frozen) {
        this.updateClientFreeze(frozen);
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.packetProcessor().processQueuedPackets();
        minecraft.runAllTasks();
        this.updateClientFreeze(frozen);
        if (!frozen) {
            minecraft.getTextureManager().tick();
        }
        minecraft.tick();
        this.updateSoundSource(minecraft);
        this.updateClientFreeze(frozen);
    }

    private void updateSoundSource(Minecraft minecraft) {
        Camera audioCamera;
        EditorState editorState = this.settings.editorState();
        if (editorState != null && (audioCamera = editorState.getAudioCamera()) != null) {
            minecraft.getSoundManager().updateSource(audioCamera);
            return;
        }
        minecraft.getSoundManager().updateSource(Minecraft.getInstance().gameRenderer.getMainCamera());
    }

    private void updateClientFreeze(boolean frozen) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            level.tickRateManager().setFrozen(frozen);
        }
    }

    private void submitDownloadedFrames(VideoWriter videoWriter, SaveableFramebufferQueue downloader, boolean drain) {
        long start = System.currentTimeMillis();
        boolean firstDrainFrame = true;
        while (true) {
            if (drain) {
                long time = System.currentTimeMillis() - start;
                String progress = "Capturing " + downloader.pendingCount() + " output frames... " + time / 1000L + "s";
                this.finishFrameFinal(Minecraft.getInstance().mainRenderTarget, progress, firstDrainFrame);
                firstDrainFrame = false;
            } else {
                RenderSystem.executePendingTasks();
            }
            if (this.settings.projection() == ExportProjection.CUBE_MAP || this.settings.projection() == ExportProjection.EQUIRECTANGULAR) {
                ImageFrame[] frames = downloader.finishDownloadMultiple(6);
                if (frames == null) {
                    if (!drain || downloader.isEmpty()) break;
                    LockSupport.parkNanos("waiting for frame to download", 100000L);
                    continue;
                }
                if (!this.settings.transparent()) {
                    for (ImageFrame frame : frames) {
                        frame.makeOpaque();
                    }
                }
                int resolutionX = this.settings.resolutionX();
                int resolutionY = this.settings.resolutionY();
                ImageFrame target = new ImageFrame(resolutionX, resolutionY, frames[0].format, true);
                target.audioBuffer = frames[0].audioBuffer;
                if (this.settings.projection() == ExportProjection.CUBE_MAP) {
                    for (int i = 0; i < frames.length; ++i) {
                        int positionY;
                        int positionX;
                        if (i < 4) {
                            positionX = resolutionX * i / 4;
                            positionY = resolutionY / 3;
                        } else if (i == 4) {
                            positionX = resolutionX / 4;
                            positionY = 0;
                        } else {
                            if (i != 5) break;
                            positionX = resolutionX / 4;
                            positionY = resolutionY * 2 / 3;
                        }
                        ImageFrame image = frames[i];
                        int sizeX = Math.min(image.width, target.width - positionX);
                        int sizeY = Math.min(image.height, target.height - positionY);
                        image.copyRect(target, 0, 0, positionX, positionY, sizeX, sizeY);
                    }
                } else {
                    for (int y = 0; y < resolutionY; ++y) {
                        for (int x = 0; x < resolutionX; ++x) {
                            int imageY;
                            int imageX;
                            ImageFrame image;
                            double yaw = (double)x / (double)resolutionX * 2.0 * Math.PI;
                            double pitch = (double)y / (double)resolutionY * Math.PI - 1.5707963267948966;
                            double sx = -Math.sin(yaw) * Math.cos(pitch);
                            double sy = Math.sin(pitch);
                            double sz = -Math.cos(yaw) * Math.cos(pitch);
                            double a = Math.max(Math.abs(sx), Math.max(Math.abs(sy), Math.abs(sz)));
                            double cx = sx / a;
                            double cy = sy / a;
                            double cz = sz / a;
                            if (cy == -1.0) {
                                image = frames[4];
                                imageX = (int)Math.round((cx + 1.0) / 2.0 * (double)(image.width - 1));
                                imageY = (int)Math.round((cz + 1.0) / 2.0 * (double)(image.height - 1));
                                image.copyPixel(target, imageX, imageY, x, y);
                                continue;
                            }
                            if (cy == 1.0) {
                                image = frames[5];
                                imageX = (int)Math.round((cx + 1.0) / 2.0 * (double)(image.width - 1));
                                imageY = image.height - 1 - (int)Math.round((cz + 1.0) / 2.0 * (double)(image.height - 1));
                                image.copyPixel(target, imageX, imageY, x, y);
                                continue;
                            }
                            if (cz == -1.0) {
                                image = frames[3];
                                imageX = image.width - 1 - (int)Math.round((cx + 1.0) / 2.0 * (double)(image.width - 1));
                                imageY = (int)Math.round((cy + 1.0) / 2.0 * (double)(image.height - 1));
                                image.copyPixel(target, imageX, imageY, x, y);
                                continue;
                            }
                            if (cz == 1.0) {
                                image = frames[1];
                                imageX = (int)Math.round((cx + 1.0) / 2.0 * (double)(image.width - 1));
                                imageY = (int)Math.round((cy + 1.0) / 2.0 * (double)(image.height - 1));
                                image.copyPixel(target, imageX, imageY, x, y);
                                continue;
                            }
                            if (cx == 1.0) {
                                image = frames[2];
                                imageX = image.width - 1 - (int)Math.round((cz + 1.0) / 2.0 * (double)(image.width - 1));
                                imageY = (int)Math.round((cy + 1.0) / 2.0 * (double)(image.height - 1));
                                image.copyPixel(target, imageX, imageY, x, y);
                                continue;
                            }
                            if (cx != -1.0) continue;
                            image = frames[0];
                            imageX = (int)Math.round((cz + 1.0) / 2.0 * (double)(image.width - 1));
                            imageY = (int)Math.round((cy + 1.0) / 2.0 * (double)(image.height - 1));
                            image.copyPixel(target, imageX, imageY, x, y);
                        }
                    }
                }
                for (ImageFrame frame : frames) {
                    frame.close();
                }
                if (this.firstFrame == null) {
                    this.firstFrame = target.toOpaqueRgbaU8NativeImage();
                }
                ++this.writtenFrames;
                long encodeStart = System.nanoTime();
                videoWriter.encode(target);
                this.encodeTimeNanos += System.nanoTime() - encodeStart;
                continue;
            }
            ImageFrame frame = downloader.finishDownload();
            if (frame == null) {
                if (!drain || downloader.isEmpty()) break;
                LockSupport.parkNanos("waiting for frame to download", 100000L);
                continue;
            }
            if (!this.settings.transparent()) {
                frame.makeOpaque();
            }
            if (this.firstFrame == null) {
                this.firstFrame = frame.toOpaqueRgbaU8NativeImage();
            }
            ++this.writtenFrames;
            long encodeStart = System.nanoTime();
            videoWriter.encode(frame);
            this.encodeTimeNanos += System.nanoTime() - encodeStart;
        }
    }

    private boolean finishFrameNormal(RenderTarget framebuffer, int currentFrame, int totalFrames) {
        this.progressCount = currentFrame;
        this.progressOutOf = totalFrames;
        if (currentFrame == totalFrames) {
            this.finishFrameFinal(framebuffer, "Saving...", true);
            return false;
        }
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("Exported Frames: " + currentFrame + "/" + totalFrames);
        long currentTime = System.currentTimeMillis();
        long elapsed = currentTime - this.renderStartTime;
        lines.add("Time elapsed: " + this.formatTime(elapsed));
        if ((double)currentFrame >= this.settings.framerate()) {
            long estimatedRemaining = (currentTime - this.renderStartTime) * (long)(totalFrames - currentFrame) / (long)currentFrame;
            lines.add("Estimated time remaining: " + this.formatTime(estimatedRemaining));
        } else {
            lines.add("Estimated time remaining: ~");
        }
        return this.finishFrame(framebuffer, lines, false, true);
    }

    private void finishFrameFinal(RenderTarget framebuffer, String customProgress, boolean forceShow) {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add(customProgress);
        this.finishFrame(framebuffer, lines, forceShow, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean finishFrame(RenderTarget framebuffer, List<String> lines, boolean forceShow, boolean showCancel) {
        MultiBufferSource.BufferSource bufferSource;
        GpuTextureView previousDepthOutputOverride;
        GpuTextureView previousColorOutputOverride;
        Window window;
        boolean cancel;
        block25: {
            int patreonWidth;
            String patreon;
            int y;
            int x;
            Matrix4f poseMatrix;
            Font font;
            block24: {
                boolean debugPressed;
                float framebufferY2;
                float framebufferY1;
                float framebufferX2;
                float framebufferX1;
                RenderSystem.executePendingTasks();
                Minecraft.getInstance().getWindow().resetIsResized();
                RenderSystem.pollEvents();
                long currentTime = System.currentTimeMillis();
                if (currentTime - this.lastRenderMillis <= 16L && !forceShow) {
                    return false;
                }
                this.lastRenderMillis = currentTime;
                cancel = false;
                window = Minecraft.getInstance().getWindow();
                font = Minecraft.getInstance().font;
                int windowFramebufferWidth = WindowSizeTracker.getWidth(window);
                int windowFramebufferHeight = WindowSizeTracker.getHeight(window);
                displayTarget = FramebufferUtils.resizeOrCreateFramebuffer(displayTarget, windowFramebufferWidth, windowFramebufferHeight, true);
                FramebufferUtils.clear(displayTarget, -16777216);
                previousColorOutputOverride = RenderSystem.outputColorTextureOverride;
                previousDepthOutputOverride = RenderSystem.outputDepthTextureOverride;
                RenderSystem.outputColorTextureOverride = displayTarget.getColorTextureView();
                RenderSystem.outputDepthTextureOverride = displayTarget.getDepthTextureView();
                float aspectWidth = (float)framebuffer.width / (float)windowFramebufferWidth;
                float aspectHeight = (float)framebuffer.height / (float)windowFramebufferHeight;
                if (aspectWidth > aspectHeight) {
                    framebufferX1 = 0.0f;
                    framebufferX2 = 1.0f;
                    framebufferY1 = 0.5f - aspectHeight / aspectWidth / 2.0f;
                    framebufferY2 = 0.5f + aspectHeight / aspectWidth / 2.0f;
                } else {
                    framebufferY1 = 0.0f;
                    framebufferY2 = 1.0f;
                    framebufferX1 = 0.5f - aspectWidth / aspectHeight / 2.0f;
                    framebufferX2 = 0.5f + aspectWidth / aspectHeight / 2.0f;
                }
                FramebufferUtils.blitTo(framebuffer.getColorTextureView(), displayTarget, windowFramebufferWidth, windowFramebufferHeight, framebufferX1, framebufferY1, framebufferX2, framebufferY2);
                float guiScale = Math.min(windowFramebufferWidth / 420, windowFramebufferHeight / 240);
                int scaledWidth = (int)Math.ceil((float)windowFramebufferWidth / guiScale);
                int scaledHeight = (int)Math.ceil((float)windowFramebufferHeight / guiScale);
                if (projectionBuffers == null) {
                    projectionBuffers = new ProjectionMatrixBuffer("flashback export");
                    projection = new Projection();
                    projection.setupOrtho(-1.0f, 1.0f, (float)scaledWidth, (float)scaledHeight, true);
                } else if (projection.width() != (float)scaledWidth || projection.height() != (float)scaledHeight) {
                    projection.setSize((float)scaledWidth, (float)scaledHeight);
                }
                GpuBufferSlice buffer = projectionBuffers.getBuffer(projection);
                RenderSystem.setProjectionMatrix((GpuBufferSlice)buffer, (ProjectionType)ProjectionType.ORTHOGRAPHIC);
                poseMatrix = new Matrix4f();
                if (this.settings.name() != null) {
                    lines.add(0, this.settings.name());
                    lines.add(1, "");
                }
                lines.add("");
                boolean bl = debugPressed = GLFW.glfwGetKey((long)Minecraft.getInstance().getWindow().handle(), (int)292) != 0;
                if (this.pressedDebugKey != debugPressed) {
                    this.pressedDebugKey = debugPressed;
                    if (this.pressedDebugKey) {
                        boolean bl2 = this.showingDebug = !this.showingDebug;
                    }
                }
                if (this.showingDebug) {
                    lines.add("ST: " + this.serverTickTimeNanos / 1000000L + ", CT: " + this.clientTickTimeNanos / 1000000L);
                    lines.add("RT: " + this.renderTimeNanos / 1000000L + ", ET: " + this.encodeTimeNanos / 1000000L);
                } else {
                    lines.add("Press [F3] to show debug info");
                }
                lines.add("");
                if (showCancel) {
                    if (GLFW.glfwGetKey((long)Minecraft.getInstance().getWindow().handle(), (int)256) != 0) {
                        long current = System.currentTimeMillis();
                        if (this.escapeCancelStartMillis <= 0L || current < this.escapeCancelStartMillis) {
                            this.escapeCancelStartMillis = current;
                        }
                        if (current - this.escapeCancelStartMillis > 3000L) {
                            cancel = true;
                            lines.add("Saving...");
                        } else {
                            long remainingSeconds = 3L - (current - this.escapeCancelStartMillis) / 1000L;
                            lines.add("Hold [ESC] to cancel (" + remainingSeconds + "s)");
                        }
                    } else {
                        lines.add("Hold [ESC] to cancel");
                        this.escapeCancelStartMillis = -1L;
                    }
                }
                bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
                x = scaledWidth / 2;
                int n = scaledHeight / 2;
                Objects.requireNonNull(font);
                y = n - 9 * (lines.size() + 1) / 2;
                for (String line : lines) {
                    if (line.isEmpty()) {
                        Objects.requireNonNull(font);
                        y += 9 / 2 + 1;
                        continue;
                    }
                    font.drawInBatch(line, (float)x - (float)font.width(line) / 2.0f, (float)y, -1, true, (Matrix4fc)poseMatrix, (MultiBufferSource)bufferSource, Font.DisplayMode.NORMAL, 0, 0xF000F0);
                    Objects.requireNonNull(font);
                    y += 9;
                }
                double mouseX = ReplayUI.imguiGlfw.rawMouseX / (double)window.getScreenWidth() * (double)scaledWidth;
                double mouseY = ReplayUI.imguiGlfw.rawMouseY / (double)window.getScreenHeight() * (double)scaledHeight;
                Objects.requireNonNull(font);
                y += 9 / 2 + 1;
                patreon = "https://www.patreon.com/flashbackmod";
                patreonWidth = font.width(patreon);
                if (!(mouseX > (double)((float)x - (float)patreonWidth / 2.0f)) || !(mouseX < (double)((float)x + (float)patreonWidth / 2.0f)) || !(mouseY > (double)y)) break block24;
                Objects.requireNonNull(font);
                if (!(mouseY < (double)(y + 9))) break block24;
                MutableComponent underlined = Component.literal((String)patreon).withStyle(ChatFormatting.UNDERLINE);
                font.drawInBatch((Component)underlined, (float)x - (float)patreonWidth / 2.0f, (float)y, -1, true, (Matrix4fc)poseMatrix, (MultiBufferSource)bufferSource, Font.DisplayMode.NORMAL, 0, 0xF000F0);
                if (GLFW.glfwGetMouseButton((long)window.handle(), (int)0) != 0) {
                    if (!this.patreonLinkClicked) {
                        this.patreonLinkClicked = true;
                        Util.getPlatform().openUri(patreon);
                    }
                    break block25;
                } else {
                    this.patreonLinkClicked = false;
                }
                break block25;
            }
            font.drawInBatch(patreon, (float)x - (float)patreonWidth / 2.0f, (float)y, -1, true, (Matrix4fc)poseMatrix, (MultiBufferSource)bufferSource, Font.DisplayMode.NORMAL, 0, 0xF000F0);
            this.patreonLinkClicked = false;
        }
        bufferSource.endBatch();
        RenderSystem.outputColorTextureOverride = previousColorOutputOverride;
        RenderSystem.outputDepthTextureOverride = previousDepthOutputOverride;
        if (!window.isMinimized()) {
            displayTarget.blitToScreen();
        }
        RenderSystem.flipFrame(null);
        RenderSystem.getDynamicUniforms().reset();
        return cancel;
    }

    private String formatTime(long millis) {
        long seconds = millis / 1000L % 60L;
        long minutes = millis / 1000L / 60L % 60L;
        long hours = millis / 1000L / 60L / 60L;
        if (hours > 0L) {
            return hours + "h " + minutes + "m " + seconds + "s";
        }
        if (minutes > 0L) {
            return minutes + "m " + seconds + "s";
        }
        return seconds + "s";
    }

    private static List<TickInfo> calculateTicks(EditorState editorState, int startTick, int endTick, double fps) {
        ArrayList<TickInfo> ticks = new ArrayList<TickInfo>();
        ticks.add(new TickInfo(0.0, 0.0, false));
        BigDecimal residual = BigDecimal.ZERO;
        BigDecimal fpsReciprocal = BigDecimal.ONE.divide(BigDecimal.valueOf(fps), MathContext.DECIMAL128);
        int currentTick = 0;
        TickrateKeyframeCapture capture = new TickrateKeyframeCapture();
        int count = endTick - startTick;
        int startFrozen = -1;
        while (currentTick <= count) {
            double freezeDerivative;
            BigDecimal remainingFrame = BigDecimal.ONE;
            while (remainingFrame.compareTo(BigDecimal.ZERO) > 0) {
                capture.tickrate = 20.0f;
                capture.frozen = false;
                editorState.applyKeyframes(capture, (float)(startTick + currentTick) + residual.floatValue());
                BigDecimal ticksThisFrame = BigDecimal.valueOf(capture.tickrate).multiply(fpsReciprocal).multiply(remainingFrame);
                if (ticksThisFrame.compareTo(BigDecimal.ONE) > 0) {
                    residual = residual.add(BigDecimal.ONE);
                    remainingFrame = remainingFrame.multiply(BigDecimal.ONE.subtract(BigDecimal.ONE.divide(ticksThisFrame, MathContext.DECIMAL128))).round(MathContext.DECIMAL128);
                    continue;
                }
                residual = residual.add(ticksThisFrame);
                break;
            }
            int roundedResidual = residual.intValue();
            residual = residual.subtract(BigDecimal.valueOf(roundedResidual));
            if ((currentTick += roundedResidual) >= count) break;
            double currentTickDouble = (double)currentTick + residual.doubleValue();
            if (currentTickDouble % 1.0 == 0.0) {
                residual = BigDecimal.ZERO;
            }
            if (!capture.frozen) {
                startFrozen = -1;
                ticks.add(new TickInfo(currentTickDouble, currentTickDouble, false));
                continue;
            }
            if (startFrozen < 0) {
                startFrozen = currentTick;
            }
            if (capture.frozenDelay == 0) {
                ticks.add(new TickInfo(currentTickDouble, Math.min(currentTickDouble, (double)startFrozen), true));
                continue;
            }
            double deltaFromStart = currentTickDouble - (double)startFrozen;
            double freezeClientTicks = capture.frozenDelay <= 5 ? 0.999 : 1.999;
            double d = freezeDerivative = capture.frozenDelay <= 5 ? 1.0 : 0.5;
            if (deltaFromStart > (double)capture.frozenDelay) {
                ticks.add(new TickInfo(currentTickDouble, Math.min(currentTickDouble, (double)startFrozen + freezeClientTicks), true));
                continue;
            }
            if (capture.frozenDelay == 1) {
                ticks.add(new TickInfo(currentTickDouble, Math.min(currentTickDouble, (double)startFrozen + freezeClientTicks), false));
                continue;
            }
            double freezePowerBase = FreezeSlowdownFormula.getFreezePowerBase(capture.frozenDelay, freezeDerivative);
            double clientTicks = freezeClientTicks * FreezeSlowdownFormula.calculateFreezeClientTick(deltaFromStart, capture.frozenDelay, freezePowerBase);
            ticks.add(new TickInfo(currentTickDouble, Math.min(currentTickDouble, (double)startFrozen + clientTicks), false));
        }
        return ticks;
    }

    static {
        displayTarget = null;
    }

    private record TempFileInfo(String name, Path file, Path folder) {
    }

    private record TickInfo(double serverTick, double clientTick, boolean frozen) {
    }
}

