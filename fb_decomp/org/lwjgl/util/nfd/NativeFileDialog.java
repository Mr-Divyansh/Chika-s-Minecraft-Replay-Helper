/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.lwjgl.PointerBuffer
 *  org.lwjgl.system.Checks
 *  org.lwjgl.system.CustomBuffer
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 *  org.lwjgl.system.NativeType
 *  org.lwjgl.system.Pointer
 *  org.lwjgl.system.Struct
 */
package org.lwjgl.util.nfd;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Pointer;
import org.lwjgl.system.Struct;
import org.lwjgl.util.nfd.LibNFD;
import org.lwjgl.util.nfd.NFDFilterItem;
import org.lwjgl.util.nfd.NFDPathSetEnum;

public class NativeFileDialog {
    public static final int NFD_ERROR = 0;
    public static final int NFD_OKAY = 1;
    public static final int NFD_CANCEL = 2;

    protected NativeFileDialog() {
        throw new UnsupportedOperationException();
    }

    public static native void nNFD_FreePath(long var0);

    public static void NFD_FreePath(@NativeType(value="nfdchar_t *") ByteBuffer filePath) {
        if (Checks.CHECKS) {
            Checks.checkNT1((ByteBuffer)filePath);
        }
        NativeFileDialog.nNFD_FreePath(MemoryUtil.memAddress((ByteBuffer)filePath));
    }

    public static void NFD_FreePath(@NativeType(value="nfdchar_t *") long filePath) {
        NativeFileDialog.nNFD_FreePath(filePath);
    }

    @NativeType(value="nfdresult_t")
    public static native int NFD_Init();

    public static native void NFD_Quit();

    public static native int nNFD_OpenDialog(long var0, long var2, int var4, long var5);

    @NativeType(value="nfdresult_t")
    public static int NFD_OpenDialog(@NativeType(value="nfdchar_t **") PointerBuffer outPath, @Nullable @NativeType(value="nfdfilteritem_t const *") NFDFilterItem.Buffer filterList, @Nullable @NativeType(value="nfdchar_t const *") ByteBuffer defaultPath) {
        if (Checks.CHECKS) {
            Checks.check((CustomBuffer)outPath, (int)1);
            Checks.checkNT1Safe((ByteBuffer)defaultPath);
            if (filterList != null) {
                Struct.validate((long)filterList.address(), (int)Checks.remainingSafe((CustomBuffer)filterList), (int)NFDFilterItem.SIZEOF, NFDFilterItem::validate);
            }
        }
        return NativeFileDialog.nNFD_OpenDialog(MemoryUtil.memAddress((CustomBuffer)outPath), MemoryUtil.memAddressSafe((Pointer)filterList), Checks.remainingSafe((CustomBuffer)filterList), MemoryUtil.memAddressSafe((ByteBuffer)defaultPath));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="nfdresult_t")
    public static int NFD_OpenDialog(@NativeType(value="nfdchar_t **") PointerBuffer outPath, @Nullable @NativeType(value="nfdfilteritem_t const *") NFDFilterItem.Buffer filterList, @Nullable @NativeType(value="nfdchar_t const *") CharSequence defaultPath) {
        if (Checks.CHECKS) {
            Checks.check((CustomBuffer)outPath, (int)1);
            if (filterList != null) {
                Struct.validate((long)filterList.address(), (int)Checks.remainingSafe((CustomBuffer)filterList), (int)NFDFilterItem.SIZEOF, NFDFilterItem::validate);
            }
        }
        MemoryStack stack = MemoryStack.stackGet();
        int stackPointer = stack.getPointer();
        try {
            stack.nUTF8Safe(defaultPath, true);
            long defaultPathEncoded = defaultPath == null ? 0L : stack.getPointerAddress();
            int n = NativeFileDialog.nNFD_OpenDialog(MemoryUtil.memAddress((CustomBuffer)outPath), MemoryUtil.memAddressSafe((Pointer)filterList), Checks.remainingSafe((CustomBuffer)filterList), defaultPathEncoded);
            return n;
        }
        finally {
            stack.setPointer(stackPointer);
        }
    }

