/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.ModifyReturnValue
 *  net.minecraft.client.renderer.fog.FogData
 *  net.minecraft.client.renderer.fog.FogRenderer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.moulberry.flashback.mixin.visuals;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import com.moulberry.flashback.visuals.ReplayVisuals;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={FogRenderer.class}, priority=900)
public class MixinFogRenderer {
    @ModifyReturnValue(method={"setupFog"}, at={@At(value="RETURN")})
    public FogData setupFog(FogData fogData) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null) {
            ReplayVisuals visuals = editorState.replayVisuals;
            if (visuals.overrideFog) {
                fogData.environmentalStart = visuals.overrideFogStart;
                fogData.environmentalEnd = visuals.overrideFogEnd;
                fogData.renderDistanceStart = visuals.overrideFogStart;
                fogData.renderDistanceEnd = visuals.overrideFogEnd;
                fogData.skyEnd = visuals.overrideFogEnd;
                fogData.cloudEnd = visuals.overrideFogEnd;
            }
            if (visuals.overrideFogColour) {
                float[] fogColour = visuals.fogColour;
                fogData.color.set(fogColour[0], fogColour[1], fogColour[2], 1.0f);
            }
        }
        return fogData;
    }
}

