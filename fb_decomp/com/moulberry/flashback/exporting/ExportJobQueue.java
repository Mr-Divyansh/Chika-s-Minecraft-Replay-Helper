/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.exporting;

import com.moulberry.flashback.exporting.ExportSettings;
import java.util.ArrayList;
import java.util.List;

public class ExportJobQueue {
    public static List<ExportSettings> queuedJobs = new ArrayList<ExportSettings>();
    public static boolean drainingQueue = false;

    public static int count() {
        return queuedJobs.size();
    }
}

