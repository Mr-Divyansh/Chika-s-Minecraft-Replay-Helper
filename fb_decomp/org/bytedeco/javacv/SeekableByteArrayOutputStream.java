/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import java.io.ByteArrayOutputStream;
import org.bytedeco.javacv.Seekable;

public class SeekableByteArrayOutputStream
extends ByteArrayOutputStream
implements Seekable {
    long position;

    @Override
    public void seek(long position, int whence) {
        if (position < 0L || position > (long)this.count || whence != 0) {
            throw new IllegalArgumentException();
        }
        this.position = position;
    }

    @Override
    public synchronized void write(int b) {
        if (this.position < (long)this.count) {
            this.buf[(int)this.position] = (byte)b;
        } else {
            super.write(b);
        }
        ++this.position;
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) {
        if (this.position < (long)this.count) {
            for (int i = 0; i < len; ++i) {
                this.write(b[off + i]);
            }
        } else {
            super.write(b, off, len);
            this.position = this.count;
        }
    }
}

