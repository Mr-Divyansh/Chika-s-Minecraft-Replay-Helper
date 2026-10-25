/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.v2.WrapWithCondition
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.mojang.blaze3d.buffers.GpuBufferSlice
 *  com.mojang.blaze3d.pipeline.RenderTarget
 *  com.mojang.blaze3d.resource.GraphicsResourceAllocator
 *  com.mojang.blaze3d.systems.RenderPass
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.textures.FilterMode
 *  com.mojang.blaze3d.textures.GpuSampler
 *  com.mojang.blaze3d.textures.GpuTexture
 *  com.mojang.blaze3d.textures.GpuTextureView
 *  com.mojang.blaze3d.textures.TextureFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.client.Camera
 *  net.minecraft.client.CloudStatus
 *  net.minecraft.client.DeltaTracker
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.particle.ParticleEngine
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.renderer.LevelTargetBundle
 *  net.minecraft.client.renderer.SectionOcclusionGraph
 *  net.minecraft.client.renderer.SubmitNodeCollector
 *  net.minecraft.client.renderer.chunk.ChunkSectionLayerGroup
 *  net.minecraft.client.renderer.chunk.ChunkSectionsToRender
 *  net.minecraft.client.renderer.chunk.SectionRenderDispatcher
 *  net.minecraft.client.renderer.chunk.SectionRenderDispatcher$RenderSection
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.entity.EntityRenderDispatcher
 *  net.minecraft.client.renderer.entity.state.AvatarRenderState
 *  net.minecraft.client.renderer.entity.state.EntityRenderState
 *  net.minecraft.client.renderer.state.OptionsRenderState
 *  net.minecraft.client.renderer.state.level.CameraRenderState
 *  net.minecraft.client.renderer.state.level.LevelRenderState
 *  net.minecraft.client.renderer.state.level.ParticlesRenderState
 *  net.minecraft.world.TickRateManager
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector4f
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.moulberry.flashback.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.combo_options.ExportProjection;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.exporting.ExportJob;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import com.moulberry.flashback.visuals.ReplayVisuals;
import com.moulberry.flashback.visuals.ShaderManager;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.OptionalInt;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.SectionOcclusionGraph;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.chunk.ChunkSectionLayerGroup;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.OptionsRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.client.renderer.state.level.ParticlesRenderState;
import net.minecraft.world.TickRateManager;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LevelRenderer.class}, priority=1100)
public abstract class MixinLevelRenderer {
    @Shadow
    @Final
    public ObjectArrayList<SectionRenderDispatcher.RenderSection> visibleSections;
    @Shadow
    @Nullable
    private SectionRenderDispatcher sectionRenderDispatcher;
    @Shadow
    @Final
    public SectionOcclusionGraph sectionOcclusionGraph;
    @Shadow
    private int ticks;
    @Shadow
    @Final
    private LevelTargetBundle targets;
    @Shadow
    @Final
    private LevelRenderState levelRenderState;
    @Unique
    private GpuTexture roundAlphaBuffer = null;
    @Unique
    private GpuTextureView roundAlphaBufferView = null;

