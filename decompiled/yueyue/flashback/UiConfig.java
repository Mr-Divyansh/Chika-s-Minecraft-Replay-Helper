/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  net.fabricmc.loader.api.FabricLoader
 *  org.slf4j.LoggerFactory
 */
package yueyue.flashback;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.LoggerFactory;

public final class UiConfig {
    public boolean record = true;
    public boolean include = true;
    public boolean follow = true;
    public boolean preview = true;
    private static final Gson JSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("yueyue-flashback-firstperson.json");
    public static UiConfig current = UiConfig.load();

    private static UiConfig load() {
        try {
            UiConfig value;
            if (Files.isRegularFile(FILE, new LinkOption[0]) && (value = (UiConfig)JSON.fromJson(Files.readString(FILE), UiConfig.class)) != null) {
                return value;
            }
        }
        catch (IOException | RuntimeException e) {
            LoggerFactory.getLogger((String)"yueyue-flashback").warn("Could not read preferences; retaining original file", (Throwable)e);
        }
        return new UiConfig();
    }

    public static void save() {
        Path temporary = FILE.resolveSibling(String.valueOf(FILE.getFileName()) + ".tmp");
        try {
            Files.createDirectories(FILE.getParent(), new FileAttribute[0]);
            Files.writeString(temporary, (CharSequence)JSON.toJson((Object)current), new OpenOption[0]);
            try {
                Files.move(temporary, FILE, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (AtomicMoveNotSupportedException e) {
                Files.move(temporary, FILE, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            LoggerFactory.getLogger((String)"yueyue-flashback").warn("Could not save preferences", (Throwable)e);
        }
    }
}

