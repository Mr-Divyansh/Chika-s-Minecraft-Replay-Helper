/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.badlogic.gdx.graphics.Pixmap
 *  com.badlogic.gdx.graphics.Pixmap$Format
 */
package org.bytedeco.javacv;

import com.badlogic.gdx.graphics.Pixmap;
import java.nio.ByteBuffer;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;

public class LibgdxFrameConverter
extends FrameConverter<Pixmap> {
    @Override
    public Frame convert(Pixmap pixmap) {
        if (pixmap == null) {
            return null;
        }
        Frame frame = new Frame(pixmap.getWidth(), pixmap.getHeight(), 8, 3, pixmap.getWidth() * 3);
        ByteBuffer pixmapBuffer = pixmap.getPixels().duplicate();
        ByteBuffer frameBuffer = (ByteBuffer)frame.image[0];
        int numPixels = pixmap.getWidth() * pixmap.getHeight();
        for (int i = 0; i < numPixels; ++i) {
            byte r = pixmapBuffer.get();
            byte g = pixmapBuffer.get();
            byte b = pixmapBuffer.get();
            pixmapBuffer.position(pixmapBuffer.position() + 1);
            frameBuffer.put(b);
            frameBuffer.put(g);
            frameBuffer.put(r);
        }
        frameBuffer.flip();
        return frame;
    }

    @Override
    public Pixmap convert(Frame frame) {
        if (frame == null || frame.image[0] == null) {
            return null;
        }
        Pixmap pixmap = new Pixmap(frame.imageWidth, frame.imageHeight, Pixmap.Format.RGBA8888);
        ByteBuffer frameBuffer = ((ByteBuffer)frame.image[0]).duplicate();
        ByteBuffer pixmapBuffer = pixmap.getPixels();
        pixmapBuffer.position(0);
        frameBuffer.rewind();
        int numPixels = frame.imageWidth * frame.imageHeight;
        for (int i = 0; i < numPixels; ++i) {
            byte b = frameBuffer.get();
            byte g = frameBuffer.get();
            byte r = frameBuffer.get();
            pixmapBuffer.put(r);
            pixmapBuffer.put(g);
            pixmapBuffer.put(b);
            pixmapBuffer.put((byte)-1);
        }
        pixmapBuffer.flip();
        return pixmap;
    }

    public Pixmap fastConvert(Frame frame) {
        if (frame == null || frame.image[0] == null) {
            return null;
        }
        Pixmap pixmap = new Pixmap(frame.imageWidth, frame.imageHeight, Pixmap.Format.RGBA8888);
        ByteBuffer frameBuffer = ((ByteBuffer)frame.image[0]).duplicate();
        ByteBuffer pixmapBuffer = pixmap.getPixels();
        pixmapBuffer.position(0);
        frameBuffer.rewind();
        pixmapBuffer.put(frameBuffer);
        pixmapBuffer.flip();
        return pixmap;
    }
}

