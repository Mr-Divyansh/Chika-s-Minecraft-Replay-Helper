/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.system.Configuration
 *  org.lwjgl.system.Library
 *  org.lwjgl.system.MemoryUtil
 *  org.lwjgl.system.MemoryUtil$MemoryAllocator
 *  org.lwjgl.system.Platform
 */
package org.lwjgl.util.nfd;

import org.lwjgl.system.Configuration;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Platform;

final class LibNFD {
    private LibNFD() {
    }

    static void initialize() {
    }

    private static native void setupMalloc(long var0, long var2, long var4, long var6, long var8, long var10);

    static {
        String libName = Platform.mapLibraryNameBundled((String)(Platform.get() == Platform.LINUX && (Boolean)Configuration.NFD_LINUX_PORTAL.get((Object)false) != false ? "lwjgl_nfd_portal" : "lwjgl_nfd"));
        Library.loadSystem(System::load, System::loadLibrary, LibNFD.class, (String)"org.lwjgl.nfd", (String)libName);
        MemoryUtil.MemoryAllocator allocator = MemoryUtil.getAllocator((boolean)((Boolean)Configuration.DEBUG_MEMORY_ALLOCATOR_INTERNAL.get((Object)true)));
        LibNFD.setupMalloc(allocator.getMalloc(), allocator.getCalloc(), allocator.getRealloc(), allocator.getFree(), allocator.getAlignedAlloc(), allocator.getAlignedFree());
    }
}

