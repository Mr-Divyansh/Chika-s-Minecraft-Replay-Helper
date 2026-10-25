/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.imnodes;

import imgui.moulberry90.ImVec2;
import imgui.moulberry90.extension.imnodes.ImNodesContext;
import imgui.moulberry90.extension.imnodes.ImNodesEditorContext;
import imgui.moulberry90.extension.imnodes.ImNodesIO;
import imgui.moulberry90.extension.imnodes.ImNodesStyle;
import imgui.moulberry90.internal.ImGuiContext;
import imgui.moulberry90.type.ImBoolean;
import imgui.moulberry90.type.ImInt;

public final class ImNodes {
    private static final ImNodesIO _GETIO_1 = new ImNodesIO(0L);
    private static final ImNodesStyle _GETSTYLE_1 = new ImNodesStyle(0L);

    private ImNodes() {
    }

    public static void setImGuiContext(ImGuiContext ctx) {
        ImNodes.nSetImGuiContext(ctx.ptr);
    }

    private static native void nSetImGuiContext(long var0);

    public static ImNodesContext createContext() {
        return new ImNodesContext(ImNodes.nCreateContext());
    }

    private static native long nCreateContext();

    public static void destroyContext() {
        ImNodes.nDestroyContext();
    }

    public static void destroyContext(ImNodesContext ctx) {
        ImNodes.nDestroyContext(ctx.ptr);
    }

    private static native void nDestroyContext();

    private static native void nDestroyContext(long var0);

    public static ImNodesContext getCurrentContext() {
        return new ImNodesContext(ImNodes.nGetCurrentContext());
    }

    private static native long nGetCurrentContext();

    public static void setCurrentContext(ImNodesContext ctx) {
        ImNodes.nSetCurrentContext(ctx.ptr);
    }

    private static native void nSetCurrentContext(long var0);

    public static ImNodesEditorContext editorContextCreate() {
        return new ImNodesEditorContext(ImNodes.nEditorContextCreate());
    }

    private static native long nEditorContextCreate();

    public static void editorContextFree(ImNodesEditorContext context) {
        ImNodes.nEditorContextFree(context.ptr);
    }

    private static native void nEditorContextFree(long var0);

    public static void editorContextSet(ImNodesEditorContext context) {
        ImNodes.nEditorContextSet(context.ptr);
    }

    private static native void nEditorContextSet(long var0);

    public static ImVec2 editorContextGetPanning() {
        ImVec2 dst = new ImVec2();
        ImNodes.nEditorContextGetPanning(dst);
        return dst;
    }

    public static float editorContextGetPanningX() {
        return ImNodes.nEditorContextGetPanningX();
    }

    public static float editorContextGetPanningY() {
        return ImNodes.nEditorContextGetPanningY();
    }

    public static void editorContextGetPanning(ImVec2 dst) {
        ImNodes.nEditorContextGetPanning(dst);
    }

    private static native void nEditorContextGetPanning(ImVec2 var0);

    private static native float nEditorContextGetPanningX();

    private static native float nEditorContextGetPanningY();

    public static void editorContextResetPanning(ImVec2 pos) {
        ImNodes.nEditorContextResetPanning(pos.x, pos.y);
    }

    public static void editorContextResetPanning(float posX, float posY) {
        ImNodes.nEditorContextResetPanning(posX, posY);
    }

    private static native void nEditorContextResetPanning(float var0, float var1);

    public static void editorContextMoveToNode(int nodeId) {
        ImNodes.nEditorContextMoveToNode(nodeId);
    }

    private static native void nEditorContextMoveToNode(int var0);

    public static ImNodesIO getIO() {
        ImNodes._GETIO_1.ptr = ImNodes.nGetIO();
        return _GETIO_1;
    }

    private static native long nGetIO();

    public static ImNodesStyle getStyle() {
        ImNodes._GETSTYLE_1.ptr = ImNodes.nGetStyle();
        return _GETSTYLE_1;
    }

