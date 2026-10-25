/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.keyframe.change;

import com.moulberry.flashback.keyframe.handler.KeyframeHandler;

public interface KeyframeChange {
    public void apply(KeyframeHandler var1);

    public KeyframeChange interpolate(KeyframeChange var1, double var2);

    public static KeyframeChange interpolateSafe(KeyframeChange from, KeyframeChange to, double amount) {
        if (from == to) {
            return from;
        }
        if (from == null) {
            return to;
        }
        if (to == null) {
            return from;
        }
        if (from.getClass().isAssignableFrom(to.getClass())) {
            return from.interpolate(to, amount);
        }
        if (to.getClass().isAssignableFrom(from.getClass())) {
            return to.interpolate(from, 1.0 - amount);
        }
        if (amount < 0.5) {
            return from;
        }
        return to;
    }
}

