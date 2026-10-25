/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.scene.image.Image
 *  javafx.scene.image.PixelFormat
 *  javafx.scene.image.PixelReader
 *  javafx.scene.image.WritableImage
 *  javafx.scene.image.WritablePixelFormat
 *  javafx.scene.paint.Color
 */
package org.bytedeco.javacv;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.paint.Color;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;

public class JavaFXFrameConverter
extends FrameConverter<Image> {
    @Override
    public Frame convert(Image f) {
        throw new UnsupportedOperationException("conversion from Image to Frame not supported yet.");
    }

    @Override
    public Image convert(Frame frame) {
        int iw = frame.imageWidth;
        int ih = frame.imageHeight;
        FramePixelReader pr = new FramePixelReader(frame);
        WritableImage answer = new WritableImage((PixelReader)pr, iw, ih);
        return answer;
    }

    class FramePixelReader
    implements PixelReader {
        Frame frame;

        FramePixelReader(Frame f) {
            this.frame = f;
        }

        public PixelFormat getPixelFormat() {
            throw new UnsupportedOperationException("getPixelFormat not supported yet.");
        }

        public int getArgb(int x, int y) {
            throw new UnsupportedOperationException("getArgb not supported yet.");
        }

        public Color getColor(int x, int y) {
            throw new UnsupportedOperationException("getColor not supported yet.");
        }

        public <T extends Buffer> void getPixels(int x, int y, int w, int h, WritablePixelFormat<T> pixelformat, T buffer, int scanlineStride) {
            int fss = this.frame.imageStride;
            if (this.frame.imageChannels != 3) {
                throw new UnsupportedOperationException("We only support frames with imageChannels = 3 (BGR)");
            }
            if (buffer instanceof ByteBuffer) {
                ByteBuffer bb = (ByteBuffer)buffer;
                ByteBuffer b = (ByteBuffer)this.frame.image[0];
                for (int i = y; i < y + h; ++i) {
                    for (int j = x; j < x + w; ++j) {
                        int base = 3 * j;
                        bb.put(b.get(fss * i + base));
                        bb.put(b.get(fss * i + base + 1));
                        bb.put(b.get(fss * i + base + 2));
                        bb.put((byte)-1);
                    }
                }
            } else {
                throw new UnsupportedOperationException("We only support bytebuffers at the moment");
            }
        }

        public void getPixels(int x, int y, int w, int h, WritablePixelFormat<ByteBuffer> pixelformat, byte[] buffer, int offset, int scanlineStride) {
            throw new UnsupportedOperationException("getPixels<ByteBuffer> Not supported yet.");
        }

        public void getPixels(int x, int y, int w, int h, WritablePixelFormat<IntBuffer> pixelformat, int[] buffer, int offset, int scanlineStride) {
            throw new UnsupportedOperationException("getPixels<IntBuffer>Not supported yet.");
        }
    }
}

