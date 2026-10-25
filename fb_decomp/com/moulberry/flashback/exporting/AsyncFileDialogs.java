/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.Util
 *  net.minecraft.util.Util$OS
 *  org.lwjgl.PointerBuffer
 *  org.lwjgl.system.MemoryStack
 */
package com.moulberry.flashback.exporting;

import com.moulberry.flashback.utils.NamedDaemonThreadFactory;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.nfd.NFDFilterItem;
import org.lwjgl.util.nfd.NativeFileDialog;

public class AsyncFileDialogs {
    private static final boolean ON_OSX = Util.getPlatform() == Util.OS.OSX;
    private static CompletableFuture<String> currentSaveOrOpenFileDialog = null;
    private static final ExecutorService dialogThread = Executors.newSingleThreadExecutor(new NamedDaemonThreadFactory("FlashbackFileDialogs"));
    private static boolean initializedNfd = false;

    public static boolean hasDialog() {
        return currentSaveOrOpenFileDialog != null;
    }

    public static CompletableFuture<String> saveFileDialog(String defaultPath, String defaultName, String filterDescription, String ... filters) {
        if (AsyncFileDialogs.hasDialog()) {
            return CompletableFuture.completedFuture(null);
        }
        CompletableFuture<String> future = currentSaveOrOpenFileDialog = new CompletableFuture();
        boolean initializedNfd = AsyncFileDialogs.initializedNfd;
        AsyncFileDialogs.initializedNfd = true;
        Runnable runnable = () -> {
            if (!initializedNfd) {
                NativeFileDialog.NFD_Init();
            }
            try (MemoryStack stack = MemoryStack.stackPush();){
                PointerBuffer out = stack.callocPointer(1);
                StringBuilder filterBuilder = new StringBuilder();
                for (String filter : filters) {
                    if (!filterBuilder.isEmpty()) {
                        filterBuilder.append(",");
                    }
                    filterBuilder.append(AsyncFileDialogs.filter(filter));
                }
                NFDFilterItem.Buffer filtersBuffer = NFDFilterItem.malloc(1);
                ((NFDFilterItem)filtersBuffer.get(0)).name(stack.UTF8((CharSequence)AsyncFileDialogs.filter(filterDescription))).spec(stack.UTF8((CharSequence)filterBuilder.toString()));
                int result = NativeFileDialog.NFD_SaveDialog(out, filtersBuffer, AsyncFileDialogs.filter(defaultPath), AsyncFileDialogs.filter(defaultName));
                if (result != 1) {
                    currentSaveOrOpenFileDialog.complete(null);
                    currentSaveOrOpenFileDialog = null;
                } else {
                    currentSaveOrOpenFileDialog.complete(out.getStringUTF8(0));
                    currentSaveOrOpenFileDialog = null;
                    NativeFileDialog.NFD_FreePath(out.get(0));
                }
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
            finally {
                if (currentSaveOrOpenFileDialog != null) {
                    currentSaveOrOpenFileDialog.complete(null);
                    currentSaveOrOpenFileDialog = null;
                }
            }
        };
        if (ON_OSX) {
            Minecraft.getInstance().submit(runnable);
        } else {
            dialogThread.submit(runnable);
        }
        return future;
    }

    public static CompletableFuture<String> openFileDialog(String defaultPath, String filterDescription, String ... filters) {
        if (AsyncFileDialogs.hasDialog()) {
            return CompletableFuture.completedFuture(null);
        }
        CompletableFuture<String> future = currentSaveOrOpenFileDialog = new CompletableFuture();
        boolean initializedNfd = AsyncFileDialogs.initializedNfd;
        AsyncFileDialogs.initializedNfd = true;
        Runnable runnable = () -> {
            if (!initializedNfd) {
                NativeFileDialog.NFD_Init();
            }
            try (MemoryStack stack = MemoryStack.stackPush();){
                PointerBuffer out = stack.callocPointer(1);
                StringBuilder filterBuilder = new StringBuilder();
                for (String filter : filters) {
                    if (!filterBuilder.isEmpty()) {
                        filterBuilder.append(",");
                    }
                    filterBuilder.append(AsyncFileDialogs.filter(filter));
                }
                NFDFilterItem.Buffer filtersBuffer = NFDFilterItem.malloc(1);
                ((NFDFilterItem)filtersBuffer.get(0)).name(stack.UTF8((CharSequence)AsyncFileDialogs.filter(filterDescription))).spec(stack.UTF8((CharSequence)filterBuilder.toString()));
                int result = NativeFileDialog.NFD_OpenDialog(out, filtersBuffer, AsyncFileDialogs.filter(defaultPath));
                if (result != 1) {
                    currentSaveOrOpenFileDialog.complete(null);
                    currentSaveOrOpenFileDialog = null;
                } else {
                    currentSaveOrOpenFileDialog.complete(out.getStringUTF8(0));
                    currentSaveOrOpenFileDialog = null;
                    NativeFileDialog.NFD_FreePath(out.get(0));
                }
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
            finally {
                if (currentSaveOrOpenFileDialog != null) {
                    currentSaveOrOpenFileDialog.complete(null);
                    currentSaveOrOpenFileDialog = null;
                }
            }
        };
        if (ON_OSX) {
            Minecraft.getInstance().submit(runnable);
        } else {
            dialogThread.submit(runnable);
        }
        return future;
    }

    public static CompletableFuture<String> openFolderDialog(String defaultPath) {
        if (AsyncFileDialogs.hasDialog()) {
            return CompletableFuture.completedFuture(null);
        }
        CompletableFuture<String> future = currentSaveOrOpenFileDialog = new CompletableFuture();
        boolean initializedNfd = AsyncFileDialogs.initializedNfd;
        AsyncFileDialogs.initializedNfd = true;
        Runnable runnable = () -> {
            if (!initializedNfd) {
                NativeFileDialog.NFD_Init();
            }
            try (MemoryStack stack = MemoryStack.stackPush();){
                PointerBuffer out = stack.callocPointer(1);
                int result = NativeFileDialog.NFD_PickFolder(out, AsyncFileDialogs.filter(defaultPath));
                if (result != 1) {
                    currentSaveOrOpenFileDialog.complete(null);
                    currentSaveOrOpenFileDialog = null;
                } else {
                    currentSaveOrOpenFileDialog.complete(out.getStringUTF8(0));
                    currentSaveOrOpenFileDialog = null;
                    NativeFileDialog.NFD_FreePath(out.get(0));
                }
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
            finally {
                if (currentSaveOrOpenFileDialog != null) {
                    currentSaveOrOpenFileDialog.complete(null);
                    currentSaveOrOpenFileDialog = null;
                }
            }
        };
        if (ON_OSX) {
            Minecraft.getInstance().submit(runnable);
        } else {
            dialogThread.submit(runnable);
        }
        return future;
    }

    public static String filter(CharSequence in) {
        return AsyncFileDialogs.filterLT20(in.toString().replace("'", "").replace("\"", "").replace("$", "").replace("`", ""));
    }

    public static String filterLT20(CharSequence in) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < in.length(); ++i) {
            char c = in.charAt(i);
            if (c < ' ' && c != '\n') continue;
            builder.append(c);
        }
        return builder.toString();
    }
}

