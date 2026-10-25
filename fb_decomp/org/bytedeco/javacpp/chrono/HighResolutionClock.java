/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.chrono;

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByVal;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.chrono.HighResolutionTime;
import org.bytedeco.javacpp.presets.chrono;

@Name(value={"std::chrono::high_resolution_clock"})
@Properties(inherit={chrono.class})
public class HighResolutionClock
extends Pointer {
    @ByVal
    public static native HighResolutionTime now();

    @MemberGetter
    public static native boolean is_steady();
}

