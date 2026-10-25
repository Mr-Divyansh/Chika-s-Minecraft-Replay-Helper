/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.indexer.ByteIndexer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Raw;

public class ByteRawIndexer
extends ByteIndexer {
    protected static final Raw RAW = Raw.getInstance();
    protected BytePointer pointer;
    final long base;
    final long size;

    public ByteRawIndexer(BytePointer pointer) {
        this(pointer, Index.create(pointer.limit() - pointer.position()));
    }

    public ByteRawIndexer(BytePointer pointer, long ... sizes) {
        this(pointer, sizes, ByteRawIndexer.strides(sizes));
    }

    public ByteRawIndexer(BytePointer pointer, long[] sizes, long[] strides) {
        this(pointer, Index.create(sizes, strides));
    }

    public ByteRawIndexer(BytePointer pointer, Index index) {
        super(index);
        this.pointer = pointer;
        this.base = pointer.address() + pointer.position();
        this.size = pointer.limit() - pointer.position();
    }

    @Override
    public Pointer pointer() {
        return this.pointer;
    }

    public ByteIndexer reindex(Index index) {
        return new ByteRawIndexer(this.pointer, index);
    }

    public byte getRaw(long i) {
        return RAW.getByte(this.base + ByteRawIndexer.checkIndex(i, this.size));
    }

    @Override
    public byte get(long i) {
        return this.getRaw(this.index(i));
    }

    @Override
    public ByteIndexer get(long i, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.getRaw(this.index(i) + (long)n);
        }
        return this;
    }

    @Override
    public byte get(long i, long j) {
        return this.getRaw(this.index(i, j));
    }

    @Override
    public ByteIndexer get(long i, long j, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.getRaw(this.index(i, j) + (long)n);
        }
        return this;
    }

    @Override
    public byte get(long i, long j, long k) {
        return this.getRaw(this.index(i, j, k));
    }

    @Override
    public byte get(long ... indices) {
        return this.getRaw(this.index(indices));
    }

    @Override
    public ByteIndexer get(long[] indices, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.getRaw(this.index(indices) + (long)n);
        }
        return this;
    }

    public ByteIndexer putRaw(long i, byte b) {
        RAW.putByte(this.base + ByteRawIndexer.checkIndex(i, this.size), b);
        return this;
    }

    @Override
    public ByteIndexer put(long i, byte b) {
        return this.putRaw(this.index(i), b);
    }

    @Override
    public ByteIndexer put(long i, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i) + (long)n, b[offset + n]);
        }
        return this;
    }

    @Override
    public ByteIndexer put(long i, long j, byte b) {
        this.putRaw(this.index(i, j), b);
        return this;
    }

    @Override
    public ByteIndexer put(long i, long j, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i, j) + (long)n, b[offset + n]);
        }
        return this;
    }

    @Override
    public ByteIndexer put(long i, long j, long k, byte b) {
        this.putRaw(this.index(i, j, k), b);
        return this;
    }

    @Override
    public ByteIndexer put(long[] indices, byte b) {
        this.putRaw(this.index(indices), b);
        return this;
    }

    @Override
    public ByteIndexer put(long[] indices, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(indices) + (long)n, b[offset + n]);
        }
        return this;
    }

    @Override
    public byte getByte(long i) {
        return RAW.getByte(this.base + ByteRawIndexer.checkIndex(i, this.size - 1L));
    }

    @Override
    public ByteIndexer putByte(long i, byte b) {
        RAW.putByte(this.base + ByteRawIndexer.checkIndex(i, this.size - 1L), b);
        return this;
    }

    @Override
    public short getShort(long i) {
        return RAW.getShort(this.base + ByteRawIndexer.checkIndex(i, this.size - 1L));
    }

    @Override
    public ByteIndexer putShort(long i, short s) {
        RAW.putShort(this.base + ByteRawIndexer.checkIndex(i, this.size - 1L), s);
        return this;
    }

    @Override
    public int getInt(long i) {
        return RAW.getInt(this.base + ByteRawIndexer.checkIndex(i, this.size - 3L));
    }

    @Override
    public ByteIndexer putInt(long i, int j) {
        RAW.putInt(this.base + ByteRawIndexer.checkIndex(i, this.size - 3L), j);
        return this;
    }

    @Override
    public long getLong(long i) {
        return RAW.getLong(this.base + ByteRawIndexer.checkIndex(i, this.size - 7L));
    }

    @Override
    public ByteIndexer putLong(long i, long j) {
        RAW.putLong(this.base + ByteRawIndexer.checkIndex(i, this.size - 7L), j);
        return this;
    }

    @Override
    public float getFloat(long i) {
        return RAW.getFloat(this.base + ByteRawIndexer.checkIndex(i, this.size - 3L));
    }

    @Override
    public ByteIndexer putFloat(long i, float f) {
        RAW.putFloat(this.base + ByteRawIndexer.checkIndex(i, this.size - 3L), f);
        return this;
    }

    @Override
    public double getDouble(long i) {
        return RAW.getDouble(this.base + ByteRawIndexer.checkIndex(i, this.size - 7L));
    }

    @Override
    public ByteIndexer putDouble(long i, double d) {
        RAW.putDouble(this.base + ByteRawIndexer.checkIndex(i, this.size - 7L), d);
        return this;
    }

    @Override
    public char getChar(long i) {
        return RAW.getChar(this.base + ByteRawIndexer.checkIndex(i, this.size - 1L));
    }

    @Override
    public ByteIndexer putChar(long i, char c) {
        RAW.putChar(this.base + ByteRawIndexer.checkIndex(i, this.size - 1L), c);
        return this;
    }

    @Override
    public void release() {
        this.pointer = null;
    }
}

