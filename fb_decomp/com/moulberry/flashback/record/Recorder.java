/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMultimap
 *  com.google.common.collect.ImmutableMultimap$Builder
 *  com.google.common.collect.Multimap
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.properties.PropertyMap
 *  com.mojang.blaze3d.pipeline.RenderTarget
 *  com.mojang.blaze3d.platform.NativeImage
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.DynamicOps
 *  io.netty.buffer.ByteBuf
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  net.fabricmc.fabric.api.networking.v1.context.PacketContext
 *  net.fabricmc.fabric.api.networking.v1.context.PacketContextProvider
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.SharedConstants
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.Screenshot
 *  net.minecraft.client.gui.components.BossHealthOverlay
 *  net.minecraft.client.gui.components.LerpingBossEvent
 *  net.minecraft.client.gui.components.PlayerTabOverlay
 *  net.minecraft.client.gui.screens.LevelLoadingScreen
 *  net.minecraft.client.multiplayer.ClientChunkCache
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.client.multiplayer.MultiPlayerGameMode
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.client.multiplayer.ServerData
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.client.resources.server.ServerPackManager$ServerPackData
 *  net.minecraft.client.server.IntegratedServer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.RegistrySynchronization
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.network.ConnectionProtocol
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.codec.ByteBufCodecs
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket
 *  net.minecraft.network.protocol.common.ClientboundResourcePackPopPacket
 *  net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket
 *  net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket
 *  net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener
 *  net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket
 *  net.minecraft.network.protocol.configuration.ClientboundUpdateEnabledFeaturesPacket
 *  net.minecraft.network.protocol.configuration.ConfigurationProtocols
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundAnimatePacket
 *  net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket
 *  net.minecraft.network.protocol.game.ClientboundBossEventPacket
 *  net.minecraft.network.protocol.game.ClientboundBundlePacket
 *  net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
 *  net.minecraft.network.protocol.game.ClientboundGameEventPacket
 *  net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket
 *  net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket
 *  net.minecraft.network.protocol.game.ClientboundLevelEventPacket
 *  net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData
 *  net.minecraft.network.protocol.game.ClientboundLoginPacket
 *  net.minecraft.network.protocol.game.ClientboundMapItemDataPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerChatPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket$Action
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket$Entry
 *  net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket
 *  net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket
 *  net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
 *  net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket
 *  net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket
 *  net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket
 *  net.minecraft.network.protocol.game.ClientboundSetExperiencePacket
 *  net.minecraft.network.protocol.game.ClientboundSetHealthPacket
 *  net.minecraft.network.protocol.game.ClientboundSetHeldSlotPacket
 *  net.minecraft.network.protocol.game.ClientboundSetObjectivePacket
 *  net.minecraft.network.protocol.game.ClientboundSetPassengersPacket
 *  net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket
 *  net.minecraft.network.protocol.game.ClientboundSetScorePacket
 *  net.minecraft.network.protocol.game.ClientboundSetTimePacket
 *  net.minecraft.network.protocol.game.ClientboundSoundEntityPacket
 *  net.minecraft.network.protocol.game.ClientboundSoundPacket
 *  net.minecraft.network.protocol.game.ClientboundSystemChatPacket
 *  net.minecraft.network.protocol.game.ClientboundTabListPacket
 *  net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket
 *  net.minecraft.network.protocol.game.CommonPlayerSpawnInfo
 *  net.minecraft.network.protocol.game.GameProtocols
 *  net.minecraft.network.syncher.SynchedEntityData$DataItem
 *  net.minecraft.network.syncher.SynchedEntityData$DataValue
 *  net.minecraft.resources.RegistryOps
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.TagNetworkSerialization
 *  net.minecraft.tags.TagNetworkSerialization$NetworkPayload
 *  net.minecraft.util.Mth
 *  net.minecraft.world.BossEvent
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.InterpolationHandler
 *  net.minecraft.world.entity.Leashable
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.flag.FeatureFlags
 *  net.minecraft.world.food.FoodData
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.GameType
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.border.WorldBorder
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.saveddata.maps.MapDecoration
 *  net.minecraft.world.level.saveddata.maps.MapId
 *  net.minecraft.world.level.saveddata.maps.MapItemSavedData
 *  net.minecraft.world.level.saveddata.maps.MapItemSavedData$MapPatch
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.scores.DisplaySlot
 *  net.minecraft.world.scores.Objective
 *  net.minecraft.world.scores.PlayerScoreEntry
 *  net.minecraft.world.scores.PlayerTeam
 *  net.minecraft.world.scores.Scoreboard
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.record;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.PacketHelper;
import com.moulberry.flashback.RegistryMetaHelper;
import com.moulberry.flashback.action.ActionAccuratePlayerPosition;
import com.moulberry.flashback.action.ActionCreateLocalPlayer;
import com.moulberry.flashback.action.ActionMoveEntities;
import com.moulberry.flashback.action.ActionNextTick;
import com.moulberry.flashback.action.ActionRealTimeClock;
import com.moulberry.flashback.action.PositionAndAngle;
import com.moulberry.flashback.compat.BobbyUtil;
import com.moulberry.flashback.compat.DistantHorizonsSupport;
import com.moulberry.flashback.ext.ClientClockManagerExt;
import com.moulberry.flashback.io.AsyncReplaySaver;
import com.moulberry.flashback.io.ReplayWriter;
import com.moulberry.flashback.mixin.compat.bobby.FakeChunkManagerAccessor;
import com.moulberry.flashback.packet.FlashbackAccurateEntityPosition;
import com.moulberry.flashback.record.FlashbackChunkMeta;
import com.moulberry.flashback.record.FlashbackMeta;
import com.moulberry.flashback.record.IgnoredPacketSet;
import com.moulberry.flashback.record.ReplayMarker;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.io.File;
import java.lang.ref.WeakReference;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;
import net.fabricmc.fabric.api.networking.v1.context.PacketContextProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.server.ServerPackManager;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ClientboundResourcePackPopPacket;
import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;
import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
import net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener;
import net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket;
import net.minecraft.network.protocol.configuration.ClientboundUpdateEnabledFeaturesPacket;
import net.minecraft.network.protocol.configuration.ConfigurationProtocols;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetHeldSlotPacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.CommonPlayerSpawnInfo;
import net.minecraft.network.protocol.game.GameProtocols;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagNetworkSerialization;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerScoreEntry;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import org.jetbrains.annotations.Nullable;

