/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.LongBuffer;
import org.bytedeco.javacpp.LongPointer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.Raw;
import org.bytedeco.javacpp.indexer.ULongArrayIndexer;
import org.bytedeco.javacpp.indexer.ULongBufferIndexer;
import org.bytedeco.javacpp.indexer.ULongRawIndexer;

public abstract class ULongIndexer
extends Indexer {
    public static final int VALUE_BYTES = 8;

    protected ULongIndexer(Index index) {
        super(index);
    }

    protected ULongIndexer(long[] sizes, long[] strides) {
        super(sizes, strides);
    }

    public static ULongIndexer create(long[] array) {
        return new ULongArrayIndexer(array);
    }

    public static ULongIndexer create(LongBuffer buffer) {
        return new ULongBufferIndexer(buffer);
    }

    public static ULongIndexer create(LongPointer pointer) {
        return new ULongRawIndexer(pointer);
    }

    public static ULongIndexer create(long[] array, Index index) {
        return new ULongArrayIndexer(array, index);
    }

    public static ULongIndexer create(LongBuffer buffer, Index index) {
        return new ULongBufferIndexer(buffer, index);
    }

    public static ULongIndexer create(LongPointer pointer, Index index) {
        return new ULongRawIndexer(pointer, index);
    }

    public static ULongIndexer create(long[] array, long ... sizes) {
        return new ULongArrayIndexer(array, sizes);
    }

    public static ULongIndexer create(LongBuffer buffer, long ... sizes) {
        return new ULongBufferIndexer(buffer, sizes);
    }

    public static ULongIndexer create(LongPointer pointer, long ... sizes) {
        return new ULongRawIndexer(pointer, sizes);
    }

    public static ULongIndexer create(long[] array, long[] sizes, long[] strides) {
        return new ULongArrayIndexer(array, sizes, strides);
    }

    public static ULongIndexer create(LongBuffer buffer, long[] sizes, long[] strides) {
        return new ULongBufferIndexer(buffer, sizes, strides);
    }

    public static ULongIndexer create(LongPointer pointer, long[] sizes, long[] strides) {
        return new ULongRawIndexer(pointer, sizes, strides);
    }

    public static ULongIndexer create(LongPointer pointer, long[] sizes, long[] strides, boolean direct) {
        return ULongIndexer.create(pointer, Index.create(sizes, strides), direct);
    }

    public static ULongIndexer create(final LongPointer pointer, Index index, boolean direct) {
        if (direct) {
            return Raw.getInstance() != null ? new ULongRawIndexer(pointer, index) : new ULongBufferIndexer(pointer.asBuffer(), index);
        }
        final long position = pointer.position();
        long[] array = new long[(int)Math.min(pointer.limit() - position, Integer.MAX_VALUE)];
        pointer.get(array);
        return new ULongArrayIndexer(array, index){

            @Override
            public void release() {
                pointer.position(position).put(this.array);
                super.release();
            }
        };
    }

    public static BigInteger toBigInteger(long l) {
        BigInteger bi = BigInteger.valueOf(l & Long.MAX_VALUE);
        if (l < 0L) {
            bi = bi.setBit(63);
        }
        return bi;
    }

    public static long fromBigInteger(BigInteger l) {
        return l.longValue();
    }

    public abstract BigInteger get(long var1);

    public ULongIndexer get(long i, BigInteger[] l) {
        return this.get(i, l, 0, l.length);
    }

    public abstract ULongIndexer get(long var1, BigInteger[] var3, int var4, int var5);

    public abstract BigInteger get(long var1, long var3);

    public ULongIndexer get(long i, long j, BigInteger[] l) {
        return this.get(i, j, l, 0, l.length);
    }

    public abstract ULongIndexer get(long var1, long var3, BigInteger[] var5, int var6, int var7);

    public abstract BigInteger get(long var1, long var3, long var5);

    public abstract BigInteger get(long ... var1);

    public ULongIndexer get(long[] indices, BigInteger[] l) {
        return this.get(indices, l, 0, l.length);
    }

    public abstract ULongIndexer get(long[] var1, BigInteger[] var2, int var3, int var4);

    public abstract ULongIndexer put(long var1, BigInteger var3);

    public ULongIndexer put(long i, BigInteger ... l) {
        return this.put(i, l, 0, l.length);
    }

    public abstract ULongIndexer put(long var1, BigInteger[] var3, int var4, int var5);

    public abstract ULongIndexer put(long var1, long var3, BigInteger var5);

    public ULongIndexer put(long i, long j, BigInteger ... l) {
        return this.put(i, j, l, 0, l.length);
    }

    public abstract ULongIndexer put(long var1, long var3, BigInteger[] var5, int var6, int var7);

    public abstract ULongIndexer put(long var1, long var3, long var5, BigInteger var7);

    public abstract ULongIndexer put(long[] var1, BigInteger var2);

    public ULongIndexer put(long[] indices, BigInteger ... l) {
        return this.put(indices, l, 0, l.length);
    }

    public abstract ULongIndexer put(long[] var1, BigInteger[] var2, int var3, int var4);

    @Override
    public double getDouble(long ... indices) {
        return this.get(indices).doubleValue();
    }

    @Override
    public ULongIndexer putDouble(long[] indices, double l) {
        return this.put(indices, BigDecimal.valueOf(l).toBigInteger());
    }
}

