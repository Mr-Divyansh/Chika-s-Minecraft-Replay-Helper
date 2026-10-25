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
import org.bytedeco.javacpp.chrono.Hours;
import org.bytedeco.javacpp.chrono.Milliseconds;
import org.bytedeco.javacpp.chrono.Minutes;
import org.bytedeco.javacpp.chrono.Seconds;
import org.bytedeco.javacpp.presets.chrono;

@Name(value={"std::chrono::microseconds"})
@Properties(inherit={chrono.class})
public class Microseconds
extends Pointer {
    public Microseconds() {
        this.allocate();
    }

    private native void allocate();

    public Microseconds(long r) {
        this.allocate(r);
    }

    private native void allocate(long var1);

    public Microseconds(Milliseconds d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Milliseconds var1);

    public Microseconds(Seconds d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Seconds var1);

    public Microseconds(Minutes d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Minutes var1);

    public Microseconds(Hours d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Hours var1);

    @Name(value={"operator="})
    @ByRef
    public native Microseconds put(@Const @ByRef Microseconds var1);

    @Name(value={"operator-"})
    @ByVal
    public native Microseconds negate();

    @Name(value={"operator++"})
    @ByRef
    public native Microseconds increment();

    @Name(value={"operator--"})
    @ByRef
    public native Microseconds decrement();

    @Name(value={"operator+="})
    @ByRef
    public native Microseconds addPut(@Const @ByRef Microseconds var1);

    @Name(value={"operator-="})
    @ByRef
    public native Microseconds subtractPut(@Const @ByRef Microseconds var1);

    @Name(value={"operator*="})
    @ByRef
    public native Microseconds multiplyPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native Microseconds modPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native Microseconds modPut(@Const @ByRef Microseconds var1);

    public native long count();

    @ByVal
    @Name(value={"zero"})
    public static native Microseconds zero_();

    @ByVal
    public static native Microseconds min();

    @ByVal
    public static native Microseconds max();
}