public class Recorder {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final int CHUNK_LENGTH_SECONDS = 300;
    private final AsyncReplaySaver asyncReplaySaver;
    private final StreamCodec<ByteBuf, Packet<? super ClientConfigurationPacketListener>> configurationPacketCodec;
    private StreamCodec<ByteBuf, Packet<? super ClientGamePacketListener>> gamePacketCodec;
    private int writtenTicksInChunk = 0;
    private int writtenTicks = 0;
    private final FlashbackMeta metadata = new FlashbackMeta();
    private boolean hasTakenScreenshot = false;
    private NativeImage finishedScreenshot = null;
    private final Queue<PacketWithPhase> pendingPackets = new ConcurrentLinkedQueue<PacketWithPhase>();
    private final WeakHashMap<Entity, Position> lastPositions = new WeakHashMap();
    private WeakReference<LocalPlayer> lastLocalPlayer = null;
    private final List<Object> lastPlayerEntityMeta = new ArrayList<Object>();
    private final Map<EquipmentSlot, ItemStack> lastPlayerEquipment = new EnumMap<EquipmentSlot, ItemStack>(EquipmentSlot.class);
    private final ItemStack[] lastHotbarItems = new ItemStack[9];
    private BlockPos lastDestroyPos = null;
    private int lastDestroyProgress = -1;
    private int lastSelectedSlot = -1;
    private float lastExperienceProgress = -1.0f;
    private int lastTotalExperience = -1;
    private int lastExperienceLevel = -1;
    private int lastFoodLevel = -1;
    private float lastSaturationLevel = -1.0f;
    private boolean wasSwinging = false;
    private int lastSwingTime = -1;
    private long lastRtcEpochMilli = 0L;
    private boolean forceRtcSync = true;
    private boolean isConfiguring = false;
    private boolean finishedConfiguration = false;
    private boolean finishedPausing = false;
    private ResourceKey<Level> lastDimensionType = null;
    private volatile boolean needsInitialSnapshot = true;
    private volatile boolean closeForWriting = false;
    private volatile boolean isPaused = false;
    private volatile boolean wasPaused = false;
    private volatile boolean skippedPacketDueToWaitingForWrite = false;
    private PositionAndAngle lastPlayerPositionAndAngle = null;
    private float lastPlayerPositionAndAnglePartialTick;
    private final TreeMap<Float, PositionAndAngle> partialPositions = new TreeMap();
    private int trackAccuratePositionCounter = 10;

    public Recorder(RegistryAccess registryAccess) {
        this.asyncReplaySaver = new AsyncReplaySaver(registryAccess);
        this.configurationPacketCodec = ConfigurationProtocols.CLIENTBOUND.codec();
        this.gamePacketCodec = GameProtocols.CLIENTBOUND_TEMPLATE.bind(RegistryFriendlyByteBuf.decorator((RegistryAccess)registryAccess)).codec();
        this.metadata.dataVersion = SharedConstants.getCurrentVersion().dataVersion().version();
        this.metadata.protocolVersion = SharedConstants.getProtocolVersion();
        this.metadata.versionString = FabricLoader.getInstance().getRawGameVersion();
        if (Flashback.isBobbyLoaded) {
            try {
                String bobbyWorldName;
                this.metadata.bobbyWorldName = bobbyWorldName = FakeChunkManagerAccessor.getCurrentWorldOrServerName(Minecraft.getInstance().getConnection());
            }
            catch (Throwable bobbyWorldName) {
                // empty catch block
            }
        }
        if (Flashback.supportsDistantHorizons) {
            this.metadata.distantHorizonPaths.putAll(DistantHorizonsSupport.getDimensionPaths());
        }
        this.metadata.namespacesForRegistries = RegistryMetaHelper.calculateNamespacesForRegistries();
        String worldName = null;
        ServerData serverData = Minecraft.getInstance().getCurrentServer();
        if (serverData != null) {
            worldName = serverData.name;
            if (worldName.equalsIgnoreCase(I18n.get((String)"selectServer.defaultName", (Object[])new Object[0]))) {
                worldName = null;
            }
        } else {
            IntegratedServer integratedServer = Minecraft.getInstance().getSingleplayerServer();
            if (integratedServer != null && (worldName = integratedServer.worldData.getLevelName()).equalsIgnoreCase(I18n.get((String)"selectWorld.newWorld", (Object[])new Object[0]))) {
                worldName = null;
            }
        }
        this.metadata.worldName = worldName;
    }

    public boolean readyToWrite() {
        return !this.closeForWriting && !this.needsInitialSnapshot && !this.wasPaused;
    }

    public void putDistantHorizonsPaths(Map<String, File> paths) {
        this.metadata.distantHorizonPaths.putAll(paths);
    }

    public void addMarker(ReplayMarker marker) {
        this.metadata.replayMarkers.put(this.writtenTicks, marker);
    }

    public void submitCustomTask(Consumer<ReplayWriter> consumer) {
        if (!this.readyToWrite()) {
            return;
        }
        this.asyncReplaySaver.submit(consumer);
    }

    public void setRegistryAccess(RegistryAccess registryAccess) {
        this.asyncReplaySaver.submit(writer -> writer.setRegistryAccess(registryAccess));
        this.gamePacketCodec = GameProtocols.CLIENTBOUND_TEMPLATE.bind(RegistryFriendlyByteBuf.decorator((RegistryAccess)registryAccess)).codec();
    }

