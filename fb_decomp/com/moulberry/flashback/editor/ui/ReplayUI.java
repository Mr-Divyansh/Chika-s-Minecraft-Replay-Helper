/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.opengl.GlStateManager
 *  com.mojang.blaze3d.platform.Window
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.LevelLoadingScreen
 *  net.minecraft.client.gui.screens.LoadingOverlay
 *  net.minecraft.client.gui.screens.ProgressScreen
 *  net.minecraft.client.input.InputQuirks
 *  net.minecraft.client.multiplayer.MultiPlayerGameMode
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.language.ClientLanguage
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.locale.Language
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.ProjectileUtil
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.GameType
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec2
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector3f
 *  org.joml.Vector4f
 *  org.lwjgl.glfw.GLFW
 */
package com.moulberry.flashback.editor.ui;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.combo_options.Sizing;
import com.moulberry.flashback.configuration.FlashbackConfigV1;
import com.moulberry.flashback.editor.ui.CustomImGuiImplGl3;
import com.moulberry.flashback.editor.ui.CustomImGuiImplGlfw;
import com.moulberry.flashback.editor.ui.DailyTips;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.ReplayUIDefaults;
import com.moulberry.flashback.editor.ui.windows.ExportDoneWindow;
import com.moulberry.flashback.editor.ui.windows.ExportQueueWindow;
import com.moulberry.flashback.editor.ui.windows.ExportScreenshotWindow;
import com.moulberry.flashback.editor.ui.windows.MainMenuBar;
import com.moulberry.flashback.editor.ui.windows.PreferencesWindow;
import com.moulberry.flashback.editor.ui.windows.SelectedEntityPopup;
import com.moulberry.flashback.editor.ui.windows.StartExportWindow;
import com.moulberry.flashback.editor.ui.windows.TimelineWindow;
import com.moulberry.flashback.editor.ui.windows.VisualsWindow;
import com.moulberry.flashback.editor.ui.windows.WindowType;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import imgui.moulberry90.ImDrawData;
import imgui.moulberry90.ImDrawList;
import imgui.moulberry90.ImFont;
import imgui.moulberry90.ImFontAtlas;
import imgui.moulberry90.ImFontConfig;
import imgui.moulberry90.ImFontGlyphRangesBuilder;
import imgui.moulberry90.ImGuiIO;
import imgui.moulberry90.ImGuiViewport;
import imgui.moulberry90.internal.ImGui;
import imgui.moulberry90.internal.ImGuiContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.input.InputQuirks;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.locale.Language;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

public class ReplayUI {
    public static final CustomImGuiImplGlfw imguiGlfw = new CustomImGuiImplGlfw();
    private static final CustomImGuiImplGl3 imguiGl3 = new CustomImGuiImplGl3();
    private static boolean initialized = false;
    private static boolean isFrameFocused = false;
    private static boolean isFrameHovered = false;
    public static int frameX = 0;
    public static int frameY = 0;
    public static int frameWidth = 1;
    public static int frameHeight = 1;
    public static int viewportSizeX = 1;
    public static int viewportSizeY = 1;
    private static boolean activeLastFrame = false;
    public static int focusMainWindowCounter = 0;
    public static boolean hasAnyPopupOpen = false;
    public static Matrix4f lastProjectionMatrix = null;
    public static Quaternionf lastViewQuaternion = null;
    public static ImFont font = null;
    private static String languageCode = null;
    private static float globalScale = 1.0f;
    public static float newGlobalScale = 1.0f;
    private static float contentScale = 1.0f;
    private static ImGuiContext imGuiContext = null;
    private static ImGuiIO imGuiIO = null;
    private static boolean popupOpenLastFrame = false;
    private static String infoOverlayText = null;
    private static long infoOverlayEndMillis = 0L;
    private static UUID selectedEntity = null;
    private static boolean openSelectedEntityPopup = false;
    private static int displayingTip = -1;
    private static boolean dontShowTipsOnStartupCheckbox = false;
    private static boolean cancelPressed = false;
    private static boolean confirmPressed = false;
    public static boolean shownRegistryErrorWarning = false;
    public static boolean shownPlayerSpawnErrorWarning = false;

    public static void init() {
        if (initialized) {
            return;
        }
        initialized = true;
        Path path = Flashback.getConfigDirectory().resolve("imgui.ini");
        if (!Files.exists(path, new LinkOption[0])) {
            try {
                Files.writeString(path, (CharSequence)"[Window][###Timeline]\nPos=0,830\nSize=1920,250\nCollapsed=0\nDockId=0x00000002,0\n\n[Window][###Visuals]\nPos=1670,0\nSize=250,830\nCollapsed=0\nDockId=0x00000004,0\n\n[Docking][Data]\nDockSpace     ID=0x7C6B3D9B Window=0xA787BDB4 Pos=0,22 Size=1920,1080 Split=Y Selected=0x1F1A625A\n  DockNode    ID=0x00000001 Parent=0x7C6B3D9B SizeRef=1920,830 Split=X Selected=0x1F1A625A\n    DockNode  ID=0x00000003 Parent=0x00000001 SizeRef=1670,830 CentralNode=1 NoTabBar=1 Selected=0x1F1A625A\n    DockNode  ID=0x00000004 Parent=0x00000001 SizeRef=250,830 Selected=0x595032EA\n  DockNode    ID=0x00000002 Parent=0x7C6B3D9B SizeRef=1920,250 Selected=0xBF88A430\n", new OpenOption[0]);
            }
            catch (IOException iOException) {}
        } else {
            try {
                String imguiIni = Files.readString(path);
                boolean modified = false;
                if (imguiIni.contains("[Window][Timeline]")) {
                    imguiIni = imguiIni.replace("[Window][Timeline]", "[Window][###Timeline]");
                    modified = true;
                }
                if (imguiIni.contains("[Window][Visuals]")) {
                    imguiIni = imguiIni.replace("[Window][Visuals]", "[Window][###Visuals]");
                    modified = true;
                }
                if (imguiIni.contains("0x8B93E3BD")) {
                    imguiIni = imguiIni.replace("0x8B93E3BD", "0x7C6B3D9B");
                    modified = true;
                }
                if (modified) {
                    Files.writeString(path, (CharSequence)imguiIni, new OpenOption[0]);
                }
            }
            catch (IOException imguiIni) {
                // empty catch block
            }
        }
        long oldImGuiContext = imgui.moulberry90.ImGui.getCurrentContext().ptr;
        imGuiContext = new ImGuiContext(imgui.moulberry90.ImGui.createContext().ptr);
        imgui.moulberry90.ImGui.setCurrentContext(imGuiContext);
        imGuiIO = new ImGuiIO(imgui.moulberry90.ImGui.getIO().ptr);
        Path relativePath = FabricLoader.getInstance().getGameDir().relativize(path);
        imGuiIO.setIniFilename(relativePath.toString());
        imGuiIO.addConfigFlags(128);
        imGuiIO.setConfigMacOSXBehaviors(InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY);
        imguiGlfw.init(Minecraft.getInstance().getWindow().handle(), true);
        imguiGl3.init("#version 150");
        contentScale = ReplayUI.imguiGlfw.contentScale;
        ReplayUI.initFonts(languageCode);
        ReplayUIDefaults.applyStyle(imgui.moulberry90.ImGui.getStyle());
        ImGuiContext currentContext = imgui.moulberry90.ImGui.getCurrentContext();
        currentContext.ptr = oldImGuiContext;
        imgui.moulberry90.ImGui.setCurrentContext(currentContext);
    }

