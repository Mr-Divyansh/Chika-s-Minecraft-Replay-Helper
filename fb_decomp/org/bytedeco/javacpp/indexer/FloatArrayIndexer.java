/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.javacpp.indexer.Index;

public class FloatArrayIndexer
extends FloatIndexer {
    protected float[] array;

    public FloatArrayIndexer(float[] array) {
        this(array, Index.create((long)array.length));
    }

    public FloatArrayIndexer(float[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public FloatArrayIndexer(float[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public FloatArrayIndexer(float[] array, Index index) {
        super(index);
        this.array = array;
    }

    public float[] array() {
        return this.array;
    }

    public FloatIndexer reindex(Index index) {
        return new FloatArrayIndexer(this.array, index);
    }

    @Override
    public float get(long i) {
        return this.array[(int)this.index(i)];
    }

    @Override
    public FloatIndexer get(long i, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            f[offset + n] = this.array[(int)this.index(i) + n];
        }
        return this;
    }

    @Override
    public float get(long i, long j) {
        return this.array[(int)this.index(i, j)];
    }

    @Override
    public FloatIndexer get(long i, long j, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            f[offset + n] = this.array[(int)this.index(i, j) + n];
        }
        return this;
    }

    @Override
    public float get(long i, long j, long k) {
        return this.array[(int)this.index(i, j, k)];
    }

    @Override
    public float get(long ... indices) {
        return this.array[(int)this.index(indices)];
    }

    @Override
    public FloatIndexer get(long[] indices, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            f[offset + n] = this.array[(int)this.index(indices) + n];
        }
        return this;
    }

    @Override
    public FloatIndexer put(long i, float f) {
        this.array[(int)this.index((long)i)] = f;
        return this;
    }

    @Override
    public FloatIndexer put(long i, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = f[offset + n];
        }
        return this;
    }

    @Override
    public FloatIndexer put(long i, long j, float f) {
        this.array[(int)this.index((long)i, (long)j)] = f;
        return this;
    }

    @Override
    public FloatIndexer put(long i, long j, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = f[offset + n];
        }
        return this;
    }

    @Override
    public FloatIndexer put(long i, long j, long k, float f) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = f;
        return this;
    }

    @Override
    public FloatIndexer put(long[] indices, float f) {
        this.array[(int)this.index((long[])indices)] = f;
        return this;
    }

    @Override
    public FloatIndexer put(long[] indices, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = f[offset + n];
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

