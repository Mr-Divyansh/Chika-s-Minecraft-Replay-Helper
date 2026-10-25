/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.DoubleBuffer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.indexer.DoubleArrayIndexer;
import org.bytedeco.javacpp.indexer.DoubleBufferIndexer;
import org.bytedeco.javacpp.indexer.DoubleRawIndexer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.Raw;

public abstract class DoubleIndexer
extends Indexer {
    public static final int VALUE_BYTES = 8;

    protected DoubleIndexer(Index index) {
        super(index);
    }

    protected DoubleIndexer(long[] sizes, long[] strides) {
        super(sizes, strides);
    }

    public static DoubleIndexer create(double[] array) {
        return new DoubleArrayIndexer(array);
    }

    public static DoubleIndexer create(DoubleBuffer buffer) {
        return new DoubleBufferIndexer(buffer);
    }

    public static DoubleIndexer create(DoublePointer pointer) {
        return new DoubleRawIndexer(pointer);
    }

    public static DoubleIndexer create(double[] array, Index index) {
        return new DoubleArrayIndexer(array, index);
    }

    public static DoubleIndexer create(DoubleBuffer buffer, Index index) {
        return new DoubleBufferIndexer(buffer, index);
    }

    public static DoubleIndexer create(DoublePointer pointer, Index index) {
        return new DoubleRawIndexer(pointer, index);
    }

    public static DoubleIndexer create(double[] array, long ... sizes) {
        return new DoubleArrayIndexer(array, sizes);
    }

    public static DoubleIndexer create(DoubleBuffer buffer, long ... sizes) {
        return new DoubleBufferIndexer(buffer, sizes);
    }

    public static DoubleIndexer create(DoublePointer pointer, long ... sizes) {
        return new DoubleRawIndexer(pointer, sizes);
    }

    public static DoubleIndexer create(double[] array, long[] sizes, long[] strides) {
        return new DoubleArrayIndexer(array, sizes, strides);
    }

    public static DoubleIndexer create(DoubleBuffer buffer, long[] sizes, long[] strides) {
        return new DoubleBufferIndexer(buffer, sizes, strides);
    }

    public static DoubleIndexer create(DoublePointer pointer, long[] sizes, long[] strides) {
        return new DoubleRawIndexer(pointer, sizes, strides);
    }

    public static DoubleIndexer create(DoublePointer pointer, long[] sizes, long[] strides, boolean direct) {
        return DoubleIndexer.create(pointer, Index.create(sizes, strides), direct);
    }

    public static DoubleIndexer create(final DoublePointer pointer, Index index, boolean direct) {
        if (direct) {
            return Raw.getInstance() != null ? new DoubleRawIndexer(pointer, index) : new DoubleBufferIndexer(pointer.asBuffer(), index);
        }
        final long position = pointer.position();
        double[] array = new double[(int)Math.min(pointer.limit() - position, Integer.MAX_VALUE)];
        pointer.get(array);
        return new DoubleArrayIndexer(array, index){

            @Override
            public void release() {
                pointer.position(position).put(this.array);
                super.release();
            }
        };
    }

    public abstract double get(long var1);

    public DoubleIndexer get(long i, double[] d) {
        return this.get(i, d, 0, d.length);
    }

    public abstract DoubleIndexer get(long var1, double[] var3, int var4, int var5);

    public abstract double get(long var1, long var3);

    public DoubleIndexer get(long i, long j, double[] d) {
        return this.get(i, j, d, 0, d.length);
    }

    public abstract DoubleIndexer get(long var1, long var3, double[] var5, int var6, int var7);

    public abstract double get(long var1, long var3, long var5);

    public abstract double get(long ... var1);

    public DoubleIndexer get(long[] indices, double[] d) {
        return this.get(indices, d, 0, d.length);
    }

    public abstract DoubleIndexer get(long[] var1, double[] var2, int var3, int var4);

    public abstract DoubleIndexer put(long var1, double var3);

    public DoubleIndexer put(long i, double ... d) {
        return this.put(i, d, 0, d.length);
    }

    public abstract DoubleIndexer put(long var1, double[] var3, int var4, int var5);

    public abstract DoubleIndexer put(long var1, long var3, double var5);

    public DoubleIndexer put(long i, long j, double ... d) {
        return this.put(i, j, d, 0, d.length);
    }

    public abstract DoubleIndexer put(long var1, long var3, double[] var5, int var6, int var7);

    public abstract DoubleIndexer put(long var1, long var3, long var5, double var7);

    public abstract DoubleIndexer put(long[] var1, double var2);

    public DoubleIndexer put(long[] indices, double ... d) {
        return this.put(indices, d, 0, d.length);
    }

    public abstract DoubleIndexer put(long[] var1, double[] var2, int var3, int var4);

    @Override
    public double getDouble(long ... indices) {
        return this.get(indices);
    }

    @Override
    public DoubleIndexer putDouble(long[] indices, double d) {
        return this.put(indices, d);
    }
}

