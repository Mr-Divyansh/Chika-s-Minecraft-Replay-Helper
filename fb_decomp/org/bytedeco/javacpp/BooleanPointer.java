/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import java.nio.ByteBuffer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Logger;

@Properties(inherit={javacpp.class})
public class BooleanPointer
extends Pointer {
    private static final Logger logger;

    public BooleanPointer(boolean ... array) {
        this(array.length);
        this.put(array);
    }

    public BooleanPointer(ByteBuffer buffer) {
        super(buffer);
        if (buffer != null && !buffer.isDirect() && buffer.hasArray()) {
            byte[] array = buffer.array();
            this.allocateArray(array.length - buffer.arrayOffset());
            for (int i = buffer.arrayOffset(); i < array.length; ++i) {
                this.put(i - buffer.arrayOffset(), array[i] != 0);
            }
            this.position(buffer.position());
            this.limit(buffer.limit());
        }
    }

    public BooleanPointer(long size) {
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
            OutOfMemoryError e2 = new OutOfMemoryError("Cannot allocate new BooleanPointer(" + size + "): totalBytes = " + BooleanPointer.formatBytes(BooleanPointer.totalBytes()) + ", physicalBytes = " + BooleanPointer.formatBytes(BooleanPointer.physicalBytes()));
            e2.initCause(e);
            throw e2;
        }
    }

    public BooleanPointer() {
    }

    public BooleanPointer(Pointer p) {
        super(p);
    }

    private native void allocateArray(long var1);

    public BooleanPointer position(long position) {
        return (BooleanPointer)super.position(position);
    }

    public BooleanPointer limit(long limit) {
        return (BooleanPointer)super.limit(limit);
    }

    public BooleanPointer capacity(long capacity) {
        return (BooleanPointer)super.capacity(capacity);
    }

    @Override
    public int sizeof() {
        return this.getClass() == BooleanPointer.class ? 1 : super.sizeof();
    }

    public BooleanPointer getPointer(long i) {
        return (BooleanPointer)new BooleanPointer(this).offsetAddress(i);
    }

    public boolean get() {
        return this.get(0L);
    }

    public native boolean get(long var1);

    public BooleanPointer put(boolean b) {
        return this.put(0L, b);
    }

    public native BooleanPointer put(long var1, boolean var3);

    public BooleanPointer get(boolean[] array) {
        return this.get(array, 0, array.length);
    }

    public BooleanPointer put(boolean ... array) {
        return this.put(array, 0, array.length);
    }

    public native BooleanPointer get(boolean[] var1, int var2, int var3);

    public native BooleanPointer put(boolean[] var1, int var2, int var3);

    static {
        block2: {
            logger = Logger.create(BooleanPointer.class);
            try {
                Loader.load();
            }
            catch (Throwable t) {
                if (!logger.isDebugEnabled()) break block2;
                logger.debug("Could not load BooleanPointer: " + t);
            }
        }
    }
}

