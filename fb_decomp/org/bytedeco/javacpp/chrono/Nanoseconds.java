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
import org.bytedeco.javacpp.chrono.Hours;
import org.bytedeco.javacpp.chrono.Microseconds;
import org.bytedeco.javacpp.chrono.Milliseconds;
import org.bytedeco.javacpp.chrono.Minutes;
import org.bytedeco.javacpp.chrono.Seconds;
import org.bytedeco.javacpp.chrono.SteadyDuration;
import org.bytedeco.javacpp.chrono.SystemDuration;
import org.bytedeco.javacpp.presets.chrono;

@Name(value={"std::chrono::nanoseconds"})
@Properties(inherit={chrono.class})
public class Nanoseconds
extends Pointer {
    public Nanoseconds() {
        this.allocate();
    }

    private native void allocate();

    public Nanoseconds(long r) {
        this.allocate(r);
    }

    private native void allocate(long var1);

    public Nanoseconds(Microseconds d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Microseconds var1);

    public Nanoseconds(Milliseconds d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Milliseconds var1);

    public Nanoseconds(Seconds d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Seconds var1);

    public Nanoseconds(Minutes d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Minutes var1);

    public Nanoseconds(Hours d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Hours var1);

    public Nanoseconds(SystemDuration d) {
        super((Pointer)null);
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef SystemDuration var1);

    public Nanoseconds(HighResolutionDuration d) {
        super((Pointer)null);
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef HighResolutionDuration var1);

    public Nanoseconds(SteadyDuration d) {
        super((Pointer)null);
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef SteadyDuration var1);

    @Name(value={"operator="})
    @ByRef
    public native Nanoseconds put(@Const @ByRef Nanoseconds var1);

    @Name(value={"operator-"})
    @ByVal
    public native Nanoseconds negate();

    @Name(value={"operator++"})
    @ByRef
    public native Nanoseconds increment();

    @Name(value={"operator--"})
    @ByRef
    public native Nanoseconds decrement();

    @Name(value={"operator+="})
    @ByRef
    public native Nanoseconds addPut(@Const @ByRef Nanoseconds var1);

    @Name(value={"operator-="})
    @ByRef
    public native Nanoseconds subtractPut(@Const @ByRef Nanoseconds var1);

    @Name(value={"operator*="})
    @ByRef
    public native Nanoseconds multiplyPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native Nanoseconds modPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native Nanoseconds modPut(@Const @ByRef Nanoseconds var1);

    public native long count();

    @ByVal
    @Name(value={"zero"})
    public static native Nanoseconds zero_();

    @ByVal
    public static native Nanoseconds min();

    @ByVal
    public static native Nanoseconds max();
}

