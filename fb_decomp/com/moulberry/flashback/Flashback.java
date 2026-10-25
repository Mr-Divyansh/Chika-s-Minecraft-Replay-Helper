/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.blaze3d.platform.InputConstants
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.realmsclient.RealmsMainScreen
 *  com.mojang.serialization.Lifecycle
 *  com.moulberry.lattice.Lattice
 *  com.moulberry.lattice.element.LatticeElements
 *  com.seibel.distanthorizons.api.DhApi
 *  io.netty.buffer.Unpooled
 *  net.fabricmc.api.ClientModInitializer
 *  net.fabricmc.api.ModInitializer
 *  net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
 *  net.fabricmc.fabric.api.client.command.v2.ClientCommands
 *  net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
 *  net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
 *  net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.KeyMapping$Category
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.components.debug.DebugScreenDisplayer
 *  net.minecraft.client.gui.components.debug.DebugScreenEntries
 *  net.minecraft.client.gui.components.debug.DebugScreenEntry
 *  net.minecraft.client.gui.components.debug.DebugScreenEntryStatus
 *  net.minecraft.client.gui.components.debug.DebugScreenProfile
 *  net.minecraft.client.gui.components.toasts.SystemToast
 *  net.minecraft.client.gui.components.toasts.SystemToast$SystemToastId
 *  net.minecraft.client.gui.components.toasts.ToastManager
 *  net.minecraft.client.gui.screens.AlertScreen
 *  net.minecraft.client.gui.screens.LevelLoadingScreen
 *  net.minecraft.client.gui.screens.PauseScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.TitleScreen
 *  net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.client.server.IntegratedServer
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.Commands$CommandSelection
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.MappedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.PacketListener
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.common.ClientCommonPacketListener
 *  net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket
 *  net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.WorldLoader
 *  net.minecraft.server.WorldLoader$DataLoadOutput
 *  net.minecraft.server.WorldLoader$InitConfig
 *  net.minecraft.server.WorldLoader$PackConfig
 *  net.minecraft.server.WorldStem
 *  net.minecraft.server.packs.repository.PackRepository
 *  net.minecraft.server.packs.repository.ServerPacksSource
 *  net.minecraft.server.permissions.PermissionSet
 *  net.minecraft.util.FileUtil
 *  net.minecraft.util.Util
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.InterpolationHandler
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.flag.FeatureFlagSet
 *  net.minecraft.world.flag.FeatureFlags
 *  net.minecraft.world.level.DataPackConfig
 *  net.minecraft.world.level.GameType
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelSettings
 *  net.minecraft.world.level.LevelSettings$DifficultySettings
 *  net.minecraft.world.level.WorldDataConfiguration
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.biome.Biomes
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.dimension.BuiltinDimensionTypes
 *  net.minecraft.world.level.dimension.LevelStem
 *  net.minecraft.world.level.gamerules.GameRules
 *  net.minecraft.world.level.levelgen.WorldDimensions
 *  net.minecraft.world.level.levelgen.WorldDimensions$Complete
 *  net.minecraft.world.level.levelgen.WorldGenSettings
 *  net.minecraft.world.level.levelgen.WorldOptions
 *  net.minecraft.world.level.storage.LevelDataAndDimensions$WorldDataAndGenSettings
 *  net.minecraft.world.level.storage.LevelStorageSource
 *  net.minecraft.world.level.storage.LevelStorageSource$LevelStorageAccess
 *  net.minecraft.world.level.storage.PrimaryLevelData
 *  net.minecraft.world.level.storage.WorldData
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.jetbrains.annotations.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.moulberry.flashback;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.serialization.Lifecycle;
import com.moulberry.flashback.FlashbackGson;
import com.moulberry.flashback.FlashbackSystemToasts;
import com.moulberry.flashback.FlashbackTextComponents;
import com.moulberry.flashback.TempFolderProvider;
import com.moulberry.flashback.action.ActionAccuratePlayerPosition;
import com.moulberry.flashback.action.ActionConfigurationPacket;
import com.moulberry.flashback.action.ActionCreateLocalPlayer;
import com.moulberry.flashback.action.ActionGamePacket;
import com.moulberry.flashback.action.ActionLevelChunkCached;
import com.moulberry.flashback.action.ActionMoveEntities;
import com.moulberry.flashback.action.ActionNextTick;
import com.moulberry.flashback.action.ActionRealTimeClock;
import com.moulberry.flashback.action.ActionRegistry;
import com.moulberry.flashback.combo_options.MarkerColour;
import com.moulberry.flashback.command.BetterColorArgument;
import com.moulberry.flashback.compat.DistantHorizonsSupport;
import com.moulberry.flashback.compat.simple_voice_chat.SimpleVoiceChatPlayback;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.editor.keybinds.Keybinds;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.exporting.AsyncFileDialogs;
import com.moulberry.flashback.exporting.ExportJob;
import com.moulberry.flashback.exporting.taskbar.TaskbarManager;
import com.moulberry.flashback.ext.MinecraftExt;
import com.moulberry.flashback.keyframe.KeyframeRegistry;
import com.moulberry.flashback.keyframe.types.AudioKeyframeType;
import com.moulberry.flashback.keyframe.types.BlockOverrideKeyframeType;
import com.moulberry.flashback.keyframe.types.CameraKeyframeType;
import com.moulberry.flashback.keyframe.types.CameraOrbitKeyframeType;
import com.moulberry.flashback.keyframe.types.CameraShakeKeyframeType;
import com.moulberry.flashback.keyframe.types.FOVKeyframeType;
import com.moulberry.flashback.keyframe.types.FreezeKeyframeType;
import com.moulberry.flashback.keyframe.types.SpeedKeyframeType;
import com.moulberry.flashback.keyframe.types.TimeOfDayKeyframeType;
import com.moulberry.flashback.keyframe.types.TimelapseKeyframeType;
import com.moulberry.flashback.keyframe.types.TrackEntityKeyframeType;
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
import com.moulberry.flashback.packet.FlashbackVoiceChatSound;
import com.moulberry.flashback.playback.EmptyLevelSource;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.record.FlashbackMeta;
import com.moulberry.flashback.record.Recorder;
import com.moulberry.flashback.record.ReplayExporter;
import com.moulberry.flashback.record.ReplayMarker;
import com.moulberry.flashback.screen.RecoverRecordingsScreen;
import com.moulberry.flashback.screen.SaveReplayScreen;
import com.moulberry.flashback.screen.UnsupportedLoaderScreen;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import com.moulberry.flashback.visuals.AccurateEntityPositionHandler;
import com.moulberry.lattice.Lattice;
import com.moulberry.lattice.element.LatticeElements;
import com.seibel.distanthorizons.api.DhApi;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.client.gui.components.debug.DebugScreenEntryStatus;
import net.minecraft.client.gui.components.debug.DebugScreenProfile;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.PacketListener;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.ClientCommonPacketListener;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener;
import net.minecraft.resources.Identifier;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.util.FileUtil;
import net.minecraft.util.Util;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.LevelDataAndDimensions;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Flashback
implements ModInitializer,
ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger((String)"flashback");
    public static final int MAGIC = -679417724;
    public static volatile Recorder RECORDER = null;
    public static ExportJob EXPORT_JOB = null;
    private static FlashbackConfigV1 config;
    public static LatticeElements configElements;
    private static Path configDirectory;
    private static int delayedStartRecording;
    private static boolean delayedOpenConfig;
    private static volatile boolean isInReplay;
    public static boolean supportsDistantHorizons;
    public static boolean isBobbyLoaded;
    private static final List<Path> pendingReplaySave;
    private static final List<Path> pendingReplayRecovery;
    private static List<String> pendingUnsupportedModsForRecording;
    private static boolean isOpeningReplay;
    public static long worldBorderLerpStartTime;
    private static final KeyMapping.Category category;
    public static final KeyMapping createMarker1KeyBind;
    public static final KeyMapping createMarker2KeyBind;
    public static final KeyMapping createMarker3KeyBind;
    public static final KeyMapping createMarker4KeyBind;
    public static final Identifier RECORDING_INFO_DEBUG_SCREEN_ID;

    public static Identifier createIdentifier(String value) {
        return Identifier.fromNamespaceAndPath((String)"flashback", (String)value);
    }

    public static Path getDataDirectory() {
        return FabricLoader.getInstance().getGameDir().resolve("flashback");
    }

    public static Path getReplayFolder() {
        return Flashback.getDataDirectory().resolve("replays");
    }

    public static Path getConfigDirectory() {
        if (configDirectory == null) {
            configDirectory = FabricLoader.getInstance().getConfigDir().resolve("flashback");
            try {
                Files.createDirectories(configDirectory, new FileAttribute[0]);
            }
            catch (Exception e) {
                LOGGER.error("Unable to create directories for config folder", (Throwable)e);
            }
        }
        return configDirectory;
    }

    public void onInitialize() {
        PayloadTypeRegistry.clientboundPlay().register(FinishedServerTick.TYPE, StreamCodec.unit((Object)FinishedServerTick.INSTANCE));
        PayloadTypeRegistry.clientboundPlay().register(FlashbackForceClientTick.TYPE, StreamCodec.unit((Object)FlashbackForceClientTick.INSTANCE));
        PayloadTypeRegistry.clientboundPlay().register(FlashbackClearParticles.TYPE, StreamCodec.unit((Object)FlashbackClearParticles.INSTANCE));
        PayloadTypeRegistry.clientboundPlay().register(FlashbackClearEntities.TYPE, StreamCodec.unit((Object)FlashbackClearEntities.INSTANCE));
        PayloadTypeRegistry.clientboundPlay().register(FlashbackInstantlyLerp.TYPE, StreamCodec.unit((Object)FlashbackInstantlyLerp.INSTANCE));
        PayloadTypeRegistry.clientboundPlay().register(FlashbackRemoteSelectHotbarSlot.TYPE, FlashbackRemoteSelectHotbarSlot.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(FlashbackRemoteExperience.TYPE, FlashbackRemoteExperience.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(FlashbackRemoteFoodData.TYPE, FlashbackRemoteFoodData.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(FlashbackRemoteSetSlot.TYPE, FlashbackRemoteSetSlot.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(FlashbackVoiceChatSound.TYPE, FlashbackVoiceChatSound.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(FlashbackAccurateEntityPosition.TYPE, FlashbackAccurateEntityPosition.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(FlashbackSetBorderLerpStartTime.TYPE, FlashbackSetBorderLerpStartTime.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(FlashbackRawCustomPayload.TYPE, FlashbackRawCustomPayload.STREAM_CODEC);
    }

    public void onInitializeClient() {
        Path configFolder = FabricLoader.getInstance().getConfigDir().resolve("flashback");
        try {
            Files.createDirectories(configFolder, new FileAttribute[0]);
        }
        catch (IOException e) {
            LOGGER.error("Failed to create config folder", (Throwable)e);
        }
        config = FlashbackConfigV1.tryLoadFromFolder(configFolder);
        configElements = LatticeElements.fromAnnotations((Component)FlashbackTextComponents.FLASHBACK_OPTIONS, (Object)config);
        if (Flashback.config.exporting.useSystemFFmpeg) {
            System.setProperty("org.bytedeco.javacpp.pathsfirst", "true");
        }
        if (Flashback.config.internal.nfdUsePortal) {
            System.setProperty("org.lwjgl.nfd.linux.portal", "true");
        }
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            Minecraft.getInstance().schedule(() -> Lattice.performTest((LatticeElements)configElements));
        }
        Keybinds.load(config);
        TempFolderProvider.tryDeleteStaleFolders(TempFolderProvider.TempFolderType.SERVER);
        Path recordingFolder = TempFolderProvider.getTypedTempFolder(TempFolderProvider.TempFolderType.RECORDING);
        if (Files.exists(recordingFolder, new LinkOption[0])) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(recordingFolder);){
                for (Path path : directoryStream) {
                    if (!Files.exists(path.resolve("metadata.json"), new LinkOption[0])) continue;
                    pendingReplayRecovery.add(path);
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (pendingReplayRecovery.isEmpty()) {
            TempFolderProvider.tryDeleteStaleFolders(TempFolderProvider.TempFolderType.RECORDING);
        }
        try {
            FileUtils.deleteDirectory((File)Path.of("replay_export_temp", new String[0]).toFile());
        }
        catch (Exception e) {
            // empty catch block
        }
        this.deleteUnusedReplayStates();
        ActionRegistry.register(ActionNextTick.INSTANCE);
        ActionRegistry.register(ActionGamePacket.INSTANCE);
        ActionRegistry.register(ActionConfigurationPacket.INSTANCE);
        ActionRegistry.register(ActionCreateLocalPlayer.INSTANCE);
        ActionRegistry.register(ActionMoveEntities.INSTANCE);
        ActionRegistry.register(ActionLevelChunkCached.INSTANCE);
        ActionRegistry.register(ActionAccuratePlayerPosition.INSTANCE);
        ActionRegistry.register(ActionRealTimeClock.INSTANCE);
        KeyframeRegistry.register(CameraKeyframeType.INSTANCE);
        KeyframeRegistry.register(CameraOrbitKeyframeType.INSTANCE);
        KeyframeRegistry.register(TrackEntityKeyframeType.INSTANCE);
        KeyframeRegistry.register(CameraShakeKeyframeType.INSTANCE);
        KeyframeRegistry.register(FOVKeyframeType.INSTANCE);
        KeyframeRegistry.register(SpeedKeyframeType.INSTANCE);
        KeyframeRegistry.register(TimelapseKeyframeType.INSTANCE);
        KeyframeRegistry.register(TimeOfDayKeyframeType.INSTANCE);
        KeyframeRegistry.register(FreezeKeyframeType.INSTANCE);
        KeyframeRegistry.register(BlockOverrideKeyframeType.INSTANCE);
        KeyframeRegistry.register(AudioKeyframeType.INSTANCE);
        ClientPlayNetworking.registerGlobalReceiver(FlashbackForceClientTick.TYPE, (payload, context) -> {
            if (Flashback.isInReplay()) {
                Minecraft.getInstance().tick();
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(FlashbackClearParticles.TYPE, (payload, context) -> {
            if (Flashback.isInReplay()) {
                Minecraft.getInstance().particleEngine.clearParticles();
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(FlashbackClearEntities.TYPE, (payload, context) -> {
            if (Flashback.isInReplay()) {
                for (Entity entity : Minecraft.getInstance().level.entitiesForRendering()) {
                    if (entity == null || entity instanceof Player) continue;
                    entity.discard();
                }
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(FlashbackInstantlyLerp.TYPE, (payload, context) -> {
            if (Flashback.isInReplay()) {
                for (Entity entity : Minecraft.getInstance().level.entitiesForRendering()) {
                    if (entity.isInterpolating()) {
                        InterpolationHandler interpolation = entity.getInterpolation();
                        entity.snapTo(interpolation.position(), interpolation.yRot(), interpolation.xRot());
                        interpolation.cancel();
                        continue;
                    }
                    entity.setOldPosAndRot();
                }
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(FlashbackRemoteSelectHotbarSlot.TYPE, (payload, context) -> {
            Entity entity;
            if (Flashback.isInReplay() && (entity = Minecraft.getInstance().level.getEntity(payload.entityId())) instanceof Player) {
                Player player = (Player)entity;
                player.getInventory().setSelectedSlot(payload.slot());
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(FlashbackRemoteExperience.TYPE, (payload, context) -> {
            Entity entity;
            if (Flashback.isInReplay() && (entity = Minecraft.getInstance().level.getEntity(payload.entityId())) instanceof Player) {
                Player player = (Player)entity;
                player.experienceProgress = payload.experienceProgress();
                player.totalExperience = payload.totalExperience();
                player.experienceLevel = payload.experienceLevel();
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(FlashbackRemoteFoodData.TYPE, (payload, context) -> {
            Entity entity;
            if (Flashback.isInReplay() && (entity = Minecraft.getInstance().level.getEntity(payload.entityId())) instanceof Player) {
                Player player = (Player)entity;
                player.getFoodData().setFoodLevel(payload.foodLevel());
                player.getFoodData().setSaturation(payload.saturationLevel());
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(FlashbackRemoteSetSlot.TYPE, (payload, context) -> {
            Entity entity;
            if (Flashback.isInReplay() && (entity = Minecraft.getInstance().level.getEntity(payload.entityId())) instanceof Player) {
                Player player = (Player)entity;
                player.getInventory().setItem(payload.slot(), payload.itemStack());
            }
        });
        if (FabricLoader.getInstance().isModLoaded("voicechat")) {
            ClientPlayNetworking.registerGlobalReceiver(FlashbackVoiceChatSound.TYPE, (payload, context) -> {
                if (Flashback.isInReplay()) {
                    SimpleVoiceChatPlayback.play(payload);
                }
            });
        }
        ClientPlayNetworking.registerGlobalReceiver(FlashbackAccurateEntityPosition.TYPE, (payload, context) -> {
            if (Flashback.isInReplay()) {
                AccurateEntityPositionHandler.update(payload);
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(FlashbackSetBorderLerpStartTime.TYPE, (payload, context) -> {
            if (Flashback.isInReplay()) {
                worldBorderLerpStartTime = payload.time();
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(FlashbackRawCustomPayload.TYPE, (payload, context) -> {
            if (Flashback.isInReplay()) {
                ClientPacketListener connection = context.client().getConnection();
                if (connection == null) {
                    return;
                }
                PacketListener patt0$temp = connection.getConnection().getPacketListener();
                if (patt0$temp instanceof ClientCommonPacketListener) {
                    ClientCommonPacketListener listener = (ClientCommonPacketListener)patt0$temp;
                    RegistryFriendlyByteBuf buffer = new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer((byte[])payload.packetBytes()), (RegistryAccess)connection.registryAccess());
                    if (payload.configPhase()) {
                        if (listener instanceof ClientConfigurationPacketListener) {
                            ClientboundCustomPayloadPacket customPayloadPacket = (ClientboundCustomPayloadPacket)ClientboundCustomPayloadPacket.CONFIG_STREAM_CODEC.decode((Object)buffer);
                            customPayloadPacket.handle(listener);
                        }
                    } else if (listener instanceof ClientPacketListener) {
                        ClientboundCustomPayloadPacket customPayloadPacket = (ClientboundCustomPayloadPacket)ClientboundCustomPayloadPacket.GAMEPLAY_STREAM_CODEC.decode((Object)buffer);
                        customPayloadPacket.handle(listener);
                    }
                }
            }
        });
        DebugScreenEntries.register((Identifier)RECORDING_INFO_DEBUG_SCREEN_ID, (DebugScreenEntry)new DebugScreenEntry(this){
            {
                Objects.requireNonNull(this$0);
            }

            public void display(DebugScreenDisplayer debugScreenDisplayer, @Nullable Level level, @Nullable LevelChunk levelChunk, @Nullable LevelChunk levelChunk2) {
                if (RECORDER != null) {
                    debugScreenDisplayer.addToGroup(RECORDING_INFO_DEBUG_SCREEN_ID, RECORDER.getDebugString());
                }
            }
        });
        LinkedHashMap newProfiles = new LinkedHashMap();
        for (Map.Entry entry : DebugScreenEntries.PROFILES.entrySet()) {
            LinkedHashMap<Identifier, DebugScreenEntryStatus> newMap = new LinkedHashMap<Identifier, DebugScreenEntryStatus>((Map)entry.getValue());
            newMap.put(RECORDING_INFO_DEBUG_SCREEN_ID, DebugScreenEntryStatus.IN_OVERLAY);
            newProfiles.put((DebugScreenProfile)entry.getKey(), Collections.unmodifiableMap(newMap));
        }
        DebugScreenEntries.PROFILES = Collections.unmodifiableMap(newProfiles);
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            LiteralArgumentBuilder flashback = ClientCommands.literal((String)"flashback");
            flashback.then(ClientCommands.literal((String)"start").executes(this::startRecordingReplay));
            flashback.then(ClientCommands.literal((String)"finish").executes(this::finishRecordingReplay));
            flashback.then(ClientCommands.literal((String)"end").executes(this::finishRecordingReplay));
            flashback.then(ClientCommands.literal((String)"pause").executes(ctx -> {
                Flashback.pauseRecordingReplay(true);
                return 0;
            }));
            flashback.then(ClientCommands.literal((String)"unpause").executes(ctx -> {
                Flashback.pauseRecordingReplay(false);
                return 0;
            }));
            flashback.then(ClientCommands.literal((String)"config").executes(this::openFlashbackConfig));
            flashback.then(((LiteralArgumentBuilder)ClientCommands.literal((String)"mark").executes(command -> {
                this.addMarker(null, null, null);
                return 0;
            })).then(((RequiredArgumentBuilder)ClientCommands.argument((String)"color", (ArgumentType)BetterColorArgument.color()).executes(command -> {
                int colour = (Integer)command.getArgument("color", Integer.class);
                this.addMarker(colour, null, null);
                return 0;
            })).then(((RequiredArgumentBuilder)ClientCommands.argument((String)"savePosition", (ArgumentType)BoolArgumentType.bool()).executes(command -> {
                int colour = (Integer)command.getArgument("color", Integer.class);
                boolean savePosition = (Boolean)command.getArgument("savePosition", Boolean.class);
                this.addMarker(colour, savePosition, null);
                return 0;
            })).then(ClientCommands.argument((String)"description", (ArgumentType)StringArgumentType.greedyString()).executes(command -> {
                int colour = (Integer)command.getArgument("color", Integer.class);
                boolean savePosition = (Boolean)command.getArgument("savePosition", Boolean.class);
                String description = (String)command.getArgument("description", String.class);
                this.addMarker(colour, savePosition, description);
                return 0;
            })))));
            dispatcher.register(flashback);
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            if (!Flashback.isInReplay() && !isOpeningReplay) {
                return;
            }
            String hideName = "hide";
            if (dispatcher.findNode(Collections.singleton("hide")) != null) {
                hideName = "hide_flashback";
            }
            LiteralArgumentBuilder hideEntity = (LiteralArgumentBuilder)Commands.literal((String)hideName).then(Commands.argument((String)"targets", (ArgumentType)EntityArgument.entities()).executes(command -> {
                EditorState editorState = EditorStateManager.getCurrent();
                if (!Flashback.isInReplay() || editorState == null) {
                    ((CommandSourceStack)command.getSource()).sendFailure((Component)Component.translatable((String)"flashback.command_only_inside_replay", (Object[])new Object[]{Component.literal((String)"hide")}));
                    return 0;
                }
                Collection entities = EntityArgument.getEntities((CommandContext)command, (String)"targets");
                for (Entity entity : entities) {
                    editorState.hideDuringExport.add(entity.getUUID());
                }
                int count = entities.size();
                ((CommandSourceStack)command.getSource()).sendSuccess(() -> Component.translatable((String)"flashback.hide_command.n_entities_hidden", (Object[])new Object[]{Component.literal((String)String.valueOf(count))}), false);
                return 0;
            }));
            dispatcher.register(hideEntity);
            String showName = "show";
            if (dispatcher.findNode(Collections.singleton("show")) != null) {
                showName = "show_flashback";
            }
            LiteralArgumentBuilder showEntity = (LiteralArgumentBuilder)Commands.literal((String)showName).then(Commands.argument((String)"targets", (ArgumentType)EntityArgument.entities()).executes(command -> {
                EditorState editorState = EditorStateManager.getCurrent();
                if (!Flashback.isInReplay() || editorState == null) {
                    ((CommandSourceStack)command.getSource()).sendFailure((Component)Component.translatable((String)"flashback.command_only_inside_replay", (Object[])new Object[]{Component.literal((String)"show")}));
                    return 0;
                }
                Collection entities = EntityArgument.getEntities((CommandContext)command, (String)"targets");
                for (Entity entity : entities) {
                    editorState.hideDuringExport.remove(entity.getUUID());
                }
                int count = entities.size();
                ((CommandSourceStack)command.getSource()).sendSuccess(() -> Component.translatable((String)"flashback.show_command.n_entities_shown", (Object[])new Object[]{Component.literal((String)String.valueOf(count))}), false);
                return 0;
            }));
            dispatcher.register(showEntity);
        });
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!Flashback.isInReplay() && Flashback.getConfig().recordingControls.automaticallyStart && RECORDER == null) {
                delayedStartRecording = 20;
            }
            if (FabricLoader.getInstance().isModLoaded("voicechat")) {
                SimpleVoiceChatPlayback.cleanUp();
            }
        });
        AtomicReference<String> unsupportedLoader = new AtomicReference<String>(Flashback.findUnsupportedLoaders());
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        AtomicBoolean synchronizeTickingCanTickServer = new AtomicBoolean(true);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            Entity other;
            Flashback.updateIsInReplay();
            AccurateEntityPositionHandler.tick();
            Entity camera = Minecraft.getInstance().getCameraEntity();
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && camera != null && camera != player && camera.isRemoved() && (other = player.level().getEntity(camera.getId())) != null && !other.isRemoved()) {
                Minecraft.getInstance().setCameraEntity(other);
            }
            Flashback.getConfig().tickDelayedSave();
            synchronizeTickingCanTickServer.set(true);
        });
        HudElementRegistry.addLast((Identifier)Identifier.fromNamespaceAndPath((String)"flashback", (String)"hud"), (graphics, delta) -> {
            long millis;
            ReplayServer replayServer = Flashback.getReplayServer();
            if (replayServer == null) {
                return;
            }
            if (Flashback.config.overlay.rtcOverlay && (millis = replayServer.getInterpolatedRtc()) != 0L) {
                ZonedDateTime dateTime = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault());
                String dateString = DateTimeFormatter.ofPattern("dd/MMM/uuuu HH:mm:ss.SSS zzz").format(dateTime);
                Font font = Minecraft.getInstance().font;
                int dateWidth = font.width(dateString);
                int outerPadding = 2;
                int innerPadding = 2;
                int dateLeft = graphics.guiWidth() - 2 - 2 - dateWidth;
                int fillLeft = dateLeft - 2;
                int textTop = 4;
                int n = graphics.guiWidth() - 2;
                Objects.requireNonNull(font);
                graphics.fill(fillLeft, 2, n, 4 + 9 - 1 + 2, Integer.MIN_VALUE);
                graphics.text(font, dateString, dateLeft, textTop, -1, true);
            }
        });
        ClientTickEvents.START_CLIENT_TICK.register(minecraft -> {
            if (RECORDER != null && Flashback.config.advanced.synchronizeTicking && minecraft.hasSingleplayerServer()) {
                boolean willRecord;
                boolean isLevelLoaded = !(minecraft.screen instanceof LevelLoadingScreen);
                boolean bl = willRecord = minecraft.level != null && (minecraft.getOverlay() == null || !minecraft.getOverlay().isPauseScreen()) && !minecraft.isPaused() && !RECORDER.isPaused() && isLevelLoaded;
                while (willRecord && !synchronizeTickingCanTickClient.compareAndSet(true, false)) {
                    LockSupport.parkNanos("flashback synchronized ticking: waiting for server", 100000L);
                }
            }
            if (Flashback.canReplaceScreen(minecraft.screen)) {
                Flashback.openNewScreen(unsupportedLoader, minecraft.screen);
            }
            if (minecraft.level != null && delayedStartRecording > 0) {
                IntegratedServer integratedServer = minecraft.getSingleplayerServer();
                if (integratedServer != null && integratedServer.getClass() != IntegratedServer.class) {
                    delayedStartRecording = 0;
                } else if (Flashback.getConfig().recordingControls.automaticallyStart && RECORDER == null) {
                    if (--delayedStartRecording == 0) {
                        Flashback.startRecordingReplay();
                    }
                } else {
                    delayedStartRecording = 0;
                }
            }
            Flashback.updateIsInReplay();
            if (RECORDER != null) {
                RECORDER.startTick();
            }
            if (createMarker1KeyBind.consumeClick()) {
                this.addMarker(Flashback.config.marker.markerOptions1);
            }
            if (createMarker2KeyBind.consumeClick()) {
                this.addMarker(Flashback.config.marker.markerOptions2);
            }
            if (createMarker3KeyBind.consumeClick()) {
                this.addMarker(Flashback.config.marker.markerOptions3);
            }
            if (createMarker4KeyBind.consumeClick()) {
                this.addMarker(Flashback.config.marker.markerOptions4);
            }
        });
        ServerTickEvents.END_SERVER_TICK.register(minecraftServer -> synchronizeTickingCanTickClient.set(true));
        ServerTickEvents.START_SERVER_TICK.register(minecraftServer -> {
            if (RECORDER != null && Flashback.config.advanced.synchronizeTicking) {
                while (!synchronizeTickingCanTickServer.compareAndSet(true, false)) {
                    LockSupport.parkNanos("flashback synchronized ticking: waiting for client", 100000L);
                }
            }
        });
        if (FabricLoader.getInstance().isModLoaded("distanthorizons")) {
            if (DhApi.getApiMajorVersion() >= 4) {
                LOGGER.info("DistantHorizons detected. Enabling Flashback+DistantHorizons integration");
                supportsDistantHorizons = true;
                DistantHorizonsSupport.register();
            } else {
                LOGGER.error("DistantHorizons is installed, but API version is too low ({}). Disabling integration.", (Object)DhApi.getApiMajorVersion());
            }
        }
        if (FabricLoader.getInstance().isModLoaded("bobby")) {
            isBobbyLoaded = true;
        }
    }

    private static void openNewScreen(AtomicReference<String> unsupportedLoader, Screen currentScreen) {
        if (unsupportedLoader.get() != null) {
            String loaderName = unsupportedLoader.get();
            unsupportedLoader.set(null);
            if (System.currentTimeMillis() > Flashback.getConfig().internal.nextUnsupportedModLoaderWarning) {
                MutableComponent warning = Component.translatable((String)"flashback.unsupported_loader.message", (Object[])new Object[]{Component.literal((String)loaderName)});
                Minecraft.getInstance().setScreen((Screen)new UnsupportedLoaderScreen(currentScreen, (Component)Component.translatable((String)"flashback.screen_unsupported"), (Component)warning));
                return;
            }
        }
        if (!pendingReplayRecovery.isEmpty()) {
            Component nl = FlashbackTextComponents.NEWLINE;
            MutableComponent title = Component.translatable((String)"flashback.screen_recovery");
            MutableComponent description = Component.empty().append((Component)Component.translatable((String)"flashback.recovery1", (Object[])new Object[]{Component.translatable((String)"flashback.recovery2").withStyle(ChatFormatting.YELLOW)})).append(nl).append((Component)Component.translatable((String)"flashback.recovery3")).append(nl).append(nl).append((Component)Component.translatable((String)"flashback.recovery4").withStyle(ChatFormatting.RED)).append(nl).append(nl).append((Component)Component.translatable((String)"flashback.recovery5").withStyle(ChatFormatting.GREEN));
            Minecraft.getInstance().setScreen((Screen)new RecoverRecordingsScreen(currentScreen, (Component)title, (Component)description, recover -> {
                switch (recover) {
                    case RECOVER: {
                        pendingReplaySave.addAll(pendingReplayRecovery);
                        pendingReplayRecovery.clear();
                        break;
                    }
                    case SKIP: {
                        pendingReplayRecovery.clear();
                        break;
                    }
                    case DELETE: {
                        TempFolderProvider.tryDeleteStaleFolders(TempFolderProvider.TempFolderType.RECORDING);
                        pendingReplayRecovery.clear();
                    }
                }
            }));
            return;
        }
        if (!pendingReplaySave.isEmpty()) {
            Path recordFolder = pendingReplaySave.getFirst();
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.withNano(0);
            Minecraft.getInstance().setScreen((Screen)new SaveReplayScreen(currentScreen, recordFolder, dateTime.toString()));
            return;
        }
        if (pendingUnsupportedModsForRecording != null) {
            String mods = StringUtils.join(pendingUnsupportedModsForRecording, (String)", ");
            MutableComponent title = Component.translatable((String)"flashback.incompatible_with_recording");
            MutableComponent description = Component.translatable((String)"flashback.incompatible_with_recording_description").append((Component)Component.literal((String)mods).withStyle(ChatFormatting.RED));
            Minecraft.getInstance().setScreen((Screen)new AlertScreen(() -> Minecraft.getInstance().setScreen(currentScreen), (Component)title, (Component)description));
            pendingUnsupportedModsForRecording = null;
            return;
        }
        if (delayedOpenConfig) {
            Flashback.openConfigScreen(currentScreen);
            delayedOpenConfig = false;
            return;
        }
    }

    public static Screen createConfigScreen(Screen oldScreen) {
        return Lattice.createConfigScreen((LatticeElements)configElements, () -> {
            config.saveToDefaultFolder();
            Minecraft.getInstance().options.save();
        }, (Screen)oldScreen);
    }

    public static void openConfigScreen(Screen oldScreen) {
        Minecraft.getInstance().setScreen(Flashback.createConfigScreen(oldScreen));
    }

    public static List<String> getReplayIncompatibleMods() {
        ArrayList<String> incompatible = new ArrayList<String>();
        return incompatible;
    }

    public static List<String> getRecordingIncompatibleMods() {
        ArrayList<String> incompatible = new ArrayList<String>();
        if (FabricLoader.getInstance().isModLoaded("farsight")) {
            incompatible.add("Farsight");
        }
        if (incompatible.isEmpty()) {
            return null;
        }
        return incompatible;
    }

    @Nullable
    private static String findUnsupportedLoaders() {
        if (FabricLoader.getInstance().isModLoaded("feather")) {
            return "Feather Client";
        }
        return null;
    }

    private static boolean canReplaceScreen(Screen screen) {
        return screen == null || screen instanceof PauseScreen || screen instanceof TitleScreen || screen instanceof RealmsMainScreen || screen instanceof JoinMultiplayerScreen;
    }

    private void addMarker(FlashbackConfigV1.SubcategoryMarker.SubcategoryMarkerOptions options) {
        String custom;
        int colour = options.color == MarkerColour.CUSTOM_RGB ? ((custom = options.customRGB.replaceAll("[^0-9a-fA-F]", "")).isEmpty() ? 0 : Integer.parseInt(custom, 16)) : options.color.colour;
        this.addMarker(colour, options.savePosition, options.description);
    }

    private void addMarker(@Nullable Integer colour, @Nullable Boolean savePosition, @Nullable String description) {
        Entity camera;
        Minecraft minecraft = Minecraft.getInstance();
        if (RECORDER == null) {
            minecraft.gui.getChat().addClientSystemMessage((Component)Component.translatable((String)"flashback.mark_command.not_recording").withStyle(ChatFormatting.RED));
            return;
        }
        ReplayMarker.MarkerPosition position = null;
        if ((savePosition == null || savePosition.booleanValue()) && (camera = Minecraft.getInstance().getCameraEntity()) != null) {
            position = new ReplayMarker.MarkerPosition(camera.getEyePosition().toVector3f(), camera.level().dimension().toString());
        }
        if (description != null && description.isBlank()) {
            description = null;
        }
        Object feedback = description != null ? I18n.get((String)"flashback.mark.added_with_description", (Object[])new Object[]{description}) : (colour != null ? I18n.get((String)"flashback.mark.added_with_color", (Object[])new Object[]{Integer.toHexString(colour)}) : I18n.get((String)"flashback.mark.added", (Object[])new Object[0]));
        if (position != null) {
            feedback = (String)feedback + I18n.get((String)"flashback.mark.added_at", (Object[])new Object[]{Float.valueOf(position.position().x), Float.valueOf(position.position().y), Float.valueOf(position.position().z)});
        }
        if (colour == null) {
            colour = 0xFF5555;
        }
        if (description != null && (description = description.trim()).isEmpty()) {
            description = null;
        }
        minecraft.gui.getChat().addClientSystemMessage((Component)Component.literal((String)feedback));
        RECORDER.addMarker(new ReplayMarker(colour, position, description));
    }

    private void deleteUnusedReplayStates() {
        Path flashbackDir = Flashback.getDataDirectory();
        Path replayDir = Flashback.getReplayFolder();
        Path replayStatesDir = flashbackDir.resolve("editor_states");
        if (!Files.exists(replayDir, new LinkOption[0]) || !Files.isDirectory(replayDir, new LinkOption[0])) {
            return;
        }
        if (!Files.exists(replayStatesDir, new LinkOption[0]) || !Files.isDirectory(replayStatesDir, new LinkOption[0])) {
            return;
        }
        ArrayList<String> recentReplays = new ArrayList<String>(Flashback.config.internal.recentReplays);
        CompletableFuture.runAsync(() -> {
            long currentTime = System.currentTimeMillis();
            HashMap<UUID, Path> replayStates = new HashMap<UUID, Path>();
            try (DirectoryStream<Path> directoryStream22 = Files.newDirectoryStream(replayStatesDir);){
                for (Path path : directoryStream22) {
                    UUID uuid;
                    BasicFileAttributeView attributeView;
                    BasicFileAttributes basicFileAttributes;
                    long lastModified;
                    long timeDifference;
                    String string = path.getFileName().toString();
                    String withoutExtension = null;
                    if (string.endsWith(".json")) {
                        withoutExtension = string.substring(0, string.length() - 5);
                    } else if (string.endsWith(".json.old")) {
                        withoutExtension = string.substring(0, string.length() - 9);
                    }
                    try {
                        boolean used = false;
                        JsonObject jsonObject = (JsonObject)FlashbackGson.COMPRESSED.fromJson(Files.readString(path), JsonObject.class);
                        if (jsonObject.has("usedByPaths")) {
                            for (JsonElement usedBy : jsonObject.get("usedByPaths").getAsJsonArray()) {
                                String usedByStr = usedBy.getAsString();
                                if (recentReplays.contains(usedByStr)) {
                                    used = true;
                                    break;
                                }
                                Path usedByPath = Path.of(usedBy.getAsString(), new String[0]);
                                if (!Files.exists(usedByPath, new LinkOption[0])) continue;
                                used = true;
                                break;
                            }
                        }
                        if (used) {
                            continue;
                        }
                    }
                    catch (Exception used) {
                        // empty catch block
                    }
                    if ((timeDifference = Math.abs(currentTime - (lastModified = Math.max((basicFileAttributes = (attributeView = Files.getFileAttributeView(path, BasicFileAttributeView.class, new LinkOption[0])).readAttributes()).creationTime().toMillis(), basicFileAttributes.lastModifiedTime().toMillis())))) < Duration.ofDays(30L).toMillis() || withoutExtension == null) continue;
                    try {
                        uuid = UUID.fromString(withoutExtension);
                    }
                    catch (Exception ignored) {
                        continue;
                    }
                    replayStates.put(uuid, path);
                }
            }
            catch (IOException directoryStream22) {
                // empty catch block
            }
            if (replayStates.isEmpty()) {
                return;
            }
            HashSet<UUID> replayUuids = new HashSet<UUID>();
            HashSet<Path> checkedReplayPaths = new HashSet<Path>();
            try {
                for (String string : recentReplays) {
                    Path path = Path.of(string, new String[0]);
                    if (!checkedReplayPaths.add(path) || !Files.exists(path, new LinkOption[0])) continue;
                    Flashback.readReplayUuidIntoSet(path, replayUuids);
                }
            }
            catch (IOException e) {
                LOGGER.error("Unable read replay uuid", (Throwable)e);
                return;
            }
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(replayDir);){
                for (Path path : directoryStream) {
                    if (!checkedReplayPaths.add(path)) continue;
                    Flashback.readReplayUuidIntoSet(path, replayUuids);
                }
            }
            catch (IOException e) {
                LOGGER.error("Unable to iterate replay directory or read replay uuid", (Throwable)e);
                return;
            }
            for (Map.Entry entry : replayStates.entrySet()) {
                if (replayUuids.contains(entry.getKey())) continue;
                try {
                    Files.deleteIfExists((Path)entry.getValue());
                }
                catch (IOException iOException) {}
            }
        }, (Executor)Util.backgroundExecutor());
    }

    private static void readReplayUuidIntoSet(Path path, Set<UUID> replayUuids) throws IOException {
        JsonObject metadataJson;
        FlashbackMeta metadata;
        if (!path.toString().endsWith(".zip")) {
            return;
        }
        String metadataString = null;
        try (FileSystem fs = FileSystems.newFileSystem(path);){
            Path metadataPath = fs.getPath("/metadata.json", new String[0]);
            if (Files.exists(metadataPath, new LinkOption[0])) {
                metadataString = Files.readString(metadataPath);
            }
        }
        if (metadataString != null && (metadata = FlashbackMeta.fromJson(metadataJson = (JsonObject)new Gson().fromJson(metadataString, JsonObject.class))) != null) {
            replayUuids.add(metadata.replayIdentifier);
        }
    }

    public static FlashbackConfigV1 getConfig() {
        return config;
    }

    @Nullable
    public static ReplayServer getReplayServer() {
        IntegratedServer integratedServer = Minecraft.getInstance().getSingleplayerServer();
        if (integratedServer instanceof ReplayServer) {
            ReplayServer replayServer = (ReplayServer)integratedServer;
            return replayServer;
        }
        return null;
    }

    public static void removePendingReplaySave(Path recordFolder) {
        pendingReplaySave.remove(recordFolder);
    }

    public static boolean isExporting() {
        return EXPORT_JOB != null && EXPORT_JOB.isRunning();
    }

    public static void updateIsInReplay() {
        isInReplay = Minecraft.getInstance().getSingleplayerServer() instanceof ReplayServer;
    }

    public static boolean isInReplay() {
        return isInReplay || isOpeningReplay;
    }

    public static long getVisualMillis() {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer != null) {
            ExportJob exportJob = EXPORT_JOB;
            float tick = exportJob != null ? (float)exportJob.getCurrentTickDouble() : (float)replayServer.getPartialReplayTick();
            return (long)(tick * 50.0f);
        }
        return Util.getMillis();
    }

    private int startRecordingReplay(CommandContext<FabricClientCommandSource> command) {
        Flashback.startRecordingReplay();
        return 0;
    }

    private int finishRecordingReplay(CommandContext<FabricClientCommandSource> command) {
        Flashback.finishRecordingReplay();
        return 0;
    }

    private int openFlashbackConfig(CommandContext<FabricClientCommandSource> command) {
        delayedOpenConfig = true;
        return 0;
    }

    public static void startRecordingReplay() {
        if (RECORDER != null) {
            SystemToast.add((ToastManager)Minecraft.getInstance().getToastManager(), (SystemToast.SystemToastId)FlashbackSystemToasts.RECORDING_TOAST, (Component)Component.translatable((String)"flashback.toast.already_recording"), (Component)Component.translatable((String)"flashback.toast.already_recording_description"));
            return;
        }
        List<String> unsupported = Flashback.getRecordingIncompatibleMods();
        if (unsupported != null && !unsupported.isEmpty()) {
            pendingUnsupportedModsForRecording = unsupported;
            return;
        }
        RECORDER = new Recorder(Minecraft.getInstance().player.registryAccess());
        if (Flashback.getConfig().recordingControls.showRecordingToasts) {
            SystemToast.add((ToastManager)Minecraft.getInstance().getToastManager(), (SystemToast.SystemToastId)FlashbackSystemToasts.RECORDING_TOAST, (Component)FlashbackTextComponents.FLASHBACK, (Component)Component.translatable((String)"flashback.toast.started_recording"));
        }
    }

    public static void pauseRecordingReplay(boolean pause) {
        RECORDER.setPaused(pause);
        if (Flashback.getConfig().recordingControls.showRecordingToasts) {
            SystemToast.add((ToastManager)Minecraft.getInstance().getToastManager(), (SystemToast.SystemToastId)FlashbackSystemToasts.RECORDING_TOAST, (Component)FlashbackTextComponents.FLASHBACK, (Component)Component.translatable((String)(pause ? "flashback.toast.paused_recording" : "flashback.toast.unpaused_recording")));
        }
    }

    public static void cancelRecordingReplay() {
        Recorder recorder = RECORDER;
        RECORDER = null;
        Path recordFolder = recorder.finish();
        try {
            FileUtils.deleteDirectory((File)recordFolder.toFile());
        }
        catch (Exception e) {
            LOGGER.error("Exception deleting record folder", (Throwable)e);
        }
        if (Flashback.getConfig().recordingControls.showRecordingToasts) {
            SystemToast.add((ToastManager)Minecraft.getInstance().getToastManager(), (SystemToast.SystemToastId)FlashbackSystemToasts.RECORDING_TOAST, (Component)FlashbackTextComponents.FLASHBACK, (Component)Component.translatable((String)"flashback.toast.cancelled_recording"));
        }
    }

    public static void finishRecordingReplay() {
        if (RECORDER == null) {
            SystemToast.add((ToastManager)Minecraft.getInstance().getToastManager(), (SystemToast.SystemToastId)FlashbackSystemToasts.RECORDING_TOAST, (Component)Component.translatable((String)"flashback.toast.not_recording"), (Component)Component.translatable((String)"flashback.toast.cant_finish_when_not_recording"));
            return;
        }
        Recorder recorder = RECORDER;
        RECORDER = null;
        recorder.endTickWithContext(true);
        if (Flashback.getConfig().recordingControls.quicksave) {
            Object filename;
            Path replayDir = Flashback.getReplayFolder();
            if (!Files.exists(replayDir, new LinkOption[0])) {
                try {
                    Files.createDirectories(replayDir, new FileAttribute[0]);
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            try {
                LocalDateTime dateTime = LocalDateTime.now();
                dateTime = dateTime.withNano(0);
                filename = FileUtil.findAvailableName((Path)replayDir, (String)dateTime.toString(), (String)".zip");
            }
            catch (IOException e) {
                LOGGER.error("Error while trying to determine filename", (Throwable)e);
                filename = String.valueOf(UUID.randomUUID()) + ".zip";
            }
            Path outputFile = replayDir.resolve((String)filename);
            ReplayExporter.export(recorder.finish(), outputFile, null);
        } else {
            pendingReplaySave.add(recorder.finish());
        }
        if (Flashback.getConfig().recordingControls.showRecordingToasts) {
            SystemToast.add((ToastManager)Minecraft.getInstance().getToastManager(), (SystemToast.SystemToastId)FlashbackSystemToasts.RECORDING_TOAST, (Component)FlashbackTextComponents.FLASHBACK, (Component)Component.translatable((String)"flashback.toast.finished_recording"));
        }
    }

    @Nullable
    public static AbstractClientPlayer getSpectatingPlayer() {
        AbstractClientPlayer clientPlayer;
        if (!Flashback.isInReplay()) {
            return null;
        }
        Entity entity = Minecraft.getInstance().getCameraEntity();
        if (entity instanceof AbstractClientPlayer && (clientPlayer = (AbstractClientPlayer)entity) != Minecraft.getInstance().player) {
            return clientPlayer;
        }
        return null;
    }

    public static void openReplayFromFileBrowser() {
        String defaultFolder = Flashback.getReplayFolder().toString();
        AsyncFileDialogs.openFileDialog(defaultFolder, "Zip File", "zip").thenAccept(pathStr -> {
            if (pathStr != null) {
                Path path = Path.of(pathStr, new String[0]);
                Minecraft.getInstance().submit(() -> Flashback.openReplayWorld(path));
            }
        });
    }

    public static GameRules createReplayGameRules(FeatureFlagSet featureFlagSet) {
        GameRules gameRules = new GameRules(featureFlagSet);
        gameRules.set(GameRules.SPAWN_MOBS, (Object)false, null);
        gameRules.set(GameRules.ENTITY_DROPS, (Object)false, null);
        gameRules.set(GameRules.SHOW_ADVANCEMENT_MESSAGES, (Object)false, null);
        gameRules.set(GameRules.RAIDS, (Object)false, null);
        gameRules.set(GameRules.SPAWN_PATROLS, (Object)false, null);
        gameRules.set(GameRules.SPAWN_WARDENS, (Object)false, null);
        gameRules.set(GameRules.SPAWN_WANDERING_TRADERS, (Object)false, null);
        gameRules.set(GameRules.SPREAD_VINES, (Object)false, null);
        gameRules.set(GameRules.FIRE_SPREAD_RADIUS_AROUND_PLAYER, (Object)0, null);
        gameRules.set(GameRules.ADVANCE_WEATHER, (Object)false, null);
        gameRules.set(GameRules.RANDOM_TICK_SPEED, (Object)0, null);
        return gameRules;
    }

    public static void openReplayWorld(Path path) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null) {
            minecraft.level.disconnect((Component)Component.empty());
        }
        minecraft.disconnectWithProgressScreen();
        minecraft.setScreen((Screen)new TitleScreen());
        ReplayUI.shownRegistryErrorWarning = false;
        ReplayUI.shownPlayerSpawnErrorWarning = false;
        String pathStr = path.toString();
        FlashbackConfigV1 config = Flashback.getConfig();
        config.internal.recentReplays.remove(pathStr);
        config.internal.recentReplays.add(0, pathStr);
        if (config.internal.recentReplays.size() > 32) {
            config.internal.recentReplays.remove(config.internal.recentReplays.size() - 1);
        }
        config.delayedSaveToDefaultFolder();
        try {
            isOpeningReplay = true;
            UUID replayUuid = UUID.randomUUID();
            Path replayTemp = TempFolderProvider.createTemp(TempFolderProvider.TempFolderType.SERVER, replayUuid);
            FileUtils.deleteDirectory((File)replayTemp.toFile());
            LevelStorageSource source = new LevelStorageSource(replayTemp.resolve("saves"), replayTemp.resolve("backups"), Minecraft.getInstance().directoryValidator(), Minecraft.getInstance().getFixerUpper());
            LevelStorageSource.LevelStorageAccess access = source.createAccess("replay");
            PackRepository packRepository = ServerPacksSource.createPackRepository((LevelStorageSource.LevelStorageAccess)access);
            packRepository.reload();
            GameRules gameRules = Flashback.createReplayGameRules(FeatureFlags.DEFAULT_FLAGS);
            WorldDataConfiguration worldDataConfiguration = new WorldDataConfiguration(new DataPackConfig(List.of(), List.of()), FeatureFlags.DEFAULT_FLAGS);
            LevelSettings levelSettings = new LevelSettings("Replay", GameType.SPECTATOR, new LevelSettings.DifficultySettings(Difficulty.NORMAL, false, true), true, worldDataConfiguration);
            WorldLoader.PackConfig packConfig = new WorldLoader.PackConfig(packRepository, worldDataConfiguration, false, true);
            WorldLoader.InitConfig initConfig = new WorldLoader.InitConfig(packConfig, Commands.CommandSelection.DEDICATED, PermissionSet.ALL_PERMISSIONS);
            WorldStem worldStem = (WorldStem)Util.blockUntilDone(executor -> WorldLoader.load((WorldLoader.InitConfig)initConfig, dataLoadContext -> {
                Registry registry = new MappedRegistry(Registries.LEVEL_STEM, Lifecycle.stable()).freeze();
                Holder.Reference plains = (Holder.Reference)dataLoadContext.datapackWorldgen().lookupOrThrow(Registries.BIOME).get(Biomes.PLAINS).get();
                Holder.Reference overworld = (Holder.Reference)dataLoadContext.datapackWorldgen().lookupOrThrow(Registries.DIMENSION_TYPE).get(BuiltinDimensionTypes.OVERWORLD).get();
                WorldDimensions worldDimensions = new WorldDimensions(Map.of(LevelStem.OVERWORLD, new LevelStem((Holder)overworld, (ChunkGenerator)new EmptyLevelSource((Holder.Reference<Biome>)plains))));
                WorldDimensions.Complete complete = worldDimensions.bake(registry);
                return new WorldLoader.DataLoadOutput((Object)new LevelDataAndDimensions.WorldDataAndGenSettings((WorldData)new PrimaryLevelData(levelSettings, complete.specialWorldProperty(), complete.lifecycle()), new WorldGenSettings(new WorldOptions(0L, false, false), worldDimensions)), complete.dimensionsRegistryAccess());
            }, WorldStem::new, (Executor)Util.backgroundExecutor(), (Executor)executor)).get();
            ((MinecraftExt)Minecraft.getInstance()).flashback$startReplayServer(access, packRepository, worldStem, Optional.of(gameRules), new MinecraftExt.StartReplayServerInfo(replayUuid, path));
            TaskbarManager.launchTaskbarManager();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            isOpeningReplay = false;
        }
    }

    static {
        configElements = null;
        configDirectory = null;
        delayedStartRecording = 0;
        delayedOpenConfig = false;
        isInReplay = false;
        supportsDistantHorizons = false;
        isBobbyLoaded = false;
        pendingReplaySave = new ArrayList<Path>();
        pendingReplayRecovery = new ArrayList<Path>();
        pendingUnsupportedModsForRecording = null;
        isOpeningReplay = false;
        worldBorderLerpStartTime = -1L;
        category = KeyMapping.Category.register((Identifier)Flashback.createIdentifier("keybind"));
        createMarker1KeyBind = KeyMappingHelper.registerKeyMapping((KeyMapping)new KeyMapping("flashback.keybind.create_marker_1", InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), category));
        createMarker2KeyBind = KeyMappingHelper.registerKeyMapping((KeyMapping)new KeyMapping("flashback.keybind.create_marker_2", InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), category));
        createMarker3KeyBind = KeyMappingHelper.registerKeyMapping((KeyMapping)new KeyMapping("flashback.keybind.create_marker_3", InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), category));
        createMarker4KeyBind = KeyMappingHelper.registerKeyMapping((KeyMapping)new KeyMapping("flashback.keybind.create_marker_4", InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), category));
        RECORDING_INFO_DEBUG_SCREEN_ID = Flashback.createIdentifier("recording_info");
    }
}

