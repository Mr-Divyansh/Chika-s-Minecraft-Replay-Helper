/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Raw;
import org.bytedeco.javacpp.indexer.UByteIndexer;

public class UByteRawIndexer
extends UByteIndexer {
    protected static final Raw RAW = Raw.getInstance();
    protected BytePointer pointer;
    final long base;
    final long size;

    public UByteRawIndexer(BytePointer pointer) {
        this(pointer, Index.create(pointer.limit() - pointer.position()));
    }

    public UByteRawIndexer(BytePointer pointer, long ... sizes) {
        this(pointer, sizes, UByteRawIndexer.strides(sizes));
    }

    public UByteRawIndexer(BytePointer pointer, long[] sizes, long[] strides) {
        this(pointer, Index.create(sizes, strides));
    }

    public UByteRawIndexer(BytePointer pointer, Index index) {
        super(index);
        this.pointer = pointer;
        this.base = pointer.address() + pointer.position();
        this.size = pointer.limit() - pointer.position();
    }

    @Override
    public Pointer pointer() {
        return this.pointer;
    }

    public UByteIndexer reindex(Index index) {
        return new UByteRawIndexer(this.pointer, index);
    }

    public int getRaw(long i) {
        return RAW.getByte(this.base + UByteRawIndexer.checkIndex(i, this.size)) & 0xFF;
    }

    @Override
    public int get(long i) {
        return this.getRaw(this.index(i));
    }

    @Override
    public UByteIndexer get(long i, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.getRaw(this.index(i) + (long)n) & 0xFF;
        }
        return this;
    }

    @Override
    public int get(long i, long j) {
        return this.getRaw(this.index(i, j)) & 0xFF;
    }

    @Override
    public UByteIndexer get(long i, long j, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.getRaw(this.index(i, j) + (long)n) & 0xFF;
        }
        return this;
    }

    @Override
    public int get(long i, long j, long k) {
        return this.getRaw(this.index(i, j, k)) & 0xFF;
    }

    @Override
    public int get(long ... indices) {
        return this.getRaw(this.index(indices)) & 0xFF;
    }

    @Override
    public UByteIndexer get(long[] indices, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.getRaw(this.index(indices) + (long)n) & 0xFF;
        }
        return this;
    }

    public UByteIndexer putRaw(long i, int b) {
        RAW.putByte(this.base + UByteRawIndexer.checkIndex(i, this.size), (byte)b);
        return this;
    }

    @Override
    public UByteIndexer put(long i, int b) {
        this.putRaw(this.index(i), b);
        return this;
    }

    @Override
    public UByteIndexer put(long i, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i) + (long)n, b[offset + n]);
        }
        return this;
    }

    @Override
    public UByteIndexer put(long i, long j, int b) {
        this.putRaw(this.index(i, j), b);
        return this;
    }

    @Override
    public UByteIndexer put(long i, long j, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i, j) + (long)n, b[offset + n]);
        }
        return this;
    }

    @Override
    public UByteIndexer put(long i, long j, long k, int b) {
        this.putRaw(this.index(i, j, k), b);
        return this;
    }

    @Override
    public UByteIndexer put(long[] indices, int b) {
        this.putRaw(this.index(indices), b);
        return this;
    }

    @Override
    public UByteIndexer put(long[] indices, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(indices) + (long)n, b[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.pointer = null;
    }
}

