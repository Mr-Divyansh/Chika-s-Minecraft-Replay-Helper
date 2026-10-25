/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.KeyboardHandler
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.Options
 *  net.minecraft.client.input.InputQuirks
 *  net.minecraft.client.input.KeyEvent
 *  org.lwjgl.PointerBuffer
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWCharCallback
 *  org.lwjgl.glfw.GLFWCharModsCallback
 *  org.lwjgl.glfw.GLFWCursorEnterCallback
 *  org.lwjgl.glfw.GLFWCursorPosCallback
 *  org.lwjgl.glfw.GLFWErrorCallback
 *  org.lwjgl.glfw.GLFWErrorCallbackI
 *  org.lwjgl.glfw.GLFWGamepadState
 *  org.lwjgl.glfw.GLFWKeyCallback
 *  org.lwjgl.glfw.GLFWMonitorCallback
 *  org.lwjgl.glfw.GLFWMouseButtonCallback
 *  org.lwjgl.glfw.GLFWNativeCocoa
 *  org.lwjgl.glfw.GLFWNativeWin32
 *  org.lwjgl.glfw.GLFWScrollCallback
 *  org.lwjgl.glfw.GLFWVidMode
 *  org.lwjgl.glfw.GLFWWindowFocusCallback
 */
package com.moulberry.flashback.editor.ui;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.keybinds.Keybind;
import com.moulberry.flashback.editor.keybinds.Keybinds;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.exporting.AsyncFileDialogs;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.ImGuiIO;
import imgui.moulberry90.ImGuiPlatformIO;
import imgui.moulberry90.ImGuiViewport;
import imgui.moulberry90.ImVec2;
import imgui.moulberry90.callback.ImPlatformFuncViewport;
import imgui.moulberry90.callback.ImPlatformFuncViewportFloat;
import imgui.moulberry90.callback.ImPlatformFuncViewportImVec2;
import imgui.moulberry90.callback.ImPlatformFuncViewportString;
import imgui.moulberry90.callback.ImPlatformFuncViewportSuppBoolean;
import imgui.moulberry90.callback.ImPlatformFuncViewportSuppImVec2;
import imgui.moulberry90.callback.ImStrConsumer;
import imgui.moulberry90.callback.ImStrSupplier;
import imgui.moulberry90.glfw.ImGuiImplGlfwNative;
import java.util.Arrays;
import java.util.Objects;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.input.InputQuirks;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCharModsCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWNativeCocoa;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowFocusCallback;

public class CustomImGuiImplGlfw {
    private static final String OS = System.getProperty("os.name", "generic").toLowerCase();
    public static final boolean IS_WINDOWS = OS.contains("win");
    protected static final boolean IS_APPLE = OS.contains("mac") || OS.contains("darwin");
    private long mainWindowPtr;
    private final int[] winWidth = new int[1];
    private final int[] winHeight = new int[1];
    private final int[] fbWidth = new int[1];
    private final int[] fbHeight = new int[1];
    private final float[] windowScaleX = new float[1];
    private final float[] windowScaleY = new float[1];
    private final long[] mouseCursors = new long[9];
    private final long[] keyOwnerWindows = new long[512];
    private final boolean[] keyPressedGame = new boolean[512];
    private final boolean[] mouseJustPressed = new boolean[5];
    private final ImVec2 mousePosBackup = new ImVec2();
    private final double[] mouseX = new double[1];
    private final double[] mouseY = new double[1];
    private final int[] windowX = new int[1];
    private final int[] windowY = new int[1];
    private final int[] monitorX = new int[1];
    private final int[] monitorY = new int[1];
    private final int[] monitorWorkAreaX = new int[1];
    private final int[] monitorWorkAreaY = new int[1];
    private final int[] monitorWorkAreaWidth = new int[1];
    private final int[] monitorWorkAreaHeight = new int[1];
    private final float[] monitorContentScaleX = new float[1];
    private final float[] monitorContentScaleY = new float[1];
    private GLFWWindowFocusCallback prevUserCallbackWindowFocus = null;
    private GLFWMouseButtonCallback prevUserCallbackMouseButton = null;
    private GLFWScrollCallback prevUserCallbackScroll = null;
    private GLFWCursorPosCallback prevUserCallbackCursorPos = null;
    private GLFWKeyCallback prevUserCallbackKey = null;
    private GLFWCharModsCallback prevUserCallbackCharMods = null;
    private GLFWCharCallback prevUserCallbackChar = null;
    private GLFWMonitorCallback prevUserCallbackMonitor = null;
    private GLFWCursorEnterCallback prevUserCallbackCursorEnter = null;
    private boolean callbacksInstalled = false;
    private boolean wantUpdateMonitors = true;
    private double time = 0.0;
    private long mouseWindowPtr;
    private MouseHandledBy grabbed = null;
    private int ignoreMouseMovements = 0;
    private boolean releasedAllKeysBecauseOfDialog = false;
    private boolean releasedAllKeysBecauseOfDisable = false;
    private final double[] grabbedOriginalMouseX = new double[1];
    private final double[] grabbedOriginalMouseY = new double[1];
    private int grabLinkedKey = -1;
    private boolean releaseGrabOnUp = false;
    public double rawMouseX;
    public double rawMouseY;
    public float contentScale = 1.0f;
    protected static final int glfwVersionCombined = 3400;
    protected static final boolean glfwHawWindowTopmost = true;
    protected static final boolean glfwHasWindowHovered = true;
    protected static final boolean glfwHasWindowAlpha = true;
    protected static final boolean glfwHasPerMonitorDpi = true;
    protected static final boolean glfwHasFocusWindow = true;
    protected static final boolean glfwHasFocusOnShow = true;
    protected static final boolean glfwHasMonitorWorkArea = true;
    protected static final boolean glfwHasOsxWindowPosFix = true;
    protected static final boolean glfwHasNewCursors = true;
    protected static final boolean glfwHasMousePassthrough = true;
    protected static final boolean glfwHasGamepadApi = true;
    protected static final boolean glfwHasGetKeyName = true;
    protected static final boolean glfwHasGetError = true;
    private static double grabbedLastMouseX = 0.0;
    private static double grabbedLastMouseY = 0.0;
    private static double grabbedCurrMouseX = 0.0;
    private static double grabbedCurrMouseY = 0.0;
    private boolean viewportWindowsHidden = false;

    public MouseHandledBy getMouseHandledBy() {
        if (!ReplayUI.isActive()) {
            return MouseHandledBy.GAME;
        }
        if (this.grabbed != null) {
            return this.grabbed;
        }
        if (ReplayUI.getIO().getWantCaptureMouse()) {
            return MouseHandledBy.IMGUI;
        }
        return MouseHandledBy.BOTH;
    }

    public boolean isGrabbed() {
        return this.grabbed != null;
    }

    public void ungrab() {
        if (this.grabbed == null) {
            return;
        }
        this.grabbed = null;
        this.grabLinkedKey = 0;
        GLFW.glfwSetInputMode((long)this.mainWindowPtr, (int)208897, (int)212993);
        GLFW.glfwSetCursorPos((long)this.mainWindowPtr, (double)this.grabbedOriginalMouseX[0], (double)this.grabbedOriginalMouseY[0]);
    }

