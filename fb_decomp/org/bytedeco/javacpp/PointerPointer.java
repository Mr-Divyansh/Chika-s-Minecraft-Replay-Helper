/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.CharPointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.LongPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.ShortPointer;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Logger;

@Properties(inherit={javacpp.class})
public class PointerPointer<P extends Pointer>
extends Pointer {
    private static final Logger logger;
    private P[] pointerArray;

    public PointerPointer(String ... array) {
        this(array.length);
        this.putString(array);
    }

    public PointerPointer(String[] array, String charsetName) throws UnsupportedEncodingException {
        this(array.length);
        this.putString(array, charsetName);
    }

    public PointerPointer(String[] array, Charset charset) {
        this(array.length);
        this.putString(array, charset);
    }

    public PointerPointer(P ... array) {
        this(array.length);
        this.put((Pointer[])array);
    }

    public PointerPointer(byte[] ... array) {
        this(array.length);
        this.put(array);
    }

    public PointerPointer(short[] ... array) {
        this(array.length);
        this.put(array);
    }

    public PointerPointer(int[] ... array) {
        this(array.length);
        this.put(array);
    }

    public PointerPointer(long[] ... array) {
        this(array.length);
        this.put(array);
    }

    public PointerPointer(float[] ... array) {
        this(array.length);
        this.put(array);
    }

    public PointerPointer(double[] ... array) {
        this(array.length);
        this.put(array);
    }

    public PointerPointer(char[] ... array) {
        this(array.length);
        this.put(array);
    }

    public PointerPointer(long size) {
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
            OutOfMemoryError e2 = new OutOfMemoryError("Cannot allocate new PointerPointer(" + size + "): totalBytes = " + PointerPointer.formatBytes(PointerPointer.totalBytes()) + ", physicalBytes = " + PointerPointer.formatBytes(PointerPointer.physicalBytes()));
            e2.initCause(e);
            throw e2;
        }
    }

    public PointerPointer() {
    }

    public PointerPointer(Pointer p) {
        super(p);
    }

    private native void allocateArray(long var1);

    public PointerPointer<P> position(long position) {
        return (PointerPointer)super.position(position);
    }

    public PointerPointer<P> limit(long limit) {
        return (PointerPointer)super.limit(limit);
    }

    public PointerPointer<P> capacity(long capacity) {
        return (PointerPointer)super.capacity(capacity);
    }

    public PointerPointer<P> getPointer(long i) {
        return (PointerPointer)new PointerPointer<P>(this).offsetAddress(i);
    }

    public String getString(long i) {
        BytePointer p = this.get(BytePointer.class, i);
        return p != null ? p.getString() : null;
    }

    public String getString(long i, String charsetName) throws UnsupportedEncodingException {
        BytePointer p = this.get(BytePointer.class, i);
        return p != null ? p.getString(charsetName) : null;
    }

    public String getString(long i, Charset charset) {
        BytePointer p = this.get(BytePointer.class, i);
        return p != null ? p.getString(charset) : null;
    }

    public PointerPointer<P> putString(String ... array) {
        this.pointerArray = new BytePointer[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.pointerArray[i] = array[i] != null ? new BytePointer(array[i]) : null;
        }
        return this.put((Pointer[])this.pointerArray);
    }

    public PointerPointer<P> putString(String[] array, String charsetName) throws UnsupportedEncodingException {
        this.pointerArray = new BytePointer[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.pointerArray[i] = array[i] != null ? new BytePointer(array[i], charsetName) : null;
        }
        return this.put((Pointer[])this.pointerArray);
    }

    public PointerPointer<P> putString(String[] array, Charset charset) {
        this.pointerArray = new BytePointer[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.pointerArray[i] = array[i] != null ? new BytePointer(array[i], charset) : null;
        }
        return this.put((Pointer[])this.pointerArray);
    }

    public PointerPointer<P> put(P ... array) {
        this.pointerArray = array;
        for (int i = 0; i < array.length; ++i) {
            this.put(i, (Pointer)array[i]);
        }
        return this;
    }

    public PointerPointer<P> put(byte[] ... array) {
        this.pointerArray = new BytePointer[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.pointerArray[i] = array[i] != null ? new BytePointer(array[i]) : null;
        }
        return this.put((Pointer[])this.pointerArray);
    }

    public PointerPointer<P> put(short[] ... array) {
        this.pointerArray = new ShortPointer[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.pointerArray[i] = array[i] != null ? new ShortPointer(array[i]) : null;
        }
        return this.put((Pointer[])this.pointerArray);
    }

    public PointerPointer<P> put(int[] ... array) {
        this.pointerArray = new IntPointer[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.pointerArray[i] = array[i] != null ? new IntPointer(array[i]) : null;
        }
        return this.put((Pointer[])this.pointerArray);
    }

    public PointerPointer<P> put(long[] ... array) {
        this.pointerArray = new LongPointer[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.pointerArray[i] = array[i] != null ? new LongPointer(array[i]) : null;
        }
        return this.put((Pointer[])this.pointerArray);
    }

    public PointerPointer<P> put(float[] ... array) {
        this.pointerArray = new FloatPointer[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.pointerArray[i] = array[i] != null ? new FloatPointer(array[i]) : null;
        }
        return this.put((Pointer[])this.pointerArray);
    }

    public PointerPointer<P> put(double[] ... array) {
        this.pointerArray = new DoublePointer[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.pointerArray[i] = array[i] != null ? new DoublePointer(array[i]) : null;
        }
        return this.put((Pointer[])this.pointerArray);
    }

    public PointerPointer<P> put(char[] ... array) {
        this.pointerArray = new CharPointer[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.pointerArray[i] = array[i] != null ? new CharPointer(array[i]) : null;
        }
        return this.put((Pointer[])this.pointerArray);
    }

    public Pointer get() {
        return this.get(0L);
    }

    public P get(Class<P> cls) {
        return this.get(cls, 0L);
    }

    public Pointer get(long i) {
        return this.get(Pointer.class, i);
    }

    public native P get(Class<P> var1, long var2);

    public PointerPointer<P> put(Pointer p) {
        return this.put(0L, p);
    }

    public native PointerPointer<P> put(long var1, Pointer var3);

    static {
        block2: {
            logger = Logger.create(PointerPointer.class);
            try {
                Loader.load();
            }
            catch (Throwable t) {
                if (!logger.isDebugEnabled()) break block2;
                logger.debug("Could not load PointerPointer: " + t);
            }
        }
    }
}

