/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  com.moulberry.lattice.keybind.KeybindInterface
 *  com.moulberry.lattice.keybind.LatticeInputType
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.input.InputQuirks
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Util
 *  net.minecraft.util.Util$OS
 *  org.lwjgl.glfw.GLFW
 */
package com.moulberry.flashback.editor.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.keybinds.KeybindHelper;
import com.moulberry.flashback.editor.keybinds.Keybinds;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.lattice.keybind.KeybindInterface;
import com.moulberry.lattice.keybind.LatticeInputType;
import imgui.moulberry90.ImGuiIO;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.InputQuirks;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;

public class Keybind
implements KeybindInterface {
    public static final int FAKE_SCROLL_KEY = 17;
    private final String description;
    private int key;
    private boolean shiftMod;
    private boolean ctrlMod;
    private boolean altMod;
    private boolean superMod;
    private boolean forceScrollKey = false;
    private boolean ingameDownLastTime = false;

    public Keybind(String description, int key, boolean shiftMod, boolean ctrlMod, boolean altMod, boolean superMod) {
        this.description = description;
        this.key = key;
        this.shiftMod = shiftMod;
        this.ctrlMod = ctrlMod;
        this.altMod = altMod;
        this.superMod = superMod;
        Keybinds.updateMapping(this, 0);
    }

    public Keybind withForceScrollKey() {
        this.forceScrollKey = true;
        return this;
    }

    public boolean isForceScrollKey() {
        return this.forceScrollKey;
    }

    public int getKey() {
        return this.key;
    }

    public boolean isShiftMod() {
        return this.shiftMod;
    }

    public boolean isCtrlMod() {
        return this.ctrlMod;
    }

    public boolean isAltMod() {
        return this.altMod;
    }

    public boolean isSuperMod() {
        return this.superMod;
    }

    public void clear() {
        int oldKey = this.key;
        this.key = 0;
        this.shiftMod = false;
        this.ctrlMod = false;
        this.altMod = false;
        this.superMod = false;
        Keybinds.updateMapping(this, oldKey);
    }

    public void set(Keybind other) {
        if (other.description.equals(this.description)) {
            int oldKey = this.key;
            this.key = other.key;
            this.shiftMod = other.shiftMod;
            this.ctrlMod = other.ctrlMod;
            this.altMod = other.altMod;
            this.superMod = other.superMod;
            Keybinds.updateMapping(this, oldKey);
        }
    }

    public String toConfigValue() {
        if (this.key == 0) {
            return "none";
        }
        String key = KeybindHelper.glfwToConfig(this.key);
        if (key.equals("none")) {
            return "none";
        }
        StringBuilder builder = new StringBuilder();
        if (this.shiftMod) {
            builder.append("shift+");
        }
        if (this.ctrlMod) {
            builder.append("ctrl+");
        }
        if (this.altMod) {
            builder.append("alt+");
        }
        if (this.superMod) {
            builder.append("super+");
        }
        builder.append(key);
        return builder.toString();
    }

    public void loadFromConfigValue(String configValue) {
        String originalConfigValue = configValue;
        configValue = configValue.toLowerCase(Locale.ROOT);
        if ((configValue = configValue.replaceAll("[^a-z0-9+_]", "")).equals("none")) {
            int oldKey = this.key;
            this.key = 0;
            this.superMod = false;
            this.altMod = false;
            this.ctrlMod = false;
            this.shiftMod = false;
            Keybinds.updateMapping(this, oldKey);
            return;
        }
        boolean shiftMod = false;
        boolean ctrlMod = false;
        boolean altMod = false;
        boolean superMod = false;
        while (true) {
            if (configValue.startsWith("shift+")) {
                shiftMod = true;
                configValue = configValue.substring(6);
                continue;
            }
            if (configValue.startsWith("ctrl+")) {
                ctrlMod = true;
                configValue = configValue.substring(5);
                continue;
            }
            if (configValue.startsWith("alt+")) {
                altMod = true;
                configValue = configValue.substring(4);
                continue;
            }
            if (!configValue.startsWith("super+")) break;
            superMod = true;
            configValue = configValue.substring(6);
        }
        int key = KeybindHelper.configToGlfw(configValue = configValue.substring(configValue.lastIndexOf("+") + 1));
        if (key != 0) {
            int oldKey = this.key;
            this.key = key;
            this.shiftMod = shiftMod;
            this.ctrlMod = ctrlMod;
            this.altMod = altMod;
            this.superMod = superMod;
            Keybinds.updateMapping(this, oldKey);
        } else {
            Flashback.LOGGER.error("Invalid keybind in config for {}: {}", (Object)this.description, (Object)originalConfigValue);
        }
    }

    public Keybind copy() {
        return new Keybind(this.description, this.key, this.shiftMod, this.ctrlMod, this.altMod, this.superMod);
    }

    public void set(int key, boolean shiftMod, boolean ctrlMod, boolean altMod, boolean superMod) {
        int oldKey = this.key;
        this.key = key;
        this.shiftMod = shiftMod;
        this.ctrlMod = ctrlMod;
        this.altMod = altMod;
        this.superMod = superMod;
        Keybinds.updateMapping(this, oldKey);
    }

    public boolean hasSameMods(Keybind other) {
        return this.shiftMod == other.shiftMod && this.ctrlMod == other.ctrlMod && this.altMod == other.altMod && this.superMod == other.superMod;
    }

    private static String getKeyNameFor(int key) {
        if (key == 17) {
            return I18n.get((String)"flashback.keymod.scroll", (Object[])new Object[0]);
        }
        return InputConstants.getKey((KeyEvent)new KeyEvent(key, -1, 0)).getDisplayName().getString();
    }

    public String longKeyIdentifier() {
        if (this.key == 0) {
            return I18n.get((String)"key.keyboard.unknown", (Object[])new Object[0]);
        }
        String keyName = this.key < 0 ? InputConstants.Type.MOUSE.getOrCreate(-this.key - 1).getDisplayName().getString() : Keybind.getKeyNameFor(this.key);
        if (!(this.shiftMod || this.ctrlMod || this.altMod || this.superMod)) {
            return keyName;
        }
        StringBuilder builder = new StringBuilder();
        if (this.shiftMod) {
            builder.append(I18n.get((String)"flashback.keymod.shift", (Object[])new Object[0])).append("+");
        }
        if (this.ctrlMod) {
            if (InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY) {
                builder.append(I18n.get((String)"flashback.keymod.mac_cmd", (Object[])new Object[0])).append("+");
            } else {
                builder.append(I18n.get((String)"flashback.keymod.ctrl", (Object[])new Object[0])).append("+");
            }
        }
        if (this.altMod) {
            builder.append(I18n.get((String)"flashback.keymod.alt", (Object[])new Object[0])).append("+");
        }
        if (this.superMod) {
            if (InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY) {
                builder.append(I18n.get((String)"flashback.keymod.ctrl", (Object[])new Object[0])).append("+");
            } else if (Util.getPlatform() == Util.OS.WINDOWS) {
                builder.append(I18n.get((String)"flashback.keymod.win_super", (Object[])new Object[0])).append("+");
            } else {
                builder.append(I18n.get((String)"flashback.keymod.super", (Object[])new Object[0])).append("+");
            }
        }
        builder.append(keyName);
        return builder.toString();
    }

    public boolean wouldBePressed(int key, boolean shiftMod, boolean ctrlMod, boolean altMod, boolean superMod) {
        if (this.key == 0) {
            return false;
        }
        if (this.key != key) {
            return false;
        }
        if (this.shiftMod != shiftMod) {
            return false;
        }
        if (this.ctrlMod != (InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY ? superMod : ctrlMod)) {
            return false;
        }
        if (this.altMod != altMod) {
            return false;
        }
        return this.superMod == (InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY ? ctrlMod : superMod);
    }

    private static boolean isShift(int key) {
        return key == 340 || key == 344;
    }

    private static boolean isCtrl(int key) {
        return key == 341 || key == 345;
    }

    private static boolean isAlt(int key) {
        return key == 342 || key == 346;
    }

    private static boolean isSuper(int key) {
        return key == 343 || key == 347;
    }

    public static boolean isShiftDownGLFW(long window) {
        return GLFW.glfwGetKey((long)window, (int)340) == 1 || GLFW.glfwGetKey((long)window, (int)344) == 1;
    }

    public static boolean isCtrlDownGLFW(long window) {
        return GLFW.glfwGetKey((long)window, (int)341) == 1 || GLFW.glfwGetKey((long)window, (int)345) == 1;
    }

    public static boolean isAltDownGLFW(long window) {
        return GLFW.glfwGetKey((long)window, (int)342) == 1 || GLFW.glfwGetKey((long)window, (int)346) == 1;
    }

    public static boolean isSuperDownGLFW(long window) {
        return GLFW.glfwGetKey((long)window, (int)343) == 1 || GLFW.glfwGetKey((long)window, (int)347) == 1;
    }

    public boolean isDown() {
        return this.areAllModifiersDown() && this.isDownIgnoreMods();
    }

    public boolean isDownIgnoreMods() {
        if (this.key == 0 || this.key == 17) {
            return false;
        }
        if (ReplayUI.isActive() && ReplayUI.isImGuiContextActive()) {
            return ImGuiHelper.isGlfwBindingDown(this.key);
        }
        long window = Minecraft.getInstance().getWindow().handle();
        if (this.key < 0) {
            return GLFW.glfwGetMouseButton((long)window, (int)(-this.key - 1)) != 0;
        }
        return GLFW.glfwGetKey((long)window, (int)this.key) != 0;
    }

    public boolean isPressed(boolean repeat) {
        return this.areAllModifiersDown() && this.isPressedIgnoreMods(repeat);
    }

    public boolean isPressedIgnoreMods(boolean repeat) {
        if (this.key == 0 || this.key == 17) {
            return false;
        }
        if (ReplayUI.isActive() && ReplayUI.isImGuiContextActive()) {
            return ImGuiHelper.isGlfwBindingClicked(this.key, repeat);
        }
        boolean down = this.isDownIgnoreMods();
        boolean wasDown = this.ingameDownLastTime;
        this.ingameDownLastTime = down;
        return down && !wasDown;
    }

    public boolean areAllModifiersDown() {
        if (this.key == 0) {
            return false;
        }
        boolean ctrlMod = this.ctrlMod;
        boolean superMod = this.superMod;
        if (InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY) {
            ctrlMod = this.superMod;
            superMod = this.ctrlMod;
        }
        if (ReplayUI.isActive() && ReplayUI.isImGuiContextActive()) {
            ImGuiIO io = ReplayUI.getIO();
            if (!Keybind.isShift(this.key) && this.shiftMod != io.getKeyShift()) {
                return false;
            }
            if (!Keybind.isCtrl(this.key) && this.ctrlMod != io.getKeyCtrl()) {
                return false;
            }
            if (!Keybind.isAlt(this.key) && this.altMod != io.getKeyAlt()) {
                return false;
            }
            if (!Keybind.isSuper(this.key) && this.superMod != io.getKeySuper()) {
                return false;
            }
        } else {
            Minecraft minecraft = Minecraft.getInstance();
            long window = minecraft.getWindow().handle();
            if (!Keybind.isShift(this.key) && this.shiftMod != Keybind.isShiftDownGLFW(window)) {
                return false;
            }
            if (!Keybind.isCtrl(this.key) && ctrlMod != Keybind.isCtrlDownGLFW(window)) {
                return false;
            }
            if (!Keybind.isAlt(this.key) && this.altMod != Keybind.isAltDownGLFW(window)) {
                return false;
            }
            if (!Keybind.isSuper(this.key) && superMod != Keybind.isSuperDownGLFW(window)) {
                return false;
            }
        }
        return true;
    }

    public String getDescriptionRaw() {
        return this.description;
    }

    public String getDescription() {
        return I18n.get((String)("flashback.keybinds." + this.description), (Object[])new Object[0]);
    }

    public Component getKeyMessage() {
        return Component.literal((String)this.longKeyIdentifier());
    }

    public void setKey(LatticeInputType type, int value, boolean shiftMod, boolean ctrlMod, boolean altMod, boolean superMod) {
        int oldKey = this.key;
        switch (type) {
            case KEYSYM: {
                this.key = value;
                break;
            }
            case SCANCODE: {
                return;
            }
            case MOUSE: {
                this.key = -value - 1;
            }
        }
        this.shiftMod = shiftMod;
        this.ctrlMod = InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY ? superMod : ctrlMod;
        this.altMod = altMod;
        this.superMod = InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY ? ctrlMod : superMod;
        Keybinds.updateMapping(this, oldKey);
    }

    public void setUnbound() {
        int oldKey = this.key;
        this.key = 0;
        this.superMod = false;
        this.altMod = false;
        this.ctrlMod = false;
        this.shiftMod = false;
        Keybinds.updateMapping(this, oldKey);
    }

    public Collection<Component> getConflicts() {
        return List.of();
    }
}

