/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jogamp.opencl.CLImage2d
 *  com.jogamp.opencl.CLMemory$Mem
 *  com.jogamp.opencl.CLObject
 *  com.jogamp.opencl.gl.CLGLImage2d
 *  com.jogamp.opengl.GL2
 *  com.jogamp.opengl.GLAutoDrawable
 *  com.jogamp.opengl.GLCapabilitiesImmutable
 *  com.jogamp.opengl.GLContext
 *  com.jogamp.opengl.GLEventListener
 *  com.jogamp.opengl.awt.GLCanvas
 *  com.jogamp.opengl.util.Gamma
 *  org.bytedeco.opencv.global.opencv_imgcodecs
 *  org.bytedeco.opencv.opencv_core.IplImage
 */
package org.bytedeco.javacv;

import com.jogamp.opencl.CLImage2d;
import com.jogamp.opencl.CLMemory;
import com.jogamp.opencl.CLObject;
import com.jogamp.opencl.gl.CLGLImage2d;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Gamma;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferDouble;
import java.awt.image.DataBufferFloat;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.DataBufferUShort;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.JavaCVCL;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.IplImage;

public class GLCanvasFrame
extends CanvasFrame {
    private int[] params = new int[2];
    private Color color = null;
    private int width;
    private int height;
    private int format;
    private int type;
    private Buffer buffer = null;
    private int frameBuffer = 0;
    private int renderBuffer = 0;
    private GLEventListener eventListener = new GLEventListener(){

        public void init(GLAutoDrawable drawable) {
            GL2 gl = drawable.getGL().getGL2();
            gl.setSwapInterval(1);
            if (GLCanvasFrame.this.inverseGamma != 1.0) {
                Gamma.setDisplayGamma((GLAutoDrawable)drawable, (float)((float)GLCanvasFrame.this.inverseGamma), (float)0.0f, (float)1.0f);
            }
            gl.glGenFramebuffers(1, GLCanvasFrame.this.params, 0);
            GLCanvasFrame.this.frameBuffer = GLCanvasFrame.this.params[0];
        }

        public void dispose(GLAutoDrawable drawable) {
            GL2 gl = drawable.getGL().getGL2();
            ((GLCanvasFrame)GLCanvasFrame.this).params[0] = GLCanvasFrame.this.frameBuffer;
            gl.glDeleteFramebuffers(1, GLCanvasFrame.this.params, 0);
            if (GLCanvasFrame.this.inverseGamma != 1.0) {
                Gamma.resetDisplayGamma((GLAutoDrawable)drawable);
            }
        }

        public void display(GLAutoDrawable drawable) {
            GL2 gl = drawable.getGL().getGL2();
            if (GLCanvasFrame.this.color != null) {
                gl.glClearColor((float)GLCanvasFrame.this.color.getRed() / 255.0f, (float)GLCanvasFrame.this.color.getGreen() / 255.0f, (float)GLCanvasFrame.this.color.getBlue() / 255.0f, 1.0f);
                gl.glClear(16384);
            } else if (GLCanvasFrame.this.buffer != null) {
                if (GLCanvasFrame.this.isResizable() && GLCanvasFrame.this.needInitialResize) {
                    int w = (int)Math.round((double)GLCanvasFrame.this.width * GLCanvasFrame.this.initialScale);
                    int h = (int)Math.round((double)GLCanvasFrame.this.height * GLCanvasFrame.this.initialScale);
                    GLCanvasFrame.this.setCanvasSize(w, h);
                }
                gl.glWindowPos2i(0, GLCanvasFrame.this.canvas.getHeight());
                gl.glPixelZoom((float)GLCanvasFrame.this.canvas.getWidth() / (float)GLCanvasFrame.this.width, -((float)GLCanvasFrame.this.canvas.getHeight()) / (float)GLCanvasFrame.this.height);
                gl.glDrawPixels(GLCanvasFrame.this.width, GLCanvasFrame.this.height, GLCanvasFrame.this.format, GLCanvasFrame.this.type, GLCanvasFrame.this.buffer);
            } else if (GLCanvasFrame.this.renderBuffer > 0) {
                gl.glBindRenderbuffer(36161, GLCanvasFrame.this.renderBuffer);
                gl.glGetRenderbufferParameteriv(36161, 36162, GLCanvasFrame.this.params, 0);
                gl.glGetRenderbufferParameteriv(36161, 36163, GLCanvasFrame.this.params, 1);
                if (GLCanvasFrame.this.isResizable() && GLCanvasFrame.this.needInitialResize) {
                    int w = (int)Math.round((double)GLCanvasFrame.this.params[0] * GLCanvasFrame.this.initialScale);
                    int h = (int)Math.round((double)GLCanvasFrame.this.params[1] * GLCanvasFrame.this.initialScale);
                    GLCanvasFrame.this.setCanvasSize(w, h);
                }
                gl.glBindFramebuffer(36008, GLCanvasFrame.this.frameBuffer);
                gl.glFramebufferRenderbuffer(36008, 36064, 36161, GLCanvasFrame.this.renderBuffer);
                assert (gl.glCheckFramebufferStatus(36008) == 36053);
                gl.glBlitFramebuffer(0, 0, GLCanvasFrame.this.params[0], GLCanvasFrame.this.params[1], 0, GLCanvasFrame.this.canvas.getHeight(), GLCanvasFrame.this.canvas.getWidth(), 0, 16384, 9729);
            }
        }

        public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        }
    };
    private static GLCanvasFrame canvasFrame;

    public GLCanvasFrame(String title) {
        this(title, 0.0);
    }

    public GLCanvasFrame(String title, double gamma) {
        super(title, gamma);
        this.init(false, null, null);
    }

    public GLCanvasFrame(String title, GraphicsConfiguration gc, GLCapabilitiesImmutable caps, GLContext shareWith) {
        this(title, gc, caps, shareWith, 0.0);
    }

    public GLCanvasFrame(String title, GraphicsConfiguration gc, GLCapabilitiesImmutable caps, GLContext shareWith, double gamma) {
        super(title, gc, gamma);
        this.init(false, caps, shareWith);
    }

    public GLCanvasFrame(String title, int screenNumber, DisplayMode displayMode) throws CanvasFrame.Exception {
        this(title, screenNumber, displayMode, 0.0);
    }

    public GLCanvasFrame(String title, int screenNumber, DisplayMode displayMode, double gamma) throws CanvasFrame.Exception {
        super(title, screenNumber, displayMode, gamma);
        this.init(true, null, null);
    }

    public GLCanvasFrame(String title, int screenNumber, DisplayMode displayMode, GLCapabilitiesImmutable caps, GLContext shareWith) throws CanvasFrame.Exception {
        this(title, screenNumber, displayMode, caps, shareWith, 0.0);
    }

    public GLCanvasFrame(String title, int screenNumber, DisplayMode displayMode, GLCapabilitiesImmutable caps, GLContext shareWith, double gamma) throws CanvasFrame.Exception {
        super(title, screenNumber, displayMode, gamma);
        this.init(true, caps, shareWith);
    }

    private void init(final boolean fullScreen, final GLCapabilitiesImmutable caps, final GLContext shareWith) {
        Runnable r = new Runnable(){

            @Override
            public void run() {
                String wasErase = System.setProperty("sun.awt.noerasebackground", "true");
                GLCanvasFrame.this.canvas = new GLCanvas(caps);
                if (shareWith != null) {
                    ((GLCanvas)GLCanvasFrame.this.canvas).setSharedContext(shareWith);
                }
                ((GLCanvas)GLCanvasFrame.this.canvas).addGLEventListener(GLCanvasFrame.this.eventListener);
                if (fullScreen) {
                    GLCanvasFrame.this.canvas.setSize(GLCanvasFrame.this.getSize());
                    GLCanvasFrame.this.needInitialResize = false;
                } else {
                    GLCanvasFrame.this.canvas.setSize(1, 1);
                    GLCanvasFrame.this.needInitialResize = true;
                }
                GLCanvasFrame.this.getContentPane().add(GLCanvasFrame.this.canvas);
                GLCanvasFrame.this.canvas.setVisible(true);
                if (wasErase != null) {
                    System.setProperty("sun.awt.noerasebackground", wasErase);
                } else {
                    System.clearProperty("sun.awt.noerasebackground");
                }
            }
        };
        if (EventQueue.isDispatchThread()) {
            r.run();
        } else {
            try {
                EventQueue.invokeAndWait(r);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Override
    protected void initCanvas(boolean fullScreen, DisplayMode displayMode, double gamma) {
    }

    public GLCanvas getGLCanvas() {
        return (GLCanvas)this.canvas;
    }

    @Override
    public void showColor(Color color) {
        this.color = color;
        this.buffer = null;
        this.getGLCanvas().display();
    }

    @Override
    public void showImage(Frame frame) {
        this.showImage(frame, false);
    }

    @Override
    public void showImage(Frame frame, boolean flipChannels) {
        if (flipChannels) {
            throw new RuntimeException("GLCanvasFrame does not support channel flipping.");
        }
        if (frame == null) {
            return;
        }
        this.color = null;
        this.width = frame.imageWidth;
        this.height = frame.imageHeight;
        this.buffer = frame.image[0];
        switch (frame.imageDepth) {
            case -8: {
                this.type = 5120;
                break;
            }
            case 8: {
                this.type = 5121;
                break;
            }
            case -16: {
                this.type = 5122;
                break;
            }
            case 16: {
                this.type = 5123;
                break;
            }
            case -32: {
                this.type = 5124;
                break;
            }
            case 32: {
                this.type = 5126;
                break;
            }
            case 64: {
                this.type = 5130;
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        switch (frame.imageChannels) {
            case 1: {
                this.format = 6409;
                break;
            }
            case 2: {
                this.format = 33319;
                break;
            }
            case 3: {
                this.format = 6407;
                break;
            }
            case 4: {
                this.format = 6408;
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        this.getGLCanvas().display();
    }

    @Override
    public void showImage(Image image) {
        if (!(image instanceof BufferedImage)) {
            throw new RuntimeException("GLCanvasFrame does not support " + image + ", BufferedImage required.");
        }
        this.showImage((BufferedImage)image);
    }

    public void showImage(BufferedImage image) {
        if (image == null) {
            return;
        }
        this.color = null;
        this.width = image.getWidth();
        this.height = image.getHeight();
        DataBuffer buffer = image.getRaster().getDataBuffer();
        if (buffer instanceof DataBufferByte) {
            this.buffer = ByteBuffer.wrap(((DataBufferByte)buffer).getData());
            this.type = 5121;
        } else if (buffer instanceof DataBufferDouble) {
            this.buffer = DoubleBuffer.wrap(((DataBufferDouble)buffer).getData());
            this.type = 5130;
        } else if (buffer instanceof DataBufferFloat) {
            this.buffer = FloatBuffer.wrap(((DataBufferFloat)buffer).getData());
            this.type = 5126;
        } else if (buffer instanceof DataBufferInt) {
            this.buffer = IntBuffer.wrap(((DataBufferInt)buffer).getData());
            this.type = 5124;
        } else if (buffer instanceof DataBufferShort) {
            this.buffer = ShortBuffer.wrap(((DataBufferShort)buffer).getData());
            this.type = 5122;
        } else if (buffer instanceof DataBufferUShort) {
            this.buffer = ShortBuffer.wrap(((DataBufferUShort)buffer).getData());
            this.type = 5123;
        } else assert (false);
        switch (image.getSampleModel().getNumBands()) {
            case 1: {
                this.format = 6409;
                break;
            }
            case 2: {
                this.format = 33319;
                break;
            }
            case 3: {
                this.format = 6407;
                break;
            }
            case 4: {
                this.format = 6408;
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        this.getGLCanvas().display();
    }

    public void showImage(int renderBuffer) {
        if (renderBuffer <= 0) {
            return;
        }
        this.color = null;
        this.buffer = null;
        this.renderBuffer = renderBuffer;
        this.getGLCanvas().display();
    }

    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(new Runnable(){

            @Override
            public void run() {
                try {
                    canvasFrame = new GLCanvasFrame("Some Title");
                    canvasFrame.setDefaultCloseOperation(3);
                    canvasFrame.showColor(Color.BLUE);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        JavaCVCL context = new JavaCVCL(canvasFrame.getGLCanvas().getContext());
        IplImage image = opencv_imgcodecs.cvLoadImageBGRA((String)"/usr/share/opencv/samples/c/lena.jpg");
        CLGLImage2d imageCLGL = context.createCLGLImageFrom(image, new CLMemory.Mem[0]);
        context.acquireGLObject((CLObject)imageCLGL);
        context.writeImage((CLImage2d)imageCLGL, image, true);
        context.releaseGLObject((CLObject)imageCLGL);
        canvasFrame.setCanvasScale(0.5);
        for (int i = 0; i < 1000; ++i) {
            canvasFrame.showImage(imageCLGL.getGLObjectID());
            Thread.sleep(10L);
            canvasFrame.showColor(Color.RED);
            Thread.sleep(10L);
        }
        canvasFrame.waitKey();
        context.release();
        System.exit(0);
    }
}

