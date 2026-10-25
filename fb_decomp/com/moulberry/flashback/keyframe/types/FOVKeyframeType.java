/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.language.I18n
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.keyframe.types;

import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeFov;
import com.moulberry.flashback.keyframe.impl.FOVKeyframe;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import imgui.moulberry90.ImGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.Nullable;

public class FOVKeyframeType
implements KeyframeType<FOVKeyframe> {
    public static FOVKeyframeType INSTANCE = new FOVKeyframeType();

    private FOVKeyframeType() {
    }

    @Override
    public Class<? extends KeyframeChange> keyframeChangeType() {
        return KeyframeChangeFov.class;
    }

    @Override
    @Nullable
    public String icon() {
        return "\ue3af";
    }

    @Override
    public String name() {
        return I18n.get((String)"flashback.keyframe.fov", (Object[])new Object[0]);
    }

    @Override
    public String id() {
        return "FOV";
    }

    @Override
    @Nullable
    public FOVKeyframe createDirect() {
        return null;
    }

    @Override
    public KeyframeType.KeyframeCreatePopup<FOVKeyframe> createPopup() {
        float[] fovKeyframeInput = new float[]{((Integer)Minecraft.getInstance().options.fov().get()).intValue()};
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && editorState.replayVisuals.overrideFov) {
            fovKeyframeInput[0] = editorState.replayVisuals.overrideFovAmount;
        }
        return () -> {
            ImGui.sliderFloat(I18n.get((String)"flashback.fov", (Object[])new Object[0]), fovKeyframeInput, 1.0f, 110.0f);
            if (ImGui.button(I18n.get((String)"flashback.add", (Object[])new Object[0])) || ReplayUI.consumeConfirm()) {
                return new FOVKeyframe(fovKeyframeInput[0]);
            }
            ImGui.sameLine();
            if (ImGui.button(I18n.get((String)"gui.cancel", (Object[])new Object[0])) || ReplayUI.consumeCancel()) {
                ImGui.closeCurrentPopup();
            }
            return null;
        };
    }
}

