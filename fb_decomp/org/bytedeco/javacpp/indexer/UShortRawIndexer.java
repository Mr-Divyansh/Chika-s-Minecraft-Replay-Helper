/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Raw;
import org.bytedeco.javacpp.indexer.UShortIndexer;

public class UShortRawIndexer
extends UShortIndexer {
    protected static final Raw RAW = Raw.getInstance();
    protected ShortPointer pointer;
    final long base;
    final long size;

    public UShortRawIndexer(ShortPointer pointer) {
        this(pointer, Index.create(pointer.limit() - pointer.position()));
    }

    public UShortRawIndexer(ShortPointer pointer, long ... sizes) {
        this(pointer, Index.create(sizes));
    }

    public UShortRawIndexer(ShortPointer pointer, long[] sizes, long[] strides) {
        this(pointer, Index.create(sizes, strides));
    }

    public UShortRawIndexer(ShortPointer pointer, Index index) {
        super(index);
        this.pointer = pointer;
        this.base = pointer.address() + pointer.position() * 2L;
        this.size = pointer.limit() - pointer.position();
    }

    @Override
    public Pointer pointer() {
        return this.pointer;
    }

    public UShortIndexer reindex(Index index) {
        return new UShortRawIndexer(this.pointer, index);
    }

    public int getRaw(long i) {
        return RAW.getShort(this.base + UShortRawIndexer.checkIndex(i, this.size) * 2L) & 0xFFFF;
    }

    @Override
    public int get(long i) {
        return this.getRaw(this.index(i));
    }

    @Override
    public UShortIndexer get(long i, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.getRaw(this.index(i) + (long)n) & 0xFFFF;
        }
        return this;
    }

    @Override
    public int get(long i, long j) {
        return this.getRaw(this.index(i, j)) & 0xFFFF;
    }

    @Override
    public UShortIndexer get(long i, long j, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.getRaw(this.index(i, j) + (long)n) & 0xFFFF;
        }
        return this;
    }

    @Override
    public int get(long i, long j, long k) {
        return this.getRaw(this.index(i, j, k)) & 0xFFFF;
    }

    @Override
    public int get(long ... indices) {
        return this.getRaw(this.index(indices)) & 0xFFFF;
    }

    @Override
    public UShortIndexer get(long[] indices, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            s[offset + n] = this.getRaw(this.index(indices) + (long)n) & 0xFFFF;
        }
        return this;
    }

    public UShortIndexer putRaw(long i, int s) {
        RAW.putShort(this.base + UShortRawIndexer.checkIndex(i, this.size) * 2L, (short)s);
        return this;
    }

    @Override
    public UShortIndexer put(long i, int s) {
        return this.putRaw(this.index(i), s);
    }

    @Override
    public UShortIndexer put(long i, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i) + (long)n, s[offset + n]);
        }
        return this;
    }

    @Override
    public UShortIndexer put(long i, long j, int s) {
        this.putRaw(this.index(i, j), s);
        return this;
    }

    @Override
    public UShortIndexer put(long i, long j, int[] s, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i, j) + (long)n, s[offset + n]);
        }
        return this;
    }

    @Override
    public UShortIndexer put(long i, long j, long k, int s) {
        this.putRaw(this.index(i, j, k), s);
        return this;
    }

    @Override
    public UShortIndexer put(long[] indices, int s) {
        this.putRaw(this.index(indices), s);
        return this;
    }

    @Override
    public UShortIndexer put(long[] indices, int[] s, int offset, int length) {
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

