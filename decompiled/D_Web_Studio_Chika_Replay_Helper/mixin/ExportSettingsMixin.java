/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.moulberry.flashback.exporting.ExportSettings
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package D_Web_Studio_Chika_Replay_Helper.mixin;

import com.moulberry.flashback.exporting.ExportSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import D_Web_Studio_Chika_Replay_Helper.UiConfig;
import D_Web_Studio_Chika_Replay_Helper.UiExportOptions;

@Mixin(value={ExportSettings.class}, remap=false)
public abstract class ExportSettingsMixin
implements UiExportOptions {
    @Unique
    private boolean yueyue$include;
    @Unique
    private boolean yueyue$follow;

    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    private void yueyue$preferences(CallbackInfo ci) {
        this.yueyue$include = UiConfig.current.include;
        this.yueyue$follow = UiConfig.current.follow;
    }

    @Override
    public boolean yueyue$include() {
        return this.yueyue$include;
    }

    @Override
    public boolean yueyue$follow() {
        return this.yueyue$follow;
    }

    @Inject(method={"noGui"}, at={@At(value="HEAD")}, cancellable=true)
    private void yueyue$cleanOutput(CallbackInfoReturnable<Boolean> cir) {
        if (!this.yueyue$include) {
            cir.setReturnValue(true);
        }
    }
}

