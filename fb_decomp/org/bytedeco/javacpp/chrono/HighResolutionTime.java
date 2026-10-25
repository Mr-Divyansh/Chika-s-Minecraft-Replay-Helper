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
import org.bytedeco.javacpp.chrono.HighResolutionDuration;
import org.bytedeco.javacpp.presets.chrono;

@Name(value={"std::chrono::time_point<std::chrono::high_resolution_clock>"})
@Properties(inherit={chrono.class})
public class HighResolutionTime
extends Pointer {
    public HighResolutionTime() {
        this.allocate();
    }

    private native void allocate();

    public HighResolutionTime(HighResolutionDuration d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef HighResolutionDuration var1);

    @ByVal
    public native HighResolutionTime time_since_epoch();

    @Name(value={"operator +="})
    @ByRef
    public native HighResolutionTime addPut(@Const @ByRef HighResolutionDuration var1);

    @Name(value={"operator -="})
    @ByRef
    public native HighResolutionTime subtractPut(@Const @ByRef HighResolutionDuration var1);

    @ByVal
    public static native HighResolutionTime min();

    @ByVal
    public static native HighResolutionTime max();
}

