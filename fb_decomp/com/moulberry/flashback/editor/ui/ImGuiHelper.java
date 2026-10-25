/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.editor.ui;

import com.moulberry.flashback.combo_options.ComboOption;
import com.moulberry.flashback.editor.keybinds.Keybind;
import com.moulberry.flashback.editor.ui.CustomImGuiImplGlfw;
import com.moulberry.flashback.editor.ui.ReplayUI;
import imgui.moulberry90.ImGui;
import imgui.moulberry90.ImVec2;
import imgui.moulberry90.type.ImBoolean;
import imgui.moulberry90.type.ImFloat;
import imgui.moulberry90.type.ImInt;
import imgui.moulberry90.type.ImString;
import java.util.HashMap;
import java.util.Map;

public class ImGuiHelper {
    private static Keybind editingKeybindLastFrame = null;
    private static Keybind editingKeybindThisFrame = null;
    private static boolean closeableModalOnTopLast = false;
    private static boolean closeableModalOnTop = false;
    private static boolean wantSpecialInputLastFrame = false;
    private static boolean wantSpecialInputThisFrame = false;
    private static StringBuilder specialInput = new StringBuilder();
    private static int backspaceCount = 0;
    private static boolean handledFocusNext = false;
    private static boolean focusNext = false;
    private static int focusIndex = 0;
    private static int focusLastIndex = 0;
    private static final int[] enumComboSharedArray = new int[]{0};
    private static final Map<Class<?>, Object[]> enumComboCachedValues = new HashMap();
    private static final Map<Class<?>, String[]> enumComboCachedText = new HashMap();
    public static float borderIndentation = 0.0f;
    private static int pushedColors = 0;
    private static int pushedStyleVars = 0;

    public static void endFrame() {
        closeableModalOnTopLast = closeableModalOnTop;
        wantSpecialInputLastFrame = wantSpecialInputThisFrame;
        wantSpecialInputThisFrame = false;
        editingKeybindLastFrame = editingKeybindThisFrame;
        editingKeybindThisFrame = null;
        handledFocusNext = false;
        focusNext = false;
        focusIndex = 0;
        if (!wantSpecialInputLastFrame) {
            specialInput.setLength(0);
        }
    }

    public static String getString(ImString string) {
        char c;
        StringBuilder builder = new StringBuilder();
        String str = string.get();
        for (int i = 0; i < str.length() && (c = str.charAt(i)) != '\u0000'; ++i) {
            builder.append(c);
        }
        return builder.toString();
    }

    public static ImString createResizableImString(String text) {
        ImString imString = new ImString(text);
        imString.inputData.isResizable = true;
        return imString;
    }

    public static void clearEnumComboTextCache() {
        enumComboCachedText.clear();
    }

    public static <T extends Enum<?>> T enumCombo(String label, T c, T[] values) {
        int currentIndex = -1;
        for (int i = 0; i < values.length; ++i) {
            T value = values[i];
            if (value != c) continue;
            currentIndex = i;
            break;
        }
        String[] text = new String[values.length];
        for (int i = 0; i < text.length; ++i) {
            text[i] = ((ComboOption)values[i]).text();
        }
        ImGuiHelper.enumComboSharedArray[0] = currentIndex;
        ImGuiHelper.combo(label, enumComboSharedArray, text);
        if (enumComboSharedArray[0] == currentIndex) {
            return c;
        }
        return values[enumComboSharedArray[0]];
    }

    public static void setEditingKeybind(Keybind keybind) {
        editingKeybindThisFrame = keybind;
    }

    public static Keybind getEditingKeybind() {
        return editingKeybindLastFrame;
    }

    public static <T extends Enum<?>> T enumCombo(String label, T c) {
        Class<?> clazz = c.getClass();
        int currentIndex = c.ordinal();
        String[] text = enumComboCachedText.get(clazz);
        if (text == null) {
            Enum[] options = (Enum[])clazz.getEnumConstants();
            enumComboCachedValues.put(clazz, options);
            text = new String[options.length];
            for (int i = 0; i < text.length; ++i) {
                text[i] = ((ComboOption)((Object)options[i])).text();
            }
            enumComboCachedText.put(clazz, text);
        }
        ImGuiHelper.enumComboSharedArray[0] = currentIndex;
        ImGuiHelper.combo(label, enumComboSharedArray, text);
        if (enumComboSharedArray[0] == currentIndex) {
            return c;
        }
        return (T)((Enum)enumComboCachedValues.get(clazz)[enumComboSharedArray[0]]);
    }

