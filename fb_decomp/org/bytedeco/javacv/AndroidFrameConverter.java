/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 */
package org.bytedeco.javacv;

import android.graphics.Bitmap;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;

public class AndroidFrameConverter
extends FrameConverter<Bitmap> {
    Bitmap bitmap;
    ByteBuffer buffer;
    byte[] row;

    public Frame convert(byte[] data, int width, int height) {
        if (this.frame == null || this.frame.imageWidth != width || this.frame.imageHeight != height || this.frame.imageChannels != 3) {
            if (this.frame != null) {
                this.frame.close();
            }
            this.frame = new Frame(width, height, 8, 3);
        }
        ByteBuffer out = (ByteBuffer)this.frame.image[0];
        int stride = this.frame.imageStride;
        int offset = height * width;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int Y = data[i * width + j] & 0xFF;
                int V = data[offset + i / 2 * width + 2 * (j / 2)] & 0xFF;
                int U = data[offset + i / 2 * width + 2 * (j / 2) + 1] & 0xFF;
                U -= 128;
                V -= 128;
                if ((Y -= 16) < 0) {
                    Y = 0;
                }
                int B = 1192 * Y + 2066 * U;
                int G = 1192 * Y - 833 * V - 400 * U;
                int R = 1192 * Y + 1634 * V;
                R = Math.min(262143, Math.max(0, R));
                G = Math.min(262143, Math.max(0, G));
                B = Math.min(262143, Math.max(0, B));
                R >>= 10;
                R &= 0xFF;
                G >>= 10;
                B >>= 10;
                out.put(i * stride + 3 * j, (byte)(B &= 0xFF));
                out.put(i * stride + 3 * j + 1, (byte)(G &= 0xFF));
                out.put(i * stride + 3 * j + 2, (byte)R);
            }
        }
        return this.frame;
    }

    @Override
    public Frame convert(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int channels = 0;
        switch (bitmap.getConfig()) {
            case ALPHA_8: {
                channels = 1;
                break;
            }
            case RGB_565: 
            case ARGB_4444: {
                channels = 2;
                break;
            }
            case ARGB_8888: {
                channels = 4;
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        if (this.frame == null || this.frame.imageWidth != bitmap.getWidth() || this.frame.imageStride != bitmap.getRowBytes() || this.frame.imageHeight != bitmap.getHeight() || this.frame.imageChannels != channels) {
            if (this.frame != null) {
                this.frame.close();
            }
            this.frame = new Frame(bitmap.getWidth(), bitmap.getHeight(), 8, channels, bitmap.getRowBytes());
        }
        bitmap.copyPixelsToBuffer(this.frame.image[0].position(0));
        return this.frame;
    }

    ByteBuffer gray2rgba(ByteBuffer in, int width, int height, int stride, int rowBytes) {
        if (this.buffer == null || this.buffer.capacity() < height * rowBytes) {
            this.buffer = ByteBuffer.allocate(height * rowBytes);
        }
        if (this.row == null || this.row.length != stride) {
            this.row = new byte[stride];
        }
        for (int y = 0; y < height; ++y) {
            in.position(y * stride);
            in.get(this.row);
            for (int x = 0; x < width; ++x) {
                byte B = this.row[x];
                int rgba = (B & 0xFF) << 24 | (B & 0xFF) << 16 | (B & 0xFF) << 8 | 0xFF;
                this.buffer.putInt(y * rowBytes + 4 * x, rgba);
            }
        }
        return this.buffer;
    }

    ByteBuffer bgr2rgba(ByteBuffer in, int width, int height, int stride, int rowBytes) {
        if (!in.order().equals(ByteOrder.LITTLE_ENDIAN)) {
            in = in.order(ByteOrder.LITTLE_ENDIAN);
        }
        if (this.buffer == null || this.buffer.capacity() < height * rowBytes) {
            this.buffer = ByteBuffer.allocate(height * rowBytes);
        }
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int rgb;
                if (x < width - 1 || y < height - 1) {
                    rgb = in.getInt(y * stride + 3 * x);
                } else {
                    int b = in.get(y * stride + 3 * x) & 0xFF;
                    int g = in.get(y * stride + 3 * x + 1) & 0xFF;
                    int r = in.get(y * stride + 3 * x + 2) & 0xFF;
                    rgb = r << 16 | g << 8 | b;
                }
                this.buffer.putInt(y * rowBytes + 4 * x, rgb << 8 | 0xFF);
            }
        }
        return this.buffer;
    }

    @Override
    public Bitmap convert(Frame frame) {
        if (frame == null || frame.image == null) {
            return null;
        }
        Bitmap.Config config = null;
        switch (frame.imageChannels) {
            case 2: {
                config = Bitmap.Config.RGB_565;
                break;
            }
            case 1: 
            case 3: 
            case 4: {
                config = Bitmap.Config.ARGB_8888;
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        if (this.bitmap == null || this.bitmap.getWidth() != frame.imageWidth || this.bitmap.getHeight() != frame.imageHeight || this.bitmap.getConfig() != config) {
            this.bitmap = Bitmap.createBitmap((int)frame.imageWidth, (int)frame.imageHeight, (Bitmap.Config)config);
        }
        ByteBuffer in = (ByteBuffer)frame.image[0];
        int width = frame.imageWidth;
        int height = frame.imageHeight;
        int stride = frame.imageStride;
        int rowBytes = this.bitmap.getRowBytes();
        if (frame.imageChannels == 1) {
            this.gray2rgba(in, width, height, stride, rowBytes);
            this.bitmap.copyPixelsFromBuffer(this.buffer.position(0));
        } else if (frame.imageChannels == 3) {
            this.bgr2rgba(in, width, height, stride, rowBytes);
            this.bitmap.copyPixelsFromBuffer(this.buffer.position(0));
        } else {
            this.bitmap.copyPixelsFromBuffer(in.position(0));
        }
        return this.bitmap;
    }
}

