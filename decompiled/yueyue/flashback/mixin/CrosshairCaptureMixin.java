/*
 * Updated for Minecraft 26.1.2
 */
package yueyue.flashback.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yueyue.flashback.UiRuntime;

@Mixin(value = {Gui.class})
public abstract class CrosshairCaptureMixin {
    @WrapOperation(method = {"extractRenderState"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;extractCrosshair(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V")})
    private void yueyue$skipRecordedCrosshair(Gui instance, GuiGraphicsExtractor graphics, DeltaTracker delta, Operation<Void> original) {
        if (UiRuntime.recordingEnabled()) {
            return;
        }
        original.call(instance, graphics, delta);
    }
}