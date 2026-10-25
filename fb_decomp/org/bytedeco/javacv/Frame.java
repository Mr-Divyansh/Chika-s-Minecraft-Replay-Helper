/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.EnumSet;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.LongPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacpp.indexer.ByteIndexer;
import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.javacpp.indexer.Indexable;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.indexer.IntIndexer;
import org.bytedeco.javacpp.indexer.LongIndexer;
import org.bytedeco.javacpp.indexer.ShortIndexer;
import org.bytedeco.javacpp.indexer.UByteIndexer;
import org.bytedeco.javacpp.indexer.UShortIndexer;

public class Frame
implements AutoCloseable,
Indexable {
    public boolean keyFrame;
    public char pictType;
    public static final int DEPTH_BYTE = -8;
    public static final int DEPTH_UBYTE = 8;
    public static final int DEPTH_SHORT = -16;
    public static final int DEPTH_USHORT = 16;
    public static final int DEPTH_INT = -32;
    public static final int DEPTH_LONG = -64;
    public static final int DEPTH_FLOAT = 32;
    public static final int DEPTH_DOUBLE = 64;
    public int imageWidth;
    public int imageHeight;
    public int imageDepth;
    public int imageChannels;
    public int imageStride;
    public Buffer[] image;
    public int sampleRate;
    public int audioChannels;
    public Buffer[] samples;
    public ByteBuffer data;
    public int streamIndex;
    public Type type;
    public Object opaque;
    public long timestamp;

    public static int pixelSize(int depth) {
        return Math.abs(depth) / 8;
    }

    public Frame() {
    }

    public Frame(int width, int height, int depth, int channels) {
        this(width, height, depth, channels, (width * channels * Frame.pixelSize(depth) + 7 & 0xFFFFFFF8) / Frame.pixelSize(depth));
    }

    public Frame(int width, int height, int depth, int channels, int imageStride) {
        this.imageWidth = width;
        this.imageHeight = height;
        this.imageDepth = depth;
        this.imageChannels = channels;
        this.imageStride = imageStride;
        this.pictType = '\u0000';
        this.image = new Buffer[1];
        this.data = null;
        this.streamIndex = -1;
        this.type = null;
        BytePointer pointer = new BytePointer(this.imageHeight * imageStride * Frame.pixelSize(depth));
        ByteBuffer buffer = pointer.asByteBuffer();
        switch (this.imageDepth) {
            case -8: 
            case 8: {
                this.image[0] = buffer;
                break;
            }
            case -16: 
            case 16: {
                this.image[0] = buffer.asShortBuffer();
                break;
            }
            case -32: {
                this.image[0] = buffer.asIntBuffer();
                break;
            }
            case -64: {
                this.image[0] = buffer.asLongBuffer();
                break;
            }
            case 32: {
                this.image[0] = buffer.asFloatBuffer();
                break;
            }
            case 64: {
                this.image[0] = buffer.asDoubleBuffer();
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unsupported depth value: " + this.imageDepth);
            }
        }
        this.opaque = new Pointer[]{pointer.retainReference()};
    }

    public <I extends Indexer> I createIndexer() {
        return this.createIndexer(true, 0);
    }

    @Override
    public <I extends Indexer> I createIndexer(boolean direct) {
        return this.createIndexer(direct, 0);
    }

    public <I extends Indexer> I createIndexer(boolean direct, int i) {
        long[] sizes = new long[]{this.imageHeight, this.imageWidth, this.imageChannels};
        long[] strides = new long[]{this.imageStride, this.imageChannels, 1L};
        Buffer buffer = this.image[i];
        Object array = buffer.hasArray() ? buffer.array() : null;
        switch (this.imageDepth) {
            case 8: {
                return (I)(array != null ? UByteIndexer.create((byte[])array, sizes, strides).indexable(this) : (direct ? UByteIndexer.create((ByteBuffer)buffer, sizes, strides).indexable(this) : UByteIndexer.create(new BytePointer((ByteBuffer)buffer), sizes, strides, false).indexable(this)));
            }
            case -8: {
                return (I)(array != null ? ByteIndexer.create((byte[])array, sizes, strides).indexable(this) : (direct ? ByteIndexer.create((ByteBuffer)buffer, sizes, strides).indexable(this) : ByteIndexer.create(new BytePointer((ByteBuffer)buffer), sizes, strides, false).indexable(this)));
            }
            case 16: {
                return (I)(array != null ? UShortIndexer.create((short[])array, sizes, strides).indexable(this) : (direct ? UShortIndexer.create((ShortBuffer)buffer, sizes, strides).indexable(this) : UShortIndexer.create(new ShortPointer((ShortBuffer)buffer), sizes, strides, false).indexable(this)));
            }
            case -16: {
                return (I)(array != null ? ShortIndexer.create((short[])array, sizes, strides).indexable(this) : (direct ? ShortIndexer.create((ShortBuffer)buffer, sizes, strides).indexable(this) : ShortIndexer.create(new ShortPointer((ShortBuffer)buffer), sizes, strides, false).indexable(this)));
            }
            case -32: {
                return (I)(array != null ? IntIndexer.create((int[])array, sizes, strides).indexable(this) : (direct ? IntIndexer.create((IntBuffer)buffer, sizes, strides).indexable(this) : IntIndexer.create(new IntPointer((IntBuffer)buffer), sizes, strides, false).indexable(this)));
            }
            case -64: {
                return (I)(array != null ? LongIndexer.create((long[])array, sizes, strides).indexable(this) : (direct ? LongIndexer.create((LongBuffer)buffer, sizes, strides).indexable(this) : LongIndexer.create(new LongPointer((LongBuffer)buffer), sizes, strides, false).indexable(this)));
            }
            case 32: {
                return (I)(array != null ? FloatIndexer.create((float[])array, sizes, strides).indexable(this) : (direct ? FloatIndexer.create((FloatBuffer)buffer, sizes, strides).indexable(this) : FloatIndexer.create(new FloatPointer((FloatBuffer)buffer), sizes, strides, false).indexable(this)));
            }
            case 64: {
                return (I)(array != null ? DoubleIndexer.create((double[])array, sizes, strides).indexable(this) : (direct ? DoubleIndexer.create((DoubleBuffer)buffer, sizes, strides).indexable(this) : DoubleIndexer.create(new DoublePointer((DoubleBuffer)buffer), sizes, strides, false).indexable(this)));
            }
        }
        assert (false);
        return null;
    }

    public Frame clone() {
        Frame newFrame = new Frame();
        newFrame.imageWidth = this.imageWidth;
        newFrame.imageHeight = this.imageHeight;
        newFrame.imageDepth = this.imageDepth;
        newFrame.imageChannels = this.imageChannels;
        newFrame.imageStride = this.imageStride;
        newFrame.keyFrame = this.keyFrame;
        newFrame.pictType = this.pictType;
        newFrame.streamIndex = this.streamIndex;
        newFrame.type = this.type;
        newFrame.opaque = new Pointer[3];
        if (this.image != null) {
            newFrame.image = new Buffer[this.image.length];
            ((Pointer[])newFrame.opaque)[0] = Frame.cloneBufferArray(this.image, newFrame.image);
        }
        newFrame.audioChannels = this.audioChannels;
        newFrame.sampleRate = this.sampleRate;
        if (this.samples != null) {
            newFrame.samples = new Buffer[this.samples.length];
            ((Pointer[])newFrame.opaque)[1] = Frame.cloneBufferArray(this.samples, newFrame.samples);
        }
        if (this.data != null) {
            Buffer[] dst = new ByteBuffer[1];
            ((Pointer[])newFrame.opaque)[2] = Frame.cloneBufferArray(new ByteBuffer[]{this.data}, dst);
            newFrame.data = dst[0];
        }
        newFrame.timestamp = this.timestamp;
        return newFrame;
    }

    private static Pointer cloneBufferArray(Buffer[] srcBuffers, Buffer[] clonedBuffers) {
        Pointer opaque = null;
        if (srcBuffers != null && srcBuffers.length > 0) {
            int i;
            int totalCapacity = 0;
            for (int i2 = 0; i2 < srcBuffers.length; ++i2) {
                srcBuffers[i2].rewind();
                totalCapacity += srcBuffers[i2].capacity();
            }
            if (srcBuffers[0] instanceof ByteBuffer) {
                BytePointer pointer = new BytePointer(totalCapacity);
                for (i = 0; i < srcBuffers.length; ++i) {
                    clonedBuffers[i] = pointer.limit(pointer.position() + (long)srcBuffers[i].limit()).asBuffer().put((ByteBuffer)srcBuffers[i]);
                    pointer.position(pointer.limit());
                }
                opaque = pointer;
            } else if (srcBuffers[0] instanceof ShortBuffer) {
                ShortPointer pointer = new ShortPointer(totalCapacity);
                for (i = 0; i < srcBuffers.length; ++i) {
                    clonedBuffers[i] = pointer.limit(pointer.position() + (long)srcBuffers[i].limit()).asBuffer().put((ShortBuffer)srcBuffers[i]);
                    pointer.position(pointer.limit());
                }
                opaque = pointer;
            } else if (srcBuffers[0] instanceof IntBuffer) {
                IntPointer pointer = new IntPointer((long)totalCapacity);
                for (i = 0; i < srcBuffers.length; ++i) {
                    clonedBuffers[i] = pointer.limit(pointer.position() + (long)srcBuffers[i].limit()).asBuffer().put((IntBuffer)srcBuffers[i]);
                    pointer.position(pointer.limit());
                }
                opaque = pointer;
            } else if (srcBuffers[0] instanceof LongBuffer) {
                LongPointer pointer = new LongPointer((long)totalCapacity);
                for (i = 0; i < srcBuffers.length; ++i) {
                    clonedBuffers[i] = pointer.limit(pointer.position() + (long)srcBuffers[i].limit()).asBuffer().put((LongBuffer)srcBuffers[i]);
                    pointer.position(pointer.limit());
                }
                opaque = pointer;
            } else if (srcBuffers[0] instanceof FloatBuffer) {
                FloatPointer pointer = new FloatPointer(totalCapacity);
                for (i = 0; i < srcBuffers.length; ++i) {
                    clonedBuffers[i] = pointer.limit(pointer.position() + (long)srcBuffers[i].limit()).asBuffer().put((FloatBuffer)srcBuffers[i]);
                    pointer.position(pointer.limit());
                }
                opaque = pointer;
            } else if (srcBuffers[0] instanceof DoubleBuffer) {
                DoublePointer pointer = new DoublePointer(totalCapacity);
                for (i = 0; i < srcBuffers.length; ++i) {
                    clonedBuffers[i] = pointer.limit(pointer.position() + (long)srcBuffers[i].limit()).asBuffer().put((DoubleBuffer)srcBuffers[i]);
                    pointer.position(pointer.limit());
                }
                opaque = pointer;
            }
            for (int i3 = 0; i3 < srcBuffers.length; ++i3) {
                srcBuffers[i3].rewind();
                clonedBuffers[i3].rewind();
            }
        }
        if (opaque != null) {
            opaque.retainReference();
        }
        return opaque;
    }

    public EnumSet<Type> getTypes() {
        EnumSet<Type> type = EnumSet.noneOf(Type.class);
        if (this.image != null) {
            type.add(Type.VIDEO);
        }
        if (this.samples != null) {
            type.add(Type.AUDIO);
        }
        if (this.data != null) {
            type.add(Type.DATA);
        }
        return type;
    }

    @Override
    public void close() {
        if (this.opaque instanceof Pointer[]) {
            for (Pointer p : (Pointer[])this.opaque) {
                if (p == null) continue;
                p.releaseReference();
                p = null;
            }
            this.opaque = null;
        }
    }

    public static enum Type {
        VIDEO,
        AUDIO,
        DATA,
        SUBTITLE,
        ATTACHMENT;

    }
}