    public void setGrabbed(boolean passthroughToGame, int grabLinkedKey, boolean releaseGrabOnUp, double x, double y) {
        if (grabLinkedKey != 0) {
            if (grabLinkedKey < 0) {
                if (GLFW.glfwGetMouseButton((long)this.mainWindowPtr, (int)(-grabLinkedKey - 1)) == 0) {
                    this.ungrab();
                    return;
                }
            } else if (GLFW.glfwGetKey((long)this.mainWindowPtr, (int)grabLinkedKey) == 0) {
                this.ungrab();
                return;
            }
        }
        if (this.grabbed != null) {
            return;
        }
        MouseHandledBy mouseHandledBy = this.grabbed = passthroughToGame ? MouseHandledBy.GAME : MouseHandledBy.EDITOR_GRABBED;
        if (grabLinkedKey != 0) {
            this.grabLinkedKey = grabLinkedKey;
            this.releaseGrabOnUp = releaseGrabOnUp;
        }
        if (x >= 0.0 && y >= 0.0) {
            this.grabbedOriginalMouseX[0] = x;
            this.grabbedOriginalMouseY[0] = y;
        } else {
            GLFW.glfwGetCursorPos((long)this.mainWindowPtr, (double[])this.grabbedOriginalMouseX, (double[])this.grabbedOriginalMouseY);
        }
        GLFW.glfwSetInputMode((long)this.mainWindowPtr, (int)208897, (int)212995);
        this.ignoreMouseMovements = 2;
        Minecraft.getInstance().mouseHandler.setIgnoreFirstMove();
    }

    public double getGrabbedMouseDeltaX() {
        double delta = grabbedCurrMouseX - grabbedLastMouseX;
        grabbedLastMouseX = grabbedCurrMouseX;
        return delta;
    }

    public double getGrabbedMouseDeltaY() {
        double delta = grabbedCurrMouseY - grabbedLastMouseY;
        grabbedLastMouseY = grabbedCurrMouseY;
        return delta;
    }

    public static int glfwKeyToImGuiKey(int glfwKey) {
        switch (glfwKey) {
            case 258: {
                return 512;
            }
            case 263: {
                return 513;
            }
            case 262: {
                return 514;
            }
            case 265: {
                return 515;
            }
            case 264: {
                return 516;
            }
            case 266: {
                return 517;
            }
            case 267: {
                return 518;
            }
            case 268: {
                return 519;
            }
            case 269: {
                return 520;
            }
            case 260: {
                return 521;
            }
            case 261: {
                return 522;
            }
            case 259: {
                return 523;
            }
            case 32: {
                return 524;
            }
            case 257: {
                return 525;
            }
            case 256: {
                return 526;
            }
            case 39: {
                return 596;
            }
            case 44: {
                return 597;
            }
            case 45: {
                return 598;
            }
            case 46: {
                return 599;
            }
            case 47: {
                return 600;
            }
            case 59: {
                return 601;
            }
            case 61: {
                return 602;
            }
            case 91: {
                return 603;
            }
            case 92: {
                return 604;
            }
            case 93: {
                return 605;
            }
            case 96: {
                return 606;
            }
            case 280: {
                return 607;
            }
            case 281: {
                return 608;
            }
            case 282: {
                return 609;
            }
            case 283: {
                return 610;
            }
            case 284: {
                return 611;
            }
            case 320: {
                return 612;
            }
            case 321: {
                return 613;
            }
            case 322: {
                return 614;
            }
            case 323: {
                return 615;
            }
            case 324: {
                return 616;
            }
            case 325: {
                return 617;
            }
            case 326: {
                return 618;
            }
            case 327: {
                return 619;
            }
            case 328: {
                return 620;
            }
            case 329: {
                return 621;
            }
            case 330: {
                return 622;
            }
            case 331: {
                return 623;
            }
            case 332: {
                return 624;
            }
            case 333: {
                return 625;
            }
            case 334: {
                return 626;
            }
            case 335: {
                return 627;
            }
            case 336: {
                return 628;
            }
            case 340: {
                return 528;
            }
            case 341: {
                return 527;
            }
            case 342: {
                return 529;
            }
            case 343: {
                return 530;
            }
            case 344: {
                return 532;
            }
            case 345: {
                return 531;
            }
            case 346: {
                return 533;
            }
            case 347: {
                return 534;
            }
            case 348: {
                return 535;
            }
            case 48: {
                return 536;
            }
            case 49: {
                return 537;
            }
            case 50: {
                return 538;
            }
            case 51: {
                return 539;
            }
            case 52: {
                return 540;
            }
            case 53: {
                return 541;
            }
            case 54: {
                return 542;
            }
            case 55: {
                return 543;
            }
            case 56: {
                return 544;
            }
            case 57: {
                return 545;
            }
            case 65: {
                return 546;
            }
            case 66: {
                return 547;
            }
            case 67: {
                return 548;
            }
            case 68: {
                return 549;
            }
            case 69: {
                return 550;
            }
            case 70: {
                return 551;
            }
            case 71: {
                return 552;
            }
            case 72: {
                return 553;
            }
            case 73: {
                return 554;
            }
            case 74: {
                return 555;
            }
            case 75: {
                return 556;
            }
            case 76: {
                return 557;
            }
            case 77: {
                return 558;
            }
            case 78: {
                return 559;
            }
            case 79: {
                return 560;
            }
            case 80: {
                return 561;
            }
            case 81: {
                return 562;
            }
            case 82: {
                return 563;
            }
            case 83: {
                return 564;
            }
            case 84: {
                return 565;
            }
            case 85: {
                return 566;
            }
            case 86: {
                return 567;
            }
            case 87: {
                return 568;
            }
            case 88: {
                return 569;
            }
            case 89: {
                return 570;
            }
            case 90: {
                return 571;
            }
            case 290: {
                return 572;
            }
            case 291: {
                return 573;
            }
            case 292: {
                return 574;
            }
            case 293: {
                return 575;
            }
            case 294: {
                return 576;
            }
            case 295: {
                return 577;
            }
            case 296: {
                return 578;
            }
            case 297: {
                return 579;
            }
            case 298: {
                return 580;
            }
            case 299: {
                return 581;
            }
            case 300: {
                return 582;
            }
            case 301: {
                return 583;
            }
            case 302: {
                return 584;
            }
            case 303: {
                return 585;
            }
            case 304: {
                return 586;
            }
            case 305: {
                return 587;
            }
            case 306: {
                return 588;
            }
            case 307: {
                return 589;
            }
            case 308: {
                return 590;
            }
            case 309: {
                return 591;
            }
            case 310: {
                return 592;
            }
            case 311: {
                return 593;
            }
            case 312: {
                return 594;
            }
            case 313: {
                return 595;
            }
        }
        return 0;
    }

    protected void updateKeyModifiers(long window) {
        ImGuiIO io = ReplayUI.getIO();
        io.addKeyEvent(4096, GLFW.glfwGetKey((long)window, (int)341) == 1 || GLFW.glfwGetKey((long)window, (int)345) == 1);
        io.addKeyEvent(8192, GLFW.glfwGetKey((long)window, (int)340) == 1 || GLFW.glfwGetKey((long)window, (int)344) == 1);
        io.addKeyEvent(16384, GLFW.glfwGetKey((long)window, (int)342) == 1 || GLFW.glfwGetKey((long)window, (int)346) == 1);
        io.addKeyEvent(32768, GLFW.glfwGetKey((long)window, (int)343) == 1 || GLFW.glfwGetKey((long)window, (int)347) == 1);
    }

