/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.imnodes;

import imgui.moulberry90.ImVec2;
import imgui.moulberry90.binding.ImGuiStruct;

public final class ImNodesStyle
extends ImGuiStruct {
    public ImNodesStyle(long ptr) {
        super(ptr);
    }

    public float getGridSpacing() {
        return this.nGetGridSpacing();
    }

    public void setGridSpacing(float value) {
        this.nSetGridSpacing(value);
    }

    private native float nGetGridSpacing();

    private native void nSetGridSpacing(float var1);

    public float getNodeCornerRounding() {
        return this.nGetNodeCornerRounding();
    }

    public void setNodeCornerRounding(float value) {
        this.nSetNodeCornerRounding(value);
    }

    private native float nGetNodeCornerRounding();

    private native void nSetNodeCornerRounding(float var1);

    public ImVec2 getNodePadding() {
        ImVec2 dst = new ImVec2();
        this.nGetNodePadding(dst);
        return dst;
    }

    public float getNodePaddingX() {
        return this.nGetNodePaddingX();
    }

    public float getNodePaddingY() {
        return this.nGetNodePaddingY();
    }

    public void getNodePadding(ImVec2 dst) {
        this.nGetNodePadding(dst);
    }

    public void setNodePadding(ImVec2 value) {
        this.nSetNodePadding(value.x, value.y);
    }

    public void setNodePadding(float valueX, float valueY) {
        this.nSetNodePadding(valueX, valueY);
    }

    private native void nGetNodePadding(ImVec2 var1);

    private native float nGetNodePaddingX();

    private native float nGetNodePaddingY();

    private native void nSetNodePadding(float var1, float var2);

    public float getNodeBorderThickness() {
        return this.nGetNodeBorderThickness();
    }

    public void setNodeBorderThickness(float value) {
        this.nSetNodeBorderThickness(value);
    }

    private native float nGetNodeBorderThickness();

    private native void nSetNodeBorderThickness(float var1);

    public float getLinkThickness() {
        return this.nGetLinkThickness();
    }

    public void setLinkThickness(float value) {
        this.nSetLinkThickness(value);
    }

    private native float nGetLinkThickness();

    private native void nSetLinkThickness(float var1);

    public float getLinkLineSegmentsPerLength() {
        return this.nGetLinkLineSegmentsPerLength();
    }

    public void setLinkLineSegmentsPerLength(float value) {
        this.nSetLinkLineSegmentsPerLength(value);
    }

    private native float nGetLinkLineSegmentsPerLength();

    private native void nSetLinkLineSegmentsPerLength(float var1);

    public float getLinkHoverDistance() {
        return this.nGetLinkHoverDistance();
    }

    public void setLinkHoverDistance(float value) {
        this.nSetLinkHoverDistance(value);
    }

    private native float nGetLinkHoverDistance();

    private native void nSetLinkHoverDistance(float var1);

    public float getPinCircleRadius() {
        return this.nGetPinCircleRadius();
    }

    public void setPinCircleRadius(float value) {
        this.nSetPinCircleRadius(value);
    }

    private native float nGetPinCircleRadius();

    private native void nSetPinCircleRadius(float var1);

    public float getPinQuadSideLength() {
        return this.nGetPinQuadSideLength();
    }

    public void setPinQuadSideLength(float value) {
        this.nSetPinQuadSideLength(value);
    }

    private native float nGetPinQuadSideLength();

    private native void nSetPinQuadSideLength(float var1);

    public float getPinTriangleSideLength() {
        return this.nGetPinTriangleSideLength();
    }

    public void setPinTriangleSideLength(float value) {
        this.nSetPinTriangleSideLength(value);
    }

    private native float nGetPinTriangleSideLength();

    private native void nSetPinTriangleSideLength(float var1);

    public float getPinLineThickness() {
        return this.nGetPinLineThickness();
    }

    public void setPinLineThickness(float value) {
        this.nSetPinLineThickness(value);
    }

    private native float nGetPinLineThickness();

    private native void nSetPinLineThickness(float var1);

    public float getPinHoverRadius() {
        return this.nGetPinHoverRadius();
    }

    public void setPinHoverRadius(float value) {
        this.nSetPinHoverRadius(value);
    }

    private native float nGetPinHoverRadius();

    private native void nSetPinHoverRadius(float var1);

    public float getPinOffset() {
        return this.nGetPinOffset();
    }

    public void setPinOffset(float value) {
        this.nSetPinOffset(value);
    }

    private native float nGetPinOffset();

    private native void nSetPinOffset(float var1);

    public ImVec2 getMiniMapPadding() {
        ImVec2 dst = new ImVec2();
        this.nGetMiniMapPadding(dst);
        return dst;
    }

    public float getMiniMapPaddingX() {
        return this.nGetMiniMapPaddingX();
    }

    public float getMiniMapPaddingY() {
        return this.nGetMiniMapPaddingY();
    }

    public void getMiniMapPadding(ImVec2 dst) {
        this.nGetMiniMapPadding(dst);
    }

    public void setMiniMapPadding(ImVec2 value) {
        this.nSetMiniMapPadding(value.x, value.y);
    }

    public void setMiniMapPadding(float valueX, float valueY) {
        this.nSetMiniMapPadding(valueX, valueY);
    }

    private native void nGetMiniMapPadding(ImVec2 var1);

    private native float nGetMiniMapPaddingX();

    private native float nGetMiniMapPaddingY();

    private native void nSetMiniMapPadding(float var1, float var2);

    public ImVec2 getMiniMapOffset() {
        ImVec2 dst = new ImVec2();
        this.nGetMiniMapOffset(dst);
        return dst;
    }

    public float getMiniMapOffsetX() {
        return this.nGetMiniMapOffsetX();
    }

    public float getMiniMapOffsetY() {
        return this.nGetMiniMapOffsetY();
    }

    public void getMiniMapOffset(ImVec2 dst) {
        this.nGetMiniMapOffset(dst);
    }

    public void setMiniMapOffset(ImVec2 value) {
        this.nSetMiniMapOffset(value.x, value.y);
    }

    public void setMiniMapOffset(float valueX, float valueY) {
        this.nSetMiniMapOffset(valueX, valueY);
    }

    private native void nGetMiniMapOffset(ImVec2 var1);

    private native float nGetMiniMapOffsetX();

    private native float nGetMiniMapOffsetY();

    private native void nSetMiniMapOffset(float var1, float var2);

    public int getFlags() {
        return this.nGetFlags();
    }

    public void setFlags(int value) {
        this.nSetFlags(value);
    }

    public void addFlags(int flags) {
        this.setFlags(this.getFlags() | flags);
    }

    public void removeFlags(int flags) {
        this.setFlags(this.getFlags() & ~flags);
    }

    public boolean hasFlags(int flags) {
        return (this.getFlags() & flags) != 0;
    }

    private native int nGetFlags();

    private native void nSetFlags(int var1);

    public int[] getColors() {
        return this.nGetColors();
    }

    public int getColors(int idx) {
        return this.nGetColors(idx);
    }

    public void setColors(int[] value) {
        this.nSetColors(value);
    }

    public void setColors(int idx, int value) {
        this.nSetColors(idx, value);
    }

    private native int[] nGetColors();

    private native int nGetColors(int var1);

    private native void nSetColors(int[] var1);

    private native void nSetColors(int var1, int var2);
}

