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

@Name(value={"std::chrono::steady_clock::duration"})
@Properties(inherit={chrono.class})
public class SteadyDuration
extends Pointer {
    public SteadyDuration() {
        this.allocate();
    }

    private native void allocate();

    public SteadyDuration(long r) {
        this.allocate(r);
    }

    private native void allocate(long var1);

    @Name(value={"operator="})
    @ByRef
    public native SteadyDuration put(@Const @ByRef SteadyDuration var1);

    @Name(value={"operator-"})
    @ByVal
    public native SteadyDuration negate();

    @Name(value={"operator++"})
    @ByRef
    public native SteadyDuration increment();

    @Name(value={"operator--"})
    @ByRef
    public native SteadyDuration decrement();

    @Name(value={"operator+="})
    @ByRef
    public native SteadyDuration addPut(@Const @ByRef SteadyDuration var1);

    @Name(value={"operator-="})
    @ByRef
    public native SteadyDuration subtractPut(@Const @ByRef SteadyDuration var1);

    @Name(value={"operator*="})
    @ByRef
    public native SteadyDuration multiplyPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native SteadyDuration modPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native SteadyDuration modPut(@Const @ByRef SteadyDuration var1);

    public native long count();

    @ByVal
    @Name(value={"zero"})
    public static native SteadyDuration zero_();

    @ByVal
    public static native SteadyDuration min();

    @ByVal
    public static native SteadyDuration max();
}

