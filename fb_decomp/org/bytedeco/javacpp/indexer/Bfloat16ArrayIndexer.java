/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.Bfloat16Indexer;
import org.bytedeco.javacpp.indexer.Index;

public class Bfloat16ArrayIndexer
extends Bfloat16Indexer {
    protected short[] array;

    public Bfloat16ArrayIndexer(short[] array) {
        this(array, Index.create((long)array.length));
    }

    public Bfloat16ArrayIndexer(short[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public Bfloat16ArrayIndexer(short[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public Bfloat16ArrayIndexer(short[] array, Index index) {
        super(index);
        this.array = array;
    }

    public short[] array() {
        return this.array;
    }

    public Bfloat16Indexer reindex(Index index) {
        return new Bfloat16ArrayIndexer(this.array, index);
    }

    @Override
    public float get(long i) {
        return Bfloat16ArrayIndexer.toFloat(this.array[(int)this.index(i)]);
    }

    @Override
    public Bfloat16Indexer get(long i, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = Bfloat16ArrayIndexer.toFloat(this.array[(int)this.index(i) + n]);
        }
        return this;
    }

    @Override
    public float get(long i, long j) {
        return Bfloat16ArrayIndexer.toFloat(this.array[(int)this.index(i, j)]);
    }

    @Override
    public Bfloat16Indexer get(long i, long j, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = Bfloat16ArrayIndexer.toFloat(this.array[(int)this.index(i, j) + n]);
        }
        return this;
    }

    @Override
    public float get(long i, long j, long k) {
        return Bfloat16ArrayIndexer.toFloat(this.array[(int)this.index(i, j, k)]);
    }

    @Override
    public float get(long ... indices) {
        return Bfloat16ArrayIndexer.toFloat(this.array[(int)this.index(indices)]);
    }

    @Override
    public Bfloat16Indexer get(long[] indices, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = Bfloat16ArrayIndexer.toFloat(this.array[(int)this.index(indices) + n]);
        }
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, float h) {
        this.array[(int)this.index((long)i)] = (short)Bfloat16ArrayIndexer.fromFloat(h);
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = (short)Bfloat16ArrayIndexer.fromFloat(h[offset + n]);
        }
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, long j, float h) {
        this.array[(int)this.index((long)i, (long)j)] = (short)Bfloat16ArrayIndexer.fromFloat(h);
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, long j, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = (short)Bfloat16ArrayIndexer.fromFloat(h[offset + n]);
        }
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, long j, long k, float h) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = (short)Bfloat16ArrayIndexer.fromFloat(h);
        return this;
    }

    @Override
    public Bfloat16Indexer put(long[] indices, float h) {
        this.array[(int)this.index((long[])indices)] = (short)Bfloat16ArrayIndexer.fromFloat(h);
        return this;
    }

    @Override
    public Bfloat16Indexer put(long[] indices, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = (short)Bfloat16ArrayIndexer.fromFloat(h[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

