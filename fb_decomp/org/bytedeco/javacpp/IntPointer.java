/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import java.nio.IntBuffer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Logger;

@Properties(inherit={javacpp.class})
public class IntPointer
extends Pointer {
    private static final Logger logger;

    public IntPointer(String s) {
        this((long)(s.length() + 1));
        this.putString(s);
    }

    public IntPointer(int ... array) {
        this((long)array.length);
        this.put(array);
    }

    public IntPointer(IntBuffer buffer) {
        super(buffer);
        if (buffer != null && !buffer.isDirect() && buffer.hasArray()) {
            int[] array = buffer.array();
            this.allocateArray(array.length - buffer.arrayOffset());
            this.put(array, buffer.arrayOffset(), array.length - buffer.arrayOffset());
            this.position(buffer.position());
            this.limit(buffer.limit());
        }
    }

    public IntPointer(long size) {
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
            OutOfMemoryError e2 = new OutOfMemoryError("Cannot allocate new IntPointer(" + size + "): totalBytes = " + IntPointer.formatBytes(IntPointer.totalBytes()) + ", physicalBytes = " + IntPointer.formatBytes(IntPointer.physicalBytes()));
            e2.initCause(e);
            throw e2;
        }
    }

    public IntPointer() {
    }

    public IntPointer(Pointer p) {
        super(p);
    }

    private native void allocateArray(long var1);

    public IntPointer position(long position) {
        return (IntPointer)super.position(position);
    }

    public IntPointer limit(long limit) {
        return (IntPointer)super.limit(limit);
    }

    public IntPointer capacity(long capacity) {
        return (IntPointer)super.capacity(capacity);
    }

    @Override
    public int sizeof() {
        return this.getClass() == IntPointer.class ? 4 : super.sizeof();
    }

    public IntPointer getPointer(long i) {
        return (IntPointer)new IntPointer(this).offsetAddress(i);
    }

    public int[] getStringCodePoints() {
        int[] newbuffer;
        if (this.limit > this.position) {
            int[] array = new int[(int)Math.min(this.limit - this.position, Integer.MAX_VALUE)];
            this.get(array);
            return array;
        }
        int[] buffer = new int[16];
        int i = 0;
        while ((buffer[i] = this.get(i)) != 0) {
            if (++i < buffer.length) continue;
            newbuffer = new int[2 * buffer.length];
            System.arraycopy(buffer, 0, newbuffer, 0, buffer.length);
            buffer = newbuffer;
        }
        newbuffer = new int[i];
        System.arraycopy(buffer, 0, newbuffer, 0, i);
        return newbuffer;
    }

    public String getString() {
        int[] codePoints = this.getStringCodePoints();
        return new String(codePoints, 0, codePoints.length);
    }

    public IntPointer putString(String s) {
        int[] codePoints = new int[s.length()];
        for (int i = 0; i < codePoints.length; ++i) {
            codePoints[i] = s.codePointAt(i);
        }
        return this.put(codePoints).put((long)codePoints.length, 0).limit(codePoints.length);
    }

    public int get() {
        return this.get(0L);
    }

    public native int get(long var1);

    public IntPointer put(int j) {
        return this.put(0L, j);
    }

    public native IntPointer put(long var1, int var3);

    public IntPointer get(int[] array) {
        return this.get(array, 0, array.length);
    }

    public IntPointer put(int ... array) {
        return this.put(array, 0, array.length);
    }

    public native IntPointer get(int[] var1, int var2, int var3);

    public native IntPointer put(int[] var1, int var2, int var3);

    @Override
    public final IntBuffer asBuffer() {
        return this.asByteBuffer().asIntBuffer();
    }

    static {
        block2: {
            logger = Logger.create(IntPointer.class);
            try {
                Loader.load();
            }
            catch (Throwable t) {
                if (!logger.isDebugEnabled()) break block2;
                logger.debug("Could not load IntPointer: " + t);
            }
        }
    }
}

