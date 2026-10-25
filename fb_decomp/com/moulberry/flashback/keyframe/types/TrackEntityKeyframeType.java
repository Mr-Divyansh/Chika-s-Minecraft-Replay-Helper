/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3d
 */
package com.moulberry.flashback.keyframe.types;

import com.moulberry.flashback.combo_options.TrackingBodyPart;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeTrackEntity;
import com.moulberry.flashback.keyframe.impl.TrackEntityKeyframe;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.type.ImString;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

public class TrackEntityKeyframeType
implements KeyframeType<TrackEntityKeyframe> {
    public static TrackEntityKeyframeType INSTANCE = new TrackEntityKeyframeType();

    private TrackEntityKeyframeType() {
    }

    @Override
    public Class<? extends KeyframeChange> keyframeChangeType() {
        return KeyframeChangeTrackEntity.class;
    }

    @Override
    @Nullable
    public String icon() {
        return "\ue55f";
    }

    @Override
    public String name() {
        return I18n.get((String)"flashback.keyframe.track_entity", (Object[])new Object[0]);
    }

    @Override
    public String id() {
        return "TRACK_ENTITY";
    }

    @Override
    @Nullable
    public TrackEntityKeyframe createDirect() {
        return null;
    }

    @Override
    public KeyframeType.KeyframeCreatePopup<TrackEntityKeyframe> createPopup() {
        CameraTrackTargetData data = new CameraTrackTargetData();
        return () -> {
            if (ImGui.inputText(I18n.get((String)"flashback.entity_uuid", (Object[])new Object[0]), data.cameraTrackTarget)) {
                data.validEntityTarget = null;
                try {
                    Entity entity;
                    String uuidStr = ImGuiHelper.getString(data.cameraTrackTarget);
                    UUID uuid = UUID.fromString(uuidStr);
                    ClientLevel level = Minecraft.getInstance().level;
                    if (level != null && (entity = (Entity)level.getEntities().get(uuid)) != null && entity != Minecraft.getInstance().player) {
                        data.validEntityTarget = uuid;
                    }
                }
                catch (Exception uuidStr) {
                    // empty catch block
                }
            }
            data.trackingBodyPart = ImGuiHelper.enumCombo(I18n.get((String)"flashback.body_part", (Object[])new Object[0]), data.trackingBodyPart);
            ImGuiHelper.inputFloat(I18n.get((String)"flashback.yaw_offset", (Object[])new Object[0]), data.cameraTrackYaw);
            ImGuiHelper.inputFloat(I18n.get((String)"flashback.pitch_offset", (Object[])new Object[0]), data.cameraTrackPitch);
            ImGuiHelper.inputFloat(I18n.get((String)"flashback.position_offset", (Object[])new Object[0]), data.positionOffset);
            ImGuiHelper.inputFloat(I18n.get((String)"flashback.view_offset", (Object[])new Object[0]), data.viewOffset);
            ImGuiHelper.inputFloat(I18n.get((String)"flashback.roll", (Object[])new Object[0]), data.roll);
            if (data.validEntityTarget == null) {
                ImGui.beginDisabled();
            }
            if ((ImGui.button(I18n.get((String)"flashback.add", (Object[])new Object[0])) || ReplayUI.consumeConfirm()) && data.validEntityTarget != null) {
                Vector3d positionOffset = new Vector3d((double)data.positionOffset[0], (double)data.positionOffset[1], (double)data.positionOffset[2]);
                Vector3d viewOffset = new Vector3d((double)data.viewOffset[0], (double)data.viewOffset[1], (double)data.viewOffset[2]);
                return new TrackEntityKeyframe(data.validEntityTarget, data.trackingBodyPart, data.cameraTrackYaw[0], data.cameraTrackPitch[0], positionOffset, viewOffset, data.roll[0]);
            }
            if (data.validEntityTarget == null) {
                ImGui.endDisabled();
            }
            ImGui.sameLine();
            if (ImGui.button(I18n.get((String)"gui.cancel", (Object[])new Object[0])) || ReplayUI.consumeCancel()) {
                ImGui.closeCurrentPopup();
            }
            return null;
        };
    }

    private static class CameraTrackTargetData {
        float[] positionOffset = new float[]{0.0f, 0.0f, 0.0f};
        float[] viewOffset = new float[]{0.0f, 0.0f, 3.0f};
        float[] cameraTrackYaw = new float[]{0.0f};
        float[] cameraTrackPitch = new float[]{0.0f};
        float[] roll = new float[]{0.0f};
        TrackingBodyPart trackingBodyPart = TrackingBodyPart.HEAD;
        ImString cameraTrackTarget = new ImString();
        UUID validEntityTarget = null;

        private CameraTrackTargetData() {
        }
    }
}

