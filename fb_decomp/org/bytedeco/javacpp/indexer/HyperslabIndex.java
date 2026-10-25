/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.StrideIndex;

public class HyperslabIndex
extends StrideIndex {
    protected long[] selectionOffsets;
    protected long[] selectionStrides;
    protected long[] selectionCounts;
    protected long[] selectionBlocks;

    public HyperslabIndex(long[] sizes, long[] selectionOffsets, long[] selectionStrides, long[] selectionCounts, long[] selectionBlocks) {
        this(sizes, HyperslabIndex.defaultStrides(sizes), selectionOffsets, selectionStrides, selectionCounts, selectionBlocks);
    }

    public HyperslabIndex(long[] sizes, long[] strides, long[] selectionOffsets, long[] selectionStrides, long[] selectionCounts, long[] selectionBlocks) {
        super(sizes, strides);
        this.selectionOffsets = selectionOffsets;
        this.selectionStrides = selectionStrides;
        this.selectionCounts = selectionCounts;
        this.selectionBlocks = selectionBlocks;
        for (int i = 0; i < selectionCounts.length; ++i) {
            this.sizes[i] = selectionCounts[i] * selectionBlocks[i];
        }
    }

    @Override
    public long index(long i) {
        return (this.selectionOffsets[0] + this.selectionStrides[0] * (i / this.selectionBlocks[0]) + i % this.selectionBlocks[0]) * this.strides[0];
    }

    @Override
    public long index(long i, long j) {
        return (this.selectionOffsets[0] + this.selectionStrides[0] * (i / this.selectionBlocks[0]) + i % this.selectionBlocks[0]) * this.strides[0] + (this.selectionOffsets[1] + this.selectionStrides[1] * (j / this.selectionBlocks[1]) + j % this.selectionBlocks[1]) * this.strides[1];
    }

    @Override
    public long index(long i, long j, long k) {
        return (this.selectionOffsets[0] + this.selectionStrides[0] * (i / this.selectionBlocks[0]) + i % this.selectionBlocks[0]) * this.strides[0] + (this.selectionOffsets[1] + this.selectionStrides[1] * (j / this.selectionBlocks[1]) + j % this.selectionBlocks[1]) * this.strides[1] + (this.selectionOffsets[2] + this.selectionStrides[2] * (k / this.selectionBlocks[2]) + k % this.selectionBlocks[2]) * this.strides[2];
    }

    @Override
    public long index(long ... indices) {
        long index = 0L;
        for (int i = 0; i < indices.length; ++i) {
            long coordinate = indices[i];
            long mappedCoordinate = this.selectionOffsets[i] + this.selectionStrides[i] * (coordinate / this.selectionBlocks[i]) + coordinate % this.selectionBlocks[i];
            index += mappedCoordinate * this.strides[i];
        }
        return index;
    }
}

