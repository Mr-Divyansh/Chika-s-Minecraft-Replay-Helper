/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90;

import imgui.moulberry90.binding.ImGuiStruct;

public final class ImGuiTableColumnSortSpecs
extends ImGuiStruct {
    public ImGuiTableColumnSortSpecs(long ptr) {
        super(ptr);
    }

    public int getColumnUserID() {
        return this.nGetColumnUserID();
    }

    private native int nGetColumnUserID();

    public int getColumnIndex() {
        return this.nGetColumnIndex();
    }

    private native int nGetColumnIndex();

    public int getSortOrder() {
        return this.nGetSortOrder();
    }

    private native int nGetSortOrder();

    public int getSortDirection() {
        return this.nGetSortDirection();
    }

    private native int nGetSortDirection();
}

