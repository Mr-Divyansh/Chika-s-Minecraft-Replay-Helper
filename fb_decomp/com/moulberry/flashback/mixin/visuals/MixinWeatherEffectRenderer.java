/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.WeatherEffectRenderer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.biome.Biome$Precipitation
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.visuals;

import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={WeatherEffectRenderer.class})
public class MixinWeatherEffectRenderer {
    @Inject(method={"getPrecipitationAt"}, at={@At(value="HEAD")}, cancellable=true)
    public void getPrecipitationAt(Level level, BlockPos blockPos, CallbackInfoReturnable<Biome.Precipitation> cir) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null) {
            switch (editorState.replayVisuals.overrideWeatherMode) {
                case CLEAR: 
                case OVERCAST: {
                    cir.setReturnValue((Object)Biome.Precipitation.NONE);
                    break;
                }
                case RAINING: 
                case THUNDERING: {
                    cir.setReturnValue((Object)Biome.Precipitation.RAIN);
                    break;
                }
                case SNOWING: {
                    cir.setReturnValue((Object)Biome.Precipitation.SNOW);
                }
            }
        }
    }
}

