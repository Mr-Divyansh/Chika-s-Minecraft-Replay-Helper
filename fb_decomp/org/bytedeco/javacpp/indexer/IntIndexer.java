/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.IntBuffer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.IntArrayIndexer;
import org.bytedeco.javacpp.indexer.IntBufferIndexer;
import org.bytedeco.javacpp.indexer.IntRawIndexer;
import org.bytedeco.javacpp.indexer.Raw;

public abstract class IntIndexer
extends Indexer {
    public static final int VALUE_BYTES = 4;

    protected IntIndexer(Index index) {
        super(index);
    }

    protected IntIndexer(long[] sizes, long[] strides) {
        super(sizes, strides);
    }

    public static IntIndexer create(int[] array) {
        return new IntArrayIndexer(array);
    }

    public static IntIndexer create(IntBuffer buffer) {
        return new IntBufferIndexer(buffer);
    }

    public static IntIndexer create(IntPointer pointer) {
        return new IntRawIndexer(pointer);
    }

    public static IntIndexer create(int[] array, Index index) {
        return new IntArrayIndexer(array, index);
    }

    public static IntIndexer create(IntBuffer buffer, Index index) {
        return new IntBufferIndexer(buffer, index);
    }

    public static IntIndexer create(IntPointer pointer, Index index) {
        return new IntRawIndexer(pointer, index);
    }

    public static IntIndexer create(int[] array, long ... sizes) {
        return new IntArrayIndexer(array, sizes);
    }

    public static IntIndexer create(IntBuffer buffer, long ... sizes) {
        return new IntBufferIndexer(buffer, sizes);
    }

    public static IntIndexer create(IntPointer pointer, long ... sizes) {
        return new IntRawIndexer(pointer, sizes);
    }

    public static IntIndexer create(int[] array, long[] sizes, long[] strides) {
        return new IntArrayIndexer(array, sizes, strides);
    }

    public static IntIndexer create(IntBuffer buffer, long[] sizes, long[] strides) {
        return new IntBufferIndexer(buffer, sizes, strides);
    }

    public static IntIndexer create(IntPointer pointer, long[] sizes, long[] strides) {
        return new IntRawIndexer(pointer, sizes, strides);
    }

    public static IntIndexer create(IntPointer pointer, long[] sizes, long[] strides, boolean direct) {
        return IntIndexer.create(pointer, Index.create(sizes, strides), direct);
    }

    public static IntIndexer create(final IntPointer pointer, Index index, boolean direct) {
        if (direct) {
            return Raw.getInstance() != null ? new IntRawIndexer(pointer, index) : new IntBufferIndexer(pointer.asBuffer(), index);
        }
        final long position = pointer.position();
        int[] array = new int[(int)Math.min(pointer.limit() - position, Integer.MAX_VALUE)];
        pointer.get(array);
        return new IntArrayIndexer(array, index){

            @Override
            public void release() {
                pointer.position(position).put(this.array);
                super.release();
            }
        };
    }

    public abstract int get(long var1);

    public IntIndexer get(long i, int[] n) {
        return this.get(i, n, 0, n.length);
    }

    public abstract IntIndexer get(long var1, int[] var3, int var4, int var5);

    public abstract int get(long var1, long var3);

    public IntIndexer get(long i, long j, int[] n) {
        return this.get(i, j, n, 0, n.length);
    }

    public abstract IntIndexer get(long var1, long var3, int[] var5, int var6, int var7);

    public abstract int get(long var1, long var3, long var5);

    public abstract int get(long ... var1);

    public IntIndexer get(long[] indices, int[] n) {
        return this.get(indices, n, 0, n.length);
    }

    public abstract IntIndexer get(long[] var1, int[] var2, int var3, int var4);

    public abstract IntIndexer put(long var1, int var3);

    public IntIndexer put(long i, int ... n) {
        return this.put(i, n, 0, n.length);
    }

    public abstract IntIndexer put(long var1, int[] var3, int var4, int var5);

    public abstract IntIndexer put(long var1, long var3, int var5);

    public IntIndexer put(long i, long j, int ... n) {
        return this.put(i, j, n, 0, n.length);
    }

    public abstract IntIndexer put(long var1, long var3, int[] var5, int var6, int var7);

    public abstract IntIndexer put(long var1, long var3, long var5, int var7);

    public abstract IntIndexer put(long[] var1, int var2);

    public IntIndexer put(long[] indices, int ... n) {
        return this.put(indices, n, 0, n.length);
    }

    public abstract IntIndexer put(long[] var1, int[] var2, int var3, int var4);

    @Override
    public double getDouble(long ... indices) {
        return this.get(indices);
    }

    @Override
    public IntIndexer putDouble(long[] indices, double n) {
        return this.put(indices, (int)n);
    }
}

