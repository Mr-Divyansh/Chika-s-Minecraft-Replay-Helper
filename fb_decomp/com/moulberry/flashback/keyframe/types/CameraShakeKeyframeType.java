/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.keyframe.types;

import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeCameraShake;
import com.moulberry.flashback.keyframe.impl.CameraShakeKeyframe;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import imgui.moulberry90.ImGui;
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.Nullable;

public class CameraShakeKeyframeType
implements KeyframeType<CameraShakeKeyframe> {
    public static CameraShakeKeyframeType INSTANCE = new CameraShakeKeyframeType();

    private CameraShakeKeyframeType() {
    }

    @Override
    public Class<? extends KeyframeChange> keyframeChangeType() {
        return KeyframeChangeCameraShake.class;
    }

    @Override
    @Nullable
    public String icon() {
        return "\uefeb";
    }

    @Override
    public String name() {
        return I18n.get((String)"flashback.keyframe.camera_shake", (Object[])new Object[0]);
    }

    @Override
    public String id() {
        return "CAMERA_SHAKE";
    }

    @Override
    @Nullable
    public CameraShakeKeyframe createDirect() {
        return null;
    }

    @Override
    public KeyframeType.KeyframeCreatePopup<CameraShakeKeyframe> createPopup() {
        boolean[] cameraShakeSplitXYKeyframeInput = new boolean[]{false};
        float[] cameraShakeFrequencyXKeyframeInput = new float[]{1.0f};
        float[] cameraShakeAmplitudeXKeyframeInput = new float[]{0.0f};
        float[] cameraShakeFrequencyYKeyframeInput = new float[]{1.0f};
        float[] cameraShakeAmplitudeYKeyframeInput = new float[]{0.0f};
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && editorState.replayVisuals.overrideCameraShake) {
            cameraShakeSplitXYKeyframeInput[0] = editorState.replayVisuals.cameraShakeSplitParams;
            cameraShakeFrequencyXKeyframeInput[0] = editorState.replayVisuals.cameraShakeXFrequency;
            cameraShakeAmplitudeXKeyframeInput[0] = editorState.replayVisuals.cameraShakeXAmplitude;
            cameraShakeFrequencyYKeyframeInput[0] = editorState.replayVisuals.cameraShakeYFrequency;
            cameraShakeAmplitudeYKeyframeInput[0] = editorState.replayVisuals.cameraShakeYAmplitude;
        }
        return () -> {
            if (ImGui.checkbox(I18n.get((String)"flashback.split_yx", (Object[])new Object[0]), cameraShakeSplitXYKeyframeInput[0])) {
                boolean bl = cameraShakeSplitXYKeyframeInput[0] = !cameraShakeSplitXYKeyframeInput[0];
                if (!cameraShakeSplitXYKeyframeInput[0]) {
                    cameraShakeFrequencyYKeyframeInput[0] = cameraShakeFrequencyXKeyframeInput[0];
                    cameraShakeAmplitudeYKeyframeInput[0] = cameraShakeAmplitudeXKeyframeInput[0];
                }
            }
            if (cameraShakeSplitXYKeyframeInput[0]) {
                ImGui.sliderFloat(I18n.get((String)"flashback.frequency_x", (Object[])new Object[0]), cameraShakeFrequencyXKeyframeInput, 0.1f, 10.0f, "%.1f");
                ImGui.sliderFloat(I18n.get((String)"flashback.amplitude_x", (Object[])new Object[0]), cameraShakeAmplitudeXKeyframeInput, 0.0f, 10.0f, "%.1f");
                ImGui.sliderFloat(I18n.get((String)"flashback.frequency_y", (Object[])new Object[0]), cameraShakeFrequencyYKeyframeInput, 0.1f, 10.0f, "%.1f");
                ImGui.sliderFloat(I18n.get((String)"flashback.amplitude_y", (Object[])new Object[0]), cameraShakeAmplitudeYKeyframeInput, 0.0f, 10.0f, "%.1f");
            } else {
                ImGui.sliderFloat(I18n.get((String)"flashback.frequency", (Object[])new Object[0]), cameraShakeFrequencyXKeyframeInput, 0.1f, 10.0f, "%.1f");
                ImGui.sliderFloat(I18n.get((String)"flashback.amplitude", (Object[])new Object[0]), cameraShakeAmplitudeXKeyframeInput, 0.0f, 10.0f, "%.1f");
            }
            if (ImGui.button(I18n.get((String)"flashback.add", (Object[])new Object[0])) || ReplayUI.consumeConfirm()) {
                if (cameraShakeSplitXYKeyframeInput[0]) {
                    return new CameraShakeKeyframe(cameraShakeFrequencyXKeyframeInput[0], cameraShakeAmplitudeXKeyframeInput[0], cameraShakeFrequencyYKeyframeInput[0], cameraShakeAmplitudeYKeyframeInput[0], true);
                }
                return new CameraShakeKeyframe(cameraShakeFrequencyXKeyframeInput[0], cameraShakeAmplitudeXKeyframeInput[0], cameraShakeFrequencyXKeyframeInput[0], cameraShakeAmplitudeXKeyframeInput[0], false);
            }
            ImGui.sameLine();
            if (ImGui.button(I18n.get((String)"gui.cancel", (Object[])new Object[0])) || ReplayUI.consumeCancel()) {
                ImGui.closeCurrentPopup();
            }
            return null;
        };
    }
}

