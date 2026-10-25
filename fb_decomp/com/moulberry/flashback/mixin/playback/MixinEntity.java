/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.playback;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Entity.class})
public abstract class MixinEntity {
    @Shadow
    private Level level;

    @Shadow
    public abstract boolean isInvisible();

    @Shadow
    public abstract boolean isInvisibleTo(Player var1);

    @Shadow
    public abstract UUID getUUID();

    @WrapOperation(method={"startRiding(Lnet/minecraft/world/entity/Entity;ZZ)Z"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/level/Level;isClientSide()Z")})
    public boolean startRiding_isClientSide(Level instance, Operation<Boolean> original) {
        if (Flashback.isInReplay()) {
            return true;
        }
        return (Boolean)original.call(new Object[]{instance});
    }

    @Inject(method={"isInvisibleTo"}, at={@At(value="HEAD")}, cancellable=true)
    public void isInvisibleTo(Player player, CallbackInfoReturnable<Boolean> cir) {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer != null && player == Minecraft.getInstance().player) {
            int localId;
            Entity entity;
            EditorState editorState = EditorStateManager.getCurrent();
            if (editorState != null && editorState.isEntityHidden((Entity)this)) {
                cir.setReturnValue((Object)true);
            }
            if ((entity = this.level.getEntity(localId = replayServer.getLocalPlayerId())) instanceof Player) {
                Player localPlayer = (Player)entity;
                cir.setReturnValue((Object)this.isInvisibleTo(localPlayer));
            } else {
                cir.setReturnValue((Object)this.isInvisible());
            }
        }
    }
}