    public void mouseButtonCallback(long windowId, int button, int action, int mods) {
        MouseHandledBy handledBy;
        if (AsyncFileDialogs.hasDialog()) {
            return;
        }
        if (!ReplayUI.isActive()) {
            if (this.prevUserCallbackMouseButton != null && windowId == this.mainWindowPtr) {
                this.prevUserCallbackMouseButton.invoke(windowId, button, action, mods);
            }
            return;
        }
        this.updateKeyModifiers(windowId);
        if (this.grabbed != null && this.grabLinkedKey < 0 && button == -this.grabLinkedKey - 1 && action == 0 == this.releaseGrabOnUp) {
            this.ungrab();
        }
        if ((handledBy = this.getMouseHandledBy()).allowGame() && this.prevUserCallbackMouseButton != null && windowId == this.mainWindowPtr) {
            this.prevUserCallbackMouseButton.invoke(windowId, button, action, mods);
        }
        if (handledBy.allowImgui() && action == 1 && button >= 0 && button < this.mouseJustPressed.length) {
            this.mouseJustPressed[button] = true;
        }
    }

    public void scrollCallback(long windowId, double xOffset, double yOffset) {
        if (AsyncFileDialogs.hasDialog()) {
            return;
        }
        if (ReplayUI.isActive()) {
            ImGuiIO io = ReplayUI.getIO();
            io.setMouseWheelH(io.getMouseWheelH() + (float)xOffset);
            io.setMouseWheel(io.getMouseWheel() + (float)yOffset);
            if (Minecraft.getInstance().screen == null || !ReplayUI.isMainFrameActive()) {
                return;
            }
        }
        if (this.prevUserCallbackScroll != null && windowId == this.mainWindowPtr) {
            this.prevUserCallbackScroll.invoke(windowId, xOffset, yOffset);
        }
    }

    public void keyCallback(long windowId, int key, int scancode, int action, int mods) {
        boolean keyInBoundsForGame;
        if (AsyncFileDialogs.hasDialog()) {
            return;
        }
        if (key < 32 || key > 348) {
            if (this.prevUserCallbackKey != null && windowId == this.mainWindowPtr) {
                this.prevUserCallbackKey.invoke(windowId, key, scancode, action, mods);
            }
            return;
        }
        ImGuiIO io = ReplayUI.getIO();
        this.updateKeyModifiers(windowId);
        boolean shiftMod = (mods & 1) != 0 || GLFW.glfwGetKey((long)windowId, (int)340) == 1 || GLFW.glfwGetKey((long)windowId, (int)344) == 1;
        boolean ctrlMod = (mods & 2) != 0 || GLFW.glfwGetKey((long)windowId, (int)341) == 1 || GLFW.glfwGetKey((long)windowId, (int)345) == 1;
        boolean altMod = (mods & 4) != 0 || GLFW.glfwGetKey((long)windowId, (int)342) == 1 || GLFW.glfwGetKey((long)windowId, (int)346) == 1;
        boolean superMod = (mods & 8) != 0 || GLFW.glfwGetKey((long)windowId, (int)343) == 1 || GLFW.glfwGetKey((long)windowId, (int)347) == 1;
        int minecraftKey = key;
        boolean bl = keyInBoundsForGame = minecraftKey >= 0 && minecraftKey < this.keyPressedGame.length;
        if (this.grabbed != null && action == 0 && this.grabLinkedKey > 0 && key == this.grabLinkedKey) {
            this.ungrab();
        }
        if (!ReplayUI.isActive() || Minecraft.getInstance().screen != null) {
            if (action == 0 && key >= 0 && key < this.keyOwnerWindows.length && this.keyOwnerWindows[key] != -1L) {
                int imguiKey = CustomImGuiImplGlfw.glfwKeyToImGuiKey(key);
                io.addKeyEvent(imguiKey, false);
                this.keyOwnerWindows[key] = -1L;
            }
            if (this.prevUserCallbackKey != null && windowId == this.mainWindowPtr) {
                if (keyInBoundsForGame) {
                    boolean bl2 = this.keyPressedGame[minecraftKey] = action != 0;
                }
                if (Flashback.isExporting()) {
                    if (action == 0 && keyInBoundsForGame && this.keyPressedGame[minecraftKey]) {
                        this.keyPressedGame[minecraftKey] = false;
                    } else {
                        return;
                    }
                }
                this.prevUserCallbackKey.invoke(windowId, minecraftKey, scancode, action, mods);
            }
            return;
        }
        Keybind editingKeybind = ImGuiHelper.getEditingKeybind();
        if (action == 1 && key != 256 && editingKeybind != null) {
            if (editingKeybind.isForceScrollKey()) {
                editingKeybind.set(17, shiftMod |= key == 340 || key == 344, ctrlMod |= key == 341 || key == 345, altMod |= key == 342 || key == 346, superMod |= key == 343 || key == 347);
                return;
            }
            shiftMod &= key != 340 && key != 344;
            ctrlMod &= key != 341 && key != 345;
            altMod &= key != 342 && key != 346;
            superMod &= key != 343 && key != 347;
            if (InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY) {
                boolean temp = ctrlMod;
                ctrlMod = superMod;
                superMod = temp;
            }
            editingKeybind.set(key, shiftMod, ctrlMod, altMod, superMod);
            return;
        }
        if (action == 1 && ImGuiHelper.getWantsSpecialInput()) {
            if (key == 259 && ImGuiHelper.backspaceInput(mods)) {
                return;
            }
            if (key == 32) {
                return;
            }
        }
        boolean passToMinecraft = false;
        boolean passToImGui = false;
        if (action == 0) {
            if (keyInBoundsForGame && this.keyPressedGame[minecraftKey]) {
                passToMinecraft = true;
            }
            if (key >= 0 && key < this.keyOwnerWindows.length && this.keyOwnerWindows[key] != -1L) {
                passToImGui = true;
            }
        } else {
            passToMinecraft = this.shouldPassToMinecraft(io, windowId, key, scancode, mods);
            boolean bl3 = passToImGui = !passToMinecraft;
        }
        if (passToMinecraft && this.prevUserCallbackKey != null && windowId == this.mainWindowPtr) {
            this.prevUserCallbackKey.invoke(windowId, minecraftKey, scancode, action, mods);
            if (keyInBoundsForGame) {
                boolean bl4 = this.keyPressedGame[minecraftKey] = action != 0;
            }
        }
        if (passToImGui && key >= 0 && key < this.keyOwnerWindows.length) {
            int imguiKey = CustomImGuiImplGlfw.glfwKeyToImGuiKey(key);
            if (action == 1 || action == 2) {
                io.addKeyEvent(imguiKey, true);
                io.setKeyEventNativeData(imguiKey, key, scancode);
                this.keyOwnerWindows[key] = windowId;
            } else if (action == 0) {
                io.addKeyEvent(imguiKey, false);
                io.setKeyEventNativeData(imguiKey, key, scancode);
                this.keyOwnerWindows[key] = -1L;
            }
        }
    }

