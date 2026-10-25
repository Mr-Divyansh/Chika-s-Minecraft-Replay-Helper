/*
 * Decompiled with CFR 0.152.
 */
package imgui.moulberry90.callback;

import imgui.moulberry90.ImGuiInputTextCallbackData;
import java.util.function.Consumer;

public abstract class ImGuiInputTextCallback
implements Consumer<ImGuiInputTextCallbackData> {
    @Override
    public final void accept(long ptr) {
        this.accept(new ImGuiInputTextCallbackData(ptr));
    }
}

