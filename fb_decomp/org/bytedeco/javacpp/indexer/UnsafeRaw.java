/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import java.lang.reflect.Field;
import org.bytedeco.javacpp.indexer.Raw;
import sun.misc.Unsafe;

class UnsafeRaw
extends Raw {
    protected static final Unsafe UNSAFE;
    protected static final long arrayOffset;

    UnsafeRaw() {
    }

    static boolean isAvailable() {
        return UNSAFE != null;
    }

    @Override
    byte getByte(long address) {
        return UNSAFE.getByte(address);
    }

    @Override
    void putByte(long address, byte b) {
        UNSAFE.putByte(address, b);
    }

    @Override
    short getShort(long address) {
        return UNSAFE.getShort(address);
    }

    @Override
    void putShort(long address, short s) {
        UNSAFE.putShort(address, s);
    }

    @Override
    int getInt(long address) {
        return UNSAFE.getInt(address);
    }

    @Override
    void putInt(long address, int i) {
        UNSAFE.putInt(address, i);
    }

    @Override
    long getLong(long address) {
        return UNSAFE.getLong(address);
    }

    @Override
    void putLong(long address, long l) {
        UNSAFE.putLong(address, l);
    }

    @Override
    float getFloat(long address) {
        return UNSAFE.getFloat(address);
    }

    @Override
    void putFloat(long address, float f) {
        UNSAFE.putFloat(address, f);
    }

    @Override
    double getDouble(long address) {
        return UNSAFE.getDouble(address);
    }

    @Override
    void putDouble(long address, double d) {
        UNSAFE.putDouble(address, d);
    }

    @Override
    char getChar(long address) {
        return UNSAFE.getChar(address);
    }

    @Override
    void putChar(long address, char c) {
        UNSAFE.putChar(address, c);
    }

    @Override
    boolean getBoolean(long address) {
        return UNSAFE.getByte(address) != 0;
    }

    @Override
    void putBoolean(long address, boolean b) {
        UNSAFE.putByte(address, b ? (byte)1 : 0);
    }

    @Override
    byte getByte(byte[] array, long offset) {
        return UNSAFE.getByte(array, arrayOffset + offset);
    }

    @Override
    void putByte(byte[] array, long offset, byte b) {
        UNSAFE.putByte(array, arrayOffset + offset, b);
    }

    @Override
    short getShort(byte[] array, long offset) {
        return UNSAFE.getShort(array, arrayOffset + offset);
    }

    @Override
    void putShort(byte[] array, long offset, short s) {
        UNSAFE.putShort(array, arrayOffset + offset, s);
    }

    @Override
    int getInt(byte[] array, long offset) {
        return UNSAFE.getInt(array, arrayOffset + offset);
    }

    @Override
    void putInt(byte[] array, long offset, int i) {
        UNSAFE.putInt(array, arrayOffset + offset, i);
    }

    @Override
    long getLong(byte[] array, long offset) {
        return UNSAFE.getLong(array, arrayOffset + offset);
    }

    @Override
    void putLong(byte[] array, long offset, long l) {
        UNSAFE.putLong(array, arrayOffset + offset, l);
    }

    @Override
    float getFloat(byte[] array, long offset) {
        return UNSAFE.getFloat(array, arrayOffset + offset);
    }

    @Override
    void putFloat(byte[] array, long offset, float f) {
        UNSAFE.putFloat(array, arrayOffset + offset, f);
    }

    @Override
    double getDouble(byte[] array, long offset) {
        return UNSAFE.getDouble(array, arrayOffset + offset);
    }

    @Override
    void putDouble(byte[] array, long offset, double d) {
        UNSAFE.putDouble(array, arrayOffset + offset, d);
    }

    @Override
    char getChar(byte[] array, long offset) {
        return UNSAFE.getChar(array, arrayOffset + offset);
    }

    @Override
    void putChar(byte[] array, long offset, char c) {
        UNSAFE.putChar(array, arrayOffset + offset, c);
    }

    @Override
    boolean getBoolean(byte[] array, long offset) {
        return UNSAFE.getBoolean(array, arrayOffset + offset);
    }

    @Override
    void putBoolean(byte[] array, long offset, boolean b) {
        UNSAFE.putBoolean(array, arrayOffset + offset, b);
    }

    static {
        long offset;
        Unsafe o;
        try {
            Class<?> c = Class.forName("sun.misc.Unsafe");
            Field f = c.getDeclaredField("theUnsafe");
            c.getDeclaredMethod("getByte", Long.TYPE);
            c.getDeclaredMethod("getShort", Long.TYPE);
            c.getDeclaredMethod("getInt", Long.TYPE);
            c.getDeclaredMethod("getLong", Long.TYPE);
            c.getDeclaredMethod("getFloat", Long.TYPE);
            c.getDeclaredMethod("getDouble", Long.TYPE);
            c.getDeclaredMethod("getChar", Long.TYPE);
            c.getDeclaredMethod("arrayBaseOffset", Class.class);
            f.setAccessible(true);
            o = (Unsafe)f.get(null);
            offset = o.arrayBaseOffset(byte[].class);
        }
        catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | SecurityException ex) {
            o = null;
            offset = 0L;
        }
        UNSAFE = o;
        arrayOffset = offset;
    }
}

