/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.internal.flag;

public final class ImGuiDockNodeFlags {
    public static final int None = 0;
    public static final int KeepAliveOnly = 1;
    public static final int NoDockingOverCentralNode = 4;
    public static final int PassthruCentralNode = 8;
    public static final int NoDockingSplit = 16;
    public static final int NoResize = 32;
    public static final int AutoHideTabBar = 64;
    public static final int NoUndocking = 128;
    public static final int NoSplit = 16;
    public static final int NoDockingInCentralNode = 4;
    public static final int DockSpace = 1024;
    public static final int CentralNode = 2048;
    public static final int NoTabBar = 4096;
    public static final int HiddenTabBar = 8192;
    public static final int NoWindowMenuButton = 16384;
    public static final int NoCloseButton = 32768;
    public static final int NoResizeX = 65536;
    public static final int NoResizeY = 131072;
    public static final int DockedWindowsInFocusRoute = 262144;
    public static final int NoDockingSplitOther = 524288;
    public static final int NoDockingOverMe = 0x100000;
    public static final int NoDockingOverOther = 0x200000;
    public static final int NoDockingOverEmpty = 0x400000;
    public static final int NoDocking = 7864336;
    public static final int SharedFlagsInheritMask_ = -1;
    public static final int NoResizeFlagsMask_ = 196640;
    public static final int LocalFlagsTransferMask_ = 260208;
    public static final int SavedFlagsMask_ = 261152;

    private ImGuiDockNodeFlags() {
    }
}