    public static boolean combo(String label, int[] currentItem, String[] values) {
        return ImGuiHelper.combo(label, currentItem, values, 0);
    }

    public static boolean combo(String label, int[] currentItem, String[] values, int imguiComboFlags) {
        boolean changed = false;
        if (currentItem[0] >= values.length && values.length > 0) {
            currentItem[0] = values.length - 1;
        }
        if (ImGui.beginCombo(label, currentItem[0] < 0 ? "" : values[currentItem[0]], imguiComboFlags)) {
            for (int i = 0; i < values.length; ++i) {
                boolean selected;
                ImGui.pushID(i);
                boolean bl = selected = i == currentItem[0];
                if (ImGui.selectable(values[i], selected) && !selected) {
                    currentItem[0] = i;
                    changed = true;
                }
                if (selected) {
                    ImGui.setItemDefaultFocus();
                }
                ImGui.popID();
            }
            ImGui.endCombo();
        }
        return changed;
    }

    public static boolean isGlfwBindingDown(int key) {
        if (key == 0) {
            return false;
        }
        if (key < 0) {
            int mouse = -key - 1;
            if (mouse >= 5) {
                return false;
            }
            return ImGui.isMouseDown(mouse);
        }
        int namedKey = CustomImGuiImplGlfw.glfwKeyToImGuiKey(key);
        if (namedKey < 512 || namedKey >= 666) {
            return false;
        }
        return ImGui.isKeyDown(namedKey);
    }

    public static boolean isGlfwBindingClicked(int key, boolean repeat) {
        if (key == 0) {
            return false;
        }
        if (key < 0) {
            int mouse = -key - 1;
            if (mouse >= 5) {
                return false;
            }
            return ImGui.isMouseClicked(mouse, repeat);
        }
        int namedKey = CustomImGuiImplGlfw.glfwKeyToImGuiKey(key);
        if (namedKey < 512 || namedKey >= 666) {
            return false;
        }
        return ImGui.isKeyPressed(namedKey, repeat);
    }

    public static String modifyFromInput(String existing) {
        wantSpecialInputThisFrame = true;
        String newInput = specialInput.toString();
        existing = ((String)existing).substring(0, Math.max(0, ((String)existing).length() - backspaceCount));
        existing = (String)existing + newInput;
        specialInput.setLength(0);
        backspaceCount = 0;
        return existing;
    }

    public static boolean getWantsSpecialInput() {
        return wantSpecialInputLastFrame;
    }

    public static boolean addInputCharacter(char c) {
        if (wantSpecialInputLastFrame) {
            specialInput.append(c);
            return true;
        }
        return false;
    }

    public static boolean backspaceInput(int mods) {
        if (wantSpecialInputLastFrame) {
            if ((mods & 2) != 0) {
                specialInput.setLength(0);
                backspaceCount = 10000;
                return true;
            }
            if (specialInput.length() > 0) {
                specialInput.setLength(specialInput.length() - 1);
            } else {
                ++backspaceCount;
            }
            return true;
        }
        return false;
    }

    public static boolean beginPopup(String id) {
        return ImGuiHelper.beginPopup(id, 0);
    }

    public static boolean beginPopup(String id, int imGuiWindowFlags) {
        if (ImGui.beginPopup(id, imGuiWindowFlags | 4 | 0x100)) {
            closeableModalOnTop = false;
            return true;
        }
        return false;
    }

    public static boolean beginPopupModal(String id) {
        return ImGuiHelper.beginPopupModal(id, 0);
    }

    public static boolean beginPopupModal(String id, int imGuiWindowFlags) {
        if (ImGui.beginPopupModal(id, imGuiWindowFlags | 0x100)) {
            closeableModalOnTop = false;
            return true;
        }
        return false;
    }

