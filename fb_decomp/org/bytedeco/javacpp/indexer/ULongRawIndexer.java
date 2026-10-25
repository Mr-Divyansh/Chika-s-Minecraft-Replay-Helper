/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.math.BigInteger;
import org.bytedeco.javacpp.LongPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Raw;
import org.bytedeco.javacpp.indexer.ULongIndexer;

public class ULongRawIndexer
extends ULongIndexer {
    protected static final Raw RAW = Raw.getInstance();
    protected LongPointer pointer;
    final long base;
    final long size;

    public ULongRawIndexer(LongPointer pointer) {
        this(pointer, Index.create(pointer.limit() - pointer.position()));
    }

    public ULongRawIndexer(LongPointer pointer, long ... sizes) {
        this(pointer, Index.create(sizes));
    }

    public ULongRawIndexer(LongPointer pointer, long[] sizes, long[] strides) {
        this(pointer, Index.create(sizes, strides));
    }

    public ULongRawIndexer(LongPointer pointer, Index index) {
        super(index);
        this.pointer = pointer;
        this.base = pointer.address() + pointer.position() * 8L;
        this.size = pointer.limit() - pointer.position();
    }

    @Override
    public Pointer pointer() {
        return this.pointer;
    }

    public ULongIndexer reindex(Index index) {
        return new ULongRawIndexer(this.pointer, index);
    }

    public BigInteger getRaw(long i) {
        return ULongRawIndexer.toBigInteger(RAW.getLong(this.base + ULongRawIndexer.checkIndex(i, this.size) * 8L));
    }

    @Override
    public BigInteger get(long i) {
        return this.getRaw(this.index(i));
    }

    @Override
    public ULongIndexer get(long i, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = this.getRaw(this.index(i) + (long)n);
        }
        return this;
    }

    @Override
    public BigInteger get(long i, long j) {
        return this.getRaw(this.index(i, j));
    }

    @Override
    public ULongIndexer get(long i, long j, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = this.getRaw(this.index(i, j) + (long)n);
        }
        return this;
    }

    @Override
    public BigInteger get(long i, long j, long k) {
        return this.getRaw(this.index(i, j, k));
    }

    @Override
    public BigInteger get(long ... indices) {
        return this.getRaw(this.index(indices));
    }

    @Override
    public ULongIndexer get(long[] indices, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = this.getRaw(this.index(indices) + (long)n);
        }
        return this;
    }

    public ULongIndexer putRaw(long i, BigInteger l) {
        RAW.putLong(this.base + ULongRawIndexer.checkIndex(i, this.size) * 8L, ULongRawIndexer.fromBigInteger(l));
        return this;
    }

    @Override
    public ULongIndexer put(long i, BigInteger l) {
        return this.putRaw(this.index(i), l);
    }

    @Override
    public ULongIndexer put(long i, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i) + (long)n, l[offset + n]);
        }
        return this;
    }

    @Override
    public ULongIndexer put(long i, long j, BigInteger l) {
        this.putRaw(this.index(i, j), l);
        return this;
    }

    @Override
    public ULongIndexer put(long i, long j, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i, j) + (long)n, l[offset + n]);
        }
        return this;
    }

    @Override
    public ULongIndexer put(long i, long j, long k, BigInteger l) {
        this.putRaw(this.index(i, j, k), l);
        return this;
    }

    @Override
    public ULongIndexer put(long[] indices, BigInteger l) {
        this.putRaw(this.index(indices), l);
        return this;
    }

    @Override
    public ULongIndexer put(long[] indices, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(indices) + (long)n, l[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.pointer = null;
    }
}

