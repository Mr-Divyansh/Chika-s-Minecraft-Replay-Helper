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

@Name(value={"std::chrono::hours"})
@Properties(inherit={chrono.class})
public class Hours
extends Pointer {
    public Hours() {
        this.allocate();
    }

    private native void allocate();

    public Hours(long r) {
        this.allocate(r);
    }

    private native void allocate(long var1);

    @Name(value={"operator="})
    @ByRef
    public native Hours put(@Const @ByRef Hours var1);

    @Name(value={"operator-"})
    @ByVal
    public native Hours negate();

    @Name(value={"operator++"})
    @ByRef
    public native Hours increment();

    @Name(value={"operator--"})
    @ByRef
    public native Hours decrement();

    @Name(value={"operator+="})
    @ByRef
    public native Hours addPut(@Const @ByRef Hours var1);

    @Name(value={"operator-="})
    @ByRef
    public native Hours subtractPut(@Const @ByRef Hours var1);

    @Name(value={"operator*="})
    @ByRef
    public native Hours multiplyPut(@Const @ByRef int var1);

    @Name(value={"operator%="})
    @ByRef
    public native Hours modPut(@Const @ByRef int var1);

    @Name(value={"operator%="})
    @ByRef
    public native Hours modPut(@Const @ByRef Hours var1);

    public native int count();

    @ByVal
    @Name(value={"zero"})
    public static native Hours zero_();

    @ByVal
    public static native Hours min();

    @ByVal
    public static native Hours max();
}

