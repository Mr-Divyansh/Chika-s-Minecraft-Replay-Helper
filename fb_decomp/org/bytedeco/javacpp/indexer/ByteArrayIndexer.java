/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.bytedeco.javacpp.indexer.ByteIndexer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Raw;

public class ByteArrayIndexer
extends ByteIndexer {
    protected static final Raw RAW = Raw.getInstance();
    protected ByteBuffer buffer;
    protected byte[] array;

    public ByteArrayIndexer(byte[] array) {
        this(array, Index.create((long)array.length));
    }

    public ByteArrayIndexer(byte[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public ByteArrayIndexer(byte[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public ByteArrayIndexer(byte[] array, Index index) {
        super(index);
        this.array = array;
    }

    public byte[] array() {
        return this.array;
    }

    public ByteIndexer reindex(Index index) {
        return new ByteArrayIndexer(this.array, index);
    }

    @Override
    public byte get(long i) {
        return this.array[(int)this.index(i)];
    }

    @Override
    public ByteIndexer get(long i, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.array[(int)this.index(i) + n];
        }
        return this;
    }

    @Override
    public byte get(long i, long j) {
        return this.array[(int)this.index(i, j)];
    }

    @Override
    public ByteIndexer get(long i, long j, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.array[(int)this.index(i, j) + n];
        }
        return this;
    }

    @Override
    public byte get(long i, long j, long k) {
        return this.array[(int)this.index(i, j, k)];
    }

    @Override
    public byte get(long ... indices) {
        return this.array[(int)this.index(indices)];
    }

    @Override
    public ByteIndexer get(long[] indices, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.array[(int)this.index(indices) + n];
        }
        return this;
    }

    @Override
    public ByteIndexer put(long i, byte b) {
        this.array[(int)this.index((long)i)] = b;
        return this;
    }

    @Override
    public ByteIndexer put(long i, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = b[offset + n];
        }
        return this;
    }

    @Override
    public ByteIndexer put(long i, long j, byte b) {
        this.array[(int)this.index((long)i, (long)j)] = b;
        return this;
    }

    @Override
    public ByteIndexer put(long i, long j, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = b[offset + n];
        }
        return this;
    }

    @Override
    public ByteIndexer put(long i, long j, long k, byte b) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = b;
        return this;
    }

    @Override
    public ByteIndexer put(long[] indices, byte b) {
        this.array[(int)this.index((long[])indices)] = b;
        return this;
    }

    @Override
    public ByteIndexer put(long[] indices, byte[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = b[offset + n];
        }
        return this;
    }

    ByteBuffer getBuffer() {
        if (this.buffer == null) {
            this.buffer = ByteBuffer.wrap(this.array).order(ByteOrder.nativeOrder());
        }
        return this.buffer;
    }

    @Override
    public byte getByte(long i) {
        return this.array[(int)i];
    }

    @Override
    public ByteIndexer putByte(long i, byte b) {
        this.array[(int)i] = b;
        return this;
    }

    @Override
    public short getShort(long i) {
        if (RAW != null) {
            return RAW.getShort(this.array, ByteArrayIndexer.checkIndex(i, this.array.length - 1));
        }
        return this.getBuffer().getShort((int)i);
    }

    @Override
    public ByteIndexer putShort(long i, short s) {
        if (RAW != null) {
            RAW.putShort(this.array, ByteArrayIndexer.checkIndex(i, this.array.length - 1), s);
        } else {
            this.getBuffer().putShort((int)i, s);
        }
        return this;
    }

    @Override
    public int getInt(long i) {
        if (RAW != null) {
            return RAW.getInt(this.array, ByteArrayIndexer.checkIndex(i, this.array.length - 3));
        }
        return this.getBuffer().getInt((int)i);
    }

    @Override
    public ByteIndexer putInt(long i, int j) {
        if (RAW != null) {
            RAW.putInt(this.array, ByteArrayIndexer.checkIndex(i, this.array.length - 3), j);
        } else {
            this.getBuffer().putInt((int)i, j);
        }
        return this;
    }

    @Override
    public long getLong(long i) {
        if (RAW != null) {
            return RAW.getLong(this.array, ByteArrayIndexer.checkIndex(i, this.array.length - 7));
        }
        return this.getBuffer().getLong((int)i);
    }

    @Override
    public ByteIndexer putLong(long i, long j) {
        if (RAW != null) {
            RAW.putLong(this.array, ByteArrayIndexer.checkIndex(i, this.array.length - 7), j);
        } else {
            this.getBuffer().putLong((int)i, j);
        }
        return this;
    }

    @Override
    public float getFloat(long i) {
        if (RAW != null) {
            return RAW.getFloat(this.array, ByteArrayIndexer.checkIndex(i, this.array.length - 3));
        }
        return this.getBuffer().getFloat((int)i);
    }

    @Override
    public ByteIndexer putFloat(long i, float f) {
        if (RAW != null) {
            RAW.putFloat(this.array, ByteArrayIndexer.checkIndex(i, this.array.length - 3), f);
        } else {
            this.getBuffer().putFloat((int)i, f);
        }
        return this;
    }

    @Override
    public double getDouble(long i) {
        if (RAW != null) {
            return RAW.getDouble(this.array, ByteArrayIndexer.checkIndex(i, this.array.length - 7));
        }
        return this.getBuffer().getDouble((int)i);
    }

    @Override
    public ByteIndexer putDouble(long i, double d) {
        if (RAW != null) {
            RAW.putDouble(this.array, ByteArrayIndexer.checkIndex(i, this.array.length - 7), d);
        } else {
            this.getBuffer().putDouble((int)i, d);
        }
        return this;
    }

    @Override
    public char getChar(long i) {
        if (RAW != null) {
            return RAW.getChar(this.array, ByteArrayIndexer.checkIndex(i, this.array.length - 1));
        }
        return this.getBuffer().getChar((int)i);
    }

    @Override
    public ByteIndexer putChar(long i, char c) {
        if (RAW != null) {
            RAW.putChar(this.array, ByteArrayIndexer.checkIndex(i, this.array.length - 1), c);
        } else {
            this.getBuffer().putChar((int)i, c);
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

