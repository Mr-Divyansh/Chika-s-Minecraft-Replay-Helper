/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import java.nio.LongBuffer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.LongIndexer;

public class LongBufferIndexer
extends LongIndexer {
    protected LongBuffer buffer;

    public LongBufferIndexer(LongBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public LongBufferIndexer(LongBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public LongBufferIndexer(LongBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public LongBufferIndexer(LongBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public LongIndexer reindex(Index index) {
        return new LongBufferIndexer(this.buffer, index);
    }

    @Override
    public long get(long i) {
        return this.buffer.get((int)this.index(i));
    }

    @Override
    public LongIndexer get(long i, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = this.buffer.get((int)this.index(i) + n);
        }
        return this;
    }

    @Override
    public long get(long i, long j) {
        return this.buffer.get((int)this.index(i, j));
    }

    @Override
    public LongIndexer get(long i, long j, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = this.buffer.get((int)this.index(i, j) + n);
        }
        return this;
    }

    @Override
    public long get(long i, long j, long k) {
        return this.buffer.get((int)this.index(i, j, k));
    }

    @Override
    public long get(long ... indices) {
        return this.buffer.get((int)this.index(indices));
    }

    @Override
    public LongIndexer get(long[] indices, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            l[offset + n] = this.buffer.get((int)this.index(indices) + n);
        }
        return this;
    }

    @Override
    public LongIndexer put(long i, long l) {
        this.buffer.put((int)this.index(i), l);
        return this;
    }

    @Override
    public LongIndexer put(long i, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, l[offset + n]);
        }
        return this;
    }

    @Override
    public LongIndexer put(long i, long j, long l) {
        this.buffer.put((int)this.index(i, j), l);
        return this;
    }

    @Override
    public LongIndexer put(long i, long j, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, l[offset + n]);
        }
        return this;
    }

    @Override
    public LongIndexer put(long i, long j, long k, long l) {
        this.buffer.put((int)this.index(i, j, k), l);
        return this;
    }

    @Override
    public LongIndexer put(long[] indices, long l) {
        this.buffer.put((int)this.index(indices), l);
        return this;
    }

    @Override
    public LongIndexer put(long[] indices, long[] l, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, l[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

