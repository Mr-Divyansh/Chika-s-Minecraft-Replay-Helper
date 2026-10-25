/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.javacpp.indexer.Index;

public class FloatBufferIndexer
extends FloatIndexer {
    protected FloatBuffer buffer;

    public FloatBufferIndexer(FloatBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public FloatBufferIndexer(FloatBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public FloatBufferIndexer(FloatBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public FloatBufferIndexer(FloatBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public FloatIndexer reindex(Index index) {
        return new FloatBufferIndexer(this.buffer, index);
    }

    @Override
    public float get(long i) {
        return this.buffer.get((int)this.index(i));
    }

    @Override
    public FloatIndexer get(long i, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            f[offset + n] = this.buffer.get((int)this.index(i) + n);
        }
        return this;
    }

    @Override
    public float get(long i, long j) {
        return this.buffer.get((int)this.index(i, j));
    }

    @Override
    public FloatIndexer get(long i, long j, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            f[offset + n] = this.buffer.get((int)this.index(i, j) + n);
        }
        return this;
    }

    @Override
    public float get(long i, long j, long k) {
        return this.buffer.get((int)this.index(i, j, k));
    }

    @Override
    public float get(long ... indices) {
        return this.buffer.get((int)this.index(indices));
    }

    @Override
    public FloatIndexer get(long[] indices, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            f[offset + n] = this.buffer.get((int)this.index(indices) + n);
        }
        return this;
    }

    @Override
    public FloatIndexer put(long i, float f) {
        this.buffer.put((int)this.index(i), f);
        return this;
    }

    @Override
    public FloatIndexer put(long i, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, f[offset + n]);
        }
        return this;
    }

    @Override
    public FloatIndexer put(long i, long j, float f) {
        this.buffer.put((int)this.index(i, j), f);
        return this;
    }

    @Override
    public FloatIndexer put(long i, long j, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, f[offset + n]);
        }
        return this;
    }

    @Override
    public FloatIndexer put(long i, long j, long k, float f) {
        this.buffer.put((int)this.index(i, j, k), f);
        return this;
    }

    @Override
    public FloatIndexer put(long[] indices, float f) {
        this.buffer.put((int)this.index(indices), f);
        return this;
    }

    @Override
    public FloatIndexer put(long[] indices, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, f[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

