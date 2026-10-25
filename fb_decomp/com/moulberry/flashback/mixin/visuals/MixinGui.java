/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.client.DeltaTracker
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.Gui$ContextualInfo
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.client.multiplayer.MultiPlayerGameMode
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.GameType
 *  net.minecraft.world.phys.HitResult
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.visuals;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.ui.CustomImGuiImplGlfw;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Gui.class})
public abstract class MixinGui {
    @Unique
    private GameType cameraGameType = null;
    @Unique
    private boolean shouldHideElements = false;

    @Shadow
    @Nullable
    protected abstract Player getCameraPlayer();

    @Inject(method={"extractRenderState"}, at={@At(value="HEAD")})
    public void extractRenderState_updateCameraGameType(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        this.shouldHideElements = false;
        this.cameraGameType = null;
        if (Flashback.isInReplay()) {
            PlayerInfo playerInfo;
            ClientPacketListener connection;
            this.shouldHideElements = ReplayUI.isActive() || Flashback.isExporting();
            this.cameraGameType = Minecraft.getInstance().gameMode.getPlayerMode();
            Player player = this.getCameraPlayer();
            if (player != null && (connection = Minecraft.getInstance().getConnection()) != null && (playerInfo = connection.getPlayerInfo(player.getUUID())) != null) {
                this.cameraGameType = playerInfo.getGameMode();
            }
        }
    }

    @Inject(method={"extractChat"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    public void extractChat(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        EditorState editorState;
        if (this.shouldHideElements && (editorState = EditorStateManager.getCurrent()) != null && !editorState.replayVisuals.showChat) {
            ci.cancel();
        }
    }

    @Inject(method={"extractTitle"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    public void extractTitle(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        EditorState editorState;
        if (this.shouldHideElements && (editorState = EditorStateManager.getCurrent()) != null && !editorState.replayVisuals.showTitleText) {
            ci.cancel();
        }
    }

    @Inject(method={"extractScoreboardSidebar"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    public void extractScoreboardSidebar(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        EditorState editorState;
        if (this.shouldHideElements && (editorState = EditorStateManager.getCurrent()) != null && !editorState.replayVisuals.showScoreboard) {
            ci.cancel();
        }
    }

    @Inject(method={"extractOverlayMessage"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    public void extractOverlayMessage(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        EditorState editorState;
        if (this.shouldHideElements && (editorState = EditorStateManager.getCurrent()) != null && !editorState.replayVisuals.showActionBar) {
            ci.cancel();
        }
    }

    @Inject(method={"nextContextualInfoState"}, at={@At(value="HEAD")}, cancellable=true)
    public void nextContextualInfoState(CallbackInfoReturnable<Gui.ContextualInfo> cir) {
        if (this.cameraGameType != null) {
            cir.setReturnValue((Object)(this.cameraGameType.isSurvival() ? Gui.ContextualInfo.EXPERIENCE : Gui.ContextualInfo.EMPTY));
        }
    }

    @Inject(method={"extractHotbarAndDecorations"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    public void extractHotbarAndDecorations(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        EditorState editorState;
        if (this.shouldHideElements && (editorState = EditorStateManager.getCurrent()) != null && !editorState.replayVisuals.showHotbar) {
            ci.cancel();
        }
    }

    @WrapOperation(method={"extractHotbarAndDecorations"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;getPlayerMode()Lnet/minecraft/world/level/GameType;")}, require=0)
    public GameType extractHotbarAndDecorations_getPlayerMode(MultiPlayerGameMode instance, Operation<GameType> original) {
        if (this.cameraGameType != null) {
            return this.cameraGameType;
        }
        return (GameType)original.call(new Object[]{instance});
    }

    @WrapOperation(method={"extractHotbarAndDecorations"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;canHurtPlayer()Z")}, require=0)
    public boolean extractHotbarAndDecorations_canHurtPlayer(MultiPlayerGameMode instance, Operation<Boolean> original) {
        if (this.cameraGameType != null) {
            return this.cameraGameType.isSurvival();
        }
        return (Boolean)original.call(new Object[]{instance});
    }

    @WrapOperation(method={"extractHotbarAndDecorations"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;hasExperience()Z")}, require=0)
    public boolean extractHotbarAndDecorations_hasExperience(MultiPlayerGameMode instance, Operation<Boolean> original) {
        if (this.cameraGameType != null) {
            return this.cameraGameType.isSurvival();
        }
        return (Boolean)original.call(new Object[]{instance});
    }

    @WrapOperation(method={"extractHotbarAndDecorations"}, at={@At(value="FIELD", target="Lnet/minecraft/client/player/LocalPlayer;experienceLevel:I")}, require=0)
    public int extractExperienceLevel_experienceLevel(LocalPlayer instance, Operation<Integer> original) {
        Player player;
        if (Flashback.isInReplay() && (player = this.getCameraPlayer()) != null) {
            return player.experienceLevel;
        }
        return (Integer)original.call(new Object[]{instance});
    }

    @WrapOperation(method={"extractCrosshair"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F")}, require=0)
    public float extractCrosshair_getAttackStrengthScale(LocalPlayer instance, float partialTick, Operation<Float> original) {
        Player player;
        if (Flashback.isInReplay() && (player = this.getCameraPlayer()) != null) {
            return player.getAttackStrengthScale(partialTick);
        }
        return ((Float)original.call(new Object[]{instance, Float.valueOf(partialTick)})).floatValue();
    }

    @WrapOperation(method={"extractCrosshair"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/player/LocalPlayer;getCurrentItemAttackStrengthDelay()F")}, require=0)
    public float extractCrosshair_getCurrentItemAttackStrengthDelay(LocalPlayer instance, Operation<Float> original) {
        Player player;
        if (Flashback.isInReplay() && (player = this.getCameraPlayer()) != null) {
            return player.getCurrentItemAttackStrengthDelay();
        }
        return ((Float)original.call(new Object[]{instance})).floatValue();
    }

    @Inject(method={"extractVignette"}, at={@At(value="HEAD")}, cancellable=true)
    public void extractVignette(GuiGraphicsExtractor guiGraphics, Entity entity, CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            ci.cancel();
        }
    }

    @Inject(method={"canRenderCrosshairForSpectator"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    public void canRenderCrosshairForSpectator(HitResult hitResult, CallbackInfoReturnable<Boolean> cir) {
        if (Flashback.isInReplay()) {
            if (!Flashback.isExporting() && ReplayUI.isActive() && ReplayUI.imguiGlfw.getMouseHandledBy() == CustomImGuiImplGlfw.MouseHandledBy.GAME) {
                cir.setReturnValue((Object)true);
                return;
            }
            cir.setReturnValue((Object)(Flashback.getSpectatingPlayer() != null ? 1 : 0));
        }
    }
}

