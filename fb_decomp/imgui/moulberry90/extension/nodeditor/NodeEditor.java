/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.nodeditor;

import imgui.moulberry90.ImDrawList;
import imgui.moulberry90.ImVec2;
import imgui.moulberry90.ImVec4;
import imgui.moulberry90.extension.nodeditor.NodeEditorConfig;
import imgui.moulberry90.extension.nodeditor.NodeEditorContext;
import imgui.moulberry90.extension.nodeditor.NodeEditorStyle;
import imgui.moulberry90.type.ImLong;

public final class NodeEditor {
    private static final NodeEditorStyle _GETSTYLE_1 = new NodeEditorStyle(0L);
    private static final ImDrawList _GETHINTFOREGROUNDDRAWLIST_1 = new ImDrawList(0L);
    private static final ImDrawList _GETHINTBACKGROUNDDRAWLIST_1 = new ImDrawList(0L);
    private static final ImDrawList _GETNODEBACKGROUNDDRAWLIST_1040171299 = new ImDrawList(0L);

    private NodeEditor() {
    }

    public static void setCurrentEditor(NodeEditorContext ctx) {
        NodeEditor.nSetCurrentEditor(ctx.ptr);
    }

    private static native void nSetCurrentEditor(long var0);

    public static NodeEditorContext getCurrentEditor() {
        return new NodeEditorContext(NodeEditor.nGetCurrentEditor());
    }

    private static native long nGetCurrentEditor();

    public static NodeEditorContext createEditor() {
        return new NodeEditorContext(NodeEditor.nCreateEditor());
    }

    public static NodeEditorContext createEditor(NodeEditorConfig config) {
        return new NodeEditorContext(NodeEditor.nCreateEditor(config.ptr));
    }

    private static native long nCreateEditor();

    private static native long nCreateEditor(long var0);

    public static void destroyEditor(NodeEditorContext ctx) {
        NodeEditor.nDestroyEditor(ctx.ptr);
    }

    private static native void nDestroyEditor(long var0);

    public static NodeEditorStyle getStyle() {
        NodeEditor._GETSTYLE_1.ptr = NodeEditor.nGetStyle();
        return _GETSTYLE_1;
    }

    private static native long nGetStyle();

    public static String getStyleColorName(int colorIndex) {
        return NodeEditor.nGetStyleColorName(colorIndex);
    }

    private static native String nGetStyleColorName(int var0);

    public static void pushStyleColor(int colorIndex, ImVec4 color) {
        NodeEditor.nPushStyleColor(colorIndex, color.x, color.y, color.z, color.w);
    }

    public static void pushStyleColor(int colorIndex, float colorX, float colorY, float colorZ, float colorW) {
        NodeEditor.nPushStyleColor(colorIndex, colorX, colorY, colorZ, colorW);
    }

    private static native void nPushStyleColor(int var0, float var1, float var2, float var3, float var4);

    public static void popStyleColor() {
        NodeEditor.nPopStyleColor();
    }

    public static void popStyleColor(int count) {
        NodeEditor.nPopStyleColor(count);
    }

    private static native void nPopStyleColor();

    private static native void nPopStyleColor(int var0);

    public static void pushStyleVar(int varIndex, float value) {
        NodeEditor.nPushStyleVar(varIndex, value);
    }

    private static native void nPushStyleVar(int var0, float var1);

    public static void pushStyleVar(int varIndex, ImVec2 value) {
        NodeEditor.nPushStyleVar(varIndex, value.x, value.y);
    }

    public static void pushStyleVar(int varIndex, float valueX, float valueY) {
        NodeEditor.nPushStyleVar(varIndex, valueX, valueY);
    }

    private static native void nPushStyleVar(int var0, float var1, float var2);

    public static void pushStyleVar(int varIndex, ImVec4 value) {
        NodeEditor.nPushStyleVar(varIndex, value.x, value.y, value.z, value.w);
    }

    public static void pushStyleVar(int varIndex, float valueX, float valueY, float valueZ, float valueW) {
        NodeEditor.nPushStyleVar(varIndex, valueX, valueY, valueZ, valueW);
    }

    private static native void nPushStyleVar(int var0, float var1, float var2, float var3, float var4);

    public static void popStyleVar() {
        NodeEditor.nPopStyleVar();
    }

    public static void popStyleVar(int count) {
        NodeEditor.nPopStyleVar(count);
    }

    private static native void nPopStyleVar();

    private static native void nPopStyleVar(int var0);

    public static void begin(String id) {
        NodeEditor.nBegin(id);
    }

