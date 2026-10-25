/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import java.nio.CharBuffer;
import org.bytedeco.javacpp.indexer.CharIndexer;
import org.bytedeco.javacpp.indexer.Index;

public class CharBufferIndexer
extends CharIndexer {
    protected CharBuffer buffer;

    public CharBufferIndexer(CharBuffer buffer) {
        this(buffer, Index.create((long)buffer.limit()));
    }

    public CharBufferIndexer(CharBuffer buffer, long ... sizes) {
        this(buffer, Index.create(sizes));
    }

    public CharBufferIndexer(CharBuffer buffer, long[] sizes, long[] strides) {
        this(buffer, Index.create(sizes, strides));
    }

    public CharBufferIndexer(CharBuffer buffer, Index index) {
        super(index);
        this.buffer = buffer;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    public CharIndexer reindex(Index index) {
        return new CharBufferIndexer(this.buffer, index);
    }

    @Override
    public char get(long i) {
        return this.buffer.get((int)this.index(i));
    }

    @Override
    public CharIndexer get(long i, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            c[offset + n] = this.buffer.get((int)this.index(i) + n);
        }
        return this;
    }

    @Override
    public char get(long i, long j) {
        return this.buffer.get((int)this.index(i, j));
    }

    @Override
    public CharIndexer get(long i, long j, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            c[offset + n] = this.buffer.get((int)this.index(i, j) + n);
        }
        return this;
    }

    @Override
    public char get(long i, long j, long k) {
        return this.buffer.get((int)this.index(i, j, k));
    }

    @Override
    public char get(long ... indices) {
        return this.buffer.get((int)this.index(indices));
    }

    @Override
    public CharIndexer get(long[] indices, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            c[offset + n] = this.buffer.get((int)this.index(indices) + n);
        }
        return this;
    }

    @Override
    public CharIndexer put(long i, char c) {
        this.buffer.put((int)this.index(i), c);
        return this;
    }

    @Override
    public CharIndexer put(long i, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i) + n, c[offset + n]);
        }
        return this;
    }

    @Override
    public CharIndexer put(long i, long j, char c) {
        this.buffer.put((int)this.index(i, j), c);
        return this;
    }

    @Override
    public CharIndexer put(long i, long j, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(i, j) + n, c[offset + n]);
        }
        return this;
    }

    @Override
    public CharIndexer put(long i, long j, long k, char c) {
        this.buffer.put((int)this.index(i, j, k), c);
        return this;
    }

    @Override
    public CharIndexer put(long[] indices, char c) {
        this.buffer.put((int)this.index(indices), c);
        return this;
    }

    @Override
    public CharIndexer put(long[] indices, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.buffer.put((int)this.index(indices) + n, c[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.buffer = null;
    }
}

