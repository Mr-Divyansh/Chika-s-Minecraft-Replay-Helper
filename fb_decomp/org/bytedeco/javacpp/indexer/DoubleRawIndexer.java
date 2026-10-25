/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Raw;

public class DoubleRawIndexer
extends DoubleIndexer {
    protected static final Raw RAW = Raw.getInstance();
    protected DoublePointer pointer;
    final long base;
    final long size;

    public DoubleRawIndexer(DoublePointer pointer) {
        this(pointer, Index.create(pointer.limit() - pointer.position()));
    }

    public DoubleRawIndexer(DoublePointer pointer, long ... sizes) {
        this(pointer, Index.create(sizes));
    }

    public DoubleRawIndexer(DoublePointer pointer, long[] sizes, long[] strides) {
        this(pointer, Index.create(sizes, strides));
    }

    public DoubleRawIndexer(DoublePointer pointer, Index index) {
        super(index);
        this.pointer = pointer;
        this.base = pointer.address() + pointer.position() * 8L;
        this.size = pointer.limit() - pointer.position();
    }

    @Override
    public Pointer pointer() {
        return this.pointer;
    }

    public DoubleIndexer reindex(Index index) {
        return new DoubleRawIndexer(this.pointer, index);
    }

    public double getRaw(long i) {
        return RAW.getDouble(this.base + DoubleRawIndexer.checkIndex(i, this.size) * 8L);
    }

    @Override
    public double get(long i) {
        return this.getRaw(this.index(i));
    }

    @Override
    public DoubleIndexer get(long i, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            d[offset + n] = this.getRaw(this.index(i) + (long)n);
        }
        return this;
    }

    @Override
    public double get(long i, long j) {
        return this.getRaw(this.index(i, j));
    }

    @Override
    public DoubleIndexer get(long i, long j, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            d[offset + n] = this.getRaw(this.index(i, j) + (long)n);
        }
        return this;
    }

    @Override
    public double get(long i, long j, long k) {
        return this.getRaw(this.index(i, j, k));
    }

    @Override
    public double get(long ... indices) {
        return this.getRaw(this.index(indices));
    }

    @Override
    public DoubleIndexer get(long[] indices, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            d[offset + n] = this.getRaw(this.index(indices) + (long)n);
        }
        return this;
    }

    public DoubleIndexer putRaw(long i, double d) {
        RAW.putDouble(this.base + DoubleRawIndexer.checkIndex(i, this.size) * 8L, d);
        return this;
    }

    @Override
    public DoubleIndexer put(long i, double d) {
        return this.putRaw(this.index(i), d);
    }

    @Override
    public DoubleIndexer put(long i, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i) + (long)n, d[offset + n]);
        }
        return this;
    }

    @Override
    public DoubleIndexer put(long i, long j, double d) {
        this.putRaw(this.index(i, j), d);
        return this;
    }

    @Override
    public DoubleIndexer put(long i, long j, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i, j) + (long)n, d[offset + n]);
        }
        return this;
    }

    @Override
    public DoubleIndexer put(long i, long j, long k, double d) {
        this.putRaw(this.index(i, j, k), d);
        return this;
    }

    @Override
    public DoubleIndexer put(long[] indices, double d) {
        this.putRaw(this.index(indices), d);
        return this;
    }

    @Override
    public DoubleIndexer put(long[] indices, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(indices) + (long)n, d[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.pointer = null;
    }
}

