/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacpp.indexer.Bfloat16Indexer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Raw;

public class Bfloat16RawIndexer
extends Bfloat16Indexer {
    protected static final Raw RAW = Raw.getInstance();
    protected ShortPointer pointer;
    final long base;
    final long size;

    public Bfloat16RawIndexer(ShortPointer pointer) {
        this(pointer, Index.create(pointer.limit() - pointer.position()));
    }

    public Bfloat16RawIndexer(ShortPointer pointer, long ... sizes) {
        this(pointer, Index.create(sizes));
    }

    public Bfloat16RawIndexer(ShortPointer pointer, long[] sizes, long[] strides) {
        this(pointer, Index.create(sizes, strides));
    }

    public Bfloat16RawIndexer(ShortPointer pointer, Index index) {
        super(index);
        this.pointer = pointer;
        this.base = pointer.address() + pointer.position() * 2L;
        this.size = pointer.limit() - pointer.position();
    }

    @Override
    public Pointer pointer() {
        return this.pointer;
    }

    public Bfloat16Indexer reindex(Index index) {
        return new Bfloat16RawIndexer(this.pointer, index);
    }

    public float getRaw(long i) {
        return Bfloat16RawIndexer.toFloat(RAW.getShort(this.base + Bfloat16RawIndexer.checkIndex(i, this.size) * 2L));
    }

    @Override
    public float get(long i) {
        return this.getRaw(this.index(i));
    }

    @Override
    public Bfloat16Indexer get(long i, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = this.getRaw(this.index(i) + (long)n);
        }
        return this;
    }

    @Override
    public float get(long i, long j) {
        return this.getRaw(this.index(i, j));
    }

    @Override
    public Bfloat16Indexer get(long i, long j, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = this.getRaw(this.index(i, j) + (long)n);
        }
        return this;
    }

    @Override
    public float get(long i, long j, long k) {
        return this.getRaw(this.index(i, j, k));
    }

    @Override
    public float get(long ... indices) {
        return this.getRaw(this.index(indices));
    }

    @Override
    public Bfloat16Indexer get(long[] indices, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            h[offset + n] = this.getRaw(this.index(indices) + (long)n);
        }
        return this;
    }

    public Bfloat16Indexer putRaw(long i, float h) {
        RAW.putShort(this.base + Bfloat16RawIndexer.checkIndex(i, this.size) * 2L, (short)Bfloat16RawIndexer.fromFloat(h));
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, float h) {
        return this.putRaw(this.index(i), h);
    }

    @Override
    public Bfloat16Indexer put(long i, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i) + (long)n, h[offset + n]);
        }
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, long j, float h) {
        this.putRaw(this.index(i, j), h);
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, long j, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i, j) + (long)n, h[offset + n]);
        }
        return this;
    }

    @Override
    public Bfloat16Indexer put(long i, long j, long k, float h) {
        this.putRaw(this.index(i, j, k), h);
        return this;
    }

    @Override
    public Bfloat16Indexer put(long[] indices, float h) {
        this.putRaw(this.index(indices), h);
        return this;
    }

    @Override
    public Bfloat16Indexer put(long[] indices, float[] h, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(indices) + (long)n, h[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.pointer = null;
    }
}

