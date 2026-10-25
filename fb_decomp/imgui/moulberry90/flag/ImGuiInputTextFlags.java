/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.flag;

public final class ImGuiInputTextFlags {
    public static final int None = 0;
    public static final int CharsDecimal = 1;
    public static final int CharsHexadecimal = 2;
    public static final int CharsScientific = 4;
    public static final int CharsUppercase = 8;
    public static final int CharsNoBlank = 16;
    public static final int AllowTabInput = 32;
    public static final int EnterReturnsTrue = 64;
    public static final int EscapeClearsAll = 128;
    public static final int CtrlEnterForNewLine = 256;
    public static final int ReadOnly = 512;
    public static final int Password = 1024;
    public static final int AlwaysOverwrite = 2048;
    public static final int AutoSelectAll = 4096;
    public static final int ParseEmptyRefVal = 8192;
    public static final int DisplayEmptyRefVal = 16384;
    public static final int NoHorizontalScroll = 32768;
    public static final int NoUndoRedo = 65536;
    public static final int CallbackCompletion = 131072;
    public static final int CallbackHistory = 262144;
    public static final int CallbackAlways = 524288;
    public static final int CallbackCharFilter = 0x100000;
    public static final int CallbackResize = 0x200000;
    public static final int CallbackEdit = 0x400000;

    private ImGuiInputTextFlags() {
    }
}

