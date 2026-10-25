/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.keyframe.change;

import com.moulberry.flashback.Interpolation;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;

public record KeyframeChangeCameraShake(float frequencyX, float amplitudeX, float frequencyY, float amplitudeY) implements KeyframeChange
{
    @Override
    public void apply(KeyframeHandler keyframeHandler) {
        keyframeHandler.applyCameraShake(this.frequencyX, this.amplitudeX, this.frequencyY, this.amplitudeY);
    }

    @Override
    public KeyframeChange interpolate(KeyframeChange to, double amount) {
        KeyframeChangeCameraShake other = (KeyframeChangeCameraShake)to;
        return new KeyframeChangeCameraShake((float)Interpolation.linear((double)this.frequencyX, (double)other.frequencyX, amount), (float)Interpolation.linear((double)this.amplitudeX, (double)other.amplitudeX, amount), (float)Interpolation.linear((double)this.frequencyY, (double)other.frequencyY, amount), (float)Interpolation.linear((double)this.amplitudeY, (double)other.amplitudeY, amount));
    }
}

