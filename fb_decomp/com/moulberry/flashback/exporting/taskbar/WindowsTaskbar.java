/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Pointer
 *  com.sun.jna.platform.win32.COM.COMInvoker
 *  com.sun.jna.platform.win32.W32Errors
 *  com.sun.jna.platform.win32.WinDef$HWND
 *  com.sun.jna.platform.win32.WinNT$HRESULT
 */
package com.moulberry.flashback.exporting.taskbar;

import com.moulberry.flashback.exporting.taskbar.ITaskbar;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.COMInvoker;
import com.sun.jna.platform.win32.W32Errors;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

public class WindowsTaskbar
extends COMInvoker
implements ITaskbar {
    private final WinDef.HWND hwnd;

    WindowsTaskbar(Pointer ptr, WinDef.HWND hwnd) {
        this.setPointer(ptr);
        this.hwnd = hwnd;
        this.invokeNative(3, new Object[0]);
    }

    private void invokeNative(int ventry, Object ... objects) {
        Object[] args = new Object[objects.length + 1];
        args[0] = this.getPointer();
        System.arraycopy(objects, 0, args, 1, objects.length);
        if (W32Errors.FAILED((WinNT.HRESULT)((WinNT.HRESULT)this._invokeNativeObject(ventry, args, WinNT.HRESULT.class)))) {
            throw new IllegalStateException("Failed to invoke vtable: " + ventry);
        }
    }

    @Override
    public void close() {
        this.reset();
        this.invokeNative(2, new Object[0]);
    }

    @Override
    public void reset() {
        this.invokeNative(10, this.hwnd, 0);
    }

    @Override
    public void setProgress(long count, long outOf) {
        this.invokeNative(9, this.hwnd, count, outOf);
    }

    @Override
    public void setPaused() {
        this.invokeNative(10, this.hwnd, 8);
    }

    @Override
    public void setNormal() {
        this.invokeNative(10, this.hwnd, 2);
    }
}

