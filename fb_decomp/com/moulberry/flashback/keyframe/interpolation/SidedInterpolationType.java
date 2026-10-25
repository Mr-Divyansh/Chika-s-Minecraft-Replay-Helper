/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.keyframe.interpolation;

public enum SidedInterpolationType {
    SMOOTH,
    LINEAR,
    EASE,
    HOLD,
    HERMITE;


    private boolean isSpecial() {
        return this == SMOOTH || this == HERMITE;
    }

    public static float interpolate(SidedInterpolationType left, SidedInterpolationType right, float amount) {
        if (left.isSpecial()) {
            if (right.isSpecial()) {
                left = LINEAR;
                right = LINEAR;
            } else {
                left = right;
            }
        } else if (right.isSpecial()) {
            right = left;
        }
        if (left == HOLD) {
            return 0.0f;
        }
        if (right == HOLD) {
            right = left;
        }
        if (left == LINEAR) {
            if (right == LINEAR) {
                return amount;
            }
            if (right == EASE) {
                return 1.0f - (float)Math.pow(1.0f - amount, 3.0);
            }
        } else if (left == EASE) {
            if (right == LINEAR) {
                return (float)Math.pow(amount, 3.0);
            }
            if (right == EASE) {
                if ((double)amount < 0.5) {
                    return 4.0f * amount * amount * amount;
                }
                return 1.0f - (float)Math.pow(-2.0f * amount + 2.0f, 3.0) / 2.0f;
            }
        }
        throw new IllegalArgumentException("Don't know how to interpolate " + String.valueOf((Object)left) + " and " + String.valueOf((Object)right));
    }
}

