/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.ClientClockManager
 *  net.minecraft.client.ClientClockManager$ClockInstance
 *  net.minecraft.core.Holder
 *  net.minecraft.world.clock.ClockNetworkState
 *  net.minecraft.world.clock.WorldClock
 *  net.minecraft.world.clock.WorldClocks
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.playback;

import com.moulberry.flashback.ext.ClientClockManagerExt;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.ClientClockManager;
import net.minecraft.core.Holder;
import net.minecraft.world.clock.ClockNetworkState;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ClientClockManager.class})
public class MixinClientClockManager
implements ClientClockManagerExt {
    @Shadow
    @Final
    private Map<Holder<WorldClock>, ClientClockManager.ClockInstance> clocks;

    @Override
    public Map<Holder<WorldClock>, ClockNetworkState> flashback$encodeClockUpdates() {
        HashMap<Holder<WorldClock>, ClockNetworkState> data = new HashMap<Holder<WorldClock>, ClockNetworkState>();
        for (Map.Entry<Holder<WorldClock>, ClientClockManager.ClockInstance> entry : this.clocks.entrySet()) {
            ClientClockManager.ClockInstance clock = entry.getValue();
            data.put(entry.getKey(), new ClockNetworkState(clock.totalTicks, clock.partialTick, clock.rate));
        }
        return data;
    }

    @Inject(method={"getTotalTicks"}, at={@At(value="HEAD")}, cancellable=true)
    public void getTotalTicks(Holder<WorldClock> definition, CallbackInfoReturnable<Long> cir) {
        EditorState editorState;
        if (definition.is(WorldClocks.OVERWORLD) && (editorState = EditorStateManager.getCurrent()) != null && editorState.replayVisuals.overrideTimeOfDay >= 0L) {
            cir.setReturnValue((Object)editorState.replayVisuals.overrideTimeOfDay);
        }
    }
}

