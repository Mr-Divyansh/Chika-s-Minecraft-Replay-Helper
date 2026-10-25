/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.BooleanIndexer;
import org.bytedeco.javacpp.indexer.Index;

public class BooleanArrayIndexer
extends BooleanIndexer {
    protected boolean[] array;

    public BooleanArrayIndexer(boolean[] array) {
        this(array, Index.create((long)array.length));
    }

    public BooleanArrayIndexer(boolean[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public BooleanArrayIndexer(boolean[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public BooleanArrayIndexer(boolean[] array, Index index) {
        super(index);
        this.array = array;
    }

    public boolean[] array() {
        return this.array;
    }

    public BooleanIndexer reindex(Index index) {
        return new BooleanArrayIndexer(this.array, index);
    }

    @Override
    public boolean get(long i) {
        return this.array[(int)this.index(i)];
    }

    @Override
    public BooleanIndexer get(long i, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.array[(int)this.index(i) + n];
        }
        return this;
    }

    @Override
    public boolean get(long i, long j) {
        return this.array[(int)this.index(i, j)];
    }

    @Override
    public BooleanIndexer get(long i, long j, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.array[(int)this.index(i, j) + n];
        }
        return this;
    }

    @Override
    public boolean get(long i, long j, long k) {
        return this.array[(int)this.index(i, j, k)];
    }

    @Override
    public boolean get(long ... indices) {
        return this.array[(int)this.index(indices)];
    }

    @Override
    public BooleanIndexer get(long[] indices, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.array[(int)this.index(indices) + n];
        }
        return this;
    }

    @Override
    public BooleanIndexer put(long i, boolean b) {
        this.array[(int)this.index((long)i)] = b;
        return this;
    }

    @Override
    public BooleanIndexer put(long i, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = b[offset + n];
        }
        return this;
    }

    @Override
    public BooleanIndexer put(long i, long j, boolean b) {
        this.array[(int)this.index((long)i, (long)j)] = b;
        return this;
    }

    @Override
    public BooleanIndexer put(long i, long j, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = b[offset + n];
        }
        return this;
    }

    @Override
    public BooleanIndexer put(long i, long j, long k, boolean b) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = b;
        return this;
    }

    @Override
    public BooleanIndexer put(long[] indices, boolean b) {
        this.array[(int)this.index((long[])indices)] = b;
        return this;
    }

    @Override
    public BooleanIndexer put(long[] indices, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = b[offset + n];
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

