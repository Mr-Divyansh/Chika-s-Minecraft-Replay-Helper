/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import java.nio.DoubleBuffer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Logger;

@Properties(inherit={javacpp.class})
public class DoublePointer
extends Pointer {
    private static final Logger logger;

    public DoublePointer(double ... array) {
        this(array.length);
        this.put(array);
    }

    public DoublePointer(DoubleBuffer buffer) {
        super(buffer);
        if (buffer != null && !buffer.isDirect() && buffer.hasArray()) {
            double[] array = buffer.array();
            this.allocateArray(array.length - buffer.arrayOffset());
            this.put(array, buffer.arrayOffset(), array.length - buffer.arrayOffset());
            this.position(buffer.position());
            this.limit(buffer.limit());
        }
    }

    public DoublePointer(long size) {
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
            OutOfMemoryError e2 = new OutOfMemoryError("Cannot allocate new DoublePointer(" + size + "): totalBytes = " + DoublePointer.formatBytes(DoublePointer.totalBytes()) + ", physicalBytes = " + DoublePointer.formatBytes(DoublePointer.physicalBytes()));
            e2.initCause(e);
            throw e2;
        }
    }

    public DoublePointer() {
    }

    public DoublePointer(Pointer p) {
        super(p);
    }

    private native void allocateArray(long var1);

    public DoublePointer position(long position) {
        return (DoublePointer)super.position(position);
    }

    public DoublePointer limit(long limit) {
        return (DoublePointer)super.limit(limit);
    }

    public DoublePointer capacity(long capacity) {
        return (DoublePointer)super.capacity(capacity);
    }

    @Override
    public int sizeof() {
        return this.getClass() == DoublePointer.class ? 8 : super.sizeof();
    }

    public DoublePointer getPointer(long i) {
        return (DoublePointer)new DoublePointer(this).offsetAddress(i);
    }

    public double get() {
        return this.get(0L);
    }

    public native double get(long var1);

    public DoublePointer put(double d) {
        return this.put(0L, d);
    }

    public native DoublePointer put(long var1, double var3);

    public DoublePointer get(double[] array) {
        return this.get(array, 0, array.length);
    }

    public DoublePointer put(double ... array) {
        return this.put(array, 0, array.length);
    }

    public native DoublePointer get(double[] var1, int var2, int var3);

    public native DoublePointer put(double[] var1, int var2, int var3);

    @Override
    public final DoubleBuffer asBuffer() {
        return this.asByteBuffer().asDoubleBuffer();
    }

    static {
        block2: {
            logger = Logger.create(DoublePointer.class);
            try {
                Loader.load();
            }
            catch (Throwable t) {
                if (!logger.isDebugEnabled()) break block2;
                logger.debug("Could not load DoublePointer: " + t);
            }
        }
    }
}

