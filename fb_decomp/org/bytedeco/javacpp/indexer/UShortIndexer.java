/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.ShortBuffer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.Raw;
import org.bytedeco.javacpp.indexer.UShortArrayIndexer;
import org.bytedeco.javacpp.indexer.UShortBufferIndexer;
import org.bytedeco.javacpp.indexer.UShortRawIndexer;

public abstract class UShortIndexer
extends Indexer {
    public static final int VALUE_BYTES = 2;

    protected UShortIndexer(Index index) {
        super(index);
    }

    protected UShortIndexer(long[] sizes, long[] strides) {
        super(sizes, strides);
    }

    public static UShortIndexer create(short[] array) {
        return new UShortArrayIndexer(array);
    }

    public static UShortIndexer create(ShortBuffer buffer) {
        return new UShortBufferIndexer(buffer);
    }

    public static UShortIndexer create(ShortPointer pointer) {
        return new UShortRawIndexer(pointer);
    }

    public static UShortIndexer create(short[] array, Index index) {
        return new UShortArrayIndexer(array, index);
    }

    public static UShortIndexer create(ShortBuffer buffer, Index index) {
        return new UShortBufferIndexer(buffer, index);
    }

    public static UShortIndexer create(ShortPointer pointer, Index index) {
        return new UShortRawIndexer(pointer, index);
    }

    public static UShortIndexer create(short[] array, long ... sizes) {
        return new UShortArrayIndexer(array, sizes);
    }

    public static UShortIndexer create(ShortBuffer buffer, long ... sizes) {
        return new UShortBufferIndexer(buffer, sizes);
    }

    public static UShortIndexer create(ShortPointer pointer, long ... sizes) {
        return new UShortRawIndexer(pointer, sizes);
    }

    public static UShortIndexer create(short[] array, long[] sizes, long[] strides) {
        return new UShortArrayIndexer(array, sizes, strides);
    }

    public static UShortIndexer create(ShortBuffer buffer, long[] sizes, long[] strides) {
        return new UShortBufferIndexer(buffer, sizes, strides);
    }

    public static UShortIndexer create(ShortPointer pointer, long[] sizes, long[] strides) {
        return new UShortRawIndexer(pointer, sizes, strides);
    }

    public static UShortIndexer create(ShortPointer pointer, long[] sizes, long[] strides, boolean direct) {
        return UShortIndexer.create(pointer, Index.create(sizes, strides), direct);
    }

    public static UShortIndexer create(final ShortPointer pointer, Index index, boolean direct) {
        if (direct) {
            return Raw.getInstance() != null ? new UShortRawIndexer(pointer, index) : new UShortBufferIndexer(pointer.asBuffer(), index);
        }
        final long position = pointer.position();
        short[] array = new short[(int)Math.min(pointer.limit() - position, Integer.MAX_VALUE)];
        pointer.get(array);
        return new UShortArrayIndexer(array, index){

            @Override
            public void release() {
                pointer.position(position).put(this.array);
                super.release();
            }
        };
    }

    public abstract int get(long var1);

    public UShortIndexer get(long i, int[] s) {
        return this.get(i, s, 0, s.length);
    }

    public abstract UShortIndexer get(long var1, int[] var3, int var4, int var5);

    public abstract int get(long var1, long var3);

    public UShortIndexer get(long i, long j, int[] s) {
        return this.get(i, j, s, 0, s.length);
    }

    public abstract UShortIndexer get(long var1, long var3, int[] var5, int var6, int var7);

    public abstract int get(long var1, long var3, long var5);

    public abstract int get(long ... var1);

    public UShortIndexer get(long[] indices, int[] s) {
        return this.get(indices, s, 0, s.length);
    }

    public abstract UShortIndexer get(long[] var1, int[] var2, int var3, int var4);

    public abstract UShortIndexer put(long var1, int var3);

    public UShortIndexer put(long i, int ... s) {
        return this.put(i, s, 0, s.length);
    }

    public abstract UShortIndexer put(long var1, int[] var3, int var4, int var5);

    public abstract UShortIndexer put(long var1, long var3, int var5);

    public UShortIndexer put(long i, long j, int ... s) {
        return this.put(i, j, s, 0, s.length);
    }

    public abstract UShortIndexer put(long var1, long var3, int[] var5, int var6, int var7);

    public abstract UShortIndexer put(long var1, long var3, long var5, int var7);

    public abstract UShortIndexer put(long[] var1, int var2);

    public UShortIndexer put(long[] indices, int ... s) {
        return this.put(indices, s, 0, s.length);
    }

    public abstract UShortIndexer put(long[] var1, int[] var2, int var3, int var4);

    @Override
    public double getDouble(long ... indices) {
        return this.get(indices);
    }

    @Override
    public UShortIndexer putDouble(long[] indices, double s) {
        return this.put(indices, (int)s);
    }
}

