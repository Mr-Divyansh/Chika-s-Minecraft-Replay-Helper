/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90;

import imgui.moulberry90.ImGuiTableColumnSortSpecs;
import imgui.moulberry90.binding.ImGuiStruct;

public final class ImGuiTableSortSpecs
extends ImGuiStruct {
    public ImGuiTableSortSpecs(long ptr) {
        super(ptr);
    }

    public ImGuiTableColumnSortSpecs[] getSpecs() {
        long[] specsPointers = this.nGetSpecs();
        ImGuiTableColumnSortSpecs[] specs = new ImGuiTableColumnSortSpecs[specsPointers.length];
        for (int i = 0; i < specsPointers.length; ++i) {
            specs[i] = new ImGuiTableColumnSortSpecs(specsPointers[i]);
        }
        return specs;
    }

    private native long[] nGetSpecs();

    public int getSpecsCount() {
        return this.nGetSpecsCount();
    }

    private native int nGetSpecsCount();

    public boolean getSpecsDirty() {
        return this.nGetSpecsDirty();
    }

    public void setSpecsDirty(boolean value) {
        this.nSetSpecsDirty(value);
    }

    private native boolean nGetSpecsDirty();

    private native void nSetSpecsDirty(boolean var1);
}

