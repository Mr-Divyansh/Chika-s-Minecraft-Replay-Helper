/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.llamalad7.mixinextras.sugar.Local
 *  com.mojang.blaze3d.platform.Window
 *  com.mojang.blaze3d.systems.RenderSystem
 *  it.unimi.dsi.fastutil.floats.FloatUnaryOperator
 *  net.minecraft.client.Camera
 *  net.minecraft.client.CameraType
 *  net.minecraft.client.DeltaTracker
 *  net.minecraft.client.DeltaTracker$Timer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.Options
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.Services
 *  net.minecraft.server.WorldStem
 *  net.minecraft.server.level.progress.LevelLoadListener
 *  net.minecraft.server.packs.repository.PackRepository
 *  net.minecraft.util.Util
 *  net.minecraft.util.thread.ReentrantBlockableEventLoop
 *  net.minecraft.world.TickRateManager
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.gamerules.GameRules
 *  net.minecraft.world.level.storage.LevelStorageSource$LevelStorageAccess
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.FreezeSlowdownFormula;
import com.moulberry.flashback.combo_options.GlowingOverride;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.exporting.ExportJob;
import com.moulberry.flashback.exporting.ExportJobQueue;
import com.moulberry.flashback.exporting.PerfectFrames;
import com.moulberry.flashback.ext.MinecraftExt;
import com.moulberry.flashback.keyframe.handler.MinecraftKeyframeHandler;
import com.moulberry.flashback.keyframe.handler.TickrateKeyframeCapture;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.sound.FlashbackAudioManager;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import com.moulberry.flashback.visuals.AccurateEntityPositionHandler;
import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.progress.LevelLoadListener;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.util.Util;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Minecraft.class})
public abstract class MixinMinecraft
extends ReentrantBlockableEventLoop<Runnable>
implements MinecraftExt {
    @Shadow
    @Nullable
    public ClientLevel level;
    @Shadow
    @Nullable
    public LocalPlayer player;
    @Shadow
    @Final
    public DeltaTracker.Timer deltaTracker;
    @Shadow
    public long clientTickCount;
    @Shadow
    @Final
    public Options options;
    @Shadow
    @Final
    public LevelRenderer levelRenderer;
    @Shadow
    @Final
    private Services services;
    @Shadow
    @Final
    private Window window;
    @Unique
    private boolean inReplayLast = false;
    @Unique
    private int serverTickFreezeDelayStart = -1;
    @Unique
    private double clientTickFreezeDelayStart = -1.0;
    @Unique
    private final DeltaTracker.Timer localPlayerTimer = new DeltaTracker.Timer(20.0f, 0L, FloatUnaryOperator.identity());
    @Unique
    private final AtomicBoolean applyKeyframes = new AtomicBoolean(false);
    @Unique
    private final ThreadLocal<MinecraftExt.StartReplayServerInfo> info = new ThreadLocal();

    public MixinMinecraft(String string) {
        super(string, true);
    }

    @Shadow
    public abstract void setScreen(@Nullable Screen var1);

    @Shadow
    protected abstract float getTickTargetMillis(float var1);

    @Shadow
    @Nullable
    public abstract Entity getCameraEntity();

    @Shadow
    public abstract void doWorldLoad(LevelStorageSource.LevelStorageAccess var1, PackRepository var2, WorldStem var3, Optional<GameRules> var4, boolean var5);

    @Inject(method={"pauseGame"}, at={@At(value="HEAD")}, cancellable=true)
    public void pauseGame(boolean bl, CallbackInfo ci) {
        if (Flashback.EXPORT_JOB != null) {
            ci.cancel();
        }
    }

    @Inject(method={"renderNames"}, at={@At(value="HEAD")}, cancellable=true)
    private static void renderNames(CallbackInfoReturnable<Boolean> cir) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && !editorState.replayVisuals.renderNametags) {
            cir.setReturnValue((Object)false);
        }
    }

    @Inject(method={"shouldEntityAppearGlowing"}, at={@At(value="HEAD")}, cancellable=true)
    private void shouldEntityAppearGlowing(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null) {
            GlowingOverride glowingOverride = editorState.glowingOverride.get(entity.getUUID());
            if (glowingOverride == GlowingOverride.FORCE_GLOW) {
                cir.setReturnValue((Object)true);
            } else if (glowingOverride == GlowingOverride.FORCE_NO_GLOW) {
                cir.setReturnValue((Object)false);
            }
        }
    }

    @WrapOperation(method={"runTick"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/sounds/SoundManager;updateSource(Lnet/minecraft/client/Camera;)V")})
    public void runTick_updateSource(SoundManager instance, Camera camera, Operation<Void> original) {
        Camera audioCamera;
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && (audioCamera = editorState.getAudioCamera()) != null) {
            original.call(new Object[]{instance, audioCamera});
            return;
        }
        original.call(new Object[]{instance, camera});
    }

    @Inject(method={"renderFrame"}, at={@At(value="INVOKE", target="Lcom/mojang/blaze3d/pipeline/RenderTarget;blitToScreen()V", shift=At.Shift.AFTER)})
    public void afterMainBlit(boolean bl, CallbackInfo ci) {
        if (!RenderSystem.isOnRenderThread()) {
            return;
        }
        ReplayUI.drawOverlay();
    }

    @Inject(method={"tick"}, at={@At(value="RETURN")})
    public void tick(CallbackInfo ci) {
        boolean inReplay;
        if (Flashback.RECORDER != null) {
            Flashback.RECORDER.endTickWithContext(false);
        }
        EditorStateManager.saveIfNeeded();
        ReplayServer replayServer = Flashback.getReplayServer();
        boolean bl = inReplay = replayServer != null;
        if (inReplay != this.inReplayLast) {
            this.inReplayLast = inReplay;
            if (inReplay) {
                Minecraft.getInstance().options.hideGui = false;
            } else {
                EditorStateManager.reset();
            }
        }
        FlashbackConfigV1 config = Flashback.getConfig();
        if (inReplay && !config.advanced.disableThirdPersonCancel && ReplayUI.isActive() && this.player != null && this.getCameraEntity() == this.player && this.options.getCameraType() != CameraType.FIRST_PERSON) {
            this.options.setCameraType(CameraType.FIRST_PERSON);
            this.levelRenderer.needsUpdate();
            ReplayUI.setInfoOverlay("Forced perspective to First-Person");
        }
    }

    @Inject(method={"getTickTargetMillis"}, at={@At(value="HEAD")}, cancellable=true)
    public void getTickTargetMillis(float f, CallbackInfoReturnable<Float> cir) {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer != null) {
            if (this.level == null) {
                this.clientTickFreezeDelayStart = -1.0;
                this.serverTickFreezeDelayStart = -1;
                return;
            }
            EditorState editorState = EditorStateManager.getCurrent();
            if (editorState != null && !replayServer.replayPaused) {
                TickRateManager tickRateManager;
                DeltaTracker.Timer timer;
                double partialReplayTick = replayServer.getPartialReplayTick();
                TickrateKeyframeCapture capture = new TickrateKeyframeCapture();
                editorState.applyKeyframes(capture, (float)partialReplayTick);
                if (capture.frozen && capture.frozenDelay > 0 && (timer = this.deltaTracker) instanceof DeltaTracker.Timer) {
                    DeltaTracker.Timer timer2 = timer;
                    if (this.clientTickFreezeDelayStart < 0.0) {
                        this.clientTickFreezeDelayStart = this.clientTickCount + 1L;
                        this.serverTickFreezeDelayStart = (int)partialReplayTick;
                        TickRateManager tickRateManager2 = this.level.tickRateManager();
                        tickRateManager2.setFrozenTicksToRun(capture.frozenDelay <= 5 ? 1 : 2);
                    }
                    double freezeClientTicks = capture.frozenDelay <= 5 ? 0.999 : 1.999;
                    double freezeDerivative = capture.frozenDelay <= 5 ? 1.0 : 0.5;
                    double deltaFromStart = partialReplayTick - (double)this.serverTickFreezeDelayStart;
                    if (deltaFromStart >= 0.0 && deltaFromStart <= (double)capture.frozenDelay) {
                        double freezePowerBase = FreezeSlowdownFormula.getFreezePowerBase(capture.frozenDelay, freezeDerivative);
                        double clientTicks = freezeClientTicks * FreezeSlowdownFormula.calculateFreezeClientTick(deltaFromStart, capture.frozenDelay, freezePowerBase);
                        double currentClientTicks = (double)((float)this.clientTickCount + timer2.deltaTickResidual) - this.clientTickFreezeDelayStart;
                        double freezeRate = Math.max((double)0.01f, Math.min(1.0, clientTicks - currentClientTicks));
                        float tickrate = Math.max(1.0f, capture.tickrate) * (float)freezeRate;
                        cir.setReturnValue((Object)Float.valueOf(1000.0f / tickrate));
                        return;
                    }
                } else {
                    this.clientTickFreezeDelayStart = -1.0;
                    this.serverTickFreezeDelayStart = -1;
                }
                if ((tickRateManager = this.level.tickRateManager()).runsNormally()) {
                    float manualMultiplier = replayServer.getDesiredTickRate(true) / 20.0f;
                    cir.setReturnValue((Object)Float.valueOf(1000.0f / Math.max(1.0f, capture.tickrate * manualMultiplier)));
                }
            } else {
                TickRateManager tickRateManager = this.level.tickRateManager();
                if (tickRateManager.runsNormally()) {
                    cir.setReturnValue((Object)Float.valueOf(tickRateManager.millisecondsPerTick()));
                }
            }
        }
    }

    @Inject(method={"disconnect(Lnet/minecraft/client/gui/screens/Screen;ZZ)V"}, at={@At(value="HEAD")})
    public void disconnectHead(Screen screen, boolean isTransferring, boolean stopSounds, CallbackInfo ci) {
        try {
            if (Flashback.getConfig().recordingControls.automaticallyFinish && Flashback.RECORDER != null && !isTransferring) {
                Flashback.finishRecordingReplay();
            }
        }
        catch (Exception e) {
            Flashback.LOGGER.error("Failed to finish replay on disconnect", (Throwable)e);
        }
    }

    @Inject(method={"disconnect(Lnet/minecraft/client/gui/screens/Screen;ZZ)V"}, at={@At(value="RETURN")})
    public void disconnectReturn(Screen screen, boolean bl, boolean bl2, CallbackInfo ci) {
        Flashback.updateIsInReplay();
    }

    @Inject(method={"runTick"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/Minecraft;runAllTasks()V", shift=At.Shift.AFTER)})
    public void runTick_runAllTasks(boolean runTick, CallbackInfo ci) {
        if (ExportJobQueue.drainingQueue) {
            if (ExportJobQueue.queuedJobs.isEmpty()) {
                ExportJobQueue.drainingQueue = false;
            } else if (Flashback.EXPORT_JOB == null) {
                Flashback.EXPORT_JOB = new ExportJob(ExportJobQueue.queuedJobs.removeFirst());
            }
        }
        if (Flashback.EXPORT_JOB != null && !ReplayUI.isActive()) {
            try {
                PerfectFrames.enable();
                Flashback.EXPORT_JOB.run();
            }
            finally {
                PerfectFrames.disable();
                Flashback.EXPORT_JOB = null;
            }
        }
        if (Flashback.isInReplay()) {
            long millis = Util.getMillis();
            this.localPlayerTimer.advanceRealTime(millis);
            int localPlayerTicks = this.localPlayerTimer.advanceGameTime(millis);
            if (this.flashback$overridingLocalPlayerTimer()) {
                localPlayerTicks = Math.min(10, localPlayerTicks);
                for (int i = 0; i < localPlayerTicks; ++i) {
                    this.level.guardEntityTick(arg_0 -> ((ClientLevel)this.level).tickNonPassenger(arg_0), (Entity)this.player);
                }
            }
        }
    }

    @Override
    public boolean flashback$overridingLocalPlayerTimer() {
        return !Flashback.isExporting() && this.level != null && this.player != null && !this.player.isPassenger() && !this.player.isRemoved() && Math.round(this.getTickTargetMillis(50.0f)) != 50;
    }

    @Override
    public float flashback$getLocalPlayerPartialTick(float originalPartialTick) {
        if (this.getCameraEntity() != this.player || !this.flashback$overridingLocalPlayerTimer()) {
            return originalPartialTick;
        }
        return this.localPlayerTimer.getGameTimeDeltaPartialTick(true);
    }

    @Override
    public void flashback$applyKeyframes() {
        this.applyKeyframes.set(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Inject(method={"runTick"}, at={@At(value="INVOKE", target="Lcom/mojang/blaze3d/platform/Window;setErrorSection(Ljava/lang/String;)V", ordinal=1)}, cancellable=true)
    public void runTick_setErrorSection(boolean bl, CallbackInfo ci) {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer == null) {
            FlashbackAudioManager.stopAll();
            return;
        }
        LocalPlayer player = this.player;
        DeltaTracker.Timer deltaTracker = this.deltaTracker;
        if (Flashback.RECORDER != null && player != null) {
            float partialTick = deltaTracker.getGameTimeDeltaPartialTick(true);
            Flashback.RECORDER.trackPartialPosition((Entity)player, partialTick);
        }
        AccurateEntityPositionHandler.apply(this.level, (DeltaTracker)deltaTracker);
        boolean paused = replayServer.replayPaused;
        boolean forceApplyKeyframes = this.applyKeyframes.compareAndSet(true, false);
        if (paused) {
            FlashbackAudioManager.pauseAll();
        }
        if (!paused || forceApplyKeyframes) {
            if (!paused) {
                FlashbackAudioManager.startHandling();
            }
            try {
                EditorState editorState = EditorStateManager.get(replayServer.getMetadata().replayIdentifier);
                editorState.applyKeyframes(new MinecraftKeyframeHandler((Minecraft)this), (float)replayServer.getPartialReplayTick());
            }
            finally {
                if (!paused) {
                    FlashbackAudioManager.finishHandling();
                }
            }
        }
        if (!replayServer.doClientRendering()) {
            ci.cancel();
        }
    }

    @Inject(method={"pauseIfInactive"}, at={@At(value="HEAD")}, cancellable=true)
    public void pauseIfInactive(CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            ci.cancel();
        }
    }

    @WrapOperation(method={"doWorldLoad"}, at={@At(value="INVOKE", target="Lnet/minecraft/server/MinecraftServer;spin(Ljava/util/function/Function;)Lnet/minecraft/server/MinecraftServer;")})
    public MinecraftServer doWorldLoad_spin(Function<Thread, MinecraftServer> function, Operation<MinecraftServer> original, @Local(argsOnly=true) LevelStorageSource.LevelStorageAccess levelStorageAccess, @Local(argsOnly=true) PackRepository packRepository, @Local(argsOnly=true) WorldStem stem, @Local(argsOnly=true) Optional<GameRules> gameRules, @Local LevelLoadListener levelLoadListener) {
        MinecraftExt.StartReplayServerInfo info = this.info.get();
        if (info != null) {
            function = thread -> new ReplayServer((Thread)thread, (Minecraft)this, levelStorageAccess, packRepository, stem, gameRules, this.services, levelLoadListener, info.playbackUUID(), info.path());
        }
        return (MinecraftServer)original.call(new Object[]{function});
    }

    @Inject(method={"doWorldLoad"}, at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;singleplayerServer:Lnet/minecraft/client/server/IntegratedServer;", opcode=181, shift=At.Shift.AFTER)})
    public void afterSetSingleplayerServer(LevelStorageSource.LevelStorageAccess levelSourceAccess, PackRepository packRepository, WorldStem worldStem, Optional<GameRules> gameRules, boolean newWorld, CallbackInfo ci) {
        Flashback.updateIsInReplay();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void flashback$startReplayServer(LevelStorageSource.LevelStorageAccess levelStorageAccess, PackRepository packRepository, WorldStem stem, Optional<GameRules> gameRules, MinecraftExt.StartReplayServerInfo info) {
        this.info.set(info);
        try {
            this.doWorldLoad(levelStorageAccess, packRepository, stem, gameRules, false);
        }
        finally {
            this.info.remove();
        }
    }
}

