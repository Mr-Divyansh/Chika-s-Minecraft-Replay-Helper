/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.nodeditor;

import imgui.moulberry90.ImVec2;
import imgui.moulberry90.ImVec4;
import imgui.moulberry90.binding.ImGuiStruct;

public final class NodeEditorStyle
extends ImGuiStruct {
    public NodeEditorStyle(long ptr) {
        super(ptr);
    }

    public ImVec4 getNodePadding() {
        ImVec4 dst = new ImVec4();
        this.nGetNodePadding(dst);
        return dst;
    }

    public float getNodePaddingX() {
        return this.nGetNodePaddingX();
    }

    public float getNodePaddingY() {
        return this.nGetNodePaddingY();
    }

    public float getNodePaddingZ() {
        return this.nGetNodePaddingZ();
    }

    public float getNodePaddingW() {
        return this.nGetNodePaddingW();
    }

    public void getNodePadding(ImVec4 dst) {
        this.nGetNodePadding(dst);
    }

    public void setNodePadding(ImVec4 value) {
        this.nSetNodePadding(value.x, value.y, value.z, value.w);
    }

    public void setNodePadding(float valueX, float valueY, float valueZ, float valueW) {
        this.nSetNodePadding(valueX, valueY, valueZ, valueW);
    }

    private native void nGetNodePadding(ImVec4 var1);

    private native float nGetNodePaddingX();

    private native float nGetNodePaddingY();

    private native float nGetNodePaddingZ();

    private native float nGetNodePaddingW();

    private native void nSetNodePadding(float var1, float var2, float var3, float var4);

    public float getNodeRounding() {
        return this.nGetNodeRounding();
    }

    public void setNodeRounding(float value) {
        this.nSetNodeRounding(value);
    }

    private native float nGetNodeRounding();

    private native void nSetNodeRounding(float var1);

    public float getNodeBorderWidth() {
        return this.nGetNodeBorderWidth();
    }

    public void setNodeBorderWidth(float value) {
        this.nSetNodeBorderWidth(value);
    }

    private native float nGetNodeBorderWidth();

    private native void nSetNodeBorderWidth(float var1);

    public float getHoveredNodeBorderWidth() {
        return this.nGetHoveredNodeBorderWidth();
    }

    public void setHoveredNodeBorderWidth(float value) {
        this.nSetHoveredNodeBorderWidth(value);
    }

    private native float nGetHoveredNodeBorderWidth();

    private native void nSetHoveredNodeBorderWidth(float var1);

    public float getHoverNodeBorderOffset() {
        return this.nGetHoverNodeBorderOffset();
    }

    public void setHoverNodeBorderOffset(float value) {
        this.nSetHoverNodeBorderOffset(value);
    }

    private native float nGetHoverNodeBorderOffset();

    private native void nSetHoverNodeBorderOffset(float var1);

    public float getSelectedNodeBorderWidth() {
        return this.nGetSelectedNodeBorderWidth();
    }

    public void setSelectedNodeBorderWidth(float value) {
        this.nSetSelectedNodeBorderWidth(value);
    }

    private native float nGetSelectedNodeBorderWidth();

    private native void nSetSelectedNodeBorderWidth(float var1);

    public float getSelectedNodeBorderOffset() {
        return this.nGetSelectedNodeBorderOffset();
    }

    public void setSelectedNodeBorderOffset(float value) {
        this.nSetSelectedNodeBorderOffset(value);
    }

    private native float nGetSelectedNodeBorderOffset();

    private native void nSetSelectedNodeBorderOffset(float var1);

    public float getPinRounding() {
        return this.nGetPinRounding();
    }

    public void setPinRounding(float value) {
        this.nSetPinRounding(value);
    }

    private native float nGetPinRounding();

    private native void nSetPinRounding(float var1);

    public float getPinBorderWidth() {
        return this.nGetPinBorderWidth();
    }

    public void setPinBorderWidth(float value) {
        this.nSetPinBorderWidth(value);
    }

    private native float nGetPinBorderWidth();

    private native void nSetPinBorderWidth(float var1);

    public float getLinkStrength() {
        return this.nGetLinkStrength();
    }

    public void setLinkStrength(float value) {
        this.nSetLinkStrength(value);
    }

    private native float nGetLinkStrength();

    private native void nSetLinkStrength(float var1);

    public ImVec2 getSourceDirection() {
        ImVec2 dst = new ImVec2();
        this.nGetSourceDirection(dst);
        return dst;
    }

    public float getSourceDirectionX() {
        return this.nGetSourceDirectionX();
    }

    public float getSourceDirectionY() {
        return this.nGetSourceDirectionY();
    }

    public void getSourceDirection(ImVec2 dst) {
        this.nGetSourceDirection(dst);
    }

    public void setSourceDirection(ImVec2 value) {
        this.nSetSourceDirection(value.x, value.y);
    }

    public void setSourceDirection(float valueX, float valueY) {
        this.nSetSourceDirection(valueX, valueY);
    }

    private native void nGetSourceDirection(ImVec2 var1);

    private native float nGetSourceDirectionX();

    private native float nGetSourceDirectionY();

    private native void nSetSourceDirection(float var1, float var2);

    public ImVec2 getTargetDirection() {
        ImVec2 dst = new ImVec2();
        this.nGetTargetDirection(dst);
        return dst;
    }

    public float getTargetDirectionX() {
        return this.nGetTargetDirectionX();
    }

    public float getTargetDirectionY() {
        return this.nGetTargetDirectionY();
    }

    public void getTargetDirection(ImVec2 dst) {
        this.nGetTargetDirection(dst);
    }

    public void setTargetDirection(ImVec2 value) {
        this.nSetTargetDirection(value.x, value.y);
    }

    public void setTargetDirection(float valueX, float valueY) {
        this.nSetTargetDirection(valueX, valueY);
    }

    private native void nGetTargetDirection(ImVec2 var1);

    private native float nGetTargetDirectionX();

    private native float nGetTargetDirectionY();

    private native void nSetTargetDirection(float var1, float var2);

    public float getScrollDuration() {
        return this.nGetScrollDuration();
    }

    public void setScrollDuration(float value) {
        this.nSetScrollDuration(value);
    }

    private native float nGetScrollDuration();

    private native void nSetScrollDuration(float var1);

    public float getFlowMarkerDistance() {
        return this.nGetFlowMarkerDistance();
    }

    public void setFlowMarkerDistance(float value) {
        this.nSetFlowMarkerDistance(value);
    }

    private native float nGetFlowMarkerDistance();

    private native void nSetFlowMarkerDistance(float var1);

    public float getFlowSpeed() {
        return this.nGetFlowSpeed();
    }

    public void setFlowSpeed(float value) {
        this.nSetFlowSpeed(value);
    }

    private native float nGetFlowSpeed();

    private native void nSetFlowSpeed(float var1);

    public float getFlowDuration() {
        return this.nGetFlowDuration();
    }

    public void setFlowDuration(float value) {
        this.nSetFlowDuration(value);
    }

    private native float nGetFlowDuration();

    private native void nSetFlowDuration(float var1);

    public ImVec2 getPivotAlignment() {
        ImVec2 dst = new ImVec2();
        this.nGetPivotAlignment(dst);
        return dst;
    }

    public float getPivotAlignmentX() {
        return this.nGetPivotAlignmentX();
    }

    public float getPivotAlignmentY() {
        return this.nGetPivotAlignmentY();
    }

    public void getPivotAlignment(ImVec2 dst) {
        this.nGetPivotAlignment(dst);
    }

    public void setPivotAlignment(ImVec2 value) {
        this.nSetPivotAlignment(value.x, value.y);
    }

    public void setPivotAlignment(float valueX, float valueY) {
        this.nSetPivotAlignment(valueX, valueY);
    }

    private native void nGetPivotAlignment(ImVec2 var1);

    private native float nGetPivotAlignmentX();

    private native float nGetPivotAlignmentY();

    private native void nSetPivotAlignment(float var1, float var2);

    public ImVec2 getPivotSize() {
        ImVec2 dst = new ImVec2();
        this.nGetPivotSize(dst);
        return dst;
    }

    public float getPivotSizeX() {
        return this.nGetPivotSizeX();
    }

    public float getPivotSizeY() {
        return this.nGetPivotSizeY();
    }

    public void getPivotSize(ImVec2 dst) {
        this.nGetPivotSize(dst);
    }

    public void setPivotSize(ImVec2 value) {
        this.nSetPivotSize(value.x, value.y);
    }

    public void setPivotSize(float valueX, float valueY) {
        this.nSetPivotSize(valueX, valueY);
    }

    private native void nGetPivotSize(ImVec2 var1);

    private native float nGetPivotSizeX();

    private native float nGetPivotSizeY();

    private native void nSetPivotSize(float var1, float var2);

    public ImVec2 getPivotScale() {
        ImVec2 dst = new ImVec2();
        this.nGetPivotScale(dst);
        return dst;
    }

    public float getPivotScaleX() {
        return this.nGetPivotScaleX();
    }

    public float getPivotScaleY() {
        return this.nGetPivotScaleY();
    }

    public void getPivotScale(ImVec2 dst) {
        this.nGetPivotScale(dst);
    }

    public void setPivotScale(ImVec2 value) {
        this.nSetPivotScale(value.x, value.y);
    }

    public void setPivotScale(float valueX, float valueY) {
        this.nSetPivotScale(valueX, valueY);
    }

    private native void nGetPivotScale(ImVec2 var1);

    private native float nGetPivotScaleX();

    private native float nGetPivotScaleY();

    private native void nSetPivotScale(float var1, float var2);

    public float getPinCorners() {
        return this.nGetPinCorners();
    }

    public void setPinCorners(float value) {
        this.nSetPinCorners(value);
    }

    private native float nGetPinCorners();

    private native void nSetPinCorners(float var1);

    public float getPinRadius() {
        return this.nGetPinRadius();
    }

    public void setPinRadius(float value) {
        this.nSetPinRadius(value);
    }

    private native float nGetPinRadius();

    private native void nSetPinRadius(float var1);

    public float getPinArrowSize() {
        return this.nGetPinArrowSize();
    }

    public void setPinArrowSize(float value) {
        this.nSetPinArrowSize(value);
    }

    private native float nGetPinArrowSize();

    private native void nSetPinArrowSize(float var1);

    public float getPinArrowWidth() {
        return this.nGetPinArrowWidth();
    }

    public void setPinArrowWidth(float value) {
        this.nSetPinArrowWidth(value);
    }

    private native float nGetPinArrowWidth();

    private native void nSetPinArrowWidth(float var1);

    public float getGroupRounding() {
        return this.nGetGroupRounding();
    }

    public void setGroupRounding(float value) {
        this.nSetGroupRounding(value);
    }

    private native float nGetGroupRounding();

    private native void nSetGroupRounding(float var1);

    public float getGroupBorderWidth() {
        return this.nGetGroupBorderWidth();
    }

    public void setGroupBorderWidth(float value) {
        this.nSetGroupBorderWidth(value);
    }

    private native float nGetGroupBorderWidth();

    private native void nSetGroupBorderWidth(float var1);

    public float getHighlightConnectedLinks() {
        return this.nGetHighlightConnectedLinks();
    }

    public void setHighlightConnectedLinks(float value) {
        this.nSetHighlightConnectedLinks(value);
    }

    private native float nGetHighlightConnectedLinks();

    private native void nSetHighlightConnectedLinks(float var1);

    public float getSnapLinkToPinDir() {
        return this.nGetSnapLinkToPinDir();
    }

    public void setSnapLinkToPinDir(float value) {
        this.nSetSnapLinkToPinDir(value);
    }

    private native float nGetSnapLinkToPinDir();

    private native void nSetSnapLinkToPinDir(float var1);

    public ImVec4[] getColors() {
        return this.nGetColors();
    }

    public void setColors(ImVec4[] value) {
        this.nSetColors(value);
    }

    private native ImVec4[] nGetColors();

    private native void nSetColors(ImVec4[] var1);
}

