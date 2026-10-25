/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.keyframe.types;

import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeFreeze;
import com.moulberry.flashback.keyframe.impl.FreezeKeyframe;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.type.ImBoolean;
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.Nullable;

public class FreezeKeyframeType
implements KeyframeType<FreezeKeyframe> {
    public static FreezeKeyframeType INSTANCE = new FreezeKeyframeType();

    private FreezeKeyframeType() {
    }

    @Override
    public Class<? extends KeyframeChange> keyframeChangeType() {
        return KeyframeChangeFreeze.class;
    }

    @Override
    @Nullable
    public String icon() {
        return "\ueb3b";
    }

    @Override
    public String name() {
        return I18n.get((String)"flashback.keyframe.freeze", (Object[])new Object[0]);
    }

    @Override
    public String id() {
        return "FREEZE";
    }

    @Override
    @Nullable
    public FreezeKeyframe createDirect() {
        return null;
    }

    @Override
    public KeyframeType.KeyframeCreatePopup<FreezeKeyframe> createPopup() {
        ImBoolean frozen = new ImBoolean();
        int[] delay = new int[]{0};
        return () -> {
            ImGui.checkbox(I18n.get((String)"flashback.frozen", (Object[])new Object[0]), frozen);
            ImGui.sliderInt(I18n.get((String)"flashback.delay", (Object[])new Object[0]), delay, 0, 10);
            ImGuiHelper.tooltip(I18n.get((String)"flashback.freeze_delay_tooltip", (Object[])new Object[0]));
            delay[0] = Math.max(0, Math.min(10, delay[0]));
            if (ImGui.button(I18n.get((String)"flashback.add", (Object[])new Object[0])) || ReplayUI.consumeConfirm()) {
                return new FreezeKeyframe(frozen.get(), delay[0]);
            }
            ImGui.sameLine();
            if (ImGui.button(I18n.get((String)"gui.cancel", (Object[])new Object[0])) || ReplayUI.consumeCancel()) {
                ImGui.closeCurrentPopup();
            }
            return null;
        };
    }
}

