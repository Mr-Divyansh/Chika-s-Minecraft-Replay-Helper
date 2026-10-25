/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90;

import imgui.moulberry90.binding.ImGuiStruct;
import imgui.moulberry90.internal.ImGuiContext;

public class ImGuiInputTextCallbackData
extends ImGuiStruct {
    private static final ImGuiContext _GETCTX_1 = new ImGuiContext(0L);

    public ImGuiInputTextCallbackData(long ptr) {
        super(ptr);
    }

    public ImGuiContext getCtx() {
        ImGuiInputTextCallbackData._GETCTX_1.ptr = this.nGetCtx();
        return _GETCTX_1;
    }

    public void setCtx(ImGuiContext value) {
        this.nSetCtx(value.ptr);
    }

    private native long nGetCtx();

    private native void nSetCtx(long var1);

    public int getEventFlag() {
        return this.nGetEventFlag();
    }

    public boolean hasEventFlag(int flags) {
        return (this.getEventFlag() & flags) != 0;
    }

    private native int nGetEventFlag();

    public int getFlags() {
        return this.nGetFlags();
    }

    public boolean hasFlags(int flags) {
        return (this.getFlags() & flags) != 0;
    }

    private native int nGetFlags();

    public int getEventChar() {
        return this.nGetEventChar();
    }

    public void setEventChar(int value) {
        this.nSetEventChar(value);
    }

    private native int nGetEventChar();

    private native void nSetEventChar(int var1);

    public int getEventKey() {
        return this.nGetEventKey();
    }

    private native int nGetEventKey();

    public String getBuf() {
        return this.nGetBuf();
    }

    public void setBuf(String value) {
        this.nSetBuf(value);
    }

    private native String nGetBuf();

    private native void nSetBuf(String var1);

    public int getBufTextLen() {
        return this.nGetBufTextLen();
    }

    public void setBufTextLen(int value) {
        this.nSetBufTextLen(value);
    }

    private native int nGetBufTextLen();

    private native void nSetBufTextLen(int var1);

    public boolean getBufDirty() {
        return this.nGetBufDirty();
    }

    public void setBufDirty(boolean value) {
        this.nSetBufDirty(value);
    }

    private native boolean nGetBufDirty();

    private native void nSetBufDirty(boolean var1);

    public int getCursorPos() {
        return this.nGetCursorPos();
    }

    public void setCursorPos(int value) {
        this.nSetCursorPos(value);
    }

    private native int nGetCursorPos();

    private native void nSetCursorPos(int var1);

    public int getSelectionStart() {
        return this.nGetSelectionStart();
    }

    public void setSelectionStart(int value) {
        this.nSetSelectionStart(value);
    }

    private native int nGetSelectionStart();

    private native void nSetSelectionStart(int var1);

    public int getSelectionEnd() {
        return this.nGetSelectionEnd();
    }

    public void setSelectionEnd(int value) {
        this.nSetSelectionEnd(value);
    }

    private native int nGetSelectionEnd();

    private native void nSetSelectionEnd(int var1);

    public void deleteChars(int pos, int bytesCount) {
        this.nDeleteChars(pos, bytesCount);
    }

    private native void nDeleteChars(int var1, int var2);

    public void insertChars(int pos, String str) {
        this.nInsertChars(pos, str);
    }

    private native void nInsertChars(int var1, String var2);

    public void selectAll() {
        this.nSelectAll();
    }

    private native void nSelectAll();

    public void clearSelection() {
        this.nClearSelection();
    }

    private native void nClearSelection();

    public boolean hasSelection() {
        return this.nHasSelection();
    }

    private native boolean nHasSelection();
}

