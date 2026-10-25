/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.IntBuffer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.Raw;
import org.bytedeco.javacpp.indexer.UIntArrayIndexer;
import org.bytedeco.javacpp.indexer.UIntBufferIndexer;
import org.bytedeco.javacpp.indexer.UIntRawIndexer;

public abstract class UIntIndexer
extends Indexer {
    public static final int VALUE_BYTES = 4;

    protected UIntIndexer(Index index) {
        super(index);
    }

    protected UIntIndexer(long[] sizes, long[] strides) {
        super(sizes, strides);
    }

    public static UIntIndexer create(int[] array) {
        return new UIntArrayIndexer(array);
    }

    public static UIntIndexer create(IntBuffer buffer) {
        return new UIntBufferIndexer(buffer);
    }

    public static UIntIndexer create(IntPointer pointer) {
        return new UIntRawIndexer(pointer);
    }

    public static UIntIndexer create(int[] array, Index index) {
        return new UIntArrayIndexer(array, index);
    }

    public static UIntIndexer create(IntBuffer buffer, Index index) {
        return new UIntBufferIndexer(buffer, index);
    }

    public static UIntIndexer create(IntPointer pointer, Index index) {
        return new UIntRawIndexer(pointer, index);
    }

    public static UIntIndexer create(int[] array, long ... sizes) {
        return new UIntArrayIndexer(array, sizes);
    }

    public static UIntIndexer create(IntBuffer buffer, long ... sizes) {
        return new UIntBufferIndexer(buffer, sizes);
    }

    public static UIntIndexer create(IntPointer pointer, long ... sizes) {
        return new UIntRawIndexer(pointer, sizes);
    }

    public static UIntIndexer create(int[] array, long[] sizes, long[] strides) {
        return new UIntArrayIndexer(array, sizes, strides);
    }

    public static UIntIndexer create(IntBuffer buffer, long[] sizes, long[] strides) {
        return new UIntBufferIndexer(buffer, sizes, strides);
    }

    public static UIntIndexer create(IntPointer pointer, long[] sizes, long[] strides) {
        return new UIntRawIndexer(pointer, sizes, strides);
    }

    public static UIntIndexer create(IntPointer pointer, long[] sizes, long[] strides, boolean direct) {
        return UIntIndexer.create(pointer, Index.create(sizes, strides), direct);
    }

    public static UIntIndexer create(final IntPointer pointer, Index index, boolean direct) {
        if (direct) {
            return Raw.getInstance() != null ? new UIntRawIndexer(pointer, index) : new UIntBufferIndexer(pointer.asBuffer(), index);
        }
        final long position = pointer.position();
        int[] array = new int[(int)Math.min(pointer.limit() - position, Integer.MAX_VALUE)];
        pointer.get(array);
        return new UIntArrayIndexer(array, index){

            @Override
            public void release() {
                pointer.position(position).put(this.array);
                super.release();
            }
        };
    }

    public abstract long get(long var1);

    public UIntIndexer get(long i, long[] n) {
        return this.get(i, n, 0, n.length);
    }

    public abstract UIntIndexer get(long var1, long[] var3, int var4, int var5);

    public abstract long get(long var1, long var3);

    public UIntIndexer get(long i, long j, long[] n) {
        return this.get(i, j, n, 0, n.length);
    }

    public abstract UIntIndexer get(long var1, long var3, long[] var5, int var6, int var7);

    public abstract long get(long var1, long var3, long var5);

    public abstract long get(long ... var1);

    public UIntIndexer get(long[] indices, long[] n) {
        return this.get(indices, n, 0, n.length);
    }

    public abstract UIntIndexer get(long[] var1, long[] var2, int var3, int var4);

    public abstract UIntIndexer put(long var1, long var3);

    public UIntIndexer put(long i, long ... n) {
        return this.put(i, n, 0, n.length);
    }

    public abstract UIntIndexer put(long var1, long[] var3, int var4, int var5);

    public abstract UIntIndexer put(long var1, long var3, long var5);

    public UIntIndexer put(long i, long j, long ... n) {
        return this.put(i, j, n, 0, n.length);
    }

    public abstract UIntIndexer put(long var1, long var3, long[] var5, int var6, int var7);

    public abstract UIntIndexer put(long var1, long var3, long var5, long var7);

    public abstract UIntIndexer put(long[] var1, long var2);

    public UIntIndexer put(long[] indices, long ... n) {
        return this.put(indices, n, 0, n.length);
    }

    public abstract UIntIndexer put(long[] var1, long[] var2, int var3, int var4);

    @Override
    public double getDouble(long ... indices) {
        return this.get(indices);
    }

    @Override
    public UIntIndexer putDouble(long[] indices, double n) {
        return this.put(indices, (long)((int)n));
    }
}

