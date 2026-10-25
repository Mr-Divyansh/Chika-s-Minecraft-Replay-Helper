/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.util.FileUtil
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.editor.ui.windows;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.Utils;
import com.moulberry.flashback.combo_options.AspectRatio;
import com.moulberry.flashback.combo_options.AudioCodec;
import com.moulberry.flashback.combo_options.ExportProjection;
import com.moulberry.flashback.combo_options.Sizing;
import com.moulberry.flashback.combo_options.VideoCodec;
import com.moulberry.flashback.combo_options.VideoContainer;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.exporting.AsyncFileDialogs;
import com.moulberry.flashback.exporting.ExportJob;
import com.moulberry.flashback.exporting.ExportJobQueue;
import com.moulberry.flashback.exporting.ExportSettings;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.type.ImString;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StartExportWindow {
    private static boolean open = false;
    private static boolean close = false;
    private static final int[] lastFramebufferSize = new int[]{0, 0};
    private static AspectRatio lastCustomAspectRatio = null;
    private static final int[] startEndTick = new int[]{-1, -1};
    private static VideoContainer[] supportedContainers = null;
    private static VideoContainer[] supportedContainersWithTransparency = null;
    private static final ImString bitrate = ImGuiHelper.createResizableImString("20m");
    private static final ImString jobName = ImGuiHelper.createResizableImString("");
    private static final ImString pngSequenceFormat = ImGuiHelper.createResizableImString("%04d");
    private static String installedIncompatibleModsString = null;
    private static final List<String> potentialIncompatibleMods = List.of("g4mespeed", "feather");

    public static void render() {
        FlashbackConfigV1 config;
        EditorState editorState = EditorStateManager.getCurrent();
        if (open) {
            installedIncompatibleModsString = null;
            for (String potentialIncompatibleMod : potentialIncompatibleMods) {
                if (!FabricLoader.getInstance().isModLoaded(potentialIncompatibleMod)) continue;
                if (installedIncompatibleModsString == null) {
                    installedIncompatibleModsString = potentialIncompatibleMod;
                    continue;
                }
                installedIncompatibleModsString = installedIncompatibleModsString + ", " + potentialIncompatibleMod;
            }
            config = Flashback.getConfig();
            config.forceDefaultExportSettings.apply(config.internalExport);
            if (config.internalExport.resolution == null || config.internalExport.resolution.length != 2) {
                config.internalExport.resolution = new int[]{1920, 1080};
            }
            if (config.internalExport.framerate == null || config.internalExport.framerate.length != 1) {
                config.internalExport.framerate = new float[]{60.0f};
            }
            if (config.internalExport.selectedVideoEncoder == null || config.internalExport.selectedVideoEncoder.length != 1) {
                config.internalExport.selectedVideoEncoder = new int[]{0};
            }
            if (config.internalExport.audioCodec == null) {
                config.internalExport.audioCodec = AudioCodec.AAC;
            }
            ImGui.openPopup("###StartExport");
            if (editorState != null && editorState.replayVisuals.sizing == Sizing.CHANGE_ASPECT_RATIO) {
                AspectRatio aspectRatio = editorState.replayVisuals.changeAspectRatio;
                if (aspectRatio != null && aspectRatio != lastCustomAspectRatio) {
                    switch (aspectRatio) {
                        case ASPECT_16_9: {
                            config.internalExport.resolution[0] = 1920;
                            config.internalExport.resolution[1] = 1080;
                            break;
                        }
                        case ASPECT_9_16: {
                            config.internalExport.resolution[0] = 1080;
                            config.internalExport.resolution[1] = 1920;
                            break;
                        }
                        case ASPECT_240_1: {
                            config.internalExport.resolution[0] = 1920;
                            config.internalExport.resolution[1] = 800;
                            break;
                        }
                        case ASPECT_1_1: {
                            config.internalExport.resolution[0] = 1920;
                            config.internalExport.resolution[1] = 1920;
                            break;
                        }
                        case ASPECT_4_3: {
                            config.internalExport.resolution[0] = 1600;
                            config.internalExport.resolution[1] = 1200;
                            break;
                        }
                        case ASPECT_3_2: {
                            config.internalExport.resolution[0] = 2160;
                            config.internalExport.resolution[1] = 1440;
                        }
                    }
                }
                lastCustomAspectRatio = aspectRatio;
            }
            open = false;
            close = false;
        }
        if (ImGuiHelper.beginPopupModalCloseable(I18n.get((String)"flashback.export_to_video", (Object[])new Object[0]) + "###StartExport", 64)) {
            int resX;
            int resY;
            if (close) {
                close = false;
                ImGui.closeCurrentPopup();
                ImGuiHelper.endPopupModalCloseable();
                return;
            }
            config = Flashback.getConfig();
            ImGuiHelper.separatorWithText(I18n.get((String)"flashback.capture_options", (Object[])new Object[0]));
            ImGuiHelper.inputInt(I18n.get((String)"flashback.resolution", (Object[])new Object[0]), config.internalExport.resolution);
            if (config.internalExport.resolution[0] < 16) {
                config.internalExport.resolution[0] = 16;
            }
            if (config.internalExport.resolution[1] < 16) {
                config.internalExport.resolution[1] = 16;
            }
            if (config.internalExport.resolution[0] % 2 != 0) {
                config.internalExport.resolution[0] = config.internalExport.resolution[0] + 1;
            }
            if (config.internalExport.resolution[1] % 2 != 0) {
                config.internalExport.resolution[1] = config.internalExport.resolution[1] + 1;
            }
            if (startEndTick[0] >= 0 && startEndTick[1] >= 0 && ImGuiHelper.inputInt(I18n.get((String)"flashback.start_end_tick", (Object[])new Object[0]), startEndTick)) {
                ReplayServer replayServer = Flashback.getReplayServer();
                if (editorState != null && replayServer != null) {
                    editorState.setExportTicks(startEndTick[0], startEndTick[1], replayServer.getTotalReplayTicks());
                }
            }
            ImGuiHelper.inputFloat(I18n.get((String)"flashback.framerate", (Object[])new Object[0]), config.internalExport.framerate);
            config.internalExport.projection = ImGuiHelper.enumCombo(I18n.get((String)"flashback.projection", (Object[])new Object[0]), config.internalExport.projection);
            if (config.internalExport.projection == ExportProjection.ORTHOGRAPHIC) {
                ImGui.sliderFloat("Ortho Zoom", config.internalExport.orthographicZoom, 0.0f, 10.0f);
            } else if (config.internalExport.projection == ExportProjection.CUBE_MAP) {
                int resX2 = config.internalExport.resolution[0];
                resY = config.internalExport.resolution[1];
                if (resX2 % 4 != 0 || resY % 3 != 0 || resX2 != resY * 4 / 3) {
                    ImGui.text("Warning: Resolution should be 4:3 for cube map export");
                }
            } else if (config.internalExport.projection == ExportProjection.EQUIRECTANGULAR && (resX = config.internalExport.resolution[0]) != (resY = config.internalExport.resolution[1]) * 2) {
                ImGui.text("Warning: Resolution should be 2:1 for equirectangular export");
            }
            if (ImGui.checkbox(I18n.get((String)"flashback.reset_rng", (Object[])new Object[0]), config.internalExport.resetRng)) {
                config.internalExport.resetRng = !config.internalExport.resetRng;
            }
            ImGuiHelper.tooltip(I18n.get((String)"flashback.reset_rng_tooltip", (Object[])new Object[0]));
            ImGui.sameLine();
            if (ImGui.checkbox(I18n.get((String)"flashback.ssaa", (Object[])new Object[0]), config.internalExport.ssaa)) {
                config.internalExport.ssaa = !config.internalExport.ssaa;
            }
            ImGuiHelper.tooltip(I18n.get((String)"flashback.ssaa_tooltip", (Object[])new Object[0]));
            ImGui.sameLine();
            if (ImGui.checkbox(I18n.get((String)"flashback.no_gui", (Object[])new Object[0]), config.internalExport.noGui)) {
                config.internalExport.noGui = !config.internalExport.noGui;
            }
            ImGuiHelper.tooltip(I18n.get((String)"flashback.no_gui_tooltip", (Object[])new Object[0]));
            if (ImGui.checkbox(I18n.get((String)"flashback.depth_map", (Object[])new Object[0]), config.internalExport.depthMap)) {
                config.internalExport.depthMap = !config.internalExport.depthMap;
            }
            ImGuiHelper.tooltip(I18n.get((String)"flashback.depth_map_tooltip", (Object[])new Object[0]));
            if (config.internalExport.depthMap && config.internalExport.container != VideoContainer.EXR_SEQUENCE) {
                ImGui.textWrapped("EXR Sequence is recommended for exporting depth maps. Using " + config.internalExport.container.text() + " may result in reduced precision!");
            }
            ImGuiHelper.separatorWithText(I18n.get((String)"flashback.video_options", (Object[])new Object[0]));
            StartExportWindow.renderVideoOptions(editorState, config);
            Enum[] supportedAudioCodecs = config.internalExport.container.getSupportedAudioCodecs();
            if (supportedAudioCodecs.length > 0) {
                ImGuiHelper.separatorWithText(I18n.get((String)"flashback.audio_options", (Object[])new Object[0]));
                if (ImGui.checkbox(I18n.get((String)"flashback.record_audio", (Object[])new Object[0]), config.internalExport.recordAudio)) {
                    boolean bl = config.internalExport.recordAudio = !config.internalExport.recordAudio;
                }
                if (config.internalExport.recordAudio) {
                    AudioCodec newAudioCodec;
                    if (ImGui.checkbox(I18n.get((String)"flashback.stereo_audio", (Object[])new Object[0]), config.internalExport.stereoAudio)) {
                        boolean bl = config.internalExport.stereoAudio = !config.internalExport.stereoAudio;
                    }
                    if ((newAudioCodec = (AudioCodec)ImGuiHelper.enumCombo((String)I18n.get((String)"flashback.audio_codec", (Object[])new Object[0]), (Enum)config.internalExport.audioCodec, (Enum[])supportedAudioCodecs)) != config.internalExport.audioCodec) {
                        config.internalExport.audioCodec = newAudioCodec;
                    }
                    if (editorState != null && editorState.audioSourceEntity != null) {
                        ImGui.textUnformatted(I18n.get((String)"flashback.audio_source.entity", (Object[])new Object[]{editorState.audioSourceEntity}));
                    } else {
                        ImGui.textUnformatted(I18n.get((String)"flashback.audio_source.camera", (Object[])new Object[0]));
                    }
                }
            } else {
                config.internalExport.recordAudio = false;
            }
            if (installedIncompatibleModsString != null) {
                ImGuiHelper.separatorWithText(I18n.get((String)"flashback.incompatible_with_exporting", (Object[])new Object[0]));
                ImGui.textWrapped(I18n.get((String)"flashback.incompatible_with_exporting_description", (Object[])new Object[0]));
                ImGui.pushTextWrapPos();
                ImGui.textColored(-16776961, installedIncompatibleModsString);
                ImGui.popTextWrapPos();
            }
            ImGui.dummy(0.0f, 10.0f * ReplayUI.getUiScale());
            boolean isFullscreen = Minecraft.getInstance().getWindow().isFullscreen();
            if (isFullscreen) {
                ImGui.separator();
                ImGui.textWrapped(I18n.get((String)"flashback.export_disable_fullscreen", (Object[])new Object[0]));
            }
            float buttonSize = (ImGui.getContentRegionAvailX() - ImGui.getStyle().getItemSpacingX()) / 2.0f;
            if (isFullscreen) {
                ImGui.beginDisabled();
            }
            if (ImGui.button(I18n.get((String)"flashback.start_export", (Object[])new Object[0]), buttonSize, ReplayUI.scaleUi(25)) && !isFullscreen) {
                StartExportWindow.createExportSettings(null, config).thenAccept(settings -> {
                    if (settings != null) {
                        close = true;
                        ++Utils.exportSequenceCount;
                        Flashback.EXPORT_JOB = new ExportJob((ExportSettings)settings);
                        config.delayedSaveToDefaultFolder();
                    }
                });
            }
            ImGui.sameLine();
            if (ImGui.button(I18n.get((String)"flashback.add_to_queue", (Object[])new Object[0]), buttonSize, ReplayUI.scaleUi(25)) && !isFullscreen) {
                jobName.set(I18n.get((String)"flashback.job_n", (Object[])new Object[]{ExportJobQueue.count() + 1}));
                ImGui.openPopup("QueuedJobName");
            }
            if (isFullscreen) {
                ImGui.endDisabled();
            }
            if (ImGuiHelper.beginPopup("QueuedJobName")) {
                ImGui.setNextItemWidth(100.0f);
                ImGui.inputText(I18n.get((String)"flashback.job_name", (Object[])new Object[0]), jobName);
                if (isFullscreen) {
                    ImGui.beginDisabled();
                }
                if ((ImGui.button(I18n.get((String)"flashback.queue_job", (Object[])new Object[0])) || ReplayUI.consumeConfirm()) && !isFullscreen) {
                    StartExportWindow.createExportSettings(ImGuiHelper.getString(jobName), config).thenAccept(settings -> {
                        if (settings != null) {
                            close = true;
                            ++Utils.exportSequenceCount;
                            ExportJobQueue.queuedJobs.add((ExportSettings)settings);
                        }
                    });
                }
                if (isFullscreen) {
                    ImGui.endDisabled();
                }
                ImGui.sameLine();
                if (ImGui.button(I18n.get((String)"gui.back", (Object[])new Object[0])) || ReplayUI.consumeCancel()) {
                    ImGui.closeCurrentPopup();
                }
                ImGui.endPopup();
            }
            ImGuiHelper.endPopupModalCloseable();
        }
    }

    private static void renderVideoOptions(EditorState editorState, FlashbackConfigV1 config) {
        String[] encoders;
        VideoCodec newCodec;
        Enum[] containers;
        if (editorState != null && !editorState.replayVisuals.renderSky) {
            if (ImGui.checkbox(I18n.get((String)"flashback.transparent_sky", (Object[])new Object[0]), config.internalExport.transparentBackground)) {
                config.internalExport.transparentBackground = !config.internalExport.transparentBackground;
            }
        } else {
            config.internalExport.transparentBackground = false;
        }
        if (config.internalExport.transparentBackground) {
            if (supportedContainersWithTransparency == null) {
                supportedContainersWithTransparency = VideoContainer.findSupportedContainers(true);
            }
            containers = supportedContainersWithTransparency;
        } else {
            if (supportedContainers == null) {
                supportedContainers = VideoContainer.findSupportedContainers(false);
            }
            containers = supportedContainers;
        }
        if (containers.length == 0) {
            ImGui.textUnformatted(I18n.get((String)"flashback.no_supported_containers_found", (Object[])new Object[0]));
            return;
        }
        if (config.internalExport.container == null || !Arrays.asList(containers).contains(config.internalExport.container)) {
            config.internalExport.container = containers[0];
        }
        config.internalExport.container = (VideoContainer)ImGuiHelper.enumCombo((String)I18n.get((String)"flashback.container", (Object[])new Object[0]), (Enum)config.internalExport.container, (Enum[])containers);
        if (config.internalExport.container.isImageSequence()) {
            ImGui.inputText(I18n.get((String)"flashback.filenames", (Object[])new Object[0]), pngSequenceFormat);
            return;
        }
        Enum[] codecs = config.internalExport.container.getSupportedVideoCodecs(config.internalExport.transparentBackground);
        if (codecs.length == 0) {
            ImGui.textUnformatted(I18n.get((String)"flashback.no_supported_codecs_found", (Object[])new Object[0]));
            return;
        }
        if (config.internalExport.videoCodec == null || !Arrays.asList(codecs).contains(config.internalExport.videoCodec)) {
            config.internalExport.videoCodec = codecs[0];
        }
        if (codecs.length > 1 && (newCodec = (VideoCodec)ImGuiHelper.enumCombo((String)I18n.get((String)"flashback.codec", (Object[])new Object[0]), (Enum)config.internalExport.videoCodec, (Enum[])codecs)) != config.internalExport.videoCodec) {
            config.internalExport.videoCodec = newCodec;
            config.internalExport.selectedVideoEncoder[0] = 0;
        }
        if ((encoders = config.internalExport.videoCodec.getEncoders()).length > 1) {
            ImGuiHelper.combo(I18n.get((String)"flashback.encoder", (Object[])new Object[0]), config.internalExport.selectedVideoEncoder, encoders);
        }
        if (config.internalExport.videoCodec != VideoCodec.GIF) {
            if (ImGui.checkbox(I18n.get((String)"flashback.use_maximum_bitrate", (Object[])new Object[0]), config.internalExport.useMaximumBitrate)) {
                boolean bl = config.internalExport.useMaximumBitrate = !config.internalExport.useMaximumBitrate;
            }
            if (!config.internalExport.useMaximumBitrate) {
                ImGui.inputText(I18n.get((String)"flashback.bitrate", (Object[])new Object[0]), bitrate);
                if (ImGui.isItemDeactivatedAfterEdit()) {
                    int numBitrate = StartExportWindow.stringToBitrate(ImGuiHelper.getString(bitrate));
                    bitrate.set(StartExportWindow.bitrateToString(numBitrate));
                }
            }
        } else {
            ImGui.pushTextWrapPos();
            ImGui.textColored(-1, I18n.get((String)"flashback.gif_output_warning", (Object[])new Object[0]));
            ImGui.popTextWrapPos();
        }
    }

    private static CompletableFuture<ExportSettings> createExportSettings(@Nullable String name, FlashbackConfigV1 config) {
        int numBitrate = config.internalExport.useMaximumBitrate ? 0 : StartExportWindow.stringToBitrate(ImGuiHelper.getString(bitrate));
        String defaultName = StartExportWindow.getDefaultFilename(name, config.internalExport.container.extension(), config);
        Function<String, ExportSettings> callback = pathStr -> {
            if (pathStr != null) {
                LocalPlayer player;
                int end;
                int start;
                EditorState editorState = EditorStateManager.getCurrent();
                if (editorState == null) {
                    return null;
                }
                if (startEndTick[0] >= 0 && startEndTick[1] >= 0) {
                    start = Math.max(0, startEndTick[0]);
                    end = Math.max(start, startEndTick[1]);
                } else {
                    EditorState.StartAndEnd firstAndLastInTracks = editorState.getFirstAndLastTicksInTracks();
                    start = firstAndLastInTracks.start();
                    end = firstAndLastInTracks.end();
                    if (start < 0) {
                        start = 0;
                    }
                    if (end < 0 || start == end) {
                        ReplayServer replayServer = Flashback.getReplayServer();
                        end = replayServer != null ? replayServer.getTotalReplayTicks() : start + 100;
                    }
                }
                ReplayServer replayServer = Flashback.getReplayServer();
                if (replayServer != null) {
                    int totalTicks = replayServer.getTotalReplayTicks();
                    start = Math.min(start, totalTicks);
                    end = Math.min(end, totalTicks);
                }
                if ((player = Minecraft.getInstance().player) == null) {
                    return null;
                }
                boolean transparent = config.internalExport.transparentBackground && !editorState.replayVisuals.renderSky;
                VideoCodec useVideoCodec = config.internalExport.videoCodec;
                VideoCodec[] codecs = config.internalExport.container.getSupportedVideoCodecs(transparent);
                if (useVideoCodec == null || !Arrays.asList(codecs).contains(useVideoCodec)) {
                    useVideoCodec = codecs[0];
                }
                String encoder = useVideoCodec.getEncoders()[config.internalExport.selectedVideoEncoder[0]];
                AudioCodec useAudioCodec = config.internalExport.audioCodec;
                if (!config.internalExport.recordAudio || config.internalExport.container.getSupportedAudioCodecs().length == 0) {
                    useAudioCodec = null;
                }
                Path path = Path.of(pathStr, new String[0]);
                config.internalExport.defaultExportPath = path.getParent().toString();
                return new ExportSettings(name, editorState.copy(), player.position(), player.getYRot(), player.getXRot(), config.internalExport.resolution[0], config.internalExport.resolution[1], start, end, config.internalExport.projection, config.internalExport.orthographicZoom[0], Math.max(1.0f, config.internalExport.framerate[0]), config.internalExport.resetRng, config.internalExport.depthMap, config.internalExport.container, useVideoCodec, encoder, numBitrate, transparent, config.internalExport.ssaa, config.internalExport.noGui, config.internalExport.stereoAudio, useAudioCodec, path, ImGuiHelper.getString(pngSequenceFormat));
            }
            return null;
        };
        String defaultExportPathString = config.internalExport.defaultExportPath;
        if (config.internalExport.container.isImageSequence()) {
            return AsyncFileDialogs.openFolderDialog(defaultExportPathString).thenApply(callback);
        }
        return AsyncFileDialogs.saveFileDialog(defaultExportPathString, defaultName, config.internalExport.container.extension(), config.internalExport.container.extension()).thenApply(callback);
    }

    @NotNull
    public static String getDefaultFilename(@Nullable String name, String extension, FlashbackConfigV1 config) {
        String desiredName;
        Path defaultPath = FabricLoader.getInstance().getGameDir();
        try {
            if (config.internalExport.defaultExportPath == null || config.internalExport.defaultExportPath.isBlank() || !Files.exists(Path.of(config.internalExport.defaultExportPath, new String[0]), new LinkOption[0])) {
                config.internalExport.defaultExportPath = defaultPath.toString();
            } else {
                defaultPath = Path.of(config.internalExport.defaultExportPath, new String[0]);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        Object defaultName = null;
        if (name != null) {
            try {
                defaultName = FileUtil.findAvailableName((Path)defaultPath, (String)name, (String)("." + extension));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (defaultName == null) {
            desiredName = Utils.resolveFilenameTemplate(Flashback.getConfig().exporting.defaultExportFilename);
            try {
                defaultName = FileUtil.findAvailableName((Path)defaultPath, (String)desiredName, (String)("." + extension));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (defaultName == null) {
            desiredName = Utils.resolveFilenameTemplate(Flashback.getConfig().exporting.defaultExportFilename);
            defaultName = desiredName + "." + extension;
        }
        return defaultName;
    }

    private static int stringToBitrate(String string) {
        int number = 0;
        int modifier = 1;
        int total = 0;
        for (char c : string.toCharArray()) {
            if (c >= '0' && c <= '9') {
                if (modifier > 1) {
                    total += number * modifier;
                    number = 0;
                    modifier = 1;
                }
                number *= 10;
                number += c - 48;
                continue;
            }
            if (c == 'k') {
                modifier *= 1000;
                continue;
            }
            if (c == 'm') {
                modifier *= 1000000;
                continue;
            }
            if (c != 'b') continue;
            modifier *= 1000000000;
        }
        return total += number * modifier;
    }

    private static String bitrateToString(int bitrate) {
        if (bitrate >= 1000000000 && bitrate == bitrate / 1000000000 * 1000000000) {
            return bitrate / 1000000000 + "b";
        }
        if (bitrate >= 1000000 && bitrate == bitrate / 1000000 * 1000000) {
            return bitrate / 1000000 + "m";
        }
        if (bitrate >= 1000 && bitrate == bitrate / 1000 * 1000) {
            return bitrate / 1000 + "k";
        }
        return String.valueOf(bitrate);
    }

    public static void open() {
        open = true;
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState == null) {
            StartExportWindow.startEndTick[0] = -1;
            StartExportWindow.startEndTick[1] = -1;
            return;
        }
        EditorState.StartAndEnd startAndEnd = editorState.getExportStartAndEnd();
        StartExportWindow.startEndTick[0] = startAndEnd.start();
        StartExportWindow.startEndTick[1] = startAndEnd.end();
    }

    static {
        StartExportWindow.bitrate.inputData.allowedChars = "0123456789kmb";
    }
}

