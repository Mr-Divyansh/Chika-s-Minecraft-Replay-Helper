/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.ShortIndexer;

public class ShortArrayIndexer
extends ShortIndexer {
    protected short[] array;

    public ShortArrayIndexer(short[] array) {
        this(array, Index.create((long)array.length));
    }

    public ShortArrayIndexer(short[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public ShortArrayIndexer(short[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public ShortArrayIndexer(short[] array, Index index) {
        super(index);
        this.array = array;
    }

    public short[] array() {
        return this.array;
    }

    public ShortIndexer reindex(Index index) {
        return new ShortArrayIndexer(this.array, index);
    }

    @Override
    public short get(long i) {
        return this.array[(int)this.index(i)];
    }

    @Override
    public ShortIndexer get(long i, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.array[(int)this.index(i) + n];
        }
        return this;
    }

    @Override
    public short get(long i, long j) {
        return this.array[(int)this.index(i, j)];
    }

    @Override
    public ShortIndexer get(long i, long j, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.array[(int)this.index(i, j) + n];
        }
        return this;
    }

    @Override
    public short get(long i, long j, long k) {
        return this.array[(int)this.index(i, j, k)];
    }

    @Override
    public short get(long ... indices) {
        return this.array[(int)this.index(indices)];
    }

    @Override
    public ShortIndexer get(long[] indices, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.array[(int)this.index(indices) + n];
        }
        return this;
    }

    @Override
    public ShortIndexer put(long i, short s) {
        this.array[(int)this.index((long)i)] = s;
        return this;
    }

    @Override
    public ShortIndexer put(long i, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = s[offset + n];
        }
        return this;
    }

    @Override
    public ShortIndexer put(long i, long j, short s) {
        this.array[(int)this.index((long)i, (long)j)] = s;
        return this;
    }

    @Override
    public ShortIndexer put(long i, long j, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = s[offset + n];
        }
        return this;
    }

    @Override
    public ShortIndexer put(long i, long j, long k, short s) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = s;
        return this;
    }

    @Override
    public ShortIndexer put(long[] indices, short s) {
        this.array[(int)this.index((long[])indices)] = s;
        return this;
    }

    @Override
    public ShortIndexer put(long[] indices, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = s[offset + n];
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

