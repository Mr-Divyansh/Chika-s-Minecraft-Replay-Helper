/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.StringUtil
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.packet_fix;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.io.AsyncReplaySaver;
import net.minecraft.util.StringUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={StringUtil.class})
public class MixinStringUtils {
    @Inject(method={"isValidPlayerName", "isAllowedChatCharacter"}, at={@At(value="HEAD")}, cancellable=true)
    private static void validate(CallbackInfoReturnable<Boolean> cir) {
        if (AsyncReplaySaver.WRITING_PACKET.get() == Boolean.TRUE || Flashback.isInReplay()) {
            cir.setReturnValue((Object)true);
        }
    }
}

