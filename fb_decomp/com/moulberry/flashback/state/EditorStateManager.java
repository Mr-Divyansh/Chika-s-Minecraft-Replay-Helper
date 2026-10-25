/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback.state;

import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.state.EditorState;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import org.jetbrains.annotations.Nullable;

public class EditorStateManager {
    private static long AUTOSAVE_INTERVAL_MILLIS = 30000L;
    private static final ReentrantLock lock = new ReentrantLock();
    private static UUID currentUuid = null;
    private static EditorState current = null;
    private static long lastSave = 0L;

    public static void saveIfNeeded() {
        try {
            lock.lock();
            if (current == null || currentUuid == null) {
                return;
            }
            long currentTime = System.currentTimeMillis();
            if (!EditorStateManager.current.dirty) {
                lastSave = currentTime;
            } else if (currentTime < lastSave || currentTime - lastSave > AUTOSAVE_INTERVAL_MILLIS) {
                EditorStateManager.save();
            }
        }
        finally {
            lock.unlock();
        }
    }

    private static void save() {
        if (current == null || currentUuid == null) {
            return;
        }
        Path normalPath = EditorStateManager.getPath(currentUuid, false);
        Path oldPath = EditorStateManager.getPath(currentUuid, true);
        if (Files.exists(normalPath, new LinkOption[0])) {
            try {
                Files.move(normalPath, oldPath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        current.save(normalPath);
        lastSave = System.currentTimeMillis();
    }

    private static void load() {
        lastSave = System.currentTimeMillis();
        Path normalPath = EditorStateManager.getPath(currentUuid, false);
        Path backupPath = EditorStateManager.getPath(currentUuid, true);
        if (Files.exists(normalPath, new LinkOption[0])) {
            current = EditorState.load(normalPath);
            if (current == null) {
                try {
                    Files.deleteIfExists(normalPath);
                }
                catch (IOException iOException) {}
            } else {
                return;
            }
        }
        if (Files.exists(backupPath, new LinkOption[0])) {
            current = EditorState.load(backupPath);
            if (current == null) {
                try {
                    Files.deleteIfExists(backupPath);
                }
                catch (IOException iOException) {}
            } else {
                return;
            }
        }
        current = new EditorState();
    }

    public static void reset() {
        try {
            lock.lock();
            EditorStateManager.save();
            current = null;
            currentUuid = null;
        }
        finally {
            lock.unlock();
        }
    }

    @Nullable
    public static EditorState getCurrent() {
        if (Flashback.isExporting()) {
            return Flashback.EXPORT_JOB.getSettings().editorState();
        }
        if (!Flashback.isInReplay()) {
            return null;
        }
        return current;
    }

    public static EditorState get(UUID replayUuid) {
        Objects.requireNonNull(replayUuid);
        try {
            lock.lock();
            if (current == null || !Objects.equals(currentUuid, replayUuid)) {
                EditorStateManager.save();
                currentUuid = replayUuid;
                EditorStateManager.load();
            }
            EditorState editorState = current;
            return editorState;
        }
        finally {
            lock.unlock();
        }
    }

    private static Path getPath(UUID replayUuid, boolean old) {
        Objects.requireNonNull(replayUuid);
        String filename = String.valueOf(replayUuid) + ".json";
        if (old) {
            filename = filename + ".old";
        }
        return Flashback.getDataDirectory().resolve("editor_states").resolve(filename);
    }
}

