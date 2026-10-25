/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback;

public class SneakyThrow {
    public static <E extends Throwable> RuntimeException sneakyThrow(Throwable e) throws E {
        throw e;
    }
}

