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
import org.bytedeco.javacpp.chrono.Nanoseconds;
import org.bytedeco.javacpp.chrono.Seconds;
import org.bytedeco.javacpp.presets.chrono;

@Name(value={"std::chrono::duration<double>"})
@Properties(inherit={chrono.class})
public class SecondsDouble
extends Pointer {
    public SecondsDouble() {
        this.allocate();
    }

    private native void allocate();

    public SecondsDouble(double r) {
        this.allocate(r);
    }

    private native void allocate(double var1);

    public SecondsDouble(Nanoseconds d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Nanoseconds var1);

    public SecondsDouble(Seconds d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Seconds var1);

    @Name(value={"operator="})
    @ByRef
    public native SecondsDouble put(@Const @ByRef SecondsDouble var1);

    @Name(value={"operator-"})
    @ByVal
    public native SecondsDouble negate();

    @Name(value={"operator++"})
    @ByRef
    public native SecondsDouble increment();

    @Name(value={"operator--"})
    @ByRef
    public native SecondsDouble decrement();

    @Name(value={"operator+="})
    @ByRef
    public native SecondsDouble addPut(@Const @ByRef SecondsDouble var1);

    @Name(value={"operator-="})
    @ByRef
    public native SecondsDouble subtractPut(@Const @ByRef SecondsDouble var1);

    @Name(value={"operator*="})
    @ByRef
    public native SecondsDouble multiplyPut(@Const @ByRef double var1);

    public native double count();

    @ByVal
    @Name(value={"zero"})
    public static native SecondsDouble zero_();

    @ByVal
    public static native SecondsDouble min();

    @ByVal
    public static native SecondsDouble max();
}

