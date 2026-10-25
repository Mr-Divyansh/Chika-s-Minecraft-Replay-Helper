/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.ShortBuffer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacpp.indexer.HalfArrayIndexer;
import org.bytedeco.javacpp.indexer.HalfBufferIndexer;
import org.bytedeco.javacpp.indexer.HalfRawIndexer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.Raw;

public abstract class HalfIndexer
extends Indexer {
    public static final int VALUE_BYTES = 2;

    protected HalfIndexer(Index index) {
        super(index);
    }

    protected HalfIndexer(long[] sizes, long[] strides) {
        super(sizes, strides);
    }

    public static HalfIndexer create(short[] array) {
        return new HalfArrayIndexer(array);
    }

    public static HalfIndexer create(ShortBuffer buffer) {
        return new HalfBufferIndexer(buffer);
    }

    public static HalfIndexer create(ShortPointer pointer) {
        return new HalfRawIndexer(pointer);
    }

    public static HalfIndexer create(short[] array, Index index) {
        return new HalfArrayIndexer(array, index);
    }

    public static HalfIndexer create(ShortBuffer buffer, Index index) {
        return new HalfBufferIndexer(buffer, index);
    }

    public static HalfIndexer create(ShortPointer pointer, Index index) {
        return new HalfRawIndexer(pointer, index);
    }

    public static HalfIndexer create(short[] array, long ... sizes) {
        return new HalfArrayIndexer(array, sizes);
    }

    public static HalfIndexer create(ShortBuffer buffer, long ... sizes) {
        return new HalfBufferIndexer(buffer, sizes);
    }

    public static HalfIndexer create(ShortPointer pointer, long ... sizes) {
        return new HalfRawIndexer(pointer, sizes);
    }

    public static HalfIndexer create(short[] array, long[] sizes, long[] strides) {
        return new HalfArrayIndexer(array, sizes, strides);
    }

    public static HalfIndexer create(ShortBuffer buffer, long[] sizes, long[] strides) {
        return new HalfBufferIndexer(buffer, sizes, strides);
    }

    public static HalfIndexer create(ShortPointer pointer, long[] sizes, long[] strides) {
        return new HalfRawIndexer(pointer, sizes, strides);
    }

    public static HalfIndexer create(ShortPointer pointer, long[] sizes, long[] strides, boolean direct) {
        return HalfIndexer.create(pointer, Index.create(sizes, strides), direct);
    }

    public static HalfIndexer create(final ShortPointer pointer, Index index, boolean direct) {
        if (direct) {
            return Raw.getInstance() != null ? new HalfRawIndexer(pointer, index) : new HalfBufferIndexer(pointer.asBuffer(), index);
        }
        final long position = pointer.position();
        short[] array = new short[(int)Math.min(pointer.limit() - position, Integer.MAX_VALUE)];
        pointer.get(array);
        return new HalfArrayIndexer(array, index){

            @Override
            public void release() {
                pointer.position(position).put(this.array);
                super.release();
            }
        };
    }

    public static float toFloat(int hbits) {
        int mant = hbits & 0x3FF;
        int exp = hbits & 0x7C00;
        if (exp == 31744) {
            exp = 261120;
        } else if (exp != 0) {
            exp += 114688;
        } else if (mant != 0) {
            exp = 115712;
            do {
                exp -= 1024;
            } while (((mant <<= 1) & 0x400) == 0);
            mant &= 0x3FF;
        }
        return Float.intBitsToFloat((hbits & 0x8000) << 16 | (exp | mant) << 13);
    }

    public static int fromFloat(float fval) {
        int fbits = Float.floatToIntBits(fval);
        int sign = fbits >>> 16 & 0x8000;
        int val = (fbits & Integer.MAX_VALUE) + 4096;
        if (val >= 1199570944) {
            if ((fbits & Integer.MAX_VALUE) >= 1199570944) {
                if (val < 2139095040) {
                    return sign | 0x7C00;
                }
                return sign | 0x7C00 | (fbits & 0x7FFFFF) >>> 13;
            }
            return sign | 0x7BFF;
        }
        if (val >= 0x38800000) {
            return sign | val - 0x38000000 >>> 13;
        }
        if (val < 0x33000000) {
            return sign;
        }
        val = (fbits & Integer.MAX_VALUE) >>> 23;
        return sign | (fbits & 0x7FFFFF | 0x800000) + (0x800000 >>> val - 102) >>> 126 - val;
    }

    public abstract float get(long var1);

    public HalfIndexer get(long i, float[] h) {
        return this.get(i, h, 0, h.length);
    }

    public abstract HalfIndexer get(long var1, float[] var3, int var4, int var5);

    public abstract float get(long var1, long var3);

    public HalfIndexer get(long i, long j, float[] h) {
        return this.get(i, j, h, 0, h.length);
    }

    public abstract HalfIndexer get(long var1, long var3, float[] var5, int var6, int var7);

    public abstract float get(long var1, long var3, long var5);

    public abstract float get(long ... var1);

    public HalfIndexer get(long[] indices, float[] h) {
        return this.get(indices, h, 0, h.length);
    }

    public abstract HalfIndexer get(long[] var1, float[] var2, int var3, int var4);

    public abstract HalfIndexer put(long var1, float var3);

    public HalfIndexer put(long i, float ... h) {
        return this.put(i, h, 0, h.length);
    }

    public abstract HalfIndexer put(long var1, float[] var3, int var4, int var5);

    public abstract HalfIndexer put(long var1, long var3, float var5);

    public HalfIndexer put(long i, long j, float ... h) {
        return this.put(i, j, h, 0, h.length);
    }

    public abstract HalfIndexer put(long var1, long var3, float[] var5, int var6, int var7);

    public abstract HalfIndexer put(long var1, long var3, long var5, float var7);

    public abstract HalfIndexer put(long[] var1, float var2);

    public HalfIndexer put(long[] indices, float ... h) {
        return this.put(indices, h, 0, h.length);
    }

    public abstract HalfIndexer put(long[] var1, float[] var2, int var3, int var4);

    @Override
    public double getDouble(long ... indices) {
        return this.get(indices);
    }

    @Override
    public HalfIndexer putDouble(long[] indices, double h) {
        return this.put(indices, (float)h);
    }
}

