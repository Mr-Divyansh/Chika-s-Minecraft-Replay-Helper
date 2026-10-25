/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonObject
 *  com.mojang.authlib.GameProfile
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufUtil
 *  io.netty.handler.codec.DecoderException
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.IntIterator
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents$Unload
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.server.IntegratedServer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Registry$PendingTags
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.Connection
 *  net.minecraft.network.PacketListener
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket
 *  net.minecraft.network.protocol.common.ClientboundResourcePackPopPacket
 *  net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener
 *  net.minecraft.network.protocol.configuration.ConfigurationProtocols
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundBossEventPacket
 *  net.minecraft.network.protocol.game.ClientboundBossEventPacket$Handler
 *  net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket
 *  net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket
 *  net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket
 *  net.minecraft.network.protocol.game.ClientboundMoveEntityPacket$Rot
 *  net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket
 *  net.minecraft.network.protocol.game.ClientboundTabListPacket
 *  net.minecraft.network.protocol.game.GameProtocols
 *  net.minecraft.resources.Identifier
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.ServerTickRateManager
 *  net.minecraft.server.Services
 *  net.minecraft.server.WorldStem
 *  net.minecraft.server.level.ChunkMap$TrackedEntity
 *  net.minecraft.server.level.ClientInformation
 *  net.minecraft.server.level.ServerEntity
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.level.TicketType
 *  net.minecraft.server.level.progress.LevelLoadListener
 *  net.minecraft.server.network.CommonListenerCookie
 *  net.minecraft.server.network.ConfigurationTask
 *  net.minecraft.server.notifications.NotificationService
 *  net.minecraft.server.packs.repository.PackRepository
 *  net.minecraft.server.players.NameAndId
 *  net.minecraft.server.players.PlayerList
 *  net.minecraft.stats.ServerStatsCounter
 *  net.minecraft.util.Mth
 *  net.minecraft.util.Util
 *  net.minecraft.world.BossEvent
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.BossEvent$BossBarOverlay
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ExperienceOrb
 *  net.minecraft.world.entity.PositionMoveRotation
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.flag.FeatureFlagSet
 *  net.minecraft.world.food.FoodData
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelSettings
 *  net.minecraft.world.level.WorldDataConfiguration
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.chunk.PalettedContainer
 *  net.minecraft.world.level.gamerules.GameRules
 *  net.minecraft.world.level.storage.LevelData
 *  net.minecraft.world.level.storage.LevelResource
 *  net.minecraft.world.level.storage.LevelStorageSource$LevelStorageAccess
 *  net.minecraft.world.level.storage.PrimaryLevelData
 *  net.minecraft.world.level.storage.ServerLevelData
 *  net.minecraft.world.level.storage.WorldData
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.playback;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.IgnoredCustomPayloads;
import com.moulberry.flashback.PacketHelper;
import com.moulberry.flashback.TempFolderProvider;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.ext.ConnectionExt;
import com.moulberry.flashback.ext.LevelChunkExt;
import com.moulberry.flashback.ext.MinecraftExt;
import com.moulberry.flashback.ext.ServerGamePacketListenerImplExt;
import com.moulberry.flashback.ext.ServerTickRateManagerExt;
import com.moulberry.flashback.io.ReplayReader;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.handler.ReplayServerKeyframeHandler;
import com.moulberry.flashback.keyframe.impl.BlockOverrideKeyframe;
import com.moulberry.flashback.keyframe.types.BlockOverrideKeyframeType;
import com.moulberry.flashback.packet.FinishedServerTick;
import com.moulberry.flashback.packet.FlashbackAccurateEntityPosition;
import com.moulberry.flashback.packet.FlashbackClearEntities;
import com.moulberry.flashback.packet.FlashbackClearParticles;
import com.moulberry.flashback.packet.FlashbackForceClientTick;
import com.moulberry.flashback.packet.FlashbackInstantlyLerp;
import com.moulberry.flashback.packet.FlashbackRawCustomPayload;
import com.moulberry.flashback.packet.FlashbackRemoteExperience;
import com.moulberry.flashback.packet.FlashbackRemoteFoodData;
import com.moulberry.flashback.packet.FlashbackRemoteSelectHotbarSlot;
import com.moulberry.flashback.packet.FlashbackRemoteSetSlot;
import com.moulberry.flashback.packet.FlashbackSetBorderLerpStartTime;
import com.moulberry.flashback.playback.AllowPendingEntityPacketSet;
import com.moulberry.flashback.playback.FlashbackFakePlayer;
import com.moulberry.flashback.playback.PlayableChunk;
import com.moulberry.flashback.playback.ReplayChunkCache;
import com.moulberry.flashback.playback.ReplayConfigurationPacketHandler;
import com.moulberry.flashback.playback.ReplayGamePacketHandler;
import com.moulberry.flashback.playback.ReplayPlayer;
import com.moulberry.flashback.record.FlashbackChunkMeta;
import com.moulberry.flashback.record.FlashbackMeta;
import com.moulberry.flashback.state.EditorScene;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import com.moulberry.flashback.state.KeyframeTrack;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.DecoderException;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ClientboundResourcePackPopPacket;
import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener;
import net.minecraft.network.protocol.configuration.ConfigurationProtocols;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.GameProtocols;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTickRateManager;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.progress.LevelLoadListener;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ConfigurationTask;
import net.minecraft.server.notifications.NotificationService;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.PlayerList;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ReplayServer
extends IntegratedServer {
    private static final Gson GSON = new Gson();
    public static int REPLAY_VIEWER_IDS_START = -981723987;
    public static String REPLAY_VIEWER_NAME = "Replay Viewer";
    public volatile int jumpToTick = -1;
    public volatile boolean replayPaused = true;
    public AtomicBoolean forceApplyKeyframes = new AtomicBoolean(false);
    public AtomicBoolean sendFinishedServerTick = new AtomicBoolean(false);
    private volatile float desiredTickRate = 20.0f;
    private volatile float desiredTickRateManual = 20.0f;
    private boolean desiredFrozen = false;
    private int desiredFrozenDelay = -1;
    private boolean isFrozen = false;
    private int frozenDelay = -1;
    public volatile boolean failedToLoadRegistryDataWarning = false;
    public volatile boolean failedToSpawnPlayerWarning = false;
    private volatile long lastTimeRtc = 0L;
    private volatile long timeRtc = 0L;
    private long pendingTimeRtc = 0L;
    private boolean hasNonSpectatorReplayViewer = false;
    private int currentTick = 0;
    private volatile int targetTick = 0;
    private final int totalTicks;
    public ResourceKey<Level> spawnLevel = null;
    private final ReplayGamePacketHandler gamePacketHandler;
    private final ReplayConfigurationPacketHandler configurationPacketHandler;
    private StreamCodec<ByteBuf, Packet<? super ClientGamePacketListener>> gamePacketCodec;
    private final StreamCodec<ByteBuf, Packet<? super ClientConfigurationPacketListener>> configurationPacketCodec;
    private final List<ReplayPlayer> replayViewers = new ArrayList<ReplayPlayer>();
    public boolean followLocalPlayerNextTickIfWrongDimension = false;
    public boolean isProcessingSnapshot = false;
    public List<FlashbackRawCustomPayload> customPacketsInSnapshot = new ArrayList<FlashbackRawCustomPayload>();
    private boolean processedSnapshot = false;
    public volatile boolean fastForwarding = false;
    public volatile boolean hasServerResourcePack = false;
    private List<BlockAtPosition> pendingBlockOverrides = new ArrayList<BlockAtPosition>();
    private int printFailedDecodePacketCount = 8;
    private final UUID playbackUUID;
    private final FlashbackMeta metadata;
    private final TreeMap<Integer, PlayableChunk> playableChunksByStart = new TreeMap();
    private ReplayChunkCache replayChunkCache = null;
    private final IntSet loadedChunkCacheFiles = new IntOpenHashSet();
    private ReplayReader currentReplayReader = null;
    private final Map<UUID, RemotePack> oldRemotePacks = new HashMap<UUID, RemotePack>();
    private final Map<UUID, RemotePack> remotePacks = new HashMap<UUID, RemotePack>();
    private final Map<UUID, BossEvent> bossEvents = new HashMap<UUID, BossEvent>();
    private Component tabListHeader = Component.empty();
    private Component tabListFooter = Component.empty();
    private final Map<ResourceKey<Level>, IntSet> needsPositionUpdate = new HashMap<ResourceKey<Level>, IntSet>();
    private Component shutdownReason = null;
    private FileSystem playbackFileSystem = null;
    private boolean initializedWithSnapshot = false;
    private final IgnoredCustomPayloads ignoredCustomPayloads = new IgnoredCustomPayloads();
    public List<Registry.PendingTags<?>> overridePendingTags = null;
    private final AtomicInteger newPlayerIds = new AtomicInteger(REPLAY_VIEWER_IDS_START);
    private int lastReplayTick;
    private long lastTickTimeNanos;
    public static final TicketType ENTITY_LOAD_TICKET = new TicketType(20L, 6);

    public ReplayServer(Thread thread, Minecraft minecraft, LevelStorageSource.LevelStorageAccess levelStorageAccess, PackRepository packRepository, WorldStem worldStem, Optional<GameRules> gameRules, Services services, LevelLoadListener levelLoadListener, UUID playbackUUID, Path path) {
        super(thread, minecraft, levelStorageAccess, packRepository, worldStem, gameRules, services, levelLoadListener);
        this.playbackUUID = playbackUUID;
        this.gamePacketHandler = new ReplayGamePacketHandler(this);
        this.configurationPacketHandler = new ReplayConfigurationPacketHandler(this);
        this.gamePacketCodec = GameProtocols.CLIENTBOUND_TEMPLATE.bind(RegistryFriendlyByteBuf.decorator((RegistryAccess)this.registryAccess())).codec();
        this.configurationPacketCodec = ConfigurationProtocols.CLIENTBOUND.codec();
        try {
            this.playbackFileSystem = FileSystems.newFileSystem(path);
            Path metadataPath = this.playbackFileSystem.getPath("/metadata.json", new String[0]);
            String metadataJson = Files.readString(metadataPath);
            this.metadata = FlashbackMeta.fromJson((JsonObject)GSON.fromJson(metadataJson, JsonObject.class));
            if (this.metadata == null) {
                throw new RuntimeException("Invalid metadata file");
            }
            int ticks = 0;
            for (Map.Entry<String, FlashbackChunkMeta> entry : this.metadata.chunks.entrySet()) {
                PlayableChunk chunkMetaWithPath = new PlayableChunk(entry.getValue(), this.playbackFileSystem.getPath("/" + entry.getKey(), new String[0]));
                this.playableChunksByStart.put(ticks, chunkMetaWithPath);
                ticks += entry.getValue().duration;
            }
            this.totalTicks = ticks;
            this.replayChunkCache = new ReplayChunkCache(this.playbackFileSystem);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.getEditorState().usedByPaths.add(path.toString());
    }

    public FlashbackMeta getMetadata() {
        return this.metadata;
    }

    public void updateRegistry(FeatureFlagSet featureFlagSet, List<Registry.PendingTags<?>> pendingTags, List<Packet<? super ClientConfigurationPacketListener>> initialPackets, List<ConfigurationTask> configurationTasks, @Nullable Collection<String> knownPackIds) {
        WorldData worldData = this.worldData;
        if (worldData instanceof PrimaryLevelData) {
            PrimaryLevelData primaryLevelData = (PrimaryLevelData)worldData;
            primaryLevelData.settings = new LevelSettings(primaryLevelData.settings.levelName(), primaryLevelData.settings.gameType(), primaryLevelData.settings.difficultySettings(), primaryLevelData.settings.allowCommands(), new WorldDataConfiguration(this.worldData.getDataConfiguration().dataPacks(), featureFlagSet));
        } else {
            this.worldData.setDataConfiguration(new WorldDataConfiguration(this.worldData.getDataConfiguration().dataPacks(), featureFlagSet));
        }
        this.gameRules = this.gameRules.copy(featureFlagSet);
        this.overridePendingTags = pendingTags;
        this.reloadResources(knownPackIds != null ? knownPackIds : this.getPackRepository().getSelectedIds());
        this.clockManager().init((MinecraftServer)this);
        this.gamePacketCodec = GameProtocols.CLIENTBOUND_TEMPLATE.bind(RegistryFriendlyByteBuf.decorator((RegistryAccess)this.registryAccess())).codec();
        if (this.currentReplayReader != null) {
            this.currentReplayReader.changeRegistryAccess((RegistryAccess)this.registryAccess());
        }
        ArrayList players = new ArrayList(this.getPlayerList().getPlayers());
        for (ServerPlayer player : players) {
            if (!(player instanceof ReplayPlayer)) continue;
            ((ServerGamePacketListenerImplExt)player.connection).flashback$switchToConfigWithTasks(initialPackets, configurationTasks);
        }
        this.replayViewers.clear();
    }

    public ReplayPlayer createPlayer(ServerLevel level, GameProfile gameProfile, ClientInformation clientInformation) {
        ServerLevel serverLevel;
        if (this.spawnLevel != null && (serverLevel = this.getLevel(this.spawnLevel)) != null) {
            level = serverLevel;
        }
        ReplayPlayer player = new ReplayPlayer((MinecraftServer)this, level, gameProfile, clientInformation);
        player.setId(this.newPlayerIds.getAndDecrement());
        player.followLocalPlayerNextTick = true;
        return player;
    }

    public boolean initServer() {
        Entity.ENTITY_COUNTER.set(1000000);
        this.setPlayerList(new PlayerList(this, (MinecraftServer)this, this.registries(), this.playerDataStorage, (NotificationService)this.notificationManager()){
            final /* synthetic */ ReplayServer this$0;
            {
                ReplayServer replayServer = this$0;
                Objects.requireNonNull(replayServer);
                this.this$0 = replayServer;
                super(server, registries, playerIo, notificationService);
            }

            public void placeNewPlayer(Connection connection, ServerPlayer serverPlayer, CommonListenerCookie commonListenerCookie) {
                if (Flashback.getConfig().internal.filterUnnecessaryPackets) {
                    ((ConnectionExt)connection).flashback$setFilterUnnecessaryPackets();
                }
                super.placeNewPlayer(connection, serverPlayer, commonListenerCookie);
            }

            public boolean canBypassPlayerLimit(NameAndId nameAndId) {
                return true;
            }

            public void broadcastSystemMessage(Component component, Function<ServerPlayer, Component> function, boolean bl) {
            }

            public void sendPlayerPermissionLevel(ServerPlayer serverPlayer) {
                if (serverPlayer instanceof FlashbackFakePlayer) {
                    return;
                }
                super.sendPlayerPermissionLevel(serverPlayer);
            }

            public void sendAllPlayerInfo(ServerPlayer serverPlayer) {
                if (serverPlayer instanceof FlashbackFakePlayer) {
                    return;
                }
                super.sendAllPlayerInfo(serverPlayer);
            }

            public void broadcastAll(Packet<?> packet) {
                if (packet instanceof ClientboundSetBorderLerpSizePacket) {
                    long time = (long)this.this$0.currentTick * 50L;
                    for (ReplayPlayer replayViewer : this.this$0.replayViewers) {
                        ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackSetBorderLerpStartTime(time));
                    }
                }
                super.broadcastAll(packet);
            }

            public void broadcast(@Nullable Player player, double x, double y, double z, double distance, ResourceKey<Level> resourceKey, Packet<?> packet) {
                UUID audioSourceEntity = null;
                EditorState editorState = this.this$0.getEditorState();
                if (editorState != null) {
                    audioSourceEntity = editorState.audioSourceEntity;
                }
                for (ReplayPlayer replayViewer : this.this$0.replayViewers) {
                    double dz;
                    double dy;
                    double dx;
                    Entity entity;
                    if (replayViewer == player || replayViewer.level().dimension() != resourceKey) continue;
                    Vec3 source = replayViewer.position();
                    if (audioSourceEntity != null && (entity = replayViewer.level().getEntity(audioSourceEntity)) != null) {
                        source = entity.position();
                    }
                    if (!((dx = x - source.x) * dx + (dy = y - source.y) * dy + (dz = z - source.z) * dz < distance * distance)) continue;
                    replayViewer.connection.send(packet);
                }
            }

            public void sendLevelInfo(ServerPlayer serverPlayer, ServerLevel serverLevel) {
                if (serverPlayer instanceof FlashbackFakePlayer) {
                    return;
                }
                super.sendLevelInfo(serverPlayer, serverLevel);
                serverPlayer.connection.send((Packet)new ClientboundResourcePackPopPacket(Optional.empty()));
                EditorState editorState = this.this$0.getEditorState();
                if (!editorState.replayVisuals.disableServerResourcePack) {
                    for (RemotePack remotePack : this.this$0.remotePacks.values()) {
                        serverPlayer.connection.send((Packet)new ClientboundResourcePackPushPacket(remotePack.id, remotePack.url, remotePack.hash, true, Optional.empty()));
                    }
                }
                this.this$0.tickResourcePacks(editorState);
                serverPlayer.connection.send((Packet)new ClientboundTabListPacket(this.this$0.tabListHeader, this.this$0.tabListFooter));
                serverPlayer.connection.send((Packet)new ClientboundInitializeBorderPacket(serverLevel.getWorldBorder()));
                this.this$0.customPacketsInSnapshot.removeIf(packet -> {
                    try {
                        ServerPlayNetworking.send((ServerPlayer)serverPlayer, (CustomPacketPayload)packet);
                        return false;
                    }
                    catch (Exception e) {
                        return true;
                    }
                });
            }

            protected void save(ServerPlayer serverPlayer) {
            }

            public void saveAll() {
            }

            public void removeAll() {
                if (this.this$0.shutdownReason == null) {
                    super.removeAll();
                } else {
                    List players = this.getPlayers();
                    for (ServerPlayer player : players) {
                        player.connection.disconnect(this.this$0.shutdownReason);
                    }
                }
            }

            public ServerStatsCounter getPlayerStats(Player player) {
                File statsDir = this.getServer().getWorldPath(LevelResource.PLAYER_STATS_DIR).toFile();
                File statsFile = new File(statsDir, String.valueOf(player.getUUID()) + ".json");
                return new ServerStatsCounter(this.getServer(), statsFile.toPath());
            }
        });
        super.initServer();
        return true;
    }

    public void pushRemotePack(UUID uuid, String url, String hash) {
        this.remotePacks.put(uuid, new RemotePack(uuid, url, hash));
    }

    public void popRemotePack(UUID uuid) {
        this.remotePacks.remove(uuid);
    }

    public void popAllRemotePacks() {
        this.remotePacks.clear();
    }

    public void setTabListCustomization(Component header, Component footer) {
        this.tabListHeader = header;
        this.tabListFooter = footer;
        this.getPlayerList().broadcastAll((Packet)new ClientboundTabListPacket(header, footer));
    }

    public void goToReplayTick(int tick) {
        this.jumpToTick = tick;
    }

    public float getDesiredTickRate(boolean manual) {
        if (manual) {
            return this.desiredTickRateManual;
        }
        return this.desiredTickRate;
    }

    public void setDesiredTickRate(float tickrate, boolean manual) {
        if (manual) {
            this.desiredTickRateManual = tickrate;
        } else {
            this.desiredTickRate = tickrate;
        }
    }

    public void setFrozen(boolean frozen, int delay) {
        this.desiredFrozen = frozen;
        this.desiredFrozenDelay = delay;
    }

    public int getReplayTick() {
        return this.targetTick;
    }

    public double getPartialReplayTick() {
        if (this.replayPaused || this.isPaused()) {
            return this.targetTick;
        }
        long currentNanos = Util.getNanos();
        long nanosPerTick = this.tickRateManager().nanosecondsPerTick();
        double partial = (double)(currentNanos - this.lastTickTimeNanos) / (double)nanosPerTick;
        partial = Math.max(0.0, Math.min(1.0, partial));
        return (double)this.lastReplayTick + partial;
    }

    public int getTotalReplayTicks() {
        return this.totalTicks;
    }

    public boolean doClientRendering() {
        return !this.isProcessingSnapshot && !this.processedSnapshot && !this.fastForwarding;
    }

    public void updateBossBar(ClientboundBossEventPacket packet) {
        packet.dispatch(new ClientboundBossEventPacket.Handler(this){
            final /* synthetic */ ReplayServer this$0;
            {
                ReplayServer replayServer = this$0;
                Objects.requireNonNull(replayServer);
                this.this$0 = replayServer;
            }

            public void add(UUID uuid, Component component, float progress, BossEvent.BossBarColor bossBarColor, BossEvent.BossBarOverlay bossBarOverlay, boolean darkenScreen, boolean playBossMusic, boolean createWorldFog) {
                BossEvent old = this.this$0.bossEvents.remove(uuid);
                BossEvent newEvent = new BossEvent(this, uuid, component, bossBarColor, bossBarOverlay){
                    {
                        Objects.requireNonNull(this$1);
                        super(id, name, color, overlay);
                    }
                };
                newEvent.setProgress(progress);
                newEvent.setDarkenScreen(darkenScreen);
                newEvent.setPlayBossMusic(playBossMusic);
                newEvent.setCreateWorldFog(createWorldFog);
                if (old != null) {
                    if (old.getProgress() != progress) {
                        this.this$0.getPlayerList().broadcastAll((Packet)ClientboundBossEventPacket.createUpdateProgressPacket((BossEvent)newEvent));
                    }
                    if (!old.getName().equals((Object)component)) {
                        this.this$0.getPlayerList().broadcastAll((Packet)ClientboundBossEventPacket.createUpdateNamePacket((BossEvent)newEvent));
                    }
                    if (!old.getColor().equals((Object)bossBarColor) || !old.getOverlay().equals((Object)bossBarOverlay)) {
                        this.this$0.getPlayerList().broadcastAll((Packet)ClientboundBossEventPacket.createUpdateStylePacket((BossEvent)newEvent));
                    }
                    if (old.shouldDarkenScreen() != darkenScreen || old.shouldPlayBossMusic() != playBossMusic || old.shouldCreateWorldFog() != createWorldFog) {
                        this.this$0.getPlayerList().broadcastAll((Packet)ClientboundBossEventPacket.createUpdatePropertiesPacket((BossEvent)newEvent));
                    }
                } else {
                    this.this$0.getPlayerList().broadcastAll((Packet)ClientboundBossEventPacket.createAddPacket((BossEvent)newEvent));
                }
                this.this$0.bossEvents.put(uuid, newEvent);
            }

            public void remove(UUID uuid) {
                if (this.this$0.bossEvents.remove(uuid) != null) {
                    this.this$0.getPlayerList().broadcastAll((Packet)ClientboundBossEventPacket.createRemovePacket((UUID)uuid));
                }
            }

            public void updateProgress(UUID uuid, float progress) {
                BossEvent current = this.this$0.bossEvents.get(uuid);
                if (current != null) {
                    current.setProgress(progress);
                    this.this$0.getPlayerList().broadcastAll((Packet)ClientboundBossEventPacket.createUpdateProgressPacket((BossEvent)current));
                }
            }

            public void updateName(UUID uuid, Component component) {
                BossEvent current = this.this$0.bossEvents.get(uuid);
                if (current != null) {
                    current.setName(component);
                    this.this$0.getPlayerList().broadcastAll((Packet)ClientboundBossEventPacket.createUpdateNamePacket((BossEvent)current));
                }
            }

            public void updateStyle(UUID uuid, BossEvent.BossBarColor bossBarColor, BossEvent.BossBarOverlay bossBarOverlay) {
                BossEvent current = this.this$0.bossEvents.get(uuid);
                if (current != null) {
                    current.setColor(bossBarColor);
                    current.setOverlay(bossBarOverlay);
                    this.this$0.getPlayerList().broadcastAll((Packet)ClientboundBossEventPacket.createUpdateStylePacket((BossEvent)current));
                }
            }

            public void updateProperties(UUID uuid, boolean darkenScreen, boolean playBossMusic, boolean createWorldFog) {
                BossEvent current = this.this$0.bossEvents.get(uuid);
                if (current != null) {
                    current.setDarkenScreen(darkenScreen);
                    current.setPlayBossMusic(playBossMusic);
                    current.setCreateWorldFog(createWorldFog);
                    this.this$0.getPlayerList().broadcastAll((Packet)ClientboundBossEventPacket.createUpdatePropertiesPacket((BossEvent)current));
                }
            }
        });
    }

    public Collection<ReplayPlayer> getReplayViewers() {
        return this.replayViewers;
    }

    public int getLocalPlayerId() {
        return this.gamePacketHandler.localPlayerId;
    }

    public void setTimeRtc(long rtc) {
        this.pendingTimeRtc = rtc;
    }

    public void updateTimeRtc(byte delta) {
        if (this.pendingTimeRtc == 0L) {
            this.pendingTimeRtc = this.timeRtc;
        }
        this.pendingTimeRtc += 50L + (long)delta;
    }

    public boolean hasRtcData() {
        return this.lastTimeRtc != 0L || this.timeRtc != 0L || this.pendingTimeRtc != 0L;
    }

    public long getInterpolatedRtc() {
        if (this.replayPaused || this.isPaused() || this.timeRtc < this.lastTimeRtc) {
            return this.timeRtc;
        }
        long currentNanos = Util.getNanos();
        long nanosPerTick = this.tickRateManager().nanosecondsPerTick();
        double partial = (double)(currentNanos - this.lastTickTimeNanos) / (double)nanosPerTick;
        partial = Math.max(0.0, Math.min(1.0, partial));
        return this.lastTimeRtc + (long)((double)(this.timeRtc - this.lastTimeRtc) * partial);
    }

    public void handleNextTick() {
        if (this.isProcessingSnapshot) {
            throw new IllegalStateException("Can't go to next tick while processing snapshot");
        }
        this.gamePacketHandler.flushPendingEntities();
        ++this.currentTick;
        this.updateRtc();
    }

    private void updateRtc() {
        if (!this.hasRtcData()) {
            return;
        }
        if (this.pendingTimeRtc == 0L && this.timeRtc != 0L) {
            this.pendingTimeRtc = this.timeRtc + 50L;
        }
        this.lastTimeRtc = this.lastTimeRtc == 0L || this.lastTimeRtc > this.pendingTimeRtc ? this.pendingTimeRtc : this.timeRtc;
        this.timeRtc = this.pendingTimeRtc;
        this.pendingTimeRtc = 0L;
    }

    public void handleConfigurationPacket(RegistryFriendlyByteBuf friendlyByteBuf) {
        Packet packet = (Packet)this.configurationPacketCodec.decode((Object)friendlyByteBuf);
        this.gamePacketHandler.flushPendingEntities();
        packet.handle((PacketListener)this.configurationPacketHandler);
    }

    public void handleGamePacket(RegistryFriendlyByteBuf friendlyByteBuf) {
        Packet packet;
        this.configurationPacketHandler.flushPendingConfiguration();
        int start = friendlyByteBuf.readerIndex();
        try {
            packet = (Packet)this.gamePacketCodec.decode((Object)friendlyByteBuf);
        }
        catch (DecoderException decoderException) {
            if (this.printFailedDecodePacketCount > 0) {
                Flashback.LOGGER.error("Failed to decode packet from replay stream", (Throwable)decoderException);
                --this.printFailedDecodePacketCount;
            }
            this.gamePacketHandler.flushPendingEntities();
            friendlyByteBuf.readerIndex(friendlyByteBuf.writerIndex());
            return;
        }
        int end = friendlyByteBuf.readerIndex();
        if (!AllowPendingEntityPacketSet.allowPendingEntity(packet)) {
            this.gamePacketHandler.flushPendingEntities();
        }
        if (packet instanceof ClientboundCustomPayloadPacket) {
            ClientboundCustomPayloadPacket custom = (ClientboundCustomPayloadPacket)packet;
            try {
                Identifier id = custom.payload().type().id();
                this.ignoredCustomPayloads.setFromConfigString(Flashback.getConfig().advanced.ignoredCustomPayloads);
                if (this.ignoredCustomPayloads.isIgnored(id)) {
                    return;
                }
                friendlyByteBuf.readerIndex(start);
                friendlyByteBuf.readVarInt();
                int dataSize = end - friendlyByteBuf.readerIndex();
                if (dataSize < 0) {
                    return;
                }
                byte[] rawBytes = ByteBufUtil.getBytes((ByteBuf)friendlyByteBuf.readBytes(dataSize));
                FlashbackRawCustomPayload rawCustomPayload = new FlashbackRawCustomPayload(rawBytes, false);
                if (this.isProcessingSnapshot) {
                    this.customPacketsInSnapshot.add(rawCustomPayload);
                }
                for (ServerPlayer serverPlayer : this.getReplayViewers()) {
                    ServerPlayNetworking.send((ServerPlayer)serverPlayer, (CustomPacketPayload)rawCustomPayload);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            friendlyByteBuf.readerIndex(end);
            return;
        }
        packet.handle((PacketListener)this.gamePacketHandler);
    }

    public void handleCreateLocalPlayer(RegistryFriendlyByteBuf friendlyByteBuf) {
        this.configurationPacketHandler.flushPendingConfiguration();
        this.gamePacketHandler.flushPendingEntities();
        this.gamePacketHandler.handleCreateLocalPlayer(friendlyByteBuf);
    }

    public void handleAccuratePlayerPosition(RegistryFriendlyByteBuf friendlyByteBuf) {
        FlashbackConfigV1 config = Flashback.getConfig();
        if (config.advanced.disableIncreasedFirstPersonUpdates) {
            friendlyByteBuf.readerIndex(friendlyByteBuf.writerIndex());
            return;
        }
        FlashbackAccurateEntityPosition packet = (FlashbackAccurateEntityPosition)FlashbackAccurateEntityPosition.STREAM_CODEC.decode((Object)friendlyByteBuf);
        for (ReplayPlayer replayViewer : this.replayViewers) {
            ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)packet);
        }
    }

    public void handleMoveEntities(RegistryFriendlyByteBuf friendlyByteBuf) {
        this.gamePacketHandler.flushPendingEntities();
        this.configurationPacketHandler.flushPendingConfiguration();
        int levelCount = friendlyByteBuf.readVarInt();
        for (int i = 0; i < levelCount; ++i) {
            ResourceKey dimension = friendlyByteBuf.readResourceKey(Registries.DIMENSION);
            ServerLevel level = (ServerLevel)this.levels.get(dimension);
            IntSet positionUpdateSet = null;
            if (level != null) {
                positionUpdateSet = this.needsPositionUpdate.computeIfAbsent((ResourceKey<Level>)dimension, k -> new IntOpenHashSet());
            }
            int count = friendlyByteBuf.readVarInt();
            for (int j = 0; j < count; ++j) {
                int id = friendlyByteBuf.readVarInt();
                double x = friendlyByteBuf.readDouble();
                double y = friendlyByteBuf.readDouble();
                double z = friendlyByteBuf.readDouble();
                float yaw = friendlyByteBuf.readFloat();
                float pitch = friendlyByteBuf.readFloat();
                float headYaw = friendlyByteBuf.readFloat();
                boolean onGround = friendlyByteBuf.readBoolean();
                if (level == null) continue;
                Entity entity = level.getEntity(id);
                if (entity != null) {
                    if (entity.isPassenger()) {
                        entity.setYRot(yaw);
                        entity.setXRot(pitch);
                    } else {
                        entity.snapTo(x, y, z, yaw, pitch);
                        this.updatePositionOfPassengers(entity);
                    }
                    entity.setYHeadRot(headYaw);
                    if (entity.onGround() != onGround) {
                        entity.setOnGround(onGround);
                    }
                    if (entity instanceof ItemEntity || entity instanceof ExperienceOrb) continue;
                    positionUpdateSet.add(id);
                    continue;
                }
                if (this.isFrozen) continue;
                byte yRot = (byte)Mth.floor((float)(yaw * 256.0f / 360.0f));
                byte xRot = (byte)Mth.floor((float)(pitch * 256.0f / 360.0f));
                this.getPlayerList().broadcastAll(PacketHelper.createTeleportForUnknown(id, x, y, z, yRot, xRot, onGround));
            }
        }
    }

    private void updatePositionOfPassengers(Entity vehicle) {
        for (Entity passenger : vehicle.getPassengers()) {
            vehicle.positionRider(passenger);
            this.updatePositionOfPassengers(passenger);
        }
    }

    public void handleLevelChunkCached(int index) {
        block5: {
            ClientboundLevelChunkWithLightPacket packet = this.replayChunkCache.getOrLoad(index, (RegistryAccess)this.registryAccess(), this.gamePacketCodec);
            if (packet != null) {
                this.configurationPacketHandler.flushPendingConfiguration();
                this.gamePacketHandler.flushPendingEntities();
                try {
                    int x = packet.getX();
                    int z = packet.getZ();
                    LevelChunk chunk = this.gamePacketHandler.level().getChunk(x, z);
                    if (Flashback.EXPORT_JOB == null && ReplayServer.doesCachedChunkIdMatch(chunk, index) && !this.gamePacketHandler.forceSendChunksDueToMovingPistonShenanigans.contains(ChunkPos.pack((int)x, (int)z))) break block5;
                    packet.handle((ClientGamePacketListener)this.gamePacketHandler);
                    if (chunk instanceof LevelChunkExt) {
                        LevelChunkExt ext = (LevelChunkExt)chunk;
                        ext.flashback$setCachedChunkId(index);
                    }
                }
                catch (Exception exception) {}
            } else {
                Flashback.LOGGER.error("Missing cached level chunk: {}", (Object)index);
            }
        }
    }

    private static boolean doesCachedChunkIdMatch(LevelChunk chunk, int chunkId) {
        if (chunk instanceof LevelChunkExt) {
            LevelChunkExt ext = (LevelChunkExt)chunk;
            return ext.flashback$getCachedChunkId() == chunkId;
        }
        return false;
    }

    public void loadLevel() {
        super.loadLevel();
        for (ServerLevel level : this.levels.values()) {
            level.noSave = true;
        }
    }

    public void closeLevel(ServerLevel serverLevel) {
        if (serverLevel == null) {
            return;
        }
        this.clearLevel(serverLevel);
        ((ServerLevelEvents.Unload)ServerLevelEvents.UNLOAD.invoker()).onLevelUnload((MinecraftServer)this, serverLevel);
        try {
            serverLevel.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearLevel(ServerLevel serverLevel) {
        if (serverLevel == null) {
            return;
        }
        for (ServerPlayer player : new ArrayList(serverLevel.players())) {
            if (player instanceof ReplayPlayer) {
                ReplayPlayer replayPlayer = (ReplayPlayer)player;
                replayPlayer.lastFirstPersonDataUUID = null;
                continue;
            }
            player.discard();
        }
        ArrayList<Entity> entities = new ArrayList<Entity>();
        for (Entity entity : serverLevel.getAllEntities()) {
            if (entity instanceof ServerPlayer) continue;
            entities.add(entity);
        }
        for (Entity entity : entities) {
            if (entity == null) continue;
            entity.discard();
        }
        for (ServerPlayer player : serverLevel.players()) {
            if (!(player instanceof ReplayPlayer)) continue;
            ReplayPlayer replayPlayer = (ReplayPlayer)player;
            ServerPlayNetworking.send((ServerPlayer)replayPlayer, (CustomPacketPayload)FlashbackClearEntities.INSTANCE);
        }
    }

    public EditorState getEditorState() {
        if (Flashback.isExporting()) {
            return Flashback.EXPORT_JOB.getSettings().editorState();
        }
        return EditorStateManager.get(this.metadata.replayIdentifier);
    }

    public boolean isReady() {
        return super.isReady() && this.initializedWithSnapshot;
    }

    public void tickServer(BooleanSupplier booleanSupplier) {
        if (!this.initializedWithSnapshot) {
            this.initializedWithSnapshot = true;
            ReplayReader replayReader = this.playableChunksByStart.get(0).getOrLoadReplayReader((RegistryAccess)this.registryAccess());
            replayReader.handleSnapshot(this);
            this.gamePacketHandler.flushPendingEntities();
            this.updateRtc();
        }
        EditorState editorState = this.getEditorState();
        this.lastReplayTick = this.targetTick;
        this.lastTickTimeNanos = this.nextTickTimeNanos - this.tickRateManager().nanosecondsPerTick();
        this.replayViewers.clear();
        this.hasNonSpectatorReplayViewer = false;
        for (ServerPlayer player : this.getPlayerList().getPlayers()) {
            if (!(player instanceof ReplayPlayer)) continue;
            ReplayPlayer replayPlayer = (ReplayPlayer)player;
            if (replayPlayer.isShiftKeyDown()) {
                replayPlayer.spectatingUuid = null;
                replayPlayer.spectatingUuidTickCount = 0;
                replayPlayer.forceRespectateTickCount = 0;
            } else {
                Entity cameraEntity = replayPlayer.getCamera();
                if (cameraEntity != null && cameraEntity != replayPlayer) {
                    replayPlayer.spectatingUuid = cameraEntity.getUUID();
                    replayPlayer.spectatingUuidTickCount = 20;
                } else if (replayPlayer.spectatingUuidTickCount > 0) {
                    --replayPlayer.spectatingUuidTickCount;
                } else {
                    replayPlayer.spectatingUuid = null;
                }
            }
            if (!replayPlayer.isSpectator()) {
                this.hasNonSpectatorReplayViewer = true;
            }
            this.replayViewers.add(replayPlayer);
        }
        if (!this.replayPaused && this.isPaused()) {
            this.replayPaused = true;
        }
        boolean normalPlayback = false;
        if (this.jumpToTick >= 0) {
            this.targetTick = this.jumpToTick;
            this.jumpToTick = -1;
        } else if (!this.replayPaused && this.targetTick < this.totalTicks) {
            ++this.targetTick;
            normalPlayback = true;
        } else if (this.targetTick == this.totalTicks && this.currentTick == this.totalTicks) {
            this.replayPaused = true;
        }
        ServerTickRateManager tickRateManager = this.tickRateManager();
        ((ServerTickRateManagerExt)tickRateManager).flashback$setSuppressClientUpdates(true);
        if (Flashback.EXPORT_JOB != null || this.targetTick == this.currentTick || normalPlayback || this.isFrozen) {
            this.runUpdates(booleanSupplier);
        } else {
            int realTargetTick = this.targetTick;
            if (this.targetTick < this.currentTick) {
                int minTick = this.playableChunksByStart.floorKey(this.targetTick) + 1;
                this.targetTick = Math.max(minTick, realTargetTick - 20);
            } else {
                this.targetTick = Math.max(this.currentTick + 1, realTargetTick - 20);
            }
            if (this.targetTick >= realTargetTick) {
                this.targetTick = realTargetTick;
                this.runUpdates(booleanSupplier);
            } else {
                while (this.targetTick <= realTargetTick) {
                    this.fastForwarding = this.targetTick < realTargetTick;
                    this.runUpdates(booleanSupplier);
                    if (this.targetTick == realTargetTick) break;
                    ++this.targetTick;
                }
                this.fastForwarding = false;
            }
        }
        ((ServerTickRateManagerExt)tickRateManager).flashback$setSuppressClientUpdates(false);
        if (this.forceApplyKeyframes.compareAndSet(true, false)) {
            ((MinecraftExt)Minecraft.getInstance()).flashback$applyKeyframes();
        }
        this.tryFollowLocalPlayer();
        for (ReplayPlayer replayViewer : this.replayViewers) {
            Entity camera;
            if (replayViewer.spectatingUuid != null) {
                Entity targetEntity;
                camera = replayViewer.getCamera();
                if ((replayViewer.forceRespectateTickCount > 0 || camera == null || camera == replayViewer || camera.isRemoved()) && (targetEntity = replayViewer.level().getEntity(replayViewer.spectatingUuid)) != null && !targetEntity.isRemoved()) {
                    replayViewer.setCamera(null);
                    replayViewer.setCamera(targetEntity);
                    replayViewer.spectatingUuid = targetEntity.getUUID();
                    if (replayViewer.forceRespectateTickCount == 0) {
                        replayViewer.forceRespectateTickCount = 5;
                    }
                }
            }
            if (replayViewer.forceRespectateTickCount > 0) {
                --replayViewer.forceRespectateTickCount;
            }
            if ((camera = replayViewer.getCamera()) != replayViewer && camera instanceof Player) {
                ItemStack hotbarItem;
                int i;
                FoodData foodData;
                Player playerCamera = (Player)camera;
                Inventory inventory = playerCamera.getInventory();
                if (!Objects.equals(replayViewer.lastFirstPersonDataUUID, playerCamera.getUUID())) {
                    replayViewer.lastFirstPersonDataUUID = playerCamera.getUUID();
                    replayViewer.lastFirstPersonExperienceProgress = playerCamera.experienceProgress;
                    replayViewer.lastFirstPersonTotalExperience = playerCamera.totalExperience;
                    replayViewer.lastFirstPersonExperienceLevel = playerCamera.experienceLevel;
                    ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackRemoteExperience(playerCamera.getId(), playerCamera.experienceProgress, playerCamera.totalExperience, playerCamera.experienceLevel));
                    foodData = playerCamera.getFoodData();
                    replayViewer.lastFirstPersonFoodLevel = foodData.getFoodLevel();
                    replayViewer.lastFirstPersonSaturationLevel = foodData.getSaturationLevel();
                    ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackRemoteFoodData(playerCamera.getId(), foodData.getFoodLevel(), foodData.getSaturationLevel()));
                    replayViewer.lastFirstPersonSelectedSlot = inventory.getSelectedSlot();
                    ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackRemoteSelectHotbarSlot(playerCamera.getId(), inventory.getSelectedSlot()));
                    for (i = 0; i < 9; ++i) {
                        hotbarItem = inventory.getItem(i);
                        replayViewer.lastFirstPersonHotbarItems[i] = hotbarItem.copy();
                        ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackRemoteSetSlot(playerCamera.getId(), i, hotbarItem.copy()));
                    }
                    continue;
                }
                if (replayViewer.lastFirstPersonExperienceProgress != playerCamera.experienceProgress || replayViewer.lastFirstPersonTotalExperience != playerCamera.totalExperience || replayViewer.lastFirstPersonExperienceLevel != playerCamera.experienceLevel) {
                    replayViewer.lastFirstPersonExperienceProgress = playerCamera.experienceProgress;
                    replayViewer.lastFirstPersonTotalExperience = playerCamera.totalExperience;
                    replayViewer.lastFirstPersonExperienceLevel = playerCamera.experienceLevel;
                    ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackRemoteExperience(playerCamera.getId(), playerCamera.experienceProgress, playerCamera.totalExperience, playerCamera.experienceLevel));
                }
                if (replayViewer.lastFirstPersonFoodLevel != (foodData = playerCamera.getFoodData()).getFoodLevel() || replayViewer.lastFirstPersonSaturationLevel != foodData.getSaturationLevel()) {
                    replayViewer.lastFirstPersonFoodLevel = foodData.getFoodLevel();
                    replayViewer.lastFirstPersonSaturationLevel = foodData.getSaturationLevel();
                    ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackRemoteFoodData(playerCamera.getId(), foodData.getFoodLevel(), foodData.getSaturationLevel()));
                }
                if (replayViewer.lastFirstPersonSelectedSlot != inventory.getSelectedSlot()) {
                    replayViewer.lastFirstPersonSelectedSlot = inventory.getSelectedSlot();
                    ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackRemoteSelectHotbarSlot(playerCamera.getId(), inventory.getSelectedSlot()));
                }
                for (i = 0; i < 9; ++i) {
                    hotbarItem = inventory.getItem(i);
                    if (ItemStack.matches((ItemStack)replayViewer.lastFirstPersonHotbarItems[i], (ItemStack)hotbarItem)) continue;
                    replayViewer.lastFirstPersonHotbarItems[i] = hotbarItem.copy();
                    ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackRemoteSetSlot(playerCamera.getId(), i, hotbarItem.copy()));
                }
                continue;
            }
            replayViewer.lastFirstPersonDataUUID = null;
        }
        this.tickResourcePacks(editorState);
        if (this.sendFinishedServerTick.compareAndExchange(true, false)) {
            for (ReplayPlayer replayViewer : this.replayViewers) {
                ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)FinishedServerTick.INSTANCE);
            }
            if (this.replayViewers.isEmpty() && Flashback.EXPORT_JOB != null) {
                Flashback.EXPORT_JOB.onFinishedServerTick();
            }
        }
    }

    private void tickResourcePacks(EditorState editorState) {
        if (this.remotePacks.isEmpty() || editorState.replayVisuals.disableServerResourcePack) {
            if (!this.oldRemotePacks.isEmpty()) {
                this.oldRemotePacks.clear();
                this.getPlayerList().broadcastAll((Packet)new ClientboundResourcePackPopPacket(Optional.empty()));
            }
        } else {
            for (Map.Entry<UUID, RemotePack> entry : this.remotePacks.entrySet()) {
                RemotePack remotePack = entry.getValue();
                RemotePack oldRemotePack = this.oldRemotePacks.get(entry.getKey());
                if (oldRemotePack == null) {
                    this.getPlayerList().broadcastAll((Packet)new ClientboundResourcePackPushPacket(remotePack.id, remotePack.url, remotePack.hash, true, Optional.empty()));
                } else if (!oldRemotePack.equals(remotePack)) {
                    this.getPlayerList().broadcastAll((Packet)new ClientboundResourcePackPopPacket(Optional.of(remotePack.id)));
                    this.getPlayerList().broadcastAll((Packet)new ClientboundResourcePackPushPacket(remotePack.id, remotePack.url, remotePack.hash, true, Optional.empty()));
                }
                this.oldRemotePacks.put(entry.getKey(), remotePack);
            }
            this.oldRemotePacks.keySet().removeIf(uuid -> {
                if (!this.remotePacks.containsKey(uuid)) {
                    this.getPlayerList().broadcastAll((Packet)new ClientboundResourcePackPopPacket(Optional.of(uuid)));
                    return true;
                }
                return false;
            });
        }
        this.hasServerResourcePack = !this.remotePacks.isEmpty();
    }

    private void runUpdates(BooleanSupplier booleanSupplier) {
        boolean tickChanged;
        ServerTickRateManager tickRateManager;
        this.desiredTickRate = 20.0f;
        this.desiredFrozen = false;
        this.getEditorState().applyKeyframes(new ReplayServerKeyframeHandler(this), this.targetTick);
        if (this.desiredFrozen && this.frozenDelay < 0) {
            this.frozenDelay = this.desiredFrozenDelay <= 0 ? 1 : (this.desiredFrozenDelay <= 5 ? 2 : 3);
        }
        float tickRate = this.desiredTickRate;
        if (Flashback.EXPORT_JOB == null) {
            tickRate *= this.desiredTickRateManual / 20.0f;
        }
        if (this.desiredFrozen) {
            if (this.frozenDelay > 0) {
                --this.frozenDelay;
            }
            if (this.frozenDelay == 0) {
                this.isFrozen = true;
            }
        } else {
            this.frozenDelay = -1;
            this.isFrozen = false;
        }
        if ((tickRateManager = this.tickRateManager()).tickrate() != tickRate) {
            tickRateManager.setTickRate(tickRate);
        }
        if (tickRateManager.isFrozen() != this.isFrozen) {
            tickRateManager.setFrozen(this.isFrozen);
        }
        boolean bl = tickChanged = this.targetTick != this.currentTick;
        if (this.isFrozen && this.targetTick >= this.currentTick) {
            tickChanged = false;
        } else {
            this.handleActions();
        }
        for (ServerLevel serverLevel : this.getAllLevels()) {
            for (Entity entity : serverLevel.getAllEntities()) {
                ChunkPos chunkPos = ChunkPos.containing((BlockPos)entity.blockPosition());
                serverLevel.getChunkSource().addTicketWithRadius(ENTITY_LOAD_TICKET, chunkPos, 3);
            }
        }
        super.tickServer(booleanSupplier);
        this.applyBlockOverridesToTimeline();
        if (!this.isFrozen && !this.needsPositionUpdate.isEmpty()) {
            for (Map.Entry entry : this.needsPositionUpdate.entrySet()) {
                ResourceKey dimension = (ResourceKey)entry.getKey();
                IntSet entities = (IntSet)entry.getValue();
                ServerLevel level = this.getLevel(dimension);
                if (level == null) continue;
                Int2ObjectMap entityMap = level.getChunkSource().chunkMap.entityMap;
                IntIterator iterator = entities.intIterator();
                while (iterator.hasNext()) {
                    int entityId = iterator.nextInt();
                    ChunkMap.TrackedEntity trackedEntity = (ChunkMap.TrackedEntity)entityMap.get(entityId);
                    if (trackedEntity == null) continue;
                    ServerEntity serverEntity = trackedEntity.serverEntity;
                    Vec3 trackingPosition = serverEntity.entity.trackingPosition();
                    byte quantizedYRot = (byte)Mth.floor((float)(serverEntity.entity.getYRot() * 256.0f / 360.0f));
                    byte quantizedXRot = (byte)Mth.floor((float)(serverEntity.entity.getXRot() * 256.0f / 360.0f));
                    if (!serverEntity.entity.isPassenger() && !serverEntity.positionCodec.getBase().equals((Object)trackingPosition)) {
                        trackedEntity.sendToTrackingPlayers((Packet)new ClientboundEntityPositionSyncPacket(serverEntity.entity.getId(), PositionMoveRotation.of((Entity)serverEntity.entity), serverEntity.wasOnGround));
                        serverEntity.positionCodec.setBase(trackingPosition);
                        serverEntity.lastSentYRot = quantizedYRot;
                        serverEntity.lastSentXRot = quantizedXRot;
                        continue;
                    }
                    if (quantizedYRot == serverEntity.lastSentYRot && quantizedXRot == serverEntity.lastSentXRot) continue;
                    trackedEntity.sendToTrackingPlayers((Packet)new ClientboundMoveEntityPacket.Rot(entityId, quantizedYRot, quantizedXRot, serverEntity.wasOnGround));
                    serverEntity.lastSentYRot = quantizedYRot;
                    serverEntity.lastSentXRot = quantizedXRot;
                }
            }
            this.needsPositionUpdate.clear();
        }
        if (Flashback.EXPORT_JOB == null) {
            if (this.processedSnapshot) {
                this.processedSnapshot = false;
                for (ReplayPlayer replayPlayer : this.replayViewers) {
                    replayPlayer.connection.chunkSender.sendNextChunks((ServerPlayer)replayPlayer);
                    ServerPlayNetworking.send((ServerPlayer)replayPlayer, (CustomPacketPayload)FlashbackInstantlyLerp.INSTANCE);
                    ServerPlayNetworking.send((ServerPlayer)replayPlayer, (CustomPacketPayload)FlashbackClearParticles.INSTANCE);
                }
                for (ServerLevel serverLevel : this.getAllLevels()) {
                    for (ServerPlayer player : serverLevel.players()) {
                        if (!(player instanceof ReplayPlayer)) continue;
                        serverLevel.getChunkSource().move(player);
                        serverLevel.getChunkSource().move(player);
                    }
                }
            }
            if (this.replayPaused) {
                if (tickChanged) {
                    if (tickRateManager.isFrozen()) {
                        tickRateManager.setFrozen(false);
                    }
                    ((ServerTickRateManagerExt)tickRateManager).flashback$setSuppressClientUpdates(false);
                    for (ReplayPlayer replayPlayer : this.replayViewers) {
                        ServerPlayNetworking.send((ServerPlayer)replayPlayer, (CustomPacketPayload)FlashbackForceClientTick.INSTANCE);
                    }
                    tickRateManager.setFrozen(true);
                    ((ServerTickRateManagerExt)tickRateManager).flashback$setSuppressClientUpdates(true);
                } else if (!tickRateManager.isFrozen()) {
                    tickRateManager.setFrozen(true);
                }
            } else if (tickRateManager.isFrozen() != this.isFrozen) {
                tickRateManager.setFrozen(this.isFrozen);
            }
        } else if (!tickRateManager.isFrozen()) {
            tickRateManager.setFrozen(true);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void applyBlockOverridesToTimeline() {
        if (this.pendingBlockOverrides.isEmpty()) {
            return;
        }
        List<BlockAtPosition> pendingBlockOverrides = this.pendingBlockOverrides;
        this.pendingBlockOverrides = new ArrayList<BlockAtPosition>();
        EditorState editorState = this.getEditorState();
        long stamp = editorState.acquireWrite();
        try {
            EditorScene scene = editorState.getCurrentScene(stamp);
            int currentTick = this.currentTick;
            boolean added = false;
            for (KeyframeTrack keyframeTrack : scene.keyframeTracks) {
                if (keyframeTrack.keyframeType != BlockOverrideKeyframeType.INSTANCE) continue;
                BlockOverrideKeyframe keyframe = (BlockOverrideKeyframe)keyframeTrack.keyframesByTick.get(currentTick);
                if (keyframe == null) {
                    keyframe = new BlockOverrideKeyframe();
                    keyframeTrack.keyframesByTick.put(currentTick, keyframe);
                }
                for (BlockAtPosition pendingBlockOverride : pendingBlockOverrides) {
                    long pos = pendingBlockOverride.pos;
                    int x = BlockPos.getX((long)pos);
                    int y = BlockPos.getY((long)pos);
                    int z = BlockPos.getZ((long)pos);
                    keyframe.setBlock(x, y, z, pendingBlockOverride.blockState);
                }
                added = true;
                break;
            }
            if (!added) {
                KeyframeTrack keyframeTrack = new KeyframeTrack(BlockOverrideKeyframeType.INSTANCE);
                BlockOverrideKeyframe keyframe = new BlockOverrideKeyframe();
                keyframeTrack.keyframesByTick.put(currentTick, keyframe);
                for (BlockAtPosition pendingBlockOverride : pendingBlockOverrides) {
                    long pos = pendingBlockOverride.pos;
                    int x = BlockPos.getX((long)pos);
                    int y = BlockPos.getY((long)pos);
                    int z = BlockPos.getZ((long)pos);
                    keyframe.setBlock(x, y, z, pendingBlockOverride.blockState);
                }
                scene.keyframeTracks.add(keyframeTrack);
            }
        }
        finally {
            editorState.release(stamp);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void handleActions() {
        Map.Entry<Integer, PlayableChunk> entry;
        boolean shouldJump;
        this.targetTick = Math.max(0, Math.min(this.totalTicks, this.targetTick));
        if (this.targetTick == this.currentTick) {
            return;
        }
        Map.Entry<Integer, PlayableChunk> oldEntry = this.playableChunksByStart.floorEntry(this.currentTick);
        int duration = oldEntry != null ? oldEntry.getValue().chunkMeta.duration : 6000;
        duration = Math.max(1200, duration);
        boolean bl = shouldJump = this.targetTick < this.currentTick;
        if (this.targetTick > this.currentTick + duration) {
            if (oldEntry != null) {
                Map.Entry<Integer, PlayableChunk> targetEntry = this.playableChunksByStart.floorEntry(this.targetTick);
                shouldJump |= oldEntry.getValue() != targetEntry.getValue();
            } else {
                shouldJump = true;
            }
        }
        if (shouldJump) {
            entry = this.playableChunksByStart.floorEntry(this.targetTick);
            this.playSnapshot(entry.getValue().getOrLoadReplayReader((RegistryAccess)this.registryAccess()));
            this.currentTick = entry.getKey();
        }
        if ((entry = this.playableChunksByStart.floorEntry(this.currentTick)) == null) {
            return;
        }
        this.currentReplayReader = entry.getValue().getOrLoadReplayReader((RegistryAccess)this.registryAccess());
        if (this.currentReplayReader.isAtStart() && this.currentTick != entry.getKey()) {
            String message = "Replay reader is at wrong position. Should be at start (" + String.valueOf(entry.getKey()) + ") but instead is at " + this.currentTick;
            Flashback.LOGGER.error(message);
            this.stopWithReason((Component)Component.literal((String)message));
            return;
        }
        if (this.currentTick == entry.getKey()) {
            this.currentReplayReader.resetToStart();
            if (!this.processedSnapshot && entry.getValue().chunkMeta.forcePlaySnapshot) {
                this.playSnapshot(this.currentReplayReader);
            }
        }
        EditorState editorState = this.getEditorState();
        long stamp = editorState.acquireRead();
        try {
            ServerLevelData serverLevelData;
            LevelData levelData;
            EditorScene scene = editorState.getCurrentScene(stamp);
            TreeMap<Integer, Keyframe> blockOverrideKeyframes = null;
            for (KeyframeTrack keyframeTrack : scene.keyframeTracks) {
                if (!keyframeTrack.enabled || keyframeTrack.keyframeType != BlockOverrideKeyframeType.INSTANCE) continue;
                blockOverrideKeyframes = keyframeTrack.keyframesByTick;
                break;
            }
            if (blockOverrideKeyframes == null) {
                editorState.release(stamp);
                stamp = 0L;
            }
            int lastActualTick = this.currentTick;
            while (this.currentTick < this.targetTick) {
                if (lastActualTick != this.currentTick) {
                    if (blockOverrideKeyframes != null) {
                        this.applyBlockOverrideKeyframes(blockOverrideKeyframes, lastActualTick);
                    }
                    for (ServerLevel level : this.getAllLevels()) {
                        levelData = level.getLevelData();
                        if (!(levelData instanceof ServerLevelData)) continue;
                        serverLevelData = (ServerLevelData)levelData;
                        serverLevelData.setGameTime(serverLevelData.getGameTime() + (long)this.currentTick - (long)lastActualTick);
                    }
                    lastActualTick = this.currentTick;
                }
                if (!this.currentReplayReader.handleNextAction(this)) {
                    Map.Entry<Integer, PlayableChunk> entry2 = this.playableChunksByStart.floorEntry(this.currentTick);
                    if (entry2.getValue() == entry.getValue()) {
                        this.targetTick = this.currentTick;
                        this.replayPaused = true;
                        return;
                    }
                    entry = entry2;
                    if (entry2.getKey() != this.currentTick) {
                        Flashback.LOGGER.error("Error processing replay: ran out of entries before expected end of PlayableChunk");
                        Flashback.LOGGER.error("Current tick: {}", (Object)this.currentTick);
                        Flashback.LOGGER.error("New entry start: {}", (Object)entry2.getKey());
                        this.stopWithReason((Component)Component.literal((String)"Error processing replay: ran out of entries before expected end of PlayableChunk"));
                        return;
                    }
                    this.currentReplayReader = entry.getValue().getOrLoadReplayReader((RegistryAccess)this.registryAccess());
                    this.currentReplayReader.resetToStart();
                    if (entry.getValue().chunkMeta.forcePlaySnapshot) {
                        this.playSnapshot(this.currentReplayReader);
                    }
                }
                if (entry.getKey() + entry.getValue().chunkMeta.duration >= this.currentTick) continue;
                Flashback.LOGGER.error("Error processing replay: actual duration of PlayableChunk inconsistent with recorded duration");
                Flashback.LOGGER.error("Current tick: {}", (Object)this.currentTick);
                Flashback.LOGGER.error("PlayableChunk tick base: {}", (Object)entry.getKey());
                Flashback.LOGGER.error("PlayableChunk duration: {}", (Object)entry.getValue().chunkMeta.duration);
                this.stopWithReason((Component)Component.literal((String)"Error processing replay: actual duration of PlayableChunk inconsistent with recorded duration"));
                return;
            }
            if (lastActualTick != this.currentTick) {
                if (blockOverrideKeyframes != null) {
                    this.applyBlockOverrideKeyframes(blockOverrideKeyframes, lastActualTick);
                }
                for (ServerLevel level : this.getAllLevels()) {
                    levelData = level.getLevelData();
                    if (!(levelData instanceof ServerLevelData)) continue;
                    serverLevelData = (ServerLevelData)levelData;
                    serverLevelData.setGameTime(serverLevelData.getGameTime() + (long)this.currentTick - (long)lastActualTick);
                }
                lastActualTick = this.currentTick;
            }
            if (blockOverrideKeyframes != null) {
                this.applyBlockOverrideKeyframes(blockOverrideKeyframes, this.currentTick);
            }
        }
        finally {
            if (stamp != 0L) {
                editorState.release(stamp);
            }
        }
    }

    private void playSnapshot(ReplayReader replayReader) {
        this.processedSnapshot = true;
        this.clearDataForPlayingSnapshot();
        replayReader.handleSnapshot(this);
        this.gamePacketHandler.flushPendingEntities();
        this.updateRtc();
        replayReader.resetToStart();
    }

    private void applyBlockOverrideKeyframes(Map<Integer, Keyframe> blockOverrideKeyframes, int tick) {
        BlockOverrideKeyframe keyframe;
        ServerLevel level = this.gamePacketHandler.level();
        if (level != null && (keyframe = (BlockOverrideKeyframe)blockOverrideKeyframes.get(tick)) != null) {
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            BlockState emptyState = BlockOverrideKeyframe.EMPTY_STATE;
            for (Long2ObjectMap.Entry chunkEntry : keyframe.blocks.long2ObjectEntrySet()) {
                long chunkPos = chunkEntry.getLongKey();
                int chunkX = BlockPos.getX((long)chunkPos);
                int chunkY = BlockPos.getY((long)chunkPos);
                int chunkZ = BlockPos.getZ((long)chunkPos);
                if (chunkY < level.getMinSectionY() || chunkY > level.getMaxSectionY()) continue;
                PalettedContainer container = (PalettedContainer)chunkEntry.getValue();
                LevelChunk levelChunk = level.getChunk(chunkX, chunkZ);
                for (int x = 0; x < 16; ++x) {
                    for (int y = 0; y < 16; ++y) {
                        for (int z = 0; z < 16; ++z) {
                            BlockState blockState = (BlockState)container.get(x, y, z);
                            if (blockState == emptyState) continue;
                            mutableBlockPos.set((chunkX << 4) + x, (chunkY << 4) + y, (chunkZ << 4) + z);
                            BlockState old = ((LevelChunkExt)levelChunk).flashback$setBlockStateWithoutUpdates((BlockPos)mutableBlockPos, blockState);
                            if (old == null) continue;
                            level.sendBlockUpdated((BlockPos)mutableBlockPos, old, blockState, 3);
                        }
                    }
                }
            }
        }
    }

    private void clearDataForPlayingSnapshot() {
        for (ReplayPlayer replayViewer : this.replayViewers) {
            for (UUID uuid : this.bossEvents.keySet()) {
                replayViewer.connection.send((Packet)ClientboundBossEventPacket.createRemovePacket((UUID)uuid));
            }
        }
        this.bossEvents.clear();
    }

    public void blockChangeOccurred(BlockPos blockPos, BlockState result) {
        if (this.replayPaused && this.hasNonSpectatorReplayViewer) {
            this.pendingBlockOverrides.add(new BlockAtPosition(blockPos.asLong(), result));
        }
    }

    private void tryFollowLocalPlayer() {
        if (this.spawnLevel == null) {
            return;
        }
        ServerLevel currentLevel = this.getLevel(this.spawnLevel);
        if (currentLevel == null) {
            return;
        }
        Entity follow = currentLevel.getEntity(this.gamePacketHandler.localPlayerId);
        if (follow == null || follow.getX() == 0.0 && follow.getY() == 0.0 && follow.getZ() == 0.0) {
            return;
        }
        for (ReplayPlayer replayViewer : this.getReplayViewers()) {
            boolean shouldFollow = replayViewer.followLocalPlayerNextTick;
            if (this.followLocalPlayerNextTickIfWrongDimension) {
                shouldFollow |= replayViewer.level() != currentLevel;
            }
            if (!shouldFollow) continue;
            replayViewer.followLocalPlayerNextTick = false;
            replayViewer.teleportTo(currentLevel, follow.getX(), follow.getY(), follow.getZ(), Set.of(), follow.getYRot(), follow.getXRot(), false);
        }
        this.followLocalPlayerNextTickIfWrongDimension = false;
    }

    public boolean haveTime() {
        return super.haveTime() && this.jumpToTick == -1;
    }

    protected void waitForTasks() {
        if (this.jumpToTick != -1 || Flashback.EXPORT_JOB != null) {
            LockSupport.parkNanos("waiting for tasks", 100000L);
        } else {
            super.waitForTasks();
        }
    }

    private void stopWithReason(Component reason) {
        this.shutdownReason = reason;
        this.halt(false);
    }

    public boolean saveEverything(boolean bl, boolean bl2, boolean bl3) {
        return false;
    }

    public boolean saveAllChunks(boolean bl, boolean bl2, boolean bl3) {
        return false;
    }

    public void stopServer() {
        for (ServerLevel level : this.levels.values()) {
            if (level == null) continue;
            try {
                level.close();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.levels.clear();
        super.stopServer();
        TempFolderProvider.deleteTemp(TempFolderProvider.TempFolderType.SERVER, this.playbackUUID);
        if (this.playbackFileSystem != null) {
            try {
                this.playbackFileSystem.close();
            }
            catch (IOException e) {
                Flashback.LOGGER.error("Failed to close playback zip", (Throwable)e);
            }
        }
        this.replayChunkCache.clear();
        this.playableChunksByStart.clear();
    }

    public void clearReplayTempFolder() {
        Path temp = TempFolderProvider.createTemp(TempFolderProvider.TempFolderType.SERVER, this.playbackUUID);
        try {
            Files.walkFileTree(temp, (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(this){
                {
                    Objects.requireNonNull(this$0);
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.getFileName().toString().equals("DistantHorizons.sqlite")) {
                        return FileVisitResult.CONTINUE;
                    }
                    if (!Files.isDirectory(file, new LinkOption[0])) {
                        Files.deleteIfExists(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        catch (IOException e) {
            Flashback.LOGGER.error("Unable to delete replay temp folder", (Throwable)e);
        }
    }

    public boolean isSingleplayerOwner(NameAndId nameAndId) {
        return nameAndId.name().equals(REPLAY_VIEWER_NAME);
    }

    private record RemotePack(UUID id, String url, String hash) {
    }

    private record BlockAtPosition(long pos, BlockState blockState) {
    }
}

