/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jogamp.opencl.CLBuffer
 *  com.jogamp.opencl.CLCommandQueue
 *  com.jogamp.opencl.CLContext
 *  com.jogamp.opencl.CLDevice
 *  com.jogamp.opencl.CLEventList
 *  com.jogamp.opencl.CLImage2d
 *  com.jogamp.opencl.CLImageFormat
 *  com.jogamp.opencl.CLImageFormat$ChannelOrder
 *  com.jogamp.opencl.CLImageFormat$ChannelType
 *  com.jogamp.opencl.CLKernel
 *  com.jogamp.opencl.CLMemory
 *  com.jogamp.opencl.CLMemory$Map
 *  com.jogamp.opencl.CLMemory$Mem
 *  com.jogamp.opencl.CLObject
 *  com.jogamp.opencl.CLPlatform
 *  com.jogamp.opencl.CLProgram
 *  com.jogamp.opencl.gl.CLGLContext
 *  com.jogamp.opencl.gl.CLGLImage2d
 *  com.jogamp.opencl.gl.CLGLObject
 *  com.jogamp.opengl.GL
 *  com.jogamp.opengl.GL2
 *  com.jogamp.opengl.GLCapabilities
 *  com.jogamp.opengl.GLCapabilitiesImmutable
 *  com.jogamp.opengl.GLContext
 *  com.jogamp.opengl.GLProfile
 *  com.jogamp.opengl.glu.GLU
 *  org.bytedeco.opencv.global.opencv_core
 *  org.bytedeco.opencv.global.opencv_imgcodecs
 *  org.bytedeco.opencv.global.opencv_imgproc
 *  org.bytedeco.opencv.opencv_core.CvArr
 *  org.bytedeco.opencv.opencv_core.CvMat
 *  org.bytedeco.opencv.opencv_core.CvScalar
 *  org.bytedeco.opencv.opencv_core.CvSize
 *  org.bytedeco.opencv.opencv_core.IplImage
 *  org.bytedeco.opencv.opencv_core.IplROI
 */
package org.bytedeco.javacv;

import com.jogamp.opencl.CLBuffer;
import com.jogamp.opencl.CLCommandQueue;
import com.jogamp.opencl.CLContext;
import com.jogamp.opencl.CLDevice;
import com.jogamp.opencl.CLEventList;
import com.jogamp.opencl.CLImage2d;
import com.jogamp.opencl.CLImageFormat;
import com.jogamp.opencl.CLKernel;
import com.jogamp.opencl.CLMemory;
import com.jogamp.opencl.CLObject;
import com.jogamp.opencl.CLPlatform;
import com.jogamp.opencl.CLProgram;
import com.jogamp.opencl.gl.CLGLContext;
import com.jogamp.opencl.gl.CLGLImage2d;
import com.jogamp.opencl.gl.CLGLObject;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Vector;
import java.util.logging.Logger;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacv.CameraDevice;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvArr;
import org.bytedeco.opencv.opencv_core.CvMat;
import org.bytedeco.opencv.opencv_core.CvScalar;
import org.bytedeco.opencv.opencv_core.CvSize;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.IplROI;

public class JavaCVCL {
    public static final String fastCompilerOptions = "-cl-fast-relaxed-math -cl-mad-enable";
    private static final Logger logger = Logger.getLogger(JavaCVCL.class.getName());
    private final CLContext context;
    private final CLCommandQueue commandQueue;
    private final GLU glu;
    private final CLKernel pyrDownKernel;
    private final CLKernel remapKernel;
    private final CLKernel remapBayerKernel;

    public JavaCVCL(CLContext context) {
        this(context, context.getDevices()[0]);
    }

    public JavaCVCL(CLContext context, CLDevice device) {
        this.context = context;
        this.glu = context instanceof CLGLContext ? new GLU() : null;
        this.commandQueue = device.createCommandQueue();
        CLKernel[] kernels = this.buildKernels(fastCompilerOptions, "JavaCV.cl", "pyrDown", "remap", "remapBayer");
        this.pyrDownKernel = kernels[0];
        this.remapKernel = kernels[1];
        this.remapBayerKernel = kernels[2];
    }

