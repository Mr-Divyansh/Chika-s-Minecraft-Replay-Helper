/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.ByteBuffer;
import org.bytedeco.javacpp.BooleanPointer;
import org.bytedeco.javacpp.indexer.BooleanArrayIndexer;
import org.bytedeco.javacpp.indexer.BooleanBufferIndexer;
import org.bytedeco.javacpp.indexer.BooleanRawIndexer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.Raw;

public abstract class BooleanIndexer
extends Indexer {
    public static final int VALUE_BYTES = 1;

    protected BooleanIndexer(Index index) {
        super(index);
    }

    protected BooleanIndexer(long[] sizes, long[] strides) {
        super(sizes, strides);
    }

    public static BooleanIndexer create(boolean[] array) {
        return new BooleanArrayIndexer(array);
    }

    public static BooleanIndexer create(ByteBuffer buffer) {
        return new BooleanBufferIndexer(buffer);
    }

    public static BooleanIndexer create(BooleanPointer pointer) {
        return new BooleanRawIndexer(pointer);
    }

    public static BooleanIndexer create(boolean[] array, Index index) {
        return new BooleanArrayIndexer(array, index);
    }

    public static BooleanIndexer create(ByteBuffer buffer, Index index) {
        return new BooleanBufferIndexer(buffer, index);
    }

    public static BooleanIndexer create(BooleanPointer pointer, Index index) {
        return new BooleanRawIndexer(pointer, index);
    }

    public static BooleanIndexer create(boolean[] array, long ... sizes) {
        return new BooleanArrayIndexer(array, sizes);
    }

    public static BooleanIndexer create(ByteBuffer buffer, long ... sizes) {
        return new BooleanBufferIndexer(buffer, sizes);
    }

    public static BooleanIndexer create(BooleanPointer pointer, long ... sizes) {
        return new BooleanRawIndexer(pointer, sizes);
    }

    public static BooleanIndexer create(boolean[] array, long[] sizes, long[] strides) {
        return new BooleanArrayIndexer(array, sizes, strides);
    }

    public static BooleanIndexer create(ByteBuffer buffer, long[] sizes, long[] strides) {
        return new BooleanBufferIndexer(buffer, sizes, strides);
    }

    public static BooleanIndexer create(BooleanPointer pointer, long[] sizes, long[] strides) {
        return new BooleanRawIndexer(pointer, sizes, strides);
    }

    public static BooleanIndexer create(BooleanPointer pointer, long[] sizes, long[] strides, boolean direct) {
        return BooleanIndexer.create(pointer, Index.create(sizes, strides), direct);
    }

    public static BooleanIndexer create(final BooleanPointer pointer, Index index, boolean direct) {
        if (direct) {
            return Raw.getInstance() != null ? new BooleanRawIndexer(pointer, index) : new BooleanBufferIndexer(pointer.asByteBuffer(), index);
        }
        final long position = pointer.position();
        boolean[] array = new boolean[(int)Math.min(pointer.limit() - position, Integer.MAX_VALUE)];
        pointer.get(array);
        return new BooleanArrayIndexer(array, index){

            @Override
            public void release() {
                pointer.position(position).put(this.array);
                super.release();
            }
        };
    }

    public abstract boolean get(long var1);

    public BooleanIndexer get(long i, boolean[] b) {
        return this.get(i, b, 0, b.length);
    }

    public abstract BooleanIndexer get(long var1, boolean[] var3, int var4, int var5);

    public abstract boolean get(long var1, long var3);

    public BooleanIndexer get(long i, long j, boolean[] b) {
        return this.get(i, j, b, 0, b.length);
    }

    public abstract BooleanIndexer get(long var1, long var3, boolean[] var5, int var6, int var7);

    public abstract boolean get(long var1, long var3, long var5);

    public abstract boolean get(long ... var1);

    public BooleanIndexer get(long[] indices, boolean[] b) {
        return this.get(indices, b, 0, b.length);
    }

    public abstract BooleanIndexer get(long[] var1, boolean[] var2, int var3, int var4);

    public abstract BooleanIndexer put(long var1, boolean var3);

    public BooleanIndexer put(long i, boolean ... b) {
        return this.put(i, b, 0, b.length);
    }

    public abstract BooleanIndexer put(long var1, boolean[] var3, int var4, int var5);

    public abstract BooleanIndexer put(long var1, long var3, boolean var5);

    public BooleanIndexer put(long i, long j, boolean ... b) {
        return this.put(i, j, b, 0, b.length);
    }

    public abstract BooleanIndexer put(long var1, long var3, boolean[] var5, int var6, int var7);

    public abstract BooleanIndexer put(long var1, long var3, long var5, boolean var7);

    public abstract BooleanIndexer put(long[] var1, boolean var2);

    public BooleanIndexer put(long[] indices, boolean ... b) {
        return this.put(indices, b, 0, b.length);
    }

    public abstract BooleanIndexer put(long[] var1, boolean[] var2, int var3, int var4);

    @Override
    public double getDouble(long ... indices) {
        return this.get(indices) ? 1.0 : 0.0;
    }

    @Override
    public BooleanIndexer putDouble(long[] indices, double b) {
        return this.put(indices, b != 0.0);
    }
}

