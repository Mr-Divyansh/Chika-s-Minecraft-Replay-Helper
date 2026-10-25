/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.UShortIndexer;

public class UShortArrayIndexer
extends UShortIndexer {
    protected short[] array;

    public UShortArrayIndexer(short[] array) {
        this(array, Index.create((long)array.length));
    }

    public UShortArrayIndexer(short[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public UShortArrayIndexer(short[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public UShortArrayIndexer(short[] array, Index index) {
        super(index);
        this.array = array;
    }

    public short[] array() {
        return this.array;
    }

    public UShortIndexer reindex(Index index) {
        return new UShortArrayIndexer(this.array, index);
    }

    @Override
    public int get(long i) {
        return this.array[(int)this.index(i)] & 0xFFFF;
    }

    @Override
    public UShortIndexer get(long i, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.array[(int)this.index(i) + n] & 0xFFFF;
        }
        return this;
    }

    @Override
    public int get(long i, long j) {
        return this.array[(int)this.index(i, j)] & 0xFFFF;
    }

    @Override
    public UShortIndexer get(long i, long j, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.array[(int)this.index(i, j) + n] & 0xFFFF;
        }
        return this;
    }

    @Override
    public int get(long i, long j, long k) {
        return this.array[(int)this.index(i, j, k)] & 0xFFFF;
    }

    @Override
    public int get(long ... indices) {
        return this.array[(int)this.index(indices)] & 0xFFFF;
    }

    @Override
    public UShortIndexer get(long[] indices, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.array[(int)this.index(indices) + n] & 0xFFFF;
        }
        return this;
    }

    @Override
    public UShortIndexer put(long i, int s) {
        this.array[(int)this.index((long)i)] = (short)s;
        return this;
    }

    @Override
    public UShortIndexer put(long i, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = (short)s[offset + n];
        }
        return this;
    }

    @Override
    public UShortIndexer put(long i, long j, int s) {
        this.array[(int)this.index((long)i, (long)j)] = (short)s;
        return this;
    }

    @Override
    public UShortIndexer put(long i, long j, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = (short)s[offset + n];
        }
        return this;
    }

    @Override
    public UShortIndexer put(long i, long j, long k, int s) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = (short)s;
        return this;
    }

    @Override
    public UShortIndexer put(long[] indices, int s) {
        this.array[(int)this.index((long[])indices)] = (short)s;
        return this;
    }

    @Override
    public UShortIndexer put(long[] indices, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = (short)s[offset + n];
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

