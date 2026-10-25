/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.buffers.GpuBufferSlice
 *  com.mojang.blaze3d.platform.Window
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.MeshData
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.fog.FogRenderer$FogMode
 *  net.minecraft.client.renderer.rendertype.RenderTypes
 *  net.minecraft.client.renderer.state.level.CameraRenderState
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Matrix4fStack
 *  org.joml.Matrix4fc
 *  org.joml.Quaterniond
 *  org.joml.Quaterniondc
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector3d
 *  org.joml.Vector3dc
 */
package com.moulberry.flashback.visuals;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.moulberry.flashback.Utils;
import com.moulberry.flashback.combo_options.Sizing;
import com.moulberry.flashback.editor.ui.windows.TimelineWindow;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeCameraPosition;
import com.moulberry.flashback.keyframe.change.KeyframeChangeCameraPositionOrbit;
import com.moulberry.flashback.keyframe.change.KeyframeChangeFov;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.state.EditorScene;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import com.moulberry.flashback.state.KeyframeTrack;
import com.moulberry.flashback.visuals.FlashbackDrawBuffer;
import com.moulberry.flashback.visuals.Shapes;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4fStack;
import org.joml.Matrix4fc;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class CameraPath {
    private static FlashbackDrawBuffer cameraPathVertexBuffer = null;
    private static CameraPathArgs lastCameraPathArgs = null;
    private static Vector3d basePosition = null;
    private static int lastEditorStateModCount = 0;
    private static int lastCursorTick = 0;
    private static final PoseStack cameraPoseStack = new PoseStack();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void renderCameraPath(PoseStack poseStack, CameraRenderState camera, ReplayServer replayServer) {
        RenderSystem.assertOnRenderThread();
        if (Minecraft.getInstance().options.hideGui) {
            return;
        }
        EditorState state = replayServer.getEditorState();
        int replayTick = TimelineWindow.getCursorTick();
        if (lastEditorStateModCount != state.modCount || lastCursorTick != replayTick) {
            CameraPathArgs cameraPathArgs;
            long stamp = state.acquireRead();
            try {
                cameraPathArgs = CameraPath.createCameraPathArgs(state.getCurrentScene(stamp), replayTick);
            }
            finally {
                state.release(stamp);
            }
            if (lastEditorStateModCount != state.modCount || !cameraPathArgs.equals(lastCameraPathArgs)) {
                MeshData meshData;
                lastCameraPathArgs = cameraPathArgs;
                BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH);
                Vector3d basePosition = new Vector3d(camera.pos.x, camera.pos.y, camera.pos.z);
                CameraPath.buildCameraPath(state, basePosition.mul(-1.0, new Vector3d()), cameraPathArgs, bufferBuilder);
                if (cameraPathVertexBuffer != null) {
                    cameraPathVertexBuffer.close();
                    cameraPathVertexBuffer = null;
                }
                if ((meshData = bufferBuilder.build()) != null) {
                    CameraPath.basePosition = basePosition;
                    cameraPathVertexBuffer = new FlashbackDrawBuffer(2);
                    cameraPathVertexBuffer.upload(meshData);
                }
            }
            lastEditorStateModCount = state.modCount;
            lastCursorTick = replayTick;
        }
        if (cameraPathVertexBuffer == null) {
            return;
        }
        GpuBufferSlice oldFog = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog((GpuBufferSlice)Minecraft.getInstance().gameRenderer.fogRenderer.getBuffer(FogRenderer.FogMode.NONE));
        poseStack.pushPose();
        poseStack.translate(CameraPath.basePosition.x - camera.pos.x, CameraPath.basePosition.y - camera.pos.y + (double)1.62f, CameraPath.basePosition.z - camera.pos.z);
        Matrix4fStack stack = RenderSystem.getModelViewStack();
        stack.pushMatrix();
        stack.set((Matrix4fc)poseStack.last().pose());
        cameraPathVertexBuffer.draw();
        if (replayServer.replayPaused) {
            CapturingKeyframeHandler handler = new CapturingKeyframeHandler();
            FovCapturingKeyframeHandler fovHandler = new FovCapturingKeyframeHandler();
            fovHandler.fov = ((Integer)Minecraft.getInstance().options.fov().get()).intValue();
            state.applyKeyframes(handler, replayTick);
            state.applyKeyframes(fovHandler, replayTick);
            if (handler.position != null) {
                BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH);
                CameraPath.renderCamera(bufferBuilder, handler.position.sub((Vector3dc)basePosition, new Vector3d()), handler.angle, fovHandler.fov, CameraPath.getCameraColour(false, true), 1.0f);
                RenderTypes.LINES.draw(bufferBuilder.buildOrThrow());
            }
        }
        stack.popMatrix();
        poseStack.popPose();
        RenderSystem.setShaderFog((GpuBufferSlice)oldFog);
    }

    private static CameraPathArgs createCameraPathArgs(EditorScene scene, int replayTick) {
        int lastCameraTick = -1;
        int nextCameraTick = -1;
        for (int trackIndex = 0; trackIndex < scene.keyframeTracks.size(); ++trackIndex) {
            KeyframeTrack keyframeTrack = scene.keyframeTracks.get(trackIndex);
            if (!keyframeTrack.enabled || !CameraPath.isChangeCameraKeyframeType(keyframeTrack.keyframeType.keyframeChangeType()) || keyframeTrack.keyframesByTick.isEmpty()) continue;
            Map.Entry<Integer, Keyframe> lastEntry = keyframeTrack.keyframesByTick.floorEntry(replayTick);
            Map.Entry<Integer, Keyframe> nextEntry = keyframeTrack.keyframesByTick.ceilingEntry(replayTick + 1);
            if (lastEntry != null && (lastCameraTick == -1 || lastEntry.getKey() > lastCameraTick)) {
                lastCameraTick = lastEntry.getKey();
            }
            if (nextEntry != null && (nextCameraTick == -1 || nextEntry.getKey() < nextCameraTick)) {
                nextCameraTick = nextEntry.getKey();
            }
            if (lastEntry != null && nextEntry != null) break;
        }
        int lastLastCameraTick = -1;
        int nextNextCameraTick = -1;
        for (int trackIndex = 0; trackIndex < scene.keyframeTracks.size(); ++trackIndex) {
            Map.Entry<Integer, Keyframe> nextNextEntry;
            KeyframeTrack keyframeTrack = scene.keyframeTracks.get(trackIndex);
            if (!keyframeTrack.enabled || !CameraPath.isChangeCameraKeyframeType(keyframeTrack.keyframeType.keyframeChangeType()) || keyframeTrack.keyframesByTick.isEmpty()) continue;
            Map.Entry<Integer, Keyframe> lastLastEntry = lastCameraTick == -1 ? null : keyframeTrack.keyframesByTick.floorEntry(lastCameraTick - 1);
            Map.Entry<Integer, Keyframe> entry = nextNextEntry = nextCameraTick == -1 ? null : keyframeTrack.keyframesByTick.ceilingEntry(nextCameraTick + 1);
            if (lastLastEntry != null && lastLastCameraTick == -1) {
                lastLastCameraTick = lastLastEntry.getKey();
            }
            if (nextNextEntry == null || nextNextCameraTick != -1) continue;
            nextNextCameraTick = nextNextEntry.getKey();
        }
        return new CameraPathArgs(lastLastCameraTick, lastCameraTick, nextCameraTick, nextNextCameraTick);
    }

    private static void buildCameraPath(EditorState state, Vector3d offset, CameraPathArgs args, BufferBuilder bufferBuilder) {
        CapturingKeyframeHandler handler = new CapturingKeyframeHandler();
        FovCapturingKeyframeHandler fovHandler = new FovCapturingKeyframeHandler();
        float defaultFov = ((Integer)Minecraft.getInstance().options.fov().get()).intValue();
        if (args.lastCameraTick != -1) {
            fovHandler.fov = defaultFov;
            state.applyKeyframes(handler, args.lastCameraTick);
            state.applyKeyframes(fovHandler, args.lastCameraTick);
            CameraPath.renderCamera(bufferBuilder, handler.position.add((Vector3dc)offset, new Vector3d()), handler.angle, fovHandler.fov, CameraPath.getCameraColour(false, false), 1.0f);
            if (args.lastLastCameraTick != -1) {
                fovHandler.fov = defaultFov;
                state.applyKeyframes(handler, args.lastLastCameraTick);
                state.applyKeyframes(fovHandler, args.lastLastCameraTick);
                CameraPath.renderCamera(bufferBuilder, handler.position.add((Vector3dc)offset, new Vector3d()), handler.angle, fovHandler.fov, CameraPath.getCameraColour(false, false), 0.6f);
                CameraPath.renderPath(bufferBuilder, args.lastLastCameraTick, args.lastCameraTick, state, offset, handler, 0.6f);
            }
        }
        if (args.nextCameraTick != -1) {
            fovHandler.fov = defaultFov;
            state.applyKeyframes(handler, args.nextCameraTick);
            state.applyKeyframes(fovHandler, args.nextCameraTick);
            CameraPath.renderCamera(bufferBuilder, handler.position.add((Vector3dc)offset, new Vector3d()), handler.angle, fovHandler.fov, CameraPath.getCameraColour(false, false), 1.0f);
            if (args.nextNextCameraTick != -1) {
                fovHandler.fov = defaultFov;
                state.applyKeyframes(handler, args.nextNextCameraTick);
                state.applyKeyframes(fovHandler, args.nextNextCameraTick);
                CameraPath.renderCamera(bufferBuilder, handler.position.add((Vector3dc)offset, new Vector3d()), handler.angle, fovHandler.fov, CameraPath.getCameraColour(false, false), 0.6f);
                CameraPath.renderPath(bufferBuilder, args.nextCameraTick, args.nextNextCameraTick, state, offset, handler, 0.6f);
            }
        }
        if (args.lastCameraTick != -1 && args.nextCameraTick != -1) {
            CameraPath.renderPath(bufferBuilder, args.lastCameraTick, args.nextCameraTick, state, offset, handler, 1.0f);
        }
    }

    private static int getCameraColour(boolean selected, boolean current) {
        if (current) {
            return 0x20FF20;
        }
        if (selected) {
            return 0xFF2020;
        }
        return 0xFFFF20;
    }

    private static void renderPath(BufferBuilder bufferBuilder, int fromTick, int toTick, EditorState editorState, Vector3d offset, CapturingKeyframeHandler handler, float opacity) {
        Vector3d lastPosition = null;
        int step = (toTick - fromTick) / 2000 + 1;
        for (int tick = fromTick; tick <= toTick; tick += step) {
            editorState.applyKeyframes(handler, tick);
            Vector3d position = handler.position.add((Vector3dc)offset, new Vector3d());
            if (lastPosition != null) {
                double dx = position.x - lastPosition.x;
                double dy = position.y - lastPosition.y;
                double dz = position.z - lastPosition.z;
                float distanceInv = 1.0f / (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
                bufferBuilder.addVertex((float)lastPosition.x, (float)lastPosition.y, (float)lastPosition.z).setColor(1.0f, 1.0f, 0.1f, 0.0f).setNormal((float)(dx *= (double)distanceInv), (float)(dy *= (double)distanceInv), (float)(dz *= (double)distanceInv)).setLineWidth(2.0f);
                bufferBuilder.addVertex((float)position.x, (float)position.y, (float)position.z).setColor(1.0f, 1.0f, 0.1f, opacity).setNormal((float)dx, (float)dy, (float)dz).setLineWidth(2.0f);
            }
            lastPosition = position;
        }
    }

    private static void renderCamera(BufferBuilder bufferBuilder, Vector3d position, Quaterniond angle, float fov, int rgb, float opacity) {
        float aspectRatio;
        cameraPoseStack.pushPose();
        cameraPoseStack.translate(position.x, position.y, position.z);
        cameraPoseStack.mulPose((Quaternionfc)new Quaternionf((Quaterniondc)angle));
        PoseStack.Pose pose = cameraPoseStack.last();
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && editorState.replayVisuals.sizing == Sizing.CHANGE_ASPECT_RATIO) {
            aspectRatio = editorState.replayVisuals.changeAspectRatio.aspectRatio();
        } else {
            Window window = Minecraft.getInstance().getWindow();
            aspectRatio = (float)window.getWidth() / (float)window.getHeight();
        }
        float focalLength = Utils.fovToFocalLength(fov);
        float targetArea = (float)Math.sqrt(aspectRatio) * 0.3f;
        float d1 = (float)Math.sqrt(targetArea / aspectRatio * (focalLength * focalLength));
        float d2 = 0.5f;
        float d = (d1 + d2) / 2.0f;
        float h = d / focalLength;
        float w = h * aspectRatio;
        float red = (float)(rgb >> 16 & 0xFF) / 255.0f;
        float green = (float)(rgb >> 8 & 0xFF) / 255.0f;
        float blue = (float)(rgb & 0xFF) / 255.0f;
        Shapes.line(bufferBuilder, pose, Vec3.ZERO, new Vec3((double)w, (double)h, (double)d), red, green, blue, opacity);
        Shapes.line(bufferBuilder, pose, new Vec3((double)w, (double)h, (double)d), new Vec3((double)(-w), (double)h, (double)d), red, green, blue, opacity);
        Shapes.line(bufferBuilder, pose, Vec3.ZERO, new Vec3((double)(-w), (double)h, (double)d), red, green, blue, opacity);
        Shapes.line(bufferBuilder, pose, new Vec3((double)(-w), (double)h, (double)d), new Vec3((double)(-w), (double)(-h), (double)d), red, green, blue, opacity);
        Shapes.line(bufferBuilder, pose, Vec3.ZERO, new Vec3((double)(-w), (double)(-h), (double)d), red, green, blue, opacity);
        Shapes.line(bufferBuilder, pose, new Vec3((double)(-w), (double)(-h), (double)d), new Vec3((double)w, (double)(-h), (double)d), red, green, blue, opacity);
        Shapes.line(bufferBuilder, pose, Vec3.ZERO, new Vec3((double)w, (double)(-h), (double)d), red, green, blue, opacity);
        Shapes.line(bufferBuilder, pose, new Vec3((double)w, (double)(-h), (double)d), new Vec3((double)w, (double)h, (double)d), red, green, blue, opacity);
        cameraPoseStack.popPose();
    }

    private static boolean isChangeCameraKeyframeType(Class<? extends KeyframeChange> clazz) {
        return clazz == KeyframeChangeCameraPosition.class || clazz == KeyframeChangeCameraPositionOrbit.class;
    }

    private record CameraPathArgs(int lastLastCameraTick, int lastCameraTick, int nextCameraTick, int nextNextCameraTick) {
    }

    private static class CapturingKeyframeHandler
    implements KeyframeHandler {
        private Vector3d position;
        private Quaterniond angle;

        private CapturingKeyframeHandler() {
        }

        @Override
        public boolean supportsKeyframeChange(Class<? extends KeyframeChange> clazz) {
            return CameraPath.isChangeCameraKeyframeType(clazz);
        }

        @Override
        public boolean alwaysApplyLastKeyframe() {
            return true;
        }

        @Override
        public void applyCameraPosition(Vector3d position, double yaw, double pitch, double roll) {
            this.position = position;
            this.angle = new Quaterniond().rotationYXZ((double)((float)(-Math.toRadians(yaw))), (double)((float)Math.toRadians(pitch)), (double)((float)(-Math.toRadians(roll))));
        }
    }

    private static class FovCapturingKeyframeHandler
    implements KeyframeHandler {
        private float fov;

        private FovCapturingKeyframeHandler() {
        }

        @Override
        public boolean supportsKeyframeChange(Class<? extends KeyframeChange> clazz) {
            return clazz == KeyframeChangeFov.class;
        }

        @Override
        public boolean alwaysApplyLastKeyframe() {
            return true;
        }

        @Override
        public void applyFov(float fov) {
            this.fov = fov;
        }
    }
}

