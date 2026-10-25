/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.LongIndexer;

public class LongArrayIndexer
extends LongIndexer {
    protected long[] array;

    public LongArrayIndexer(long[] array) {
        this(array, Index.create((long)array.length));
    }

    public LongArrayIndexer(long[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public LongArrayIndexer(long[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public LongArrayIndexer(long[] array, Index index) {
        super(index);
        this.array = array;
    }

    public long[] array() {
        return this.array;
    }

    public LongIndexer reindex(Index index) {
        return new LongArrayIndexer(this.array, index);
    }

    @Override
    public long get(long i) {
        return this.array[(int)this.index(i)];
    }

    @Override
    public LongIndexer get(long i, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = this.array[(int)this.index(i) + n];
        }
        return this;
    }

    @Override
    public long get(long i, long j) {
        return this.array[(int)this.index(i, j)];
    }

    @Override
    public LongIndexer get(long i, long j, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = this.array[(int)this.index(i, j) + n];
        }
        return this;
    }

    @Override
    public long get(long i, long j, long k) {
        return this.array[(int)this.index(i, j, k)];
    }

    @Override
    public long get(long ... indices) {
        return this.array[(int)this.index(indices)];
    }

    @Override
    public LongIndexer get(long[] indices, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = this.array[(int)this.index(indices) + n];
        }
        return this;
    }

    @Override
    public LongIndexer put(long i, long l) {
        this.array[(int)this.index((long)i)] = l;
        return this;
    }

    @Override
    public LongIndexer put(long i, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = l[offset + n];
        }
        return this;
    }

    @Override
    public LongIndexer put(long i, long j, long l) {
        this.array[(int)this.index((long)i, (long)j)] = l;
        return this;
    }

    @Override
    public LongIndexer put(long i, long j, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = l[offset + n];
        }
        return this;
    }

    @Override
    public LongIndexer put(long i, long j, long k, long l) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = l;
        return this;
    }

    @Override
    public LongIndexer put(long[] indices, long l) {
        this.array[(int)this.index((long[])indices)] = l;
        return this;
    }

    @Override
    public LongIndexer put(long[] indices, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = l[offset + n];
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