    public static void begin(String id, ImVec2 size) {
        NodeEditor.nBegin(id, size.x, size.y);
    }

    public static void begin(String id, float sizeX, float sizeY) {
        NodeEditor.nBegin(id, sizeX, sizeY);
    }

    private static native void nBegin(String var0);

    private static native void nBegin(String var0, float var1, float var2);

    public static void end() {
        NodeEditor.nEnd();
    }

    private static native void nEnd();

    public static void beginNode(long id) {
        NodeEditor.nBeginNode(id);
    }

    private static native void nBeginNode(long var0);

    public static void beginPin(long id, int kind) {
        NodeEditor.nBeginPin(id, kind);
    }

    private static native void nBeginPin(long var0, int var2);

    public static void pinRect(ImVec2 a, ImVec2 b) {
        NodeEditor.nPinRect(a.x, a.y, b.x, b.y);
    }

    public static void pinRect(float aX, float aY, float bX, float bY) {
        NodeEditor.nPinRect(aX, aY, bX, bY);
    }

    private static native void nPinRect(float var0, float var1, float var2, float var3);

    public static void pinPivotRect(ImVec2 a, ImVec2 b) {
        NodeEditor.nPinPivotRect(a.x, a.y, b.x, b.y);
    }

    public static void pinPivotRect(float aX, float aY, float bX, float bY) {
        NodeEditor.nPinPivotRect(aX, aY, bX, bY);
    }

    private static native void nPinPivotRect(float var0, float var1, float var2, float var3);

    public static void pinPivotSize(ImVec2 size) {
        NodeEditor.nPinPivotSize(size.x, size.y);
    }

    public static void pinPivotSize(float sizeX, float sizeY) {
        NodeEditor.nPinPivotSize(sizeX, sizeY);
    }

    private static native void nPinPivotSize(float var0, float var1);

    public static void pinPivotScale(ImVec2 scale) {
        NodeEditor.nPinPivotScale(scale.x, scale.y);
    }

    public static void pinPivotScale(float scaleX, float scaleY) {
        NodeEditor.nPinPivotScale(scaleX, scaleY);
    }

    private static native void nPinPivotScale(float var0, float var1);

    public static void pinPivotAlignment(ImVec2 alignment) {
        NodeEditor.nPinPivotAlignment(alignment.x, alignment.y);
    }

    public static void pinPivotAlignment(float alignmentX, float alignmentY) {
        NodeEditor.nPinPivotAlignment(alignmentX, alignmentY);
    }

    private static native void nPinPivotAlignment(float var0, float var1);

    public static void endPin() {
        NodeEditor.nEndPin();
    }

    private static native void nEndPin();

    public static void group(ImVec2 size) {
        NodeEditor.nGroup(size.x, size.y);
    }

    public static void group(float sizeX, float sizeY) {
        NodeEditor.nGroup(sizeX, sizeY);
    }

    private static native void nGroup(float var0, float var1);

    public static void endNode() {
        NodeEditor.nEndNode();
    }

    private static native void nEndNode();

    public static boolean beginGroupHint(long nodeId) {
        return NodeEditor.nBeginGroupHint(nodeId);
    }

    private static native boolean nBeginGroupHint(long var0);

    public static ImVec2 getGroupMin() {
        ImVec2 dst = new ImVec2();
        NodeEditor.nGetGroupMin(dst);
        return dst;
    }

    public static float getGroupMinX() {
        return NodeEditor.nGetGroupMinX();
    }

    public static float getGroupMinY() {
        return NodeEditor.nGetGroupMinY();
    }

    public static void getGroupMin(ImVec2 dst) {
        NodeEditor.nGetGroupMin(dst);
    }

    private static native void nGetGroupMin(ImVec2 var0);

    private static native float nGetGroupMinX();

    private static native float nGetGroupMinY();

    public static ImVec2 getGroupMax() {
        ImVec2 dst = new ImVec2();
        NodeEditor.nGetGroupMax(dst);
        return dst;
    }

    public static float getGroupMaxX() {
        return NodeEditor.nGetGroupMaxX();
    }

    public static float getGroupMaxY() {
        return NodeEditor.nGetGroupMaxY();
    }

    public static void getGroupMax(ImVec2 dst) {
        NodeEditor.nGetGroupMax(dst);
    }

    private static native void nGetGroupMax(ImVec2 var0);

    private static native float nGetGroupMaxX();

    private static native float nGetGroupMaxY();

