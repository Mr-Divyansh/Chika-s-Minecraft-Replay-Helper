/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.IntIndexer;

public class IntArrayIndexer
extends IntIndexer {
    protected int[] array;

    public IntArrayIndexer(int[] array) {
        this(array, Index.create((long)array.length));
    }

    public IntArrayIndexer(int[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public IntArrayIndexer(int[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public IntArrayIndexer(int[] array, Index index) {
        super(index);
        this.array = array;
    }

    public int[] array() {
        return this.array;
    }

    public IntIndexer reindex(Index index) {
        return new IntArrayIndexer(this.array, index);
    }

    @Override
    public int get(long i) {
        return this.array[(int)this.index(i)];
    }

    @Override
    public IntIndexer get(long i, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = this.array[(int)this.index(i) + n];
        }
        return this;
    }

    @Override
    public int get(long i, long j) {
        return this.array[(int)this.index(i, j)];
    }

    @Override
    public IntIndexer get(long i, long j, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = this.array[(int)this.index(i, j) + n];
        }
        return this;
    }

    @Override
    public int get(long i, long j, long k) {
        return this.array[(int)this.index(i, j, k)];
    }

    @Override
    public int get(long ... indices) {
        return this.array[(int)this.index(indices)];
    }

    @Override
    public IntIndexer get(long[] indices, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = this.array[(int)this.index(indices) + n];
        }
        return this;
    }

    @Override
    public IntIndexer put(long i, int n) {
        this.array[(int)this.index((long)i)] = n;
        return this;
    }

    @Override
    public IntIndexer put(long i, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = m[offset + n];
        }
        return this;
    }

    @Override
    public IntIndexer put(long i, long j, int n) {
        this.array[(int)this.index((long)i, (long)j)] = n;
        return this;
    }

    @Override
    public IntIndexer put(long i, long j, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = m[offset + n];
        }
        return this;
    }

    @Override
    public IntIndexer put(long i, long j, long k, int n) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = n;
        return this;
    }

    @Override
    public IntIndexer put(long[] indices, int n) {
        this.array[(int)this.index((long[])indices)] = n;
        return this;
    }

    @Override
    public IntIndexer put(long[] indices, int[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = m[offset + n];
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

