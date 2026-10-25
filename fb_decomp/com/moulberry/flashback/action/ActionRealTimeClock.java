/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.resources.Identifier
 */
package com.moulberry.flashback.action;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.action.Action;
import com.moulberry.flashback.playback.ReplayServer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;

public class ActionRealTimeClock
implements Action {
    private static final Identifier NAME = Flashback.createIdentifier("action/real_time_clock_optional");
    public static final ActionRealTimeClock INSTANCE = new ActionRealTimeClock();

    private ActionRealTimeClock() {
    }

    @Override
    public Identifier name() {
        return NAME;
    }

    @Override
    public void handle(ReplayServer replayServer, RegistryFriendlyByteBuf friendlyByteBuf) {
        byte delta = friendlyByteBuf.readByte();
        if (delta == 0) {
            replayServer.setTimeRtc(friendlyByteBuf.readLong());
        } else {
            replayServer.updateTimeRtc(delta);
        }
    }
}

