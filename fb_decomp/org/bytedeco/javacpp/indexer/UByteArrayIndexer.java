/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.UByteIndexer;

public class UByteArrayIndexer
extends UByteIndexer {
    protected byte[] array;

    public UByteArrayIndexer(byte[] array) {
        this(array, Index.create((long)array.length));
    }

    public UByteArrayIndexer(byte[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public UByteArrayIndexer(byte[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public UByteArrayIndexer(byte[] array, Index index) {
        super(index);
        this.array = array;
    }

    public byte[] array() {
        return this.array;
    }

    public UByteIndexer reindex(Index index) {
        return new UByteArrayIndexer(this.array, index);
    }

    @Override
    public int get(long i) {
        return this.array[(int)this.index(i)] & 0xFF;
    }

    @Override
    public UByteIndexer get(long i, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.array[(int)this.index(i) + n] & 0xFF;
        }
        return this;
    }

    @Override
    public int get(long i, long j) {
        return this.array[(int)this.index(i, j)] & 0xFF;
    }

    @Override
    public UByteIndexer get(long i, long j, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.array[(int)this.index(i, j) + n] & 0xFF;
        }
        return this;
    }

    @Override
    public int get(long i, long j, long k) {
        return this.array[(int)this.index(i, j, k)] & 0xFF;
    }

    @Override
    public int get(long ... indices) {
        return this.array[(int)this.index(indices)] & 0xFF;
    }

    @Override
    public UByteIndexer get(long[] indices, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.array[(int)this.index(indices) + n] & 0xFF;
        }
        return this;
    }

    @Override
    public UByteIndexer put(long i, int b) {
        this.array[(int)this.index((long)i)] = (byte)b;
        return this;
    }

    @Override
    public UByteIndexer put(long i, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = (byte)b[offset + n];
        }
        return this;
    }

    @Override
    public UByteIndexer put(long i, long j, int b) {
        this.array[(int)this.index((long)i, (long)j)] = (byte)b;
        return this;
    }

    @Override
    public UByteIndexer put(long i, long j, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = (byte)b[offset + n];
        }
        return this;
    }

    @Override
    public UByteIndexer put(long i, long j, long k, int b) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = (byte)b;
        return this;
    }

    @Override
    public UByteIndexer put(long[] indices, int b) {
        this.array[(int)this.index((long[])indices)] = (byte)b;
        return this;
    }

    @Override
    public UByteIndexer put(long[] indices, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = (byte)b[offset + n];
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

