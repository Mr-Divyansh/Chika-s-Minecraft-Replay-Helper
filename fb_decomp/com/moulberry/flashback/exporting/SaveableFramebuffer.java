/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.buffers.GpuBuffer
 *  com.mojang.blaze3d.buffers.GpuBuffer$MappedView
 *  com.mojang.blaze3d.systems.CommandEncoder
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.textures.GpuTexture
 *  com.mojang.blaze3d.textures.TextureFormat
 *  org.jetbrains.annotations.Nullable
 *  org.lwjgl.system.MemoryUtil
 */
package com.moulberry.flashback.exporting;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.TextureFormat;
import com.moulberry.flashback.exporting.ImageFrame;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryUtil;

public class SaveableFramebuffer
implements AutoCloseable {
    @Nullable
    public FloatBuffer audioBuffer;
    private ImageFrame downloaded = null;
    private final int width;
    private final int height;
    private GpuBuffer pbo;
    private boolean isDownloading = false;
    public static boolean fixDepthDownload = false;

    public SaveableFramebuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.pbo = RenderSystem.getDevice().createBuffer(() -> "Flashback texture output buffer", 9, 4L * (long)width * (long)height);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void startDownload(GpuTexture gpuTexture) {
        if (this.isDownloading) {
            throw new IllegalStateException("Can't start downloading while already downloading");
        }
        this.isDownloading = true;
        if (this.pbo == null) {
            throw new IllegalStateException();
        }
        ImageFrame.Format format = switch (gpuTexture.getFormat()) {
            case TextureFormat.RGBA8 -> ImageFrame.Format.RGBA_U8;
            case TextureFormat.DEPTH32, TextureFormat.FLASHBACK_R32_FLOAT -> ImageFrame.Format.GRAY_F32;
            default -> throw new IllegalStateException("Don't know how to download format " + String.valueOf(gpuTexture.getFormat()));
        };
        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
        Runnable runnable = () -> {
            try (GpuBuffer.MappedView mappedView = commandEncoder.mapBuffer(this.pbo, true, false);){
                this.downloaded = new ImageFrame(MemoryUtil.memAddress((ByteBuffer)mappedView.data()), this.width, this.height, format);
            }
        };
        try {
            fixDepthDownload = true;
            commandEncoder.copyTextureToBuffer(gpuTexture, this.pbo, 0L, runnable, 0);
        }
        finally {
            fixDepthDownload = false;
        }
    }

    public boolean canFinishDownload() {
        if (!this.isDownloading) {
            throw new IllegalStateException("Can't finish downloading before download has started");
        }
        return this.downloaded != null;
    }

    public ImageFrame finishDownload() {
        if (!this.isDownloading) {
            throw new IllegalStateException("Can't finish downloading before download has started");
        }
        if (this.downloaded == null) {
            return null;
        }
        this.isDownloading = false;
        ImageFrame downloaded = this.downloaded;
        this.downloaded = null;
        return downloaded;
    }

    @Override
    public void close() {
        if (this.pbo != null) {
            this.pbo.close();
            this.pbo = null;
        }
        if (this.downloaded != null) {
            this.downloaded.close();
            this.downloaded = null;
        }
    }
}