    public static boolean beginPopupModalCloseable(String id) {
        return ImGuiHelper.beginPopupModalCloseable(id, 0);
    }

    public static boolean beginPopupModalCloseable(String id, int imGuiWindowFlags) {
        if (ImGui.beginPopupModal(id, new ImBoolean(true), imGuiWindowFlags | 0x100)) {
            closeableModalOnTop = true;
            return true;
        }
        return false;
    }

    public static void endPopupModalCloseable() {
        if (closeableModalOnTop && closeableModalOnTopLast && ReplayUI.consumeCancel()) {
            ImGui.closeCurrentPopup();
        }
        ImGui.endPopup();
    }

    public static void setupBorder() {
        ImGui.beginGroup();
        float windowPadding = ImGui.getStyle().getWindowPaddingX();
        borderIndentation += windowPadding;
        ImGui.indent(windowPadding);
        ImGui.setCursorPosY(ImGui.getCursorPosY() + ImGui.getStyle().getWindowPaddingY());
    }

    public static void finishBorder() {
        float windowPadding = ImGui.getStyle().getWindowPaddingX();
        borderIndentation -= windowPadding;
        ImGui.unindent(windowPadding);
        ImGui.endGroup();
        ImGui.getWindowDrawList().addRect(ImGui.getItemRectMinX(), ImGui.getItemRectMinY(), ImGui.getItemRectMinX() + ImGui.getContentRegionAvailX(), ImGui.getItemRectMaxY() + ImGui.getStyle().getWindowPaddingY(), ImGui.getColorU32(27));
        ImGui.setCursorPosY(ImGui.getCursorPosY() + ImGui.getStyle().getWindowPaddingY());
    }

    public static float calcTextWidth(String text) {
        ImVec2 textSizeVec = new ImVec2();
        ImGui.calcTextSize(textSizeVec, text);
        return textSizeVec.x;
    }

    public static void helpMarker(String message) {
        ImGui.textDisabled("(?)");
        ImGuiHelper.tooltip(message);
    }

    public static void tooltip(String message) {
        ImGuiHelper.tooltip(message, 0);
    }

    public static void tooltip(String message, int imGuiHoveredFlags) {
        if (ImGui.isItemHovered(imGuiHoveredFlags)) {
            ImGuiHelper.drawTooltip(message);
        }
    }

    public static void drawTooltip(String message) {
        ImGui.beginTooltip();
        ImGui.pushTextWrapPos((float)ImGui.getFontSize() * 35.0f);
        ImGui.textUnformatted(message);
        ImGui.popTextWrapPos();
        ImGui.endTooltip();
    }

    public static void disabledMenuItem(String label, String disabledMessage) {
        ImGui.beginDisabled();
        ImGui.menuItem(label);
        ImGui.endDisabled();
        ImGuiHelper.tooltip(disabledMessage, 1024);
    }

    public static void separatorWithText(String text) {
        float textStartX = ImGui.getCursorScreenPosX() + ImGui.getStyle().getIndentSpacing();
        float size = ImGui.getWindowSizeX() - borderIndentation;
        if (ImGui.isRectVisible(size, ImGui.getFontSize())) {
            float textEndX = textStartX + ImGuiHelper.calcTextWidth(text);
            float lineEndX = ImGui.getWindowPosX() + size;
            float lineY = ImGui.getCursorScreenPosY() + (float)ImGui.getFontSize() / 2.0f;
            ImGui.getWindowDrawList().addLine(ImGui.getCursorScreenPosX() - 4.0f, lineY, Math.min(lineEndX, textStartX) - 4.0f, lineY, ImGui.getColorU32(27));
            if (textEndX + 4.0f < lineEndX) {
                ImGui.getWindowDrawList().addLine(textEndX + 4.0f, lineY, lineEndX - 4.0f, lineY, ImGui.getColorU32(27));
            }
        }
        ImGui.setCursorScreenPos(textStartX, ImGui.getCursorScreenPosY());
        ImGui.textColored(ImGui.getColorU32(1), text);
    }

