/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 */
package com.moulberry.flashback.editor.ui.windows;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.exporting.ExportJob;
import com.moulberry.flashback.exporting.ExportJobQueue;
import com.moulberry.flashback.exporting.ExportSettings;
import imgui.moulberry90.ImGui;
import net.minecraft.client.resources.language.I18n;

public class ExportQueueWindow {
    private static boolean open = false;

    public static void open() {
        open = true;
    }

    public static void render() {
        String title;
        if (open) {
            ImGui.openPopup("###ExportQueue");
            open = false;
        }
        if (ImGuiHelper.beginPopupModalCloseable((title = I18n.get((String)"flashback.export_queue", (Object[])new Object[0])) + "###ExportQueue", 64)) {
            ImGuiHelper.pushStyleColor(5, -8355712);
            boolean canStartJob = !ExportJobQueue.queuedJobs.isEmpty() && Flashback.EXPORT_JOB == null;
            boolean canRemoveJob = !ExportJobQueue.queuedJobs.isEmpty();
            ImGui.textUnformatted(I18n.get((String)"flashback.export_jobs", (Object[])new Object[0]));
            if (ImGui.beginChild("##Jobs", 300.0f, 150.0f, true)) {
                if (ImGui.beginTable("##JobTable", 3, 8192)) {
                    ImGui.tableSetupColumn(I18n.get((String)"flashback.name", (Object[])new Object[0]), 8);
                    int startJob = -1;
                    int removeJob = -1;
                    for (int i = 0; i < ExportJobQueue.queuedJobs.size(); ++i) {
                        ImGui.pushID(i);
                        ExportSettings queuedJob = ExportJobQueue.queuedJobs.get(i);
                        String name = queuedJob.name() == null ? I18n.get((String)"flashback.job_n", (Object[])new Object[]{i + 1}) : queuedJob.name();
                        ImGui.tableNextColumn();
                        ImGui.textUnformatted(name);
                        ImGui.tableNextColumn();
                        if (ImGui.smallButton(I18n.get((String)"flashback.start", (Object[])new Object[0]))) {
                            startJob = i;
                        }
                        ImGui.tableNextColumn();
                        if (ImGui.smallButton(I18n.get((String)"flashback.remove", (Object[])new Object[0]))) {
                            removeJob = i;
                        }
                        ImGui.popID();
                    }
                    if (startJob >= 0 && canStartJob) {
                        ExportSettings settings = ExportJobQueue.queuedJobs.remove(startJob);
                        Flashback.EXPORT_JOB = new ExportJob(settings);
                    } else if (removeJob >= 0 && canRemoveJob) {
                        ExportJobQueue.queuedJobs.remove(removeJob);
                    }
                    ImGui.endTable();
                }
                ImGui.endChild();
            }
            ImGuiHelper.popStyleColor();
            if (!canStartJob) {
                ImGui.beginDisabled();
            }
            if (ImGui.button(I18n.get((String)"flashback.start_all", (Object[])new Object[0])) && canStartJob) {
                ExportJobQueue.drainingQueue = true;
            }
            if (!canStartJob) {
                ImGui.endDisabled();
            }
            ImGui.sameLine();
            if (!canRemoveJob) {
                ImGui.beginDisabled();
            }
            if (ImGui.button(I18n.get((String)"flashback.remove_all", (Object[])new Object[0])) && canRemoveJob) {
                ExportJobQueue.queuedJobs.clear();
            }
            if (!canRemoveJob) {
                ImGui.endDisabled();
            }
            ImGuiHelper.endPopupModalCloseable();
        }
    }
}

