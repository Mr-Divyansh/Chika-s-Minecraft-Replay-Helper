/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ca.spottedleaf.starlight.common.chunk.ExtendedChunk
 *  ca.spottedleaf.starlight.common.light.SWMRNibbleArray
 *  ca.spottedleaf.starlight.common.light.StarLightEngine
 *  com.mojang.authlib.GameProfile
 *  io.netty.channel.ChannelFutureListener
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.embedded.EmbeddedChannel
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents$Load
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.Registry
 *  net.minecraft.core.Registry$PendingTags
 *  net.minecraft.core.SectionPos
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.Connection
 *  net.minecraft.network.DisconnectionDetails
 *  net.minecraft.network.PacketListener
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.ByteBufCodecs
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.PacketFlow
 *  net.minecraft.network.protocol.common.ClientboundClearDialogPacket
 *  net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket
 *  net.minecraft.network.protocol.common.ClientboundCustomReportDetailsPacket
 *  net.minecraft.network.protocol.common.ClientboundDisconnectPacket
 *  net.minecraft.network.protocol.common.ClientboundKeepAlivePacket
 *  net.minecraft.network.protocol.common.ClientboundPingPacket
 *  net.minecraft.network.protocol.common.ClientboundResourcePackPopPacket
 *  net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket
 *  net.minecraft.network.protocol.common.ClientboundServerLinksPacket
 *  net.minecraft.network.protocol.common.ClientboundShowDialogPacket
 *  net.minecraft.network.protocol.common.ClientboundStoreCookiePacket
 *  net.minecraft.network.protocol.common.ClientboundTransferPacket
 *  net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.cookie.ClientboundCookieRequestPacket
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.network.protocol.game.ClientboundAnimatePacket
 *  net.minecraft.network.protocol.game.ClientboundAwardStatsPacket
 *  net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket
 *  net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket
 *  net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
 *  net.minecraft.network.protocol.game.ClientboundBlockEventPacket
 *  net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket
 *  net.minecraft.network.protocol.game.ClientboundBossEventPacket
 *  net.minecraft.network.protocol.game.ClientboundBundlePacket
 *  net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket
 *  net.minecraft.network.protocol.game.ClientboundChunkBatchFinishedPacket
 *  net.minecraft.network.protocol.game.ClientboundChunkBatchStartPacket
 *  net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket
 *  net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket$ChunkBiomeData
 *  net.minecraft.network.protocol.game.ClientboundClearTitlesPacket
 *  net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket
 *  net.minecraft.network.protocol.game.ClientboundCommandsPacket
 *  net.minecraft.network.protocol.game.ClientboundContainerClosePacket
 *  net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket
 *  net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket
 *  net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
 *  net.minecraft.network.protocol.game.ClientboundCooldownPacket
 *  net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket
 *  net.minecraft.network.protocol.game.ClientboundDamageEventPacket
 *  net.minecraft.network.protocol.game.ClientboundDebugBlockValuePacket
 *  net.minecraft.network.protocol.game.ClientboundDebugChunkValuePacket
 *  net.minecraft.network.protocol.game.ClientboundDebugEntityValuePacket
 *  net.minecraft.network.protocol.game.ClientboundDebugEventPacket
 *  net.minecraft.network.protocol.game.ClientboundDebugSamplePacket
 *  net.minecraft.network.protocol.game.ClientboundDeleteChatPacket
 *  net.minecraft.network.protocol.game.ClientboundDisguisedChatPacket
 *  net.minecraft.network.protocol.game.ClientboundEntityEventPacket
 *  net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket
 *  net.minecraft.network.protocol.game.ClientboundExplodePacket
 *  net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket
 *  net.minecraft.network.protocol.game.ClientboundGameEventPacket
 *  net.minecraft.network.protocol.game.ClientboundGameEventPacket$Type
 *  net.minecraft.network.protocol.game.ClientboundGameRuleValuesPacket
 *  net.minecraft.network.protocol.game.ClientboundGameTestHighlightPosPacket
 *  net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket
 *  net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket
 *  net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData
 *  net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket
 *  net.minecraft.network.protocol.game.ClientboundLevelEventPacket
 *  net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket
 *  net.minecraft.network.protocol.game.ClientboundLightUpdatePacket
 *  net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData
 *  net.minecraft.network.protocol.game.ClientboundLoginPacket
 *  net.minecraft.network.protocol.game.ClientboundLowDiskSpaceWarningPacket
 *  net.minecraft.network.protocol.game.ClientboundMapItemDataPacket
 *  net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket
 *  net.minecraft.network.protocol.game.ClientboundMountScreenOpenPacket
 *  net.minecraft.network.protocol.game.ClientboundMoveEntityPacket
 *  net.minecraft.network.protocol.game.ClientboundMoveMinecartPacket
 *  net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket
 *  net.minecraft.network.protocol.game.ClientboundOpenBookPacket
 *  net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
 *  net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket
 *  net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerChatPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket$Action
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket$Entry
 *  net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerRotationPacket
 *  net.minecraft.network.protocol.game.ClientboundProjectilePowerPacket
 *  net.minecraft.network.protocol.game.ClientboundRecipeBookAddPacket
 *  net.minecraft.network.protocol.game.ClientboundRecipeBookRemovePacket
 *  net.minecraft.network.protocol.game.ClientboundRecipeBookSettingsPacket
 *  net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket
 *  net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket
 *  net.minecraft.network.protocol.game.ClientboundResetScorePacket
 *  net.minecraft.network.protocol.game.ClientboundRespawnPacket
 *  net.minecraft.network.protocol.game.ClientboundRotateHeadPacket
 *  net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket
 *  net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket
 *  net.minecraft.network.protocol.game.ClientboundServerDataPacket
 *  net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket
 *  net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket
 *  net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket
 *  net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket
 *  net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket
 *  net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket
 *  net.minecraft.network.protocol.game.ClientboundSetCameraPacket
 *  net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket
 *  net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket
 *  net.minecraft.network.protocol.game.ClientboundSetCursorItemPacket
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
 *  net.minecraft.network.protocol.game.ClientboundSetPlayerInventoryPacket
 *  net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket
 *  net.minecraft.network.protocol.game.ClientboundSetScorePacket
 *  net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket
 *  net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket
 *  net.minecraft.network.protocol.game.ClientboundSetTimePacket
 *  net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket
 *  net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket
 *  net.minecraft.network.protocol.game.ClientboundSoundEntityPacket
 *  net.minecraft.network.protocol.game.ClientboundSoundPacket
 *  net.minecraft.network.protocol.game.ClientboundStartConfigurationPacket
 *  net.minecraft.network.protocol.game.ClientboundStopSoundPacket
 *  net.minecraft.network.protocol.game.ClientboundSystemChatPacket
 *  net.minecraft.network.protocol.game.ClientboundTabListPacket
 *  net.minecraft.network.protocol.game.ClientboundTagQueryPacket
 *  net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket
 *  net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket
 *  net.minecraft.network.protocol.game.ClientboundTestInstanceBlockStatus
 *  net.minecraft.network.protocol.game.ClientboundTickingStatePacket
 *  net.minecraft.network.protocol.game.ClientboundTickingStepPacket
 *  net.minecraft.network.protocol.game.ClientboundTrackedWaypointPacket
 *  net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket
 *  net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket
 *  net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket$AttributeSnapshot
 *  net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket
 *  net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket
 *  net.minecraft.network.protocol.game.CommonPlayerSpawnInfo
 *  net.minecraft.network.protocol.ping.ClientboundPongResponsePacket
 *  net.minecraft.network.syncher.SynchedEntityData$DataItem
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerChunkCache
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.level.ThreadedLevelLightEngine
 *  net.minecraft.server.network.CommonListenerCookie
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.ProblemReporter
 *  net.minecraft.util.ProblemReporter$ScopedCollector
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.clock.ClockNetworkState
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Avatar
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntitySpawnReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.HumanoidArm
 *  net.minecraft.world.entity.InterpolationHandler
 *  net.minecraft.world.entity.Leashable
 *  net.minecraft.world.entity.LightningBolt
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PositionMoveRotation
 *  net.minecraft.world.entity.ai.attributes.AttributeInstance
 *  net.minecraft.world.entity.ai.attributes.AttributeMap
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.decoration.painting.Painting
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.GameType
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelHeightAccessor
 *  net.minecraft.world.level.LevelSettings
 *  net.minecraft.world.level.LevelSettings$DifficultySettings
 *  net.minecraft.world.level.LightLayer
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.biome.Biomes
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.piston.PistonBaseBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.border.WorldBorder
 *  net.minecraft.world.level.chunk.ChunkAccess
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.chunk.DataLayer
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.chunk.status.ChunkStatus
 *  net.minecraft.world.level.dimension.LevelStem
 *  net.minecraft.world.level.lighting.LevelLightEngine
 *  net.minecraft.world.level.saveddata.maps.MapItemSavedData
 *  net.minecraft.world.level.storage.DerivedLevelData
 *  net.minecraft.world.level.storage.LevelData
 *  net.minecraft.world.level.storage.PrimaryLevelData
 *  net.minecraft.world.level.storage.PrimaryLevelData$SpecialWorldProperty
 *  net.minecraft.world.level.storage.ServerLevelData
 *  net.minecraft.world.level.storage.TagValueInput
 *  net.minecraft.world.level.storage.WorldData
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.playback;

