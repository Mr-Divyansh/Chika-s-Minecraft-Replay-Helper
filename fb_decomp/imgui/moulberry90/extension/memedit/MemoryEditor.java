/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.memedit;

import imgui.moulberry90.binding.ImGuiStructDestroyable;
import imgui.moulberry90.extension.memedit.MemoryEditorSizes;

public final class MemoryEditor
extends ImGuiStructDestroyable {
    public MemoryEditor() {
    }

    public MemoryEditor(long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public boolean getOpen() {
        return this.nGetOpen();
    }

    public void setOpen(boolean value) {
        this.nSetOpen(value);
    }

    private native boolean nGetOpen();

    private native void nSetOpen(boolean var1);

    public boolean getReadOnly() {
        return this.nGetReadOnly();
    }

    public void setReadOnly(boolean value) {
        this.nSetReadOnly(value);
    }

    private native boolean nGetReadOnly();

    private native void nSetReadOnly(boolean var1);

    public int getCols() {
        return this.nGetCols();
    }

    public void setCols(int value) {
        this.nSetCols(value);
    }

    private native int nGetCols();

    private native void nSetCols(int var1);

    public boolean getOptShowOptions() {
        return this.nGetOptShowOptions();
    }

    public void setOptShowOptions(boolean value) {
        this.nSetOptShowOptions(value);
    }

    private native boolean nGetOptShowOptions();

    private native void nSetOptShowOptions(boolean var1);

    public boolean getOptShowDataPreview() {
        return this.nGetOptShowDataPreview();
    }

    public void setOptShowDataPreview(boolean value) {
        this.nSetOptShowDataPreview(value);
    }

    private native boolean nGetOptShowDataPreview();

    private native void nSetOptShowDataPreview(boolean var1);

    public boolean getOptShowHexII() {
        return this.nGetOptShowHexII();
    }

    public void setOptShowHexII(boolean value) {
        this.nSetOptShowHexII(value);
    }

    private native boolean nGetOptShowHexII();

    private native void nSetOptShowHexII(boolean var1);

    public boolean getOptShowAscii() {
        return this.nGetOptShowAscii();
    }

    public void setOptShowAscii(boolean value) {
        this.nSetOptShowAscii(value);
    }

    private native boolean nGetOptShowAscii();

    private native void nSetOptShowAscii(boolean var1);

    public boolean getOptGreyOutZeroes() {
        return this.nGetOptGreyOutZeroes();
    }

    public void setOptGreyOutZeroes(boolean value) {
        this.nSetOptGreyOutZeroes(value);
    }

    private native boolean nGetOptGreyOutZeroes();

    private native void nSetOptGreyOutZeroes(boolean var1);

    public boolean getOptUpperCaseHex() {
        return this.nGetOptUpperCaseHex();
    }

    public void setOptUpperCaseHex(boolean value) {
        this.nSetOptUpperCaseHex(value);
    }

    private native boolean nGetOptUpperCaseHex();

    private native void nSetOptUpperCaseHex(boolean var1);

    public int getOptMidColsCount() {
        return this.nGetOptMidColsCount();
    }

    public void setOptMidColsCount(int value) {
        this.nSetOptMidColsCount(value);
    }

    private native int nGetOptMidColsCount();

    private native void nSetOptMidColsCount(int var1);

    public int getOptAddrDigitsCount() {
        return this.nGetOptAddrDigitsCount();
    }

    public void setOptAddrDigitsCount(int value) {
        this.nSetOptAddrDigitsCount(value);
    }

    private native int nGetOptAddrDigitsCount();

    private native void nSetOptAddrDigitsCount(int var1);

    public float getOptFooterExtraHeight() {
        return this.nGetOptFooterExtraHeight();
    }

    public void setOptFooterExtraHeight(float value) {
        this.nSetOptFooterExtraHeight(value);
    }

    private native float nGetOptFooterExtraHeight();

    private native void nSetOptFooterExtraHeight(float var1);

    public int getHighlightColor() {
        return this.nGetHighlightColor();
    }

    public void setHighlightColor(int value) {
        this.nSetHighlightColor(value);
    }

    private native int nGetHighlightColor();

    private native void nSetHighlightColor(int var1);

    public void gotoAddrAndHighlight(long addrMin, long addrMax) {
        this.nGotoAddrAndHighlight(addrMin, addrMax);
    }

    private native void nGotoAddrAndHighlight(long var1, long var3);

    public void calcSizes(MemoryEditorSizes s, long memSize, long baseDisplayAddr) {
        this.nCalcSizes(s.ptr, memSize, baseDisplayAddr);
    }

    private native void nCalcSizes(long var1, long var3, long var5);

    public void drawWindow(String title, long memData, long memSize) {
        this.nDrawWindow(title, memData, memSize);
    }

    public void drawWindow(String title, long memData, long memSize, long baseDisplayAddr) {
        this.nDrawWindow(title, memData, memSize, baseDisplayAddr);
    }

    private native void nDrawWindow(String var1, long var2, long var4);

    private native void nDrawWindow(String var1, long var2, long var4, long var6);

    public void drawContents(long memData, long memSize) {
        this.nDrawContents(memData, memSize);
    }

    public void drawContents(long memData, long memSize, long baseDisplayAddr) {
        this.nDrawContents(memData, memSize, baseDisplayAddr);
    }

    private native void nDrawContents(long var1, long var3);

    private native void nDrawContents(long var1, long var3, long var5);

    public void drawOptionsLine(MemoryEditorSizes s, long memData, long memSize, long baseDisplayAddr) {
        this.nDrawOptionsLine(s.ptr, memData, memSize, baseDisplayAddr);
    }

    private native void nDrawOptionsLine(long var1, long var3, long var5, long var7);

    public void drawPreviewLine(MemoryEditorSizes s, long memDataVoid, long memSize, long baseDisplayAddr) {
        this.nDrawPreviewLine(s.ptr, memDataVoid, memSize, baseDisplayAddr);
    }

    private native void nDrawPreviewLine(long var1, long var3, long var5, long var7);
}

