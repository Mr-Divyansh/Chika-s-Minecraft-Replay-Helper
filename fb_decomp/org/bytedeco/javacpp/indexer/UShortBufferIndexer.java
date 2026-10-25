/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import java.nio.ShortBuffer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.UShortIndexer;

public class UShortBufferIndexer
extends UShortIndexer {
    protected ShortBuffer buffer;

    public UShortBufferIndexer(ShortBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public UShortBufferIndexer(ShortBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public UShortBufferIndexer(ShortBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public UShortBufferIndexer(ShortBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public UShortIndexer reindex(Index index) {
        return new UShortBufferIndexer(this.buffer, index);
    }

    @Override
    public int get(long i) {
        return this.buffer.get((int)this.index(i)) & 0xFFFF;
    }

    @Override
    public UShortIndexer get(long i, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.buffer.get((int)this.index(i) + n) & 0xFFFF;
        }
        return this;
    }

    @Override
    public int get(long i, long j) {
        return this.buffer.get((int)this.index(i, j)) & 0xFFFF;
    }

    @Override
    public UShortIndexer get(long i, long j, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.buffer.get((int)this.index(i, j) + n) & 0xFFFF;
        }
        return this;
    }

    @Override
    public int get(long i, long j, long k) {
        return this.buffer.get((int)this.index(i, j, k)) & 0xFFFF;
    }

    @Override
    public int get(long ... indices) {
        return this.buffer.get((int)this.index(indices)) & 0xFFFF;
    }

    @Override
    public UShortIndexer get(long[] indices, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.buffer.get((int)this.index(indices) + n) & 0xFFFF;
        }
        return this;
    }

    @Override
    public UShortIndexer put(long i, int s) {
        this.buffer.put((int)this.index(i), (short)s);
        return this;
    }

    @Override
    public UShortIndexer put(long i, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, (short)s[offset + n]);
        }
        return this;
    }

    @Override
    public UShortIndexer put(long i, long j, int s) {
        this.buffer.put((int)this.index(i, j), (short)s);
        return this;
    }

    @Override
    public UShortIndexer put(long i, long j, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, (short)s[offset + n]);
        }
        return this;
    }

    @Override
    public UShortIndexer put(long i, long j, long k, int s) {
        this.buffer.put((int)this.index(i, j, k), (short)s);
        return this;
    }

    @Override
    public UShortIndexer put(long[] indices, int s) {
        this.buffer.put((int)this.index(indices), (short)s);
        return this;
    }

    @Override
    public UShortIndexer put(long[] indices, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, (short)s[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

