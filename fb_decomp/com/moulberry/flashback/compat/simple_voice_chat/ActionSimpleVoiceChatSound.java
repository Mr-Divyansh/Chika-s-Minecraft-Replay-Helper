/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerPlayer
 */
package com.moulberry.flashback.compat.simple_voice_chat;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.action.Action;
import com.moulberry.flashback.packet.FlashbackVoiceChatSound;
import com.moulberry.flashback.playback.ReplayPlayer;
import com.moulberry.flashback.playback.ReplayServer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public class ActionSimpleVoiceChatSound
implements Action {
    private static final Identifier NAME = Flashback.createIdentifier("action/simple_voice_chat_sound_optional");
    public static final ActionSimpleVoiceChatSound INSTANCE = new ActionSimpleVoiceChatSound();

    private ActionSimpleVoiceChatSound() {
    }

    @Override
    public Identifier name() {
        return NAME;
    }

    @Override
    public void handle(ReplayServer replayServer, RegistryFriendlyByteBuf friendlyByteBuf) {
        boolean sendVoiceChat;
        FlashbackVoiceChatSound soundPacket = (FlashbackVoiceChatSound)FlashbackVoiceChatSound.STREAM_CODEC.decode((Object)friendlyByteBuf);
        if (replayServer.isProcessingSnapshot) {
            return;
        }
        if (Flashback.isExporting()) {
            sendVoiceChat = Flashback.EXPORT_JOB.getSettings().recordAudio() && Flashback.EXPORT_JOB.getCurrentTickDouble() > 0.0;
        } else {
            boolean bl = sendVoiceChat = !replayServer.fastForwarding && !replayServer.replayPaused;
        }
        if (sendVoiceChat) {
            for (ReplayPlayer replayViewer : replayServer.getReplayViewers()) {
                ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)soundPacket);
            }
        }
    }
}

