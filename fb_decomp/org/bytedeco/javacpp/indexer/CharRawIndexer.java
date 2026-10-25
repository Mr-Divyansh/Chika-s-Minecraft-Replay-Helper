/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.CharPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.indexer.CharIndexer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Raw;

public class CharRawIndexer
extends CharIndexer {
    protected static final Raw RAW = Raw.getInstance();
    protected CharPointer pointer;
    final long base;
    final long size;

    public CharRawIndexer(CharPointer pointer) {
        this(pointer, Index.create(pointer.limit() - pointer.position()));
    }

    public CharRawIndexer(CharPointer pointer, long ... sizes) {
        this(pointer, Index.create(sizes));
    }

    public CharRawIndexer(CharPointer pointer, long[] sizes, long[] strides) {
        this(pointer, Index.create(sizes, strides));
    }

    public CharRawIndexer(CharPointer pointer, Index index) {
        super(index);
        this.pointer = pointer;
        this.base = pointer.address() + pointer.position() * 2L;
        this.size = pointer.limit() - pointer.position();
    }

    @Override
    public Pointer pointer() {
        return this.pointer;
    }

    public CharIndexer reindex(Index index) {
        return new CharRawIndexer(this.pointer, index);
    }

    public char getRaw(long i) {
        return RAW.getChar(this.base + CharRawIndexer.checkIndex(i, this.size) * 2L);
    }

    @Override
    public char get(long i) {
        return this.getRaw(this.index(i));
    }

    @Override
    public CharIndexer get(long i, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            c[offset + n] = this.getRaw(this.index(i) + (long)n);
        }
        return this;
    }

    @Override
    public char get(long i, long j) {
        return this.getRaw(this.index(i, j));
    }

    @Override
    public CharIndexer get(long i, long j, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            c[offset + n] = this.getRaw(this.index(i, j) + (long)n);
        }
        return this;
    }

    @Override
    public char get(long i, long j, long k) {
        return this.getRaw(this.index(i, j, k));
    }

    @Override
    public char get(long ... indices) {
        return this.getRaw(this.index(indices));
    }

    @Override
    public CharIndexer get(long[] indices, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            c[offset + n] = this.getRaw(this.index(indices) + (long)n);
        }
        return this;
    }

    public CharIndexer putRaw(long i, char c) {
        RAW.putChar(this.base + CharRawIndexer.checkIndex(i, this.size) * 2L, c);
        return this;
    }

    @Override
    public CharIndexer put(long i, char c) {
        return this.putRaw(this.index(i), c);
    }

    @Override
    public CharIndexer put(long i, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i) + (long)n, c[offset + n]);
        }
        return this;
    }

    @Override
    public CharIndexer put(long i, long j, char c) {
        this.putRaw(this.index(i, j), c);
        return this;
    }

    @Override
    public CharIndexer put(long i, long j, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i, j) + (long)n, c[offset + n]);
        }
        return this;
    }

    @Override
    public CharIndexer put(long i, long j, long k, char c) {
        this.putRaw(this.index(i, j, k), c);
        return this;
    }

    @Override
    public CharIndexer put(long[] indices, char c) {
        this.putRaw(this.index(indices), c);
        return this;
    }

    @Override
    public CharIndexer put(long[] indices, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(indices) + (long)n, c[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.pointer = null;
    }
}

