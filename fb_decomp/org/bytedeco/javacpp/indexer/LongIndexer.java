/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.LongBuffer;
import org.bytedeco.javacpp.LongPointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.LongArrayIndexer;
import org.bytedeco.javacpp.indexer.LongBufferIndexer;
import org.bytedeco.javacpp.indexer.LongRawIndexer;
import org.bytedeco.javacpp.indexer.Raw;

public abstract class LongIndexer
extends Indexer {
    public static final int VALUE_BYTES = 8;

    protected LongIndexer(Index index) {
        super(index);
    }

    protected LongIndexer(long[] sizes, long[] strides) {
        super(sizes, strides);
    }

    public static LongIndexer create(long[] array) {
        return new LongArrayIndexer(array);
    }

    public static LongIndexer create(LongBuffer buffer) {
        return new LongBufferIndexer(buffer);
    }

    public static LongIndexer create(LongPointer pointer) {
        return new LongRawIndexer(pointer);
    }

    public static LongIndexer create(long[] array, Index index) {
        return new LongArrayIndexer(array, index);
    }

    public static LongIndexer create(LongBuffer buffer, Index index) {
        return new LongBufferIndexer(buffer, index);
    }

    public static LongIndexer create(LongPointer pointer, Index index) {
        return new LongRawIndexer(pointer, index);
    }

    public static LongIndexer create(long[] array, long ... sizes) {
        return new LongArrayIndexer(array, sizes);
    }

    public static LongIndexer create(LongBuffer buffer, long ... sizes) {
        return new LongBufferIndexer(buffer, sizes);
    }

    public static LongIndexer create(LongPointer pointer, long ... sizes) {
        return new LongRawIndexer(pointer, sizes);
    }

    public static LongIndexer create(long[] array, long[] sizes, long[] strides) {
        return new LongArrayIndexer(array, sizes, strides);
    }

    public static LongIndexer create(LongBuffer buffer, long[] sizes, long[] strides) {
        return new LongBufferIndexer(buffer, sizes, strides);
    }

    public static LongIndexer create(LongPointer pointer, long[] sizes, long[] strides) {
        return new LongRawIndexer(pointer, sizes, strides);
    }

    public static LongIndexer create(LongPointer pointer, long[] sizes, long[] strides, boolean direct) {
        return LongIndexer.create(pointer, Index.create(sizes, strides), direct);
    }

    public static LongIndexer create(final LongPointer pointer, Index index, boolean direct) {
        if (direct) {
            return Raw.getInstance() != null ? new LongRawIndexer(pointer, index) : new LongBufferIndexer(pointer.asBuffer(), index);
        }
        final long position = pointer.position();
        long[] array = new long[(int)Math.min(pointer.limit() - position, Integer.MAX_VALUE)];
        pointer.get(array);
        return new LongArrayIndexer(array, index){

            @Override
            public void release() {
                pointer.position(position).put(this.array);
                super.release();
            }
        };
    }

    public abstract long get(long var1);

    public LongIndexer get(long i, long[] l) {
        return this.get(i, l, 0, l.length);
    }

    public abstract LongIndexer get(long var1, long[] var3, int var4, int var5);

    public abstract long get(long var1, long var3);

    public LongIndexer get(long i, long j, long[] l) {
        return this.get(i, j, l, 0, l.length);
    }

    public abstract LongIndexer get(long var1, long var3, long[] var5, int var6, int var7);

    public abstract long get(long var1, long var3, long var5);

    public abstract long get(long ... var1);

    public LongIndexer get(long[] indices, long[] l) {
        return this.get(indices, l, 0, l.length);
    }

    public abstract LongIndexer get(long[] var1, long[] var2, int var3, int var4);

    public abstract LongIndexer put(long var1, long var3);

    public LongIndexer put(long i, long ... l) {
        return this.put(i, l, 0, l.length);
    }

    public abstract LongIndexer put(long var1, long[] var3, int var4, int var5);

    public abstract LongIndexer put(long var1, long var3, long var5);

    public LongIndexer put(long i, long j, long ... l) {
        return this.put(i, j, l, 0, l.length);
    }

    public abstract LongIndexer put(long var1, long var3, long[] var5, int var6, int var7);

    public abstract LongIndexer put(long var1, long var3, long var5, long var7);

    public abstract LongIndexer put(long[] var1, long var2);

    public LongIndexer put(long[] indices, long ... l) {
        return this.put(indices, l, 0, l.length);
    }

    public abstract LongIndexer put(long[] var1, long[] var2, int var3, int var4);

    @Override
    public double getDouble(long ... indices) {
        return this.get(indices);
    }

    @Override
    public LongIndexer putDouble(long[] indices, double l) {
        return this.put(indices, (long)l);
    }
}

