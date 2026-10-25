/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import org.bytedeco.javacpp.Pointer;

public abstract class FunctionPointer
extends Pointer {
    protected FunctionPointer() {
    }

    protected FunctionPointer(Pointer p) {
        super(p);
    }
}

