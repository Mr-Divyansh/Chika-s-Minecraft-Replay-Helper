/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.annotation.ValueGetter;
import org.bytedeco.javacpp.annotation.ValueSetter;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Logger;

@Properties(inherit={javacpp.class})
public class BytePointer
extends Pointer {
    private static final Logger logger;

    public BytePointer(String s, String charsetName) throws UnsupportedEncodingException {
        this(s.getBytes(charsetName).length + 1);
        this.putString(s, charsetName);
    }

    public BytePointer(String s, Charset charset) {
        this(s.getBytes(charset).length + 1);
        this.putString(s, charset);
    }

    public BytePointer(String s) {
        this(s.getBytes().length + 1);
        this.putString(s);
    }

    public BytePointer(byte ... array) {
        this(array.length);
        this.put(array);
    }

    public BytePointer(ByteBuffer buffer) {
        super(buffer);
        if (buffer != null && !buffer.isDirect() && buffer.hasArray()) {
            byte[] array = buffer.array();
            this.allocateArray(array.length - buffer.arrayOffset());
            this.put(array, buffer.arrayOffset(), array.length - buffer.arrayOffset());
            this.position(buffer.position());
            this.limit(buffer.limit());
        }
    }

    public BytePointer(long size) {
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
            OutOfMemoryError e2 = new OutOfMemoryError("Cannot allocate new BytePointer(" + size + "): totalBytes = " + BytePointer.formatBytes(BytePointer.totalBytes()) + ", physicalBytes = " + BytePointer.formatBytes(BytePointer.physicalBytes()));
            e2.initCause(e);
            throw e2;
        }
    }

    public BytePointer() {
    }

    public BytePointer(Pointer p) {
        super(p);
    }

    private native void allocateArray(long var1);

    public BytePointer position(long position) {
        return (BytePointer)super.position(position);
    }

    public BytePointer limit(long limit) {
        return (BytePointer)super.limit(limit);
    }

    public BytePointer capacity(long capacity) {
        return (BytePointer)super.capacity(capacity);
    }

    @Override
    public int sizeof() {
        return this.getClass() == BytePointer.class ? 1 : super.sizeof();
    }

    public BytePointer getPointer(long i) {
        return (BytePointer)new BytePointer(this).offsetAddress(i);
    }

    public byte[] getStringBytes() {
        long size = this.limit - this.position;
        if (size <= 0L) {
            size = BytePointer.strlen(this);
        }
        byte[] array = new byte[(int)Math.min(size, Integer.MAX_VALUE)];
        this.get(array);
        return array;
    }

    public String getString(String charsetName) throws UnsupportedEncodingException {
        return new String(this.getStringBytes(), charsetName);
    }

    public String getString(Charset charset) {
        return new String(this.getStringBytes(), charset);
    }

    public String getString() {
        return new String(this.getStringBytes());
    }

    public BytePointer putString(String s, String charsetName) throws UnsupportedEncodingException {
        byte[] bytes = s.getBytes(charsetName);
        return this.put(bytes).put(bytes.length, (byte)0).limit(bytes.length);
    }

    public BytePointer putString(String s, Charset charset) {
        byte[] bytes = s.getBytes(charset);
        return this.put(bytes).put(bytes.length, (byte)0).limit(bytes.length);
    }

    public BytePointer putString(String s) {
        byte[] bytes = s.getBytes();
        return this.put(bytes).put(bytes.length, (byte)0).limit(bytes.length);
    }

    public byte get() {
        return this.get(0L);
    }

    public native byte get(long var1);

    public BytePointer put(byte b) {
        return this.put(0L, b);
    }

    public native BytePointer put(long var1, byte var3);

    public BytePointer get(byte[] array) {
        return this.get(array, 0, array.length);
    }

    public BytePointer put(byte ... array) {
        return this.put(array, 0, array.length);
    }

    public native BytePointer get(byte[] var1, int var2, int var3);

    public native BytePointer put(byte[] var1, int var2, int var3);

    @Override
    public final ByteBuffer asBuffer() {
        return this.asByteBuffer();
    }

    public int getUnsigned() {
        return this.getUnsigned(0L);
    }

    public int getUnsigned(long i) {
        return this.get(i) & 0xFF;
    }

    public BytePointer putUnsigned(int b) {
        return this.putUnsigned(0L, b);
    }

    public BytePointer putUnsigned(long i, int b) {
        return this.put(i, (byte)b);
    }

    public short getShort() {
        return this.getShort(0L);
    }

    @ValueGetter
    @Cast(value={"short"})
    public native short getShort(long var1);

    public BytePointer putShort(short s) {
        return this.putShort(0L, s);
    }

    @ValueSetter
    @Cast(value={"short"})
    public native BytePointer putShort(long var1, short var3);

    public int getInt() {
        return this.getInt(0L);
    }

    @ValueGetter
    @Cast(value={"int"})
    public native int getInt(long var1);

    public BytePointer putInt(int j) {
        return this.putInt(0L, j);
    }

    @ValueSetter
    @Cast(value={"int"})
    public native BytePointer putInt(long var1, int var3);

    public long getLong() {
        return this.getLong(0L);
    }

    @ValueGetter
    @Cast(value={"long long"})
    public native long getLong(long var1);

    public BytePointer putLong(long j) {
        return this.putLong(0L, j);
    }

    @ValueSetter
    @Cast(value={"long long"})
    public native BytePointer putLong(long var1, long var3);

    public float getFloat() {
        return this.getFloat(0L);
    }

    @ValueGetter
    @Cast(value={"float"})
    public native float getFloat(long var1);

    public BytePointer putFloat(float f) {
        return this.putFloat(0L, f);
    }

    @ValueSetter
    @Cast(value={"float"})
    public native BytePointer putFloat(long var1, float var3);

    public double getDouble() {
        return this.getDouble(0L);
    }

    @ValueGetter
    @Cast(value={"double"})
    public native double getDouble(long var1);

    public BytePointer putDouble(double d) {
        return this.putDouble(0L, d);
    }

    @ValueSetter
    @Cast(value={"double"})
    public native BytePointer putDouble(long var1, double var3);

    public boolean getBool() {
        return this.getBool(0L);
    }

    @ValueGetter
    @Cast(value={"bool"})
    public native boolean getBool(long var1);

    public BytePointer putBool(boolean b) {
        return this.putBool(0L, b);
    }

    @ValueSetter
    @Cast(value={"bool"})
    public native BytePointer putBool(long var1, boolean var3);

    public char getChar() {
        return this.getChar(0L);
    }

    @ValueGetter
    @Cast(value={"short"})
    public native char getChar(long var1);

    public BytePointer putChar(char c) {
        return this.putChar(0L, c);
    }

    @ValueSetter
    @Cast(value={"short"})
    public native BytePointer putChar(long var1, char var3);

    public Pointer getPointerValue() {
        return this.getPointerValue(0L);
    }

    @ValueGetter
    @Cast(value={"void*"})
    public native Pointer getPointerValue(long var1);

    public BytePointer putPointerValue(Pointer p) {
        return this.putPointerValue(0L, p);
    }

    @ValueSetter
    @Cast(value={"void*"})
    public native BytePointer putPointerValue(long var1, Pointer var3);

    @Cast(value={"char*"})
    public static native BytePointer strcat(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"char*"}) BytePointer var1);

    @Cast(value={"char*"})
    public static native BytePointer strchr(@Cast(value={"char*"}) BytePointer var0, int var1);

    public static native int strcmp(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"char*"}) BytePointer var1);

    public static native int strcoll(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"char*"}) BytePointer var1);

    @Cast(value={"char*"})
    public static native BytePointer strcpy(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"char*"}) BytePointer var1);

    @Cast(value={"size_t"})
    public static native long strcspn(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"char*"}) BytePointer var1);

    @Cast(value={"char*"})
    public static native BytePointer strerror(int var0);

    @Cast(value={"size_t"})
    public static native long strlen(@Cast(value={"char*"}) BytePointer var0);

    @Cast(value={"char*"})
    public static native BytePointer strncat(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    public static native int strncmp(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @Cast(value={"char*"})
    public static native BytePointer strncpy(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    @Cast(value={"char*"})
    public static native BytePointer strpbrk(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"char*"}) BytePointer var1);

    @Cast(value={"char*"})
    public static native BytePointer strrchr(@Cast(value={"char*"}) BytePointer var0, int var1);

    @Cast(value={"size_t"})
    public static native long strspn(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"char*"}) BytePointer var1);

    @Cast(value={"char*"})
    public static native BytePointer strstr(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"char*"}) BytePointer var1);

    @Cast(value={"char*"})
    public static native BytePointer strtok(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"char*"}) BytePointer var1);

    @Cast(value={"size_t"})
    public static native long strxfrm(@Cast(value={"char*"}) BytePointer var0, @Cast(value={"char*"}) BytePointer var1, @Cast(value={"size_t"}) long var2);

    static {
        block2: {
            logger = Logger.create(BytePointer.class);
            try {
                Loader.load();
            }
            catch (Throwable t) {
                if (!logger.isDebugEnabled()) break block2;
                logger.debug("Could not load BytePointer: " + t);
            }
        }
    }
}

