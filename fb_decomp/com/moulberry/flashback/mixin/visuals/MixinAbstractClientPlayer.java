/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.player.PlayerSkin
 *  net.minecraft.world.level.Level
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

import com.mojang.authlib.GameProfile;
import com.moulberry.flashback.FilePlayerSkin;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={AbstractClientPlayer.class})
public abstract class MixinAbstractClientPlayer
extends Player {
    @Unique
    @Nullable
    private PlayerInfo fallbackPlayerInfo;
    @Unique
    private PlayerInfo skinOverridePlayerInfo = null;

    @Shadow
    @Nullable
    public abstract PlayerInfo getPlayerInfo();

    public MixinAbstractClientPlayer(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    public void postInit(ClientLevel clientLevel, GameProfile gameProfile, CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            if (Minecraft.getInstance().getConnection() != null) {
                try {
                    this.getPlayerInfo();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            this.fallbackPlayerInfo = new PlayerInfo(gameProfile, false);
        }
    }

    @Inject(method={"getPlayerInfo"}, at={@At(value="RETURN")}, cancellable=true)
    public void getPlayerInfo(CallbackInfoReturnable<PlayerInfo> cir) {
        if (cir.getReturnValue() == null) {
            cir.setReturnValue((Object)this.fallbackPlayerInfo);
        }
    }

    @Inject(method={"getSkin"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    public void getSkin(CallbackInfoReturnable<PlayerSkin> cir) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null) {
            FilePlayerSkin filePlayerSkin = editorState.skinOverrideFromFile.get(this.getUUID());
            if (filePlayerSkin != null) {
                cir.setReturnValue((Object)filePlayerSkin.getSkin());
                return;
            }
            GameProfile skinOverride = editorState.skinOverride.get(this.getUUID());
            if (skinOverride != null) {
                if (this.skinOverridePlayerInfo == null || this.skinOverridePlayerInfo.getProfile() != skinOverride) {
                    this.skinOverridePlayerInfo = new PlayerInfo(skinOverride, false);
                }
                cir.setReturnValue((Object)this.skinOverridePlayerInfo.getSkin());
            }
        }
    }
}

