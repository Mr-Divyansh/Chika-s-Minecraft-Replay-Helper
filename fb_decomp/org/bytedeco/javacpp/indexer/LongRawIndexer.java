/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.LongPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.LongIndexer;
import org.bytedeco.javacpp.indexer.Raw;

public class LongRawIndexer
extends LongIndexer {
    protected static final Raw RAW = Raw.getInstance();
    protected LongPointer pointer;
    final long base;
    final long size;

    public LongRawIndexer(LongPointer pointer) {
        this(pointer, Index.create(pointer.limit() - pointer.position()));
    }

    public LongRawIndexer(LongPointer pointer, long ... sizes) {
        this(pointer, Index.create(sizes));
    }

    public LongRawIndexer(LongPointer pointer, long[] sizes, long[] strides) {
        this(pointer, Index.create(sizes, strides));
    }

    public LongRawIndexer(LongPointer pointer, Index index) {
        super(index);
        this.pointer = pointer;
        this.base = pointer.address() + pointer.position() * 8L;
        this.size = pointer.limit() - pointer.position();
    }

    @Override
    public Pointer pointer() {
        return this.pointer;
    }

    public LongIndexer reindex(Index index) {
        return new LongRawIndexer(this.pointer, index);
    }

    public long getRaw(long i) {
        return RAW.getLong(this.base + LongRawIndexer.checkIndex(i, this.size) * 8L);
    }

    @Override
    public long get(long i) {
        return this.getRaw(this.index(i));
    }

    @Override
    public LongIndexer get(long i, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = this.getRaw(this.index(i) + (long)n);
        }
        return this;
    }

    @Override
    public long get(long i, long j) {
        return this.getRaw(this.index(i, j));
    }

    @Override
    public LongIndexer get(long i, long j, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = this.getRaw(this.index(i, j) + (long)n);
        }
        return this;
    }

    @Override
    public long get(long i, long j, long k) {
        return this.getRaw(this.index(i, j, k));
    }

    @Override
    public long get(long ... indices) {
        return this.getRaw(this.index(indices));
    }

    @Override
    public LongIndexer get(long[] indices, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = this.getRaw(this.index(indices) + (long)n);
        }
        return this;
    }

    public LongIndexer putRaw(long i, long l) {
        RAW.putLong(this.base + LongRawIndexer.checkIndex(i, this.size) * 8L, l);
        return this;
    }

    @Override
    public LongIndexer put(long i, long l) {
        return this.putRaw(this.index(i), l);
    }

    @Override
    public LongIndexer put(long i, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i) + (long)n, l[offset + n]);
        }
        return this;
    }

    @Override
    public LongIndexer put(long i, long j, long l) {
        this.putRaw(this.index(i, j), l);
        return this;
    }

    @Override
    public LongIndexer put(long i, long j, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i, j) + (long)n, l[offset + n]);
        }
        return this;
    }

    @Override
    public LongIndexer put(long i, long j, long k, long l) {
        this.putRaw(this.index(i, j, k), l);
        return this;
    }

    @Override
    public LongIndexer put(long[] indices, long l) {
        this.putRaw(this.index(indices), l);
        return this;
    }

    @Override
    public LongIndexer put(long[] indices, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(indices) + (long)n, l[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.pointer = null;
    }
}