    public static boolean inputInt(String label, int[] value) {
        if (value.length == 0) {
            ImGui.textUnformatted(label);
            return false;
        }
        boolean valueChanged = false;
        float availableWidth = ImGui.calcItemWidth();
        float innerSpacing = ImGui.getStyle().getItemInnerSpacingX();
        float widthItemOne = Math.max(1.0f, (float)Math.floor((availableWidth - innerSpacing * (float)(value.length - 1)) / (float)value.length));
        float widthItemLast = Math.max(1.0f, (float)Math.floor(availableWidth - (widthItemOne + innerSpacing) * (float)(value.length - 1)));
        ImGui.beginGroup();
        ImGui.pushID(label);
        for (int i = 0; i < value.length; ++i) {
            int scroll;
            ImGui.pushID(i);
            if (i > 0) {
                ImGui.sameLine(0.0f, innerSpacing);
            }
            ImInt imInt = new ImInt(value[i]);
            if (i < value.length - 1) {
                ImGui.setNextItemWidth(widthItemOne);
            } else {
                ImGui.setNextItemWidth(widthItemLast);
            }
            if (++focusIndex == focusLastIndex || focusNext) {
                ImGui.setKeyboardFocusHere();
                focusNext = false;
                focusLastIndex = 0;
            }
            if (ImGui.inputInt("", imInt, 0)) {
                valueChanged = true;
                value[i] = imInt.get();
            }
            if (ImGui.isItemHovered() && (scroll = (int)Math.signum(ReplayUI.getIO().getMouseWheel())) != 0) {
                valueChanged = true;
                int n = i;
                value[n] = value[n] + 1;
            }
            if (!handledFocusNext && ImGui.isItemActive() && ImGui.isKeyPressed(258, false)) {
                handledFocusNext = true;
                if (ImGui.isKeyDown(340) || ImGui.isKeyDown(344)) {
                    focusLastIndex = focusIndex - 1;
                } else {
                    focusNext = true;
                }
            }
            ImGui.popID();
        }
        ImGui.popID();
        String renderedText = label.split("##")[0];
        if (!renderedText.isEmpty()) {
            ImGui.sameLine(0.0f, innerSpacing);
            ImGui.textUnformatted(renderedText);
        }
        ImGui.endGroup();
        return valueChanged;
    }

    public static boolean inputFloat(String label, float[] value) {
        if (value.length == 0) {
            ImGui.textUnformatted(label);
            return false;
        }
        boolean valueChanged = false;
        float availableWidth = ImGui.calcItemWidth();
        float innerSpacing = ImGui.getStyle().getItemInnerSpacingX();
        float widthItemOne = Math.max(1.0f, (float)Math.floor((availableWidth - innerSpacing * (float)(value.length - 1)) / (float)value.length));
        float widthItemLast = Math.max(1.0f, (float)Math.floor(availableWidth - (widthItemOne + innerSpacing) * (float)(value.length - 1)));
        ImGui.beginGroup();
        ImGui.pushID(label);
        for (int i = 0; i < value.length; ++i) {
            ImGui.pushID(i);
            if (i > 0) {
                ImGui.sameLine(0.0f, innerSpacing);
            }
            ImFloat imFloat = new ImFloat(value[i]);
            if (i < value.length - 1) {
                ImGui.setNextItemWidth(widthItemOne);
            } else {
                ImGui.setNextItemWidth(widthItemLast);
            }
            if (++focusIndex == focusLastIndex || focusNext) {
                ImGui.setKeyboardFocusHere();
                focusNext = false;
                focusLastIndex = 0;
            }
            int scroll = (int)Math.signum(ReplayUI.getIO().getMouseWheel());
            if (ImGui.inputFloat("", imFloat, 0.0f)) {
                valueChanged = true;
                value[i] = imFloat.get();
            }
            if (ImGui.isItemHovered() && scroll != 0) {
                valueChanged = true;
                value[i] = Math.round(value[i] + (float)scroll);
            }
            if (!handledFocusNext && ImGui.isItemActive() && ImGui.isKeyPressed(258, false)) {
                handledFocusNext = true;
                if (ImGui.isKeyDown(340) || ImGui.isKeyDown(344)) {
                    focusLastIndex = focusIndex - 1;
                } else {
                    focusNext = true;
                }
            }
            ImGui.popID();
        }
        ImGui.popID();
        String renderedText = label.split("##")[0];
        if (!renderedText.isEmpty()) {
            ImGui.sameLine(0.0f, innerSpacing);
            ImGui.textUnformatted(renderedText);
        }
        ImGui.endGroup();
        return valueChanged;
    }

