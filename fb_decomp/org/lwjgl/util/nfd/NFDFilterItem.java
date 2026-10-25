/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.system.Checks
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 *  org.lwjgl.system.NativeResource
 *  org.lwjgl.system.NativeType
 *  org.lwjgl.system.Struct
 *  org.lwjgl.system.Struct$Layout
 *  org.lwjgl.system.Struct$Member
 *  org.lwjgl.system.StructBuffer
 */
package org.lwjgl.util.nfd;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType(value="struct nfdfilteritem_t")
public class NFDFilterItem
extends Struct<NFDFilterItem>
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int NAME;
    public static final int SPEC;

    protected NFDFilterItem(long address, @Nullable ByteBuffer container) {
        super(address, container);
    }

    protected NFDFilterItem create(long address, @Nullable ByteBuffer container) {
        return new NFDFilterItem(address, container);
    }

    public NFDFilterItem(ByteBuffer container) {
        super(MemoryUtil.memAddress((ByteBuffer)container), NFDFilterItem.__checkContainer((ByteBuffer)container, (int)SIZEOF));
    }

    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="nfdchar_t const *")
    public ByteBuffer name() {
        return NFDFilterItem.nname(this.address());
    }

    @NativeType(value="nfdchar_t const *")
    public String nameString() {
        return NFDFilterItem.nnameString(this.address());
    }

    @NativeType(value="nfdchar_t const *")
    public ByteBuffer spec() {
        return NFDFilterItem.nspec(this.address());
    }

    @NativeType(value="nfdchar_t const *")
    public String specString() {
        return NFDFilterItem.nspecString(this.address());
    }

    public NFDFilterItem name(@NativeType(value="nfdchar_t const *") ByteBuffer value) {
        NFDFilterItem.nname(this.address(), value);
        return this;
    }

    public NFDFilterItem spec(@NativeType(value="nfdchar_t const *") ByteBuffer value) {
        NFDFilterItem.nspec(this.address(), value);
        return this;
    }

    public NFDFilterItem set(ByteBuffer name, ByteBuffer spec) {
        this.name(name);
        this.spec(spec);
        return this;
    }

    public NFDFilterItem set(NFDFilterItem src) {
        MemoryUtil.memCopy((long)src.address(), (long)this.address(), (long)SIZEOF);
        return this;
    }

    public static NFDFilterItem malloc() {
        return new NFDFilterItem(MemoryUtil.nmemAllocChecked((long)SIZEOF), null);
    }

    public static NFDFilterItem calloc() {
        return new NFDFilterItem(MemoryUtil.nmemCallocChecked((long)1L, (long)SIZEOF), null);
    }

    public static NFDFilterItem create() {
        ByteBuffer container = BufferUtils.createByteBuffer((int)SIZEOF);
        return new NFDFilterItem(MemoryUtil.memAddress((ByteBuffer)container), container);
    }

    public static NFDFilterItem create(long address) {
        return new NFDFilterItem(address, null);
    }

    @Nullable
    public static NFDFilterItem createSafe(long address) {
        return address == 0L ? null : new NFDFilterItem(address, null);
    }

    public static Buffer malloc(int capacity) {
        return new Buffer(MemoryUtil.nmemAllocChecked((long)NFDFilterItem.__checkMalloc((int)capacity, (int)SIZEOF)), capacity);
    }

    public static Buffer calloc(int capacity) {
        return new Buffer(MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
    }

    public static Buffer create(int capacity) {
        ByteBuffer container = NFDFilterItem.__create((int)capacity, (int)SIZEOF);
        return new Buffer(MemoryUtil.memAddress((ByteBuffer)container), container, -1, 0, capacity, capacity);
    }

    public static Buffer create(long address, int capacity) {
        return new Buffer(address, capacity);
    }

    @Nullable
    public static Buffer createSafe(long address, int capacity) {
        return address == 0L ? null : new Buffer(address, capacity);
    }

    public static NFDFilterItem malloc(MemoryStack stack) {
        return new NFDFilterItem(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    public static NFDFilterItem calloc(MemoryStack stack) {
        return new NFDFilterItem(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
    }

    public static Buffer malloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
    }

    public static Buffer calloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
    }

    public static ByteBuffer nname(long struct) {
        return MemoryUtil.memByteBufferNT1((long)MemoryUtil.memGetAddress((long)(struct + (long)NAME)));
    }

    public static String nnameString(long struct) {
        return MemoryUtil.memUTF8((long)MemoryUtil.memGetAddress((long)(struct + (long)NAME)));
    }

    public static ByteBuffer nspec(long struct) {
        return MemoryUtil.memByteBufferNT1((long)MemoryUtil.memGetAddress((long)(struct + (long)SPEC)));
    }

    public static String nspecString(long struct) {
        return MemoryUtil.memUTF8((long)MemoryUtil.memGetAddress((long)(struct + (long)SPEC)));
    }

    public static void nname(long struct, ByteBuffer value) {
        if (Checks.CHECKS) {
            Checks.checkNT1((ByteBuffer)value);
        }
        MemoryUtil.memPutAddress((long)(struct + (long)NAME), (long)MemoryUtil.memAddress((ByteBuffer)value));
    }

    public static void nspec(long struct, ByteBuffer value) {
        if (Checks.CHECKS) {
            Checks.checkNT1((ByteBuffer)value);
        }
        MemoryUtil.memPutAddress((long)(struct + (long)SPEC), (long)MemoryUtil.memAddress((ByteBuffer)value));
    }

    public static void validate(long struct) {
        Checks.check((long)MemoryUtil.memGetAddress((long)(struct + (long)NAME)));
        Checks.check((long)MemoryUtil.memGetAddress((long)(struct + (long)SPEC)));
    }

    static {
        Struct.Layout layout = NFDFilterItem.__struct((Struct.Member[])new Struct.Member[]{NFDFilterItem.__member((int)POINTER_SIZE), NFDFilterItem.__member((int)POINTER_SIZE)});
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        NAME = layout.offsetof(0);
        SPEC = layout.offsetof(1);
    }

    public static class Buffer
    extends StructBuffer<NFDFilterItem, Buffer>
    implements NativeResource {
        private static final NFDFilterItem ELEMENT_FACTORY = NFDFilterItem.create(-1L);

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

        protected NFDFilterItem getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="nfdchar_t const *")
        public ByteBuffer name() {
            return NFDFilterItem.nname(this.address());
        }

        @NativeType(value="nfdchar_t const *")
        public String nameString() {
            return NFDFilterItem.nnameString(this.address());
        }

        @NativeType(value="nfdchar_t const *")
        public ByteBuffer spec() {
            return NFDFilterItem.nspec(this.address());
        }

        @NativeType(value="nfdchar_t const *")
        public String specString() {
            return NFDFilterItem.nspecString(this.address());
        }

        public Buffer name(@NativeType(value="nfdchar_t const *") ByteBuffer value) {
            NFDFilterItem.nname(this.address(), value);
            return this;
        }

        public Buffer spec(@NativeType(value="nfdchar_t const *") ByteBuffer value) {
            NFDFilterItem.nspec(this.address(), value);
            return this;
        }
    }
}

