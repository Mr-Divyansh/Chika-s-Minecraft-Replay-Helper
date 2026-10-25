/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.NativeImage
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.DefaultPlayerSkin
 *  net.minecraft.core.ClientAsset$ResourceTexture
 *  net.minecraft.core.ClientAsset$Texture
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.player.PlayerModelType
 *  net.minecraft.world.entity.player.PlayerSkin
 */
package com.moulberry.flashback;

import com.mojang.blaze3d.platform.NativeImage;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.GlobalCleaner;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.entity.player.PlayerSkin;

public class FilePlayerSkin {
    private transient PlayerSkin playerSkin = null;
    private final String pathToSkin;

    public FilePlayerSkin(String pathToSkin) {
        this.pathToSkin = pathToSkin;
    }

    public PlayerSkin getSkin() {
        if (this.playerSkin != null) {
            return this.playerSkin;
        }
        Path path = Path.of(this.pathToSkin, new String[0]);
        try (InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);){
            NativeImage nativeImage = NativeImage.read((InputStream)inputStream);
            int w = nativeImage.getWidth();
            int h = nativeImage.getHeight();
            int argb = nativeImage.getPixel(54 * w / 64, 20 * h / 64);
            PlayerModelType model = PlayerModelType.WIDE;
            if ((argb >> 24 & 0xFF) < 20) {
                model = PlayerModelType.SLIM;
            }
            DynamicTexture dynamicTexture = new DynamicTexture(() -> "file player skin", nativeImage);
            Identifier resourceLocation = Identifier.fromNamespaceAndPath((String)"flashback", (String)("skin_from_file/" + String.valueOf(UUID.randomUUID())));
            Minecraft.getInstance().getTextureManager().register(resourceLocation, (AbstractTexture)dynamicTexture);
            GlobalCleaner.INSTANCE.register(this, new CleanState(resourceLocation));
            this.playerSkin = PlayerSkin.insecure((ClientAsset.Texture)new ClientAsset.ResourceTexture(resourceLocation, resourceLocation), null, null, (PlayerModelType)model);
        }
        catch (Exception e) {
            Flashback.LOGGER.error("Unable to load skin from file", (Throwable)e);
            this.playerSkin = DefaultPlayerSkin.getDefaultSkin();
        }
        return this.playerSkin;
    }

    private static class CleanState
    implements Runnable {
        private Identifier skinIdentifier;

        public CleanState(Identifier skinIdentifier) {
            this.skinIdentifier = skinIdentifier;
        }

        @Override
        public void run() {
            if (this.skinIdentifier != null) {
                Identifier toClean = this.skinIdentifier;
                Minecraft.getInstance().execute(() -> {
                    Flashback.LOGGER.info("Cleaning player skin {} because it's no longer in use!", (Object)toClean);
                    Minecraft.getInstance().getTextureManager().release(toClean);
                });
                this.skinIdentifier = null;
            }
        }
    }
}

