/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.entity.EntityRenderDispatcher
 *  net.minecraft.client.renderer.entity.state.EntityRenderState
 *  net.minecraft.world.entity.Entity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.visuals;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityRenderDispatcher.class}, priority=990)
public abstract class MixinEntityRenderDispatcher {
    @Inject(method={"shouldRender"}, at={@At(value="HEAD")}, cancellable=true)
    public void shouldRender(Entity entity, Frustum frustum, double d, double e, double f, CallbackInfoReturnable<Boolean> cir) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null) {
            String id;
            if (!editorState.filteredEntities.isEmpty() && editorState.filteredEntities.contains(id = entity.getType().builtInRegistryHolder().key().identifier().toString())) {
                cir.setReturnValue((Object)false);
                return;
            }
            if (editorState.isEntityHidden(entity)) {
                cir.setReturnValue((Object)false);
            }
        }
    }

    @Inject(method={"extractEntity"}, at={@At(value="RETURN")})
    public void extractEntity(Entity entity, float partialTick, CallbackInfoReturnable<EntityRenderState> cir) {
        EntityRenderState renderState = (EntityRenderState)cir.getReturnValue();
        if (Flashback.isInReplay()) {
            EditorState editorState = EditorStateManager.getCurrent();
            if (editorState == null) {
                return;
            }
            if (!editorState.replayVisuals.renderBlocks) {
                renderState.shadowRadius = 0.0f;
                renderState.shadowPieces.clear();
            }
        }
    }
}