    private boolean shouldPassToMinecraft(ImGuiIO io, long windowId, int key, int scancode, int mods) {
        if (key == 256) {
            return !io.getWantTextInput() && !ReplayUI.hasAnyPopupOpen;
        }
        boolean shiftMod = (mods & 1) != 0;
        boolean ctrlMod = (mods & 2) != 0;
        boolean altMod = (mods & 4) != 0;
        boolean superMod = (mods & 8) != 0;
        for (Keybind keybind : Keybinds.KEYBINDS) {
            if (!keybind.wouldBePressed(key, shiftMod, ctrlMod, altMod, superMod)) continue;
            return false;
        }
        if (key >= 290 && key <= 314) {
            return true;
        }
        if (GLFW.glfwGetKey((long)windowId, (int)292) != 0) {
            return true;
        }
        if (io.getWantTextInput()) {
            return false;
        }
        if (this.grabbed == MouseHandledBy.GAME) {
            return true;
        }
        Options options = Minecraft.getInstance().options;
        KeyEvent keyEvent = new KeyEvent(key, scancode, mods);
        if (ReplayUI.isMainFrameActive()) {
            for (KeyMapping keyMapping : options.keyMappings) {
                if (!keyMapping.matches(keyEvent)) continue;
                return true;
            }
        }
        if (options.keyUp.matches(keyEvent) || options.keyLeft.matches(keyEvent) || options.keyDown.matches(keyEvent) || options.keyRight.matches(keyEvent) || options.keyJump.matches(keyEvent) || options.keyChat.matches(keyEvent) || options.keyCommand.matches(keyEvent)) {
            ReplayUI.focusMainWindowCounter = 5;
            return true;
        }
        return false;
    }

    public void cursorPosCallback(long windowId, double xpos, double ypos) {
        if (AsyncFileDialogs.hasDialog()) {
            return;
        }
        this.rawMouseX = xpos;
        this.rawMouseY = ypos;
        if (!ReplayUI.isActive()) {
            if (this.prevUserCallbackCursorPos != null && windowId == this.mainWindowPtr) {
                this.prevUserCallbackCursorPos.invoke(windowId, xpos, ypos);
            }
            return;
        }
        MouseHandledBy handledBy = this.getMouseHandledBy();
        if (this.ignoreMouseMovements > 0) {
            grabbedCurrMouseX = xpos;
            grabbedCurrMouseY = ypos;
            grabbedLastMouseX = xpos;
            grabbedLastMouseY = ypos;
            --this.ignoreMouseMovements;
            return;
        }
        if (handledBy.allowGame() && this.prevUserCallbackCursorPos != null && windowId == this.mainWindowPtr) {
            this.prevUserCallbackCursorPos.invoke(windowId, ReplayUI.getNewMouseX(xpos), ReplayUI.getNewMouseY(ypos));
        }
        if (handledBy == MouseHandledBy.EDITOR_GRABBED) {
            grabbedCurrMouseX = xpos;
            grabbedCurrMouseY = ypos;
        }
    }

    public void windowFocusCallback(long windowId, boolean focused) {
        if (this.prevUserCallbackWindowFocus != null && windowId == this.mainWindowPtr) {
            this.prevUserCallbackWindowFocus.invoke(windowId, focused);
        }
        ReplayUI.getIO().addFocusEvent(focused);
    }

    public void cursorEnterCallback(long windowId, boolean entered) {
        if (this.prevUserCallbackCursorEnter != null && windowId == this.mainWindowPtr) {
            this.prevUserCallbackCursorEnter.invoke(windowId, entered);
        }
        if (entered) {
            this.mouseWindowPtr = windowId;
        }
        if (!entered && this.mouseWindowPtr == windowId) {
            this.mouseWindowPtr = 0L;
        }
    }

    public void charModsCallback(long windowId, int c, int mods) {
        boolean pass;
        if (AsyncFileDialogs.hasDialog()) {
            return;
        }
        ImGuiIO io = ReplayUI.getIO();
        boolean bl = pass = !ReplayUI.isActive() || !io.getWantCaptureKeyboard() && !io.getWantTextInput();
        if (pass && this.prevUserCallbackCharMods != null && windowId == this.mainWindowPtr) {
            this.prevUserCallbackCharMods.invoke(windowId, c, mods);
        }
    }

    public void charCallback(long windowId, int c) {
        if (AsyncFileDialogs.hasDialog()) {
            return;
        }
        if (!ReplayUI.isActive()) {
            if (this.prevUserCallbackChar != null && windowId == this.mainWindowPtr) {
                this.prevUserCallbackChar.invoke(windowId, c);
            }
            return;
        }
        ImGuiIO io = ReplayUI.getIO();
        if (!io.getWantCaptureKeyboard() && !io.getWantTextInput() && this.prevUserCallbackChar != null && windowId == this.mainWindowPtr) {
            this.prevUserCallbackChar.invoke(windowId, c);
        }
        if (!ImGuiHelper.addInputCharacter((char)c)) {
            io.addInputCharacter(c);
        }
    }

    public void monitorCallback(long windowId, int event) {
        if (this.prevUserCallbackMonitor != null && windowId == this.mainWindowPtr) {
            this.prevUserCallbackMonitor.invoke(windowId, event);
        }
        this.wantUpdateMonitors = true;
    }

    public boolean init(long windowId, boolean installCallbacks) {
        this.mainWindowPtr = windowId;
        ImGuiIO io = ReplayUI.getIO();
        io.addBackendFlags(1030);
        io.setBackendPlatformName("imgui_java_impl_glfw");
        final KeyboardHandler keyboardHandler = Minecraft.getInstance().keyboardHandler;
        io.setGetClipboardTextFn(new ImStrSupplier(this){
            {
                Objects.requireNonNull(this$0);
            }

            @Override
            public String get() {
                return keyboardHandler.getClipboard();
            }
        });
        io.setSetClipboardTextFn(new ImStrConsumer(this){
            {
                Objects.requireNonNull(this$0);
            }

            @Override
            public void accept(String s) {
                keyboardHandler.setClipboard(s);
            }
        });
        GLFWErrorCallback prevErrorCallback = GLFW.glfwSetErrorCallback(null);
        this.mouseCursors[0] = GLFW.glfwCreateStandardCursor((int)221185);
        this.mouseCursors[1] = GLFW.glfwCreateStandardCursor((int)221186);
        this.mouseCursors[3] = GLFW.glfwCreateStandardCursor((int)221190);
        this.mouseCursors[4] = GLFW.glfwCreateStandardCursor((int)221189);
        this.mouseCursors[7] = GLFW.glfwCreateStandardCursor((int)221188);
        this.mouseCursors[2] = GLFW.glfwCreateStandardCursor((int)221193);
        this.mouseCursors[5] = GLFW.glfwCreateStandardCursor((int)221192);
        this.mouseCursors[6] = GLFW.glfwCreateStandardCursor((int)221191);
        this.mouseCursors[8] = GLFW.glfwCreateStandardCursor((int)221194);
        GLFW.glfwSetErrorCallback((GLFWErrorCallbackI)prevErrorCallback);
        if (installCallbacks) {
            this.callbacksInstalled = true;
            this.prevUserCallbackWindowFocus = GLFW.glfwSetWindowFocusCallback((long)windowId, this::windowFocusCallback);
            this.prevUserCallbackCursorEnter = GLFW.glfwSetCursorEnterCallback((long)windowId, this::cursorEnterCallback);
            this.prevUserCallbackMouseButton = GLFW.glfwSetMouseButtonCallback((long)windowId, this::mouseButtonCallback);
            this.prevUserCallbackScroll = GLFW.glfwSetScrollCallback((long)windowId, this::scrollCallback);
            this.prevUserCallbackCursorPos = GLFW.glfwSetCursorPosCallback((long)windowId, this::cursorPosCallback);
            this.prevUserCallbackKey = GLFW.glfwSetKeyCallback((long)windowId, this::keyCallback);
            this.prevUserCallbackChar = GLFW.glfwSetCharCallback((long)windowId, this::charCallback);
            this.prevUserCallbackCharMods = GLFW.glfwSetCharModsCallback((long)windowId, this::charModsCallback);
            this.prevUserCallbackMonitor = GLFW.glfwSetMonitorCallback(this::monitorCallback);
        }
        GLFW.glfwGetWindowSize((long)this.mainWindowPtr, (int[])this.winWidth, (int[])this.winHeight);
        GLFW.glfwGetFramebufferSize((long)this.mainWindowPtr, (int[])this.fbWidth, (int[])this.fbHeight);
        io.setDisplaySize(this.winWidth[0], this.winHeight[0]);
        if (this.winWidth[0] > 0 && this.winHeight[0] > 0) {
            float scaleX = (float)this.fbWidth[0] / (float)this.winWidth[0];
            float scaleY = (float)this.fbHeight[0] / (float)this.winHeight[0];
            io.setDisplayFramebufferScale(scaleX, scaleY);
            GLFW.glfwGetWindowContentScale((long)Minecraft.getInstance().getWindow().handle(), (float[])this.windowScaleX, (float[])this.windowScaleY);
            this.contentScale = Math.max(this.windowScaleX[0] / scaleX, this.windowScaleY[0] / scaleY);
        }
        this.updateMonitors();
        ImGuiViewport mainViewport = ImGui.getMainViewport();
        mainViewport.setPlatformHandle(this.mainWindowPtr);
        if (IS_WINDOWS) {
            mainViewport.setPlatformHandleRaw(GLFWNativeWin32.glfwGetWin32Window((long)windowId));
        }
        if (IS_APPLE) {
            mainViewport.setPlatformHandleRaw(GLFWNativeCocoa.glfwGetCocoaWindow((long)windowId));
        }
        if (io.hasConfigFlags(1024)) {
            this.initPlatformInterface();
        }
        return true;
    }

