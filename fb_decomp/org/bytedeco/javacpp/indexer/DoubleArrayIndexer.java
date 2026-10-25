/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacpp.indexer.Index;

public class DoubleArrayIndexer
extends DoubleIndexer {
    protected double[] array;

    public DoubleArrayIndexer(double[] array) {
        this(array, Index.create((long)array.length));
    }

    public DoubleArrayIndexer(double[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public DoubleArrayIndexer(double[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public DoubleArrayIndexer(double[] array, Index index) {
        super(index);
        this.array = array;
    }

    public double[] array() {
        return this.array;
    }

    public DoubleIndexer reindex(Index index) {
        return new DoubleArrayIndexer(this.array, index);
    }

    @Override
    public double get(long i) {
        return this.array[(int)this.index(i)];
    }

    @Override
    public DoubleIndexer get(long i, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            d[offset + n] = this.array[(int)this.index(i) + n];
        }
        return this;
    }

    @Override
    public double get(long i, long j) {
        return this.array[(int)this.index(i, j)];
    }

    @Override
    public DoubleIndexer get(long i, long j, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            d[offset + n] = this.array[(int)this.index(i, j) + n];
        }
        return this;
    }

    @Override
    public double get(long i, long j, long k) {
        return this.array[(int)this.index(i, j, k)];
    }

    @Override
    public double get(long ... indices) {
        return this.array[(int)this.index(indices)];
    }

    @Override
    public DoubleIndexer get(long[] indices, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            d[offset + n] = this.array[(int)this.index(indices) + n];
        }
        return this;
    }

    @Override
    public DoubleIndexer put(long i, double d) {
        this.array[(int)this.index((long)i)] = d;
        return this;
    }

    @Override
    public DoubleIndexer put(long i, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = d[offset + n];
        }
        return this;
    }

    @Override
    public DoubleIndexer put(long i, long j, double d) {
        this.array[(int)this.index((long)i, (long)j)] = d;
        return this;
    }

    @Override
    public DoubleIndexer put(long i, long j, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = d[offset + n];
        }
        return this;
    }

    @Override
    public DoubleIndexer put(long i, long j, long k, double d) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = d;
        return this;
    }

    @Override
    public DoubleIndexer put(long[] indices, double d) {
        this.array[(int)this.index((long[])indices)] = d;
        return this;
    }

    @Override
    public DoubleIndexer put(long[] indices, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = d[offset + n];
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

