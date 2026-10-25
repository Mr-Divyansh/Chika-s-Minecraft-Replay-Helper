/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Holder
 *  net.minecraft.world.clock.ClockNetworkState
 *  net.minecraft.world.clock.WorldClock
 */
package com.moulberry.flashback.ext;

import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.world.clock.ClockNetworkState;
import net.minecraft.world.clock.WorldClock;

public interface ClientClockManagerExt {
    public Map<Holder<WorldClock>, ClockNetworkState> flashback$encodeClockUpdates();
}

