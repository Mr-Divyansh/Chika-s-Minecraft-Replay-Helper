/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import java.beans.PropertyChangeListener;
import org.bytedeco.javacv.BaseChildSettings;
import org.bytedeco.javacv.BaseSettings;
import org.bytedeco.javacv.ProjectiveDevice;
import org.bytedeco.javacv.ProjectorDevice;

public class ProjectorSettings
extends BaseSettings {
    boolean calibrated = false;

    public ProjectorSettings() {
        this(false);
    }

    public ProjectorSettings(boolean calibrated) {
        this.calibrated = calibrated;
    }

    public int getQuantity() {
        return this.size();
    }

    public void setQuantity(int quantity) {
        int i;
        ProjectorDevice.Settings[] a = this.toArray();
        for (i = a.length; i > quantity; --i) {
            this.remove(a[i - 1]);
        }
        while (i < quantity) {
            ProjectiveDevice.Settings c = this.calibrated ? new ProjectorDevice.CalibratedSettings() : new ProjectorDevice.CalibrationSettings();
            c.setName("Projector " + String.format("%2d", i));
            c.setScreenNumber(c.getScreenNumber() + i);
            this.add(c);
            for (PropertyChangeListener l : this.pcSupport.getPropertyChangeListeners()) {
                ((BaseChildSettings)c).addPropertyChangeListener(l);
            }
            ++i;
        }
        this.pcSupport.firePropertyChange("quantity", a.length, quantity);
    }

    public ProjectorDevice.Settings[] toArray() {
        return (ProjectorDevice.Settings[])this.toArray(new ProjectorDevice.Settings[this.size()]);
    }
}

