/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import java.nio.IntBuffer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.IntIndexer;

public class IntBufferIndexer
extends IntIndexer {
    protected IntBuffer buffer;

    public IntBufferIndexer(IntBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public IntBufferIndexer(IntBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public IntBufferIndexer(IntBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public IntBufferIndexer(IntBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public IntIndexer reindex(Index index) {
        return new IntBufferIndexer(this.buffer, index);
    }

    @Override
    public int get(long i) {
        return this.buffer.get((int)this.index(i));
    }

    @Override
    public IntIndexer get(long i, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = this.buffer.get((int)this.index(i) + n);
        }
        return this;
    }

    @Override
    public int get(long i, long j) {
        return this.buffer.get((int)this.index(i, j));
    }

    @Override
    public IntIndexer get(long i, long j, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = this.buffer.get((int)this.index(i, j) + n);
        }
        return this;
    }

    @Override
    public int get(long i, long j, long k) {
        return this.buffer.get((int)this.index(i, j, k));
    }

    @Override
    public int get(long ... indices) {
        return this.buffer.get((int)this.index(indices));
    }

    @Override
    public IntIndexer get(long[] indices, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = this.buffer.get((int)this.index(indices) + n);
        }
        return this;
    }

    @Override
    public IntIndexer put(long i, int n) {
        this.buffer.put((int)this.index(i), n);
        return this;
    }

    @Override
    public IntIndexer put(long i, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, m[offset + n]);
        }
        return this;
    }

    @Override
    public IntIndexer put(long i, long j, int n) {
        this.buffer.put((int)this.index(i, j), n);
        return this;
    }

    @Override
    public IntIndexer put(long i, long j, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, m[offset + n]);
        }
        return this;
    }

    @Override
    public IntIndexer put(long i, long j, long k, int n) {
        this.buffer.put((int)this.index(i, j, k), n);
        return this;
    }

    @Override
    public IntIndexer put(long[] indices, int n) {
        this.buffer.put((int)this.index(indices), n);
        return this;
    }

    @Override
    public IntIndexer put(long[] indices, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, m[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

