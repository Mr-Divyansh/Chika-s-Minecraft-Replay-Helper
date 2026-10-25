/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Raw;
import org.bytedeco.javacpp.indexer.UIntIndexer;

public class UIntRawIndexer
extends UIntIndexer {
    protected static final Raw RAW = Raw.getInstance();
    protected IntPointer pointer;
    final long base;
    final long size;

    public UIntRawIndexer(IntPointer pointer) {
        this(pointer, Index.create(pointer.limit() - pointer.position()));
    }

    public UIntRawIndexer(IntPointer pointer, long ... sizes) {
        this(pointer, Index.create(sizes));
    }

    public UIntRawIndexer(IntPointer pointer, long[] sizes, long[] strides) {
        this(pointer, Index.create(sizes, strides));
    }

    public UIntRawIndexer(IntPointer pointer, Index index) {
        super(index);
        this.pointer = pointer;
        this.base = pointer.address() + pointer.position() * 4L;
        this.size = pointer.limit() - pointer.position();
    }

    @Override
    public Pointer pointer() {
        return this.pointer;
    }

    public UIntIndexer reindex(Index index) {
        return new UIntRawIndexer(this.pointer, index);
    }

    public long getRaw(long i) {
        return (long)RAW.getInt(this.base + UIntRawIndexer.checkIndex(i, this.size) * 4L) & 0xFFFFFFFFL;
    }

    @Override
    public long get(long i) {
        return this.getRaw(this.index(i));
    }

    @Override
    public UIntIndexer get(long i, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = this.getRaw(this.index(i) + (long)n) & 0xFFFFFFFFL;
        }
        return this;
    }

    @Override
    public long get(long i, long j) {
        return this.getRaw(this.index(i, j)) & 0xFFFFFFFFL;
    }

    @Override
    public UIntIndexer get(long i, long j, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = this.getRaw(this.index(i, j) + (long)n) & 0xFFFFFFFFL;
        }
        return this;
    }

    @Override
    public long get(long i, long j, long k) {
        return this.getRaw(this.index(i, j, k)) & 0xFFFFFFFFL;
    }

    @Override
    public long get(long ... indices) {
        return this.getRaw(this.index(indices)) & 0xFFFFFFFFL;
    }

    @Override
    public UIntIndexer get(long[] indices, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = this.getRaw(this.index(indices) + (long)n) & 0xFFFFFFFFL;
        }
        return this;
    }

    public UIntIndexer putRaw(long i, long n) {
        RAW.putInt(this.base + UIntRawIndexer.checkIndex(i, this.size) * 4L, (int)n);
        return this;
    }

    @Override
    public UIntIndexer put(long i, long n) {
        return this.putRaw(this.index(i), n);
    }

    @Override
    public UIntIndexer put(long i, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i) + (long)n, m[offset + n]);
        }
        return this;
    }

    @Override
    public UIntIndexer put(long i, long j, long n) {
        this.putRaw(this.index(i, j), n);
        return this;
    }

    @Override
    public UIntIndexer put(long i, long j, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i, j) + (long)n, m[offset + n]);
        }
        return this;
    }

    @Override
    public UIntIndexer put(long i, long j, long k, long n) {
        this.putRaw(this.index(i, j, k), n);
        return this;
    }

    @Override
    public UIntIndexer put(long[] indices, long n) {
        this.putRaw(this.index(indices), n);
        return this;
    }

    @Override
    public UIntIndexer put(long[] indices, long[] m, int offset, int length) {
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

