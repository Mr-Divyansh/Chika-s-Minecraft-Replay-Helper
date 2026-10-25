/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Logger;

@Name(value={"size_t"})
@Properties(inherit={javacpp.class})
public class SizeTPointer
extends Pointer {
    private static final Logger logger;

    public SizeTPointer(long ... array) {
        this((long)array.length);
        this.put(array);
    }

    public SizeTPointer(long size) {
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
            OutOfMemoryError e2 = new OutOfMemoryError("Cannot allocate new SizeTPointer(" + size + "): totalBytes = " + SizeTPointer.formatBytes(SizeTPointer.totalBytes()) + ", physicalBytes = " + SizeTPointer.formatBytes(SizeTPointer.physicalBytes()));
            e2.initCause(e);
            throw e2;
        }
    }

    public SizeTPointer() {
    }

    public SizeTPointer(Pointer p) {
        super(p);
    }

    private native void allocateArray(long var1);

    public SizeTPointer position(long position) {
        return (SizeTPointer)super.position(position);
    }

    public SizeTPointer limit(long limit) {
        return (SizeTPointer)super.limit(limit);
    }

    public SizeTPointer capacity(long capacity) {
        return (SizeTPointer)super.capacity(capacity);
    }

    public SizeTPointer getPointer(long i) {
        return (SizeTPointer)new SizeTPointer(this).offsetAddress(i);
    }

    public long get() {
        return this.get(0L);
    }

    @Cast(value={"size_t"})
    public native long get(long var1);

    public SizeTPointer put(long s) {
        return this.put(0L, s);
    }

    public native SizeTPointer put(long var1, long var3);

    public SizeTPointer get(long[] array) {
        return this.get(array, 0, array.length);
    }

    public SizeTPointer put(long ... array) {
        return this.put(array, 0, array.length);
    }

    public SizeTPointer get(long[] array, int offset, int length) {
        for (int i = offset; i < offset + length; ++i) {
            array[i] = this.get(i);
        }
        return this;
    }

    public SizeTPointer put(long[] array, int offset, int length) {
        for (int i = offset; i < offset + length; ++i) {
            this.put((long)i, array[i]);
        }
        return this;
    }

    static {
        block2: {
            logger = Logger.create(SizeTPointer.class);
            try {
                Loader.load();
            }
            catch (Throwable t) {
                if (!logger.isDebugEnabled()) break block2;
                logger.debug("Could not load SizeTPointer: " + t);
            }
        }
    }
}

