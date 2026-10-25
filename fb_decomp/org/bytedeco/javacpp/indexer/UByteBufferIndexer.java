/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.UByteIndexer;

public class UByteBufferIndexer
extends UByteIndexer {
    protected ByteBuffer buffer;

    public UByteBufferIndexer(ByteBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public UByteBufferIndexer(ByteBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public UByteBufferIndexer(ByteBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public UByteBufferIndexer(ByteBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public UByteIndexer reindex(Index index) {
        return new UByteBufferIndexer(this.buffer, index);
    }

    @Override
    public int get(long i) {
        return this.buffer.get((int)this.index(i)) & 0xFF;
    }

    @Override
    public UByteIndexer get(long i, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.buffer.get((int)this.index(i) + n) & 0xFF;
        }
        return this;
    }

    @Override
    public int get(long i, long j) {
        return this.buffer.get((int)this.index(i, j)) & 0xFF;
    }

    @Override
    public UByteIndexer get(long i, long j, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.buffer.get((int)this.index(i, j) + n) & 0xFF;
        }
        return this;
    }

    @Override
    public int get(long i, long j, long k) {
        return this.buffer.get((int)this.index(i, j, k)) & 0xFF;
    }

    @Override
    public int get(long ... indices) {
        return this.buffer.get((int)this.index(indices)) & 0xFF;
    }

    @Override
    public UByteIndexer get(long[] indices, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            b[offset + n] = this.buffer.get((int)this.index(indices) + n) & 0xFF;
        }
        return this;
    }

    @Override
    public UByteIndexer put(long i, int b) {
        this.buffer.put((int)this.index(i), (byte)b);
        return this;
    }

    @Override
    public UByteIndexer put(long i, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, (byte)b[offset + n]);
        }
        return this;
    }

    @Override
    public UByteIndexer put(long i, long j, int b) {
        this.buffer.put((int)this.index(i, j), (byte)b);
        return this;
    }

    @Override
    public UByteIndexer put(long i, long j, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, (byte)b[offset + n]);
        }
        return this;
    }

    @Override
    public UByteIndexer put(long i, long j, long k, int b) {
        this.buffer.put((int)this.index(i, j, k), (byte)b);
        return this;
    }

    @Override
    public UByteIndexer put(long[] indices, int b) {
        this.buffer.put((int)this.index(indices), (byte)b);
        return this;
    }

    @Override
    public UByteIndexer put(long[] indices, int[] b, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, (byte)b[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

