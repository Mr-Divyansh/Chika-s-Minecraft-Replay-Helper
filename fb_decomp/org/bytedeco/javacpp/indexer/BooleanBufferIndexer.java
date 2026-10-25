/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.bytedeco.javacpp.indexer.BooleanIndexer;
import org.bytedeco.javacpp.indexer.Index;

public class BooleanBufferIndexer
extends BooleanIndexer {
    protected ByteBuffer buffer;

    public BooleanBufferIndexer(ByteBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public BooleanBufferIndexer(ByteBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public BooleanBufferIndexer(ByteBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public BooleanBufferIndexer(ByteBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public BooleanIndexer reindex(Index index) {
        return new BooleanBufferIndexer(this.buffer, index);
    }

    @Override
    public boolean get(long i) {
        return this.buffer.get((int)this.index(i)) != 0;
    }

    @Override
    public BooleanIndexer get(long i, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.buffer.get((int)this.index(i) + n) != 0;
        }
        return this;
    }

    @Override
    public boolean get(long i, long j) {
        return this.buffer.get((int)this.index(i, j)) != 0;
    }

    @Override
    public BooleanIndexer get(long i, long j, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.buffer.get((int)this.index(i, j) + n) != 0;
        }
        return this;
    }

    @Override
    public boolean get(long i, long j, long k) {
        return this.buffer.get((int)this.index(i, j, k)) != 0;
    }

    @Override
    public boolean get(long ... indices) {
        return this.buffer.get((int)this.index(indices)) != 0;
    }

    @Override
    public BooleanIndexer get(long[] indices, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.buffer.get((int)this.index(indices) + n) != 0;
        }
        return this;
    }

    @Override
    public BooleanIndexer put(long i, boolean b) {
        this.buffer.put((int)this.index(i), b ? (byte)1 : 0);
        return this;
    }

    @Override
    public BooleanIndexer put(long i, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, b[offset + n] ? (byte)1 : 0);
        }
        return this;
    }

    @Override
    public BooleanIndexer put(long i, long j, boolean b) {
        this.buffer.put((int)this.index(i, j), b ? (byte)1 : 0);
        return this;
    }

    @Override
    public BooleanIndexer put(long i, long j, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, b[offset + n] ? (byte)1 : 0);
        }
        return this;
    }

    @Override
    public BooleanIndexer put(long i, long j, long k, boolean b) {
        this.buffer.put((int)this.index(i, j, k), b ? (byte)1 : 0);
        return this;
    }

    @Override
    public BooleanIndexer put(long[] indices, boolean b) {
        this.buffer.put((int)this.index(indices), b ? (byte)1 : 0);
        return this;
    }

    @Override
    public BooleanIndexer put(long[] indices, boolean[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, b[offset + n] ? (byte)1 : 0);
        }
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

