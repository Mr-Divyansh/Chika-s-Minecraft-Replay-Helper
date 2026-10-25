/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.bytedeco.javacpp.indexer.ByteIndexer;
import org.bytedeco.javacpp.indexer.Index;

public class ByteBufferIndexer
extends ByteIndexer {
    protected ByteBuffer buffer;

    public ByteBufferIndexer(ByteBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public ByteBufferIndexer(ByteBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public ByteBufferIndexer(ByteBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public ByteBufferIndexer(ByteBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public ByteIndexer reindex(Index index) {
        return new ByteBufferIndexer(this.buffer, index);
    }

    @Override
    public byte get(long i) {
        return this.buffer.get((int)this.index(i));
    }

    @Override
    public ByteIndexer get(long i, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.buffer.get((int)this.index(i) + n);
        }
        return this;
    }

    @Override
    public byte get(long i, long j) {
        return this.buffer.get((int)this.index(i, j));
    }

    @Override
    public ByteIndexer get(long i, long j, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.buffer.get((int)this.index(i, j) + n);
        }
        return this;
    }

    @Override
    public byte get(long i, long j, long k) {
        return this.buffer.get((int)this.index(i, j, k));
    }

    @Override
    public byte get(long ... indices) {
        return this.buffer.get((int)this.index(indices));
    }

    @Override
    public ByteIndexer get(long[] indices, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.buffer.get((int)this.index(indices) + n);
        }
        return this;
    }

    @Override
    public ByteIndexer put(long i, byte b) {
        this.buffer.put((int)this.index(i), b);
        return this;
    }

    @Override
    public ByteIndexer put(long i, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, b[offset + n]);
        }
        return this;
    }

    @Override
    public ByteIndexer put(long i, long j, byte b) {
        this.buffer.put((int)this.index(i, j), b);
        return this;
    }

    @Override
    public ByteIndexer put(long i, long j, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, b[offset + n]);
        }
        return this;
    }

    @Override
    public ByteIndexer put(long i, long j, long k, byte b) {
        this.buffer.put((int)this.index(i, j, k), b);
        return this;
    }

    @Override
    public ByteIndexer put(long[] indices, byte b) {
        this.buffer.put((int)this.index(indices), b);
        return this;
    }

    @Override
    public ByteIndexer put(long[] indices, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, b[offset + n]);
        }
        return this;
    }

    @Override
    public byte getByte(long i) {
        return this.buffer.get((int)i);
    }

    @Override
    public ByteIndexer putByte(long i, byte b) {
        this.buffer.put((int)i, b);
        return this;
    }

    @Override
    public short getShort(long i) {
        return this.buffer.getShort((int)i);
    }

    @Override
    public ByteIndexer putShort(long i, short s) {
        this.buffer.putShort((int)i, s);
        return this;
    }

    @Override
    public int getInt(long i) {
        return this.buffer.getInt((int)i);
    }

    @Override
    public ByteIndexer putInt(long i, int j) {
        this.buffer.putInt((int)i, j);
        return this;
    }

    @Override
    public long getLong(long i) {
        return this.buffer.getLong((int)i);
    }

    @Override
    public ByteIndexer putLong(long i, long j) {
        this.buffer.putLong((int)i, j);
        return this;
    }

    @Override
    public float getFloat(long i) {
        return this.buffer.getFloat((int)i);
    }

    @Override
    public ByteIndexer putFloat(long i, float f) {
        this.buffer.putFloat((int)i, f);
        return this;
    }

    @Override
    public double getDouble(long i) {
        return this.buffer.getDouble((int)i);
    }

    @Override
    public ByteIndexer putDouble(long i, double d) {
        this.buffer.putDouble((int)i, d);
        return this;
    }

    @Override
    public char getChar(long i) {
        return this.buffer.getChar((int)i);
    }

    @Override
    public ByteIndexer putChar(long i, char c) {
        this.buffer.putChar((int)i, c);
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

