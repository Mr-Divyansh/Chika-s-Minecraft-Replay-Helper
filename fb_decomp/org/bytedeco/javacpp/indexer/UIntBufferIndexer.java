/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import java.nio.IntBuffer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.UIntIndexer;

public class UIntBufferIndexer
extends UIntIndexer {
    protected IntBuffer buffer;

    public UIntBufferIndexer(IntBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public UIntBufferIndexer(IntBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public UIntBufferIndexer(IntBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public UIntBufferIndexer(IntBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public UIntIndexer reindex(Index index) {
        return new UIntBufferIndexer(this.buffer, index);
    }

    @Override
    public long get(long i) {
        return (long)this.buffer.get((int)this.index(i)) & 0xFFFFFFFFL;
    }

    @Override
    public UIntIndexer get(long i, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = (long)this.buffer.get((int)this.index(i) + n) & 0xFFFFFFFFL;
        }
        return this;
    }

    @Override
    public long get(long i, long j) {
        return (long)this.buffer.get((int)this.index(i, j)) & 0xFFFFFFFFL;
    }

    @Override
    public UIntIndexer get(long i, long j, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = (long)this.buffer.get((int)this.index(i, j) + n) & 0xFFFFFFFFL;
        }
        return this;
    }

    @Override
    public long get(long i, long j, long k) {
        return (long)this.buffer.get((int)this.index(i, j, k)) & 0xFFFFFFFFL;
    }

    @Override
    public long get(long ... indices) {
        return (long)this.buffer.get((int)this.index(indices)) & 0xFFFFFFFFL;
    }

    @Override
    public UIntIndexer get(long[] indices, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = (long)this.buffer.get((int)this.index(indices) + n) & 0xFFFFFFFFL;
        }
        return this;
    }

    @Override
    public UIntIndexer put(long i, long n) {
        this.buffer.put((int)this.index(i), (int)n);
        return this;
    }

    @Override
    public UIntIndexer put(long i, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, (int)m[offset + n]);
        }
        return this;
    }

    @Override
    public UIntIndexer put(long i, long j, long n) {
        this.buffer.put((int)this.index(i, j), (int)n);
        return this;
    }

    @Override
    public UIntIndexer put(long i, long j, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, (int)m[offset + n]);
        }
        return this;
    }

    @Override
    public UIntIndexer put(long i, long j, long k, long n) {
        this.buffer.put((int)this.index(i, j, k), (int)n);
        return this;
    }

    @Override
    public UIntIndexer put(long[] indices, long n) {
        this.buffer.put((int)this.index(indices), (int)n);
        return this;
    }

    @Override
    public UIntIndexer put(long[] indices, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, (int)m[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

