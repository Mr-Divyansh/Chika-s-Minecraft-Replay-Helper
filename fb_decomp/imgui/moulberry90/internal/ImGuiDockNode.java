/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.internal;

import imgui.moulberry90.ImGuiWindowClass;
import imgui.moulberry90.ImVec2;
import imgui.moulberry90.binding.ImGuiStruct;
import imgui.moulberry90.internal.ImGui;
import imgui.moulberry90.internal.ImGuiWindow;
import imgui.moulberry90.internal.ImRect;

public final class ImGuiDockNode
extends ImGuiStruct {
    public ImGuiDockNode(long ptr) {
        super(ptr);
        ImGui.init();
    }

    public int getID() {
        return this.nGetID();
    }

    public void setID(int value) {
        this.nSetID(value);
    }

    private native int nGetID();

    private native void nSetID(int var1);

    public int getSharedFlags() {
        return this.nGetSharedFlags();
    }

    public void setSharedFlags(int value) {
        this.nSetSharedFlags(value);
    }

    public void addSharedFlags(int flags) {
        this.setSharedFlags(this.getSharedFlags() | flags);
    }

    public void removeSharedFlags(int flags) {
        this.setSharedFlags(this.getSharedFlags() & ~flags);
    }

    public boolean hasSharedFlags(int flags) {
        return (this.getSharedFlags() & flags) != 0;
    }

    private native int nGetSharedFlags();

    private native void nSetSharedFlags(int var1);

    public int getLocalFlags() {
        return this.nGetLocalFlags();
    }

    public void setLocalFlags(int value) {
        this.nSetLocalFlags(value);
    }

    public void addLocalFlags(int flags) {
        this.setLocalFlags(this.getLocalFlags() | flags);
    }

    public void removeLocalFlags(int flags) {
        this.setLocalFlags(this.getLocalFlags() & ~flags);
    }

    public boolean hasLocalFlags(int flags) {
        return (this.getLocalFlags() & flags) != 0;
    }

    private native int nGetLocalFlags();

    private native void nSetLocalFlags(int var1);

    public int getLocalFlagsInWindows() {
        return this.nGetLocalFlagsInWindows();
    }

    public void setLocalFlagsInWindows(int value) {
        this.nSetLocalFlagsInWindows(value);
    }

    public void addLocalFlagsInWindows(int flags) {
        this.setLocalFlagsInWindows(this.getLocalFlagsInWindows() | flags);
    }

    public void removeLocalFlagsInWindows(int flags) {
        this.setLocalFlagsInWindows(this.getLocalFlagsInWindows() & ~flags);
    }

    public boolean hasLocalFlagsInWindows(int flags) {
        return (this.getLocalFlagsInWindows() & flags) != 0;
    }

    private native int nGetLocalFlagsInWindows();

    private native void nSetLocalFlagsInWindows(int var1);

    public int getMergedFlags() {
        return this.nGetMergedFlags();
    }

    public boolean hasMergedFlags(int flags) {
        return (this.getMergedFlags() & flags) != 0;
    }

    private native int nGetMergedFlags();

    public ImGuiDockNode getParentNode() {
        return new ImGuiDockNode(this.nGetParentNode());
    }

    public void setParentNode(ImGuiDockNode value) {
        this.nSetParentNode(value.ptr);
    }

    private native long nGetParentNode();

    private native void nSetParentNode(long var1);

    public ImGuiDockNode getChildNodeFirst() {
        return new ImGuiDockNode(this.nGetChildNodeFirst());
    }

    private native long nGetChildNodeFirst();

    public void setChildNodeFirst(ImGuiDockNode imGuiDockNode) {
        this.nSetChildNodeFirst(imGuiDockNode.ptr);
    }

    private native void nSetChildNodeFirst(long var1);

    public ImGuiDockNode getChildNodeSecond() {
        return new ImGuiDockNode(this.nGetChildNodeSecond());
    }

    private native long nGetChildNodeSecond();

    public void setChildNodeSecond(ImGuiDockNode imGuiDockNode) {
        this.nSetChildNodeSecond(imGuiDockNode.ptr);
    }

    private native void nSetChildNodeSecond(long var1);

    public ImVec2 getPos() {
        ImVec2 dst = new ImVec2();
        this.nGetPos(dst);
        return dst;
    }

    public float getPosX() {
        return this.nGetPosX();
    }

    public float getPosY() {
        return this.nGetPosY();
    }

    public void getPos(ImVec2 dst) {
        this.nGetPos(dst);
    }

    public void setPos(ImVec2 value) {
        this.nSetPos(value.x, value.y);
    }

    public void setPos(float valueX, float valueY) {
        this.nSetPos(valueX, valueY);
    }

    private native void nGetPos(ImVec2 var1);

    private native float nGetPosX();

    private native float nGetPosY();

    private native void nSetPos(float var1, float var2);

    public ImVec2 getSize() {
        ImVec2 dst = new ImVec2();
        this.nGetSize(dst);
        return dst;
    }

    public float getSizeX() {
        return this.nGetSizeX();
    }

    public float getSizeY() {
        return this.nGetSizeY();
    }

    public void getSize(ImVec2 dst) {
        this.nGetSize(dst);
    }

    public void setSize(ImVec2 value) {
        this.nSetSize(value.x, value.y);
    }

    public void setSize(float valueX, float valueY) {
        this.nSetSize(valueX, valueY);
    }

    private native void nGetSize(ImVec2 var1);

    private native float nGetSizeX();

    private native float nGetSizeY();

    private native void nSetSize(float var1, float var2);

    public ImVec2 getSizeRef() {
        ImVec2 dst = new ImVec2();
        this.nGetSizeRef(dst);
        return dst;
    }

    public float getSizeRefX() {
        return this.nGetSizeRefX();
    }

    public float getSizeRefY() {
        return this.nGetSizeRefY();
    }

    public void getSizeRef(ImVec2 dst) {
        this.nGetSizeRef(dst);
    }

    public void setSizeRef(ImVec2 value) {
        this.nSetSizeRef(value.x, value.y);
    }

    public void setSizeRef(float valueX, float valueY) {
        this.nSetSizeRef(valueX, valueY);
    }

    private native void nGetSizeRef(ImVec2 var1);

    private native float nGetSizeRefX();

    private native float nGetSizeRefY();

    private native void nSetSizeRef(float var1, float var2);

    public int getSplitAxis() {
        return this.nGetSplitAxis();
    }

    private native int nGetSplitAxis();

    public ImGuiWindowClass getWindowClass() {
        return new ImGuiWindowClass(this.nGetWindowClass());
    }

    private native long nGetWindowClass();

    public int getLastBgColor() {
        return this.nGetLastBgColor();
    }

    public void setLastBgColor(int value) {
        this.nSetLastBgColor(value);
    }

    private native int nGetLastBgColor();

    private native void nSetLastBgColor(int var1);

    public ImGuiWindow getHostWindow() {
        return new ImGuiWindow(this.nGetHostWindow());
    }

    public void setHostWindow(ImGuiWindow value) {
        this.nSetHostWindow(value.ptr);
    }

    private native long nGetHostWindow();

    private native void nSetHostWindow(long var1);

    public ImGuiWindow getVisibleWindow() {
        return new ImGuiWindow(this.nGetVisibleWindow());
    }

    public void setVisibleWindow(ImGuiWindow value) {
        this.nSetVisibleWindow(value.ptr);
    }

    private native long nGetVisibleWindow();

    private native void nSetVisibleWindow(long var1);

    public ImGuiDockNode getCentralNode() {
        return new ImGuiDockNode(this.nGetCentralNode());
    }

    public void setCentralNode(ImGuiDockNode value) {
        this.nSetCentralNode(value.ptr);
    }

    private native long nGetCentralNode();

    private native void nSetCentralNode(long var1);

    public ImGuiDockNode getOnlyNodeWithWindows() {
        return new ImGuiDockNode(this.nGetOnlyNodeWithWindows());
    }

    public void setOnlyNodeWithWindows(ImGuiDockNode value) {
        this.nSetOnlyNodeWithWindows(value.ptr);
    }

    private native long nGetOnlyNodeWithWindows();

    private native void nSetOnlyNodeWithWindows(long var1);

    public int getLastFrameAlive() {
        return this.nGetLastFrameAlive();
    }

    public void setLastFrameAlive(int value) {
        this.nSetLastFrameAlive(value);
    }

    private native int nGetLastFrameAlive();

    private native void nSetLastFrameAlive(int var1);

    public int getLastFrameActive() {
        return this.nGetLastFrameActive();
    }

    public void setLastFrameActive(int value) {
        this.nSetLastFrameActive(value);
    }

    private native int nGetLastFrameActive();

    private native void nSetLastFrameActive(int var1);

    public int getLastFrameFocused() {
        return this.nGetLastFrameFocused();
    }

    public void setLastFrameFocused(int value) {
        this.nSetLastFrameFocused(value);
    }

    private native int nGetLastFrameFocused();

    private native void nSetLastFrameFocused(int var1);

    public int getLastFocusedNodeId() {
        return this.nGetLastFocusedNodeId();
    }

    public void setLastFocusedNodeId(int value) {
        this.nSetLastFocusedNodeId(value);
    }

    private native int nGetLastFocusedNodeId();

    private native void nSetLastFocusedNodeId(int var1);

    public int getSelectedTabId() {
        return this.nGetSelectedTabId();
    }

    public void setSelectedTabId(int value) {
        this.nSetSelectedTabId(value);
    }

    private native int nGetSelectedTabId();

    private native void nSetSelectedTabId(int var1);

    public int getWantCloseTabId() {
        return this.nGetWantCloseTabId();
    }

    public void setWantCloseTabId(int value) {
        this.nSetWantCloseTabId(value);
    }

    private native int nGetWantCloseTabId();

    private native void nSetWantCloseTabId(int var1);

    public int getAuthorityForPos() {
        return this.nGetAuthorityForPos();
    }

    public void setAuthorityForPos(int value) {
        this.nSetAuthorityForPos(value);
    }

    private native int nGetAuthorityForPos();

    private native void nSetAuthorityForPos(int var1);

    public int getAuthorityForSize() {
        return this.nGetAuthorityForSize();
    }

    public void setAuthorityForSize(int value) {
        this.nSetAuthorityForSize(value);
    }

    private native int nGetAuthorityForSize();

    private native void nSetAuthorityForSize(int var1);

    public int getAuthorityForViewport() {
        return this.nGetAuthorityForViewport();
    }

    public void setAuthorityForViewport(int value) {
        this.nSetAuthorityForViewport(value);
    }

    private native int nGetAuthorityForViewport();

    private native void nSetAuthorityForViewport(int var1);

    public boolean getIsVisible() {
        return this.nGetIsVisible();
    }

    public void setIsVisible(boolean value) {
        this.nSetIsVisible(value);
    }

    private native boolean nGetIsVisible();

    private native void nSetIsVisible(boolean var1);

    public boolean getIsFocused() {
        return this.nGetIsFocused();
    }

    public void setIsFocused(boolean value) {
        this.nSetIsFocused(value);
    }

    private native boolean nGetIsFocused();

    private native void nSetIsFocused(boolean var1);

    public boolean getHasCloseButton() {
        return this.nGetHasCloseButton();
    }

    public void setHasCloseButton(boolean value) {
        this.nSetHasCloseButton(value);
    }

    private native boolean nGetHasCloseButton();

    private native void nSetHasCloseButton(boolean var1);

    public boolean getHasWindowMenuButton() {
        return this.nGetHasWindowMenuButton();
    }

    public void setHasWindowMenuButton(boolean value) {
        this.nSetHasWindowMenuButton(value);
    }

    private native boolean nGetHasWindowMenuButton();

    private native void nSetHasWindowMenuButton(boolean var1);

    public boolean getHasCentralNodeChild() {
        return this.nGetHasCentralNodeChild();
    }

    public void setHasCentralNodeChild(boolean value) {
        this.nSetHasCentralNodeChild(value);
    }

    private native boolean nGetHasCentralNodeChild();

    private native void nSetHasCentralNodeChild(boolean var1);

    public boolean getWantCloseAll() {
        return this.nGetWantCloseAll();
    }

    public void setWantCloseAll(boolean value) {
        this.nSetWantCloseAll(value);
    }

    private native boolean nGetWantCloseAll();

    private native void nSetWantCloseAll(boolean var1);

    public boolean getWantLockSizeOnce() {
        return this.nGetWantLockSizeOnce();
    }

    public void setWantLockSizeOnce(boolean value) {
        this.nSetWantLockSizeOnce(value);
    }

    private native boolean nGetWantLockSizeOnce();

    private native void nSetWantLockSizeOnce(boolean var1);

    public boolean getWantMouseMove() {
        return this.nGetWantMouseMove();
    }

    public void setWantMouseMove(boolean value) {
        this.nSetWantMouseMove(value);
    }

    private native boolean nGetWantMouseMove();

    private native void nSetWantMouseMove(boolean var1);

    public boolean getWantHiddenTabBarUpdate() {
        return this.nGetWantHiddenTabBarUpdate();
    }

    public void setWantHiddenTabBarUpdate(boolean value) {
        this.nSetWantHiddenTabBarUpdate(value);
    }

    private native boolean nGetWantHiddenTabBarUpdate();

    private native void nSetWantHiddenTabBarUpdate(boolean var1);

    public boolean getWantHiddenTabBarToggle() {
        return this.nGetWantHiddenTabBarToggle();
    }

    public void setWantHiddenTabBarToggle(boolean value) {
        this.nSetWantHiddenTabBarToggle(value);
    }

    private native boolean nGetWantHiddenTabBarToggle();

    private native void nSetWantHiddenTabBarToggle(boolean var1);

    public boolean isRootNode() {
        return this.nIsRootNode();
    }

    private native boolean nIsRootNode();

    public boolean isDockSpace() {
        return this.nIsDockSpace();
    }

    private native boolean nIsDockSpace();

    public boolean isFloatingNode() {
        return this.nIsFloatingNode();
    }

    private native boolean nIsFloatingNode();

    public boolean isCentralNode() {
        return this.nIsCentralNode();
    }

    private native boolean nIsCentralNode();

    public boolean isHiddenTabBar() {
        return this.nIsHiddenTabBar();
    }

    private native boolean nIsHiddenTabBar();

    public boolean isNoTabBar() {
        return this.nIsNoTabBar();
    }

    private native boolean nIsNoTabBar();

    public boolean isSplitNode() {
        return this.nIsSplitNode();
    }

    private native boolean nIsSplitNode();

    public boolean isLeafNode() {
        return this.nIsLeafNode();
    }

    private native boolean nIsLeafNode();

    public boolean isEmpty() {
        return this.nIsEmpty();
    }

    private native boolean nIsEmpty();

    public ImRect rect() {
        ImRect dst = new ImRect();
        this.nRect(dst);
        return dst;
    }

    public void rect(ImRect dst) {
        this.nRect(dst);
    }

    private native void nRect(ImRect var1);

    public void updateMergedFlags() {
        this.nUpdateMergedFlags();
    }

    private native void nUpdateMergedFlags();
}

