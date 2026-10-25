/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.keyframe.types;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeTickrate;
import com.moulberry.flashback.keyframe.impl.TickrateKeyframe;
import com.moulberry.flashback.playback.ReplayServer;
import imgui.moulberry90.ImGui;
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.Nullable;

public class SpeedKeyframeType
implements KeyframeType<TickrateKeyframe> {
    public static SpeedKeyframeType INSTANCE = new SpeedKeyframeType();

    private SpeedKeyframeType() {
    }

    @Override
    public Class<? extends KeyframeChange> keyframeChangeType() {
        return KeyframeChangeTickrate.class;
    }

    @Override
    @Nullable
    public String icon() {
        return "\ue9e4";
    }

    @Override
    public String name() {
        return I18n.get((String)"flashback.keyframe.speed", (Object[])new Object[0]);
    }

    @Override
    public String id() {
        return "SPEED";
    }

    @Override
    @Nullable
    public TickrateKeyframe createDirect() {
        return null;
    }

    @Override
    public KeyframeType.KeyframeCreatePopup<TickrateKeyframe> createPopup() {
        float[] speedKeyframeInput = new float[]{1.0f};
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer != null) {
            speedKeyframeInput[0] = replayServer.getDesiredTickRate(false) / 20.0f;
        }
        return () -> {
            ImGui.sliderFloat(I18n.get((String)"flashback.keyframe.speed", (Object[])new Object[0]), speedKeyframeInput, 0.1f, 10.0f);
            if (ImGui.button(I18n.get((String)"flashback.add", (Object[])new Object[0])) || ReplayUI.consumeConfirm()) {
                return new TickrateKeyframe(speedKeyframeInput[0] * 20.0f);
            }
            ImGui.sameLine();
            if (ImGui.button(I18n.get((String)"gui.cancel", (Object[])new Object[0])) || ReplayUI.consumeCancel()) {
                ImGui.closeCurrentPopup();
            }
            return null;
        };
    }
}