    public void newFrame() {
        ImGuiIO io = ReplayUI.getIO();
        GLFW.glfwGetWindowSize((long)this.mainWindowPtr, (int[])this.winWidth, (int[])this.winHeight);
        GLFW.glfwGetFramebufferSize((long)this.mainWindowPtr, (int[])this.fbWidth, (int[])this.fbHeight);
        io.setDisplaySize(this.winWidth[0], this.winHeight[0]);
        if (this.winWidth[0] > 0 && this.winHeight[0] > 0) {
            float scaleX = (float)this.fbWidth[0] / (float)this.winWidth[0];
            float scaleY = (float)this.fbHeight[0] / (float)this.winHeight[0];
            io.setDisplayFramebufferScale(scaleX, scaleY);
            GLFW.glfwGetWindowContentScale((long)Minecraft.getInstance().getWindow().handle(), (float[])this.windowScaleX, (float[])this.windowScaleY);
            this.contentScale = Math.max(this.windowScaleX[0] / scaleX, this.windowScaleY[0] / scaleY);
        }
        if (this.wantUpdateMonitors) {
            this.updateMonitors();
        }
        double currentTime = GLFW.glfwGetTime();
        io.setDeltaTime(this.time > 0.0 ? (float)(currentTime - this.time) : 0.016666668f);
        this.time = currentTime;
        if (AsyncFileDialogs.hasDialog()) {
            if (!this.releasedAllKeysBecauseOfDialog) {
                this.releasedAllKeysBecauseOfDialog = true;
                for (int key = 0; key < this.keyPressedGame.length; ++key) {
                    if (!this.keyPressedGame[key]) continue;
                    this.keyPressedGame[key] = false;
                    int scancode = GLFW.glfwGetKeyScancode((int)key);
                    this.prevUserCallbackKey.invoke(this.mainWindowPtr, key, scancode, 0, 0);
                }
                Arrays.fill(this.keyOwnerWindows, -1L);
                io.clearInputKeys();
                io.setKeyCtrl(false);
                io.setKeyShift(false);
                io.setKeyAlt(false);
                io.setKeySuper(false);
            }
            return;
        }
        this.releasedAllKeysBecauseOfDialog = false;
        boolean shiftDown = false;
        boolean ctrlDown = false;
        boolean altDown = false;
        boolean superDown = false;
        ImGuiPlatformIO platformIO = ImGui.getPlatformIO();
        for (int n = 0; n < platformIO.getViewportsSize(); ++n) {
            ImGuiViewport viewport = platformIO.getViewports(n);
            long windowPtr = viewport.getPlatformHandle();
            if (GLFW.glfwGetWindowAttrib((long)windowPtr, (int)131073) == 0) continue;
            shiftDown |= GLFW.glfwGetKey((long)windowPtr, (int)340) != 0 || GLFW.glfwGetKey((long)windowPtr, (int)344) != 0;
            ctrlDown |= GLFW.glfwGetKey((long)windowPtr, (int)341) != 0 || GLFW.glfwGetKey((long)windowPtr, (int)345) != 0;
            altDown |= GLFW.glfwGetKey((long)windowPtr, (int)342) != 0 || GLFW.glfwGetKey((long)windowPtr, (int)346) != 0;
            superDown |= GLFW.glfwGetKey((long)windowPtr, (int)343) != 0 || GLFW.glfwGetKey((long)windowPtr, (int)347) != 0;
        }
        io.setKeyShift(shiftDown);
        io.setKeyCtrl(ctrlDown);
        io.setKeyAlt(altDown);
        io.setKeySuper(superDown);
        this.updateMousePosAndButtons();
        this.updateMouseCursor();
        this.updateGamepads();
    }

    public void updateReleaseAllKeys(boolean release) {
        if (release) {
            if (!this.releasedAllKeysBecauseOfDisable) {
                this.releasedAllKeysBecauseOfDisable = true;
                ImGuiIO io = ReplayUI.getIO();
                Arrays.fill(this.keyOwnerWindows, -1L);
                io.clearInputKeys();
                io.setKeyCtrl(false);
                io.setKeyShift(false);
                io.setKeyAlt(false);
                io.setKeySuper(false);
                ImGuiPlatformIO platformIO = ImGui.getPlatformIO();
                for (int n = 0; n < platformIO.getViewportsSize(); ++n) {
                    long windowPtr = platformIO.getViewports(n).getPlatformHandle();
                    GLFW.glfwSetCursor((long)windowPtr, (long)this.mouseCursors[0]);
                }
            }
        } else {
            this.releasedAllKeysBecauseOfDisable = false;
        }
    }

