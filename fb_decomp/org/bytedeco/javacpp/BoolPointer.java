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

@Name(value={"bool"})
@Properties(inherit={javacpp.class})
public class BoolPointer
extends Pointer {
    private static final Logger logger;

    public BoolPointer(long size) {
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
            OutOfMemoryError e2 = new OutOfMemoryError("Cannot allocate new BoolPointer(" + size + "): totalBytes = " + BoolPointer.formatBytes(BoolPointer.totalBytes()) + ", physicalBytes = " + BoolPointer.formatBytes(BoolPointer.physicalBytes()));
            e2.initCause(e);
            throw e2;
        }
    }

    public BoolPointer() {
    }

    public BoolPointer(Pointer p) {
        super(p);
    }

    private native void allocateArray(long var1);

    public BoolPointer position(long position) {
        return (BoolPointer)super.position(position);
    }

    public BoolPointer limit(long limit) {
        return (BoolPointer)super.limit(limit);
    }

    public BoolPointer capacity(long capacity) {
        return (BoolPointer)super.capacity(capacity);
    }

    public BoolPointer getPointer(long i) {
        return (BoolPointer)new BoolPointer(this).offsetAddress(i);
    }

    public boolean get() {
        return this.get(0L);
    }

    @Cast(value={"bool"})
    public native boolean get(long var1);

    public BoolPointer put(boolean b) {
        return this.put(0L, b);
    }

    public native BoolPointer put(long var1, boolean var3);

    static {
        block2: {
            logger = Logger.create(BoolPointer.class);
            try {
                Loader.load();
            }
            catch (Throwable t) {
                if (!logger.isDebugEnabled()) break block2;
                logger.debug("Could not load BoolPointer: " + t);
            }
        }
    }
}

