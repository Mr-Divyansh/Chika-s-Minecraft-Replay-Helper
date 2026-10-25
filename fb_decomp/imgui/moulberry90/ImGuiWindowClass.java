/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90;

import imgui.moulberry90.binding.ImGuiStructDestroyable;

public final class ImGuiWindowClass
extends ImGuiStructDestroyable {
    public ImGuiWindowClass() {
    }

    public ImGuiWindowClass(long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public int getClassId() {
        return this.nGetClassId();
    }

    public void setClassId(int value) {
        this.nSetClassId(value);
    }

    private native int nGetClassId();

    private native void nSetClassId(int var1);

    public int getParentViewportId() {
        return this.nGetParentViewportId();
    }

    public void setParentViewportId(int value) {
        this.nSetParentViewportId(value);
    }

    private native int nGetParentViewportId();

    private native void nSetParentViewportId(int var1);

    public int getFocusRouteParentWindowId() {
        return this.nGetFocusRouteParentWindowId();
    }

    public void setFocusRouteParentWindowId(int value) {
        this.nSetFocusRouteParentWindowId(value);
    }

    private native int nGetFocusRouteParentWindowId();

    private native void nSetFocusRouteParentWindowId(int var1);

    public int getViewportFlagsOverrideSet() {
        return this.nGetViewportFlagsOverrideSet();
    }

    public void setViewportFlagsOverrideSet(int value) {
        this.nSetViewportFlagsOverrideSet(value);
    }

    public void addViewportFlagsOverrideSet(int flags) {
        this.setViewportFlagsOverrideSet(this.getViewportFlagsOverrideSet() | flags);
    }

    public void removeViewportFlagsOverrideSet(int flags) {
        this.setViewportFlagsOverrideSet(this.getViewportFlagsOverrideSet() & ~flags);
    }

    public boolean hasViewportFlagsOverrideSet(int flags) {
        return (this.getViewportFlagsOverrideSet() & flags) != 0;
    }

    private native int nGetViewportFlagsOverrideSet();

    private native void nSetViewportFlagsOverrideSet(int var1);

    public int getViewportFlagsOverrideClear() {
        return this.nGetViewportFlagsOverrideClear();
    }

    public void setViewportFlagsOverrideClear(int value) {
        this.nSetViewportFlagsOverrideClear(value);
    }

    public void addViewportFlagsOverrideClear(int flags) {
        this.setViewportFlagsOverrideClear(this.getViewportFlagsOverrideClear() | flags);
    }

    public void removeViewportFlagsOverrideClear(int flags) {
        this.setViewportFlagsOverrideClear(this.getViewportFlagsOverrideClear() & ~flags);
    }

    public boolean hasViewportFlagsOverrideClear(int flags) {
        return (this.getViewportFlagsOverrideClear() & flags) != 0;
    }

    private native int nGetViewportFlagsOverrideClear();

    private native void nSetViewportFlagsOverrideClear(int var1);

    public int getTabItemFlagsOverrideSet() {
        return this.nGetTabItemFlagsOverrideSet();
    }

    public void setTabItemFlagsOverrideSet(int value) {
        this.nSetTabItemFlagsOverrideSet(value);
    }

    public void addTabItemFlagsOverrideSet(int flags) {
        this.setTabItemFlagsOverrideSet(this.getTabItemFlagsOverrideSet() | flags);
    }

    public void removeTabItemFlagsOverrideSet(int flags) {
        this.setTabItemFlagsOverrideSet(this.getTabItemFlagsOverrideSet() & ~flags);
    }

    public boolean hasTabItemFlagsOverrideSet(int flags) {
        return (this.getTabItemFlagsOverrideSet() & flags) != 0;
    }

    private native int nGetTabItemFlagsOverrideSet();

    private native void nSetTabItemFlagsOverrideSet(int var1);

    public int getDockNodeFlagsOverrideSet() {
        return this.nGetDockNodeFlagsOverrideSet();
    }

    public void setDockNodeFlagsOverrideSet(int value) {
        this.nSetDockNodeFlagsOverrideSet(value);
    }

    public void addDockNodeFlagsOverrideSet(int flags) {
        this.setDockNodeFlagsOverrideSet(this.getDockNodeFlagsOverrideSet() | flags);
    }

    public void removeDockNodeFlagsOverrideSet(int flags) {
        this.setDockNodeFlagsOverrideSet(this.getDockNodeFlagsOverrideSet() & ~flags);
    }

    public boolean hasDockNodeFlagsOverrideSet(int flags) {
        return (this.getDockNodeFlagsOverrideSet() & flags) != 0;
    }

    private native int nGetDockNodeFlagsOverrideSet();

    private native void nSetDockNodeFlagsOverrideSet(int var1);

    public boolean getDockingAlwaysTabBar() {
        return this.nGetDockingAlwaysTabBar();
    }

    public void setDockingAlwaysTabBar(boolean value) {
        this.nSetDockingAlwaysTabBar(value);
    }

    private native boolean nGetDockingAlwaysTabBar();

    private native void nSetDockingAlwaysTabBar(boolean var1);

    public boolean getDockingAllowUnclassed() {
        return this.nGetDockingAllowUnclassed();
    }

    public void setDockingAllowUnclassed(boolean value) {
        this.nSetDockingAllowUnclassed(value);
    }

    private native boolean nGetDockingAllowUnclassed();

    private native void nSetDockingAllowUnclassed(boolean var1);
}

