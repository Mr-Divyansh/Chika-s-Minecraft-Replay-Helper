/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3d
 */
package com.moulberry.flashback.keyframe.types;

import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeCameraPositionOrbit;
import com.moulberry.flashback.keyframe.impl.CameraOrbitKeyframe;
import imgui.moulberry90.ImGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

public class CameraOrbitKeyframeType
implements KeyframeType<CameraOrbitKeyframe> {
    public static CameraOrbitKeyframeType INSTANCE = new CameraOrbitKeyframeType();

    private CameraOrbitKeyframeType() {
    }

    @Override
    public Class<? extends KeyframeChange> keyframeChangeType() {
        return KeyframeChangeCameraPositionOrbit.class;
    }

    @Override
    @Nullable
    public String icon() {
        return "\ue577";
    }

    @Override
    public String name() {
        return I18n.get((String)"flashback.keyframe.camera_orbit", (Object[])new Object[0]);
    }

    @Override
    public String id() {
        return "CAMERA_ORBIT";
    }

    @Override
    @Nullable
    public CameraOrbitKeyframe createDirect() {
        return null;
    }

    @Override
    public KeyframeType.KeyframeCreatePopup<CameraOrbitKeyframe> createPopup() {
        float[] cameraOrbitCenter = new float[]{0.0f, 0.0f, 0.0f};
        float[] cameraOrbitDistance = new float[]{8.0f};
        float[] cameraOrbitYaw = new float[]{0.0f};
        float[] cameraOrbitPitch = new float[]{0.0f};
        Entity entity = Minecraft.getInstance().getCameraEntity();
        if (entity != null) {
            Vec3 eyePosition = entity.getEyePosition();
            cameraOrbitCenter[0] = (float)eyePosition.x;
            cameraOrbitCenter[1] = (float)eyePosition.y;
            cameraOrbitCenter[2] = (float)eyePosition.z;
        }
        return () -> {
            ImGuiHelper.inputFloat(I18n.get((String)"flashback.position", (Object[])new Object[0]), cameraOrbitCenter);
            ImGuiHelper.inputFloat(I18n.get((String)"flashback.distance", (Object[])new Object[0]), cameraOrbitDistance);
            ImGuiHelper.inputFloat(I18n.get((String)"flashback.yaw", (Object[])new Object[0]), cameraOrbitYaw);
            ImGuiHelper.inputFloat(I18n.get((String)"flashback.pitch", (Object[])new Object[0]), cameraOrbitPitch);
            if (ImGui.button(I18n.get((String)"flashback.add", (Object[])new Object[0])) || ReplayUI.consumeConfirm()) {
                Vector3d center = new Vector3d((double)cameraOrbitCenter[0], (double)cameraOrbitCenter[1], (double)cameraOrbitCenter[2]);
                return new CameraOrbitKeyframe(center, cameraOrbitDistance[0], cameraOrbitYaw[0], cameraOrbitPitch[0]);
            }
            ImGui.sameLine();
            if (ImGui.button(I18n.get((String)"gui.cancel", (Object[])new Object[0])) || ReplayUI.consumeCancel()) {
                ImGui.closeCurrentPopup();
            }
            return null;
        };
    }
}

