/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.ShortBuffer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.Raw;
import org.bytedeco.javacpp.indexer.ShortArrayIndexer;
import org.bytedeco.javacpp.indexer.ShortBufferIndexer;
import org.bytedeco.javacpp.indexer.ShortRawIndexer;

public abstract class ShortIndexer
extends Indexer {
    public static final int VALUE_BYTES = 2;

    protected ShortIndexer(Index index) {
        super(index);
    }

    protected ShortIndexer(long[] sizes, long[] strides) {
        super(sizes, strides);
    }

    public static ShortIndexer create(short[] array) {
        return new ShortArrayIndexer(array);
    }

    public static ShortIndexer create(ShortBuffer buffer) {
        return new ShortBufferIndexer(buffer);
    }

    public static ShortIndexer create(ShortPointer pointer) {
        return new ShortRawIndexer(pointer);
    }

    public static ShortIndexer create(short[] array, Index index) {
        return new ShortArrayIndexer(array, index);
    }

    public static ShortIndexer create(ShortBuffer buffer, Index index) {
        return new ShortBufferIndexer(buffer, index);
    }

    public static ShortIndexer create(ShortPointer pointer, Index index) {
        return new ShortRawIndexer(pointer, index);
    }

    public static ShortIndexer create(short[] array, long ... sizes) {
        return new ShortArrayIndexer(array, sizes);
    }

    public static ShortIndexer create(ShortBuffer buffer, long ... sizes) {
        return new ShortBufferIndexer(buffer, sizes);
    }

    public static ShortIndexer create(ShortPointer pointer, long ... sizes) {
        return new ShortRawIndexer(pointer, sizes);
    }

    public static ShortIndexer create(short[] array, long[] sizes, long[] strides) {
        return new ShortArrayIndexer(array, sizes, strides);
    }

    public static ShortIndexer create(ShortBuffer buffer, long[] sizes, long[] strides) {
        return new ShortBufferIndexer(buffer, sizes, strides);
    }

    public static ShortIndexer create(ShortPointer pointer, long[] sizes, long[] strides) {
        return new ShortRawIndexer(pointer, sizes, strides);
    }

    public static ShortIndexer create(ShortPointer pointer, long[] sizes, long[] strides, boolean direct) {
        return ShortIndexer.create(pointer, Index.create(sizes, strides), direct);
    }

    public static ShortIndexer create(final ShortPointer pointer, Index index, boolean direct) {
        if (direct) {
            return Raw.getInstance() != null ? new ShortRawIndexer(pointer, index) : new ShortBufferIndexer(pointer.asBuffer(), index);
        }
        final long position = pointer.position();
        short[] array = new short[(int)Math.min(pointer.limit() - position, Integer.MAX_VALUE)];
        pointer.get(array);
        return new ShortArrayIndexer(array, index){

            @Override
            public void release() {
                pointer.position(position).put(this.array);
                super.release();
            }
        };
    }

    public abstract short get(long var1);

    public ShortIndexer get(long i, short[] s) {
        return this.get(i, s, 0, s.length);
    }

    public abstract ShortIndexer get(long var1, short[] var3, int var4, int var5);

    public abstract short get(long var1, long var3);

    public ShortIndexer get(long i, long j, short[] s) {
        return this.get(i, j, s, 0, s.length);
    }

    public abstract ShortIndexer get(long var1, long var3, short[] var5, int var6, int var7);

    public abstract short get(long var1, long var3, long var5);

    public abstract short get(long ... var1);

    public ShortIndexer get(long[] indices, short[] s) {
        return this.get(indices, s, 0, s.length);
    }

    public abstract ShortIndexer get(long[] var1, short[] var2, int var3, int var4);

    public abstract ShortIndexer put(long var1, short var3);

    public ShortIndexer put(long i, short ... s) {
        return this.put(i, s, 0, s.length);
    }

    public abstract ShortIndexer put(long var1, short[] var3, int var4, int var5);

    public abstract ShortIndexer put(long var1, long var3, short var5);

    public ShortIndexer put(long i, long j, short ... s) {
        return this.put(i, j, s, 0, s.length);
    }

    public abstract ShortIndexer put(long var1, long var3, short[] var5, int var6, int var7);

    public abstract ShortIndexer put(long var1, long var3, long var5, short var7);

    public abstract ShortIndexer put(long[] var1, short var2);

    public ShortIndexer put(long[] indices, short ... s) {
        return this.put(indices, s, 0, s.length);
    }

    public abstract ShortIndexer put(long[] var1, short[] var2, int var3, int var4);

    @Override
    public double getDouble(long ... indices) {
        return this.get(indices);
    }

    @Override
    public ShortIndexer putDouble(long[] indices, double s) {
        return this.put(indices, (short)s);
    }
}