    private void updateMousePosAndButtons() {
        ImGuiIO io = ReplayUI.getIO();
        MouseHandledBy mouseHandledBy = this.getMouseHandledBy();
        if (!mouseHandledBy.allowImgui() || AsyncFileDialogs.hasDialog()) {
            for (int i = 0; i < 5; ++i) {
                io.setMouseDown(i, false);
                this.mouseJustPressed[i] = false;
            }
            return;
        }
        for (int i = 0; i < 5; ++i) {
            io.setMouseDown(i, this.mouseJustPressed[i] || GLFW.glfwGetMouseButton((long)this.mainWindowPtr, (int)i) != 0);
            this.mouseJustPressed[i] = false;
        }
        io.getMousePos(this.mousePosBackup);
        io.setMousePos(-3.4028235E38f, -3.4028235E38f);
        io.setMouseHoveredViewport(0);
        ImGuiPlatformIO platformIO = ImGui.getPlatformIO();
        for (int n = 0; n < platformIO.getViewportsSize(); ++n) {
            boolean focused;
            ImGuiViewport viewport = platformIO.getViewports(n);
            long windowPtr = viewport.getPlatformHandle();
            boolean bl = focused = GLFW.glfwGetWindowAttrib((long)windowPtr, (int)131073) != 0;
            if (focused) {
                for (int i = 0; i < 5; ++i) {
                    if (io.getMouseDown(i)) continue;
                    io.setMouseDown(i, GLFW.glfwGetMouseButton((long)windowPtr, (int)i) != 0);
                }
            }
            if (io.getWantSetMousePos() && focused) {
                GLFW.glfwSetCursorPos((long)windowPtr, (double)(this.mousePosBackup.x - viewport.getPosX()), (double)(this.mousePosBackup.y - viewport.getPosY()));
            }
            if (this.mouseWindowPtr != windowPtr && !focused) continue;
            GLFW.glfwGetCursorPos((long)windowPtr, (double[])this.mouseX, (double[])this.mouseY);
            if (io.hasConfigFlags(1024)) {
                GLFW.glfwGetWindowPos((long)windowPtr, (int[])this.windowX, (int[])this.windowY);
                io.setMousePos((float)this.mouseX[0] + (float)this.windowX[0], (float)this.mouseY[0] + (float)this.windowY[0]);
                continue;
            }
            io.setMousePos((float)this.mouseX[0], (float)this.mouseY[0]);
        }
    }

    private void updateMouseCursor() {
        boolean cursorDisabled;
        if (AsyncFileDialogs.hasDialog()) {
            return;
        }
        ImGuiIO io = ReplayUI.getIO();
        boolean noCursorChange = io.hasConfigFlags(32);
        boolean bl = cursorDisabled = GLFW.glfwGetInputMode((long)this.mainWindowPtr, (int)208897) == 212995;
        if (noCursorChange || cursorDisabled) {
            return;
        }
        int imguiCursor = ImGui.getMouseCursor();
        ImGuiPlatformIO platformIO = ImGui.getPlatformIO();
        for (int n = 0; n < platformIO.getViewportsSize(); ++n) {
            long windowPtr = platformIO.getViewports(n).getPlatformHandle();
            if (imguiCursor == -1 || io.getMouseDrawCursor()) {
                GLFW.glfwSetInputMode((long)windowPtr, (int)208897, (int)212994);
                continue;
            }
            GLFW.glfwSetCursor((long)windowPtr, (long)(this.mouseCursors[imguiCursor] != 0L ? this.mouseCursors[imguiCursor] : this.mouseCursors[0]));
            GLFW.glfwSetInputMode((long)windowPtr, (int)208897, (int)212993);
        }
    }

    private float saturate(float v) {
        return v < 0.0f ? 0.0f : (v > 1.0f ? 1.0f : v);
    }

    private void updateGamepads() {
        MapAnalog mapAnalog;
        MapButton mapButton;
        if (AsyncFileDialogs.hasDialog()) {
            return;
        }
        ImGuiIO io = ReplayUI.getIO();
        if (!io.hasConfigFlags(2)) {
            return;
        }
        io.removeBackendFlags(1);
        try (GLFWGamepadState gamepad = GLFWGamepadState.create();){
            if (!GLFW.glfwGetGamepadState((int)0, (GLFWGamepadState)gamepad)) {
                return;
            }
            mapButton = (keyNo, buttonNo, _unused) -> io.addKeyEvent(keyNo, gamepad.buttons(buttonNo) != 0);
            mapAnalog = (keyNo, axisNo, _unused, v0, v1) -> {
                float v = gamepad.axes(axisNo);
                io.addKeyAnalogEvent(keyNo, (v = (v - v0) / (v1 - v0)) > 0.1f, this.saturate(v));
            };
        }
        io.addBackendFlags(1);
        mapButton.run(631, 7, 7);
        mapButton.run(632, 6, 6);
        mapButton.run(633, 2, 2);
        mapButton.run(634, 1, 1);
        mapButton.run(635, 3, 3);
        mapButton.run(636, 0, 0);
        mapButton.run(637, 14, 13);
        mapButton.run(638, 12, 11);
        mapButton.run(639, 11, 10);
        mapButton.run(640, 13, 12);
        mapButton.run(641, 4, 4);
        mapButton.run(642, 5, 5);
        mapAnalog.run(643, 4, 4, -0.75f, 1.0f);
        mapAnalog.run(644, 5, 5, -0.75f, 1.0f);
        mapButton.run(645, 9, 8);
        mapButton.run(646, 10, 9);
        mapAnalog.run(647, 0, 0, -0.25f, -1.0f);
        mapAnalog.run(648, 0, 0, 0.25f, 1.0f);
        mapAnalog.run(649, 1, 1, -0.25f, -1.0f);
        mapAnalog.run(650, 1, 1, 0.25f, 1.0f);
        mapAnalog.run(651, 2, 2, -0.25f, -1.0f);
        mapAnalog.run(652, 2, 2, 0.25f, 1.0f);
        mapAnalog.run(653, 3, 3, -0.25f, -1.0f);
        mapAnalog.run(654, 3, 3, 0.25f, 1.0f);
    }

    protected void updateMonitors() {
        ImGuiPlatformIO platformIO = ImGui.getPlatformIO();
        this.wantUpdateMonitors = false;
        PointerBuffer monitors = GLFW.glfwGetMonitors();
        if (monitors == null) {
            System.err.println("Unable to get monitors!");
            return;
        }
        if (monitors.limit() == 0) {
            return;
        }
        platformIO.resizeMonitors(0);
        for (int n = 0; n < monitors.limit(); ++n) {
            long monitor = monitors.get(n);
            GLFWVidMode vidMode = GLFW.glfwGetVideoMode((long)monitor);
            if (vidMode == null) continue;
            GLFW.glfwGetMonitorPos((long)monitor, (int[])this.monitorX, (int[])this.monitorY);
            float mainPosX = this.monitorX[0];
            float mainPosY = this.monitorY[0];
            float mainSizeX = vidMode.width();
            float mainSizeY = vidMode.height();
            float workPosX = 0.0f;
            float workPosY = 0.0f;
            float workSizeX = 0.0f;
            float workSizeY = 0.0f;
            GLFW.glfwGetMonitorWorkarea((long)monitor, (int[])this.monitorWorkAreaX, (int[])this.monitorWorkAreaY, (int[])this.monitorWorkAreaWidth, (int[])this.monitorWorkAreaHeight);
            if (this.monitorWorkAreaWidth[0] > 0 && this.monitorWorkAreaHeight[0] > 0) {
                workPosX = this.monitorWorkAreaX[0];
                workPosY = this.monitorWorkAreaY[0];
                workSizeX = this.monitorWorkAreaWidth[0];
                workSizeY = this.monitorWorkAreaHeight[0];
            }
            float dpiScale = 0.0f;
            GLFW.glfwGetMonitorContentScale((long)monitor, (float[])this.monitorContentScaleX, (float[])this.monitorContentScaleY);
            dpiScale = this.monitorContentScaleX[0];
            platformIO.pushMonitors(monitor, mainPosX, mainPosY, mainSizeX, mainSizeY, workPosX, workPosY, workSizeX, workSizeY, dpiScale);
        }
    }

