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
import org.bytedeco.javacpp.chrono.SystemDuration;
import org.bytedeco.javacpp.presets.chrono;

@Name(value={"std::chrono::time_point<std::chrono::system_clock>"})
@Properties(inherit={chrono.class})
public class SystemTime
extends Pointer {
    public SystemTime() {
        this.allocate();
    }

    private native void allocate();

    public SystemTime(SystemDuration d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef SystemDuration var1);

    @ByVal
    public native SystemDuration time_since_epoch();

    @Name(value={"operator +="})
    @ByRef
    public native SystemTime addPut(@Const @ByRef SystemDuration var1);

    @Name(value={"operator -="})
    @ByRef
    public native SystemTime subtractPut(@Const @ByRef SystemDuration var1);

    @ByVal
    public static native SystemTime min();

    @ByVal
    public static native SystemTime max();
}

