/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

public class BufferRing<B extends ReleasableBuffer> {
    private Object[] buffers;
    private int position;

    public BufferRing(BufferFactory<B> factory, int size) {
        this.buffers = new Object[size];
        for (int i = 0; i < size; ++i) {
            this.buffers[i] = factory.create();
        }
        this.position = 0;
    }

    public int capacity() {
        return this.buffers.length;
    }

    public int position() {
        return this.position;
    }

    public BufferRing position(int position) {
        this.position = (position % this.buffers.length + this.buffers.length) % this.buffers.length;
        return this;
    }

    public B get() {
        return (B)((ReleasableBuffer)this.buffers[this.position]);
    }

    public B get(int offset) {
        return (B)((ReleasableBuffer)this.buffers[((this.position + offset) % this.buffers.length + this.buffers.length) % this.buffers.length]);
    }

    public void release() {
        for (int i = 0; i < this.buffers.length; ++i) {
            ((ReleasableBuffer)this.buffers[i]).release();
        }
        this.buffers = null;
    }

    public static interface ReleasableBuffer {
        public void release();
    }

    public static interface BufferFactory<B extends ReleasableBuffer> {
        public B create();
    }
}

