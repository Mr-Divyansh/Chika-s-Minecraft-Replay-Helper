/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.opengl.GlTexture
 *  com.mojang.blaze3d.platform.NativeImage
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.util.Util
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.editor.ui.windows;

import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.platform.NativeImage;
import com.moulberry.flashback.MedalTvUploading;
import com.moulberry.flashback.Utils;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.exporting.ExportSettings;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.ImGuiViewport;
import imgui.moulberry90.ImVec2;
import imgui.moulberry90.type.ImBoolean;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

public class ExportDoneWindow {
    private static final List<FinishedExportEntry> entries = new ArrayList<FinishedExportEntry>();
    @Nullable
    private static MedalTvUploading.UploadStatus inProgressUpload = null;

    public static void addFinishedExportEntry(FinishedExportEntry entry) {
        entries.add(entry);
    }

    public static boolean isDone() {
        return !entries.isEmpty();
    }

    public static void render() {
        if (!entries.isEmpty()) {
            ExportDoneWindow.renderFinishedExports();
        } else {
            ExportDoneWindow.renderInProgressUpload();
        }
    }

    public static void renderFinishedExports() {
        ImGuiViewport viewport = ImGui.getMainViewport();
        ImGui.setNextWindowPos(viewport.getCenterX(), viewport.getCenterY(), 8, 0.5f, 0.5f);
        ImBoolean open = new ImBoolean(true);
        ImGui.setNextWindowSizeConstraints(250.0f, 50.0f, 5000.0f, 500.0f);
        int flags = 524608;
        ImGui.openPopup("###ExportDone");
        if (ImGui.beginPopupModal(I18n.get((String)"flashback.export_done", (Object[])new Object[0]) + "###ExportDone", open, flags)) {
            ImVec2 padding = ImGui.getStyle().getFramePadding();
            int index = 0;
            for (FinishedExportEntry entry : entries) {
                ImGui.pushID(index++);
                String name = entry.settings.name();
                if (name == null) {
                    name = entry.outputLocation.getFileName().toString();
                }
                ImGuiHelper.separatorWithText(name);
                if (entry.errorMovingToOutput) {
                    ImGui.textWrapped(I18n.get((String)"flashback.error_moving_to_output", (Object[])new Object[0]));
                    ImGui.separator();
                }
                int DESIRED_W = 240;
                int DESIRED_H = 135;
                ImGui.beginGroup();
                NativeImage thumbnail = entry.thumbnail;
                if (thumbnail != null) {
                    int height;
                    int width;
                    int originalH;
                    int id = entry.getThumbnailTextureId();
                    int originalW = thumbnail.getWidth();
                    if (originalW * 135 > 240 * (originalH = thumbnail.getHeight())) {
                        width = 240;
                        height = 240 * originalH / originalW;
                    } else {
                        width = 135 * originalW / originalH;
                        height = 135;
                    }
                    if (ImGui.imageButton("ExportThumbnail", id, new ImVec2(width, height))) {
                        Util.getPlatform().openPath(entry.outputLocation);
                    }
                } else if (ImGui.button(I18n.get((String)"flashback.export_done.missing_thumbnail", (Object[])new Object[0]), 240.0f + padding.x * 2.0f, 135.0f + padding.y * 2.0f)) {
                    Util.getPlatform().openPath(entry.outputLocation);
                }
                ImGui.sameLine();
                ImGui.beginGroup();
                if (entry.fileSize > 0L) {
                    String bytesString = entry.fileSize < 1000L ? I18n.get((String)"flashback.bytes", (Object[])new Object[]{NumberFormat.getInstance().format(entry.fileSize)}) : (entry.fileSize < 1000000L ? I18n.get((String)"flashback.kilobytes", (Object[])new Object[]{NumberFormat.getInstance().format(entry.fileSize / 1000L)}) : I18n.get((String)"flashback.megabytes", (Object[])new Object[]{NumberFormat.getInstance().format(entry.fileSize / 1000L / 1000L)}));
                    ImGui.text(I18n.get((String)"flashback.export_done.size", (Object[])new Object[]{bytesString}));
                }
                String duration = Utils.timeInSecondsToString((int)entry.duration);
                ImGui.text(I18n.get((String)"flashback.export_done.duration", (Object[])new Object[]{duration}));
                if (entry.outputIsFolder) {
                    if (ImGui.button(I18n.get((String)"flashback.open_folder", (Object[])new Object[0]))) {
                        Util.getPlatform().openPath(entry.outputLocation);
                    }
                } else {
                    if (ImGui.button(I18n.get((String)"flashback.open_file", (Object[])new Object[0]))) {
                        Util.getPlatform().openPath(entry.outputLocation);
                    }
                    if (ImGui.button(I18n.get((String)"flashback.open_folder", (Object[])new Object[0]))) {
                        Util.getPlatform().openPath(entry.outputLocation.getParent());
                    }
                }
                if (!entry.checkedCanUpload) {
                    entry.checkedCanUpload = true;
                    entry.cannotUploadReason = MedalTvUploading.checkCanUpload(entry.settings);
                }
                if (entry.cannotUploadReason != MedalTvUploading.CannotUploadReason.UNSUPPORTED_FORMAT) {
                    String uploadAndCopyLink = I18n.get((String)"flashback.export_done.upload_and_copy_link", (Object[])new Object[0]);
                    if (entry.cannotUploadReason != null) {
                        ImGui.beginDisabled();
                        ImGui.button(uploadAndCopyLink);
                        ImGui.endDisabled();
                        switch (entry.cannotUploadReason) {
                            case UNSUPPORTED_FORMAT: {
                                throw new UnsupportedOperationException();
                            }
                            case FILE_MISSING: {
                                ImGuiHelper.tooltip("Cannot upload: file is missing", 1024);
                                break;
                            }
                            case ERROR_CHECKING_FILE: {
                                ImGuiHelper.tooltip("Cannot upload: error while checking file", 1024);
                                break;
                            }
                            case OVER_2GB: {
                                ImGuiHelper.tooltip("Cannot upload: file is over 2gb limit", 1024);
                            }
                        }
                    } else {
                        if (ImGui.button(uploadAndCopyLink)) {
                            entry.cannotUploadReason = MedalTvUploading.checkCanUpload(entry.settings);
                            if (entry.cannotUploadReason == null) {
                                if (entry.completedUpload == null || entry.completedUpload.shouldCancel) {
                                    entry.completedUpload = MedalTvUploading.upload(entry.settings);
                                }
                                inProgressUpload = entry.completedUpload;
                            }
                        }
                        ImGuiHelper.tooltip("Upload the clip to medal.tv and generate a link to view it");
                    }
                }
                ImGui.endGroup();
                ImGui.endGroup();
                ImGui.popID();
            }
            ExportDoneWindow.renderInProgressUpload();
            ImGui.endPopup();
        }
        if (!open.get() && inProgressUpload == null) {
            entries.clear();
        }
    }

