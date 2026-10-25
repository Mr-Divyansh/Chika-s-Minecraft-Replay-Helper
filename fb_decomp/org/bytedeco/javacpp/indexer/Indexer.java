/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.Buffer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Indexable;
import org.bytedeco.javacpp.indexer.StrideIndex;

public abstract class Indexer
implements AutoCloseable {
    @Deprecated
    protected long[] sizes;
    @Deprecated
    protected long[] strides;
    protected final Index index;
    protected Indexable indexable;

    @Override
    public void close() {
        this.release();
    }

    protected Indexer(Index index) {
        this.index = index;
        this.sizes = index.sizes();
        if (index instanceof StrideIndex) {
            this.strides = ((StrideIndex)index).strides();
        }
    }

    protected Indexer(long[] sizes, long[] strides) {
        this(Index.create(sizes, strides));
    }

    public int rank() {
        return this.index.rank();
    }

    public long[] sizes() {
        return this.index.sizes();
    }

    @Deprecated
    public long[] strides() {
        return this.strides;
    }

    public long size(int i) {
        return this.index.size(i);
    }

    @Deprecated
    public long stride(int i) {
        return this.strides != null ? this.strides[i] : -1L;
    }

    @Deprecated
    public long rows() {
        return this.sizes.length > 0 && this.sizes.length < 4 ? this.sizes[0] : -1L;
    }

    @Deprecated
    public long cols() {
        return this.sizes.length > 1 && this.sizes.length < 4 ? this.sizes[1] : -1L;
    }

    @Deprecated
    public long width() {
        return this.sizes.length > 1 && this.sizes.length < 4 ? this.sizes[1] : -1L;
    }

    @Deprecated
    public long height() {
        return this.sizes.length > 0 && this.sizes.length < 4 ? this.sizes[0] : -1L;
    }

    @Deprecated
    public long channels() {
        return this.sizes.length > 2 && this.sizes.length < 4 ? this.sizes[2] : -1L;
    }

    protected static final long checkIndex(long i, long size) {
        if (i < 0L || i >= size) {
            throw new IndexOutOfBoundsException(Long.toString(i));
        }
        return i;
    }

    @Deprecated
    public static long[] strides(long ... sizes) {
        return StrideIndex.defaultStrides(sizes);
    }

    public long index(long i) {
        return this.index.index(i);
    }

    public long index(long i, long j) {
        return this.index.index(i, j);
    }

    public long index(long i, long j, long k) {
        return this.index.index(i, j, k);
    }

    public long index(long ... indices) {
        return this.index.index(indices);
    }

    public Indexable indexable() {
        return this.indexable;
    }

    public Indexer indexable(Indexable indexable) {
        this.indexable = indexable;
        return this;
    }

    public Object array() {
        return null;
    }

    public Buffer buffer() {
        return null;
    }

    public Pointer pointer() {
        return null;
    }

    public abstract void release();

    public abstract double getDouble(long ... var1);

    public abstract Indexer putDouble(long[] var1, double var2);

    public abstract <I extends Indexer> I reindex(Index var1);

    public String toString() {
        long rows = this.sizes.length > 0 ? this.sizes[0] : 1L;
        long cols = this.sizes.length > 1 ? this.sizes[1] : 1L;
        long channels = this.sizes.length > 2 ? this.sizes[2] : 1L;
        StringBuilder s = new StringBuilder(rows > 1L ? "\n[ " : "[ ");
        int i = 0;
        while ((long)i < rows) {
            int j = 0;
            while ((long)j < cols) {
                if (channels > 1L) {
                    s.append("(");
                }
                int k = 0;
                while ((long)k < channels) {
                    double v = this.getDouble(i, j, k);
                    s.append((float)v);
                    if ((long)k < channels - 1L) {
                        s.append(", ");
                    }
                    ++k;
                }
                if (channels > 1L) {
                    s.append(")");
                }
                if ((long)j < cols - 1L) {
                    s.append(", ");
                }
                ++j;
            }
            if ((long)i < rows - 1L) {
                s.append("\n  ");
            }
            ++i;
        }
        s.append(" ]");
        return s.toString();
    }
}

