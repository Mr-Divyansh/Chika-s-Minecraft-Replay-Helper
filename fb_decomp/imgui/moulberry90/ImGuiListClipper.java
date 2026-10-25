/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90;

import imgui.moulberry90.binding.ImGuiStructDestroyable;
import imgui.moulberry90.callback.ImListClipperCallback;
import imgui.moulberry90.internal.ImGuiContext;
import java.util.function.Consumer;

public final class ImGuiListClipper
extends ImGuiStructDestroyable {
    private static final ImGuiContext _GETCTX_1 = new ImGuiContext(0L);

    public ImGuiListClipper() {
    }

    public ImGuiListClipper(long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public ImGuiContext getCtx() {
        ImGuiListClipper._GETCTX_1.ptr = this.nGetCtx();
        return _GETCTX_1;
    }

    public void setCtx(ImGuiContext value) {
        this.nSetCtx(value.ptr);
    }

    private native long nGetCtx();

    private native void nSetCtx(long var1);

    public int getDisplayStart() {
        return this.nGetDisplayStart();
    }

    public void setDisplayStart(int value) {
        this.nSetDisplayStart(value);
    }

    private native int nGetDisplayStart();

    private native void nSetDisplayStart(int var1);

    public int getDisplayEnd() {
        return this.nGetDisplayEnd();
    }

    public void setDisplayEnd(int value) {
        this.nSetDisplayEnd(value);
    }

    private native int nGetDisplayEnd();

    private native void nSetDisplayEnd(int var1);

    public int getItemsCount() {
        return this.nGetItemsCount();
    }

    private native int nGetItemsCount();

    public float getItemsHeight() {
        return this.nGetItemsHeight();
    }

    private native float nGetItemsHeight();

    public float getStartPosY() {
        return this.nGetStartPosY();
    }

    private native float nGetStartPosY();

    public void begin(int itemsCount) {
        this.nBegin(itemsCount);
    }

    public void begin(int itemsCount, float itemsHeight) {
        this.nBegin(itemsCount, itemsHeight);
    }

    private native void nBegin(int var1);

    private native void nBegin(int var1, float var2);

    public void end() {
        this.nEnd();
    }

    private native void nEnd();

    public boolean step() {
        return this.nStep();
    }

    private native boolean nStep();

    public void includeItemByIndex(int itemIndex) {
        this.nIncludeItemByIndex(itemIndex);
    }

    private native void nIncludeItemByIndex(int var1);

    public void includeItemsByIndex(int itemBegin, int itemEnd) {
        this.nIncludeItemsByIndex(itemBegin, itemEnd);
    }

    private native void nIncludeItemsByIndex(int var1, int var2);

    public static void forEach(Consumer<ImGuiListClipper> clipAction) {
        ImGuiListClipper clipper = new ImGuiListClipper();
        clipAction.accept(clipper);
        clipper.destroy();
    }

    public static void forEach(int itemsCount, ImListClipperCallback callback) {
        ImGuiListClipper.nForEach(itemsCount, -1, callback);
    }

    public static void forEach(int itemsCount, int itemsHeight, ImListClipperCallback callback) {
        ImGuiListClipper.nForEach(itemsCount, itemsHeight, callback);
    }

    private static native void nForEach(int var0, int var1, ImListClipperCallback var2);
}

