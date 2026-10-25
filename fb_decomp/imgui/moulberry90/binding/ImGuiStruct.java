/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.binding;

import imgui.moulberry90.ImGui;

public abstract class ImGuiStruct {
    public long ptr;

    public ImGuiStruct(long ptr) {
        ImGui.init();
        this.ptr = ptr;
    }

    public final boolean isValidPtr() {
        return this.ptr != 0L;
    }

    public final boolean isNotValidPtr() {
        return !this.isValidPtr();
    }
}

