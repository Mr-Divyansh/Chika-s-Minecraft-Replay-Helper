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

public class ActionCreateLocalPlayer
implements Action {
    private static final Identifier NAME = Flashback.createIdentifier("action/create_local_player");
    public static final ActionCreateLocalPlayer INSTANCE = new ActionCreateLocalPlayer();

    private ActionCreateLocalPlayer() {
    }

    @Override
    public Identifier name() {
        return NAME;
    }

    @Override
    public void handle(ReplayServer replayServer, RegistryFriendlyByteBuf friendlyByteBuf) {
        replayServer.handleCreateLocalPlayer(friendlyByteBuf);
    }
}

