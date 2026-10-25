/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.chrono;

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.ByVal;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.chrono.SystemTime;
import org.bytedeco.javacpp.presets.chrono;

@Name(value={"std::chrono::system_clock"})
@Properties(inherit={chrono.class})
public class SystemClock
extends Pointer {
    @ByVal
    public static native SystemTime now();

    @Cast(value={"time_t"})
    public static native long to_time_t(@Const @ByRef SystemTime var0);

    @ByVal
    public static native SystemTime from_time_t(@Cast(value={"time_t"}) long var0);
}

