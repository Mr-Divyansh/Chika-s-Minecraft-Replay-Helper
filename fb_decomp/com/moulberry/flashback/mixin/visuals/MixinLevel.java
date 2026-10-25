/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.world.level.Level
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.moulberry.flashback.mixin.visuals;

import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Level.class})
public abstract class MixinLevel {
    @Inject(method={"getRainLevel"}, at={@At(value="HEAD")}, cancellable=true)
    public void getRainLevel(float f, CallbackInfoReturnable<Float> cir) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && this instanceof ClientLevel) {
            switch (editorState.replayVisuals.overrideWeatherMode) {
                case CLEAR: {
                    cir.setReturnValue((Object)Float.valueOf(0.0f));
                    break;
                }
                case OVERCAST: 
                case RAINING: 
                case SNOWING: 
                case THUNDERING: {
                    cir.setReturnValue((Object)Float.valueOf(1.0f));
                }
            }
        }
    }

    @Inject(method={"getThunderLevel"}, at={@At(value="HEAD")}, cancellable=true)
    public void getThunderLevel(float f, CallbackInfoReturnable<Float> cir) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && this instanceof ClientLevel) {
            switch (editorState.replayVisuals.overrideWeatherMode) {
                case CLEAR: 
                case OVERCAST: 
                case RAINING: 
                case SNOWING: {
                    cir.setReturnValue((Object)Float.valueOf(0.0f));
                    break;
                }
                case THUNDERING: {
                    cir.setReturnValue((Object)Float.valueOf(1.0f));
                }
            }
        }
    }

    @Inject(method={"getDefaultClockTime", "getOverworldClockTime"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    public void getDefaultClockTime(CallbackInfoReturnable<Long> cir) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && this instanceof ClientLevel && editorState.replayVisuals.overrideTimeOfDay >= 0L) {
            cir.setReturnValue((Object)editorState.replayVisuals.overrideTimeOfDay);
        }
    }
}

