/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.language.I18n
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.keyframe.types;

import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeTimeOfDay;
import com.moulberry.flashback.keyframe.impl.TimeOfDayKeyframe;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import imgui.moulberry90.ImGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.Nullable;

public class TimeOfDayKeyframeType
implements KeyframeType<TimeOfDayKeyframe> {
    public static TimeOfDayKeyframeType INSTANCE = new TimeOfDayKeyframeType();

    private TimeOfDayKeyframeType() {
    }

    @Override
    public Class<? extends KeyframeChange> keyframeChangeType() {
        return KeyframeChangeTimeOfDay.class;
    }

    @Override
    @Nullable
    public String icon() {
        return "\ue518";
    }

    @Override
    public String name() {
        return I18n.get((String)"flashback.keyframe.time_of_day", (Object[])new Object[0]);
    }

    @Override
    public String id() {
        return "TIME_OF_DAY";
    }

    @Override
    @Nullable
    public TimeOfDayKeyframe createDirect() {
        return null;
    }

    @Override
    public KeyframeType.KeyframeCreatePopup<TimeOfDayKeyframe> createPopup() {
        EditorState editorState = EditorStateManager.getCurrent();
        int[] timeOfDayKeyframeInput = new int[]{editorState != null && editorState.replayVisuals.overrideTimeOfDay >= 0L ? (int)editorState.replayVisuals.overrideTimeOfDay : (int)(Minecraft.getInstance().level.getDefaultClockTime() % 24000L)};
        return () -> {
            ImGuiHelper.inputInt(I18n.get((String)"flashback.time", (Object[])new Object[0]), timeOfDayKeyframeInput);
            if (ImGui.button(I18n.get((String)"flashback.add", (Object[])new Object[0])) || ReplayUI.consumeConfirm()) {
                return new TimeOfDayKeyframe(timeOfDayKeyframeInput[0]);
            }
            ImGui.sameLine();
            if (ImGui.button(I18n.get((String)"gui.cancel", (Object[])new Object[0])) || ReplayUI.consumeCancel()) {
                ImGui.closeCurrentPopup();
            }
            return null;
        };
    }
}