    private static native long nGetStyle();

    public static void styleColorsDark() {
        ImNodes.nStyleColorsDark();
    }

    public static void styleColorsDark(ImNodesStyle style) {
        ImNodes.nStyleColorsDark(style.ptr);
    }

    private static native void nStyleColorsDark();

    private static native void nStyleColorsDark(long var0);

    public static void styleColorsClassic() {
        ImNodes.nStyleColorsClassic();
    }

    public static void styleColorsClassic(ImNodesStyle style) {
        ImNodes.nStyleColorsClassic(style.ptr);
    }

    private static native void nStyleColorsClassic();

    private static native void nStyleColorsClassic(long var0);

    public static void styleColorsLight() {
        ImNodes.nStyleColorsLight();
    }

    public static void styleColorsLight(ImNodesStyle style) {
        ImNodes.nStyleColorsLight(style.ptr);
    }

    private static native void nStyleColorsLight();

    private static native void nStyleColorsLight(long var0);

    public static void beginNodeEditor() {
        ImNodes.nBeginNodeEditor();
    }

    private static native void nBeginNodeEditor();

    public static void endNodeEditor() {
        ImNodes.nEndNodeEditor();
    }

    private static native void nEndNodeEditor();

    public static void miniMap() {
        ImNodes.nMiniMap();
    }

    public static void miniMap(float miniMapSizeFraction) {
        ImNodes.nMiniMap(miniMapSizeFraction);
    }

    public static void miniMap(float miniMapSizeFraction, int miniMapLocation) {
        ImNodes.nMiniMap(miniMapSizeFraction, miniMapLocation);
    }

    private static native void nMiniMap();

    private static native void nMiniMap(float var0);

    private static native void nMiniMap(float var0, int var1);

    public static void pushColorStyle(int item, int color) {
        ImNodes.nPushColorStyle(item, color);
    }

    private static native void nPushColorStyle(int var0, int var1);

    public static void popColorStyle() {
        ImNodes.nPopColorStyle();
    }

    private static native void nPopColorStyle();

    public static void pushStyleVar(int styleItem, float value) {
        ImNodes.nPushStyleVar(styleItem, value);
    }

    private static native void nPushStyleVar(int var0, float var1);

    public static void pushStyleVar(int styleItem, ImVec2 value) {
        ImNodes.nPushStyleVar(styleItem, value.x, value.y);
    }

    public static void pushStyleVar(int styleItem, float valueX, float valueY) {
        ImNodes.nPushStyleVar(styleItem, valueX, valueY);
    }

    private static native void nPushStyleVar(int var0, float var1, float var2);

    public static void popStyleVar() {
        ImNodes.nPopStyleVar();
    }

    private static native void nPopStyleVar();

    public static void beginNode(int node) {
        ImNodes.nBeginNode(node);
    }

    private static native void nBeginNode(int var0);

    public static void endNode() {
        ImNodes.nEndNode();
    }

    private static native void nEndNode();

    public static ImVec2 getNodeDimensions(int id) {
        ImVec2 dst = new ImVec2();
        ImNodes.nGetNodeDimensions(dst, id);
        return dst;
    }

    public static float getNodeDimensionsX(int id) {
        return ImNodes.nGetNodeDimensionsX(id);
    }

    public static float getNodeDimensionsY(int id) {
        return ImNodes.nGetNodeDimensionsY(id);
    }

    public static void getNodeDimensions(ImVec2 dst, int id) {
        ImNodes.nGetNodeDimensions(dst, id);
    }

    private static native void nGetNodeDimensions(ImVec2 var0, int var1);

    private static native float nGetNodeDimensionsX(int var0);

    private static native float nGetNodeDimensionsY(int var0);

    public static void beginNodeTitleBar() {
        ImNodes.nBeginNodeTitleBar();
    }

    private static native void nBeginNodeTitleBar();

