/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.extension.imguizmo;

import imgui.moulberry90.ImDrawList;
import imgui.moulberry90.ImVec2;
import imgui.moulberry90.internal.ImGuiContext;

public final class ImGuizmo {
    private ImGuizmo() {
    }

    public static void setDrawList() {
        ImGuizmo.nSetDrawList();
    }

    public static void setDrawList(ImDrawList drawList) {
        ImGuizmo.nSetDrawList(drawList.ptr);
    }

    private static native void nSetDrawList();

    private static native void nSetDrawList(long var0);

    public static void beginFrame() {
        ImGuizmo.nBeginFrame();
    }

    private static native void nBeginFrame();

    public static void setImGuiContext(ImGuiContext ctx) {
        ImGuizmo.nSetImGuiContext(ctx.ptr);
    }

    private static native void nSetImGuiContext(long var0);

    public static boolean isOver() {
        return ImGuizmo.nIsOver();
    }

    private static native boolean nIsOver();

    public static boolean isUsing() {
        return ImGuizmo.nIsUsing();
    }

    private static native boolean nIsUsing();

    public static void enable(boolean enable) {
        ImGuizmo.nEnable(enable);
    }

    private static native void nEnable(boolean var0);

    public static void decomposeMatrixToComponents(float[] matrix, float[] translation, float[] rotation, float[] scale) {
        ImGuizmo.nDecomposeMatrixToComponents(matrix, translation, rotation, scale);
    }

    private static native void nDecomposeMatrixToComponents(float[] var0, float[] var1, float[] var2, float[] var3);

    public static void recomposeMatrixFromComponents(float[] translation, float[] rotation, float[] scale, float[] matrix) {
        ImGuizmo.nRecomposeMatrixFromComponents(translation, rotation, scale, matrix);
    }

    private static native void nRecomposeMatrixFromComponents(float[] var0, float[] var1, float[] var2, float[] var3);

    public static void setRect(float x, float y, float width, float height) {
        ImGuizmo.nSetRect(x, y, width, height);
    }

    private static native void nSetRect(float var0, float var1, float var2, float var3);

    public static void setOrthographic(boolean isOrthographic) {
        ImGuizmo.nSetOrthographic(isOrthographic);
    }

    private static native void nSetOrthographic(boolean var0);

    public static void drawCubes(float[] view, float[] projection, float[] matrices) {
        ImGuizmo.nDrawCubes(view, projection, matrices);
    }

    private static native void nDrawCubes(float[] var0, float[] var1, float[] var2);

    public static void drawCubes(float[] view, float[] projection, float[] matrices, int matrixCount) {
        ImGuizmo.nDrawCubes(view, projection, matrices, matrixCount);
    }

    private static native void nDrawCubes(float[] var0, float[] var1, float[] var2, int var3);

    public static void drawCubes(float[] view, float[] projection, float[] ... matrices) {
        if (matrices.length > 4) {
            throw new IllegalArgumentException("Only up to 4 cubes matrices is supported");
        }
        float[] cubeMatrices = new float[matrices.length * 16];
        int cubeMatricesLength = matrices.length;
        for (int i = 0; i < cubeMatricesLength; ++i) {
            float[] cubeMatrix = matrices[i];
            System.arraycopy(cubeMatrix, 0, cubeMatrices, i * cubeMatrix.length, cubeMatrix.length);
        }
        ImGuizmo.drawCubes(view, projection, cubeMatrices, cubeMatrices.length);
    }

    public static void drawGrid(float[] view, float[] projection, float[] matrix, float gridSize) {
        ImGuizmo.nDrawGrid(view, projection, matrix, gridSize);
    }

    private static native void nDrawGrid(float[] var0, float[] var1, float[] var2, float var3);

    public static void manipulate(float[] view, float[] projection, int operation, int mode, float[] matrix) {
        ImGuizmo.nManipulate(view, projection, operation, mode, matrix);
    }

    public static void manipulate(float[] view, float[] projection, int operation, int mode, float[] matrix, float[] deltaMatrix) {
        ImGuizmo.nManipulate(view, projection, operation, mode, matrix, deltaMatrix);
    }

    public static void manipulate(float[] view, float[] projection, int operation, int mode, float[] matrix, float[] deltaMatrix, float[] snap) {
        ImGuizmo.nManipulate(view, projection, operation, mode, matrix, deltaMatrix, snap);
    }

    public static void manipulate(float[] view, float[] projection, int operation, int mode, float[] matrix, float[] deltaMatrix, float[] snap, float[] localBounds) {
        ImGuizmo.nManipulate(view, projection, operation, mode, matrix, deltaMatrix, snap, localBounds);
    }

    public static void manipulate(float[] view, float[] projection, int operation, int mode, float[] matrix, float[] deltaMatrix, float[] snap, float[] localBounds, float[] boundsSnap) {
        ImGuizmo.nManipulate(view, projection, operation, mode, matrix, deltaMatrix, snap, localBounds, boundsSnap);
    }

    private static native void nManipulate(float[] var0, float[] var1, int var2, int var3, float[] var4);

    private static native void nManipulate(float[] var0, float[] var1, int var2, int var3, float[] var4, float[] var5);

    private static native void nManipulate(float[] var0, float[] var1, int var2, int var3, float[] var4, float[] var5, float[] var6);

    private static native void nManipulate(float[] var0, float[] var1, int var2, int var3, float[] var4, float[] var5, float[] var6, float[] var7);

    private static native void nManipulate(float[] var0, float[] var1, int var2, int var3, float[] var4, float[] var5, float[] var6, float[] var7, float[] var8);

    public static void viewManipulate(float[] view, float length, ImVec2 position, ImVec2 size, int backgroundColor) {
        ImGuizmo.nViewManipulate(view, length, position.x, position.y, size.x, size.y, backgroundColor);
    }

    public static void viewManipulate(float[] view, float length, float positionX, float positionY, float sizeX, float sizeY, int backgroundColor) {
        ImGuizmo.nViewManipulate(view, length, positionX, positionY, sizeX, sizeY, backgroundColor);
    }

    private static native void nViewManipulate(float[] var0, float var1, float var2, float var3, float var4, float var5, int var6);

    public static void setID(int id) {
        ImGuizmo.nSetID(id);
    }

    private static native void nSetID(int var0);

    public static boolean isOver(int operation) {
        return ImGuizmo.nIsOver(operation);
    }

    private static native boolean nIsOver(int var0);

    public static void setGizmoSizeClipSpace(float value) {
        ImGuizmo.nSetGizmoSizeClipSpace(value);
    }

    private static native void nSetGizmoSizeClipSpace(float var0);

    public static void allowAxisFlip(boolean value) {
        ImGuizmo.nAllowAxisFlip(value);
    }

    private static native void nAllowAxisFlip(boolean var0);
}