    public static ImDrawList getHintForegroundDrawList() {
        NodeEditor._GETHINTFOREGROUNDDRAWLIST_1.ptr = NodeEditor.nGetHintForegroundDrawList();
        return _GETHINTFOREGROUNDDRAWLIST_1;
    }

    private static native long nGetHintForegroundDrawList();

    public static ImDrawList getHintBackgroundDrawList() {
        NodeEditor._GETHINTBACKGROUNDDRAWLIST_1.ptr = NodeEditor.nGetHintBackgroundDrawList();
        return _GETHINTBACKGROUNDDRAWLIST_1;
    }

    private static native long nGetHintBackgroundDrawList();

    public static void endGroupHint() {
        NodeEditor.nEndGroupHint();
    }

    private static native void nEndGroupHint();

    public static ImDrawList getNodeBackgroundDrawList(long nodeId) {
        NodeEditor._GETNODEBACKGROUNDDRAWLIST_1040171299.ptr = NodeEditor.nGetNodeBackgroundDrawList(nodeId);
        return _GETNODEBACKGROUNDDRAWLIST_1040171299;
    }

    private static native long nGetNodeBackgroundDrawList(long var0);

    public static boolean link(long id, long startPinId, long endPinId) {
        return NodeEditor.nLink(id, startPinId, endPinId);
    }

    public static boolean link(long id, long startPinId, long endPinId, ImVec4 color) {
        return NodeEditor.nLink(id, startPinId, endPinId, color.x, color.y, color.z, color.w);
    }

    public static boolean link(long id, long startPinId, long endPinId, float colorX, float colorY, float colorZ, float colorW) {
        return NodeEditor.nLink(id, startPinId, endPinId, colorX, colorY, colorZ, colorW);
    }

    public static boolean link(long id, long startPinId, long endPinId, ImVec4 color, float thickness) {
        return NodeEditor.nLink(id, startPinId, endPinId, color.x, color.y, color.z, color.w, thickness);
    }

    public static boolean link(long id, long startPinId, long endPinId, float colorX, float colorY, float colorZ, float colorW, float thickness) {
        return NodeEditor.nLink(id, startPinId, endPinId, colorX, colorY, colorZ, colorW, thickness);
    }

    public static boolean link(long id, long startPinId, long endPinId, float thickness) {
        return NodeEditor.nLink(id, startPinId, endPinId, thickness);
    }

    private static native boolean nLink(long var0, long var2, long var4);

    private static native boolean nLink(long var0, long var2, long var4, float var6, float var7, float var8, float var9);

    private static native boolean nLink(long var0, long var2, long var4, float var6, float var7, float var8, float var9, float var10);

    private static native boolean nLink(long var0, long var2, long var4, float var6);

    public static void flow(long linkId) {
        NodeEditor.nFlow(linkId);
    }

    public static void flow(long linkId, int direction) {
        NodeEditor.nFlow(linkId, direction);
    }

    private static native void nFlow(long var0);

    private static native void nFlow(long var0, int var2);

    public static boolean beginCreate() {
        return NodeEditor.nBeginCreate();
    }

    public static boolean beginCreate(ImVec4 color) {
        return NodeEditor.nBeginCreate(color.x, color.y, color.z, color.w);
    }

    public static boolean beginCreate(float colorX, float colorY, float colorZ, float colorW) {
        return NodeEditor.nBeginCreate(colorX, colorY, colorZ, colorW);
    }

    public static boolean beginCreate(ImVec4 color, float thickness) {
        return NodeEditor.nBeginCreate(color.x, color.y, color.z, color.w, thickness);
    }

    public static boolean beginCreate(float colorX, float colorY, float colorZ, float colorW, float thickness) {
        return NodeEditor.nBeginCreate(colorX, colorY, colorZ, colorW, thickness);
    }

    public static boolean beginCreate(float thickness) {
        return NodeEditor.nBeginCreate(thickness);
    }

    private static native boolean nBeginCreate();

    private static native boolean nBeginCreate(float var0, float var1, float var2, float var3);

    private static native boolean nBeginCreate(float var0, float var1, float var2, float var3, float var4);

    private static native boolean nBeginCreate(float var0);

    public static boolean queryNewLink(ImLong startId, ImLong endId) {
        return NodeEditor.nQueryNewLink(startId != null ? startId.getData() : null, endId != null ? endId.getData() : null);
    }

    private static native boolean nQueryNewLink(long[] var0, long[] var1);

    public static boolean queryNewLink(ImLong startId, ImLong endId, ImVec4 color) {
        return NodeEditor.nQueryNewLink(startId != null ? startId.getData() : null, endId != null ? endId.getData() : null, color.x, color.y, color.z, color.w);
    }

