/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import java.nio.CharBuffer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Logger;

@Properties(inherit={javacpp.class})
public class CharPointer
extends Pointer {
    private static final Logger logger;

    public CharPointer(String s) {
        this(s.toCharArray().length + 1);
        this.putString(s);
    }

    public CharPointer(char ... array) {
        this(array.length);
        this.put(array);
    }

    public CharPointer(CharBuffer buffer) {
        super(buffer);
        if (buffer != null && !buffer.isDirect() && buffer.hasArray()) {
            char[] array = buffer.array();
            this.allocateArray(array.length - buffer.arrayOffset());
            this.put(array, buffer.arrayOffset(), array.length - buffer.arrayOffset());
            this.position(buffer.position());
            this.limit(buffer.limit());
        }
    }

    public CharPointer(long size) {
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
            OutOfMemoryError e2 = new OutOfMemoryError("Cannot allocate new CharPointer(" + size + "): totalBytes = " + CharPointer.formatBytes(CharPointer.totalBytes()) + ", physicalBytes = " + CharPointer.formatBytes(CharPointer.physicalBytes()));
            e2.initCause(e);
            throw e2;
        }
    }

    public CharPointer() {
    }

    public CharPointer(Pointer p) {
        super(p);
    }

    private native void allocateArray(long var1);

    public CharPointer position(long position) {
        return (CharPointer)super.position(position);
    }

    public CharPointer limit(long limit) {
        return (CharPointer)super.limit(limit);
    }

    public CharPointer capacity(long capacity) {
        return (CharPointer)super.capacity(capacity);
    }

    @Override
    public int sizeof() {
        return this.getClass() == CharPointer.class ? 2 : super.sizeof();
    }

    public CharPointer getPointer(long i) {
        return (CharPointer)new CharPointer(this).offsetAddress(i);
    }

    public char[] getStringChars() {
        char[] newbuffer;
        if (this.limit > this.position) {
            char[] array = new char[(int)Math.min(this.limit - this.position, Integer.MAX_VALUE)];
            this.get(array);
            return array;
        }
        char[] buffer = new char[16];
        int i = 0;
        while ((buffer[i] = this.get(i)) != '\u0000') {
            if (++i < buffer.length) continue;
            newbuffer = new char[2 * buffer.length];
            System.arraycopy(buffer, 0, newbuffer, 0, buffer.length);
            buffer = newbuffer;
        }
        newbuffer = new char[i];
        System.arraycopy(buffer, 0, newbuffer, 0, i);
        return newbuffer;
    }

    public String getString() {
        return new String(this.getStringChars());
    }

    public CharPointer putString(String s) {
        char[] chars = s.toCharArray();
        return this.put(chars).put(chars.length, '\u0000').limit(chars.length);
    }

    public char get() {
        return this.get(0L);
    }

    public native char get(long var1);

    public CharPointer put(char c) {
        return this.put(0L, c);
    }

    public native CharPointer put(long var1, char var3);

    public CharPointer get(char[] array) {
        return this.get(array, 0, array.length);
    }

    public CharPointer put(char ... array) {
        return this.put(array, 0, array.length);
    }

    public native CharPointer get(char[] var1, int var2, int var3);

    public native CharPointer put(char[] var1, int var2, int var3);

    @Override
    public final CharBuffer asBuffer() {
        return this.asByteBuffer().asCharBuffer();
    }

    static {
        block2: {
            logger = Logger.create(CharPointer.class);
            try {
                Loader.load();
            }
            catch (Throwable t) {
                if (!logger.isDebugEnabled()) break block2;
                logger.debug("Could not load CharPointer: " + t);
            }
        }
    }
}