    public static void endNodeTitleBar() {
        ImNodes.nEndNodeTitleBar();
    }

    private static native void nEndNodeTitleBar();

    public static void beginInputAttribute(int id) {
        ImNodes.nBeginInputAttribute(id);
    }

    public static void beginInputAttribute(int id, int shape) {
        ImNodes.nBeginInputAttribute(id, shape);
    }

    private static native void nBeginInputAttribute(int var0);

    private static native void nBeginInputAttribute(int var0, int var1);

    public static void endInputAttribute() {
        ImNodes.nEndInputAttribute();
    }

    private static native void nEndInputAttribute();

    public static void beginOutputAttribute(int id) {
        ImNodes.nBeginOutputAttribute(id);
    }

    public static void beginOutputAttribute(int id, int shape) {
        ImNodes.nBeginOutputAttribute(id, shape);
    }

    private static native void nBeginOutputAttribute(int var0);

    private static native void nBeginOutputAttribute(int var0, int var1);

    public static void endOutputAttribute() {
        ImNodes.nEndOutputAttribute();
    }

    private static native void nEndOutputAttribute();

    public static void beginStaticAttribute(int id) {
        ImNodes.nBeginStaticAttribute(id);
    }

    private static native void nBeginStaticAttribute(int var0);

    public static void endStaticAttribute() {
        ImNodes.nEndStaticAttribute();
    }

    private static native void nEndStaticAttribute();

    public static void pushAttributeFlag(int flag) {
        ImNodes.nPushAttributeFlag(flag);
    }

    private static native void nPushAttributeFlag(int var0);

    public static void popAttributeFlag() {
        ImNodes.nPopAttributeFlag();
    }

    private static native void nPopAttributeFlag();

    public static void link(int id, int source, int target) {
        ImNodes.nLink(id, source, target);
    }

    private static native void nLink(int var0, int var1, int var2);

    public static void setNodeDraggable(int nodeId, boolean draggable) {
        ImNodes.nSetNodeDraggable(nodeId, draggable);
    }

    private static native void nSetNodeDraggable(int var0, boolean var1);

    public static void setNodeScreenSpacePos(int nodeId, ImVec2 screenSpacePos) {
        ImNodes.nSetNodeScreenSpacePos(nodeId, screenSpacePos.x, screenSpacePos.y);
    }

    public static void setNodeScreenSpacePos(int nodeId, float screenSpacePosX, float screenSpacePosY) {
        ImNodes.nSetNodeScreenSpacePos(nodeId, screenSpacePosX, screenSpacePosY);
    }

    private static native void nSetNodeScreenSpacePos(int var0, float var1, float var2);

    public static void setNodeEditorSpacePos(int nodeId, ImVec2 editorSpacePos) {
        ImNodes.nSetNodeEditorSpacePos(nodeId, editorSpacePos.x, editorSpacePos.y);
    }

    public static void setNodeEditorSpacePos(int nodeId, float editorSpacePosX, float editorSpacePosY) {
        ImNodes.nSetNodeEditorSpacePos(nodeId, editorSpacePosX, editorSpacePosY);
    }

    private static native void nSetNodeEditorSpacePos(int var0, float var1, float var2);

    public static void setNodeGridSpacePos(int nodeId, ImVec2 gridPos) {
        ImNodes.nSetNodeGridSpacePos(nodeId, gridPos.x, gridPos.y);
    }

    public static void setNodeGridSpacePos(int nodeId, float gridPosX, float gridPosY) {
        ImNodes.nSetNodeGridSpacePos(nodeId, gridPosX, gridPosY);
    }

    private static native void nSetNodeGridSpacePos(int var0, float var1, float var2);

    public static ImVec2 getNodeScreenSpacePos(int nodeId) {
        ImVec2 dst = new ImVec2();
        ImNodes.nGetNodeScreenSpacePos(dst, nodeId);
        return dst;
    }

