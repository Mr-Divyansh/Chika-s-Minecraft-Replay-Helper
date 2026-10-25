/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.editor.ui;

import imgui.moulberry90.ImGui;
import imgui.moulberry90.ImGuiStyle;

public class ReplayUIDefaults {
    public static final String LAYOUT = "[Window][###Timeline]\nPos=0,830\nSize=1920,250\nCollapsed=0\nDockId=0x00000002,0\n\n[Window][###Visuals]\nPos=1670,0\nSize=250,830\nCollapsed=0\nDockId=0x00000004,0\n\n[Docking][Data]\nDockSpace     ID=0x7C6B3D9B Window=0xA787BDB4 Pos=0,22 Size=1920,1080 Split=Y Selected=0x1F1A625A\n  DockNode    ID=0x00000001 Parent=0x7C6B3D9B SizeRef=1920,830 Split=X Selected=0x1F1A625A\n    DockNode  ID=0x00000003 Parent=0x00000001 SizeRef=1670,830 CentralNode=1 NoTabBar=1 Selected=0x1F1A625A\n    DockNode  ID=0x00000004 Parent=0x00000001 SizeRef=250,830 Selected=0x595032EA\n  DockNode    ID=0x00000002 Parent=0x7C6B3D9B SizeRef=1920,250 Selected=0xBF88A430\n";

    public static void applyStyle(ImGuiStyle style) {
        ImGui.styleColorsDark(style);
        style.setColor(0, -1);
        style.setColor(1, -5592406);
        style.setColor(27, -5592406);
        int windowBg = -869785568;
        style.setColor(2, windowBg);
        style.setColor(5, ReplayUIDefaults.brighten(windowBg, 150));
        int titleBg = -15002347;
        style.setColor(10, titleBg);
        style.setColor(11, ReplayUIDefaults.brighten(titleBg, 140));
        int popupBg = -13096920;
        style.setColor(4, popupBg);
        int frameBg = -1438697416;
        style.setColor(7, frameBg);
        style.setColor(9, ReplayUIDefaults.darken(frameBg));
        style.setColor(8, ReplayUIDefaults.brighten(frameBg));
        int button = -3446784;
        style.setColor(21, button);
        style.setColor(23, ReplayUIDefaults.darken(button));
        style.setColor(22, ReplayUIDefaults.brighten(button));
        style.setColor(18, -292864);
        int header = -2135204096;
        style.setColor(24, header);
        style.setColor(26, ReplayUIDefaults.darken(header));
        style.setColor(25, ReplayUIDefaults.brighten(header));
        style.setColor(13, windowBg);
        style.setColor(34, -13621208);
        style.setColor(35, -10993600);
        style.setColor(33, -9940912);
        style.setColor(37, -1439684568);
        style.setColor(38, -1437056960);
        style.setColor(56, 0x58CCCCCC);
        style.setColor(36, 0);
        style.setColor(39, 0);
    }

    private static int brighten(int abgr) {
        return ReplayUIDefaults.brighten(abgr, 120);
    }

    private static int brighten(int abgr, int percentage) {
        int alpha = abgr >> 24 & 0xFF;
        int blue = abgr >> 16 & 0xFF;
        int green = abgr >> 8 & 0xFF;
        int red = abgr & 0xFF;
        blue = Math.min(255, blue * percentage / 100);
        green = Math.min(255, green * percentage / 100);
        red = Math.min(255, red * percentage / 100);
        return alpha << 24 | blue << 16 | green << 8 | red;
    }

    private static int darken(int abgr) {
        int alpha = abgr >> 24 & 0xFF;
        int blue = abgr >> 16 & 0xFF;
        int green = abgr >> 8 & 0xFF;
        int red = abgr & 0xFF;
        blue = Math.min(255, blue * 4 / 5);
        green = Math.min(255, green * 4 / 5);
        red = Math.min(255, red * 4 / 5);
        return alpha << 24 | blue << 16 | green << 8 | red;
    }
}

