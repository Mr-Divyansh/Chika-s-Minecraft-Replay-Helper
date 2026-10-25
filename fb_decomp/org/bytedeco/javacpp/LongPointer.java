/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import java.nio.LongBuffer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Logger;

@Properties(inherit={javacpp.class})
public class LongPointer
extends Pointer {
    private static final Logger logger;

    public LongPointer(long ... array) {
        this((long)array.length);
        this.put(array);
    }

    public LongPointer(LongBuffer buffer) {
        super(buffer);
        if (buffer != null && !buffer.isDirect() && buffer.hasArray()) {
            long[] array = buffer.array();
            this.allocateArray(array.length - buffer.arrayOffset());
            this.put(array, buffer.arrayOffset(), array.length - buffer.arrayOffset());
            this.position(buffer.position());
            this.limit(buffer.limit());
        }
    }

    public LongPointer(long size) {
        try {
            this.allocateArray(size);
            if (size > 0L && this.address == 0L) {
                throw new OutOfMemoryError("Native allocator returned address == 0");
            }
        }
        catch (UnsatisfiedLinkError e) {
            throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
        }
        catch (OutOfMemoryError e) {
            OutOfMemoryError e2 = new OutOfMemoryError("Cannot allocate new LongPointer(" + size + "): totalBytes = " + LongPointer.formatBytes(LongPointer.totalBytes()) + ", physicalBytes = " + LongPointer.formatBytes(LongPointer.physicalBytes()));
            e2.initCause(e);
            throw e2;
        }
    }

    public LongPointer() {
    }

    public LongPointer(Pointer p) {
        super(p);
    }

    private native void allocateArray(long var1);

    public LongPointer position(long position) {
        return (LongPointer)super.position(position);
    }

    public LongPointer limit(long limit) {
        return (LongPointer)super.limit(limit);
    }

    public LongPointer capacity(long capacity) {
        return (LongPointer)super.capacity(capacity);
    }

    @Override
    public int sizeof() {
        return this.getClass() == LongPointer.class ? 8 : super.sizeof();
    }

    public LongPointer getPointer(long i) {
        return (LongPointer)new LongPointer(this).offsetAddress(i);
    }

    public long get() {
        return this.get(0L);
    }

    public native long get(long var1);

    public LongPointer put(long l) {
        return this.put(0L, l);
    }

    public native LongPointer put(long var1, long var3);

    public LongPointer get(long[] array) {
        return this.get(array, 0, array.length);
    }

    public LongPointer put(long ... array) {
        return this.put(array, 0, array.length);
    }

    public native LongPointer get(long[] var1, int var2, int var3);

    public native LongPointer put(long[] var1, int var2, int var3);

    @Override
    public final LongBuffer asBuffer() {
        return this.asByteBuffer().asLongBuffer();
    }

    static {
        block2: {
            logger = Logger.create(LongPointer.class);
            try {
                Loader.load();
            }
            catch (Throwable t) {
                if (!logger.isDebugEnabled()) break block2;
                logger.debug("Could not load LongPointer: " + t);
            }
        }
    }
}

