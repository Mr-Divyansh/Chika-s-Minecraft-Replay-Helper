/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Raw;

public class FloatRawIndexer
extends FloatIndexer {
    protected static final Raw RAW = Raw.getInstance();
    protected FloatPointer pointer;
    final long base;
    final long size;

    public FloatRawIndexer(FloatPointer pointer) {
        this(pointer, Index.create(pointer.limit() - pointer.position()));
    }

    public FloatRawIndexer(FloatPointer pointer, long ... sizes) {
        this(pointer, Index.create(sizes));
    }

    public FloatRawIndexer(FloatPointer pointer, long[] sizes, long[] strides) {
        this(pointer, Index.create(sizes, strides));
    }

    public FloatRawIndexer(FloatPointer pointer, Index index) {
        super(index);
        this.pointer = pointer;
        this.base = pointer.address() + pointer.position() * 4L;
        this.size = pointer.limit() - pointer.position();
    }

    @Override
    public Pointer pointer() {
        return this.pointer;
    }

    public FloatIndexer reindex(Index index) {
        return new FloatRawIndexer(this.pointer, index);
    }

    public float getRaw(long i) {
        return RAW.getFloat(this.base + FloatRawIndexer.checkIndex(i, this.size) * 4L);
    }

    @Override
    public float get(long i) {
        return this.getRaw(this.index(i));
    }

    @Override
    public FloatIndexer get(long i, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            f[offset + n] = this.getRaw(this.index(i) + (long)n);
        }
        return this;
    }

    @Override
    public float get(long i, long j) {
        return this.getRaw(this.index(i, j));
    }

    @Override
    public FloatIndexer get(long i, long j, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            f[offset + n] = this.getRaw(this.index(i, j) + (long)n);
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
    public FloatIndexer get(long[] indices, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            f[offset + n] = this.getRaw(this.index(indices) + (long)n);
        }
        return this;
    }

    public FloatIndexer putRaw(long i, float f) {
        RAW.putFloat(this.base + FloatRawIndexer.checkIndex(i, this.size) * 4L, f);
        return this;
    }

    @Override
    public FloatIndexer put(long i, float f) {
        return this.putRaw(this.index(i), f);
    }

    @Override
    public FloatIndexer put(long i, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i) + (long)n, f[offset + n]);
        }
        return this;
    }

    @Override
    public FloatIndexer put(long i, long j, float f) {
        this.putRaw(this.index(i, j), f);
        return this;
    }

    @Override
    public FloatIndexer put(long i, long j, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(i, j) + (long)n, f[offset + n]);
        }
        return this;
    }

    @Override
    public FloatIndexer put(long i, long j, long k, float f) {
        this.putRaw(this.index(i, j, k), f);
        return this;
    }

    @Override
    public FloatIndexer put(long[] indices, float f) {
        this.putRaw(this.index(indices), f);
        return this;
    }

    @Override
    public FloatIndexer put(long[] indices, float[] f, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.putRaw(this.index(indices) + (long)n, f[offset + n]);
        }
        return this;
    }

    @Override
    public void release() {
        this.pointer = null;
    }
}