    public static boolean queryNewLink(ImLong startId, ImLong endId, float colorX, float colorY, float colorZ, float colorW) {
        return NodeEditor.nQueryNewLink(startId != null ? startId.getData() : null, endId != null ? endId.getData() : null, colorX, colorY, colorZ, colorW);
    }

    public static boolean queryNewLink(ImLong startId, ImLong endId, ImVec4 color, float thickness) {
        return NodeEditor.nQueryNewLink(startId != null ? startId.getData() : null, endId != null ? endId.getData() : null, color.x, color.y, color.z, color.w, thickness);
    }

    public static boolean queryNewLink(ImLong startId, ImLong endId, float colorX, float colorY, float colorZ, float colorW, float thickness) {
        return NodeEditor.nQueryNewLink(startId != null ? startId.getData() : null, endId != null ? endId.getData() : null, colorX, colorY, colorZ, colorW, thickness);
    }

    private static native boolean nQueryNewLink(long[] var0, long[] var1, float var2, float var3, float var4, float var5);

    private static native boolean nQueryNewLink(long[] var0, long[] var1, float var2, float var3, float var4, float var5, float var6);

    public static boolean queryNewNode(ImLong pinId) {
        return NodeEditor.nQueryNewNode(pinId != null ? pinId.getData() : null);
    }

    private static native boolean nQueryNewNode(long[] var0);

    public static boolean queryNewNode(ImLong pinId, ImVec4 color) {
        return NodeEditor.nQueryNewNode(pinId != null ? pinId.getData() : null, color.x, color.y, color.z, color.w);
    }

    public static boolean queryNewNode(ImLong pinId, float colorX, float colorY, float colorZ, float colorW) {
        return NodeEditor.nQueryNewNode(pinId != null ? pinId.getData() : null, colorX, colorY, colorZ, colorW);
    }

    public static boolean queryNewNode(ImLong pinId, ImVec4 color, float thickness) {
        return NodeEditor.nQueryNewNode(pinId != null ? pinId.getData() : null, color.x, color.y, color.z, color.w, thickness);
    }

    public static boolean queryNewNode(ImLong pinId, float colorX, float colorY, float colorZ, float colorW, float thickness) {
        return NodeEditor.nQueryNewNode(pinId != null ? pinId.getData() : null, colorX, colorY, colorZ, colorW, thickness);
    }

    private static native boolean nQueryNewNode(long[] var0, float var1, float var2, float var3, float var4);

    private static native boolean nQueryNewNode(long[] var0, float var1, float var2, float var3, float var4, float var5);

    public static boolean acceptNewItem() {
        return NodeEditor.nAcceptNewItem();
    }

    private static native boolean nAcceptNewItem();

    public static boolean acceptNewItem(ImVec4 color) {
        return NodeEditor.nAcceptNewItem(color.x, color.y, color.z, color.w);
    }

    public static boolean acceptNewItem(float colorX, float colorY, float colorZ, float colorW) {
        return NodeEditor.nAcceptNewItem(colorX, colorY, colorZ, colorW);
    }

    public static boolean acceptNewItem(ImVec4 color, float thickness) {
        return NodeEditor.nAcceptNewItem(color.x, color.y, color.z, color.w, thickness);
    }

    public static boolean acceptNewItem(float colorX, float colorY, float colorZ, float colorW, float thickness) {
        return NodeEditor.nAcceptNewItem(colorX, colorY, colorZ, colorW, thickness);
    }

    private static native boolean nAcceptNewItem(float var0, float var1, float var2, float var3);

    private static native boolean nAcceptNewItem(float var0, float var1, float var2, float var3, float var4);

    public static void rejectNewItem() {
        NodeEditor.nRejectNewItem();
    }

    private static native void nRejectNewItem();

    public static void rejectNewItem(ImVec4 color) {
        NodeEditor.nRejectNewItem(color.x, color.y, color.z, color.w);
    }

    public static void rejectNewItem(float colorX, float colorY, float colorZ, float colorW) {
        NodeEditor.nRejectNewItem(colorX, colorY, colorZ, colorW);
    }

    public static void rejectNewItem(ImVec4 color, float thickness) {
        NodeEditor.nRejectNewItem(color.x, color.y, color.z, color.w, thickness);
    }

    public static void rejectNewItem(float colorX, float colorY, float colorZ, float colorW, float thickness) {
        NodeEditor.nRejectNewItem(colorX, colorY, colorZ, colorW, thickness);
    }