    public static native int nNFD_OpenDialogMultiple(long var0, long var2, int var4, long var5);

    @NativeType(value="nfdresult_t")
    public static int NFD_OpenDialogMultiple(@NativeType(value="nfdpathset_t const **") PointerBuffer outPath, @Nullable @NativeType(value="nfdfilteritem_t const *") NFDFilterItem.Buffer filterList, @Nullable @NativeType(value="nfdchar_t const *") ByteBuffer defaultPath) {
        if (Checks.CHECKS) {
            Checks.check((CustomBuffer)outPath, (int)1);
            Checks.checkNT1Safe((ByteBuffer)defaultPath);
            if (filterList != null) {
                Struct.validate((long)filterList.address(), (int)Checks.remainingSafe((CustomBuffer)filterList), (int)NFDFilterItem.SIZEOF, NFDFilterItem::validate);
            }
        }
        return NativeFileDialog.nNFD_OpenDialogMultiple(MemoryUtil.memAddress((CustomBuffer)outPath), MemoryUtil.memAddressSafe((Pointer)filterList), Checks.remainingSafe((CustomBuffer)filterList), MemoryUtil.memAddressSafe((ByteBuffer)defaultPath));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="nfdresult_t")
    public static int NFD_OpenDialogMultiple(@NativeType(value="nfdpathset_t const **") PointerBuffer outPath, @Nullable @NativeType(value="nfdfilteritem_t const *") NFDFilterItem.Buffer filterList, @Nullable @NativeType(value="nfdchar_t const *") CharSequence defaultPath) {
        if (Checks.CHECKS) {
            Checks.check((CustomBuffer)outPath, (int)1);
            if (filterList != null) {
                Struct.validate((long)filterList.address(), (int)Checks.remainingSafe((CustomBuffer)filterList), (int)NFDFilterItem.SIZEOF, NFDFilterItem::validate);
            }
        }
        MemoryStack stack = MemoryStack.stackGet();
        int stackPointer = stack.getPointer();
        try {
            stack.nUTF8Safe(defaultPath, true);
            long defaultPathEncoded = defaultPath == null ? 0L : stack.getPointerAddress();
            int n = NativeFileDialog.nNFD_OpenDialogMultiple(MemoryUtil.memAddress((CustomBuffer)outPath), MemoryUtil.memAddressSafe((Pointer)filterList), Checks.remainingSafe((CustomBuffer)filterList), defaultPathEncoded);
            return n;
        }
        finally {
            stack.setPointer(stackPointer);
        }
    }

    public static native int nNFD_SaveDialog(long var0, long var2, int var4, long var5, long var7);

