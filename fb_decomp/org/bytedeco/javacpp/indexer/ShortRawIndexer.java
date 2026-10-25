/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Raw;
import org.bytedeco.javacpp.indexer.ShortIndexer;

public class ShortRawIndexer
extends ShortIndexer {
    protected static final Raw RAW = Raw.getInstance();
    protected ShortPointer pointer;
    final long base;
    final long size;

    public ShortRawIndexer(ShortPointer pointer) {
        this(pointer, Index.create(pointer.limit() - pointer.position()));
    }

    public ShortRawIndexer(ShortPointer pointer, long ... sizes) {
        this(pointer, Index.create(sizes));
    }

    public ShortRawIndexer(ShortPointer pointer, long[] sizes, long[] strides) {
        this(pointer, Index.create(sizes, strides));
    }

    public ShortRawIndexer(ShortPointer pointer, Index index) {
        super(index);
        this.pointer = pointer;
        this.base = pointer.address() + pointer.position() * 2L;
        this.size = pointer.limit() - pointer.position();
    }

    @Override
    public Pointer pointer() {
        return this.pointer;
    }

    public ShortIndexer reindex(Index index) {
        return new ShortRawIndexer(this.pointer, index);
    }

    public short getRaw(long i) {
        return RAW.getShort(this.base + ShortRawIndexer.checkIndex(i, this.size) * 2L);
    }

    @Override
    public short get(long i) {
        return this.getRaw(this.index(i));
    }

    @Override
    public ShortIndexer get(long i, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.getRaw(this.index(i) + (long)n);
        }
        return this;
    }

    @Override
    public short get(long i, long j) {
        return this.getRaw(this.index(i, j));
    }

    @Override
    public ShortIndexer get(long i, long j, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.getRaw(this.index(i, j) + (long)n);
        }
        return this;
    }

    @Override
    public short get(long i, long j, long k) {
        return this.getRaw(this.index(i, j, k));
    }

    @Override
    public short get(long ... indices) {
        return this.getRaw(this.index(indices));
    }

    @Override
    public ShortIndexer get(long[] indices, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.getRaw(this.index(indices) + (long)n);
        }
        return this;
    }

    public ShortIndexer putRaw(long i, short s) {
        RAW.putShort(this.base + ShortRawIndexer.checkIndex(i, this.size) * 2L, s);
        return this;
    }

    @Override
    public ShortIndexer put(long i, short s) {
        return this.putRaw(this.index(i), s);
    }

    @Override
    public ShortIndexer put(long i, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i) + (long)n, s[offset + n]);
        }
        return this;
    }

    @Override
    public ShortIndexer put(long i, long j, short s) {
        this.putRaw(this.index(i, j), s);
        return this;
    }

    @Override
    public ShortIndexer put(long i, long j, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i, j) + (long)n, s[offset + n]);
        }
        return this;
    }

    @Override
    public ShortIndexer put(long i, long j, long k, short s) {
        this.putRaw(this.index(i, j, k), s);
        return this;
    }

    @Override
    public ShortIndexer put(long[] indices, short s) {
        this.putRaw(this.index(indices), s);
        return this;
    }

    @Override
    public ShortIndexer put(long[] indices, short[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(indices) + (long)n, s[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.pointer = null;
    }
}

