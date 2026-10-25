/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bytedeco.libdc1394.dc1394_t
 *  org.bytedeco.libdc1394.dc1394camera_id_t
 *  org.bytedeco.libdc1394.dc1394camera_list_t
 *  org.bytedeco.libdc1394.dc1394camera_t
 *  org.bytedeco.libdc1394.dc1394video_frame_t
 *  org.bytedeco.libdc1394.global.dc1394
 *  org.bytedeco.libdc1394.presets.dc1394$pollfd
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.libdc1394.dc1394_t;
import org.bytedeco.libdc1394.dc1394camera_id_t;
import org.bytedeco.libdc1394.dc1394camera_list_t;
import org.bytedeco.libdc1394.dc1394camera_t;
import org.bytedeco.libdc1394.dc1394video_frame_t;
import org.bytedeco.libdc1394.global.dc1394;
import org.bytedeco.libdc1394.presets.dc1394;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.IplImage;

public class DC1394FrameGrabber
extends FrameGrabber {
    private static FrameGrabber.Exception loadingException = null;
    private static final boolean linux = Loader.getPlatform().startsWith("linux");
    private dc1394_t d = null;
    private dc1394camera_t camera = null;
    private dc1394.pollfd fds = linux ? new dc1394.pollfd() : null;
    private boolean oneShotMode = false;
    private boolean resetDone = false;
    private dc1394video_frame_t[] raw_image = new dc1394video_frame_t[]{new dc1394video_frame_t(null), new dc1394video_frame_t(null)};
    private dc1394video_frame_t conv_image = new dc1394video_frame_t();
    private dc1394video_frame_t frame = null;
    private dc1394video_frame_t enqueue_image = null;
    private IplImage temp_image;
    private IplImage return_image = null;
    private FrameConverter converter = new OpenCVFrameConverter.ToIplImage();
    private final int[] out = new int[1];
    private final float[] outFloat = new float[1];
    private final float[] gammaOut = new float[1];

    public static String[] getDeviceDescriptions() throws FrameGrabber.Exception {
        DC1394FrameGrabber.tryLoad();
        dc1394_t d = dc1394.dc1394_new();
        if (d == null) {
            throw new FrameGrabber.Exception("dc1394_new() Error: Failed to initialize libdc1394.");
        }
        dc1394camera_list_t list = new dc1394camera_list_t(null);
        int err = dc1394.dc1394_camera_enumerate((dc1394_t)d, (dc1394camera_list_t)list);
        if (err != 0) {
            throw new FrameGrabber.Exception("dc1394_camera_enumerate() Error " + err + ": Failed to enumerate cameras.");
        }
        int num = list.num();
        String[] descriptions = new String[num];
        if (num > 0) {
            dc1394camera_id_t ids = list.ids();
            for (int i = 0; i < num; ++i) {
                ids.position((long)i);
                dc1394camera_t camera = dc1394.dc1394_camera_new_unit((dc1394_t)d, (long)ids.guid(), (int)ids.unit());
                if (camera == null) {
                    throw new FrameGrabber.Exception("dc1394_camera_new_unit() Error: Failed to initialize camera with GUID 0x" + Long.toHexString(ids.guid()) + " / " + camera.unit() + ".");
                }
                descriptions[i] = camera.vendor().getString() + " " + camera.model().getString() + " 0x" + Long.toHexString(camera.guid()) + " / " + camera.unit();
                dc1394.dc1394_camera_free((dc1394camera_t)camera);
            }
        }
        dc1394.dc1394_camera_free_list((dc1394camera_list_t)list);
        dc1394.dc1394_free((dc1394_t)d);
        return descriptions;
    }

    public static DC1394FrameGrabber createDefault(File deviceFile) throws FrameGrabber.Exception {
        throw new FrameGrabber.Exception(DC1394FrameGrabber.class + " does not support device files.");
    }

    public static DC1394FrameGrabber createDefault(String devicePath) throws FrameGrabber.Exception {
        throw new FrameGrabber.Exception(DC1394FrameGrabber.class + " does not support device paths.");
    }

    public static DC1394FrameGrabber createDefault(int deviceNumber) throws FrameGrabber.Exception {
        return new DC1394FrameGrabber(deviceNumber);
    }

    public static void tryLoad() throws FrameGrabber.Exception {
        if (loadingException != null) {
            throw loadingException;
        }
        try {
            Loader.load(dc1394.class);
        }
        catch (Throwable t) {
            loadingException = new FrameGrabber.Exception("Failed to load " + DC1394FrameGrabber.class, t);
            throw loadingException;
        }
    }

    public DC1394FrameGrabber(int deviceNumber) throws FrameGrabber.Exception {
        this.d = dc1394.dc1394_new();
        dc1394camera_list_t list = new dc1394camera_list_t(null);
        int err = dc1394.dc1394_camera_enumerate((dc1394_t)this.d, (dc1394camera_list_t)list);
        if (err != 0) {
            throw new FrameGrabber.Exception("dc1394_camera_enumerate() Error " + err + ": Failed to enumerate cameras.");
        }
        int num = list.num();
        if (num <= deviceNumber) {
            throw new FrameGrabber.Exception("DC1394Grabber() Error: Camera number " + deviceNumber + " not found. There are only " + num + " devices.");
        }
        dc1394camera_id_t ids = list.ids().position((long)deviceNumber);
        this.camera = dc1394.dc1394_camera_new_unit((dc1394_t)this.d, (long)ids.guid(), (int)ids.unit());
        if (this.camera == null) {
            throw new FrameGrabber.Exception("dc1394_camera_new_unit() Error: Failed to initialize camera with GUID 0x" + Long.toHexString(ids.guid()) + " / " + this.camera.unit() + ".");
        }
        dc1394.dc1394_camera_free_list((dc1394camera_list_t)list);
    }

    @Override
    public void release() throws FrameGrabber.Exception {
        if (this.camera != null) {
            this.stop();
            dc1394.dc1394_camera_free((dc1394camera_t)this.camera);
            this.camera = null;
        }
        if (this.d != null) {
            dc1394.dc1394_free((dc1394_t)this.d);
            this.d = null;
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.release();
    }

    @Override
    public double getGamma() {
        return Float.isNaN(this.gammaOut[0]) || Float.isInfinite(this.gammaOut[0]) || this.gammaOut[0] == 0.0f ? 2.2 : (double)this.gammaOut[0];
    }

    @Override
    public int getImageWidth() {
        return this.return_image == null ? super.getImageWidth() : this.return_image.width();
    }

    @Override
    public int getImageHeight() {
        return this.return_image == null ? super.getImageHeight() : this.return_image.height();
    }

    @Override
    public double getFrameRate() {
        if (this.camera == null) {
            return super.getFrameRate();
        }
        if (dc1394.dc1394_feature_get_absolute_value((dc1394camera_t)this.camera, (int)431, (float[])this.outFloat) != 0) {
            dc1394.dc1394_video_get_framerate((dc1394camera_t)this.camera, (int[])this.out);
            dc1394.dc1394_framerate_as_float((int)this.out[0], (float[])this.outFloat);
        }
        return this.outFloat[0];
    }

    @Override
    public void setImageMode(FrameGrabber.ImageMode imageMode) {
        if (imageMode != this.imageMode) {
            this.temp_image = null;
            this.return_image = null;
        }
        super.setImageMode(imageMode);
    }

    @Override
    public void start() throws FrameGrabber.Exception {
        this.start(true, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void start(boolean tryReset, boolean try1394b) throws FrameGrabber.Exception {
        block74: {
            int c = -1;
            if (this.imageMode == FrameGrabber.ImageMode.COLOR || this.imageMode == FrameGrabber.ImageMode.RAW) {
                if (this.imageWidth <= 0 || this.imageHeight <= 0) {
                    c = -1;
                } else if (this.imageWidth <= 640 && this.imageHeight <= 480) {
                    c = 68;
                } else if (this.imageWidth <= 800 && this.imageHeight <= 600) {
                    c = 72;
                } else if (this.imageWidth <= 1024 && this.imageHeight <= 768) {
                    c = 75;
                } else if (this.imageWidth <= 1280 && this.imageHeight <= 960) {
                    c = 80;
                } else if (this.imageWidth <= 1600 && this.imageHeight <= 1200) {
                    c = 83;
                }
            } else if (this.imageMode == FrameGrabber.ImageMode.GRAY) {
                if (this.imageWidth <= 0 || this.imageHeight <= 0) {
                    c = -1;
                } else if (this.imageWidth <= 640 && this.imageHeight <= 480) {
                    c = this.bpp > 8 ? 70 : 69;
                } else if (this.imageWidth <= 800 && this.imageHeight <= 600) {
                    c = this.bpp > 8 ? 77 : 73;
                } else if (this.imageWidth <= 1024 && this.imageHeight <= 768) {
                    c = this.bpp > 8 ? 78 : 76;
                } else if (this.imageWidth <= 1280 && this.imageHeight <= 960) {
                    c = this.bpp > 8 ? 85 : 81;
                } else if (this.imageWidth <= 1600 && this.imageHeight <= 1200) {
                    int n = c = this.bpp > 8 ? 86 : 84;
                }
            }
            if (c == -1) {
                dc1394.dc1394_video_get_mode((dc1394camera_t)this.camera, (int[])this.out);
                c = this.out[0];
            }
            int f = -1;
            if (this.frameRate <= 0.0) {
                f = -1;
            } else if (this.frameRate <= 1.876) {
                f = 32;
            } else if (this.frameRate <= 3.76) {
                f = 33;
            } else if (this.frameRate <= 7.51) {
                f = 34;
            } else if (this.frameRate <= 15.01) {
                f = 35;
            } else if (this.frameRate <= 30.01) {
                f = 36;
            } else if (this.frameRate <= 60.01) {
                f = 37;
            } else if (this.frameRate <= 120.01) {
                f = 38;
            } else if (this.frameRate <= 240.01) {
                f = 39;
            }
            if (f == -1) {
                dc1394.dc1394_video_get_framerate((dc1394camera_t)this.camera, (int[])this.out);
                f = this.out[0];
            }
            try {
                int err;
                this.oneShotMode = false;
                if (this.triggerMode) {
                    err = dc1394.dc1394_external_trigger_set_power((dc1394camera_t)this.camera, (int)1);
                    if (err != 0) {
                        this.oneShotMode = true;
                    } else {
                        err = dc1394.dc1394_external_trigger_set_mode((dc1394camera_t)this.camera, (int)390);
                        if (err != 0) {
                            err = dc1394.dc1394_external_trigger_set_mode((dc1394camera_t)this.camera, (int)384);
                        }
                        if ((err = dc1394.dc1394_external_trigger_set_source((dc1394camera_t)this.camera, (int)580)) != 0) {
                            this.oneShotMode = true;
                            dc1394.dc1394_external_trigger_set_power((dc1394camera_t)this.camera, (int)0);
                        }
                    }
                }
                err = dc1394.dc1394_video_set_operation_mode((dc1394camera_t)this.camera, (int)480);
                if (try1394b && (err = dc1394.dc1394_video_set_operation_mode((dc1394camera_t)this.camera, (int)481)) == 0) {
                    err = dc1394.dc1394_video_set_iso_speed((dc1394camera_t)this.camera, (int)3);
                }
                if (!(err == 0 && try1394b || (err = dc1394.dc1394_video_set_iso_speed((dc1394camera_t)this.camera, (int)2)) == 0)) {
                    throw new FrameGrabber.Exception("dc1394_video_set_iso_speed() Error " + err + ": Could not set maximum iso speed.");
                }
                err = dc1394.dc1394_video_set_mode((dc1394camera_t)this.camera, (int)c);
                if (err != 0) {
                    throw new FrameGrabber.Exception("dc1394_video_set_mode() Error " + err + ": Could not set video mode.");
                }
                if (dc1394.dc1394_is_video_mode_scalable((int)c) == 1) {
                    err = dc1394.dc1394_format7_set_roi((dc1394camera_t)this.camera, (int)c, (int)-1, (int)-1, (int)-1, (int)-1, (int)-1, (int)-1);
                    if (err != 0) {
                        throw new FrameGrabber.Exception("dc1394_format7_set_roi() Error " + err + ": Could not set format7 mode.");
                    }
                } else {
                    err = dc1394.dc1394_video_set_framerate((dc1394camera_t)this.camera, (int)f);
                    if (err != 0) {
                        throw new FrameGrabber.Exception("dc1394_video_set_framerate() Error " + err + ": Could not set framerate.");
                    }
                }
                if ((err = dc1394.dc1394_capture_setup((dc1394camera_t)this.camera, (int)this.numBuffers, (int)4)) != 0) {
                    throw new FrameGrabber.Exception("dc1394_capture_setup() Error " + err + ": Could not setup camera-\nmake sure that the video mode and framerate are\nsupported by your camera.");
                }
                if (this.gamma != 0.0 && (err = dc1394.dc1394_feature_set_absolute_value((dc1394camera_t)this.camera, (int)422, (float)((float)this.gamma))) != 0) {
                    throw new FrameGrabber.Exception("dc1394_feature_set_absolute_value() Error " + err + ": Could not set gamma.");
                }
                err = dc1394.dc1394_feature_get_absolute_value((dc1394camera_t)this.camera, (int)422, (float[])this.gammaOut);
                if (err != 0) {
                    this.gammaOut[0] = 2.2f;
                }
                if (linux) {
                    this.fds.fd(dc1394.dc1394_capture_get_fileno((dc1394camera_t)this.camera));
                }
                if (!this.oneShotMode && (err = dc1394.dc1394_video_set_transmission((dc1394camera_t)this.camera, (int)1)) != 0) {
                    throw new FrameGrabber.Exception("dc1394_video_set_transmission() Error " + err + ": Could not start camera iso transmission.");
                }
            }
            catch (FrameGrabber.Exception e) {
                if (tryReset && !this.resetDone) {
                    try {
                        dc1394.dc1394_reset_bus((dc1394camera_t)this.camera);
                        Thread.sleep(100L);
                        this.resetDone = true;
                        this.start(false, try1394b);
                        break block74;
                    }
                    catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        throw new FrameGrabber.Exception("dc1394_reset_bus() Error: Could not reset bus and try to start again.", ex);
                    }
                }
                throw e;
            }
            finally {
                this.resetDone = false;
            }
        }
        if (linux && try1394b) {
            if (this.triggerMode) {
                this.trigger();
            }
            this.fds.events((short)1);
            if (dc1394.poll((dc1394.pollfd)this.fds, (long)1L, (int)this.timeout) == 0) {
                this.stop();
                this.start(tryReset, false);
            } else if (this.triggerMode) {
                this.grab();
                this.enqueue();
            }
        }
    }

    @Override
    public void stop() throws FrameGrabber.Exception {
        this.enqueue_image = null;
        this.temp_image = null;
        this.return_image = null;
        this.timestamp = 0L;
        this.frameNumber = 0;
        int err = dc1394.dc1394_video_set_transmission((dc1394camera_t)this.camera, (int)0);
        if (err != 0) {
            throw new FrameGrabber.Exception("dc1394_video_set_transmission() Error " + err + ": Could not stop the camera?");
        }
        err = dc1394.dc1394_capture_stop((dc1394camera_t)this.camera);
        if (err != 0 && err != -10) {
            throw new FrameGrabber.Exception("dc1394_capture_stop() Error " + err + ": Could not stop the camera?");
        }
        err = dc1394.dc1394_external_trigger_get_mode((dc1394camera_t)this.camera, (int[])this.out);
        if (err == 0 && this.out[0] >= 384 && (err = dc1394.dc1394_external_trigger_set_power((dc1394camera_t)this.camera, (int)0)) != 0) {
            throw new FrameGrabber.Exception("dc1394_external_trigger_set_power() Error " + err + ": Could not switch off external trigger.");
        }
    }

    private void enqueue() throws FrameGrabber.Exception {
        this.enqueue(this.enqueue_image);
        this.enqueue_image = null;
    }

    private void enqueue(dc1394video_frame_t image) throws FrameGrabber.Exception {
        int err;
        if (image != null && (err = dc1394.dc1394_capture_enqueue((dc1394camera_t)this.camera, (dc1394video_frame_t)image)) != 0) {
            throw new FrameGrabber.Exception("dc1394_capture_enqueue() Error " + err + ": Could not release a frame.");
        }
    }

    @Override
    public void trigger() throws FrameGrabber.Exception {
        this.enqueue();
        if (this.oneShotMode) {
            int err = dc1394.dc1394_video_set_one_shot((dc1394camera_t)this.camera, (int)1);
            if (err != 0) {
                throw new FrameGrabber.Exception("dc1394_video_set_one_shot() Error " + err + ": Could not set camera into one-shot mode.");
            }
        } else {
            long time = System.currentTimeMillis();
            do {
                dc1394.dc1394_software_trigger_get_power((dc1394camera_t)this.camera, (int[])this.out);
            } while (System.currentTimeMillis() - time <= (long)this.timeout && this.out[0] == 1);
            int err = dc1394.dc1394_software_trigger_set_power((dc1394camera_t)this.camera, (int)1);
            if (err != 0) {
                throw new FrameGrabber.Exception("dc1394_software_trigger_set_power() Error " + err + ": Could not trigger camera.");
            }
        }
    }

    @Override
    public Frame grab() throws FrameGrabber.Exception {
        int i;
        int err;
        this.enqueue();
        if (linux) {
            this.fds.events((short)1);
            if (dc1394.poll((dc1394.pollfd)this.fds, (long)1L, (int)this.timeout) == 0) {
                throw new FrameGrabber.Exception("poll() Error: Timeout occured. (Has start() been called?)");
            }
        }
        if ((err = dc1394.dc1394_capture_dequeue((dc1394camera_t)this.camera, (int)672, (dc1394video_frame_t)this.raw_image[i = 0])) != 0) {
            throw new FrameGrabber.Exception("dc1394_capture_dequeue(WAIT) Error " + err + ": Could not capture a frame. (Has start() been called?)");
        }
        int numDequeued = 0;
        while (!this.raw_image[i].isNull()) {
            this.enqueue();
            this.enqueue_image = this.raw_image[i];
            i = (i + 1) % 2;
            ++numDequeued;
            err = dc1394.dc1394_capture_dequeue((dc1394camera_t)this.camera, (int)673, (dc1394video_frame_t)this.raw_image[i]);
            if (err == 0) continue;
            throw new FrameGrabber.Exception("dc1394_capture_dequeue(POLL) Error " + err + ": Could not capture a frame.");
        }
        this.frame = this.raw_image[(i + 1) % 2];
        int w = this.frame.size(0);
        int h = this.frame.size(1);
        int depth = this.frame.data_depth();
        int iplDepth = 0;
        switch (depth) {
            case 8: {
                iplDepth = 8;
                break;
            }
            case 16: {
                iplDepth = 16;
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        int stride = this.frame.stride();
        int size = this.frame.image_bytes();
        int numChannels = stride / w * 8 / depth;
        ByteOrder frameEndian = this.frame.little_endian() != 0 ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
        boolean alreadySwapped = false;
        int color_coding = this.frame.color_coding();
        boolean colorbayer = color_coding == 361 || color_coding == 362;
        boolean colorrgb = color_coding == 356 || color_coding == 358;
        boolean coloryuv = color_coding == 353 || color_coding == 354 || color_coding == 355;
        BytePointer imageData = this.frame.image();
        if ((depth <= 8 || frameEndian.equals(ByteOrder.nativeOrder())) && !coloryuv && (this.imageMode == FrameGrabber.ImageMode.RAW || this.imageMode == FrameGrabber.ImageMode.COLOR && numChannels == 3 || this.imageMode == FrameGrabber.ImageMode.GRAY && numChannels == 1 && !colorbayer)) {
            if (this.return_image == null) {
                this.return_image = IplImage.createHeader((int)w, (int)h, (int)iplDepth, (int)numChannels);
            }
            this.return_image.widthStep(stride);
            this.return_image.imageSize(size);
            this.return_image.imageData(imageData);
        } else {
            int padding_bytes = this.frame.padding_bytes();
            int padding1 = (int)Math.ceil((double)padding_bytes / (double)(w * depth / 8));
            int padding3 = (int)Math.ceil((double)padding_bytes / (double)(w * 3 * depth / 8));
            if (this.return_image == null) {
                int c = this.imageMode == FrameGrabber.ImageMode.COLOR ? 3 : 1;
                int padding = this.imageMode == FrameGrabber.ImageMode.COLOR ? padding3 : padding1;
                this.return_image = IplImage.create((int)w, (int)(h + padding), (int)iplDepth, (int)c);
                this.return_image.height(this.return_image.height() - padding);
            }
            if (this.temp_image == null) {
                if (!(this.imageMode != FrameGrabber.ImageMode.COLOR || numChannels <= 1 && depth <= 8 || coloryuv || colorbayer)) {
                    this.temp_image = IplImage.create((int)w, (int)(h + padding1), (int)iplDepth, (int)numChannels);
                    this.temp_image.height(this.temp_image.height() - padding1);
                } else if (this.imageMode == FrameGrabber.ImageMode.GRAY && (coloryuv || colorbayer || colorrgb && depth > 8)) {
                    this.temp_image = IplImage.create((int)w, (int)(h + padding3), (int)iplDepth, (int)3);
                    this.temp_image.height(this.temp_image.height() - padding3);
                } else {
                    this.temp_image = this.imageMode == FrameGrabber.ImageMode.GRAY && colorrgb ? IplImage.createHeader((int)w, (int)h, (int)iplDepth, (int)3) : (this.imageMode == FrameGrabber.ImageMode.COLOR && numChannels == 1 && !coloryuv && !colorbayer ? IplImage.createHeader((int)w, (int)h, (int)iplDepth, (int)1) : this.return_image);
                }
            }
            this.conv_image.size(0, this.temp_image.width());
            this.conv_image.size(1, this.temp_image.height());
            if (depth > 8) {
                this.conv_image.color_coding(this.imageMode == FrameGrabber.ImageMode.RAW ? 362 : (this.temp_image.nChannels() == 1 ? 357 : 358));
                this.conv_image.data_depth(16);
            } else {
                this.conv_image.color_coding(this.imageMode == FrameGrabber.ImageMode.RAW ? 361 : (this.temp_image.nChannels() == 1 ? 352 : 356));
                this.conv_image.data_depth(8);
            }
            this.conv_image.stride(this.temp_image.widthStep());
            int temp_size = this.temp_image.imageSize();
            this.conv_image.allocated_image_bytes((long)temp_size).total_bytes((long)temp_size).image_bytes(temp_size);
            this.conv_image.image(this.temp_image.imageData());
            if (colorbayer) {
                int c = this.frame.color_filter();
                if (c == 512) {
                    this.frame.color_filter(515);
                } else if (c == 513) {
                    this.frame.color_filter(514);
                } else if (c == 514) {
                    this.frame.color_filter(513);
                } else if (c == 515) {
                    this.frame.color_filter(512);
                } else assert (false);
                err = dc1394.dc1394_debayer_frames((dc1394video_frame_t)this.frame, (dc1394video_frame_t)this.conv_image, (int)1);
                this.frame.color_filter(c);
                if (err != 0) {
                    throw new FrameGrabber.Exception("dc1394_debayer_frames() Error " + err + ": Could not debayer frame.");
                }
            } else if (depth > 8 && this.frame.data_depth() == this.conv_image.data_depth() && this.frame.color_coding() == this.conv_image.color_coding() && this.frame.stride() == this.conv_image.stride()) {
                ShortBuffer in = this.frame.getByteBuffer().order(frameEndian).asShortBuffer();
                ShortBuffer out = this.temp_image.getByteBuffer().order(ByteOrder.nativeOrder()).asShortBuffer();
                out.put(in);
                alreadySwapped = true;
            } else if (this.imageMode == FrameGrabber.ImageMode.GRAY && colorrgb || this.imageMode == FrameGrabber.ImageMode.COLOR && numChannels == 1 && !coloryuv && !colorbayer) {
                this.temp_image.widthStep(stride);
                this.temp_image.imageSize(size);
                this.temp_image.imageData(imageData);
            } else if (!colorrgb && (colorbayer || coloryuv || numChannels > 1) && (err = dc1394.dc1394_convert_frames((dc1394video_frame_t)this.frame, (dc1394video_frame_t)this.conv_image)) != 0) {
                throw new FrameGrabber.Exception("dc1394_convert_frames() Error " + err + ": Could not convert frame.");
            }
            if (!alreadySwapped && depth > 8 && !frameEndian.equals(ByteOrder.nativeOrder())) {
                ByteBuffer bb = this.temp_image.getByteBuffer();
                ShortBuffer in = bb.order(frameEndian).asShortBuffer();
                ShortBuffer out = bb.order(ByteOrder.nativeOrder()).asShortBuffer();
                out.put(in);
            }
            if (this.imageMode == FrameGrabber.ImageMode.COLOR && numChannels == 1 && !coloryuv && !colorbayer) {
                opencv_imgproc.cvCvtColor((CvArr)this.temp_image, (CvArr)this.return_image, (int)8);
            } else if (this.imageMode == FrameGrabber.ImageMode.GRAY && (colorbayer || colorrgb || coloryuv)) {
                opencv_imgproc.cvCvtColor((CvArr)this.temp_image, (CvArr)this.return_image, (int)6);
            }
        }
        switch (this.frame.color_filter()) {
            case 512: {
                this.sensorPattern = 0L;
                break;
            }
            case 513: {
                this.sensorPattern = 0x100000000L;
                break;
            }
            case 514: {
                this.sensorPattern = 1L;
                break;
            }
            case 515: {
                this.sensorPattern = 0x100000001L;
                break;
            }
            default: {
                this.sensorPattern = -1L;
            }
        }
        this.enqueue_image = this.frame;
        this.timestamp = this.frame.timestamp();
        this.frameNumber += numDequeued;
        return this.converter.convert(this.return_image);
    }
}

