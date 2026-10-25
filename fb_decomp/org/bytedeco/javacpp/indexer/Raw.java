/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacpp.indexer;

import org.bytedeco.javacpp.indexer.UnsafeRaw;

abstract class Raw {
    static final Raw INSTANCE = UnsafeRaw.isAvailable() ? new UnsafeRaw() : null;

    Raw() {
    }

    static Raw getInstance() {
        return INSTANCE;
    }

    abstract byte getByte(long var1);

    abstract void putByte(long var1, byte var3);

    abstract short getShort(long var1);

    abstract void putShort(long var1, short var3);

    abstract int getInt(long var1);

    abstract void putInt(long var1, int var3);

    abstract long getLong(long var1);

    abstract void putLong(long var1, long var3);

    abstract float getFloat(long var1);

    abstract void putFloat(long var1, float var3);

    abstract double getDouble(long var1);

    abstract void putDouble(long var1, double var3);

    abstract char getChar(long var1);

    abstract void putChar(long var1, char var3);

    abstract boolean getBoolean(long var1);

    abstract void putBoolean(long var1, boolean var3);

    abstract byte getByte(byte[] var1, long var2);

    abstract void putByte(byte[] var1, long var2, byte var4);

    abstract short getShort(byte[] var1, long var2);

    abstract void putShort(byte[] var1, long var2, short var4);

    abstract int getInt(byte[] var1, long var2);

    abstract void putInt(byte[] var1, long var2, int var4);

    abstract long getLong(byte[] var1, long var2);

    abstract void putLong(byte[] var1, long var2, long var4);

    abstract float getFloat(byte[] var1, long var2);

    abstract void putFloat(byte[] var1, long var2, float var4);

    abstract double getDouble(byte[] var1, long var2);

    abstract void putDouble(byte[] var1, long var2, double var4);

    abstract char getChar(byte[] var1, long var2);

    abstract void putChar(byte[] var1, long var2, char var4);

    abstract boolean getBoolean(byte[] var1, long var2);

    abstract void putBoolean(byte[] var1, long var2, boolean var4);
}