    private static native void nRejectNewItem(float var0, float var1, float var2, float var3);

    private static native void nRejectNewItem(float var0, float var1, float var2, float var3, float var4);

    public static void endCreate() {
        NodeEditor.nEndCreate();
    }

    private static native void nEndCreate();

    public static boolean beginDelete() {
        return NodeEditor.nBeginDelete();
    }

    private static native boolean nBeginDelete();

    public static boolean queryDeletedLink(ImLong linkId) {
        return NodeEditor.nQueryDeletedLink(linkId != null ? linkId.getData() : null);
    }

    public static boolean queryDeletedLink(ImLong linkId, ImLong startId) {
        return NodeEditor.nQueryDeletedLink(linkId != null ? linkId.getData() : null, startId != null ? startId.getData() : null);
    }

    public static boolean queryDeletedLink(ImLong linkId, ImLong startId, ImLong endId) {
        return NodeEditor.nQueryDeletedLink(linkId != null ? linkId.getData() : null, startId != null ? startId.getData() : null, endId != null ? endId.getData() : null);
    }

    private static native boolean nQueryDeletedLink(long[] var0);

    private static native boolean nQueryDeletedLink(long[] var0, long[] var1);

    private static native boolean nQueryDeletedLink(long[] var0, long[] var1, long[] var2);

    public static boolean queryDeletedNode(ImLong nodeId) {
        return NodeEditor.nQueryDeletedNode(nodeId != null ? nodeId.getData() : null);
    }

    private static native boolean nQueryDeletedNode(long[] var0);

    public static boolean acceptDeletedItem() {
        return NodeEditor.nAcceptDeletedItem();
    }

    public static boolean acceptDeletedItem(boolean deleteDependencies) {
        return NodeEditor.nAcceptDeletedItem(deleteDependencies);
    }

    private static native boolean nAcceptDeletedItem();

    private static native boolean nAcceptDeletedItem(boolean var0);

    public static void rejectDeletedItem() {
        NodeEditor.nRejectDeletedItem();
    }

    private static native void nRejectDeletedItem();

    public static void endDelete() {
        NodeEditor.nEndDelete();
    }

    private static native void nEndDelete();

    public static void setNodePosition(long nodeId, ImVec2 editorPosition) {
        NodeEditor.nSetNodePosition(nodeId, editorPosition.x, editorPosition.y);
    }

    public static void setNodePosition(long nodeId, float editorPositionX, float editorPositionY) {
        NodeEditor.nSetNodePosition(nodeId, editorPositionX, editorPositionY);
    }

    private static native void nSetNodePosition(long var0, float var2, float var3);

    public static void setGroupSize(long nodeId, ImVec2 size) {
        NodeEditor.nSetGroupSize(nodeId, size.x, size.y);
    }

    public static void setGroupSize(long nodeId, float sizeX, float sizeY) {
        NodeEditor.nSetGroupSize(nodeId, sizeX, sizeY);
    }

    private static native void nSetGroupSize(long var0, float var2, float var3);

    public static ImVec2 getNodePosition(long nodeId) {
        ImVec2 dst = new ImVec2();
        NodeEditor.nGetNodePosition(dst, nodeId);
        return dst;
    }

    public static float getNodePositionX(long nodeId) {
        return NodeEditor.nGetNodePositionX(nodeId);
    }

    public static float getNodePositionY(long nodeId) {
        return NodeEditor.nGetNodePositionY(nodeId);
    }

    public static void getNodePosition(ImVec2 dst, long nodeId) {
        NodeEditor.nGetNodePosition(dst, nodeId);
    }

    private static native void nGetNodePosition(ImVec2 var0, long var1);

    private static native float nGetNodePositionX(long var0);

    private static native float nGetNodePositionY(long var0);

    public static ImVec2 getNodeSize(long nodeId) {
        ImVec2 dst = new ImVec2();
        NodeEditor.nGetNodeSize(dst, nodeId);
        return dst;
    }

    public static float getNodeSizeX(long nodeId) {
        return NodeEditor.nGetNodeSizeX(nodeId);
    }

    public static float getNodeSizeY(long nodeId) {
        return NodeEditor.nGetNodeSizeY(nodeId);
    }

    public static void getNodeSize(ImVec2 dst, long nodeId) {
        NodeEditor.nGetNodeSize(dst, nodeId);
    }

    private static native void nGetNodeSize(ImVec2 var0, long var1);

    private static native float nGetNodeSizeX(long var0);

