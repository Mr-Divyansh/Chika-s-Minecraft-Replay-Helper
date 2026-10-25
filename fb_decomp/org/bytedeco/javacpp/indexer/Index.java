/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.HyperslabIndex;
import org.bytedeco.javacpp.indexer.OneIndex;
import org.bytedeco.javacpp.indexer.StrideIndex;

public abstract class Index {
    protected final long[] sizes;

    public static Index create(long size) {
        return new OneIndex(size);
    }

    public static Index create(long ... sizes) {
        return new StrideIndex(sizes);
    }

    public static Index create(long[] sizes, long[] strides) {
        return new StrideIndex(sizes, strides);
    }

    public static Index create(long[] sizes, long[] selectionOffsets, long[] selectionStrides, long[] selectionCounts, long[] selectionBlocks) {
        return new HyperslabIndex(sizes, selectionOffsets, selectionStrides, selectionCounts, selectionBlocks);
    }

    public static Index create(long[] sizes, long[] strides, long[] selectionOffsets, long[] selectionStrides, long[] selectionCounts, long[] selectionBlocks) {
        return new HyperslabIndex(sizes, strides, selectionOffsets, selectionStrides, selectionCounts, selectionBlocks);
    }

    public Index(long ... sizes) {
        this.sizes = sizes;
    }

    public int rank() {
        return this.sizes.length;
    }

    public long[] sizes() {
        return this.sizes;
    }

    public long size(int i) {
        return this.sizes[i];
    }

    public long index(long i) {
        return this.index(new long[]{i});
    }

    public long index(long i, long j) {
        return this.index(new long[]{i, j});
    }

    public long index(long i, long j, long k) {
        return this.index(new long[]{i, j, k});
    }

    public abstract long index(long ... var1);
}