    public void setViewportWindowsHidden(boolean viewportWindowsHidden) {
        if (this.viewportWindowsHidden == viewportWindowsHidden) {
            return;
        }
        if (!this.viewportWindowsHidden) {
            this.viewportWindowsHidden = true;
            ImGuiPlatformIO platformIO = ImGui.getPlatformIO();
            for (int n = 0; n < platformIO.getViewportsSize(); ++n) {
                long windowPtr = platformIO.getViewports(n).getPlatformHandle();
                if (windowPtr == this.mainWindowPtr) continue;
                GLFW.glfwHideWindow((long)windowPtr);
            }
        } else {
            this.viewportWindowsHidden = false;
            ImGuiPlatformIO platformIO = ImGui.getPlatformIO();
            for (int n = 0; n < platformIO.getViewportsSize(); ++n) {
                long windowPtr = platformIO.getViewports(n).getPlatformHandle();
                if (windowPtr == this.mainWindowPtr) continue;
                GLFW.glfwShowWindow((long)windowPtr);
            }
        }
    }

    private void windowCloseCallback(long windowId) {
        ImGuiViewport vp = ImGui.findViewportByPlatformHandle(windowId);
        vp.setPlatformRequestClose(true);
    }

    private void windowPosCallback(long windowId, int xPos, int yPos) {
        boolean ignoreEvent;
        ImGuiViewport vp = ImGui.findViewportByPlatformHandle(windowId);
        ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
        boolean bl = ignoreEvent = ImGui.getFrameCount() <= data.ignoreWindowPosEventFrame + 1;
        if (ignoreEvent) {
            return;
        }
        vp.setPlatformRequestMove(true);
    }

    private void windowSizeCallback(long windowId, int width, int height) {
        boolean ignoreEvent;
        ImGuiViewport vp = ImGui.findViewportByPlatformHandle(windowId);
        ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
        boolean bl = ignoreEvent = ImGui.getFrameCount() <= data.ignoreWindowSizeEventFrame + 1;
        if (ignoreEvent) {
            return;
        }
        vp.setPlatformRequestResize(true);
    }

    private void initPlatformInterface() {
        ImGuiPlatformIO platformIO = ImGui.getPlatformIO();
        platformIO.setPlatformCreateWindow(new CreateWindowFunction(this));
        platformIO.setPlatformDestroyWindow(new DestroyWindowFunction(this));
        platformIO.setPlatformShowWindow(new ShowWindowFunction(this));
        platformIO.setPlatformGetWindowPos(new GetWindowPosFunction());
        platformIO.setPlatformSetWindowPos(new SetWindowPosFunction());
        platformIO.setPlatformGetWindowSize(new GetWindowSizeFunction());
        platformIO.setPlatformSetWindowSize(new SetWindowSizeFunction(this));
        platformIO.setPlatformSetWindowTitle(new SetWindowTitleFunction());
        platformIO.setPlatformSetWindowFocus(new SetWindowFocusFunction(this));
        platformIO.setPlatformGetWindowFocus(new GetWindowFocusFunction());
        platformIO.setPlatformGetWindowMinimized(new GetWindowMinimizedFunction());
        platformIO.setPlatformSetWindowAlpha(new SetWindowAlphaFunction(this));
        platformIO.setPlatformRenderWindow(new RenderWindowFunction());
        platformIO.setPlatformSwapBuffers(new SwapBuffersFunction());
        ImGuiViewport mainViewport = ImGui.getMainViewport();
        ImGuiViewportDataGlfw data = new ImGuiViewportDataGlfw();
        data.window = this.mainWindowPtr;
        data.windowOwned = false;
        mainViewport.setPlatformUserData(data);
    }

    public static enum MouseHandledBy {
        EDITOR_GRABBED,
        IMGUI,
        GAME,
        BOTH;


        public boolean allowImgui() {
            return this == IMGUI || this == BOTH;
        }

        public boolean allowGame() {
            return this == GAME || this == BOTH;
        }
    }

    @FunctionalInterface
    private static interface MapButton {
        public void run(int var1, int var2, int var3);
    }

    @FunctionalInterface
    private static interface MapAnalog {
        public void run(int var1, int var2, int var3, float var4, float var5);
    }

    private static final class ImGuiViewportDataGlfw {
        long window;
        boolean windowOwned = false;
        int ignoreWindowPosEventFrame = -1;
        int ignoreWindowSizeEventFrame = -1;

        private ImGuiViewportDataGlfw() {
        }
    }

    private final class CreateWindowFunction
    extends ImPlatformFuncViewport {
        final /* synthetic */ CustomImGuiImplGlfw this$0;

        private CreateWindowFunction(CustomImGuiImplGlfw customImGuiImplGlfw) {
            CustomImGuiImplGlfw customImGuiImplGlfw2 = customImGuiImplGlfw;
            Objects.requireNonNull(customImGuiImplGlfw2);
            this.this$0 = customImGuiImplGlfw2;
        }

        @Override
        public void accept(ImGuiViewport vp) {
            ImGuiViewportDataGlfw data = new ImGuiViewportDataGlfw();
            vp.setPlatformUserData(data);
            GLFW.glfwWindowHint((int)131076, (int)0);
            GLFW.glfwWindowHint((int)131073, (int)0);
            GLFW.glfwWindowHint((int)131084, (int)0);
            GLFW.glfwWindowHint((int)131077, (int)(vp.hasFlags(8) ? 0 : 1));
            GLFW.glfwWindowHint((int)131079, (int)(vp.hasFlags(1024) ? 1 : 0));
            data.window = GLFW.glfwCreateWindow((int)((int)vp.getSizeX()), (int)((int)vp.getSizeY()), (CharSequence)"No Title Yet", (long)0L, (long)this.this$0.mainWindowPtr);
            data.windowOwned = true;
            vp.setPlatformHandle(data.window);
            if (IS_WINDOWS) {
                vp.setPlatformHandleRaw(GLFWNativeWin32.glfwGetWin32Window((long)data.window));
            }
            GLFW.glfwSetWindowPos((long)data.window, (int)((int)vp.getPosX()), (int)((int)vp.getPosY()));
            GLFW.glfwSetMouseButtonCallback((long)data.window, this.this$0::mouseButtonCallback);
            GLFW.glfwSetScrollCallback((long)data.window, this.this$0::scrollCallback);
            GLFW.glfwSetKeyCallback((long)data.window, this.this$0::keyCallback);
            GLFW.glfwSetCharCallback((long)data.window, this.this$0::charCallback);
            GLFW.glfwSetCharModsCallback((long)data.window, this.this$0::charModsCallback);
            GLFW.glfwSetWindowCloseCallback((long)data.window, this.this$0::windowCloseCallback);
            GLFW.glfwSetWindowPosCallback((long)data.window, this.this$0::windowPosCallback);
            GLFW.glfwSetWindowSizeCallback((long)data.window, this.this$0::windowSizeCallback);
            GLFW.glfwMakeContextCurrent((long)data.window);
            GLFW.glfwSwapInterval((int)0);
        }
    }

