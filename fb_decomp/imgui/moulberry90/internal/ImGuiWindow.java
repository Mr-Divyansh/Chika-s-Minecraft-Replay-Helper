/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.internal;

import imgui.moulberry90.binding.ImGuiStruct;
import imgui.moulberry90.internal.ImGui;

public class ImGuiWindow
extends ImGuiStruct {
    public ImGuiWindow(long ptr) {
        super(ptr);
        ImGui.init();
    }

    public boolean getScrollbarX() {
        return this.nGetScrollbarX();
    }

    private native boolean nGetScrollbarX();

    public boolean getScrollbarY() {
        return this.nGetScrollbarY();
    }

    private native boolean nGetScrollbarY();
}