    public static void initFonts(String languageCode) {
        if (languageCode != null) {
            ReplayUI.languageCode = languageCode;
        } else {
            languageCode = "en_us";
        }
        if (!initialized) {
            return;
        }
        ImGuiIO io = ReplayUI.getIO();
        ImFontAtlas fonts = io.getFonts();
        fonts.clear();
        int size = (int)(16.0f * ReplayUI.getUiScale());
        ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder();
        rangesBuilder.addRanges(fonts.getGlyphRangesDefault());
        if (languageCode.startsWith("uk") || languageCode.startsWith("ru") || languageCode.startsWith("bg")) {
            ReplayUI.addRanges(rangesBuilder, fonts.getGlyphRangesCyrillic());
        } else if (languageCode.startsWith("tr")) {
            rangesBuilder.addText("\u00c7\u00e7\u011e\u011f\u0130\u0131\u00d6\u00f6\u015e\u015f\u00dc\u00fc");
        } else if (languageCode.startsWith("pl")) {
            ReplayUI.addRanges(rangesBuilder, new short[]{256, 383, 0});
        } else if (languageCode.startsWith("cs")) {
            rangesBuilder.addText("\u00c1\u00e1\u010c\u010d\u010e\u010f\u00c9\u00e9\u011a\u011b\u00cd\u00ed\u0147\u0148\u00d3\u00f3\u0158\u0159\u0160\u0161\u0164\u0165\u00da\u00fa\u016e\u016f\u00dd\u00fd\u017d\u017e");
        } else if (languageCode.startsWith("he")) {
            ReplayUI.addRanges(rangesBuilder, new short[]{1424, 1535, -1251, -1201, 0});
        } else if (languageCode.startsWith("ja")) {
            ReplayUI.addRanges(rangesBuilder, fonts.getGlyphRangesJapanese());
        } else if (languageCode.startsWith("zh")) {
            ReplayUI.addRanges(rangesBuilder, fonts.getGlyphRangesChineseFull());
        } else if (languageCode.startsWith("ko")) {
            ReplayUI.addRanges(rangesBuilder, fonts.getGlyphRangesKorean());
        }
        Language language = Language.getInstance();
        if (language instanceof ClientLanguage) {
            ClientLanguage clientLanguage = (ClientLanguage)language;
            for (String value : clientLanguage.storage.values()) {
                rangesBuilder.addText(value);
            }
        }
        rangesBuilder.addChar('\u2318');
        rangesBuilder.addChar('\u2303');
        rangesBuilder.addChar('\u2387');
        rangesBuilder.addChar('\u21e7');
        rangesBuilder.addChar('\u2756');
        rangesBuilder.addChar('\u26a0');
        rangesBuilder.addChar('\u2190');
        rangesBuilder.addChar('\u2191');
        rangesBuilder.addChar('\u2192');
        rangesBuilder.addChar('\u2193');
        for (int i = 32; i <= 348; ++i) {
            String key;
            int scancode = GLFW.glfwGetKeyScancode((int)i);
            if (scancode == -1 || (key = GLFW.glfwGetKeyName((int)i, (int)-1)) == null) continue;
            rangesBuilder.addText(key);
            rangesBuilder.addText(key.toLowerCase());
            rangesBuilder.addText(key.toUpperCase());
            rangesBuilder.addText(key.toLowerCase(Locale.ROOT));
            rangesBuilder.addText(key.toUpperCase(Locale.ROOT));
        }
        short[] glyphRanges = rangesBuilder.buildRanges();
        ImFontConfig fontConfig = new ImFontConfig();
        fontConfig.setOversampleH(2);
        fontConfig.setOversampleV(2);
        fontConfig.setName("Inter (Medium), 16px");
        fontConfig.setGlyphOffset(0.0f, 0.0f);
        font = fonts.addFontFromMemoryTTF(ReplayUI.loadFont("inter-medium.ttf"), (float)size, fontConfig, glyphRanges);
        fontConfig.setMergeMode(true);
        fontConfig.setGlyphOffset(0.0f, (int)(5.0f * ReplayUI.getUiScale()));
        io.getFonts().addFontFromMemoryTTF(ReplayUI.loadFont("materialiconsround-regular.otf"), (float)((int)(20.0f * ReplayUI.getUiScale())), fontConfig, ReplayUI.buildMaterialIconRanges());
        fontConfig.setGlyphOffset(0.0f, 0.0f);
        if (languageCode.startsWith("he")) {
            short[] hebrewRanges = new short[]{1424, 1535, -1251, -1201, 0};
            io.getFonts().addFontFromMemoryTTF(ReplayUI.loadFont("heebo-medium.ttf"), (float)size, fontConfig, hebrewRanges);
        } else if (languageCode.startsWith("ja")) {
            io.getFonts().addFontFromMemoryTTF(ReplayUI.loadFont("notosansjp-medium.ttf"), (float)(size * 5 / 4), fontConfig, glyphRanges);
        } else if (languageCode.startsWith("zh")) {
            io.getFonts().addFontFromMemoryTTF(ReplayUI.loadFont("notosanstc-medium.ttf"), (float)(size * 5 / 4), fontConfig, glyphRanges);
            io.getFonts().addFontFromMemoryTTF(ReplayUI.loadFont("notosanssc-medium.ttf"), (float)(size * 5 / 4), fontConfig, glyphRanges);
        } else if (languageCode.startsWith("ko")) {
            io.getFonts().addFontFromMemoryTTF(ReplayUI.loadFont("notosanskr-medium.ttf"), (float)(size * 5 / 4), fontConfig, glyphRanges);
        }
        fontConfig.setMergeMode(false);
        fonts.build();
        imguiGl3.updateFontsTexture();
        fontConfig.destroy();
        fonts.clearTexData();
    }

