/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.OptionInstance
 *  net.minecraft.client.Options
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.visuals;

import com.moulberry.flashback.ext.OptionsExt;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Options.class})
public class MixinOptions
implements OptionsExt {
    @Shadow
    @Final
    private OptionInstance<Integer> fov;
    @Unique
    private OptionInstance<Integer> cachedFovOptionInstance = null;

    @Inject(method={"fov"}, at={@At(value="RETURN")}, cancellable=true)
    public void fov(CallbackInfoReturnable<OptionInstance<Integer>> cir) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && editorState.replayVisuals.overrideFov) {
            if (this.cachedFovOptionInstance == null || (Integer)this.cachedFovOptionInstance.get() != Math.round(editorState.replayVisuals.overrideFovAmount)) {
                OptionInstance delegate = (OptionInstance)cir.getReturnValue();
                this.cachedFovOptionInstance = new OptionInstance("options.fov", OptionInstance.noTooltip(), Options::genericValueLabel, delegate.values(), delegate.codec(), (Object)Math.round(editorState.replayVisuals.overrideFovAmount), value -> {
                    editorState.replayVisuals.overrideFov = false;
                    delegate.set(value);
                });
            }
            cir.setReturnValue(this.cachedFovOptionInstance);
        }
    }

    @Override
    public int flashback$getOriginalFov() {
        return (Integer)this.fov.get();
    }
}

