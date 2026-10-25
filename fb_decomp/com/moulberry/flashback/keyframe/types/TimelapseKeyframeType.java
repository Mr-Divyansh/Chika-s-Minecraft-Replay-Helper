/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.keyframe.types;

import com.moulberry.flashback.Utils;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeTickrate;
import com.moulberry.flashback.keyframe.impl.TimelapseKeyframe;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.type.ImString;
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.Nullable;

public class TimelapseKeyframeType
implements KeyframeType<TimelapseKeyframe> {
    public static TimelapseKeyframeType INSTANCE = new TimelapseKeyframeType();

    private TimelapseKeyframeType() {
    }

    @Override
    public Class<? extends KeyframeChange> keyframeChangeType() {
        return KeyframeChangeTickrate.class;
    }

    @Override
    @Nullable
    public String icon() {
        return "\ue422";
    }

    @Override
    public String name() {
        return I18n.get((String)"flashback.keyframe.timelapse", (Object[])new Object[0]);
    }

    @Override
    public String id() {
        return "TIMELAPSE";
    }

    @Override
    public boolean allowChangingInterpolationType() {
        return false;
    }

    @Override
    public boolean allowChangingTimelineTick() {
        return false;
    }

    @Override
    public boolean neverApplyLastKeyframe() {
        return true;
    }

    @Override
    @Nullable
    public TimelapseKeyframe createDirect() {
        return null;
    }

    @Override
    public KeyframeType.KeyframeCreatePopup<TimelapseKeyframe> createPopup() {
        ImString timelapseKeyframeInput = ImGuiHelper.createResizableImString("1s");
        timelapseKeyframeInput.inputData.allowedChars = "0123456789tsmh.";
        return () -> {
            ImGui.inputText(I18n.get((String)"flashback.time", (Object[])new Object[0]), timelapseKeyframeInput);
            if (ImGui.button(I18n.get((String)"flashback.add", (Object[])new Object[0])) || ReplayUI.consumeConfirm()) {
                return new TimelapseKeyframe(Utils.stringToTime(ImGuiHelper.getString(timelapseKeyframeInput)));
            }
            ImGui.sameLine();
            if (ImGui.button(I18n.get((String)"gui.cancel", (Object[])new Object[0])) || ReplayUI.consumeCancel()) {
                ImGui.closeCurrentPopup();
            }
            return null;
        };
    }
}

