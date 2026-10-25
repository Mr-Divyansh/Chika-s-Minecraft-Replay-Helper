/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import java.beans.PropertyVetoException;
import org.bytedeco.javacv.BaseSettings;
import org.bytedeco.javacv.CameraDevice;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.ProjectiveDevice;

public class CameraSettings
extends BaseSettings {
    boolean calibrated = false;
    double monitorWindowsScale = 1.0;
    Class<? extends FrameGrabber> frameGrabber = null;

    public CameraSettings() {
        this(false);
    }

    public CameraSettings(boolean calibrated) {
        this.calibrated = calibrated;
    }

    public int getQuantity() {
        return this.size();
    }

    public void setQuantity(int quantity) throws PropertyVetoException {
        int i;
        quantity = Math.max(1, quantity);
        CameraDevice.Settings[] a = this.toArray();
        for (i = a.length; i > quantity; --i) {
            this.remove(a[i - 1]);
        }
        while (i < quantity) {
            ProjectiveDevice.Settings c = this.calibrated ? new CameraDevice.CalibratedSettings() : new CameraDevice.CalibrationSettings();
            c.setName("Camera " + String.format("%2d", i));
            c.setDeviceNumber(i);
            c.setFrameGrabber(this.frameGrabber);
            this.add(c);
            ++i;
        }
        this.pcSupport.firePropertyChange("quantity", a.length, quantity);
    }

    public double getMonitorWindowsScale() {
        return this.monitorWindowsScale;
    }

    public void setMonitorWindowsScale(double monitorWindowsScale) {
        this.monitorWindowsScale = monitorWindowsScale;
    }

    public Class<? extends FrameGrabber> getFrameGrabber() {
        return this.frameGrabber;
    }

    public void setFrameGrabber(Class<? extends FrameGrabber> frameGrabber) {
        this.frameGrabber = frameGrabber;
        this.pcSupport.firePropertyChange("frameGrabber", this.frameGrabber, this.frameGrabber);
    }

    public CameraDevice.Settings[] toArray() {
        return (CameraDevice.Settings[])this.toArray(new CameraDevice.Settings[this.size()]);
    }
}