    public static GLCapabilities getDefaultGLCapabilities(GLProfile profile) {
        GLCapabilities caps = new GLCapabilities(profile != null ? profile : GLProfile.getDefault());
        caps.setDoubleBuffered(false);
        return caps;
    }

    public JavaCVCL() {
        this(false);
    }

    public JavaCVCL(boolean createPbuffer) {
        this((GLCapabilitiesImmutable)(createPbuffer ? JavaCVCL.getDefaultGLCapabilities(null) : null), null, null);
    }

    public JavaCVCL(GLContext shareWith) {
        this((GLCapabilitiesImmutable)JavaCVCL.getDefaultGLCapabilities(shareWith == null ? null : shareWith.getGLDrawable().getGLProfile()), shareWith, null);
    }

    public JavaCVCL(GLCapabilitiesImmutable caps, GLContext shareWith, CLDevice device) {
        GLContext glContext = GLContext.getCurrent();
        if (device == null && glContext != null) {
            CLDevice[] devices;
            for (CLDevice d : devices = CLPlatform.getDefault().listCLDevices()) {
                if (!d.isGLMemorySharingSupported()) continue;
                device = d;
                break;
            }
        }
        if (glContext != null && device != null) {
            this.context = CLGLContext.create((GLContext)glContext, (CLDevice[])new CLDevice[]{device});
            this.glu = GLU.createGLU();
        } else if (device != null) {
            this.context = CLContext.create((CLDevice[])new CLDevice[]{device});
            this.glu = null;
        } else {
            this.context = CLContext.create();
            device = this.context.getDevices()[0];
            this.glu = null;
        }
        this.commandQueue = device.createCommandQueue();
        CLKernel[] kernels = this.buildKernels(fastCompilerOptions, "JavaCV.cl", "pyrDown", "remap", "remapBayer");
        this.pyrDownKernel = kernels[0];
        this.remapKernel = kernels[1];
        this.remapBayerKernel = kernels[2];
    }

