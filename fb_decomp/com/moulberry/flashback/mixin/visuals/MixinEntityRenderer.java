/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.world.entity.Entity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.visuals;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityRenderer.class})
public class MixinEntityRenderer {
    @WrapOperation(method={"extractRenderState"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/entity/Entity;isInvisible()Z")})
    public boolean isInvisible(Entity instance, Operation<Boolean> original) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && editorState.isEntityHidden(instance)) {
            return true;
        }
        return (Boolean)original.call(new Object[]{instance});
    }

    @WrapOperation(method={"extractRenderState"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/entity/EntityRenderer;shouldShowName(Lnet/minecraft/world/entity/Entity;D)Z")})
    public boolean shouldShowName(EntityRenderer<?, ?> instance, Entity entity, double distance, Operation<Boolean> original) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null) {
            if (!editorState.replayVisuals.renderNametags) {
                return false;
            }
            if (editorState.hideNametags.contains(entity.getUUID())) {
                return false;
            }
            if (editorState.isEntityHidden(entity)) {
                return false;
            }
        }
        return (Boolean)original.call(new Object[]{instance, entity, distance});
    }

    @Inject(method={"shouldShowName"}, at={@At(value="HEAD")}, cancellable=true)
    public void shouldShowName(Entity entity, double d, CallbackInfoReturnable<Boolean> cir) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null) {
            if (!editorState.replayVisuals.renderNametags) {
                cir.setReturnValue((Object)false);
            } else if (editorState.hideNametags.contains(entity.getUUID())) {
                cir.setReturnValue((Object)false);
            } else if (editorState.isEntityHidden(entity)) {
                cir.setReturnValue((Object)false);
            }
        }
    }

    @Inject(method={"submitNameDisplay(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;I)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void submitNameDisplay(CallbackInfo ci) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && !editorState.replayVisuals.renderNametags) {
            ci.cancel();
        }
    }
}

