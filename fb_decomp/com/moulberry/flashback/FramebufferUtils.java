/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.ProjectionType
 *  com.mojang.blaze3d.buffers.GpuBuffer
 *  com.mojang.blaze3d.buffers.GpuBufferSlice
 *  com.mojang.blaze3d.opengl.GlStateManager
 *  com.mojang.blaze3d.pipeline.RenderTarget
 *  com.mojang.blaze3d.pipeline.TextureTarget
 *  com.mojang.blaze3d.systems.RenderPass
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.systems.RenderSystem$AutoStorageIndexBuffer
 *  com.mojang.blaze3d.textures.FilterMode
 *  com.mojang.blaze3d.textures.GpuTexture
 *  com.mojang.blaze3d.textures.GpuTextureView
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.MeshData
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.renderer.Projection
 *  net.minecraft.client.renderer.ProjectionMatrixBuffer
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fStack
 *  org.joml.Matrix4fc
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 *  org.joml.Vector4f
 *  org.joml.Vector4fc
 *  org.lwjgl.opengl.GL11
 */
package com.moulberry.flashback;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.moulberry.flashback.visuals.ShaderManager;
import java.util.OptionalInt;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.lwjgl.opengl.GL11;

public class FramebufferUtils {
    private static ProjectionMatrixBuffer projectionBuffers;
    private static Projection projection;
    private static RenderTarget tempRenderTarget;

    public static void clear(RenderTarget renderTarget, int colour) {
        int oldReadFbo = GL11.glGetInteger((int)36010);
        int oldDrawFbo = GL11.glGetInteger((int)36006);
        GpuTexture colourTexture = renderTarget.getColorTexture();
        GpuTexture depthTexture = renderTarget.getDepthTexture();
        if (colourTexture != null && !colourTexture.isClosed() && depthTexture != null && !depthTexture.isClosed()) {
            RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(colourTexture, colour, depthTexture, 1.0);
        } else if (colourTexture != null && !colourTexture.isClosed()) {
            RenderSystem.getDevice().createCommandEncoder().clearColorTexture(colourTexture, colour);
        } else if (depthTexture != null && !depthTexture.isClosed()) {
            RenderSystem.getDevice().createCommandEncoder().clearDepthTexture(depthTexture, 1.0);
        }
        GlStateManager._glBindFramebuffer((int)36008, (int)oldReadFbo);
        GlStateManager._glBindFramebuffer((int)36009, (int)oldDrawFbo);
    }

    public static RenderTarget resizeOrCreateFramebuffer(RenderTarget renderTarget, int width, int height, boolean useDepth) {
        if (renderTarget == null) {
            renderTarget = new TextureTarget(null, width, height, useDepth);
        } else if (renderTarget.width != width || renderTarget.height != height) {
            renderTarget.resize(width, height);
        }
        return renderTarget;
    }

    public static void blitTo(GpuTextureView from, RenderTarget to, int width, int height, float x1, float y1, float x2, float y2) {
        if (projectionBuffers == null) {
            projectionBuffers = new ProjectionMatrixBuffer("flashback blit");
            projection = new Projection();
            projection.setupOrtho(1000.0f, 3000.0f, (float)width, (float)height, true);
        } else if (projection.width() != (float)width || projection.height() != (float)height) {
            projection.setSize((float)width, (float)height);
        }
        Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();
        modelViewStack.set((Matrix4fc)new Matrix4f().translation(0.0f, 0.0f, -2000.0f));
        GpuBufferSlice oldProjectionMatrix = RenderSystem.getProjectionMatrixBuffer();
        ProjectionType oldProjectionType = RenderSystem.getProjectionType();
        RenderSystem.setProjectionMatrix((GpuBufferSlice)projectionBuffers.getBuffer(projection), (ProjectionType)ProjectionType.ORTHOGRAPHIC);
        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        builder.addVertex((float)width * x1, (float)height * y2, 0.0f).setUv(0.0f, 0.0f);
        builder.addVertex((float)width * x2, (float)height * y2, 0.0f).setUv(1.0f, 0.0f);
        builder.addVertex((float)width * x2, (float)height * y1, 0.0f).setUv(1.0f, 1.0f);
        builder.addVertex((float)width * x1, (float)height * y1, 0.0f).setUv(0.0f, 1.0f);
        try (MeshData meshData = builder.buildOrThrow();){
            RenderSystem.AutoStorageIndexBuffer autoStorageIndexBuffer = RenderSystem.getSequentialBuffer((VertexFormat.Mode)VertexFormat.Mode.QUADS);
            GpuBuffer gpuBuffer = autoStorageIndexBuffer.getBuffer(6);
            GpuBuffer vertexBuffer = DefaultVertexFormat.POSITION_TEX.uploadImmediateVertexBuffer(meshData.vertexBuffer());
            GpuBufferSlice gpuBufferSlice = RenderSystem.getDynamicUniforms().writeTransform((Matrix4fc)RenderSystem.getModelViewMatrix(), (Vector4fc)new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), (Vector3fc)new Vector3f(), (Matrix4fc)new Matrix4f());
            try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "flashback blit", to.getColorTextureView(), OptionalInt.empty());){
                renderPass.setPipeline(ShaderManager.BLIT_SCREEN_WITH_UV);
                RenderSystem.bindDefaultUniforms((RenderPass)renderPass);
                renderPass.setUniform("DynamicTransforms", gpuBufferSlice);
                renderPass.setVertexBuffer(0, vertexBuffer);
                renderPass.setIndexBuffer(gpuBuffer, autoStorageIndexBuffer.type());
                renderPass.bindTexture("InSampler", from, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST));
                renderPass.drawIndexed(0, 0, 6, 1);
            }
        }
        RenderSystem.setProjectionMatrix((GpuBufferSlice)oldProjectionMatrix, (ProjectionType)oldProjectionType);
        modelViewStack.popMatrix();
    }

    public static void blitToScreenPartial(RenderTarget renderTarget, int width, int height, float x1, float y1, float x2, float y2) {
        GlStateManager._viewport((int)0, (int)0, (int)width, (int)height);
        int oldReadFbo = GL11.glGetInteger((int)36010);
        int oldDrawFbo = GL11.glGetInteger((int)36006);
        tempRenderTarget = FramebufferUtils.resizeOrCreateFramebuffer(tempRenderTarget, width, height, true);
        FramebufferUtils.clear(tempRenderTarget, 0);
        FramebufferUtils.blitTo(renderTarget.getColorTextureView(), tempRenderTarget, width, height, x1, y1, x2, y2);
        tempRenderTarget.blitToScreen();
        GlStateManager._glBindFramebuffer((int)36008, (int)oldReadFbo);
        GlStateManager._glBindFramebuffer((int)36009, (int)oldDrawFbo);
    }

    static {
        tempRenderTarget = null;
    }
}

