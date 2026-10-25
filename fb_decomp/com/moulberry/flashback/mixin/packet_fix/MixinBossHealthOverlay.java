/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.components.BossHealthOverlay
 *  net.minecraft.client.gui.components.LerpingBossEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.game.ClientboundBossEventPacket
 *  net.minecraft.network.protocol.game.ClientboundBossEventPacket$Handler
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.BossEvent$BossBarOverlay
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin.packet_fix;

import com.moulberry.flashback.Flashback;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={BossHealthOverlay.class})
public class MixinBossHealthOverlay {
    @Shadow
    @Final
    Map<UUID, LerpingBossEvent> events;

    @Inject(method={"update"}, at={@At(value="HEAD")}, cancellable=true)
    public void update(ClientboundBossEventPacket clientboundBossEventPacket, final CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            clientboundBossEventPacket.dispatch(new ClientboundBossEventPacket.Handler(){
                final /* synthetic */ MixinBossHealthOverlay this$0;
                {
                    MixinBossHealthOverlay mixinBossHealthOverlay = this$0;
                    Objects.requireNonNull(mixinBossHealthOverlay);
                    this.this$0 = mixinBossHealthOverlay;
                }

                public void updateProgress(UUID uuid, float f) {
                    if (!this.this$0.events.containsKey(uuid)) {
                        ci.cancel();
                    }
                }

                public void updateName(UUID uuid, Component component) {
                    if (!this.this$0.events.containsKey(uuid)) {
                        ci.cancel();
                    }
                }

                public void updateStyle(UUID uuid, BossEvent.BossBarColor bossBarColor, BossEvent.BossBarOverlay bossBarOverlay) {
                    if (!this.this$0.events.containsKey(uuid)) {
                        ci.cancel();
                    }
                }

                public void updateProperties(UUID uuid, boolean bl, boolean bl2, boolean bl3) {
                    if (!this.this$0.events.containsKey(uuid)) {
                        ci.cancel();
                    }
                }
            });
        }
    }
}