    public static float getNodeScreenSpacePosX(int nodeId) {
        return ImNodes.nGetNodeScreenSpacePosX(nodeId);
    }

    public static float getNodeScreenSpacePosY(int nodeId) {
        return ImNodes.nGetNodeScreenSpacePosY(nodeId);
    }

    public static void getNodeScreenSpacePos(ImVec2 dst, int nodeId) {
        ImNodes.nGetNodeScreenSpacePos(dst, nodeId);
    }

    private static native void nGetNodeScreenSpacePos(ImVec2 var0, int var1);

    private static native float nGetNodeScreenSpacePosX(int var0);

    private static native float nGetNodeScreenSpacePosY(int var0);

    public static ImVec2 getNodeEditorSpacePos(int nodeId) {
        ImVec2 dst = new ImVec2();
        ImNodes.nGetNodeEditorSpacePos(dst, nodeId);
        return dst;
    }

    public static float getNodeEditorSpacePosX(int nodeId) {
        return ImNodes.nGetNodeEditorSpacePosX(nodeId);
    }

    public static float getNodeEditorSpacePosY(int nodeId) {
        return ImNodes.nGetNodeEditorSpacePosY(nodeId);
    }

    public static void getNodeEditorSpacePos(ImVec2 dst, int nodeId) {
        ImNodes.nGetNodeEditorSpacePos(dst, nodeId);
    }

    private static native void nGetNodeEditorSpacePos(ImVec2 var0, int var1);

    private static native float nGetNodeEditorSpacePosX(int var0);

    private static native float nGetNodeEditorSpacePosY(int var0);

    public static ImVec2 getNodeGridSpacePos(int nodeId) {
        ImVec2 dst = new ImVec2();
        ImNodes.nGetNodeGridSpacePos(dst, nodeId);
        return dst;
    }

    public static float getNodeGridSpacePosX(int nodeId) {
        return ImNodes.nGetNodeGridSpacePosX(nodeId);
    }

    public static float getNodeGridSpacePosY(int nodeId) {
        return ImNodes.nGetNodeGridSpacePosY(nodeId);
    }

    public static void getNodeGridSpacePos(ImVec2 dst, int nodeId) {
        ImNodes.nGetNodeGridSpacePos(dst, nodeId);
    }

    private static native void nGetNodeGridSpacePos(ImVec2 var0, int var1);

    private static native float nGetNodeGridSpacePosX(int var0);

    private static native float nGetNodeGridSpacePosY(int var0);

    public static void snapNodeToGrid(int nodeId) {
        ImNodes.nSnapNodeToGrid(nodeId);
    }

    private static native void nSnapNodeToGrid(int var0);

    public static boolean isEditorHovered() {
        return ImNodes.nIsEditorHovered();
    }

    private static native boolean nIsEditorHovered();

    public static boolean isNodeHovered(ImInt nodeId) {
        return ImNodes.nIsNodeHovered(nodeId != null ? nodeId.getData() : null);
    }

    private static native boolean nIsNodeHovered(int[] var0);

    public static boolean isLinkHovered(ImInt linkId) {
        return ImNodes.nIsLinkHovered(linkId != null ? linkId.getData() : null);
    }

    private static native boolean nIsLinkHovered(int[] var0);

    public static boolean isPinHovered(ImInt attributeId) {
        return ImNodes.nIsPinHovered(attributeId != null ? attributeId.getData() : null);
    }

    private static native boolean nIsPinHovered(int[] var0);

    public static native int getHoveredNode();

    public static native int getHoveredLink();

    public static native int getHoveredPin();

    public static int numSelectedNodes() {
        return ImNodes.nNumSelectedNodes();
    }

    private static native int nNumSelectedNodes();

    public static int numSelectedLinks() {
        return ImNodes.nNumSelectedLinks();
    }

    private static native int nNumSelectedLinks();

    public static void getSelectedNodes(int[] nodeIds) {
        ImNodes.nGetSelectedNodes(nodeIds);
    }

