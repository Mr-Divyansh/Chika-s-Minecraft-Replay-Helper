/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90;

import imgui.moulberry90.binding.ImGuiStructDestroyable;

public final class ImGuiStorage
extends ImGuiStructDestroyable {
    public ImGuiStorage() {
    }

    public ImGuiStorage(long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public void clear() {
        this.nClear();
    }

    private native void nClear();

    public int getInt(int key) {
        return this.nGetInt(key);
    }

    public int getInt(int key, int defaultVal) {
        return this.nGetInt(key, defaultVal);
    }

    private native int nGetInt(int var1);

    private native int nGetInt(int var1, int var2);

    public void setInt(int key, int val) {
        this.nSetInt(key, val);
    }

    private native void nSetInt(int var1, int var2);

    public boolean getBool(int key) {
        return this.nGetBool(key);
    }

    public boolean getBool(int key, boolean defaultVal) {
        return this.nGetBool(key, defaultVal);
    }

    private native boolean nGetBool(int var1);

    private native boolean nGetBool(int var1, boolean var2);

    public void setBool(int key, boolean val) {
        this.nSetBool(key, val);
    }

    private native void nSetBool(int var1, boolean var2);

    public float getFloat(int key) {
        return this.nGetFloat(key);
    }

    public float getFloat(int key, float defaultVal) {
        return this.nGetFloat(key, defaultVal);
    }

    private native float nGetFloat(int var1);

    private native float nGetFloat(int var1, float var2);

    public void setFloat(int key, float val) {
        this.nSetFloat(key, val);
    }

    private native void nSetFloat(int var1, float var2);

    public void buildSortByKey() {
        this.nBuildSortByKey();
    }

    private native void nBuildSortByKey();

    public void setAllInt(int val) {
        this.nSetAllInt(val);
    }

    private native void nSetAllInt(int var1);
}

