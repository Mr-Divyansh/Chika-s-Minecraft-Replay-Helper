/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.HalfIndexer;
import org.bytedeco.javacpp.indexer.Index;

public class HalfArrayIndexer
extends HalfIndexer {
    protected short[] array;

    public HalfArrayIndexer(short[] array) {
        this(array, Index.create((long)array.length));
    }

    public HalfArrayIndexer(short[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public HalfArrayIndexer(short[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public HalfArrayIndexer(short[] array, Index index) {
        super(index);
        this.array = array;
    }

    public short[] array() {
        return this.array;
    }

    public HalfIndexer reindex(Index index) {
        return new HalfArrayIndexer(this.array, index);
    }

    @Override
    public float get(long i) {
        return HalfArrayIndexer.toFloat(this.array[(int)this.index(i)]);
    }

    @Override
    public HalfIndexer get(long i, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = HalfArrayIndexer.toFloat(this.array[(int)this.index(i) + n]);
        }
        return this;
    }

    @Override
    public float get(long i, long j) {
        return HalfArrayIndexer.toFloat(this.array[(int)this.index(i, j)]);
    }

    @Override
    public HalfIndexer get(long i, long j, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = HalfArrayIndexer.toFloat(this.array[(int)this.index(i, j) + n]);
        }
        return this;
    }

    @Override
    public float get(long i, long j, long k) {
        return HalfArrayIndexer.toFloat(this.array[(int)this.index(i, j, k)]);
    }

    @Override
    public float get(long ... indices) {
        return HalfArrayIndexer.toFloat(this.array[(int)this.index(indices)]);
    }

    @Override
    public HalfIndexer get(long[] indices, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = HalfArrayIndexer.toFloat(this.array[(int)this.index(indices) + n]);
        }
        return this;
    }

    @Override
    public HalfIndexer put(long i, float h) {
        this.array[(int)this.index((long)i)] = (short)HalfArrayIndexer.fromFloat(h);
        return this;
    }

    @Override
    public HalfIndexer put(long i, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = (short)HalfArrayIndexer.fromFloat(h[offset + n]);
        }
        return this;
    }

    @Override
    public HalfIndexer put(long i, long j, float h) {
        this.array[(int)this.index((long)i, (long)j)] = (short)HalfArrayIndexer.fromFloat(h);
        return this;
    }

    @Override
    public HalfIndexer put(long i, long j, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = (short)HalfArrayIndexer.fromFloat(h[offset + n]);
        }
        return this;
    }

    @Override
    public HalfIndexer put(long i, long j, long k, float h) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = (short)HalfArrayIndexer.fromFloat(h);
        return this;
    }

    @Override
    public HalfIndexer put(long[] indices, float h) {
        this.array[(int)this.index((long[])indices)] = (short)HalfArrayIndexer.fromFloat(h);
        return this;
    }

    @Override
    public HalfIndexer put(long[] indices, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = (short)HalfArrayIndexer.fromFloat(h[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

