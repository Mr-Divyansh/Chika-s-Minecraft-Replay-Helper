/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.imnodes;

import imgui.moulberry90.binding.ImGuiStruct;

public final class ImNodesIO
extends ImGuiStruct {
    public ImNodesIO(long ptr) {
        super(ptr);
    }

    public int getAltMouseButton() {
        return this.nGetAltMouseButton();
    }

    public void setAltMouseButton(int value) {
        this.nSetAltMouseButton(value);
    }

    private native int nGetAltMouseButton();

    private native void nSetAltMouseButton(int var1);

    public float getAutoPanningSpeed() {
        return this.nGetAutoPanningSpeed();
    }

    public void setAutoPanningSpeed(float value) {
        this.nSetAutoPanningSpeed(value);
    }

    private native float nGetAutoPanningSpeed();

    private native void nSetAutoPanningSpeed(float var1);
}