    private static native void nGetSelectedNodes(int[] var0);

    public static void getSelectedLinks(int[] linkIds) {
        ImNodes.nGetSelectedLinks(linkIds);
    }

    private static native void nGetSelectedLinks(int[] var0);

    public static void clearNodeSelection() {
        ImNodes.nClearNodeSelection();
    }

    private static native void nClearNodeSelection();

    public static void clearLinkSelection() {
        ImNodes.nClearLinkSelection();
    }

    private static native void nClearLinkSelection();

    public static void selectNode(int nodeId) {
        ImNodes.nSelectNode(nodeId);
    }

    private static native void nSelectNode(int var0);

    public static void clearNodeSelection(int nodeId) {
        ImNodes.nClearNodeSelection(nodeId);
    }

    private static native void nClearNodeSelection(int var0);

    public static boolean isNodeSelected(int nodeId) {
        return ImNodes.nIsNodeSelected(nodeId);
    }

    private static native boolean nIsNodeSelected(int var0);

    public static void selectLink(int linkId) {
        ImNodes.nSelectLink(linkId);
    }

    private static native void nSelectLink(int var0);

    public static void clearLinkSelection(int linkId) {
        ImNodes.nClearLinkSelection(linkId);
    }

    private static native void nClearLinkSelection(int var0);

    public static boolean isLinkSelected(int linkId) {
        return ImNodes.nIsLinkSelected(linkId);
    }

    private static native boolean nIsLinkSelected(int var0);

    public static boolean isAttributeActive() {
        return ImNodes.nIsAttributeActive();
    }

    private static native boolean nIsAttributeActive();

    public static boolean isAnyAttributeActive() {
        return ImNodes.nIsAnyAttributeActive();
    }

    public static boolean isAnyAttributeActive(ImInt attributeId) {
        return ImNodes.nIsAnyAttributeActive(attributeId != null ? attributeId.getData() : null);
    }

    private static native boolean nIsAnyAttributeActive();

    private static native boolean nIsAnyAttributeActive(int[] var0);

    public static native int getActiveAttribute();

    public static boolean isLinkStarted(ImInt startedAtAttributeId) {
        return ImNodes.nIsLinkStarted(startedAtAttributeId != null ? startedAtAttributeId.getData() : null);
    }

    private static native boolean nIsLinkStarted(int[] var0);

    public static boolean isLinkDropped() {
        return ImNodes.nIsLinkDropped();
    }

    public static boolean isLinkDropped(ImInt startedAtAttributeId) {
        return ImNodes.nIsLinkDropped(startedAtAttributeId != null ? startedAtAttributeId.getData() : null);
    }

    public static boolean isLinkDropped(ImInt startedAtAttributeId, boolean includingDetachedLinks) {
        return ImNodes.nIsLinkDropped(startedAtAttributeId != null ? startedAtAttributeId.getData() : null, includingDetachedLinks);
    }

    public static boolean isLinkDropped(boolean includingDetachedLinks) {
        return ImNodes.nIsLinkDropped(includingDetachedLinks);
    }

    private static native boolean nIsLinkDropped();

    private static native boolean nIsLinkDropped(int[] var0);

    private static native boolean nIsLinkDropped(int[] var0, boolean var1);

    private static native boolean nIsLinkDropped(boolean var0);

    public static boolean isLinkCreated(ImInt startedAtAttributeId, ImInt endedAtAttributeId) {
        return ImNodes.nIsLinkCreated(startedAtAttributeId != null ? startedAtAttributeId.getData() : null, endedAtAttributeId != null ? endedAtAttributeId.getData() : null);
    }

    public static boolean isLinkCreated(ImInt startedAtAttributeId, ImInt endedAtAttributeId, ImBoolean createdFromSnap) {
        return ImNodes.nIsLinkCreated(startedAtAttributeId != null ? startedAtAttributeId.getData() : null, endedAtAttributeId != null ? endedAtAttributeId.getData() : null, createdFromSnap != null ? createdFromSnap.getData() : null);
    }

