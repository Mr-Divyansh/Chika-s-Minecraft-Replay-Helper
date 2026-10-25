/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import java.nio.ShortBuffer;
import org.bytedeco.javacpp.indexer.HalfIndexer;
import org.bytedeco.javacpp.indexer.Index;

public class HalfBufferIndexer
extends HalfIndexer {
    protected ShortBuffer buffer;

    public HalfBufferIndexer(ShortBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public HalfBufferIndexer(ShortBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public HalfBufferIndexer(ShortBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public HalfBufferIndexer(ShortBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public HalfIndexer reindex(Index index) {
        return new HalfBufferIndexer(this.buffer, index);
    }

    @Override
    public float get(long i) {
        return HalfBufferIndexer.toFloat(this.buffer.get((int)this.index(i)));
    }

    @Override
    public HalfIndexer get(long i, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = HalfBufferIndexer.toFloat(this.buffer.get((int)this.index(i) + n));
        }
        return this;
    }

    @Override
    public float get(long i, long j) {
        return HalfBufferIndexer.toFloat(this.buffer.get((int)this.index(i, j)));
    }

    @Override
    public HalfIndexer get(long i, long j, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = HalfBufferIndexer.toFloat(this.buffer.get((int)this.index(i, j) + n));
        }
        return this;
    }

    @Override
    public float get(long i, long j, long k) {
        return HalfBufferIndexer.toFloat(this.buffer.get((int)this.index(i, j, k)));
    }

    @Override
    public float get(long ... indices) {
        return HalfBufferIndexer.toFloat(this.buffer.get((int)this.index(indices)));
    }

    @Override
    public HalfIndexer get(long[] indices, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = HalfBufferIndexer.toFloat(this.buffer.get((int)this.index(indices) + n));
        }
        return this;
    }

    @Override
    public HalfIndexer put(long i, float h) {
        this.buffer.put((int)this.index(i), (short)HalfBufferIndexer.fromFloat(h));
        return this;
    }

    @Override
    public HalfIndexer put(long i, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, (short)HalfBufferIndexer.fromFloat(h[offset + n]));
        }
        return this;
    }

    @Override
    public HalfIndexer put(long i, long j, float h) {
        this.buffer.put((int)this.index(i, j), (short)HalfBufferIndexer.fromFloat(h));
        return this;
    }

    @Override
    public HalfIndexer put(long i, long j, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, (short)HalfBufferIndexer.fromFloat(h[offset + n]));
        }
        return this;
    }

    @Override
    public HalfIndexer put(long i, long j, long k, float h) {
        this.buffer.put((int)this.index(i, j, k), (short)HalfBufferIndexer.fromFloat(h));
        return this;
    }

    @Override
    public HalfIndexer put(long[] indices, float h) {
        this.buffer.put((int)this.index(indices), (short)HalfBufferIndexer.fromFloat(h));
        return this;
    }

    @Override
    public HalfIndexer put(long[] indices, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, (short)HalfBufferIndexer.fromFloat(h[offset + n]));
        }
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