    @Inject(method={"tick"}, at={@At(value="HEAD")})
    public void tick(CallbackInfo ci) {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer != null) {
            this.ticks = replayServer.getReplayTick();
        }
    }

    @WrapOperation(method={"tick"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/TickRateManager;runsNormally()Z")})
    public boolean tick_runsNormally(TickRateManager instance, Operation<Boolean> original) {
        if (Flashback.isInReplay()) {
            return false;
        }
        return (Boolean)original.call(new Object[]{instance});
    }

    @Inject(method={"renderLevel"}, at={@At(value="HEAD")})
    public void renderLevel(GraphicsResourceAllocator resourceAllocator, DeltaTracker deltaTracker, boolean renderOutline, CameraRenderState cameraState, Matrix4fc modelViewMatrix, GpuBufferSlice terrainFog, Vector4f fogColor, boolean shouldRenderSky, ChunkSectionsToRender chunkSectionsToRender, CallbackInfo ci) {
        ReplayUI.lastProjectionMatrix = new Matrix4f((Matrix4fc)cameraState.projectionMatrix);
        ReplayUI.lastViewQuaternion = new Quaternionf((Quaternionfc)cameraState.orientation);
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null) {
            ReplayVisuals visuals = editorState.replayVisuals;
            if (!visuals.renderSky) {
                if (Flashback.isExporting() && Flashback.EXPORT_JOB.getSettings().transparent()) {
                    fogColor.set(0.0f, 0.0f, 0.0f, 0.0f);
                } else {
                    float[] skyColour = visuals.skyColour;
                    fogColor.set(skyColour[0], skyColour[1], skyColour[2], 1.0f);
                }
            }
        }
    }

    @Inject(method={"renderLevel"}, at={@At(value="RETURN")})
    public void renderLevelRet(CallbackInfo ci) {
        ExportJob exportJob = Flashback.EXPORT_JOB;
        if (exportJob != null && exportJob.isRunning()) {
            exportJob.tryDepthDownload();
        }
    }

    @Inject(method={"close"}, at={@At(value="HEAD")})
    public void close(CallbackInfo ci) {
        if (this.roundAlphaBuffer != null) {
            this.roundAlphaBuffer.close();
            this.roundAlphaBuffer = null;
        }
        if (this.roundAlphaBufferView != null) {
            this.roundAlphaBufferView.close();
            this.roundAlphaBufferView = null;
        }
    }

    @Inject(method={"extractBlockDestroyAnimation"}, at={@At(value="HEAD")}, cancellable=true, require=0)
    public void renderBlockDestroyAnimation(CallbackInfo ci) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && !editorState.replayVisuals.renderBlocks) {
            ci.cancel();
        }
    }

    @WrapOperation(method={"lambda$addMainPass$0"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;renderGroup(Lnet/minecraft/client/renderer/chunk/ChunkSectionLayerGroup;Lcom/mojang/blaze3d/textures/GpuSampler;)V")})
    public void method_62214_renderChunkGroup(ChunkSectionsToRender instance, ChunkSectionLayerGroup chunkSectionLayerGroup, GpuSampler gpuSampler, Operation<Void> original) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && !editorState.replayVisuals.renderBlocks) {
            return;
        }
        original.call(new Object[]{instance, chunkSectionLayerGroup, gpuSampler});
        if (chunkSectionLayerGroup == ChunkSectionLayerGroup.OPAQUE && Flashback.isExporting() && Flashback.EXPORT_JOB.getSettings().transparent()) {
            RenderTarget main = Minecraft.getInstance().mainRenderTarget;
            if (this.roundAlphaBuffer == null) {
                this.roundAlphaBuffer = RenderSystem.getDevice().createTexture(() -> "flashback round alpha buffer", 8, TextureFormat.RGBA8, main.width, main.height, 1, 1);
                this.roundAlphaBufferView = RenderSystem.getDevice().createTextureView(this.roundAlphaBuffer);
            } else if (this.roundAlphaBuffer.getWidth(0) != main.width || this.roundAlphaBuffer.getHeight(0) != main.height) {
                this.roundAlphaBuffer.close();
                this.roundAlphaBufferView.close();
                this.roundAlphaBuffer = RenderSystem.getDevice().createTexture(() -> "flashback round alpha buffer", 8, TextureFormat.RGBA8, main.width, main.height, 1, 1);
                this.roundAlphaBufferView = RenderSystem.getDevice().createTextureView(this.roundAlphaBuffer);
            }
            try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "flashback round alpha render pass 1", this.roundAlphaBufferView, OptionalInt.empty());){
                renderPass.setPipeline(ShaderManager.BLIT_SCREEN);
                RenderSystem.bindDefaultUniforms((RenderPass)renderPass);
                renderPass.bindTexture("InSampler", main.getColorTextureView(), RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST));
                renderPass.draw(0, 3);
            }
            renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "flashback round alpha render pass 2", main.getColorTextureView(), OptionalInt.empty());
            try {
                renderPass.setPipeline(ShaderManager.BLIT_SCREEN_ROUND_ALPHA);
                RenderSystem.bindDefaultUniforms((RenderPass)renderPass);
                renderPass.bindTexture("InSampler", this.roundAlphaBufferView, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST));
                renderPass.draw(0, 3);
            }
            finally {
                if (renderPass != null) {
                    renderPass.close();
                }
            }
        }
    }

    @Inject(method={"submitBlockEntities"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderBlockEntities(CallbackInfo ci) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && !editorState.replayVisuals.renderBlocks) {
            ci.cancel();
        }
    }

    @WrapWithCondition(method={"submitEntities"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;submit(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/client/renderer/state/level/CameraRenderState;DDDLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V")})
    public boolean renderEntity(EntityRenderDispatcher instance, EntityRenderState entityRenderState, CameraRenderState cameraRenderState, double d, double e, double f, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (entityRenderState instanceof AvatarRenderState) {
            return editorState == null || editorState.replayVisuals.renderPlayers;
        }
        return editorState == null || editorState.replayVisuals.renderEntities;
    }

    @WrapOperation(method={"renderLevel"}, at={@At(value="FIELD", target="Lnet/minecraft/client/renderer/state/OptionsRenderState;cloudStatus:Lnet/minecraft/client/CloudStatus;", opcode=180)}, require=0)
    public CloudStatus renderLevel_getCloudsType(OptionsRenderState instance, Operation<CloudStatus> original) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && !editorState.replayVisuals.renderSky) {
            return CloudStatus.OFF;
        }
        return (CloudStatus)original.call(new Object[]{instance});
    }

    @WrapWithCondition(method={"extractLevel"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/particle/ParticleEngine;extract(Lnet/minecraft/client/renderer/state/level/ParticlesRenderState;Lnet/minecraft/client/renderer/culling/Frustum;Lnet/minecraft/client/Camera;F)V")})
    public boolean extractLevel_particleEngine_extract(ParticleEngine instance, ParticlesRenderState renderState, Frustum frustum, Camera camera, float partialTickTime) {
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && !editorState.replayVisuals.renderParticles) {
            renderState.reset();
            return false;
        }
        return true;
    }

    @Inject(method={"addSkyPass"}, at={@At(value="HEAD")}, cancellable=true)
    public void addSkyPass(CallbackInfo ci) {
        ExportJob exportJob;
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && !editorState.replayVisuals.renderSky) {
            ci.cancel();
        }
        if ((exportJob = Flashback.EXPORT_JOB) != null && exportJob.getSettings().projection() == ExportProjection.ORTHOGRAPHIC) {
            ci.cancel();
        }
    }

    @Inject(method={"addCloudsPass"}, at={@At(value="HEAD")}, cancellable=true)
    public void addCloudsPass(CallbackInfo ci) {
        ExportJob exportJob = Flashback.EXPORT_JOB;
        if (exportJob != null && exportJob.getSettings().projection() == ExportProjection.ORTHOGRAPHIC) {
            ci.cancel();
        }
    }
}

