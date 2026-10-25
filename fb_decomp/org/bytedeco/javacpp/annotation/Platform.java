/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.METHOD})
public @interface Platform {
    public String[] value() default {};

    public String[] not() default {};

    public String[] pattern() default {};

    public String[] pragma() default {};

    public String[] define() default {};

    public String[] exclude() default {};

    public String[] include() default {};

    public String[] cinclude() default {};

    public String[] includepath() default {};

    public String[] includeresource() default {};

    public String[] compiler() default {};

    public String[] linkpath() default {};

    public String[] linkresource() default {};

    public String[] link() default {};

    public String[] frameworkpath() default {};

    public String[] framework() default {};

    public String[] preloadpath() default {};

    public String[] preloadresource() default {};

    public String[] preload() default {};

    public String[] resourcepath() default {};

    public String[] resource() default {};

    public String[] extension() default {};

    public String[] executablepath() default {};

    public String[] executable() default {};

    public String library() default "";
}

