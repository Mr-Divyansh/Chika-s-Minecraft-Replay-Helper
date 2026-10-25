/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Range
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.state;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.moulberry.flashback.editor.ui.ReplayUI;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeTickrate;
import com.moulberry.flashback.keyframe.impl.TimelapseKeyframe;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import com.moulberry.flashback.keyframe.interpolation.SidedInterpolationType;
import com.moulberry.flashback.keyframe.types.TimelapseKeyframeType;
import com.moulberry.flashback.state.RealTimeMapping;
import imgui.moulberry90.type.ImString;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import org.jetbrains.annotations.Nullable;

public class KeyframeTrack {
    public final KeyframeType<?> keyframeType;
    public TreeMap<Integer, Keyframe> keyframesByTick = new TreeMap();
    public boolean enabled = true;
    public String customName = null;
    public int customColour = 0;
    public transient ImString nameEditField = null;
    public transient boolean forceFocusTrack = false;
    public transient float animatedOffsetInUi = 0.0f;

    public KeyframeTrack(KeyframeType<?> keyframeType) {
        this.keyframeType = keyframeType;
    }

    @Nullable
    public KeyframeChange createKeyframeChange(float tick, @Nullable RealTimeMapping realTimeMapping) {
        if (this.keyframeType.hasCustomKeyframeChangeCalculation()) {
            return this.keyframeType.customKeyframeChange(this.keyframesByTick, tick);
        }
        if (this.keyframeType == TimelapseKeyframeType.INSTANCE) {
            return this.tryApplyKeyframesTimelapse(tick);
        }
        TreeMap<Integer, Keyframe> keyframeTimes = this.keyframesByTick;
        if (keyframeTimes.isEmpty()) {
            return null;
        }
        Map.Entry<Integer, Keyframe> lowerEntry = keyframeTimes.floorEntry((int)tick);
        if (lowerEntry == null) {
            return null;
        }
        Keyframe lowerKeyframe = lowerEntry.getValue();
        if (tick == (float)lowerEntry.getKey().intValue()) {
            return lowerKeyframe.createChange();
        }
        SidedInterpolationType leftInterpolation = lowerEntry.getValue().interpolationType().rightSide;
        if (leftInterpolation == SidedInterpolationType.HOLD) {
            return lowerKeyframe.createChange();
        }
        Map.Entry<Integer, Keyframe> ceilEntry = keyframeTimes.ceilingEntry(lowerEntry.getKey() + 1);
        if (ceilEntry == null) {
            if ((int)tick == lowerEntry.getKey()) {
                return lowerKeyframe.createChange();
            }
            return null;
        }
        SidedInterpolationType rightInterpolation = ceilEntry.getValue().interpolationType().leftSide;
        if (rightInterpolation == SidedInterpolationType.HOLD) {
            rightInterpolation = leftInterpolation;
        }
        float realTimeTick = realTimeMapping == null ? tick : realTimeMapping.getRealTime(tick);
        float realTimeLowerTick = realTimeMapping == null ? (float)lowerEntry.getKey().intValue() : realTimeMapping.getRealTime(lowerEntry.getKey().intValue());
        float realTimeCeilTick = realTimeMapping == null ? (float)ceilEntry.getKey().intValue() : realTimeMapping.getRealTime(ceilEntry.getKey().intValue());
        float amount = (realTimeTick - realTimeLowerTick) / (realTimeCeilTick - realTimeLowerTick);
        Object leftChange = null;
        Object rightChange = null;
        if (leftInterpolation == SidedInterpolationType.SMOOTH || rightInterpolation == SidedInterpolationType.SMOOTH) {
            Map.Entry<Integer, Keyframe> afterAfterEntry;
            Map.Entry<Integer, Keyframe> beforeEntry = keyframeTimes.floorEntry(lowerEntry.getKey() - 1);
            if (beforeEntry == null || beforeEntry.getValue().interpolationType() == InterpolationType.HOLD) {
                beforeEntry = lowerEntry;
            }
            if ((afterAfterEntry = keyframeTimes.ceilingEntry(ceilEntry.getKey() + 1)) == null || ceilEntry.getValue().interpolationType() == InterpolationType.HOLD) {
                afterAfterEntry = ceilEntry;
            }
            float realTimeBeforeTick = realTimeMapping == null ? (float)beforeEntry.getKey().intValue() : realTimeMapping.getRealTime(beforeEntry.getKey().intValue());
            float realTimeAfterTick = realTimeMapping == null ? (float)afterAfterEntry.getKey().intValue() : realTimeMapping.getRealTime(afterAfterEntry.getKey().intValue());
            KeyframeChange smoothChange = beforeEntry.getValue().createSmoothInterpolatedChange(lowerEntry.getValue(), ceilEntry.getValue(), afterAfterEntry.getValue(), realTimeBeforeTick, realTimeLowerTick, realTimeCeilTick, realTimeAfterTick, amount);
            if (leftInterpolation == SidedInterpolationType.SMOOTH) {
                leftChange = smoothChange;
            }
            if (rightInterpolation == SidedInterpolationType.SMOOTH) {
                rightChange = smoothChange;
            }
        }
        if (leftInterpolation == SidedInterpolationType.HERMITE || rightInterpolation == SidedInterpolationType.HERMITE) {
            Integer minKey = lowerEntry.getKey();
            while (minKey != null) {
                Map.Entry<Integer, Keyframe> before = keyframeTimes.floorEntry(minKey - 1);
                if (before == null) {
                    minKey = null;
                    break;
                }
                if (before.getValue().interpolationType() == InterpolationType.HOLD) break;
                minKey = before.getKey();
            }
            Integer maxKey = ceilEntry.getKey();
            while (maxKey != null) {
                Map.Entry<Integer, Keyframe> after = keyframeTimes.ceilingEntry(maxKey + 1);
                if (after == null) {
                    maxKey = null;
                    break;
                }
                if (after.getValue().interpolationType() == InterpolationType.HOLD) {
                    maxKey = after.getKey();
                    break;
                }
                maxKey = after.getKey();
            }
            NavigableMap<Object, Object> subMap = minKey != null ? (maxKey != null ? Maps.subMap(this.keyframesByTick, (Range)Range.closed((Comparable)minKey, (Comparable)maxKey)) : Maps.subMap(this.keyframesByTick, (Range)Range.atLeast((Comparable)minKey))) : (maxKey != null ? Maps.subMap(this.keyframesByTick, (Range)Range.atMost((Comparable)maxKey)) : this.keyframesByTick);
            HashMap<Float, Keyframe> transformedMap = new HashMap<Float, Keyframe>();
            for (Map.Entry entry : subMap.entrySet()) {
                float realtime = realTimeMapping == null ? (float)((Integer)entry.getKey()).intValue() : realTimeMapping.getRealTime(((Integer)entry.getKey()).intValue());
                transformedMap.put(Float.valueOf(realtime), (Keyframe)entry.getValue());
            }
            KeyframeChange hermiteChange = lowerKeyframe.createHermiteInterpolatedChange(transformedMap, realTimeTick);
            if (leftInterpolation == SidedInterpolationType.HERMITE) {
                leftChange = hermiteChange;
            }
            if (rightInterpolation == SidedInterpolationType.HERMITE) {
                rightChange = hermiteChange;
            }
        }
        if (leftChange == null || rightChange == null) {
            double adjustedAmount = SidedInterpolationType.interpolate(leftInterpolation, rightInterpolation, amount);
            KeyframeChange keyframeChange = lowerKeyframe.createChange();
            if (adjustedAmount != 0.0) {
                KeyframeChange keyframeChangeCeil = ceilEntry.getValue().createChange();
                keyframeChange = KeyframeChange.interpolateSafe(keyframeChange, keyframeChangeCeil, (float)adjustedAmount);
            }
            if (leftChange == null) {
                leftChange = keyframeChange;
            }
            if (rightChange == null) {
                rightChange = keyframeChange;
            }
        }
        return KeyframeChange.interpolateSafe(leftChange, rightChange, amount);
    }

    @Nullable
    private KeyframeChangeTickrate tryApplyKeyframesTimelapse(float tick) {
        TreeMap<Integer, Keyframe> keyframeTimes = this.keyframesByTick;
        if (keyframeTimes.isEmpty()) {
            return null;
        }
        Map.Entry<Integer, Keyframe> lowerEntry = keyframeTimes.floorEntry((int)tick);
        Map.Entry<Integer, Keyframe> ceilEntry = keyframeTimes.ceilingEntry((int)tick + 1);
        if (lowerEntry != null && ceilEntry != null) {
            int ceilTicks = ((TimelapseKeyframe)ceilEntry.getValue()).ticks;
            int lowerTicks = ((TimelapseKeyframe)lowerEntry.getValue()).ticks;
            if (ceilTicks <= lowerTicks) {
                ReplayUI.setInfoOverlayShort("Unable to timelapse. Right keyframe's time must be greater than left keyframe's time");
                return null;
            }
            double tickrate = (double)(ceilEntry.getKey() - lowerEntry.getKey()) / (double)(ceilTicks - lowerTicks) * 20.0;
            return new KeyframeChangeTickrate((float)tickrate);
        }
        return null;
    }
}

