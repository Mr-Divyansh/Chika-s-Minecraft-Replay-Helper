/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.Index;

public class StrideIndex
extends Index {
    protected final long[] strides;

    public static long[] defaultStrides(long ... sizes) {
        long[] strides = new long[sizes.length];
        strides[sizes.length - 1] = 1L;
        for (int i = sizes.length - 2; i >= 0; --i) {
            strides[i] = strides[i + 1] * sizes[i + 1];
        }
        return strides;
    }

    public StrideIndex(long ... sizes) {
        this(sizes, StrideIndex.defaultStrides(sizes));
    }

    public StrideIndex(long[] sizes, long[] strides) {
        super(sizes);
        this.strides = strides;
    }

    public long[] strides() {
        return this.strides;
    }

    @Override
    public long index(long i) {
        return i * this.strides[0];
    }

    @Override
    public long index(long i, long j) {
        return i * this.strides[0] + j * this.strides[1];
    }

    @Override
    public long index(long i, long j, long k) {
        return i * this.strides[0] + j * this.strides[1] + k * this.strides[2];
    }

    @Override
    public long index(long ... indices) {
        long index = 0L;
        for (int i = 0; i < indices.length && i < this.strides.length; ++i) {
            index += indices[i] * this.strides[i];
        }
        return index;
    }
}