import ca.spottedleaf.starlight.common.chunk.ExtendedChunk;
import ca.spottedleaf.starlight.common.light.SWMRNibbleArray;
import ca.spottedleaf.starlight.common.light.StarLightEngine;
import com.mojang.authlib.GameProfile;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.PacketHelper;
import com.moulberry.flashback.combo_options.WeatherOverride;
import com.moulberry.flashback.exception.UnsupportedPacketException;
import com.moulberry.flashback.ext.LevelChunkExt;
import com.moulberry.flashback.ext.ServerLevelExt;
import com.moulberry.flashback.ext.ThreadedLevelLightEngineExt;
import com.moulberry.flashback.packet.FlashbackRemoteExperience;
import com.moulberry.flashback.packet.FlashbackRemoteFoodData;
import com.moulberry.flashback.packet.FlashbackRemoteSelectHotbarSlot;
import com.moulberry.flashback.packet.FlashbackRemoteSetSlot;
import com.moulberry.flashback.playback.EmptyLevelSource;
import com.moulberry.flashback.playback.FlashbackFakePlayer;
import com.moulberry.flashback.playback.ReplayPlayer;
import com.moulberry.flashback.playback.ReplayServer;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.PacketListener;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.ClientboundClearDialogPacket;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ClientboundCustomReportDetailsPacket;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.common.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.common.ClientboundPingPacket;
import net.minecraft.network.protocol.common.ClientboundResourcePackPopPacket;
import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;
import net.minecraft.network.protocol.common.ClientboundServerLinksPacket;
import net.minecraft.network.protocol.common.ClientboundShowDialogPacket;
import net.minecraft.network.protocol.common.ClientboundStoreCookiePacket;
import net.minecraft.network.protocol.common.ClientboundTransferPacket;
import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.cookie.ClientboundCookieRequestPacket;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundChunkBatchFinishedPacket;
import net.minecraft.network.protocol.game.ClientboundChunkBatchStartPacket;
import net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket;
import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
import net.minecraft.network.protocol.game.ClientboundDebugBlockValuePacket;
import net.minecraft.network.protocol.game.ClientboundDebugChunkValuePacket;
import net.minecraft.network.protocol.game.ClientboundDebugEntityValuePacket;
import net.minecraft.network.protocol.game.ClientboundDebugEventPacket;
import net.minecraft.network.protocol.game.ClientboundDebugSamplePacket;
import net.minecraft.network.protocol.game.ClientboundDeleteChatPacket;
import net.minecraft.network.protocol.game.ClientboundDisguisedChatPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundGameRuleValuesPacket;
import net.minecraft.network.protocol.game.ClientboundGameTestHighlightPosPacket;
import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundLowDiskSpaceWarningPacket;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundMountScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundMoveMinecartPacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerRotationPacket;
import net.minecraft.network.protocol.game.ClientboundProjectilePowerPacket;
import net.minecraft.network.protocol.game.ClientboundRecipeBookAddPacket;
import net.minecraft.network.protocol.game.ClientboundRecipeBookRemovePacket;
import net.minecraft.network.protocol.game.ClientboundRecipeBookSettingsPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundResetScorePacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
import net.minecraft.network.protocol.game.ClientboundSetCursorItemPacket;
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
import net.minecraft.network.protocol.game.ClientboundSetPlayerInventoryPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStartConfigurationPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundTestInstanceBlockStatus;
import net.minecraft.network.protocol.game.ClientboundTickingStatePacket;
import net.minecraft.network.protocol.game.ClientboundTickingStepPacket;
import net.minecraft.network.protocol.game.ClientboundTrackedWaypointPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.game.CommonPlayerSpawnInfo;
import net.minecraft.network.protocol.ping.ClientboundPongResponsePacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.Difficulty;
import net.minecraft.world.clock.ClockNetworkState;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.decoration.painting.Painting;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ReplayGamePacketHandler
implements ClientGamePacketListener {
    private final BlockState FALLBACK_BLOCK_STATE = Blocks.VOID_AIR.defaultBlockState();
    private final ReplayServer replayServer;
    private final Map<UUID, PlayerInfo> playerInfoMap = new HashMap<UUID, PlayerInfo>();
    private Int2ObjectMap<Entity> pendingEntities = new Int2ObjectOpenHashMap();
    private ResourceKey<Level> currentDimension = null;
    public int localPlayerId = -1;
    public LongSet forceSendChunksDueToMovingPistonShenanigans = new LongOpenHashSet();

    public ReplayGamePacketHandler(ReplayServer replayServer) {
        this.replayServer = replayServer;
    }

    private void forward(Packet<?> packet) {
        for (ServerPlayer serverPlayer : this.replayServer.getReplayViewers()) {
            serverPlayer.connection.send(packet);
        }
    }

    private void forward(Entity entity, Packet<? super ClientGamePacketListener> packet) {
        if (entity == null) {
            this.forward(packet);
        } else {
            ServerChunkCache serverChunkCache = this.level().getChunkSource();
            serverChunkCache.sendToTrackingPlayers(entity, packet);
        }
    }

    private void setBlockState(ServerLevel level, BlockPos blockPos, BlockState blockState) {
        if (blockState == null) {
            blockState = this.FALLBACK_BLOCK_STATE;
        }
        LevelChunk levelChunk = level.getChunkAt(blockPos);
        ((LevelChunkExt)levelChunk).flashback$setBlockStateWithoutUpdates(blockPos, blockState);
    }

    public void flushPendingEntities() {
        ServerLevel level = this.level();
        boolean canSpawnLightningBolt = switch (this.replayServer.getEditorState().replayVisuals.overrideWeatherMode) {
            default -> throw new MatchException(null, null);
            case WeatherOverride.NONE, WeatherOverride.THUNDERING -> true;
            case WeatherOverride.CLEAR, WeatherOverride.OVERCAST, WeatherOverride.RAINING, WeatherOverride.SNOWING -> false;
        };
        for (Entity pendingEntity : this.pendingEntities.values()) {
            if (pendingEntity instanceof LightningBolt && !canSpawnLightningBolt) continue;
            Entity existingEntity = level.getEntity(pendingEntity.getId());
            if (existingEntity != null) {
                if (existingEntity instanceof ServerPlayer) {
                    ServerPlayer existingPlayer = (ServerPlayer)existingEntity;
                    existingPlayer.discard();
                } else if (existingEntity.getType().equals(pendingEntity.getType())) {
                    try {
                        existingEntity.restoreFrom(pendingEntity);
                        existingEntity.setPos(pendingEntity.getX(), pendingEntity.getY(), pendingEntity.getZ());
                        existingEntity.setXRot(pendingEntity.getXRot());
                        existingEntity.setYRot(pendingEntity.getYRot());
                        existingEntity.setYHeadRot(pendingEntity.getYHeadRot());
                        existingEntity.setDeltaMovement(pendingEntity.getDeltaMovement());
                        if (pendingEntity instanceof LivingEntity) {
                            SynchedEntityData.DataItem[] pendingLiving = (SynchedEntityData.DataItem[])pendingEntity;
                            existingEntity.setYBodyRot(pendingLiving.yBodyRot);
                        }
                        for (SynchedEntityData.DataItem dataItem : pendingEntity.getEntityData().itemsById) {
                            existingEntity.getEntityData().set(dataItem.getAccessor(), dataItem.getValue());
                        }
                        continue;
                    }
                    catch (Exception ignored) {
                        existingEntity.discard();
                    }
                } else {
                    existingEntity.discard();
                }
            }
            ((ServerLevelExt)level).flashback$setCanSpawnEntities(true);
            try {
                level.addFreshEntity(pendingEntity);
                ChunkPos chunkPos = ChunkPos.containing((BlockPos)pendingEntity.blockPosition());
                level.getChunkSource().addTicketWithRadius(ReplayServer.ENTITY_LOAD_TICKET, chunkPos, 3);
            }
            catch (Exception e) {
                Flashback.LOGGER.error("Unable to spawn entity", (Throwable)e);
            }
            ((ServerLevelExt)level).flashback$setCanSpawnEntities(false);
        }
        this.pendingEntities.clear();
    }

    private Entity getEntityOrPending(int entityId) {
        if (this.pendingEntities.containsKey(entityId)) {
            return (Entity)this.pendingEntities.get(entityId);
        }
        this.flushPendingEntities();
        return this.level().getEntity(entityId);
    }

    public ServerLevel level() {
        return this.currentDimension == null ? null : this.replayServer.getLevel(this.currentDimension);
    }

    public void handleAddEntity(ClientboundAddEntityPacket clientboundAddEntityPacket) {
        Entity entity;
        if (Entity.ENTITY_COUNTER.get() <= clientboundAddEntityPacket.getId()) {
            Entity.ENTITY_COUNTER.set(clientboundAddEntityPacket.getId() + 1000);
        }
        if (clientboundAddEntityPacket.id >= ReplayServer.REPLAY_VIEWER_IDS_START && clientboundAddEntityPacket.id <= ReplayServer.REPLAY_VIEWER_IDS_START + 128) {
            clientboundAddEntityPacket.id += 128;
        }
        if ((entity = this.createEntityFromPacket(clientboundAddEntityPacket)) != null) {
            Direction direction;
            if (entity instanceof Painting && !(direction = Direction.from3DDataValue((int)clientboundAddEntityPacket.getData())).getAxis().isHorizontal()) {
                clientboundAddEntityPacket.data = Direction.NORTH.get3DDataValue();
            }
            entity.recreateFromPacket(clientboundAddEntityPacket);
            this.pendingEntities.put(clientboundAddEntityPacket.getId(), (Object)entity);
        }
    }

    private ServerPlayer spawnPlayer(ClientboundAddEntityPacket addEntityPacket, GameProfile gameProfile, GameType gameType) {
        Entity existingEntity;
        this.flushPendingEntities();
        CommonListenerCookie commonListenerCookie = CommonListenerCookie.createInitial((GameProfile)gameProfile, (boolean)false);
        FlashbackFakePlayer serverPlayer = new FlashbackFakePlayer(this, (MinecraftServer)this.replayServer, this.level(), commonListenerCookie.gameProfile(), commonListenerCookie.clientInformation()){
            {
                Objects.requireNonNull(this$0);
                super(minecraftServer, serverLevel, gameProfile, clientInformation);
            }

            public boolean isSpectator() {
                return false;
            }

            public boolean isCreative() {
                return true;
            }
        };
        ServerPlayer existingPlayer = this.replayServer.getPlayerList().getPlayer(serverPlayer.getUUID());
        if (existingPlayer != null) {
            existingPlayer.discard();
        }
        if ((existingEntity = this.level().getEntity(serverPlayer.getId())) != null) {
            existingEntity.discard();
        }
        for (ReplayPlayer replayViewer : this.replayServer.getReplayViewers()) {
            if (!Objects.equals(replayViewer.lastFirstPersonDataUUID, addEntityPacket.getUUID())) continue;
            replayViewer.lastFirstPersonDataUUID = null;
        }
        Connection connection = new Connection(this, PacketFlow.SERVERBOUND){
            {
                Objects.requireNonNull(this$0);
                super(receiving);
            }

            public void send(Packet<?> packet, @Nullable ChannelFutureListener channelFutureListener, boolean bl) {
            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelHandler[]{connection});
        serverPlayer.recreateFromPacket(addEntityPacket);
        try {
            this.replayServer.getPlayerList().placeNewPlayer(connection, (ServerPlayer)serverPlayer, commonListenerCookie);
        }
        catch (Exception e) {
            this.replayServer.failedToSpawnPlayerWarning = true;
            Flashback.LOGGER.error("Failed to spawn player", (Throwable)e);
            return null;
        }
        serverPlayer.setGameMode(gameType);
        if (serverPlayer.isRemoved()) {
            this.replayServer.failedToSpawnPlayerWarning = true;
            Flashback.LOGGER.error("ServerPlayer {} was removed while spawning. Incompatible mod?", (Object)serverPlayer.getUUID());
            return null;
        }
        return serverPlayer;
    }

    @Nullable
    private Entity createEntityFromPacket(ClientboundAddEntityPacket packet) {
        EntityType entityType = packet.getType();
        if (entityType == EntityType.PLAYER) {
            PlayerInfo playerInfo = this.playerInfoMap.get(packet.getUUID());
            if (playerInfo == null) {
                return null;
            }
            this.spawnPlayer(packet, playerInfo.getProfile(), playerInfo.getGameMode());
            return null;
        }
        return entityType.create((Level)this.level(), EntitySpawnReason.LOAD);
    }

    public void handleAddObjective(ClientboundSetObjectivePacket clientboundSetObjectivePacket) {
        this.forward((Packet<?>)clientboundSetObjectivePacket);
    }

    public void handleAnimate(ClientboundAnimatePacket clientboundAnimatePacket) {
        Entity entity = this.level().getEntity(clientboundAnimatePacket.getId());
        this.forward(entity, (Packet<? super ClientGamePacketListener>)clientboundAnimatePacket);
    }

    public void handleHurtAnimation(ClientboundHurtAnimationPacket clientboundHurtAnimationPacket) {
        Entity entity = this.level().getEntity(clientboundHurtAnimationPacket.id());
        this.forward(entity, (Packet<? super ClientGamePacketListener>)clientboundHurtAnimationPacket);
    }

    public void handleAwardStats(ClientboundAwardStatsPacket clientboundAwardStatsPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundAwardStatsPacket);
    }

    public void handleRecipeBookAdd(ClientboundRecipeBookAddPacket clientboundRecipeBookAddPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundRecipeBookAddPacket);
    }

    public void handleRecipeBookRemove(ClientboundRecipeBookRemovePacket clientboundRecipeBookRemovePacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundRecipeBookRemovePacket);
    }

    public void handleRecipeBookSettings(ClientboundRecipeBookSettingsPacket clientboundRecipeBookSettingsPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundRecipeBookSettingsPacket);
    }

    public void handleBlockDestruction(ClientboundBlockDestructionPacket clientboundBlockDestructionPacket) {
        this.forward((Packet<?>)clientboundBlockDestructionPacket);
    }

    public void handleOpenSignEditor(ClientboundOpenSignEditorPacket clientboundOpenSignEditorPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundOpenSignEditorPacket);
    }

    public void handleBlockEntityData(ClientboundBlockEntityDataPacket clientboundBlockEntityDataPacket) {
        BlockPos blockPos = clientboundBlockEntityDataPacket.getPos();
        this.level().getBlockEntity(blockPos, clientboundBlockEntityDataPacket.getType()).ifPresent(blockEntity -> {
            try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(blockEntity.problemPath(), Flashback.LOGGER);){
                blockEntity.loadWithComponents(TagValueInput.create((ProblemReporter)scopedCollector, (HolderLookup.Provider)this.replayServer.registryAccess(), (CompoundTag)clientboundBlockEntityDataPacket.getTag()));
            }
            blockEntity.setChanged();
            BlockState blockState = this.level().getBlockState(blockPos);
            Level level = blockEntity.getLevel();
            if (level != null) {
                level.sendBlockUpdated(blockPos, blockState, blockState, 3);
            }
        });
    }

    public void handleBlockEvent(ClientboundBlockEventPacket clientboundBlockEventPacket) {
        BlockPos pos = clientboundBlockEventPacket.getPos();
        BlockState blockState = this.level().getBlockState(pos);
        if (blockState.getBlock() instanceof PistonBaseBlock) {
            this.forceSendChunksDueToMovingPistonShenanigans.add(ChunkPos.pack((int)(pos.getX() >> 4), (int)(pos.getZ() >> 4)));
            this.forceSendChunksDueToMovingPistonShenanigans.add(ChunkPos.pack((int)((pos.getX() >> 4) + 1), (int)(pos.getZ() >> 4)));
            this.forceSendChunksDueToMovingPistonShenanigans.add(ChunkPos.pack((int)(pos.getX() >> 4), (int)((pos.getZ() >> 4) + 1)));
            this.forceSendChunksDueToMovingPistonShenanigans.add(ChunkPos.pack((int)((pos.getX() >> 4) - 1), (int)(pos.getZ() >> 4)));
            this.forceSendChunksDueToMovingPistonShenanigans.add(ChunkPos.pack((int)(pos.getX() >> 4), (int)((pos.getZ() >> 4) - 1)));
        }
        this.replayServer.getPlayerList().broadcastAll((Packet)clientboundBlockEventPacket);
    }

    public void handleBlockUpdate(ClientboundBlockUpdatePacket clientboundBlockUpdatePacket) {
        BlockState blockState = clientboundBlockUpdatePacket.getBlockState();
        this.setBlockState(this.level(), clientboundBlockUpdatePacket.getPos(), blockState);
        this.forward((Packet<?>)clientboundBlockUpdatePacket);
    }

    public void handleSystemChat(ClientboundSystemChatPacket clientboundSystemChatPacket) {
        this.forward((Packet<?>)clientboundSystemChatPacket);
    }

    public void handlePlayerChat(ClientboundPlayerChatPacket clientboundPlayerChatPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundPlayerChatPacket);
    }

    public void handleRotatePlayer(ClientboundPlayerRotationPacket clientboundPlayerRotationPacket) {
        Entity entity = this.level().getEntity(this.localPlayerId);
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player)entity;
        player.setYRot(clientboundPlayerRotationPacket.yRot());
        player.setXRot(clientboundPlayerRotationPacket.xRot());
    }

    public void handleDisguisedChat(ClientboundDisguisedChatPacket clientboundDisguisedChatPacket) {
        this.forward((Packet<?>)clientboundDisguisedChatPacket);
    }

    public void handleDeleteChat(ClientboundDeleteChatPacket clientboundDeleteChatPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundDeleteChatPacket);
    }

    public void handleChunkBlocksUpdate(ClientboundSectionBlocksUpdatePacket clientboundSectionBlocksUpdatePacket) {
        ServerLevel level = this.level();
        clientboundSectionBlocksUpdatePacket.runUpdates((blockPos, blockState) -> this.setBlockState(level, (BlockPos)blockPos, (BlockState)blockState));
        this.forward((Packet<?>)clientboundSectionBlocksUpdatePacket);
    }

    public void handleMapItemData(ClientboundMapItemDataPacket clientboundMapItemDataPacket) {
        this.forward((Packet<?>)clientboundMapItemDataPacket);
        ServerLevel level = this.level();
        if (level == null) {
            return;
        }
        MapItemSavedData mapItemSavedData = level.getMapData(clientboundMapItemDataPacket.mapId());
        if (mapItemSavedData == null) {
            mapItemSavedData = MapItemSavedData.createForClient((byte)clientboundMapItemDataPacket.scale(), (boolean)clientboundMapItemDataPacket.locked(), (ResourceKey)level.dimension());
            level.setMapData(clientboundMapItemDataPacket.mapId(), mapItemSavedData);
        }
        clientboundMapItemDataPacket.applyToMap(mapItemSavedData);
    }

    public void handleMinecartAlongTrack(ClientboundMoveMinecartPacket clientboundMoveMinecartPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundMoveMinecartPacket);
    }

    public void handleContainerClose(ClientboundContainerClosePacket clientboundContainerClosePacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundContainerClosePacket);
    }

    public void handleContainerContent(ClientboundContainerSetContentPacket clientboundContainerSetContentPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundContainerSetContentPacket);
    }

    public void handleMountScreenOpen(ClientboundMountScreenOpenPacket clientboundMountScreenOpenPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundMountScreenOpenPacket);
    }

    public void handleContainerSetData(ClientboundContainerSetDataPacket clientboundContainerSetDataPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundContainerSetDataPacket);
    }

    public void handleContainerSetSlot(ClientboundContainerSetSlotPacket clientboundContainerSetSlotPacket) {
        Entity entity = this.level().getEntity(this.localPlayerId);
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player)entity;
        if (clientboundContainerSetSlotPacket.getContainerId() == 0) {
            int slot = clientboundContainerSetSlotPacket.getSlot();
            ItemStack itemStack = clientboundContainerSetSlotPacket.getItem();
            player.getInventory().setItem(slot, itemStack);
            for (ReplayPlayer replayViewer : this.replayServer.getReplayViewers()) {
                if (!Objects.equals(replayViewer.lastFirstPersonDataUUID, player.getUUID())) continue;
                replayViewer.lastFirstPersonHotbarItems[slot] = itemStack.copy();
                ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackRemoteSetSlot(player.getId(), slot, itemStack.copy()));
            }
        }
    }

    public void handleEntityEvent(ClientboundEntityEventPacket clientboundEntityEventPacket) {
        Entity entity = clientboundEntityEventPacket.getEntity((Level)this.level());
        this.forward(entity, (Packet<? super ClientGamePacketListener>)clientboundEntityEventPacket);
    }

    public void handleEntityLinkPacket(ClientboundSetEntityLinkPacket clientboundSetEntityLinkPacket) {
        Entity source = this.level().getEntity(clientboundSetEntityLinkPacket.getSourceId());
        if (source == null) {
            this.forward((Packet<?>)clientboundSetEntityLinkPacket);
            return;
        }
        if (source instanceof Leashable) {
            Leashable leashable = (Leashable)source;
            if (clientboundSetEntityLinkPacket.getDestId() == 0) {
                leashable.setLeashedTo(null, true);
            } else {
                Entity dest = this.level().getEntity(clientboundSetEntityLinkPacket.getDestId());
                if (dest == null) {
                    this.forward((Packet<?>)clientboundSetEntityLinkPacket);
                    return;
                }
                leashable.setLeashedTo(dest, true);
            }
        }
    }

    public void handleEntityPositionSync(ClientboundEntityPositionSyncPacket clientboundEntityPositionSyncPacket) {
        Entity entity = this.getEntityOrPending(clientboundEntityPositionSyncPacket.id());
        if (entity == null) {
            return;
        }
        Vec3 position = clientboundEntityPositionSyncPacket.values().position();
        entity.setPos(position);
        entity.setOnGround(clientboundEntityPositionSyncPacket.onGround());
    }

    public void handleSetEntityPassengersPacket(ClientboundSetPassengersPacket clientboundSetPassengersPacket) {
        Entity vehicle = this.level().getEntity(clientboundSetPassengersPacket.getVehicle());
        if (vehicle == null) {
            return;
        }
        vehicle.ejectPassengers();
        for (int passengerId : clientboundSetPassengersPacket.getPassengers()) {
            Entity passenger = this.level().getEntity(passengerId);
            if (passenger == null) continue;
            passenger.startRiding(vehicle, true, false);
        }
    }

    public void handleExplosion(ClientboundExplodePacket e) {
        ClientboundExplodePacket withoutKnockback = new ClientboundExplodePacket(e.center(), e.radius(), e.blockCount(), Optional.empty(), e.explosionParticle(), e.explosionSound(), e.blockParticles());
        this.forward((Packet<?>)withoutKnockback);
    }

    public void handleGameEvent(ClientboundGameEventPacket clientboundGameEventPacket) {
        ClientboundGameEventPacket.Type type = clientboundGameEventPacket.getEvent();
        ServerLevel level = this.level();
        float floatValue = clientboundGameEventPacket.getParam();
        if (type == ClientboundGameEventPacket.NO_RESPAWN_BLOCK_AVAILABLE) {
            return;
        }
        if (type == ClientboundGameEventPacket.START_RAINING) {
            level.getWeatherData().setRaining(true);
            level.setRainLevel(0.0f);
        } else if (type == ClientboundGameEventPacket.STOP_RAINING) {
            level.getWeatherData().setRaining(false);
            level.setRainLevel(1.0f);
        } else {
            if (type == ClientboundGameEventPacket.CHANGE_GAME_MODE) {
                return;
            }
            if (type == ClientboundGameEventPacket.WIN_GAME) {
                return;
            }
            if (type == ClientboundGameEventPacket.DEMO_EVENT) {
                return;
            }
            if (type == ClientboundGameEventPacket.RAIN_LEVEL_CHANGE) {
                level.setRainLevel(floatValue);
            } else if (type == ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE) {
                level.setThunderLevel(floatValue);
            } else {
                if (type == ClientboundGameEventPacket.PUFFER_FISH_STING) {
                    return;
                }
                if (type == ClientboundGameEventPacket.GUARDIAN_ELDER_EFFECT) {
                    return;
                }
                if (type == ClientboundGameEventPacket.IMMEDIATE_RESPAWN) {
                    return;
                }
                if (type == ClientboundGameEventPacket.LIMITED_CRAFTING) {
                    return;
                }
                if (type == ClientboundGameEventPacket.LEVEL_CHUNKS_LOAD_START) {
                    return;
                }
            }
        }
        this.forward((Packet<?>)clientboundGameEventPacket);
    }

    public void handleLevelChunkWithLight(ClientboundLevelChunkWithLightPacket levelChunkWithLightPacket) {
        int x = levelChunkWithLightPacket.getX();
        int z = levelChunkWithLightPacket.getZ();
        ThreadedLevelLightEngine levelLightEngine = this.level().getChunkSource().getLightEngine();
        LevelChunk chunk = this.level().getChunk(x, z);
        ClientboundLevelChunkPacketData chunkData = levelChunkWithLightPacket.getChunkData();
        chunk.replaceWithPacketData(chunkData.getReadBuffer(), chunkData.getHeightmaps(), chunkData.getBlockEntitiesTagsConsumer(x, z));
        ClientboundLightUpdatePacketData lightData = levelChunkWithLightPacket.getLightData();
        if (FabricLoader.getInstance().isModLoaded("starlight")) {
            chunk.setLightCorrect(false);
            try {
                SWMRNibbleArray[] blockNibbles = StarLightEngine.getFilledEmptyLight((LevelHeightAccessor)this.level());
                SWMRNibbleArray[] skyNibbles = StarLightEngine.getFilledEmptyLight((LevelHeightAccessor)this.level());
                ReplayGamePacketHandler.extractStarlightData((LevelLightEngine)levelLightEngine, lightData.getBlockYMask(), lightData.getEmptyBlockYMask(), lightData.getBlockUpdates().iterator(), blockNibbles);
                ReplayGamePacketHandler.extractStarlightData((LevelLightEngine)levelLightEngine, lightData.getSkyYMask(), lightData.getEmptySkyYMask(), lightData.getSkyUpdates().iterator(), skyNibbles);
                ((ExtendedChunk)chunk).setBlockNibbles(blockNibbles);
                ((ExtendedChunk)chunk).setSkyNibbles(skyNibbles);
            }
            catch (Exception e) {
                Flashback.LOGGER.error("Error while setting starlight light data", (Throwable)e);
            }
            chunk.setLightCorrect(true);
        } else {
            this.applyLightData((LevelLightEngine)levelLightEngine, x, z, lightData);
        }
        ChunkPos chunkPos = chunk.getPos();
        ((ServerLevelExt)this.level()).flashback$markChunkAsSendable(chunkPos.pack());
        for (ServerPlayer serverPlayer : this.replayServer.getReplayViewers()) {
            if (!serverPlayer.getChunkTrackingView().contains(chunkPos)) continue;
            serverPlayer.connection.chunkSender.markChunkPendingToSend(chunk);
        }
        chunk.markUnsaved();
    }

    private static void extractStarlightData(LevelLightEngine levelLightEngine, BitSet yMask, BitSet emptyYMask, Iterator<byte[]> iterator, SWMRNibbleArray[] nibbles) {
        for (int index = 0; index < levelLightEngine.getLightSectionCount(); ++index) {
            boolean hasData = yMask.get(index);
            boolean isEmpty = emptyYMask.get(index);
            if (!hasData && !isEmpty) continue;
            DataLayer dataLayer = hasData ? new DataLayer((byte[])iterator.next().clone()) : new DataLayer();
            nibbles[index] = SWMRNibbleArray.fromVanilla((DataLayer)dataLayer);
        }
    }

    private void applyLightData(LevelLightEngine levelLightEngine, int x, int z, ClientboundLightUpdatePacketData clientboundLightUpdatePacketData) {
        levelLightEngine.retainData(new ChunkPos(x, z), true);
        BitSet skyYMask = clientboundLightUpdatePacketData.getSkyYMask();
        BitSet emptySkyYMask = clientboundLightUpdatePacketData.getEmptySkyYMask();
        Iterator<byte[]> iterator = clientboundLightUpdatePacketData.getSkyUpdates().iterator();
        this.readSectionList(x, z, levelLightEngine, LightLayer.SKY, skyYMask, emptySkyYMask, iterator);
        BitSet blockYMask = clientboundLightUpdatePacketData.getBlockYMask();
        BitSet emptyBlockYMask = clientboundLightUpdatePacketData.getEmptyBlockYMask();
        Iterator<byte[]> iterator2 = clientboundLightUpdatePacketData.getBlockUpdates().iterator();
        this.readSectionList(x, z, levelLightEngine, LightLayer.BLOCK, blockYMask, emptyBlockYMask, iterator2);
        ((ThreadedLevelLightEngineExt)levelLightEngine).flashback$submitPost(x, z, () -> {
            LevelChunk chunkAccess = this.level().getChunk(x, z);
            chunkAccess.initializeLightSources();
            boolean isLightCorrect = chunkAccess.getPersistedStatus().isOrAfter(ChunkStatus.LIGHT) && chunkAccess.isLightCorrect();
            ((ThreadedLevelLightEngine)levelLightEngine).initializeLight((ChunkAccess)chunkAccess, isLightCorrect).thenRun(() -> {
                LevelChunk chunkAccess2 = this.level().getChunk(x, z);
                chunkAccess2.setLightCorrect(true);
                ChunkPos chunkPos = new ChunkPos(x, z);
                ClientboundLightUpdatePacket lightPacket = new ClientboundLightUpdatePacket(chunkPos, levelLightEngine, null, null);
                for (ServerPlayer serverPlayer : this.replayServer.getReplayViewers()) {
                    if (!serverPlayer.getChunkTrackingView().contains(chunkPos)) continue;
                    serverPlayer.connection.send((Packet)lightPacket);
                }
            });
        });
    }

    private void readSectionList(int x, int z, LevelLightEngine levelLightEngine, LightLayer lightLayer, BitSet yMask, BitSet emptyYMask, Iterator<byte[]> iterator) {
        for (int index = 0; index < levelLightEngine.getLightSectionCount(); ++index) {
            int y = levelLightEngine.getMinLightSection() + index;
            boolean hasData = yMask.get(index);
            boolean isEmpty = emptyYMask.get(index);
            if (!hasData && !isEmpty) continue;
            levelLightEngine.queueSectionData(lightLayer, SectionPos.of((int)x, (int)y, (int)z), hasData ? new DataLayer((byte[])iterator.next().clone()) : new DataLayer());
        }
    }

    public void handleChunksBiomes(ClientboundChunksBiomesPacket clientboundChunksBiomesPacket) {
        ArrayList<LevelChunk> chunks = new ArrayList<LevelChunk>(clientboundChunksBiomesPacket.chunkBiomeData().size());
        for (ClientboundChunksBiomesPacket.ChunkBiomeData chunkBiomeData : clientboundChunksBiomesPacket.chunkBiomeData()) {
            LevelChunk chunk = this.level().getChunk(chunkBiomeData.pos().x(), chunkBiomeData.pos().z());
            chunk.replaceBiomes(chunkBiomeData.getReadBuffer());
            chunks.add(chunk);
        }
        this.level().getChunkSource().chunkMap.resendBiomesForChunks(chunks);
    }

    public void handleForgetLevelChunk(ClientboundForgetLevelChunkPacket clientboundForgetLevelChunkPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundForgetLevelChunkPacket);
    }

    public void handleLevelEvent(ClientboundLevelEventPacket clientboundLevelEventPacket) {
        if (clientboundLevelEventPacket.isGlobalEvent()) {
            this.replayServer.getPlayerList().broadcastAll((Packet)clientboundLevelEventPacket);
        } else {
            BlockPos blockPos = clientboundLevelEventPacket.getPos();
            ResourceKey dimension = this.level().dimension();
            this.replayServer.getPlayerList().broadcast(null, (double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), 64.0, dimension, (Packet)clientboundLevelEventPacket);
        }
    }

    public void handleLogin(ClientboundLoginPacket clientboundLoginPacket) {
        Entity entity;
        ServerPlayer localPlayer = null;
        boolean recreatePlayer = false;
        ServerLevel level = this.level();
        if (level != null && (entity = level.getEntity(this.localPlayerId)) instanceof ServerPlayer) {
            ServerPlayer serverPlayer;
            localPlayer = serverPlayer = (ServerPlayer)entity;
            if (this.localPlayerId != clientboundLoginPacket.playerId()) {
                localPlayer.discard();
                recreatePlayer = true;
            }
        }
        this.localPlayerId = clientboundLoginPacket.playerId();
        this.ensureWorldCreated(clientboundLoginPacket.commonPlayerSpawnInfo(), (Entity)(recreatePlayer ? null : localPlayer), true);
        CommonPlayerSpawnInfo spawnInfo = clientboundLoginPacket.commonPlayerSpawnInfo();
        WorldData worldData = this.replayServer.getWorldData();
        if (worldData instanceof PrimaryLevelData) {
            PrimaryLevelData primaryLevelData = (PrimaryLevelData)worldData;
            PrimaryLevelData.SpecialWorldProperty specialWorldProperty = spawnInfo.isFlat() ? PrimaryLevelData.SpecialWorldProperty.FLAT : (spawnInfo.isDebug() ? PrimaryLevelData.SpecialWorldProperty.DEBUG : PrimaryLevelData.SpecialWorldProperty.NONE);
            primaryLevelData.specialWorldProperty = specialWorldProperty;
            primaryLevelData.settings = new LevelSettings(primaryLevelData.settings.levelName(), primaryLevelData.settings.gameType(), new LevelSettings.DifficultySettings(Difficulty.NORMAL, clientboundLoginPacket.hardcore(), true), true, primaryLevelData.settings.dataConfiguration());
        }
        if (recreatePlayer) {
            ClientboundAddEntityPacket addEntityPacket = (ClientboundAddEntityPacket)PacketHelper.createAddEntity((Entity)localPlayer);
            addEntityPacket.id = clientboundLoginPacket.playerId();
            this.spawnPlayer(addEntityPacket, localPlayer.getGameProfile(), localPlayer.gameMode.getGameModeForPlayer());
        }
    }

    public void handleCreateLocalPlayer(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        Entity existing;
        UUID uuid = registryFriendlyByteBuf.readUUID();
        double x = registryFriendlyByteBuf.readDouble();
        double y = registryFriendlyByteBuf.readDouble();
        double z = registryFriendlyByteBuf.readDouble();
        float xRot = registryFriendlyByteBuf.readFloat();
        float yRot = registryFriendlyByteBuf.readFloat();
        float yHeadRot = registryFriendlyByteBuf.readFloat();
        Vec3 velocity = new Vec3(registryFriendlyByteBuf.readDouble(), registryFriendlyByteBuf.readDouble(), registryFriendlyByteBuf.readDouble());
        ClientboundAddEntityPacket addEntityPacket = new ClientboundAddEntityPacket(this.localPlayerId, uuid, x, y, z, xRot, yRot, EntityType.PLAYER, 0, velocity, (double)yHeadRot);
        GameProfile gameProfile = (GameProfile)ByteBufCodecs.GAME_PROFILE.decode((Object)registryFriendlyByteBuf);
        GameType gameType = GameType.byId((int)registryFriendlyByteBuf.readVarInt());
        Entity entity = existing = this.localPlayerId == -1 ? null : this.getEntityOrPending(this.localPlayerId);
        if (existing == null) {
            this.spawnPlayer(addEntityPacket, gameProfile, gameType);
        } else {
            existing.snapTo(x, y, z, yRot, xRot);
            InterpolationHandler interpolation = existing.getInterpolation();
            if (interpolation != null) {
                interpolation.cancel();
            }
            existing.setDeltaMovement(velocity);
        }
    }

    private void ensureWorldCreated(CommonPlayerSpawnInfo commonPlayerSpawnInfo, Entity localPlayer, boolean forceReset) {
        ServerLevel oldLevel;
        boolean forceRecreate;
        ResourceKey dimension = commonPlayerSpawnInfo.dimension();
        if (forceReset) {
            this.replayServer.clearReplayTempFolder();
            if (this.currentDimension == dimension) {
                this.replayServer.clearLevel(this.level());
            }
        }
        boolean bl = forceRecreate = (oldLevel = this.replayServer.getLevel(dimension)) != null && !oldLevel.dimensionType().equals(commonPlayerSpawnInfo.dimensionType().value());
        if (oldLevel == null || forceRecreate) {
            ServerLevelData serverLevelData = this.replayServer.worldData.overworldData();
            if (dimension != Level.OVERWORLD) {
                serverLevelData = new DerivedLevelData(this.replayServer.worldData, serverLevelData);
            }
            Holder.Reference plains = (Holder.Reference)this.replayServer.registryAccess().lookupOrThrow(Registries.BIOME).get(Biomes.PLAINS).get();
            LevelStem levelStem = new LevelStem(commonPlayerSpawnInfo.dimensionType(), (ChunkGenerator)new EmptyLevelSource((Holder.Reference<Biome>)plains));
            ServerLevel serverLevel = new ServerLevel((MinecraftServer)this.replayServer, this.replayServer.executor, this.replayServer.storageSource, serverLevelData, dimension, levelStem, false, commonPlayerSpawnInfo.seed(), List.of(), false);
            serverLevel.noSave = true;
            ((ServerLevelEvents.Load)ServerLevelEvents.LOAD.invoker()).onLevelLoad((MinecraftServer)this.replayServer, serverLevel);
            this.replayServer.levels.put(dimension, serverLevel);
            this.replayServer.getPlayerList().addWorldborderListener(serverLevel);
            if (oldLevel != null) {
                this.replayServer.closeLevel(oldLevel);
            }
        }
        ServerLevel newLevel = this.replayServer.getLevel(dimension);
        ((ServerLevelExt)newLevel).flashback$setCanSpawnEntities(false);
        ((ServerLevelExt)newLevel).flashback$setSeedHash(commonPlayerSpawnInfo.seed());
        this.replayServer.followLocalPlayerNextTickIfWrongDimension = true;
        if (newLevel != oldLevel) {
            if (localPlayer != null) {
                localPlayer.teleportTo(newLevel, 0.0, 0.0, 0.0, Set.of(), 0.0f, 0.0f, true);
            }
            for (ReplayPlayer replayViewer : this.replayServer.getReplayViewers()) {
                replayViewer.teleportTo(newLevel, replayViewer.getX(), replayViewer.getY(), replayViewer.getZ(), Set.of(), replayViewer.getYRot(), replayViewer.getXRot(), true);
                replayViewer.followLocalPlayerNextTick = true;
            }
            this.forceSendChunksDueToMovingPistonShenanigans.clear();
        }
        if (this.currentDimension != null && this.currentDimension != dimension) {
            ServerLevel level;
            if (this.currentDimension == Level.OVERWORLD) {
                this.replayServer.clearLevel(this.level());
            } else if (this.currentDimension != null && (level = (ServerLevel)this.replayServer.levels.remove(this.currentDimension)) != null) {
                this.replayServer.closeLevel(level);
            }
        }
        this.currentDimension = commonPlayerSpawnInfo.dimension();
        this.replayServer.spawnLevel = this.currentDimension;
    }

    public void handleMoveEntity(ClientboundMoveEntityPacket clientboundMoveEntityPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundMoveEntityPacket);
    }

    public void handleMovePlayer(ClientboundPlayerPositionPacket clientboundPlayerPositionPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundPlayerPositionPacket);
    }

    public void handleParticleEvent(ClientboundLevelParticlesPacket clientboundLevelParticlesPacket) {
        for (ServerPlayer serverPlayer : this.replayServer.getReplayViewers()) {
            boolean overrideLimiter = clientboundLevelParticlesPacket.isOverrideLimiter();
            double x = clientboundLevelParticlesPacket.getX();
            double y = clientboundLevelParticlesPacket.getY();
            double z = clientboundLevelParticlesPacket.getZ();
            this.level().sendParticles(serverPlayer, overrideLimiter, x, y, z, (Packet)clientboundLevelParticlesPacket);
        }
    }

    public void handlePlayerAbilities(ClientboundPlayerAbilitiesPacket clientboundPlayerAbilitiesPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundPlayerAbilitiesPacket);
    }

    public void handlePlayerInfoRemove(ClientboundPlayerInfoRemovePacket clientboundPlayerInfoRemovePacket) {
        for (UUID uuid : clientboundPlayerInfoRemovePacket.profileIds()) {
            this.playerInfoMap.remove(uuid);
        }
        this.forward((Packet<?>)clientboundPlayerInfoRemovePacket);
    }

    public void handlePlayerInfoUpdate(ClientboundPlayerInfoUpdatePacket clientboundPlayerInfoUpdatePacket) {
        PlayerInfo playerInfo;
        for (ClientboundPlayerInfoUpdatePacket.Entry entry : clientboundPlayerInfoUpdatePacket.newEntries()) {
            playerInfo = new PlayerInfo(Objects.requireNonNull(entry.profile()), false);
            this.playerInfoMap.putIfAbsent(entry.profileId(), playerInfo);
        }
        for (ClientboundPlayerInfoUpdatePacket.Entry entry : clientboundPlayerInfoUpdatePacket.entries()) {
            playerInfo = this.playerInfoMap.get(entry.profileId());
            if (playerInfo == null) continue;
            for (ClientboundPlayerInfoUpdatePacket.Action action : clientboundPlayerInfoUpdatePacket.actions()) {
                this.applyPlayerInfoUpdate(action, entry, playerInfo);
            }
        }
        this.forward((Packet<?>)clientboundPlayerInfoUpdatePacket);
    }

    private void applyPlayerInfoUpdate(ClientboundPlayerInfoUpdatePacket.Action action, ClientboundPlayerInfoUpdatePacket.Entry entry, PlayerInfo playerInfo) {
        switch (action) {
            case ADD_PLAYER: 
            case INITIALIZE_CHAT: 
            case UPDATE_LISTED: {
                break;
            }
            case UPDATE_GAME_MODE: {
                playerInfo.setGameMode(entry.gameMode());
                break;
            }
            case UPDATE_LATENCY: {
                playerInfo.setLatency(entry.latency());
                break;
            }
            case UPDATE_DISPLAY_NAME: {
                playerInfo.setTabListDisplayName(entry.displayName());
                break;
            }
            case UPDATE_LIST_ORDER: {
                playerInfo.setTabListOrder(entry.listOrder());
                break;
            }
            case UPDATE_HAT: {
                playerInfo.setShowHat(entry.showHat());
            }
        }
    }

    public void handleRemoveEntities(ClientboundRemoveEntitiesPacket clientboundRemoveEntitiesPacket) {
        IntArrayList forwardRemoveUnknown = new IntArrayList();
        clientboundRemoveEntitiesPacket.getEntityIds().forEach(arg_0 -> this.lambda$handleRemoveEntities$0((IntList)forwardRemoveUnknown, arg_0));
        if (!forwardRemoveUnknown.isEmpty()) {
            this.forward((Packet<?>)new ClientboundRemoveEntitiesPacket((IntList)forwardRemoveUnknown));
        }
    }

    public void handleRemoveMobEffect(ClientboundRemoveMobEffectPacket clientboundRemoveMobEffectPacket) {
        Entity entity = this.getEntityOrPending(clientboundRemoveMobEffectPacket.entityId());
        if (entity == null) {
            this.forward((Packet<?>)clientboundRemoveMobEffectPacket);
        } else if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            livingEntity.removeEffect(clientboundRemoveMobEffectPacket.effect());
        }
    }

    public void handleRespawn(ClientboundRespawnPacket clientboundRespawnPacket) {
        Entity localPlayer = null;
        ServerLevel level = this.level();
        if (level != null) {
            localPlayer = level.getEntity(this.localPlayerId);
        }
        this.ensureWorldCreated(clientboundRespawnPacket.commonPlayerSpawnInfo(), localPlayer, false);
        if (localPlayer instanceof ServerPlayer) {
            ServerPlayer oldServerPlayer = (ServerPlayer)localPlayer;
            ClientboundAddEntityPacket addEntityPacket = new ClientboundAddEntityPacket(oldServerPlayer.getId(), oldServerPlayer.getUUID(), oldServerPlayer.getX(), oldServerPlayer.getY(), oldServerPlayer.getZ(), oldServerPlayer.getXRot(), oldServerPlayer.getYRot(), EntityType.PLAYER, 0, Vec3.ZERO, (double)oldServerPlayer.getYHeadRot());
            ServerPlayer newServerPlayer = this.spawnPlayer(addEntityPacket, oldServerPlayer.getGameProfile(), oldServerPlayer.gameMode.getGameModeForPlayer());
            if (newServerPlayer == null) {
                return;
            }
            newServerPlayer.getEntityData().set(Avatar.DATA_PLAYER_MODE_CUSTOMISATION, (Object)((Byte)oldServerPlayer.getEntityData().get(Avatar.DATA_PLAYER_MODE_CUSTOMISATION)));
            newServerPlayer.getEntityData().set(Avatar.DATA_PLAYER_MAIN_HAND, (Object)((HumanoidArm)oldServerPlayer.getEntityData().get(Avatar.DATA_PLAYER_MAIN_HAND)));
            if (clientboundRespawnPacket.shouldKeep((byte)2)) {
                newServerPlayer.setShiftKeyDown(oldServerPlayer.isShiftKeyDown());
                newServerPlayer.setSprinting(oldServerPlayer.isSprinting());
                List oldEntityData = oldServerPlayer.getEntityData().getNonDefaultValues();
                if (oldEntityData != null) {
                    newServerPlayer.getEntityData().assignValues(oldEntityData);
                }
            }
            if (clientboundRespawnPacket.shouldKeep((byte)1)) {
                newServerPlayer.getAttributes().assignAllValues(oldServerPlayer.getAttributes());
            } else {
                newServerPlayer.getAttributes().assignBaseValues(oldServerPlayer.getAttributes());
            }
        }
    }

    public void handleRotateMob(ClientboundRotateHeadPacket clientboundRotateHeadPacket) {
        Entity entity = this.getEntityOrPending(clientboundRotateHeadPacket.entityId);
        if (entity == null) {
            this.forward((Packet<?>)clientboundRotateHeadPacket);
            return;
        }
        if (entity.getId() == this.localPlayerId) {
            return;
        }
        entity.setYHeadRot(clientboundRotateHeadPacket.getYHeadRot());
    }

    public void handleSetHeldSlot(ClientboundSetHeldSlotPacket clientboundSetHeldSlotPacket) {
        if (!Inventory.isHotbarSlot((int)clientboundSetHeldSlotPacket.slot())) {
            return;
        }
        Entity entity = this.level().getEntity(this.localPlayerId);
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player)entity;
        Inventory inventory = player.getInventory();
        if (inventory.getSelectedSlot() == clientboundSetHeldSlotPacket.slot()) {
            return;
        }
        inventory.setSelectedSlot(clientboundSetHeldSlotPacket.slot());
        for (ReplayPlayer replayViewer : this.replayServer.getReplayViewers()) {
            if (!Objects.equals(replayViewer.lastFirstPersonDataUUID, player.getUUID())) continue;
            replayViewer.lastFirstPersonSelectedSlot = inventory.getSelectedSlot();
            ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackRemoteSelectHotbarSlot(player.getId(), inventory.getSelectedSlot()));
        }
    }

    public void handleSetDisplayObjective(ClientboundSetDisplayObjectivePacket clientboundSetDisplayObjectivePacket) {
        this.forward((Packet<?>)clientboundSetDisplayObjectivePacket);
    }

    public void handleSetEntityData(ClientboundSetEntityDataPacket clientboundSetEntityDataPacket) {
        Entity entity = this.getEntityOrPending(clientboundSetEntityDataPacket.id());
        this.forward(entity, (Packet<? super ClientGamePacketListener>)clientboundSetEntityDataPacket);
        if (entity != null) {
            entity.getEntityData().assignValues(clientboundSetEntityDataPacket.packedItems());
        }
    }

    public void handleSetEntityMotion(ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket) {
        Entity entity = this.getEntityOrPending(clientboundSetEntityMotionPacket.id());
        this.forward(entity, (Packet<? super ClientGamePacketListener>)clientboundSetEntityMotionPacket);
        if (entity != null) {
            entity.setDeltaMovement(clientboundSetEntityMotionPacket.movement());
        }
    }

    public void handleSetEquipment(ClientboundSetEquipmentPacket clientboundSetEquipmentPacket) {
        Entity entity = this.getEntityOrPending(clientboundSetEquipmentPacket.getEntity());
        this.forward(entity, (Packet<? super ClientGamePacketListener>)clientboundSetEquipmentPacket);
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            clientboundSetEquipmentPacket.getSlots().forEach(pair -> livingEntity.setItemSlot((EquipmentSlot)pair.getFirst(), (ItemStack)pair.getSecond()));
        }
    }

    public void handleSetExperience(ClientboundSetExperiencePacket clientboundSetExperiencePacket) {
        Entity entity = this.level().getEntity(this.localPlayerId);
        if (entity instanceof Player) {
            Player player = (Player)entity;
            player.experienceProgress = clientboundSetExperiencePacket.getExperienceProgress();
            player.totalExperience = clientboundSetExperiencePacket.getTotalExperience();
            player.experienceLevel = clientboundSetExperiencePacket.getExperienceLevel();
            for (ReplayPlayer replayViewer : this.replayServer.getReplayViewers()) {
                if (!Objects.equals(replayViewer.lastFirstPersonDataUUID, player.getUUID())) continue;
                replayViewer.lastFirstPersonExperienceProgress = player.experienceProgress;
                replayViewer.lastFirstPersonTotalExperience = player.totalExperience;
                replayViewer.lastFirstPersonExperienceLevel = player.experienceLevel;
                ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackRemoteExperience(player.getId(), player.experienceProgress, player.totalExperience, player.experienceLevel));
            }
        }
    }

    public void handleSetHealth(ClientboundSetHealthPacket clientboundSetHealthPacket) {
        Entity entity = this.level().getEntity(this.localPlayerId);
        if (entity instanceof Player) {
            Player player = (Player)entity;
            player.setHealth(clientboundSetHealthPacket.getHealth());
            int food = clientboundSetHealthPacket.getFood();
            float saturation = clientboundSetHealthPacket.getSaturation();
            player.getFoodData().setFoodLevel(food);
            player.getFoodData().setSaturation(saturation);
            for (ReplayPlayer replayViewer : this.replayServer.getReplayViewers()) {
                if (!Objects.equals(replayViewer.lastFirstPersonDataUUID, player.getUUID())) continue;
                replayViewer.lastFirstPersonFoodLevel = food;
                replayViewer.lastFirstPersonSaturationLevel = saturation;
                ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackRemoteFoodData(player.getId(), food, saturation));
            }
        }
    }

    public void handleSetPlayerTeamPacket(ClientboundSetPlayerTeamPacket clientboundSetPlayerTeamPacket) {
        this.forward((Packet<?>)clientboundSetPlayerTeamPacket);
    }

    public void handleSetPlayerInventory(ClientboundSetPlayerInventoryPacket clientboundSetPlayerInventoryPacket) {
        Entity entity = this.level().getEntity(this.localPlayerId);
        if (entity instanceof Player) {
            Player player = (Player)entity;
            int slot = clientboundSetPlayerInventoryPacket.slot();
            ItemStack itemStack = clientboundSetPlayerInventoryPacket.contents();
            player.getInventory().setItem(slot, itemStack);
            if (slot < 0 || slot > 9) {
                return;
            }
            for (ReplayPlayer replayViewer : this.replayServer.getReplayViewers()) {
                if (!Objects.equals(replayViewer.lastFirstPersonDataUUID, player.getUUID())) continue;
                replayViewer.lastFirstPersonHotbarItems[slot] = itemStack.copy();
                ServerPlayNetworking.send((ServerPlayer)replayViewer, (CustomPacketPayload)new FlashbackRemoteSetSlot(player.getId(), slot, itemStack.copy()));
            }
        }
    }

    public void handleSetScore(ClientboundSetScorePacket clientboundSetScorePacket) {
        this.forward((Packet<?>)clientboundSetScorePacket);
    }

    public void handleResetScore(ClientboundResetScorePacket clientboundResetScorePacket) {
        this.forward((Packet<?>)clientboundResetScorePacket);
    }

    public void handleSetSpawn(ClientboundSetDefaultSpawnPositionPacket clientboundSetDefaultSpawnPositionPacket) {
        this.level().setRespawnData(clientboundSetDefaultSpawnPositionPacket.respawnData());
    }

    public void handleSetTime(ClientboundSetTimePacket clientboundSetTimePacket) {
        for (ServerLevel level : this.replayServer.getAllLevels()) {
            for (Map.Entry entry : clientboundSetTimePacket.clockUpdates().entrySet()) {
                level.clockManager().setTotalTicks((Holder)entry.getKey(), ((ClockNetworkState)entry.getValue()).totalTicks());
                level.clockManager().setPaused((Holder)entry.getKey(), ((ClockNetworkState)entry.getValue()).rate() == 0.0f);
            }
            LevelData levelData = level.getLevelData();
            if (!(levelData instanceof ServerLevelData)) continue;
            ServerLevelData serverLevelData = (ServerLevelData)levelData;
            serverLevelData.setGameTime(clientboundSetTimePacket.gameTime());
        }
        this.forward((Packet<?>)clientboundSetTimePacket);
    }

    public void handleSoundEvent(ClientboundSoundPacket clientboundSoundPacket) {
        Holder sound = clientboundSoundPacket.getSound();
        double x = clientboundSoundPacket.getX();
        double y = clientboundSoundPacket.getY();
        double z = clientboundSoundPacket.getZ();
        float volume = clientboundSoundPacket.getVolume();
        ResourceKey dimension = this.level().dimension();
        this.replayServer.getPlayerList().broadcast(null, x, y, z, (double)((SoundEvent)sound.value()).getRange(volume), dimension, (Packet)clientboundSoundPacket);
    }

    public void handleSoundEntityEvent(ClientboundSoundEntityPacket clientboundSoundEntityPacket) {
        Entity entity = this.level().getEntity(clientboundSoundEntityPacket.getId());
        if (entity == null) {
            this.forward((Packet<?>)clientboundSoundEntityPacket);
            return;
        }
        Holder sound = clientboundSoundEntityPacket.getSound();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        float volume = clientboundSoundEntityPacket.getVolume();
        ResourceKey dimension = this.level().dimension();
        this.replayServer.getPlayerList().broadcast(null, x, y, z, (double)((SoundEvent)sound.value()).getRange(volume), dimension, (Packet)clientboundSoundEntityPacket);
    }

    public void handleTakeItemEntity(ClientboundTakeItemEntityPacket clientboundTakeItemEntityPacket) {
        Entity taker = this.level().getEntity(clientboundTakeItemEntityPacket.getPlayerId());
        this.forward(taker, (Packet<? super ClientGamePacketListener>)clientboundTakeItemEntityPacket);
        Entity taken = this.level().getEntity(clientboundTakeItemEntityPacket.getItemId());
        if (taken != null) {
            taken.discard();
        }
    }

    public void handleTeleportEntity(ClientboundTeleportEntityPacket clientboundTeleportEntityPacket) {
        Entity entity = this.getEntityOrPending(clientboundTeleportEntityPacket.id());
        if (entity == null) {
            this.forward((Packet<?>)clientboundTeleportEntityPacket);
            return;
        }
        PositionMoveRotation change = clientboundTeleportEntityPacket.change();
        Set relatives = clientboundTeleportEntityPacket.relatives();
        PositionMoveRotation lerpTarget = PositionMoveRotation.of((Entity)entity);
        PositionMoveRotation absolute = PositionMoveRotation.calculateAbsolute((PositionMoveRotation)lerpTarget, (PositionMoveRotation)change, (Set)relatives);
        entity.snapTo(absolute.position().x(), absolute.position().y(), absolute.position().z(), absolute.yRot(), absolute.xRot());
        entity.setDeltaMovement(absolute.deltaMovement());
        entity.setOnGround(clientboundTeleportEntityPacket.onGround());
        entity.setYHeadRot(entity.getYRot());
    }

    public void handleTickingState(ClientboundTickingStatePacket clientboundTickingStatePacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundTickingStatePacket);
    }

    public void handleTickingStep(ClientboundTickingStepPacket clientboundTickingStepPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundTickingStepPacket);
    }

    public void handleUpdateAttributes(ClientboundUpdateAttributesPacket clientboundUpdateAttributesPacket) {
        Entity entity = this.getEntityOrPending(clientboundUpdateAttributesPacket.getEntityId());
        if (entity == null) {
            this.forward((Packet<?>)clientboundUpdateAttributesPacket);
            return;
        }
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            AttributeMap attributeMap = livingEntity.getAttributes();
            for (ClientboundUpdateAttributesPacket.AttributeSnapshot snapshot : clientboundUpdateAttributesPacket.getValues()) {
                AttributeInstance attributeInstance = attributeMap.getInstance(snapshot.attribute());
                if (attributeInstance == null) continue;
                attributeInstance.setBaseValue(snapshot.base());
                attributeInstance.removeModifiers();
                for (AttributeModifier modifier : snapshot.modifiers()) {
                    attributeInstance.addTransientModifier(modifier);
                }
            }
        }
    }

    public void handleUpdateMobEffect(ClientboundUpdateMobEffectPacket clientboundUpdateMobEffectPacket) {
        Entity entity = this.getEntityOrPending(clientboundUpdateMobEffectPacket.getEntityId());
        if (entity == null) {
            this.forward((Packet<?>)clientboundUpdateMobEffectPacket);
            return;
        }
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            Holder holder = clientboundUpdateMobEffectPacket.getEffect();
            MobEffectInstance mobEffectInstance = new MobEffectInstance(holder, clientboundUpdateMobEffectPacket.getEffectDurationTicks(), clientboundUpdateMobEffectPacket.getEffectAmplifier(), clientboundUpdateMobEffectPacket.isEffectAmbient(), clientboundUpdateMobEffectPacket.isEffectVisible(), clientboundUpdateMobEffectPacket.effectShowsIcon(), null);
            if (!clientboundUpdateMobEffectPacket.shouldBlend()) {
                mobEffectInstance.skipBlending();
            }
            livingEntity.forceAddEffect(mobEffectInstance, null);
        }
    }

    public void handlePlayerCombatEnd(ClientboundPlayerCombatEndPacket clientboundPlayerCombatEndPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundPlayerCombatEndPacket);
    }

    public void handlePlayerCombatEnter(ClientboundPlayerCombatEnterPacket clientboundPlayerCombatEnterPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundPlayerCombatEnterPacket);
    }

    public void handlePlayerCombatKill(ClientboundPlayerCombatKillPacket clientboundPlayerCombatKillPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundPlayerCombatKillPacket);
    }

    public void handleChangeDifficulty(ClientboundChangeDifficultyPacket clientboundChangeDifficultyPacket) {
        this.replayServer.setDifficultyLocked(clientboundChangeDifficultyPacket.locked());
        this.replayServer.setDifficulty(clientboundChangeDifficultyPacket.difficulty(), true);
    }

    public void handleSetCamera(ClientboundSetCameraPacket clientboundSetCameraPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundSetCameraPacket);
    }

    public void handleSetCursorItem(ClientboundSetCursorItemPacket clientboundSetCursorItemPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundSetCursorItemPacket);
    }

    public void handleInitializeBorder(ClientboundInitializeBorderPacket clientboundInitializeBorderPacket) {
        WorldBorder worldBorder = this.level().getWorldBorder();
        worldBorder.setCenter(clientboundInitializeBorderPacket.getNewCenterX(), clientboundInitializeBorderPacket.getNewCenterZ());
        long lerpTime = clientboundInitializeBorderPacket.getLerpTime();
        if (lerpTime > 0L) {
            worldBorder.lerpSizeBetween(clientboundInitializeBorderPacket.getOldSize(), clientboundInitializeBorderPacket.getNewSize(), lerpTime, this.level().getGameTime());
        } else {
            worldBorder.setSize(clientboundInitializeBorderPacket.getNewSize());
        }
        worldBorder.setAbsoluteMaxSize(clientboundInitializeBorderPacket.getNewAbsoluteMaxSize());
        worldBorder.setWarningBlocks(clientboundInitializeBorderPacket.getWarningBlocks());
        worldBorder.setWarningTime(clientboundInitializeBorderPacket.getWarningTime());
    }

    public void handleSetBorderLerpSize(ClientboundSetBorderLerpSizePacket clientboundSetBorderLerpSizePacket) {
        WorldBorder worldBorder = this.level().getWorldBorder();
        long lerpTime = clientboundSetBorderLerpSizePacket.getLerpTime();
        if (lerpTime > 0L) {
            worldBorder.lerpSizeBetween(clientboundSetBorderLerpSizePacket.getOldSize(), clientboundSetBorderLerpSizePacket.getNewSize(), lerpTime, this.level().getGameTime());
        } else {
            worldBorder.setSize(clientboundSetBorderLerpSizePacket.getNewSize());
        }
    }

    public void handleSetBorderSize(ClientboundSetBorderSizePacket clientboundSetBorderSizePacket) {
        WorldBorder worldBorder = this.level().getWorldBorder();
        worldBorder.setSize(clientboundSetBorderSizePacket.getSize());
    }

    public void handleSetBorderWarningDelay(ClientboundSetBorderWarningDelayPacket clientboundSetBorderWarningDelayPacket) {
        WorldBorder worldBorder = this.level().getWorldBorder();
        worldBorder.setWarningTime(clientboundSetBorderWarningDelayPacket.getWarningDelay());
    }

    public void handleSetBorderWarningDistance(ClientboundSetBorderWarningDistancePacket clientboundSetBorderWarningDistancePacket) {
        WorldBorder worldBorder = this.level().getWorldBorder();
        worldBorder.setWarningBlocks(clientboundSetBorderWarningDistancePacket.getWarningBlocks());
    }

    public void handleSetBorderCenter(ClientboundSetBorderCenterPacket clientboundSetBorderCenterPacket) {
        WorldBorder worldBorder = this.level().getWorldBorder();
        worldBorder.setCenter(clientboundSetBorderCenterPacket.getNewCenterX(), clientboundSetBorderCenterPacket.getNewCenterZ());
    }

    public void handleTabListCustomisation(ClientboundTabListPacket clientboundTabListPacket) {
        this.replayServer.setTabListCustomization(clientboundTabListPacket.header(), clientboundTabListPacket.footer());
    }

    public void handleBossUpdate(ClientboundBossEventPacket clientboundBossEventPacket) {
        this.replayServer.updateBossBar(clientboundBossEventPacket);
    }

    public void handleItemCooldown(ClientboundCooldownPacket clientboundCooldownPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundCooldownPacket);
    }

    public void handleMoveVehicle(ClientboundMoveVehiclePacket clientboundMoveVehiclePacket) {
        Entity vehicle;
        Entity player = this.level().getEntity(this.localPlayerId);
        if (player instanceof Player && (vehicle = player.getRootVehicle()) != player) {
            double x = clientboundMoveVehiclePacket.position().x;
            double y = clientboundMoveVehiclePacket.position().y;
            double z = clientboundMoveVehiclePacket.position().z;
            float yaw = clientboundMoveVehiclePacket.yRot();
            float pitch = clientboundMoveVehiclePacket.xRot();
            vehicle.snapTo(x, y, z, yaw, pitch);
        }
    }

    public void handleUpdateAdvancementsPacket(ClientboundUpdateAdvancementsPacket clientboundUpdateAdvancementsPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundUpdateAdvancementsPacket);
    }

    public void handleSelectAdvancementsTab(ClientboundSelectAdvancementsTabPacket clientboundSelectAdvancementsTabPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundSelectAdvancementsTabPacket);
    }

    public void handlePlaceRecipe(ClientboundPlaceGhostRecipePacket clientboundPlaceGhostRecipePacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundPlaceGhostRecipePacket);
    }

    public void handleCommands(ClientboundCommandsPacket clientboundCommandsPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundCommandsPacket);
    }

    public void handleStopSoundEvent(ClientboundStopSoundPacket clientboundStopSoundPacket) {
        this.forward((Packet<?>)clientboundStopSoundPacket);
    }

    public void handleCommandSuggestions(ClientboundCommandSuggestionsPacket clientboundCommandSuggestionsPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundCommandSuggestionsPacket);
    }

    public void handleUpdateRecipes(ClientboundUpdateRecipesPacket clientboundUpdateRecipesPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundUpdateRecipesPacket);
    }

    public void handleLookAt(ClientboundPlayerLookAtPacket clientboundPlayerLookAtPacket) {
        Entity player;
        Vec3 position = clientboundPlayerLookAtPacket.getPosition((Level)this.level());
        if (position != null && (player = this.level().getEntity(this.localPlayerId)) instanceof Player) {
            player.lookAt(clientboundPlayerLookAtPacket.getFromAnchor(), position);
        }
    }

    public void handleTagQueryPacket(ClientboundTagQueryPacket clientboundTagQueryPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundTagQueryPacket);
    }

    public void handleLightUpdatePacket(ClientboundLightUpdatePacket clientboundLightUpdatePacket) {
        int x = clientboundLightUpdatePacket.getX();
        int z = clientboundLightUpdatePacket.getZ();
        ThreadedLevelLightEngine levelLightEngine = this.level().getChunkSource().getLightEngine();
        LevelChunk chunk = this.level().getChunk(x, z);
        ClientboundLightUpdatePacketData lightData = clientboundLightUpdatePacket.getLightData();
        this.applyLightData((LevelLightEngine)levelLightEngine, x, z, lightData);
        chunk.markUnsaved();
    }

    public void handleOpenBook(ClientboundOpenBookPacket clientboundOpenBookPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundOpenBookPacket);
    }

    public void handleOpenScreen(ClientboundOpenScreenPacket clientboundOpenScreenPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundOpenScreenPacket);
    }

    public void handleMerchantOffers(ClientboundMerchantOffersPacket clientboundMerchantOffersPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundMerchantOffersPacket);
    }

    public void handleSetChunkCacheRadius(ClientboundSetChunkCacheRadiusPacket clientboundSetChunkCacheRadiusPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundSetChunkCacheRadiusPacket);
    }

    public void handleSetSimulationDistance(ClientboundSetSimulationDistancePacket clientboundSetSimulationDistancePacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundSetSimulationDistancePacket);
    }

    public void handleSetChunkCacheCenter(ClientboundSetChunkCacheCenterPacket clientboundSetChunkCacheCenterPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundSetChunkCacheCenterPacket);
    }

    public void handleBlockChangedAck(ClientboundBlockChangedAckPacket clientboundBlockChangedAckPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundBlockChangedAckPacket);
    }

    public void setActionBarText(ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket) {
        this.forward((Packet<?>)clientboundSetActionBarTextPacket);
    }

    public void setSubtitleText(ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket) {
        this.forward((Packet<?>)clientboundSetSubtitleTextPacket);
    }

    public void setTitleText(ClientboundSetTitleTextPacket clientboundSetTitleTextPacket) {
        this.forward((Packet<?>)clientboundSetTitleTextPacket);
    }

    public void setTitlesAnimation(ClientboundSetTitlesAnimationPacket clientboundSetTitlesAnimationPacket) {
        this.forward((Packet<?>)clientboundSetTitlesAnimationPacket);
    }

    public void handleTitlesClear(ClientboundClearTitlesPacket clientboundClearTitlesPacket) {
        this.forward((Packet<?>)clientboundClearTitlesPacket);
    }

    public void handleServerData(ClientboundServerDataPacket clientboundServerDataPacket) {
        this.forward((Packet<?>)clientboundServerDataPacket);
    }

    public void handleCustomChatCompletions(ClientboundCustomChatCompletionsPacket clientboundCustomChatCompletionsPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundCustomChatCompletionsPacket);
    }

    public void handleBundlePacket(ClientboundBundlePacket clientboundBundlePacket) {
        for (Packet subPacket : clientboundBundlePacket.subPackets()) {
            subPacket.handle((PacketListener)this);
        }
    }

    public void handleDamageEvent(ClientboundDamageEventPacket clientboundDamageEventPacket) {
        Entity entity = this.level().getEntity(clientboundDamageEventPacket.entityId());
        this.forward(entity, (Packet<? super ClientGamePacketListener>)clientboundDamageEventPacket);
    }

    public void handleConfigurationStart(ClientboundStartConfigurationPacket clientboundStartConfigurationPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundStartConfigurationPacket);
    }

    public void handleChunkBatchStart(ClientboundChunkBatchStartPacket clientboundChunkBatchStartPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundChunkBatchStartPacket);
    }

    public void handleChunkBatchFinished(ClientboundChunkBatchFinishedPacket clientboundChunkBatchFinishedPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundChunkBatchFinishedPacket);
    }

    public void handleDebugSample(ClientboundDebugSamplePacket clientboundDebugSamplePacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundDebugSamplePacket);
    }

    public void handleProjectilePowerPacket(ClientboundProjectilePowerPacket clientboundProjectilePowerPacket) {
        Entity entity = this.level().getEntity(clientboundProjectilePowerPacket.getId());
        this.forward(entity, (Packet<? super ClientGamePacketListener>)clientboundProjectilePowerPacket);
    }

    public void handleKeepAlive(ClientboundKeepAlivePacket clientboundKeepAlivePacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundKeepAlivePacket);
    }

    public void handlePing(ClientboundPingPacket clientboundPingPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundPingPacket);
    }

    public void handleCustomPayload(ClientboundCustomPayloadPacket clientboundCustomPayloadPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundCustomPayloadPacket);
    }

    public void handleDisconnect(ClientboundDisconnectPacket clientboundDisconnectPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundDisconnectPacket);
    }

    public void handleResourcePackPush(ClientboundResourcePackPushPacket clientboundResourcePackPushPacket) {
        this.replayServer.pushRemotePack(clientboundResourcePackPushPacket.id(), clientboundResourcePackPushPacket.url(), clientboundResourcePackPushPacket.hash());
    }

    public void handleResourcePackPop(ClientboundResourcePackPopPacket clientboundResourcePackPopPacket) {
        if (clientboundResourcePackPopPacket.id().isEmpty()) {
            this.replayServer.popAllRemotePacks();
        } else {
            this.replayServer.popRemotePack((UUID)clientboundResourcePackPopPacket.id().get());
        }
    }

    public void handleUpdateTags(ClientboundUpdateTagsPacket clientboundUpdateTagsPacket) {
        ArrayList list = new ArrayList(clientboundUpdateTagsPacket.getTags().size());
        clientboundUpdateTagsPacket.getTags().forEach((resourceKey, networkPayload) -> {
            Registry registry = this.replayServer.registryAccess().lookupOrThrow(resourceKey);
            list.add(registry.prepareTagReload(networkPayload.resolve(registry)));
        });
        list.forEach(Registry.PendingTags::apply);
        this.forward((Packet<?>)clientboundUpdateTagsPacket);
    }

    public void handleStoreCookie(ClientboundStoreCookiePacket clientboundStoreCookiePacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundStoreCookiePacket);
    }

    public void handleTransfer(ClientboundTransferPacket clientboundTransferPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundTransferPacket);
    }

    public void handleCustomReportDetails(ClientboundCustomReportDetailsPacket clientboundCustomReportDetailsPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundCustomReportDetailsPacket);
    }

    public void handleServerLinks(ClientboundServerLinksPacket clientboundServerLinksPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundServerLinksPacket);
    }

    public void handleClearDialog(ClientboundClearDialogPacket clientboundClearDialogPacket) {
    }

    public void handleShowDialog(ClientboundShowDialogPacket clientboundShowDialogPacket) {
    }

    public void handleRequestCookie(ClientboundCookieRequestPacket clientboundCookieRequestPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundCookieRequestPacket);
    }

    public void handlePongResponse(ClientboundPongResponsePacket clientboundPongResponsePacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundPongResponsePacket);
    }

    public void handleTestInstanceBlockStatus(ClientboundTestInstanceBlockStatus clientboundTestInstanceBlockStatus) {
        throw new UnsupportedPacketException((Packet<?>)clientboundTestInstanceBlockStatus);
    }

    public void handleWaypoint(ClientboundTrackedWaypointPacket clientboundTrackedWaypointPacket) {
    }

    public void handleDebugChunkValue(ClientboundDebugChunkValuePacket clientboundDebugChunkValuePacket) {
    }

    public void handleDebugBlockValue(ClientboundDebugBlockValuePacket clientboundDebugBlockValuePacket) {
    }

    public void handleDebugEntityValue(ClientboundDebugEntityValuePacket clientboundDebugEntityValuePacket) {
    }

    public void handleDebugEvent(ClientboundDebugEventPacket clientboundDebugEventPacket) {
    }

    public void handleGameTestHighlightPos(ClientboundGameTestHighlightPosPacket clientboundGameTestHighlightPosPacket) {
    }

    public void handleGameRuleValues(ClientboundGameRuleValuesPacket packet) {
    }

    public void handleLowDiskSpaceWarning(ClientboundLowDiskSpaceWarningPacket packet) {
    }

    public void onDisconnect(DisconnectionDetails disconnectionDetails) {
    }

    public boolean isAcceptingMessages() {
        return false;
    }

    private /* synthetic */ void lambda$handleRemoveEntities$0(IntList forwardRemoveUnknown, int i) {
        Entity entity = this.level().getEntity(i);
        if (entity == null) {
            forwardRemoveUnknown.add(i);
        } else {
            entity.discard();
        }
    }
}

