/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.Cache
 *  com.google.common.cache.CacheBuilder
 *  de.maxhenkel.voicechat.api.Position
 *  de.maxhenkel.voicechat.api.audiochannel.ClientEntityAudioChannel
 *  de.maxhenkel.voicechat.api.audiochannel.ClientLocationalAudioChannel
 *  de.maxhenkel.voicechat.api.audiochannel.ClientStaticAudioChannel
 *  de.maxhenkel.voicechat.voice.client.ClientManager
 *  de.maxhenkel.voicechat.voice.client.ClientVoicechat
 *  net.minecraft.world.phys.Vec3
 */
package com.moulberry.flashback.compat.simple_voice_chat;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.compat.simple_voice_chat.SimpleVoiceChatPlugin;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.packet.FlashbackVoiceChatSound;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import de.maxhenkel.voicechat.api.Position;
import de.maxhenkel.voicechat.api.audiochannel.ClientEntityAudioChannel;
import de.maxhenkel.voicechat.api.audiochannel.ClientLocationalAudioChannel;
import de.maxhenkel.voicechat.api.audiochannel.ClientStaticAudioChannel;
import de.maxhenkel.voicechat.voice.client.ClientManager;
import de.maxhenkel.voicechat.voice.client.ClientVoicechat;
import java.lang.runtime.SwitchBootstraps;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.world.phys.Vec3;

public class SimpleVoiceChatPlayback {
    private static final Cache<UUID, ClientStaticAudioChannel> staticAudioChannelCache = CacheBuilder.newBuilder().expireAfterAccess(Duration.ofMinutes(1L)).build();
    private static final Cache<UUID, ClientLocationalAudioChannel> locationAudioChannelCache = CacheBuilder.newBuilder().expireAfterAccess(Duration.ofMinutes(1L)).build();
    private static final Cache<UUID, ClientEntityAudioChannel> entityAudioChannelCache = CacheBuilder.newBuilder().expireAfterAccess(Duration.ofMinutes(1L)).build();
    private static final int ERROR_LOG_MAX = 8;
    private static int errorLogCount = 0;

    public static void play(FlashbackVoiceChatSound sound) {
        block9: {
            try {
                UUID source = sound.source();
                EditorState editorState = EditorStateManager.getCurrent();
                if (editorState == null || editorState.hideDuringExport.contains(source) || editorState.muteVoice.contains(source)) {
                    return;
                }
                boolean whispering = false;
                FlashbackVoiceChatSound flashbackVoiceChatSound = sound;
                Objects.requireNonNull(flashbackVoiceChatSound);
                FlashbackVoiceChatSound flashbackVoiceChatSound2 = flashbackVoiceChatSound;
                int n = 0;
                switch (SwitchBootstraps.typeSwitch("typeSwitch", new Object[]{FlashbackVoiceChatSound.SoundStatic.class, FlashbackVoiceChatSound.SoundLocational.class, FlashbackVoiceChatSound.SoundEntity.class}, (FlashbackVoiceChatSound)flashbackVoiceChatSound2, n)) {
                    case 0: {
                        FlashbackVoiceChatSound.SoundStatic soundStatic = (FlashbackVoiceChatSound.SoundStatic)flashbackVoiceChatSound2;
                        ClientStaticAudioChannel channel = (ClientStaticAudioChannel)staticAudioChannelCache.get((Object)source, () -> SimpleVoiceChatPlugin.CLIENT_API.createStaticAudioChannel(source));
                        channel.play(sound.samples());
                        break;
                    }
                    case 1: {
                        FlashbackVoiceChatSound.SoundLocational soundLocational = (FlashbackVoiceChatSound.SoundLocational)flashbackVoiceChatSound2;
                        Vec3 pos = soundLocational.position();
                        Position position = SimpleVoiceChatPlugin.CLIENT_API.createPosition(pos.x, pos.y, pos.z);
                        ClientLocationalAudioChannel channel = (ClientLocationalAudioChannel)locationAudioChannelCache.get((Object)source, () -> SimpleVoiceChatPlugin.CLIENT_API.createLocationalAudioChannel(source, position));
                        channel.setLocation(position);
                        channel.setDistance(soundLocational.distance());
                        channel.play(sound.samples());
                        break;
                    }
                    case 2: {
                        FlashbackVoiceChatSound.SoundEntity soundEntity = (FlashbackVoiceChatSound.SoundEntity)flashbackVoiceChatSound2;
                        ClientEntityAudioChannel channel = (ClientEntityAudioChannel)entityAudioChannelCache.get((Object)source, () -> SimpleVoiceChatPlugin.CLIENT_API.createEntityAudioChannel(source));
                        channel.setWhispering(soundEntity.whispering());
                        channel.setDistance(soundEntity.distance());
                        channel.play(sound.samples());
                        whispering = soundEntity.whispering();
                        break;
                    }
                }
                ClientVoicechat client = ClientManager.getClient();
                if (client != null) {
                    client.getTalkCache().updateLevel(sound.source(), null, whispering, sound.samples());
                }
            }
            catch (Exception | NoSuchMethodError e) {
                ReplayUI.setInfoOverlay("Error while playing Simple Voice Chat audio. Ensure that both Simple Voice Chat and Flashback are up-to-date");
                if (errorLogCount >= 8) break block9;
                Flashback.LOGGER.error("Error while trying to play voice chat sound", e);
                if (++errorLogCount != 8) break block9;
                Flashback.LOGGER.error("Stopping logging voice chat playback errors since there are too many");
            }
        }
    }

    public static void cleanUp() {
        staticAudioChannelCache.cleanUp();
        locationAudioChannelCache.cleanUp();
        entityAudioChannelCache.cleanUp();
    }
}

