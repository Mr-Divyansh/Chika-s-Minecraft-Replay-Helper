/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import java.beans.PropertyChangeListener;
import java.beans.beancontext.BeanContextSupport;
import java.util.Arrays;
import org.bytedeco.javacv.BaseChildSettings;

public class BaseSettings
extends BeanContextSupport
implements Comparable<BaseSettings> {
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcSupport.addPropertyChangeListener(listener);
        for (Object s : this.toArray()) {
            if (s instanceof BaseChildSettings) {
                ((BaseChildSettings)s).addPropertyChangeListener(listener);
                continue;
            }
            if (!(s instanceof BaseSettings)) continue;
            ((BaseSettings)s).addPropertyChangeListener(listener);
        }
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcSupport.removePropertyChangeListener(listener);
        for (Object s : this.toArray()) {
            if (s instanceof BaseChildSettings) {
                ((BaseChildSettings)s).removePropertyChangeListener(listener);
                continue;
            }
            if (!(s instanceof BaseSettings)) continue;
            ((BaseSettings)s).addPropertyChangeListener(listener);
        }
    }

    @Override
    public int compareTo(BaseSettings o) {
        return this.getName().compareTo(o.getName());
    }

    protected String getName() {
        return "";
    }

    @Override
    public Object[] toArray() {
        Object[] a = super.toArray();
        Arrays.sort(a);
        return a;
    }

    @Override
    public Object[] toArray(Object[] a) {
        a = super.toArray(a);
        Arrays.sort(a);
        return a;
    }
}

