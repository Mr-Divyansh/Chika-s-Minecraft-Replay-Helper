/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.ClientModInitializer
 *  net.fabricmc.loader.api.FabricLoader
 */
package D_Web_Studio_Chika_Replay_Helper;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public final class UiBootstrap
implements ClientModInitializer {
    public static final KeyMapping TOGGLE_RECORD_KEY = KeyBindingHelper.registerKeyBinding(new KeyMapping(
        "key.d_web_studio_chika_replay_helper.toggle_record",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_U,
        "key.categories.d_web_studio_chika_replay_helper"
    ));

    public static boolean available() {
        return FabricLoader.getInstance().getModContainer("flashback").map(mod -> {
            String version = mod.getMetadata().getVersion().getFriendlyString();
            return version.startsWith("0.43.");
        }).orElse(false);
    }

    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (TOGGLE_RECORD_KEY.consumeClick()) {
                UiConfig.current.record = !UiConfig.current.record;
                UiConfig.save();
                if (client.player != null) {
                    client.player.displayClientMessage(
                        Component.literal("Chika Replay Helper GUI recording: " + (UiConfig.current.record ? "ON" : "OFF")),
                        true
                    );
                }
            }
        });
        if (UiBootstrap.available()) {
            UiRuntime.initialize();
        }
    }
}

