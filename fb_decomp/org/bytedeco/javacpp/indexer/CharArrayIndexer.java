/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.CharIndexer;
import org.bytedeco.javacpp.indexer.Index;

public class CharArrayIndexer
extends CharIndexer {
    protected char[] array;

    public CharArrayIndexer(char[] array) {
        this(array, Index.create((long)array.length));
    }

    public CharArrayIndexer(char[] array, long ... sizes) {
        this(array, Index.create(sizes));
    }

    public CharArrayIndexer(char[] array, long[] sizes, long[] strides) {
        this(array, Index.create(sizes, strides));
    }

    public CharArrayIndexer(char[] array, Index index) {
        super(index);
        this.array = array;
    }

    public char[] array() {
        return this.array;
    }

    public CharIndexer reindex(Index index) {
        return new CharArrayIndexer(this.array, index);
    }

    @Override
    public char get(long i) {
        return this.array[(int)this.index(i)];
    }

    @Override
    public CharIndexer get(long i, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            c[offset + n] = this.array[(int)this.index(i) + n];
        }
        return this;
    }

    @Override
    public char get(long i, long j) {
        return this.array[(int)this.index(i, j)];
    }

    @Override
    public CharIndexer get(long i, long j, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            c[offset + n] = this.array[(int)this.index(i, j) + n];
        }
        return this;
    }

    @Override
    public char get(long i, long j, long k) {
        return this.array[(int)this.index(i, j, k)];
    }

    @Override
    public char get(long ... indices) {
        return this.array[(int)this.index(indices)];
    }

    @Override
    public CharIndexer get(long[] indices, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            c[offset + n] = this.array[(int)this.index(indices) + n];
        }
        return this;
    }

    @Override
    public CharIndexer put(long i, char c) {
        this.array[(int)this.index((long)i)] = c;
        return this;
    }

    @Override
    public CharIndexer put(long i, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i) + n] = c[offset + n];
        }
        return this;
    }

    @Override
    public CharIndexer put(long i, long j, char c) {
        this.array[(int)this.index((long)i, (long)j)] = c;
        return this;
    }

    @Override
    public CharIndexer put(long i, long j, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long)i, (long)j) + n] = c[offset + n];
        }
        return this;
    }

    @Override
    public CharIndexer put(long i, long j, long k, char c) {
        this.array[(int)this.index((long)i, (long)j, (long)k)] = c;
        return this;
    }

    @Override
    public CharIndexer put(long[] indices, char c) {
        this.array[(int)this.index((long[])indices)] = c;
        return this;
    }

    @Override
    public CharIndexer put(long[] indices, char[] c, int offset, int length) {
        for (int n = 0; n < length; ++n) {
            this.array[(int)this.index((long[])indices) + n] = c[offset + n];
        }
        return this;
    }

    @Override
    public void release() {
        this.array = null;
    }
}

