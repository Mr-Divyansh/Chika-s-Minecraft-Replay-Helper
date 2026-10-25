/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.keyframe;

import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import java.util.TreeMap;
import org.jetbrains.annotations.Nullable;

public interface KeyframeType<T extends Keyframe> {
    @Nullable
    default public String icon() {
        return null;
    }

    public String name();

    public String id();

    @Nullable
    public T createDirect();

    @Nullable
    public KeyframeCreatePopup<T> createPopup();

    @Nullable
    public Class<? extends KeyframeChange> keyframeChangeType();

    default public boolean supportsHandler(KeyframeHandler handler) {
        Class<KeyframeChange> clazz = this.keyframeChangeType();
        return clazz != null && handler.supportsKeyframeChange(clazz);
    }

    default public boolean allowChangingInterpolationType() {
        return true;
    }

    default public boolean allowChangingTimelineTick() {
        return true;
    }

    default public boolean neverApplyLastKeyframe() {
        return false;
    }

    default public boolean canBeCreatedNormally() {
        return true;
    }

    default public boolean allowApplyingDuplicateKeyframeChanges() {
        return false;
    }

    default public boolean cullKeyframesInTimelineToTheLeft() {
        return true;
    }

    default public boolean hasCustomKeyframeChangeCalculation() {
        return false;
    }

    default public KeyframeChange customKeyframeChange(TreeMap<Integer, Keyframe> keyframes, float tick) {
        throw new UnsupportedOperationException();
    }

    public static interface KeyframeCreatePopup<T extends Keyframe> {
        @Nullable
        public T render();
    }
}

