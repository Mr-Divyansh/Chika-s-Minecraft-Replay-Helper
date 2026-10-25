/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import org.bytedeco.javacv.Frame;

public abstract class FrameConverter<F>
implements AutoCloseable {
    protected Frame frame;

    public abstract Frame convert(F var1);

    public abstract F convert(Frame var1);

    @Override
    public void close() {
        if (this.frame != null) {
            this.frame.close();
            this.frame = null;
        }
    }
}

