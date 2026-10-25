/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.moulberry.mixinconstraints.annotations.IfModLoaded
 *  de.maxhenkel.voicechat.voice.client.SoundManager
 *  net.minecraft.client.Minecraft
 *  org.lwjgl.openal.EXTThreadLocalContext
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Pseudo
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.compat.voice_chat;

import com.moulberry.flashback.Flashback;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import de.maxhenkel.voicechat.voice.client.SoundManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.openal.EXTThreadLocalContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@IfModLoaded(value="voicechat")
@Pseudo
@Mixin(value={SoundManager.class}, remap=false)
public class MixinVoiceChatSoundManager {
    @Inject(method={"openContext"}, at={@At(value="HEAD")}, cancellable=true)
    public void openContext(CallbackInfoReturnable<Boolean> cir) {
        if (Flashback.isExporting() && Flashback.EXPORT_JOB.getSettings().recordAudio()) {
            long context = Minecraft.getInstance().getSoundManager().soundEngine.library.context;
            cir.setReturnValue((Object)EXTThreadLocalContext.alcSetThreadContext((long)context));
        }
    }
}

