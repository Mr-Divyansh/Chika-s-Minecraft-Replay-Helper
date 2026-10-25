/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.mojang.blaze3d.pipeline.RenderTarget
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.renderpearl.api.buffers.GpuBufferSlice
 *  com.mojang.renderpearl.api.commands.RenderPass
 *  com.mojang.renderpearl.api.pipeline.CompiledRenderPipeline
 *  com.mojang.renderpearl.api.pipeline.RenderPipeline
 *  net.minecraft.client.gui.render.GuiRenderer
 *  net.minecraft.client.renderer.RenderPipelines
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package yueyue.flashback.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.pipeline.CompiledRenderPipeline;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import java.util.function.Supplier;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yueyue.flashback.capture.UiCapture;

@Mixin(value={GuiRenderer.class})
public abstract class GuiCaptureMixin {
    @Unique
    private boolean yueyue$skipCaptureDraw;

    @Inject(method={"draw"}, at={@At(value="HEAD")})
    private void yueyue$beginCapture(CallbackInfo ci) {
        UiCapture.beginFrame();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @WrapOperation(method={"draw"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/render/GuiRenderer;executeDrawRange(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/pipeline/RenderTarget;Lcom/mojang/renderpearl/api/buffers/GpuBufferSlice;II)V")})
    private void yueyue$copyDrawRange(GuiRenderer renderer, Supplier<String> label, RenderTarget target, GpuBufferSlice transforms, int start, int end, Operation<Void> original) {
        original.call(new Object[]{renderer, label, target, transforms, start, end});
        RenderTarget capture = UiCapture.target();
        if (capture == null) {
            return;
        }
        UiCapture.setReplayingMeshes(true);
        try {
            RenderSystem.getDevice().createCommandEncoder().clearDepthTexture(capture.getDepthTexture(), 0.0);
            original.call(new Object[]{renderer, label, capture, transforms, start, end});
        }
        catch (RuntimeException failure) {
            UiCapture.abortFrame(failure);
        }
        finally {
            UiCapture.setReplayingMeshes(false);
        }
    }

    @Inject(method={"draw"}, at={@At(value="RETURN")})
    private void yueyue$finishCapture(CallbackInfo ci) {
        UiCapture.finishFrame();
    }

    @WrapOperation(method={"executeDraw"}, at={@At(value="INVOKE", target="Lcom/mojang/blaze3d/systems/RenderSystem;getCompiledPipeline(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;)Lcom/mojang/renderpearl/api/pipeline/CompiledRenderPipeline;")})
    private CompiledRenderPipeline yueyue$trackCapturePipeline(RenderPipeline pipeline, Operation<CompiledRenderPipeline> original) {
        this.yueyue$skipCaptureDraw = UiCapture.isReplayingMeshes() && pipeline == RenderPipelines.CROSSHAIR;
        return (CompiledRenderPipeline)original.call(new Object[]{pipeline});
    }

    @WrapOperation(method={"executeDraw"}, at={@At(value="INVOKE", target="Lcom/mojang/renderpearl/api/commands/RenderPass;drawIndexed(IIIII)V")})
    private void yueyue$omitWorldDependentCrosshair(RenderPass pass, int count, int instances, int firstIndex, int baseVertex, int firstInstance, Operation<Void> original) {
        if (!this.yueyue$skipCaptureDraw) {
            original.call(new Object[]{pass, count, instances, firstIndex, baseVertex, firstInstance});
        }
    }

    @Inject(method={"close"}, at={@At(value="HEAD")})
    private void yueyue$closeCapture(CallbackInfo ci) {
        UiCapture.close();
    }
}

