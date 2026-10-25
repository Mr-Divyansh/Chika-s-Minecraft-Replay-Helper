/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.nio.CharBuffer;
import org.bytedeco.javacpp.CharPointer;
import org.bytedeco.javacpp.indexer.CharArrayIndexer;
import org.bytedeco.javacpp.indexer.CharBufferIndexer;
import org.bytedeco.javacpp.indexer.CharRawIndexer;
import org.bytedeco.javacpp.indexer.Index;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.Raw;

public abstract class CharIndexer
extends Indexer {
    public static final int VALUE_BYTES = 2;

    protected CharIndexer(Index index) {
        super(index);
    }

    protected CharIndexer(long[] sizes, long[] strides) {
        super(sizes, strides);
    }

    public static CharIndexer create(char[] array) {
        return new CharArrayIndexer(array);
    }

    public static CharIndexer create(CharBuffer buffer) {
        return new CharBufferIndexer(buffer);
    }

    public static CharIndexer create(CharPointer pointer) {
        return new CharRawIndexer(pointer);
    }

    public static CharIndexer create(char[] array, Index index) {
        return new CharArrayIndexer(array, index);
    }

    public static CharIndexer create(CharBuffer buffer, Index index) {
        return new CharBufferIndexer(buffer, index);
    }

    public static CharIndexer create(CharPointer pointer, Index index) {
        return new CharRawIndexer(pointer, index);
    }

    public static CharIndexer create(char[] array, long ... sizes) {
        return new CharArrayIndexer(array, sizes);
    }

    public static CharIndexer create(CharBuffer buffer, long ... sizes) {
        return new CharBufferIndexer(buffer, sizes);
    }

    public static CharIndexer create(CharPointer pointer, long ... sizes) {
        return new CharRawIndexer(pointer, sizes);
    }

    public static CharIndexer create(char[] array, long[] sizes, long[] strides) {
        return new CharArrayIndexer(array, sizes, strides);
    }

    public static CharIndexer create(CharBuffer buffer, long[] sizes, long[] strides) {
        return new CharBufferIndexer(buffer, sizes, strides);
    }

    public static CharIndexer create(CharPointer pointer, long[] sizes, long[] strides) {
        return new CharRawIndexer(pointer, sizes, strides);
    }

    public static CharIndexer create(CharPointer pointer, long[] sizes, long[] strides, boolean direct) {
        return CharIndexer.create(pointer, Index.create(sizes, strides), direct);
    }

    public static CharIndexer create(final CharPointer pointer, Index index, boolean direct) {
        if (direct) {
            return Raw.getInstance() != null ? new CharRawIndexer(pointer, index) : new CharBufferIndexer(pointer.asBuffer(), index);
        }
        final long position = pointer.position();
        char[] array = new char[(int)Math.min(pointer.limit() - position, Integer.MAX_VALUE)];
        pointer.get(array);
        return new CharArrayIndexer(array, index){

            @Override
            public void release() {
                pointer.position(position).put(this.array);
                super.release();
            }
        };
    }

    public abstract char get(long var1);

    public CharIndexer get(long i, char[] c) {
        return this.get(i, c, 0, c.length);
    }

    public abstract CharIndexer get(long var1, char[] var3, int var4, int var5);

    public abstract char get(long var1, long var3);

    public CharIndexer get(long i, long j, char[] c) {
        return this.get(i, j, c, 0, c.length);
    }

    public abstract CharIndexer get(long var1, long var3, char[] var5, int var6, int var7);

    public abstract char get(long var1, long var3, long var5);

    public abstract char get(long ... var1);

    public CharIndexer get(long[] indices, char[] c) {
        return this.get(indices, c, 0, c.length);
    }

    public abstract CharIndexer get(long[] var1, char[] var2, int var3, int var4);

    public abstract CharIndexer put(long var1, char var3);

    public CharIndexer put(long i, char ... c) {
        return this.put(i, c, 0, c.length);
    }

    public abstract CharIndexer put(long var1, char[] var3, int var4, int var5);

    public abstract CharIndexer put(long var1, long var3, char var5);

    public CharIndexer put(long i, long j, char ... c) {
        return this.put(i, j, c, 0, c.length);
    }

    public abstract CharIndexer put(long var1, long var3, char[] var5, int var6, int var7);

    public abstract CharIndexer put(long var1, long var3, long var5, char var7);

    public abstract CharIndexer put(long[] var1, char var2);

    public CharIndexer put(long[] indices, char ... c) {
        return this.put(indices, c, 0, c.length);
    }

    public abstract CharIndexer put(long[] var1, char[] var2, int var3, int var4);

    @Override
    public double getDouble(long ... indices) {
        return this.get(indices);
    }

    @Override
    public CharIndexer putDouble(long[] indices, double c) {
        return this.put(indices, (char)c);
    }
}

