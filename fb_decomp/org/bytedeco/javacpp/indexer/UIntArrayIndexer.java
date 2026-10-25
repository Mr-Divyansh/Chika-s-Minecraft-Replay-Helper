/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.UIntIndexer;

public class UIntArrayIndexer
extends UIntIndexer {
    protected int[] array;

    public UIntArrayIndexer(int[] array) {
        this(array, Index.create((long)array.length));
    }

    public UIntArrayIndexer(int[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public UIntArrayIndexer(int[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public UIntArrayIndexer(int[] array, Index index) {
        super(index);
        this.array = array;
    }

    public int[] array() {
        return this.array;
    }

    public UIntIndexer reindex(Index index) {
        return new UIntArrayIndexer(this.array, index);
    }

    @Override
    public long get(long i) {
        return (long)this.array[(int)this.index(i)] & 0xFFFFFFFFL;
    }

    @Override
    public UIntIndexer get(long i, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = (long)this.array[(int)this.index(i) + n] & 0xFFFFFFFFL;
        }
        return this;
    }

    @Override
    public long get(long i, long j) {
        return (long)this.array[(int)this.index(i, j)] & 0xFFFFFFFFL;
    }

    @Override
    public UIntIndexer get(long i, long j, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = (long)this.array[(int)this.index(i, j) + n] & 0xFFFFFFFFL;
        }
        return this;
    }

    @Override
    public long get(long i, long j, long k) {
        return (long)this.array[(int)this.index(i, j, k)] & 0xFFFFFFFFL;
    }

    @Override
    public long get(long ... indices) {
        return (long)this.array[(int)this.index(indices)] & 0xFFFFFFFFL;
    }

    @Override
    public UIntIndexer get(long[] indices, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            m[offset + n] = (long)this.array[(int)this.index(indices) + n] & 0xFFFFFFFFL;
        }
        return this;
    }

    @Override
    public UIntIndexer put(long i, long n) {
        this.array[(int)this.index((long)i)] = (int)n;
        return this;
    }

    @Override
    public UIntIndexer put(long i, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = (int)m[offset + n];
        }
        return this;
    }

    @Override
    public UIntIndexer put(long i, long j, long n) {
        this.array[(int)this.index((long)i, (long)j)] = (int)n;
        return this;
    }

    @Override
    public UIntIndexer put(long i, long j, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = (int)m[offset + n];
        }
        return this;
    }

    @Override
    public UIntIndexer put(long i, long j, long k, long n) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = (int)n;
        return this;
    }

    @Override
    public UIntIndexer put(long[] indices, long n) {
        this.array[(int)this.index((long[])indices)] = (int)n;
        return this;
    }

    @Override
    public UIntIndexer put(long[] indices, long[] m, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = (int)m[offset + n];
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

