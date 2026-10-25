/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.moulberry.lattice.LatticeDynamicFrequency
 *  com.moulberry.lattice.annotation.LatticeCategory
 *  com.moulberry.lattice.annotation.LatticeFormatValues
 *  com.moulberry.lattice.annotation.LatticeOption
 *  com.moulberry.lattice.annotation.constraint.LatticeEnableIf
 *  com.moulberry.lattice.annotation.constraint.LatticeFloatRange
 *  com.moulberry.lattice.annotation.constraint.LatticeIntRange
 *  com.moulberry.lattice.annotation.constraint.LatticeShowIf
 *  com.moulberry.lattice.annotation.widget.LatticeWidgetButton
 *  com.moulberry.lattice.annotation.widget.LatticeWidgetDropdown
 *  com.moulberry.lattice.annotation.widget.LatticeWidgetKeybind
 *  com.moulberry.lattice.annotation.widget.LatticeWidgetMessage
 *  com.moulberry.lattice.annotation.widget.LatticeWidgetSlider
 *  com.moulberry.lattice.annotation.widget.LatticeWidgetTextArea
 *  com.moulberry.lattice.annotation.widget.LatticeWidgetTextField
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.network.chat.Component
 */
package com.moulberry.flashback.configuration;

import com.google.gson.JsonObject;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.FlashbackGson;
import com.moulberry.flashback.combo_options.AudioCodec;
import com.moulberry.flashback.combo_options.ExportProjection;
import com.moulberry.flashback.combo_options.MarkerColour;
import com.moulberry.flashback.combo_options.MovementDirection;
import com.moulberry.flashback.combo_options.RecordingControlsLocation;
import com.moulberry.flashback.combo_options.VideoCodec;
import com.moulberry.flashback.combo_options.VideoContainer;
import com.moulberry.flashback.configuration.FlashbackConfigV0;
import com.moulberry.flashback.configuration.ForceDefaultExportSettings;
import com.moulberry.flashback.editor.keybinds.Keybinds;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import com.moulberry.flashback.screen.select_replay.ReplaySorting;
import com.moulberry.lattice.LatticeDynamicFrequency;
import com.moulberry.lattice.annotation.LatticeCategory;
import com.moulberry.lattice.annotation.LatticeFormatValues;
import com.moulberry.lattice.annotation.LatticeOption;
import com.moulberry.lattice.annotation.constraint.LatticeEnableIf;
import com.moulberry.lattice.annotation.constraint.LatticeFloatRange;
import com.moulberry.lattice.annotation.constraint.LatticeIntRange;
import com.moulberry.lattice.annotation.constraint.LatticeShowIf;
import com.moulberry.lattice.annotation.widget.LatticeWidgetButton;
import com.moulberry.lattice.annotation.widget.LatticeWidgetDropdown;
import com.moulberry.lattice.annotation.widget.LatticeWidgetKeybind;
import com.moulberry.lattice.annotation.widget.LatticeWidgetMessage;
import com.moulberry.lattice.annotation.widget.LatticeWidgetSlider;
import com.moulberry.lattice.annotation.widget.LatticeWidgetTextArea;
import com.moulberry.lattice.annotation.widget.LatticeWidgetTextField;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;

public class FlashbackConfigV1 {
    private static final int CURRENT_CONFIG_VERSION = 2;
    private int configVersion = -1;
    public Map<String, String> keybinds = new LinkedHashMap<String, String>();
    @LatticeCategory(name="flashback.option.recording_controls")
    public SubcategoryRecordingControls recordingControls = new SubcategoryRecordingControls();
    @LatticeCategory(name="flashback.option.recording")
    public SubcategoryRecording recording = new SubcategoryRecording();
    @LatticeCategory(name="flashback.option.exporting")
    public SubcategoryExporting exporting = new SubcategoryExporting();
    @LatticeCategory(name="flashback.option.keyframes")
    public SubcategoryKeyframes keyframes = new SubcategoryKeyframes();
    @LatticeCategory(name="flashback.option.editor_movement")
    public SubcategoryEditorMovement editorMovement = new SubcategoryEditorMovement();
    @LatticeCategory(name="flashback.option.marker")
    public SubcategoryMarker marker = new SubcategoryMarker();
    @LatticeCategory(name="flashback.overlay")
    public SubcategoryOverlay overlay = new SubcategoryOverlay();
    @LatticeCategory(name="flashback.advanced")
    public SubcategoryAdvanced advanced = new SubcategoryAdvanced();
    public SubcategoryInternal internal = new SubcategoryInternal();
    public SubcategoryInternalExport internalExport = new SubcategoryInternalExport();
    public ForceDefaultExportSettings forceDefaultExportSettings = new ForceDefaultExportSettings();
    private transient int saveDelay = 0;

