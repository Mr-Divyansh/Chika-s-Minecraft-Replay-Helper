/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.imguiknobs;

import imgui.moulberry90.type.ImFloat;
import imgui.moulberry90.type.ImInt;

public final class ImGuiKnobs {
    private ImGuiKnobs() {
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, float speed) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, float speed, String format) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, float speed, String format, int variant) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, float speed, String format, int variant, float size) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, size);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, float speed, String format, int variant, float size, int flags) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, size, flags);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, float speed, String format, int variant, float size, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, size, flags, steps);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, String format, int variant, float size, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, format, variant, size, flags, steps);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, int variant, float size, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, variant, size, flags, steps);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, float speed, int variant, float size, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, variant, size, flags, steps);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, float speed, float size, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, size, flags, steps);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, float speed, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, flags, steps);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, float speed, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, steps);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, float speed, String format, float size, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, size, flags, steps);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, float speed, String format, int variant, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, flags, steps);
    }

    public static boolean knob(String label, ImFloat pValue, float vMin, float vMax, float speed, String format, int variant, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, steps);
    }

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, float var4);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, float var4, String var5);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, float var4, String var5, int var6);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, float var4, String var5, int var6, float var7);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, float var4, String var5, int var6, float var7, int var8);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, float var4, String var5, int var6, float var7, int var8, int var9);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, String var4, int var5, float var6, int var7, int var8);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, int var4, float var5, int var6, int var7);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, float var4, int var5, float var6, int var7, int var8);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, float var4, float var5, int var6, int var7);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, float var4, int var5, int var6);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, float var4, int var5);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, float var4, String var5, float var6, int var7, int var8);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, float var4, String var5, int var6, int var7, int var8);

    private static native boolean nKnob(String var0, float[] var1, float var2, float var3, float var4, String var5, int var6, int var7);

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, float speed) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, float speed, String format) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, float speed, String format, int variant) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, float speed, String format, int variant, float size) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, size);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, float speed, String format, int variant, float size, int flags) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, size, flags);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, float speed, String format, int variant, float size, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, size, flags, steps);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, String format, int variant, float size, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, format, variant, size, flags, steps);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, int variant, float size, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, variant, size, flags, steps);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, float speed, int variant, float size, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, variant, size, flags, steps);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, float speed, float size, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, size, flags, steps);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, float speed, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, flags, steps);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, float speed, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, steps);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, float speed, String format, float size, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, size, flags, steps);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, float speed, String format, int variant, int flags, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, flags, steps);
    }

    public static boolean knob(String label, ImInt pValue, int vMin, int vMax, float speed, String format, int variant, int steps) {
        return ImGuiKnobs.nKnob(label, pValue != null ? pValue.getData() : null, vMin, vMax, speed, format, variant, steps);
    }

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, float var4);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, float var4, String var5);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, float var4, String var5, int var6);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, float var4, String var5, int var6, float var7);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, float var4, String var5, int var6, float var7, int var8);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, float var4, String var5, int var6, float var7, int var8, int var9);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, String var4, int var5, float var6, int var7, int var8);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, int var4, float var5, int var6, int var7);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, float var4, int var5, float var6, int var7, int var8);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, float var4, float var5, int var6, int var7);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, float var4, int var5, int var6);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, float var4, int var5);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, float var4, String var5, float var6, int var7, int var8);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, float var4, String var5, int var6, int var7, int var8);

    private static native boolean nKnob(String var0, int[] var1, int var2, int var3, float var4, String var5, int var6, int var7);
}

