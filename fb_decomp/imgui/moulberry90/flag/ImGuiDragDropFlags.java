/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.flag;

public final class ImGuiDragDropFlags {
    public static final int None = 0;
    public static final int SourceNoPreviewTooltip = 1;
    public static final int SourceNoDisableHover = 2;
    public static final int SourceNoHoldToOpenOthers = 4;
    public static final int SourceAllowNullID = 8;
    public static final int SourceExtern = 16;
    public static final int PayloadAutoExpire = 32;
    public static final int PayloadNoCrossContext = 64;
    public static final int PayloadNoCrossProcess = 128;
    public static final int AcceptBeforeDelivery = 1024;
    public static final int AcceptNoDrawDefaultRect = 2048;
    public static final int AcceptNoPreviewTooltip = 4096;
    public static final int AcceptPeekOnly = 3072;
    public static final int SourceAutoExpirePayload = 32;

    private ImGuiDragDropFlags() {
    }
}