    @NativeType(value="nfdresult_t")
    public static int NFD_SaveDialog(@NativeType(value="nfdchar_t **") PointerBuffer outPath, @Nullable @NativeType(value="nfdfilteritem_t const *") NFDFilterItem.Buffer filterList, @Nullable @NativeType(value="nfdchar_t const *") ByteBuffer defaultPath, @Nullable @NativeType(value="nfdchar_t const *") ByteBuffer defaultName) {
        if (Checks.CHECKS) {
            Checks.check((CustomBuffer)outPath, (int)1);
            Checks.checkNT1Safe((ByteBuffer)defaultPath);
            Checks.checkNT1Safe((ByteBuffer)defaultName);
            if (filterList != null) {
                Struct.validate((long)filterList.address(), (int)Checks.remainingSafe((CustomBuffer)filterList), (int)NFDFilterItem.SIZEOF, NFDFilterItem::validate);
            }
        }
        return NativeFileDialog.nNFD_SaveDialog(MemoryUtil.memAddress((CustomBuffer)outPath), MemoryUtil.memAddressSafe((Pointer)filterList), Checks.remainingSafe((CustomBuffer)filterList), MemoryUtil.memAddressSafe((ByteBuffer)defaultPath), MemoryUtil.memAddressSafe((ByteBuffer)defaultName));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="nfdresult_t")
    public static int NFD_SaveDialog(@NativeType(value="nfdchar_t **") PointerBuffer outPath, @Nullable @NativeType(value="nfdfilteritem_t const *") NFDFilterItem.Buffer filterList, @Nullable @NativeType(value="nfdchar_t const *") CharSequence defaultPath, @Nullable @NativeType(value="nfdchar_t const *") CharSequence defaultName) {
        if (Checks.CHECKS) {
            Checks.check((CustomBuffer)outPath, (int)1);
            if (filterList != null) {
                Struct.validate((long)filterList.address(), (int)Checks.remainingSafe((CustomBuffer)filterList), (int)NFDFilterItem.SIZEOF, NFDFilterItem::validate);
            }
        }
        MemoryStack stack = MemoryStack.stackGet();
        int stackPointer = stack.getPointer();
        try {
            stack.nUTF8Safe(defaultPath, true);
            long defaultPathEncoded = defaultPath == null ? 0L : stack.getPointerAddress();
            stack.nUTF8Safe(defaultName, true);
            long defaultNameEncoded = defaultName == null ? 0L : stack.getPointerAddress();
            int n = NativeFileDialog.nNFD_SaveDialog(MemoryUtil.memAddress((CustomBuffer)outPath), MemoryUtil.memAddressSafe((Pointer)filterList), Checks.remainingSafe((CustomBuffer)filterList), defaultPathEncoded, defaultNameEncoded);
            return n;
        }
        finally {
            stack.setPointer(stackPointer);
        }
    }

    public static native int nNFD_PickFolder(long var0, long var2);

    @NativeType(value="nfdresult_t")
    public static int NFD_PickFolder(@NativeType(value="nfdchar_t **") PointerBuffer outPath, @Nullable @NativeType(value="nfdchar_t const *") ByteBuffer defaultPath) {
        if (Checks.CHECKS) {
            Checks.check((CustomBuffer)outPath, (int)1);
            Checks.checkNT1Safe((ByteBuffer)defaultPath);
        }
        return NativeFileDialog.nNFD_PickFolder(MemoryUtil.memAddress((CustomBuffer)outPath), MemoryUtil.memAddressSafe((ByteBuffer)defaultPath));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="nfdresult_t")
    public static int NFD_PickFolder(@NativeType(value="nfdchar_t **") PointerBuffer outPath, @Nullable @NativeType(value="nfdchar_t const *") CharSequence defaultPath) {
        if (Checks.CHECKS) {
            Checks.check((CustomBuffer)outPath, (int)1);
        }
        MemoryStack stack = MemoryStack.stackGet();
        int stackPointer = stack.getPointer();
        try {
            stack.nUTF8Safe(defaultPath, true);
            long defaultPathEncoded = defaultPath == null ? 0L : stack.getPointerAddress();
            int n = NativeFileDialog.nNFD_PickFolder(MemoryUtil.memAddress((CustomBuffer)outPath), defaultPathEncoded);
            return n;
        }
        finally {
            stack.setPointer(stackPointer);
        }
    }

    public static native long nNFD_GetError();

    @Nullable
    @NativeType(value="char const *")
    public static String NFD_GetError() {
        long __result = NativeFileDialog.nNFD_GetError();
        return MemoryUtil.memUTF8Safe((long)__result);
    }

    public static native void NFD_ClearError();

    public static native int nNFD_PathSet_GetCount(long var0, long var2);