    public void release() {
        if (!this.context.isReleased()) {
            this.context.release();
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.release();
    }

    public CLContext getCLContext() {
        return this.context;
    }

    public CLCommandQueue getCLCommandQueue() {
        return this.commandQueue;
    }

    public CLGLContext getCLGLContext() {
        return this.context instanceof CLGLContext ? (CLGLContext)this.context : null;
    }

    public GLContext getGLContext() {
        return this.context instanceof CLGLContext ? ((CLGLContext)this.context).getGLContext() : null;
    }

    public GL getGL() {
        GLContext glContext = this.getGLContext();
        return glContext != null ? glContext.getGL() : null;
    }

    public GL2 getGL2() {
        GL gl = this.getGL();
        return gl != null ? gl.getGL2() : null;
    }

    public GLU getGLU() {
        return this.glu;
    }

    public CLKernel buildKernel(String resourceNames, String kernelName) {
        return this.buildKernels(fastCompilerOptions, Loader.getCallerClass(2), resourceNames, kernelName)[0];
    }

    public CLKernel buildKernel(String compilerOptions, String resourceNames, String kernelName) {
        return this.buildKernels(compilerOptions, Loader.getCallerClass(2), resourceNames, kernelName)[0];
    }

    public CLKernel[] buildKernels(String compilerOptions, String resourceNames, String ... kernelNames) {
        return this.buildKernels(compilerOptions, Loader.getCallerClass(2), resourceNames, kernelNames);
    }

    public CLKernel[] buildKernels(String compilerOptions, Class resourceClass, String resourceNames, String ... kernelNames) {
        try {
            InputStream s;
            String[] a = resourceNames.split(":");
            if (a.length == 1) {
                s = resourceClass.getResourceAsStream(a[0]);
            } else {
                Vector<InputStream> vs = new Vector<InputStream>(a.length);
                for (String name : a) {
                    vs.addElement(resourceClass.getResourceAsStream(name));
                }
                s = new SequenceInputStream(vs.elements());
            }
            CLProgram program = this.context.createProgram(s);
            program.build(compilerOptions);
            assert (program.isExecutable());
            CLKernel[] kernels = new CLKernel[kernelNames.length];
            for (int i = 0; i < kernelNames.length; ++i) {
                kernels[i] = program.createCLKernel(kernelNames[i]);
            }
            return kernels;
        }
        catch (IOException ex) {
            throw (Error)new LinkageError(ex.toString()).initCause(ex);
        }
    }

    public CLImage2d createCLImageFrom(IplImage image, CLMemory.Mem ... flags) {
        int width = image.width();
        int height = image.height();
        int pitch = image.widthStep();
        ByteBuffer buffer = image.getByteBuffer();
        CLImageFormat.ChannelOrder order = null;
        CLImageFormat.ChannelType type = null;
        int size = 0;
        switch (image.depth()) {
            case -2147483640: {
                type = CLImageFormat.ChannelType.SNORM_INT8;
                size = 1;
                break;
            }
            case 8: {
                type = CLImageFormat.ChannelType.UNORM_INT8;
                size = 1;
                break;
            }
            case -2147483632: {
                type = CLImageFormat.ChannelType.SNORM_INT16;
                size = 2;
                break;
            }
            case 16: {
                type = CLImageFormat.ChannelType.UNORM_INT16;
                size = 2;
                break;
            }
            case -2147483616: {
                type = CLImageFormat.ChannelType.SIGNED_INT32;
                size = 4;
                break;
            }
            case 32: {
                type = CLImageFormat.ChannelType.FLOAT;
                size = 4;
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        switch (image.nChannels()) {
            case 1: {
                order = CLImageFormat.ChannelOrder.LUMINANCE;
                break;
            }
            case 2: {
                order = CLImageFormat.ChannelOrder.RG;
                size *= 2;
                break;
            }
            case 3: {
                order = CLImageFormat.ChannelOrder.RGB;
                size *= 3;
                break;
            }
            case 4: {
                order = CLImageFormat.ChannelOrder.RGBA;
                size *= 4;
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        if (width != pitch / size) {
            width = pitch / size;
        }
        CLImageFormat format = new CLImageFormat(order, type);
        return this.context.createImage2d((Buffer)buffer, width, height, format, flags);
    }

    public CLGLImage2d createCLGLImageFrom(IplImage image, CLMemory.Mem ... flags) {
        GL2 gl = this.getGL2();
        if (gl == null) {
            return null;
        }
        int width = image.width();
        int height = image.height();
        int pitch = image.widthStep();
        int format = 0;
        int size = 0;
        block0 : switch (image.nChannels()) {
            case 1: {
                switch (image.depth()) {
                    case -2147483640: {
                        format = 36885;
                        size = 1;
                        break block0;
                    }
                    case 8: {
                        format = 32832;
                        size = 1;
                        break block0;
                    }
                    case -2147483632: {
                        format = 36889;
                        size = 2;
                        break block0;
                    }
                    case 16: {
                        format = 32834;
                        size = 2;
                        break block0;
                    }
                    case -2147483616: {
                        format = 36230;
                        size = 4;
                        break block0;
                    }
                    case 32: {
                        format = 34840;
                        size = 4;
                        break block0;
                    }
                }
                assert (false);
                break;
            }
            case 2: {
                switch (image.depth()) {
                    case -2147483640: {
                        format = 36757;
                        size = 2;
                        break block0;
                    }
                    case 8: {
                        format = 33323;
                        size = 2;
                        break block0;
                    }
                    case -2147483632: {
                        format = 36761;
                        size = 4;
                        break block0;
                    }
                    case 16: {
                        format = 33324;
                        size = 4;
                        break block0;
                    }
                    case -2147483616: {
                        format = 33339;
                        size = 8;
                        break block0;
                    }
                    case 32: {
                        format = 33328;
                        size = 8;
                        break block0;
                    }
                }
                assert (false);
                break;
            }
            case 3: {
                switch (image.depth()) {
                    case -2147483640: {
                        format = 36758;
                        size = 3;
                        break block0;
                    }
                    case 8: {
                        format = 32849;
                        size = 3;
                        break block0;
                    }
                    case -2147483632: {
                        format = 36762;
                        size = 6;
                        break block0;
                    }
                    case 16: {
                        format = 32852;
                        size = 6;
                        break block0;
                    }
                    case -2147483616: {
                        format = 36227;
                        size = 12;
                        break block0;
                    }
                    case 32: {
                        format = 34837;
                        size = 12;
                        break block0;
                    }
                }
                assert (false);
                break;
            }
            case 4: {
                switch (image.depth()) {
                    case -2147483640: {
                        format = 36759;
                        size = 4;
                        break block0;
                    }
                    case 8: {
                        format = 32856;
                        size = 4;
                        break block0;
                    }
                    case -2147483632: {
                        format = 36763;
                        size = 8;
                        break block0;
                    }
                    case 16: {
                        format = 32859;
                        size = 8;
                        break block0;
                    }
                    case -2147483616: {
                        format = 36226;
                        size = 16;
                        break block0;
                    }
                    case 32: {
                        format = 34836;
                        size = 16;
                        break block0;
                    }
                }
                assert (false);
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        if (width != pitch / size) {
            width = pitch / size;
        }
        int[] renderBuffer = new int[1];
        gl.glGenRenderbuffers(1, renderBuffer, 0);
        gl.glBindRenderbuffer(36161, renderBuffer[0]);
        gl.glRenderbufferStorage(36161, format, width, height);
        return this.getCLGLContext().createFromGLRenderbuffer(renderBuffer[0], flags);
    }

    public void releaseCLGLImage(CLGLImage2d image) {
        image.release();
        this.getGL2().glDeleteRenderbuffers(1, new int[]{image.getGLObjectID()}, 0);
    }

    public CLBuffer createPinnedBuffer(int size) {
        CLBuffer pinnedBuffer = this.context.createBuffer(size, new CLMemory.Mem[]{CLMemory.Mem.ALLOCATE_BUFFER});
        ByteBuffer byteBuffer = this.commandQueue.putMapBuffer(pinnedBuffer, CLMemory.Map.READ_WRITE, true);
        pinnedBuffer.use((Buffer)byteBuffer);
        return pinnedBuffer;
    }

    public IplImage createPinnedIplImage(int width, int height, int depth, int channels) {
        return new PinnedIplImage(width, height, depth, channels);
    }

    public IplImage createIplImageFrom(CLImage2d image) {
        int width = image.width;
        int height = image.height;
        CLImageFormat format = image.getFormat();
        CLImageFormat.ChannelOrder order = format.getImageChannelOrder();
        CLImageFormat.ChannelType type = format.getImageChannelDataType();
        int depth = 0;
        int channels = 0;
        switch (order) {
            case R: 
            case A: 
            case INTENSITY: 
            case LUMINANCE: {
                channels = 1;
                break;
            }
            case Rx: 
            case RG: 
            case RA: {
                channels = 2;
                break;
            }
            case RGx: 
            case RGB: {
                channels = 3;
                break;
            }
            case RGBx: 
            case RGBA: 
            case ARGB: 
            case BGRA: {
                channels = 4;
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        switch (type) {
            case SIGNED_INT8: 
            case SNORM_INT8: {
                depth = -2147483640;
                break;
            }
            case UNSIGNED_INT8: 
            case UNORM_INT8: {
                depth = 8;
                break;
            }
            case SIGNED_INT16: 
            case SNORM_INT16: {
                depth = -2147483632;
                break;
            }
            case UNSIGNED_INT16: 
            case UNORM_INT16: {
                depth = 16;
                break;
            }
            case UNSIGNED_INT32: 
            case SIGNED_INT32: {
                depth = -2147483616;
                break;
            }
            case FLOAT: {
                depth = 32;
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        return IplImage.create((int)width, (int)height, (int)depth, (int)channels);
    }

    public IplImage readImage(CLImage2d clImg, IplImage iplImage, boolean blocking) {
        if (iplImage == null) {
            iplImage = this.createIplImageFrom(clImg);
        }
        int x = 0;
        int y = 0;
        int width = clImg.width;
        int height = clImg.height;
        int pitch = iplImage.widthStep();
        ByteBuffer buffer = iplImage.getByteBuffer();
        IplROI roi = iplImage.roi();
        if (roi != null) {
            x = roi.xOffset();
            y = roi.yOffset();
            width = roi.width();
            height = roi.height();
            int pixelSize = iplImage.nChannels() * ((iplImage.depth() & Integer.MAX_VALUE) / 8);
            buffer = iplImage.getByteBuffer(y * pitch + x * pixelSize);
        }
        clImg.use((Buffer)buffer);
        this.commandQueue.putReadImage(clImg, pitch, x, y, width, height, blocking);
        return iplImage;
    }

    public CLImage2d writeImage(CLImage2d clImg, IplImage iplImage, boolean blocking) {
        if (clImg == null) {
            clImg = this.createCLImageFrom(iplImage, new CLMemory.Mem[0]);
        }
        int x = 0;
        int y = 0;
        int width = iplImage.width();
        int height = iplImage.height();
        int pitch = iplImage.widthStep();
        ByteBuffer buffer = iplImage.getByteBuffer();
        IplROI roi = iplImage.roi();
        if (roi != null) {
            x = roi.xOffset();
            y = roi.yOffset();
            width = roi.width();
            height = roi.height();
            int pixelSize = iplImage.nChannels() * ((iplImage.depth() & Integer.MAX_VALUE) / 8);
            buffer = iplImage.getByteBuffer(y * pitch + x * pixelSize);
        }
        clImg.use((Buffer)buffer);
        this.commandQueue.putWriteImage(clImg, pitch, x, y, width, height, blocking);
        return clImg;
    }

    public void acquireGLObject(CLObject object) {
        if (object instanceof CLGLObject) {
            this.commandQueue.putAcquireGLObject((CLGLObject)object);
        }
    }

    public void releaseGLObject(CLObject object) {
        if (object instanceof CLGLObject) {
            this.commandQueue.putReleaseGLObject((CLGLObject)object);
        }
    }

    public void readBuffer(CLBuffer<?> buffer, boolean blocking) {
        this.commandQueue.putReadBuffer(buffer, blocking);
    }

    public void writeBuffer(CLBuffer<?> buffer, boolean blocking) {
        this.commandQueue.putWriteBuffer(buffer, blocking);
    }

    public void executeKernel(CLKernel kernel, long globalWorkOffsetX, long globalWorkSizeX, long localWorkSizeX) {
        this.commandQueue.put1DRangeKernel(kernel, globalWorkOffsetX, globalWorkSizeX, localWorkSizeX);
    }

    public void executeKernel(CLKernel kernel, long globalWorkOffsetX, long globalWorkSizeX, long localWorkSizeX, CLEventList events) {
        this.commandQueue.put1DRangeKernel(kernel, globalWorkOffsetX, globalWorkSizeX, localWorkSizeX, events);
    }

    public void executeKernel(CLKernel kernel, long globalWorkOffsetX, long globalWorkSizeX, long localWorkSizeX, CLEventList condition, CLEventList events) {
        this.commandQueue.put1DRangeKernel(kernel, globalWorkOffsetX, globalWorkSizeX, localWorkSizeX, condition, events);
    }

    public void executeKernel(CLKernel kernel, long globalWorkOffsetX, long globalWorkOffsetY, long globalWorkSizeX, long globalWorkSizeY, long localWorkSizeX, long localWorkSizeY) {
        this.commandQueue.put2DRangeKernel(kernel, globalWorkOffsetX, globalWorkOffsetY, globalWorkSizeX, globalWorkSizeY, localWorkSizeX, localWorkSizeY);
    }

    public void executeKernel(CLKernel kernel, long globalWorkOffsetX, long globalWorkOffsetY, long globalWorkSizeX, long globalWorkSizeY, long localWorkSizeX, long localWorkSizeY, CLEventList events) {
        this.commandQueue.put2DRangeKernel(kernel, globalWorkOffsetX, globalWorkOffsetY, globalWorkSizeX, globalWorkSizeY, localWorkSizeX, localWorkSizeY, events);
    }

    public void executeKernel(CLKernel kernel, long globalWorkOffsetX, long globalWorkOffsetY, long globalWorkSizeX, long globalWorkSizeY, long localWorkSizeX, long localWorkSizeY, CLEventList condition, CLEventList events) {
        this.commandQueue.put2DRangeKernel(kernel, globalWorkOffsetX, globalWorkOffsetY, globalWorkSizeX, globalWorkSizeY, localWorkSizeX, localWorkSizeY, condition, events);
    }

    public void executeKernel(CLKernel kernel, long globalWorkOffsetX, long globalWorkOffsetY, long globalWorkOffsetZ, long globalWorkSizeX, long globalWorkSizeY, long globalWorkSizeZ, long localWorkSizeX, long localWorkSizeY, long localWorkSizeZ) {
        this.commandQueue.put3DRangeKernel(kernel, globalWorkOffsetX, globalWorkOffsetY, globalWorkOffsetZ, globalWorkSizeX, globalWorkSizeY, globalWorkSizeZ, localWorkSizeX, localWorkSizeY, localWorkSizeZ);
    }

    public void executeKernel(CLKernel kernel, long globalWorkOffsetX, long globalWorkOffsetY, long globalWorkOffsetZ, long globalWorkSizeX, long globalWorkSizeY, long globalWorkSizeZ, long localWorkSizeX, long localWorkSizeY, long localWorkSizeZ, CLEventList events) {
        this.commandQueue.put3DRangeKernel(kernel, globalWorkOffsetX, globalWorkOffsetY, globalWorkOffsetZ, globalWorkSizeX, globalWorkSizeY, globalWorkSizeZ, localWorkSizeX, localWorkSizeY, localWorkSizeZ, events);
    }

    public void executeKernel(CLKernel kernel, long globalWorkOffsetX, long globalWorkOffsetY, long globalWorkOffsetZ, long globalWorkSizeX, long globalWorkSizeY, long globalWorkSizeZ, long localWorkSizeX, long localWorkSizeY, long localWorkSizeZ, CLEventList condition, CLEventList events) {
        this.commandQueue.put3DRangeKernel(kernel, globalWorkOffsetX, globalWorkOffsetY, globalWorkOffsetZ, globalWorkSizeX, globalWorkSizeY, globalWorkSizeZ, localWorkSizeX, localWorkSizeY, localWorkSizeZ, condition, events);
    }

    public void finish() {
        this.commandQueue.finish();
    }

    public void flush() {
        this.commandQueue.flush();
    }

    public static int alignCeil(int x, int n) {
        return (x + n - 1) / n * n;
    }

    public static int alignFloor(int x, int n) {
        return x / n * n;
    }

    public void pyrDown(CLImage2d srcImg, CLImage2d dstImg) {
        CLEventList list = null;
        this.pyrDownKernel.putArg((CLMemory)srcImg).putArg((CLMemory)dstImg).rewind();
        this.executeKernel(this.pyrDownKernel, 0L, 0L, JavaCVCL.alignCeil(dstImg.width, 2), JavaCVCL.alignCeil(dstImg.height, 64), 2L, 64L, list);
    }

    public void remap(CLImage2d srcImg, CLImage2d dstImg, CLImage2d mapxImg, CLImage2d mapyImg) {
        this.remap(srcImg, dstImg, mapxImg, mapyImg, -1L);
    }

    public void remap(CLImage2d srcImg, CLImage2d dstImg, CLImage2d mapxImg, CLImage2d mapyImg, long sensorPattern) {
        CLEventList list = null;
        CLKernel kernel = sensorPattern != -1L ? this.remapBayerKernel.putArg((CLMemory)srcImg).putArg((CLMemory)dstImg).putArg((CLMemory)mapxImg).putArg((CLMemory)mapyImg).putArg(sensorPattern).rewind() : this.remapKernel.putArg((CLMemory)srcImg).putArg((CLMemory)dstImg).putArg((CLMemory)mapxImg).putArg((CLMemory)mapyImg).rewind();
        this.executeKernel(kernel, 0L, 0L, JavaCVCL.alignCeil(dstImg.width, 2), JavaCVCL.alignCeil(dstImg.height, 64), 2L, 64L, list);
    }

    public static void main(String[] args) {
        CLImageFormat[] formats;
        JavaCVCL context = new JavaCVCL();
        for (CLImageFormat f : formats = context.getCLContext().getSupportedImage2dFormats(new CLMemory.Mem[0])) {
            System.out.println(f);
        }
        CameraDevice camera = new CameraDevice("Camera");
        camera.imageWidth = 1280;
        camera.imageHeight = 960;
        camera.cameraMatrix = CvMat.create((int)3, (int)3);
        double f = (double)camera.imageWidth * 2.5;
        camera.cameraMatrix.put(new double[]{f, 0.0, camera.imageWidth / 2, 0.0, f, camera.imageHeight / 2, 0.0, 0.0, 1.0});
        camera.R = CvMat.create((int)3, (int)3);
        opencv_core.cvSetIdentity((CvArr)camera.R);
        camera.T = CvMat.create((int)3, (int)1);
        opencv_core.cvSetZero((CvArr)camera.T);
        camera.distortionCoeffs = CvMat.create((int)1, (int)4);
        opencv_core.cvSetZero((CvArr)camera.distortionCoeffs);
        camera.distortionCoeffs.put(new double[]{0.2});
        camera.colorMixingMatrix = CvMat.create((int)3, (int)3);
        opencv_core.cvSetIdentity((CvArr)camera.colorMixingMatrix);
        IplImage srcImg = opencv_imgcodecs.cvLoadImageRGBA((String)args[0]);
        IplImage downDst = IplImage.create((int)(srcImg.width() / 2), (int)(srcImg.height() / 2), (int)8, (int)4);
        camera.setFixedPointMaps(false);
        camera.setMapsPyramidLevel(1);
        IplImage mapxImg = camera.getUndistortMap1();
        IplImage mapyImg = camera.getUndistortMap2();
        long start = System.nanoTime();
        opencv_imgproc.cvRemap((CvArr)srcImg, (CvArr)downDst, (CvArr)mapxImg, (CvArr)mapyImg, (int)9, (CvScalar)CvScalar.ZERO);
        System.out.println("cvRemap: " + (double)(System.nanoTime() - start) / 1000000.0);
        opencv_imgcodecs.cvSaveImage((String)"/tmp/opencv.png", (CvArr)downDst);
        CLImage2d src = context.createCLImageFrom(srcImg, new CLMemory.Mem[0]);
        CLImage2d dst = context.createCLImageFrom(downDst, new CLMemory.Mem[0]);
        CLImage2d mapx = context.createCLImageFrom(mapxImg, new CLMemory.Mem[0]);
        CLImage2d mapy = context.createCLImageFrom(mapyImg, new CLMemory.Mem[0]);
        context.writeImage(src, srcImg, false);
        context.writeImage(mapx, mapxImg, false);
        context.writeImage(mapy, mapyImg, false);
        context.remap(src, dst, mapx, mapy);
        context.readImage(dst, downDst, true);
        opencv_imgcodecs.cvSaveImage((String)"/tmp/javacvcl.png", (CvArr)downDst);
        context.release();
        System.exit(0);
    }

    class PinnedIplImage
    extends IplImage {
        final CLBuffer pinnedBuffer;

        PinnedIplImage(int width, int height, int depth, int channels) {
            super((Pointer)opencv_core.cvCreateImageHeader((CvSize)new CvSize().width(width).height(height), (int)depth, (int)channels));
            this.pinnedBuffer = JavaCVCL.this.createPinnedBuffer(this.imageSize());
            this.imageData(new BytePointer(this.getByteBuffer()));
        }

        public CLBuffer getCLBuffer() {
            return this.pinnedBuffer;
        }

        public ByteBuffer getByteBuffer() {
            return (ByteBuffer)this.pinnedBuffer.getBuffer();
        }

        public void release() {
            JavaCVCL.this.commandQueue.putUnmapMemory((CLMemory)this.pinnedBuffer, (Buffer)this.getByteBuffer());
            this.pinnedBuffer.release();
            opencv_core.cvReleaseImageHeader((IplImage)this);
        }
    }
}

