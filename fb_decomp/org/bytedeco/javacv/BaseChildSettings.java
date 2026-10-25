/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.beancontext.BeanContextChildSupport;
import java.util.ListResourceBundle;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class BaseChildSettings
extends BeanContextChildSupport
implements Comparable<BaseChildSettings> {
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcSupport.removePropertyChangeListener(listener);
    }

    @Override
    public int compareTo(BaseChildSettings o) {
        return this.getName().compareTo(o.getName());
    }

    protected String getName() {
        return "";
    }

    public static class PropertyVetoExceptionThatNetBeansLikes
    extends PropertyVetoException
    implements Callable {
        public PropertyVetoExceptionThatNetBeansLikes(String mess, PropertyChangeEvent evt) {
            super(mess, evt);
        }

        public Object call() throws Exception {
            LogRecord lg = new LogRecord(Level.ALL, this.getMessage());
            lg.setResourceBundle(new ListResourceBundle(){

                @Override
                protected Object[][] getContents() {
                    return new Object[][]{{this.getMessage(), this.getMessage()}};
                }
            });
            return new LogRecord[]{lg};
        }
    }
}

