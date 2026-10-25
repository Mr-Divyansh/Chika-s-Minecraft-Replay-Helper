/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import cl.eye.CLCamera;
import java.io.File;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.IplImage;

public class PS3EyeFrameGrabber
extends FrameGrabber {
    private static FrameGrabber.Exception loadingException = null;
    CLCamera camera = null;
    int cameraIndex = 0;
    int[] ps3_frame = null;
    byte[] ipl_frame = null;
    IplImage image_4ch = null;
    IplImage image_1ch = null;
    FrameConverter converter = new OpenCVFrameConverter.ToIplImage();
    String stat;
    String uuid;
    protected Triggered triggered = Triggered.NO_TRIGGER;

    public static String[] getDeviceDescriptions() throws FrameGrabber.Exception {
        PS3EyeFrameGrabber.tryLoad();
        String[] descriptions = new String[CLCamera.cameraCount()];
        for (int i = 0; i < descriptions.length; ++i) {
            descriptions[i] = CLCamera.cameraUUID(i);
        }
        return descriptions;
    }

    public static PS3EyeFrameGrabber createDefault(File deviceFile) throws FrameGrabber.Exception {
        throw new FrameGrabber.Exception(PS3EyeFrameGrabber.class + " does not support device files.");
    }

    public static PS3EyeFrameGrabber createDefault(String devicePath) throws FrameGrabber.Exception {
        throw new FrameGrabber.Exception(PS3EyeFrameGrabber.class + " does not support device paths.");
    }

    public static PS3EyeFrameGrabber createDefault(int deviceNumber) throws FrameGrabber.Exception {
        return new PS3EyeFrameGrabber(deviceNumber);
    }

    public static void tryLoad() throws FrameGrabber.Exception {
        if (loadingException != null) {
            throw loadingException;
        }
        try {
            CLCamera.IsLibraryLoaded();
        }
        catch (Throwable t) {
            loadingException = new FrameGrabber.Exception("Failed to load " + PS3EyeFrameGrabber.class, t);
            throw loadingException;
        }
    }

    public PS3EyeFrameGrabber() throws FrameGrabber.Exception {
        this(0);
    }

    public PS3EyeFrameGrabber(int cameraIndex) throws FrameGrabber.Exception {
        this(cameraIndex, 640, 480, 60);
    }

    public PS3EyeFrameGrabber(int cameraIndex, int imageWidth, int imageHeight, int framerate) throws FrameGrabber.Exception {
        this(cameraIndex, 640, 480, 60, null);
    }

    public PS3EyeFrameGrabber(int cameraIndex, int imageWidth, int imageHeight, int framerate, Object applet) throws FrameGrabber.Exception {
        if (!CLCamera.IsLibraryLoaded()) {
            throw new FrameGrabber.Exception("CLEye multicam dll not loaded");
        }
        this.camera = new CLCamera();
        this.cameraIndex = cameraIndex;
        this.stat = "created";
        this.uuid = CLCamera.cameraUUID(cameraIndex);
        if (!(imageWidth == 640 && imageHeight == 480 || imageWidth == 320 && imageHeight == 240)) {
            throw new FrameGrabber.Exception("Only 640x480 or 320x240 images supported");
        }
        this.setImageWidth(imageWidth);
        this.setImageHeight(imageHeight);
        this.setImageMode(FrameGrabber.ImageMode.COLOR);
        this.setFrameRate(framerate);
        this.setTimeout(1 + 1000 / framerate);
        this.setBitsPerPixel(8);
        this.setTriggerMode(false);
        this.setNumBuffers(4);
    }

    public static int getCameraCount() {
        return CLCamera.cameraCount();
    }

    public static String[] listPS3Cameras() {
        int no = PS3EyeFrameGrabber.getCameraCount();
        if (no > 0) {
            String[] uuids = new String[no];
            --no;
            while (no >= 0) {
                uuids[no] = CLCamera.cameraUUID(no);
                --no;
            }
            return uuids;
        }
        return null;
    }

    public IplImage makeImage(int[] frame) {
        this.image_4ch.getIntBuffer().put(this.ps3_frame);
        return this.image_4ch;
    }

    public int[] grab_raw() {
        if (this.camera.getCameraFrame(this.ps3_frame, this.timeout)) {
            return this.ps3_frame;
        }
        return null;
    }

    @Override
    public void trigger() throws FrameGrabber.Exception {
        for (int i = 0; i < this.numBuffers + 1; ++i) {
            this.grab_raw();
        }
        this.ps3_frame = this.grab_raw();
        if (this.ps3_frame != null) {
            this.triggered = Triggered.HAS_FRAME;
            this.timestamp = System.nanoTime() / 1000L;
        } else {
            this.triggered = Triggered.NO_FRAME;
        }
    }

    public IplImage grab_RGB4() {
        if (this.camera.getCameraFrame(this.ps3_frame, this.timeout)) {
            this.timestamp = System.nanoTime() / 1000L;
            this.image_4ch.getIntBuffer().put(this.ps3_frame);
            return this.image_4ch;
        }
        return null;
    }

    @Override
    public Frame grab() throws FrameGrabber.Exception {
        IplImage img = null;
        switch (this.triggered) {
            case NO_TRIGGER: {
                img = this.grab_RGB4();
                break;
            }
            case HAS_FRAME: {
                this.triggered = Triggered.NO_TRIGGER;
                img = this.makeImage(this.ps3_frame);
                break;
            }
            case NO_FRAME: {
                this.triggered = Triggered.NO_TRIGGER;
                return null;
            }
            default: {
                throw new FrameGrabber.Exception("Int. error - unknown triggering state");
            }
        }
        if (img != null && this.imageMode == FrameGrabber.ImageMode.GRAY) {
            opencv_imgproc.cvCvtColor((CvArr)img, (CvArr)this.image_1ch, (int)7);
            img = this.image_1ch;
        }
        return this.converter.convert(img);
    }

    @Override
    public void start() throws FrameGrabber.Exception {
        boolean b;
        if (this.ps3_frame == null) {
            this.ps3_frame = new int[this.imageWidth * this.imageHeight];
            this.image_4ch = IplImage.create((int)this.imageWidth, (int)this.imageHeight, (int)8, (int)4);
            this.image_1ch = IplImage.create((int)this.imageWidth, (int)this.imageHeight, (int)8, (int)1);
        }
        if (!(b = this.camera.createCamera(this.cameraIndex, this.imageMode == FrameGrabber.ImageMode.GRAY ? CLCamera.CLEYE_MONO_PROCESSED : CLCamera.CLEYE_COLOR_PROCESSED, this.imageWidth == 320 && this.imageHeight == 240 ? CLCamera.CLEYE_QVGA : CLCamera.CLEYE_VGA, (int)this.frameRate))) {
            throw new FrameGrabber.Exception("Low level createCamera() failed");
        }
        b = this.camera.startCamera();
        if (!b) {
            throw new FrameGrabber.Exception("Camera start() failed");
        }
        this.stat = "started";
    }

    @Override
    public void stop() throws FrameGrabber.Exception {
        boolean b = this.camera.stopCamera();
        if (!b) {
            throw new FrameGrabber.Exception("Camera stop() failed");
        }
        this.stat = "stopped";
    }

    @Override
    public void release() {
        if (this.camera != null) {
            this.camera.dispose();
            this.camera = null;
        }
        if (this.image_4ch != null) {
            this.image_4ch.release();
            this.image_4ch = null;
        }
        if (this.image_1ch != null) {
            this.image_1ch.release();
            this.image_1ch = null;
        }
        if (this.ipl_frame != null) {
            this.ipl_frame = null;
        }
        if (this.ps3_frame != null) {
            this.ps3_frame = null;
        }
        this.stat = "released";
    }

    public void dispose() {
        this.release();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.release();
    }

    public CLCamera getCamera() {
        return this.camera;
    }

    public String getUUID() {
        return this.uuid;
    }

    public String toString() {
        return "UUID=" + this.uuid + "; status=" + this.stat + "; timeout=" + this.timeout + "; " + (this.camera != null ? this.camera.toString() : "<no camera>");
    }

    public static void main(String[] argv) {
        String[] uuids = PS3EyeFrameGrabber.listPS3Cameras();
        for (int i = 0; i < uuids.length; ++i) {
            System.out.println(i + ": " + uuids[i]);
        }
    }

    protected static enum Triggered {
        NO_TRIGGER,
        HAS_FRAME,
        NO_FRAME;

    }
}

