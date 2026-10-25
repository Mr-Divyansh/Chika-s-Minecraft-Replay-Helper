/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.implot;

import imgui.moulberry90.ImDrawList;
import imgui.moulberry90.ImVec2;
import imgui.moulberry90.ImVec4;
import imgui.moulberry90.extension.implot.ImPlotContext;
import imgui.moulberry90.extension.implot.ImPlotInputMap;
import imgui.moulberry90.extension.implot.ImPlotPoint;
import imgui.moulberry90.extension.implot.ImPlotRange;
import imgui.moulberry90.extension.implot.ImPlotRect;
import imgui.moulberry90.extension.implot.ImPlotStyle;
import imgui.moulberry90.internal.ImGuiContext;
import imgui.moulberry90.type.ImBoolean;
import imgui.moulberry90.type.ImDouble;
import imgui.moulberry90.type.ImFloat;

public final class ImPlot {
    private static final ImPlotStyle _GETSTYLE_1 = new ImPlotStyle(0L);
    private static final ImPlotInputMap _GETINPUTMAP_1 = new ImPlotInputMap(0L);

    private ImPlot() {
    }

    public static ImPlotContext createContext() {
        return new ImPlotContext(ImPlot.nCreateContext());
    }

    private static native long nCreateContext();

    public static void destroyContext() {
        ImPlot.nDestroyContext();
    }

    public static void destroyContext(ImPlotContext ctx) {
        ImPlot.nDestroyContext(ctx.ptr);
    }

    private static native void nDestroyContext();

    private static native void nDestroyContext(long var0);

    public static ImPlotContext getCurrentContext() {
        return new ImPlotContext(ImPlot.nGetCurrentContext());
    }

    private static native long nGetCurrentContext();

    public static void setCurrentContext(ImPlotContext ctx) {
        ImPlot.nSetCurrentContext(ctx.ptr);
    }

    private static native void nSetCurrentContext(long var0);

    public static void setImGuiContext(ImGuiContext ctx) {
        ImPlot.nSetImGuiContext(ctx.ptr);
    }

    private static native void nSetImGuiContext(long var0);

    public static boolean beginPlot(String titleId) {
        return ImPlot.nBeginPlot(titleId);
    }

    public static boolean beginPlot(String titleId, ImVec2 size) {
        return ImPlot.nBeginPlot(titleId, size.x, size.y);
    }

    public static boolean beginPlot(String titleId, float sizeX, float sizeY) {
        return ImPlot.nBeginPlot(titleId, sizeX, sizeY);
    }

    public static boolean beginPlot(String titleId, ImVec2 size, int flags) {
        return ImPlot.nBeginPlot(titleId, size.x, size.y, flags);
    }

    public static boolean beginPlot(String titleId, float sizeX, float sizeY, int flags) {
        return ImPlot.nBeginPlot(titleId, sizeX, sizeY, flags);
    }

    public static boolean beginPlot(String titleId, int flags) {
        return ImPlot.nBeginPlot(titleId, flags);
    }

    private static native boolean nBeginPlot(String var0);

    private static native boolean nBeginPlot(String var0, float var1, float var2);

    private static native boolean nBeginPlot(String var0, float var1, float var2, int var3);

    private static native boolean nBeginPlot(String var0, int var1);

    public static void endPlot() {
        ImPlot.nEndPlot();
    }

    private static native void nEndPlot();

    public static boolean beginSubplots(String titleID, int rows, int cols, ImVec2 size) {
        return ImPlot.nBeginSubplots(titleID, rows, cols, size.x, size.y);
    }

    public static boolean beginSubplots(String titleID, int rows, int cols, float sizeX, float sizeY) {
        return ImPlot.nBeginSubplots(titleID, rows, cols, sizeX, sizeY);
    }

    public static boolean beginSubplots(String titleID, int rows, int cols, ImVec2 size, int flags) {
        return ImPlot.nBeginSubplots(titleID, rows, cols, size.x, size.y, flags);
    }

    public static boolean beginSubplots(String titleID, int rows, int cols, float sizeX, float sizeY, int flags) {
        return ImPlot.nBeginSubplots(titleID, rows, cols, sizeX, sizeY, flags);
    }

    public static boolean beginSubplots(String titleID, int rows, int cols, ImVec2 size, int flags, float[] rowRatios) {
        return ImPlot.nBeginSubplots(titleID, rows, cols, size.x, size.y, flags, rowRatios);
    }

    public static boolean beginSubplots(String titleID, int rows, int cols, float sizeX, float sizeY, int flags, float[] rowRatios) {
        return ImPlot.nBeginSubplots(titleID, rows, cols, sizeX, sizeY, flags, rowRatios);
    }

    public static boolean beginSubplots(String titleID, int rows, int cols, ImVec2 size, int flags, float[] rowRatios, float[] colRatios) {
        return ImPlot.nBeginSubplots(titleID, rows, cols, size.x, size.y, flags, rowRatios, colRatios);
    }

    public static boolean beginSubplots(String titleID, int rows, int cols, float sizeX, float sizeY, int flags, float[] rowRatios, float[] colRatios) {
        return ImPlot.nBeginSubplots(titleID, rows, cols, sizeX, sizeY, flags, rowRatios, colRatios);
    }

    public static boolean beginSubplots(String titleID, int rows, int cols, ImVec2 size, float[] rowRatios, float[] colRatios) {
        return ImPlot.nBeginSubplots(titleID, rows, cols, size.x, size.y, rowRatios, colRatios);
    }

    public static boolean beginSubplots(String titleID, int rows, int cols, float sizeX, float sizeY, float[] rowRatios, float[] colRatios) {
        return ImPlot.nBeginSubplots(titleID, rows, cols, sizeX, sizeY, rowRatios, colRatios);
    }

    private static native boolean nBeginSubplots(String var0, int var1, int var2, float var3, float var4);

    private static native boolean nBeginSubplots(String var0, int var1, int var2, float var3, float var4, int var5);

    private static native boolean nBeginSubplots(String var0, int var1, int var2, float var3, float var4, int var5, float[] var6);

    private static native boolean nBeginSubplots(String var0, int var1, int var2, float var3, float var4, int var5, float[] var6, float[] var7);

    private static native boolean nBeginSubplots(String var0, int var1, int var2, float var3, float var4, float[] var5, float[] var6);

    public static void endSubplots() {
        ImPlot.nEndSubplots();
    }

    private static native void nEndSubplots();

    public static void setupAxis(int axis) {
        ImPlot.nSetupAxis(axis);
    }

    public static void setupAxis(int axis, String label) {
        ImPlot.nSetupAxis(axis, label);
    }

    public static void setupAxis(int axis, String label, int flags) {
        ImPlot.nSetupAxis(axis, label, flags);
    }

    public static void setupAxis(int axis, int flags) {
        ImPlot.nSetupAxis(axis, flags);
    }

    private static native void nSetupAxis(int var0);

    private static native void nSetupAxis(int var0, String var1);

    private static native void nSetupAxis(int var0, String var1, int var2);

    private static native void nSetupAxis(int var0, int var1);

    public static void setupAxisLimits(int axis, double vMin, double vMax) {
        ImPlot.nSetupAxisLimits(axis, vMin, vMax);
    }

    public static void setupAxisLimits(int axis, double vMin, double vMax, int cond) {
        ImPlot.nSetupAxisLimits(axis, vMin, vMax, cond);
    }

    private static native void nSetupAxisLimits(int var0, double var1, double var3);

    private static native void nSetupAxisLimits(int var0, double var1, double var3, int var5);

    public static void setupAxisLinks(int axis, ImDouble linkMin, ImDouble linkMax) {
        ImPlot.nSetupAxisLinks(axis, linkMin != null ? linkMin.getData() : null, linkMax != null ? linkMax.getData() : null);
    }

    private static native void nSetupAxisLinks(int var0, double[] var1, double[] var2);

    public static void setupAxisFormat(int axis, String fmt) {
        ImPlot.nSetupAxisFormat(axis, fmt);
    }

    private static native void nSetupAxisFormat(int var0, String var1);

    public static void setupAxisTicks(int axis, double[] values, int nTicks) {
        ImPlot.nSetupAxisTicks(axis, values, nTicks);
    }

    public static void setupAxisTicks(int axis, double[] values, int nTicks, String[] labels) {
        ImPlot.nSetupAxisTicks(axis, values, nTicks, labels, labels.length);
    }

    public static void setupAxisTicks(int axis, double[] values, int nTicks, String[] labels, boolean keepDefault) {
        ImPlot.nSetupAxisTicks(axis, values, nTicks, labels, labels.length, keepDefault);
    }

    public static void setupAxisTicks(int axis, double[] values, int nTicks, boolean keepDefault) {
        ImPlot.nSetupAxisTicks(axis, values, nTicks, keepDefault);
    }

    private static native void nSetupAxisTicks(int var0, double[] var1, int var2);

    private static native void nSetupAxisTicks(int var0, double[] var1, int var2, String[] var3, int var4);

    private static native void nSetupAxisTicks(int var0, double[] var1, int var2, String[] var3, int var4, boolean var5);

    private static native void nSetupAxisTicks(int var0, double[] var1, int var2, boolean var3);

    public static void setupAxisTicks(int axis, double vMin, double vMax, int nTicks) {
        ImPlot.nSetupAxisTicks(axis, vMin, vMax, nTicks);
    }

    public static void setupAxisTicks(int axis, double vMin, double vMax, int nTicks, String[] labels) {
        ImPlot.nSetupAxisTicks(axis, vMin, vMax, nTicks, labels, labels.length);
    }

    public static void setupAxisTicks(int axis, double vMin, double vMax, int nTicks, String[] labels, boolean keepDefault) {
        ImPlot.nSetupAxisTicks(axis, vMin, vMax, nTicks, labels, labels.length, keepDefault);
    }

    public static void setupAxisTicks(int axis, double vMin, double vMax, int nTicks, boolean keepDefault) {
        ImPlot.nSetupAxisTicks(axis, vMin, vMax, nTicks, keepDefault);
    }

    private static native void nSetupAxisTicks(int var0, double var1, double var3, int var5);

    private static native void nSetupAxisTicks(int var0, double var1, double var3, int var5, String[] var6, int var7);

    private static native void nSetupAxisTicks(int var0, double var1, double var3, int var5, String[] var6, int var7, boolean var8);

    private static native void nSetupAxisTicks(int var0, double var1, double var3, int var5, boolean var6);

    public static void setupAxisScale(int axis, int scale) {
        ImPlot.nSetupAxisScale(axis, scale);
    }

    private static native void nSetupAxisScale(int var0, int var1);

    public static void setupAxisLimitsConstraints(int axis, double vMin, double vMax) {
        ImPlot.nSetupAxisLimitsConstraints(axis, vMin, vMax);
    }

    private static native void nSetupAxisLimitsConstraints(int var0, double var1, double var3);

    public static void setupAxisZoomConstraints(int axis, double vMin, double vMax) {
        ImPlot.nSetupAxisZoomConstraints(axis, vMin, vMax);
    }

    private static native void nSetupAxisZoomConstraints(int var0, double var1, double var3);

    public static void setupAxes(String xLabel, String yLabel) {
        ImPlot.nSetupAxes(xLabel, yLabel);
    }

    public static void setupAxes(String xLabel, String yLabel, int xFlags) {
        ImPlot.nSetupAxes(xLabel, yLabel, xFlags);
    }

    public static void setupAxes(String xLabel, String yLabel, int xFlags, int yFlags) {
        ImPlot.nSetupAxes(xLabel, yLabel, xFlags, yFlags);
    }

    private static native void nSetupAxes(String var0, String var1);

    private static native void nSetupAxes(String var0, String var1, int var2);

    private static native void nSetupAxes(String var0, String var1, int var2, int var3);

    public static void setupAxesLimits(double xMin, double xMax, double yMin, double yMax) {
        ImPlot.nSetupAxesLimits(xMin, xMax, yMin, yMax);
    }

    public static void setupAxesLimits(double xMin, double xMax, double yMin, double yMax, int cond) {
        ImPlot.nSetupAxesLimits(xMin, xMax, yMin, yMax, cond);
    }

    private static native void nSetupAxesLimits(double var0, double var2, double var4, double var6);

    private static native void nSetupAxesLimits(double var0, double var2, double var4, double var6, int var8);

    public static void setupLegend(int location) {
        ImPlot.nSetupLegend(location);
    }

    public static void setupLegend(int location, int flags) {
        ImPlot.nSetupLegend(location, flags);
    }

    private static native void nSetupLegend(int var0);

    private static native void nSetupLegend(int var0, int var1);

    public static void setupMouseText(int location) {
        ImPlot.nSetupMouseText(location);
    }

    public static void setupMouseText(int location, int flags) {
        ImPlot.nSetupMouseText(location, flags);
    }

    private static native void nSetupMouseText(int var0);

    private static native void nSetupMouseText(int var0, int var1);

    public static void setupFinish() {
        ImPlot.nSetupFinish();
    }

    private static native void nSetupFinish();

    public static void setNextAxisLimits(int axis, double vMin, double vMax) {
        ImPlot.nSetNextAxisLimits(axis, vMin, vMax);
    }

    public static void setNextAxisLimits(int axis, double vMin, double vMax, int cond) {
        ImPlot.nSetNextAxisLimits(axis, vMin, vMax, cond);
    }

    private static native void nSetNextAxisLimits(int var0, double var1, double var3);

    private static native void nSetNextAxisLimits(int var0, double var1, double var3, int var5);

    public static void setNextAxisLinks(int axis, ImDouble linkMin, ImDouble linkMax) {
        ImPlot.nSetNextAxisLinks(axis, linkMin != null ? linkMin.getData() : null, linkMax != null ? linkMax.getData() : null);
    }

    private static native void nSetNextAxisLinks(int var0, double[] var1, double[] var2);

    public static void setNextAxisToFit(int axis) {
        ImPlot.nSetNextAxisToFit(axis);
    }

    private static native void nSetNextAxisToFit(int var0);

    public static void setNextAxesLimits(double xMin, double xMax, double yMin, double yMax) {
        ImPlot.nSetNextAxesLimits(xMin, xMax, yMin, yMax);
    }

    public static void setNextAxesLimits(double xMin, double xMax, double yMin, double yMax, int cond) {
        ImPlot.nSetNextAxesLimits(xMin, xMax, yMin, yMax, cond);
    }

    private static native void nSetNextAxesLimits(double var0, double var2, double var4, double var6);

    private static native void nSetNextAxesLimits(double var0, double var2, double var4, double var6, int var8);

    public static void setNextAxesToFit() {
        ImPlot.nSetNextAxesToFit();
    }

    private static native void nSetNextAxesToFit();

    public static void plotLine(String labelId, short[] values) {
        ImPlot.nPlotLine(labelId, values);
    }

    public static void plotLine(String labelId, short[] values, double xscale) {
        ImPlot.nPlotLine(labelId, values, xscale);
    }

