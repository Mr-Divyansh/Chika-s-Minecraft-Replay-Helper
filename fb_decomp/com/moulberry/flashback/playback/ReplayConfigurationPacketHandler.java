/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Lifecycle
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.LayeredRegistryAccess
 *  net.minecraft.core.MappedRegistry
 *  net.minecraft.core.RegistrationInfo
 *  net.minecraft.core.Registry
 *  net.minecraft.core.Registry$PendingTags
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.RegistryAccess$Frozen
 *  net.minecraft.core.RegistryAccess$ImmutableRegistryAccess
 *  net.minecraft.core.RegistryAccess$RegistryEntry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.DisconnectionDetails
 *  net.minecraft.network.protocol.Packet
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
 *  net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener
 *  net.minecraft.network.protocol.configuration.ClientboundCodeOfConductPacket
 *  net.minecraft.network.protocol.configuration.ClientboundFinishConfigurationPacket
 *  net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket
 *  net.minecraft.network.protocol.configuration.ClientboundResetChatPacket
 *  net.minecraft.network.protocol.configuration.ClientboundSelectKnownPacks
 *  net.minecraft.network.protocol.configuration.ClientboundUpdateEnabledFeaturesPacket
 *  net.minecraft.network.protocol.cookie.ClientboundCookieRequestPacket
 *  net.minecraft.resources.Identifier
 *  net.minecraft.resources.RegistryDataLoader
 *  net.minecraft.resources.RegistryDataLoader$NetworkedRegistryData
 *  net.minecraft.resources.RegistryDataLoader$RegistryData
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.RegistryLayer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.network.ConfigurationTask
 *  net.minecraft.server.network.config.SynchronizeRegistriesTask
 *  net.minecraft.server.packs.PackLocationInfo
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.repository.KnownPack
 *  net.minecraft.server.packs.repository.PackRepository
 *  net.minecraft.server.packs.repository.ServerPacksSource
 *  net.minecraft.server.packs.resources.MultiPackResourceManager
 *  net.minecraft.server.packs.resources.ResourceProvider
 *  net.minecraft.tags.TagLoader
 *  net.minecraft.tags.TagLoader$LoadResult
 *  net.minecraft.tags.TagNetworkSerialization
 *  net.minecraft.tags.TagNetworkSerialization$NetworkPayload
 *  net.minecraft.util.Util
 *  net.minecraft.world.flag.FeatureFlagSet
 *  net.minecraft.world.flag.FeatureFlags
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.biome.Biomes
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.dimension.LevelStem
 */
package com.moulberry.flashback.playback;

import com.mojang.serialization.Lifecycle;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.exception.UnsupportedPacketException;
import com.moulberry.flashback.playback.EmptyLevelSource;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.registry.RegistryHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executor;
import net.minecraft.core.Holder;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.protocol.Packet;
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
import net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener;
import net.minecraft.network.protocol.configuration.ClientboundCodeOfConductPacket;
import net.minecraft.network.protocol.configuration.ClientboundFinishConfigurationPacket;
import net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket;
import net.minecraft.network.protocol.configuration.ClientboundResetChatPacket;
import net.minecraft.network.protocol.configuration.ClientboundSelectKnownPacks;
import net.minecraft.network.protocol.configuration.ClientboundUpdateEnabledFeaturesPacket;
import net.minecraft.network.protocol.cookie.ClientboundCookieRequestPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ConfigurationTask;
import net.minecraft.server.network.config.SynchronizeRegistriesTask;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.tags.TagLoader;
import net.minecraft.tags.TagNetworkSerialization;
import net.minecraft.util.Util;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;

