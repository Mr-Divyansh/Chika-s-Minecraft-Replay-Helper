/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.common.ClientboundClearDialogPacket
 *  net.minecraft.network.protocol.common.ClientboundCustomReportDetailsPacket
 *  net.minecraft.network.protocol.common.ClientboundDisconnectPacket
 *  net.minecraft.network.protocol.common.ClientboundKeepAlivePacket
 *  net.minecraft.network.protocol.common.ClientboundPingPacket
 *  net.minecraft.network.protocol.common.ClientboundServerLinksPacket
 *  net.minecraft.network.protocol.common.ClientboundShowDialogPacket
 *  net.minecraft.network.protocol.common.ClientboundStoreCookiePacket
 *  net.minecraft.network.protocol.common.ClientboundTransferPacket
 *  net.minecraft.network.protocol.configuration.ClientboundCodeOfConductPacket
 *  net.minecraft.network.protocol.configuration.ClientboundFinishConfigurationPacket
 *  net.minecraft.network.protocol.cookie.ClientboundCookieRequestPacket
 *  net.minecraft.network.protocol.game.ClientboundAwardStatsPacket
 *  net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket
 *  net.minecraft.network.protocol.game.ClientboundChunkBatchFinishedPacket
 *  net.minecraft.network.protocol.game.ClientboundChunkBatchStartPacket
 *  net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket
 *  net.minecraft.network.protocol.game.ClientboundCommandsPacket
 *  net.minecraft.network.protocol.game.ClientboundContainerClosePacket
 *  net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket
 *  net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket
 *  net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
 *  net.minecraft.network.protocol.game.ClientboundCooldownPacket
 *  net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket
 *  net.minecraft.network.protocol.game.ClientboundDebugBlockValuePacket
 *  net.minecraft.network.protocol.game.ClientboundDebugChunkValuePacket
 *  net.minecraft.network.protocol.game.ClientboundDebugEntityValuePacket
 *  net.minecraft.network.protocol.game.ClientboundDebugEventPacket
 *  net.minecraft.network.protocol.game.ClientboundDebugSamplePacket
 *  net.minecraft.network.protocol.game.ClientboundDeleteChatPacket
 *  net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket
 *  net.minecraft.network.protocol.game.ClientboundGameRuleValuesPacket
 *  net.minecraft.network.protocol.game.ClientboundLevelEventPacket
 *  net.minecraft.network.protocol.game.ClientboundLowDiskSpaceWarningPacket
 *  net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket
 *  net.minecraft.network.protocol.game.ClientboundMountScreenOpenPacket
 *  net.minecraft.network.protocol.game.ClientboundMoveEntityPacket$Pos
 *  net.minecraft.network.protocol.game.ClientboundMoveEntityPacket$PosRot
 *  net.minecraft.network.protocol.game.ClientboundMoveEntityPacket$Rot
 *  net.minecraft.network.protocol.game.ClientboundMoveMinecartPacket
 *  net.minecraft.network.protocol.game.ClientboundOpenBookPacket
 *  net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
 *  net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket
 *  net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerChatPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket
 *  net.minecraft.network.protocol.game.ClientboundRecipeBookAddPacket
 *  net.minecraft.network.protocol.game.ClientboundRecipeBookRemovePacket
 *  net.minecraft.network.protocol.game.ClientboundRecipeBookSettingsPacket
 *  net.minecraft.network.protocol.game.ClientboundRotateHeadPacket
 *  net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket
 *  net.minecraft.network.protocol.game.ClientboundSetCameraPacket
 *  net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket
 *  net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket
 *  net.minecraft.network.protocol.game.ClientboundSetCursorItemPacket
 *  net.minecraft.network.protocol.game.ClientboundSetExperiencePacket
 *  net.minecraft.network.protocol.game.ClientboundSetHealthPacket
 *  net.minecraft.network.protocol.game.ClientboundSetPlayerInventoryPacket
 *  net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket
 *  net.minecraft.network.protocol.game.ClientboundSoundEntityPacket
 *  net.minecraft.network.protocol.game.ClientboundSoundPacket
 *  net.minecraft.network.protocol.game.ClientboundStartConfigurationPacket
 *  net.minecraft.network.protocol.game.ClientboundTagQueryPacket
 *  net.minecraft.network.protocol.game.ClientboundTestInstanceBlockStatus
 *  net.minecraft.network.protocol.game.ClientboundTickingStatePacket
 *  net.minecraft.network.protocol.game.ClientboundTickingStepPacket
 *  net.minecraft.network.protocol.game.ClientboundTrackedWaypointPacket
 *  net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket
 *  net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket
 *  net.minecraft.network.protocol.ping.ClientboundPongResponsePacket
 */
package com.moulberry.flashback.record;