    public static boolean radio(String label, int[] currentItem, String[] values) {
        int item = currentItem[0];
        int newItem = -1;
        for (int i = 0; i < values.length; ++i) {
            String value = values[i];
            if (i > 0) {
                ImGui.sameLine();
            }
            if (item == i) {
                ImGui.beginDisabled();
            }
            if (ImGui.button(value)) {
                newItem = i;
            }
            if (item != i) continue;
            ImGui.endDisabled();
        }
        if (label != null) {
            ImGui.sameLine();
            ImGui.textUnformatted(label);
        }
        if (newItem != -1) {
            currentItem[0] = newItem;
            return true;
        }
        return false;
    }

    public static int buttons(String ... labels) {
        if (labels.length == 0) {
            return -1;
        }
        float[] widths = new float[labels.length];
        float totalRawWidth = 0.0f;
        for (int i = 0; i < labels.length; ++i) {
            float width;
            String label = labels[i];
            widths[i] = width = ImGuiHelper.calcTextWidth(label);
            totalRawWidth += width;
        }
        float availableWidth = ImGui.getContentRegionAvailX();
        float freeSpace = availableWidth - totalRawWidth - ImGui.getStyle().getItemSpacingX() * (float)(labels.length - 1);
        float extraPadding = Math.max(ImGui.getStyle().getFramePaddingX() * 2.0f, freeSpace / (float)labels.length);
        float height = (float)ImGui.getFontSize() + ImGui.getStyle().getFramePaddingY() * 2.0f;
        int pressed = -1;
        for (int i = 0; i < labels.length; ++i) {
            if (ImGui.button(labels[i], widths[i] + extraPadding, height)) {
                pressed = i;
            }
            if (i == labels.length - 1) continue;
            ImGui.sameLine();
        }
        return pressed;
    }

    public static void popAllStyleColors() {
        ImGui.popStyleColor(pushedColors);
        pushedColors = 0;
    }

    public static void popAllStyleVars() {
        ImGui.popStyleVar(pushedStyleVars);
        pushedStyleVars = 0;
    }

    public static void pushStyleColor(int imGuiCol, float r, float g, float b, float a) {
        ImGui.pushStyleColor(imGuiCol, r, g, b, a);
        ++pushedColors;
    }

    public static void pushStyleColor(int imGuiCol, int r, int g, int b, int a) {
        ImGui.pushStyleColor(imGuiCol, r, g, b, a);
        ++pushedColors;
    }

    public static void pushStyleColor(int imGuiCol, int col) {
        ImGui.pushStyleColor(imGuiCol, col);
        ++pushedColors;
    }

    public static void popStyleColor() {
        if (pushedColors >= 1) {
            ImGui.popStyleColor();
            --pushedColors;
        }
    }

    public static void popStyleColor(int count) {
        if ((count = Math.min(count, pushedColors)) == 0) {
            return;
        }
        ImGui.popStyleColor(count);
        pushedColors -= count;
    }

    public static void pushStyleVar(int imGuiStyleVar, float val) {
        ImGui.pushStyleVar(imGuiStyleVar, val);
        ++pushedStyleVars;
    }

    public static void pushStyleVar(int imGuiStyleVar, float valX, float valY) {
        ImGui.pushStyleVar(imGuiStyleVar, valX, valY);
        ++pushedStyleVars;
    }

    public static void popStyleVar() {
        if (pushedStyleVars >= 1) {
            ImGui.popStyleVar();
            --pushedStyleVars;
        }
    }

    public static void popStyleVar(int count) {
        if ((count = Math.min(count, pushedStyleVars)) == 0) {
            return;
        }
        ImGui.popStyleVar(count);
        pushedStyleVars -= count;
    }
}

