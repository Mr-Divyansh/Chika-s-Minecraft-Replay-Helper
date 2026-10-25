/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.moulberry.mixinconstraints.annotations.IfModLoaded
 *  de.maxhenkel.voicechat.voice.client.speaker.ALSpeakerBase
 *  net.minecraft.client.Camera
 *  net.minecraft.client.renderer.GameRenderer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Pseudo
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.compat.voice_chat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import de.maxhenkel.voicechat.voice.client.speaker.ALSpeakerBase;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded(value="voicechat")
@Pseudo
@Mixin(value={ALSpeakerBase.class})
public class MixinVoiceChatALSpeakerBase {
    @WrapOperation(method={"setPositionSync"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GameRenderer;getMainCamera()Lnet/minecraft/client/Camera;")})
    public Camera setPositionSync_getMainCamera(GameRenderer instance, Operation<Camera> original) {
        Camera audioCamera;
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && (audioCamera = editorState.getAudioCamera()) != null) {
            return audioCamera;
        }
        return (Camera)original.call(new Object[]{instance});
    }
}

