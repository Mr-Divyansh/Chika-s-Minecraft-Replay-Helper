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
import org.bytedeco.javacpp.chrono.Minutes;
import org.bytedeco.javacpp.chrono.Seconds;
import org.bytedeco.javacpp.presets.chrono;

@Name(value={"std::chrono::milliseconds"})
@Properties(inherit={chrono.class})
public class Milliseconds
extends Pointer {
    public Milliseconds() {
        this.allocate();
    }

    private native void allocate();

    public Milliseconds(long r) {
        this.allocate(r);
    }

    private native void allocate(long var1);

    public Milliseconds(Seconds d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Seconds var1);

    public Milliseconds(Minutes d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Minutes var1);

    public Milliseconds(Hours d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Hours var1);

    @Name(value={"operator="})
    @ByRef
    public native Milliseconds put(@Const @ByRef Milliseconds var1);

    @Name(value={"operator-"})
    @ByVal
    public native Milliseconds negate();

    @Name(value={"operator++"})
    @ByRef
    public native Milliseconds increment();

    @Name(value={"operator--"})
    @ByRef
    public native Milliseconds decrement();

    @Name(value={"operator+="})
    @ByRef
    public native Milliseconds addPut(@Const @ByRef Milliseconds var1);

    @Name(value={"operator-="})
    @ByRef
    public native Milliseconds subtractPut(@Const @ByRef Milliseconds var1);

    @Name(value={"operator*="})
    @ByRef
    public native Milliseconds multiplyPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native Milliseconds modPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native Milliseconds modPut(@Const @ByRef Milliseconds var1);

    public native long count();

    @ByVal
    @Name(value={"zero"})
    public static native Milliseconds zero_();

    @ByVal
    public static native Milliseconds min();

    @ByVal
    public static native Milliseconds max();
}

