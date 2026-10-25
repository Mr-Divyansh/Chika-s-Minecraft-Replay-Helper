/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.state;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class RealTimeMapping {
    private final NavigableMap<Integer, SpeedChange> map = new TreeMap<Integer, SpeedChange>();

    public void addMapping(int tick, float speed) {
        if (this.map.ceilingKey(tick) != null) {
            throw new IllegalStateException("Must call addMapping with tick that is greater than the last");
        }
        float realTimeUntilThisPoint = this.getRealTime(tick);
        this.map.put(tick, new SpeedChange(speed, realTimeUntilThisPoint));
    }

    public float getRealTime(float tick) {
        if (tick <= 0.0f || this.map.isEmpty()) {
            return tick;
        }
        Map.Entry<Integer, SpeedChange> entry = this.map.floorEntry((int)tick);
        if (entry == null) {
            return tick;
        }
        SpeedChange change = entry.getValue();
        return change.realTimeUntilThisPoint + (tick - (float)entry.getKey().intValue()) / change.speedFactor;
    }

    private record SpeedChange(float speedFactor, float realTimeUntilThisPoint) {
    }
}

