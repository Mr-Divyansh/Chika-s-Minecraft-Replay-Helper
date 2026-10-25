/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.ByteBuffer;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.Raw;
import org.bytedeco.javacpp.indexer.UByteArrayIndexer;
import org.bytedeco.javacpp.indexer.UByteBufferIndexer;
import org.bytedeco.javacpp.indexer.UByteRawIndexer;

public abstract class UByteIndexer
extends Indexer {
    public static final int VALUE_BYTES = 1;

    protected UByteIndexer(Index index) {
        super(index);
    }

    protected UByteIndexer(long[] sizes, long[] strides) {
        super(sizes, strides);
    }

    public static UByteIndexer create(byte[] array) {
        return new UByteArrayIndexer(array);
    }

    public static UByteIndexer create(ByteBuffer buffer) {
        return new UByteBufferIndexer(buffer);
    }

    public static UByteIndexer create(BytePointer pointer) {
        return new UByteRawIndexer(pointer);
    }

    public static UByteIndexer create(byte[] array, Index index) {
        return new UByteArrayIndexer(array, index);
    }

    public static UByteIndexer create(ByteBuffer buffer, Index index) {
        return new UByteBufferIndexer(buffer, index);
    }

    public static UByteIndexer create(BytePointer pointer, Index index) {
        return new UByteRawIndexer(pointer, index);
    }

    public static UByteIndexer create(byte[] array, long ... sizes) {
        return new UByteArrayIndexer(array, sizes);
    }

    public static UByteIndexer create(ByteBuffer buffer, long ... sizes) {
        return new UByteBufferIndexer(buffer, sizes);
    }

    public static UByteIndexer create(BytePointer pointer, long ... sizes) {
        return new UByteRawIndexer(pointer, sizes);
    }

    public static UByteIndexer create(byte[] array, long[] sizes, long[] strides) {
        return new UByteArrayIndexer(array, sizes, strides);
    }

    public static UByteIndexer create(ByteBuffer buffer, long[] sizes, long[] strides) {
        return new UByteBufferIndexer(buffer, sizes, strides);
    }

    public static UByteIndexer create(BytePointer pointer, long[] sizes, long[] strides) {
        return new UByteRawIndexer(pointer, sizes, strides);
    }

    public static UByteIndexer create(BytePointer pointer, long[] sizes, long[] strides, boolean direct) {
        return UByteIndexer.create(pointer, Index.create(sizes, strides), direct);
    }

    public static UByteIndexer create(final BytePointer pointer, Index index, boolean direct) {
        if (direct) {
            return Raw.getInstance() != null ? new UByteRawIndexer(pointer, index) : new UByteBufferIndexer(pointer.asBuffer(), index);
        }
        final long position = pointer.position();
        byte[] array = new byte[(int)Math.min(pointer.limit() - position, Integer.MAX_VALUE)];
        pointer.get(array);
        return new UByteArrayIndexer(array, index){

            @Override
            public void release() {
                pointer.position(position).put(this.array);
                super.release();
            }
        };
    }

    public abstract int get(long var1);

    public UByteIndexer get(long i, int[] b) {
        return this.get(i, b, 0, b.length);
    }

    public abstract UByteIndexer get(long var1, int[] var3, int var4, int var5);

    public abstract int get(long var1, long var3);

    public UByteIndexer get(long i, long j, int[] b) {
        return this.get(i, j, b, 0, b.length);
    }

    public abstract UByteIndexer get(long var1, long var3, int[] var5, int var6, int var7);

    public abstract int get(long var1, long var3, long var5);

    public abstract int get(long ... var1);

    public UByteIndexer get(long[] indices, int[] b) {
        return this.get(indices, b, 0, b.length);
    }

    public abstract UByteIndexer get(long[] var1, int[] var2, int var3, int var4);

    public abstract UByteIndexer put(long var1, int var3);

    public UByteIndexer put(long i, int ... b) {
        return this.put(i, b, 0, b.length);
    }

    public abstract UByteIndexer put(long var1, int[] var3, int var4, int var5);

    public abstract UByteIndexer put(long var1, long var3, int var5);

    public UByteIndexer put(long i, long j, int ... b) {
        return this.put(i, j, b, 0, b.length);
    }

    public abstract UByteIndexer put(long var1, long var3, int[] var5, int var6, int var7);

    public abstract UByteIndexer put(long var1, long var3, long var5, int var7);

    public abstract UByteIndexer put(long[] var1, int var2);

    public UByteIndexer put(long[] indices, int ... b) {
        return this.put(indices, b, 0, b.length);
    }

    public abstract UByteIndexer put(long[] var1, int[] var2, int var3, int var4);

    @Override
    public double getDouble(long ... indices) {
        return this.get(indices);
    }

    @Override
    public UByteIndexer putDouble(long[] indices, double b) {
        return this.put(indices, (int)b);
    }
}

