/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import java.nio.ShortBuffer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Logger;

@Properties(inherit={javacpp.class})
public class ShortPointer
extends Pointer {
    private static final Logger logger;

    public ShortPointer(short ... array) {
        this(array.length);
        this.put(array);
    }

    public ShortPointer(ShortBuffer buffer) {
        super(buffer);
        if (buffer != null && !buffer.isDirect() && buffer.hasArray()) {
            short[] array = buffer.array();
            this.allocateArray(array.length - buffer.arrayOffset());
            this.put(array, buffer.arrayOffset(), array.length - buffer.arrayOffset());
            this.position(buffer.position());
            this.limit(buffer.limit());
        }
    }

    public ShortPointer(long size) {
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
            OutOfMemoryError e2 = new OutOfMemoryError("Cannot allocate new ShortPointer(" + size + "): totalBytes = " + ShortPointer.formatBytes(ShortPointer.totalBytes()) + ", physicalBytes = " + ShortPointer.formatBytes(ShortPointer.physicalBytes()));
            e2.initCause(e);
            throw e2;
        }
    }

    public ShortPointer() {
    }

    public ShortPointer(Pointer p) {
        super(p);
    }

    private native void allocateArray(long var1);

    public ShortPointer position(long position) {
        return (ShortPointer)super.position(position);
    }

    public ShortPointer limit(long limit) {
        return (ShortPointer)super.limit(limit);
    }

    public ShortPointer capacity(long capacity) {
        return (ShortPointer)super.capacity(capacity);
    }

    @Override
    public int sizeof() {
        return this.getClass() == ShortPointer.class ? 2 : super.sizeof();
    }

    public ShortPointer getPointer(long i) {
        return (ShortPointer)new ShortPointer(this).offsetAddress(i);
    }

    public short get() {
        return this.get(0L);
    }

    public native short get(long var1);

    public ShortPointer put(short s) {
        return this.put(0L, s);
    }

    public native ShortPointer put(long var1, short var3);

    public ShortPointer get(short[] array) {
        return this.get(array, 0, array.length);
    }

    public ShortPointer put(short ... array) {
        return this.put(array, 0, array.length);
    }

    public native ShortPointer get(short[] var1, int var2, int var3);

    public native ShortPointer put(short[] var1, int var2, int var3);

    @Override
    public final ShortBuffer asBuffer() {
        return this.asByteBuffer().asShortBuffer();
    }

    static {
        block2: {
            logger = Logger.create(ShortPointer.class);
            try {
                Loader.load();
            }
            catch (Throwable t) {
                if (!logger.isDebugEnabled()) break block2;
                logger.debug("Could not load ShortPointer: " + t);
            }
        }
    }
}

