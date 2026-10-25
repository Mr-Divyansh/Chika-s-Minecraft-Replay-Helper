/*
 * Decompiled with CFR 0.152.
 */
package cl.eye;

public class CLCamera {
    public static int CLEYE_MONO_PROCESSED = 0;
    public static int CLEYE_COLOR_PROCESSED = 1;
    public static int CLEYE_MONO_RAW = 2;
    public static int CLEYE_COLOR_RAW = 3;
    public static int CLEYE_BAYER_RAW = 4;
    public static int CLEYE_QVGA = 0;
    public static int CLEYE_VGA = 1;
    public static int CLEYE_AUTO_GAIN = 0;
    public static int CLEYE_GAIN = 1;
    public static int CLEYE_AUTO_EXPOSURE = 2;
    public static int CLEYE_EXPOSURE = 3;
    public static int CLEYE_AUTO_WHITEBALANCE = 4;
    public static int CLEYE_WHITEBALANCE_RED = 5;
    public static int CLEYE_WHITEBALANCE_GREEN = 6;
    public static int CLEYE_WHITEBALANCE_BLUE = 7;
    public static int CLEYE_HFLIP = 8;
    public static int CLEYE_VFLIP = 9;
    public static int CLEYE_HKEYSTONE = 10;
    public static int CLEYE_VKEYSTONE = 11;
    public static int CLEYE_XOFFSET = 12;
    public static int CLEYE_YOFFSET = 13;
    public static int CLEYE_ROTATION = 14;
    public static int CLEYE_ZOOM = 15;
    public static int CLEYE_LENSCORRECTION1 = 16;
    public static int CLEYE_LENSCORRECTION2 = 17;
    public static int CLEYE_LENSCORRECTION3 = 18;
    public static int CLEYE_LENSBRIGHTNESS = 19;
    private int cameraInstance = 0;
    private static boolean libraryLoaded = false;
    private static String dllpathx32 = "C://Program Files//Code Laboratories//CL-Eye Platform SDK//Bin//CLEyeMulticam.dll";
    private static String dllpathx64 = "C://Program Files (x86)//Code Laboratories//CL-Eye Platform SDK//Bin//CLEyeMulticam.dll";

    static native int CLEyeGetCameraCount();

    static native String CLEyeGetCameraUUID(int var0);

    static native int CLEyeCreateCamera(int var0, int var1, int var2, int var3);

    static native boolean CLEyeDestroyCamera(int var0);

    static native boolean CLEyeCameraStart(int var0);

    static native boolean CLEyeCameraStop(int var0);

    static native boolean CLEyeSetCameraParameter(int var0, int var1, int var2);

    static native int CLEyeGetCameraParameter(int var0, int var1);

    static native boolean CLEyeCameraGetFrame(int var0, int[] var1, int var2);

    public static boolean IsLibraryLoaded() {
        return libraryLoaded;
    }

    public static void loadLibrary(String libraryPath) {
        if (libraryLoaded) {
            return;
        }
        try {
            System.load(libraryPath);
            System.out.println("CLEyeMulticam.dll loaded");
        }
        catch (UnsatisfiedLinkError e1) {
            System.out.println("(3) Could not find the CLEyeMulticam.dll (Custom Path)");
        }
    }

    public static int cameraCount() {
        return CLCamera.CLEyeGetCameraCount();
    }

    public static String cameraUUID(int index) {
        return CLCamera.CLEyeGetCameraUUID(index);
    }

    public void dispose() {
        this.stopCamera();
        this.destroyCamera();
    }

    public boolean createCamera(int cameraIndex, int mode, int resolution, int framerate) {
        this.cameraInstance = CLCamera.CLEyeCreateCamera(cameraIndex, mode, resolution, framerate);
        return this.cameraInstance != 0;
    }

    public boolean destroyCamera() {
        return CLCamera.CLEyeDestroyCamera(this.cameraInstance);
    }

    public boolean startCamera() {
        return CLCamera.CLEyeCameraStart(this.cameraInstance);
    }

    public boolean stopCamera() {
        return CLCamera.CLEyeCameraStop(this.cameraInstance);
    }

    public boolean getCameraFrame(int[] imgData, int waitTimeout) {
        return CLCamera.CLEyeCameraGetFrame(this.cameraInstance, imgData, waitTimeout);
    }

    public boolean setCameraParam(int param, int val) {
        return CLCamera.CLEyeSetCameraParameter(this.cameraInstance, param, val);
    }

    public int getCameraParam(int param) {
        return CLCamera.CLEyeGetCameraParameter(this.cameraInstance, param);
    }

    static {
        try {
            System.load(dllpathx32);
            libraryLoaded = true;
            System.out.println("CLEyeMulticam.dll loaded");
        }
        catch (UnsatisfiedLinkError e1) {
            System.out.println("(1) Could not find the CLEyeMulticam.dll");
            try {
                System.load(dllpathx64);
                libraryLoaded = true;
                System.out.println("CLEyeMulticam.dll loaded");
            }
            catch (UnsatisfiedLinkError e2) {
                System.out.println("(2) Could not find the CLEyeMulticam.dll");
            }
        }
    }
}

