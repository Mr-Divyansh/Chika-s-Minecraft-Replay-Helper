/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.nodeditor;

import imgui.moulberry90.binding.ImGuiStructDestroyable;

public final class NodeEditorConfig
extends ImGuiStructDestroyable {
    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public String getSettingsFile() {
        return this.nGetSettingsFile();
    }

    public void setSettingsFile(String value) {
        this.nSetSettingsFile(value);
    }

    private native String nGetSettingsFile();

    private native void nSetSettingsFile(String var1);

    public int getCanvasSizeMode() {
        return this.nGetCanvasSizeMode();
    }

    public void setCanvasSizeMode(int value) {
        this.nSetCanvasSizeMode(value);
    }

    public native int nGetCanvasSizeMode();

    public native void nSetCanvasSizeMode(int var1);

    public int getDragButtonIndex() {
        return this.nGetDragButtonIndex();
    }

    public void setDragButtonIndex(int value) {
        this.nSetDragButtonIndex(value);
    }

    private native int nGetDragButtonIndex();

    private native void nSetDragButtonIndex(int var1);

    public int getSelectButtonIndex() {
        return this.nGetSelectButtonIndex();
    }

    public void setSelectButtonIndex(int value) {
        this.nSetSelectButtonIndex(value);
    }

    private native int nGetSelectButtonIndex();

    private native void nSetSelectButtonIndex(int var1);

    public int getNavigateButtonIndex() {
        return this.nGetNavigateButtonIndex();
    }

    public void setNavigateButtonIndex(int value) {
        this.nSetNavigateButtonIndex(value);
    }

    private native int nGetNavigateButtonIndex();

    private native void nSetNavigateButtonIndex(int var1);

    public int getContextMenuButtonIndex() {
        return this.nGetContextMenuButtonIndex();
    }

    public void setContextMenuButtonIndex(int value) {
        this.nSetContextMenuButtonIndex(value);
    }

    private native int nGetContextMenuButtonIndex();

    private native void nSetContextMenuButtonIndex(int var1);

    public boolean getEnableSmoothZoom() {
        return this.nGetEnableSmoothZoom();
    }

    public void setEnableSmoothZoom(boolean value) {
        this.nSetEnableSmoothZoom(value);
    }

    private native boolean nGetEnableSmoothZoom();

    private native void nSetEnableSmoothZoom(boolean var1);

    public float getSmoothZoomPower() {
        return this.nGetSmoothZoomPower();
    }

    public void setSmoothZoomPower(float value) {
        this.nSetSmoothZoomPower(value);
    }

    private native float nGetSmoothZoomPower();

    private native void nSetSmoothZoomPower(float var1);
}

