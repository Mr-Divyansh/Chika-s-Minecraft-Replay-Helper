/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.NativeImage
 *  com.mojang.blaze3d.platform.NativeImage$Format
 *  org.jetbrains.annotations.Nullable
 *  org.lwjgl.system.MemoryUtil
 */
package com.moulberry.flashback.exporting;

import com.mojang.blaze3d.platform.NativeImage;
import java.nio.FloatBuffer;
import org.bytedeco.ffmpeg.global.avutil;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryUtil;

public class ImageFrame
implements AutoCloseable {
    public final int width;
    public final int height;
    public final long size;
    public long pixels;
    public final Format format;
    @Nullable
    public FloatBuffer audioBuffer;
    private final int customFFmpegPixelFormat;

    public void copyRect(ImageFrame target, int fromX, int fromY, int toX, int toY, int sizeX, int sizeY) {
        if (this.format != target.format) {
            throw new IllegalArgumentException("target must have same format: this.format = " + String.valueOf((Object)this.format) + ", target.format = " + String.valueOf((Object)target.format));
        }
        if (fromX < 0) {
            throw new IllegalArgumentException("fromX < 0");
        }
        if (toX < 0) {
            throw new IllegalArgumentException("toX < 0");
        }
        if (fromY < 0) {
            throw new IllegalArgumentException("fromY < 0");
        }
        if (toY < 0) {
            throw new IllegalArgumentException("toY < 0");
        }
        if (fromX + sizeX > this.width) {
            throw new IllegalArgumentException("fromX + sizeX > this.width");
        }
        if (fromY + sizeY > this.height) {
            throw new IllegalArgumentException("fromY + sizeY > this.height");
        }
        if (toX + sizeX > target.width) {
            throw new IllegalArgumentException("toX + sizeX > target.width");
        }
        if (toY + sizeY > target.height) {
            throw new IllegalArgumentException("toY + sizeY > target.height");
        }
        for (int y = 0; y < sizeY; ++y) {
            long fromOffset = ((long)fromX + (long)(fromY + y) * (long)this.width) * (long)this.format.bytes();
            long toOffset = ((long)toX + (long)(toY + y) * (long)target.width) * (long)target.format.bytes();
            MemoryUtil.memCopy((long)(this.pixels + fromOffset), (long)(target.pixels + toOffset), (long)((long)sizeX * (long)this.format.bytes()));
        }
    }

    public void copyPixel(ImageFrame target, int fromX, int fromY, int toX, int toY) {
        if (this.format != target.format) {
            throw new IllegalArgumentException("target must have same format: this.format = " + String.valueOf((Object)this.format) + ", target.format = " + String.valueOf((Object)target.format));
        }
        long fromOffset = ((long)fromX + (long)fromY * (long)this.width) * (long)this.format.bytes();
        long toOffset = ((long)toX + (long)toY * (long)target.width) * (long)target.format.bytes();
        MemoryUtil.memCopy((long)(this.pixels + fromOffset), (long)(target.pixels + toOffset), (long)this.format.bytes());
    }

    public NativeImage toOpaqueRgbaU8NativeImage() {
        NativeImage copy = new NativeImage(NativeImage.Format.RGBA, this.width, this.height, true);
        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                long fromOffset = ((long)x + (long)y * (long)this.width) * (long)this.format.bytes();
                int rgba = this.format.toOpaqueRgbaU8(this.pixels + fromOffset);
                copy.setPixelABGR(x, y, rgba);
            }
        }
        return copy;
    }

    public void makeOpaque() {
        int bytes = this.format.bytes();
        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                long fromOffset = ((long)x + (long)y * (long)this.width) * (long)bytes;
                this.format.makeOpaque(this.pixels + fromOffset);
            }
        }
    }

    public int ffmpegPixelFormat() {
        if (this.format == Format.CUSTOM) {
            return this.customFFmpegPixelFormat;
        }
        return this.format.ffmpegPixelFormat();
    }

    public ImageFrame(int width, int height, Format format, boolean zero) {
        this.width = width;
        this.height = height;
        this.size = (long)width * (long)height * (long)format.bytes();
        this.pixels = zero ? MemoryUtil.nmemCalloc((long)this.size, (long)1L) : MemoryUtil.nmemAlloc((long)this.size);
        this.format = format;
        this.customFFmpegPixelFormat = 0;
    }

    public ImageFrame(long src, int width, int height, Format format) {
        this.width = width;
        this.height = height;
        this.size = (long)width * (long)height * (long)format.bytes();
        this.pixels = MemoryUtil.nmemAlloc((long)this.size);
        this.format = format;
        this.customFFmpegPixelFormat = 0;
        MemoryUtil.memCopy((long)src, (long)this.pixels, (long)this.size);
    }

    public ImageFrame(long pixels, int width, int height, int size, int customFFmpegPixelFormat, @Nullable FloatBuffer audioBuffer) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.size = size;
        this.format = Format.CUSTOM;
        this.customFFmpegPixelFormat = customFFmpegPixelFormat;
        this.audioBuffer = audioBuffer;
    }

    @Override
    public void close() {
        if (this.pixels != 0L) {
            MemoryUtil.nmemFree((long)this.pixels);
        }
        this.pixels = 0L;
    }

    public static enum Format {
        RGBA_U8,
        GRAY_F32,
        CUSTOM;


        public void makeOpaque(long ptr) {
            switch (this.ordinal()) {
                case 0: {
                    MemoryUtil.memPutInt((long)ptr, (int)(MemoryUtil.memGetInt((long)ptr) | 0xFF000000));
                    break;
                }
                case 1: {
                    break;
                }
                case 2: {
                    throw new UnsupportedOperationException();
                }
            }
        }

        public int toOpaqueRgbaU8(long mem) {
            return switch (this.ordinal()) {
                default -> throw new MatchException(null, null);
                case 0 -> MemoryUtil.memGetInt((long)mem) | 0xFF000000;
                case 1 -> {
                    int gray = (int)(Math.max(0.0f, Math.min(1.0f, MemoryUtil.memGetFloat((long)mem))) * 255.0f);
                    yield 65793 * gray | 0xFF000000;
                }
                case 2 -> throw new UnsupportedOperationException();
            };
        }

        public int channels() {
            return switch (this.ordinal()) {
                default -> throw new MatchException(null, null);
                case 0 -> 4;
                case 1 -> 1;
                case 2 -> throw new UnsupportedOperationException();
            };
        }

        public int bytesPerChannel() {
            return switch (this.ordinal()) {
                default -> throw new MatchException(null, null);
                case 0 -> 1;
                case 1 -> 4;
                case 2 -> throw new UnsupportedOperationException();
            };
        }

        public int bytes() {
            return this.bytesPerChannel() * this.channels();
        }

        private int ffmpegPixelFormat() {
            return switch (this.ordinal()) {
                default -> throw new MatchException(null, null);
                case 0 -> 26;
                case 1 -> avutil.AV_PIX_FMT_GRAYF32;
                case 2 -> throw new UnsupportedOperationException();
            };
        }
    }
}

