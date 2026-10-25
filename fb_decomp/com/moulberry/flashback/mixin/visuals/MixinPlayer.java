/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.scores.PlayerTeam
 *  net.minecraft.world.scores.Team
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.visuals;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.Utils;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={Player.class})
public abstract class MixinPlayer
extends LivingEntity {
    protected MixinPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @WrapOperation(method={"getDisplayName"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/scores/PlayerTeam;formatNameForTeam(Lnet/minecraft/world/scores/Team;Lnet/minecraft/network/chat/Component;)Lnet/minecraft/network/chat/MutableComponent;")})
    public MutableComponent getDisplayName_formatNameForTeam(Team team, Component component, Operation<MutableComponent> original) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && team instanceof PlayerTeam) {
            boolean hideSuffix;
            PlayerTeam playerTeam = (PlayerTeam)team;
            boolean hidePrefix = editorState.hideTeamPrefix.contains(this.uuid) && !Utils.isComponentEmpty(playerTeam.getPlayerPrefix());
            boolean bl = hideSuffix = editorState.hideTeamSuffix.contains(this.uuid) && !Utils.isComponentEmpty(playerTeam.getPlayerSuffix());
            if (hidePrefix || hideSuffix) {
                ChatFormatting chatFormatting;
                MutableComponent mutableComponent = Component.empty();
                if (!hidePrefix) {
                    mutableComponent = mutableComponent.append(playerTeam.getPlayerPrefix());
                }
                mutableComponent = mutableComponent.append(component);
                if (!hideSuffix) {
                    mutableComponent = mutableComponent.append(playerTeam.getPlayerSuffix());
                }
                if ((chatFormatting = playerTeam.getColor()) != ChatFormatting.RESET) {
                    mutableComponent.withStyle(chatFormatting);
                }
                return mutableComponent;
            }
        }
        return (MutableComponent)original.call(new Object[]{team, component});
    }
}

