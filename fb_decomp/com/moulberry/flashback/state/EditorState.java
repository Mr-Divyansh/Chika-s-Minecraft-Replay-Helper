/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.Camera
 *  net.minecraft.client.DeltaTracker
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.player.PlayerModelPart
 *  net.minecraft.world.level.GameType
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.state;

import com.mojang.authlib.GameProfile;
import com.moulberry.flashback.FilePlayerSkin;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.FlashbackGson;
import com.moulberry.flashback.combo_options.GlowingOverride;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeTickrate;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.state.EditorScene;
import com.moulberry.flashback.state.KeyframeTrack;
import com.moulberry.flashback.state.RealTimeMapping;
import com.moulberry.flashback.visuals.ReplayVisuals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.StampedLock;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.level.GameType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public class EditorState {
    volatile transient boolean dirty = false;
    public volatile transient int modCount;
    private volatile transient int lastRealTimeMappingModCount = this.modCount = ThreadLocalRandom.current().nextInt();
    private volatile transient RealTimeMapping realTimeMapping = null;
    public final ReplayVisuals replayVisuals = new ReplayVisuals();
    private final StampedLock sceneLock = new StampedLock();
    private final List<EditorScene> scenes;
    private int sceneIndex = 0;
    public double zoomMin = 0.0;
    public double zoomMax = 1.0;
    public Set<String> usedByPaths = new HashSet<String>();
    public UUID audioSourceEntity = null;
    public Set<UUID> hideDuringExport = new HashSet<UUID>();
    public boolean hideAllSpectators = false;
    public Set<UUID> muteVoice = new HashSet<UUID>();
    public Set<UUID> hideNametags = new HashSet<UUID>();
    public Map<UUID, GameProfile> skinOverride = new HashMap<UUID, GameProfile>();
    public Map<UUID, FilePlayerSkin> skinOverrideFromFile = new HashMap<UUID, FilePlayerSkin>();
    public Map<UUID, String> nameOverride = new HashMap<UUID, String>();
    public Map<UUID, GlowingOverride> glowingOverride = new HashMap<UUID, GlowingOverride>();
    public Set<UUID> hideTeamPrefix = new HashSet<UUID>();
    public Set<UUID> hideTeamSuffix = new HashSet<UUID>();
    public Set<UUID> hideBelowName = new HashSet<UUID>();
    public Set<UUID> hideCape = new HashSet<UUID>();
    public Set<String> filteredEntities = new HashSet<String>();
    public Set<String> filteredParticles = new HashSet<String>();
    public Map<UUID, EnumSet<EquipmentSlot>> hiddenEquipment = new HashMap<UUID, EnumSet<EquipmentSlot>>();
    public Map<UUID, EnumSet<PlayerModelPart>> hiddenModelParts = new HashMap<UUID, EnumSet<PlayerModelPart>>();

    public EditorState() {
        this.scenes = new ArrayList<EditorScene>();
        this.scenes.add(new EditorScene("Scene 1"));
        FlashbackConfigV1 config = Flashback.getConfig();
        if (config.internal.enableOverrideFovByDefault) {
            this.replayVisuals.overrideFov = true;
            if (this.replayVisuals.overrideFovAmount < 0.0f) {
                this.replayVisuals.overrideFovAmount = config.internal.defaultOverrideFov;
            }
        }
    }

    @ApiStatus.Internal
    public long acquireRead() {
        return this.sceneLock.readLock();
    }

    @ApiStatus.Internal
    public long acquireWrite() {
        return this.sceneLock.writeLock();
    }

    @ApiStatus.Internal
    public void release(long stamp) {
        this.sceneLock.unlock(stamp);
    }

    @ApiStatus.Internal
    public List<EditorScene> getScenes(long stamp) {
        if (!this.sceneLock.validate(stamp)) {
            throw new IllegalStateException("Invalid stamp!");
        }
        return this.scenes;
    }

    public int getSceneIndex() {
        return this.sceneIndex;
    }

    @ApiStatus.Internal
    public void setSceneIndex(int sceneIndex, long stamp) {
        if (!this.sceneLock.validate(stamp)) {
            throw new IllegalStateException("Invalid stamp!");
        }
        this.sceneIndex = sceneIndex;
    }

    @ApiStatus.Internal
    public EditorScene getCurrentScene(long stamp) {
        if (!this.sceneLock.validate(stamp)) {
            throw new IllegalStateException("Invalid stamp!");
        }
        return this.scenes.get(this.sceneIndex);
    }

    private EditorScene currentScene() {
        return this.scenes.get(this.sceneIndex);
    }

    @Nullable
    public Camera getAudioCamera() {
        if (this.audioSourceEntity == null) {
            return null;
        }
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return null;
        }
        Entity sourceEntity = (Entity)level.getEntities().get(this.audioSourceEntity);
        if (sourceEntity == null) {
            return null;
        }
        Camera dummyCamera = new Camera();
        dummyCamera.eyeHeight = sourceEntity.getEyeHeight();
        dummyCamera.setLevel(level);
        dummyCamera.setEntity(sourceEntity);
        dummyCamera.update((DeltaTracker)Minecraft.getInstance().deltaTracker);
        return dummyCamera;
    }

    public void markDirty() {
        this.dirty = true;
        ++this.modCount;
    }

    public void save(Path path) {
        this.dirty = false;
        String serialized = FlashbackGson.COMPRESSED.toJson((Object)this, EditorState.class);
        try {
            Files.createDirectories(path.getParent(), new FileAttribute[0]);
            Files.writeString(path, (CharSequence)serialized, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE, StandardOpenOption.SYNC);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public static EditorState load(Path path) {
        if (!Files.exists(path, new LinkOption[0])) {
            return null;
        }
        String serialized = null;
        try {
            serialized = Files.readString(path);
            return (EditorState)FlashbackGson.COMPRESSED.fromJson(serialized, EditorState.class);
        }
        catch (Exception e) {
            Flashback.LOGGER.error("Error loading editor state", (Throwable)e);
            Flashback.LOGGER.error("JSON: {}", (Object)serialized);
            return null;
        }
    }

    public EditorState copy() {
        String serialized = FlashbackGson.COMPRESSED.toJson((Object)this, EditorState.class);
        return (EditorState)FlashbackGson.COMPRESSED.fromJson(serialized, EditorState.class);
    }

    public EditorState copyWithoutKeyframes() {
        String serialized = FlashbackGson.COMPRESSED.toJson((Object)this, EditorState.class);
        EditorState editorState = (EditorState)FlashbackGson.COMPRESSED.fromJson(serialized, EditorState.class);
        for (EditorScene scene : editorState.scenes) {
            scene.keyframeTracks.clear();
        }
        return editorState;
    }

    public boolean isEntityHidden(Entity entity) {
        Player player;
        if (this.hideAllSpectators && entity instanceof Player && (player = (Player)entity).gameMode() == GameType.SPECTATOR) {
            return true;
        }
        return this.hideDuringExport.contains(entity.getUUID());
    }

    public boolean maybeHasHiddenEntities() {
        return this.hideAllSpectators || !this.hideDuringExport.isEmpty();
    }

    public void applyKeyframes(KeyframeHandler keyframeHandler, float tick) {
        this.applyKeyframes(keyframeHandler, tick, 0L);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiStatus.Internal
    public void applyKeyframes(KeyframeHandler keyframeHandler, float tick, long stamp) {
        HashSet<Class<KeyframeChange>> applied = new HashSet<Class<KeyframeChange>>();
        HashMap<Class<KeyframeChange>, KeyframeTrack> maybeApplyLastTick = new HashMap<Class<KeyframeChange>, KeyframeTrack>();
        this.updateRealtimeMappingsIfNeeded();
        boolean unlock = false;
        if (!this.sceneLock.validate(stamp)) {
            stamp = this.sceneLock.readLock();
            unlock = true;
        }
        try {
            KeyframeChange change;
            for (KeyframeTrack keyframeTrack : this.currentScene().keyframeTracks) {
                Class<KeyframeChange> keyframeChangeType;
                if (!keyframeTrack.enabled || (keyframeChangeType = keyframeTrack.keyframeType.keyframeChangeType()) == null || !keyframeTrack.keyframeType.allowApplyingDuplicateKeyframeChanges() && applied.contains(keyframeChangeType) || !keyframeTrack.keyframeType.supportsHandler(keyframeHandler)) continue;
                change = keyframeTrack.createKeyframeChange(tick, this.realTimeMapping);
                if (change == null) {
                    KeyframeTrack oldTrack;
                    if (!keyframeHandler.alwaysApplyLastKeyframe() || keyframeTrack.keyframeType.neverApplyLastKeyframe() || keyframeTrack.keyframesByTick.isEmpty() || !((float)keyframeTrack.keyframesByTick.lastKey().intValue() <= tick) || (oldTrack = (KeyframeTrack)maybeApplyLastTick.get(keyframeChangeType)) != null && keyframeTrack.keyframesByTick.lastKey() <= oldTrack.keyframesByTick.lastKey()) continue;
                    maybeApplyLastTick.put(keyframeChangeType, keyframeTrack);
                    continue;
                }
                if (change.getClass() != keyframeChangeType) {
                    throw new IllegalStateException("Expected " + String.valueOf(keyframeChangeType) + ", got " + String.valueOf(change.getClass()) + ". Caused by: " + keyframeTrack.keyframeType.id());
                }
                applied.add(keyframeChangeType);
                maybeApplyLastTick.remove(keyframeChangeType);
                change.apply(keyframeHandler);
            }
            if (keyframeHandler.alwaysApplyLastKeyframe() && !maybeApplyLastTick.isEmpty()) {
                for (Map.Entry entry : maybeApplyLastTick.entrySet()) {
                    KeyframeTrack keyframeTrack = (KeyframeTrack)entry.getValue();
                    change = keyframeTrack.createKeyframeChange(keyframeTrack.keyframesByTick.lastKey().intValue(), this.realTimeMapping);
                    if (change == null) continue;
                    if (change.getClass() != entry.getKey()) {
                        throw new IllegalStateException("Expected " + String.valueOf(entry.getKey()) + ", got " + String.valueOf(change.getClass()) + ". Caused by: " + keyframeTrack.keyframeType.id());
                    }
                    change.apply(keyframeHandler);
                }
            }
        }
        finally {
            if (unlock) {
                this.sceneLock.unlock(stamp);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateRealtimeMappingsIfNeeded() {
        long stamp = this.sceneLock.readLock();
        try {
            FlashbackConfigV1 config = Flashback.getConfig();
            if (!config.keyframes.useRealtimeInterpolation) {
                this.sceneLock.unlock(stamp);
                stamp = this.sceneLock.writeLock();
                this.lastRealTimeMappingModCount = this.modCount;
                this.realTimeMapping = null;
            } else if (this.realTimeMapping == null || this.lastRealTimeMappingModCount != this.modCount) {
                this.sceneLock.unlock(stamp);
                stamp = this.sceneLock.writeLock();
                if (this.realTimeMapping == null || this.lastRealTimeMappingModCount != this.modCount) {
                    this.calculateRealtimeMappings();
                }
            }
        }
        finally {
            this.sceneLock.unlock(stamp);
        }
    }

    private void calculateRealtimeMappings() {
        this.lastRealTimeMappingModCount = this.modCount;
        this.realTimeMapping = new RealTimeMapping();
        ArrayList<KeyframeTrack> applicableTracks = new ArrayList<KeyframeTrack>();
        int start = -1;
        int end = -1;
        int lastApplicableKeyframe = -1;
        for (KeyframeTrack keyframeTrack : this.currentScene().keyframeTracks) {
            Object keyframeChangeType;
            if (!keyframeTrack.enabled || keyframeTrack.keyframesByTick.isEmpty() || (keyframeChangeType = keyframeTrack.keyframeType.keyframeChangeType()) == null || !KeyframeChangeTickrate.class.isAssignableFrom((Class<?>)keyframeChangeType)) continue;
            applicableTracks.add(keyframeTrack);
            int trackStart = keyframeTrack.keyframesByTick.firstKey();
            int trackEnd = keyframeTrack.keyframesByTick.lastKey();
            if (start < 0 || trackStart < start) {
                start = trackStart;
            }
            if (end < 0 || trackEnd > end) {
                end = trackEnd;
            }
            if (keyframeTrack.keyframeType.neverApplyLastKeyframe() || lastApplicableKeyframe >= 0 && trackEnd <= lastApplicableKeyframe) continue;
            lastApplicableKeyframe = trackEnd;
        }
        if (applicableTracks.isEmpty() || start < 0 || end < 0) {
            return;
        }
        float lastSpeed = Float.NaN;
        block1: for (int tick = start; tick <= end; ++tick) {
            for (KeyframeTrack keyframeTrack : applicableTracks) {
                KeyframeChange change = keyframeTrack.createKeyframeChange(tick, this.realTimeMapping);
                if (!(change instanceof KeyframeChangeTickrate)) continue;
                KeyframeChangeTickrate changeTickrate = (KeyframeChangeTickrate)change;
                float newSpeed = changeTickrate.tickrate() / 20.0f;
                if (newSpeed == lastSpeed) continue block1;
                lastSpeed = newSpeed;
                this.realTimeMapping.addMapping(tick, newSpeed);
                continue block1;
            }
        }
        for (KeyframeTrack keyframeTrack : applicableTracks) {
            KeyframeChange change = keyframeTrack.createKeyframeChange(end + 1, this.realTimeMapping);
            if (!(change instanceof KeyframeChangeTickrate)) continue;
            KeyframeChangeTickrate changeTickrate = (KeyframeChangeTickrate)change;
            float newSpeed = changeTickrate.tickrate() / 20.0f;
            if (newSpeed != lastSpeed) {
                this.realTimeMapping.addMapping(end + 1, newSpeed);
            }
            return;
        }
        for (KeyframeTrack keyframeTrack : applicableTracks) {
            if (keyframeTrack.keyframeType.neverApplyLastKeyframe() || keyframeTrack.keyframesByTick.lastKey() != lastApplicableKeyframe) continue;
            KeyframeChange change = keyframeTrack.createKeyframeChange(lastApplicableKeyframe, this.realTimeMapping);
            if (!(change instanceof KeyframeChangeTickrate)) break;
            KeyframeChangeTickrate changeTickrate = (KeyframeChangeTickrate)change;
            float newSpeed = changeTickrate.tickrate() / 20.0f;
            if (newSpeed != lastSpeed) {
                this.realTimeMapping.addMapping(end + 1, newSpeed);
            }
            return;
        }
        this.realTimeMapping.addMapping(end + 1, 1.0f);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setExportTicks(int start, int end, int totalTicks) {
        long stamp = this.sceneLock.writeLock();
        try {
            this.currentScene().setExportTicks(start, end, totalTicks);
            this.markDirty();
        }
        finally {
            this.sceneLock.unlock(stamp);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public StartAndEnd getExportStartAndEnd() {
        int start = -1;
        int end = -1;
        long stamp = this.sceneLock.readLock();
        try {
            EditorScene scene = this.currentScene();
            if (scene != null && (scene.exportStartTicks >= 0 || scene.exportEndTicks >= 0)) {
                ReplayServer replayServer;
                start = scene.exportStartTicks >= 0 ? scene.exportStartTicks : 0;
                end = scene.exportEndTicks >= 0 ? scene.exportEndTicks : ((replayServer = Flashback.getReplayServer()) == null ? start : replayServer.getTotalReplayTicks());
            }
        }
        finally {
            this.sceneLock.unlock(stamp);
        }
        return new StartAndEnd(start, end);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public StartAndEnd getFirstAndLastTicksInTracks() {
        int start = -1;
        int end = -1;
        long stamp = this.sceneLock.readLock();
        try {
            for (KeyframeTrack keyframeTrack : this.currentScene().keyframeTracks) {
                if (!keyframeTrack.enabled || keyframeTrack.keyframesByTick.isEmpty()) continue;
                int min = keyframeTrack.keyframesByTick.firstKey();
                Map.Entry<Integer, Keyframe> entry = keyframeTrack.keyframesByTick.lastEntry();
                int max = entry.getKey();
                float lastCustomWidth = entry.getValue().getCustomWidthInTicks();
                if (lastCustomWidth > 0.0f) {
                    max = entry.getKey() + (int)Math.ceil(lastCustomWidth);
                }
                start = start == -1 ? min : Math.min(start, min);
                if (end == -1) {
                    end = max;
                    continue;
                }
                end = Math.max(end, max);
            }
        }
        finally {
            this.sceneLock.unlock(stamp);
        }
        return new StartAndEnd(start, end);
    }

    public record StartAndEnd(int start, int end) {
    }
}

