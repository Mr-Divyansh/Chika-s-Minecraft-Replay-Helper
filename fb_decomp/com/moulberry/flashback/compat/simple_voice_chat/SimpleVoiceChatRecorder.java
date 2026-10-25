/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  de.maxhenkel.voicechat.api.events.ClientReceiveSoundEvent$EntitySound
 *  de.maxhenkel.voicechat.api.events.ClientReceiveSoundEvent$LocationalSound
 *  de.maxhenkel.voicechat.api.events.ClientReceiveSoundEvent$StaticSound
 *  de.maxhenkel.voicechat.api.events.ClientSoundEvent
 *  de.maxhenkel.voicechat.voice.client.ClientManager
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.phys.Vec3
 */
package com.moulberry.flashback.compat.simple_voice_chat;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.compat.simple_voice_chat.ActionSimpleVoiceChatSound;
import com.moulberry.flashback.compat.simple_voice_chat.SimpleVoiceChatPlugin;
import com.moulberry.flashback.packet.FlashbackVoiceChatSound;
import de.maxhenkel.voicechat.api.events.ClientReceiveSoundEvent;
import de.maxhenkel.voicechat.api.events.ClientSoundEvent;
import de.maxhenkel.voicechat.voice.client.ClientManager;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public class SimpleVoiceChatRecorder {
    private static boolean shouldWritePacket() {
        return Flashback.RECORDER != null && Flashback.RECORDER.readyToWrite() && Flashback.getConfig().recording.recordVoiceChat;
    }

    public static void onReceiveEntitySound(ClientReceiveSoundEvent.EntitySound event) {
        if (!SimpleVoiceChatRecorder.shouldWritePacket()) {
            return;
        }
        FlashbackVoiceChatSound.SoundEntity soundPacket = new FlashbackVoiceChatSound.SoundEntity(event.getId(), event.getRawAudio(), event.isWhispering(), event.getDistance());
        SimpleVoiceChatRecorder.submitSoundPacket(soundPacket);
    }

    public static void onReceiveLocationalSound(ClientReceiveSoundEvent.LocationalSound event) {
        if (!SimpleVoiceChatRecorder.shouldWritePacket()) {
            return;
        }
        Vec3 position = new Vec3(event.getPosition().getX(), event.getPosition().getY(), event.getPosition().getZ());
        FlashbackVoiceChatSound.SoundLocational soundPacket = new FlashbackVoiceChatSound.SoundLocational(event.getId(), event.getRawAudio(), position, event.getDistance());
        SimpleVoiceChatRecorder.submitSoundPacket(soundPacket);
    }

    public static void onReceiveStaticSound(ClientReceiveSoundEvent.StaticSound event) {
        if (!SimpleVoiceChatRecorder.shouldWritePacket()) {
            return;
        }
        FlashbackVoiceChatSound.SoundStatic soundPacket = new FlashbackVoiceChatSound.SoundStatic(event.getId(), event.getRawAudio());
        SimpleVoiceChatRecorder.submitSoundPacket(soundPacket);
    }

    public static void onSendSound(ClientSoundEvent event) {
        if (!SimpleVoiceChatRecorder.shouldWritePacket()) {
            return;
        }
        UUID id = ClientManager.getPlayerStateManager().getOwnID();
        Record soundPacket = SimpleVoiceChatPlugin.CLIENT_API.getGroup() != null ? new FlashbackVoiceChatSound.SoundStatic(id, event.getRawAudio()) : new FlashbackVoiceChatSound.SoundEntity(id, event.getRawAudio(), event.isWhispering(), (float)SimpleVoiceChatPlugin.CLIENT_API.getVoiceChatDistance());
        SimpleVoiceChatRecorder.submitSoundPacket(soundPacket);
    }

    private static void submitSoundPacket(FlashbackVoiceChatSound soundPacket) {
        Minecraft.getInstance().submit(() -> {
            if (SimpleVoiceChatRecorder.shouldWritePacket()) {
                Flashback.RECORDER.submitCustomTask(writer -> {
                    writer.startAction(ActionSimpleVoiceChatSound.INSTANCE);
                    FlashbackVoiceChatSound.STREAM_CODEC.encode((Object)writer.friendlyByteBuf(), (Object)soundPacket);
                    writer.finishAction(ActionSimpleVoiceChatSound.INSTANCE);
                });
            }
        });
    }
}

