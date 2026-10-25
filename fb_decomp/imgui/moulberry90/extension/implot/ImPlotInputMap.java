/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.implot;

import imgui.moulberry90.binding.ImGuiStructDestroyable;

public final class ImPlotInputMap
extends ImGuiStructDestroyable {
    public ImPlotInputMap() {
    }

    public ImPlotInputMap(long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public int getPan() {
        return this.nGetPan();
    }

    public void setPan(int value) {
        this.nSetPan(value);
    }

    private native int nGetPan();

    private native void nSetPan(int var1);

    public int getPanMod() {
        return this.nGetPanMod();
    }

    public void setPanMod(int value) {
        this.nSetPanMod(value);
    }

    private native int nGetPanMod();

    private native void nSetPanMod(int var1);

    public int getFit() {
        return this.nGetFit();
    }

    public void setFit(int value) {
        this.nSetFit(value);
    }

    private native int nGetFit();

    private native void nSetFit(int var1);

    public int getSelect() {
        return this.nGetSelect();
    }

    public void setSelect(int value) {
        this.nSetSelect(value);
    }

    private native int nGetSelect();

    private native void nSetSelect(int var1);

    public int getSelectCancel() {
        return this.nGetSelectCancel();
    }

    public void setSelectCancel(int value) {
        this.nSetSelectCancel(value);
    }

    private native int nGetSelectCancel();

    private native void nSetSelectCancel(int var1);

    public int getSelectMod() {
        return this.nGetSelectMod();
    }

    public void setSelectMod(int value) {
        this.nSetSelectMod(value);
    }

    private native int nGetSelectMod();

    private native void nSetSelectMod(int var1);

    public int getSelectHorzMod() {
        return this.nGetSelectHorzMod();
    }

    public void setSelectHorzMod(int value) {
        this.nSetSelectHorzMod(value);
    }

    private native int nGetSelectHorzMod();

    private native void nSetSelectHorzMod(int var1);

    public int getSelectVertMod() {
        return this.nGetSelectVertMod();
    }

    public void setSelectVertMod(int value) {
        this.nSetSelectVertMod(value);
    }

    private native int nGetSelectVertMod();

    private native void nSetSelectVertMod(int var1);

    public int getMenu() {
        return this.nGetMenu();
    }

    public void setMenu(int value) {
        this.nSetMenu(value);
    }

    private native int nGetMenu();

    private native void nSetMenu(int var1);

    public int getOverrideMod() {
        return this.nGetOverrideMod();
    }

    public void setOverrideMod(int value) {
        this.nSetOverrideMod(value);
    }

    private native int nGetOverrideMod();

    private native void nSetOverrideMod(int var1);

    public int getZoomMod() {
        return this.nGetZoomMod();
    }

    public void setZoomMod(int value) {
        this.nSetZoomMod(value);
    }

    private native int nGetZoomMod();

    private native void nSetZoomMod(int var1);

    public float getZoomRate() {
        return this.nGetZoomRate();
    }

    public void setZoomRate(float value) {
        this.nSetZoomRate(value);
    }

    private native float nGetZoomRate();

    private native void nSetZoomRate(float var1);
}