    @NativeType(value="nfdresult_t")
    public static int NFD_PathSet_GetCount(@NativeType(value="nfdpathset_t const *") long pathSet, @NativeType(value="nfdpathsetsize_t *") IntBuffer count) {
        if (Checks.CHECKS) {
            Checks.check((long)pathSet);
            Checks.check((Buffer)count, (int)1);
        }
        return NativeFileDialog.nNFD_PathSet_GetCount(pathSet, MemoryUtil.memAddress((IntBuffer)count));
    }

    public static native int nNFD_PathSet_GetPath(long var0, int var2, long var3);

    @NativeType(value="nfdresult_t")
    public static int NFD_PathSet_GetPath(@NativeType(value="nfdpathset_t const *") long pathSet, @NativeType(value="nfdpathsetsize_t") int index, @NativeType(value="nfdchar_t **") PointerBuffer outPath) {
        if (Checks.CHECKS) {
            Checks.check((long)pathSet);
            Checks.check((CustomBuffer)outPath, (int)1);
        }
        return NativeFileDialog.nNFD_PathSet_GetPath(pathSet, index, MemoryUtil.memAddress((CustomBuffer)outPath));
    }

    public static native void nNFD_PathSet_FreePath(long var0);

    public static void NFD_PathSet_FreePath(@NativeType(value="nfdchar_t *") ByteBuffer filePath) {
        if (Checks.CHECKS) {
            Checks.checkNT1((ByteBuffer)filePath);
        }
        NativeFileDialog.nNFD_PathSet_FreePath(MemoryUtil.memAddress((ByteBuffer)filePath));
    }

    public static void NFD_PathSet_FreePath(@NativeType(value="nfdchar_t *") long filePath) {
        NativeFileDialog.nNFD_PathSet_FreePath(filePath);
    }

    public static native int nNFD_PathSet_GetEnum(long var0, long var2);

    @NativeType(value="nfdresult_t")
    public static int NFD_PathSet_GetEnum(@NativeType(value="nfdpathset_t const *") long pathSet, @NativeType(value="nfdpathsetenum_t *") NFDPathSetEnum outEnumerator) {
        if (Checks.CHECKS) {
            Checks.check((long)pathSet);
        }
        return NativeFileDialog.nNFD_PathSet_GetEnum(pathSet, outEnumerator.address());
    }

    public static native void nNFD_PathSet_FreeEnum(long var0);

    public static void NFD_PathSet_FreeEnum(@NativeType(value="nfdpathsetenum_t *") NFDPathSetEnum enumerator) {
        NativeFileDialog.nNFD_PathSet_FreeEnum(enumerator.address());
    }

    public static native int nNFD_PathSet_EnumNext(long var0, long var2);

    @NativeType(value="nfdresult_t")
    public static int NFD_PathSet_EnumNext(@NativeType(value="nfdpathsetenum_t *") NFDPathSetEnum enumerator, @NativeType(value="nfdchar_t **") PointerBuffer outPath) {
        if (Checks.CHECKS) {
            Checks.check((CustomBuffer)outPath, (int)1);
        }
        return NativeFileDialog.nNFD_PathSet_EnumNext(enumerator.address(), MemoryUtil.memAddress((CustomBuffer)outPath));
    }

    public static native void nNFD_PathSet_Free(long var0);

    public static void NFD_PathSet_Free(@NativeType(value="nfdpathset_t const *") long pathSet) {
        if (Checks.CHECKS) {
            Checks.check((long)pathSet);
        }
        NativeFileDialog.nNFD_PathSet_Free(pathSet);
    }

    public static native int nNFD_PathSet_GetCount(long var0, int[] var2);

    @NativeType(value="nfdresult_t")
    public static int NFD_PathSet_GetCount(@NativeType(value="nfdpathset_t const *") long pathSet, @NativeType(value="nfdpathsetsize_t *") int[] count) {
        if (Checks.CHECKS) {
            Checks.check((long)pathSet);
            Checks.check((int[])count, (int)1);
        }
        return NativeFileDialog.nNFD_PathSet_GetCount(pathSet, count);
    }

    static {
        LibNFD.initialize();
    }
}