    private static void addRanges(ImFontGlyphRangesBuilder builder, short[] ranges) {
        for (int i = 0; i < ranges.length && ranges[i] != 0; i += 2) {
            int from = ranges[i] & 0xFFFF;
            int to = ranges[i + 1] & 0xFFFF;
            for (int k = from; k <= to; ++k) {
                builder.addChar((char)k);
            }
        }
    }

    private static short[] buildMaterialIconRanges() {
        ImFontGlyphRangesBuilder builder = new ImFontGlyphRangesBuilder();
        builder.addChar('\ue04b');
        builder.addChar('\ue577');
        builder.addChar('\uefeb');
        builder.addChar('\ue3af');
        builder.addChar('\ue9e4');
        builder.addChar('\ue422');
        builder.addChar('\ue518');
        builder.addChar('\ue945');
        builder.addChar('\ue8f4');
        builder.addChar('\ue8f5');
        builder.addChar('\ue148');
        builder.addChar('\ue5d2');
        builder.addChar('\ue872');
        builder.addChar('\ue3c9');
        builder.addChar('\ue40a');
        builder.addChar('\ue92b');
        builder.addChar('\ueb3b');
        builder.addChar('\ue14a');
        builder.addChar('\ue55f');
        builder.addChar('\uea44');
        builder.addChar('\ue3a1');
        builder.addChar('\ue41a');
        builder.addChar('\ue419');
        return builder.buildRanges();
    }

