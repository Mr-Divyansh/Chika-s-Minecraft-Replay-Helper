/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.moulberry.flashback.editor.ui.windows.StartExportWindow
 *  imgui.moulberry90.ImGui
 *  net.minecraft.client.resources.language.I18n
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package yueyue.flashback.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.editor.ui.windows.StartExportWindow;
import imgui.moulberry90.ImGui;
import net.minecraft.client.resources.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yueyue.flashback.UiConfig;
import yueyue.flashback.UiRuntime;

@Mixin(value={StartExportWindow.class}, remap=false)
public abstract class ExportWindowMixin {
    @WrapOperation(method={"render"}, at={@At(value="INVOKE", target="Limgui/moulberry90/ImGui;checkbox(Ljava/lang/String;Z)Z")})
    private static boolean yueyue$options(String name, boolean selected, Operation<Boolean> original) {
        boolean changed = (Boolean)original.call(new Object[]{name, selected});
        if (name.equals(I18n.get((String)"flashback.no_gui", (Object[])new Object[0]))) {
            ImGui.separator();
            boolean save = false;
            if (ImGui.checkbox((String)"\u5bfc\u51fa\u5f55\u5236\u7684\u771f\u5b9e HUD / \u80cc\u5305 / \u5bb9\u5668\u754c\u9762###yueyue_include", (boolean)UiConfig.current.include)) {
                UiConfig.current.include = !UiConfig.current.include;
                save = true;
            }
            if (ImGui.checkbox((String)"\u9501\u5b9a\u5f55\u5236\u8005\u7b2c\u4e00\u4eba\u79f0\u955c\u5934###yueyue_follow", (boolean)UiConfig.current.follow)) {
                UiConfig.current.follow = !UiConfig.current.follow;
                save = true;
            }
            if (ImGui.checkbox((String)"\u9884\u89c8\u5f55\u5236\u7684\u771f\u5b9e\u754c\u9762###yueyue_preview", (boolean)UiConfig.current.preview)) {
                UiConfig.current.preview = !UiConfig.current.preview;
                save = true;
            }
            if (ImGui.checkbox((String)(UiConfig.current.record ? "Record With Inventory: ON###yueyue_record" : "Record With Inventory: OFF###yueyue_record"), (boolean)UiConfig.current.record)) {
                UiConfig.current.record = !UiConfig.current.record;
                save = true;
            }
            ImGui.textWrapped((String)"\u4ec5\u5b89\u88c5\u6269\u5c55\u540e\u5f55\u5236\u7684\u7247\u6bb5\u5305\u542b\u6b64\u8f68\u9053\uff1b\u52fe\u9009\u539f\u7248\u201c\u9690\u85cfGUI\u201d\u4ecd\u4f1a\u9690\u85cf\u5b83\u3002\u754c\u9762\u6700\u591a125\u5e27/\u79d2\uff0c\u4e16\u754c\u753b\u9762\u4f7f\u7528\u539f\u5bfc\u51fa\u5e27\u7387\u3002");
            if (UiRuntime.currentFrame() == null) {
                ImGui.textWrapped((String)"\u5f53\u524d\u7247\u6bb5\u6ca1\u6709\u771f\u5b9e\u754c\u9762\u8bb0\u5f55\uff0c\u5c06\u4f7f\u7528 Flashback \u539f\u6709\u753b\u9762\u3002");
            }
            ImGui.separator();
            if (save) {
                UiConfig.save();
            }
        }
        return changed;
    }
}

