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

@Name(value={"std::chrono::duration<float>"})
@Properties(inherit={chrono.class})
public class SecondsFloat
extends Pointer {
    public SecondsFloat() {
        this.allocate();
    }

    private native void allocate();

    public SecondsFloat(float r) {
        this.allocate(r);
    }

    private native void allocate(float var1);

    public SecondsFloat(Nanoseconds d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Nanoseconds var1);

    public SecondsFloat(Seconds d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Seconds var1);

    @Name(value={"operator="})
    @ByRef
    public native SecondsFloat put(@Const @ByRef SecondsFloat var1);

    @Name(value={"operator-"})
    @ByVal
    public native SecondsFloat negate();

    @Name(value={"operator++"})
    @ByRef
    public native SecondsFloat increment();

    @Name(value={"operator--"})
    @ByRef
    public native SecondsFloat decrement();

    @Name(value={"operator+="})
    @ByRef
    public native SecondsFloat addPut(@Const @ByRef SecondsFloat var1);

    @Name(value={"operator-="})
    @ByRef
    public native SecondsFloat subtractPut(@Const @ByRef SecondsFloat var1);

    @Name(value={"operator*="})
    @ByRef
    public native SecondsFloat multiplyPut(@Const @ByRef float var1);

    public native float count();

    @ByVal
    @Name(value={"zero"})
    public static native SecondsFloat zero_();

    @ByVal
    public static native SecondsFloat min();

    @ByVal
    public static native SecondsFloat max();
}