    private FlashbackConfigV1() {
    }

    public static FlashbackConfigV1 tryLoadFromFolder(Path configFolder) {
        Path primary = configFolder.resolve("flashback.json");
        Path backup = configFolder.resolve(".flashback.json.backup");
        if (Files.exists(primary, new LinkOption[0])) {
            try {
                return FlashbackConfigV1.load(primary);
            }
            catch (Exception e) {
                Flashback.LOGGER.error("Failed to load config from {}", (Object)primary, (Object)e);
            }
        }
        if (Files.exists(backup, new LinkOption[0])) {
            try {
                return FlashbackConfigV1.load(backup);
            }
            catch (Exception e) {
                Flashback.LOGGER.error("Failed to load config from {}", (Object)backup, (Object)e);
            }
        }
        return new FlashbackConfigV1();
    }

    public void delayedSaveToDefaultFolder() {
        if (this.saveDelay <= 0) {
            this.saveDelay = 200;
        }
    }

    public void tickDelayedSave() {
        if (this.saveDelay > 0) {
            --this.saveDelay;
            if (this.saveDelay == 0) {
                this.saveToDefaultFolder();
            }
        }
    }

    public void saveToDefaultFolder() {
        Keybinds.save(this);
        Path configFolder = FabricLoader.getInstance().getConfigDir().resolve("flashback");
        this.saveToFolder(configFolder);
        this.saveDelay = 0;
    }

