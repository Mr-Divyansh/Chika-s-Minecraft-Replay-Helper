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

@Name(value={"std::chrono::system_clock::duration"})
@Properties(inherit={chrono.class})
public class SystemDuration
extends Pointer {
    public SystemDuration() {
        this.allocate();
    }

    private native void allocate();

    public SystemDuration(long r) {
        this.allocate(r);
    }

    private native void allocate(long var1);

    @Name(value={"operator="})
    @ByRef
    public native SystemDuration put(@Const @ByRef SystemDuration var1);

    @Name(value={"operator-"})
    @ByVal
    public native SystemDuration negate();

    @Name(value={"operator++"})
    @ByRef
    public native SystemDuration increment();

    @Name(value={"operator--"})
    @ByRef
    public native SystemDuration decrement();

    @Name(value={"operator+="})
    @ByRef
    public native SystemDuration addPut(@Const @ByRef SystemDuration var1);

    @Name(value={"operator-="})
    @ByRef
    public native SystemDuration subtractPut(@Const @ByRef SystemDuration var1);

    @Name(value={"operator*="})
    @ByRef
    public native SystemDuration multiplyPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native SystemDuration modPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native SystemDuration modPut(@Const @ByRef SystemDuration var1);

    public native long count();

    @ByVal
    @Name(value={"zero"})
    public static native SystemDuration zero_();

    @ByVal
    public static native SystemDuration min();

    @ByVal
    public static native SystemDuration max();
}

