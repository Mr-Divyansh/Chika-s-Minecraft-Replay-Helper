/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.ClientModInitializer
 *  net.fabricmc.loader.api.FabricLoader
 */
package yueyue.flashback;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import yueyue.flashback.UiRuntime;

public final class UiBootstrap
implements ClientModInitializer {
    public static boolean available() {
        return FabricLoader.getInstance().getModContainer("flashback").map(mod -> {
            String version = mod.getMetadata().getVersion().getFriendlyString();
            return version.startsWith("0.43.");
        }).orElse(false);
    }

    public void onInitializeClient() {
        if (UiBootstrap.available()) {
            UiRuntime.initialize();
        }
    }
}