    private synchronized void saveToFolder(Path configFolder) {
        Path primary = configFolder.resolve("flashback.json");
        Path backup = configFolder.resolve(".flashback.json.backup");
        if (Files.exists(primary, new LinkOption[0])) {
            try {
                FlashbackConfigV1.load(primary);
                try {
                    Files.move(primary, backup, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                }
                catch (IOException e) {
                    Flashback.LOGGER.error("Failed to backup config", (Throwable)e);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.save(primary);
    }

    private static FlashbackConfigV1 load(Path path) throws IOException {
        String serialized = Files.readString(path);
        try {
            JsonObject jsonObject = (JsonObject)FlashbackGson.PRETTY.fromJson(serialized, JsonObject.class);
            if (!jsonObject.has("configVersion")) {
                FlashbackConfigV0 legacyConfig = (FlashbackConfigV0)FlashbackGson.PRETTY.fromJson(serialized, FlashbackConfigV0.class);
                FlashbackConfigV1 config = (FlashbackConfigV1)FlashbackGson.PRETTY.fromJson(serialized, FlashbackConfigV1.class);
                legacyConfig.migrateTo(config);
                config.configVersion = 2;
                return config;
            }
            int n = jsonObject.get("configVersion").getAsInt();
        }
        catch (Exception jsonObject) {
            // empty catch block
        }
        FlashbackConfigV1 config = (FlashbackConfigV1)FlashbackGson.PRETTY.fromJson(serialized, FlashbackConfigV1.class);
        config.configVersion = 2;
        return config;
    }

    private void save(Path path) {
        String serialized = FlashbackGson.PRETTY.toJson((Object)this, FlashbackConfigV1.class);
        try {
            Files.writeString(path, (CharSequence)serialized, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE, StandardOpenOption.DSYNC);
        }
        catch (IOException e) {
            Flashback.LOGGER.error("Failed to save config", (Throwable)e);
        }
    }

    public static class SubcategoryRecordingControls {
        @LatticeOption(title="flashback.option.recording_controls.location", description="!!.description")
        @LatticeWidgetDropdown
        public RecordingControlsLocation controlsLocation = RecordingControlsLocation.RIGHT;
        @LatticeOption(title="flashback.option.recording_controls.automatically_start", description="!!.description")
        @LatticeWidgetButton
        public boolean automaticallyStart = false;
        @LatticeOption(title="flashback.option.recording_controls.automatically_finish", description="!!.description")
        @LatticeWidgetButton
        public boolean automaticallyFinish = true;
        @LatticeOption(title="flashback.option.recording_controls.show_recording_toasts", description="!!.description")
        @LatticeWidgetButton
        public boolean showRecordingToasts = true;
        @LatticeOption(title="flashback.option.recording_controls.quicksave", description="!!.description")
        @LatticeWidgetButton
        public boolean quicksave = false;
    }

    public static class SubcategoryRecording {
        @LatticeOption(title="flashback.option.recording.mark_dimension_changes", description="!!.description")
        @LatticeWidgetButton
        public boolean markDimensionChanges = true;
        @LatticeOption(title="flashback.option.recording.record_hotbar", description="!!.description")
        @LatticeWidgetButton
        public boolean recordHotbar = false;
        @LatticeOption(title="flashback.option.recording.local_player_updates_per_second", description="!!.description")
        @LatticeFormatValues(formattingString="flashback.option.per_second", translate=true)
        @LatticeIntRange(min=20, max=120, step=20, clampMin=20, clampMax=360, clampStep=20)
        @LatticeWidgetSlider
        public int localPlayerUpdatesPerSecond = 20;
        @LatticeOption(title="flashback.option.recording.record_voice_chat", description="!!.description")
        @LatticeWidgetButton
        @LatticeShowIf(function="hasSimpleVoiceChat")
        public boolean recordVoiceChat = false;
        @LatticeOption(title="flashback.option.recording.record_bobby_chunks", description="!!.description")
        @LatticeWidgetButton
        @LatticeShowIf(function="hasBobby")
        public boolean recordBobbyIntoReplay = false;

        public boolean hasSimpleVoiceChat() {
            return FabricLoader.getInstance().isModLoaded("voicechat");
        }

        public boolean hasBobby() {
            return Flashback.isBobbyLoaded;
        }
    }

    public static class SubcategoryExporting {
        @LatticeOption(title="flashback.export_filename", description="flashback.export_filename_tooltip")
        @LatticeWidgetTextField
        public String defaultExportFilename = "%date%T%time%";
        @LatticeOption(title="flashback.dummy_render_frames", description="flashback.dummy_render_frames_description")
        @LatticeIntRange(min=0, max=60, clampMin=0)
        @LatticeWidgetSlider
        public int exportRenderDummyFrames = 0;
        @LatticeOption(title="flashback.use_system_ffmpeg", description="!!.description")
        @LatticeWidgetButton
        public boolean useSystemFFmpeg = false;
    }

    public static class SubcategoryKeyframes {
        @LatticeOption(title="flashback.default_interpolation", description="flashback.default_interpolation_description")
        @LatticeWidgetDropdown
        public InterpolationType defaultInterpolationType = InterpolationType.SMOOTH;
        @LatticeOption(title="flashback.use_realtime_interpolation", description="flashback.use_realtime_interpolation_description")
        @LatticeWidgetButton
        public boolean useRealtimeInterpolation = true;
    }

    public static class SubcategoryEditorMovement {
        @LatticeOption(title="flashback.movement_direction")
        @LatticeWidgetButton
        public MovementDirection flightDirection = MovementDirection.HORIZONTAL;
        @LatticeOption(title="flashback.momentum")
        @LatticeFloatRange(min=0.0f, max=1.0f, clampMin=0.0f, clampMax=1.0f)
        @LatticeWidgetSlider
        public float flightMomentum = 1.0f;
        @LatticeOption(title="flashback.lock_x")
        @LatticeWidgetButton
        public boolean flightLockX = false;
        @LatticeOption(title="flashback.lock_y")
        @LatticeWidgetButton
        public boolean flightLockY = false;
        @LatticeOption(title="flashback.lock_z")
        @LatticeWidgetButton
        public boolean flightLockZ = false;
        @LatticeOption(title="flashback.lock_yaw")
        @LatticeWidgetButton
        public boolean flightLockYaw = false;
        @LatticeOption(title="flashback.lock_pitch")
        @LatticeWidgetButton
        public boolean flightLockPitch = false;
    }

    public static class SubcategoryMarker {
        @LatticeOption(title="flashback.keybind.create_marker_1")
        @LatticeWidgetKeybind
        private final transient KeyMapping createMarker1KeyBind = Flashback.createMarker1KeyBind;
        @LatticeOption(title="flashback.keybind.create_marker_2")
        @LatticeWidgetKeybind
        private final transient KeyMapping createMarker2KeyBind = Flashback.createMarker2KeyBind;
        @LatticeOption(title="flashback.keybind.create_marker_3")
        @LatticeWidgetKeybind
        private final transient KeyMapping createMarker3KeyBind = Flashback.createMarker3KeyBind;
        @LatticeOption(title="flashback.keybind.create_marker_4")
        @LatticeWidgetKeybind
        private final transient KeyMapping createMarker4KeyBind = Flashback.createMarker4KeyBind;
        @LatticeCategory(name="flashback.option.marker.marker_1_options")
        @LatticeEnableIf(function="createMarker1IsBound", frequency=LatticeDynamicFrequency.EVERY_TICK)
        public SubcategoryMarkerOptions markerOptions1 = new SubcategoryMarkerOptions();
        @LatticeCategory(name="flashback.option.marker.marker_2_options")
        @LatticeEnableIf(function="createMarker2IsBound", frequency=LatticeDynamicFrequency.EVERY_TICK)
        public SubcategoryMarkerOptions markerOptions2 = new SubcategoryMarkerOptions();
        @LatticeCategory(name="flashback.option.marker.marker_3_options")
        @LatticeEnableIf(function="createMarker3IsBound", frequency=LatticeDynamicFrequency.EVERY_TICK)
        public SubcategoryMarkerOptions markerOptions3 = new SubcategoryMarkerOptions();
        @LatticeCategory(name="flashback.option.marker.marker_4_options")
        @LatticeEnableIf(function="createMarker4IsBound", frequency=LatticeDynamicFrequency.EVERY_TICK)
        public SubcategoryMarkerOptions markerOptions4 = new SubcategoryMarkerOptions();

        private boolean createMarker1IsBound() {
            return !this.createMarker1KeyBind.isUnbound();
        }

        private boolean createMarker2IsBound() {
            return !this.createMarker2KeyBind.isUnbound();
        }

        private boolean createMarker3IsBound() {
            return !this.createMarker3KeyBind.isUnbound();
        }

        private boolean createMarker4IsBound() {
            return !this.createMarker4KeyBind.isUnbound();
        }

        public static class SubcategoryMarkerOptions {
            @LatticeOption(title="flashback.color")
            @LatticeWidgetDropdown
            public MarkerColour color = MarkerColour.RED;
            @LatticeOption(title="flashback.custom_rgb")
            @LatticeWidgetTextField
            @LatticeShowIf(function="isColorSetToCustom", frequency=LatticeDynamicFrequency.EVERY_TICK)
            public String customRGB = "#FF0000";
            @LatticeOption(title="flashback.option.marker.options.save_position")
            @LatticeWidgetButton
            public boolean savePosition = true;
            @LatticeOption(title="flashback.description")
            @LatticeWidgetTextField
            public String description = "";

            private boolean isColorSetToCustom() {
                return this.color == MarkerColour.CUSTOM_RGB;
            }
        }
    }

    public static class SubcategoryOverlay {
        @LatticeOption(title="flashback.overlay.rtc_overlay", description="!!.description")
        @LatticeWidgetButton
        public boolean rtcOverlay = false;
    }

    public static class SubcategoryAdvanced {
        @LatticeWidgetMessage(maxRows=5)
        public transient Component warning = Component.translatable((String)"flashback.advanced_description");
        @LatticeOption(title="flashback.disable_first_person_updates")
        @LatticeWidgetButton
        public boolean disableIncreasedFirstPersonUpdates = false;
        @LatticeOption(title="flashback.disable_third_person_cancel")
        @LatticeWidgetButton
        public boolean disableThirdPersonCancel = false;
        @LatticeOption(title="flashback.advanced.synchronize_ticking", description="!!.description")
        @LatticeWidgetButton
        public boolean synchronizeTicking = false;
        @LatticeOption(title="flashback.advanced.ignored_custom_packets", description="!!.description")
        @LatticeWidgetTextArea
        public String ignoredCustomPayloads = "";
    }

    public static class SubcategoryInternal {
        public List<String> recentReplays = new ArrayList<String>();
        public Set<String> openedWindows = new HashSet<String>();
        public long nextUnsupportedModLoaderWarning = 0L;
        public boolean filterUnnecessaryPackets = true;
        public boolean signedRenderFilter = false;
        public int viewedTipsOfTheDay = 0;
        public boolean showTipOfTheDay = true;
        public long nextTipOfTheDay = 0L;
        public ReplaySorting replaySorting = ReplaySorting.CREATED_DATE;
        public boolean sortDescending = true;
        public float defaultOverrideFov = 70.0f;
        public boolean enableOverrideFovByDefault = false;
        public boolean nfdUsePortal = true;
    }

    public static class SubcategoryInternalExport {
        public int[] resolution = new int[]{1920, 1080};
        public float[] framerate = new float[]{60.0f};
        public ExportProjection projection = ExportProjection.PERSPECTIVE;
        public float[] orthographicZoom = new float[]{1.0f};
        public boolean resetRng = false;
        public boolean ssaa = false;
        public boolean noGui = false;
        public boolean depthMap = false;
        public VideoContainer container = null;
        public VideoCodec videoCodec = null;
        public int[] selectedVideoEncoder = new int[]{0};
        public boolean useMaximumBitrate = false;
        public boolean recordAudio = false;
        public boolean transparentBackground = false;
        public AudioCodec audioCodec = AudioCodec.AAC;
        public boolean stereoAudio = false;
        public String defaultExportPath = null;
    }
}

