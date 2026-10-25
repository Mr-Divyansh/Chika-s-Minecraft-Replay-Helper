/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90;

import imgui.moulberry90.binding.ImGuiStructDestroyable;

public final class ImGuiTextFilter
extends ImGuiStructDestroyable {
    public ImGuiTextFilter() {
        this("");
    }

    public ImGuiTextFilter(String defaultFilter) {
        this.ptr = this.nCreate(defaultFilter);
    }

    public ImGuiTextFilter(long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return this.nCreate("");
    }

    private native long nCreate(String var1);

    public boolean draw() {
        return this.nDraw();
    }

    public boolean draw(String label) {
        return this.nDraw(label);
    }

    public boolean draw(String label, float width) {
        return this.nDraw(label, width);
    }

    public boolean draw(float width) {
        return this.nDraw(width);
    }

    private native boolean nDraw();

    private native boolean nDraw(String var1);

    private native boolean nDraw(String var1, float var2);

    private native boolean nDraw(float var1);

    public boolean passFilter(String text) {
        return this.nPassFilter(text);
    }

    private native boolean nPassFilter(String var1);

    public void build() {
        this.nBuild();
    }

    private native void nBuild();

    public void clear() {
        this.nClear();
    }

    private native void nClear();

    public boolean isActive() {
        return this.nIsActive();
    }

    private native boolean nIsActive();

    public native String getInputBuffer();

    public native void setInputBuffer(String var1);

    public int getCountGrep() {
        return this.nGetCountGrep();
    }

    public void setCountGrep(int value) {
        this.nSetCountGrep(value);
    }

    private native int nGetCountGrep();

    private native void nSetCountGrep(int var1);
}

