/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import java.nio.DoubleBuffer;
import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacpp.indexer.Index;

public class DoubleBufferIndexer
extends DoubleIndexer {
    protected DoubleBuffer buffer;

    public DoubleBufferIndexer(DoubleBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public DoubleBufferIndexer(DoubleBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public DoubleBufferIndexer(DoubleBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public DoubleBufferIndexer(DoubleBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public DoubleIndexer reindex(Index index) {
        return new DoubleBufferIndexer(this.buffer, index);
    }

    @Override
    public double get(long i) {
        return this.buffer.get((int)this.index(i));
    }

    @Override
    public DoubleIndexer get(long i, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            d[offset + n] = this.buffer.get((int)this.index(i) + n);
        }
        return this;
    }

    @Override
    public double get(long i, long j) {
        return this.buffer.get((int)this.index(i, j));
    }

    @Override
    public DoubleIndexer get(long i, long j, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            d[offset + n] = this.buffer.get((int)this.index(i, j) + n);
        }
        return this;
    }

    @Override
    public double get(long i, long j, long k) {
        return this.buffer.get((int)this.index(i, j, k));
    }

    @Override
    public double get(long ... indices) {
        return this.buffer.get((int)this.index(indices));
    }

    @Override
    public DoubleIndexer get(long[] indices, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            d[offset + n] = this.buffer.get((int)this.index(indices) + n);
        }
        return this;
    }

    @Override
    public DoubleIndexer put(long i, double d) {
        this.buffer.put((int)this.index(i), d);
        return this;
    }

    @Override
    public DoubleIndexer put(long i, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, d[offset + n]);
        }
        return this;
    }

    @Override
    public DoubleIndexer put(long i, long j, double d) {
        this.buffer.put((int)this.index(i, j), d);
        return this;
    }

    @Override
    public DoubleIndexer put(long i, long j, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, d[offset + n]);
        }
        return this;
    }

    @Override
    public DoubleIndexer put(long i, long j, long k, double d) {
        this.buffer.put((int)this.index(i, j, k), d);
        return this;
    }

    @Override
    public DoubleIndexer put(long[] indices, double d) {
        this.buffer.put((int)this.index(indices), d);
        return this;
    }

    @Override
    public DoubleIndexer put(long[] indices, double[] d, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, d[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