    public static void plotLine(String labelId, short[] values, double xscale, double xstart) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart);
    }

    public static void plotLine(String labelId, short[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart, flags);
    }

    public static void plotLine(String labelId, short[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotLine(String var0, short[] var1);

    private static native void nPlotLine(String var0, short[] var1, double var2);

    private static native void nPlotLine(String var0, short[] var1, double var2, double var4);

    private static native void nPlotLine(String var0, short[] var1, double var2, double var4, int var6);

    private static native void nPlotLine(String var0, short[] var1, double var2, double var4, int var6, int var7);

    public static void plotLine(String labelId, int[] values) {
        ImPlot.nPlotLine(labelId, values);
    }

    public static void plotLine(String labelId, int[] values, double xscale) {
        ImPlot.nPlotLine(labelId, values, xscale);
    }

    public static void plotLine(String labelId, int[] values, double xscale, double xstart) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart);
    }

    public static void plotLine(String labelId, int[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart, flags);
    }

    public static void plotLine(String labelId, int[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotLine(String var0, int[] var1);

    private static native void nPlotLine(String var0, int[] var1, double var2);

    private static native void nPlotLine(String var0, int[] var1, double var2, double var4);

    private static native void nPlotLine(String var0, int[] var1, double var2, double var4, int var6);

    private static native void nPlotLine(String var0, int[] var1, double var2, double var4, int var6, int var7);

    public static void plotLine(String labelId, long[] values) {
        ImPlot.nPlotLine(labelId, values);
    }

    public static void plotLine(String labelId, long[] values, double xscale) {
        ImPlot.nPlotLine(labelId, values, xscale);
    }

    public static void plotLine(String labelId, long[] values, double xscale, double xstart) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart);
    }

    public static void plotLine(String labelId, long[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart, flags);
    }

    public static void plotLine(String labelId, long[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotLine(String var0, long[] var1);

    private static native void nPlotLine(String var0, long[] var1, double var2);

    private static native void nPlotLine(String var0, long[] var1, double var2, double var4);

    private static native void nPlotLine(String var0, long[] var1, double var2, double var4, int var6);

    private static native void nPlotLine(String var0, long[] var1, double var2, double var4, int var6, int var7);

    public static void plotLine(String labelId, float[] values) {
        ImPlot.nPlotLine(labelId, values);
    }

    public static void plotLine(String labelId, float[] values, double xscale) {
        ImPlot.nPlotLine(labelId, values, xscale);
    }

    public static void plotLine(String labelId, float[] values, double xscale, double xstart) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart);
    }

    public static void plotLine(String labelId, float[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart, flags);
    }

    public static void plotLine(String labelId, float[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotLine(String var0, float[] var1);

    private static native void nPlotLine(String var0, float[] var1, double var2);

    private static native void nPlotLine(String var0, float[] var1, double var2, double var4);

    private static native void nPlotLine(String var0, float[] var1, double var2, double var4, int var6);

    private static native void nPlotLine(String var0, float[] var1, double var2, double var4, int var6, int var7);

    public static void plotLine(String labelId, double[] values) {
        ImPlot.nPlotLine(labelId, values);
    }

    public static void plotLine(String labelId, double[] values, double xscale) {
        ImPlot.nPlotLine(labelId, values, xscale);
    }

    public static void plotLine(String labelId, double[] values, double xscale, double xstart) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart);
    }

    public static void plotLine(String labelId, double[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart, flags);
    }

    public static void plotLine(String labelId, double[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotLine(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotLine(String var0, double[] var1);

    private static native void nPlotLine(String var0, double[] var1, double var2);

    private static native void nPlotLine(String var0, double[] var1, double var2, double var4);

    private static native void nPlotLine(String var0, double[] var1, double var2, double var4, int var6);

    private static native void nPlotLine(String var0, double[] var1, double var2, double var4, int var6, int var7);

    public static void plotLineV(String labelId, short[] values, int count) {
        ImPlot.nPlotLineV(labelId, values, count);
    }

    public static void plotLineV(String labelId, short[] values, int count, double xscale) {
        ImPlot.nPlotLineV(labelId, values, count, xscale);
    }

    public static void plotLineV(String labelId, short[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart);
    }

    public static void plotLineV(String labelId, short[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotLineV(String labelId, short[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotLineV(String var0, short[] var1, int var2);

    private static native void nPlotLineV(String var0, short[] var1, int var2, double var3);

    private static native void nPlotLineV(String var0, short[] var1, int var2, double var3, double var5);

    private static native void nPlotLineV(String var0, short[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotLineV(String var0, short[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotLineV(String labelId, int[] values, int count) {
        ImPlot.nPlotLineV(labelId, values, count);
    }

    public static void plotLineV(String labelId, int[] values, int count, double xscale) {
        ImPlot.nPlotLineV(labelId, values, count, xscale);
    }

    public static void plotLineV(String labelId, int[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart);
    }

    public static void plotLineV(String labelId, int[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotLineV(String labelId, int[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotLineV(String var0, int[] var1, int var2);

    private static native void nPlotLineV(String var0, int[] var1, int var2, double var3);

    private static native void nPlotLineV(String var0, int[] var1, int var2, double var3, double var5);

    private static native void nPlotLineV(String var0, int[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotLineV(String var0, int[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotLineV(String labelId, long[] values, int count) {
        ImPlot.nPlotLineV(labelId, values, count);
    }

    public static void plotLineV(String labelId, long[] values, int count, double xscale) {
        ImPlot.nPlotLineV(labelId, values, count, xscale);
    }

    public static void plotLineV(String labelId, long[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart);
    }

    public static void plotLineV(String labelId, long[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotLineV(String labelId, long[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotLineV(String var0, long[] var1, int var2);

    private static native void nPlotLineV(String var0, long[] var1, int var2, double var3);

    private static native void nPlotLineV(String var0, long[] var1, int var2, double var3, double var5);

    private static native void nPlotLineV(String var0, long[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotLineV(String var0, long[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotLineV(String labelId, float[] values, int count) {
        ImPlot.nPlotLineV(labelId, values, count);
    }

    public static void plotLineV(String labelId, float[] values, int count, double xscale) {
        ImPlot.nPlotLineV(labelId, values, count, xscale);
    }

    public static void plotLineV(String labelId, float[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart);
    }

    public static void plotLineV(String labelId, float[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotLineV(String labelId, float[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotLineV(String var0, float[] var1, int var2);

    private static native void nPlotLineV(String var0, float[] var1, int var2, double var3);

    private static native void nPlotLineV(String var0, float[] var1, int var2, double var3, double var5);

    private static native void nPlotLineV(String var0, float[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotLineV(String var0, float[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotLineV(String labelId, double[] values, int count) {
        ImPlot.nPlotLineV(labelId, values, count);
    }

    public static void plotLineV(String labelId, double[] values, int count, double xscale) {
        ImPlot.nPlotLineV(labelId, values, count, xscale);
    }

    public static void plotLineV(String labelId, double[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart);
    }

    public static void plotLineV(String labelId, double[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotLineV(String labelId, double[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotLineV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotLineV(String var0, double[] var1, int var2);

    private static native void nPlotLineV(String var0, double[] var1, int var2, double var3);

    private static native void nPlotLineV(String var0, double[] var1, int var2, double var3, double var5);

    private static native void nPlotLineV(String var0, double[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotLineV(String var0, double[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotLine(String labelId, short[] xs, short[] ys) {
        ImPlot.nPlotLine(labelId, xs, ys);
    }

    public static void plotLine(String labelId, short[] xs, short[] ys, int flags) {
        ImPlot.nPlotLine(labelId, xs, ys, flags);
    }

    private static native void nPlotLine(String var0, short[] var1, short[] var2);

    private static native void nPlotLine(String var0, short[] var1, short[] var2, int var3);

    public static void plotLine(String labelId, int[] xs, int[] ys) {
        ImPlot.nPlotLine(labelId, xs, ys);
    }

    public static void plotLine(String labelId, int[] xs, int[] ys, int flags) {
        ImPlot.nPlotLine(labelId, xs, ys, flags);
    }

    private static native void nPlotLine(String var0, int[] var1, int[] var2);

    private static native void nPlotLine(String var0, int[] var1, int[] var2, int var3);

    public static void plotLine(String labelId, long[] xs, long[] ys) {
        ImPlot.nPlotLine(labelId, xs, ys);
    }

    public static void plotLine(String labelId, long[] xs, long[] ys, int flags) {
        ImPlot.nPlotLine(labelId, xs, ys, flags);
    }

    private static native void nPlotLine(String var0, long[] var1, long[] var2);

    private static native void nPlotLine(String var0, long[] var1, long[] var2, int var3);

    public static void plotLine(String labelId, float[] xs, float[] ys) {
        ImPlot.nPlotLine(labelId, xs, ys);
    }

    public static void plotLine(String labelId, float[] xs, float[] ys, int flags) {
        ImPlot.nPlotLine(labelId, xs, ys, flags);
    }

    private static native void nPlotLine(String var0, float[] var1, float[] var2);

    private static native void nPlotLine(String var0, float[] var1, float[] var2, int var3);

    public static void plotLine(String labelId, double[] xs, double[] ys) {
        ImPlot.nPlotLine(labelId, xs, ys);
    }

    public static void plotLine(String labelId, double[] xs, double[] ys, int flags) {
        ImPlot.nPlotLine(labelId, xs, ys, flags);
    }

    private static native void nPlotLine(String var0, double[] var1, double[] var2);

    private static native void nPlotLine(String var0, double[] var1, double[] var2, int var3);

    public static void plotLineV(String labelId, short[] xs, short[] ys, int count) {
        ImPlot.nPlotLineV(labelId, xs, ys, count);
    }

    public static void plotLineV(String labelId, short[] xs, short[] ys, int count, int flags) {
        ImPlot.nPlotLineV(labelId, xs, ys, count, flags);
    }

    public static void plotLineV(String labelId, short[] xs, short[] ys, int count, int flags, int offset) {
        ImPlot.nPlotLineV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotLineV(String var0, short[] var1, short[] var2, int var3);

    private static native void nPlotLineV(String var0, short[] var1, short[] var2, int var3, int var4);

    private static native void nPlotLineV(String var0, short[] var1, short[] var2, int var3, int var4, int var5);

    public static void plotLineV(String labelId, int[] xs, int[] ys, int count) {
        ImPlot.nPlotLineV(labelId, xs, ys, count);
    }

    public static void plotLineV(String labelId, int[] xs, int[] ys, int count, int flags) {
        ImPlot.nPlotLineV(labelId, xs, ys, count, flags);
    }

    public static void plotLineV(String labelId, int[] xs, int[] ys, int count, int flags, int offset) {
        ImPlot.nPlotLineV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotLineV(String var0, int[] var1, int[] var2, int var3);

    private static native void nPlotLineV(String var0, int[] var1, int[] var2, int var3, int var4);

    private static native void nPlotLineV(String var0, int[] var1, int[] var2, int var3, int var4, int var5);

    public static void plotLineV(String labelId, long[] xs, long[] ys, int count) {
        ImPlot.nPlotLineV(labelId, xs, ys, count);
    }

    public static void plotLineV(String labelId, long[] xs, long[] ys, int count, int flags) {
        ImPlot.nPlotLineV(labelId, xs, ys, count, flags);
    }

    public static void plotLineV(String labelId, long[] xs, long[] ys, int count, int flags, int offset) {
        ImPlot.nPlotLineV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotLineV(String var0, long[] var1, long[] var2, int var3);

    private static native void nPlotLineV(String var0, long[] var1, long[] var2, int var3, int var4);

    private static native void nPlotLineV(String var0, long[] var1, long[] var2, int var3, int var4, int var5);

    public static void plotLineV(String labelId, float[] xs, float[] ys, int count) {
        ImPlot.nPlotLineV(labelId, xs, ys, count);
    }

    public static void plotLineV(String labelId, float[] xs, float[] ys, int count, int flags) {
        ImPlot.nPlotLineV(labelId, xs, ys, count, flags);
    }

    public static void plotLineV(String labelId, float[] xs, float[] ys, int count, int flags, int offset) {
        ImPlot.nPlotLineV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotLineV(String var0, float[] var1, float[] var2, int var3);

    private static native void nPlotLineV(String var0, float[] var1, float[] var2, int var3, int var4);

    private static native void nPlotLineV(String var0, float[] var1, float[] var2, int var3, int var4, int var5);

    public static void plotLineV(String labelId, double[] xs, double[] ys, int count) {
        ImPlot.nPlotLineV(labelId, xs, ys, count);
    }

    public static void plotLineV(String labelId, double[] xs, double[] ys, int count, int flags) {
        ImPlot.nPlotLineV(labelId, xs, ys, count, flags);
    }

    public static void plotLineV(String labelId, double[] xs, double[] ys, int count, int flags, int offset) {
        ImPlot.nPlotLineV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotLineV(String var0, double[] var1, double[] var2, int var3);

    private static native void nPlotLineV(String var0, double[] var1, double[] var2, int var3, int var4);

    private static native void nPlotLineV(String var0, double[] var1, double[] var2, int var3, int var4, int var5);

    public static void plotScatter(String labelId, short[] values) {
        ImPlot.nPlotScatter(labelId, values);
    }

    public static void plotScatter(String labelId, short[] values, double xscale) {
        ImPlot.nPlotScatter(labelId, values, xscale);
    }

    public static void plotScatter(String labelId, short[] values, double xscale, double xstart) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart);
    }

    public static void plotScatter(String labelId, short[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart, flags);
    }

    public static void plotScatter(String labelId, short[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotScatter(String var0, short[] var1);

    private static native void nPlotScatter(String var0, short[] var1, double var2);

    private static native void nPlotScatter(String var0, short[] var1, double var2, double var4);

    private static native void nPlotScatter(String var0, short[] var1, double var2, double var4, int var6);

    private static native void nPlotScatter(String var0, short[] var1, double var2, double var4, int var6, int var7);

    public static void plotScatter(String labelId, int[] values) {
        ImPlot.nPlotScatter(labelId, values);
    }

    public static void plotScatter(String labelId, int[] values, double xscale) {
        ImPlot.nPlotScatter(labelId, values, xscale);
    }

    public static void plotScatter(String labelId, int[] values, double xscale, double xstart) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart);
    }

    public static void plotScatter(String labelId, int[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart, flags);
    }

    public static void plotScatter(String labelId, int[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotScatter(String var0, int[] var1);

    private static native void nPlotScatter(String var0, int[] var1, double var2);

    private static native void nPlotScatter(String var0, int[] var1, double var2, double var4);

    private static native void nPlotScatter(String var0, int[] var1, double var2, double var4, int var6);

    private static native void nPlotScatter(String var0, int[] var1, double var2, double var4, int var6, int var7);

    public static void plotScatter(String labelId, long[] values) {
        ImPlot.nPlotScatter(labelId, values);
    }

    public static void plotScatter(String labelId, long[] values, double xscale) {
        ImPlot.nPlotScatter(labelId, values, xscale);
    }

    public static void plotScatter(String labelId, long[] values, double xscale, double xstart) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart);
    }

    public static void plotScatter(String labelId, long[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart, flags);
    }

    public static void plotScatter(String labelId, long[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotScatter(String var0, long[] var1);

    private static native void nPlotScatter(String var0, long[] var1, double var2);

    private static native void nPlotScatter(String var0, long[] var1, double var2, double var4);

    private static native void nPlotScatter(String var0, long[] var1, double var2, double var4, int var6);

    private static native void nPlotScatter(String var0, long[] var1, double var2, double var4, int var6, int var7);

    public static void plotScatter(String labelId, float[] values) {
        ImPlot.nPlotScatter(labelId, values);
    }

    public static void plotScatter(String labelId, float[] values, double xscale) {
        ImPlot.nPlotScatter(labelId, values, xscale);
    }

    public static void plotScatter(String labelId, float[] values, double xscale, double xstart) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart);
    }

    public static void plotScatter(String labelId, float[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart, flags);
    }

    public static void plotScatter(String labelId, float[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotScatter(String var0, float[] var1);

    private static native void nPlotScatter(String var0, float[] var1, double var2);

    private static native void nPlotScatter(String var0, float[] var1, double var2, double var4);

    private static native void nPlotScatter(String var0, float[] var1, double var2, double var4, int var6);

    private static native void nPlotScatter(String var0, float[] var1, double var2, double var4, int var6, int var7);

    public static void plotScatter(String labelId, double[] values) {
        ImPlot.nPlotScatter(labelId, values);
    }

    public static void plotScatter(String labelId, double[] values, double xscale) {
        ImPlot.nPlotScatter(labelId, values, xscale);
    }

    public static void plotScatter(String labelId, double[] values, double xscale, double xstart) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart);
    }

    public static void plotScatter(String labelId, double[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart, flags);
    }

    public static void plotScatter(String labelId, double[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotScatter(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotScatter(String var0, double[] var1);

    private static native void nPlotScatter(String var0, double[] var1, double var2);

    private static native void nPlotScatter(String var0, double[] var1, double var2, double var4);

    private static native void nPlotScatter(String var0, double[] var1, double var2, double var4, int var6);

    private static native void nPlotScatter(String var0, double[] var1, double var2, double var4, int var6, int var7);

    public static void plotScatterV(String labelId, short[] values, int count) {
        ImPlot.nPlotScatterV(labelId, values, count);
    }

    public static void plotScatterV(String labelId, short[] values, int count, double xscale) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale);
    }

    public static void plotScatterV(String labelId, short[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart);
    }

    public static void plotScatterV(String labelId, short[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotScatterV(String labelId, short[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotScatterV(String var0, short[] var1, int var2);

    private static native void nPlotScatterV(String var0, short[] var1, int var2, double var3);

    private static native void nPlotScatterV(String var0, short[] var1, int var2, double var3, double var5);

    private static native void nPlotScatterV(String var0, short[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotScatterV(String var0, short[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotScatterV(String labelId, int[] values, int count) {
        ImPlot.nPlotScatterV(labelId, values, count);
    }

    public static void plotScatterV(String labelId, int[] values, int count, double xscale) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale);
    }

    public static void plotScatterV(String labelId, int[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart);
    }

    public static void plotScatterV(String labelId, int[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotScatterV(String labelId, int[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotScatterV(String var0, int[] var1, int var2);

    private static native void nPlotScatterV(String var0, int[] var1, int var2, double var3);

    private static native void nPlotScatterV(String var0, int[] var1, int var2, double var3, double var5);

    private static native void nPlotScatterV(String var0, int[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotScatterV(String var0, int[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotScatterV(String labelId, long[] values, int count) {
        ImPlot.nPlotScatterV(labelId, values, count);
    }

    public static void plotScatterV(String labelId, long[] values, int count, double xscale) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale);
    }

    public static void plotScatterV(String labelId, long[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart);
    }

    public static void plotScatterV(String labelId, long[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotScatterV(String labelId, long[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotScatterV(String var0, long[] var1, int var2);

    private static native void nPlotScatterV(String var0, long[] var1, int var2, double var3);

    private static native void nPlotScatterV(String var0, long[] var1, int var2, double var3, double var5);

    private static native void nPlotScatterV(String var0, long[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotScatterV(String var0, long[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotScatterV(String labelId, float[] values, int count) {
        ImPlot.nPlotScatterV(labelId, values, count);
    }

    public static void plotScatterV(String labelId, float[] values, int count, double xscale) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale);
    }

    public static void plotScatterV(String labelId, float[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart);
    }

    public static void plotScatterV(String labelId, float[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotScatterV(String labelId, float[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotScatterV(String var0, float[] var1, int var2);

    private static native void nPlotScatterV(String var0, float[] var1, int var2, double var3);

    private static native void nPlotScatterV(String var0, float[] var1, int var2, double var3, double var5);

    private static native void nPlotScatterV(String var0, float[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotScatterV(String var0, float[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotScatterV(String labelId, double[] values, int count) {
        ImPlot.nPlotScatterV(labelId, values, count);
    }

    public static void plotScatterV(String labelId, double[] values, int count, double xscale) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale);
    }

    public static void plotScatterV(String labelId, double[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart);
    }

    public static void plotScatterV(String labelId, double[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotScatterV(String labelId, double[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotScatterV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotScatterV(String var0, double[] var1, int var2);

    private static native void nPlotScatterV(String var0, double[] var1, int var2, double var3);

    private static native void nPlotScatterV(String var0, double[] var1, int var2, double var3, double var5);

    private static native void nPlotScatterV(String var0, double[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotScatterV(String var0, double[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotScatter(String labelId, short[] xs, short[] ys) {
        ImPlot.nPlotScatter(labelId, xs, ys);
    }

    public static void plotScatter(String labelId, short[] xs, short[] ys, int flags) {
        ImPlot.nPlotScatter(labelId, xs, ys, flags);
    }

    private static native void nPlotScatter(String var0, short[] var1, short[] var2);

    private static native void nPlotScatter(String var0, short[] var1, short[] var2, int var3);

    public static void plotScatter(String labelId, int[] xs, int[] ys) {
        ImPlot.nPlotScatter(labelId, xs, ys);
    }

    public static void plotScatter(String labelId, int[] xs, int[] ys, int flags) {
        ImPlot.nPlotScatter(labelId, xs, ys, flags);
    }

    private static native void nPlotScatter(String var0, int[] var1, int[] var2);

    private static native void nPlotScatter(String var0, int[] var1, int[] var2, int var3);

    public static void plotScatter(String labelId, long[] xs, long[] ys) {
        ImPlot.nPlotScatter(labelId, xs, ys);
    }

    public static void plotScatter(String labelId, long[] xs, long[] ys, int flags) {
        ImPlot.nPlotScatter(labelId, xs, ys, flags);
    }

    private static native void nPlotScatter(String var0, long[] var1, long[] var2);

    private static native void nPlotScatter(String var0, long[] var1, long[] var2, int var3);

    public static void plotScatter(String labelId, float[] xs, float[] ys) {
        ImPlot.nPlotScatter(labelId, xs, ys);
    }

    public static void plotScatter(String labelId, float[] xs, float[] ys, int flags) {
        ImPlot.nPlotScatter(labelId, xs, ys, flags);
    }

    private static native void nPlotScatter(String var0, float[] var1, float[] var2);

    private static native void nPlotScatter(String var0, float[] var1, float[] var2, int var3);

    public static void plotScatter(String labelId, double[] xs, double[] ys) {
        ImPlot.nPlotScatter(labelId, xs, ys);
    }

    public static void plotScatter(String labelId, double[] xs, double[] ys, int flags) {
        ImPlot.nPlotScatter(labelId, xs, ys, flags);
    }

    private static native void nPlotScatter(String var0, double[] var1, double[] var2);

    private static native void nPlotScatter(String var0, double[] var1, double[] var2, int var3);

    public static void plotScatterV(String labelId, short[] xs, short[] ys, int count) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count);
    }

    public static void plotScatterV(String labelId, short[] xs, short[] ys, int count, int flags) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count, flags);
    }

    public static void plotScatterV(String labelId, short[] xs, short[] ys, int count, int flags, int offset) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotScatterV(String var0, short[] var1, short[] var2, int var3);

    private static native void nPlotScatterV(String var0, short[] var1, short[] var2, int var3, int var4);

    private static native void nPlotScatterV(String var0, short[] var1, short[] var2, int var3, int var4, int var5);

    public static void plotScatterV(String labelId, int[] xs, int[] ys, int count) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count);
    }

    public static void plotScatterV(String labelId, int[] xs, int[] ys, int count, int flags) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count, flags);
    }

    public static void plotScatterV(String labelId, int[] xs, int[] ys, int count, int flags, int offset) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotScatterV(String var0, int[] var1, int[] var2, int var3);

    private static native void nPlotScatterV(String var0, int[] var1, int[] var2, int var3, int var4);

    private static native void nPlotScatterV(String var0, int[] var1, int[] var2, int var3, int var4, int var5);

    public static void plotScatterV(String labelId, long[] xs, long[] ys, int count) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count);
    }

    public static void plotScatterV(String labelId, long[] xs, long[] ys, int count, int flags) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count, flags);
    }

    public static void plotScatterV(String labelId, long[] xs, long[] ys, int count, int flags, int offset) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotScatterV(String var0, long[] var1, long[] var2, int var3);

    private static native void nPlotScatterV(String var0, long[] var1, long[] var2, int var3, int var4);

    private static native void nPlotScatterV(String var0, long[] var1, long[] var2, int var3, int var4, int var5);

    public static void plotScatterV(String labelId, float[] xs, float[] ys, int count) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count);
    }

    public static void plotScatterV(String labelId, float[] xs, float[] ys, int count, int flags) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count, flags);
    }

    public static void plotScatterV(String labelId, float[] xs, float[] ys, int count, int flags, int offset) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotScatterV(String var0, float[] var1, float[] var2, int var3);

    private static native void nPlotScatterV(String var0, float[] var1, float[] var2, int var3, int var4);

    private static native void nPlotScatterV(String var0, float[] var1, float[] var2, int var3, int var4, int var5);

    public static void plotScatterV(String labelId, double[] xs, double[] ys, int count) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count);
    }

    public static void plotScatterV(String labelId, double[] xs, double[] ys, int count, int flags) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count, flags);
    }

    public static void plotScatterV(String labelId, double[] xs, double[] ys, int count, int flags, int offset) {
        ImPlot.nPlotScatterV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotScatterV(String var0, double[] var1, double[] var2, int var3);

    private static native void nPlotScatterV(String var0, double[] var1, double[] var2, int var3, int var4);

    private static native void nPlotScatterV(String var0, double[] var1, double[] var2, int var3, int var4, int var5);

    public static void plotStairs(String labelId, short[] values) {
        ImPlot.nPlotStairs(labelId, values);
    }

    public static void plotStairs(String labelId, short[] values, double xscale) {
        ImPlot.nPlotStairs(labelId, values, xscale);
    }

    public static void plotStairs(String labelId, short[] values, double xscale, double xstart) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart);
    }

    public static void plotStairs(String labelId, short[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart, flags);
    }

    public static void plotStairs(String labelId, short[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotStairs(String var0, short[] var1);

    private static native void nPlotStairs(String var0, short[] var1, double var2);

    private static native void nPlotStairs(String var0, short[] var1, double var2, double var4);

    private static native void nPlotStairs(String var0, short[] var1, double var2, double var4, int var6);

    private static native void nPlotStairs(String var0, short[] var1, double var2, double var4, int var6, int var7);

    public static void plotStairs(String labelId, int[] values) {
        ImPlot.nPlotStairs(labelId, values);
    }

    public static void plotStairs(String labelId, int[] values, double xscale) {
        ImPlot.nPlotStairs(labelId, values, xscale);
    }

    public static void plotStairs(String labelId, int[] values, double xscale, double xstart) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart);
    }

    public static void plotStairs(String labelId, int[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart, flags);
    }

    public static void plotStairs(String labelId, int[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotStairs(String var0, int[] var1);

    private static native void nPlotStairs(String var0, int[] var1, double var2);

    private static native void nPlotStairs(String var0, int[] var1, double var2, double var4);

    private static native void nPlotStairs(String var0, int[] var1, double var2, double var4, int var6);

    private static native void nPlotStairs(String var0, int[] var1, double var2, double var4, int var6, int var7);

    public static void plotStairs(String labelId, long[] values) {
        ImPlot.nPlotStairs(labelId, values);
    }

    public static void plotStairs(String labelId, long[] values, double xscale) {
        ImPlot.nPlotStairs(labelId, values, xscale);
    }

    public static void plotStairs(String labelId, long[] values, double xscale, double xstart) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart);
    }

    public static void plotStairs(String labelId, long[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart, flags);
    }

    public static void plotStairs(String labelId, long[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotStairs(String var0, long[] var1);

    private static native void nPlotStairs(String var0, long[] var1, double var2);

    private static native void nPlotStairs(String var0, long[] var1, double var2, double var4);

    private static native void nPlotStairs(String var0, long[] var1, double var2, double var4, int var6);

    private static native void nPlotStairs(String var0, long[] var1, double var2, double var4, int var6, int var7);

    public static void plotStairs(String labelId, float[] values) {
        ImPlot.nPlotStairs(labelId, values);
    }

    public static void plotStairs(String labelId, float[] values, double xscale) {
        ImPlot.nPlotStairs(labelId, values, xscale);
    }

    public static void plotStairs(String labelId, float[] values, double xscale, double xstart) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart);
    }

    public static void plotStairs(String labelId, float[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart, flags);
    }

    public static void plotStairs(String labelId, float[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotStairs(String var0, float[] var1);

    private static native void nPlotStairs(String var0, float[] var1, double var2);

    private static native void nPlotStairs(String var0, float[] var1, double var2, double var4);

    private static native void nPlotStairs(String var0, float[] var1, double var2, double var4, int var6);

    private static native void nPlotStairs(String var0, float[] var1, double var2, double var4, int var6, int var7);

    public static void plotStairs(String labelId, double[] values) {
        ImPlot.nPlotStairs(labelId, values);
    }

    public static void plotStairs(String labelId, double[] values, double xscale) {
        ImPlot.nPlotStairs(labelId, values, xscale);
    }

    public static void plotStairs(String labelId, double[] values, double xscale, double xstart) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart);
    }

    public static void plotStairs(String labelId, double[] values, double xscale, double xstart, int flags) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart, flags);
    }

    public static void plotStairs(String labelId, double[] values, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStairs(labelId, values, xscale, xstart, flags, offset);
    }

    private static native void nPlotStairs(String var0, double[] var1);

    private static native void nPlotStairs(String var0, double[] var1, double var2);

    private static native void nPlotStairs(String var0, double[] var1, double var2, double var4);

    private static native void nPlotStairs(String var0, double[] var1, double var2, double var4, int var6);

    private static native void nPlotStairs(String var0, double[] var1, double var2, double var4, int var6, int var7);

    public static void plotStairsV(String labelId, short[] values, int count) {
        ImPlot.nPlotStairsV(labelId, values, count);
    }

    public static void plotStairsV(String labelId, short[] values, int count, double xscale) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale);
    }

    public static void plotStairsV(String labelId, short[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart);
    }

    public static void plotStairsV(String labelId, short[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotStairsV(String labelId, short[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotStairsV(String var0, short[] var1, int var2);

    private static native void nPlotStairsV(String var0, short[] var1, int var2, double var3);

    private static native void nPlotStairsV(String var0, short[] var1, int var2, double var3, double var5);

    private static native void nPlotStairsV(String var0, short[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotStairsV(String var0, short[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotStairsV(String labelId, int[] values, int count) {
        ImPlot.nPlotStairsV(labelId, values, count);
    }

    public static void plotStairsV(String labelId, int[] values, int count, double xscale) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale);
    }

    public static void plotStairsV(String labelId, int[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart);
    }

    public static void plotStairsV(String labelId, int[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotStairsV(String labelId, int[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotStairsV(String var0, int[] var1, int var2);

    private static native void nPlotStairsV(String var0, int[] var1, int var2, double var3);

    private static native void nPlotStairsV(String var0, int[] var1, int var2, double var3, double var5);

    private static native void nPlotStairsV(String var0, int[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotStairsV(String var0, int[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotStairsV(String labelId, long[] values, int count) {
        ImPlot.nPlotStairsV(labelId, values, count);
    }

    public static void plotStairsV(String labelId, long[] values, int count, double xscale) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale);
    }

    public static void plotStairsV(String labelId, long[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart);
    }

    public static void plotStairsV(String labelId, long[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotStairsV(String labelId, long[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotStairsV(String var0, long[] var1, int var2);

    private static native void nPlotStairsV(String var0, long[] var1, int var2, double var3);

    private static native void nPlotStairsV(String var0, long[] var1, int var2, double var3, double var5);

    private static native void nPlotStairsV(String var0, long[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotStairsV(String var0, long[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotStairsV(String labelId, float[] values, int count) {
        ImPlot.nPlotStairsV(labelId, values, count);
    }

    public static void plotStairsV(String labelId, float[] values, int count, double xscale) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale);
    }

    public static void plotStairsV(String labelId, float[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart);
    }

    public static void plotStairsV(String labelId, float[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotStairsV(String labelId, float[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotStairsV(String var0, float[] var1, int var2);

    private static native void nPlotStairsV(String var0, float[] var1, int var2, double var3);

    private static native void nPlotStairsV(String var0, float[] var1, int var2, double var3, double var5);

    private static native void nPlotStairsV(String var0, float[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotStairsV(String var0, float[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotStairsV(String labelId, double[] values, int count) {
        ImPlot.nPlotStairsV(labelId, values, count);
    }

    public static void plotStairsV(String labelId, double[] values, int count, double xscale) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale);
    }

    public static void plotStairsV(String labelId, double[] values, int count, double xscale, double xstart) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart);
    }

    public static void plotStairsV(String labelId, double[] values, int count, double xscale, double xstart, int flags) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart, flags);
    }

    public static void plotStairsV(String labelId, double[] values, int count, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStairsV(labelId, values, count, xscale, xstart, flags, offset);
    }

    private static native void nPlotStairsV(String var0, double[] var1, int var2);

    private static native void nPlotStairsV(String var0, double[] var1, int var2, double var3);

    private static native void nPlotStairsV(String var0, double[] var1, int var2, double var3, double var5);

    private static native void nPlotStairsV(String var0, double[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotStairsV(String var0, double[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotStairs(String labelId, short[] xs, short[] ys) {
        ImPlot.nPlotStairs(labelId, xs, ys);
    }

    public static void plotStairs(String labelId, short[] xs, short[] ys, int flags) {
        ImPlot.nPlotStairs(labelId, xs, ys, flags);
    }

    private static native void nPlotStairs(String var0, short[] var1, short[] var2);

    private static native void nPlotStairs(String var0, short[] var1, short[] var2, int var3);

    public static void plotStairs(String labelId, int[] xs, int[] ys) {
        ImPlot.nPlotStairs(labelId, xs, ys);
    }

    public static void plotStairs(String labelId, int[] xs, int[] ys, int flags) {
        ImPlot.nPlotStairs(labelId, xs, ys, flags);
    }

    private static native void nPlotStairs(String var0, int[] var1, int[] var2);

    private static native void nPlotStairs(String var0, int[] var1, int[] var2, int var3);

    public static void plotStairs(String labelId, long[] xs, long[] ys) {
        ImPlot.nPlotStairs(labelId, xs, ys);
    }

    public static void plotStairs(String labelId, long[] xs, long[] ys, int flags) {
        ImPlot.nPlotStairs(labelId, xs, ys, flags);
    }

    private static native void nPlotStairs(String var0, long[] var1, long[] var2);

    private static native void nPlotStairs(String var0, long[] var1, long[] var2, int var3);

    public static void plotStairs(String labelId, float[] xs, float[] ys) {
        ImPlot.nPlotStairs(labelId, xs, ys);
    }

    public static void plotStairs(String labelId, float[] xs, float[] ys, int flags) {
        ImPlot.nPlotStairs(labelId, xs, ys, flags);
    }

    private static native void nPlotStairs(String var0, float[] var1, float[] var2);

    private static native void nPlotStairs(String var0, float[] var1, float[] var2, int var3);

    public static void plotStairs(String labelId, double[] xs, double[] ys) {
        ImPlot.nPlotStairs(labelId, xs, ys);
    }

    public static void plotStairs(String labelId, double[] xs, double[] ys, int flags) {
        ImPlot.nPlotStairs(labelId, xs, ys, flags);
    }

    private static native void nPlotStairs(String var0, double[] var1, double[] var2);

    private static native void nPlotStairs(String var0, double[] var1, double[] var2, int var3);

    public static void plotStairsV(String labelId, short[] xs, short[] ys, int count) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count);
    }

    public static void plotStairsV(String labelId, short[] xs, short[] ys, int count, int flags) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count, flags);
    }

    public static void plotStairsV(String labelId, short[] xs, short[] ys, int count, int flags, int offset) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotStairsV(String var0, short[] var1, short[] var2, int var3);

    private static native void nPlotStairsV(String var0, short[] var1, short[] var2, int var3, int var4);

    private static native void nPlotStairsV(String var0, short[] var1, short[] var2, int var3, int var4, int var5);

    public static void plotStairsV(String labelId, int[] xs, int[] ys, int count) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count);
    }

    public static void plotStairsV(String labelId, int[] xs, int[] ys, int count, int flags) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count, flags);
    }

    public static void plotStairsV(String labelId, int[] xs, int[] ys, int count, int flags, int offset) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotStairsV(String var0, int[] var1, int[] var2, int var3);

    private static native void nPlotStairsV(String var0, int[] var1, int[] var2, int var3, int var4);

    private static native void nPlotStairsV(String var0, int[] var1, int[] var2, int var3, int var4, int var5);

    public static void plotStairsV(String labelId, long[] xs, long[] ys, int count) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count);
    }

    public static void plotStairsV(String labelId, long[] xs, long[] ys, int count, int flags) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count, flags);
    }

    public static void plotStairsV(String labelId, long[] xs, long[] ys, int count, int flags, int offset) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotStairsV(String var0, long[] var1, long[] var2, int var3);

    private static native void nPlotStairsV(String var0, long[] var1, long[] var2, int var3, int var4);

    private static native void nPlotStairsV(String var0, long[] var1, long[] var2, int var3, int var4, int var5);

    public static void plotStairsV(String labelId, float[] xs, float[] ys, int count) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count);
    }

    public static void plotStairsV(String labelId, float[] xs, float[] ys, int count, int flags) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count, flags);
    }

    public static void plotStairsV(String labelId, float[] xs, float[] ys, int count, int flags, int offset) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotStairsV(String var0, float[] var1, float[] var2, int var3);

    private static native void nPlotStairsV(String var0, float[] var1, float[] var2, int var3, int var4);

    private static native void nPlotStairsV(String var0, float[] var1, float[] var2, int var3, int var4, int var5);

    public static void plotStairsV(String labelId, double[] xs, double[] ys, int count) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count);
    }

    public static void plotStairsV(String labelId, double[] xs, double[] ys, int count, int flags) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count, flags);
    }

    public static void plotStairsV(String labelId, double[] xs, double[] ys, int count, int flags, int offset) {
        ImPlot.nPlotStairsV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotStairsV(String var0, double[] var1, double[] var2, int var3);

    private static native void nPlotStairsV(String var0, double[] var1, double[] var2, int var3, int var4);

    private static native void nPlotStairsV(String var0, double[] var1, double[] var2, int var3, int var4, int var5);

    public static void plotShaded(String labelId, short[] values) {
        ImPlot.nPlotShaded(labelId, values);
    }

    public static void plotShaded(String labelId, short[] values, double yRef) {
        ImPlot.nPlotShaded(labelId, values, yRef);
    }

    public static void plotShaded(String labelId, short[] values, double yRef, double xscale) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale);
    }

    public static void plotShaded(String labelId, short[] values, double yRef, double xscale, double xstart) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart);
    }

    public static void plotShaded(String labelId, short[] values, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart, flags);
    }

    public static void plotShaded(String labelId, short[] values, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotShaded(String var0, short[] var1);

    private static native void nPlotShaded(String var0, short[] var1, double var2);

    private static native void nPlotShaded(String var0, short[] var1, double var2, double var4);

    private static native void nPlotShaded(String var0, short[] var1, double var2, double var4, double var6);

    private static native void nPlotShaded(String var0, short[] var1, double var2, double var4, double var6, int var8);

    private static native void nPlotShaded(String var0, short[] var1, double var2, double var4, double var6, int var8, int var9);

    public static void plotShaded(String labelId, int[] values) {
        ImPlot.nPlotShaded(labelId, values);
    }

    public static void plotShaded(String labelId, int[] values, double yRef) {
        ImPlot.nPlotShaded(labelId, values, yRef);
    }

    public static void plotShaded(String labelId, int[] values, double yRef, double xscale) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale);
    }

    public static void plotShaded(String labelId, int[] values, double yRef, double xscale, double xstart) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart);
    }

    public static void plotShaded(String labelId, int[] values, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart, flags);
    }

    public static void plotShaded(String labelId, int[] values, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotShaded(String var0, int[] var1);

    private static native void nPlotShaded(String var0, int[] var1, double var2);

    private static native void nPlotShaded(String var0, int[] var1, double var2, double var4);

    private static native void nPlotShaded(String var0, int[] var1, double var2, double var4, double var6);

    private static native void nPlotShaded(String var0, int[] var1, double var2, double var4, double var6, int var8);

    private static native void nPlotShaded(String var0, int[] var1, double var2, double var4, double var6, int var8, int var9);

    public static void plotShaded(String labelId, long[] values) {
        ImPlot.nPlotShaded(labelId, values);
    }

    public static void plotShaded(String labelId, long[] values, double yRef) {
        ImPlot.nPlotShaded(labelId, values, yRef);
    }

    public static void plotShaded(String labelId, long[] values, double yRef, double xscale) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale);
    }

    public static void plotShaded(String labelId, long[] values, double yRef, double xscale, double xstart) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart);
    }

    public static void plotShaded(String labelId, long[] values, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart, flags);
    }

    public static void plotShaded(String labelId, long[] values, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotShaded(String var0, long[] var1);

    private static native void nPlotShaded(String var0, long[] var1, double var2);

    private static native void nPlotShaded(String var0, long[] var1, double var2, double var4);

    private static native void nPlotShaded(String var0, long[] var1, double var2, double var4, double var6);

    private static native void nPlotShaded(String var0, long[] var1, double var2, double var4, double var6, int var8);

    private static native void nPlotShaded(String var0, long[] var1, double var2, double var4, double var6, int var8, int var9);

    public static void plotShaded(String labelId, float[] values) {
        ImPlot.nPlotShaded(labelId, values);
    }

    public static void plotShaded(String labelId, float[] values, double yRef) {
        ImPlot.nPlotShaded(labelId, values, yRef);
    }

    public static void plotShaded(String labelId, float[] values, double yRef, double xscale) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale);
    }

    public static void plotShaded(String labelId, float[] values, double yRef, double xscale, double xstart) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart);
    }

    public static void plotShaded(String labelId, float[] values, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart, flags);
    }

    public static void plotShaded(String labelId, float[] values, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotShaded(String var0, float[] var1);

    private static native void nPlotShaded(String var0, float[] var1, double var2);

    private static native void nPlotShaded(String var0, float[] var1, double var2, double var4);

    private static native void nPlotShaded(String var0, float[] var1, double var2, double var4, double var6);

    private static native void nPlotShaded(String var0, float[] var1, double var2, double var4, double var6, int var8);

    private static native void nPlotShaded(String var0, float[] var1, double var2, double var4, double var6, int var8, int var9);

    public static void plotShaded(String labelId, double[] values) {
        ImPlot.nPlotShaded(labelId, values);
    }

    public static void plotShaded(String labelId, double[] values, double yRef) {
        ImPlot.nPlotShaded(labelId, values, yRef);
    }

    public static void plotShaded(String labelId, double[] values, double yRef, double xscale) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale);
    }

    public static void plotShaded(String labelId, double[] values, double yRef, double xscale, double xstart) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart);
    }

    public static void plotShaded(String labelId, double[] values, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart, flags);
    }

    public static void plotShaded(String labelId, double[] values, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotShaded(labelId, values, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotShaded(String var0, double[] var1);

    private static native void nPlotShaded(String var0, double[] var1, double var2);

    private static native void nPlotShaded(String var0, double[] var1, double var2, double var4);

    private static native void nPlotShaded(String var0, double[] var1, double var2, double var4, double var6);

    private static native void nPlotShaded(String var0, double[] var1, double var2, double var4, double var6, int var8);

    private static native void nPlotShaded(String var0, double[] var1, double var2, double var4, double var6, int var8, int var9);

    public static void plotShadedV(String labelId, short[] values, int count) {
        ImPlot.nPlotShadedV(labelId, values, count);
    }

    public static void plotShadedV(String labelId, short[] values, int count, double yRef) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef);
    }

    public static void plotShadedV(String labelId, short[] values, int count, double yRef, double xscale) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale);
    }

    public static void plotShadedV(String labelId, short[] values, int count, double yRef, double xscale, double xstart) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart);
    }

    public static void plotShadedV(String labelId, short[] values, int count, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart, flags);
    }

    public static void plotShadedV(String labelId, short[] values, int count, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotShadedV(String var0, short[] var1, int var2);

    private static native void nPlotShadedV(String var0, short[] var1, int var2, double var3);

    private static native void nPlotShadedV(String var0, short[] var1, int var2, double var3, double var5);

    private static native void nPlotShadedV(String var0, short[] var1, int var2, double var3, double var5, double var7);

    private static native void nPlotShadedV(String var0, short[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native void nPlotShadedV(String var0, short[] var1, int var2, double var3, double var5, double var7, int var9, int var10);

    public static void plotShadedV(String labelId, int[] values, int count) {
        ImPlot.nPlotShadedV(labelId, values, count);
    }

    public static void plotShadedV(String labelId, int[] values, int count, double yRef) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef);
    }

    public static void plotShadedV(String labelId, int[] values, int count, double yRef, double xscale) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale);
    }

    public static void plotShadedV(String labelId, int[] values, int count, double yRef, double xscale, double xstart) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart);
    }

    public static void plotShadedV(String labelId, int[] values, int count, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart, flags);
    }

    public static void plotShadedV(String labelId, int[] values, int count, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotShadedV(String var0, int[] var1, int var2);

    private static native void nPlotShadedV(String var0, int[] var1, int var2, double var3);

    private static native void nPlotShadedV(String var0, int[] var1, int var2, double var3, double var5);

    private static native void nPlotShadedV(String var0, int[] var1, int var2, double var3, double var5, double var7);

    private static native void nPlotShadedV(String var0, int[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native void nPlotShadedV(String var0, int[] var1, int var2, double var3, double var5, double var7, int var9, int var10);

    public static void plotShadedV(String labelId, long[] values, int count) {
        ImPlot.nPlotShadedV(labelId, values, count);
    }

    public static void plotShadedV(String labelId, long[] values, int count, double yRef) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef);
    }

    public static void plotShadedV(String labelId, long[] values, int count, double yRef, double xscale) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale);
    }

    public static void plotShadedV(String labelId, long[] values, int count, double yRef, double xscale, double xstart) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart);
    }

    public static void plotShadedV(String labelId, long[] values, int count, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart, flags);
    }

    public static void plotShadedV(String labelId, long[] values, int count, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotShadedV(String var0, long[] var1, int var2);

    private static native void nPlotShadedV(String var0, long[] var1, int var2, double var3);

    private static native void nPlotShadedV(String var0, long[] var1, int var2, double var3, double var5);

    private static native void nPlotShadedV(String var0, long[] var1, int var2, double var3, double var5, double var7);

    private static native void nPlotShadedV(String var0, long[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native void nPlotShadedV(String var0, long[] var1, int var2, double var3, double var5, double var7, int var9, int var10);

    public static void plotShadedV(String labelId, float[] values, int count) {
        ImPlot.nPlotShadedV(labelId, values, count);
    }

    public static void plotShadedV(String labelId, float[] values, int count, double yRef) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef);
    }

    public static void plotShadedV(String labelId, float[] values, int count, double yRef, double xscale) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale);
    }

    public static void plotShadedV(String labelId, float[] values, int count, double yRef, double xscale, double xstart) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart);
    }

    public static void plotShadedV(String labelId, float[] values, int count, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart, flags);
    }

    public static void plotShadedV(String labelId, float[] values, int count, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotShadedV(String var0, float[] var1, int var2);

    private static native void nPlotShadedV(String var0, float[] var1, int var2, double var3);

    private static native void nPlotShadedV(String var0, float[] var1, int var2, double var3, double var5);

    private static native void nPlotShadedV(String var0, float[] var1, int var2, double var3, double var5, double var7);

    private static native void nPlotShadedV(String var0, float[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native void nPlotShadedV(String var0, float[] var1, int var2, double var3, double var5, double var7, int var9, int var10);

    public static void plotShadedV(String labelId, double[] values, int count) {
        ImPlot.nPlotShadedV(labelId, values, count);
    }

    public static void plotShadedV(String labelId, double[] values, int count, double yRef) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef);
    }

    public static void plotShadedV(String labelId, double[] values, int count, double yRef, double xscale) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale);
    }

    public static void plotShadedV(String labelId, double[] values, int count, double yRef, double xscale, double xstart) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart);
    }

    public static void plotShadedV(String labelId, double[] values, int count, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart, flags);
    }

    public static void plotShadedV(String labelId, double[] values, int count, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, values, count, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotShadedV(String var0, double[] var1, int var2);

    private static native void nPlotShadedV(String var0, double[] var1, int var2, double var3);

    private static native void nPlotShadedV(String var0, double[] var1, int var2, double var3, double var5);

    private static native void nPlotShadedV(String var0, double[] var1, int var2, double var3, double var5, double var7);

    private static native void nPlotShadedV(String var0, double[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native void nPlotShadedV(String var0, double[] var1, int var2, double var3, double var5, double var7, int var9, int var10);

    public static void plotShaded(String labelId, short[] xs, short[] ys) {
        ImPlot.nPlotShaded(labelId, xs, ys);
    }

    public static void plotShaded(String labelId, short[] xs, short[] ys, double yRef) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef);
    }

    public static void plotShaded(String labelId, short[] xs, short[] ys, double yRef, int flags) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef, flags);
    }

    public static void plotShaded(String labelId, short[] xs, short[] ys, double yRef, int flags, int offset) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef, flags, offset);
    }

    private static native void nPlotShaded(String var0, short[] var1, short[] var2);

    private static native void nPlotShaded(String var0, short[] var1, short[] var2, double var3);

    private static native void nPlotShaded(String var0, short[] var1, short[] var2, double var3, int var5);

    private static native void nPlotShaded(String var0, short[] var1, short[] var2, double var3, int var5, int var6);

    public static void plotShaded(String labelId, int[] xs, int[] ys) {
        ImPlot.nPlotShaded(labelId, xs, ys);
    }

    public static void plotShaded(String labelId, int[] xs, int[] ys, double yRef) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef);
    }

    public static void plotShaded(String labelId, int[] xs, int[] ys, double yRef, int flags) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef, flags);
    }

    public static void plotShaded(String labelId, int[] xs, int[] ys, double yRef, int flags, int offset) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef, flags, offset);
    }

    private static native void nPlotShaded(String var0, int[] var1, int[] var2);

    private static native void nPlotShaded(String var0, int[] var1, int[] var2, double var3);

    private static native void nPlotShaded(String var0, int[] var1, int[] var2, double var3, int var5);

    private static native void nPlotShaded(String var0, int[] var1, int[] var2, double var3, int var5, int var6);

    public static void plotShaded(String labelId, long[] xs, long[] ys) {
        ImPlot.nPlotShaded(labelId, xs, ys);
    }

    public static void plotShaded(String labelId, long[] xs, long[] ys, double yRef) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef);
    }

    public static void plotShaded(String labelId, long[] xs, long[] ys, double yRef, int flags) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef, flags);
    }

    public static void plotShaded(String labelId, long[] xs, long[] ys, double yRef, int flags, int offset) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef, flags, offset);
    }

    private static native void nPlotShaded(String var0, long[] var1, long[] var2);

    private static native void nPlotShaded(String var0, long[] var1, long[] var2, double var3);

    private static native void nPlotShaded(String var0, long[] var1, long[] var2, double var3, int var5);

    private static native void nPlotShaded(String var0, long[] var1, long[] var2, double var3, int var5, int var6);

    public static void plotShaded(String labelId, float[] xs, float[] ys) {
        ImPlot.nPlotShaded(labelId, xs, ys);
    }

    public static void plotShaded(String labelId, float[] xs, float[] ys, double yRef) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef);
    }

    public static void plotShaded(String labelId, float[] xs, float[] ys, double yRef, int flags) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef, flags);
    }

    public static void plotShaded(String labelId, float[] xs, float[] ys, double yRef, int flags, int offset) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef, flags, offset);
    }

    private static native void nPlotShaded(String var0, float[] var1, float[] var2);

    private static native void nPlotShaded(String var0, float[] var1, float[] var2, double var3);

    private static native void nPlotShaded(String var0, float[] var1, float[] var2, double var3, int var5);

    private static native void nPlotShaded(String var0, float[] var1, float[] var2, double var3, int var5, int var6);

    public static void plotShaded(String labelId, double[] xs, double[] ys) {
        ImPlot.nPlotShaded(labelId, xs, ys);
    }

    public static void plotShaded(String labelId, double[] xs, double[] ys, double yRef) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef);
    }

    public static void plotShaded(String labelId, double[] xs, double[] ys, double yRef, int flags) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef, flags);
    }

    public static void plotShaded(String labelId, double[] xs, double[] ys, double yRef, int flags, int offset) {
        ImPlot.nPlotShaded(labelId, xs, ys, yRef, flags, offset);
    }

    private static native void nPlotShaded(String var0, double[] var1, double[] var2);

    private static native void nPlotShaded(String var0, double[] var1, double[] var2, double var3);

    private static native void nPlotShaded(String var0, double[] var1, double[] var2, double var3, int var5);

    private static native void nPlotShaded(String var0, double[] var1, double[] var2, double var3, int var5, int var6);

    public static void plotShadedV(String labelId, short[] xs, short[] ys, int count) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count);
    }

    public static void plotShadedV(String labelId, short[] xs, short[] ys, int count, double yRef) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef);
    }

    public static void plotShadedV(String labelId, short[] xs, short[] ys, int count, double yRef, int flags) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef, flags);
    }

    public static void plotShadedV(String labelId, short[] xs, short[] ys, int count, double yRef, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef, flags, offset);
    }

    private static native void nPlotShadedV(String var0, short[] var1, short[] var2, int var3);

    private static native void nPlotShadedV(String var0, short[] var1, short[] var2, int var3, double var4);

    private static native void nPlotShadedV(String var0, short[] var1, short[] var2, int var3, double var4, int var6);

    private static native void nPlotShadedV(String var0, short[] var1, short[] var2, int var3, double var4, int var6, int var7);

    public static void plotShadedV(String labelId, int[] xs, int[] ys, int count) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count);
    }

    public static void plotShadedV(String labelId, int[] xs, int[] ys, int count, double yRef) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef);
    }

    public static void plotShadedV(String labelId, int[] xs, int[] ys, int count, double yRef, int flags) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef, flags);
    }

    public static void plotShadedV(String labelId, int[] xs, int[] ys, int count, double yRef, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef, flags, offset);
    }

    private static native void nPlotShadedV(String var0, int[] var1, int[] var2, int var3);

    private static native void nPlotShadedV(String var0, int[] var1, int[] var2, int var3, double var4);

    private static native void nPlotShadedV(String var0, int[] var1, int[] var2, int var3, double var4, int var6);

    private static native void nPlotShadedV(String var0, int[] var1, int[] var2, int var3, double var4, int var6, int var7);

    public static void plotShadedV(String labelId, long[] xs, long[] ys, int count) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count);
    }

    public static void plotShadedV(String labelId, long[] xs, long[] ys, int count, double yRef) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef);
    }

    public static void plotShadedV(String labelId, long[] xs, long[] ys, int count, double yRef, int flags) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef, flags);
    }

    public static void plotShadedV(String labelId, long[] xs, long[] ys, int count, double yRef, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef, flags, offset);
    }

    private static native void nPlotShadedV(String var0, long[] var1, long[] var2, int var3);

    private static native void nPlotShadedV(String var0, long[] var1, long[] var2, int var3, double var4);

    private static native void nPlotShadedV(String var0, long[] var1, long[] var2, int var3, double var4, int var6);

    private static native void nPlotShadedV(String var0, long[] var1, long[] var2, int var3, double var4, int var6, int var7);

    public static void plotShadedV(String labelId, float[] xs, float[] ys, int count) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count);
    }

    public static void plotShadedV(String labelId, float[] xs, float[] ys, int count, double yRef) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef);
    }

    public static void plotShadedV(String labelId, float[] xs, float[] ys, int count, double yRef, int flags) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef, flags);
    }

    public static void plotShadedV(String labelId, float[] xs, float[] ys, int count, double yRef, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef, flags, offset);
    }

    private static native void nPlotShadedV(String var0, float[] var1, float[] var2, int var3);

    private static native void nPlotShadedV(String var0, float[] var1, float[] var2, int var3, double var4);

    private static native void nPlotShadedV(String var0, float[] var1, float[] var2, int var3, double var4, int var6);

    private static native void nPlotShadedV(String var0, float[] var1, float[] var2, int var3, double var4, int var6, int var7);

    public static void plotShadedV(String labelId, double[] xs, double[] ys, int count) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count);
    }

    public static void plotShadedV(String labelId, double[] xs, double[] ys, int count, double yRef) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef);
    }

    public static void plotShadedV(String labelId, double[] xs, double[] ys, int count, double yRef, int flags) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef, flags);
    }

    public static void plotShadedV(String labelId, double[] xs, double[] ys, int count, double yRef, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, xs, ys, count, yRef, flags, offset);
    }

    private static native void nPlotShadedV(String var0, double[] var1, double[] var2, int var3);

    private static native void nPlotShadedV(String var0, double[] var1, double[] var2, int var3, double var4);

    private static native void nPlotShadedV(String var0, double[] var1, double[] var2, int var3, double var4, int var6);

    private static native void nPlotShadedV(String var0, double[] var1, double[] var2, int var3, double var4, int var6, int var7);

    public static void plotShaded(String labelId, short[] xs, short[] ys1, short[] ys2) {
        ImPlot.nPlotShaded(labelId, xs, ys1, ys2);
    }

    public static void plotShaded(String labelId, short[] xs, short[] ys1, short[] ys2, int flags) {
        ImPlot.nPlotShaded(labelId, xs, ys1, ys2, flags);
    }

    private static native void nPlotShaded(String var0, short[] var1, short[] var2, short[] var3);

    private static native void nPlotShaded(String var0, short[] var1, short[] var2, short[] var3, int var4);

    public static void plotShaded(String labelId, int[] xs, int[] ys1, int[] ys2) {
        ImPlot.nPlotShaded(labelId, xs, ys1, ys2);
    }

    public static void plotShaded(String labelId, int[] xs, int[] ys1, int[] ys2, int flags) {
        ImPlot.nPlotShaded(labelId, xs, ys1, ys2, flags);
    }

    private static native void nPlotShaded(String var0, int[] var1, int[] var2, int[] var3);

    private static native void nPlotShaded(String var0, int[] var1, int[] var2, int[] var3, int var4);

    public static void plotShaded(String labelId, long[] xs, long[] ys1, long[] ys2) {
        ImPlot.nPlotShaded(labelId, xs, ys1, ys2);
    }

    public static void plotShaded(String labelId, long[] xs, long[] ys1, long[] ys2, int flags) {
        ImPlot.nPlotShaded(labelId, xs, ys1, ys2, flags);
    }

    private static native void nPlotShaded(String var0, long[] var1, long[] var2, long[] var3);

    private static native void nPlotShaded(String var0, long[] var1, long[] var2, long[] var3, int var4);

    public static void plotShaded(String labelId, float[] xs, float[] ys1, float[] ys2) {
        ImPlot.nPlotShaded(labelId, xs, ys1, ys2);
    }

    public static void plotShaded(String labelId, float[] xs, float[] ys1, float[] ys2, int flags) {
        ImPlot.nPlotShaded(labelId, xs, ys1, ys2, flags);
    }

    private static native void nPlotShaded(String var0, float[] var1, float[] var2, float[] var3);

    private static native void nPlotShaded(String var0, float[] var1, float[] var2, float[] var3, int var4);

    public static void plotShaded(String labelId, double[] xs, double[] ys1, double[] ys2) {
        ImPlot.nPlotShaded(labelId, xs, ys1, ys2);
    }

    public static void plotShaded(String labelId, double[] xs, double[] ys1, double[] ys2, int flags) {
        ImPlot.nPlotShaded(labelId, xs, ys1, ys2, flags);
    }

    private static native void nPlotShaded(String var0, double[] var1, double[] var2, double[] var3);

    private static native void nPlotShaded(String var0, double[] var1, double[] var2, double[] var3, int var4);

    public static void plotShadedV(String labelId, short[] xs, short[] ys1, short[] ys2, int count) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count);
    }

    public static void plotShadedV(String labelId, short[] xs, short[] ys1, short[] ys2, int count, int flags) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count, flags);
    }

    public static void plotShadedV(String labelId, short[] xs, short[] ys1, short[] ys2, int count, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count, flags, offset);
    }

    private static native void nPlotShadedV(String var0, short[] var1, short[] var2, short[] var3, int var4);

    private static native void nPlotShadedV(String var0, short[] var1, short[] var2, short[] var3, int var4, int var5);

    private static native void nPlotShadedV(String var0, short[] var1, short[] var2, short[] var3, int var4, int var5, int var6);

    public static void plotShadedV(String labelId, int[] xs, int[] ys1, int[] ys2, int count) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count);
    }

    public static void plotShadedV(String labelId, int[] xs, int[] ys1, int[] ys2, int count, int flags) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count, flags);
    }

    public static void plotShadedV(String labelId, int[] xs, int[] ys1, int[] ys2, int count, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count, flags, offset);
    }

    private static native void nPlotShadedV(String var0, int[] var1, int[] var2, int[] var3, int var4);

    private static native void nPlotShadedV(String var0, int[] var1, int[] var2, int[] var3, int var4, int var5);

    private static native void nPlotShadedV(String var0, int[] var1, int[] var2, int[] var3, int var4, int var5, int var6);

    public static void plotShadedV(String labelId, long[] xs, long[] ys1, long[] ys2, int count) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count);
    }

    public static void plotShadedV(String labelId, long[] xs, long[] ys1, long[] ys2, int count, int flags) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count, flags);
    }

    public static void plotShadedV(String labelId, long[] xs, long[] ys1, long[] ys2, int count, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count, flags, offset);
    }

    private static native void nPlotShadedV(String var0, long[] var1, long[] var2, long[] var3, int var4);

    private static native void nPlotShadedV(String var0, long[] var1, long[] var2, long[] var3, int var4, int var5);

    private static native void nPlotShadedV(String var0, long[] var1, long[] var2, long[] var3, int var4, int var5, int var6);

    public static void plotShadedV(String labelId, float[] xs, float[] ys1, float[] ys2, int count) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count);
    }

    public static void plotShadedV(String labelId, float[] xs, float[] ys1, float[] ys2, int count, int flags) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count, flags);
    }

    public static void plotShadedV(String labelId, float[] xs, float[] ys1, float[] ys2, int count, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count, flags, offset);
    }

    private static native void nPlotShadedV(String var0, float[] var1, float[] var2, float[] var3, int var4);

    private static native void nPlotShadedV(String var0, float[] var1, float[] var2, float[] var3, int var4, int var5);

    private static native void nPlotShadedV(String var0, float[] var1, float[] var2, float[] var3, int var4, int var5, int var6);

    public static void plotShadedV(String labelId, double[] xs, double[] ys1, double[] ys2, int count) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count);
    }

    public static void plotShadedV(String labelId, double[] xs, double[] ys1, double[] ys2, int count, int flags) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count, flags);
    }

    public static void plotShadedV(String labelId, double[] xs, double[] ys1, double[] ys2, int count, int flags, int offset) {
        ImPlot.nPlotShadedV(labelId, xs, ys1, ys2, count, flags, offset);
    }

    private static native void nPlotShadedV(String var0, double[] var1, double[] var2, double[] var3, int var4);

    private static native void nPlotShadedV(String var0, double[] var1, double[] var2, double[] var3, int var4, int var5);

    private static native void nPlotShadedV(String var0, double[] var1, double[] var2, double[] var3, int var4, int var5, int var6);

    public static void plotBars(String labelId, short[] values) {
        ImPlot.nPlotBars(labelId, values);
    }

    public static void plotBars(String labelId, short[] values, double barWidth) {
        ImPlot.nPlotBars(labelId, values, barWidth);
    }

    public static void plotBars(String labelId, short[] values, double barWidth, double xstart) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart);
    }

    public static void plotBars(String labelId, short[] values, double barWidth, double xstart, int flags) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart, flags);
    }

    public static void plotBars(String labelId, short[] values, double barWidth, double xstart, int flags, int offset) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart, flags, offset);
    }

    private static native void nPlotBars(String var0, short[] var1);

    private static native void nPlotBars(String var0, short[] var1, double var2);

    private static native void nPlotBars(String var0, short[] var1, double var2, double var4);

    private static native void nPlotBars(String var0, short[] var1, double var2, double var4, int var6);

    private static native void nPlotBars(String var0, short[] var1, double var2, double var4, int var6, int var7);

    public static void plotBars(String labelId, int[] values) {
        ImPlot.nPlotBars(labelId, values);
    }

    public static void plotBars(String labelId, int[] values, double barWidth) {
        ImPlot.nPlotBars(labelId, values, barWidth);
    }

    public static void plotBars(String labelId, int[] values, double barWidth, double xstart) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart);
    }

    public static void plotBars(String labelId, int[] values, double barWidth, double xstart, int flags) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart, flags);
    }

    public static void plotBars(String labelId, int[] values, double barWidth, double xstart, int flags, int offset) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart, flags, offset);
    }

    private static native void nPlotBars(String var0, int[] var1);

    private static native void nPlotBars(String var0, int[] var1, double var2);

    private static native void nPlotBars(String var0, int[] var1, double var2, double var4);

    private static native void nPlotBars(String var0, int[] var1, double var2, double var4, int var6);

    private static native void nPlotBars(String var0, int[] var1, double var2, double var4, int var6, int var7);

    public static void plotBars(String labelId, long[] values) {
        ImPlot.nPlotBars(labelId, values);
    }

    public static void plotBars(String labelId, long[] values, double barWidth) {
        ImPlot.nPlotBars(labelId, values, barWidth);
    }

    public static void plotBars(String labelId, long[] values, double barWidth, double xstart) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart);
    }

    public static void plotBars(String labelId, long[] values, double barWidth, double xstart, int flags) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart, flags);
    }

    public static void plotBars(String labelId, long[] values, double barWidth, double xstart, int flags, int offset) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart, flags, offset);
    }

    private static native void nPlotBars(String var0, long[] var1);

    private static native void nPlotBars(String var0, long[] var1, double var2);

    private static native void nPlotBars(String var0, long[] var1, double var2, double var4);

    private static native void nPlotBars(String var0, long[] var1, double var2, double var4, int var6);

    private static native void nPlotBars(String var0, long[] var1, double var2, double var4, int var6, int var7);

    public static void plotBars(String labelId, float[] values) {
        ImPlot.nPlotBars(labelId, values);
    }

    public static void plotBars(String labelId, float[] values, double barWidth) {
        ImPlot.nPlotBars(labelId, values, barWidth);
    }

    public static void plotBars(String labelId, float[] values, double barWidth, double xstart) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart);
    }

    public static void plotBars(String labelId, float[] values, double barWidth, double xstart, int flags) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart, flags);
    }

    public static void plotBars(String labelId, float[] values, double barWidth, double xstart, int flags, int offset) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart, flags, offset);
    }

    private static native void nPlotBars(String var0, float[] var1);

    private static native void nPlotBars(String var0, float[] var1, double var2);

    private static native void nPlotBars(String var0, float[] var1, double var2, double var4);

    private static native void nPlotBars(String var0, float[] var1, double var2, double var4, int var6);

    private static native void nPlotBars(String var0, float[] var1, double var2, double var4, int var6, int var7);

    public static void plotBars(String labelId, double[] values) {
        ImPlot.nPlotBars(labelId, values);
    }

    public static void plotBars(String labelId, double[] values, double barWidth) {
        ImPlot.nPlotBars(labelId, values, barWidth);
    }

    public static void plotBars(String labelId, double[] values, double barWidth, double xstart) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart);
    }

    public static void plotBars(String labelId, double[] values, double barWidth, double xstart, int flags) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart, flags);
    }

    public static void plotBars(String labelId, double[] values, double barWidth, double xstart, int flags, int offset) {
        ImPlot.nPlotBars(labelId, values, barWidth, xstart, flags, offset);
    }

    private static native void nPlotBars(String var0, double[] var1);

    private static native void nPlotBars(String var0, double[] var1, double var2);

    private static native void nPlotBars(String var0, double[] var1, double var2, double var4);

    private static native void nPlotBars(String var0, double[] var1, double var2, double var4, int var6);

    private static native void nPlotBars(String var0, double[] var1, double var2, double var4, int var6, int var7);

    public static void plotBarsV(String labelId, short[] values, int count) {
        ImPlot.nPlotBarsV(labelId, values, count);
    }

    public static void plotBarsV(String labelId, short[] values, int count, double barWidth) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth);
    }

    public static void plotBarsV(String labelId, short[] values, int count, double barWidth, double xstart) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart);
    }

    public static void plotBarsV(String labelId, short[] values, int count, double barWidth, double xstart, int flags) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart, flags);
    }

    public static void plotBarsV(String labelId, short[] values, int count, double barWidth, double xstart, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart, flags, offset);
    }

    private static native void nPlotBarsV(String var0, short[] var1, int var2);

    private static native void nPlotBarsV(String var0, short[] var1, int var2, double var3);

    private static native void nPlotBarsV(String var0, short[] var1, int var2, double var3, double var5);

    private static native void nPlotBarsV(String var0, short[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotBarsV(String var0, short[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotBarsV(String labelId, int[] values, int count) {
        ImPlot.nPlotBarsV(labelId, values, count);
    }

    public static void plotBarsV(String labelId, int[] values, int count, double barWidth) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth);
    }

    public static void plotBarsV(String labelId, int[] values, int count, double barWidth, double xstart) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart);
    }

    public static void plotBarsV(String labelId, int[] values, int count, double barWidth, double xstart, int flags) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart, flags);
    }

    public static void plotBarsV(String labelId, int[] values, int count, double barWidth, double xstart, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart, flags, offset);
    }

    private static native void nPlotBarsV(String var0, int[] var1, int var2);

    private static native void nPlotBarsV(String var0, int[] var1, int var2, double var3);

    private static native void nPlotBarsV(String var0, int[] var1, int var2, double var3, double var5);

    private static native void nPlotBarsV(String var0, int[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotBarsV(String var0, int[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotBarsV(String labelId, long[] values, int count) {
        ImPlot.nPlotBarsV(labelId, values, count);
    }

    public static void plotBarsV(String labelId, long[] values, int count, double barWidth) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth);
    }

    public static void plotBarsV(String labelId, long[] values, int count, double barWidth, double xstart) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart);
    }

    public static void plotBarsV(String labelId, long[] values, int count, double barWidth, double xstart, int flags) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart, flags);
    }

    public static void plotBarsV(String labelId, long[] values, int count, double barWidth, double xstart, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart, flags, offset);
    }

    private static native void nPlotBarsV(String var0, long[] var1, int var2);

    private static native void nPlotBarsV(String var0, long[] var1, int var2, double var3);

    private static native void nPlotBarsV(String var0, long[] var1, int var2, double var3, double var5);

    private static native void nPlotBarsV(String var0, long[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotBarsV(String var0, long[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotBarsV(String labelId, float[] values, int count) {
        ImPlot.nPlotBarsV(labelId, values, count);
    }

    public static void plotBarsV(String labelId, float[] values, int count, double barWidth) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth);
    }

    public static void plotBarsV(String labelId, float[] values, int count, double barWidth, double xstart) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart);
    }

    public static void plotBarsV(String labelId, float[] values, int count, double barWidth, double xstart, int flags) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart, flags);
    }

    public static void plotBarsV(String labelId, float[] values, int count, double barWidth, double xstart, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart, flags, offset);
    }

    private static native void nPlotBarsV(String var0, float[] var1, int var2);

    private static native void nPlotBarsV(String var0, float[] var1, int var2, double var3);

    private static native void nPlotBarsV(String var0, float[] var1, int var2, double var3, double var5);

    private static native void nPlotBarsV(String var0, float[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotBarsV(String var0, float[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotBarsV(String labelId, double[] values, int count) {
        ImPlot.nPlotBarsV(labelId, values, count);
    }

    public static void plotBarsV(String labelId, double[] values, int count, double barWidth) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth);
    }

    public static void plotBarsV(String labelId, double[] values, int count, double barWidth, double xstart) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart);
    }

    public static void plotBarsV(String labelId, double[] values, int count, double barWidth, double xstart, int flags) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart, flags);
    }

    public static void plotBarsV(String labelId, double[] values, int count, double barWidth, double xstart, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, values, count, barWidth, xstart, flags, offset);
    }

    private static native void nPlotBarsV(String var0, double[] var1, int var2);

    private static native void nPlotBarsV(String var0, double[] var1, int var2, double var3);

    private static native void nPlotBarsV(String var0, double[] var1, int var2, double var3, double var5);

    private static native void nPlotBarsV(String var0, double[] var1, int var2, double var3, double var5, int var7);

    private static native void nPlotBarsV(String var0, double[] var1, int var2, double var3, double var5, int var7, int var8);

    public static void plotBars(String labelId, short[] xs, short[] ys) {
        ImPlot.nPlotBars(labelId, xs, ys);
    }

    public static void plotBars(String labelId, short[] xs, short[] ys, int flags) {
        ImPlot.nPlotBars(labelId, xs, ys, flags);
    }

    public static void plotBars(String labelId, short[] xs, short[] ys, int flags, int offset) {
        ImPlot.nPlotBars(labelId, xs, ys, flags, offset);
    }

    private static native void nPlotBars(String var0, short[] var1, short[] var2);

    private static native void nPlotBars(String var0, short[] var1, short[] var2, int var3);

    private static native void nPlotBars(String var0, short[] var1, short[] var2, int var3, int var4);

    public static void plotBars(String labelId, int[] xs, int[] ys) {
        ImPlot.nPlotBars(labelId, xs, ys);
    }

    public static void plotBars(String labelId, int[] xs, int[] ys, int flags) {
        ImPlot.nPlotBars(labelId, xs, ys, flags);
    }

    public static void plotBars(String labelId, int[] xs, int[] ys, int flags, int offset) {
        ImPlot.nPlotBars(labelId, xs, ys, flags, offset);
    }

    private static native void nPlotBars(String var0, int[] var1, int[] var2);

    private static native void nPlotBars(String var0, int[] var1, int[] var2, int var3);

    private static native void nPlotBars(String var0, int[] var1, int[] var2, int var3, int var4);

    public static void plotBars(String labelId, long[] xs, long[] ys) {
        ImPlot.nPlotBars(labelId, xs, ys);
    }

    public static void plotBars(String labelId, long[] xs, long[] ys, int flags) {
        ImPlot.nPlotBars(labelId, xs, ys, flags);
    }

    public static void plotBars(String labelId, long[] xs, long[] ys, int flags, int offset) {
        ImPlot.nPlotBars(labelId, xs, ys, flags, offset);
    }

    private static native void nPlotBars(String var0, long[] var1, long[] var2);

    private static native void nPlotBars(String var0, long[] var1, long[] var2, int var3);

    private static native void nPlotBars(String var0, long[] var1, long[] var2, int var3, int var4);

    public static void plotBars(String labelId, float[] xs, float[] ys) {
        ImPlot.nPlotBars(labelId, xs, ys);
    }

    public static void plotBars(String labelId, float[] xs, float[] ys, int flags) {
        ImPlot.nPlotBars(labelId, xs, ys, flags);
    }

    public static void plotBars(String labelId, float[] xs, float[] ys, int flags, int offset) {
        ImPlot.nPlotBars(labelId, xs, ys, flags, offset);
    }

    private static native void nPlotBars(String var0, float[] var1, float[] var2);

    private static native void nPlotBars(String var0, float[] var1, float[] var2, int var3);

    private static native void nPlotBars(String var0, float[] var1, float[] var2, int var3, int var4);

    public static void plotBars(String labelId, double[] xs, double[] ys) {
        ImPlot.nPlotBars(labelId, xs, ys);
    }

    public static void plotBars(String labelId, double[] xs, double[] ys, int flags) {
        ImPlot.nPlotBars(labelId, xs, ys, flags);
    }

    public static void plotBars(String labelId, double[] xs, double[] ys, int flags, int offset) {
        ImPlot.nPlotBars(labelId, xs, ys, flags, offset);
    }

    private static native void nPlotBars(String var0, double[] var1, double[] var2);

    private static native void nPlotBars(String var0, double[] var1, double[] var2, int var3);

    private static native void nPlotBars(String var0, double[] var1, double[] var2, int var3, int var4);

    public static void plotBarsV(String labelId, short[] xs, short[] ys, double barWidth) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth);
    }

    public static void plotBarsV(String labelId, short[] xs, short[] ys, double barWidth, int flags) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth, flags);
    }

    public static void plotBarsV(String labelId, short[] xs, short[] ys, double barWidth, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth, flags, offset);
    }

    private static native void nPlotBarsV(String var0, short[] var1, short[] var2, double var3);

    private static native void nPlotBarsV(String var0, short[] var1, short[] var2, double var3, int var5);

    private static native void nPlotBarsV(String var0, short[] var1, short[] var2, double var3, int var5, int var6);

    public static void plotBarsV(String labelId, int[] xs, int[] ys, double barWidth) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth);
    }

    public static void plotBarsV(String labelId, int[] xs, int[] ys, double barWidth, int flags) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth, flags);
    }

    public static void plotBarsV(String labelId, int[] xs, int[] ys, double barWidth, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth, flags, offset);
    }

    private static native void nPlotBarsV(String var0, int[] var1, int[] var2, double var3);

    private static native void nPlotBarsV(String var0, int[] var1, int[] var2, double var3, int var5);

    private static native void nPlotBarsV(String var0, int[] var1, int[] var2, double var3, int var5, int var6);

    public static void plotBarsV(String labelId, long[] xs, long[] ys, double barWidth) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth);
    }

    public static void plotBarsV(String labelId, long[] xs, long[] ys, double barWidth, int flags) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth, flags);
    }

    public static void plotBarsV(String labelId, long[] xs, long[] ys, double barWidth, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth, flags, offset);
    }

    private static native void nPlotBarsV(String var0, long[] var1, long[] var2, double var3);

    private static native void nPlotBarsV(String var0, long[] var1, long[] var2, double var3, int var5);

    private static native void nPlotBarsV(String var0, long[] var1, long[] var2, double var3, int var5, int var6);

    public static void plotBarsV(String labelId, float[] xs, float[] ys, double barWidth) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth);
    }

    public static void plotBarsV(String labelId, float[] xs, float[] ys, double barWidth, int flags) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth, flags);
    }

    public static void plotBarsV(String labelId, float[] xs, float[] ys, double barWidth, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth, flags, offset);
    }

    private static native void nPlotBarsV(String var0, float[] var1, float[] var2, double var3);

    private static native void nPlotBarsV(String var0, float[] var1, float[] var2, double var3, int var5);

    private static native void nPlotBarsV(String var0, float[] var1, float[] var2, double var3, int var5, int var6);

    public static void plotBarsV(String labelId, double[] xs, double[] ys, double barWidth) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth);
    }

    public static void plotBarsV(String labelId, double[] xs, double[] ys, double barWidth, int flags) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth, flags);
    }

    public static void plotBarsV(String labelId, double[] xs, double[] ys, double barWidth, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, xs, ys, barWidth, flags, offset);
    }

    private static native void nPlotBarsV(String var0, double[] var1, double[] var2, double var3);

    private static native void nPlotBarsV(String var0, double[] var1, double[] var2, double var3, int var5);

    private static native void nPlotBarsV(String var0, double[] var1, double[] var2, double var3, int var5, int var6);

    public static void plotBarsV(String labelId, short[] xs, short[] ys, int count, double barWidth) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth);
    }

    public static void plotBarsV(String labelId, short[] xs, short[] ys, int count, double barWidth, int flags) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth, flags);
    }

    public static void plotBarsV(String labelId, short[] xs, short[] ys, int count, double barWidth, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth, flags, offset);
    }

    private static native void nPlotBarsV(String var0, short[] var1, short[] var2, int var3, double var4);

    private static native void nPlotBarsV(String var0, short[] var1, short[] var2, int var3, double var4, int var6);

    private static native void nPlotBarsV(String var0, short[] var1, short[] var2, int var3, double var4, int var6, int var7);

    public static void plotBarsV(String labelId, int[] xs, int[] ys, int count, double barWidth) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth);
    }

    public static void plotBarsV(String labelId, int[] xs, int[] ys, int count, double barWidth, int flags) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth, flags);
    }

    public static void plotBarsV(String labelId, int[] xs, int[] ys, int count, double barWidth, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth, flags, offset);
    }

    private static native void nPlotBarsV(String var0, int[] var1, int[] var2, int var3, double var4);

    private static native void nPlotBarsV(String var0, int[] var1, int[] var2, int var3, double var4, int var6);

    private static native void nPlotBarsV(String var0, int[] var1, int[] var2, int var3, double var4, int var6, int var7);

    public static void plotBarsV(String labelId, long[] xs, long[] ys, int count, double barWidth) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth);
    }

    public static void plotBarsV(String labelId, long[] xs, long[] ys, int count, double barWidth, int flags) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth, flags);
    }

    public static void plotBarsV(String labelId, long[] xs, long[] ys, int count, double barWidth, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth, flags, offset);
    }

    private static native void nPlotBarsV(String var0, long[] var1, long[] var2, int var3, double var4);

    private static native void nPlotBarsV(String var0, long[] var1, long[] var2, int var3, double var4, int var6);

    private static native void nPlotBarsV(String var0, long[] var1, long[] var2, int var3, double var4, int var6, int var7);

    public static void plotBarsV(String labelId, float[] xs, float[] ys, int count, double barWidth) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth);
    }

    public static void plotBarsV(String labelId, float[] xs, float[] ys, int count, double barWidth, int flags) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth, flags);
    }

    public static void plotBarsV(String labelId, float[] xs, float[] ys, int count, double barWidth, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth, flags, offset);
    }

    private static native void nPlotBarsV(String var0, float[] var1, float[] var2, int var3, double var4);

    private static native void nPlotBarsV(String var0, float[] var1, float[] var2, int var3, double var4, int var6);

    private static native void nPlotBarsV(String var0, float[] var1, float[] var2, int var3, double var4, int var6, int var7);

    public static void plotBarsV(String labelId, double[] xs, double[] ys, int count, double barWidth) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth);
    }

    public static void plotBarsV(String labelId, double[] xs, double[] ys, int count, double barWidth, int flags) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth, flags);
    }

    public static void plotBarsV(String labelId, double[] xs, double[] ys, int count, double barWidth, int flags, int offset) {
        ImPlot.nPlotBarsV(labelId, xs, ys, count, barWidth, flags, offset);
    }

    private static native void nPlotBarsV(String var0, double[] var1, double[] var2, int var3, double var4);

    private static native void nPlotBarsV(String var0, double[] var1, double[] var2, int var3, double var4, int var6);

    private static native void nPlotBarsV(String var0, double[] var1, double[] var2, int var3, double var4, int var6, int var7);

    public static void plotBarGroups(String[] labelIds, short[] values, int groupCount) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount);
    }

    public static void plotBarGroups(String[] labelIds, short[] values, int groupCount, double groupSize) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize);
    }

    public static void plotBarGroups(String[] labelIds, short[] values, int groupCount, double groupSize, double shift) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize, shift);
    }

    public static void plotBarGroups(String[] labelIds, short[] values, int groupCount, double groupSize, double shift, int flags) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize, shift, flags);
    }

    private static native void nPlotBarGroups(String[] var0, int var1, short[] var2, int var3);

    private static native void nPlotBarGroups(String[] var0, int var1, short[] var2, int var3, double var4);

    private static native void nPlotBarGroups(String[] var0, int var1, short[] var2, int var3, double var4, double var6);

    private static native void nPlotBarGroups(String[] var0, int var1, short[] var2, int var3, double var4, double var6, int var8);

    public static void plotBarGroups(String[] labelIds, int[] values, int groupCount) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount);
    }

    public static void plotBarGroups(String[] labelIds, int[] values, int groupCount, double groupSize) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize);
    }

    public static void plotBarGroups(String[] labelIds, int[] values, int groupCount, double groupSize, double shift) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize, shift);
    }

    public static void plotBarGroups(String[] labelIds, int[] values, int groupCount, double groupSize, double shift, int flags) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize, shift, flags);
    }

    private static native void nPlotBarGroups(String[] var0, int var1, int[] var2, int var3);

    private static native void nPlotBarGroups(String[] var0, int var1, int[] var2, int var3, double var4);

    private static native void nPlotBarGroups(String[] var0, int var1, int[] var2, int var3, double var4, double var6);

    private static native void nPlotBarGroups(String[] var0, int var1, int[] var2, int var3, double var4, double var6, int var8);

    public static void plotBarGroups(String[] labelIds, long[] values, int groupCount) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount);
    }

    public static void plotBarGroups(String[] labelIds, long[] values, int groupCount, double groupSize) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize);
    }

    public static void plotBarGroups(String[] labelIds, long[] values, int groupCount, double groupSize, double shift) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize, shift);
    }

    public static void plotBarGroups(String[] labelIds, long[] values, int groupCount, double groupSize, double shift, int flags) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize, shift, flags);
    }

    private static native void nPlotBarGroups(String[] var0, int var1, long[] var2, int var3);

    private static native void nPlotBarGroups(String[] var0, int var1, long[] var2, int var3, double var4);

    private static native void nPlotBarGroups(String[] var0, int var1, long[] var2, int var3, double var4, double var6);

    private static native void nPlotBarGroups(String[] var0, int var1, long[] var2, int var3, double var4, double var6, int var8);

    public static void plotBarGroups(String[] labelIds, float[] values, int groupCount) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount);
    }

    public static void plotBarGroups(String[] labelIds, float[] values, int groupCount, double groupSize) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize);
    }

    public static void plotBarGroups(String[] labelIds, float[] values, int groupCount, double groupSize, double shift) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize, shift);
    }

    public static void plotBarGroups(String[] labelIds, float[] values, int groupCount, double groupSize, double shift, int flags) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize, shift, flags);
    }

    private static native void nPlotBarGroups(String[] var0, int var1, float[] var2, int var3);

    private static native void nPlotBarGroups(String[] var0, int var1, float[] var2, int var3, double var4);

    private static native void nPlotBarGroups(String[] var0, int var1, float[] var2, int var3, double var4, double var6);

    private static native void nPlotBarGroups(String[] var0, int var1, float[] var2, int var3, double var4, double var6, int var8);

    public static void plotBarGroups(String[] labelIds, double[] values, int groupCount) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount);
    }

    public static void plotBarGroups(String[] labelIds, double[] values, int groupCount, double groupSize) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize);
    }

    public static void plotBarGroups(String[] labelIds, double[] values, int groupCount, double groupSize, double shift) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize, shift);
    }

    public static void plotBarGroups(String[] labelIds, double[] values, int groupCount, double groupSize, double shift, int flags) {
        ImPlot.nPlotBarGroups(labelIds, labelIds.length, values, groupCount, groupSize, shift, flags);
    }

    private static native void nPlotBarGroups(String[] var0, int var1, double[] var2, int var3);

    private static native void nPlotBarGroups(String[] var0, int var1, double[] var2, int var3, double var4);

    private static native void nPlotBarGroups(String[] var0, int var1, double[] var2, int var3, double var4, double var6);

    private static native void nPlotBarGroups(String[] var0, int var1, double[] var2, int var3, double var4, double var6, int var8);

    public static void plotBarGroupsV(String[] labelIds, short[] values, int itemCount, int groupCount) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount);
    }

    public static void plotBarGroupsV(String[] labelIds, short[] values, int itemCount, int groupCount, double groupSize) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize);
    }

    public static void plotBarGroupsV(String[] labelIds, short[] values, int itemCount, int groupCount, double groupSize, double shift) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize, shift);
    }

    public static void plotBarGroupsV(String[] labelIds, short[] values, int itemCount, int groupCount, double groupSize, double shift, int flags) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize, shift, flags);
    }

    private static native void nPlotBarGroupsV(String[] var0, int var1, short[] var2, int var3, int var4);

    private static native void nPlotBarGroupsV(String[] var0, int var1, short[] var2, int var3, int var4, double var5);

    private static native void nPlotBarGroupsV(String[] var0, int var1, short[] var2, int var3, int var4, double var5, double var7);

    private static native void nPlotBarGroupsV(String[] var0, int var1, short[] var2, int var3, int var4, double var5, double var7, int var9);

    public static void plotBarGroupsV(String[] labelIds, int[] values, int itemCount, int groupCount) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount);
    }

    public static void plotBarGroupsV(String[] labelIds, int[] values, int itemCount, int groupCount, double groupSize) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize);
    }

    public static void plotBarGroupsV(String[] labelIds, int[] values, int itemCount, int groupCount, double groupSize, double shift) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize, shift);
    }

    public static void plotBarGroupsV(String[] labelIds, int[] values, int itemCount, int groupCount, double groupSize, double shift, int flags) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize, shift, flags);
    }

    private static native void nPlotBarGroupsV(String[] var0, int var1, int[] var2, int var3, int var4);

    private static native void nPlotBarGroupsV(String[] var0, int var1, int[] var2, int var3, int var4, double var5);

    private static native void nPlotBarGroupsV(String[] var0, int var1, int[] var2, int var3, int var4, double var5, double var7);

    private static native void nPlotBarGroupsV(String[] var0, int var1, int[] var2, int var3, int var4, double var5, double var7, int var9);

    public static void plotBarGroupsV(String[] labelIds, long[] values, int itemCount, int groupCount) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount);
    }

    public static void plotBarGroupsV(String[] labelIds, long[] values, int itemCount, int groupCount, double groupSize) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize);
    }

    public static void plotBarGroupsV(String[] labelIds, long[] values, int itemCount, int groupCount, double groupSize, double shift) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize, shift);
    }

    public static void plotBarGroupsV(String[] labelIds, long[] values, int itemCount, int groupCount, double groupSize, double shift, int flags) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize, shift, flags);
    }

    private static native void nPlotBarGroupsV(String[] var0, int var1, long[] var2, int var3, int var4);

    private static native void nPlotBarGroupsV(String[] var0, int var1, long[] var2, int var3, int var4, double var5);

    private static native void nPlotBarGroupsV(String[] var0, int var1, long[] var2, int var3, int var4, double var5, double var7);

    private static native void nPlotBarGroupsV(String[] var0, int var1, long[] var2, int var3, int var4, double var5, double var7, int var9);

    public static void plotBarGroupsV(String[] labelIds, float[] values, int itemCount, int groupCount) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount);
    }

    public static void plotBarGroupsV(String[] labelIds, float[] values, int itemCount, int groupCount, double groupSize) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize);
    }

    public static void plotBarGroupsV(String[] labelIds, float[] values, int itemCount, int groupCount, double groupSize, double shift) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize, shift);
    }

    public static void plotBarGroupsV(String[] labelIds, float[] values, int itemCount, int groupCount, double groupSize, double shift, int flags) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize, shift, flags);
    }

    private static native void nPlotBarGroupsV(String[] var0, int var1, float[] var2, int var3, int var4);

    private static native void nPlotBarGroupsV(String[] var0, int var1, float[] var2, int var3, int var4, double var5);

    private static native void nPlotBarGroupsV(String[] var0, int var1, float[] var2, int var3, int var4, double var5, double var7);

    private static native void nPlotBarGroupsV(String[] var0, int var1, float[] var2, int var3, int var4, double var5, double var7, int var9);

    public static void plotBarGroupsV(String[] labelIds, double[] values, int itemCount, int groupCount) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount);
    }

    public static void plotBarGroupsV(String[] labelIds, double[] values, int itemCount, int groupCount, double groupSize) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize);
    }

    public static void plotBarGroupsV(String[] labelIds, double[] values, int itemCount, int groupCount, double groupSize, double shift) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize, shift);
    }

    public static void plotBarGroupsV(String[] labelIds, double[] values, int itemCount, int groupCount, double groupSize, double shift, int flags) {
        ImPlot.nPlotBarGroupsV(labelIds, labelIds.length, values, itemCount, groupCount, groupSize, shift, flags);
    }

    private static native void nPlotBarGroupsV(String[] var0, int var1, double[] var2, int var3, int var4);

    private static native void nPlotBarGroupsV(String[] var0, int var1, double[] var2, int var3, int var4, double var5);

    private static native void nPlotBarGroupsV(String[] var0, int var1, double[] var2, int var3, int var4, double var5, double var7);

    private static native void nPlotBarGroupsV(String[] var0, int var1, double[] var2, int var3, int var4, double var5, double var7, int var9);

    public static void plotErrorBars(String labelId, short[] xs, short[] ys, short[] err) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err);
    }

    public static void plotErrorBars(String labelId, short[] xs, short[] ys, short[] err, int flags) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err, flags);
    }

    public static void plotErrorBars(String labelId, short[] xs, short[] ys, short[] err, int flags, int offset) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err, flags, offset);
    }

    private static native void nPlotErrorBars(String var0, short[] var1, short[] var2, short[] var3);

    private static native void nPlotErrorBars(String var0, short[] var1, short[] var2, short[] var3, int var4);

    private static native void nPlotErrorBars(String var0, short[] var1, short[] var2, short[] var3, int var4, int var5);

    public static void plotErrorBars(String labelId, int[] xs, int[] ys, int[] err) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err);
    }

    public static void plotErrorBars(String labelId, int[] xs, int[] ys, int[] err, int flags) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err, flags);
    }

    public static void plotErrorBars(String labelId, int[] xs, int[] ys, int[] err, int flags, int offset) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err, flags, offset);
    }

    private static native void nPlotErrorBars(String var0, int[] var1, int[] var2, int[] var3);

    private static native void nPlotErrorBars(String var0, int[] var1, int[] var2, int[] var3, int var4);

    private static native void nPlotErrorBars(String var0, int[] var1, int[] var2, int[] var3, int var4, int var5);

    public static void plotErrorBars(String labelId, long[] xs, long[] ys, long[] err) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err);
    }

    public static void plotErrorBars(String labelId, long[] xs, long[] ys, long[] err, int flags) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err, flags);
    }

    public static void plotErrorBars(String labelId, long[] xs, long[] ys, long[] err, int flags, int offset) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err, flags, offset);
    }

    private static native void nPlotErrorBars(String var0, long[] var1, long[] var2, long[] var3);

    private static native void nPlotErrorBars(String var0, long[] var1, long[] var2, long[] var3, int var4);

    private static native void nPlotErrorBars(String var0, long[] var1, long[] var2, long[] var3, int var4, int var5);

    public static void plotErrorBars(String labelId, float[] xs, float[] ys, float[] err) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err);
    }

    public static void plotErrorBars(String labelId, float[] xs, float[] ys, float[] err, int flags) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err, flags);
    }

    public static void plotErrorBars(String labelId, float[] xs, float[] ys, float[] err, int flags, int offset) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err, flags, offset);
    }

    private static native void nPlotErrorBars(String var0, float[] var1, float[] var2, float[] var3);

    private static native void nPlotErrorBars(String var0, float[] var1, float[] var2, float[] var3, int var4);

    private static native void nPlotErrorBars(String var0, float[] var1, float[] var2, float[] var3, int var4, int var5);

    public static void plotErrorBars(String labelId, double[] xs, double[] ys, double[] err) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err);
    }

    public static void plotErrorBars(String labelId, double[] xs, double[] ys, double[] err, int flags) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err, flags);
    }

    public static void plotErrorBars(String labelId, double[] xs, double[] ys, double[] err, int flags, int offset) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, err, flags, offset);
    }

    private static native void nPlotErrorBars(String var0, double[] var1, double[] var2, double[] var3);

    private static native void nPlotErrorBars(String var0, double[] var1, double[] var2, double[] var3, int var4);

    private static native void nPlotErrorBars(String var0, double[] var1, double[] var2, double[] var3, int var4, int var5);

    public static void plotErrorBarsV(String labelId, short[] xs, short[] ys, short[] err, int count) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count);
    }

    public static void plotErrorBarsV(String labelId, short[] xs, short[] ys, short[] err, int count, int flags) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count, flags);
    }

    public static void plotErrorBarsV(String labelId, short[] xs, short[] ys, short[] err, int count, int flags, int offset) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count, flags, offset);
    }

    private static native void nPlotErrorBarsV(String var0, short[] var1, short[] var2, short[] var3, int var4);

    private static native void nPlotErrorBarsV(String var0, short[] var1, short[] var2, short[] var3, int var4, int var5);

    private static native void nPlotErrorBarsV(String var0, short[] var1, short[] var2, short[] var3, int var4, int var5, int var6);

    public static void plotErrorBarsV(String labelId, int[] xs, int[] ys, int[] err, int count) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count);
    }

    public static void plotErrorBarsV(String labelId, int[] xs, int[] ys, int[] err, int count, int flags) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count, flags);
    }

    public static void plotErrorBarsV(String labelId, int[] xs, int[] ys, int[] err, int count, int flags, int offset) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count, flags, offset);
    }

    private static native void nPlotErrorBarsV(String var0, int[] var1, int[] var2, int[] var3, int var4);

    private static native void nPlotErrorBarsV(String var0, int[] var1, int[] var2, int[] var3, int var4, int var5);

    private static native void nPlotErrorBarsV(String var0, int[] var1, int[] var2, int[] var3, int var4, int var5, int var6);

    public static void plotErrorBarsV(String labelId, long[] xs, long[] ys, long[] err, int count) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count);
    }

    public static void plotErrorBarsV(String labelId, long[] xs, long[] ys, long[] err, int count, int flags) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count, flags);
    }

    public static void plotErrorBarsV(String labelId, long[] xs, long[] ys, long[] err, int count, int flags, int offset) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count, flags, offset);
    }

    private static native void nPlotErrorBarsV(String var0, long[] var1, long[] var2, long[] var3, int var4);

    private static native void nPlotErrorBarsV(String var0, long[] var1, long[] var2, long[] var3, int var4, int var5);

    private static native void nPlotErrorBarsV(String var0, long[] var1, long[] var2, long[] var3, int var4, int var5, int var6);

    public static void plotErrorBarsV(String labelId, float[] xs, float[] ys, float[] err, int count) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count);
    }

    public static void plotErrorBarsV(String labelId, float[] xs, float[] ys, float[] err, int count, int flags) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count, flags);
    }

    public static void plotErrorBarsV(String labelId, float[] xs, float[] ys, float[] err, int count, int flags, int offset) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count, flags, offset);
    }

    private static native void nPlotErrorBarsV(String var0, float[] var1, float[] var2, float[] var3, int var4);

    private static native void nPlotErrorBarsV(String var0, float[] var1, float[] var2, float[] var3, int var4, int var5);

    private static native void nPlotErrorBarsV(String var0, float[] var1, float[] var2, float[] var3, int var4, int var5, int var6);

    public static void plotErrorBarsV(String labelId, double[] xs, double[] ys, double[] err, int count) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count);
    }

    public static void plotErrorBarsV(String labelId, double[] xs, double[] ys, double[] err, int count, int flags) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count, flags);
    }

    public static void plotErrorBarsV(String labelId, double[] xs, double[] ys, double[] err, int count, int flags, int offset) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, err, count, flags, offset);
    }

    private static native void nPlotErrorBarsV(String var0, double[] var1, double[] var2, double[] var3, int var4);

    private static native void nPlotErrorBarsV(String var0, double[] var1, double[] var2, double[] var3, int var4, int var5);

    private static native void nPlotErrorBarsV(String var0, double[] var1, double[] var2, double[] var3, int var4, int var5, int var6);

    public static void plotErrorBars(String labelId, short[] xs, short[] ys, short[] neg, short[] pos) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos);
    }

    public static void plotErrorBars(String labelId, short[] xs, short[] ys, short[] neg, short[] pos, int flags) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos, flags);
    }

    public static void plotErrorBars(String labelId, short[] xs, short[] ys, short[] neg, short[] pos, int flags, int offset) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos, flags, offset);
    }

    private static native void nPlotErrorBars(String var0, short[] var1, short[] var2, short[] var3, short[] var4);

    private static native void nPlotErrorBars(String var0, short[] var1, short[] var2, short[] var3, short[] var4, int var5);

    private static native void nPlotErrorBars(String var0, short[] var1, short[] var2, short[] var3, short[] var4, int var5, int var6);

    public static void plotErrorBars(String labelId, int[] xs, int[] ys, int[] neg, int[] pos) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos);
    }

    public static void plotErrorBars(String labelId, int[] xs, int[] ys, int[] neg, int[] pos, int flags) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos, flags);
    }

    public static void plotErrorBars(String labelId, int[] xs, int[] ys, int[] neg, int[] pos, int flags, int offset) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos, flags, offset);
    }

    private static native void nPlotErrorBars(String var0, int[] var1, int[] var2, int[] var3, int[] var4);

    private static native void nPlotErrorBars(String var0, int[] var1, int[] var2, int[] var3, int[] var4, int var5);

    private static native void nPlotErrorBars(String var0, int[] var1, int[] var2, int[] var3, int[] var4, int var5, int var6);

    public static void plotErrorBars(String labelId, long[] xs, long[] ys, long[] neg, long[] pos) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos);
    }

    public static void plotErrorBars(String labelId, long[] xs, long[] ys, long[] neg, long[] pos, int flags) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos, flags);
    }

    public static void plotErrorBars(String labelId, long[] xs, long[] ys, long[] neg, long[] pos, int flags, int offset) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos, flags, offset);
    }

    private static native void nPlotErrorBars(String var0, long[] var1, long[] var2, long[] var3, long[] var4);

    private static native void nPlotErrorBars(String var0, long[] var1, long[] var2, long[] var3, long[] var4, int var5);

    private static native void nPlotErrorBars(String var0, long[] var1, long[] var2, long[] var3, long[] var4, int var5, int var6);

    public static void plotErrorBars(String labelId, float[] xs, float[] ys, float[] neg, float[] pos) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos);
    }

    public static void plotErrorBars(String labelId, float[] xs, float[] ys, float[] neg, float[] pos, int flags) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos, flags);
    }

    public static void plotErrorBars(String labelId, float[] xs, float[] ys, float[] neg, float[] pos, int flags, int offset) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos, flags, offset);
    }

    private static native void nPlotErrorBars(String var0, float[] var1, float[] var2, float[] var3, float[] var4);

    private static native void nPlotErrorBars(String var0, float[] var1, float[] var2, float[] var3, float[] var4, int var5);

    private static native void nPlotErrorBars(String var0, float[] var1, float[] var2, float[] var3, float[] var4, int var5, int var6);

    public static void plotErrorBars(String labelId, double[] xs, double[] ys, double[] neg, double[] pos) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos);
    }

    public static void plotErrorBars(String labelId, double[] xs, double[] ys, double[] neg, double[] pos, int flags) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos, flags);
    }

    public static void plotErrorBars(String labelId, double[] xs, double[] ys, double[] neg, double[] pos, int flags, int offset) {
        ImPlot.nPlotErrorBars(labelId, xs, ys, neg, pos, flags, offset);
    }

    private static native void nPlotErrorBars(String var0, double[] var1, double[] var2, double[] var3, double[] var4);

    private static native void nPlotErrorBars(String var0, double[] var1, double[] var2, double[] var3, double[] var4, int var5);

    private static native void nPlotErrorBars(String var0, double[] var1, double[] var2, double[] var3, double[] var4, int var5, int var6);

    public static void plotErrorBarsV(String labelId, short[] xs, short[] ys, short[] neg, short[] pos, int count) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count);
    }

    public static void plotErrorBarsV(String labelId, short[] xs, short[] ys, short[] neg, short[] pos, int count, int flags) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count, flags);
    }

    public static void plotErrorBarsV(String labelId, short[] xs, short[] ys, short[] neg, short[] pos, int count, int flags, int offset) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count, flags, offset);
    }

    private static native void nPlotErrorBarsV(String var0, short[] var1, short[] var2, short[] var3, short[] var4, int var5);

    private static native void nPlotErrorBarsV(String var0, short[] var1, short[] var2, short[] var3, short[] var4, int var5, int var6);

    private static native void nPlotErrorBarsV(String var0, short[] var1, short[] var2, short[] var3, short[] var4, int var5, int var6, int var7);

    public static void plotErrorBarsV(String labelId, int[] xs, int[] ys, int[] neg, int[] pos, int count) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count);
    }

    public static void plotErrorBarsV(String labelId, int[] xs, int[] ys, int[] neg, int[] pos, int count, int flags) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count, flags);
    }

    public static void plotErrorBarsV(String labelId, int[] xs, int[] ys, int[] neg, int[] pos, int count, int flags, int offset) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count, flags, offset);
    }

    private static native void nPlotErrorBarsV(String var0, int[] var1, int[] var2, int[] var3, int[] var4, int var5);

    private static native void nPlotErrorBarsV(String var0, int[] var1, int[] var2, int[] var3, int[] var4, int var5, int var6);

    private static native void nPlotErrorBarsV(String var0, int[] var1, int[] var2, int[] var3, int[] var4, int var5, int var6, int var7);

    public static void plotErrorBarsV(String labelId, long[] xs, long[] ys, long[] neg, long[] pos, int count) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count);
    }

    public static void plotErrorBarsV(String labelId, long[] xs, long[] ys, long[] neg, long[] pos, int count, int flags) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count, flags);
    }

    public static void plotErrorBarsV(String labelId, long[] xs, long[] ys, long[] neg, long[] pos, int count, int flags, int offset) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count, flags, offset);
    }

    private static native void nPlotErrorBarsV(String var0, long[] var1, long[] var2, long[] var3, long[] var4, int var5);

    private static native void nPlotErrorBarsV(String var0, long[] var1, long[] var2, long[] var3, long[] var4, int var5, int var6);

    private static native void nPlotErrorBarsV(String var0, long[] var1, long[] var2, long[] var3, long[] var4, int var5, int var6, int var7);

    public static void plotErrorBarsV(String labelId, float[] xs, float[] ys, float[] neg, float[] pos, int count) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count);
    }

    public static void plotErrorBarsV(String labelId, float[] xs, float[] ys, float[] neg, float[] pos, int count, int flags) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count, flags);
    }

    public static void plotErrorBarsV(String labelId, float[] xs, float[] ys, float[] neg, float[] pos, int count, int flags, int offset) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count, flags, offset);
    }

    private static native void nPlotErrorBarsV(String var0, float[] var1, float[] var2, float[] var3, float[] var4, int var5);

    private static native void nPlotErrorBarsV(String var0, float[] var1, float[] var2, float[] var3, float[] var4, int var5, int var6);

    private static native void nPlotErrorBarsV(String var0, float[] var1, float[] var2, float[] var3, float[] var4, int var5, int var6, int var7);

    public static void plotErrorBarsV(String labelId, double[] xs, double[] ys, double[] neg, double[] pos, int count) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count);
    }

    public static void plotErrorBarsV(String labelId, double[] xs, double[] ys, double[] neg, double[] pos, int count, int flags) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count, flags);
    }

    public static void plotErrorBarsV(String labelId, double[] xs, double[] ys, double[] neg, double[] pos, int count, int flags, int offset) {
        ImPlot.nPlotErrorBarsV(labelId, xs, ys, neg, pos, count, flags, offset);
    }

    private static native void nPlotErrorBarsV(String var0, double[] var1, double[] var2, double[] var3, double[] var4, int var5);

    private static native void nPlotErrorBarsV(String var0, double[] var1, double[] var2, double[] var3, double[] var4, int var5, int var6);

    private static native void nPlotErrorBarsV(String var0, double[] var1, double[] var2, double[] var3, double[] var4, int var5, int var6, int var7);

    public static void plotStems(String labelId, short[] values) {
        ImPlot.nPlotStems(labelId, values);
    }

    public static void plotStems(String labelId, short[] values, double yRef) {
        ImPlot.nPlotStems(labelId, values, yRef);
    }

    public static void plotStems(String labelId, short[] values, double yRef, double xscale) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale);
    }

    public static void plotStems(String labelId, short[] values, double yRef, double xscale, double xstart) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart);
    }

    public static void plotStems(String labelId, short[] values, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart, flags);
    }

    public static void plotStems(String labelId, short[] values, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotStems(String var0, short[] var1);

    private static native void nPlotStems(String var0, short[] var1, double var2);

    private static native void nPlotStems(String var0, short[] var1, double var2, double var4);

    private static native void nPlotStems(String var0, short[] var1, double var2, double var4, double var6);

    private static native void nPlotStems(String var0, short[] var1, double var2, double var4, double var6, int var8);

    private static native void nPlotStems(String var0, short[] var1, double var2, double var4, double var6, int var8, int var9);

    public static void plotStems(String labelId, int[] values) {
        ImPlot.nPlotStems(labelId, values);
    }

    public static void plotStems(String labelId, int[] values, double yRef) {
        ImPlot.nPlotStems(labelId, values, yRef);
    }

    public static void plotStems(String labelId, int[] values, double yRef, double xscale) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale);
    }

    public static void plotStems(String labelId, int[] values, double yRef, double xscale, double xstart) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart);
    }

    public static void plotStems(String labelId, int[] values, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart, flags);
    }

    public static void plotStems(String labelId, int[] values, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotStems(String var0, int[] var1);

    private static native void nPlotStems(String var0, int[] var1, double var2);

    private static native void nPlotStems(String var0, int[] var1, double var2, double var4);

    private static native void nPlotStems(String var0, int[] var1, double var2, double var4, double var6);

    private static native void nPlotStems(String var0, int[] var1, double var2, double var4, double var6, int var8);

    private static native void nPlotStems(String var0, int[] var1, double var2, double var4, double var6, int var8, int var9);

    public static void plotStems(String labelId, long[] values) {
        ImPlot.nPlotStems(labelId, values);
    }

    public static void plotStems(String labelId, long[] values, double yRef) {
        ImPlot.nPlotStems(labelId, values, yRef);
    }

    public static void plotStems(String labelId, long[] values, double yRef, double xscale) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale);
    }

    public static void plotStems(String labelId, long[] values, double yRef, double xscale, double xstart) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart);
    }

    public static void plotStems(String labelId, long[] values, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart, flags);
    }

    public static void plotStems(String labelId, long[] values, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotStems(String var0, long[] var1);

    private static native void nPlotStems(String var0, long[] var1, double var2);

    private static native void nPlotStems(String var0, long[] var1, double var2, double var4);

    private static native void nPlotStems(String var0, long[] var1, double var2, double var4, double var6);

    private static native void nPlotStems(String var0, long[] var1, double var2, double var4, double var6, int var8);

    private static native void nPlotStems(String var0, long[] var1, double var2, double var4, double var6, int var8, int var9);

    public static void plotStems(String labelId, float[] values) {
        ImPlot.nPlotStems(labelId, values);
    }

    public static void plotStems(String labelId, float[] values, double yRef) {
        ImPlot.nPlotStems(labelId, values, yRef);
    }

    public static void plotStems(String labelId, float[] values, double yRef, double xscale) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale);
    }

    public static void plotStems(String labelId, float[] values, double yRef, double xscale, double xstart) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart);
    }

    public static void plotStems(String labelId, float[] values, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart, flags);
    }

    public static void plotStems(String labelId, float[] values, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotStems(String var0, float[] var1);

    private static native void nPlotStems(String var0, float[] var1, double var2);

    private static native void nPlotStems(String var0, float[] var1, double var2, double var4);

    private static native void nPlotStems(String var0, float[] var1, double var2, double var4, double var6);

    private static native void nPlotStems(String var0, float[] var1, double var2, double var4, double var6, int var8);

    private static native void nPlotStems(String var0, float[] var1, double var2, double var4, double var6, int var8, int var9);

    public static void plotStems(String labelId, double[] values) {
        ImPlot.nPlotStems(labelId, values);
    }

    public static void plotStems(String labelId, double[] values, double yRef) {
        ImPlot.nPlotStems(labelId, values, yRef);
    }

    public static void plotStems(String labelId, double[] values, double yRef, double xscale) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale);
    }

    public static void plotStems(String labelId, double[] values, double yRef, double xscale, double xstart) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart);
    }

    public static void plotStems(String labelId, double[] values, double yRef, double xscale, double xstart, int flags) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart, flags);
    }

    public static void plotStems(String labelId, double[] values, double yRef, double xscale, double xstart, int flags, int offset) {
        ImPlot.nPlotStems(labelId, values, yRef, xscale, xstart, flags, offset);
    }

    private static native void nPlotStems(String var0, double[] var1);

    private static native void nPlotStems(String var0, double[] var1, double var2);

    private static native void nPlotStems(String var0, double[] var1, double var2, double var4);

    private static native void nPlotStems(String var0, double[] var1, double var2, double var4, double var6);

    private static native void nPlotStems(String var0, double[] var1, double var2, double var4, double var6, int var8);

    private static native void nPlotStems(String var0, double[] var1, double var2, double var4, double var6, int var8, int var9);

    public static void plotStemsV(String labelId, short[] values, int count) {
        ImPlot.nPlotStemsV(labelId, values, count);
    }

    public static void plotStemsV(String labelId, short[] values, int count, double yRef) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef);
    }

    public static void plotStemsV(String labelId, short[] values, int count, double yRef, double scale) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale);
    }

    public static void plotStemsV(String labelId, short[] values, int count, double yRef, double scale, double start) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start);
    }

    public static void plotStemsV(String labelId, short[] values, int count, double yRef, double scale, double start, int flags) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start, flags);
    }

    public static void plotStemsV(String labelId, short[] values, int count, double yRef, double scale, double start, int flags, int offset) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start, flags, offset);
    }

    private static native void nPlotStemsV(String var0, short[] var1, int var2);

    private static native void nPlotStemsV(String var0, short[] var1, int var2, double var3);

    private static native void nPlotStemsV(String var0, short[] var1, int var2, double var3, double var5);

    private static native void nPlotStemsV(String var0, short[] var1, int var2, double var3, double var5, double var7);

    private static native void nPlotStemsV(String var0, short[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native void nPlotStemsV(String var0, short[] var1, int var2, double var3, double var5, double var7, int var9, int var10);

    public static void plotStemsV(String labelId, int[] values, int count) {
        ImPlot.nPlotStemsV(labelId, values, count);
    }

    public static void plotStemsV(String labelId, int[] values, int count, double yRef) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef);
    }

    public static void plotStemsV(String labelId, int[] values, int count, double yRef, double scale) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale);
    }

    public static void plotStemsV(String labelId, int[] values, int count, double yRef, double scale, double start) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start);
    }

    public static void plotStemsV(String labelId, int[] values, int count, double yRef, double scale, double start, int flags) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start, flags);
    }

    public static void plotStemsV(String labelId, int[] values, int count, double yRef, double scale, double start, int flags, int offset) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start, flags, offset);
    }

    private static native void nPlotStemsV(String var0, int[] var1, int var2);

    private static native void nPlotStemsV(String var0, int[] var1, int var2, double var3);

    private static native void nPlotStemsV(String var0, int[] var1, int var2, double var3, double var5);

    private static native void nPlotStemsV(String var0, int[] var1, int var2, double var3, double var5, double var7);

    private static native void nPlotStemsV(String var0, int[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native void nPlotStemsV(String var0, int[] var1, int var2, double var3, double var5, double var7, int var9, int var10);

    public static void plotStemsV(String labelId, long[] values, int count) {
        ImPlot.nPlotStemsV(labelId, values, count);
    }

    public static void plotStemsV(String labelId, long[] values, int count, double yRef) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef);
    }

    public static void plotStemsV(String labelId, long[] values, int count, double yRef, double scale) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale);
    }

    public static void plotStemsV(String labelId, long[] values, int count, double yRef, double scale, double start) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start);
    }

    public static void plotStemsV(String labelId, long[] values, int count, double yRef, double scale, double start, int flags) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start, flags);
    }

    public static void plotStemsV(String labelId, long[] values, int count, double yRef, double scale, double start, int flags, int offset) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start, flags, offset);
    }

    private static native void nPlotStemsV(String var0, long[] var1, int var2);

    private static native void nPlotStemsV(String var0, long[] var1, int var2, double var3);

    private static native void nPlotStemsV(String var0, long[] var1, int var2, double var3, double var5);

    private static native void nPlotStemsV(String var0, long[] var1, int var2, double var3, double var5, double var7);

    private static native void nPlotStemsV(String var0, long[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native void nPlotStemsV(String var0, long[] var1, int var2, double var3, double var5, double var7, int var9, int var10);

    public static void plotStemsV(String labelId, float[] values, int count) {
        ImPlot.nPlotStemsV(labelId, values, count);
    }

    public static void plotStemsV(String labelId, float[] values, int count, double yRef) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef);
    }

    public static void plotStemsV(String labelId, float[] values, int count, double yRef, double scale) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale);
    }

    public static void plotStemsV(String labelId, float[] values, int count, double yRef, double scale, double start) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start);
    }

    public static void plotStemsV(String labelId, float[] values, int count, double yRef, double scale, double start, int flags) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start, flags);
    }

    public static void plotStemsV(String labelId, float[] values, int count, double yRef, double scale, double start, int flags, int offset) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start, flags, offset);
    }

    private static native void nPlotStemsV(String var0, float[] var1, int var2);

    private static native void nPlotStemsV(String var0, float[] var1, int var2, double var3);

    private static native void nPlotStemsV(String var0, float[] var1, int var2, double var3, double var5);

    private static native void nPlotStemsV(String var0, float[] var1, int var2, double var3, double var5, double var7);

    private static native void nPlotStemsV(String var0, float[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native void nPlotStemsV(String var0, float[] var1, int var2, double var3, double var5, double var7, int var9, int var10);

    public static void plotStemsV(String labelId, double[] values, int count) {
        ImPlot.nPlotStemsV(labelId, values, count);
    }

    public static void plotStemsV(String labelId, double[] values, int count, double yRef) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef);
    }

    public static void plotStemsV(String labelId, double[] values, int count, double yRef, double scale) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale);
    }

    public static void plotStemsV(String labelId, double[] values, int count, double yRef, double scale, double start) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start);
    }

    public static void plotStemsV(String labelId, double[] values, int count, double yRef, double scale, double start, int flags) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start, flags);
    }

    public static void plotStemsV(String labelId, double[] values, int count, double yRef, double scale, double start, int flags, int offset) {
        ImPlot.nPlotStemsV(labelId, values, count, yRef, scale, start, flags, offset);
    }

    private static native void nPlotStemsV(String var0, double[] var1, int var2);

    private static native void nPlotStemsV(String var0, double[] var1, int var2, double var3);

    private static native void nPlotStemsV(String var0, double[] var1, int var2, double var3, double var5);

    private static native void nPlotStemsV(String var0, double[] var1, int var2, double var3, double var5, double var7);

    private static native void nPlotStemsV(String var0, double[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native void nPlotStemsV(String var0, double[] var1, int var2, double var3, double var5, double var7, int var9, int var10);

    public static void plotStems(String labelId, short[] xs, short[] ys) {
        ImPlot.nPlotStems(labelId, xs, ys);
    }

    public static void plotStems(String labelId, short[] xs, short[] ys, double ref) {
        ImPlot.nPlotStems(labelId, xs, ys, ref);
    }

    public static void plotStems(String labelId, short[] xs, short[] ys, double ref, int flags) {
        ImPlot.nPlotStems(labelId, xs, ys, ref, flags);
    }

    public static void plotStems(String labelId, short[] xs, short[] ys, double ref, int flags, int offset) {
        ImPlot.nPlotStems(labelId, xs, ys, ref, flags, offset);
    }

    private static native void nPlotStems(String var0, short[] var1, short[] var2);

    private static native void nPlotStems(String var0, short[] var1, short[] var2, double var3);

    private static native void nPlotStems(String var0, short[] var1, short[] var2, double var3, int var5);

    private static native void nPlotStems(String var0, short[] var1, short[] var2, double var3, int var5, int var6);

    public static void plotStems(String labelId, int[] xs, int[] ys) {
        ImPlot.nPlotStems(labelId, xs, ys);
    }

    public static void plotStems(String labelId, int[] xs, int[] ys, double ref) {
        ImPlot.nPlotStems(labelId, xs, ys, ref);
    }

    public static void plotStems(String labelId, int[] xs, int[] ys, double ref, int flags) {
        ImPlot.nPlotStems(labelId, xs, ys, ref, flags);
    }

    public static void plotStems(String labelId, int[] xs, int[] ys, double ref, int flags, int offset) {
        ImPlot.nPlotStems(labelId, xs, ys, ref, flags, offset);
    }

    private static native void nPlotStems(String var0, int[] var1, int[] var2);

    private static native void nPlotStems(String var0, int[] var1, int[] var2, double var3);

    private static native void nPlotStems(String var0, int[] var1, int[] var2, double var3, int var5);

    private static native void nPlotStems(String var0, int[] var1, int[] var2, double var3, int var5, int var6);

    public static void plotStems(String labelId, long[] xs, long[] ys) {
        ImPlot.nPlotStems(labelId, xs, ys);
    }

    public static void plotStems(String labelId, long[] xs, long[] ys, double ref) {
        ImPlot.nPlotStems(labelId, xs, ys, ref);
    }

    public static void plotStems(String labelId, long[] xs, long[] ys, double ref, int flags) {
        ImPlot.nPlotStems(labelId, xs, ys, ref, flags);
    }

    public static void plotStems(String labelId, long[] xs, long[] ys, double ref, int flags, int offset) {
        ImPlot.nPlotStems(labelId, xs, ys, ref, flags, offset);
    }

    private static native void nPlotStems(String var0, long[] var1, long[] var2);

    private static native void nPlotStems(String var0, long[] var1, long[] var2, double var3);

    private static native void nPlotStems(String var0, long[] var1, long[] var2, double var3, int var5);

    private static native void nPlotStems(String var0, long[] var1, long[] var2, double var3, int var5, int var6);

    public static void plotStems(String labelId, float[] xs, float[] ys) {
        ImPlot.nPlotStems(labelId, xs, ys);
    }

    public static void plotStems(String labelId, float[] xs, float[] ys, double ref) {
        ImPlot.nPlotStems(labelId, xs, ys, ref);
    }

    public static void plotStems(String labelId, float[] xs, float[] ys, double ref, int flags) {
        ImPlot.nPlotStems(labelId, xs, ys, ref, flags);
    }

    public static void plotStems(String labelId, float[] xs, float[] ys, double ref, int flags, int offset) {
        ImPlot.nPlotStems(labelId, xs, ys, ref, flags, offset);
    }

    private static native void nPlotStems(String var0, float[] var1, float[] var2);

    private static native void nPlotStems(String var0, float[] var1, float[] var2, double var3);

    private static native void nPlotStems(String var0, float[] var1, float[] var2, double var3, int var5);

    private static native void nPlotStems(String var0, float[] var1, float[] var2, double var3, int var5, int var6);

    public static void plotStems(String labelId, double[] xs, double[] ys) {
        ImPlot.nPlotStems(labelId, xs, ys);
    }

    public static void plotStems(String labelId, double[] xs, double[] ys, double ref) {
        ImPlot.nPlotStems(labelId, xs, ys, ref);
    }

    public static void plotStems(String labelId, double[] xs, double[] ys, double ref, int flags) {
        ImPlot.nPlotStems(labelId, xs, ys, ref, flags);
    }

    public static void plotStems(String labelId, double[] xs, double[] ys, double ref, int flags, int offset) {
        ImPlot.nPlotStems(labelId, xs, ys, ref, flags, offset);
    }

    private static native void nPlotStems(String var0, double[] var1, double[] var2);

    private static native void nPlotStems(String var0, double[] var1, double[] var2, double var3);

    private static native void nPlotStems(String var0, double[] var1, double[] var2, double var3, int var5);

    private static native void nPlotStems(String var0, double[] var1, double[] var2, double var3, int var5, int var6);

    public static void plotStemsV(String labelId, short[] xs, short[] ys, int count) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count);
    }

    public static void plotStemsV(String labelId, short[] xs, short[] ys, int count, double yRef) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef);
    }

    public static void plotStemsV(String labelId, short[] xs, short[] ys, int count, double yRef, int flags) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef, flags);
    }

    public static void plotStemsV(String labelId, short[] xs, short[] ys, int count, double yRef, int flags, int offset) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef, flags, offset);
    }

    private static native void nPlotStemsV(String var0, short[] var1, short[] var2, int var3);

    private static native void nPlotStemsV(String var0, short[] var1, short[] var2, int var3, double var4);

    private static native void nPlotStemsV(String var0, short[] var1, short[] var2, int var3, double var4, int var6);

    private static native void nPlotStemsV(String var0, short[] var1, short[] var2, int var3, double var4, int var6, int var7);

    public static void plotStemsV(String labelId, int[] xs, int[] ys, int count) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count);
    }

    public static void plotStemsV(String labelId, int[] xs, int[] ys, int count, double yRef) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef);
    }

    public static void plotStemsV(String labelId, int[] xs, int[] ys, int count, double yRef, int flags) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef, flags);
    }

    public static void plotStemsV(String labelId, int[] xs, int[] ys, int count, double yRef, int flags, int offset) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef, flags, offset);
    }

    private static native void nPlotStemsV(String var0, int[] var1, int[] var2, int var3);

    private static native void nPlotStemsV(String var0, int[] var1, int[] var2, int var3, double var4);

    private static native void nPlotStemsV(String var0, int[] var1, int[] var2, int var3, double var4, int var6);

    private static native void nPlotStemsV(String var0, int[] var1, int[] var2, int var3, double var4, int var6, int var7);

    public static void plotStemsV(String labelId, long[] xs, long[] ys, int count) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count);
    }

    public static void plotStemsV(String labelId, long[] xs, long[] ys, int count, double yRef) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef);
    }

    public static void plotStemsV(String labelId, long[] xs, long[] ys, int count, double yRef, int flags) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef, flags);
    }

    public static void plotStemsV(String labelId, long[] xs, long[] ys, int count, double yRef, int flags, int offset) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef, flags, offset);
    }

    private static native void nPlotStemsV(String var0, long[] var1, long[] var2, int var3);

    private static native void nPlotStemsV(String var0, long[] var1, long[] var2, int var3, double var4);

    private static native void nPlotStemsV(String var0, long[] var1, long[] var2, int var3, double var4, int var6);

    private static native void nPlotStemsV(String var0, long[] var1, long[] var2, int var3, double var4, int var6, int var7);

    public static void plotStemsV(String labelId, float[] xs, float[] ys, int count) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count);
    }

    public static void plotStemsV(String labelId, float[] xs, float[] ys, int count, double yRef) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef);
    }

    public static void plotStemsV(String labelId, float[] xs, float[] ys, int count, double yRef, int flags) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef, flags);
    }

    public static void plotStemsV(String labelId, float[] xs, float[] ys, int count, double yRef, int flags, int offset) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef, flags, offset);
    }

    private static native void nPlotStemsV(String var0, float[] var1, float[] var2, int var3);

    private static native void nPlotStemsV(String var0, float[] var1, float[] var2, int var3, double var4);

    private static native void nPlotStemsV(String var0, float[] var1, float[] var2, int var3, double var4, int var6);

    private static native void nPlotStemsV(String var0, float[] var1, float[] var2, int var3, double var4, int var6, int var7);

    public static void plotStemsV(String labelId, double[] xs, double[] ys, int count) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count);
    }

    public static void plotStemsV(String labelId, double[] xs, double[] ys, int count, double yRef) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef);
    }

    public static void plotStemsV(String labelId, double[] xs, double[] ys, int count, double yRef, int flags) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef, flags);
    }

    public static void plotStemsV(String labelId, double[] xs, double[] ys, int count, double yRef, int flags, int offset) {
        ImPlot.nPlotStemsV(labelId, xs, ys, count, yRef, flags, offset);
    }

    private static native void nPlotStemsV(String var0, double[] var1, double[] var2, int var3);

    private static native void nPlotStemsV(String var0, double[] var1, double[] var2, int var3, double var4);

    private static native void nPlotStemsV(String var0, double[] var1, double[] var2, int var3, double var4, int var6);

    private static native void nPlotStemsV(String var0, double[] var1, double[] var2, int var3, double var4, int var6, int var7);

    public static void plotInfLines(String labelId, short[] values) {
        ImPlot.nPlotInfLines(labelId, values);
    }

    public static void plotInfLines(String labelId, short[] values, int flags) {
        ImPlot.nPlotInfLines(labelId, values, flags);
    }

    public static void plotInfLines(String labelId, short[] values, int flags, int offset) {
        ImPlot.nPlotInfLines(labelId, values, flags, offset);
    }

    private static native void nPlotInfLines(String var0, short[] var1);

    private static native void nPlotInfLines(String var0, short[] var1, int var2);

    private static native void nPlotInfLines(String var0, short[] var1, int var2, int var3);

    public static void plotInfLines(String labelId, int[] values) {
        ImPlot.nPlotInfLines(labelId, values);
    }

    public static void plotInfLines(String labelId, int[] values, int flags) {
        ImPlot.nPlotInfLines(labelId, values, flags);
    }

    public static void plotInfLines(String labelId, int[] values, int flags, int offset) {
        ImPlot.nPlotInfLines(labelId, values, flags, offset);
    }

    private static native void nPlotInfLines(String var0, int[] var1);

    private static native void nPlotInfLines(String var0, int[] var1, int var2);

    private static native void nPlotInfLines(String var0, int[] var1, int var2, int var3);

    public static void plotInfLines(String labelId, long[] values) {
        ImPlot.nPlotInfLines(labelId, values);
    }

    public static void plotInfLines(String labelId, long[] values, int flags) {
        ImPlot.nPlotInfLines(labelId, values, flags);
    }

    public static void plotInfLines(String labelId, long[] values, int flags, int offset) {
        ImPlot.nPlotInfLines(labelId, values, flags, offset);
    }

    private static native void nPlotInfLines(String var0, long[] var1);

    private static native void nPlotInfLines(String var0, long[] var1, int var2);

    private static native void nPlotInfLines(String var0, long[] var1, int var2, int var3);

    public static void plotInfLines(String labelId, float[] values) {
        ImPlot.nPlotInfLines(labelId, values);
    }

    public static void plotInfLines(String labelId, float[] values, int flags) {
        ImPlot.nPlotInfLines(labelId, values, flags);
    }

    public static void plotInfLines(String labelId, float[] values, int flags, int offset) {
        ImPlot.nPlotInfLines(labelId, values, flags, offset);
    }

    private static native void nPlotInfLines(String var0, float[] var1);

    private static native void nPlotInfLines(String var0, float[] var1, int var2);

    private static native void nPlotInfLines(String var0, float[] var1, int var2, int var3);

    public static void plotInfLines(String labelId, double[] values) {
        ImPlot.nPlotInfLines(labelId, values);
    }

    public static void plotInfLines(String labelId, double[] values, int flags) {
        ImPlot.nPlotInfLines(labelId, values, flags);
    }

    public static void plotInfLines(String labelId, double[] values, int flags, int offset) {
        ImPlot.nPlotInfLines(labelId, values, flags, offset);
    }

    private static native void nPlotInfLines(String var0, double[] var1);

    private static native void nPlotInfLines(String var0, double[] var1, int var2);

    private static native void nPlotInfLines(String var0, double[] var1, int var2, int var3);

    public static void plotInfLinesV(String labelId, short[] values, int count) {
        ImPlot.nPlotInfLinesV(labelId, values, count);
    }

    public static void plotInfLinesV(String labelId, short[] values, int count, int flags) {
        ImPlot.nPlotInfLinesV(labelId, values, count, flags);
    }

    public static void plotInfLinesV(String labelId, short[] values, int count, int flags, int offset) {
        ImPlot.nPlotInfLinesV(labelId, values, count, flags, offset);
    }

    private static native void nPlotInfLinesV(String var0, short[] var1, int var2);

    private static native void nPlotInfLinesV(String var0, short[] var1, int var2, int var3);

    private static native void nPlotInfLinesV(String var0, short[] var1, int var2, int var3, int var4);

    public static void plotInfLinesV(String labelId, int[] values, int count) {
        ImPlot.nPlotInfLinesV(labelId, values, count);
    }

    public static void plotInfLinesV(String labelId, int[] values, int count, int flags) {
        ImPlot.nPlotInfLinesV(labelId, values, count, flags);
    }

    public static void plotInfLinesV(String labelId, int[] values, int count, int flags, int offset) {
        ImPlot.nPlotInfLinesV(labelId, values, count, flags, offset);
    }

    private static native void nPlotInfLinesV(String var0, int[] var1, int var2);

    private static native void nPlotInfLinesV(String var0, int[] var1, int var2, int var3);

    private static native void nPlotInfLinesV(String var0, int[] var1, int var2, int var3, int var4);

    public static void plotInfLinesV(String labelId, long[] values, int count) {
        ImPlot.nPlotInfLinesV(labelId, values, count);
    }

    public static void plotInfLinesV(String labelId, long[] values, int count, int flags) {
        ImPlot.nPlotInfLinesV(labelId, values, count, flags);
    }

    public static void plotInfLinesV(String labelId, long[] values, int count, int flags, int offset) {
        ImPlot.nPlotInfLinesV(labelId, values, count, flags, offset);
    }

    private static native void nPlotInfLinesV(String var0, long[] var1, int var2);

    private static native void nPlotInfLinesV(String var0, long[] var1, int var2, int var3);

    private static native void nPlotInfLinesV(String var0, long[] var1, int var2, int var3, int var4);

    public static void plotInfLinesV(String labelId, float[] values, int count) {
        ImPlot.nPlotInfLinesV(labelId, values, count);
    }

    public static void plotInfLinesV(String labelId, float[] values, int count, int flags) {
        ImPlot.nPlotInfLinesV(labelId, values, count, flags);
    }

    public static void plotInfLinesV(String labelId, float[] values, int count, int flags, int offset) {
        ImPlot.nPlotInfLinesV(labelId, values, count, flags, offset);
    }

    private static native void nPlotInfLinesV(String var0, float[] var1, int var2);

    private static native void nPlotInfLinesV(String var0, float[] var1, int var2, int var3);

    private static native void nPlotInfLinesV(String var0, float[] var1, int var2, int var3, int var4);

    public static void plotInfLinesV(String labelId, double[] values, int count) {
        ImPlot.nPlotInfLinesV(labelId, values, count);
    }

    public static void plotInfLinesV(String labelId, double[] values, int count, int flags) {
        ImPlot.nPlotInfLinesV(labelId, values, count, flags);
    }

    public static void plotInfLinesV(String labelId, double[] values, int count, int flags, int offset) {
        ImPlot.nPlotInfLinesV(labelId, values, count, flags, offset);
    }

    private static native void nPlotInfLinesV(String var0, double[] var1, int var2);

    private static native void nPlotInfLinesV(String var0, double[] var1, int var2, int var3);

    private static native void nPlotInfLinesV(String var0, double[] var1, int var2, int var3, int var4);

    public static void plotPieChart(String[] labelIds, short[] values, double x, double y, double radius) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius);
    }

    public static void plotPieChart(String[] labelIds, short[] values, double x, double y, double radius, String labelFmt) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt);
    }

    public static void plotPieChart(String[] labelIds, short[] values, double x, double y, double radius, String labelFmt, double angle0) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0);
    }

    public static void plotPieChart(String[] labelIds, short[] values, double x, double y, double radius, String labelFmt, double angle0, int flags) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0, flags);
    }

    public static void plotPieChart(String[] labelIds, short[] values, double x, double y, double radius, double angle0, int flags) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, angle0, flags);
    }

    private static native void nPlotPieChart(String[] var0, int var1, short[] var2, double var3, double var5, double var7);

    private static native void nPlotPieChart(String[] var0, int var1, short[] var2, double var3, double var5, double var7, String var9);

    private static native void nPlotPieChart(String[] var0, int var1, short[] var2, double var3, double var5, double var7, String var9, double var10);

    private static native void nPlotPieChart(String[] var0, int var1, short[] var2, double var3, double var5, double var7, String var9, double var10, int var12);

    private static native void nPlotPieChart(String[] var0, int var1, short[] var2, double var3, double var5, double var7, double var9, int var11);

    public static void plotPieChart(String[] labelIds, int[] values, double x, double y, double radius) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius);
    }

    public static void plotPieChart(String[] labelIds, int[] values, double x, double y, double radius, String labelFmt) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt);
    }

    public static void plotPieChart(String[] labelIds, int[] values, double x, double y, double radius, String labelFmt, double angle0) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0);
    }

    public static void plotPieChart(String[] labelIds, int[] values, double x, double y, double radius, String labelFmt, double angle0, int flags) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0, flags);
    }

    public static void plotPieChart(String[] labelIds, int[] values, double x, double y, double radius, double angle0, int flags) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, angle0, flags);
    }

    private static native void nPlotPieChart(String[] var0, int var1, int[] var2, double var3, double var5, double var7);

    private static native void nPlotPieChart(String[] var0, int var1, int[] var2, double var3, double var5, double var7, String var9);

    private static native void nPlotPieChart(String[] var0, int var1, int[] var2, double var3, double var5, double var7, String var9, double var10);

    private static native void nPlotPieChart(String[] var0, int var1, int[] var2, double var3, double var5, double var7, String var9, double var10, int var12);

    private static native void nPlotPieChart(String[] var0, int var1, int[] var2, double var3, double var5, double var7, double var9, int var11);

    public static void plotPieChart(String[] labelIds, long[] values, double x, double y, double radius) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius);
    }

    public static void plotPieChart(String[] labelIds, long[] values, double x, double y, double radius, String labelFmt) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt);
    }

    public static void plotPieChart(String[] labelIds, long[] values, double x, double y, double radius, String labelFmt, double angle0) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0);
    }

    public static void plotPieChart(String[] labelIds, long[] values, double x, double y, double radius, String labelFmt, double angle0, int flags) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0, flags);
    }

    public static void plotPieChart(String[] labelIds, long[] values, double x, double y, double radius, double angle0, int flags) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, angle0, flags);
    }

    private static native void nPlotPieChart(String[] var0, int var1, long[] var2, double var3, double var5, double var7);

    private static native void nPlotPieChart(String[] var0, int var1, long[] var2, double var3, double var5, double var7, String var9);

    private static native void nPlotPieChart(String[] var0, int var1, long[] var2, double var3, double var5, double var7, String var9, double var10);

    private static native void nPlotPieChart(String[] var0, int var1, long[] var2, double var3, double var5, double var7, String var9, double var10, int var12);

    private static native void nPlotPieChart(String[] var0, int var1, long[] var2, double var3, double var5, double var7, double var9, int var11);

    public static void plotPieChart(String[] labelIds, float[] values, double x, double y, double radius) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius);
    }

    public static void plotPieChart(String[] labelIds, float[] values, double x, double y, double radius, String labelFmt) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt);
    }

    public static void plotPieChart(String[] labelIds, float[] values, double x, double y, double radius, String labelFmt, double angle0) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0);
    }

    public static void plotPieChart(String[] labelIds, float[] values, double x, double y, double radius, String labelFmt, double angle0, int flags) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0, flags);
    }

    public static void plotPieChart(String[] labelIds, float[] values, double x, double y, double radius, double angle0, int flags) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, angle0, flags);
    }

    private static native void nPlotPieChart(String[] var0, int var1, float[] var2, double var3, double var5, double var7);

    private static native void nPlotPieChart(String[] var0, int var1, float[] var2, double var3, double var5, double var7, String var9);

    private static native void nPlotPieChart(String[] var0, int var1, float[] var2, double var3, double var5, double var7, String var9, double var10);

    private static native void nPlotPieChart(String[] var0, int var1, float[] var2, double var3, double var5, double var7, String var9, double var10, int var12);

    private static native void nPlotPieChart(String[] var0, int var1, float[] var2, double var3, double var5, double var7, double var9, int var11);

    public static void plotPieChart(String[] labelIds, double[] values, double x, double y, double radius) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius);
    }

    public static void plotPieChart(String[] labelIds, double[] values, double x, double y, double radius, String labelFmt) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt);
    }

    public static void plotPieChart(String[] labelIds, double[] values, double x, double y, double radius, String labelFmt, double angle0) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0);
    }

    public static void plotPieChart(String[] labelIds, double[] values, double x, double y, double radius, String labelFmt, double angle0, int flags) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0, flags);
    }

    public static void plotPieChart(String[] labelIds, double[] values, double x, double y, double radius, double angle0, int flags) {
        ImPlot.nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, angle0, flags);
    }

    private static native void nPlotPieChart(String[] var0, int var1, double[] var2, double var3, double var5, double var7);

    private static native void nPlotPieChart(String[] var0, int var1, double[] var2, double var3, double var5, double var7, String var9);

    private static native void nPlotPieChart(String[] var0, int var1, double[] var2, double var3, double var5, double var7, String var9, double var10);

    private static native void nPlotPieChart(String[] var0, int var1, double[] var2, double var3, double var5, double var7, String var9, double var10, int var12);

    private static native void nPlotPieChart(String[] var0, int var1, double[] var2, double var3, double var5, double var7, double var9, int var11);

    public static void plotPieChartV(String[] labelIds, short[] values, int count, double x, double y, double radius) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius);
    }

    public static void plotPieChartV(String[] labelIds, short[] values, int count, double x, double y, double radius, String labelFmt) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt);
    }

    public static void plotPieChartV(String[] labelIds, short[] values, int count, double x, double y, double radius, String labelFmt, double angle0) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt, angle0);
    }

    public static void plotPieChartV(String[] labelIds, short[] values, int count, double x, double y, double radius, String labelFmt, double angle0, int flags) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt, angle0, flags);
    }

    public static void plotPieChartV(String[] labelIds, short[] values, int count, double x, double y, double radius, double angle0, int flags) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, angle0, flags);
    }

    private static native void nPlotPieChartV(String[] var0, int var1, short[] var2, int var3, double var4, double var6, double var8);

    private static native void nPlotPieChartV(String[] var0, int var1, short[] var2, int var3, double var4, double var6, double var8, String var10);

    private static native void nPlotPieChartV(String[] var0, int var1, short[] var2, int var3, double var4, double var6, double var8, String var10, double var11);

    private static native void nPlotPieChartV(String[] var0, int var1, short[] var2, int var3, double var4, double var6, double var8, String var10, double var11, int var13);

    private static native void nPlotPieChartV(String[] var0, int var1, short[] var2, int var3, double var4, double var6, double var8, double var10, int var12);

    public static void plotPieChartV(String[] labelIds, int[] values, int count, double x, double y, double radius) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius);
    }

    public static void plotPieChartV(String[] labelIds, int[] values, int count, double x, double y, double radius, String labelFmt) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt);
    }

    public static void plotPieChartV(String[] labelIds, int[] values, int count, double x, double y, double radius, String labelFmt, double angle0) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt, angle0);
    }

    public static void plotPieChartV(String[] labelIds, int[] values, int count, double x, double y, double radius, String labelFmt, double angle0, int flags) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt, angle0, flags);
    }

    public static void plotPieChartV(String[] labelIds, int[] values, int count, double x, double y, double radius, double angle0, int flags) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, angle0, flags);
    }

    private static native void nPlotPieChartV(String[] var0, int var1, int[] var2, int var3, double var4, double var6, double var8);

    private static native void nPlotPieChartV(String[] var0, int var1, int[] var2, int var3, double var4, double var6, double var8, String var10);

    private static native void nPlotPieChartV(String[] var0, int var1, int[] var2, int var3, double var4, double var6, double var8, String var10, double var11);

    private static native void nPlotPieChartV(String[] var0, int var1, int[] var2, int var3, double var4, double var6, double var8, String var10, double var11, int var13);

    private static native void nPlotPieChartV(String[] var0, int var1, int[] var2, int var3, double var4, double var6, double var8, double var10, int var12);

    public static void plotPieChartV(String[] labelIds, long[] values, int count, double x, double y, double radius) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius);
    }

    public static void plotPieChartV(String[] labelIds, long[] values, int count, double x, double y, double radius, String labelFmt) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt);
    }

    public static void plotPieChartV(String[] labelIds, long[] values, int count, double x, double y, double radius, String labelFmt, double angle0) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt, angle0);
    }

    public static void plotPieChartV(String[] labelIds, long[] values, int count, double x, double y, double radius, String labelFmt, double angle0, int flags) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt, angle0, flags);
    }

    public static void plotPieChartV(String[] labelIds, long[] values, int count, double x, double y, double radius, double angle0, int flags) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, angle0, flags);
    }

    private static native void nPlotPieChartV(String[] var0, int var1, long[] var2, int var3, double var4, double var6, double var8);

    private static native void nPlotPieChartV(String[] var0, int var1, long[] var2, int var3, double var4, double var6, double var8, String var10);

    private static native void nPlotPieChartV(String[] var0, int var1, long[] var2, int var3, double var4, double var6, double var8, String var10, double var11);

    private static native void nPlotPieChartV(String[] var0, int var1, long[] var2, int var3, double var4, double var6, double var8, String var10, double var11, int var13);

    private static native void nPlotPieChartV(String[] var0, int var1, long[] var2, int var3, double var4, double var6, double var8, double var10, int var12);

    public static void plotPieChartV(String[] labelIds, float[] values, int count, double x, double y, double radius) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius);
    }

    public static void plotPieChartV(String[] labelIds, float[] values, int count, double x, double y, double radius, String labelFmt) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt);
    }

    public static void plotPieChartV(String[] labelIds, float[] values, int count, double x, double y, double radius, String labelFmt, double angle0) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt, angle0);
    }

    public static void plotPieChartV(String[] labelIds, float[] values, int count, double x, double y, double radius, String labelFmt, double angle0, int flags) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt, angle0, flags);
    }

    public static void plotPieChartV(String[] labelIds, float[] values, int count, double x, double y, double radius, double angle0, int flags) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, angle0, flags);
    }

    private static native void nPlotPieChartV(String[] var0, int var1, float[] var2, int var3, double var4, double var6, double var8);

    private static native void nPlotPieChartV(String[] var0, int var1, float[] var2, int var3, double var4, double var6, double var8, String var10);

    private static native void nPlotPieChartV(String[] var0, int var1, float[] var2, int var3, double var4, double var6, double var8, String var10, double var11);

    private static native void nPlotPieChartV(String[] var0, int var1, float[] var2, int var3, double var4, double var6, double var8, String var10, double var11, int var13);

    private static native void nPlotPieChartV(String[] var0, int var1, float[] var2, int var3, double var4, double var6, double var8, double var10, int var12);

    public static void plotPieChartV(String[] labelIds, double[] values, int count, double x, double y, double radius) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius);
    }

    public static void plotPieChartV(String[] labelIds, double[] values, int count, double x, double y, double radius, String labelFmt) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt);
    }

    public static void plotPieChartV(String[] labelIds, double[] values, int count, double x, double y, double radius, String labelFmt, double angle0) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt, angle0);
    }

    public static void plotPieChartV(String[] labelIds, double[] values, int count, double x, double y, double radius, String labelFmt, double angle0, int flags) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, labelFmt, angle0, flags);
    }

    public static void plotPieChartV(String[] labelIds, double[] values, int count, double x, double y, double radius, double angle0, int flags) {
        ImPlot.nPlotPieChartV(labelIds, labelIds.length, values, count, x, y, radius, angle0, flags);
    }

    private static native void nPlotPieChartV(String[] var0, int var1, double[] var2, int var3, double var4, double var6, double var8);

    private static native void nPlotPieChartV(String[] var0, int var1, double[] var2, int var3, double var4, double var6, double var8, String var10);

    private static native void nPlotPieChartV(String[] var0, int var1, double[] var2, int var3, double var4, double var6, double var8, String var10, double var11);

    private static native void nPlotPieChartV(String[] var0, int var1, double[] var2, int var3, double var4, double var6, double var8, String var10, double var11, int var13);

    private static native void nPlotPieChartV(String[] var0, int var1, double[] var2, int var3, double var4, double var6, double var8, double var10, int var12);

    public static void plotHeatmap(String labelId, short[] values, int rows, int cols) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols);
    }

    public static void plotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin);
    }

    public static void plotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax);
    }

    public static void plotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt);
    }

    public static void plotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y);
    }

    public static void plotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY);
    }

    public static void plotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin, ImPlotPoint boundsMax) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    public static void plotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    public static void plotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin, ImPlotPoint boundsMax, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, flags);
    }

    public static void plotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, flags);
    }

    public static void plotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax, ImPlotPoint boundsMin, ImPlotPoint boundsMax, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, flags);
    }

    public static void plotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, flags);
    }

    private static native void nPlotHeatmap(String var0, short[] var1, int var2, int var3);

    private static native void nPlotHeatmap(String var0, short[] var1, int var2, int var3, double var4);

    private static native void nPlotHeatmap(String var0, short[] var1, int var2, int var3, double var4, double var6);

    private static native void nPlotHeatmap(String var0, short[] var1, int var2, int var3, double var4, double var6, String var8);

    private static native void nPlotHeatmap(String var0, short[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11);

    private static native void nPlotHeatmap(String var0, short[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11, double var13, double var15);

    private static native void nPlotHeatmap(String var0, short[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11, double var13, double var15, int var17);

    private static native void nPlotHeatmap(String var0, short[] var1, int var2, int var3, double var4, double var6, double var8, double var10, double var12, double var14, int var16);

    public static void plotHeatmap(String labelId, int[] values, int rows, int cols) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols);
    }

    public static void plotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin);
    }

    public static void plotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax);
    }

    public static void plotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt);
    }

    public static void plotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y);
    }

    public static void plotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY);
    }

    public static void plotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin, ImPlotPoint boundsMax) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    public static void plotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    public static void plotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin, ImPlotPoint boundsMax, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, flags);
    }

    public static void plotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, flags);
    }

    public static void plotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax, ImPlotPoint boundsMin, ImPlotPoint boundsMax, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, flags);
    }

    public static void plotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, flags);
    }

    private static native void nPlotHeatmap(String var0, int[] var1, int var2, int var3);

    private static native void nPlotHeatmap(String var0, int[] var1, int var2, int var3, double var4);

    private static native void nPlotHeatmap(String var0, int[] var1, int var2, int var3, double var4, double var6);

    private static native void nPlotHeatmap(String var0, int[] var1, int var2, int var3, double var4, double var6, String var8);

    private static native void nPlotHeatmap(String var0, int[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11);

    private static native void nPlotHeatmap(String var0, int[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11, double var13, double var15);

    private static native void nPlotHeatmap(String var0, int[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11, double var13, double var15, int var17);

    private static native void nPlotHeatmap(String var0, int[] var1, int var2, int var3, double var4, double var6, double var8, double var10, double var12, double var14, int var16);

    public static void plotHeatmap(String labelId, long[] values, int rows, int cols) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols);
    }

    public static void plotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin);
    }

    public static void plotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax);
    }

    public static void plotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt);
    }

    public static void plotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y);
    }

    public static void plotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY);
    }

    public static void plotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin, ImPlotPoint boundsMax) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    public static void plotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    public static void plotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin, ImPlotPoint boundsMax, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, flags);
    }

    public static void plotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, flags);
    }

    public static void plotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax, ImPlotPoint boundsMin, ImPlotPoint boundsMax, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, flags);
    }

    public static void plotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, flags);
    }

    private static native void nPlotHeatmap(String var0, long[] var1, int var2, int var3);

    private static native void nPlotHeatmap(String var0, long[] var1, int var2, int var3, double var4);

    private static native void nPlotHeatmap(String var0, long[] var1, int var2, int var3, double var4, double var6);

    private static native void nPlotHeatmap(String var0, long[] var1, int var2, int var3, double var4, double var6, String var8);

    private static native void nPlotHeatmap(String var0, long[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11);

    private static native void nPlotHeatmap(String var0, long[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11, double var13, double var15);

    private static native void nPlotHeatmap(String var0, long[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11, double var13, double var15, int var17);

    private static native void nPlotHeatmap(String var0, long[] var1, int var2, int var3, double var4, double var6, double var8, double var10, double var12, double var14, int var16);

    public static void plotHeatmap(String labelId, float[] values, int rows, int cols) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols);
    }

    public static void plotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin);
    }

    public static void plotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax);
    }

    public static void plotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt);
    }

    public static void plotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y);
    }

    public static void plotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY);
    }

    public static void plotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin, ImPlotPoint boundsMax) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    public static void plotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    public static void plotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin, ImPlotPoint boundsMax, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, flags);
    }

    public static void plotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, flags);
    }

    public static void plotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax, ImPlotPoint boundsMin, ImPlotPoint boundsMax, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, flags);
    }

    public static void plotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, flags);
    }

    private static native void nPlotHeatmap(String var0, float[] var1, int var2, int var3);

    private static native void nPlotHeatmap(String var0, float[] var1, int var2, int var3, double var4);

    private static native void nPlotHeatmap(String var0, float[] var1, int var2, int var3, double var4, double var6);

    private static native void nPlotHeatmap(String var0, float[] var1, int var2, int var3, double var4, double var6, String var8);

    private static native void nPlotHeatmap(String var0, float[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11);

    private static native void nPlotHeatmap(String var0, float[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11, double var13, double var15);

    private static native void nPlotHeatmap(String var0, float[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11, double var13, double var15, int var17);

    private static native void nPlotHeatmap(String var0, float[] var1, int var2, int var3, double var4, double var6, double var8, double var10, double var12, double var14, int var16);

    public static void plotHeatmap(String labelId, double[] values, int rows, int cols) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols);
    }

    public static void plotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin);
    }

    public static void plotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax);
    }

    public static void plotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt);
    }

    public static void plotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y);
    }

    public static void plotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY);
    }

    public static void plotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin, ImPlotPoint boundsMax) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    public static void plotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    public static void plotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, ImPlotPoint boundsMin, ImPlotPoint boundsMax, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, flags);
    }

    public static void plotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, flags);
    }

    public static void plotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax, ImPlotPoint boundsMin, ImPlotPoint boundsMax, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, flags);
    }

    public static void plotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, int flags) {
        ImPlot.nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, flags);
    }

    private static native void nPlotHeatmap(String var0, double[] var1, int var2, int var3);

    private static native void nPlotHeatmap(String var0, double[] var1, int var2, int var3, double var4);

    private static native void nPlotHeatmap(String var0, double[] var1, int var2, int var3, double var4, double var6);

    private static native void nPlotHeatmap(String var0, double[] var1, int var2, int var3, double var4, double var6, String var8);

    private static native void nPlotHeatmap(String var0, double[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11);

    private static native void nPlotHeatmap(String var0, double[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11, double var13, double var15);

    private static native void nPlotHeatmap(String var0, double[] var1, int var2, int var3, double var4, double var6, String var8, double var9, double var11, double var13, double var15, int var17);

    private static native void nPlotHeatmap(String var0, double[] var1, int var2, int var3, double var4, double var6, double var8, double var10, double var12, double var14, int var16);

    public static double plotHistogram(String labelId, short[] values) {
        return ImPlot.nPlotHistogram(labelId, values);
    }

    public static double plotHistogram(String labelId, short[] values, int bins) {
        return ImPlot.nPlotHistogram(labelId, values, bins);
    }

    public static double plotHistogram(String labelId, short[] values, int bins, double barScale) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale);
    }

    public static double plotHistogram(String labelId, short[] values, int bins, double barScale, ImPlotRange range) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, range.min, range.max);
    }

    public static double plotHistogram(String labelId, short[] values, int bins, double barScale, double rangeMin, double rangeMax) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, rangeMin, rangeMax);
    }

    public static double plotHistogram(String labelId, short[] values, int bins, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, range.min, range.max, flags);
    }

    public static double plotHistogram(String labelId, short[] values, int bins, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogram(String labelId, short[] values, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, barScale, range.min, range.max, flags);
    }

    public static double plotHistogram(String labelId, short[] values, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogram(String labelId, short[] values, int bins, double barScale, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, flags);
    }

    private static native double nPlotHistogram(String var0, short[] var1);

    private static native double nPlotHistogram(String var0, short[] var1, int var2);

    private static native double nPlotHistogram(String var0, short[] var1, int var2, double var3);

    private static native double nPlotHistogram(String var0, short[] var1, int var2, double var3, double var5, double var7);

    private static native double nPlotHistogram(String var0, short[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native double nPlotHistogram(String var0, short[] var1, double var2, double var4, double var6, int var8);

    private static native double nPlotHistogram(String var0, short[] var1, int var2, double var3, int var5);

    public static double plotHistogram(String labelId, int[] values) {
        return ImPlot.nPlotHistogram(labelId, values);
    }

    public static double plotHistogram(String labelId, int[] values, int bins) {
        return ImPlot.nPlotHistogram(labelId, values, bins);
    }

    public static double plotHistogram(String labelId, int[] values, int bins, double barScale) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale);
    }

    public static double plotHistogram(String labelId, int[] values, int bins, double barScale, ImPlotRange range) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, range.min, range.max);
    }

    public static double plotHistogram(String labelId, int[] values, int bins, double barScale, double rangeMin, double rangeMax) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, rangeMin, rangeMax);
    }

    public static double plotHistogram(String labelId, int[] values, int bins, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, range.min, range.max, flags);
    }

    public static double plotHistogram(String labelId, int[] values, int bins, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogram(String labelId, int[] values, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, barScale, range.min, range.max, flags);
    }

    public static double plotHistogram(String labelId, int[] values, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogram(String labelId, int[] values, int bins, double barScale, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, flags);
    }

    private static native double nPlotHistogram(String var0, int[] var1);

    private static native double nPlotHistogram(String var0, int[] var1, int var2);

    private static native double nPlotHistogram(String var0, int[] var1, int var2, double var3);

    private static native double nPlotHistogram(String var0, int[] var1, int var2, double var3, double var5, double var7);

    private static native double nPlotHistogram(String var0, int[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native double nPlotHistogram(String var0, int[] var1, double var2, double var4, double var6, int var8);

    private static native double nPlotHistogram(String var0, int[] var1, int var2, double var3, int var5);

    public static double plotHistogram(String labelId, long[] values) {
        return ImPlot.nPlotHistogram(labelId, values);
    }

    public static double plotHistogram(String labelId, long[] values, int bins) {
        return ImPlot.nPlotHistogram(labelId, values, bins);
    }

    public static double plotHistogram(String labelId, long[] values, int bins, double barScale) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale);
    }

    public static double plotHistogram(String labelId, long[] values, int bins, double barScale, ImPlotRange range) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, range.min, range.max);
    }

    public static double plotHistogram(String labelId, long[] values, int bins, double barScale, double rangeMin, double rangeMax) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, rangeMin, rangeMax);
    }

    public static double plotHistogram(String labelId, long[] values, int bins, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, range.min, range.max, flags);
    }

    public static double plotHistogram(String labelId, long[] values, int bins, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogram(String labelId, long[] values, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, barScale, range.min, range.max, flags);
    }

    public static double plotHistogram(String labelId, long[] values, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogram(String labelId, long[] values, int bins, double barScale, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, flags);
    }

    private static native double nPlotHistogram(String var0, long[] var1);

    private static native double nPlotHistogram(String var0, long[] var1, int var2);

    private static native double nPlotHistogram(String var0, long[] var1, int var2, double var3);

    private static native double nPlotHistogram(String var0, long[] var1, int var2, double var3, double var5, double var7);

    private static native double nPlotHistogram(String var0, long[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native double nPlotHistogram(String var0, long[] var1, double var2, double var4, double var6, int var8);

    private static native double nPlotHistogram(String var0, long[] var1, int var2, double var3, int var5);

    public static double plotHistogram(String labelId, float[] values) {
        return ImPlot.nPlotHistogram(labelId, values);
    }

    public static double plotHistogram(String labelId, float[] values, int bins) {
        return ImPlot.nPlotHistogram(labelId, values, bins);
    }

    public static double plotHistogram(String labelId, float[] values, int bins, double barScale) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale);
    }

    public static double plotHistogram(String labelId, float[] values, int bins, double barScale, ImPlotRange range) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, range.min, range.max);
    }

    public static double plotHistogram(String labelId, float[] values, int bins, double barScale, double rangeMin, double rangeMax) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, rangeMin, rangeMax);
    }

    public static double plotHistogram(String labelId, float[] values, int bins, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, range.min, range.max, flags);
    }

    public static double plotHistogram(String labelId, float[] values, int bins, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogram(String labelId, float[] values, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, barScale, range.min, range.max, flags);
    }

    public static double plotHistogram(String labelId, float[] values, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogram(String labelId, float[] values, int bins, double barScale, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, flags);
    }

    private static native double nPlotHistogram(String var0, float[] var1);

    private static native double nPlotHistogram(String var0, float[] var1, int var2);

    private static native double nPlotHistogram(String var0, float[] var1, int var2, double var3);

    private static native double nPlotHistogram(String var0, float[] var1, int var2, double var3, double var5, double var7);

    private static native double nPlotHistogram(String var0, float[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native double nPlotHistogram(String var0, float[] var1, double var2, double var4, double var6, int var8);

    private static native double nPlotHistogram(String var0, float[] var1, int var2, double var3, int var5);

    public static double plotHistogram(String labelId, double[] values) {
        return ImPlot.nPlotHistogram(labelId, values);
    }

    public static double plotHistogram(String labelId, double[] values, int bins) {
        return ImPlot.nPlotHistogram(labelId, values, bins);
    }

    public static double plotHistogram(String labelId, double[] values, int bins, double barScale) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale);
    }

    public static double plotHistogram(String labelId, double[] values, int bins, double barScale, ImPlotRange range) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, range.min, range.max);
    }

    public static double plotHistogram(String labelId, double[] values, int bins, double barScale, double rangeMin, double rangeMax) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, rangeMin, rangeMax);
    }

    public static double plotHistogram(String labelId, double[] values, int bins, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, range.min, range.max, flags);
    }

    public static double plotHistogram(String labelId, double[] values, int bins, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogram(String labelId, double[] values, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, barScale, range.min, range.max, flags);
    }

    public static double plotHistogram(String labelId, double[] values, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogram(String labelId, double[] values, int bins, double barScale, int flags) {
        return ImPlot.nPlotHistogram(labelId, values, bins, barScale, flags);
    }

    private static native double nPlotHistogram(String var0, double[] var1);

    private static native double nPlotHistogram(String var0, double[] var1, int var2);

    private static native double nPlotHistogram(String var0, double[] var1, int var2, double var3);

    private static native double nPlotHistogram(String var0, double[] var1, int var2, double var3, double var5, double var7);

    private static native double nPlotHistogram(String var0, double[] var1, int var2, double var3, double var5, double var7, int var9);

    private static native double nPlotHistogram(String var0, double[] var1, double var2, double var4, double var6, int var8);

    private static native double nPlotHistogram(String var0, double[] var1, int var2, double var3, int var5);

    public static double plotHistogramV(String labelId, short[] values, int count) {
        return ImPlot.nPlotHistogramV(labelId, values, count);
    }

    public static double plotHistogramV(String labelId, short[] values, int count, int bins) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins);
    }

    public static double plotHistogramV(String labelId, short[] values, int count, int bins, double barScale) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale);
    }

    public static double plotHistogramV(String labelId, short[] values, int count, int bins, double barScale, ImPlotRange range) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, range.min, range.max);
    }

    public static double plotHistogramV(String labelId, short[] values, int count, int bins, double barScale, double rangeMin, double rangeMax) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, rangeMin, rangeMax);
    }

    public static double plotHistogramV(String labelId, short[] values, int count, int bins, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, range.min, range.max, flags);
    }

    public static double plotHistogramV(String labelId, short[] values, int count, int bins, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogramV(String labelId, short[] values, int count, int bins, double barScale, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, flags);
    }

    private static native double nPlotHistogramV(String var0, short[] var1, int var2);

    private static native double nPlotHistogramV(String var0, short[] var1, int var2, int var3);

    private static native double nPlotHistogramV(String var0, short[] var1, int var2, int var3, double var4);

    private static native double nPlotHistogramV(String var0, short[] var1, int var2, int var3, double var4, double var6, double var8);

    private static native double nPlotHistogramV(String var0, short[] var1, int var2, int var3, double var4, double var6, double var8, int var10);

    private static native double nPlotHistogramV(String var0, short[] var1, int var2, int var3, double var4, int var6);

    public static double plotHistogramV(String labelId, int[] values, int count) {
        return ImPlot.nPlotHistogramV(labelId, values, count);
    }

    public static double plotHistogramV(String labelId, int[] values, int count, int bins) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins);
    }

    public static double plotHistogramV(String labelId, int[] values, int count, int bins, double barScale) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale);
    }

    public static double plotHistogramV(String labelId, int[] values, int count, int bins, double barScale, ImPlotRange range) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, range.min, range.max);
    }

    public static double plotHistogramV(String labelId, int[] values, int count, int bins, double barScale, double rangeMin, double rangeMax) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, rangeMin, rangeMax);
    }

    public static double plotHistogramV(String labelId, int[] values, int count, int bins, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, range.min, range.max, flags);
    }

    public static double plotHistogramV(String labelId, int[] values, int count, int bins, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogramV(String labelId, int[] values, int count, int bins, double barScale, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, flags);
    }

    private static native double nPlotHistogramV(String var0, int[] var1, int var2);

    private static native double nPlotHistogramV(String var0, int[] var1, int var2, int var3);

    private static native double nPlotHistogramV(String var0, int[] var1, int var2, int var3, double var4);

    private static native double nPlotHistogramV(String var0, int[] var1, int var2, int var3, double var4, double var6, double var8);

    private static native double nPlotHistogramV(String var0, int[] var1, int var2, int var3, double var4, double var6, double var8, int var10);

    private static native double nPlotHistogramV(String var0, int[] var1, int var2, int var3, double var4, int var6);

    public static double plotHistogramV(String labelId, long[] values, int count) {
        return ImPlot.nPlotHistogramV(labelId, values, count);
    }

    public static double plotHistogramV(String labelId, long[] values, int count, int bins) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins);
    }

    public static double plotHistogramV(String labelId, long[] values, int count, int bins, double barScale) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale);
    }

    public static double plotHistogramV(String labelId, long[] values, int count, int bins, double barScale, ImPlotRange range) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, range.min, range.max);
    }

    public static double plotHistogramV(String labelId, long[] values, int count, int bins, double barScale, double rangeMin, double rangeMax) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, rangeMin, rangeMax);
    }

    public static double plotHistogramV(String labelId, long[] values, int count, int bins, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, range.min, range.max, flags);
    }

    public static double plotHistogramV(String labelId, long[] values, int count, int bins, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogramV(String labelId, long[] values, int count, int bins, double barScale, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, flags);
    }

    private static native double nPlotHistogramV(String var0, long[] var1, int var2);

    private static native double nPlotHistogramV(String var0, long[] var1, int var2, int var3);

    private static native double nPlotHistogramV(String var0, long[] var1, int var2, int var3, double var4);

    private static native double nPlotHistogramV(String var0, long[] var1, int var2, int var3, double var4, double var6, double var8);

    private static native double nPlotHistogramV(String var0, long[] var1, int var2, int var3, double var4, double var6, double var8, int var10);

    private static native double nPlotHistogramV(String var0, long[] var1, int var2, int var3, double var4, int var6);

    public static double plotHistogramV(String labelId, float[] values, int count) {
        return ImPlot.nPlotHistogramV(labelId, values, count);
    }

    public static double plotHistogramV(String labelId, float[] values, int count, int bins) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins);
    }

    public static double plotHistogramV(String labelId, float[] values, int count, int bins, double barScale) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale);
    }

    public static double plotHistogramV(String labelId, float[] values, int count, int bins, double barScale, ImPlotRange range) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, range.min, range.max);
    }

    public static double plotHistogramV(String labelId, float[] values, int count, int bins, double barScale, double rangeMin, double rangeMax) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, rangeMin, rangeMax);
    }

    public static double plotHistogramV(String labelId, float[] values, int count, int bins, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, range.min, range.max, flags);
    }

    public static double plotHistogramV(String labelId, float[] values, int count, int bins, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogramV(String labelId, float[] values, int count, int bins, double barScale, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, flags);
    }

    private static native double nPlotHistogramV(String var0, float[] var1, int var2);

    private static native double nPlotHistogramV(String var0, float[] var1, int var2, int var3);

    private static native double nPlotHistogramV(String var0, float[] var1, int var2, int var3, double var4);

    private static native double nPlotHistogramV(String var0, float[] var1, int var2, int var3, double var4, double var6, double var8);

    private static native double nPlotHistogramV(String var0, float[] var1, int var2, int var3, double var4, double var6, double var8, int var10);

    private static native double nPlotHistogramV(String var0, float[] var1, int var2, int var3, double var4, int var6);

    public static double plotHistogramV(String labelId, double[] values, int count) {
        return ImPlot.nPlotHistogramV(labelId, values, count);
    }

    public static double plotHistogramV(String labelId, double[] values, int count, int bins) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins);
    }

    public static double plotHistogramV(String labelId, double[] values, int count, int bins, double barScale) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale);
    }

    public static double plotHistogramV(String labelId, double[] values, int count, int bins, double barScale, ImPlotRange range) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, range.min, range.max);
    }

    public static double plotHistogramV(String labelId, double[] values, int count, int bins, double barScale, double rangeMin, double rangeMax) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, rangeMin, rangeMax);
    }

    public static double plotHistogramV(String labelId, double[] values, int count, int bins, double barScale, ImPlotRange range, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, range.min, range.max, flags);
    }

    public static double plotHistogramV(String labelId, double[] values, int count, int bins, double barScale, double rangeMin, double rangeMax, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, rangeMin, rangeMax, flags);
    }

    public static double plotHistogramV(String labelId, double[] values, int count, int bins, double barScale, int flags) {
        return ImPlot.nPlotHistogramV(labelId, values, count, bins, barScale, flags);
    }

    private static native double nPlotHistogramV(String var0, double[] var1, int var2);

    private static native double nPlotHistogramV(String var0, double[] var1, int var2, int var3);

    private static native double nPlotHistogramV(String var0, double[] var1, int var2, int var3, double var4);

    private static native double nPlotHistogramV(String var0, double[] var1, int var2, int var3, double var4, double var6, double var8);

    private static native double nPlotHistogramV(String var0, double[] var1, int var2, int var3, double var4, double var6, double var8, int var10);

    private static native double nPlotHistogramV(String var0, double[] var1, int var2, int var3, double var4, int var6);

    public static double plotHistogram2D(String labelId, short[] xs, short[] ys) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys);
    }

    public static double plotHistogram2D(String labelId, short[] xs, short[] ys, int xBins) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins);
    }

    public static double plotHistogram2D(String labelId, short[] xs, short[] ys, int xBins, int yBins) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins);
    }

    public static double plotHistogram2D(String labelId, short[] xs, short[] ys, int xBins, int yBins, ImPlotRect range) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    public static double plotHistogram2D(String labelId, short[] xs, short[] ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    public static double plotHistogram2D(String labelId, short[] xs, short[] ys, int xBins, int yBins, ImPlotRect range, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, flags);
    }

    public static double plotHistogram2D(String labelId, short[] xs, short[] ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, flags);
    }

    public static double plotHistogram2D(String labelId, short[] xs, short[] ys, int xBins, int yBins, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, flags);
    }

    private static native double nPlotHistogram2D(String var0, short[] var1, short[] var2);

    private static native double nPlotHistogram2D(String var0, short[] var1, short[] var2, int var3);

    private static native double nPlotHistogram2D(String var0, short[] var1, short[] var2, int var3, int var4);

    private static native double nPlotHistogram2D(String var0, short[] var1, short[] var2, int var3, int var4, double var5, double var7, double var9, double var11);

    private static native double nPlotHistogram2D(String var0, short[] var1, short[] var2, int var3, int var4, double var5, double var7, double var9, double var11, int var13);

    private static native double nPlotHistogram2D(String var0, short[] var1, short[] var2, int var3, int var4, int var5);

    public static double plotHistogram2D(String labelId, int[] xs, int[] ys) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys);
    }

    public static double plotHistogram2D(String labelId, int[] xs, int[] ys, int xBins) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins);
    }

    public static double plotHistogram2D(String labelId, int[] xs, int[] ys, int xBins, int yBins) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins);
    }

    public static double plotHistogram2D(String labelId, int[] xs, int[] ys, int xBins, int yBins, ImPlotRect range) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    public static double plotHistogram2D(String labelId, int[] xs, int[] ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    public static double plotHistogram2D(String labelId, int[] xs, int[] ys, int xBins, int yBins, ImPlotRect range, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, flags);
    }

    public static double plotHistogram2D(String labelId, int[] xs, int[] ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, flags);
    }

    public static double plotHistogram2D(String labelId, int[] xs, int[] ys, int xBins, int yBins, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, flags);
    }

    private static native double nPlotHistogram2D(String var0, int[] var1, int[] var2);

    private static native double nPlotHistogram2D(String var0, int[] var1, int[] var2, int var3);

    private static native double nPlotHistogram2D(String var0, int[] var1, int[] var2, int var3, int var4);

    private static native double nPlotHistogram2D(String var0, int[] var1, int[] var2, int var3, int var4, double var5, double var7, double var9, double var11);

    private static native double nPlotHistogram2D(String var0, int[] var1, int[] var2, int var3, int var4, double var5, double var7, double var9, double var11, int var13);

    private static native double nPlotHistogram2D(String var0, int[] var1, int[] var2, int var3, int var4, int var5);

    public static double plotHistogram2D(String labelId, long[] xs, long[] ys) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys);
    }

    public static double plotHistogram2D(String labelId, long[] xs, long[] ys, int xBins) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins);
    }

    public static double plotHistogram2D(String labelId, long[] xs, long[] ys, int xBins, int yBins) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins);
    }

    public static double plotHistogram2D(String labelId, long[] xs, long[] ys, int xBins, int yBins, ImPlotRect range) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    public static double plotHistogram2D(String labelId, long[] xs, long[] ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    public static double plotHistogram2D(String labelId, long[] xs, long[] ys, int xBins, int yBins, ImPlotRect range, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, flags);
    }

    public static double plotHistogram2D(String labelId, long[] xs, long[] ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, flags);
    }

    public static double plotHistogram2D(String labelId, long[] xs, long[] ys, int xBins, int yBins, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, flags);
    }

    private static native double nPlotHistogram2D(String var0, long[] var1, long[] var2);

    private static native double nPlotHistogram2D(String var0, long[] var1, long[] var2, int var3);

    private static native double nPlotHistogram2D(String var0, long[] var1, long[] var2, int var3, int var4);

    private static native double nPlotHistogram2D(String var0, long[] var1, long[] var2, int var3, int var4, double var5, double var7, double var9, double var11);

    private static native double nPlotHistogram2D(String var0, long[] var1, long[] var2, int var3, int var4, double var5, double var7, double var9, double var11, int var13);

    private static native double nPlotHistogram2D(String var0, long[] var1, long[] var2, int var3, int var4, int var5);

    public static double plotHistogram2D(String labelId, float[] xs, float[] ys) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys);
    }

    public static double plotHistogram2D(String labelId, float[] xs, float[] ys, int xBins) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins);
    }

    public static double plotHistogram2D(String labelId, float[] xs, float[] ys, int xBins, int yBins) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins);
    }

    public static double plotHistogram2D(String labelId, float[] xs, float[] ys, int xBins, int yBins, ImPlotRect range) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    public static double plotHistogram2D(String labelId, float[] xs, float[] ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    public static double plotHistogram2D(String labelId, float[] xs, float[] ys, int xBins, int yBins, ImPlotRect range, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, flags);
    }

    public static double plotHistogram2D(String labelId, float[] xs, float[] ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, flags);
    }

    public static double plotHistogram2D(String labelId, float[] xs, float[] ys, int xBins, int yBins, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, flags);
    }

    private static native double nPlotHistogram2D(String var0, float[] var1, float[] var2);

    private static native double nPlotHistogram2D(String var0, float[] var1, float[] var2, int var3);

    private static native double nPlotHistogram2D(String var0, float[] var1, float[] var2, int var3, int var4);

    private static native double nPlotHistogram2D(String var0, float[] var1, float[] var2, int var3, int var4, double var5, double var7, double var9, double var11);

    private static native double nPlotHistogram2D(String var0, float[] var1, float[] var2, int var3, int var4, double var5, double var7, double var9, double var11, int var13);

    private static native double nPlotHistogram2D(String var0, float[] var1, float[] var2, int var3, int var4, int var5);

    public static double plotHistogram2D(String labelId, double[] xs, double[] ys) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys);
    }

    public static double plotHistogram2D(String labelId, double[] xs, double[] ys, int xBins) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins);
    }

    public static double plotHistogram2D(String labelId, double[] xs, double[] ys, int xBins, int yBins) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins);
    }

    public static double plotHistogram2D(String labelId, double[] xs, double[] ys, int xBins, int yBins, ImPlotRect range) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    public static double plotHistogram2D(String labelId, double[] xs, double[] ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    public static double plotHistogram2D(String labelId, double[] xs, double[] ys, int xBins, int yBins, ImPlotRect range, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, flags);
    }

    public static double plotHistogram2D(String labelId, double[] xs, double[] ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, flags);
    }

    public static double plotHistogram2D(String labelId, double[] xs, double[] ys, int xBins, int yBins, int flags) {
        return ImPlot.nPlotHistogram2D(labelId, xs, ys, xBins, yBins, flags);
    }

    private static native double nPlotHistogram2D(String var0, double[] var1, double[] var2);

    private static native double nPlotHistogram2D(String var0, double[] var1, double[] var2, int var3);

    private static native double nPlotHistogram2D(String var0, double[] var1, double[] var2, int var3, int var4);

    private static native double nPlotHistogram2D(String var0, double[] var1, double[] var2, int var3, int var4, double var5, double var7, double var9, double var11);

    private static native double nPlotHistogram2D(String var0, double[] var1, double[] var2, int var3, int var4, double var5, double var7, double var9, double var11, int var13);

    private static native double nPlotHistogram2D(String var0, double[] var1, double[] var2, int var3, int var4, int var5);

    public static double plotHistogram2DV(String labelId, short[] xs, short[] ys, int count) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count);
    }

    public static double plotHistogram2DV(String labelId, short[] xs, short[] ys, int count, int xBins) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins);
    }

    public static double plotHistogram2DV(String labelId, short[] xs, short[] ys, int count, int xBins, int yBins) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins);
    }

    public static double plotHistogram2DV(String labelId, short[] xs, short[] ys, int count, int xBins, int yBins, ImPlotRect range) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    public static double plotHistogram2DV(String labelId, short[] xs, short[] ys, int count, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    public static double plotHistogram2DV(String labelId, short[] xs, short[] ys, int count, int xBins, int yBins, ImPlotRect range, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, flags);
    }

    public static double plotHistogram2DV(String labelId, short[] xs, short[] ys, int count, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, flags);
    }

    public static double plotHistogram2DV(String labelId, short[] xs, short[] ys, int count, int xBins, int yBins, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, flags);
    }

    private static native double nPlotHistogram2DV(String var0, short[] var1, short[] var2, int var3);

    private static native double nPlotHistogram2DV(String var0, short[] var1, short[] var2, int var3, int var4);

    private static native double nPlotHistogram2DV(String var0, short[] var1, short[] var2, int var3, int var4, int var5);

    private static native double nPlotHistogram2DV(String var0, short[] var1, short[] var2, int var3, int var4, int var5, double var6, double var8, double var10, double var12);

    private static native double nPlotHistogram2DV(String var0, short[] var1, short[] var2, int var3, int var4, int var5, double var6, double var8, double var10, double var12, int var14);

    private static native double nPlotHistogram2DV(String var0, short[] var1, short[] var2, int var3, int var4, int var5, int var6);

    public static double plotHistogram2DV(String labelId, int[] xs, int[] ys, int count) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count);
    }

    public static double plotHistogram2DV(String labelId, int[] xs, int[] ys, int count, int xBins) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins);
    }

    public static double plotHistogram2DV(String labelId, int[] xs, int[] ys, int count, int xBins, int yBins) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins);
    }

    public static double plotHistogram2DV(String labelId, int[] xs, int[] ys, int count, int xBins, int yBins, ImPlotRect range) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    public static double plotHistogram2DV(String labelId, int[] xs, int[] ys, int count, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    public static double plotHistogram2DV(String labelId, int[] xs, int[] ys, int count, int xBins, int yBins, ImPlotRect range, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, flags);
    }

    public static double plotHistogram2DV(String labelId, int[] xs, int[] ys, int count, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, flags);
    }

    public static double plotHistogram2DV(String labelId, int[] xs, int[] ys, int count, int xBins, int yBins, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, flags);
    }

    private static native double nPlotHistogram2DV(String var0, int[] var1, int[] var2, int var3);

    private static native double nPlotHistogram2DV(String var0, int[] var1, int[] var2, int var3, int var4);

    private static native double nPlotHistogram2DV(String var0, int[] var1, int[] var2, int var3, int var4, int var5);

    private static native double nPlotHistogram2DV(String var0, int[] var1, int[] var2, int var3, int var4, int var5, double var6, double var8, double var10, double var12);

    private static native double nPlotHistogram2DV(String var0, int[] var1, int[] var2, int var3, int var4, int var5, double var6, double var8, double var10, double var12, int var14);

    private static native double nPlotHistogram2DV(String var0, int[] var1, int[] var2, int var3, int var4, int var5, int var6);

    public static double plotHistogram2DV(String labelId, long[] xs, long[] ys, int count) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count);
    }

    public static double plotHistogram2DV(String labelId, long[] xs, long[] ys, int count, int xBins) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins);
    }

    public static double plotHistogram2DV(String labelId, long[] xs, long[] ys, int count, int xBins, int yBins) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins);
    }

    public static double plotHistogram2DV(String labelId, long[] xs, long[] ys, int count, int xBins, int yBins, ImPlotRect range) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    public static double plotHistogram2DV(String labelId, long[] xs, long[] ys, int count, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    public static double plotHistogram2DV(String labelId, long[] xs, long[] ys, int count, int xBins, int yBins, ImPlotRect range, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, flags);
    }

    public static double plotHistogram2DV(String labelId, long[] xs, long[] ys, int count, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, flags);
    }

    public static double plotHistogram2DV(String labelId, long[] xs, long[] ys, int count, int xBins, int yBins, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, flags);
    }

    private static native double nPlotHistogram2DV(String var0, long[] var1, long[] var2, int var3);

    private static native double nPlotHistogram2DV(String var0, long[] var1, long[] var2, int var3, int var4);

    private static native double nPlotHistogram2DV(String var0, long[] var1, long[] var2, int var3, int var4, int var5);

    private static native double nPlotHistogram2DV(String var0, long[] var1, long[] var2, int var3, int var4, int var5, double var6, double var8, double var10, double var12);

    private static native double nPlotHistogram2DV(String var0, long[] var1, long[] var2, int var3, int var4, int var5, double var6, double var8, double var10, double var12, int var14);

    private static native double nPlotHistogram2DV(String var0, long[] var1, long[] var2, int var3, int var4, int var5, int var6);

    public static double plotHistogram2DV(String labelId, float[] xs, float[] ys, int count) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count);
    }

    public static double plotHistogram2DV(String labelId, float[] xs, float[] ys, int count, int xBins) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins);
    }

    public static double plotHistogram2DV(String labelId, float[] xs, float[] ys, int count, int xBins, int yBins) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins);
    }

    public static double plotHistogram2DV(String labelId, float[] xs, float[] ys, int count, int xBins, int yBins, ImPlotRect range) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    public static double plotHistogram2DV(String labelId, float[] xs, float[] ys, int count, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    public static double plotHistogram2DV(String labelId, float[] xs, float[] ys, int count, int xBins, int yBins, ImPlotRect range, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, flags);
    }

    public static double plotHistogram2DV(String labelId, float[] xs, float[] ys, int count, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, flags);
    }

    public static double plotHistogram2DV(String labelId, float[] xs, float[] ys, int count, int xBins, int yBins, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, flags);
    }

    private static native double nPlotHistogram2DV(String var0, float[] var1, float[] var2, int var3);

    private static native double nPlotHistogram2DV(String var0, float[] var1, float[] var2, int var3, int var4);

    private static native double nPlotHistogram2DV(String var0, float[] var1, float[] var2, int var3, int var4, int var5);

    private static native double nPlotHistogram2DV(String var0, float[] var1, float[] var2, int var3, int var4, int var5, double var6, double var8, double var10, double var12);

    private static native double nPlotHistogram2DV(String var0, float[] var1, float[] var2, int var3, int var4, int var5, double var6, double var8, double var10, double var12, int var14);

    private static native double nPlotHistogram2DV(String var0, float[] var1, float[] var2, int var3, int var4, int var5, int var6);

    public static double plotHistogram2DV(String labelId, double[] xs, double[] ys, int count) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count);
    }

    public static double plotHistogram2DV(String labelId, double[] xs, double[] ys, int count, int xBins) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins);
    }

    public static double plotHistogram2DV(String labelId, double[] xs, double[] ys, int count, int xBins, int yBins) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins);
    }

    public static double plotHistogram2DV(String labelId, double[] xs, double[] ys, int count, int xBins, int yBins, ImPlotRect range) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    public static double plotHistogram2DV(String labelId, double[] xs, double[] ys, int count, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    public static double plotHistogram2DV(String labelId, double[] xs, double[] ys, int count, int xBins, int yBins, ImPlotRect range, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, flags);
    }

    public static double plotHistogram2DV(String labelId, double[] xs, double[] ys, int count, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, flags);
    }

    public static double plotHistogram2DV(String labelId, double[] xs, double[] ys, int count, int xBins, int yBins, int flags) {
        return ImPlot.nPlotHistogram2DV(labelId, xs, ys, count, xBins, yBins, flags);
    }

    private static native double nPlotHistogram2DV(String var0, double[] var1, double[] var2, int var3);

    private static native double nPlotHistogram2DV(String var0, double[] var1, double[] var2, int var3, int var4);

    private static native double nPlotHistogram2DV(String var0, double[] var1, double[] var2, int var3, int var4, int var5);

    private static native double nPlotHistogram2DV(String var0, double[] var1, double[] var2, int var3, int var4, int var5, double var6, double var8, double var10, double var12);

    private static native double nPlotHistogram2DV(String var0, double[] var1, double[] var2, int var3, int var4, int var5, double var6, double var8, double var10, double var12, int var14);

    private static native double nPlotHistogram2DV(String var0, double[] var1, double[] var2, int var3, int var4, int var5, int var6);

    public static void plotDigital(String labelId, short[] xs, short[] ys) {
        ImPlot.nPlotDigital(labelId, xs, ys);
    }

    public static void plotDigital(String labelId, short[] xs, short[] ys, int flags) {
        ImPlot.nPlotDigital(labelId, xs, ys, flags);
    }

    public static void plotDigital(String labelId, short[] xs, short[] ys, int flags, int offset) {
        ImPlot.nPlotDigital(labelId, xs, ys, flags, offset);
    }

    private static native void nPlotDigital(String var0, short[] var1, short[] var2);

    private static native void nPlotDigital(String var0, short[] var1, short[] var2, int var3);

    private static native void nPlotDigital(String var0, short[] var1, short[] var2, int var3, int var4);

    public static void plotDigital(String labelId, int[] xs, int[] ys) {
        ImPlot.nPlotDigital(labelId, xs, ys);
    }

    public static void plotDigital(String labelId, int[] xs, int[] ys, int flags) {
        ImPlot.nPlotDigital(labelId, xs, ys, flags);
    }

    public static void plotDigital(String labelId, int[] xs, int[] ys, int flags, int offset) {
        ImPlot.nPlotDigital(labelId, xs, ys, flags, offset);
    }

    private static native void nPlotDigital(String var0, int[] var1, int[] var2);

    private static native void nPlotDigital(String var0, int[] var1, int[] var2, int var3);

    private static native void nPlotDigital(String var0, int[] var1, int[] var2, int var3, int var4);

    public static void plotDigital(String labelId, long[] xs, long[] ys) {
        ImPlot.nPlotDigital(labelId, xs, ys);
    }

    public static void plotDigital(String labelId, long[] xs, long[] ys, int flags) {
        ImPlot.nPlotDigital(labelId, xs, ys, flags);
    }

    public static void plotDigital(String labelId, long[] xs, long[] ys, int flags, int offset) {
        ImPlot.nPlotDigital(labelId, xs, ys, flags, offset);
    }

    private static native void nPlotDigital(String var0, long[] var1, long[] var2);

    private static native void nPlotDigital(String var0, long[] var1, long[] var2, int var3);

    private static native void nPlotDigital(String var0, long[] var1, long[] var2, int var3, int var4);

    public static void plotDigital(String labelId, float[] xs, float[] ys) {
        ImPlot.nPlotDigital(labelId, xs, ys);
    }

    public static void plotDigital(String labelId, float[] xs, float[] ys, int flags) {
        ImPlot.nPlotDigital(labelId, xs, ys, flags);
    }

    public static void plotDigital(String labelId, float[] xs, float[] ys, int flags, int offset) {
        ImPlot.nPlotDigital(labelId, xs, ys, flags, offset);
    }

    private static native void nPlotDigital(String var0, float[] var1, float[] var2);

    private static native void nPlotDigital(String var0, float[] var1, float[] var2, int var3);

    private static native void nPlotDigital(String var0, float[] var1, float[] var2, int var3, int var4);

    public static void plotDigital(String labelId, double[] xs, double[] ys) {
        ImPlot.nPlotDigital(labelId, xs, ys);
    }

    public static void plotDigital(String labelId, double[] xs, double[] ys, int flags) {
        ImPlot.nPlotDigital(labelId, xs, ys, flags);
    }

    public static void plotDigital(String labelId, double[] xs, double[] ys, int flags, int offset) {
        ImPlot.nPlotDigital(labelId, xs, ys, flags, offset);
    }

    private static native void nPlotDigital(String var0, double[] var1, double[] var2);

    private static native void nPlotDigital(String var0, double[] var1, double[] var2, int var3);

    private static native void nPlotDigital(String var0, double[] var1, double[] var2, int var3, int var4);

    public static void plotDigitalV(String labelId, short[] xs, short[] ys, int count) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count);
    }

    public static void plotDigitalV(String labelId, short[] xs, short[] ys, int count, int flags) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count, flags);
    }

    public static void plotDigitalV(String labelId, short[] xs, short[] ys, int count, int flags, int offset) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotDigitalV(String var0, short[] var1, short[] var2, int var3);

    private static native void nPlotDigitalV(String var0, short[] var1, short[] var2, int var3, int var4);

    private static native void nPlotDigitalV(String var0, short[] var1, short[] var2, int var3, int var4, int var5);

    public static void plotDigitalV(String labelId, int[] xs, int[] ys, int count) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count);
    }

    public static void plotDigitalV(String labelId, int[] xs, int[] ys, int count, int flags) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count, flags);
    }

    public static void plotDigitalV(String labelId, int[] xs, int[] ys, int count, int flags, int offset) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotDigitalV(String var0, int[] var1, int[] var2, int var3);

    private static native void nPlotDigitalV(String var0, int[] var1, int[] var2, int var3, int var4);

    private static native void nPlotDigitalV(String var0, int[] var1, int[] var2, int var3, int var4, int var5);

    public static void plotDigitalV(String labelId, long[] xs, long[] ys, int count) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count);
    }

    public static void plotDigitalV(String labelId, long[] xs, long[] ys, int count, int flags) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count, flags);
    }

    public static void plotDigitalV(String labelId, long[] xs, long[] ys, int count, int flags, int offset) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotDigitalV(String var0, long[] var1, long[] var2, int var3);

    private static native void nPlotDigitalV(String var0, long[] var1, long[] var2, int var3, int var4);

    private static native void nPlotDigitalV(String var0, long[] var1, long[] var2, int var3, int var4, int var5);

    public static void plotDigitalV(String labelId, float[] xs, float[] ys, int count) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count);
    }

    public static void plotDigitalV(String labelId, float[] xs, float[] ys, int count, int flags) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count, flags);
    }

    public static void plotDigitalV(String labelId, float[] xs, float[] ys, int count, int flags, int offset) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotDigitalV(String var0, float[] var1, float[] var2, int var3);

    private static native void nPlotDigitalV(String var0, float[] var1, float[] var2, int var3, int var4);

    private static native void nPlotDigitalV(String var0, float[] var1, float[] var2, int var3, int var4, int var5);

    public static void plotDigitalV(String labelId, double[] xs, double[] ys, int count) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count);
    }

    public static void plotDigitalV(String labelId, double[] xs, double[] ys, int count, int flags) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count, flags);
    }

    public static void plotDigitalV(String labelId, double[] xs, double[] ys, int count, int flags, int offset) {
        ImPlot.nPlotDigitalV(labelId, xs, ys, count, flags, offset);
    }

    private static native void nPlotDigitalV(String var0, double[] var1, double[] var2, int var3);

    private static native void nPlotDigitalV(String var0, double[] var1, double[] var2, int var3, int var4);

    private static native void nPlotDigitalV(String var0, double[] var1, double[] var2, int var3, int var4, int var5);

    public static void plotImage(String labelId, long userTextureId, ImPlotPoint boundsMin, ImPlotPoint boundsMax) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    public static void plotImage(String labelId, long userTextureId, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    public static void plotImage(String labelId, long userTextureId, ImPlotPoint boundsMin, ImPlotPoint boundsMax, ImVec2 uv0) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0.x, uv0.y);
    }

    public static void plotImage(String labelId, long userTextureId, ImPlotPoint boundsMin, ImPlotPoint boundsMax, float uv0X, float uv0Y) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0X, uv0Y);
    }

    public static void plotImage(String labelId, long userTextureId, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, ImVec2 uv0) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, uv0.x, uv0.y);
    }

    public static void plotImage(String labelId, long userTextureId, ImPlotPoint boundsMin, ImPlotPoint boundsMax, ImVec2 uv0, ImVec2 uv1) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0.x, uv0.y, uv1.x, uv1.y);
    }

    public static void plotImage(String labelId, long userTextureId, ImPlotPoint boundsMin, ImPlotPoint boundsMax, float uv0X, float uv0Y, float uv1X, float uv1Y) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0X, uv0Y, uv1X, uv1Y);
    }

    public static void plotImage(String labelId, long userTextureId, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, ImVec2 uv0, ImVec2 uv1) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, uv0.x, uv0.y, uv1.x, uv1.y);
    }

    public static void plotImage(String labelId, long userTextureId, ImPlotPoint boundsMin, ImPlotPoint boundsMax, ImVec2 uv0, ImVec2 uv1, ImVec4 tintCol) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0.x, uv0.y, uv1.x, uv1.y, tintCol.x, tintCol.y, tintCol.z, tintCol.w);
    }

    public static void plotImage(String labelId, long userTextureId, ImPlotPoint boundsMin, ImPlotPoint boundsMax, float uv0X, float uv0Y, float uv1X, float uv1Y, float tintColX, float tintColY, float tintColZ, float tintColW) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0X, uv0Y, uv1X, uv1Y, tintColX, tintColY, tintColZ, tintColW);
    }

    public static void plotImage(String labelId, long userTextureId, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, ImVec2 uv0, ImVec2 uv1, ImVec4 tintCol) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, uv0.x, uv0.y, uv1.x, uv1.y, tintCol.x, tintCol.y, tintCol.z, tintCol.w);
    }

    public static void plotImage(String labelId, long userTextureId, ImPlotPoint boundsMin, ImPlotPoint boundsMax, ImVec2 uv0, ImVec2 uv1, ImVec4 tintCol, int flags) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0.x, uv0.y, uv1.x, uv1.y, tintCol.x, tintCol.y, tintCol.z, tintCol.w, flags);
    }

    public static void plotImage(String labelId, long userTextureId, ImPlotPoint boundsMin, ImPlotPoint boundsMax, float uv0X, float uv0Y, float uv1X, float uv1Y, float tintColX, float tintColY, float tintColZ, float tintColW, int flags) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0X, uv0Y, uv1X, uv1Y, tintColX, tintColY, tintColZ, tintColW, flags);
    }

    public static void plotImage(String labelId, long userTextureId, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, ImVec2 uv0, ImVec2 uv1, ImVec4 tintCol, int flags) {
        ImPlot.nPlotImage(labelId, userTextureId, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, uv0.x, uv0.y, uv1.x, uv1.y, tintCol.x, tintCol.y, tintCol.z, tintCol.w, flags);
    }

    private static native void nPlotImage(String var0, long var1, double var3, double var5, double var7, double var9);

    private static native void nPlotImage(String var0, long var1, double var3, double var5, double var7, double var9, float var11, float var12);

    private static native void nPlotImage(String var0, long var1, double var3, double var5, double var7, double var9, float var11, float var12, float var13, float var14);

    private static native void nPlotImage(String var0, long var1, double var3, double var5, double var7, double var9, float var11, float var12, float var13, float var14, float var15, float var16, float var17, float var18);

    private static native void nPlotImage(String var0, long var1, double var3, double var5, double var7, double var9, float var11, float var12, float var13, float var14, float var15, float var16, float var17, float var18, int var19);

    public static void plotText(String text, double x, double y) {
        ImPlot.nPlotText(text, x, y);
    }

    public static void plotText(String text, double x, double y, ImVec2 pixOffset) {
        ImPlot.nPlotText(text, x, y, pixOffset.x, pixOffset.y);
    }

    public static void plotText(String text, double x, double y, float pixOffsetX, float pixOffsetY) {
        ImPlot.nPlotText(text, x, y, pixOffsetX, pixOffsetY);
    }

    public static void plotText(String text, double x, double y, ImVec2 pixOffset, int flags) {
        ImPlot.nPlotText(text, x, y, pixOffset.x, pixOffset.y, flags);
    }

    public static void plotText(String text, double x, double y, float pixOffsetX, float pixOffsetY, int flags) {
        ImPlot.nPlotText(text, x, y, pixOffsetX, pixOffsetY, flags);
    }

    public static void plotText(String text, double x, double y, int flags) {
        ImPlot.nPlotText(text, x, y, flags);
    }

    private static native void nPlotText(String var0, double var1, double var3);

    private static native void nPlotText(String var0, double var1, double var3, float var5, float var6);

    private static native void nPlotText(String var0, double var1, double var3, float var5, float var6, int var7);

    private static native void nPlotText(String var0, double var1, double var3, int var5);

    public static void plotDummy(String labelID) {
        ImPlot.nPlotDummy(labelID);
    }

    public static void plotDummy(String labelID, int flags) {
        ImPlot.nPlotDummy(labelID, flags);
    }

    private static native void nPlotDummy(String var0);

    private static native void nPlotDummy(String var0, int var1);

    public static boolean dragPoint(int id, ImDouble x, ImDouble y, ImVec4 col) {
        return ImPlot.nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, col.x, col.y, col.z, col.w);
    }

    public static boolean dragPoint(int id, ImDouble x, ImDouble y, float colX, float colY, float colZ, float colW) {
        return ImPlot.nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, colX, colY, colZ, colW);
    }

    public static boolean dragPoint(int id, ImDouble x, ImDouble y, ImVec4 col, float size) {
        return ImPlot.nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, col.x, col.y, col.z, col.w, size);
    }

    public static boolean dragPoint(int id, ImDouble x, ImDouble y, float colX, float colY, float colZ, float colW, float size) {
        return ImPlot.nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, colX, colY, colZ, colW, size);
    }

    public static boolean dragPoint(int id, ImDouble x, ImDouble y, ImVec4 col, float size, int flags) {
        return ImPlot.nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, col.x, col.y, col.z, col.w, size, flags);
    }

    public static boolean dragPoint(int id, ImDouble x, ImDouble y, float colX, float colY, float colZ, float colW, float size, int flags) {
        return ImPlot.nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, colX, colY, colZ, colW, size, flags);
    }

    public static boolean dragPoint(int id, ImDouble x, ImDouble y, ImVec4 col, int flags) {
        return ImPlot.nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, col.x, col.y, col.z, col.w, flags);
    }

    public static boolean dragPoint(int id, ImDouble x, ImDouble y, float colX, float colY, float colZ, float colW, int flags) {
        return ImPlot.nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, colX, colY, colZ, colW, flags);
    }

    private static native boolean nDragPoint(int var0, double[] var1, double[] var2, float var3, float var4, float var5, float var6);

    private static native boolean nDragPoint(int var0, double[] var1, double[] var2, float var3, float var4, float var5, float var6, float var7);

    private static native boolean nDragPoint(int var0, double[] var1, double[] var2, float var3, float var4, float var5, float var6, float var7, int var8);

    private static native boolean nDragPoint(int var0, double[] var1, double[] var2, float var3, float var4, float var5, float var6, int var7);

    public static boolean dragLineX(int id, ImDouble x, ImVec4 col) {
        return ImPlot.nDragLineX(id, x != null ? x.getData() : null, col.x, col.y, col.z, col.w);
    }

    public static boolean dragLineX(int id, ImDouble x, float colX, float colY, float colZ, float colW) {
        return ImPlot.nDragLineX(id, x != null ? x.getData() : null, colX, colY, colZ, colW);
    }

    public static boolean dragLineX(int id, ImDouble x, ImVec4 col, float thickness) {
        return ImPlot.nDragLineX(id, x != null ? x.getData() : null, col.x, col.y, col.z, col.w, thickness);
    }

    public static boolean dragLineX(int id, ImDouble x, float colX, float colY, float colZ, float colW, float thickness) {
        return ImPlot.nDragLineX(id, x != null ? x.getData() : null, colX, colY, colZ, colW, thickness);
    }

    public static boolean dragLineX(int id, ImDouble x, ImVec4 col, float thickness, int flags) {
        return ImPlot.nDragLineX(id, x != null ? x.getData() : null, col.x, col.y, col.z, col.w, thickness, flags);
    }

    public static boolean dragLineX(int id, ImDouble x, float colX, float colY, float colZ, float colW, float thickness, int flags) {
        return ImPlot.nDragLineX(id, x != null ? x.getData() : null, colX, colY, colZ, colW, thickness, flags);
    }

    public static boolean dragLineX(int id, ImDouble x, ImVec4 col, int flags) {
        return ImPlot.nDragLineX(id, x != null ? x.getData() : null, col.x, col.y, col.z, col.w, flags);
    }

    public static boolean dragLineX(int id, ImDouble x, float colX, float colY, float colZ, float colW, int flags) {
        return ImPlot.nDragLineX(id, x != null ? x.getData() : null, colX, colY, colZ, colW, flags);
    }

    private static native boolean nDragLineX(int var0, double[] var1, float var2, float var3, float var4, float var5);

    private static native boolean nDragLineX(int var0, double[] var1, float var2, float var3, float var4, float var5, float var6);

    private static native boolean nDragLineX(int var0, double[] var1, float var2, float var3, float var4, float var5, float var6, int var7);

    private static native boolean nDragLineX(int var0, double[] var1, float var2, float var3, float var4, float var5, int var6);

    public static boolean dragLineY(int id, ImDouble y, ImVec4 col) {
        return ImPlot.nDragLineY(id, y != null ? y.getData() : null, col.x, col.y, col.z, col.w);
    }

    public static boolean dragLineY(int id, ImDouble y, float colX, float colY, float colZ, float colW) {
        return ImPlot.nDragLineY(id, y != null ? y.getData() : null, colX, colY, colZ, colW);
    }

    public static boolean dragLineY(int id, ImDouble y, ImVec4 col, float thickness) {
        return ImPlot.nDragLineY(id, y != null ? y.getData() : null, col.x, col.y, col.z, col.w, thickness);
    }

    public static boolean dragLineY(int id, ImDouble y, float colX, float colY, float colZ, float colW, float thickness) {
        return ImPlot.nDragLineY(id, y != null ? y.getData() : null, colX, colY, colZ, colW, thickness);
    }

    public static boolean dragLineY(int id, ImDouble y, ImVec4 col, float thickness, int flags) {
        return ImPlot.nDragLineY(id, y != null ? y.getData() : null, col.x, col.y, col.z, col.w, thickness, flags);
    }

    public static boolean dragLineY(int id, ImDouble y, float colX, float colY, float colZ, float colW, float thickness, int flags) {
        return ImPlot.nDragLineY(id, y != null ? y.getData() : null, colX, colY, colZ, colW, thickness, flags);
    }

    public static boolean dragLineY(int id, ImDouble y, ImVec4 col, int flags) {
        return ImPlot.nDragLineY(id, y != null ? y.getData() : null, col.x, col.y, col.z, col.w, flags);
    }

    public static boolean dragLineY(int id, ImDouble y, float colX, float colY, float colZ, float colW, int flags) {
        return ImPlot.nDragLineY(id, y != null ? y.getData() : null, colX, colY, colZ, colW, flags);
    }

    private static native boolean nDragLineY(int var0, double[] var1, float var2, float var3, float var4, float var5);

    private static native boolean nDragLineY(int var0, double[] var1, float var2, float var3, float var4, float var5, float var6);

    private static native boolean nDragLineY(int var0, double[] var1, float var2, float var3, float var4, float var5, float var6, int var7);

    private static native boolean nDragLineY(int var0, double[] var1, float var2, float var3, float var4, float var5, int var6);

    public static boolean dragRect(int id, ImDouble xMin, ImDouble yMin, ImDouble xMax, ImDouble yMax, ImVec4 col) {
        return ImPlot.nDragRect(id, xMin != null ? xMin.getData() : null, yMin != null ? yMin.getData() : null, xMax != null ? xMax.getData() : null, yMax != null ? yMax.getData() : null, col.x, col.y, col.z, col.w);
    }

    public static boolean dragRect(int id, ImDouble xMin, ImDouble yMin, ImDouble xMax, ImDouble yMax, float colX, float colY, float colZ, float colW) {
        return ImPlot.nDragRect(id, xMin != null ? xMin.getData() : null, yMin != null ? yMin.getData() : null, xMax != null ? xMax.getData() : null, yMax != null ? yMax.getData() : null, colX, colY, colZ, colW);
    }

    public static boolean dragRect(int id, ImDouble xMin, ImDouble yMin, ImDouble xMax, ImDouble yMax, ImVec4 col, int flags) {
        return ImPlot.nDragRect(id, xMin != null ? xMin.getData() : null, yMin != null ? yMin.getData() : null, xMax != null ? xMax.getData() : null, yMax != null ? yMax.getData() : null, col.x, col.y, col.z, col.w, flags);
    }

    public static boolean dragRect(int id, ImDouble xMin, ImDouble yMin, ImDouble xMax, ImDouble yMax, float colX, float colY, float colZ, float colW, int flags) {
        return ImPlot.nDragRect(id, xMin != null ? xMin.getData() : null, yMin != null ? yMin.getData() : null, xMax != null ? xMax.getData() : null, yMax != null ? yMax.getData() : null, colX, colY, colZ, colW, flags);
    }

    private static native boolean nDragRect(int var0, double[] var1, double[] var2, double[] var3, double[] var4, float var5, float var6, float var7, float var8);

    private static native boolean nDragRect(int var0, double[] var1, double[] var2, double[] var3, double[] var4, float var5, float var6, float var7, float var8, int var9);

    public static void annotation(double x, double y, ImVec4 col, ImVec2 pixOffset, boolean clamp) {
        ImPlot.nAnnotation(x, y, col.x, col.y, col.z, col.w, pixOffset.x, pixOffset.y, clamp);
    }

    public static void annotation(double x, double y, float colX, float colY, float colZ, float colW, float pixOffsetX, float pixOffsetY, boolean clamp) {
        ImPlot.nAnnotation(x, y, colX, colY, colZ, colW, pixOffsetX, pixOffsetY, clamp);
    }

    public static void annotation(double x, double y, ImVec4 col, ImVec2 pixOffset, boolean clamp, boolean round) {
        ImPlot.nAnnotation(x, y, col.x, col.y, col.z, col.w, pixOffset.x, pixOffset.y, clamp, round);
    }

    public static void annotation(double x, double y, float colX, float colY, float colZ, float colW, float pixOffsetX, float pixOffsetY, boolean clamp, boolean round) {
        ImPlot.nAnnotation(x, y, colX, colY, colZ, colW, pixOffsetX, pixOffsetY, clamp, round);
    }

    private static native void nAnnotation(double var0, double var2, float var4, float var5, float var6, float var7, float var8, float var9, boolean var10);

    private static native void nAnnotation(double var0, double var2, float var4, float var5, float var6, float var7, float var8, float var9, boolean var10, boolean var11);

    public static void annotation(double x, double y, ImVec4 col, ImVec2 pixOffset, boolean clamp, String fmt) {
        ImPlot.nAnnotation(x, y, col.x, col.y, col.z, col.w, pixOffset.x, pixOffset.y, clamp, fmt);
    }

    public static void annotation(double x, double y, float colX, float colY, float colZ, float colW, float pixOffsetX, float pixOffsetY, boolean clamp, String fmt) {
        ImPlot.nAnnotation(x, y, colX, colY, colZ, colW, pixOffsetX, pixOffsetY, clamp, fmt);
    }

    private static native void nAnnotation(double var0, double var2, float var4, float var5, float var6, float var7, float var8, float var9, boolean var10, String var11);

    public static void tagX(double x, ImVec4 col) {
        ImPlot.nTagX(x, col.x, col.y, col.z, col.w);
    }

    public static void tagX(double x, float colX, float colY, float colZ, float colW) {
        ImPlot.nTagX(x, colX, colY, colZ, colW);
    }

    public static void tagX(double x, ImVec4 col, boolean round) {
        ImPlot.nTagX(x, col.x, col.y, col.z, col.w, round);
    }

    public static void tagX(double x, float colX, float colY, float colZ, float colW, boolean round) {
        ImPlot.nTagX(x, colX, colY, colZ, colW, round);
    }

    private static native void nTagX(double var0, float var2, float var3, float var4, float var5);

    private static native void nTagX(double var0, float var2, float var3, float var4, float var5, boolean var6);

    public static void tagX(double x, ImVec4 col, String fmt) {
        ImPlot.nTagX(x, col.x, col.y, col.z, col.w, fmt);
    }

    public static void tagX(double x, float colX, float colY, float colZ, float colW, String fmt) {
        ImPlot.nTagX(x, colX, colY, colZ, colW, fmt);
    }

    private static native void nTagX(double var0, float var2, float var3, float var4, float var5, String var6);

    public static void tagY(double y, ImVec4 col) {
        ImPlot.nTagY(y, col.x, col.y, col.z, col.w);
    }

    public static void tagY(double y, float colX, float colY, float colZ, float colW) {
        ImPlot.nTagY(y, colX, colY, colZ, colW);
    }

    public static void tagY(double y, ImVec4 col, boolean round) {
        ImPlot.nTagY(y, col.x, col.y, col.z, col.w, round);
    }

    public static void tagY(double y, float colX, float colY, float colZ, float colW, boolean round) {
        ImPlot.nTagY(y, colX, colY, colZ, colW, round);
    }

    private static native void nTagY(double var0, float var2, float var3, float var4, float var5);

    private static native void nTagY(double var0, float var2, float var3, float var4, float var5, boolean var6);

    public static void tagY(double y, ImVec4 col, String fmt) {
        ImPlot.nTagY(y, col.x, col.y, col.z, col.w, fmt);
    }

    public static void tagY(double y, float colX, float colY, float colZ, float colW, String fmt) {
        ImPlot.nTagY(y, colX, colY, colZ, colW, fmt);
    }

    private static native void nTagY(double var0, float var2, float var3, float var4, float var5, String var6);

    public static void setAxis(int axis) {
        ImPlot.nSetAxis(axis);
    }

    private static native void nSetAxis(int var0);

    public static void setAxes(int xAxis, int yAxis) {
        ImPlot.nSetAxes(xAxis, yAxis);
    }

    private static native void nSetAxes(int var0, int var1);

    public static ImPlotPoint pixelsToPlot(ImVec2 pix) {
        ImPlotPoint dst = new ImPlotPoint();
        ImPlot.nPixelsToPlot(dst, pix.x, pix.y);
        return dst;
    }

    public static ImPlotPoint pixelsToPlot(float pixX, float pixY) {
        ImPlotPoint dst = new ImPlotPoint();
        ImPlot.nPixelsToPlot(dst, pixX, pixY);
        return dst;
    }

    public static void pixelsToPlot(ImPlotPoint dst, ImVec2 pix) {
        ImPlot.nPixelsToPlot(dst, pix.x, pix.y);
    }

    public static void pixelsToPlot(ImPlotPoint dst, float pixX, float pixY) {
        ImPlot.nPixelsToPlot(dst, pixX, pixY);
    }

    public static ImPlotPoint pixelsToPlot(ImVec2 pix, int xAxis) {
        ImPlotPoint dst = new ImPlotPoint();
        ImPlot.nPixelsToPlot(dst, pix.x, pix.y, xAxis);
        return dst;
    }

    public static ImPlotPoint pixelsToPlot(float pixX, float pixY, int xAxis) {
        ImPlotPoint dst = new ImPlotPoint();
        ImPlot.nPixelsToPlot(dst, pixX, pixY, xAxis);
        return dst;
    }

    public static void pixelsToPlot(ImPlotPoint dst, ImVec2 pix, int xAxis) {
        ImPlot.nPixelsToPlot(dst, pix.x, pix.y, xAxis);
    }

    public static void pixelsToPlot(ImPlotPoint dst, float pixX, float pixY, int xAxis) {
        ImPlot.nPixelsToPlot(dst, pixX, pixY, xAxis);
    }

    public static ImPlotPoint pixelsToPlot(ImVec2 pix, int xAxis, int yAxis) {
        ImPlotPoint dst = new ImPlotPoint();
        ImPlot.nPixelsToPlot(dst, pix.x, pix.y, xAxis, yAxis);
        return dst;
    }

    public static ImPlotPoint pixelsToPlot(float pixX, float pixY, int xAxis, int yAxis) {
        ImPlotPoint dst = new ImPlotPoint();
        ImPlot.nPixelsToPlot(dst, pixX, pixY, xAxis, yAxis);
        return dst;
    }

    public static void pixelsToPlot(ImPlotPoint dst, ImVec2 pix, int xAxis, int yAxis) {
        ImPlot.nPixelsToPlot(dst, pix.x, pix.y, xAxis, yAxis);
    }

    public static void pixelsToPlot(ImPlotPoint dst, float pixX, float pixY, int xAxis, int yAxis) {
        ImPlot.nPixelsToPlot(dst, pixX, pixY, xAxis, yAxis);
    }

    private static native void nPixelsToPlot(ImPlotPoint var0, float var1, float var2);

    private static native void nPixelsToPlot(ImPlotPoint var0, float var1, float var2, int var3);

    private static native void nPixelsToPlot(ImPlotPoint var0, float var1, float var2, int var3, int var4);

    public static ImVec2 plotToPixels(ImPlotPoint plt) {
        ImVec2 dst = new ImVec2();
        ImPlot.nPlotToPixels(dst, plt.x, plt.y);
        return dst;
    }

    public static ImVec2 plotToPixels(double pltX, double pltY) {
        ImVec2 dst = new ImVec2();
        ImPlot.nPlotToPixels(dst, pltX, pltY);
        return dst;
    }

    public static float plotToPixelsX(ImPlotPoint plt) {
        return ImPlot.nPlotToPixelsX(plt.x, plt.y);
    }

    public static float plotToPixelsY(ImPlotPoint plt) {
        return ImPlot.nPlotToPixelsY(plt.x, plt.y);
    }

    public static void plotToPixels(ImVec2 dst, ImPlotPoint plt) {
        ImPlot.nPlotToPixels(dst, plt.x, plt.y);
    }

    public static void plotToPixels(ImVec2 dst, double pltX, double pltY) {
        ImPlot.nPlotToPixels(dst, pltX, pltY);
    }

    public static ImVec2 plotToPixels(ImPlotPoint plt, int xAxis) {
        ImVec2 dst = new ImVec2();
        ImPlot.nPlotToPixels(dst, plt.x, plt.y, xAxis);
        return dst;
    }

    public static ImVec2 plotToPixels(double pltX, double pltY, int xAxis) {
        ImVec2 dst = new ImVec2();
        ImPlot.nPlotToPixels(dst, pltX, pltY, xAxis);
        return dst;
    }

    public static float plotToPixelsX(ImPlotPoint plt, int xAxis) {
        return ImPlot.nPlotToPixelsX(plt.x, plt.y, xAxis);
    }

    public static float plotToPixelsY(ImPlotPoint plt, int xAxis) {
        return ImPlot.nPlotToPixelsY(plt.x, plt.y, xAxis);
    }

    public static void plotToPixels(ImVec2 dst, ImPlotPoint plt, int xAxis) {
        ImPlot.nPlotToPixels(dst, plt.x, plt.y, xAxis);
    }

    public static void plotToPixels(ImVec2 dst, double pltX, double pltY, int xAxis) {
        ImPlot.nPlotToPixels(dst, pltX, pltY, xAxis);
    }

    public static ImVec2 plotToPixels(ImPlotPoint plt, int xAxis, int yAxis) {
        ImVec2 dst = new ImVec2();
        ImPlot.nPlotToPixels(dst, plt.x, plt.y, xAxis, yAxis);
        return dst;
    }

    public static ImVec2 plotToPixels(double pltX, double pltY, int xAxis, int yAxis) {
        ImVec2 dst = new ImVec2();
        ImPlot.nPlotToPixels(dst, pltX, pltY, xAxis, yAxis);
        return dst;
    }

    public static float plotToPixelsX(ImPlotPoint plt, int xAxis, int yAxis) {
        return ImPlot.nPlotToPixelsX(plt.x, plt.y, xAxis, yAxis);
    }

    public static float plotToPixelsY(ImPlotPoint plt, int xAxis, int yAxis) {
        return ImPlot.nPlotToPixelsY(plt.x, plt.y, xAxis, yAxis);
    }

    public static void plotToPixels(ImVec2 dst, ImPlotPoint plt, int xAxis, int yAxis) {
        ImPlot.nPlotToPixels(dst, plt.x, plt.y, xAxis, yAxis);
    }

    public static void plotToPixels(ImVec2 dst, double pltX, double pltY, int xAxis, int yAxis) {
        ImPlot.nPlotToPixels(dst, pltX, pltY, xAxis, yAxis);
    }

    private static native void nPlotToPixels(ImVec2 var0, double var1, double var3);

    private static native float nPlotToPixelsX(double var0, double var2);

    private static native float nPlotToPixelsY(double var0, double var2);

    private static native void nPlotToPixels(ImVec2 var0, double var1, double var3, int var5);

    private static native float nPlotToPixelsX(double var0, double var2, int var4);

    private static native float nPlotToPixelsY(double var0, double var2, int var4);

    private static native void nPlotToPixels(ImVec2 var0, double var1, double var3, int var5, int var6);

    private static native float nPlotToPixelsX(double var0, double var2, int var4, int var5);

    private static native float nPlotToPixelsY(double var0, double var2, int var4, int var5);

    public static ImVec2 getPlotPos() {
        ImVec2 dst = new ImVec2();
        ImPlot.nGetPlotPos(dst);
        return dst;
    }

    public static float getPlotPosX() {
        return ImPlot.nGetPlotPosX();
    }

    public static float getPlotPosY() {
        return ImPlot.nGetPlotPosY();
    }

    public static void getPlotPos(ImVec2 dst) {
        ImPlot.nGetPlotPos(dst);
    }

    private static native void nGetPlotPos(ImVec2 var0);

    private static native float nGetPlotPosX();

    private static native float nGetPlotPosY();

    public static ImVec2 getPlotSize() {
        ImVec2 dst = new ImVec2();
        ImPlot.nGetPlotSize(dst);
        return dst;
    }

    public static float getPlotSizeX() {
        return ImPlot.nGetPlotSizeX();
    }

    public static float getPlotSizeY() {
        return ImPlot.nGetPlotSizeY();
    }

    public static void getPlotSize(ImVec2 dst) {
        ImPlot.nGetPlotSize(dst);
    }

    private static native void nGetPlotSize(ImVec2 var0);

    private static native float nGetPlotSizeX();

    private static native float nGetPlotSizeY();

    public static ImPlotPoint getPlotMousePos() {
        ImPlotPoint dst = new ImPlotPoint();
        ImPlot.nGetPlotMousePos(dst);
        return dst;
    }

    public static void getPlotMousePos(ImPlotPoint dst) {
        ImPlot.nGetPlotMousePos(dst);
    }

    public static ImPlotPoint getPlotMousePos(int xAxis) {
        ImPlotPoint dst = new ImPlotPoint();
        ImPlot.nGetPlotMousePos(dst, xAxis);
        return dst;
    }

    public static void getPlotMousePos(ImPlotPoint dst, int xAxis) {
        ImPlot.nGetPlotMousePos(dst, xAxis);
    }

    public static ImPlotPoint getPlotMousePos(int xAxis, int yAxis) {
        ImPlotPoint dst = new ImPlotPoint();
        ImPlot.nGetPlotMousePos(dst, xAxis, yAxis);
        return dst;
    }

    public static void getPlotMousePos(ImPlotPoint dst, int xAxis, int yAxis) {
        ImPlot.nGetPlotMousePos(dst, xAxis, yAxis);
    }

    private static native void nGetPlotMousePos(ImPlotPoint var0);

    private static native void nGetPlotMousePos(ImPlotPoint var0, int var1);

    private static native void nGetPlotMousePos(ImPlotPoint var0, int var1, int var2);

    public static ImPlotRect getPlotLimits() {
        ImPlotRect dst = new ImPlotRect();
        ImPlot.nGetPlotLimits(dst);
        return dst;
    }

    public static void getPlotLimits(ImPlotRect dst) {
        ImPlot.nGetPlotLimits(dst);
    }

    public static ImPlotRect getPlotLimits(int xAxis) {
        ImPlotRect dst = new ImPlotRect();
        ImPlot.nGetPlotLimits(dst, xAxis);
        return dst;
    }

    public static void getPlotLimits(ImPlotRect dst, int xAxis) {
        ImPlot.nGetPlotLimits(dst, xAxis);
    }

    public static ImPlotRect getPlotLimits(int xAxis, int yAxis) {
        ImPlotRect dst = new ImPlotRect();
        ImPlot.nGetPlotLimits(dst, xAxis, yAxis);
        return dst;
    }

    public static void getPlotLimits(ImPlotRect dst, int xAxis, int yAxis) {
        ImPlot.nGetPlotLimits(dst, xAxis, yAxis);
    }

    private static native void nGetPlotLimits(ImPlotRect var0);

    private static native void nGetPlotLimits(ImPlotRect var0, int var1);

    private static native void nGetPlotLimits(ImPlotRect var0, int var1, int var2);

    public static boolean isPlotHovered() {
        return ImPlot.nIsPlotHovered();
    }

    private static native boolean nIsPlotHovered();

    public static boolean isAxisHovered(int axis) {
        return ImPlot.nIsAxisHovered(axis);
    }

    private static native boolean nIsAxisHovered(int var0);

    public static boolean isSubplotsHovered() {
        return ImPlot.nIsSubplotsHovered();
    }

    private static native boolean nIsSubplotsHovered();

    public static boolean isPlotSelected() {
        return ImPlot.nIsPlotSelected();
    }

    private static native boolean nIsPlotSelected();

    public static ImPlotRect getPlotSelection() {
        ImPlotRect dst = new ImPlotRect();
        ImPlot.nGetPlotSelection(dst);
        return dst;
    }

    public static void getPlotSelection(ImPlotRect dst) {
        ImPlot.nGetPlotSelection(dst);
    }

    public static ImPlotRect getPlotSelection(int xAxis) {
        ImPlotRect dst = new ImPlotRect();
        ImPlot.nGetPlotSelection(dst, xAxis);
        return dst;
    }

    public static void getPlotSelection(ImPlotRect dst, int xAxis) {
        ImPlot.nGetPlotSelection(dst, xAxis);
    }

    public static ImPlotRect getPlotSelection(int xAxis, int yAxis) {
        ImPlotRect dst = new ImPlotRect();
        ImPlot.nGetPlotSelection(dst, xAxis, yAxis);
        return dst;
    }

    public static void getPlotSelection(ImPlotRect dst, int xAxis, int yAxis) {
        ImPlot.nGetPlotSelection(dst, xAxis, yAxis);
    }

    private static native void nGetPlotSelection(ImPlotRect var0);

    private static native void nGetPlotSelection(ImPlotRect var0, int var1);

    private static native void nGetPlotSelection(ImPlotRect var0, int var1, int var2);

    public static void cancelPlotSelection() {
        ImPlot.nCancelPlotSelection();
    }

    private static native void nCancelPlotSelection();

    public static void hideNextItem() {
        ImPlot.nHideNextItem();
    }

    public static void hideNextItem(boolean hidden) {
        ImPlot.nHideNextItem(hidden);
    }

    public static void hideNextItem(boolean hidden, int cond) {
        ImPlot.nHideNextItem(hidden, cond);
    }

    public static void hideNextItem(int cond) {
        ImPlot.nHideNextItem(cond);
    }

    private static native void nHideNextItem();

    private static native void nHideNextItem(boolean var0);

    private static native void nHideNextItem(boolean var0, int var1);

    private static native void nHideNextItem(int var0);

    public static boolean beginAlignedPlots(String groupId) {
        return ImPlot.nBeginAlignedPlots(groupId);
    }

    public static boolean beginAlignedPlots(String groupId, boolean vertical) {
        return ImPlot.nBeginAlignedPlots(groupId, vertical);
    }

    private static native boolean nBeginAlignedPlots(String var0);

    private static native boolean nBeginAlignedPlots(String var0, boolean var1);

    public static void endAlignedPlots() {
        ImPlot.nEndAlignedPlots();
    }

    private static native void nEndAlignedPlots();

    public static boolean beginLegendPopup(String labelId) {
        return ImPlot.nBeginLegendPopup(labelId);
    }

    public static boolean beginLegendPopup(String labelId, int mouseButton) {
        return ImPlot.nBeginLegendPopup(labelId, mouseButton);
    }

    private static native boolean nBeginLegendPopup(String var0);

    private static native boolean nBeginLegendPopup(String var0, int var1);

    public static void endLegendPopup() {
        ImPlot.nEndLegendPopup();
    }

    private static native void nEndLegendPopup();

    public static boolean isLegendEntryHovered(String labelId) {
        return ImPlot.nIsLegendEntryHovered(labelId);
    }

    private static native boolean nIsLegendEntryHovered(String var0);

    public static boolean beginDragDropTargetPlot() {
        return ImPlot.nBeginDragDropTargetPlot();
    }

    private static native boolean nBeginDragDropTargetPlot();

    public static boolean beginDragDropTargetAxis(int axis) {
        return ImPlot.nBeginDragDropTargetAxis(axis);
    }

    private static native boolean nBeginDragDropTargetAxis(int var0);

    public static boolean beginDragDropTargetLegend() {
        return ImPlot.nBeginDragDropTargetLegend();
    }

    private static native boolean nBeginDragDropTargetLegend();

    public static void endDragDropTarget() {
        ImPlot.nEndDragDropTarget();
    }

    private static native void nEndDragDropTarget();

    public static boolean beginDragDropSourcePlot() {
        return ImPlot.nBeginDragDropSourcePlot();
    }

    public static boolean beginDragDropSourcePlot(int flags) {
        return ImPlot.nBeginDragDropSourcePlot(flags);
    }

    private static native boolean nBeginDragDropSourcePlot();

    private static native boolean nBeginDragDropSourcePlot(int var0);

    public static boolean beginDragDropSourceAxis(int axis) {
        return ImPlot.nBeginDragDropSourceAxis(axis);
    }

    public static boolean beginDragDropSourceAxis(int axis, int flags) {
        return ImPlot.nBeginDragDropSourceAxis(axis, flags);
    }

    private static native boolean nBeginDragDropSourceAxis(int var0);

    private static native boolean nBeginDragDropSourceAxis(int var0, int var1);

    public static boolean beginDragDropSourceItem(String labelId) {
        return ImPlot.nBeginDragDropSourceItem(labelId);
    }

    public static boolean beginDragDropSourceItem(String labelId, int flags) {
        return ImPlot.nBeginDragDropSourceItem(labelId, flags);
    }

    private static native boolean nBeginDragDropSourceItem(String var0);

    private static native boolean nBeginDragDropSourceItem(String var0, int var1);

    public static void endDragDropSource() {
        ImPlot.nEndDragDropSource();
    }

    private static native void nEndDragDropSource();

    public static ImPlotStyle getStyle() {
        ImPlot._GETSTYLE_1.ptr = ImPlot.nGetStyle();
        return _GETSTYLE_1;
    }

    private static native long nGetStyle();

    public static void styleColorsAuto() {
        ImPlot.nStyleColorsAuto();
    }

    public static void styleColorsAuto(ImPlotStyle dst) {
        ImPlot.nStyleColorsAuto(dst.ptr);
    }

    private static native void nStyleColorsAuto();

    private static native void nStyleColorsAuto(long var0);

    public static void styleColorsClassic() {
        ImPlot.nStyleColorsClassic();
    }

    public static void styleColorsClassic(ImPlotStyle dst) {
        ImPlot.nStyleColorsClassic(dst.ptr);
    }

    private static native void nStyleColorsClassic();

    private static native void nStyleColorsClassic(long var0);

    public static void styleColorsDark() {
        ImPlot.nStyleColorsDark();
    }

    public static void styleColorsDark(ImPlotStyle dst) {
        ImPlot.nStyleColorsDark(dst.ptr);
    }

    private static native void nStyleColorsDark();

    private static native void nStyleColorsDark(long var0);

    public static void styleColorsLight() {
        ImPlot.nStyleColorsLight();
    }

    public static void styleColorsLight(ImPlotStyle dst) {
        ImPlot.nStyleColorsLight(dst.ptr);
    }

    private static native void nStyleColorsLight();

    private static native void nStyleColorsLight(long var0);

    public static native void pushStyleColor(int var0, long var1);

    public static void pushStyleColor(int idx, int col) {
        ImPlot.nPushStyleColor(idx, col);
    }

    private static native void nPushStyleColor(int var0, int var1);

    public static void pushStyleColor(int idx, ImVec4 col) {
        ImPlot.nPushStyleColor(idx, col.x, col.y, col.z, col.w);
    }

    public static void pushStyleColor(int idx, float colX, float colY, float colZ, float colW) {
        ImPlot.nPushStyleColor(idx, colX, colY, colZ, colW);
    }

    private static native void nPushStyleColor(int var0, float var1, float var2, float var3, float var4);

    public static void popStyleColor() {
        ImPlot.nPopStyleColor();
    }

    public static void popStyleColor(int count) {
        ImPlot.nPopStyleColor(count);
    }

    private static native void nPopStyleColor();

    private static native void nPopStyleColor(int var0);

    public static void pushStyleVar(int idx, float val) {
        ImPlot.nPushStyleVar(idx, val);
    }

    private static native void nPushStyleVar(int var0, float var1);

    public static void pushStyleVar(int idx, int val) {
        ImPlot.nPushStyleVar(idx, val);
    }

    private static native void nPushStyleVar(int var0, int var1);

    public static void pushStyleVar(int idx, ImVec2 val) {
        ImPlot.nPushStyleVar(idx, val.x, val.y);
    }

    public static void pushStyleVar(int idx, float valX, float valY) {
        ImPlot.nPushStyleVar(idx, valX, valY);
    }

    private static native void nPushStyleVar(int var0, float var1, float var2);

    public static void popStyleVar() {
        ImPlot.nPopStyleVar();
    }

    public static void popStyleVar(int count) {
        ImPlot.nPopStyleVar(count);
    }

    private static native void nPopStyleVar();

    private static native void nPopStyleVar(int var0);

    public static void setNextLineStyle() {
        ImPlot.nSetNextLineStyle();
    }

    public static void setNextLineStyle(ImVec4 col) {
        ImPlot.nSetNextLineStyle(col.x, col.y, col.z, col.w);
    }

    public static void setNextLineStyle(float colX, float colY, float colZ, float colW) {
        ImPlot.nSetNextLineStyle(colX, colY, colZ, colW);
    }

    public static void setNextLineStyle(ImVec4 col, float weight) {
        ImPlot.nSetNextLineStyle(col.x, col.y, col.z, col.w, weight);
    }

    public static void setNextLineStyle(float colX, float colY, float colZ, float colW, float weight) {
        ImPlot.nSetNextLineStyle(colX, colY, colZ, colW, weight);
    }

    public static void setNextLineStyle(float weight) {
        ImPlot.nSetNextLineStyle(weight);
    }

    private static native void nSetNextLineStyle();

    private static native void nSetNextLineStyle(float var0, float var1, float var2, float var3);

    private static native void nSetNextLineStyle(float var0, float var1, float var2, float var3, float var4);

    private static native void nSetNextLineStyle(float var0);

    public static void setNextFillStyle() {
        ImPlot.nSetNextFillStyle();
    }

    public static void setNextFillStyle(ImVec4 col) {
        ImPlot.nSetNextFillStyle(col.x, col.y, col.z, col.w);
    }

    public static void setNextFillStyle(float colX, float colY, float colZ, float colW) {
        ImPlot.nSetNextFillStyle(colX, colY, colZ, colW);
    }

    public static void setNextFillStyle(ImVec4 col, float alphaMod) {
        ImPlot.nSetNextFillStyle(col.x, col.y, col.z, col.w, alphaMod);
    }

    public static void setNextFillStyle(float colX, float colY, float colZ, float colW, float alphaMod) {
        ImPlot.nSetNextFillStyle(colX, colY, colZ, colW, alphaMod);
    }

    public static void setNextFillStyle(float alphaMod) {
        ImPlot.nSetNextFillStyle(alphaMod);
    }

    private static native void nSetNextFillStyle();

    private static native void nSetNextFillStyle(float var0, float var1, float var2, float var3);

    private static native void nSetNextFillStyle(float var0, float var1, float var2, float var3, float var4);

    private static native void nSetNextFillStyle(float var0);

    public static void setNextMarkerStyle() {
        ImPlot.nSetNextMarkerStyle();
    }

    public static void setNextMarkerStyle(int marker) {
        ImPlot.nSetNextMarkerStyle(marker);
    }

    public static void setNextMarkerStyle(int marker, float size) {
        ImPlot.nSetNextMarkerStyle(marker, size);
    }

    public static void setNextMarkerStyle(int marker, float size, ImVec4 fill) {
        ImPlot.nSetNextMarkerStyle(marker, size, fill.x, fill.y, fill.z, fill.w);
    }

    public static void setNextMarkerStyle(int marker, float size, float fillX, float fillY, float fillZ, float fillW) {
        ImPlot.nSetNextMarkerStyle(marker, size, fillX, fillY, fillZ, fillW);
    }

    public static void setNextMarkerStyle(int marker, float size, ImVec4 fill, float weight) {
        ImPlot.nSetNextMarkerStyle(marker, size, fill.x, fill.y, fill.z, fill.w, weight);
    }

    public static void setNextMarkerStyle(int marker, float size, float fillX, float fillY, float fillZ, float fillW, float weight) {
        ImPlot.nSetNextMarkerStyle(marker, size, fillX, fillY, fillZ, fillW, weight);
    }

    public static void setNextMarkerStyle(int marker, float size, ImVec4 fill, float weight, ImVec4 outline) {
        ImPlot.nSetNextMarkerStyle(marker, size, fill.x, fill.y, fill.z, fill.w, weight, outline.x, outline.y, outline.z, outline.w);
    }

    public static void setNextMarkerStyle(int marker, float size, float fillX, float fillY, float fillZ, float fillW, float weight, float outlineX, float outlineY, float outlineZ, float outlineW) {
        ImPlot.nSetNextMarkerStyle(marker, size, fillX, fillY, fillZ, fillW, weight, outlineX, outlineY, outlineZ, outlineW);
    }

    public static void setNextMarkerStyle(int marker, float size, ImVec4 fill, ImVec4 outline) {
        ImPlot.nSetNextMarkerStyle(marker, size, fill.x, fill.y, fill.z, fill.w, outline.x, outline.y, outline.z, outline.w);
    }

    public static void setNextMarkerStyle(int marker, float size, float fillX, float fillY, float fillZ, float fillW, float outlineX, float outlineY, float outlineZ, float outlineW) {
        ImPlot.nSetNextMarkerStyle(marker, size, fillX, fillY, fillZ, fillW, outlineX, outlineY, outlineZ, outlineW);
    }

    private static native void nSetNextMarkerStyle();

    private static native void nSetNextMarkerStyle(int var0);

    private static native void nSetNextMarkerStyle(int var0, float var1);

    private static native void nSetNextMarkerStyle(int var0, float var1, float var2, float var3, float var4, float var5);

    private static native void nSetNextMarkerStyle(int var0, float var1, float var2, float var3, float var4, float var5, float var6);

    private static native void nSetNextMarkerStyle(int var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10);

    private static native void nSetNextMarkerStyle(int var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9);

    public static void setNextErrorBarStyle() {
        ImPlot.nSetNextErrorBarStyle();
    }

    public static void setNextErrorBarStyle(ImVec4 col) {
        ImPlot.nSetNextErrorBarStyle(col.x, col.y, col.z, col.w);
    }

    public static void setNextErrorBarStyle(float colX, float colY, float colZ, float colW) {
        ImPlot.nSetNextErrorBarStyle(colX, colY, colZ, colW);
    }

    public static void setNextErrorBarStyle(ImVec4 col, float size) {
        ImPlot.nSetNextErrorBarStyle(col.x, col.y, col.z, col.w, size);
    }

    public static void setNextErrorBarStyle(float colX, float colY, float colZ, float colW, float size) {
        ImPlot.nSetNextErrorBarStyle(colX, colY, colZ, colW, size);
    }

    public static void setNextErrorBarStyle(ImVec4 col, float size, float weight) {
        ImPlot.nSetNextErrorBarStyle(col.x, col.y, col.z, col.w, size, weight);
    }

    public static void setNextErrorBarStyle(float colX, float colY, float colZ, float colW, float size, float weight) {
        ImPlot.nSetNextErrorBarStyle(colX, colY, colZ, colW, size, weight);
    }

    public static void setNextErrorBarStyle(float size, float weight) {
        ImPlot.nSetNextErrorBarStyle(size, weight);
    }

    private static native void nSetNextErrorBarStyle();

    private static native void nSetNextErrorBarStyle(float var0, float var1, float var2, float var3);

    private static native void nSetNextErrorBarStyle(float var0, float var1, float var2, float var3, float var4);

    private static native void nSetNextErrorBarStyle(float var0, float var1, float var2, float var3, float var4, float var5);

    private static native void nSetNextErrorBarStyle(float var0, float var1);

    public static ImVec4 getLastItemColor() {
        ImVec4 dst = new ImVec4();
        ImPlot.nGetLastItemColor(dst);
        return dst;
    }

    public static float getLastItemColorX() {
        return ImPlot.nGetLastItemColorX();
    }

    public static float getLastItemColorY() {
        return ImPlot.nGetLastItemColorY();
    }

    public static float getLastItemColorZ() {
        return ImPlot.nGetLastItemColorZ();
    }

    public static float getLastItemColorW() {
        return ImPlot.nGetLastItemColorW();
    }

    public static void getLastItemColor(ImVec4 dst) {
        ImPlot.nGetLastItemColor(dst);
    }

    private static native void nGetLastItemColor(ImVec4 var0);

    private static native float nGetLastItemColorX();

    private static native float nGetLastItemColorY();

    private static native float nGetLastItemColorZ();

    private static native float nGetLastItemColorW();

    public static String getStyleColorName(int idx) {
        return ImPlot.nGetStyleColorName(idx);
    }

    private static native String nGetStyleColorName(int var0);

    public static String getMarkerName(int idx) {
        return ImPlot.nGetMarkerName(idx);
    }

    private static native String nGetMarkerName(int var0);

    public static int addColormap(String name, ImVec4[] cols) {
        return ImPlot.nAddColormap(name, cols);
    }

    public static int addColormap(String name, ImVec4[] cols, boolean qual) {
        return ImPlot.nAddColormap(name, cols, qual);
    }

    private static native int nAddColormap(String var0, ImVec4[] var1);

    private static native int nAddColormap(String var0, ImVec4[] var1, boolean var2);

    public static int addColormap(String name, int[] cols) {
        return ImPlot.nAddColormap(name, cols);
    }

    public static int addColormap(String name, int[] cols, boolean qual) {
        return ImPlot.nAddColormap(name, cols, qual);
    }

    private static native int nAddColormap(String var0, int[] var1);

    private static native int nAddColormap(String var0, int[] var1, boolean var2);

    public static int getColormapCount() {
        return ImPlot.nGetColormapCount();
    }

    private static native int nGetColormapCount();

    public static String getColormapName(int cmap) {
        return ImPlot.nGetColormapName(cmap);
    }

    private static native String nGetColormapName(int var0);

    public static int getColormapIndex(String name) {
        return ImPlot.nGetColormapIndex(name);
    }

    private static native int nGetColormapIndex(String var0);

    public static void pushColormap(int cmap) {
        ImPlot.nPushColormap(cmap);
    }

    private static native void nPushColormap(int var0);

    public static void pushColormap(String name) {
        ImPlot.nPushColormap(name);
    }

    private static native void nPushColormap(String var0);

    public static void popColormap() {
        ImPlot.nPopColormap();
    }

    public static void popColormap(int count) {
        ImPlot.nPopColormap(count);
    }

    private static native void nPopColormap();

    private static native void nPopColormap(int var0);

    public static ImVec4 nextColormapColor() {
        ImVec4 dst = new ImVec4();
        ImPlot.nNextColormapColor(dst);
        return dst;
    }

    public static float nextColormapColorX() {
        return ImPlot.nNextColormapColorX();
    }

    public static float nextColormapColorY() {
        return ImPlot.nNextColormapColorY();
    }

    public static float nextColormapColorZ() {
        return ImPlot.nNextColormapColorZ();
    }

    public static float nextColormapColorW() {
        return ImPlot.nNextColormapColorW();
    }

    public static void nextColormapColor(ImVec4 dst) {
        ImPlot.nNextColormapColor(dst);
    }

    private static native void nNextColormapColor(ImVec4 var0);

    private static native float nNextColormapColorX();

    private static native float nNextColormapColorY();

    private static native float nNextColormapColorZ();

    private static native float nNextColormapColorW();

    public static int getColormapSize() {
        return ImPlot.nGetColormapSize();
    }

    public static int getColormapSize(int cmap) {
        return ImPlot.nGetColormapSize(cmap);
    }

    private static native int nGetColormapSize();

    private static native int nGetColormapSize(int var0);

    public static ImVec4 getColormapColor(int idx) {
        ImVec4 dst = new ImVec4();
        ImPlot.nGetColormapColor(dst, idx);
        return dst;
    }

    public static float getColormapColorX(int idx) {
        return ImPlot.nGetColormapColorX(idx);
    }

    public static float getColormapColorY(int idx) {
        return ImPlot.nGetColormapColorY(idx);
    }

    public static float getColormapColorZ(int idx) {
        return ImPlot.nGetColormapColorZ(idx);
    }

    public static float getColormapColorW(int idx) {
        return ImPlot.nGetColormapColorW(idx);
    }

    public static void getColormapColor(ImVec4 dst, int idx) {
        ImPlot.nGetColormapColor(dst, idx);
    }

    public static ImVec4 getColormapColor(int idx, int cmap) {
        ImVec4 dst = new ImVec4();
        ImPlot.nGetColormapColor(dst, idx, cmap);
        return dst;
    }

    public static float getColormapColorX(int idx, int cmap) {
        return ImPlot.nGetColormapColorX(idx, cmap);
    }

    public static float getColormapColorY(int idx, int cmap) {
        return ImPlot.nGetColormapColorY(idx, cmap);
    }

    public static float getColormapColorZ(int idx, int cmap) {
        return ImPlot.nGetColormapColorZ(idx, cmap);
    }

    public static float getColormapColorW(int idx, int cmap) {
        return ImPlot.nGetColormapColorW(idx, cmap);
    }

    public static void getColormapColor(ImVec4 dst, int idx, int cmap) {
        ImPlot.nGetColormapColor(dst, idx, cmap);
    }

    private static native void nGetColormapColor(ImVec4 var0, int var1);

    private static native float nGetColormapColorX(int var0);

    private static native float nGetColormapColorY(int var0);

    private static native float nGetColormapColorZ(int var0);

    private static native float nGetColormapColorW(int var0);

    private static native void nGetColormapColor(ImVec4 var0, int var1, int var2);

    private static native float nGetColormapColorX(int var0, int var1);

    private static native float nGetColormapColorY(int var0, int var1);

    private static native float nGetColormapColorZ(int var0, int var1);

    private static native float nGetColormapColorW(int var0, int var1);

    public static ImVec4 sampleColormap(float t) {
        ImVec4 dst = new ImVec4();
        ImPlot.nSampleColormap(dst, t);
        return dst;
    }

    public static float sampleColormapX(float t) {
        return ImPlot.nSampleColormapX(t);
    }

    public static float sampleColormapY(float t) {
        return ImPlot.nSampleColormapY(t);
    }

    public static float sampleColormapZ(float t) {
        return ImPlot.nSampleColormapZ(t);
    }

    public static float sampleColormapW(float t) {
        return ImPlot.nSampleColormapW(t);
    }

    public static void sampleColormap(ImVec4 dst, float t) {
        ImPlot.nSampleColormap(dst, t);
    }

    public static ImVec4 sampleColormap(float t, int cmap) {
        ImVec4 dst = new ImVec4();
        ImPlot.nSampleColormap(dst, t, cmap);
        return dst;
    }

    public static float sampleColormapX(float t, int cmap) {
        return ImPlot.nSampleColormapX(t, cmap);
    }

    public static float sampleColormapY(float t, int cmap) {
        return ImPlot.nSampleColormapY(t, cmap);
    }

    public static float sampleColormapZ(float t, int cmap) {
        return ImPlot.nSampleColormapZ(t, cmap);
    }

    public static float sampleColormapW(float t, int cmap) {
        return ImPlot.nSampleColormapW(t, cmap);
    }

    public static void sampleColormap(ImVec4 dst, float t, int cmap) {
        ImPlot.nSampleColormap(dst, t, cmap);
    }

    private static native void nSampleColormap(ImVec4 var0, float var1);

    private static native float nSampleColormapX(float var0);

    private static native float nSampleColormapY(float var0);

    private static native float nSampleColormapZ(float var0);

    private static native float nSampleColormapW(float var0);

    private static native void nSampleColormap(ImVec4 var0, float var1, int var2);

    private static native float nSampleColormapX(float var0, int var1);

    private static native float nSampleColormapY(float var0, int var1);

    private static native float nSampleColormapZ(float var0, int var1);

    private static native float nSampleColormapW(float var0, int var1);

    public static void colormapScale(String label, double scaleMin, double scaleMax) {
        ImPlot.nColormapScale(label, scaleMin, scaleMax);
    }

    public static void colormapScale(String label, double scaleMin, double scaleMax, ImVec2 size) {
        ImPlot.nColormapScale(label, scaleMin, scaleMax, size.x, size.y);
    }

    public static void colormapScale(String label, double scaleMin, double scaleMax, float sizeX, float sizeY) {
        ImPlot.nColormapScale(label, scaleMin, scaleMax, sizeX, sizeY);
    }

    public static void colormapScale(String label, double scaleMin, double scaleMax, ImVec2 size, String format) {
        ImPlot.nColormapScale(label, scaleMin, scaleMax, size.x, size.y, format);
    }

    public static void colormapScale(String label, double scaleMin, double scaleMax, float sizeX, float sizeY, String format) {
        ImPlot.nColormapScale(label, scaleMin, scaleMax, sizeX, sizeY, format);
    }

    public static void colormapScale(String label, double scaleMin, double scaleMax, ImVec2 size, String format, int flags) {
        ImPlot.nColormapScale(label, scaleMin, scaleMax, size.x, size.y, format, flags);
    }

    public static void colormapScale(String label, double scaleMin, double scaleMax, float sizeX, float sizeY, String format, int flags) {
        ImPlot.nColormapScale(label, scaleMin, scaleMax, sizeX, sizeY, format, flags);
    }

    public static void colormapScale(String label, double scaleMin, double scaleMax, ImVec2 size, String format, int flags, int cmap) {
        ImPlot.nColormapScale(label, scaleMin, scaleMax, size.x, size.y, format, flags, cmap);
    }

    public static void colormapScale(String label, double scaleMin, double scaleMax, float sizeX, float sizeY, String format, int flags, int cmap) {
        ImPlot.nColormapScale(label, scaleMin, scaleMax, sizeX, sizeY, format, flags, cmap);
    }

    public static void colormapScale(String label, double scaleMin, double scaleMax, String format, int flags, int cmap) {
        ImPlot.nColormapScale(label, scaleMin, scaleMax, format, flags, cmap);
    }

    public static void colormapScale(String label, double scaleMin, double scaleMax, int flags, int cmap) {
        ImPlot.nColormapScale(label, scaleMin, scaleMax, flags, cmap);
    }

    public static void colormapScale(String label, double scaleMin, double scaleMax, ImVec2 size, int flags, int cmap) {
        ImPlot.nColormapScale(label, scaleMin, scaleMax, size.x, size.y, flags, cmap);
    }

    public static void colormapScale(String label, double scaleMin, double scaleMax, float sizeX, float sizeY, int flags, int cmap) {
        ImPlot.nColormapScale(label, scaleMin, scaleMax, sizeX, sizeY, flags, cmap);
    }

    private static native void nColormapScale(String var0, double var1, double var3);

    private static native void nColormapScale(String var0, double var1, double var3, float var5, float var6);

    private static native void nColormapScale(String var0, double var1, double var3, float var5, float var6, String var7);

    private static native void nColormapScale(String var0, double var1, double var3, float var5, float var6, String var7, int var8);

    private static native void nColormapScale(String var0, double var1, double var3, float var5, float var6, String var7, int var8, int var9);

    private static native void nColormapScale(String var0, double var1, double var3, String var5, int var6, int var7);

    private static native void nColormapScale(String var0, double var1, double var3, int var5, int var6);

    private static native void nColormapScale(String var0, double var1, double var3, float var5, float var6, int var7, int var8);

    public static boolean colormapSlider(String label, ImFloat t) {
        return ImPlot.nColormapSlider(label, t != null ? t.getData() : null);
    }

    public static boolean colormapSlider(String label, ImFloat t, String format) {
        return ImPlot.nColormapSlider(label, t != null ? t.getData() : null, format);
    }

    public static boolean colormapSlider(String label, ImFloat t, String format, int cmap) {
        return ImPlot.nColormapSlider(label, t != null ? t.getData() : null, format, cmap);
    }

    public static boolean colormapSlider(String label, ImFloat t, int cmap) {
        return ImPlot.nColormapSlider(label, t != null ? t.getData() : null, cmap);
    }

    private static native boolean nColormapSlider(String var0, float[] var1);

    private static native boolean nColormapSlider(String var0, float[] var1, String var2);

    private static native boolean nColormapSlider(String var0, float[] var1, String var2, int var3);

    private static native boolean nColormapSlider(String var0, float[] var1, int var2);

    public static boolean colormapButton(String label) {
        return ImPlot.nColormapButton(label);
    }

    public static boolean colormapButton(String label, ImVec2 size) {
        return ImPlot.nColormapButton(label, size.x, size.y);
    }

    public static boolean colormapButton(String label, float sizeX, float sizeY) {
        return ImPlot.nColormapButton(label, sizeX, sizeY);
    }

    public static boolean colormapButton(String label, ImVec2 size, int cmap) {
        return ImPlot.nColormapButton(label, size.x, size.y, cmap);
    }

    public static boolean colormapButton(String label, float sizeX, float sizeY, int cmap) {
        return ImPlot.nColormapButton(label, sizeX, sizeY, cmap);
    }

    public static boolean colormapButton(String label, int cmap) {
        return ImPlot.nColormapButton(label, cmap);
    }

    private static native boolean nColormapButton(String var0);

    private static native boolean nColormapButton(String var0, float var1, float var2);

    private static native boolean nColormapButton(String var0, float var1, float var2, int var3);

    private static native boolean nColormapButton(String var0, int var1);

    public static void bustColorCache() {
        ImPlot.nBustColorCache();
    }

    public static void bustColorCache(String plotTitleId) {
        ImPlot.nBustColorCache(plotTitleId);
    }

    private static native void nBustColorCache();

    private static native void nBustColorCache(String var0);

    public static ImPlotInputMap getInputMap() {
        ImPlot._GETINPUTMAP_1.ptr = ImPlot.nGetInputMap();
        return _GETINPUTMAP_1;
    }

    private static native long nGetInputMap();

    public static void mapInputDefault() {
        ImPlot.nMapInputDefault();
    }

    public static void mapInputDefault(ImPlotInputMap dst) {
        ImPlot.nMapInputDefault(dst.ptr);
    }

    private static native void nMapInputDefault();

    private static native void nMapInputDefault(long var0);

    public static void mapInputReverse() {
        ImPlot.nMapInputReverse();
    }

    public static void mapInputReverse(ImPlotInputMap dst) {
        ImPlot.nMapInputReverse(dst.ptr);
    }

    private static native void nMapInputReverse();

    private static native void nMapInputReverse(long var0);

    public static void itemIcon(ImVec4 col) {
        ImPlot.nItemIcon(col.x, col.y, col.z, col.w);
    }

    public static void itemIcon(float colX, float colY, float colZ, float colW) {
        ImPlot.nItemIcon(colX, colY, colZ, colW);
    }

    private static native void nItemIcon(float var0, float var1, float var2, float var3);

    public static void itemIcon(int col) {
        ImPlot.nItemIcon(col);
    }

    private static native void nItemIcon(int var0);

    public static void colormapIcon(int cmap) {
        ImPlot.nColormapIcon(cmap);
    }

    private static native void nColormapIcon(int var0);

    public static ImDrawList getPlotDrawList() {
        return new ImDrawList(ImPlot.nGetPlotDrawList());
    }

    private static native long nGetPlotDrawList();

    public static void pushPlotClipRect() {
        ImPlot.nPushPlotClipRect();
    }

    public static void pushPlotClipRect(float expand) {
        ImPlot.nPushPlotClipRect(expand);
    }

    private static native void nPushPlotClipRect();

    private static native void nPushPlotClipRect(float var0);

    public static void popPlotClipRect() {
        ImPlot.nPopPlotClipRect();
    }

    private static native void nPopPlotClipRect();

    public static boolean showStyleSelector(String label) {
        return ImPlot.nShowStyleSelector(label);
    }

    private static native boolean nShowStyleSelector(String var0);

    public static boolean showColormapSelector(String label) {
        return ImPlot.nShowColormapSelector(label);
    }

    private static native boolean nShowColormapSelector(String var0);

    public static boolean showInputMapSelector(String label) {
        return ImPlot.nShowInputMapSelector(label);
    }

    private static native boolean nShowInputMapSelector(String var0);

    public static void showStyleEditor() {
        ImPlot.nShowStyleEditor();
    }

    public static void showStyleEditor(ImPlotStyle ref) {
        ImPlot.nShowStyleEditor(ref.ptr);
    }

    private static native void nShowStyleEditor();

    private static native void nShowStyleEditor(long var0);

    public static void showUserGuide() {
        ImPlot.nShowUserGuide();
    }

    private static native void nShowUserGuide();

    public static void showMetricsWindow() {
        ImPlot.nShowMetricsWindow();
    }

    public static void showMetricsWindow(ImBoolean pOpen) {
        ImPlot.nShowMetricsWindow(pOpen != null ? pOpen.getData() : null);
    }

    private static native void nShowMetricsWindow();

    private static native void nShowMetricsWindow(boolean[] var0);

    public static void showDemoWindow() {
        ImPlot.nShowDemoWindow();
    }

    public static void showDemoWindow(ImBoolean pOpen) {
        ImPlot.nShowDemoWindow(pOpen != null ? pOpen.getData() : null);
    }

    private static native void nShowDemoWindow();

    private static native void nShowDemoWindow(boolean[] var0);
}