import java.util.Set;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundClearDialogPacket;
import net.minecraft.network.protocol.common.ClientboundCustomReportDetailsPacket;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.common.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.common.ClientboundPingPacket;
import net.minecraft.network.protocol.common.ClientboundServerLinksPacket;
import net.minecraft.network.protocol.common.ClientboundShowDialogPacket;
import net.minecraft.network.protocol.common.ClientboundStoreCookiePacket;
import net.minecraft.network.protocol.common.ClientboundTransferPacket;
import net.minecraft.network.protocol.configuration.ClientboundCodeOfConductPacket;
import net.minecraft.network.protocol.configuration.ClientboundFinishConfigurationPacket;
import net.minecraft.network.protocol.cookie.ClientboundCookieRequestPacket;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
import net.minecraft.network.protocol.game.ClientboundChunkBatchFinishedPacket;
import net.minecraft.network.protocol.game.ClientboundChunkBatchStartPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket;
import net.minecraft.network.protocol.game.ClientboundDebugBlockValuePacket;
import net.minecraft.network.protocol.game.ClientboundDebugChunkValuePacket;
import net.minecraft.network.protocol.game.ClientboundDebugEntityValuePacket;
import net.minecraft.network.protocol.game.ClientboundDebugEventPacket;
import net.minecraft.network.protocol.game.ClientboundDebugSamplePacket;
import net.minecraft.network.protocol.game.ClientboundDeleteChatPacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundGameRuleValuesPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLowDiskSpaceWarningPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundMountScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundMoveMinecartPacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRecipeBookAddPacket;
import net.minecraft.network.protocol.game.ClientboundRecipeBookRemovePacket;
import net.minecraft.network.protocol.game.ClientboundRecipeBookSettingsPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
import net.minecraft.network.protocol.game.ClientboundSetCursorItemPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerInventoryPacket;
import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStartConfigurationPacket;
import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
import net.minecraft.network.protocol.game.ClientboundTestInstanceBlockStatus;
import net.minecraft.network.protocol.game.ClientboundTickingStatePacket;
import net.minecraft.network.protocol.game.ClientboundTickingStepPacket;
import net.minecraft.network.protocol.game.ClientboundTrackedWaypointPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.ping.ClientboundPongResponsePacket;

public class IgnoredPacketSet {
    private static final Set<Class<?>> IGNORED_IN_REPLAY = Set.of(ClientboundAwardStatsPacket.class, ClientboundRecipeBookAddPacket.class, ClientboundRecipeBookRemovePacket.class, ClientboundRecipeBookSettingsPacket.class, ClientboundUpdateRecipesPacket.class, ClientboundTransferPacket.class, ClientboundUpdateAdvancementsPacket.class, ClientboundClearDialogPacket.class, ClientboundShowDialogPacket.class, ClientboundTrackedWaypointPacket.class);
    private static final Set<Class<?>> IGNORED = Set.of(ClientboundLevelEventPacket.class, ClientboundSoundPacket.class, ClientboundSoundEntityPacket.class, ClientboundStoreCookiePacket.class, ClientboundCustomReportDetailsPacket.class, ClientboundServerLinksPacket.class, ClientboundCookieRequestPacket.class, ClientboundDisconnectPacket.class, ClientboundPingPacket.class, ClientboundKeepAlivePacket.class, ClientboundTransferPacket.class, ClientboundClearDialogPacket.class, ClientboundShowDialogPacket.class, ClientboundFinishConfigurationPacket.class, ClientboundCodeOfConductPacket.class, ClientboundAwardStatsPacket.class, ClientboundRecipeBookAddPacket.class, ClientboundRecipeBookRemovePacket.class, ClientboundRecipeBookSettingsPacket.class, ClientboundOpenSignEditorPacket.class, ClientboundRotateHeadPacket.class, ClientboundMoveEntityPacket.Pos.class, ClientboundMoveEntityPacket.Rot.class, ClientboundMoveEntityPacket.PosRot.class, ClientboundPlayerPositionPacket.class, ClientboundPlayerChatPacket.class, ClientboundDeleteChatPacket.class, ClientboundMoveMinecartPacket.class, ClientboundContainerClosePacket.class, ClientboundContainerSetContentPacket.class, ClientboundMountScreenOpenPacket.class, ClientboundContainerSetDataPacket.class, ClientboundContainerSetSlotPacket.class, ClientboundForgetLevelChunkPacket.class, ClientboundPlayerAbilitiesPacket.class, ClientboundSetCursorItemPacket.class, ClientboundSetExperiencePacket.class, ClientboundSetHealthPacket.class, ClientboundSetPlayerInventoryPacket.class, ClientboundTickingStatePacket.class, ClientboundTickingStepPacket.class, ClientboundPlayerCombatEndPacket.class, ClientboundPlayerCombatEnterPacket.class, ClientboundPlayerCombatKillPacket.class, ClientboundSetCameraPacket.class, ClientboundCooldownPacket.class, ClientboundUpdateAdvancementsPacket.class, ClientboundSelectAdvancementsTabPacket.class, ClientboundPlaceGhostRecipePacket.class, ClientboundCommandsPacket.class, ClientboundCommandSuggestionsPacket.class, ClientboundUpdateRecipesPacket.class, ClientboundTagQueryPacket.class, ClientboundOpenBookPacket.class, ClientboundOpenScreenPacket.class, ClientboundMerchantOffersPacket.class, ClientboundSetChunkCacheRadiusPacket.class, ClientboundSetSimulationDistancePacket.class, ClientboundSetChunkCacheCenterPacket.class, ClientboundBlockChangedAckPacket.class, ClientboundCustomChatCompletionsPacket.class, ClientboundStartConfigurationPacket.class, ClientboundChunkBatchStartPacket.class, ClientboundChunkBatchFinishedPacket.class, ClientboundDebugSamplePacket.class, ClientboundPongResponsePacket.class, ClientboundTestInstanceBlockStatus.class, ClientboundTrackedWaypointPacket.class, ClientboundDebugChunkValuePacket.class, ClientboundDebugBlockValuePacket.class, ClientboundDebugEntityValuePacket.class, ClientboundDebugEventPacket.class, ClientboundGameRuleValuesPacket.class, ClientboundLowDiskSpaceWarningPacket.class);

    public static boolean isIgnored(Packet<?> packet) {
        return IGNORED.contains(packet.getClass());
    }

    public static boolean isIgnoredInReplay(Packet<?> packet) {
        return IGNORED_IN_REPLAY.contains(packet.getClass());
    }
}