    private static native boolean nIsLinkCreated(int[] var0, int[] var1);

    private static native boolean nIsLinkCreated(int[] var0, int[] var1, boolean[] var2);

    public static boolean isLinkCreated(ImInt startedAtNodeId, ImInt startedAtAttributeId, ImInt endedAtNodeId, ImInt endedAtAttributeId) {
        return ImNodes.nIsLinkCreated(startedAtNodeId != null ? startedAtNodeId.getData() : null, startedAtAttributeId != null ? startedAtAttributeId.getData() : null, endedAtNodeId != null ? endedAtNodeId.getData() : null, endedAtAttributeId != null ? endedAtAttributeId.getData() : null);
    }

    public static boolean isLinkCreated(ImInt startedAtNodeId, ImInt startedAtAttributeId, ImInt endedAtNodeId, ImInt endedAtAttributeId, ImBoolean createdFromSnap) {
        return ImNodes.nIsLinkCreated(startedAtNodeId != null ? startedAtNodeId.getData() : null, startedAtAttributeId != null ? startedAtAttributeId.getData() : null, endedAtNodeId != null ? endedAtNodeId.getData() : null, endedAtAttributeId != null ? endedAtAttributeId.getData() : null, createdFromSnap != null ? createdFromSnap.getData() : null);
    }

    private static native boolean nIsLinkCreated(int[] var0, int[] var1, int[] var2, int[] var3);

    private static native boolean nIsLinkCreated(int[] var0, int[] var1, int[] var2, int[] var3, boolean[] var4);

    public static boolean isLinkDestroyed(ImInt linkId) {
        return ImNodes.nIsLinkDestroyed(linkId != null ? linkId.getData() : null);
    }

    private static native boolean nIsLinkDestroyed(int[] var0);

    public static String saveCurrentEditorStateToIniString() {
        return ImNodes.nSaveCurrentEditorStateToIniString();
    }

    private static native String nSaveCurrentEditorStateToIniString();

    public static String saveEditorStateToIniString(ImNodesEditorContext editor) {
        return ImNodes.nSaveEditorStateToIniString(editor.ptr);
    }

    private static native String nSaveEditorStateToIniString(long var0);

    public static void loadCurrentEditorStateFromIniString(String data, int dataSize) {
        ImNodes.nLoadCurrentEditorStateFromIniString(data, dataSize);
    }

    private static native void nLoadCurrentEditorStateFromIniString(String var0, int var1);

    public static void loadEditorStateFromIniString(ImNodesEditorContext editor, String data, int dataSize) {
        ImNodes.nLoadEditorStateFromIniString(editor.ptr, data, dataSize);
    }

    private static native void nLoadEditorStateFromIniString(long var0, String var2, int var3);

    public static void saveCurrentEditorStateToIniFile(String fileName) {
        ImNodes.nSaveCurrentEditorStateToIniFile(fileName);
    }

    private static native void nSaveCurrentEditorStateToIniFile(String var0);

    public static void saveEditorStateToIniFile(ImNodesEditorContext editor, String fileName) {
        ImNodes.nSaveEditorStateToIniFile(editor.ptr, fileName);
    }

    private static native void nSaveEditorStateToIniFile(long var0, String var2);

    public static void loadCurrentEditorStateFromIniFile(String fileName) {
        ImNodes.nLoadCurrentEditorStateFromIniFile(fileName);
    }

    private static native void nLoadCurrentEditorStateFromIniFile(String var0);

    public static void loadEditorStateFromIniFile(ImNodesEditorContext editor, String fileName) {
        ImNodes.nLoadEditorStateFromIniFile(editor.ptr, fileName);
    }

    private static native void nLoadEditorStateFromIniFile(long var0, String var2);
}

