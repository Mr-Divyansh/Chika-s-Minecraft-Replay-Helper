/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.math.BigInteger;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.ULongIndexer;

public class ULongArrayIndexer
extends ULongIndexer {
    protected long[] array;

    public ULongArrayIndexer(long[] array) {
        this(array, Index.create((long)array.length));
    }

    public ULongArrayIndexer(long[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public ULongArrayIndexer(long[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public ULongArrayIndexer(long[] array, Index index) {
        super(index);
        this.array = array;
    }

    public long[] array() {
        return this.array;
    }

    public ULongIndexer reindex(Index index) {
        return new ULongArrayIndexer(this.array, index);
    }

    @Override
    public BigInteger get(long i) {
        return ULongArrayIndexer.toBigInteger(this.array[(int)this.index(i)]);
    }

    @Override
    public ULongIndexer get(long i, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = ULongArrayIndexer.toBigInteger(this.array[(int)this.index(i) + n]);
        }
        return this;
    }

    @Override
    public BigInteger get(long i, long j) {
        return ULongArrayIndexer.toBigInteger(this.array[(int)this.index(i, j)]);
    }

    @Override
    public ULongIndexer get(long i, long j, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = ULongArrayIndexer.toBigInteger(this.array[(int)this.index(i, j) + n]);
        }
        return this;
    }

    @Override
    public BigInteger get(long i, long j, long k) {
        return ULongArrayIndexer.toBigInteger(this.array[(int)this.index(i, j, k)]);
    }

    @Override
    public BigInteger get(long ... indices) {
        return ULongArrayIndexer.toBigInteger(this.array[(int)this.index(indices)]);
    }

    @Override
    public ULongIndexer get(long[] indices, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = ULongArrayIndexer.toBigInteger(this.array[(int)this.index(indices) + n]);
        }
        return this;
    }

    @Override
    public ULongIndexer put(long i, BigInteger l) {
        this.array[(int)this.index((long)i)] = ULongArrayIndexer.fromBigInteger(l);
        return this;
    }

    @Override
    public ULongIndexer put(long i, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = ULongArrayIndexer.fromBigInteger(l[offset + n]);
        }
        return this;
    }

    @Override
    public ULongIndexer put(long i, long j, BigInteger l) {
        this.array[(int)this.index((long)i, (long)j)] = ULongArrayIndexer.fromBigInteger(l);
        return this;
    }

    @Override
    public ULongIndexer put(long i, long j, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = ULongArrayIndexer.fromBigInteger(l[offset + n]);
        }
        return this;
    }

    @Override
    public ULongIndexer put(long i, long j, long k, BigInteger l) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = ULongArrayIndexer.fromBigInteger(l);
        return this;
    }

    @Override
    public ULongIndexer put(long[] indices, BigInteger l) {
        this.array[(int)this.index((long[])indices)] = ULongArrayIndexer.fromBigInteger(l);
        return this;
    }

    @Override
    public ULongIndexer put(long[] indices, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = ULongArrayIndexer.fromBigInteger(l[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