    private static native float nGetNodeSizeY(long var0);

    public static void centerNodeOnScreen(long nodeId) {
        NodeEditor.nCenterNodeOnScreen(nodeId);
    }

    private static native void nCenterNodeOnScreen(long var0);

    public static void setNodeZPosition(long nodeId, float z) {
        NodeEditor.nSetNodeZPosition(nodeId, z);
    }

    private static native void nSetNodeZPosition(long var0, float var2);

    public static float getNodeZPosition(long nodeId) {
        return NodeEditor.nGetNodeZPosition(nodeId);
    }

    private static native float nGetNodeZPosition(long var0);

    public static void restoreNodeState(long nodeId) {
        NodeEditor.nRestoreNodeState(nodeId);
    }

    private static native void nRestoreNodeState(long var0);

    public static void suspend() {
        NodeEditor.nSuspend();
    }

    private static native void nSuspend();

    public static void resume() {
        NodeEditor.nResume();
    }

    private static native void nResume();

    public static boolean isSuspended() {
        return NodeEditor.nIsSuspended();
    }

    private static native boolean nIsSuspended();

    public static boolean isActive() {
        return NodeEditor.nIsActive();
    }

    private static native boolean nIsActive();

    public static boolean hasSelectionChanged() {
        return NodeEditor.nHasSelectionChanged();
    }

    private static native boolean nHasSelectionChanged();

    public static int getSelectedObjectCount() {
        return NodeEditor.nGetSelectedObjectCount();
    }

    private static native int nGetSelectedObjectCount();

    public static int getSelectedNodes(long[] nodes, int size) {
        return NodeEditor.nGetSelectedNodes(nodes, size);
    }

    private static native int nGetSelectedNodes(long[] var0, int var1);

    public static int getSelectedLinks(long[] links, int size) {
        return NodeEditor.nGetSelectedLinks(links, size);
    }

    private static native int nGetSelectedLinks(long[] var0, int var1);

    public static boolean isNodeSelected(long nodeId) {
        return NodeEditor.nIsNodeSelected(nodeId);
    }

    private static native boolean nIsNodeSelected(long var0);

    public static boolean isLinkSelected(long linkId) {
        return NodeEditor.nIsLinkSelected(linkId);
    }

    private static native boolean nIsLinkSelected(long var0);

    public static void clearSelection() {
        NodeEditor.nClearSelection();
    }

    private static native void nClearSelection();

    public static void selectNode(long nodeId) {
        NodeEditor.nSelectNode(nodeId);
    }

    public static void selectNode(long nodeId, boolean append) {
        NodeEditor.nSelectNode(nodeId, append);
    }

    private static native void nSelectNode(long var0);

    private static native void nSelectNode(long var0, boolean var2);

    public static void selectLink(long linkId) {
        NodeEditor.nSelectLink(linkId);
    }

    public static void selectLink(long linkId, boolean append) {
        NodeEditor.nSelectLink(linkId, append);
    }

    private static native void nSelectLink(long var0);

    private static native void nSelectLink(long var0, boolean var2);

    public static void deselectNode(long nodeId) {
        NodeEditor.nDeselectNode(nodeId);
    }

    private static native void nDeselectNode(long var0);

    public static void deselectLink(long linkId) {
        NodeEditor.nDeselectLink(linkId);
    }

    private static native void nDeselectLink(long var0);

    public static boolean deleteNode(long nodeId) {
        return NodeEditor.nDeleteNode(nodeId);
    }

    private static native boolean nDeleteNode(long var0);

    public static boolean deleteLink(long linkId) {
        return NodeEditor.nDeleteLink(linkId);
    }

    private static native boolean nDeleteLink(long var0);

    public static boolean hasAnyLinksNode(long nodeId) {
        return NodeEditor.nHasAnyLinksNode(nodeId);
    }

    private static native boolean nHasAnyLinksNode(long var0);

    public static boolean hasAnyLinksPin(long pinId) {
        return NodeEditor.nHasAnyLinksPin(pinId);
    }

    private static native boolean nHasAnyLinksPin(long var0);

    public static int breakLinksNode(long nodeId) {
        return NodeEditor.nBreakLinksNode(nodeId);
    }

    private static native int nBreakLinksNode(long var0);

    public static int breakLinksPin(long pinId) {
        return NodeEditor.nBreakLinksPin(pinId);
    }

    private static native int nBreakLinksPin(long var0);

    public static void navigateToContent() {
        NodeEditor.nNavigateToContent();
    }

