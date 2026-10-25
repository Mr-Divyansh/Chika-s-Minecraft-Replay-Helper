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
import org.bytedeco.javacpp.presets.chrono;

@Name(value={"std::chrono::high_resolution_clock::duration"})
@Properties(inherit={chrono.class})
public class HighResolutionDuration
extends Pointer {
    public HighResolutionDuration() {
        this.allocate();
    }

    private native void allocate();

    public HighResolutionDuration(long r) {
        this.allocate(r);
    }

    private native void allocate(long var1);

    @Name(value={"operator="})
    @ByRef
    public native HighResolutionDuration put(@Const @ByRef HighResolutionDuration var1);

    @Name(value={"operator-"})
    @ByVal
    public native HighResolutionDuration negate();

    @Name(value={"operator++"})
    @ByRef
    public native HighResolutionDuration increment();

    @Name(value={"operator--"})
    @ByRef
    public native HighResolutionDuration decrement();

    @Name(value={"operator+="})
    @ByRef
    public native HighResolutionDuration addPut(@Const @ByRef HighResolutionDuration var1);

    @Name(value={"operator-="})
    @ByRef
    public native HighResolutionDuration subtractPut(@Const @ByRef HighResolutionDuration var1);

    @Name(value={"operator*="})
    @ByRef
    public native HighResolutionDuration multiplyPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native HighResolutionDuration modPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native HighResolutionDuration modPut(@Const @ByRef HighResolutionDuration var1);

    public native long count();

    @ByVal
    @Name(value={"zero"})
    public static native HighResolutionDuration zero_();

    @ByVal
    public static native HighResolutionDuration min();

    @ByVal
    public static native HighResolutionDuration max();
}

