/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import java.nio.ShortBuffer;
import org.bytedeco.javacpp.indexer.Bfloat16Indexer;
import org.bytedeco.javacpp.indexer.Index;

public class Bfloat16BufferIndexer
extends Bfloat16Indexer {
    protected ShortBuffer buffer;

    public Bfloat16BufferIndexer(ShortBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public Bfloat16BufferIndexer(ShortBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public Bfloat16BufferIndexer(ShortBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public Bfloat16BufferIndexer(ShortBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public Bfloat16Indexer reindex(Index index) {
        return new Bfloat16BufferIndexer(this.buffer, index);
    }

    @Override
    public float get(long i) {
        return Bfloat16BufferIndexer.toFloat(this.buffer.get((int)this.index(i)));
    }

    @Override
    public Bfloat16Indexer get(long i, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = Bfloat16BufferIndexer.toFloat(this.buffer.get((int)this.index(i) + n));
        }
        return this;
    }

    @Override
    public float get(long i, long j) {
        return Bfloat16BufferIndexer.toFloat(this.buffer.get((int)this.index(i, j)));
    }

    @Override
    public Bfloat16Indexer get(long i, long j, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = Bfloat16BufferIndexer.toFloat(this.buffer.get((int)this.index(i, j) + n));
        }
        return this;
    }

    @Override
    public float get(long i, long j, long k) {
        return Bfloat16BufferIndexer.toFloat(this.buffer.get((int)this.index(i, j, k)));
    }

    @Override
    public float get(long ... indices) {
        return Bfloat16BufferIndexer.toFloat(this.buffer.get((int)this.index(indices)));
    }

    @Override
    public Bfloat16Indexer get(long[] indices, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = Bfloat16BufferIndexer.toFloat(this.buffer.get((int)this.index(indices) + n));
        }
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, float h) {
        this.buffer.put((int)this.index(i), (short)Bfloat16BufferIndexer.fromFloat(h));
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, (short)Bfloat16BufferIndexer.fromFloat(h[offset + n]));
        }
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, long j, float h) {
        this.buffer.put((int)this.index(i, j), (short)Bfloat16BufferIndexer.fromFloat(h));
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, long j, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, (short)Bfloat16BufferIndexer.fromFloat(h[offset + n]));
        }
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, long j, long k, float h) {
        this.buffer.put((int)this.index(i, j, k), (short)Bfloat16BufferIndexer.fromFloat(h));
        return this;
    }

    @Override
    public Bfloat16Indexer put(long[] indices, float h) {
        this.buffer.put((int)this.index(indices), (short)Bfloat16BufferIndexer.fromFloat(h));
        return this;
    }

    @Override
    public Bfloat16Indexer put(long[] indices, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, (short)Bfloat16BufferIndexer.fromFloat(h[offset + n]));
        }
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

