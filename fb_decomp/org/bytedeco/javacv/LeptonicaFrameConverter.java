/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.leptonica.PIX
 *  org.bytedeco.leptonica.global.leptonica
 */
package org.bytedeco.javacv;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.leptonica.global.leptonica;

public class LeptonicaFrameConverter
extends FrameConverter<PIX> {
    PIX pix;
    BytePointer frameData;
    BytePointer pixData;
    ByteBuffer frameBuffer;
    ByteBuffer pixBuffer;

    static boolean isEqual(Frame frame, PIX pix) {
        return pix != null && frame != null && frame.image != null && frame.image.length > 0 && frame.imageWidth == pix.w() && frame.imageHeight == pix.h() && frame.imageChannels == pix.d() / 8 && frame.imageDepth == 8 && (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN) || new Pointer(frame.image[0]).address() == pix.data().address()) && frame.imageStride * Math.abs(frame.imageDepth) / 8 == pix.wpl() * 4;
    }

    @Override
    public PIX convert(Frame frame) {
        if (frame == null || frame.image == null) {
            return null;
        }
        if (frame.opaque instanceof PIX) {
            return (PIX)frame.opaque;
        }
        if (!LeptonicaFrameConverter.isEqual(frame, this.pix)) {
            Pointer data;
            if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
                if (this.pixData == null || this.pixData.capacity() < (long)(frame.imageHeight * frame.imageStride)) {
                    if (this.pixData != null) {
                        this.pixData.releaseReference();
                    }
                    this.pixData = (BytePointer)new BytePointer(frame.imageHeight * frame.imageStride).retainReference();
                }
                data = this.pixData;
                this.pixBuffer = data.asByteBuffer().order(ByteOrder.BIG_ENDIAN);
            } else {
                data = new Pointer(frame.image[0].position(0));
            }
            if (this.pix != null) {
                this.pix.releaseReference();
            }
            this.pix = (PIX)PIX.create((int)frame.imageWidth, (int)frame.imageHeight, (int)(frame.imageChannels * 8), (Pointer)data).wpl(frame.imageStride / 4 * Math.abs(frame.imageDepth) / 8).retainReference();
        }
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ((ByteBuffer)this.pixBuffer.position(0)).asIntBuffer().put(((ByteBuffer)frame.image[0].position(0)).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer());
        }
        return this.pix;
    }

    @Override
    public Frame convert(PIX pix) {
        if (pix == null) {
            return null;
        }
        PIX tempPix = null;
        if (pix.colormap() != null) {
            tempPix = pix = leptonica.pixRemoveColormap((PIX)pix, (int)2);
        } else if (pix.d() < 8) {
            switch (pix.d()) {
                case 1: {
                    tempPix = pix = leptonica.pixConvert1To8(null, (PIX)pix, (byte)0, (byte)-1);
                    break;
                }
                case 2: {
                    tempPix = pix = leptonica.pixConvert2To8((PIX)pix, (byte)0, (byte)85, (byte)-86, (byte)-1, (int)0);
                    break;
                }
                case 4: {
                    tempPix = pix = leptonica.pixConvert4To8((PIX)pix, (int)0);
                    break;
                }
                default: {
                    assert (false);
                    break;
                }
            }
        }
        if (!LeptonicaFrameConverter.isEqual(this.frame, pix)) {
            this.frame = new Frame();
            this.frame.imageWidth = pix.w();
            this.frame.imageHeight = pix.h();
            this.frame.imageDepth = 8;
            this.frame.imageChannels = pix.d() / 8;
            this.frame.imageStride = pix.wpl() * 4;
            if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
                if (this.frameData == null || this.frameData.capacity() < (long)(this.frame.imageHeight * this.frame.imageStride)) {
                    if (this.frameData != null) {
                        this.frameData.releaseReference();
                    }
                    this.frameData = (BytePointer)new BytePointer(this.frame.imageHeight * this.frame.imageStride).retainReference();
                }
                this.frameBuffer = this.frameData.asByteBuffer().order(ByteOrder.LITTLE_ENDIAN);
                this.frame.opaque = this.frameData;
                this.frame.image = new Buffer[]{this.frameBuffer};
            } else {
                if (tempPix != null) {
                    if (this.pix != null) {
                        this.pix.releaseReference();
                    }
                    this.pix = pix = pix.clone();
                }
                this.frame.opaque = pix;
                this.frame.image = new Buffer[]{pix.createBuffer()};
            }
        }
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ((ByteBuffer)this.frameBuffer.position(0)).asIntBuffer().put(pix.createBuffer().order(ByteOrder.BIG_ENDIAN).asIntBuffer());
        }
        if (tempPix != null) {
            leptonica.pixDestroy((PIX)tempPix);
        }
        return this.frame;
    }

    @Override
    public void close() {
        super.close();
        if (this.pix != null) {
            this.pix.releaseReference();
            this.pix = null;
        }
        if (this.pixData != null) {
            this.pixData.releaseReference();
            this.pixData = null;
        }
        if (this.frameData != null) {
            this.frameData.releaseReference();
            this.frameData = null;
        }
    }

    static {
        Loader.load(leptonica.class);
    }
}