    private static byte[] loadFont(String name) {
        byte[] byArray;
        block9: {
            Optional resource = Minecraft.getInstance().getResourceManager().getResource(Flashback.createIdentifier(name));
            if (resource.isEmpty()) {
                throw new MissingResourceException("Missing font: " + name, "Font", "");
            }
            InputStream is = ((Resource)resource.get()).open();
            try {
                byArray = is.readAllBytes();
                if (is == null) break block9;
            }
            catch (Throwable throwable) {
                try {
                    if (is != null) {
                        try {
                            is.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            is.close();
        }
        return byArray;
    }

    public static ImGuiIO getIO() {
        if (!initialized) {
            ReplayUI.init();
        }
        return imGuiIO;
    }

    public static Vec3 getMouseForwardsVector() {
        return ReplayUI.getMouseForwardsVector(ReplayUI.getIO().getMousePosX(), ReplayUI.getIO().getMousePosY());
    }

    public static Vec3 getMouseForwardsVector(float mouseX, float mouseY) {
        if (!ReplayUI.isActive() || !isFrameHovered && !ReplayUI.isMovingCamera() || lastProjectionMatrix == null || lastViewQuaternion == null) {
            return null;
        }
        return ReplayUI.getForwardsVector(mouseX, mouseY);
    }

    public static Vec2 getMouseViewportFraction() {
        float x = (ReplayUI.getIO().getMousePosX() - imgui.moulberry90.ImGui.getMainViewport().getPosX() - (float)frameX) / (float)frameWidth;
        float y = (ReplayUI.getIO().getMousePosY() - imgui.moulberry90.ImGui.getMainViewport().getPosY() - (float)frameY) / (float)frameHeight;
        return new Vec2(x, y);
    }

    public static Vec2 getMouseViewportFraction(float mouseX, float mouseY) {
        float x = (mouseX - imgui.moulberry90.ImGui.getMainViewport().getPosX() - (float)frameX) / (float)frameWidth;
        float y = (mouseY - imgui.moulberry90.ImGui.getMainViewport().getPosY() - (float)frameY) / (float)frameHeight;
        return new Vec2(x, y);
    }

    public static Vec3 getForwardsVector(float mouseX, float mouseY) {
        float x = (mouseX - imgui.moulberry90.ImGui.getMainViewport().getPosX() - (float)frameX) / (float)frameWidth * 2.0f - 1.0f;
        float y = (mouseY - imgui.moulberry90.ImGui.getMainViewport().getPosY() - (float)frameY) / (float)frameHeight * 2.0f - 1.0f;
        return ReplayUI.getForwardsVectorRaw(x, y);
    }

    public static Vec3 getForwardsVectorRaw(float x, float y) {
        if (!ReplayUI.isMovingCamera() && (x < -1.0f || x > 1.0f || y < -1.0f || y > 1.0f)) {
            return null;
        }
        Matrix4f matrix = new Matrix4f((Matrix4fc)lastProjectionMatrix);
        matrix.invert();
        Vector4f forwards = new Vector4f(x, y, 0.0f, 1.0f);
        forwards.mul((Matrix4fc)matrix);
        return new Vec3((double)forwards.x(), (double)(-forwards.y()), (double)forwards.z()).normalize();
    }

    @Nullable
    public static Vec3 getMouseLookVectorFromForwards(Vec3 forwards) {
        if (forwards == null) {
            return null;
        }
        Vector3f view = forwards.toVector3f();
        view.rotate((Quaternionfc)lastViewQuaternion);
        return new Vec3((double)view.x(), (double)view.y(), (double)view.z()).normalize();
    }

    @Nullable
    public static Vec3 getMouseLookVector(float mouseX, float mouseY) {
        return ReplayUI.getMouseLookVectorFromForwards(ReplayUI.getMouseForwardsVector(mouseX, mouseY));
    }

    @Nullable
    public static Vec3 getMouseLookVector() {
        return ReplayUI.getMouseLookVectorFromForwards(ReplayUI.getMouseForwardsVector());
    }

    public static boolean isEntitySelected(UUID uuid) {
        return ReplayUI.isActive() && uuid.equals(selectedEntity);
    }

    public static UUID getSelectedEntity() {
        return selectedEntity;
    }

    private static void setSelectedEntity(UUID uuid) {
        selectedEntity = uuid;
    }

    public static boolean isMovingCamera() {
        return imguiGlfw.isGrabbed() && imguiGlfw.getMouseHandledBy() == CustomImGuiImplGlfw.MouseHandledBy.GAME;
    }

    public static void setInfoOverlay(String text) {
        infoOverlayText = text;
        infoOverlayEndMillis = System.currentTimeMillis() + 5000L;
    }

    public static synchronized void setInfoOverlayShort(String text) {
        infoOverlayText = text;
        infoOverlayEndMillis = System.currentTimeMillis() + 1000L;
    }

    public static void setupMainViewport() {
        Window window = Minecraft.getInstance().getWindow();
        int frameBottom = window.height - (frameY + frameHeight);
        GlStateManager._viewport((int)(frameX * window.getWidth() / window.getScreenWidth()), (int)(frameBottom * window.getHeight() / window.getScreenHeight()), (int)Math.max(1, frameWidth * window.getWidth() / window.getScreenWidth()), (int)Math.max(1, frameHeight * window.getHeight() / window.getScreenHeight()));
    }

    public static float getUiScale() {
        return globalScale * contentScale;
    }

    public static int scaleUi(int v) {
        return (int)((float)v * ReplayUI.getUiScale());
    }

    public static double getNewMouseX(double x) {
        return x - (double)frameX;
    }

    public static double getNewMouseY(double y) {
        return y - (double)frameY;
    }

    public static int getNewGameWidth(float scale) {
        return Math.max(1, Math.round((float)frameWidth * scale));
    }

    public static int getNewGameHeight(float scale) {
        return Math.max(1, Math.round((float)frameHeight * scale));
    }

    private static boolean isActiveInternal() {
        if (!Flashback.isInReplay()) {
            return false;
        }
        if (Flashback.EXPORT_JOB != null) {
            return false;
        }
        if (Minecraft.getInstance().options.hideGui) {
            return false;
        }
        MultiPlayerGameMode gameMode = Minecraft.getInstance().gameMode;
        if (gameMode == null) {
            return false;
        }
        if (gameMode.getPlayerMode() != GameType.SPECTATOR) {
            return false;
        }
        if (Minecraft.getInstance().level == null) {
            return false;
        }
        if (Minecraft.getInstance().player == null) {
            return false;
        }
        return Minecraft.getInstance().getOverlay() == null;
    }

    public static boolean isActive() {
        return activeLastFrame;
    }

    public static boolean shouldModifyViewport() {
        EditorState editorState = EditorStateManager.getCurrent();
        return ReplayUI.isActive() && editorState != null && editorState.replayVisuals.sizing != Sizing.UNDERLAY;
    }

    private static void transitionActiveState(boolean active) {
        if (activeLastFrame == active) {
            return;
        }
        activeLastFrame = active;
        Window window = Minecraft.getInstance().getWindow();
        if (window.getWidth() > 0 && window.getWidth() <= 16384 && window.getHeight() > 0 && window.getHeight() <= 16384) {
            Minecraft.getInstance().resizeGui();
        }
        imguiGlfw.ungrab();
        if (!activeLastFrame) {
            if (Minecraft.getInstance().gameMode != null) {
                if (Minecraft.getInstance().screen == null) {
                    Minecraft.getInstance().mouseHandler.releaseMouse();
                    Minecraft.getInstance().mouseHandler.grabMouse();
                } else {
                    Minecraft.getInstance().mouseHandler.grabMouse();
                    Minecraft.getInstance().mouseHandler.releaseMouse();
                }
                Minecraft.getInstance().mouseHandler.setIgnoreFirstMove();
            }
        } else {
            long handle = imgui.moulberry90.ImGui.getMainViewport().getPlatformHandle();
            if (GLFW.glfwGetInputMode((long)handle, (int)208897) != 212993) {
                GLFW.glfwSetInputMode((long)handle, (int)208897, (int)212993);
                GLFW.glfwSetCursorPos((long)handle, (double)(imgui.moulberry90.ImGui.getMainViewport().getSizeX() / 2.0f), (double)(imgui.moulberry90.ImGui.getMainViewport().getSizeY() / 2.0f));
            }
        }
        imguiGlfw.setViewportWindowsHidden(!activeLastFrame);
    }

    public static boolean isImGuiContextActive() {
        return imGuiContext != null && ReplayUI.imGuiContext.ptr == imgui.moulberry90.ImGui.getCurrentContext().ptr;
    }

    public static void drawOverlay() {
        if (!initialized && Minecraft.getInstance().getOverlay() instanceof LoadingOverlay) {
            return;
        }
        ReplayUI.init();
        GlStateManager._disableColorLogicOp();
        long oldImGuiContext = imgui.moulberry90.ImGui.getCurrentContext().ptr;
        imgui.moulberry90.ImGui.setCurrentContext(imGuiContext);
        try {
            ReplayUI.drawOverlayInternal();
        }
        catch (Throwable throwable) {
            ImGuiContext currentContext = imgui.moulberry90.ImGui.getCurrentContext();
            currentContext.ptr = oldImGuiContext;
            imgui.moulberry90.ImGui.setCurrentContext(currentContext);
            throw throwable;
        }
        ImGuiContext currentContext = imgui.moulberry90.ImGui.getCurrentContext();
        currentContext.ptr = oldImGuiContext;
        imgui.moulberry90.ImGui.setCurrentContext(currentContext);
    }

    public static void drawOverlayInternal() {
        int oldFrameX = frameX;
        int oldFrameY = frameY;
        int oldFrameWidth = frameWidth;
        int oldFrameHeight = frameHeight;
        if (!initialized) {
            throw new IllegalStateException("Tried to use EditorUI while it was not initialized");
        }
        if (Minecraft.getInstance().screen instanceof ProgressScreen || Minecraft.getInstance().screen instanceof LevelLoadingScreen) {
            return;
        }
        if (!ReplayUI.isActiveInternal()) {
            ReplayUI.transitionActiveState(false);
            imguiGlfw.updateReleaseAllKeys(true);
            focusMainWindowCounter = 5;
            return;
        }
        imguiGlfw.updateReleaseAllKeys(false);
        if (!imgui.moulberry90.ImGui.isAnyMouseDown()) {
            float newContentScale;
            if ((double)(newGlobalScale = (float)((int)(newGlobalScale * 16.0f)) / 16.0f) < 0.25) {
                newGlobalScale = 0.25f;
            }
            if (newGlobalScale > 4.0f) {
                newGlobalScale = 4.0f;
            }
            if ((double)(newContentScale = (float)((int)(ReplayUI.imguiGlfw.contentScale * 16.0f)) / 16.0f) < 0.125) {
                newContentScale = 0.125f;
            }
            if (newContentScale > 8.0f) {
                newContentScale = 8.0f;
            }
            if (globalScale != newGlobalScale || contentScale != newContentScale) {
                int oldFontSize = (int)(16.0f * ReplayUI.getUiScale());
                globalScale = newGlobalScale;
                contentScale = newContentScale;
                if (oldFontSize != (int)(16.0f * ReplayUI.getUiScale())) {
                    ReplayUI.initFonts(languageCode);
                }
            }
        }
        imguiGlfw.newFrame();
        imguiGl3.newFrame();
        imgui.moulberry90.ImGui.newFrame();
        confirmPressed = imgui.moulberry90.ImGui.isKeyPressed(525);
        cancelPressed = imgui.moulberry90.ImGui.isKeyPressed(526);
        hasAnyPopupOpen = imgui.moulberry90.ImGui.isPopupOpen("", 3072);
        if (hasAnyPopupOpen) {
            ReplayUI.getIO().addConfigFlags(1);
        } else {
            ReplayUI.getIO().removeConfigFlags(1);
        }
        MainMenuBar.render();
        if (!ReplayUI.isActiveInternal()) {
            imgui.moulberry90.ImGui.render();
            ImGuiHelper.endFrame();
            ReplayUI.transitionActiveState(false);
            imguiGlfw.updateReleaseAllKeys(true);
            focusMainWindowCounter = 5;
            return;
        }
        imgui.moulberry90.ImGui.setNextWindowBgAlpha(0.0f);
        int mainDock = imgui.moulberry90.ImGui.dockSpaceOverViewport(0, imgui.moulberry90.ImGui.getMainViewport(), 4);
        ImGui.dockBuilderGetCentralNode(mainDock).addLocalFlags(4096);
        isFrameFocused = false;
        isFrameHovered = false;
        imgui.moulberry90.ImGui.setNextWindowDockID(mainDock);
        ImGuiHelper.pushStyleVar(2, 0.0f, 0.0f);
        if (imgui.moulberry90.ImGui.begin("Main", 65966)) {
            Sizing sizing;
            if (focusMainWindowCounter > 0) {
                --focusMainWindowCounter;
                imgui.moulberry90.ImGui.setWindowFocus();
            }
            ImGuiHelper.popStyleVar();
            float minX = imgui.moulberry90.ImGui.getWindowContentRegionMinX();
            float maxX = imgui.moulberry90.ImGui.getWindowContentRegionMaxX();
            float minY = imgui.moulberry90.ImGui.getWindowContentRegionMinY();
            float maxY = imgui.moulberry90.ImGui.getWindowContentRegionMaxY();
            if (ReplayUI.getIO().hasConfigFlags(1024)) {
                frameX = (int)(imgui.moulberry90.ImGui.getWindowPosX() - imgui.moulberry90.ImGui.getWindowViewport().getPosX() + minX);
                frameY = (int)(imgui.moulberry90.ImGui.getWindowPosY() - imgui.moulberry90.ImGui.getWindowViewport().getPosY() + minY);
            } else {
                frameX = (int)(imgui.moulberry90.ImGui.getWindowPosX() + minX);
                frameY = (int)(imgui.moulberry90.ImGui.getWindowPosY() + minY);
            }
            frameWidth = (int)Math.max(1.0f, maxX - minX);
            frameHeight = (int)Math.max(1.0f, maxY - minY);
            viewportSizeX = (int)imgui.moulberry90.ImGui.getMainViewport().getSizeX();
            viewportSizeY = (int)imgui.moulberry90.ImGui.getMainViewport().getSizeY();
            EditorState editorState = EditorStateManager.getCurrent();
            Sizing sizing2 = sizing = editorState == null ? Sizing.KEEP_ASPECT_RATIO : editorState.replayVisuals.sizing;
            if (sizing == Sizing.KEEP_ASPECT_RATIO || sizing == Sizing.CHANGE_ASPECT_RATIO) {
                float currentAspectRatio = (float)frameWidth / (float)frameHeight;
                float aspectRatio = sizing == Sizing.KEEP_ASPECT_RATIO ? imgui.moulberry90.ImGui.getMainViewport().getSizeX() / imgui.moulberry90.ImGui.getMainViewport().getSizeY() : editorState.replayVisuals.changeAspectRatio.aspectRatio();
                if (currentAspectRatio < aspectRatio) {
                    int newHeight = (int)((float)frameWidth / aspectRatio);
                    frameY += (frameHeight - newHeight) / 2;
                    frameHeight = newHeight;
                } else if (currentAspectRatio > aspectRatio) {
                    int newWidth = (int)((float)frameHeight * aspectRatio);
                    frameX += (frameWidth - newWidth) / 2;
                    frameWidth = newWidth;
                }
            }
            if (imgui.moulberry90.ImGui.isWindowFocused()) {
                imgui.moulberry90.ImGui.getWindowDrawList().addRect(frameX + 1, frameY + 1, (float)(frameX + frameWidth - 1), (float)(frameY + frameHeight - 1), -4040669);
                isFrameFocused = true;
            }
            String showText = null;
            int showTextBorderColour = -1;
            if (infoOverlayText != null) {
                long currentMillis = System.currentTimeMillis();
                if (currentMillis < infoOverlayEndMillis - 30000L || currentMillis > infoOverlayEndMillis) {
                    infoOverlayText = null;
                    infoOverlayEndMillis = 0L;
                } else {
                    showText = infoOverlayText;
                    showTextBorderColour = -16711681;
                }
            }
            if (showText != null) {
                imgui.moulberry90.ImGui.setNextWindowPos((float)frameX + (float)frameWidth * 0.5f, (float)frameY + (float)frameHeight * 0.75f, 1, 0.5f, 0.5f);
                imgui.moulberry90.ImGui.setNextWindowSizeConstraints(0.0f, 0.0f, 550.0f, (float)frameHeight * 0.45f);
                ImGuiHelper.pushStyleVar(4, 2.0f);
                ImGuiHelper.pushStyleColor(5, showTextBorderColour);
                if (imgui.moulberry90.ImGui.begin("##InfoOverlay", 528739)) {
                    imgui.moulberry90.ImGui.textUnformatted(showText);
                }
                imgui.moulberry90.ImGui.end();
                ImGuiHelper.popStyleColor();
                ImGuiHelper.popStyleVar();
            }
            if (sizing == Sizing.UNDERLAY) {
                frameX = 0;
                frameY = 0;
                frameWidth = Minecraft.getInstance().getWindow().getScreenWidth();
                frameHeight = Minecraft.getInstance().getWindow().getScreenHeight();
            }
            if (Minecraft.getInstance().screen == null && Minecraft.getInstance().getOverlay() == null) {
                if (editorState != null && editorState.replayVisuals.ruleOfThirdsGuide) {
                    ImDrawList drawList = imgui.moulberry90.ImGui.getBackgroundDrawList();
                    drawList.removeFlags(1);
                    drawList.addLine(frameX + frameWidth / 3, frameY, frameX + frameWidth / 3, frameY + frameHeight, -1426063361, 2.0f);
                    drawList.addLine(frameX + frameWidth * 2 / 3, frameY, frameX + frameWidth * 2 / 3, frameY + frameHeight, -1426063361, 2.0f);
                    drawList.addLine(frameX, frameY + frameHeight / 3, frameX + frameWidth, frameY + frameHeight / 3, -1426063361, 2.0f);
                    drawList.addLine(frameX, frameY + frameHeight * 2 / 3, frameX + frameWidth, frameY + frameHeight * 2 / 3, -1426063361, 2.0f);
                    drawList.addFlags(1);
                }
                if (editorState != null && editorState.replayVisuals.centerGuide) {
                    ImDrawList drawList = imgui.moulberry90.ImGui.getBackgroundDrawList();
                    drawList.removeFlags(1);
                    drawList.addLine(frameX + frameWidth / 2, frameY, frameX + frameWidth / 2, frameY + frameHeight, -1426063361, 2.0f);
                    drawList.addLine(frameX, frameY + frameHeight / 2, frameX + frameWidth, frameY + frameHeight / 2, -1426063361, 2.0f);
                    drawList.addFlags(1);
                }
            }
            if (displayingTip == -1) {
                FlashbackConfigV1 config = Flashback.getConfig();
                if (!config.internal.showTipOfTheDay) {
                    displayingTip = 0;
                } else {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime >= config.internal.nextTipOfTheDay - TimeUnit.DAYS.toMillis(2L) && currentTime <= config.internal.nextTipOfTheDay) {
                        displayingTip = 0;
                    } else {
                        displayingTip = Integer.numberOfTrailingZeros(~config.internal.viewedTipsOfTheDay) + 1;
                        config.internal.viewedTipsOfTheDay |= 1 << displayingTip - 1;
                        config.internal.nextTipOfTheDay = currentTime + TimeUnit.DAYS.toMillis(1L);
                        config.delayedSaveToDefaultFolder();
                    }
                }
            } else if (displayingTip > 0) {
                int tip = displayingTip - 1;
                if (tip >= DailyTips.TIPS.length) {
                    displayingTip = 0;
                } else {
                    ImGuiViewport viewport = imgui.moulberry90.ImGui.getMainViewport();
                    imgui.moulberry90.ImGui.setNextWindowPos(viewport.getCenterX(), viewport.getCenterY(), 8, 0.5f, 0.5f);
                    if (imgui.moulberry90.ImGui.begin(I18n.get((String)"flashback.tip_of_the_day", (Object[])new Object[0]), 320)) {
                        boolean canShowNext;
                        boolean canShowPrev;
                        float oldPositionY = imgui.moulberry90.ImGui.getCursorPosY();
                        imgui.moulberry90.ImGui.pushTextWrapPos(ReplayUI.scaleUi(375));
                        imgui.moulberry90.ImGui.textUnformatted(DailyTips.TIPS[tip]);
                        imgui.moulberry90.ImGui.popTextWrapPos();
                        float verticalSize = imgui.moulberry90.ImGui.getCursorPosY() - oldPositionY;
                        int minimumVerticalSize = ReplayUI.scaleUi(100);
                        if (verticalSize < (float)minimumVerticalSize) {
                            imgui.moulberry90.ImGui.setCursorPosY(imgui.moulberry90.ImGui.getCursorPosY() + (float)minimumVerticalSize - verticalSize);
                        }
                        if (imgui.moulberry90.ImGui.checkbox(I18n.get((String)"flashback.replayui.dont_show_tips", (Object[])new Object[0]), dontShowTipsOnStartupCheckbox)) {
                            dontShowTipsOnStartupCheckbox = !dontShowTipsOnStartupCheckbox;
                        }
                        imgui.moulberry90.ImGui.sameLine();
                        imgui.moulberry90.ImGui.dummy(ReplayUI.scaleUi(20), 0.0f);
                        imgui.moulberry90.ImGui.sameLine();
                        if (imgui.moulberry90.ImGui.button(I18n.get((String)"flashback.close", (Object[])new Object[0])) || imgui.moulberry90.ImGui.isWindowFocused() && ReplayUI.consumeCancel()) {
                            if (dontShowTipsOnStartupCheckbox) {
                                FlashbackConfigV1 config = Flashback.getConfig();
                                config.internal.showTipOfTheDay = false;
                                config.delayedSaveToDefaultFolder();
                            }
                            displayingTip = 0;
                        }
                        imgui.moulberry90.ImGui.sameLine();
                        boolean bl = canShowPrev = displayingTip > 1;
                        if (!canShowPrev) {
                            imgui.moulberry90.ImGui.beginDisabled();
                        }
                        if (imgui.moulberry90.ImGui.button(I18n.get((String)"gui.back", (Object[])new Object[0])) && canShowPrev) {
                            FlashbackConfigV1 config = Flashback.getConfig();
                            config.internal.viewedTipsOfTheDay |= 1 << --displayingTip - 1;
                            config.delayedSaveToDefaultFolder();
                        }
                        if (!canShowPrev) {
                            imgui.moulberry90.ImGui.endDisabled();
                        }
                        imgui.moulberry90.ImGui.sameLine();
                        boolean bl2 = canShowNext = displayingTip < DailyTips.TIPS.length;
                        if (!canShowNext) {
                            imgui.moulberry90.ImGui.beginDisabled();
                        }
                        if (imgui.moulberry90.ImGui.button(I18n.get((String)"flashback.next", (Object[])new Object[0])) && canShowNext) {
                            FlashbackConfigV1 config = Flashback.getConfig();
                            config.internal.viewedTipsOfTheDay |= 1 << ++displayingTip - 1;
                            config.delayedSaveToDefaultFolder();
                        }
                        if (!canShowNext) {
                            imgui.moulberry90.ImGui.endDisabled();
                        }
                    }
                    imgui.moulberry90.ImGui.end();
                }
            }
            if (selectedEntity != null) {
                Entity entity = (Entity)Minecraft.getInstance().level.getEntities().get(selectedEntity);
                if (entity == null || editorState == null) {
                    ReplayUI.setSelectedEntity(null);
                } else if (entity instanceof Player && !editorState.replayVisuals.renderPlayers) {
                    ReplayUI.setSelectedEntity(null);
                } else if (!(entity instanceof Player) && !editorState.replayVisuals.renderEntities) {
                    ReplayUI.setSelectedEntity(null);
                } else {
                    if (openSelectedEntityPopup) {
                        imgui.moulberry90.ImGui.openPopup("###EntityPopup");
                        SelectedEntityPopup.open(entity, editorState);
                    }
                    if (ImGuiHelper.beginPopup("###EntityPopup")) {
                        SelectedEntityPopup.render(entity, editorState);
                        imgui.moulberry90.ImGui.endPopup();
                    }
                    if (!imgui.moulberry90.ImGui.isPopupOpen("###EntityPopup")) {
                        ReplayUI.setSelectedEntity(null);
                    }
                }
            }
            openSelectedEntityPopup = false;
            if (imgui.moulberry90.ImGui.isWindowHovered() && ReplayUI.getIO().getMousePosY() > imgui.moulberry90.ImGui.getWindowPosY()) {
                isFrameHovered = true;
                if (Minecraft.getInstance().screen != null) {
                    imgui.moulberry90.ImGui.setNextFrameWantCaptureMouse(false);
                } else {
                    int wheelY;
                    LocalPlayer player;
                    boolean isMovingCamera = ReplayUI.isMovingCamera();
                    if ((isFrameFocused || isMovingCamera) && (player = Minecraft.getInstance().player) != null && (wheelY = (int)Math.signum(ReplayUI.getIO().getMouseWheel())) != 0) {
                        float defaultFlyingSpeed = 0.05f;
                        float flyingSpeed = Mth.clamp((float)(player.getAbilities().getFlyingSpeed() + (float)wheelY * 0.05f / 10.0f), (float)0.005f, (float)0.5f);
                        ReplayUI.setInfoOverlay(I18n.get((String)"flashback.flying_speed", (Object[])new Object[]{Float.valueOf(flyingSpeed / 0.05f)}));
                        player.getAbilities().setFlyingSpeed(flyingSpeed);
                    }
                    if (!isMovingCamera && ReplayUI.getIO().getWantCaptureMouse() && !popupOpenLastFrame && !imgui.moulberry90.ImGui.isPopupOpen("", 3072)) {
                        ReplayUI.handleBasicInputs();
                    }
                }
            }
        } else {
            ImGuiHelper.popStyleVar();
        }
        imgui.moulberry90.ImGui.end();
        VisualsWindow.render();
        TimelineWindow.render();
        StartExportWindow.render();
        ExportScreenshotWindow.render();
        PreferencesWindow.render();
        ExportQueueWindow.render();
        WindowType.renderAll();
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer != null) {
            if (replayServer.failedToLoadRegistryDataWarning && !shownRegistryErrorWarning) {
                imgui.moulberry90.ImGui.openPopup("###RegistryWarning");
                if (ImGuiHelper.beginPopupModal(I18n.get((String)"flashback.registry_warning", (Object[])new Object[0]) + "###RegistryWarning", 64)) {
                    imgui.moulberry90.ImGui.pushTextWrapPos(ReplayUI.scaleUi(375));
                    imgui.moulberry90.ImGui.textWrapped(I18n.get((String)"flashback.registry_warning_description", (Object[])new Object[0]));
                    imgui.moulberry90.ImGui.popTextWrapPos();
                    if (imgui.moulberry90.ImGui.button(I18n.get((String)"gui.ok", (Object[])new Object[0]))) {
                        imgui.moulberry90.ImGui.closeCurrentPopup();
                        shownRegistryErrorWarning = true;
                    }
                    imgui.moulberry90.ImGui.endPopup();
                }
            } else if (replayServer.failedToSpawnPlayerWarning && !shownPlayerSpawnErrorWarning) {
                imgui.moulberry90.ImGui.openPopup("###PlayerSpawnWarning");
                if (ImGuiHelper.beginPopupModal(I18n.get((String)"flashback.player_spawn_warning", (Object[])new Object[0]) + "###PlayerSpawnWarning", 64)) {
                    imgui.moulberry90.ImGui.pushTextWrapPos(ReplayUI.scaleUi(375));
                    imgui.moulberry90.ImGui.textWrapped(I18n.get((String)"flashback.player_spawn_warning_description", (Object[])new Object[0]));
                    imgui.moulberry90.ImGui.popTextWrapPos();
                    if (imgui.moulberry90.ImGui.button(I18n.get((String)"gui.ok", (Object[])new Object[0]))) {
                        imgui.moulberry90.ImGui.closeCurrentPopup();
                        shownPlayerSpawnErrorWarning = true;
                    }
                    imgui.moulberry90.ImGui.endPopup();
                }
            }
        }
        ExportDoneWindow.render();
        popupOpenLastFrame = imgui.moulberry90.ImGui.isPopupOpen("", 3072);
        imgui.moulberry90.ImGui.render();
        ImGuiHelper.endFrame();
        long ctx = GLFW.glfwGetCurrentContext();
        imgui.moulberry90.ImGui.updatePlatformWindows();
        imgui.moulberry90.ImGui.renderPlatformWindowsDefault();
        GLFW.glfwMakeContextCurrent((long)ctx);
        ImDrawData drawData = imgui.moulberry90.ImGui.getDrawData();
        if (drawData != null) {
            imguiGl3.renderDrawData(drawData);
        }
        if (frameX != oldFrameX || frameY != oldFrameY || frameWidth != oldFrameWidth || frameHeight != oldFrameHeight) {
            Minecraft.getInstance().resizeGui();
        }
        ReplayUI.transitionActiveState(true);
    }

    public static boolean isMainFrameActive() {
        return isFrameFocused;
    }

    private static void handleBasicInputs() {
        int key;
        if (imgui.moulberry90.ImGui.isMouseClicked(1)) {
            HitResult result = ReplayUI.getLookTarget();
            if (result instanceof EntityHitResult) {
                EntityHitResult entityHitResult = (EntityHitResult)result;
                if (Minecraft.getInstance().player == Minecraft.getInstance().getCameraEntity()) {
                    Minecraft.getInstance().player.setDeltaMovement(Vec3.ZERO);
                }
                ReplayUI.setSelectedEntity(entityHitResult.getEntity().getUUID());
                openSelectedEntityPopup = true;
            }
            return;
        }
        if (imgui.moulberry90.ImGui.isMouseClicked(0) && (key = -1) != 0) {
            imguiGlfw.setGrabbed(true, key, true, (float)frameX + (float)frameWidth / 2.0f, (float)frameY + (float)frameHeight / 2.0f);
        }
    }

    @Nullable
    private static HitResult getLookTarget() {
        ClipContext clipContext;
        Vec3 look = ReplayUI.getMouseLookVector();
        if (look == null) {
            return null;
        }
        Entity cameraEntity = Minecraft.getInstance().getCameraEntity();
        if (cameraEntity == null) {
            return null;
        }
        float distance = 64.0f;
        Vec3 from = cameraEntity.getEyePosition();
        Vec3 to = from.add(look.scale((double)distance));
        BlockHitResult blockResult = null;
        EntityHitResult entityResult = null;
        EditorState editorState = EditorStateManager.getCurrent();
        if ((editorState == null || editorState.replayVisuals.renderBlocks) && (blockResult = Minecraft.getInstance().level.clip(clipContext = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, CollisionContext.empty()))).getType() != HitResult.Type.MISS) {
            distance = (float)blockResult.getLocation().distanceTo(from);
        }
        if (editorState == null || editorState.replayVisuals.renderEntities || editorState.replayVisuals.renderPlayers) {
            Predicate<Entity> predicate;
            AABB boundingBox = new AABB(from.subtract(0.5, 0.5, 0.5), from.add(0.5, 0.5, 0.5));
            boundingBox = boundingBox.expandTowards(look.scale((double)distance));
            if (editorState.replayVisuals.renderEntities && editorState.replayVisuals.renderPlayers) {
                predicate = entity -> true;
            } else if (editorState.replayVisuals.renderPlayers) {
                predicate = entity -> entity instanceof Player;
            } else if (editorState.replayVisuals.renderEntities) {
                predicate = entity -> !(entity instanceof Player);
            } else {
                throw new IllegalStateException();
            }
            entityResult = ProjectileUtil.getEntityHitResult((Entity)cameraEntity, (Vec3)from, (Vec3)from.add(look.scale((double)distance)), (AABB)boundingBox, predicate, (double)(distance * distance));
        }
        if (entityResult != null && entityResult.getLocation().distanceTo(from) < (double)distance) {
            return entityResult;
        }
        if (blockResult != null && blockResult.getType() != HitResult.Type.MISS) {
            return blockResult;
        }
        return null;
    }

    public static boolean consumeConfirm() {
        boolean confirmPressed = ReplayUI.confirmPressed;
        ReplayUI.confirmPressed = false;
        return confirmPressed;
    }

    public static boolean consumeCancel() {
        boolean cancelPressed = ReplayUI.cancelPressed;
        ReplayUI.cancelPressed = false;
        return cancelPressed;
    }

    public static boolean isMoveQuickDown() {
        return imgui.moulberry90.ImGui.isKeyDown(Minecraft.getInstance().options.keySprint.key.getValue());
    }

    public static boolean isCtrlOrCmdDown() {
        return InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY ? ReplayUI.getIO().getKeySuper() : ReplayUI.getIO().getKeyCtrl();
    }
}