    public static void navigateToContent(float duration) {
        NodeEditor.nNavigateToContent(duration);
    }

    private static native void nNavigateToContent();

    private static native void nNavigateToContent(float var0);

    public static void navigateToSelection() {
        NodeEditor.nNavigateToSelection();
    }

    public static void navigateToSelection(boolean zoomIn) {
        NodeEditor.nNavigateToSelection(zoomIn);
    }

    public static void navigateToSelection(boolean zoomIn, float duration) {
        NodeEditor.nNavigateToSelection(zoomIn, duration);
    }

    public static void navigateToSelection(float duration) {
        NodeEditor.nNavigateToSelection(duration);
    }

    private static native void nNavigateToSelection();

    private static native void nNavigateToSelection(boolean var0);

    private static native void nNavigateToSelection(boolean var0, float var1);

    private static native void nNavigateToSelection(float var0);

    public static boolean showNodeContextMenu(ImLong nodeId) {
        return NodeEditor.nShowNodeContextMenu(nodeId != null ? nodeId.getData() : null);
    }

    private static native boolean nShowNodeContextMenu(long[] var0);

    public static boolean showPinContextMenu(ImLong pinId) {
        return NodeEditor.nShowPinContextMenu(pinId != null ? pinId.getData() : null);
    }

    private static native boolean nShowPinContextMenu(long[] var0);

    public static boolean showLinkContextMenu(ImLong linkId) {
        return NodeEditor.nShowLinkContextMenu(linkId != null ? linkId.getData() : null);
    }

    private static native boolean nShowLinkContextMenu(long[] var0);

    public static native long getNodeWithContextMenu();

    public static native long getPinWithContextMenu();

    public static native long getLinkWithContextMenu();

    public static boolean showBackgroundContextMenu() {
        return NodeEditor.nShowBackgroundContextMenu();
    }

    private static native boolean nShowBackgroundContextMenu();

    public static void enableShortcuts(boolean enable) {
        NodeEditor.nEnableShortcuts(enable);
    }

    private static native void nEnableShortcuts(boolean var0);

    public static boolean areShortcutsEnabled() {
        return NodeEditor.nAreShortcutsEnabled();
    }

    private static native boolean nAreShortcutsEnabled();

    public static boolean beginShortcut() {
        return NodeEditor.nBeginShortcut();
    }

    private static native boolean nBeginShortcut();

    public static boolean acceptCut() {
        return NodeEditor.nAcceptCut();
    }

    private static native boolean nAcceptCut();

    public static boolean acceptCopy() {
        return NodeEditor.nAcceptCopy();
    }

    private static native boolean nAcceptCopy();

    public static boolean acceptPaste() {
        return NodeEditor.nAcceptPaste();
    }

    private static native boolean nAcceptPaste();

    public static boolean acceptDuplicate() {
        return NodeEditor.nAcceptDuplicate();
    }

    private static native boolean nAcceptDuplicate();

    public static boolean acceptCreateNode() {
        return NodeEditor.nAcceptCreateNode();
    }

    private static native boolean nAcceptCreateNode();

    public static int getActionContextSize() {
        return NodeEditor.nGetActionContextSize();
    }

    private static native int nGetActionContextSize();

    public static int getActionContextNodes(long[] nodes, int size) {
        return NodeEditor.nGetActionContextNodes(nodes, size);
    }

    private static native int nGetActionContextNodes(long[] var0, int var1);

    public static int getActionContextLinks(long[] links, int size) {
        return NodeEditor.nGetActionContextLinks(links, size);
    }

    private static native int nGetActionContextLinks(long[] var0, int var1);

    public static void endShortcut() {
        NodeEditor.nEndShortcut();
    }

    private static native void nEndShortcut();

    public static float getCurrentZoom() {
        return NodeEditor.nGetCurrentZoom();
    }

    private static native float nGetCurrentZoom();

    public static long getHoveredNode() {
        return NodeEditor.nGetHoveredNode();
    }

    private static native long nGetHoveredNode();

    public static long getHoveredPin() {
        return NodeEditor.nGetHoveredPin();
    }

    private static native long nGetHoveredPin();

    public static long getHoveredLink() {
        return NodeEditor.nGetHoveredLink();
    }

    private static native long nGetHoveredLink();

    public static long getDoubleClickedNode() {
        return NodeEditor.nGetDoubleClickedNode();
    }

    private static native long nGetDoubleClickedNode();

    public static long getDoubleClickedPin() {
        return NodeEditor.nGetDoubleClickedPin();
    }

    private static native long nGetDoubleClickedPin();

