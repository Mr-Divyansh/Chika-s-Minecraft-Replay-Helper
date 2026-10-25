/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.memedit;

import imgui.moulberry90.binding.ImGuiStructDestroyable;

public final class MemoryEditorSizes
extends ImGuiStructDestroyable {
    public MemoryEditorSizes() {
    }

    public MemoryEditorSizes(long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public int getAddrDigitsCount() {
        return this.nGetAddrDigitsCount();
    }

    public void setAddrDigitsCount(int value) {
        this.nSetAddrDigitsCount(value);
    }

    private native int nGetAddrDigitsCount();

    private native void nSetAddrDigitsCount(int var1);

    public float getLineHeight() {
        return this.nGetLineHeight();
    }

    public void setLineHeight(float value) {
        this.nSetLineHeight(value);
    }

    private native float nGetLineHeight();

    private native void nSetLineHeight(float var1);

    public float getGlyphWidth() {
        return this.nGetGlyphWidth();
    }

    public void setGlyphWidth(float value) {
        this.nSetGlyphWidth(value);
    }

    private native float nGetGlyphWidth();

    private native void nSetGlyphWidth(float var1);

    public float getHexCellWidth() {
        return this.nGetHexCellWidth();
    }

    public void setHexCellWidth(float value) {
        this.nSetHexCellWidth(value);
    }

    private native float nGetHexCellWidth();

    private native void nSetHexCellWidth(float var1);

    public float getSpacingBetweenMidCols() {
        return this.nGetSpacingBetweenMidCols();
    }

    public void setSpacingBetweenMidCols(float value) {
        this.nSetSpacingBetweenMidCols(value);
    }

    private native float nGetSpacingBetweenMidCols();

    private native void nSetSpacingBetweenMidCols(float var1);

    public float getPosHexStart() {
        return this.nGetPosHexStart();
    }

    public void setPosHexStart(float value) {
        this.nSetPosHexStart(value);
    }

    private native float nGetPosHexStart();

    private native void nSetPosHexStart(float var1);

    public float getPosHexEnd() {
        return this.nGetPosHexEnd();
    }

    public void setPosHexEnd(float value) {
        this.nSetPosHexEnd(value);
    }

    private native float nGetPosHexEnd();

    private native void nSetPosHexEnd(float var1);

    public float getPosAsciiStart() {
        return this.nGetPosAsciiStart();
    }

    public void setPosAsciiStart(float value) {
        this.nSetPosAsciiStart(value);
    }

    private native float nGetPosAsciiStart();

    private native void nSetPosAsciiStart(float var1);

    public float getPosAsciiEnd() {
        return this.nGetPosAsciiEnd();
    }

    public void setPosAsciiEnd(float value) {
        this.nSetPosAsciiEnd(value);
    }

    private native float nGetPosAsciiEnd();

    private native void nSetPosAsciiEnd(float var1);

    public float getWindowWidth() {
        return this.nGetWindowWidth();
    }

    public void setWindowWidth(float value) {
        this.nSetWindowWidth(value);
    }

    private native float nGetWindowWidth();

    private native void nSetWindowWidth(float var1);
}

