/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.keyframe.change;

import com.moulberry.flashback.Interpolation;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;

public record KeyframeChangeTickrate(float tickrate) implements KeyframeChange
{
    @Override
    public void apply(KeyframeHandler keyframeHandler) {
        keyframeHandler.applyTickrate(this.tickrate);
    }

    @Override
    public KeyframeChange interpolate(KeyframeChange to, double amount) {
        KeyframeChangeTickrate other = (KeyframeChangeTickrate)to;
        return new KeyframeChangeTickrate((float)Interpolation.linear((double)this.tickrate, (double)other.tickrate, amount));
    }
}

