/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.exporting.taskbar;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.ui.windows.ExportDoneWindow;
import com.moulberry.flashback.exporting.ExportJob;
import com.moulberry.flashback.exporting.taskbar.ITaskbar;
import com.moulberry.flashback.exporting.taskbar.TaskbarHost;

public class TaskbarManager {
    private static ITaskbar taskbarInterface = null;
    private static int lastProgressCount = 0;
    private static int lastProgressOutOf = 0;
    private static long lastExportJobUpdate = -1L;
    private static boolean lastPaused = false;
    private static volatile boolean started = false;

    public static void launchTaskbarManager() {
        if (started) {
            return;
        }
        started = true;
        Thread taskbarThread = new Thread(() -> {
            while (Flashback.isInReplay()) {
                try {
                    Thread.sleep(100L);
                    TaskbarManager.tickTaskbarProgress();
                }
                catch (InterruptedException interruptedException) {}
            }
            started = false;
        });
        taskbarThread.setName("Flashback-Taskbar-Updater");
        taskbarThread.setDaemon(true);
        taskbarThread.start();
    }

    public static void tickTaskbarProgress() {
        ExportJob exportJob = Flashback.EXPORT_JOB;
        if (exportJob != null) {
            long currentTime = System.currentTimeMillis();
            if (lastExportJobUpdate > currentTime) {
                lastExportJobUpdate = currentTime;
            }
            boolean paused = false;
            if (lastExportJobUpdate > 0L && exportJob.progressCount == lastProgressCount && exportJob.progressOutOf == lastProgressOutOf) {
                paused = currentTime - lastExportJobUpdate > 5000L;
            } else {
                lastExportJobUpdate = currentTime;
            }
            TaskbarManager.setTaskbarProgress(exportJob.progressCount, exportJob.progressOutOf, paused);
            return;
        }
        lastExportJobUpdate = -1L;
        if (ExportDoneWindow.isDone()) {
            TaskbarManager.setTaskbarProgress(1, 1, false);
            return;
        }
        TaskbarManager.setTaskbarProgress(0, 0, false);
    }

    private static void setTaskbarProgress(int count, int outOf, boolean paused) {
        if (count == lastProgressCount && outOf == lastProgressOutOf && paused == lastPaused) {
            return;
        }
        if (taskbarInterface == null) {
            taskbarInterface = TaskbarHost.createTaskbar();
        }
        if (count <= 0 || outOf <= 0) {
            taskbarInterface.reset();
            lastPaused = false;
        } else {
            if (paused != lastPaused) {
                if (paused) {
                    taskbarInterface.setPaused();
                } else {
                    taskbarInterface.setNormal();
                }
                lastPaused = paused;
            }
            taskbarInterface.setProgress(count, outOf);
        }
        lastProgressCount = count;
        lastProgressOutOf = outOf;
    }
}

