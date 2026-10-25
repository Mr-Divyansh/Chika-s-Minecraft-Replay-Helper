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
import org.bytedeco.javacpp.presets.chrono;

@Name(value={"std::chrono::seconds"})
@Properties(inherit={chrono.class})
public class Seconds
extends Pointer {
    public Seconds() {
        this.allocate();
    }

    private native void allocate();

    public Seconds(long r) {
        this.allocate(r);
    }

    private native void allocate(long var1);

    public Seconds(Minutes d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Minutes var1);

    public Seconds(Hours d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Hours var1);

    @Name(value={"operator="})
    @ByRef
    public native Seconds put(@Const @ByRef Seconds var1);

    @Name(value={"operator-"})
    @ByVal
    public native Seconds negate();

    @Name(value={"operator++"})
    @ByRef
    public native Seconds increment();

    @Name(value={"operator--"})
    @ByRef
    public native Seconds decrement();

    @Name(value={"operator+="})
    @ByRef
    public native Seconds addPut(@Const @ByRef Seconds var1);

    @Name(value={"operator-="})
    @ByRef
    public native Seconds subtractPut(@Const @ByRef Seconds var1);

    @Name(value={"operator*="})
    @ByRef
    public native Seconds multiplyPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native Seconds modPut(@Const @ByRef long var1);

    @Name(value={"operator%="})
    @ByRef
    public native Seconds modPut(@Const @ByRef Seconds var1);

    public native long count();

    @ByVal
    @Name(value={"zero"})
    public static native Seconds zero_();

    @ByVal
    public static native Seconds min();

    @ByVal
    public static native Seconds max();
}

