/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.bytedeco.javacpp.annotation.Adapter;
import org.bytedeco.javacpp.annotation.Cast;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.PARAMETER})
@Cast(value={"std::basic_string", "&"})
@Adapter(value="StringAdapter")
public @interface StdWString {
    public String value() default "wchar_t";
}