    public static long getDoubleClickedLink() {
        return NodeEditor.nGetDoubleClickedLink();
    }

    private static native long nGetDoubleClickedLink();

    public static boolean isBackgroundClicked() {
        return NodeEditor.nIsBackgroundClicked();
    }

    private static native boolean nIsBackgroundClicked();

    public static boolean isBackgroundDoubleClicked() {
        return NodeEditor.nIsBackgroundDoubleClicked();
    }

    private static native boolean nIsBackgroundDoubleClicked();

    public static boolean getLinkPins(long linkId, ImLong startPinId, ImLong endPinId) {
        return NodeEditor.nGetLinkPins(linkId, startPinId != null ? startPinId.getData() : null, endPinId != null ? endPinId.getData() : null);
    }

    private static native boolean nGetLinkPins(long var0, long[] var2, long[] var3);

    public static boolean pinHadAnyLinks(long pinId) {
        return NodeEditor.nPinHadAnyLinks(pinId);
    }

    private static native boolean nPinHadAnyLinks(long var0);

    public static ImVec2 getScreenSize() {
        ImVec2 dst = new ImVec2();
        NodeEditor.nGetScreenSize(dst);
        return dst;
    }

    public static float getScreenSizeX() {
        return NodeEditor.nGetScreenSizeX();
    }

    public static float getScreenSizeY() {
        return NodeEditor.nGetScreenSizeY();
    }

    public static void getScreenSize(ImVec2 dst) {
        NodeEditor.nGetScreenSize(dst);
    }

    private static native void nGetScreenSize(ImVec2 var0);

    private static native float nGetScreenSizeX();

    private static native float nGetScreenSizeY();

    public static ImVec2 screenToCanvas(ImVec2 pos) {
        ImVec2 dst = new ImVec2();
        NodeEditor.nScreenToCanvas(dst, pos.x, pos.y);
        return dst;
    }

    public static ImVec2 screenToCanvas(float posX, float posY) {
        ImVec2 dst = new ImVec2();
        NodeEditor.nScreenToCanvas(dst, posX, posY);
        return dst;
    }

    public static float screenToCanvasX(ImVec2 pos) {
        return NodeEditor.nScreenToCanvasX(pos.x, pos.y);
    }

    public static float screenToCanvasY(ImVec2 pos) {
        return NodeEditor.nScreenToCanvasY(pos.x, pos.y);
    }

    public static void screenToCanvas(ImVec2 dst, ImVec2 pos) {
        NodeEditor.nScreenToCanvas(dst, pos.x, pos.y);
    }

    public static void screenToCanvas(ImVec2 dst, float posX, float posY) {
        NodeEditor.nScreenToCanvas(dst, posX, posY);
    }

    private static native void nScreenToCanvas(ImVec2 var0, float var1, float var2);

    private static native float nScreenToCanvasX(float var0, float var1);

    private static native float nScreenToCanvasY(float var0, float var1);

    public static ImVec2 canvasToScreen(ImVec2 pos) {
        ImVec2 dst = new ImVec2();
        NodeEditor.nCanvasToScreen(dst, pos.x, pos.y);
        return dst;
    }

    public static ImVec2 canvasToScreen(float posX, float posY) {
        ImVec2 dst = new ImVec2();
        NodeEditor.nCanvasToScreen(dst, posX, posY);
        return dst;
    }

    public static float canvasToScreenX(ImVec2 pos) {
        return NodeEditor.nCanvasToScreenX(pos.x, pos.y);
    }

    public static float canvasToScreenY(ImVec2 pos) {
        return NodeEditor.nCanvasToScreenY(pos.x, pos.y);
    }

    public static void canvasToScreen(ImVec2 dst, ImVec2 pos) {
        NodeEditor.nCanvasToScreen(dst, pos.x, pos.y);
    }

    public static void canvasToScreen(ImVec2 dst, float posX, float posY) {
        NodeEditor.nCanvasToScreen(dst, posX, posY);
    }

    private static native void nCanvasToScreen(ImVec2 var0, float var1, float var2);

    private static native float nCanvasToScreenX(float var0, float var1);

    private static native float nCanvasToScreenY(float var0, float var1);

    public static int getNodeCount() {
        return NodeEditor.nGetNodeCount();
    }

    private static native int nGetNodeCount();

    public static int getOrderedNodeIds(long[] nodes, int size) {
        return NodeEditor.nGetOrderedNodeIds(nodes, size);
    }

    private static native int nGetOrderedNodeIds(long[] var0, int var1);
}

