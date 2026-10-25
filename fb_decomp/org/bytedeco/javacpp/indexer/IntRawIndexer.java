/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.IntIndexer;
import org.bytedeco.javacpp.indexer.Raw;

public class IntRawIndexer
extends IntIndexer {
    protected static final Raw RAW = Raw.getInstance();
    protected IntPointer pointer;
    final long base;
    final long size;

    public IntRawIndexer(IntPointer pointer) {
        this(pointer, Index.create(pointer.limit() - pointer.position()));
    }

    public IntRawIndexer(IntPointer pointer, long ... sizes) {
        this(pointer, Index.create(sizes));
    }

    public IntRawIndexer(IntPointer pointer, long[] sizes, long[] strides) {
        this(pointer, Index.create(sizes, strides));
    }

    public IntRawIndexer(IntPointer pointer, Index index) {
        super(index);
        this.pointer = pointer;
        this.base = pointer.address() + pointer.position() * 4L;
        this.size = pointer.limit() - pointer.position();
    }

    @Override
    public Pointer pointer() {
        return this.pointer;
    }

    public IntIndexer reindex(Index index) {
        return new IntRawIndexer(this.pointer, index);
    }

    public int getRaw(long i) {
        return RAW.getInt(this.base + IntRawIndexer.checkIndex(i, this.size) * 4L);
    }

    @Override
    public int get(long i) {
        return this.getRaw(this.index(i));
    }

    @Override
    public IntIndexer get(long i, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = this.getRaw(this.index(i) + (long)n);
        }
        return this;
    }

    @Override
    public int get(long i, long j) {
        return this.getRaw(this.index(i, j));
    }

    @Override
    public IntIndexer get(long i, long j, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = this.getRaw(this.index(i, j) + (long)n);
        }
        return this;
    }

    @Override
    public int get(long i, long j, long k) {
        return this.getRaw(this.index(i, j, k));
    }

    @Override
    public int get(long ... indices) {
        return this.getRaw(this.index(indices));
    }

    @Override
    public IntIndexer get(long[] indices, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = this.getRaw(this.index(indices) + (long)n);
        }
        return this;
    }

    public IntIndexer putRaw(long i, int n) {
        RAW.putInt(this.base + IntRawIndexer.checkIndex(i, this.size) * 4L, n);
        return this;
    }

    @Override
    public IntIndexer put(long i, int n) {
        return this.putRaw(this.index(i), n);
    }

    @Override
    public IntIndexer put(long i, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i) + (long)n, m[offset + n]);
        }
        return this;
    }

    @Override
    public IntIndexer put(long i, long j, int n) {
        this.putRaw(this.index(i, j), n);
        return this;
    }

    @Override
    public IntIndexer put(long i, long j, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i, j) + (long)n, m[offset + n]);
        }
        return this;
    }

    @Override
    public IntIndexer put(long i, long j, long k, int n) {
        this.putRaw(this.index(i, j, k), n);
        return this;
    }

    @Override
    public IntIndexer put(long[] indices, int n) {
        this.putRaw(this.index(indices), n);
        return this;
    }

    @Override
    public IntIndexer put(long[] indices, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(indices) + (long)n, m[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.pointer = null;
    }
}

