/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.math.BigInteger;
import java.nio.Buffer;
import java.nio.LongBuffer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.ULongIndexer;

public class ULongBufferIndexer
extends ULongIndexer {
    protected LongBuffer buffer;

    public ULongBufferIndexer(LongBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public ULongBufferIndexer(LongBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public ULongBufferIndexer(LongBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public ULongBufferIndexer(LongBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public ULongIndexer reindex(Index index) {
        return new ULongBufferIndexer(this.buffer, index);
    }

    @Override
    public BigInteger get(long i) {
        return ULongBufferIndexer.toBigInteger(this.buffer.get((int)this.index(i)));
    }

    @Override
    public ULongIndexer get(long i, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = ULongBufferIndexer.toBigInteger(this.buffer.get((int)this.index(i) + n));
        }
        return this;
    }

    @Override
    public BigInteger get(long i, long j) {
        return ULongBufferIndexer.toBigInteger(this.buffer.get((int)this.index(i, j)));
    }

    @Override
    public ULongIndexer get(long i, long j, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = ULongBufferIndexer.toBigInteger(this.buffer.get((int)this.index(i, j) + n));
        }
        return this;
    }

    @Override
    public BigInteger get(long i, long j, long k) {
        return ULongBufferIndexer.toBigInteger(this.buffer.get((int)this.index(i, j, k)));
    }

    @Override
    public BigInteger get(long ... indices) {
        return ULongBufferIndexer.toBigInteger(this.buffer.get((int)this.index(indices)));
    }

    @Override
    public ULongIndexer get(long[] indices, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = ULongBufferIndexer.toBigInteger(this.buffer.get((int)this.index(indices) + n));
        }
        return this;
    }

    @Override
    public ULongIndexer put(long i, BigInteger l) {
        this.buffer.put((int)this.index(i), ULongBufferIndexer.fromBigInteger(l));
        return this;
    }

    @Override
    public ULongIndexer put(long i, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, ULongBufferIndexer.fromBigInteger(l[offset + n]));
        }
        return this;
    }

    @Override
    public ULongIndexer put(long i, long j, BigInteger l) {
        this.buffer.put((int)this.index(i, j), ULongBufferIndexer.fromBigInteger(l));
        return this;
    }

    @Override
    public ULongIndexer put(long i, long j, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, ULongBufferIndexer.fromBigInteger(l[offset + n]));
        }
        return this;
    }

    @Override
    public ULongIndexer put(long i, long j, long k, BigInteger l) {
        this.buffer.put((int)this.index(i, j, k), ULongBufferIndexer.fromBigInteger(l));
        return this;
    }

    @Override
    public ULongIndexer put(long[] indices, BigInteger l) {
        this.buffer.put((int)this.index(indices), ULongBufferIndexer.fromBigInteger(l));
        return this;
    }

    @Override
    public ULongIndexer put(long[] indices, BigInteger[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, ULongBufferIndexer.fromBigInteger(l[offset + n]));
        }
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