    public String getDebugString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Flashback] Recording. T: ");
        builder.append(this.writtenTicks);
        builder.append(". S: ");
        builder.append(this.metadata.chunks.size());
        builder.append(" (");
        builder.append(this.writtenTicksInChunk);
        builder.append("/");
        builder.append(6000);
        builder.append(")");
        return builder.toString();
    }

    public void trackPartialPosition(Entity entity, float partialTick) {
        int localPlayerUpdatesPerSecond = Flashback.getConfig().recording.localPlayerUpdatesPerSecond;
        if (localPlayerUpdatesPerSecond <= 20) {
            return;
        }
        double x = Mth.lerp((double)partialTick, (double)entity.xo, (double)entity.getX());
        double y = Mth.lerp((double)partialTick, (double)entity.yo, (double)entity.getY());
        double z = Mth.lerp((double)partialTick, (double)entity.zo, (double)entity.getZ());
        float yaw = entity.getViewYRot(partialTick);
        float pitch = entity.getViewXRot(partialTick);
        this.partialPositions.put(Float.valueOf(partialTick), new PositionAndAngle(x, y, z, yaw, pitch));
    }

    public void startTick() {
        this.writeRtc(this.forceRtcSync);
        this.forceRtcSync = false;
    }

    private void writeRtc(boolean force) {
        if (this.needsInitialSnapshot || this.closeForWriting) {
            return;
        }
        Instant now = Instant.now();
        long epochMilli = now.toEpochMilli();
        long predicted = this.lastRtcEpochMilli + 50L;
        this.lastRtcEpochMilli = epochMilli;
        long delta = epochMilli - predicted;
        if (!force && delta == 0L) {
            return;
        }
        byte byteDelta = (byte)delta;
        if (force || (long)byteDelta != delta) {
            this.asyncReplaySaver.submit(writer -> {
                writer.startAction(ActionRealTimeClock.INSTANCE);
                writer.friendlyByteBuf().writeByte(0);
                writer.friendlyByteBuf().writeLong(epochMilli);
                writer.finishAction(ActionRealTimeClock.INSTANCE);
            });
        } else {
            if (byteDelta == 0) {
                throw new IllegalStateException();
            }
            this.asyncReplaySaver.submit(writer -> {
                writer.startAction(ActionRealTimeClock.INSTANCE);
                writer.friendlyByteBuf().writeByte((int)byteDelta);
                writer.finishAction(ActionRealTimeClock.INSTANCE);
            });
        }
    }

    public void endTickWithContext(boolean close) {
        this.runWithClientPacketContext(() -> this.endTick(close));
    }

    private void endTick(boolean close) {
        if (this.closeForWriting) {
            return;
        }
        if (close) {
            this.closeForWriting = true;
        }
        if (this.isPaused) {
            this.wasPaused = true;
        }
        if (this.needsInitialSnapshot) {
            this.needsInitialSnapshot = false;
            this.writeSnapshot(true);
        }
        this.finishedConfiguration |= this.flushPackets();
        Minecraft minecraft = Minecraft.getInstance();
        boolean isLevelLoaded = !(Minecraft.getInstance().screen instanceof LevelLoadingScreen);
        boolean changedDimensions = false;
        int localPlayerUpdatesPerSecond = Flashback.getConfig().recording.localPlayerUpdatesPerSecond;
        boolean trackAccurateFirstPersonPosition = localPlayerUpdatesPerSecond > 20;
        boolean wroteNewTick = false;
        if (!(minecraft.level == null || minecraft.getOverlay() != null && minecraft.getOverlay().isPauseScreen() || minecraft.isPaused() || this.isPaused || !isLevelLoaded)) {
            this.writeEntityPositions();
            this.writeLocalData();
            if (trackAccurateFirstPersonPosition) {
                this.writeAccurateFirstPersonPosition(localPlayerUpdatesPerSecond);
            }
            wroteNewTick = true;
            this.asyncReplaySaver.submit(writer -> writer.startAndFinishAction(ActionNextTick.INSTANCE));
            ++this.writtenTicksInChunk;
            ++this.writtenTicks;
            if (this.finishedScreenshot != null) {
                this.asyncReplaySaver.writeIcon(this.finishedScreenshot);
                this.finishedScreenshot = null;
            }
            if (!this.hasTakenScreenshot && (this.writtenTicks >= 20 && minecraft.screen == null || close)) {
                Screenshot.takeScreenshot((RenderTarget)minecraft.getMainRenderTarget(), image -> {
                    this.finishedScreenshot = image;
                });
                this.hasTakenScreenshot = true;
            }
            ResourceKey dimension = minecraft.level.dimension();
            if (this.lastDimensionType == null) {
                this.lastDimensionType = dimension;
            } else if (this.lastDimensionType != dimension) {
                this.lastDimensionType = dimension;
                changedDimensions = true;
            }
        } else if (trackAccurateFirstPersonPosition) {
            this.updateLastPlayerPositionAndAngle(Minecraft.getInstance().player);
            this.partialPositions.clear();
        }
        this.finishedPausing |= this.wasPaused && !this.isPaused;
        boolean writeChunk = close;
        if (minecraft.level != null) {
            boolean finished = this.finishedConfiguration || this.finishedPausing;
            writeChunk |= this.writtenTicksInChunk >= 6000 || finished || changedDimensions;
        }
        if (writeChunk) {
            if (this.writtenTicksInChunk == 0 || !wroteNewTick) {
                this.asyncReplaySaver.submit(writer -> writer.startAndFinishAction(ActionNextTick.INSTANCE));
                ++this.writtenTicksInChunk;
                ++this.writtenTicks;
            }
            int chunkId = this.metadata.chunks.size();
            String chunkName = "c" + chunkId + ".flashback";
            if (changedDimensions && Flashback.getConfig().recording.markDimensionChanges) {
                this.addMarker(new ReplayMarker(0xAA00AA, null, "Changed Dimension"));
            }
            FlashbackChunkMeta chunkMeta = new FlashbackChunkMeta();
            chunkMeta.duration = this.writtenTicksInChunk;
            this.metadata.chunks.put(chunkName, chunkMeta);
            this.metadata.totalTicks = this.writtenTicks;
            String metadata = GSON.toJson((JsonElement)this.metadata.toJson());
            this.asyncReplaySaver.writeReplayChunk(chunkName, metadata);
            this.writtenTicksInChunk = 0;
            if (!close) {
                if (this.finishedPausing) {
                    this.asyncReplaySaver.submit(ReplayWriter::startSnapshot);
                    this.asyncReplaySaver.submit(ReplayWriter::endSnapshot);
                    this.writeSnapshot(false);
                } else {
                    this.writeSnapshot(true);
                }
            }
            this.finishedPausing = false;
            this.finishedConfiguration = false;
            if (minecraft.level != null) {
                this.lastDimensionType = minecraft.level.dimension();
            }
        }
        if (!this.isPaused) {
            this.wasPaused = false;
        }
    }

    private void writeAccurateFirstPersonPosition(int localPlayerUpdatesPerSecond) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            this.lastPlayerPositionAndAngle = null;
            this.partialPositions.clear();
            return;
        }
        if (this.lastPlayerPositionAndAngle != null) {
            int divisions = localPlayerUpdatesPerSecond / 20;
            Minecraft.getInstance().mouseHandler.handleAccumulatedMovement();
            float nextPartialTick = Minecraft.getInstance().deltaTracker.getGameTimeDeltaPartialTick(true);
            double nextX = Mth.lerp((double)nextPartialTick, (double)player.xo, (double)player.getX());
            double nextY = Mth.lerp((double)nextPartialTick, (double)player.yo, (double)player.getY());
            double nextZ = Mth.lerp((double)nextPartialTick, (double)player.zo, (double)player.getZ());
            float nextYaw = player.getViewYRot(nextPartialTick);
            float nextPitch = player.getViewXRot(nextPartialTick);
            PositionAndAngle nextPosition = new PositionAndAngle(nextX, nextY, nextZ, nextYaw, nextPitch);
            if (!this.lastPlayerPositionAndAngle.equals(nextPosition)) {
                this.trackAccuratePositionCounter = 10;
            } else if (this.trackAccuratePositionCounter > 0) {
                --this.trackAccuratePositionCounter;
            }
            if (this.trackAccuratePositionCounter > 0) {
                ArrayList<PositionAndAngle> interpolatedPositions = new ArrayList<PositionAndAngle>();
                for (int i = 0; i <= divisions; ++i) {
                    float amount = (float)i / (float)divisions;
                    float floorPartial = -1.0f + this.lastPlayerPositionAndAnglePartialTick;
                    PositionAndAngle floorPosition = this.lastPlayerPositionAndAngle;
                    float ceilPartial = 1.0f + nextPartialTick;
                    PositionAndAngle ceilPosition = nextPosition;
                    Map.Entry<Float, PositionAndAngle> floorEntry = this.partialPositions.floorEntry(Float.valueOf(amount));
                    Map.Entry<Float, PositionAndAngle> ceilEntry = this.partialPositions.ceilingEntry(Float.valueOf(Math.nextUp(amount)));
                    if (floorEntry != null) {
                        floorPartial = floorEntry.getKey().floatValue();
                        floorPosition = floorEntry.getValue();
                    }
                    if (ceilEntry != null) {
                        ceilPartial = ceilEntry.getKey().floatValue();
                        ceilPosition = ceilEntry.getValue();
                    }
                    double lerpAmount = 0.5;
                    if (!Objects.equals(Float.valueOf(floorPartial), Float.valueOf(ceilPartial))) {
                        lerpAmount = (amount - floorPartial) / (ceilPartial - floorPartial);
                    }
                    PositionAndAngle interpolatedPosition = floorPosition.lerp(ceilPosition, lerpAmount);
                    interpolatedPositions.add(interpolatedPosition);
                }
                FlashbackAccurateEntityPosition accurateEntityPosition = new FlashbackAccurateEntityPosition(player.getId(), interpolatedPositions);
                this.asyncReplaySaver.submit(writer -> {
                    writer.startAction(ActionAccuratePlayerPosition.INSTANCE);
                    FlashbackAccurateEntityPosition.STREAM_CODEC.encode((Object)writer.friendlyByteBuf(), (Object)accurateEntityPosition);
                    writer.finishAction(ActionAccuratePlayerPosition.INSTANCE);
                });
            }
        }
        this.updateLastPlayerPositionAndAngle(player);
        this.partialPositions.clear();
    }

    private void updateLastPlayerPositionAndAngle(@Nullable LocalPlayer player) {
        Map.Entry<Float, PositionAndAngle> floorEntry = this.partialPositions.floorEntry(Float.valueOf(1.0f));
        if (floorEntry != null) {
            this.lastPlayerPositionAndAngle = floorEntry.getValue();
            this.lastPlayerPositionAndAnglePartialTick = floorEntry.getKey().floatValue();
        } else if (player != null) {
            double x = player.xo;
            double y = player.yo;
            double z = player.zo;
            float yaw = player.getViewYRot(0.0f);
            float pitch = player.getViewXRot(0.0f);
            this.lastPlayerPositionAndAngle = new PositionAndAngle(x, y, z, yaw, pitch);
            this.lastPlayerPositionAndAnglePartialTick = 1.0f;
        } else {
            this.lastPlayerPositionAndAngle = null;
        }
    }

    public boolean isPaused() {
        return this.isPaused;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public Path finish() {
        return this.asyncReplaySaver.finish();
    }

    private void writeLocalData() {
        MultiPlayerGameMode multiPlayerGameMode;
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (this.lastLocalPlayer == null || this.lastLocalPlayer.get() != player) {
            this.resetLastLocalData();
            this.lastLocalPlayer = new WeakReference<LocalPlayer>(player);
        }
        ArrayList<Packet<? super ClientGamePacketListener>> gamePackets = new ArrayList<Packet<? super ClientGamePacketListener>>();
        if (Flashback.getConfig().recording.recordHotbar) {
            int selectedSlot;
            FoodData foodData;
            if (player.experienceProgress != this.lastExperienceProgress || player.totalExperience != this.lastTotalExperience || player.experienceLevel != this.lastExperienceLevel) {
                this.lastExperienceProgress = player.experienceProgress;
                this.lastTotalExperience = player.totalExperience;
                this.lastExperienceLevel = player.experienceLevel;
                gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetExperiencePacket(player.experienceProgress, player.totalExperience, player.experienceLevel));
            }
            if ((foodData = player.getFoodData()).getFoodLevel() != this.lastFoodLevel || foodData.getSaturationLevel() != this.lastSaturationLevel) {
                this.lastFoodLevel = foodData.getFoodLevel();
                this.lastSaturationLevel = foodData.getSaturationLevel();
                gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetHealthPacket(player.getHealth(), foodData.getFoodLevel(), foodData.getSaturationLevel()));
            }
            if ((selectedSlot = player.getInventory().getSelectedSlot()) != this.lastSelectedSlot) {
                gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetHeldSlotPacket(selectedSlot));
                this.lastSelectedSlot = selectedSlot;
            }
        }
        SynchedEntityData.DataItem[] items = Minecraft.getInstance().player.getEntityData().itemsById;
        ArrayList<SynchedEntityData.DataValue> changedData = new ArrayList<SynchedEntityData.DataValue>();
        for (int i = 0; i < items.length; ++i) {
            EquipmentSlot[] dataItem = items[i];
            Object value = dataItem.value().value();
            if (i >= this.lastPlayerEntityMeta.size()) {
                this.lastPlayerEntityMeta.add(i, value);
                continue;
            }
            Object old = this.lastPlayerEntityMeta.get(i);
            if (Objects.equals(old, value)) continue;
            this.lastPlayerEntityMeta.set(i, value);
            changedData.add(dataItem.value());
        }
        if (!changedData.isEmpty()) {
            gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetEntityDataPacket(player.getId(), changedData));
        }
        ArrayList<Pair> changedSlots = new ArrayList<Pair>();
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemStack = player.getItemBySlot(equipmentSlot);
            if (this.lastPlayerEquipment.containsKey(equipmentSlot) && ItemStack.matches((ItemStack)this.lastPlayerEquipment.get(equipmentSlot), (ItemStack)itemStack)) continue;
            ItemStack copied = itemStack.copy();
            this.lastPlayerEquipment.put(equipmentSlot, copied);
            changedSlots.add(Pair.of((Object)equipmentSlot, (Object)copied));
        }
        if (!changedSlots.isEmpty()) {
            gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetEquipmentPacket(player.getId(), changedSlots));
        }
        if (Flashback.getConfig().recording.recordHotbar) {
            for (int i = 0; i < this.lastHotbarItems.length; ++i) {
                ItemStack copied;
                ItemStack hotbarItem = player.getInventory().getItem(i);
                if (this.lastHotbarItems[i] != null && ItemStack.matches((ItemStack)this.lastHotbarItems[i], (ItemStack)hotbarItem)) continue;
                this.lastHotbarItems[i] = copied = hotbarItem.copy();
                gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundContainerSetSlotPacket(0, 0, i, copied));
            }
        }
        if ((multiPlayerGameMode = Minecraft.getInstance().gameMode) == null) {
            this.lastDestroyPos = null;
            this.lastDestroyProgress = -1;
        } else {
            boolean changed;
            BlockPos destroyPos = multiPlayerGameMode.destroyBlockPos.immutable();
            int destroyProgress = multiPlayerGameMode.getDestroyStage();
            boolean bl = changed = destroyProgress != this.lastDestroyProgress;
            if (destroyProgress >= 0) {
                changed |= !destroyPos.equals((Object)this.lastDestroyPos);
            }
            if (changed) {
                gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundBlockDestructionPacket(player.getId(), destroyPos, destroyProgress));
            }
            this.lastDestroyPos = destroyPos;
            this.lastDestroyProgress = destroyProgress;
        }
        if (player.swinging && (!this.wasSwinging || this.lastSwingTime > player.swingTime)) {
            int animation = player.swingingArm == InteractionHand.MAIN_HAND ? 0 : 3;
            gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundAnimatePacket((Entity)player, animation));
        }
        this.lastSwingTime = player.swingTime;
        this.wasSwinging = player.swinging;
        gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetEntityMotionPacket(player.getId(), player.getDeltaMovement()));
        this.asyncReplaySaver.writeGamePackets(this.gamePacketCodec, gamePackets);
    }

    private void resetLastLocalData() {
        this.lastPlayerEntityMeta.clear();
        this.lastPlayerEquipment.clear();
        this.lastDestroyPos = null;
        this.lastDestroyProgress = -1;
        this.lastSelectedSlot = -1;
        this.lastExperienceProgress = -1.0f;
        this.lastTotalExperience = -1;
        this.lastExperienceLevel = -1;
        this.lastFoodLevel = -1;
        this.lastSaturationLevel = -1.0f;
        Arrays.fill(this.lastHotbarItems, null);
    }

    private void writeEntityPositions() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            this.lastPositions.clear();
            return;
        }
        record IdWithPosition(int id, Position position) {
        }
        ArrayList<IdWithPosition> changedPositions = new ArrayList<IdWithPosition>();
        for (Entity entity : level.entitiesForRendering()) {
            Position lastPosition;
            Position position;
            InterpolationHandler interpolation;
            if (PacketHelper.shouldIgnoreEntity(entity)) continue;
            float headRot = entity.getYHeadRot();
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                float f = headRot = livingEntity.lerpHeadSteps > 0 ? (float)livingEntity.lerpYHeadRot : livingEntity.getYHeadRot();
            }
            if ((interpolation = entity.getInterpolation()) != null && interpolation.hasActiveInterpolation()) {
                xyz = interpolation.position();
                position = new Position(xyz.x, xyz.y, xyz.z, interpolation.yRot(), interpolation.xRot(), headRot, entity.onGround());
            } else {
                xyz = entity.trackingPosition();
                position = new Position(xyz.x, xyz.y, xyz.z, entity.getYRot(), entity.getXRot(), headRot, entity.onGround());
            }
            if (Objects.equals(position, lastPosition = this.lastPositions.get(entity))) continue;
            this.lastPositions.put(entity, position);
            changedPositions.add(new IdWithPosition(entity.getId(), position));
        }
        if (changedPositions.isEmpty()) {
            return;
        }
        this.asyncReplaySaver.submit(writer -> {
            writer.startAction(ActionMoveEntities.INSTANCE);
            RegistryFriendlyByteBuf friendlyByteBuf = writer.friendlyByteBuf();
            friendlyByteBuf.writeVarInt(1);
            friendlyByteBuf.writeResourceKey(level.dimension());
            friendlyByteBuf.writeVarInt(changedPositions.size());
            for (IdWithPosition changedPosition : changedPositions) {
                friendlyByteBuf.writeVarInt(changedPosition.id);
                friendlyByteBuf.writeDouble(changedPosition.position.x);
                friendlyByteBuf.writeDouble(changedPosition.position.y);
                friendlyByteBuf.writeDouble(changedPosition.position.z);
                friendlyByteBuf.writeFloat(changedPosition.position.yaw);
                friendlyByteBuf.writeFloat(changedPosition.position.pitch);
                friendlyByteBuf.writeFloat(changedPosition.position.headYRot);
                friendlyByteBuf.writeBoolean(changedPosition.position.onGround);
            }
            writer.finishAction(ActionMoveEntities.INSTANCE);
        });
    }

    public boolean flushPackets() {
        PacketWithPhase packet;
        if (this.pendingPackets.isEmpty()) {
            return false;
        }
        boolean endedConfiguration = false;
        ArrayList<Packet<? super ClientGamePacketListener>> gamePackets = new ArrayList<Packet<? super ClientGamePacketListener>>();
        ArrayList<Packet<? super ClientConfigurationPacketListener>> configurationPackets = new ArrayList<Packet<? super ClientConfigurationPacketListener>>();
        while ((packet = this.pendingPackets.poll()) != null) {
            if (packet.phase == ConnectionProtocol.PLAY) {
                if (!configurationPackets.isEmpty()) {
                    this.asyncReplaySaver.writeConfigurationPackets(this.configurationPacketCodec, configurationPackets);
                    configurationPackets.clear();
                }
                gamePackets.add(packet.packet);
                if (packet.packet instanceof ClientboundLoginPacket) {
                    this.asyncReplaySaver.writeGamePackets(this.gamePacketCodec, gamePackets);
                    gamePackets.clear();
                    this.writeCreateLocalPlayer();
                }
                if (!this.isConfiguring) continue;
                endedConfiguration = true;
                this.isConfiguring = false;
                continue;
            }
            if (packet.phase == ConnectionProtocol.CONFIGURATION) {
                if (!gamePackets.isEmpty()) {
                    this.asyncReplaySaver.writeGamePackets(this.gamePacketCodec, gamePackets);
                    gamePackets.clear();
                }
                configurationPackets.add(packet.packet);
                this.isConfiguring = true;
                continue;
            }
            throw new IllegalArgumentException("Unsupported phase: " + String.valueOf(packet.phase));
        }
        if (!configurationPackets.isEmpty()) {
            this.asyncReplaySaver.writeConfigurationPackets(this.configurationPacketCodec, configurationPackets);
        }
        if (!gamePackets.isEmpty()) {
            this.asyncReplaySaver.writeGamePackets(this.gamePacketCodec, gamePackets);
            if (this.isConfiguring) {
                endedConfiguration = true;
                this.isConfiguring = false;
            }
        }
        return endedConfiguration;
    }

    private void writeCreateLocalPlayer() {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null) {
            UUID uuid = localPlayer.getUUID();
            double x = localPlayer.getX();
            double y = localPlayer.getY();
            double z = localPlayer.getZ();
            float xRot = localPlayer.getXRot();
            float yRot = localPlayer.getYRot();
            float yHeadRot = localPlayer.getYHeadRot();
            Vec3 deltaMovement = localPlayer.getDeltaMovement();
            GameProfile currentProfile = localPlayer.getGameProfile();
            ImmutableMultimap.Builder propertyMapBuilder = ImmutableMultimap.builder();
            propertyMapBuilder.putAll((Multimap)Minecraft.getInstance().getGameProfile().properties());
            propertyMapBuilder.putAll((Multimap)currentProfile.properties());
            GameProfile newProfile = new GameProfile(currentProfile.id(), currentProfile.name(), new PropertyMap((Multimap)propertyMapBuilder.build()));
            int gameModeId = Minecraft.getInstance().gameMode.getPlayerMode().getId();
            this.asyncReplaySaver.submit(writer -> {
                writer.startAction(ActionCreateLocalPlayer.INSTANCE);
                RegistryFriendlyByteBuf registryFriendlyByteBuf = writer.friendlyByteBuf();
                registryFriendlyByteBuf.writeUUID(uuid);
                registryFriendlyByteBuf.writeDouble(x);
                registryFriendlyByteBuf.writeDouble(y);
                registryFriendlyByteBuf.writeDouble(z);
                registryFriendlyByteBuf.writeFloat(xRot);
                registryFriendlyByteBuf.writeFloat(yRot);
                registryFriendlyByteBuf.writeFloat(yHeadRot);
                registryFriendlyByteBuf.writeDouble(deltaMovement.x());
                registryFriendlyByteBuf.writeDouble(deltaMovement.y());
                registryFriendlyByteBuf.writeDouble(deltaMovement.z());
                ByteBufCodecs.GAME_PROFILE.encode((Object)registryFriendlyByteBuf, (Object)newProfile);
                registryFriendlyByteBuf.writeVarInt(gameModeId);
                writer.finishAction(ActionCreateLocalPlayer.INSTANCE);
            });
        }
    }

    private void runWithClientPacketContext(Runnable runnable) {
        LocalPlayer localPlayer;
        PacketContext context = PacketContext.get();
        if (context == null && (localPlayer = Minecraft.getInstance().player) instanceof PacketContextProvider) {
            LocalPlayer provider = localPlayer;
            PacketContext.runWithContext((PacketContextProvider)provider, (Runnable)runnable);
        } else {
            runnable.run();
        }
    }

    public void writeLevelEvent(int type, BlockPos blockPos, int data, boolean globalEvent) {
        if (!this.readyToWrite()) {
            return;
        }
        this.runWithClientPacketContext(() -> this.pendingPackets.add(new PacketWithPhase((Packet<?>)new ClientboundLevelEventPacket(type, blockPos, data, globalEvent), ConnectionProtocol.PLAY)));
    }

    public void writeSound(Holder<SoundEvent> holder, SoundSource soundSource, double x, double y, double z, float volume, float pitch, long seed) {
        if (!this.readyToWrite()) {
            return;
        }
        this.runWithClientPacketContext(() -> this.pendingPackets.add(new PacketWithPhase((Packet<?>)new ClientboundSoundPacket(holder, soundSource, x, y, z, volume, pitch, seed), ConnectionProtocol.PLAY)));
    }

    public void writeEntitySound(Holder<SoundEvent> holder, SoundSource soundSource, Entity entity, float volume, float pitch, long seed) {
        if (!this.readyToWrite()) {
            return;
        }
        this.runWithClientPacketContext(() -> this.pendingPackets.add(new PacketWithPhase((Packet<?>)new ClientboundSoundEntityPacket(holder, soundSource, entity, volume, pitch, seed), ConnectionProtocol.PLAY)));
    }

    public void writePacketAsync(Packet<?> packet, ConnectionProtocol phase) {
        ClientboundCustomPayloadPacket customPayloadPacket;
        if (!this.readyToWrite()) {
            this.skippedPacketDueToWaitingForWrite = true;
            return;
        }
        if (packet instanceof ClientboundBundlePacket) {
            ClientboundBundlePacket bundlePacket = (ClientboundBundlePacket)packet;
            for (Packet subPacket : bundlePacket.subPackets()) {
                this.writePacketAsync(subPacket, phase);
            }
            return;
        }
        if (packet instanceof ClientboundPlayerChatPacket) {
            ClientboundPlayerChatPacket playerChatPacket = (ClientboundPlayerChatPacket)packet;
            try {
                Component content = playerChatPacket.unsignedContent() != null ? playerChatPacket.unsignedContent() : Component.literal((String)playerChatPacket.body().content());
                Component decorated = playerChatPacket.chatType().decorate(content);
                packet = new ClientboundSystemChatPacket(decorated, false);
            }
            catch (Exception e) {
                return;
            }
        }
        if (packet instanceof ClientboundCustomPayloadPacket && (customPayloadPacket = (ClientboundCustomPayloadPacket)packet).type().id().getNamespace().startsWith("fabric-screen-handler-api")) {
            return;
        }
        if (IgnoredPacketSet.isIgnored(packet)) {
            return;
        }
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null) {
            try {
                ClientboundSetEquipmentPacket entityEquipmentPacket;
                ClientboundSetEntityDataPacket entityDataPacket;
                int localPlayerId = localPlayer.getId();
                if (packet instanceof ClientboundSetEntityDataPacket && (entityDataPacket = (ClientboundSetEntityDataPacket)packet).id() == localPlayerId) {
                    return;
                }
                if (packet instanceof ClientboundSetEquipmentPacket && (entityEquipmentPacket = (ClientboundSetEquipmentPacket)packet).getEntity() == localPlayerId) {
                    return;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.pendingPackets.add(new PacketWithPhase((Packet<?>)packet, phase));
    }

    /*
     * WARNING - void declaration
     */
    public void writeSnapshot(boolean asActualSnapshot) {
        void var26_40;
        boolean isValidProperties;
        Minecraft minecraft = Minecraft.getInstance();
        if (this.skippedPacketDueToWaitingForWrite) {
            this.skippedPacketDueToWaitingForWrite = false;
            minecraft.runAllTasks();
        }
        if (asActualSnapshot) {
            this.asyncReplaySaver.submit(ReplayWriter::startSnapshot);
        }
        if (this.lastRtcEpochMilli == 0L) {
            this.writeRtc(true);
        } else {
            this.forceRtcSync = true;
        }
        ClientLevel level = minecraft.level;
        LocalPlayer localPlayer = minecraft.player;
        ClientPacketListener connection = minecraft.getConnection();
        MultiPlayerGameMode gameMode = minecraft.gameMode;
        ClientChunkCache clientChunkCache = level.getChunkSource();
        AtomicReferenceArray chunksList = clientChunkCache.storage.chunks;
        ArrayList<Packet<? super ClientConfigurationPacketListener>> configurationPackets = new ArrayList<Packet<? super ClientConfigurationPacketListener>>();
        configurationPackets.add((Packet<? super ClientConfigurationPacketListener>)new ClientboundUpdateEnabledFeaturesPacket(FeatureFlags.REGISTRY.toNames(level.enabledFeatures())));
        RegistryOps dynamicOps = localPlayer.registryAccess().createSerializationContext((DynamicOps)NbtOps.INSTANCE);
        RegistrySynchronization.packRegistries((DynamicOps)dynamicOps, (RegistryAccess)localPlayer.registryAccess(), Set.of(), (resourceKey, list) -> configurationPackets.add((Packet<? super ClientConfigurationPacketListener>)new ClientboundRegistryDataPacket(resourceKey, list)));
        HashMap serializedTags = new HashMap();
        localPlayer.registryAccess().registries().forEach(entry -> {
            try {
                TagNetworkSerialization.NetworkPayload tags = TagNetworkSerialization.serializeToNetwork((Registry)entry.value());
                serializedTags.put(entry.key(), tags);
            }
            catch (Exception exception) {
                // empty catch block
            }
        });
        configurationPackets.add((Packet<? super ClientConfigurationPacketListener>)new ClientboundUpdateTagsPacket(serializedTags));
        configurationPackets.add((Packet<? super ClientConfigurationPacketListener>)new ClientboundResourcePackPopPacket(Optional.empty()));
        for (ServerPackManager.ServerPackData pack : minecraft.getDownloadedPackSource().manager.packs) {
            configurationPackets.add((Packet<? super ClientConfigurationPacketListener>)new ClientboundResourcePackPushPacket(pack.id, pack.url.toString(), pack.hash == null ? "" : pack.hash.toString(), true, Optional.empty()));
        }
        this.asyncReplaySaver.writeConfigurationPackets(this.configurationPacketCodec, configurationPackets);
        ArrayList<Packet<? super ClientGamePacketListener>> gamePackets = new ArrayList<Packet<? super ClientGamePacketListener>>();
        long hashedSeed = level.getBiomeManager().biomeZoomSeed;
        CommonPlayerSpawnInfo commonPlayerSpawnInfo = new CommonPlayerSpawnInfo(level.dimensionTypeRegistration(), level.dimension(), hashedSeed, gameMode.getPlayerMode(), gameMode.getPreviousPlayerMode(), level.isDebug(), level.getLevelData().isFlat, Optional.empty(), 0, level.getSeaLevel());
        ClientboundLoginPacket loginPacket = new ClientboundLoginPacket(localPlayer.getId(), level.getLevelData().isHardcore(), connection.levels(), 1, minecraft.options.getEffectiveRenderDistance(), level.getServerSimulationDistance(), localPlayer.isReducedDebugInfo(), localPlayer.shouldShowDeathScreen(), localPlayer.getDoLimitedCrafting(), commonPlayerSpawnInfo, false);
        gamePackets.add((Packet<? super ClientGamePacketListener>)loginPacket);
        this.asyncReplaySaver.writeGamePackets(this.gamePacketCodec, gamePackets);
        gamePackets.clear();
        this.writeCreateLocalPlayer();
        ClientboundPlayerInfoUpdatePacket infoUpdatePacket = new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME), List.of());
        infoUpdatePacket.entries = new ArrayList();
        HashSet<UUID> addedEntries = new HashSet<UUID>();
        HashSet<UUID> addedWithValidProperties = new HashSet<UUID>();
        for (PlayerInfo info : connection.getListedOnlinePlayers()) {
            boolean bl = isValidProperties = !info.getProfile().properties().isEmpty();
            if (!addedEntries.add(info.getProfile().id())) continue;
            infoUpdatePacket.entries.add(new ClientboundPlayerInfoUpdatePacket.Entry(info.getProfile().id(), info.getProfile(), true, info.getLatency(), info.getGameMode(), info.getTabListDisplayName(), info.showHat(), info.getTabListOrder(), null));
            if (!isValidProperties) continue;
            addedWithValidProperties.add(info.getProfile().id());
        }
        for (PlayerInfo info : connection.getOnlinePlayers()) {
            isValidProperties = !info.getProfile().properties().isEmpty();
            boolean add = false;
            if (addedEntries.add(info.getProfile().id())) {
                add = true;
            } else if (isValidProperties && !addedWithValidProperties.contains(info.getProfile().id())) {
                infoUpdatePacket.entries.removeIf(entry -> entry.profileId().equals(info.getProfile().id()));
                add = true;
            }
            if (!add) continue;
            infoUpdatePacket.entries.add(new ClientboundPlayerInfoUpdatePacket.Entry(info.getProfile().id(), info.getProfile(), false, info.getLatency(), info.getGameMode(), info.getTabListDisplayName(), info.showHat(), info.getTabListOrder(), null));
            if (!isValidProperties) continue;
            addedWithValidProperties.add(info.getProfile().id());
        }
        for (AbstractClientPlayer player : level.players()) {
            PlayerInfo info = player.getPlayerInfo();
            if (info != null) {
                boolean isValidProperties2 = !info.getProfile().properties().isEmpty();
                boolean add = false;
                if (addedEntries.add(info.getProfile().id())) {
                    add = true;
                } else if (isValidProperties2 && !addedWithValidProperties.contains(info.getProfile().id())) {
                    infoUpdatePacket.entries.removeIf(entry -> entry.profileId().equals(info.getProfile().id()));
                    add = true;
                }
                if (!add) continue;
                infoUpdatePacket.entries.add(new ClientboundPlayerInfoUpdatePacket.Entry(player.getUUID(), player.getGameProfile(), false, info.getLatency(), info.getGameMode(), info.getTabListDisplayName(), info.showHat(), info.getTabListOrder(), null));
                if (!isValidProperties2) continue;
                addedWithValidProperties.add(info.getProfile().id());
                continue;
            }
            if (!addedEntries.add(player.getUUID())) continue;
            infoUpdatePacket.entries.add(new ClientboundPlayerInfoUpdatePacket.Entry(player.getUUID(), player.getGameProfile(), false, 0, GameType.DEFAULT_MODE, player.getDisplayName(), true, 0, null));
        }
        gamePackets.add((Packet<? super ClientGamePacketListener>)infoUpdatePacket);
        PlayerTabOverlay playerTabOverlay = minecraft.gui.getTabList();
        gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundTabListPacket((Component)(playerTabOverlay.header != null ? playerTabOverlay.header : Component.empty()), (Component)(playerTabOverlay.footer != null ? playerTabOverlay.footer : Component.empty())));
        BossHealthOverlay bossOverlay = minecraft.gui.getBossOverlay();
        for (LerpingBossEvent event : bossOverlay.events.values()) {
            gamePackets.add((Packet<? super ClientGamePacketListener>)ClientboundBossEventPacket.createAddPacket((BossEvent)event));
        }
        Scoreboard scoreboard = localPlayer.connection.scoreboard();
        for (PlayerTeam playerTeam : scoreboard.getPlayerTeams()) {
            gamePackets.add((Packet<? super ClientGamePacketListener>)ClientboundSetPlayerTeamPacket.createAddOrModifyPacket((PlayerTeam)playerTeam, (boolean)true));
        }
        HashSet<Objective> handledObjectives = new HashSet<Objective>();
        DisplaySlot[] playerTeam = DisplaySlot.values();
        int n = playerTeam.length;
        boolean bl = false;
        while (var26_40 < n) {
            DisplaySlot displaySlot = playerTeam[var26_40];
            Objective objective = scoreboard.getDisplayObjective(displaySlot);
            if (objective != null && handledObjectives.add(objective)) {
                gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetObjectivePacket(objective, 0));
                for (DisplaySlot displaySlot2 : DisplaySlot.values()) {
                    if (scoreboard.getDisplayObjective(displaySlot2) != objective) continue;
                    gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetDisplayObjectivePacket(displaySlot2, objective));
                }
                for (PlayerScoreEntry playerScoreEntry : scoreboard.listPlayerScores(objective)) {
                    gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetScorePacket(playerScoreEntry.owner(), objective.getName(), playerScoreEntry.value(), Optional.ofNullable(playerScoreEntry.display()), Optional.ofNullable(playerScoreEntry.numberFormatOverride())));
                }
            }
            ++var26_40;
        }
        WorldBorder worldBorder = level.getWorldBorder();
        gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundInitializeBorderPacket(worldBorder));
        gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetTimePacket(level.getGameTime(), ((ClientClockManagerExt)level.clockManager()).flashback$encodeClockUpdates()));
        gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetDefaultSpawnPositionPacket(level.getRespawnData()));
        if (level.isRaining()) {
            gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundGameEventPacket(ClientboundGameEventPacket.START_RAINING, 0.0f));
        } else {
            gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundGameEventPacket(ClientboundGameEventPacket.STOP_RAINING, 0.0f));
        }
        gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, level.getRainLevel(1.0f)));
        gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, level.getThunderLevel(1.0f)));
        try {
            Runnable runnable;
            while ((runnable = (Runnable)level.lightUpdateQueue.poll()) != null) {
                runnable.run();
            }
        }
        catch (Exception e) {
            Flashback.LOGGER.error("Error while running light tasks", (Throwable)e);
        }
        for (int i = 0; i < 256; ++i) {
            try {
                if (level.getLightEngine().runLightUpdates() != 0) continue;
            }
            catch (Exception exception) {
                Flashback.LOGGER.error("Error while running light updates", (Throwable)exception);
            }
            break;
        }
        this.writeChunkDataSnapshot(chunksList, clientChunkCache, level, localPlayer, gamePackets);
        if (Flashback.getConfig().recording.recordHotbar) {
            int n2;
            this.lastExperienceProgress = localPlayer.experienceProgress;
            this.lastTotalExperience = localPlayer.totalExperience;
            this.lastExperienceLevel = localPlayer.experienceLevel;
            gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetExperiencePacket(localPlayer.experienceProgress, localPlayer.totalExperience, localPlayer.experienceLevel));
            FoodData foodData = localPlayer.getFoodData();
            this.lastFoodLevel = foodData.getFoodLevel();
            this.lastSaturationLevel = foodData.getSaturationLevel();
            gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetHealthPacket(localPlayer.getHealth(), foodData.getFoodLevel(), foodData.getSaturationLevel()));
            this.lastSelectedSlot = n2 = localPlayer.getInventory().getSelectedSlot();
            gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetHeldSlotPacket(n2));
            for (int i = 0; i < 9; ++i) {
                ItemStack hotbarItem = localPlayer.getInventory().getItem(i);
                this.lastHotbarItems[i] = hotbarItem.copy();
                gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundContainerSetSlotPacket(0, 0, i, hotbarItem.copy()));
            }
        }
        for (Entity entity : level.entitiesForRendering()) {
            Leashable leashable;
            List nonDefaultEntityData;
            if (PacketHelper.shouldIgnoreEntity(entity)) continue;
            if (!(entity instanceof LocalPlayer)) {
                gamePackets.add(PacketHelper.createAddEntity(entity));
            }
            if ((nonDefaultEntityData = entity.getEntityData().getNonDefaultValues()) != null && !nonDefaultEntityData.isEmpty()) {
                gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetEntityDataPacket(entity.getId(), nonDefaultEntityData));
            }
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                Collection syncableAttributes = livingEntity.getAttributes().getSyncableAttributes();
                if (!syncableAttributes.isEmpty()) {
                    gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundUpdateAttributesPacket(entity.getId(), syncableAttributes));
                }
                ArrayList<Pair> changedSlots = new ArrayList<Pair>();
                for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    ItemStack itemStack = livingEntity.getItemBySlot(equipmentSlot);
                    if (itemStack.isEmpty()) continue;
                    changedSlots.add(Pair.of((Object)equipmentSlot, (Object)itemStack.copy()));
                }
                if (!changedSlots.isEmpty()) {
                    gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetEquipmentPacket(entity.getId(), changedSlots));
                }
            }
            if (entity.isVehicle()) {
                gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetPassengersPacket(entity));
            }
            if (entity.isPassenger()) {
                gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetPassengersPacket(entity.getVehicle()));
            }
            if (!(entity instanceof Leashable) || !(leashable = (Leashable)entity).isLeashed()) continue;
            gamePackets.add((Packet<? super ClientGamePacketListener>)new ClientboundSetEntityLinkPacket(entity, leashable.getLeashHolder()));
        }
        for (Map.Entry entry2 : level.mapData.entrySet()) {
            MapItemSavedData data = (MapItemSavedData)entry2.getValue();
            int offsetX = 0;
            int offsetY = 0;
            int sizeX = 128;
            int sizeY = 128;
            if (data.colors.length != sizeX * sizeY) {
                Flashback.LOGGER.error("Unable to save snapshot of map data, expected colour array to be size {}, got {} instead", (Object)(sizeX * sizeY), (Object)data.colors.length);
                continue;
            }
            byte[] colorsCopy = new byte[sizeX * sizeY];
            System.arraycopy(data.colors, 0, colorsCopy, 0, sizeX * sizeY);
            MapItemSavedData.MapPatch patch = new MapItemSavedData.MapPatch(offsetX, offsetY, sizeX, sizeY, colorsCopy);
            ArrayList<MapDecoration> decorations = new ArrayList<MapDecoration>();
            for (MapDecoration decoration : data.getDecorations()) {
                decorations.add(decoration);
            }
            ClientboundMapItemDataPacket packet = new ClientboundMapItemDataPacket((MapId)entry2.getKey(), data.scale, data.locked, decorations, patch);
            gamePackets.add((Packet<? super ClientGamePacketListener>)packet);
        }
        this.writeCustomSnapshot(gamePackets::add);
        this.asyncReplaySaver.writeGamePackets(this.gamePacketCodec, gamePackets);
        if (asActualSnapshot) {
            this.asyncReplaySaver.submit(ReplayWriter::endSnapshot);
        }
    }

    private void writeChunkDataSnapshot(AtomicReferenceArray<LevelChunk> chunksList, ClientChunkCache clientChunkCache, ClientLevel level, LocalPlayer localPlayer, List<Packet<? super ClientGamePacketListener>> gamePackets) {
        ArrayList<LevelChunk> chunks = new ArrayList<LevelChunk>(chunksList.length());
        LongOpenHashSet seenChunkPositions = new LongOpenHashSet(chunksList.length());
        for (int i = 0; i < chunksList.length(); ++i) {
            LevelChunk chunk = chunksList.get(i);
            if (chunk == null) continue;
            chunks.add(chunk);
            seenChunkPositions.add(chunk.getPos().pack());
        }
        if (Flashback.isBobbyLoaded && Flashback.getConfig().recording.recordBobbyIntoReplay) {
            BobbyUtil.addBobbyChunks(clientChunkCache, chunks, seenChunkPositions);
        }
        if (Runtime.getRuntime().availableProcessors() <= 1) {
            ArrayList<ClientboundLevelChunkWithLightPacket> levelChunkPackets = new ArrayList<ClientboundLevelChunkWithLightPacket>();
            for (LevelChunk chunk : chunks) {
                levelChunkPackets.add(new ClientboundLevelChunkWithLightPacket(chunk, level.getLightEngine(), null, null));
            }
            int centerX = localPlayer.getBlockX() >> 4;
            int centerZ = localPlayer.getBlockZ() >> 4;
            levelChunkPackets.sort(Comparator.comparingInt(task -> {
                int dx = task.getX() - centerX;
                int dz = task.getZ() - centerZ;
                return dx * dx + dz * dz;
            }));
            gamePackets.addAll(levelChunkPackets);
        } else {
            try (ForkJoinPool pool = new ForkJoinPool();){
                final class PositionedTask {
                    private final ChunkPos pos;
                    private final ForkJoinTask<ClientboundLevelChunkWithLightPacket> task;
                    private ClientboundLightUpdatePacketData lightData;

                    PositionedTask(Recorder this$0, ChunkPos pos, ForkJoinTask<ClientboundLevelChunkWithLightPacket> task) {
                        Objects.requireNonNull(this$0);
                        this.lightData = null;
                        this.pos = pos;
                        this.task = task;
                    }
                }
                ArrayList<PositionedTask> levelChunkPacketTasks = new ArrayList<PositionedTask>();
                for (LevelChunk chunk : chunks) {
                    Future task2 = pool.submit(() -> new ClientboundLevelChunkWithLightPacket(chunk, level.getLightEngine(), new BitSet(), new BitSet()));
                    levelChunkPacketTasks.add(new PositionedTask(this, chunk.getPos(), (ForkJoinTask<ClientboundLevelChunkWithLightPacket>)task2));
                }
                int centerX = localPlayer.getBlockX() >> 4;
                int centerZ = localPlayer.getBlockZ() >> 4;
                levelChunkPacketTasks.sort(Comparator.comparingInt(task -> {
                    int dx = task.pos.x() - centerX;
                    int dz = task.pos.z() - centerZ;
                    return dx * dx + dz * dz;
                }));
                for (PositionedTask positionedTask : levelChunkPacketTasks) {
                    positionedTask.lightData = new ClientboundLightUpdatePacketData(positionedTask.pos, level.getLightEngine(), null, null);
                }
                for (PositionedTask positionedTask : levelChunkPacketTasks) {
                    ClientboundLevelChunkWithLightPacket levelChunkWithLightPacket = positionedTask.task.join();
                    levelChunkWithLightPacket.lightData = positionedTask.lightData;
                    gamePackets.add((Packet<? super ClientGamePacketListener>)levelChunkWithLightPacket);
                }
            }
        }
    }

    public void writeCustomSnapshot(Consumer<Packet<? super ClientGamePacketListener>> consumer) {
    }

    private record Position(double x, double y, double z, float yaw, float pitch, float headYRot, boolean onGround) {
        public Position {
            yaw = Mth.wrapDegrees((float)yaw);
            pitch = Mth.wrapDegrees((float)pitch);
            headYRot = Mth.wrapDegrees((float)headYRot);
        }
    }

    private record PacketWithPhase(Packet<?> packet, ConnectionProtocol phase) {
    }
}

