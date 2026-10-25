/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Window
 *  org.lwjgl.glfw.GLFW
 */
package com.moulberry.flashback;

import com.mojang.blaze3d.platform.Window;
import org.lwjgl.glfw.GLFW;

public class WindowSizeTracker {
    private static int lastFramebufferWidth;
    private static int lastFramebufferHeight;
    private static int realFramebufferWidth;
    private static int realFramebufferHeight;

    public static int getWidth(Window window) {
        if (lastFramebufferWidth != window.framebufferWidth) {
            WindowSizeTracker.recalculate(window);
        }
        return realFramebufferWidth;
    }

    public static int getHeight(Window window) {
        if (lastFramebufferHeight != window.framebufferHeight) {
            WindowSizeTracker.recalculate(window);
        }
        return realFramebufferHeight;
    }

    private static void recalculate(Window window) {
        int[] width = new int[1];
        int[] height = new int[1];
        GLFW.glfwGetFramebufferSize((long)window.handle(), (int[])width, (int[])height);
        realFramebufferWidth = width[0] > 0 ? width[0] : 1;
        realFramebufferHeight = height[0] > 0 ? height[0] : 1;
        lastFramebufferWidth = window.framebufferWidth;
        lastFramebufferHeight = window.framebufferHeight;
    }
}