    private final class DestroyWindowFunction
    extends ImPlatformFuncViewport {
        final /* synthetic */ CustomImGuiImplGlfw this$0;

        private DestroyWindowFunction(CustomImGuiImplGlfw customImGuiImplGlfw) {
            CustomImGuiImplGlfw customImGuiImplGlfw2 = customImGuiImplGlfw;
            Objects.requireNonNull(customImGuiImplGlfw2);
            this.this$0 = customImGuiImplGlfw2;
        }

        @Override
        public void accept(ImGuiViewport vp) {
            ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
            if (data != null && data.windowOwned) {
                for (int i = 0; i < this.this$0.keyOwnerWindows.length; ++i) {
                    if (this.this$0.keyOwnerWindows[i] != data.window) continue;
                    this.this$0.keyCallback(data.window, i, 0, 0, 0);
                }
                GLFW.glfwDestroyWindow((long)data.window);
            }
            vp.setPlatformUserData(null);
            vp.setPlatformHandle(0L);
        }
    }

    private final class ShowWindowFunction
    extends ImPlatformFuncViewport {
        final /* synthetic */ CustomImGuiImplGlfw this$0;

        private ShowWindowFunction(CustomImGuiImplGlfw customImGuiImplGlfw) {
            CustomImGuiImplGlfw customImGuiImplGlfw2 = customImGuiImplGlfw;
            Objects.requireNonNull(customImGuiImplGlfw2);
            this.this$0 = customImGuiImplGlfw2;
        }

        @Override
        public void accept(ImGuiViewport vp) {
            ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
            if (IS_WINDOWS && vp.hasFlags(16)) {
                ImGuiImplGlfwNative.win32hideFromTaskBar(vp.getPlatformHandleRaw());
            }
            if (!this.this$0.viewportWindowsHidden) {
                GLFW.glfwShowWindow((long)data.window);
            }
        }
    }

    private static final class GetWindowPosFunction
    extends ImPlatformFuncViewportSuppImVec2 {
        private final int[] posX = new int[1];
        private final int[] posY = new int[1];

        private GetWindowPosFunction() {
        }

        @Override
        public void get(ImGuiViewport vp, ImVec2 dstImVec2) {
            ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
            GLFW.glfwGetWindowPos((long)data.window, (int[])this.posX, (int[])this.posY);
            dstImVec2.x = this.posX[0];
            dstImVec2.y = this.posY[0];
        }
    }

    private static final class SetWindowPosFunction
    extends ImPlatformFuncViewportImVec2 {
        private SetWindowPosFunction() {
        }

        @Override
        public void accept(ImGuiViewport vp, ImVec2 imVec2) {
            ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
            data.ignoreWindowPosEventFrame = ImGui.getFrameCount();
            GLFW.glfwSetWindowPos((long)data.window, (int)((int)imVec2.x), (int)((int)imVec2.y));
        }
    }

    private static final class GetWindowSizeFunction
    extends ImPlatformFuncViewportSuppImVec2 {
        private final int[] width = new int[1];
        private final int[] height = new int[1];

        private GetWindowSizeFunction() {
        }

        @Override
        public void get(ImGuiViewport vp, ImVec2 dstImVec2) {
            ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
            GLFW.glfwGetWindowSize((long)data.window, (int[])this.width, (int[])this.height);
            dstImVec2.x = this.width[0];
            dstImVec2.y = this.height[0];
        }
    }

    private final class SetWindowSizeFunction
    extends ImPlatformFuncViewportImVec2 {
        private final int[] x;
        private final int[] y;
        private final int[] width;
        private final int[] height;
        final /* synthetic */ CustomImGuiImplGlfw this$0;

        private SetWindowSizeFunction(CustomImGuiImplGlfw customImGuiImplGlfw) {
            CustomImGuiImplGlfw customImGuiImplGlfw2 = customImGuiImplGlfw;
            Objects.requireNonNull(customImGuiImplGlfw2);
            this.this$0 = customImGuiImplGlfw2;
            this.x = new int[1];
            this.y = new int[1];
            this.width = new int[1];
            this.height = new int[1];
        }

        @Override
        public void accept(ImGuiViewport vp, ImVec2 imVec2) {
            ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
            if (IS_APPLE) {
            }
            data.ignoreWindowSizeEventFrame = ImGui.getFrameCount();
            GLFW.glfwSetWindowSize((long)data.window, (int)((int)imVec2.x), (int)((int)imVec2.y));
        }
    }

    private static final class SetWindowTitleFunction
    extends ImPlatformFuncViewportString {
        private SetWindowTitleFunction() {
        }

        @Override
        public void accept(ImGuiViewport vp, String str) {
            ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
            GLFW.glfwSetWindowTitle((long)data.window, (CharSequence)str);
        }
    }

    private final class SetWindowFocusFunction
    extends ImPlatformFuncViewport {
        final /* synthetic */ CustomImGuiImplGlfw this$0;

        private SetWindowFocusFunction(CustomImGuiImplGlfw customImGuiImplGlfw) {
            CustomImGuiImplGlfw customImGuiImplGlfw2 = customImGuiImplGlfw;
            Objects.requireNonNull(customImGuiImplGlfw2);
            this.this$0 = customImGuiImplGlfw2;
        }

        @Override
        public void accept(ImGuiViewport vp) {
            ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
            GLFW.glfwFocusWindow((long)data.window);
        }
    }

    private static final class GetWindowFocusFunction
    extends ImPlatformFuncViewportSuppBoolean {
        private GetWindowFocusFunction() {
        }

        @Override
        public boolean get(ImGuiViewport vp) {
            ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
            return GLFW.glfwGetWindowAttrib((long)data.window, (int)131073) != 0;
        }
    }

    private static final class GetWindowMinimizedFunction
    extends ImPlatformFuncViewportSuppBoolean {
        private GetWindowMinimizedFunction() {
        }

        @Override
        public boolean get(ImGuiViewport vp) {
            ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
            return GLFW.glfwGetWindowAttrib((long)data.window, (int)131074) != 0;
        }
    }

    private final class SetWindowAlphaFunction
    extends ImPlatformFuncViewportFloat {
        final /* synthetic */ CustomImGuiImplGlfw this$0;

        private SetWindowAlphaFunction(CustomImGuiImplGlfw customImGuiImplGlfw) {
            CustomImGuiImplGlfw customImGuiImplGlfw2 = customImGuiImplGlfw;
            Objects.requireNonNull(customImGuiImplGlfw2);
            this.this$0 = customImGuiImplGlfw2;
        }

        @Override
        public void accept(ImGuiViewport vp, float f) {
            ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
            GLFW.glfwSetWindowOpacity((long)data.window, (float)f);
        }
    }

    private static final class RenderWindowFunction
    extends ImPlatformFuncViewport {
        private RenderWindowFunction() {
        }

        @Override
        public void accept(ImGuiViewport vp) {
            ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
            GLFW.glfwMakeContextCurrent((long)data.window);
        }
    }

    private static final class SwapBuffersFunction
    extends ImPlatformFuncViewport {
        private SwapBuffersFunction() {
        }

        @Override
        public void accept(ImGuiViewport vp) {
            ImGuiViewportDataGlfw data = (ImGuiViewportDataGlfw)vp.getPlatformUserData();
            GLFW.glfwMakeContextCurrent((long)data.window);
            GLFW.glfwSwapBuffers((long)data.window);
        }
    }
}

