/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import java.nio.ShortBuffer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.ShortIndexer;

public class ShortBufferIndexer
extends ShortIndexer {
    protected ShortBuffer buffer;

    public ShortBufferIndexer(ShortBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public ShortBufferIndexer(ShortBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public ShortBufferIndexer(ShortBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public ShortBufferIndexer(ShortBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public ShortIndexer reindex(Index index) {
        return new ShortBufferIndexer(this.buffer, index);
    }

    @Override
    public short get(long i) {
        return this.buffer.get((int)this.index(i));
    }

    @Override
    public ShortIndexer get(long i, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.buffer.get((int)this.index(i) + n);
        }
        return this;
    }

    @Override
    public short get(long i, long j) {
        return this.buffer.get((int)this.index(i, j));
    }

    @Override
    public ShortIndexer get(long i, long j, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.buffer.get((int)this.index(i, j) + n);
        }
        return this;
    }

    @Override
    public short get(long i, long j, long k) {
        return this.buffer.get((int)this.index(i, j, k));
    }

    @Override
    public short get(long ... indices) {
        return this.buffer.get((int)this.index(indices));
    }

    @Override
    public ShortIndexer get(long[] indices, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.buffer.get((int)this.index(indices) + n);
        }
        return this;
    }

    @Override
    public ShortIndexer put(long i, short s) {
        this.buffer.put((int)this.index(i), s);
        return this;
    }

    @Override
    public ShortIndexer put(long i, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, s[offset + n]);
        }
        return this;
    }

    @Override
    public ShortIndexer put(long i, long j, short s) {
        this.buffer.put((int)this.index(i, j), s);
        return this;
    }

    @Override
    public ShortIndexer put(long i, long j, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, s[offset + n]);
        }
        return this;
    }

    @Override
    public ShortIndexer put(long i, long j, long k, short s) {
        this.buffer.put((int)this.index(i, j, k), s);
        return this;
    }

    @Override
    public ShortIndexer put(long[] indices, short s) {
        this.buffer.put((int)this.index(indices), s);
        return this;
    }

    @Override
    public ShortIndexer put(long[] indices, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, s[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

