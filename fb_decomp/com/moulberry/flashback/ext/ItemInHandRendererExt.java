/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.client.renderer.SubmitNodeCollector
 *  net.minecraft.world.InteractionHand
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.ext;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Set;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.InteractionHand;
import org.jetbrains.annotations.Nullable;

public interface ItemInHandRendererExt {
    public void flashback$renderHandsWithItems(float var1, PoseStack var2, SubmitNodeCollector var3, AbstractClientPlayer var4, int var5, @Nullable Set<InteractionHand> var6);
}

