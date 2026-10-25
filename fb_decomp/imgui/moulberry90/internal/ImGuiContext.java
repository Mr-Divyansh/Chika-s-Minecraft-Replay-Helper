/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.internal;

import imgui.moulberry90.binding.ImGuiStruct;
import imgui.moulberry90.internal.ImGui;

public class ImGuiContext
extends ImGuiStruct {
    public ImGuiContext(long ptr) {
        super(ptr);
        ImGui.init();
    }
}

