/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.chrono;

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByVal;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.chrono.SteadyTime;
import org.bytedeco.javacpp.presets.chrono;

@Name(value={"std::chrono::steady_clock"})
@Properties(inherit={chrono.class})
public class SteadyClock
extends Pointer {
    @ByVal
    public static native SteadyTime now();
}