    public static void renderInProgressUpload() {
        if (inProgressUpload == null) {
            return;
        }
        int flags = 524608;
        ImGui.openPopup("###UploadingToMedal");
        if (ImGui.beginPopupModal("Uploading clip###UploadingToMedal", flags)) {
            Throwable throwable;
            boolean hasError = false;
            boolean finished = ExportDoneWindow.inProgressUpload.finished;
            int progressPercentage = ExportDoneWindow.inProgressUpload.progressPercentage;
            ImVec2 cursor = ImGui.getCursorScreenPos();
            ImGui.dummy(500.0f, 20.0f);
            ImGui.getForegroundDrawList().addRectFilled(cursor.x, cursor.y, cursor.x + 500.0f, cursor.y + 20.0f, -16777216);
            ImGui.getForegroundDrawList().addRectFilled(cursor.x, cursor.y, cursor.x + (float)(progressPercentage * 5), cursor.y + 20.0f, -743165);
            String errorMessage = ExportDoneWindow.inProgressUpload.errorMessage;
            if (errorMessage != null) {
                ImGui.dummy(0.0f, 10.0f);
                ImGui.textColored(-16776961, errorMessage);
                hasError = true;
            }
            if ((throwable = ExportDoneWindow.inProgressUpload.throwable) != null) {
                ImGui.dummy(0.0f, 10.0f);
                ImGui.textColored(-16776961, throwable.toString());
                hasError = true;
            }
            if (!hasError && progressPercentage >= 100) {
                String clipboard;
                ImGui.dummy(0.0f, 10.0f);
                URI shareUrl = ExportDoneWindow.inProgressUpload.shareUrl;
                String shareUrlStr = shareUrl.toString();
                ImGui.text("Video is unlisted and expires after 14 days");
                ImGui.text("Share Link:");
                ImGui.sameLine();
                ImGui.textColored(-743165, shareUrlStr);
                if (ImGui.isItemClicked()) {
                    try {
                        Util.getPlatform().openUri(shareUrl);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if ((clipboard = Minecraft.getInstance().keyboardHandler.getClipboard()).equals(shareUrlStr)) {
                    ImGui.button("Copied!");
                } else if (ImGui.button("Copy to Clipboard")) {
                    Minecraft.getInstance().keyboardHandler.setClipboard(shareUrlStr);
                }
            }
            ImGui.dummy(0.0f, 10.0f);
            if (!hasError) {
                if (!finished) {
                    ImGui.beginDisabled();
                }
                if (ImGui.button("Done") && finished) {
                    inProgressUpload = null;
                    ImGui.closeCurrentPopup();
                }
                if (!finished) {
                    ImGui.endDisabled();
                }
            }
            if (hasError || !finished && Math.abs(ExportDoneWindow.inProgressUpload.startTime - System.currentTimeMillis()) > 5000L) {
                if (!hasError) {
                    ImGui.sameLine();
                }
                if (ImGui.button("Cancel")) {
                    ExportDoneWindow.inProgressUpload.shouldCancel = true;
                    inProgressUpload = null;
                    ImGui.closeCurrentPopup();
                }
            }
            ImGui.endPopup();
        }
    }

    private static void clear() {
        for (FinishedExportEntry entry : entries) {
            if (entry.thumbnail != null) {
                entry.thumbnail.close();
            }
            if (entry.uploaded == null) continue;
            entry.uploaded.close();
        }
        entries.clear();
    }

    public static final class FinishedExportEntry {
        private final ExportSettings settings;
        private final Path outputLocation;
        private final boolean errorMovingToOutput;
        @Nullable
        private final NativeImage thumbnail;
        private final double duration;
        private final long fileSize;
        private final boolean outputIsFolder;
        private DynamicTexture uploaded = null;
        private MedalTvUploading.UploadStatus completedUpload = null;
        private boolean checkedCanUpload = false;
        @Nullable
        private MedalTvUploading.CannotUploadReason cannotUploadReason = null;

        public FinishedExportEntry(ExportSettings settings, Path outputLocation, boolean errorMovingToOutput, @Nullable NativeImage thumbnail, double duration, long fileSize) {
            this.settings = settings;
            this.outputLocation = outputLocation;
            this.errorMovingToOutput = errorMovingToOutput;
            this.thumbnail = thumbnail;
            this.duration = duration;
            this.fileSize = fileSize;
            this.outputIsFolder = Files.isDirectory(settings.output(), new LinkOption[0]);
        }

        public int getThumbnailTextureId() {
            Objects.requireNonNull(this.thumbnail);
            if (this.uploaded == null) {
                this.uploaded = new DynamicTexture(() -> "flashback export thumbnail", this.thumbnail);
            }
            return ((GlTexture)this.uploaded.getTexture()).glId();
        }
    }
}

