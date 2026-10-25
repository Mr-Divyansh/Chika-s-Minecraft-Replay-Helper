/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.FloatBuffer;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.indexer.FloatArrayIndexer;
import org.bytedeco.javacpp.indexer.FloatBufferIndexer;
import org.bytedeco.javacpp.indexer.FloatRawIndexer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.Raw;

public abstract class FloatIndexer
extends Indexer {
    public static final int VALUE_BYTES = 4;

    protected FloatIndexer(Index index) {
        super(index);
    }

    protected FloatIndexer(long[] sizes, long[] strides) {
        super(sizes, strides);
    }

    public static FloatIndexer create(float[] array) {
        return new FloatArrayIndexer(array);
    }

    public static FloatIndexer create(FloatBuffer buffer) {
        return new FloatBufferIndexer(buffer);
    }

    public static FloatIndexer create(FloatPointer pointer) {
        return new FloatRawIndexer(pointer);
    }

    public static FloatIndexer create(float[] array, Index index) {
        return new FloatArrayIndexer(array, index);
    }

    public static FloatIndexer create(FloatBuffer buffer, Index index) {
        return new FloatBufferIndexer(buffer, index);
    }

    public static FloatIndexer create(FloatPointer pointer, Index index) {
        return new FloatRawIndexer(pointer, index);
    }

    public static FloatIndexer create(float[] array, long ... sizes) {
        return new FloatArrayIndexer(array, sizes);
    }

    public static FloatIndexer create(FloatBuffer buffer, long ... sizes) {
        return new FloatBufferIndexer(buffer, sizes);
    }

    public static FloatIndexer create(FloatPointer pointer, long ... sizes) {
        return new FloatRawIndexer(pointer, sizes);
    }

    public static FloatIndexer create(float[] array, long[] sizes, long[] strides) {
        return new FloatArrayIndexer(array, sizes, strides);
    }

    public static FloatIndexer create(FloatBuffer buffer, long[] sizes, long[] strides) {
        return new FloatBufferIndexer(buffer, sizes, strides);
    }

    public static FloatIndexer create(FloatPointer pointer, long[] sizes, long[] strides) {
        return new FloatRawIndexer(pointer, sizes, strides);
    }

    public static FloatIndexer create(FloatPointer pointer, long[] sizes, long[] strides, boolean direct) {
        return FloatIndexer.create(pointer, Index.create(sizes, strides), direct);
    }

    public static FloatIndexer create(final FloatPointer pointer, Index index, boolean direct) {
        if (direct) {
            return Raw.getInstance() != null ? new FloatRawIndexer(pointer, index) : new FloatBufferIndexer(pointer.asBuffer(), index);
        }
        final long position = pointer.position();
        float[] array = new float[(int)Math.min(pointer.limit() - position, Integer.MAX_VALUE)];
        pointer.get(array);
        return new FloatArrayIndexer(array, index){

            @Override
            public void release() {
                pointer.position(position).put(this.array);
                super.release();
            }
        };
    }

    public abstract float get(long var1);

    public FloatIndexer get(long i, float[] f) {
        return this.get(i, f, 0, f.length);
    }

    public abstract FloatIndexer get(long var1, float[] var3, int var4, int var5);

    public abstract float get(long var1, long var3);

    public FloatIndexer get(long i, long j, float[] f) {
        return this.get(i, j, f, 0, f.length);
    }

    public abstract FloatIndexer get(long var1, long var3, float[] var5, int var6, int var7);

    public abstract float get(long var1, long var3, long var5);

    public abstract float get(long ... var1);

    public FloatIndexer get(long[] indices, float[] f) {
        return this.get(indices, f, 0, f.length);
    }

    public abstract FloatIndexer get(long[] var1, float[] var2, int var3, int var4);

    public abstract FloatIndexer put(long var1, float var3);

    public FloatIndexer put(long i, float ... f) {
        return this.put(i, f, 0, f.length);
    }

    public abstract FloatIndexer put(long var1, float[] var3, int var4, int var5);

    public abstract FloatIndexer put(long var1, long var3, float var5);

    public FloatIndexer put(long i, long j, float ... f) {
        return this.put(i, j, f, 0, f.length);
    }

    public abstract FloatIndexer put(long var1, long var3, float[] var5, int var6, int var7);

    public abstract FloatIndexer put(long var1, long var3, long var5, float var7);

    public abstract FloatIndexer put(long[] var1, float var2);

    public FloatIndexer put(long[] indices, float ... f) {
        return this.put(indices, f, 0, f.length);
    }

    public abstract FloatIndexer put(long[] var1, float[] var2, int var3, int var4);

    @Override
    public double getDouble(long ... indices) {
        return this.get(indices);
    }

    @Override
    public FloatIndexer putDouble(long[] indices, double f) {
        return this.put(indices, (float)f);
    }
}

