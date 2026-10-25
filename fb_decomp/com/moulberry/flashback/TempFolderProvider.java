/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 */
package com.moulberry.flashback;

import com.moulberry.flashback.Flashback;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.HashSet;
import java.util.UUID;
import org.apache.commons.io.FileUtils;

public class TempFolderProvider {
    private static Path getSharedTempFolder() {
        return Flashback.getDataDirectory().resolve("temp");
    }

    public static Path getTypedTempFolder(TempFolderType type) {
        return TempFolderProvider.getSharedTempFolder().resolve(type.id);
    }

    public static void tryDeleteStaleFolders(TempFolderType type) {
        Path tempFolder = TempFolderProvider.getSharedTempFolder();
        if (!Files.exists(tempFolder, new LinkOption[0])) {
            return;
        }
        Path typedTempFolder = tempFolder.resolve(type.id);
        if (!Files.exists(typedTempFolder, new LinkOption[0])) {
            return;
        }
        HashSet<Path> toDelete = new HashSet<Path>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(typedTempFolder);){
            for (Path path : directoryStream) {
                if (!Files.isDirectory(path, new LinkOption[0])) continue;
                Path lockFile = path.resolve("flashback_pid");
                String pidStr = "?";
                boolean isStillInUse = false;
                try {
                    pidStr = Files.readString(lockFile);
                    long pid = Long.parseLong(pidStr);
                    isStillInUse = ProcessHandle.of(pid).isPresent();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (!isStillInUse) {
                    toDelete.add(path);
                    continue;
                }
                Flashback.LOGGER.error("Cannot delete stale temp folder {}, pid {} is still in use", (Object)tempFolder.relativize(path), (Object)pidStr);
            }
        }
        catch (IOException e) {
            Flashback.LOGGER.error("Failed to find stale temp folders to delete", (Throwable)e);
        }
        for (Path path : toDelete) {
            Flashback.LOGGER.info("Deleting stale temp folder {}", (Object)tempFolder.relativize(path));
            try {
                FileUtils.deleteDirectory((File)path.toFile());
            }
            catch (Exception e) {
                Flashback.LOGGER.error("Failed to delete stale temp folder", (Throwable)e);
            }
        }
        TempFolderProvider.deleteDirectoryIfEmpty(typedTempFolder);
        TempFolderProvider.deleteDirectoryIfEmpty(tempFolder);
    }

    public static Path createTemp(TempFolderType type, UUID uuid) {
        Path path = TempFolderProvider.getTempPath(type, uuid);
        if (!Files.exists(path, new LinkOption[0])) {
            try {
                Files.createDirectories(path, new FileAttribute[0]);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            long pid = ProcessHandle.current().pid();
            try {
                Files.writeString(path.resolve("flashback_pid"), (CharSequence)String.valueOf(pid), new OpenOption[0]);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return path;
    }

    private static Path getTempPath(TempFolderType type, UUID uuid) {
        return TempFolderProvider.getSharedTempFolder().resolve(type.id).resolve(uuid.toString());
    }

    public static void deleteTemp(TempFolderType type, UUID uuid) {
        Path path = TempFolderProvider.getTempPath(type, uuid);
        try {
            FileUtils.deleteDirectory((File)path.toFile());
        }
        catch (Exception e) {
            Flashback.LOGGER.error("Failed to delete temp directory", (Throwable)e);
        }
        TempFolderProvider.deleteDirectoryIfEmpty(path.getParent());
        TempFolderProvider.deleteDirectoryIfEmpty(path.getParent().getParent());
    }

    public static void deleteDirectoryIfEmpty(Path path) {
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        try {
            boolean empty;
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path);){
                empty = !stream.iterator().hasNext();
            }
            if (empty) {
                Files.deleteIfExists(path);
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public static enum TempFolderType {
        SERVER("server"),
        RECORDING("recording");

        private final String id;

        private TempFolderType(String id) {
            this.id = id;
        }
    }
}

