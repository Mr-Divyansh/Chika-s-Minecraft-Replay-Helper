/*
 * Updated for Minecraft 26.1.2
 */
package D_Web_Studio_Chika_Replay_Helper.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.function.Supplier;
import net.minecraft.client.gui.render.GuiRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import D_Web_Studio_Chika_Replay_Helper.capture.UiCapture;

@Mixin(value={GuiRenderer.class})
public abstract class GuiCaptureMixin {
    @Unique
    private boolean yueyue$skipCaptureDraw;

    @Inject(method={"draw"}, at={@At(value="HEAD")})
    private void yueyue$beginCapture(GpuBufferSlice transforms, CallbackInfo ci) {
        UiCapture.beginFrame();
    }

    @WrapOperation(method={"draw"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/render/GuiRenderer;executeDrawRange(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/pipeline/RenderTarget;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;II)V")})
    private void yueyue$copyDrawRange(GuiRenderer renderer, Supplier<String> label, RenderTarget target, GpuBufferSlice transforms, GpuBufferSlice dynamicTransforms, GpuBuffer vertexBuffer, VertexFormat.IndexType indexType, int start, int end, Operation<Void> original) {
        original.call(renderer, label, target, transforms, dynamicTransforms, vertexBuffer, indexType, start, end);
        RenderTarget capture = UiCapture.target();
        if (capture == null) {
            return;
        }
        UiCapture.setReplayingMeshes(true);
        try {
            RenderSystem.getDevice().createCommandEncoder().clearDepthTexture(capture.getDepthTexture(), 0.0);
            original.call(renderer, label, capture, transforms, dynamicTransforms, vertexBuffer, indexType, start, end);
        }
        catch (RuntimeException failure) {
            UiCapture.abortFrame(failure);
        }
        finally {
            UiCapture.setReplayingMeshes(false);
        }
    }

    @Inject(method={"draw"}, at={@At(value="RETURN")})
    private void yueyue$finishCapture(GpuBufferSlice transforms, CallbackInfo ci) {
        UiCapture.finishFrame();
    }

    @Inject(method={"close"}, at={@At(value="HEAD")})
    private void yueyue$closeCapture(CallbackInfo ci) {
        UiCapture.close();
    }
}

