/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.implot;

import imgui.moulberry90.ImVec2;
import imgui.moulberry90.ImVec4;
import imgui.moulberry90.binding.ImGuiStructDestroyable;

public final class ImPlotStyle
extends ImGuiStructDestroyable {
    public ImPlotStyle() {
    }

    public ImPlotStyle(long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public float getLineWeight() {
        return this.nGetLineWeight();
    }

    public void setLineWeight(float value) {
        this.nSetLineWeight(value);
    }

    private native float nGetLineWeight();

    private native void nSetLineWeight(float var1);

    public int getMarker() {
        return this.nGetMarker();
    }

    public void setMarker(int value) {
        this.nSetMarker(value);
    }

    private native int nGetMarker();

    private native void nSetMarker(int var1);

    public float getMarkerSize() {
        return this.nGetMarkerSize();
    }

    public void setMarkerSize(float value) {
        this.nSetMarkerSize(value);
    }

    private native float nGetMarkerSize();

    private native void nSetMarkerSize(float var1);

    public float getMarkerWeight() {
        return this.nGetMarkerWeight();
    }

    public void setMarkerWeight(float value) {
        this.nSetMarkerWeight(value);
    }

    private native float nGetMarkerWeight();

    private native void nSetMarkerWeight(float var1);

    public float getFillAlpha() {
        return this.nGetFillAlpha();
    }

    public void setFillAlpha(float value) {
        this.nSetFillAlpha(value);
    }

    private native float nGetFillAlpha();

    private native void nSetFillAlpha(float var1);

    public float getErrorBarSize() {
        return this.nGetErrorBarSize();
    }

    public void setErrorBarSize(float value) {
        this.nSetErrorBarSize(value);
    }

    private native float nGetErrorBarSize();

    private native void nSetErrorBarSize(float var1);

    public float getErrorBarWeight() {
        return this.nGetErrorBarWeight();
    }

    public void setErrorBarWeight(float value) {
        this.nSetErrorBarWeight(value);
    }

    private native float nGetErrorBarWeight();

    private native void nSetErrorBarWeight(float var1);

    public float getDigitalBitHeight() {
        return this.nGetDigitalBitHeight();
    }

    public void setDigitalBitHeight(float value) {
        this.nSetDigitalBitHeight(value);
    }

    private native float nGetDigitalBitHeight();

    private native void nSetDigitalBitHeight(float var1);

    public float getDigitalBitGap() {
        return this.nGetDigitalBitGap();
    }

    public void setDigitalBitGap(float value) {
        this.nSetDigitalBitGap(value);
    }

    private native float nGetDigitalBitGap();

    private native void nSetDigitalBitGap(float var1);

    public float getPlotBorderSize() {
        return this.nGetPlotBorderSize();
    }

    public void setPlotBorderSize(float value) {
        this.nSetPlotBorderSize(value);
    }

    private native float nGetPlotBorderSize();

    private native void nSetPlotBorderSize(float var1);

    public float getMinorAlpha() {
        return this.nGetMinorAlpha();
    }

    public void setMinorAlpha(float value) {
        this.nSetMinorAlpha(value);
    }

    private native float nGetMinorAlpha();

    private native void nSetMinorAlpha(float var1);

    public ImVec2 getMajorTickLen() {
        ImVec2 dst = new ImVec2();
        this.nGetMajorTickLen(dst);
        return dst;
    }

    public float getMajorTickLenX() {
        return this.nGetMajorTickLenX();
    }

    public float getMajorTickLenY() {
        return this.nGetMajorTickLenY();
    }

    public void getMajorTickLen(ImVec2 dst) {
        this.nGetMajorTickLen(dst);
    }

    public void setMajorTickLen(ImVec2 value) {
        this.nSetMajorTickLen(value.x, value.y);
    }

    public void setMajorTickLen(float valueX, float valueY) {
        this.nSetMajorTickLen(valueX, valueY);
    }

    private native void nGetMajorTickLen(ImVec2 var1);

    private native float nGetMajorTickLenX();

    private native float nGetMajorTickLenY();

    private native void nSetMajorTickLen(float var1, float var2);

    public ImVec2 getMinorTickLen() {
        ImVec2 dst = new ImVec2();
        this.nGetMinorTickLen(dst);
        return dst;
    }

    public float getMinorTickLenX() {
        return this.nGetMinorTickLenX();
    }

    public float getMinorTickLenY() {
        return this.nGetMinorTickLenY();
    }

    public void getMinorTickLen(ImVec2 dst) {
        this.nGetMinorTickLen(dst);
    }

    public void setMinorTickLen(ImVec2 value) {
        this.nSetMinorTickLen(value.x, value.y);
    }

    public void setMinorTickLen(float valueX, float valueY) {
        this.nSetMinorTickLen(valueX, valueY);
    }

    private native void nGetMinorTickLen(ImVec2 var1);

    private native float nGetMinorTickLenX();

    private native float nGetMinorTickLenY();

    private native void nSetMinorTickLen(float var1, float var2);

    public ImVec2 getMajorTickSize() {
        ImVec2 dst = new ImVec2();
        this.nGetMajorTickSize(dst);
        return dst;
    }

    public float getMajorTickSizeX() {
        return this.nGetMajorTickSizeX();
    }

    public float getMajorTickSizeY() {
        return this.nGetMajorTickSizeY();
    }

    public void getMajorTickSize(ImVec2 dst) {
        this.nGetMajorTickSize(dst);
    }

    public void setMajorTickSize(ImVec2 value) {
        this.nSetMajorTickSize(value.x, value.y);
    }

    public void setMajorTickSize(float valueX, float valueY) {
        this.nSetMajorTickSize(valueX, valueY);
    }

    private native void nGetMajorTickSize(ImVec2 var1);

    private native float nGetMajorTickSizeX();

    private native float nGetMajorTickSizeY();

    private native void nSetMajorTickSize(float var1, float var2);

    public ImVec2 getMinorTickSize() {
        ImVec2 dst = new ImVec2();
        this.nGetMinorTickSize(dst);
        return dst;
    }

    public float getMinorTickSizeX() {
        return this.nGetMinorTickSizeX();
    }

    public float getMinorTickSizeY() {
        return this.nGetMinorTickSizeY();
    }

    public void getMinorTickSize(ImVec2 dst) {
        this.nGetMinorTickSize(dst);
    }

    public void setMinorTickSize(ImVec2 value) {
        this.nSetMinorTickSize(value.x, value.y);
    }

    public void setMinorTickSize(float valueX, float valueY) {
        this.nSetMinorTickSize(valueX, valueY);
    }

    private native void nGetMinorTickSize(ImVec2 var1);

    private native float nGetMinorTickSizeX();

    private native float nGetMinorTickSizeY();

    private native void nSetMinorTickSize(float var1, float var2);

    public ImVec2 getMajorGridSize() {
        ImVec2 dst = new ImVec2();
        this.nGetMajorGridSize(dst);
        return dst;
    }

    public float getMajorGridSizeX() {
        return this.nGetMajorGridSizeX();
    }

    public float getMajorGridSizeY() {
        return this.nGetMajorGridSizeY();
    }

    public void getMajorGridSize(ImVec2 dst) {
        this.nGetMajorGridSize(dst);
    }

    public void setMajorGridSize(ImVec2 value) {
        this.nSetMajorGridSize(value.x, value.y);
    }

    public void setMajorGridSize(float valueX, float valueY) {
        this.nSetMajorGridSize(valueX, valueY);
    }

    private native void nGetMajorGridSize(ImVec2 var1);

    private native float nGetMajorGridSizeX();

    private native float nGetMajorGridSizeY();

    private native void nSetMajorGridSize(float var1, float var2);

    public ImVec2 getMinorGridSize() {
        ImVec2 dst = new ImVec2();
        this.nGetMinorGridSize(dst);
        return dst;
    }

    public float getMinorGridSizeX() {
        return this.nGetMinorGridSizeX();
    }

    public float getMinorGridSizeY() {
        return this.nGetMinorGridSizeY();
    }

    public void getMinorGridSize(ImVec2 dst) {
        this.nGetMinorGridSize(dst);
    }

    public void setMinorGridSize(ImVec2 value) {
        this.nSetMinorGridSize(value.x, value.y);
    }

    public void setMinorGridSize(float valueX, float valueY) {
        this.nSetMinorGridSize(valueX, valueY);
    }

    private native void nGetMinorGridSize(ImVec2 var1);

    private native float nGetMinorGridSizeX();

    private native float nGetMinorGridSizeY();

    private native void nSetMinorGridSize(float var1, float var2);

    public ImVec2 getPlotPadding() {
        ImVec2 dst = new ImVec2();
        this.nGetPlotPadding(dst);
        return dst;
    }

    public float getPlotPaddingX() {
        return this.nGetPlotPaddingX();
    }

    public float getPlotPaddingY() {
        return this.nGetPlotPaddingY();
    }

    public void getPlotPadding(ImVec2 dst) {
        this.nGetPlotPadding(dst);
    }

    public void setPlotPadding(ImVec2 value) {
        this.nSetPlotPadding(value.x, value.y);
    }

    public void setPlotPadding(float valueX, float valueY) {
        this.nSetPlotPadding(valueX, valueY);
    }

    private native void nGetPlotPadding(ImVec2 var1);

    private native float nGetPlotPaddingX();

    private native float nGetPlotPaddingY();

    private native void nSetPlotPadding(float var1, float var2);

    public ImVec2 getLabelPadding() {
        ImVec2 dst = new ImVec2();
        this.nGetLabelPadding(dst);
        return dst;
    }

    public float getLabelPaddingX() {
        return this.nGetLabelPaddingX();
    }

    public float getLabelPaddingY() {
        return this.nGetLabelPaddingY();
    }

    public void getLabelPadding(ImVec2 dst) {
        this.nGetLabelPadding(dst);
    }

    public void setLabelPadding(ImVec2 value) {
        this.nSetLabelPadding(value.x, value.y);
    }

    public void setLabelPadding(float valueX, float valueY) {
        this.nSetLabelPadding(valueX, valueY);
    }

    private native void nGetLabelPadding(ImVec2 var1);

    private native float nGetLabelPaddingX();

    private native float nGetLabelPaddingY();

    private native void nSetLabelPadding(float var1, float var2);

    public ImVec2 getLegendPadding() {
        ImVec2 dst = new ImVec2();
        this.nGetLegendPadding(dst);
        return dst;
    }

    public float getLegendPaddingX() {
        return this.nGetLegendPaddingX();
    }

    public float getLegendPaddingY() {
        return this.nGetLegendPaddingY();
    }

    public void getLegendPadding(ImVec2 dst) {
        this.nGetLegendPadding(dst);
    }

    public void setLegendPadding(ImVec2 value) {
        this.nSetLegendPadding(value.x, value.y);
    }

    public void setLegendPadding(float valueX, float valueY) {
        this.nSetLegendPadding(valueX, valueY);
    }

    private native void nGetLegendPadding(ImVec2 var1);

    private native float nGetLegendPaddingX();

    private native float nGetLegendPaddingY();

    private native void nSetLegendPadding(float var1, float var2);

    public ImVec2 getLegendInnerPadding() {
        ImVec2 dst = new ImVec2();
        this.nGetLegendInnerPadding(dst);
        return dst;
    }

    public float getLegendInnerPaddingX() {
        return this.nGetLegendInnerPaddingX();
    }

    public float getLegendInnerPaddingY() {
        return this.nGetLegendInnerPaddingY();
    }

    public void getLegendInnerPadding(ImVec2 dst) {
        this.nGetLegendInnerPadding(dst);
    }

    public void setLegendInnerPadding(ImVec2 value) {
        this.nSetLegendInnerPadding(value.x, value.y);
    }

    public void setLegendInnerPadding(float valueX, float valueY) {
        this.nSetLegendInnerPadding(valueX, valueY);
    }

    private native void nGetLegendInnerPadding(ImVec2 var1);

    private native float nGetLegendInnerPaddingX();

    private native float nGetLegendInnerPaddingY();

    private native void nSetLegendInnerPadding(float var1, float var2);

    public ImVec2 getLegendSpacing() {
        ImVec2 dst = new ImVec2();
        this.nGetLegendSpacing(dst);
        return dst;
    }

    public float getLegendSpacingX() {
        return this.nGetLegendSpacingX();
    }

    public float getLegendSpacingY() {
        return this.nGetLegendSpacingY();
    }

    public void getLegendSpacing(ImVec2 dst) {
        this.nGetLegendSpacing(dst);
    }

    public void setLegendSpacing(ImVec2 value) {
        this.nSetLegendSpacing(value.x, value.y);
    }

    public void setLegendSpacing(float valueX, float valueY) {
        this.nSetLegendSpacing(valueX, valueY);
    }

    private native void nGetLegendSpacing(ImVec2 var1);

    private native float nGetLegendSpacingX();

    private native float nGetLegendSpacingY();

    private native void nSetLegendSpacing(float var1, float var2);

    public ImVec2 getMousePosPadding() {
        ImVec2 dst = new ImVec2();
        this.nGetMousePosPadding(dst);
        return dst;
    }

    public float getMousePosPaddingX() {
        return this.nGetMousePosPaddingX();
    }

    public float getMousePosPaddingY() {
        return this.nGetMousePosPaddingY();
    }

    public void getMousePosPadding(ImVec2 dst) {
        this.nGetMousePosPadding(dst);
    }

    public void setMousePosPadding(ImVec2 value) {
        this.nSetMousePosPadding(value.x, value.y);
    }

    public void setMousePosPadding(float valueX, float valueY) {
        this.nSetMousePosPadding(valueX, valueY);
    }

    private native void nGetMousePosPadding(ImVec2 var1);

    private native float nGetMousePosPaddingX();

    private native float nGetMousePosPaddingY();

    private native void nSetMousePosPadding(float var1, float var2);

    public ImVec2 getAnnotationPadding() {
        ImVec2 dst = new ImVec2();
        this.nGetAnnotationPadding(dst);
        return dst;
    }

    public float getAnnotationPaddingX() {
        return this.nGetAnnotationPaddingX();
    }

    public float getAnnotationPaddingY() {
        return this.nGetAnnotationPaddingY();
    }

    public void getAnnotationPadding(ImVec2 dst) {
        this.nGetAnnotationPadding(dst);
    }

    public void setAnnotationPadding(ImVec2 value) {
        this.nSetAnnotationPadding(value.x, value.y);
    }

    public void setAnnotationPadding(float valueX, float valueY) {
        this.nSetAnnotationPadding(valueX, valueY);
    }

    private native void nGetAnnotationPadding(ImVec2 var1);

    private native float nGetAnnotationPaddingX();

    private native float nGetAnnotationPaddingY();

    private native void nSetAnnotationPadding(float var1, float var2);

    public ImVec2 getFitPadding() {
        ImVec2 dst = new ImVec2();
        this.nGetFitPadding(dst);
        return dst;
    }

    public float getFitPaddingX() {
        return this.nGetFitPaddingX();
    }

    public float getFitPaddingY() {
        return this.nGetFitPaddingY();
    }

    public void getFitPadding(ImVec2 dst) {
        this.nGetFitPadding(dst);
    }

    public void setFitPadding(ImVec2 value) {
        this.nSetFitPadding(value.x, value.y);
    }

    public void setFitPadding(float valueX, float valueY) {
        this.nSetFitPadding(valueX, valueY);
    }

    private native void nGetFitPadding(ImVec2 var1);

    private native float nGetFitPaddingX();

    private native float nGetFitPaddingY();

    private native void nSetFitPadding(float var1, float var2);

    public ImVec2 getPlotDefaultSize() {
        ImVec2 dst = new ImVec2();
        this.nGetPlotDefaultSize(dst);
        return dst;
    }

    public float getPlotDefaultSizeX() {
        return this.nGetPlotDefaultSizeX();
    }

    public float getPlotDefaultSizeY() {
        return this.nGetPlotDefaultSizeY();
    }

    public void getPlotDefaultSize(ImVec2 dst) {
        this.nGetPlotDefaultSize(dst);
    }

    public void setPlotDefaultSize(ImVec2 value) {
        this.nSetPlotDefaultSize(value.x, value.y);
    }

    public void setPlotDefaultSize(float valueX, float valueY) {
        this.nSetPlotDefaultSize(valueX, valueY);
    }

    private native void nGetPlotDefaultSize(ImVec2 var1);

    private native float nGetPlotDefaultSizeX();

    private native float nGetPlotDefaultSizeY();

    private native void nSetPlotDefaultSize(float var1, float var2);

    public ImVec2 getPlotMinSize() {
        ImVec2 dst = new ImVec2();
        this.nGetPlotMinSize(dst);
        return dst;
    }

    public float getPlotMinSizeX() {
        return this.nGetPlotMinSizeX();
    }

    public float getPlotMinSizeY() {
        return this.nGetPlotMinSizeY();
    }

    public void getPlotMinSize(ImVec2 dst) {
        this.nGetPlotMinSize(dst);
    }

    public void setPlotMinSize(ImVec2 value) {
        this.nSetPlotMinSize(value.x, value.y);
    }

    public void setPlotMinSize(float valueX, float valueY) {
        this.nSetPlotMinSize(valueX, valueY);
    }

    private native void nGetPlotMinSize(ImVec2 var1);

    private native float nGetPlotMinSizeX();

    private native float nGetPlotMinSizeY();

    private native void nSetPlotMinSize(float var1, float var2);

    public ImVec4[] getColors() {
        return this.nGetColors();
    }

    public void setColors(ImVec4[] value) {
        this.nSetColors(value);
    }

    private native ImVec4[] nGetColors();

    private native void nSetColors(ImVec4[] var1);

    public int getColormap() {
        return this.nGetColormap();
    }

    public void setColormap(int value) {
        this.nSetColormap(value);
    }

    private native int nGetColormap();

    private native void nSetColormap(int var1);

    public boolean getUseLocalTime() {
        return this.nGetUseLocalTime();
    }

    public void setUseLocalTime(boolean value) {
        this.nSetUseLocalTime(value);
    }

    private native boolean nGetUseLocalTime();

    private native void nSetUseLocalTime(boolean var1);

    public boolean getUseISO8601() {
        return this.nGetUseISO8601();
    }

    public void setUseISO8601(boolean value) {
        this.nSetUseISO8601(value);
    }

    private native boolean nGetUseISO8601();

    private native void nSetUseISO8601(boolean var1);

    public boolean getUse24HourClock() {
        return this.nGetUse24HourClock();
    }

    public void setUse24HourClock(boolean value) {
        this.nSetUse24HourClock(value);
    }

    private native boolean nGetUse24HourClock();

    private native void nSetUse24HourClock(boolean var1);
}

