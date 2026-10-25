/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.chrono;

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.ByVal;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.chrono.SteadyDuration;
import org.bytedeco.javacpp.presets.chrono;

@Name(value={"std::chrono::time_point<std::chrono::steady_clock>"})
@Properties(inherit={chrono.class})
public class SteadyTime
extends Pointer {
    public SteadyTime() {
        this.allocate();
    }

    private native void allocate();

    public SteadyTime(SteadyDuration d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef SteadyDuration var1);

    @ByVal
    public native SteadyDuration time_since_epoch();

    @Name(value={"operator +="})
    @ByRef
    public native SteadyTime addPut(@Const @ByRef SteadyDuration var1);

    @Name(value={"operator -="})
    @ByRef
    public native SteadyTime subtractPut(@Const @ByRef SteadyDuration var1);

    @ByVal
    public static native SteadyTime min();

    @ByVal
    public static native SteadyTime max();
}

