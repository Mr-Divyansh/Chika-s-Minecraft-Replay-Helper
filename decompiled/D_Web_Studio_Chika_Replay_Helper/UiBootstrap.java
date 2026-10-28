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
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public final class UiBootstrap
implements ClientModInitializer {
    private static boolean wasToggleKeyDown;

    public static boolean available() {
        return FabricLoader.getInstance().getModContainer("flashback").map(mod -> {
            String version = mod.getMetadata().getVersion().getFriendlyString();
            return version.startsWith("0.43.");
        }).orElse(false);
    }

    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean down = InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), GLFW.GLFW_KEY_U);
            if (down && !wasToggleKeyDown) {
                UiConfig.current.record = !UiConfig.current.record;
                UiConfig.save();
                if (client.player != null) {
                    client.player.sendSystemMessage(Component.literal("Chika Replay Helper GUI recording: " + (UiConfig.current.record ? "ON" : "OFF")));
                }
            }
            wasToggleKeyDown = down;
        });
        if (UiBootstrap.available()) {
            UiRuntime.initialize();
        }
    }
}

