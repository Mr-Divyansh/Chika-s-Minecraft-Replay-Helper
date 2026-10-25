/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 *  org.lwjgl.system.NativeResource
 *  org.lwjgl.system.NativeType
 *  org.lwjgl.system.Struct
 *  org.lwjgl.system.StructBuffer
 */
package org.lwjgl.util.nfd;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;
import org.lwjgl.util.nfd.LibNFD;

@NativeType(value="struct nfdpathsetenum_t")
public class NFDPathSetEnum
extends Struct<NFDPathSetEnum>
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;

    private static native int offsets(long var0);

    protected NFDPathSetEnum(long address, @Nullable ByteBuffer container) {
        super(address, container);
    }

    protected NFDPathSetEnum create(long address, @Nullable ByteBuffer container) {
        return new NFDPathSetEnum(address, container);
    }

    public NFDPathSetEnum(ByteBuffer container) {
        super(MemoryUtil.memAddress((ByteBuffer)container), NFDPathSetEnum.__checkContainer((ByteBuffer)container, (int)SIZEOF));
    }

    public int sizeof() {
        return SIZEOF;
    }

    public static NFDPathSetEnum malloc() {
        return new NFDPathSetEnum(MemoryUtil.nmemAllocChecked((long)SIZEOF), null);
    }

    public static NFDPathSetEnum calloc() {
        return new NFDPathSetEnum(MemoryUtil.nmemCallocChecked((long)1L, (long)SIZEOF), null);
    }

    public static NFDPathSetEnum create() {
        ByteBuffer container = BufferUtils.createByteBuffer((int)SIZEOF);
        return new NFDPathSetEnum(MemoryUtil.memAddress((ByteBuffer)container), container);
    }

    public static NFDPathSetEnum create(long address) {
        return new NFDPathSetEnum(address, null);
    }

    @Nullable
    public static NFDPathSetEnum createSafe(long address) {
        return address == 0L ? null : new NFDPathSetEnum(address, null);
    }

    public static Buffer malloc(int capacity) {
        return new Buffer(MemoryUtil.nmemAllocChecked((long)NFDPathSetEnum.__checkMalloc((int)capacity, (int)SIZEOF)), capacity);
    }

    public static Buffer calloc(int capacity) {
        return new Buffer(MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
    }

    public static Buffer create(int capacity) {
        ByteBuffer container = NFDPathSetEnum.__create((int)capacity, (int)SIZEOF);
        return new Buffer(MemoryUtil.memAddress((ByteBuffer)container), container, -1, 0, capacity, capacity);
    }

    public static Buffer create(long address, int capacity) {
        return new Buffer(address, capacity);
    }

    @Nullable
    public static Buffer createSafe(long address, int capacity) {
        return address == 0L ? null : new Buffer(address, capacity);
    }

    public static NFDPathSetEnum malloc(MemoryStack stack) {
        return new NFDPathSetEnum(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    public static NFDPathSetEnum calloc(MemoryStack stack) {
        return new NFDPathSetEnum(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
    }

    public static Buffer malloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
    }

    public static Buffer calloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
    }

    static {
        LibNFD.initialize();
        try (MemoryStack stack = MemoryStack.stackPush();){
            IntBuffer offsets = stack.mallocInt(1);
            SIZEOF = NFDPathSetEnum.offsets(MemoryUtil.memAddress((IntBuffer)offsets));
            ALIGNOF = offsets.get(0);
        }
    }

    public static class Buffer
    extends StructBuffer<NFDPathSetEnum, Buffer>
    implements NativeResource {
        private static final NFDPathSetEnum ELEMENT_FACTORY = NFDPathSetEnum.create(-1L);

        public Buffer(ByteBuffer container) {
            super(container, container.remaining() / SIZEOF);
        }

        public Buffer(long address, int cap) {
            super(address, null, -1, 0, cap, cap);
        }

        Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
            super(address, container, mark, pos, lim, cap);
        }

        protected Buffer self() {
            return this;
        }

        protected NFDPathSetEnum getElementFactory() {
            return ELEMENT_FACTORY;
        }
    }
}

