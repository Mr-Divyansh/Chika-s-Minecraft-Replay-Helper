/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.llamalad7.mixinextras.sugar.Local
 *  com.llamalad7.mixinextras.sugar.ref.LocalRef
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.client.renderer.ItemInHandRenderer
 *  net.minecraft.client.renderer.SubmitNodeCollector
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Quaternionfc
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.playback;

import com.google.common.base.MoreObjects;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.ext.ItemInHandRendererExt;
import com.moulberry.flashback.ext.RemotePlayerExt;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionfc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ItemInHandRenderer.class})
public abstract class MixinItemInHandRenderer
implements ItemInHandRendererExt {
    @Shadow
    private float oMainHandHeight;
    @Shadow
    private float mainHandHeight;
    @Shadow
    private float oOffHandHeight;
    @Shadow
    private float offHandHeight;
    @Shadow
    private ItemStack mainHandItem;
    @Shadow
    private ItemStack offHandItem;
    @Shadow
    @Final
    private Minecraft minecraft;
    @Unique
    private static final int RENDER_MAIN_HAND = 1;
    @Unique
    private static final int RENDER_OFF_HAND = 2;
    @Unique
    private static final int RENDER_BOTH_HANDS = 3;
    @Unique
    private UUID lastSpectatingPlayer = null;

    @Shadow
    private static boolean isChargedCrossbow(ItemStack itemStack) {
        return false;
    }

    @Shadow
    protected abstract void renderArmWithItem(AbstractClientPlayer var1, float var2, float var3, InteractionHand var4, float var5, ItemStack var6, float var7, PoseStack var8, SubmitNodeCollector var9, int var10);

    @Unique
    private static int evaluateWhichHandsToRender(AbstractClientPlayer player) {
        boolean isHoldingCrossbow;
        ItemStack mainStack = player.getMainHandItem();
        ItemStack offStack = player.getOffhandItem();
        boolean isHoldingBow = mainStack.is((Object)Items.BOW) || offStack.is((Object)Items.BOW);
        boolean bl = isHoldingCrossbow = mainStack.is((Object)Items.CROSSBOW) || offStack.is((Object)Items.CROSSBOW);
        if (!isHoldingBow && !isHoldingCrossbow) {
            return 3;
        }
        if (player.isUsingItem()) {
            ItemStack useStack = player.getUseItem();
            InteractionHand interactionHand = player.getUsedItemHand();
            if (!useStack.is((Object)Items.BOW) && !useStack.is((Object)Items.CROSSBOW)) {
                return interactionHand == InteractionHand.MAIN_HAND && MixinItemInHandRenderer.isChargedCrossbow(player.getOffhandItem()) ? 1 : 3;
            }
            return interactionHand == InteractionHand.MAIN_HAND ? 1 : 2;
        }
        if (MixinItemInHandRenderer.isChargedCrossbow(mainStack)) {
            return 1;
        }
        return 3;
    }

    @Override
    public void flashback$renderHandsWithItems(float partialTick, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, AbstractClientPlayer clientPlayer, int i, @Nullable Set<InteractionHand> renderableArms) {
        float m;
        float l;
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && editorState.isEntityHidden((Entity)clientPlayer)) {
            return;
        }
        float g = clientPlayer.getAttackAnim(partialTick);
        InteractionHand interactionHand = (InteractionHand)MoreObjects.firstNonNull((Object)clientPlayer.swingingArm, (Object)InteractionHand.MAIN_HAND);
        float h = Mth.lerp((float)partialTick, (float)clientPlayer.xRotO, (float)clientPlayer.getXRot());
        int handRenderSelection = MixinItemInHandRenderer.evaluateWhichHandsToRender(clientPlayer);
        if (renderableArms != null) {
            if (!renderableArms.contains(InteractionHand.MAIN_HAND)) {
                handRenderSelection &= 0xFFFFFFFE;
            }
            if (!renderableArms.contains(InteractionHand.OFF_HAND)) {
                handRenderSelection &= 0xFFFFFFFD;
            }
        }
        if (handRenderSelection == 0) {
            return;
        }
        if (clientPlayer instanceof RemotePlayerExt) {
            RemotePlayerExt remotePlayerExt = (RemotePlayerExt)clientPlayer;
            float xBob = remotePlayerExt.flashback$getXBob(partialTick);
            float yBob = remotePlayerExt.flashback$getYBob(partialTick);
            poseStack.mulPose((Quaternionfc)Axis.XP.rotationDegrees(Mth.wrapDegrees((float)(clientPlayer.getViewXRot(partialTick) - xBob)) * 0.1f));
            poseStack.mulPose((Quaternionfc)Axis.YP.rotationDegrees(Mth.wrapDegrees((float)(clientPlayer.getViewYRot(partialTick) - yBob)) * 0.1f));
        }
        if ((handRenderSelection & 1) != 0) {
            l = interactionHand == InteractionHand.MAIN_HAND ? g : 0.0f;
            m = 1.0f - Mth.lerp((float)partialTick, (float)this.oMainHandHeight, (float)this.mainHandHeight);
            this.renderArmWithItem(clientPlayer, partialTick, h, InteractionHand.MAIN_HAND, l, this.mainHandItem, m, poseStack, submitNodeCollector, i);
        }
        if ((handRenderSelection & 2) != 0) {
            l = interactionHand == InteractionHand.OFF_HAND ? g : 0.0f;
            m = 1.0f - Mth.lerp((float)partialTick, (float)this.oOffHandHeight, (float)this.offHandHeight);
            this.renderArmWithItem(clientPlayer, partialTick, h, InteractionHand.OFF_HAND, l, this.offHandItem, m, poseStack, submitNodeCollector, i);
        }
        this.minecraft.gameRenderer.getFeatureRenderDispatcher().renderAllFeatures();
        this.minecraft.renderBuffers().bufferSource().endBatch();
    }

    @Inject(method={"renderPlayerArm"}, at={@At(value="FIELD", target="Lnet/minecraft/client/renderer/ItemInHandRenderer;entityRenderDispatcher:Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;")})
    public void renderPlayerArm(CallbackInfo ci, @Local LocalRef<AbstractClientPlayer> player) {
        AbstractClientPlayer spectatingPlayer = Flashback.getSpectatingPlayer();
        if (spectatingPlayer != null) {
            player.set((Object)spectatingPlayer);
        }
    }

    @Inject(method={"tick"}, at={@At(value="HEAD")}, cancellable=true)
    public void tick(CallbackInfo ci) {
        AbstractClientPlayer spectatingPlayer = Flashback.getSpectatingPlayer();
        if (spectatingPlayer == null) {
            this.lastSpectatingPlayer = null;
        } else {
            ItemStack newMainHandItem = spectatingPlayer.getMainHandItem();
            ItemStack newOffHandItem = spectatingPlayer.getOffhandItem();
            if (!spectatingPlayer.getUUID().equals(this.lastSpectatingPlayer)) {
                this.lastSpectatingPlayer = spectatingPlayer.getUUID();
                this.mainHandItem = newMainHandItem;
                this.offHandItem = newOffHandItem;
                this.mainHandHeight = 1.0f;
                this.oMainHandHeight = 1.0f;
                this.offHandHeight = 1.0f;
                this.oOffHandHeight = 1.0f;
                ci.cancel();
                return;
            }
            this.oMainHandHeight = this.mainHandHeight;
            this.oOffHandHeight = this.offHandHeight;
            if (ItemStack.matches((ItemStack)this.mainHandItem, (ItemStack)newMainHandItem)) {
                this.mainHandItem = newMainHandItem;
            }
            if (ItemStack.matches((ItemStack)this.offHandItem, (ItemStack)newOffHandItem)) {
                this.offHandItem = newOffHandItem;
            }
            float str = spectatingPlayer.getAttackStrengthScale(1.0f);
            this.mainHandHeight += Mth.clamp((float)((this.mainHandItem == newMainHandItem ? str * str * str : 0.0f) - this.mainHandHeight), (float)-0.4f, (float)0.4f);
            this.offHandHeight += Mth.clamp((float)((float)(this.offHandItem == newOffHandItem ? 1 : 0) - this.offHandHeight), (float)-0.4f, (float)0.4f);
            if (this.mainHandHeight < 0.1f) {
                this.mainHandItem = newMainHandItem;
            }
            if (this.offHandHeight < 0.1f) {
                this.offHandItem = newOffHandItem;
            }
            ci.cancel();
        }
    }
}

