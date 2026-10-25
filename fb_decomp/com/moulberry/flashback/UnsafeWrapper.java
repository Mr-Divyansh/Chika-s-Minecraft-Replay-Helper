/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import sun.misc.Unsafe;

public class UnsafeWrapper {
    public static final Unsafe UNSAFE = UnsafeWrapper.getUnsafeInstance();

    private static Unsafe getUnsafeInstance() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe)theUnsafe.get(null);
        }
        catch (Exception theUnsafe) {
            Field[] fields;
            for (Field field : fields = Unsafe.class.getDeclaredFields()) {
                int modifiers;
                if (!field.getType().equals(Unsafe.class) || !Modifier.isStatic(modifiers = field.getModifiers()) || !Modifier.isFinal(modifiers)) continue;
                try {
                    field.setAccessible(true);
                    return (Unsafe)field.get(null);
                }
                catch (Exception exception) {
                    break;
                }
            }
            throw new Error("Flashback requires sun.misc.Unsafe to be available.");
        }
    }
}

