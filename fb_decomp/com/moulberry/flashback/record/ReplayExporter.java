/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  org.apache.commons.io.FileUtils
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.record;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.record.FlashbackMeta;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;

public class ReplayExporter {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void export(Path recordFolder, Path outputFile, @Nullable String name) {
        Flashback.LOGGER.info("Exporting {} to {}", (Object)recordFolder, (Object)outputFile);
        FlashbackMeta meta = ReplayExporter.tryReadMeta(recordFolder.resolve("metadata.json"));
        if (meta == null) {
            meta = ReplayExporter.tryReadMeta(recordFolder.resolve("metadata.json.old"));
        }
        if (meta == null) {
            Flashback.LOGGER.error("Cannot export, both metadata files are invalid");
            return;
        }
        if (name != null) {
            meta.name = name;
        }
        meta.chunks.keySet().removeIf(chunkName -> {
            Path chunkPath = recordFolder.resolve((String)chunkName);
            if (!Files.exists(chunkPath, new LinkOption[0])) {
                Flashback.LOGGER.warn("Cannot find chunk path: {}, skipping", (Object)chunkPath);
                return true;
            }
            return false;
        });
        if (meta.chunks.isEmpty()) {
            Flashback.LOGGER.error("Cannot export, no chunk files exist");
            return;
        }
        try {
            Files.createDirectories(outputFile.getParent(), new FileAttribute[0]);
        }
        catch (IOException e) {
            Flashback.LOGGER.error("Unable to create parent directories", (Throwable)e);
        }
        try {
            Path iconPath;
            Path levelChunkCachePath;
            FileOutputStream fos = new FileOutputStream(outputFile.toFile());
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ZipOutputStream zipOut = new ZipOutputStream(bos);
            zipOut.setLevel(1);
            ZipEntry zipEntry = new ZipEntry("metadata.json");
            zipOut.putNextEntry(zipEntry);
            zipOut.write(GSON.toJson((JsonElement)meta.toJson()).getBytes(StandardCharsets.UTF_8));
            zipOut.closeEntry();
            Path levelChunkCaches = recordFolder.resolve("level_chunk_caches");
            if (Files.exists(levelChunkCaches, new LinkOption[0]) && Files.isDirectory(levelChunkCaches, new LinkOption[0])) {
                try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(levelChunkCaches);){
                    for (Path path : directoryStream) {
                        zipEntry = new ZipEntry("level_chunk_caches/" + path.getFileName().toString());
                        zipOut.putNextEntry(zipEntry);
                        Files.copy(path, zipOut);
                        zipOut.closeEntry();
                    }
                }
            }
            if (Files.exists(levelChunkCachePath = recordFolder.resolve("level_chunk_cache"), new LinkOption[0])) {
                zipEntry = new ZipEntry("level_chunk_cache");
                zipOut.putNextEntry(zipEntry);
                Files.copy(levelChunkCachePath, zipOut);
                zipOut.closeEntry();
            }
            if (Files.exists(iconPath = recordFolder.resolve("icon.png"), new LinkOption[0])) {
                zipEntry = new ZipEntry("icon.png");
                zipOut.putNextEntry(zipEntry);
                Files.copy(iconPath, zipOut);
                zipOut.closeEntry();
            }
            for (String chunkName2 : meta.chunks.keySet()) {
                Path chunkPath = recordFolder.resolve(chunkName2);
                zipEntry = new ZipEntry(chunkName2);
                zipOut.putNextEntry(zipEntry);
                Files.copy(chunkPath, zipOut);
                zipOut.closeEntry();
            }
            zipOut.close();
            bos.close();
            fos.close();
            try {
                FileUtils.deleteDirectory((File)recordFolder.toFile());
            }
            catch (Exception exception) {
                Flashback.LOGGER.error("Exception deleting record folder", (Throwable)exception);
            }
        }
        catch (Exception e) {
            Flashback.LOGGER.error("Exception exporting replay", (Throwable)e);
        }
    }

    @Nullable
    private static FlashbackMeta tryReadMeta(Path file) {
        Flashback.LOGGER.info("Trying to read metadata json {}", (Object)file);
        if (!Files.exists(file, new LinkOption[0])) {
            Flashback.LOGGER.error("Metadata JSON doesn't exist!");
            return null;
        }
        try {
            String metaString = Files.readString(file);
            if (metaString.isBlank()) {
                Flashback.LOGGER.error("Metadata JSON is blank");
                return null;
            }
            JsonObject metaObject = (JsonObject)GSON.fromJson(metaString, JsonObject.class);
            if (metaObject.isEmpty()) {
                Flashback.LOGGER.error("Metadata JSON is empty");
                return null;
            }
            return FlashbackMeta.fromJson(metaObject);
        }
        catch (Exception e) {
            Flashback.LOGGER.error("Exception while reading metadata", (Throwable)e);
            return null;
        }
    }
}

