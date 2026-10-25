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
import org.bytedeco.javacpp.presets.chrono;

@Name(value={"std::chrono::minutes"})
@Properties(inherit={chrono.class})
public class Minutes
extends Pointer {
    public Minutes() {
        this.allocate();
    }

    private native void allocate();

    public Minutes(long r) {
        this.allocate(r);
    }

    private native void allocate(long var1);

    public Minutes(Hours d) {
        this.allocate(d);
    }

    private native void allocate(@Const @ByRef Hours var1);

    @Name(value={"operator="})
    @ByRef
    public native Minutes put(@Const @ByRef Minutes var1);

    @Name(value={"operator-"})
    @ByVal
    public native Minutes negate();

    @Name(value={"operator++"})
    @ByRef
    public native Minutes increment();

    @Name(value={"operator--"})
    @ByRef
    public native Minutes decrement();

    @Name(value={"operator+="})
    @ByRef
    public native Minutes addPut(@Const @ByRef Minutes var1);

    @Name(value={"operator-="})
    @ByRef
    public native Minutes subtractPut(@Const @ByRef Minutes var1);

    @Name(value={"operator*="})
    @ByRef
    public native Minutes multiplyPut(@Const @ByRef int var1);

    @Name(value={"operator%="})
    @ByRef
    public native Minutes modPut(@Const @ByRef int var1);

    @Name(value={"operator%="})
    @ByRef
    public native Minutes modPut(@Const @ByRef Minutes var1);

    public native int count();

    @ByVal
    @Name(value={"zero"})
    public static native Minutes zero_();

    @ByVal
    public static native Minutes min();

    @ByVal
    public static native Minutes max();
}

