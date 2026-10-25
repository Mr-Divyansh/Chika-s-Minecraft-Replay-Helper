/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.moulberry.mixinconstraints.annotations.IfModLoaded
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Pseudo
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.compat.axiom;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@IfModLoaded(value="axiom")
@Pseudo
@Mixin(targets={"com.moulberry.axiom.editor.EditorUI"}, remap=false)
public class MixinAxiomEditorUI {
    @Shadow
    private static boolean enabled;

    @Inject(method={"isActiveInternal"}, at={@At(value="HEAD")}, cancellable=true)
    private static void isActiveInternal(CallbackInfoReturnable<Boolean> cir) {
        if (ReplayUI.isActive() || Flashback.isInReplay()) {
            enabled = false;
            cir.setReturnValue((Object)false);
        }
    }
}

