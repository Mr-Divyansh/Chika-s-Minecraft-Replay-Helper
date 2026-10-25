/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback.exporting;

import com.moulberry.flashback.exporting.ImageFrame;
import java.util.function.Consumer;

public interface VideoWriter
extends AutoCloseable {
    public void encode(ImageFrame var1);

    public void finish(Consumer<String> var1);

    @Override
    default public void close() {
    }
}

