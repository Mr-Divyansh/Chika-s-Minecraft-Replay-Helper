/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.exporting.taskbar;

public interface ITaskbar {
    public void close();

    public void reset();

    public void setProgress(long var1, long var3);

    public void setPaused();

    public void setNormal();
}

