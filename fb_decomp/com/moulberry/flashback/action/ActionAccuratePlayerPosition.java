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

public class ActionAccuratePlayerPosition
implements Action {
    private static final Identifier NAME = Flashback.createIdentifier("action/accurate_player_position_optional");
    public static final ActionAccuratePlayerPosition INSTANCE = new ActionAccuratePlayerPosition();

    private ActionAccuratePlayerPosition() {
    }

    @Override
    public Identifier name() {
        return NAME;
    }

    @Override
    public void handle(ReplayServer replayServer, RegistryFriendlyByteBuf friendlyByteBuf) {
        replayServer.handleAccuratePlayerPosition(friendlyByteBuf);
    }
}

