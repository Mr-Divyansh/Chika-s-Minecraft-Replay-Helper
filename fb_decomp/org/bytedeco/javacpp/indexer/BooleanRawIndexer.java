/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.BooleanPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.indexer.BooleanIndexer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Raw;

public class BooleanRawIndexer
extends BooleanIndexer {
    protected static final Raw RAW = Raw.getInstance();
    protected BooleanPointer pointer;
    final long base;
    final long size;

    public BooleanRawIndexer(BooleanPointer pointer) {
        this(pointer, Index.create(pointer.limit() - pointer.position()));
    }

    public BooleanRawIndexer(BooleanPointer pointer, long ... sizes) {
        this(pointer, sizes, BooleanRawIndexer.strides(sizes));
    }

    public BooleanRawIndexer(BooleanPointer pointer, long[] sizes, long[] strides) {
        this(pointer, Index.create(sizes, strides));
    }

    public BooleanRawIndexer(BooleanPointer pointer, Index index) {
        super(index);
        this.pointer = pointer;
        this.base = pointer.address() + pointer.position() * 1L;
        this.size = pointer.limit() - pointer.position();
    }

    @Override
    public Pointer pointer() {
        return this.pointer;
    }

    public BooleanIndexer reindex(Index index) {
        return new BooleanRawIndexer(this.pointer, index);
    }

    public boolean getRaw(long i) {
        return RAW.getBoolean(this.base + BooleanRawIndexer.checkIndex(i, this.size) * 1L);
    }

    @Override
    public boolean get(long i) {
        return this.getRaw(this.index(i));
    }

    @Override
    public BooleanIndexer get(long i, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.getRaw(this.index(i) + (long)n);
        }
        return this;
    }

    @Override
    public boolean get(long i, long j) {
        return this.getRaw(this.index(i, j));
    }

    @Override
    public BooleanIndexer get(long i, long j, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.getRaw(this.index(i, j) + (long)n);
        }
        return this;
    }

    @Override
    public boolean get(long i, long j, long k) {
        return this.getRaw(this.index(i, j, k));
    }

    @Override
    public boolean get(long ... indices) {
        return this.getRaw(this.index(indices));
    }

    @Override
    public BooleanIndexer get(long[] indices, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.getRaw(this.index(indices) + (long)n);
        }
        return this;
    }

    public BooleanIndexer putRaw(long i, boolean b) {
        RAW.putBoolean(this.base + BooleanRawIndexer.checkIndex(i, this.size) * 1L, b);
        return this;
    }

    @Override
    public BooleanIndexer put(long i, boolean b) {
        return this.putRaw(this.index(i), b);
    }

    @Override
    public BooleanIndexer put(long i, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i) + (long)n, b[offset + n]);
        }
        return this;
    }

    @Override
    public BooleanIndexer put(long i, long j, boolean b) {
        this.putRaw(this.index(i, j), b);
        return this;
    }

    @Override
    public BooleanIndexer put(long i, long j, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i, j) + (long)n, b[offset + n]);
        }
        return this;
    }

    @Override
    public BooleanIndexer put(long i, long j, long k, boolean b) {
        this.putRaw(this.index(i, j, k), b);
        return this;
    }

    @Override
    public BooleanIndexer put(long[] indices, boolean b) {
        this.putRaw(this.index(indices), b);
        return this;
    }

    @Override
    public BooleanIndexer put(long[] indices, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(indices) + (long)n, b[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.pointer = null;
    }
}

