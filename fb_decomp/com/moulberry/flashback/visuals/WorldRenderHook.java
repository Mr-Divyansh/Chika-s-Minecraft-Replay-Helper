/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.pipeline.RenderPipeline
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.Font$DisplayMode
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.RenderPipelines
 *  net.minecraft.client.renderer.rendertype.RenderSetup
 *  net.minecraft.client.renderer.rendertype.RenderType
 *  net.minecraft.client.renderer.state.level.CameraRenderState
 *  net.minecraft.resources.Identifier
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Quaternionfc
 */
package com.moulberry.flashback.visuals;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.record.FlashbackMeta;
import com.moulberry.flashback.record.ReplayMarker;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import com.moulberry.flashback.visuals.CameraPath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionfc;

public class WorldRenderHook {
    private static final RenderType MARKER_CIRCLE_RENDER_TYPE = RenderType.create((String)"flashback:marker_circle", (RenderSetup)RenderSetup.builder((RenderPipeline)RenderPipelines.GUI_TEXTURED).withTexture("Sampler0", Identifier.parse((String)"flashback:world_marker_circle.png")).createRenderSetup());

    public static void renderHook(PoseStack poseStack, CameraRenderState camera) {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer == null || Flashback.isExporting() || !ReplayUI.isActive()) {
            return;
        }
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && editorState.replayVisuals.cameraPath) {
            CameraPath.renderCameraPath(poseStack, camera, replayServer);
        }
        FlashbackMeta meta = replayServer.getMetadata();
        if (!meta.replayMarkers.isEmpty()) {
            MultiBufferSource.BufferSource multiBufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
            multiBufferSource.endBatch();
            VertexConsumer bufferBuilder = null;
            String dimension = Minecraft.getInstance().level.dimension().toString();
            for (ReplayMarker marker : meta.replayMarkers.values()) {
                ReplayMarker.MarkerPosition position;
                if (marker.position() == null || !(position = marker.position()).dimension().equals(dimension)) continue;
                poseStack.pushPose();
                poseStack.translate((double)position.position().x - camera.pos.x, (double)position.position().y - camera.pos.y, (double)position.position().z - camera.pos.z);
                poseStack.mulPose((Quaternionfc)camera.orientation);
                if (bufferBuilder == null) {
                    bufferBuilder = multiBufferSource.getBuffer(MARKER_CIRCLE_RENDER_TYPE);
                }
                float width = 0.2f;
                bufferBuilder.addVertex(poseStack.last(), -0.2f, -0.2f, 0.0f).setUv(0.0f, 0.0f).setColor(marker.colour() | 0xFF000000);
                bufferBuilder.addVertex(poseStack.last(), 0.2f, -0.2f, 0.0f).setUv(1.0f, 0.0f).setColor(marker.colour() | 0xFF000000);
                bufferBuilder.addVertex(poseStack.last(), 0.2f, 0.2f, 0.0f).setUv(1.0f, 1.0f).setColor(marker.colour() | 0xFF000000);
                bufferBuilder.addVertex(poseStack.last(), -0.2f, 0.2f, 0.0f).setUv(0.0f, 1.0f).setColor(marker.colour() | 0xFF000000);
                if (marker.description() != null) {
                    Font font = Minecraft.getInstance().font;
                    Matrix4f matrix4f = poseStack.last().pose();
                    matrix4f.rotate((float)Math.PI, 0.0f, 1.0f, 0.0f);
                    matrix4f.scale(-0.025f, -0.025f, -0.025f);
                    int descriptionWidth = font.width(marker.description());
                    font.drawInBatch(marker.description(), (float)(-descriptionWidth) / 2.0f, -20.0f, -1, true, (Matrix4fc)matrix4f, (MultiBufferSource)multiBufferSource, Font.DisplayMode.NORMAL, 0, 0xF000F0);
                    bufferBuilder = null;
                }
                poseStack.popPose();
            }
            multiBufferSource.endBatch();
        }
    }
}

