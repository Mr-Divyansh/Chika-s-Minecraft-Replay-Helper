/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_ProfileRGB;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

public class CanvasFrame
extends JFrame {
    public static CanvasFrame global = null;
    public static final long DEFAULT_LATENCY = 200L;
    private long latency = 200L;
    private KeyEvent keyEvent = null;
    private KeyEventDispatcher keyEventDispatch = new KeyEventDispatcher(){

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == 401) {
                CanvasFrame canvasFrame = CanvasFrame.this;
                synchronized (canvasFrame) {
                    CanvasFrame.this.keyEvent = e;
                    CanvasFrame.this.notify();
                }
            }
            return false;
        }
    };
    protected Canvas canvas = null;
    protected boolean needInitialResize = false;
    protected double initialScale = 1.0;
    protected double inverseGamma = 1.0;
    private Color color = null;
    private Image image = null;
    private BufferedImage buffer = null;
    private Java2DFrameConverter converter = new Java2DFrameConverter();

    public static String[] getScreenDescriptions() {
        GraphicsDevice[] screens = CanvasFrame.getScreenDevices();
        String[] descriptions = new String[screens.length];
        for (int i = 0; i < screens.length; ++i) {
            descriptions[i] = screens[i].getIDstring();
        }
        return descriptions;
    }

    public static DisplayMode getDisplayMode(int screenNumber) {
        GraphicsDevice[] screens = CanvasFrame.getScreenDevices();
        if (screenNumber >= 0 && screenNumber < screens.length) {
            return screens[screenNumber].getDisplayMode();
        }
        return null;
    }

    public static double getGamma(int screenNumber) {
        GraphicsDevice[] screens = CanvasFrame.getScreenDevices();
        if (screenNumber >= 0 && screenNumber < screens.length) {
            return CanvasFrame.getGamma(screens[screenNumber]);
        }
        return 0.0;
    }

    public static double getDefaultGamma() {
        return CanvasFrame.getGamma(CanvasFrame.getDefaultScreenDevice());
    }

    public static double getGamma(GraphicsDevice screen) {
        ColorSpace cs = screen.getDefaultConfiguration().getColorModel().getColorSpace();
        if (cs.isCS_sRGB()) {
            return 2.2;
        }
        try {
            return ((ICC_ProfileRGB)((ICC_ColorSpace)cs).getProfile()).getGamma(0);
        }
        catch (RuntimeException runtimeException) {
            return 0.0;
        }
    }

    public static GraphicsDevice getScreenDevice(int screenNumber) throws Exception {
        GraphicsDevice[] screens = CanvasFrame.getScreenDevices();
        if (screenNumber >= screens.length) {
            throw new Exception("CanvasFrame Error: Screen number " + screenNumber + " not found. There are only " + screens.length + " screens.");
        }
        return screens[screenNumber];
    }

    public static GraphicsDevice[] getScreenDevices() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
    }

    public static GraphicsDevice getDefaultScreenDevice() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }

    public CanvasFrame(String title) {
        this(title, 0.0);
    }

    public CanvasFrame(String title, double gamma) {
        super(title);
        this.init(false, null, gamma);
    }

    public CanvasFrame(String title, GraphicsConfiguration gc) {
        this(title, gc, 0.0);
    }

    public CanvasFrame(String title, GraphicsConfiguration gc, double gamma) {
        super(title, gc);
        this.init(false, null, gamma);
    }

    public CanvasFrame(String title, int screenNumber, DisplayMode displayMode) throws Exception {
        this(title, screenNumber, displayMode, 0.0);
    }

    public CanvasFrame(String title, int screenNumber, DisplayMode displayMode, double gamma) throws Exception {
        super(title, CanvasFrame.getScreenDevice(screenNumber).getDefaultConfiguration());
        this.init(true, displayMode, gamma);
    }

    private void init(final boolean fullScreen, final DisplayMode displayMode, final double gamma) {
        Runnable r = new Runnable(){

            @Override
            public void run() {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(CanvasFrame.this.keyEventDispatch);
                GraphicsDevice gd = CanvasFrame.this.getGraphicsConfiguration().getDevice();
                DisplayMode d = gd.getDisplayMode();
                DisplayMode d2 = null;
                if (displayMode != null && d != null) {
                    int w = displayMode.getWidth();
                    int h = displayMode.getHeight();
                    int b = displayMode.getBitDepth();
                    int r = displayMode.getRefreshRate();
                    d2 = new DisplayMode(w > 0 ? w : d.getWidth(), h > 0 ? h : d.getHeight(), b > 0 ? b : d.getBitDepth(), r > 0 ? r : d.getRefreshRate());
                }
                if (fullScreen) {
                    CanvasFrame.this.setUndecorated(true);
                    CanvasFrame.this.getRootPane().setWindowDecorationStyle(0);
                    CanvasFrame.this.setResizable(false);
                    gd.setFullScreenWindow(CanvasFrame.this);
                } else {
                    CanvasFrame.this.setLocationByPlatform(true);
                }
                if (d2 != null && !d2.equals(d)) {
                    gd.setDisplayMode(d2);
                }
                double g = gamma == 0.0 ? CanvasFrame.getGamma(gd) : gamma;
                CanvasFrame.this.inverseGamma = g == 0.0 ? 1.0 : 1.0 / g;
                CanvasFrame.this.setVisible(true);
                CanvasFrame.this.initCanvas(fullScreen, displayMode, gamma);
            }
        };
        if (EventQueue.isDispatchThread()) {
            r.run();
        } else {
            try {
                EventQueue.invokeAndWait(r);
            }
            catch (java.lang.Exception exception) {
                // empty catch block
            }
        }
    }

    protected void initCanvas(boolean fullScreen, DisplayMode displayMode, double gamma) {
        this.canvas = new Canvas(){

            @Override
            public void update(Graphics g) {
                this.paint(g);
            }

            @Override
            public void paint(Graphics g) {
                try {
                    if (CanvasFrame.this.canvas.getWidth() <= 0 || CanvasFrame.this.canvas.getHeight() <= 0) {
                        return;
                    }
                    BufferStrategy strategy = CanvasFrame.this.canvas.getBufferStrategy();
                    while (true) {
                        g = strategy.getDrawGraphics();
                        if (CanvasFrame.this.color != null) {
                            g.setColor(CanvasFrame.this.color);
                            g.fillRect(0, 0, this.getWidth(), this.getHeight());
                        }
                        if (CanvasFrame.this.image != null) {
                            g.drawImage(CanvasFrame.this.image, 0, 0, this.getWidth(), this.getHeight(), null);
                        }
                        if (CanvasFrame.this.buffer != null) {
                            g.drawImage(CanvasFrame.this.buffer, 0, 0, this.getWidth(), this.getHeight(), null);
                        }
                        g.dispose();
                        if (strategy.contentsRestored()) continue;
                        strategy.show();
                        if (!strategy.contentsLost()) break;
                    }
                }
                catch (NullPointerException nullPointerException) {
                }
                catch (IllegalStateException illegalStateException) {
                    // empty catch block
                }
            }
        };
        if (fullScreen) {
            this.canvas.setSize(this.getSize());
            this.needInitialResize = false;
        } else {
            this.canvas.setSize(10, 10);
            this.needInitialResize = true;
        }
        this.getContentPane().add(this.canvas);
        this.canvas.setVisible(true);
        this.canvas.createBufferStrategy(2);
    }

    public long getLatency() {
        return this.latency;
    }

    public void setLatency(long latency) {
        this.latency = latency;
    }

    public void waitLatency() throws InterruptedException {
        Thread.sleep(this.getLatency());
    }

    public KeyEvent waitKey() throws InterruptedException {
        return this.waitKey(0);
    }

    public synchronized KeyEvent waitKey(int delay) throws InterruptedException {
        if (delay >= 0) {
            this.keyEvent = null;
            this.wait(delay);
        }
        KeyEvent e = this.keyEvent;
        this.keyEvent = null;
        return e;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public Dimension getCanvasSize() {
        return this.canvas.getSize();
    }

    public void setCanvasSize(final int width, final int height) {
        Dimension d = this.getCanvasSize();
        if (d.width == width && d.height == height) {
            return;
        }
        Runnable r = new Runnable(){

            @Override
            public void run() {
                CanvasFrame.this.setExtendedState(0);
                CanvasFrame.this.canvas.setSize(width, height);
                CanvasFrame.this.pack();
                CanvasFrame.this.canvas.setSize(width + 1, height + 1);
                CanvasFrame.this.canvas.setSize(width, height);
                CanvasFrame.this.needInitialResize = false;
            }
        };
        if (EventQueue.isDispatchThread()) {
            r.run();
        } else {
            try {
                EventQueue.invokeAndWait(r);
            }
            catch (java.lang.Exception exception) {
                // empty catch block
            }
        }
    }

    public double getCanvasScale() {
        return this.initialScale;
    }

    public void setCanvasScale(double initialScale) {
        this.initialScale = initialScale;
        this.needInitialResize = true;
    }

    public Graphics2D createGraphics() {
        if (this.buffer == null || this.buffer.getWidth() != this.canvas.getWidth() || this.buffer.getHeight() != this.canvas.getHeight()) {
            BufferedImage newbuffer = this.canvas.getGraphicsConfiguration().createCompatibleImage(this.canvas.getWidth(), this.canvas.getHeight(), 3);
            if (this.buffer != null) {
                Graphics g = newbuffer.getGraphics();
                g.drawImage(this.buffer, 0, 0, null);
                g.dispose();
            }
            this.buffer = newbuffer;
        }
        return this.buffer.createGraphics();
    }

    public void releaseGraphics(Graphics2D g) {
        g.dispose();
        this.canvas.paint(null);
    }

    public void showColor(Color color) {
        this.color = color;
        this.image = null;
        this.canvas.paint(null);
    }

    public void showImage(Frame image) {
        this.showImage(image, false);
    }

    public void showImage(Frame image, boolean flipChannels) {
        this.showImage(this.converter.getBufferedImage(image, Java2DFrameConverter.getBufferedImageType(image) == 0 ? 1.0 : this.inverseGamma, flipChannels, null));
    }

    public void showImage(Image image) {
        if (image == null) {
            return;
        }
        if (this.isResizable() && this.needInitialResize) {
            int w = (int)Math.round((double)image.getWidth(null) * this.initialScale);
            int h = (int)Math.round((double)image.getHeight(null) * this.initialScale);
            this.setCanvasSize(w, h);
        }
        this.color = null;
        this.image = image;
        this.canvas.paint(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void tile(final CanvasFrame[] frames) {
        class MovedListener
        extends ComponentAdapter {
            boolean moved = false;

            MovedListener() {
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void componentMoved(ComponentEvent e) {
                Component c;
                this.moved = true;
                Component component = c = e.getComponent();
                synchronized (component) {
                    c.notify();
                }
            }
        }
        final MovedListener movedListener = new MovedListener();
        int canvasCols = (int)Math.round(Math.sqrt(frames.length));
        if (canvasCols * canvasCols < frames.length) {
            ++canvasCols;
        }
        int canvasX = 0;
        int canvasY = 0;
        int canvasMaxY = 0;
        for (int i = 0; i < frames.length; ++i) {
            final int n = i;
            final int x = canvasX;
            final int y = canvasY;
            try {
                movedListener.moved = false;
                EventQueue.invokeLater(new Runnable(){
                    {
                    }

                    @Override
                    public void run() {
                        frames[n].addComponentListener(movedListener);
                        frames[n].setLocation(x, y);
                    }
                });
                for (int count = 0; !movedListener.moved && count < 5; ++count) {
                    CanvasFrame canvasFrame = frames[n];
                    synchronized (canvasFrame) {
                        frames[n].wait(100L);
                        continue;
                    }
                }
                EventQueue.invokeLater(new Runnable(){
                    {
                    }

                    @Override
                    public void run() {
                        frames[n].removeComponentListener(movedListener);
                    }
                });
            }
            catch (java.lang.Exception exception) {
                // empty catch block
            }
            canvasX = frames[i].getX() + frames[i].getWidth();
            canvasMaxY = Math.max(canvasMaxY, frames[i].getY() + frames[i].getHeight());
            if ((i + 1) % canvasCols != 0) continue;
            canvasX = 0;
            canvasY = canvasMaxY;
        }
    }

    public static class Exception
    extends java.lang.Exception {
        public Exception(String message) {
            super(message);
        }

        public Exception(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

