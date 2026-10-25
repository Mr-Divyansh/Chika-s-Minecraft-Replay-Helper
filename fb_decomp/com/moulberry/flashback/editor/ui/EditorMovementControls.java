/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.phys.Vec2
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Intersectionf
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 */
package com.moulberry.flashback.editor.ui;

import com.moulberry.flashback.editor.ui.ReplayUI;
import imgui.moulberry90.ImGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Intersectionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public abstract sealed class EditorMovementControls {
    public abstract boolean allowGameInputWhileCaptureKeyboard();

    public abstract boolean shouldStop(boolean var1);

    public abstract void update(double var1, double var3);

    public static EditorMovementControls none() {
        return None.INSTANCE;
    }

    public static EditorMovementControls rotate() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return None.INSTANCE;
        }
        return new Rotate(ReplayUI.getMouseForwardsVector(), new Vec2(Minecraft.getInstance().player.getXRot(), Minecraft.getInstance().player.getYRot()));
    }

    protected static final class None
    extends EditorMovementControls {
        private static final None INSTANCE = new None();

        protected None() {
        }

        @Override
        public boolean allowGameInputWhileCaptureKeyboard() {
            return false;
        }

        @Override
        public boolean shouldStop(boolean isGrabbed) {
            return false;
        }

        @Override
        public void update(double mouseDeltaX, double mouseDeltaY) {
        }
    }

    protected static final class Rotate
    extends EditorMovementControls {
        private final Vec3 originalForwards;
        private final Vec2 originalAngles;

        public Rotate(Vec3 originalForwards, Vec2 originalAngles) {
            this.originalForwards = originalForwards;
            this.originalAngles = originalAngles;
        }

        @Override
        public boolean allowGameInputWhileCaptureKeyboard() {
            return true;
        }

        @Override
        public boolean shouldStop(boolean isGrabbed) {
            return !ImGui.isMouseDown(0);
        }

        @Override
        public void update(double mouseDeltaX, double mouseDeltaY) {
            Vec3 forwards = ReplayUI.getMouseForwardsVector();
            LocalPlayer player = Minecraft.getInstance().player;
            if (forwards == null || player == null) {
                return;
            }
            float pitch = this.originalAngles.x;
            float yaw = this.originalAngles.y;
            Vec3 lastVector = this.originalForwards;
            for (int i = 1; i <= 10; ++i) {
                Vec3 thisVector = this.originalForwards.lerp(forwards, (double)((float)i / 10.0f));
                double differenceY = lastVector.y - thisVector.y;
                double pitchDelta = Math.toDegrees(Math.asin(differenceY / 2.0)) * 2.0;
                pitch = Mth.wrapDegrees((float)(pitch - (float)pitchDelta));
                pitch = Mth.clamp((float)pitch, (float)-90.0f, (float)90.0f);
                double yaw1 = Math.toDegrees(Math.atan2(lastVector.x, lastVector.z));
                double yaw2 = Math.toDegrees(Math.atan2(thisVector.x, thisVector.z));
                double yawDelta = Mth.wrapDegrees((double)(yaw1 - yaw2));
                Vector3f forwards3f = lastVector.lerp(thisVector, 0.5).toVector3f();
                forwards3f.rotateX((float)Math.toRadians(pitch));
                forwards3f.normalize();
                float radius = (float)Math.sqrt(1.0f - forwards3f.y() * forwards3f.y()) * 1.08f;
                if (radius < 0.2f) {
                    radius = 0.2f;
                }
                yaw = Mth.wrapDegrees((float)(yaw - (float)yawDelta / radius));
                lastVector = thisVector;
            }
            player.setXRot(pitch);
            player.setYRot(yaw);
        }
    }

    protected static final class Arcball
    extends EditorMovementControls {
        private final Vec3 point;
        private float distance;
        private boolean snapLook;

        public Arcball(Vec3 point, float distance, boolean snapLook) {
            this.point = point;
            this.distance = Math.max(1.0f, distance);
            this.snapLook = snapLook;
        }

        @Override
        public boolean allowGameInputWhileCaptureKeyboard() {
            return false;
        }

        @Override
        public boolean shouldStop(boolean isGrabbed) {
            return !isGrabbed;
        }

        @Override
        public void update(double mouseDeltaX, double mouseDeltaY) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }
            if (this.snapLook) {
                this.snapLook = false;
                Vec3 look = ReplayUI.getMouseLookVector();
                double horizontal = Math.sqrt(look.x * look.x + look.z * look.z);
                float newXRot = Mth.wrapDegrees((float)((float)Math.toDegrees(-Mth.atan2((double)look.y, (double)horizontal))));
                float newYRot = Mth.wrapDegrees((float)((float)Math.toDegrees(Mth.atan2((double)look.z, (double)look.x)) - 90.0f));
                player.setXRot(newXRot);
                player.setYRot(newYRot);
                return;
            }
            double baseSensitivity = (Double)Minecraft.getInstance().options.sensitivity().get() * (double)0.6f + (double)0.2f;
            double sensitivity = baseSensitivity * baseSensitivity * baseSensitivity * 8.0;
            int invert = (Boolean)Minecraft.getInstance().options.invertMouseY().get() != false ? -1 : 1;
            player.turn(mouseDeltaX * sensitivity, mouseDeltaY * sensitivity * (double)invert);
            if (ReplayUI.getIO().getMouseWheel() != 0.0f) {
                float wheel = Math.signum(ReplayUI.getIO().getMouseWheel());
                float move = this.distance * 0.05f;
                if (move > 4.0f) {
                    move = 4.0f + (float)Math.sqrt(move - 4.0f);
                }
                move *= wheel;
                if (ReplayUI.isMoveQuickDown()) {
                    move *= 2.0f;
                }
                this.distance = Math.max(1.0f, this.distance - move);
            }
            Vec3 position = this.point.subtract(player.getLookAngle().scale((double)this.distance));
            Minecraft.getInstance().player.setPos(position.subtract(0.0, (double)player.getEyeHeight(), 0.0));
            Minecraft.getInstance().player.setOldPosAndRot();
        }
    }

    protected static final class Pan
    extends EditorMovementControls {
        private final Vec3 originalPos;
        private final Vector3f planePoint;
        private final Vector3f planeNormal;

        public Pan(Vec3 originalPos, Vector3f planePoint, Vector3f planeNormal) {
            this.originalPos = originalPos;
            this.planePoint = planePoint;
            this.planeNormal = planeNormal;
        }

        @Override
        public boolean allowGameInputWhileCaptureKeyboard() {
            return false;
        }

        @Override
        public boolean shouldStop(boolean isGrabbed) {
            return !ImGui.isMouseDown(1);
        }

        @Override
        public void update(double mouseDeltaX, double mouseDeltaY) {
            Vector3f view;
            Vec3 forwards = ReplayUI.getMouseForwardsVector();
            LocalPlayer player = Minecraft.getInstance().player;
            if (forwards == null || player == null) {
                return;
            }
            Vector3f origin = this.originalPos.add(0.0, (double)player.getEyeHeight(), 0.0).toVector3f();
            float distance = Intersectionf.intersectRayPlane((Vector3fc)origin, (Vector3fc)(view = ReplayUI.getMouseLookVectorFromForwards(forwards).toVector3f()), (Vector3fc)this.planePoint, (Vector3fc)this.planeNormal, (float)1.0E-5f);
            if (distance < 0.0f) {
                return;
            }
            Vector3f targetPoint = origin.add((Vector3fc)new Vector3f((Vector3fc)view).mul(distance));
            Vector3f targetDelta = new Vector3f((Vector3fc)targetPoint).sub((Vector3fc)this.planePoint);
            Vec3 finalPos = this.originalPos.subtract((double)targetDelta.x, (double)targetDelta.y, (double)targetDelta.z);
            player.setPos(finalPos);
            player.setOldPosAndRot();
        }
    }
}

