/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 */
package com.moulberry.flashback.visuals;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import com.moulberry.flashback.visuals.FastNoiseLite;
import com.moulberry.flashback.visuals.ReplayVisuals;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public class CameraRotation {
    private static final FastNoiseLite fastNoiseLite = new FastNoiseLite();
    private static float shakeTimeY = 0.0f;
    private static float shakeTimeX = 0.0f;
    private static float lastShakeReplayTick = 0.0f;

    public static Quaternionf modifyViewQuaternion(Quaternionf quaternionf) {
        ReplayServer replayServer;
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState == null) {
            return quaternionf;
        }
        if (ReplayUI.isMovingCamera()) {
            return quaternionf;
        }
        ReplayVisuals visuals = editorState.replayVisuals;
        quaternionf = new Quaternionf((Quaternionfc)quaternionf);
        if (visuals.overrideRoll) {
            quaternionf = quaternionf.rotateZ((float)Math.toRadians(visuals.overrideRollAmount));
        }
        if ((replayServer = Flashback.getReplayServer()) != null && visuals.overrideCameraShake && (Flashback.isExporting() || !replayServer.replayPaused)) {
            float tick = Flashback.isExporting() ? (float)Flashback.EXPORT_JOB.getCurrentTickDouble() : (float)replayServer.getPartialReplayTick();
            float speedFactor = 20.0f / replayServer.getDesiredTickRate(false);
            if (tick < lastShakeReplayTick) {
                lastShakeReplayTick = tick;
                shakeTimeY = 0.0f;
                shakeTimeX = 0.0f;
            } else if (tick > lastShakeReplayTick) {
                float yFrequency = visuals.cameraShakeYFrequency;
                float xFrequency = visuals.cameraShakeXFrequency;
                shakeTimeY += (tick - lastShakeReplayTick) * speedFactor * yFrequency * 2.0f;
                shakeTimeX += (tick - lastShakeReplayTick) * speedFactor * xFrequency * 2.0f;
                lastShakeReplayTick = tick;
            }
            float yAmplitude = visuals.cameraShakeYAmplitude;
            float xAmplitude = visuals.cameraShakeXAmplitude;
            float yRot = fastNoiseLite.GetNoise(shakeTimeX, -10000.0f) * 2.0f * (float)Math.PI;
            float xRot = fastNoiseLite.GetNoise(-10000.0f, shakeTimeY) * 2.0f * (float)Math.PI;
            quaternionf = quaternionf.rotateYXZ(yRot * xAmplitude / 360.0f, xRot * yAmplitude / 360.0f, 0.0f);
        }
        return quaternionf;
    }
}