public class ReplayConfigurationPacketHandler
implements ClientConfigurationPacketListener {
    private final ReplayServer replayServer;
    private Map<ResourceKey<? extends Registry<?>>, RegistryDataLoader.NetworkedRegistryData> pendingRegistryMap = null;
    private Map<ResourceKey<? extends Registry<?>>, TagNetworkSerialization.NetworkPayload> pendingTags = null;
    private PackRepository packRepository = null;
    private List<KnownPack> knownPacks = null;
    private List<String> knownPackIds = null;
    private FeatureFlagSet pendingFeatureFlags = null;
    private boolean pendingResetChat = false;
    private boolean dirty = false;

    public ReplayConfigurationPacketHandler(ReplayServer replayServer) {
        this.replayServer = replayServer;
    }

    public void flushPendingConfiguration() {
        if (!this.dirty) {
            return;
        }
        this.dirty = false;
        boolean sendTags = false;
        ArrayList<ConfigurationTask> configurationTasks = new ArrayList<ConfigurationTask>();
        FeatureFlagSet currentFeatureFlags = this.replayServer.worldData.enabledFeatures();
        ArrayList<Packet<? super ClientConfigurationPacketListener>> initialPackets = new ArrayList<Packet<? super ClientConfigurationPacketListener>>();
        if (this.pendingFeatureFlags != null) {
            if (!currentFeatureFlags.equals((Object)this.pendingFeatureFlags)) {
                currentFeatureFlags = this.pendingFeatureFlags;
                initialPackets.add((Packet<? super ClientConfigurationPacketListener>)new ClientboundUpdateEnabledFeaturesPacket(FeatureFlags.REGISTRY.toNames(this.pendingFeatureFlags)));
            }
            this.pendingFeatureFlags = null;
        }
        if (this.pendingResetChat) {
            initialPackets.add((Packet<? super ClientConfigurationPacketListener>)ClientboundResetChatPacket.INSTANCE);
            this.pendingResetChat = false;
        }
        ArrayList pendingTags = new ArrayList();
        if (this.pendingTags != null && !this.pendingTags.isEmpty()) {
            this.pendingTags.forEach((resourceKey, networkPayload) -> {
                RegistryDataLoader.NetworkedRegistryData entry;
                Registry registry = this.replayServer.registryAccess().lookupOrThrow(resourceKey);
                TagLoader.LoadResult loadResult = networkPayload.resolve(registry);
                if (this.pendingRegistryMap != null && (entry = this.pendingRegistryMap.get(resourceKey)) != null) {
                    this.pendingRegistryMap.put((ResourceKey<Registry<?>>)resourceKey, new RegistryDataLoader.NetworkedRegistryData(entry.elements(), networkPayload));
                }
                pendingTags.add(registry.prepareTagReload(loadResult));
            });
            pendingTags.forEach(Registry.PendingTags::apply);
            sendTags = true;
            this.pendingTags = null;
        }
        boolean synchronizeRegistries = false;
        if (this.pendingRegistryMap != null && !this.pendingRegistryMap.isEmpty()) {
            if (this.packRepository != null) {
                try (MultiPackResourceManager resourceManager = new MultiPackResourceManager(PackType.SERVER_DATA, this.packRepository.openAllSelected());){
                    synchronizeRegistries = this.tryUpdateRegistries(pendingTags, (ResourceProvider)resourceManager);
                }
            } else {
                synchronizeRegistries = this.tryUpdateRegistries(pendingTags, ResourceProvider.EMPTY);
            }
        }
        if (synchronizeRegistries) {
            configurationTasks.add((ConfigurationTask)new SynchronizeRegistriesTask(this.knownPacks != null ? this.knownPacks : List.of(), this.replayServer.registries));
        } else if (sendTags) {
            this.replayServer.getPlayerList().broadcastAll((Packet)new ClientboundUpdateTagsPacket(TagNetworkSerialization.serializeTagsToNetwork((LayeredRegistryAccess)this.replayServer.registries)));
        }
        if (initialPackets.isEmpty() && configurationTasks.isEmpty()) {
            return;
        }
        this.replayServer.updateRegistry(currentFeatureFlags, pendingTags, initialPackets, configurationTasks, this.knownPackIds);
        for (ServerPlayer player : new ArrayList(this.replayServer.getPlayerList().getPlayers())) {
            player.discard();
        }
        for (ServerLevel value : this.replayServer.levels.values()) {
            this.replayServer.closeLevel(value);
        }
        this.replayServer.levels.clear();
        this.replayServer.loadLevel();
    }

    private boolean tryUpdateRegistries(List<Registry.PendingTags<?>> pendingTags, ResourceProvider resourceProvider) {
        boolean hasRegistriesChanged;
        RegistryAccess.Frozen synchronizedRegistries;
        Map<ResourceKey<? extends Registry<?>>, RegistryDataLoader.NetworkedRegistryData> entries = this.pendingRegistryMap;
        this.pendingRegistryMap = null;
        List updatedLookups = TagLoader.buildUpdatedLookups((RegistryAccess.Frozen)this.replayServer.registryAccess(), pendingTags);
        try {
            synchronizedRegistries = (RegistryAccess.Frozen)RegistryDataLoader.load(entries, (ResourceProvider)resourceProvider, (List)updatedLookups, (List)RegistryDataLoader.SYNCHRONIZED_REGISTRIES, (Executor)Util.backgroundExecutor()).join();
        }
        catch (Exception e) {
            this.replayServer.failedToLoadRegistryDataWarning = true;
            Flashback.LOGGER.error("Error while trying to load registry data. Skipping... this might cause other issues", (Throwable)e);
            return false;
        }
        boolean bl = hasRegistriesChanged = !RegistryHelper.equals((RegistryAccess)this.replayServer.registryAccess(), (RegistryAccess)synchronizedRegistries, RegistryDataLoader.SYNCHRONIZED_REGISTRIES);
        if (hasRegistriesChanged) {
            LayeredRegistryAccess newRegistries = this.replayServer.registries;
            boolean replacedPreviousLayer = false;
            HashSet<ResourceKey> changedRegistries = new HashSet<ResourceKey>();
            for (RegistryDataLoader.RegistryData synchronizedRegistry : RegistryDataLoader.SYNCHRONIZED_REGISTRIES) {
                changedRegistries.add(synchronizedRegistry.key());
            }
            for (RegistryLayer layer : RegistryLayer.values()) {
                RegistryAccess.Frozen registriesForLayer = this.replayServer.registries.getLayer((Object)layer);
                ArrayList<Registry> registries = new ArrayList<Registry>();
                boolean replacedPartOfLayer = false;
                for (RegistryAccess.RegistryEntry registryEntry : registriesForLayer.registries().toList()) {
                    Optional overriden = synchronizedRegistries.lookup(registryEntry.key());
                    if (changedRegistries.contains(registryEntry.key()) && overriden.isPresent()) {
                        registries.add((Registry)overriden.get());
                        replacedPartOfLayer = true;
                        continue;
                    }
                    if (registryEntry.key() == Registries.LEVEL_STEM) {
                        try {
                            Optional dimensionsOpt = synchronizedRegistries.lookup(Registries.DIMENSION_TYPE);
                            Optional biomesOpt = synchronizedRegistries.lookup(Registries.BIOME);
                            if (dimensionsOpt.isPresent()) {
                                Registry dimensions = (Registry)dimensionsOpt.get();
                                Holder.Reference plains = biomesOpt.isPresent() ? ((Registry)biomesOpt.get()).getOrThrow(Biomes.PLAINS) : this.replayServer.registryAccess().lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.PLAINS);
                                MappedRegistry mapped = new MappedRegistry(Registries.LEVEL_STEM, Lifecycle.stable());
                                for (ResourceKey key : dimensions.registryKeySet()) {
                                    ResourceKey stemKey = ResourceKey.create((ResourceKey)Registries.LEVEL_STEM, (Identifier)key.identifier());
                                    LevelStem stem = new LevelStem((Holder)dimensions.getOrThrow(key), (ChunkGenerator)new EmptyLevelSource((Holder.Reference<Biome>)plains));
                                    mapped.register(stemKey, (Object)stem, RegistrationInfo.BUILT_IN);
                                }
                                replacedPartOfLayer = true;
                                registries.add(mapped.freeze());
                                continue;
                            }
                        }
                        catch (Exception ignored) {
                            registries.add(registryEntry.value());
                        }
                    }
                    registries.add(registryEntry.value());
                }
                if (replacedPartOfLayer) {
                    replacedPreviousLayer = true;
                    RegistryAccess.ImmutableRegistryAccess finalRegistries = new RegistryAccess.ImmutableRegistryAccess(registries);
                    newRegistries = newRegistries.replaceFrom((Object)layer, new RegistryAccess.Frozen[]{finalRegistries.freeze()});
                    continue;
                }
                if (!replacedPreviousLayer) continue;
                newRegistries = newRegistries.replaceFrom((Object)layer, new RegistryAccess.Frozen[]{registriesForLayer});
            }
            if (newRegistries != this.replayServer.registries) {
                this.replayServer.registries.keys = newRegistries.keys;
                this.replayServer.registries.values = newRegistries.values;
                ((RegistryAccess.ImmutableRegistryAccess)this.replayServer.registries.composite).registries = ((RegistryAccess.ImmutableRegistryAccess)newRegistries.composite).registries;
                return true;
            }
        }
        return false;
    }

    public void handleCodeOfConduct(ClientboundCodeOfConductPacket clientboundCodeOfConductPacket) {
    }

    public void handleConfigurationFinished(ClientboundFinishConfigurationPacket clientboundFinishConfigurationPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundFinishConfigurationPacket);
    }

    public void handleRegistryData(ClientboundRegistryDataPacket clientboundRegistryDataPacket) {
        this.dirty = true;
        if (this.pendingRegistryMap == null) {
            this.pendingRegistryMap = new HashMap();
        }
        this.pendingRegistryMap.put(clientboundRegistryDataPacket.registry(), new RegistryDataLoader.NetworkedRegistryData(clientboundRegistryDataPacket.entries(), TagNetworkSerialization.NetworkPayload.EMPTY));
    }

    public void handleEnabledFeatures(ClientboundUpdateEnabledFeaturesPacket clientboundUpdateEnabledFeaturesPacket) {
        this.dirty = true;
        this.pendingFeatureFlags = FeatureFlags.REGISTRY.fromNames((Iterable)clientboundUpdateEnabledFeaturesPacket.features());
    }

    public void handleSelectKnownPacks(ClientboundSelectKnownPacks clientboundSelectKnownPacks) {
        this.packRepository = ServerPacksSource.createVanillaTrustedRepository();
        this.packRepository.reload();
        HashMap packToId = new HashMap();
        this.packRepository.getAvailablePacks().forEach(pack -> {
            PackLocationInfo location = pack.location();
            location.knownPackInfo().ifPresent(knownPack -> packToId.put(knownPack, location.id()));
        });
        this.knownPacks = new ArrayList<KnownPack>();
        this.knownPackIds = new ArrayList<String>();
        for (KnownPack knownPack : clientboundSelectKnownPacks.knownPacks()) {
            String id = (String)packToId.get(knownPack);
            if (id == null) continue;
            this.knownPacks.add(knownPack);
            this.knownPackIds.add(id);
        }
        this.packRepository.setSelected(this.knownPackIds);
    }

    public void handleResetChat(ClientboundResetChatPacket clientboundResetChatPacket) {
        this.dirty = true;
        this.pendingResetChat = true;
    }

    public void handleKeepAlive(ClientboundKeepAlivePacket clientboundKeepAlivePacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundKeepAlivePacket);
    }

    public void handlePing(ClientboundPingPacket clientboundPingPacket) {
        throw new UnsupportedPacketException((Packet<?>)clientboundPingPacket);
    }

    public void handleCustomPayload(ClientboundCustomPayloadPacket clientboundCustomPayloadPacket) {
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
        this.dirty = true;
        if (this.pendingTags == null) {
            this.pendingTags = new HashMap();
        }
        this.pendingTags.putAll(clientboundUpdateTagsPacket.getTags());
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

    public void onDisconnect(DisconnectionDetails disconnectionDetails) {
    }

    public boolean isAcceptingMessages() {
        return false;
    }
}

