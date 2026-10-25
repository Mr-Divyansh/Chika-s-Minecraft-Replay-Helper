/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.bytedeco.javacpp.annotation.Platform;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface Properties {
    public Class[] inherit() default {};

    public String[] names() default {};

    public Platform[] value() default {};

    public String target() default "";

    public String global() default "";

    public String helper() default "";
}

