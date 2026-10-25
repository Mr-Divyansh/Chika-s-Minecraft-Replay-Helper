/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Pointer
 *  com.sun.jna.platform.win32.Guid$GUID
 *  com.sun.jna.platform.win32.Ole32
 *  com.sun.jna.platform.win32.W32Errors
 *  com.sun.jna.platform.win32.WinDef$HWND
 *  com.sun.jna.platform.win32.WinNT$HRESULT
 *  com.sun.jna.ptr.PointerByReference
 *  net.minecraft.client.Minecraft
 *  org.lwjgl.glfw.GLFWNativeWin32
 */
package com.moulberry.flashback.exporting.taskbar;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.ui.CustomImGuiImplGlfw;
import com.moulberry.flashback.exporting.taskbar.ITaskbar;
import com.moulberry.flashback.exporting.taskbar.NoopTaskbar;
import com.moulberry.flashback.exporting.taskbar.WindowsTaskbar;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.W32Errors;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFWNativeWin32;

public class TaskbarHost {
    public static ITaskbar createTaskbar() {
        if (CustomImGuiImplGlfw.IS_WINDOWS) {
            try {
                return TaskbarHost.createWindowsInterface();
            }
            catch (Exception e) {
                Flashback.LOGGER.error("Unable to create windows taskbar interface", (Throwable)e);
                return new NoopTaskbar();
            }
        }
        return new NoopTaskbar();
    }

    private static WindowsTaskbar createWindowsInterface() {
        PointerByReference itaskbar3res = new PointerByReference();
        if (W32Errors.FAILED((WinNT.HRESULT)Ole32.INSTANCE.CoCreateInstance(new Guid.GUID("56FDF344-FD6D-11d0-958A-006097C9A090"), null, 21, new Guid.GUID("EA1AFB91-9E28-4B86-90E9-9E9F8A5EEFAF"), itaskbar3res))) {
            throw new IllegalStateException("Failed to create ITaskbar3");
        }
        WinDef.HWND hwnd = new WinDef.HWND(new Pointer(GLFWNativeWin32.glfwGetWin32Window((long)Minecraft.getInstance().getWindow().handle())));
        return new WindowsTaskbar(itaskbar3res.getValue(), hwnd);
    }
}

